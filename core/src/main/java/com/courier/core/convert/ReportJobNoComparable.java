package com.courier.core.convert;

import com.courier.sdk.manage.resp.ManageDetailResp;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * Created by beyond on 2016/4/28.
 */
public class ReportJobNoComparable implements Comparator<ManageDetailResp> {
    @Override
    public int compare(ManageDetailResp o1, ManageDetailResp o2) {
        if (StringUtils.isEmpty(o1.getCode()) || StringUtils.isEmpty(o2.getCode())) return -1;
        try {
            if (Double.parseDouble(o1.getCode()) > Double.parseDouble(o2.getCode())) {
                return 1;
            } else if (Double.parseDouble(o1.getCode()) < Double.parseDouble(o2.getCode())) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return -1;
        }
    }
}
