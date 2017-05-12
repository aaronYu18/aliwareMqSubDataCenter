package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/6/2.
 */
public class ResponseMailNoModelCResult extends BaseEntity {
    private static final long serialVersionUID = 1977864464183020091L;
    private String logisticProviderID;
    private String success;
    private OrderMessage orderMessage;
    private String reason;
    private String txLogisticID;

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public OrderMessage getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(OrderMessage orderMessage) {
        this.orderMessage = orderMessage;
    }


}
