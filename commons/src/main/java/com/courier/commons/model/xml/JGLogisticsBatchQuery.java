package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

import java.util.List;

/**
 * Created by bin on 2015/11/4.
 */
public class JGLogisticsBatchQuery extends BaseEntity {
    private static final long serialVersionUID = -4091423113840771823L;
    private String logisticProviderID; // 物流公司编号
    private String success;
    private String reason;

    private List<JGLogisticsOrder> orders;

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public List<JGLogisticsOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<JGLogisticsOrder> orders) {
        this.orders = orders;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
