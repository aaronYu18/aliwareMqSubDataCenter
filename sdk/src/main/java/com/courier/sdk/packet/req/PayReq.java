package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/5.
 */
public class PayReq extends IdEntity {
    private static final long serialVersionUID = 8100143356327300887L;

    private Long orderId;
    private Double payAmount;
    private String payCode;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
