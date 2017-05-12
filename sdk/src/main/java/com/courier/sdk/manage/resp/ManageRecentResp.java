package com.courier.sdk.manage.resp;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/18.
 */
public class ManageRecentResp extends IdEntity{
    private static final long serialVersionUID = -5593630223449839116L;

    private String[] dates;
    private Integer[] sendNos;
    private Integer[] collectNos;

    public Integer[] getCollectNos() {
        return collectNos;
    }

    public void setCollectNos(Integer[] collectNos) {
        this.collectNos = collectNos;
    }

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public Integer[] getSendNos() {
        return sendNos;
    }

    public void setSendNos(Integer[] sendNos) {
        this.sendNos = sendNos;
    }
}
