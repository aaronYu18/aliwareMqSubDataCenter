package com.courier.sdk.common.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/12/4.
 */
public class LoginStatusResp extends IdEntity {
    private static final long serialVersionUID = -5996297620161812895L;
    private Byte statusCode;
    private Date tickedTime;

    public Byte getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Byte statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTickedTime() {
        return tickedTime;
    }

    public void setTickedTime(Date tickedTime) {
        this.tickedTime = tickedTime;
    }
}
