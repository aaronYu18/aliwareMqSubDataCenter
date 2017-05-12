package com.courier.core.convert;

import com.courier.commons.util.DateUtil;
import com.courier.db.vModel.VReportTotalNum;

import java.util.Comparator;

/**
 * Created by beyond on 2016/4/26.
 */
public class ReportNumComparable implements Comparator<VReportTotalNum> {
    @Override
    public int compare(VReportTotalNum o1, VReportTotalNum o2) {
        if (o1.getTime()==null||o2.getTime()==null) return -1;

        if (DateUtil.strToDate(o1.getTime(),DateUtil.FULL_DAY_FORMAT).getTime()> DateUtil.strToDate(o2.getTime(),DateUtil.FULL_DAY_FORMAT).getTime()){
            return 1;
        }else if (DateUtil.strToDate(o1.getTime(),DateUtil.FULL_DAY_FORMAT).getTime()<DateUtil.strToDate(o2.getTime(),DateUtil.FULL_DAY_FORMAT).getTime()){
            return -1;
        }else {
            return 0;
        }
    }
}
