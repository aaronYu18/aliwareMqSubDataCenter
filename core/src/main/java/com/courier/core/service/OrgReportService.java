package com.courier.core.service;

import com.courier.commons.constant.Global;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.convert.ReportConvert;
import com.courier.core.convert.ReportNumComparable;
import com.courier.core.convert.ReportRedisKey;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.OrgReportMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.*;
import com.courier.db.vModel.VReportTotalNum;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Constant;
import com.courier.sdk.constant.Enumerate;
import com.courier.sdk.manage.resp.ManageHomePageResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 2015/11/2.
 */
@Service
@Transactional
public class OrgReportService extends BaseManager<OrgReport> {
    private static final Logger logger = LoggerFactory.getLogger(OrgReportService.class);

    @Autowired OrgReportMapper orgReportMapper;

    @Autowired ManagerService managerService;
    @Autowired CacheUtil cacheUtil;
    @Autowired BranchService branchService;
    @Autowired ReportService reportService;
    @Autowired UserService userService;
    @Autowired RegionService regionService;

    @Override
    public OrgReport getEntity() {
        return new OrgReport();
    }

    @Override
    public BaseMapper<OrgReport> getBaseMapper() {
        return orgReportMapper;
    }

    public void insertTerminalData(Date countT, CommonEnum.OrgReportEnum orgReportEnum) {
        orgReportMapper.insertTerminalData(countT, orgReportEnum.getCode());
    }

    public void insertBranchData(Date countT, CommonEnum.OrgReportEnum orgReportEnum, CommonEnum.OrgReportEnum next) {
        orgReportMapper.insertBranchData(countT, orgReportEnum.getCode(), next.getCode());
    }

    public void insertProvinceData(Date countT, CommonEnum.OrgReportEnum orgReportEnum, CommonEnum.OrgReportEnum next) {
        orgReportMapper.insertProvinceData(countT, orgReportEnum.getCode(), next.getCode());
    }
    public void insertAvgData(Date beginMT, Date endMT, Date countT, CommonEnum.OrgReportEnum type, CommonEnum.OrgReportEnum matchType) {
        Map<String, String> filters = new HashMap<>();

        filters.put("beginMT", DateUtil.dateToStr(beginMT));
        filters.put("endMT", DateUtil.dateToStr(endMT));
        filters.put("countT", DateUtil.dateToStr(countT));

        if(type != null)
            filters.put("type", type.getCode().toString());
        if(matchType != null)
            filters.put("matchType", matchType.getCode().toString());

        orgReportMapper.insertAvgData(filters);
    }

    public void insertMonthTotalData(Date beginMT, Date endMT, Date countT, CommonEnum.OrgReportEnum type, CommonEnum.OrgReportEnum matchType) {
        Map<String, String> filters = new HashMap<>();

        filters.put("beginMT", DateUtil.dateToStr(beginMT));
        filters.put("endMT", DateUtil.dateToStr(endMT));
        filters.put("countT", DateUtil.dateToStr(countT));

        if(type != null)
            filters.put("type", type.getCode().toString());
        if(matchType != null)
            filters.put("matchType", matchType.getCode().toString());

        orgReportMapper.insertMonthTotalData(filters);
    }

    public void deleteDynaData() {
        orgReportMapper.deleteDynaData();
    }

    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public List<Report> findOneByKeys(String oCode, String bCode, String pCode, Byte type, Date countTime, Integer queryDays){
        Map<String, Object> filters  = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(oCode)) filters.put("orgCode", oCode);
        if (!StringUtils.isEmpty(bCode)) filters.put("branchCode", bCode);
        if (!StringUtils.isEmpty(pCode)) filters.put("provinceCode", pCode);
        if (type != null) filters.put("type", type);
        if (countTime != null) filters.put("countTime", countTime);
        if (queryDays != null && queryDays != 0) filters.put("queryDays", queryDays);

        return orgReportMapper.findOneByKeys(filters);
    }

    public List<VReportTotalNum> findOrgReportGroupByTime(String beginDate, String endDate, Byte type, String provinceCode, String branchCode, String orgCode) {
        return orgReportMapper.findOrgReportGroupByTime(beginDate, endDate, type, provinceCode, branchCode, orgCode);
    }

    VReportTotalNum countOrgReportGroupByTime(String beginDate, String endDate, Byte type, String provinceCode, String branchCode, String orgCode) {
        return orgReportMapper.countOrgReportGroupByTime(beginDate, endDate, type, provinceCode, branchCode, orgCode);
    }

    /**
     *  分公司直属快递员的统计 (相当于分部) type:CommonEnum.OrgReportEnum.terminal
     * @return
     */
    public VReportTotalNum findByOrgAndBranchCode(String beginDate, String endDate, String branchCode) {
        return orgReportMapper.findByOrgAndBranchCode(beginDate,endDate,branchCode);
    }

    List<VReportTotalNum> findMyWorkByBranchDesc(String beginDate, String endDate,String branchCode, Integer pageNo, Integer pageSize) {
        return orgReportMapper.findMyWorkByBranchDesc(beginDate,endDate,branchCode, pageNo, pageSize);
    }

    List<VReportTotalNum> findMyWorkByProvinceDesc(String beginDate, String endDate,String provinceCode, Integer pageNo, Integer pageSize) {
        return orgReportMapper.findMyWorkByProvinceDesc(beginDate,endDate,provinceCode, pageNo, pageSize);
    }

    VReportTotalNum queryByRegionKey(Byte type, String beginDate, String endDate,
                                     String provinceCode, String branchCode, String key) {
        return orgReportMapper.queryByRegionKey(type, beginDate, endDate, provinceCode, branchCode, key);
    }
    /**
     * 查到最近7天的数据
     * @param uuid
     * @param queryType
     * @param orgCode
     * @return
     */
    public ResponseDTO find7DayByOrg(String uuid, Enumerate.ManagerRole queryType, String orgCode) {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        if (role == null || !manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType())) return new ResponseDTO(CodeEnum.C4001);
        //todo 权限判断
        orgCode = isAuthority(role, manager.getOrgCode(), orgCode);
        if (StringUtils.isEmpty(orgCode))return new ResponseDTO(CodeEnum.C4002);
        if(role > queryType.getCode()) return new ResponseDTO(CodeEnum.C4002);

        String key = ReportRedisKey.buildLast_7day_Region_KEY(queryType.getCode(),new Date(),orgCode, "");
        if (DateUtil.toShotHHInteger(new Date()) <= Global.Global_Cache_Time) {
            key = ReportRedisKey.buildLast_7day_Region_KEY(queryType.getCode(),DateUtil.lastDate(new Date()),orgCode, "");
        }
        ResponseDTO responseDTOCache = cacheUtil.getCacheByFromRedis(key, ResponseDTO.class);
        if (responseDTOCache != null) return responseDTOCache;

        ResponseDTO dayByOrg = find7DayByOrg(queryType, orgCode, manager);
        if (dayByOrg!=null&&dayByOrg.getCodeEnum().equals(CodeEnum.C1000))cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, dayByOrg);
        return dayByOrg;
    }



    /**
     * 按天统计我的战绩
     *
     * @param uuid
     * @param orgCode
     * @param beginTime
     * @param endTime
     * @return
     */
    public ResponseDTO findMyWorkGroupByDate(String uuid, String orgCode, String beginTime, String endTime, Enumerate.ManagerRole queryType, Integer pageNo, Integer pageSize) {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        if (role == null || !manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType())) return new ResponseDTO(CodeEnum.C4001);
        //todo 权限判断
        orgCode = isAuthority(role, manager.getOrgCode(), orgCode);
        if (StringUtils.isEmpty(orgCode)) return new ResponseDTO(CodeEnum.C4002);
        if(role > queryType.getCode()) return new ResponseDTO(CodeEnum.C4002);

        String key = ReportRedisKey.buildMyWorkGroupByDate_Region_KEY(queryType.getCode(),new Date(),orgCode, beginTime + "_"+endTime+"_"+pageNo+"_"+pageSize);
        if (DateUtil.toShotHHInteger(new Date()) <= Global.Global_Cache_Time) {
            key = ReportRedisKey.buildMyWorkGroupByDate_Region_KEY(queryType.getCode(),DateUtil.lastDate(new Date()),orgCode, beginTime + "_"+endTime+"_"+pageNo+"_"+pageSize);
        }
        ResponseDTO responseDTOCache = cacheUtil.getCacheByFromRedis(key, ResponseDTO.class);
        if (responseDTOCache != null) return responseDTOCache;

        ResponseDTO myWorkGroupByDate = findMyWorkGroupByDate(orgCode, beginTime, endTime, queryType, pageNo, pageSize);

        if (myWorkGroupByDate != null && myWorkGroupByDate.getCodeEnum().equals(CodeEnum.C1000))
            cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, myWorkGroupByDate);
        return myWorkGroupByDate;
    }

    /**
     * 按网点(快递员)统计
     *
     * @param uuid
     * @param orgCode
     * @param beginTime
     * @param endTime
     * @return
     */
    public ResponseDTO findMyWorkByRegion(String uuid, String orgCode, String beginTime, String endTime, Enumerate.ManagerRole queryType, Integer pageNo, Integer pageSize) {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        if (role == null || !manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType())) return new ResponseDTO(CodeEnum.C4001);

        //todo 权限判断
        orgCode = isAuthority(role, manager.getOrgCode(), orgCode);
        if (StringUtils.isEmpty(orgCode))return new ResponseDTO(CodeEnum.C4002);
        if(role > queryType.getCode()) return new ResponseDTO(CodeEnum.C4002);

        String key = ReportRedisKey.buildMyWork_Region_KEY(queryType.getCode(),new Date(),orgCode,beginTime + "_"+endTime+"_"+pageNo+"_"+pageSize);
        if (DateUtil.toShotHHInteger(new Date()) <= Global.Global_Cache_Time) {
            key = ReportRedisKey.buildMyWork_Region_KEY(queryType.getCode(), DateUtil.lastDate(new Date()),orgCode, beginTime + "_"+endTime+"_"+pageNo+"_"+pageSize);
        }
        ResponseDTO responseDTOCache = null;
        try {
            responseDTOCache = cacheUtil.getCacheByFromRedis(key, ResponseDTO.class);
        }catch (Exception e) {
            logger.error("get responseDTOCache from redis, key is {}", key);
        }
        if (responseDTOCache != null) return responseDTOCache;

        ResponseDTO myWorkByRegion = findMyWorkByRegion(orgCode, beginTime, endTime, queryType, pageNo, pageSize);
        if (myWorkByRegion != null && myWorkByRegion.getCodeEnum().equals(CodeEnum.C1000))
            cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, myWorkByRegion);
        return myWorkByRegion;
    }

    public ResponseDTO queryDetailByRegionKey(String uuid,String orgCode,String queryKey ,String beginTime,String endTime,Enumerate.ManagerRole queryType){
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Manager manager = (Manager)responseDTO.getT2();
        Byte role = manager.getRole();
        if (role == null || !manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType())) return new ResponseDTO(CodeEnum.C4001);

        //todo 权限判断
        queryKey = isAuthorityRegionKey(orgCode, queryKey, manager, queryType);
        if (StringUtils.isEmpty(queryKey)) return  new ResponseDTO(CodeEnum.C1000, null, new ManageHomePageResp());
        if(role > queryType.getCode()) return new ResponseDTO(CodeEnum.C4002);

        String key = ReportRedisKey.buildQueryDetailByRegionKey(queryType.getCode(),new Date(),queryKey,beginTime + "_"+endTime);
        if (DateUtil.toShotHHInteger(new Date()) <= Global.Global_Cache_Time) {
            key = ReportRedisKey.buildQueryDetailByRegionKey(queryType.getCode(), DateUtil.lastDate(new Date()),queryKey, beginTime + "_"+endTime);
        }
        ResponseDTO responseDTOCache = cacheUtil.getCacheByFromRedis(key, ResponseDTO.class);
//        if (responseDTOCache != null ) return responseDTOCache;

        if (StringUtils.isEmpty(orgCode)) orgCode = manager.getOrgCode();
        ResponseDTO queryDetailByRegionKey = queryDetailByRegionKey(orgCode, queryKey, beginTime, endTime, queryType);

        if (queryDetailByRegionKey != null && queryDetailByRegionKey.getCodeEnum().equals(CodeEnum.C1000))
            cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, queryDetailByRegionKey);

        return queryDetailByRegionKey;
    }



    private ResponseDTO find7DayByOrg(Enumerate.ManagerRole queryType, String orgCode, Manager manager) {
        List<VReportTotalNum> lists = null;
        if (Enumerate.ManagerRole.provinceCode.getCode() == queryType.getCode()) {//todo 查询省网
            if (StringUtils.isEmpty(orgCode)) orgCode = manager.getOrgCode();
            Branch branch = branchService.findByOrgCode(orgCode);
            lists = find7DayByRegion(CommonEnum.OrgReportEnum.province.getCode(),branch.getProvinceCode());
        } else {//todo 查询 分公司 分部
            if (Enumerate.ManagerRole.companyCode.getCode() == queryType.getCode()) { //todo 分公司 且有分公司的权限
                lists = find7DayByRegion(CommonEnum.OrgReportEnum.branch.getCode(),orgCode);
            } else if (Enumerate.ManagerRole.branchCode.getCode() == queryType.getCode()) { //todo 分部 且有分部的权限
                lists = find7DayByRegion(CommonEnum.OrgReportEnum.terminal.getCode(),orgCode);
            }else{//todo 参数错误或权限不够
                return new ResponseDTO(CodeEnum.C1034);
            }
        }
        return new ResponseDTO(CodeEnum.C1000, lists, null);
    }
    private ResponseDTO findMyWorkGroupByDate(String orgCode, String beginTime, String endTime, Enumerate.ManagerRole queryType, Integer pageNo, Integer pageSize) {
        if (Enumerate.ManagerRole.provinceCode.getCode() == queryType.getCode()) {
            Branch branch = branchService.findByOrgCode(orgCode);
            List<VReportTotalNum> lists = findMyWorkGroupByDate(CommonEnum.OrgReportEnum.province.getCode(),branch.getProvinceCode(),beginTime,endTime,pageNo,pageSize);
            VReportTotalNum reportTotalNum = countOrgReportGroupByTime(beginTime,endTime,CommonEnum.OrgReportEnum.province.getCode(),branch.getProvinceCode(),null,null);
            ManageHomePageResp manageHomePageResp = ReportConvert.deal(lists, Enumerate.RespDataType.DATE.getType(),reportTotalNum,orgCode);
            return new ResponseDTO(CodeEnum.C1000, null,manageHomePageResp);
        } else {
            List<VReportTotalNum> lists = null;
            VReportTotalNum reportTotalNum = null;
            if (Enumerate.ManagerRole.companyCode.getCode() == queryType.getCode()) { //todo 分公司 且有分公司的权限
                lists = findMyWorkGroupByDate(CommonEnum.OrgReportEnum.branch.getCode(),orgCode,beginTime,endTime,pageNo,pageSize);
                reportTotalNum = countOrgReportGroupByTime(beginTime,endTime,CommonEnum.OrgReportEnum.branch.getCode(),null,orgCode,null);
            } else if (Enumerate.ManagerRole.branchCode.getCode() == queryType.getCode()) { //todo 分部 且有分部的权限
                lists = findMyWorkGroupByDate(CommonEnum.OrgReportEnum.terminal.getCode(),orgCode,beginTime,endTime,pageNo,pageSize);
                reportTotalNum = countOrgReportGroupByTime(beginTime,endTime,CommonEnum.OrgReportEnum.terminal.getCode(),null,null,orgCode);
            }else{//todo 参数错误或权限不够
                return new ResponseDTO(CodeEnum.C1034);
            }
            ManageHomePageResp manageHomePageResp = ReportConvert.deal(lists, Enumerate.RespDataType.DATE.getType(),reportTotalNum,orgCode);
            return new ResponseDTO(CodeEnum.C1000, null, manageHomePageResp);
        }
    }
    private ResponseDTO findMyWorkByRegion(String orgCode, String beginTime, String endTime, Enumerate.ManagerRole queryType, Integer pageNo, Integer pageSize) {
        int totalNum = 0;
        ManageHomePageResp manageHomePageResp;
        List<VReportTotalNum> lists;
        if (Enumerate.ManagerRole.provinceCode.getCode() == queryType.getCode()) {
            Branch branch = branchService.findByOrgCode(orgCode);
            List<String> branches = branchService.findByPCodeAndType(branch.getProvinceCode(), CommonEnum.BranchType.BRANCH.getCode(), null, null);//todo 补零需要
            lists = findMyWorkByProvinceDesc(beginTime,endTime,branch.getProvinceCode(), 0, pageNo * pageSize);
            totalNum = CollectionUtils.isEmpty(branches) ? 0 : branches.size();
            VReportTotalNum reportTotal = countOrgReportGroupByTime(beginTime, endTime, CommonEnum.OrgReportEnum.province.getCode(), branch.getProvinceCode(), null, null);
            lists= ReportConvert.dealRegionZero(lists,branches,CommonEnum.BranchType.BRANCH.getCode());
            manageHomePageResp = ReportConvert.dealList(lists, Enumerate.RespDataType.ORGCODE.getType(), queryType.getCode(), "", userService, branchService,reportTotal, orgCode,pageNo,pageSize);
        } else {
            if (Enumerate.ManagerRole.companyCode.getCode() == queryType.getCode()) { //todo 分公司找分部 且有分公司的权限
                List<String> orgCodes = branchService.findByBranchCode(orgCode,null,null, null);//todo 补零需要
                lists = findMyWorkByBranchDesc(beginTime,endTime,orgCode, 0, pageNo * pageSize);
                firstPageByCompany(beginTime, endTime, orgCode, pageNo, lists);//分公司的第一页
                totalNum = CollectionUtils.isEmpty(orgCodes) ? 0 : orgCodes.size();
                VReportTotalNum reportTotal = countOrgReportGroupByTime(beginTime, endTime, CommonEnum.OrgReportEnum.branch.getCode(), null, orgCode, null);
                lists = ReportConvert.dealRegionZero(lists,orgCodes,CommonEnum.BranchType.TERMINAL.getCode());
                manageHomePageResp = ReportConvert.dealList(lists, Enumerate.RespDataType.ORGCODE.getType(), queryType.getCode(), orgCode, userService,branchService, reportTotal, orgCode,pageNo,pageSize);
            } else if (Enumerate.ManagerRole.branchCode.getCode() == queryType.getCode()) { //todo 分部找员工jobNo 且有分部的权限
                Set<Long> userIds = userService.findUidByOrgCode(orgCode, null, null);
                lists = reportService.findReportGroupByUserIdDesc(beginTime, endTime, orgCode, 0,  pageNo * pageSize);
                lists = ReportConvert.dealUserIdZero(lists, userIds);
                totalNum = CollectionUtils.isEmpty(userIds) ? 0 : userIds.size();
                VReportTotalNum reportTotal = countOrgReportGroupByTime(beginTime, endTime, CommonEnum.OrgReportEnum.terminal.getCode(), null, null, orgCode);
                manageHomePageResp = ReportConvert.dealList(lists, Enumerate.RespDataType.JOBNO.getType(), queryType.getCode(), "", userService,branchService, reportTotal, orgCode,pageNo,pageSize);
            } else {//todo 参数错误或权限不够
                return new ResponseDTO(CodeEnum.C1034);
            }
        }
        return new ResponseDTO(CodeEnum.C1000, null, manageHomePageResp == null ? new ManageHomePageResp() : manageHomePageResp, totalNum);
    }

    private ResponseDTO queryDetailByRegionKey(String orgCode, String key, String beginTime, String endTime, Enumerate.ManagerRole queryType) {
        ManageHomePageResp manageHomePageResp;
        if (Enumerate.ManagerRole.provinceCode.getCode() == queryType.getCode()) {
            Branch branch = branchService.findByOrgCode(orgCode);
            VReportTotalNum reportTotalNum = queryByRegionKey(CommonEnum.OrgReportEnum.branch.getCode(), beginTime, endTime, branch.getProvinceCode(), null, key);
            manageHomePageResp = ReportConvert.dealRegionKey(reportTotalNum, Enumerate.RespDataType.ORGCODE.getType(), key, null,orgCode,branchService);
        } else {
            if (Enumerate.ManagerRole.companyCode.getCode() == queryType.getCode()) {
                VReportTotalNum reportTotalNum = queryByRegionKey(CommonEnum.OrgReportEnum.terminal.getCode(), beginTime, endTime, null, orgCode, key);
                manageHomePageResp = ReportConvert.dealRegionKey(reportTotalNum, Enumerate.RespDataType.ORGCODE.getType(), key, null,orgCode,branchService);
            } else if (Enumerate.ManagerRole.branchCode.getCode() == queryType.getCode()) {
                ResponseDTO byOrgCodeJobNo = userService.findByOrgCodeJobNo(orgCode, key);
                if (byOrgCodeJobNo.getCodeEnum() != CodeEnum.C1000) return byOrgCodeJobNo;
                User user = (User) byOrgCodeJobNo.getT2();
                if (user == null)  return new ResponseDTO(CodeEnum.C1034);
                VReportTotalNum reportTotalNum = reportService.queryReportByUserId(beginTime, endTime, user.getId());
                manageHomePageResp = ReportConvert.dealRegionKey(reportTotalNum, Enumerate.RespDataType.JOBNO.getType(), key, user,orgCode,branchService);
            } else {
                return new ResponseDTO(CodeEnum.C1034);
            }
        }
        return new ResponseDTO(CodeEnum.C1000, null, manageHomePageResp == null ? new ManageHomePageResp() : manageHomePageResp);
    }


    private List<VReportTotalNum> findMyWorkGroupByDate(Byte type ,String regionCode , String beginTime, String endTime, Integer pageNo, Integer pageSize){
        List<VReportTotalNum> totalList = null;
        String pageEndTime = ReportConvert.pageIndexEndDesc(beginTime, endTime, pageNo, pageSize);
        String pageBeginTime = ReportConvert.pageIndexBeginDesc(pageEndTime, beginTime, endTime, pageNo, pageSize);
        if (CommonEnum.OrgReportEnum.province.getCode().equals(type)){
            totalList = findOrgReportGroupByTime( pageBeginTime ,  pageEndTime ,type,regionCode,null,null);
        }else if (CommonEnum.OrgReportEnum.branch.getCode().equals(type)){
            totalList = findOrgReportGroupByTime( pageBeginTime , pageEndTime ,type,null,regionCode,null);
        }else if (CommonEnum.OrgReportEnum.terminal.getCode().equals(type)){
            totalList = findOrgReportGroupByTime(pageBeginTime , pageEndTime ,type,null,null,regionCode);
        }

        return totalList;
    }

    /**
     * @param type
     * @param regionCode  可以为省Code 分公司Code 分部Code
     */
    private List<VReportTotalNum> find7DayByRegion(Byte type,String regionCode){

        List<VReportTotalNum> totalList = null;
        if (StringUtils.isEmpty(regionCode)) return day7List(totalList);
        Date now = new Date();
        Date endTime = DateUtil.lastDate(now);
        Date beginTime = DateUtil.add(now, Calendar.DAY_OF_MONTH, -Global.DAY_MONTH_LIMIT);
        if (CommonEnum.OrgReportEnum.province.getCode().equals(type)){
            totalList = findOrgReportGroupByTime(DateUtil.toDay(beginTime), DateUtil.toDay(endTime),type,regionCode,null,null);
        }else if (CommonEnum.OrgReportEnum.branch.getCode().equals(type)){
            totalList = findOrgReportGroupByTime(DateUtil.toDay(beginTime),DateUtil.toDay(endTime),type,null,regionCode,null);
        }else if (CommonEnum.OrgReportEnum.terminal.getCode().equals(type)){
            totalList = findOrgReportGroupByTime(DateUtil.toDay(beginTime),DateUtil.toDay(endTime),type,null,null,regionCode);
        }
        List<VReportTotalNum> list = day7List(totalList);
        return  list;
    }

    /**
     * 补全 数据
     * @param totalList
     */
    private List<VReportTotalNum> day7List(List<VReportTotalNum> totalList) {
        if (totalList == null) totalList = new ArrayList<>();
        List<String> labels = ReportConvert.timeLabels(Global.DAY_MONTH_LIMIT);
        //todo 补缺日期
        for (String label : labels) {
            if (!ReportConvert.isExist(label, totalList)) {
                VReportTotalNum reportTotalNum = new VReportTotalNum(0., 0., 0., label);
                totalList.add(reportTotalNum);
            }
        }
        Collections.sort(totalList, new ReportNumComparable());
        return totalList;
    }
    /**
     * 返回为空就没有权限 （判断是否为直属网点）
     * @param role
     * @param orgCode
     * @param targetOrgCode 为空 返回默认值
     * @return
     */
    private  String isAuthority(Byte role, String orgCode,String targetOrgCode){
        if (StringUtils.isEmpty(targetOrgCode)) return orgCode;
        List<String> orgCodes = branchService.getOrgCodes(role, orgCode);
        if(!orgCodes.contains(targetOrgCode)){
            return "";
        }else{
            return targetOrgCode;
        }
    }
    /**
     * 返回为空 没有权限
     * @return
     */
    private String isAuthorityRegionKey(String orgCode, String queryKey, Manager manager, Enumerate.ManagerRole queryType) {
        if (Enumerate.ManagerRole.branchCode.getCode().equals(queryType.getCode())) {
            return isAuthorityJobNo(orgCode == null ? manager.getOrgCode() : orgCode, queryKey);
        } else if (Enumerate.ManagerRole.provinceCode.getCode().equals(queryType.getCode())) {
            return isAuthorityProvince(orgCode, queryKey, manager);
        } else if (Enumerate.ManagerRole.companyCode.getCode().equals(queryType.getCode())) {
            return isAuthorityCompany(orgCode, queryKey, manager);
        }
        return "";
    }

    private String isAuthorityCompany(String orgCode, String queryKey, Manager manager) {
        List<String> branchs = branchService.findByBCodeType(orgCode == null ? manager.getOrgCode() : orgCode, null);
        if (!CollectionUtils.isEmpty(branchs) && branchs.contains(queryKey)) {
            return queryKey;
        } else {
            return "";
        }
    }

    private String isAuthorityProvince(String orgCode, String queryKey, Manager manager) {
        Branch branch = branchService.findByOrgCode(orgCode == null ? manager.getOrgCode() : orgCode);
        List<String> companies = branchService.findByPCodeAndType(branch.getProvinceCode(), CommonEnum.BranchType.BRANCH.getCode(), null, null);
        if (!CollectionUtils.isEmpty(companies) && companies.contains(queryKey)) {
            return queryKey;
        } else {
            return "";
        }
    }

    /**
     * 返回为空 没有权限
     * @return
     */
    private String isAuthorityJobNo(String orgCode, String jobNo){
        if (StringUtils.isEmpty(orgCode)) return "";
        List<User> users = userService.findByOrgCode(orgCode);
        if (CollectionUtils.isEmpty(users)) return "";
        List<String> jobNos = users.parallelStream().map(User::getJobNo).collect(Collectors.toList());
        if (!jobNos.contains(jobNo)){
            return "";
        }else {
            return jobNo;
        }
    }
    /**
     * 针对分公司的 第一条数据
     * @return
     */
    private void firstPageByCompany(String beginTime, String endTime, String branchCode, Integer pageNo, List<VReportTotalNum> lists) {
        if (!pageNo.equals(Constant.PAGE_NO)) return;
        if (lists == null) lists = new ArrayList<>();
        if (ReportConvert.existOrgCode(lists,branchCode)) return;
        VReportTotalNum vReportTotalNum = findByOrgAndBranchCode(beginTime, endTime, branchCode);
        if (vReportTotalNum != null) lists.add(vReportTotalNum);
    }

    /**
     * 查询orgReport并按派件数排序
     */
    public Page<OrgReport> findPageBySort(Byte type, Date beginTime, Date endTime, Integer pageNo, Integer pageSize){
        Integer totalCount = orgReportMapper.countBySort(type, beginTime, endTime);
        Page<OrgReport> page = new Page<>();
        Integer index = null;
        if (pageNo != null && pageSize != null) index = (pageNo - 1) * pageSize;
        List<OrgReport> orgReports = orgReportMapper.findBySort(type, beginTime, endTime, index, pageSize);
        if (!CollectionUtils.isEmpty(orgReports)) {
            for (OrgReport orgReport : orgReports) {
//                if (type.equals(CommonEnum.OrgReportEnum.province.getCode())){
                    orgReport.setProvinceName(regionService.getByCode(orgReport.getProvinceCode()));
//                }
                if (type.equals(CommonEnum.OrgReportEnum.branch.getCode())){
                    Branch branch = branchService.findByOrgCode(orgReport.getBranchCode());
                    if (branch != null) orgReport.setBranchName(branch.getBranchName());
                }
                if (type.equals(CommonEnum.OrgReportEnum.terminal.getCode())){
                    Branch branch = branchService.findByOrgCode(orgReport.getOrgCode());
                    if (branch != null) orgReport.setOrgName(branch.getTerminalName());
                }
            }
        }
        if (pageNo != null && pageSize != null){
            page.setPageNo(pageNo);
            page.setPageSize(pageSize);
        }
        page.setTotalCount(totalCount);
        page.setResult(orgReports);
        return page;
    }

}
