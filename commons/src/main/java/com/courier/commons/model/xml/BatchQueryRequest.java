package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

import java.util.List;

/**
 * Created by bin on 2015/11/28.
 */
public class BatchQueryRequest extends BaseEntity {
    private static final long serialVersionUID = 8656700322777780803L;
    private String  logisticProviderID;
    private String  clientID;
    private List<Order> orders;

    public BatchQueryRequest( List<Order>  orders, String clientID) {
        this.orders = orders;
        this.clientID = clientID;
        this.logisticProviderID = "YTO";
    }

    public BatchQueryRequest(String logisticProviderID, String clientID,  List<Order>  orders) {
        this.logisticProviderID = logisticProviderID;
        this.clientID = clientID;
        this.orders = orders;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
