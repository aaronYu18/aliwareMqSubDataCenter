package com.courier.core.vModel;

import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.DeliveryOrder;

import java.io.Serializable;

/**
 * Created by admin on 2015/11/2.
 */
public class VOrder implements Serializable, Comparable<VOrder> {
    private static final long serialVersionUID = 3937680239335584640L;
    private DeliveryOrder deliveryOrder;
    private CollectOrder collectOrder;
    private Type type;
    private Double distance;

    public VOrder() {
    }

    public VOrder(CollectOrder collectOrder, DeliveryOrder deliveryOrder, Double distance, Type type) {
        this.collectOrder = collectOrder;
        this.deliveryOrder = deliveryOrder;
        this.distance = distance;
        this.type = type;
    }


    public enum Type{
        DELIVERY, COLLECT;
    }

    public CollectOrder getCollectOrder() {
        return collectOrder;
    }

    public void setCollectOrder(CollectOrder collectOrder) {
        this.collectOrder = collectOrder;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int compareTo(VOrder arg0) {
        Double one = this.getDistance();

        Double two = arg0.getDistance();
        if(one == null) one = Double.MAX_VALUE;
        if(two == null) two = Double.MAX_VALUE;

        // 先按距离
        if (one < two){
            return  -1;
        }else if(one > two){
            return  1;
        }else {
            //  再按address排序
            String thisAddress = getAddress(this);
            String address = getAddress(arg0);

            if (address.compareTo(thisAddress) > 0) return 1;
            if (address.compareTo(thisAddress) < 0) return -1;

            return 0;
        }
    }

    public String getAddress(VOrder vOrder){
        if(vOrder == null) return "";
        Type type = vOrder.getType();

        if(type.equals(Type.DELIVERY)) {
            DeliveryOrder deliveryOrder = vOrder.getDeliveryOrder();
            if(deliveryOrder == null) return "";

            String receiverAddress = deliveryOrder.getReceiverAddress();
            return receiverAddress == null ? "" : receiverAddress;
        } else {
            CollectOrder collectOrder = vOrder.getCollectOrder();
            if(collectOrder == null) return "";

            String senderAddress = collectOrder.getSenderAddress();
            return senderAddress == null ? "" : senderAddress;
        }
    }
}
