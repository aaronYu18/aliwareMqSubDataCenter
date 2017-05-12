package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 16/4/19.
 */
public class HistoryPayReq extends IdEntity {
    private static final long serialVersionUID = 7463652088006395510L;

    private Date beginTime;
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
