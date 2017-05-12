package com.courier.core.convert;

import com.courier.sdk.manage.resp.ManageDetailResp;

import java.util.Comparator;

/**
 * Created by beyond on 2016/4/28.
 */
public class ReportDateComparable implements Comparator<ManageDetailResp> {
    @Override
    public int compare(ManageDetailResp o1, ManageDetailResp o2) {
        if (o1.getDate()==null||o2.getDate()==null) return -1;

        if (o1.getDate().getTime()> o2.getDate().getTime()){
            return -1;
        }else if (o1.getDate().getTime()<o2.getDate().getTime()){
            return 1;
        }else {
            return 0;
        }
    }
}
