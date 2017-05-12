package com.courier.core.convert;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.DateUtil;

import java.util.Date;

/**
 * Created by beyond on 2016/4/22.
 */
public class ReportRedisKey {
    public static String buildTableKey(Long userId, Date nowDate, Date queryDate) {
        String now = DateUtil.toShortDay(nowDate);
        String query = DateUtil.toShortDay(queryDate);
        return String.format(CacheConstant.REPORT_USER_TABLE_KEY, userId, now, query);
    }

    public static String buildPlotKey(Long userId, Date date) {
        String time = DateUtil.toShortDay(date);
        return String.format(CacheConstant.REPORT_USER_PLOT_KEY, userId, time);
    }


    public static String buildLast_7day_Region_KEY(Byte type,Date nowDate,String orgCode,String query) {
        String now = DateUtil.toShortDay(nowDate);
        return String.format(CacheConstant.LAST_7DAY_REGION_KEY,type,orgCode, now,query);
    }

    public static String buildMyWorkGroupByDate_Region_KEY(Byte type,Date nowDate,String orgCode, String query) {
        String now = DateUtil.toShortDay(nowDate);
        return String.format(CacheConstant.MYWORK_GROUPBY_DATE_REGION_KEY,type,orgCode, now, query);
    }


    public static String buildMyWork_Region_KEY(Byte type,Date nowDate,String orgCode,String query) {
        String now = DateUtil.toShortDay(nowDate);
        return String.format(CacheConstant.MYWORK_REGION_KEY,type,orgCode, now, query);
    }
    public static String buildQueryDetailByRegionKey(Byte type,Date nowDate,String orgCode,String query) {
        String now = DateUtil.toShortDay(nowDate);
        return String.format(CacheConstant.QUERYDETAILBYREGIONKEY,type,orgCode, now, query);
    }

}
