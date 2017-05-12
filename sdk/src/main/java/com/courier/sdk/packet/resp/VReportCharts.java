package com.courier.sdk.packet.resp;


import com.courier.sdk.common.IdEntity;

/**
 * Created by admin on 2016/3/10.
 */
public class VReportCharts extends IdEntity {
    private static final long serialVersionUID = 6372504306702927819L;

    private String[] months;
    private Integer[] sendNos;
    private Integer[] collectNos;

    public VReportCharts() {
    }

    public VReportCharts(Integer[] collectNo, String[] months, Integer[] sendNos) {
        this.collectNos = collectNo;
        this.months = months;
        this.sendNos = sendNos;
    }

    public Integer[] getCollectNos() {
        return collectNos;
    }

    public void setCollectNos(Integer[] collectNos) {
        this.collectNos = collectNos;
    }

    public String[] getMonths() {
        return months;
    }

    public void setMonths(String[] months) {
        this.months = months;
    }

    public Integer[] getSendNos() {
        return sendNos;
    }

    public void setSendNos(Integer[] sendNos) {
        this.sendNos = sendNos;
    }
}
