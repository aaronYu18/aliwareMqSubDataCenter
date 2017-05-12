package com.courier.core.vModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2015/11/2.
 */
public class VOrderAndNum implements Serializable {
    private static final long serialVersionUID = 1365147325756645624L;
    private List<VOrder> vOrders;
    private int deliveryNo;
    private int collectNo;

    public VOrderAndNum() {
    }

    public VOrderAndNum(List<VOrder> vOrders, int collectNo, int deliveryNo) {
        this.vOrders = vOrders;
        this.collectNo = collectNo;
        this.deliveryNo = deliveryNo;
    }

    public int getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(int collectNo) {
        this.collectNo = collectNo;
    }

    public int getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(int deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public List<VOrder> getvOrders() {
        return vOrders;
    }

    public void setvOrders(List<VOrder> vOrders) {
        this.vOrders = vOrders;
    }
}
