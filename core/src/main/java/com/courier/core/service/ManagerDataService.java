package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.UserLocation;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VManagerCurrentData;
import com.courier.commons.vModel.VManagerSubCurrentData;
import com.courier.core.vModel.VYtoPage;
import com.courier.db.dao.ReportMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.*;
import com.courier.db.vModel.VYtoData;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by ryan on 16/1/19.
 */
@Service
@Transactional
public class ManagerDataService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerDataService.class);

    @Autowired
    BranchService branchService;
    @Autowired
    UserService userService;
    @Autowired
    private OrgReportService orgReportService;
    @Autowired
    private CacheUtil cacheUtil;

    @Autowired OrderOperationService orderOperationService;

    @Autowired
    private UserLocationService userLocationService;

    @Value("${show.location.begin.hour}")
    private Integer showLocationBegin;
    @Value("${show.location.end.hour}")
    private Integer showLocationEnd;

    /**
     *
     */
    public Page<VManagerSubCurrentData> buildSubList(String orgCode, Byte roleVal, Integer pageNo, Integer pageSize, Date beginT, Date endT) throws Exception {
        if(StringUtils.isEmpty(orgCode) || roleVal == null) return null;

        if(pageNo != null) pageNo = (pageNo - 1) * pageSize;

        Enumerate.ManagerRole nextLevelRole = Enumerate.ManagerRole.getNextLevel(roleVal);
        List<String> orgCodes = new ArrayList<>();
        int totalNo = 0;

        if(roleVal.equals(Enumerate.ManagerRole.provinceCode.getCode())){                    //  todo 省区网管
            orgCodes = branchService.fetchCompanyByOrgCode(orgCode, CommonEnum.BranchType.BRANCH.getCode(), pageNo, pageSize);
            totalNo = branchService.countCompanyByOrgCode(orgCode, CommonEnum.BranchType.BRANCH.getCode());

        }else if(roleVal.equals(Enumerate.ManagerRole.companyCode.getCode())){               //  todo 分公司
            if(pageNo == 0) {
                pageSize = pageSize-1;
            }else{
                pageNo = pageNo - 1;
            }
            totalNo = branchService.countTerminalsByBranchCode(orgCode);
            orgCodes = branchService.findTerminalsByBranchCode(orgCode, pageNo, pageSize);

        }else if(roleVal.equals(Enumerate.ManagerRole.branchCode.getCode())){                //  todo 分部|直属网点
            orgCodes.add(orgCode);
        }

        // todo 判断是否需要子网点|所属快递员数据
        return buildData(roleVal, orgCode, orgCodes, nextLevelRole, totalNo, pageNo, pageSize, beginT, endT);
    }


    public VYtoPage buildMyOrg(String orgCode, Byte roleVal) throws Exception {
        if(StringUtils.isEmpty(orgCode) || roleVal == null) return null;

        VYtoPage page = buildMyOrgData(orgCode, roleVal);
        return page;
    }


    public VManagerSubCurrentData todayDataQuery(String key, Byte targetRole, Date beginT, Date endT) {
        if(StringUtils.isEmpty(key)) return null;

        if(targetRole.equals(Enumerate.ManagerRole.provinceCode.getCode().byteValue())) {                    //  todo 分公司
            return buildItem(key, null, Enumerate.ManagerRole.companyCode, beginT, endT);

        }else if(targetRole.equals(Enumerate.ManagerRole.companyCode.getCode().byteValue())){                //  todo 分部
            return buildItem(key, null, Enumerate.ManagerRole.branchCode, beginT, endT);

        }else if(targetRole.equals(Enumerate.ManagerRole.branchCode.getCode().byteValue())){                //  todo 快递员
            return buildForCourier(key, beginT, endT);
        }

        return null;
    }



    /*******************************  begin private method ********************************/
    // todo  组装子数据
    private Page<VManagerSubCurrentData> buildData(Byte roleVal, String orgCode, List<String> orgCodes, Enumerate.ManagerRole nextLevelRole,
                                                   Integer totalNo, Integer pageNo, Integer pageSize, Date beginT, Date endT) {
        //  todo 分部|直属网点
        if(roleVal == Enumerate.ManagerRole.branchCode.getCode()){
            totalNo = userService.countByOrgCode(orgCode);

            List<VManagerSubCurrentData> list = orderOperationService.buildForManageList(orgCode, beginT, endT, pageNo, pageSize, showLocationBegin, showLocationEnd);

            return new Page(pageNo, pageSize, list, totalNo);
        }

        //  todo 分公司
        List<VManagerSubCurrentData> list = new ArrayList<>();
        if(roleVal == Enumerate.ManagerRole.companyCode.getCode() && pageNo == 0)                   // todo 如果类型为分公司 且 为第一页 则将该分公司直属快递员信息一并统计（加入第一位）
            list.add(buildItem(orgCode, roleVal, nextLevelRole, beginT, endT));

        if(!CollectionUtils.isEmpty(orgCodes)) {
            for (String code : orgCodes){
                VManagerSubCurrentData subData = buildItem(code, null, nextLevelRole, beginT, endT);
                if(subData != null) list.add(subData);
            }
        }

        return new Page(pageNo, pageSize, list, totalNo);
    }


    // todo 根据用户id统计（直属网点）
    private VManagerSubCurrentData buildForCourier(String jobNo, Date beginT, Date endT) {
        if(StringUtils.isEmpty(jobNo)) return null;
        User user = userService.findByJobNo(jobNo);
        if(user == null) return null;

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        VManagerCurrentData result = orderOperationService.buildData(null, beginT, endT, Arrays.asList(user.getId()));

        VManagerSubCurrentData subData = new VManagerSubCurrentData(result.getCollectingNo(), result.getCollectNo(), user.getJobNo(), user.getUsername(), result.getProblemNo(),
                result.getSendingNo(), result.getSignNo(), Enumerate.ManagerRole.courier.getCode(), true, user.getOrgCode(), 0);
        subData.setUserId(user.getId());
        if (hour >= showLocationBegin && hour < showLocationEnd) { //每天8点到19点之间可以查看地理位置
            ResponseDTO responseDTO = userLocationService.getUserGps(user.getOrgCode(), user.getJobNo());
            if(responseDTO != null && responseDTO.getCodeEnum().equals(CodeEnum.C1000)){
                UserLocation userLocation = (UserLocation) responseDTO.getT2();

                subData.setLat(userLocation.getLat());
                subData.setLng(userLocation.getLng());
            }
        }

        return subData;
    }

    private VManagerSubCurrentData buildItem(String code, Byte roleVal, Enumerate.ManagerRole nextLevelRole, Date beginT, Date endT){
        if(StringUtils.isEmpty(code)) return null;

        Byte nextRoleVal = nextLevelRole.getCode();
        VManagerCurrentData result = orderOperationService.countForManage(code, nextRoleVal, beginT, endT);
        return new VManagerSubCurrentData(result.getCollectingNo(), result.getCollectNo(), code, branchService.queryOrgName(code, roleVal == null ? nextRoleVal : roleVal), result.getProblemNo(),
                result.getSendingNo(), result.getSignNo(), nextRoleVal, false, code, result.getTotalUserNo());
    }


    private VYtoPage buildMyOrgData(String orgCode, Byte role) {
        if(StringUtils.isEmpty(orgCode)) return new VYtoPage();
        VYtoData vYtoData = new VYtoData();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date lastDayCountT = DateUtil.getMiddleT(calendar);
        Calendar calendarMouth = Calendar.getInstance();
        calendarMouth.add(Calendar.MONTH, -1);
        calendarMouth.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date lastMonthCountT = DateUtil.getMiddleT(calendarMouth);

        // 一号以前取上月，一号以后取当月
        List<Report> month_temp = null;
        Report avg_temp = null;
        Byte dayCode = null;
        Byte monthCode = null;
        Byte monthTempCode = null;
        Byte avgCode = null;
        Byte avgTempCode = null;
        String pCode = null;
        String bCode = null;
        String oCode = null;
        if (Enumerate.ManagerRole.provinceCode.getCode().equals(role)) {
            monthCode = CommonEnum.OrgReportEnum.province_month.getCode();
            monthTempCode = CommonEnum.OrgReportEnum.province_month_temp.getCode();
            avgCode = CommonEnum.OrgReportEnum.province_avg.getCode();
            avgTempCode = CommonEnum.OrgReportEnum.province_avg_temp.getCode();
            Branch branch = branchService.findByOrgCode(orgCode);
            pCode = branch == null ? "" : branch.getProvinceCode();
            dayCode = CommonEnum.OrgReportEnum.province.getCode();
        }else if (Enumerate.ManagerRole.companyCode.getCode().equals(role)) {
            monthCode = CommonEnum.OrgReportEnum.branch_month.getCode();
            monthTempCode = CommonEnum.OrgReportEnum.branch_month_temp.getCode();
            avgCode = CommonEnum.OrgReportEnum.branch_avg.getCode();
            avgTempCode = CommonEnum.OrgReportEnum.branch_avg_temp.getCode();
            bCode = orgCode;
            dayCode = CommonEnum.OrgReportEnum.branch.getCode();
        }else {
            monthCode = CommonEnum.OrgReportEnum.terminal_month.getCode();
            monthTempCode = CommonEnum.OrgReportEnum.terminal_month_temp.getCode();
            avgCode = CommonEnum.OrgReportEnum.terminal_avg.getCode();
            avgTempCode = CommonEnum.OrgReportEnum.terminal_avg_temp.getCode();
            oCode = orgCode;
            dayCode = CommonEnum.OrgReportEnum.terminal.getCode();
        }
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1) {
            month_temp = orgReportService.findOneByKeys(oCode, bCode, pCode, monthCode, lastMonthCountT, null);
            avg_temp = getAvg(lastMonthCountT, avgCode);
        } else {
            month_temp = orgReportService.findOneByKeys(oCode, bCode, pCode, monthTempCode, lastDayCountT, null);
            avg_temp = getAvgTemp(lastDayCountT, avgTempCode);
        }
        // todo  当月总
        if (month_temp != null && month_temp.size()>0) {
            Report temp = month_temp.get(0);
            vYtoData.setMonthCollectNo(temp.getCollectNo().intValue());
            vYtoData.setMonthSendNo(temp.getSendNo().intValue());
        }
        // todo  当月全网平均总
        if (avg_temp != null) {
            vYtoData.setMonthMeanCollectdNo(avg_temp.getCollectNo());
            vYtoData.setMonthMeanSendNo(avg_temp.getSendNo());
        }
        Integer queryDays = 7;
        calendar.add(Calendar.DATE, -queryDays);

        List<Report> reports = orgReportService.findOneByKeys(oCode, bCode, pCode, dayCode, null, queryDays);
        return new VYtoPage(reports, vYtoData);
    }

    private Report getAvg(Date lastMonthCountT, Byte avgCode){
        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -1);
        }
        String key = String.format(CacheConstant.ORG_PAGE_AVG_KEY, DateUtil.toShortDay(calendar.getTime()));
        Report avg = cacheUtil.getCacheByFromRedis(key, Report.class);
        if (avg == null) {
            List<Report> reports = orgReportService.findOneByKeys(null, null, null, avgCode, lastMonthCountT, null);
            if (reports != null && reports.size() > 0) avg = reports.get(0);
            try{
                cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, avg);
            }catch (Exception e){
                logger.error("put org avg to redis, error is {}", e.getMessage());
            }
        }
        return avg;
    }

    private Report getAvgTemp(Date lastDayCountT, Byte avgTempCode){
        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -1);
        }
        String key = String.format(CacheConstant.ORG_PAGE_AVG_TEMP_KEY, DateUtil.toShortDay(calendar.getTime()));
        Report avgTemp = cacheUtil.getCacheByFromRedis(key, Report.class);
        if (avgTemp == null) {
            List<Report> reports = orgReportService.findOneByKeys(null, null, null, avgTempCode, lastDayCountT, null);
            if (reports != null && reports.size() > 0) avgTemp = reports.get(0);
            try{
                cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, avgTemp);
            }catch (Exception e){
                logger.error("put org avgTemp to redis, error is {}", e.getMessage());
            }
        }
        return avgTemp;
    }


}
