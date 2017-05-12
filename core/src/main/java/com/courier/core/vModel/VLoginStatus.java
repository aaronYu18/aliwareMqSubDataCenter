package com.courier.core.vModel;

import java.util.Date;

/**
 * Created by vincent on 15/12/4.
 */
public class VLoginStatus {
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
