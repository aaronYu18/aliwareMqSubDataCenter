package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/11/6.
 */
public class OperationCountResp extends IdEntity {
    private static final long serialVersionUID = -5399408006687728758L;
    private Date day;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
