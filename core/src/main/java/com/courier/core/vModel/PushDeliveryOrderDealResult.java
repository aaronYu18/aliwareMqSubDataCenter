package com.courier.core.vModel;

import com.courier.db.entity.AppointmentOrder;
import com.courier.db.entity.DeliveryOrder;

import java.io.Serializable;

/**
 * Created by admin on 2016/6/29.
 */
public class PushDeliveryOrderDealResult implements Serializable {
    public enum Type{
        insert, update, delete;
    }

    private Type type;
    private DeliveryOrder deliveryOrder;
    private String mailNo;
    private AppointmentOrder appointmentOrder;



    public PushDeliveryOrderDealResult(Type type, String mailNo, DeliveryOrder deliveryOrder) {
        this.mailNo = mailNo;
        this.type = type;
        this.deliveryOrder = deliveryOrder;
    }

    public PushDeliveryOrderDealResult(Type type, DeliveryOrder deliveryOrder, AppointmentOrder order) {
        this.type = type;
        this.deliveryOrder = deliveryOrder;
        this.appointmentOrder = order;
    }
    public PushDeliveryOrderDealResult(Type type, String mailNo) {
        this.type = type;
        this.mailNo = mailNo;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public AppointmentOrder getAppointmentOrder() {
        return appointmentOrder;
    }

    public void setAppointmentOrder(AppointmentOrder appointmentOrder) {
        this.appointmentOrder = appointmentOrder;
    }
}
