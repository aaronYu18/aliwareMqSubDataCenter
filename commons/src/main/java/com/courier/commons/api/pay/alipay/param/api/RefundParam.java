package com.courier.commons.api.pay.alipay.param.api;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by ryan on 16/3/28.
 */
public class RefundParam extends BaseEntity {

    private static final long serialVersionUID = 5451791296601062205L;
    /** 用户Id*/
    private Long userId;
    /** 交易流水号*/
    private String serialNo;
    /** 退款*/
    private double refundAmount;
    /** 退款理由*/
    private String refundReason;

    public RefundParam(){}

    public RefundParam(Long userId, double refundAmount, String serialNo, String refundReason) {
        this.userId = userId;
        this.refundAmount = refundAmount;
        this.serialNo = serialNo;
        this.refundReason = refundReason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
