package com.courier.core.convert;

import com.courier.db.entity.Report;

import java.util.Comparator;

/**
 * Created by david on 16-3-10.
 */
public class ReportComparable implements Comparator<Report> {
    @Override
    public int compare(Report o1, Report o2) {
        if (o1.getCountTime()==null||o2.getCountTime()==null) return -1;

        if (o1.getCountTime().getTime()> o2.getCountTime().getTime()){
            return 1;
        }else if (o1.getCountTime().getTime()<o2.getCountTime().getTime()){
            return -1;
        }else {
            return 0;
        }
    }
}
