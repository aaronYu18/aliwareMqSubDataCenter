package com.courier.core.convert;

import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.core.service.BranchService;
import com.courier.core.service.UserService;
import com.courier.db.entity.Report;
import com.courier.db.entity.User;
import com.courier.db.vModel.VReportTotalNum;
import com.courier.sdk.constant.Enumerate;
import com.courier.sdk.manage.resp.ManageDetailResp;
import com.courier.sdk.manage.resp.ManageHomePageResp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by beyond on 2016/4/21.
 */
public class ReportConvert {

    //String pageEndTime = ReportConvert.pageIndexEndDesc(beginTime, endTime, pageNo, pageSize);
    //String pageBeginTime = ReportConvert.pageIndexBeginDesc(pageEndTime, beginTime, endTime, pageNo, pageSize);
    //倒序排列
    public static String pageIndexEndDesc(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Date beginDate = DateUtil.strToDate(beginTime, DateUtil.FULL_DAY_FORMAT);
        Date endDate = DateUtil.strToDate(endTime, DateUtil.FULL_DAY_FORMAT);
        Double interval = DateUtil.dayInterval(endDate, beginDate);
        if (interval < pageSize) return endTime;
        Date pageEndDate = DateUtil.getDateBefore(endDate, (pageNo - 1) * pageSize);
        if (DateUtil.dayInterval(pageEndDate, beginDate) > 0) return DateUtil.toDay(pageEndDate);
        else return endTime;
    }

    public static String pageIndexBeginDesc(String pageIndexEndTime, String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Date beginDate = DateUtil.strToDate(beginTime, DateUtil.FULL_DAY_FORMAT);
        Date endDate = DateUtil.strToDate(endTime, DateUtil.FULL_DAY_FORMAT);
        Double interval = DateUtil.dayInterval(endDate, beginDate);
        if (interval < pageSize) return beginTime;
        Date pageBeginDate = DateUtil.getDateBefore(DateUtil.strToDate(pageIndexEndTime, DateUtil.FULL_DAY_FORMAT), pageSize - 1);
        if (DateUtil.dayInterval(pageBeginDate, beginDate) > 0) return DateUtil.toDay(pageBeginDate);
        else return beginTime;
    }

    /*public static String pageIndexBegin(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Date beginDate = DateUtil.strToDate(beginTime, DateUtil.FULL_DAY_FORMAT);
        Date endDate = DateUtil.strToDate(endTime, DateUtil.FULL_DAY_FORMAT);
        Double interval = DateUtil.dayInterval(endDate, beginDate);
        if (interval < pageSize) return beginTime;
        Date pageBeginDate = DateUtil.getDateAfter(beginDate, (pageNo - 1) * pageSize);
        if (DateUtil.dayInterval(endDate, pageBeginDate) > 0) return DateUtil.toDay(pageBeginDate);
        else return endTime;
    }

    public static String pageIndexEnd(String pageBeginTime, String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Date beginDate = DateUtil.strToDate(beginTime, DateUtil.FULL_DAY_FORMAT);
        Date endDate = DateUtil.strToDate(endTime, DateUtil.FULL_DAY_FORMAT);
        Double interval = DateUtil.dayInterval(endDate, beginDate);
        if (interval < pageSize) return endTime;
        Date pageEndDate = DateUtil.getDateAfter(DateUtil.strToDate(pageBeginTime, DateUtil.FULL_DAY_FORMAT), pageSize - 1);
        if (DateUtil.dayInterval(endDate, pageEndDate) > 0) return DateUtil.toDay(pageEndDate);
        else return endTime;
    }*/

    public static List<String> statLabels(Integer limit) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            Date date = DateUtil.lastNumMonth(i);
            list.add(DateUtil.toYearMonth(date));
        }
        return list;
    }

    public static void convert(List<Report> list, List<String> labels, Long userId, Integer limit) {
        if (list == null) return;

        for (String label : labels) {
            boolean exist = isExist(list, label);
            if (!exist) {
                Date date = DateUtil.valueOfStandard(label);
                Report report = new Report(userId, 0d, date, 0d, 0d, CommonEnum.ReportEnum.MONTH.getCode());
                list.add(report);
            }
        }
    }

    public static boolean isExist(List<Report> list, String label) {
        for (Report report : list) {
            if (label.equalsIgnoreCase(DateUtil.toYearMonth(report.getCountTime()))) {
                return true;
            }
        }
        return false;
    }

    public static List<String> timeLabels(int limit) {
        List<String> labels = new ArrayList<>();
        Date now = new Date();
        for (int i = 1; i <= limit; i++) {
            Date date = DateUtil.add(now, Calendar.DAY_OF_MONTH, -i);
            labels.add(DateUtil.toDay(date));
        }
        return labels;
    }

    public static boolean isExist(String label, List<VReportTotalNum> list) {
        if (StringUtils.isEmpty(label) || list == null) return true;
        for (VReportTotalNum report : list) {
            if (label.equalsIgnoreCase(report.getTime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 为分公司 分部 补零
     * @param lists
     * @param codes
     * @param type
     */
    public static List<VReportTotalNum> dealRegionZero(List<VReportTotalNum> lists, List<String> codes, Byte type) {
        if (CollectionUtils.isEmpty(codes)) return lists;
        if (lists== null) lists = new ArrayList<>();
        for (String code : codes) {
            VReportTotalNum reportTotalNum = new VReportTotalNum(0d, 0d, 0d);
            if (CommonEnum.BranchType.BRANCH.getCode().equals(type)) {
                reportTotalNum.setBranchCode(code);
                if (!existBranceCode(lists, code))
                    lists.add(reportTotalNum);

            } else if (CommonEnum.BranchType.TERMINAL.getCode().equals(type)) {
                reportTotalNum.setOrgCode(code);
                if (!existOrgCode(lists, code))
                    lists.add(reportTotalNum);
            }
        }
        return lists;
    }

    public static List<VReportTotalNum> dealUserIdZero(List<VReportTotalNum> lists, Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) return lists;
        if (lists == null) lists = new ArrayList<>();
        for (Long userId : userIds) {
            if (!existUserId(lists, userId)) {
                VReportTotalNum reportTotalNum = new VReportTotalNum(0d, 0d, 0d);
                reportTotalNum.setUserId(userId);
                lists.add(reportTotalNum);
            }
        }
        return lists;
    }

    public static ManageHomePageResp deal(List<VReportTotalNum> lists, Byte type, VReportTotalNum totalNum,String orgCode) {
        ManageHomePageResp resp = new ManageHomePageResp();
        if (!CollectionUtils.isEmpty(lists)) {
            List<ManageDetailResp> detailRespList = new ArrayList<>();

            for (VReportTotalNum reportTotalNum : lists) {
                ManageDetailResp detailResp = new ManageDetailResp();
                int collectNo = reportTotalNum.getCollectNoTotal().intValue();
                int failedNo = reportTotalNum.getFailedNoTotal().intValue();
                int sendNo = reportTotalNum.getSendNoTotal().intValue();
                String time = reportTotalNum.getTime();
                String jobName = reportTotalNum.getJobName();
                detailResp.setCollectCount(collectNo);
                detailResp.setQuestionCount(failedNo);
                detailResp.setSignCount(sendNo);
                detailResp.setCode(reportTotalNum.getJobNo());
                detailResp.setName(jobName);
                detailResp.setDate(DateUtil.strToDate(time, DateUtil.FULL_DAY_FORMAT));
                detailResp.setType(type);
                detailRespList.add(detailResp);
            }
            Collections.sort(detailRespList, new ReportDateComparable());
            dealTotal(totalNum, resp);
            resp.setDetailList(detailRespList);
        }
        resp.setOrgCode(orgCode);
        return resp;
    }

    /**
     * 精准查询 分公司 分部 快递员
     * @param reportTotalNum
     * @param type 返回网点还是快递员工号
     * @param key
     * @return
     */
    public static ManageHomePageResp dealRegionKey(VReportTotalNum reportTotalNum,Byte type,String key,User user,String orgCode,BranchService branchService){
        ManageHomePageResp resp = new ManageHomePageResp();
        ManageDetailResp detailResp = new ManageDetailResp();
        List<ManageDetailResp> detailRespList = new ArrayList<>();
        if (reportTotalNum !=null){
            int collectNo = reportTotalNum.getCollectNoTotal().intValue();
            int failedNo = reportTotalNum.getFailedNoTotal().intValue();
            int sendNo = reportTotalNum.getSendNoTotal().intValue();
            detailResp.setCollectCount(collectNo);
            detailResp.setQuestionCount(failedNo);
            detailResp.setSignCount(sendNo);
        }else{
            detailResp.setCollectCount(0);
            detailResp.setQuestionCount(0);
            detailResp.setSignCount(0);
        }
        detailResp.setCode(key);

        detailResp.setType(type);
        detailRespList.add(detailResp);
        resp.setDetailList(detailRespList);
        if (user!=null){
            detailResp.setUserId(user.getId());
            detailResp.setName(user.getUsername());
        }else{//非快递员 为网点
            String name = branchService.queryOrgName(key);
            detailResp.setName(name);
        }
        resp.setOrgCode(orgCode);
        return resp;
    }

    /**
     * 分公司的第一页 做特殊处理
     * @param lists
     * @param type
     * @param queryType
     * @param branchCode 不为空 为 分公司
     * @param pageNo
     * @return
     */
    public static ManageHomePageResp dealList(List<VReportTotalNum> lists, Byte type, Byte queryType, String branchCode, UserService userService, BranchService branchService, VReportTotalNum totalNum, String orgCode, Integer pageNo, Integer pageSize) {
        ManageHomePageResp resp = new ManageHomePageResp();
        if (!CollectionUtils.isEmpty(lists)) {
            List<ManageDetailResp> detailRespList = new ArrayList<>();

            ManageDetailResp detailRespFirst = null;
            for (VReportTotalNum reportTotalNum : lists) {
                ManageDetailResp detailResp = new ManageDetailResp();
                int collectNo = reportTotalNum.getCollectNoTotal().intValue();
                int failedNo = reportTotalNum.getFailedNoTotal().intValue();
                int sendNo = reportTotalNum.getSendNoTotal().intValue();
                String name = reportTotalNum.getJobName();

                String code = null;
                Long userId = null;
                if (queryType.equals(Enumerate.ManagerRole.provinceCode.getCode())) {
                    code = reportTotalNum.getBranchCode();
                    name = branchService.queryOrgName(code);
                } else if (queryType.equals(Enumerate.ManagerRole.companyCode.getCode())) {
                    code = reportTotalNum.getOrgCode();
                    name = branchService.queryOrgName(code);
                } else if (queryType.equals(Enumerate.ManagerRole.branchCode.getCode())) {
                    User user = userService.get(reportTotalNum.getUserId());
                    if (user != null) {
                        name = user.getUsername();
                        code = user.getJobNo();
                        userId = user.getId();
                    }
                }
                detailResp.setCode(code);
                detailResp.setName(name == null ? code : name);
                detailResp.setUserId(userId);
                detailResp.setCollectCount(collectNo);
                detailResp.setQuestionCount(failedNo);
                detailResp.setSignCount(sendNo);
                detailResp.setType(type);

                if (!StringUtils.isEmpty(branchCode) && branchCode.equalsIgnoreCase(code)){
                    detailRespFirst = detailResp;
                } else{
                    detailRespList.add(detailResp);
                }
            }
            if (detailRespFirst != null) detailRespList.add(0, detailRespFirst);

            dealTotal(totalNum, resp);
            List<ManageDetailResp> tempList = null;
            if (detailRespList.size() > pageNo * pageSize) {
                tempList = detailRespList.subList((pageNo - 1) * pageSize, pageNo * pageSize);
            } else {
                tempList = detailRespList.subList((pageNo - 1) * pageSize, detailRespList.size());
            }
            resp.setDetailList(new ArrayList<>(tempList));
        }
        resp.setOrgCode(orgCode);
        return resp;
    }

    private static void dealTotal(VReportTotalNum totalNum, ManageHomePageResp resp) {
        if (totalNum != null) {
            Integer collectNoTotal = totalNum.getCollectNoTotal().intValue();
            Integer failedNoTotal = totalNum.getFailedNoTotal().intValue();
            Integer sendNoTotal = totalNum.getSendNoTotal().intValue();
            resp.setCollectCount(collectNoTotal);
            resp.setSignCount(sendNoTotal);
            resp.setQuestionCount(failedNoTotal);
        }
    }

    public static boolean existOrgCode(List<VReportTotalNum> lists, String orgCode) {
        List<VReportTotalNum> keys = lists.parallelStream().filter(t -> t.getOrgCode() != null).filter(t -> t.getOrgCode().equals(orgCode)).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(keys)) return false;
        else return true;
    }

    /**
     * f返回false 不存在
     * @param lists
     * @param code
     * @return
     */
    public static boolean existBranceCode(List<VReportTotalNum> lists, String code) {
        List<VReportTotalNum> keys = lists.parallelStream().filter(t -> t.getBranchCode() != null).filter(t -> t.getBranchCode().equals(code)).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(keys)) return false;
        else return true;
    }

    /**
     * f返回false 不存在
     * @param lists
     * @param userId
     * @return
     */
    public static boolean existUserId(List<VReportTotalNum> lists, Long userId) {
        List<VReportTotalNum> keys = lists.parallelStream().filter(t -> t.getUserId() != null).filter(t -> t.getUserId().equals(userId)).collect(Collectors.toList());
        if (org.springframework.util.CollectionUtils.isEmpty(keys)) return false;
        else return true;
    }
}
