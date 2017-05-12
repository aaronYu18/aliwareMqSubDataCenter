package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/5.
 */
public class PayStatusResp extends IdEntity{
    private Byte payStatus;
    private String payStatusDesc;

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusDesc() {
        return payStatusDesc;
    }

    public void setPayStatusDesc(String payStatusDesc) {
        this.payStatusDesc = payStatusDesc;
    }
}
