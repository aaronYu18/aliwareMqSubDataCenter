package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/6.
 */
public class RefundReq extends IdEntity {

    private static final long serialVersionUID = -2835867874945550463L;

    private String tradeNo;
    private Double refundAmount;
    private String refundReason;

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
