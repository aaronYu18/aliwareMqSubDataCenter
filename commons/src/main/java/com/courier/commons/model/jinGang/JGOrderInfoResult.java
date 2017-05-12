package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;



/**
 * Created by bin on 2015/11/9.
 */
public class JGOrderInfoResult extends BaseEntity {
    private static final long serialVersionUID = -2968265135114783331L;
    public String orderId;
    public String mailNo;
    public String courierCode;
    public String orgCode;
    public String orderStatus;                      //已取(0)；未取(1)；已关闭(2)；已取消(3)

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

}
