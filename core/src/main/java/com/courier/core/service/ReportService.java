package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.convert.ReportComparable;
import com.courier.core.convert.ReportConvert;
import com.courier.core.convert.ReportRedisKey;
import com.courier.db.dao.PayReportMapper;
import com.courier.db.dao.ReportMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.PayReport;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VReportTotalNum;
import com.courier.db.vModel.VYtoData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by admin on 2015/11/2.
 */
@Service
@Transactional
public class ReportService extends BaseManager<Report> {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    private static final Integer limit = Global.REPORT_MONTH_LIMIT;

    private static final Integer cacheTime = Global.Global_Cache_Time;

    @Autowired
    ReportMapper reportMapper;
    @Autowired
    PayReportMapper payReportMapper;

    @Autowired
    UserService userService;
    @Autowired
    CacheUtil cacheUtil;

    @Autowired
    BranchService branchService;

    @Autowired
    ManagerService managerService;

    @Override
    public Report getEntity() {
        return new Report();
    }

    @Override
    public BaseMapper<Report> getBaseMapper() {
        return reportMapper;
    }


    public List<Report> findThisMonth(Long userId, Date queryDate) {
        Date nowDate = new Date();
        String key = ReportRedisKey.buildTableKey(userId, nowDate, queryDate);
        if (DateUtil.toShotHHInteger(new Date()) <= cacheTime) {
            key =  ReportRedisKey.buildTableKey(userId, DateUtil.lastDate(nowDate), queryDate);
        }
        List<Report> cacheList = cacheUtil.getCacheByFromRedis(key, List.class);
        if (cacheList != null)   return cacheList;

        List<Report> list = reportMapper.findThisMonth(userId, queryDate);
        List<PayReport> payReports = payReportMapper.findThisMonth(userId, queryDate);
        if (list != null && payReports != null) {
            for (Report report : list) {
                for (PayReport payReport : payReports) {
                    if (DateUtils.isSameDay(report.getCountTime(), payReport.getCountTime())) {
                        report.setDayAmount(payReport.getCollectionAmount());
                        break;
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)) cacheUtil.putData2RedisByTime(ReportRedisKey.buildTableKey(userId, nowDate, queryDate), Global.ONE_DAY_AGE, list);
        return list;
    }

    public Report findThisDay(Long userId, String date) {
        return reportMapper.findThisDay(userId, date);
    }

    public List<Report> findStatByMonths(Long userId) {
        Date now = new Date();

        String key = ReportRedisKey.buildPlotKey(userId, now);
        if (DateUtil.toShotHHInteger(new Date()) <= cacheTime) {
            key =  ReportRedisKey.buildPlotKey(userId, DateUtil.lastDate(now));
        }
        List<Report> cacheList = cacheUtil.getCacheByFromRedis(key, List.class);
        if (cacheList != null)    return cacheList;

        List<String> labels = ReportConvert.statLabels(limit);
        List<Report> list = findStatByMonths(userId, labels);
        ReportConvert.convert(list, labels, userId, limit);

        Collections.sort(list, new ReportComparable());
        if (!CollectionUtils.isEmpty(list)) cacheUtil.putData2RedisByTime(ReportRedisKey.buildPlotKey(userId, now), Global.ONE_DAY_AGE, list);
        return list;
    }


    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public List<Report> findByFilters(Map<String, Object> filters) throws Exception {
        if (filters == null) filters = new HashMap<String, Object>();

        List<Report> list = reportMapper.findByFilters(filters);

        return list;
    }
    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public Report findOneByKeys(Long userId, Date countTime, CommonEnum.ReportEnum type){
        Map<String, Object> filters  = new HashMap<String, Object>();

        if (userId != null && userId != 0l)
            filters.put("userId", userId);
        if (countTime != null)
            filters.put("countTime", DateUtil.toSeconds(countTime));
        if (type != null)
            filters.put("type", type.getCode());

        Report list = reportMapper.findOneByKeys(filters);

        return list;
    }

    public int batchInsert(List<Report> reports) {
        if (reports == null || reports.size() <= 0) return 1;
        return reportMapper.batchInsert(reports);
    }

    public VYtoData findYtoData(Long userId) {
        VYtoData vYtoData = new VYtoData();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date lastDayCountT = DateUtil.getMiddleT(calendar);
        Calendar calendarMouth = Calendar.getInstance();
        calendarMouth.add(Calendar.MONTH, -1);
        calendarMouth.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date lastMonthCountT = DateUtil.getMiddleT(calendarMouth);

        // todo  用户总
        Report all = findOneByKeys(userId, lastDayCountT, CommonEnum.ReportEnum.USER_TOTAL);
        if (all != null) {
            vYtoData.setTotalCollectNo(all.getCollectNo().intValue());
            vYtoData.setTotalSendNo(all.getSendNo().intValue());
        }

        // 一号以前取上月，一号以后取当月
        Report month_temp = null, avg_temp = null;
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1) {
            month_temp = findOneByKeys(userId, lastMonthCountT, CommonEnum.ReportEnum.MONTH);
            avg_temp = getAvg(lastMonthCountT);
        } else {
            month_temp = findOneByKeys(userId, lastDayCountT, CommonEnum.ReportEnum.MONTH_TEMP);
            avg_temp = getAvgTemp(lastDayCountT);
        }
        // todo  当月总
        if (month_temp != null) {
            vYtoData.setMonthCollectNo(month_temp.getCollectNo().intValue());
            vYtoData.setMonthSendNo(month_temp.getSendNo().intValue());
        }
        // todo  当月全网平均总
        if (avg_temp != null) {
            vYtoData.setMonthMeanCollectdNo(avg_temp.getCollectNo());
            vYtoData.setMonthMeanSendNo(avg_temp.getSendNo());
        }
        return vYtoData;
    }

    private Report getAvg(Date lastMonthCountT){
        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -1);
        }
        String key = String.format(CacheConstant.YTO_PAGE_AVG_KEY, DateUtil.toShortDay(calendar.getTime()));
        Report avg = cacheUtil.getCacheByFromRedis(key, Report.class);
        if (avg == null) {
            avg = findOneByKeys(null, lastMonthCountT, CommonEnum.ReportEnum.AVG);
            try{
                cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, avg);
            }catch (Exception e){
                logger.error("put yto avg to redis, error is {}", e.getMessage());
            }
        }
        return avg;
    }

    private Report getAvgTemp(Date lastDayCountT){
        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -1);
        }
        String key = String.format(CacheConstant.YTO_PAGE_AVG_TEMP_KEY, DateUtil.toShortDay(calendar.getTime()));
        Report avgTemp = cacheUtil.getCacheByFromRedis(key, Report.class);
        if (avgTemp == null) {
            avgTemp = findOneByKeys(null, lastDayCountT, CommonEnum.ReportEnum.AVG_TEMP);
            try{
                cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, avgTemp);
            }catch (Exception e){
                logger.error("put yto avgTemp to redis, error is {}", e.getMessage());
            }
        }
        return avgTemp;
    }

    public List<Report> findByUidTypeAndCreateTime(Long userId, byte type, String beginT) {
        return reportMapper.findByUidTypeAndCreateTime(userId, type, beginT);
    }

    public List<Report> buildByFilters(Map<String, Object> filters) {
        return reportMapper.buildByFilters(filters);
    }

    public int delDataForInsert() {
        return reportMapper.delDataForInsert();
    }

    public Report findMonthAvg(Map<String, Object> filters) {
        return reportMapper.findMonthAvg(filters);
    }

    public List<Report> findStatByMonths(Long userId, List<String> labels) {
        return reportMapper.findStatByMonths(userId, labels);
    }

    public Report findByUserIds(String beginDate, String endDate, Long[] userIds) {
        Report report;
        if (ArrayUtils.isEmpty(userIds) || (report = reportMapper.findByUserIds(beginDate, endDate, userIds)) == null) {
            report = new Report();
            report.setSendNo(0.);
            report.setCollectNo(0.);
            report.setFailedNo(0.);
        }
        return report;
    }

    public List<Report> findDetail(String beginDate, String endDate, Long[] userIds) {
        return reportMapper.findDetail(beginDate, endDate, userIds);
    }

    public List<Long> findFrequentUIds(int minSendNo, Date beginT, Date endT) {
        return reportMapper.findFrequentUIds(minSendNo, beginT, endT);
    }

    List<VReportTotalNum> findReportGroupByUserIdDesc(String beginDate, String endDate, String orgCode,
                                                      Integer pageNo, Integer pageSize) {
        return reportMapper.findReportGroupByUserIdDesc(beginDate, endDate, orgCode, pageNo, pageSize);
    }

    VReportTotalNum queryReportByUserId(String beginDate, String endDate, Long userId) {
        return reportMapper.queryReportByUserId(beginDate, endDate, userId);
    }
}
