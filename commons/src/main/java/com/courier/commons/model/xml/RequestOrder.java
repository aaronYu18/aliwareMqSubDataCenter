package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

import java.util.List;

/**
 * 圆通App的请求参数 接口 2.1 2.2 2.4 3.2共用
 * Created by bin on 2015/11/28.
 */
public class RequestOrder extends BaseEntity {
    private static final long serialVersionUID = 8537802293171179776L;
    private String clientID;
    private String logisticProviderID;
    private String txLogisticID;
    private String customerId;
    private String tradeNo;
    private String mailNo;
    private String type;
    private String orderType;
    private String serviceType;
    private Sender sender;
    private Receiver receiver;
    private String sendStartTime;
    private String sendEndTime;
    private String itemsValue;
    private String itemsWeight;
    private List<Item> items;
    private String insuranceValue;
    private String packageOrNot;
    private String special;
    private String remark;

    private String totalServiceFee;
    private String codSplitFee;

    private String createOrgCode;
    private String createOrgName;
    private String createUserCode;
    private String createUserName;
    private String acceptOrgCode;
    private String acceptOrgName;
    private String acceptUserCode;
    private String acceptUserName;

    private Double couponsMoney;
    private Byte couponType;

    public RequestOrder() {
    }

    public RequestOrder(String clientID, String logisticProviderID, String txLogisticID, String customerId, String orderType, String serviceType) {
        this.clientID = clientID;
        this.logisticProviderID = logisticProviderID;
        this.txLogisticID = txLogisticID;
        this.customerId = customerId;
        this.orderType = orderType;
        this.serviceType = serviceType;
    }

    public RequestOrder(String clientID, String txLogisticID, String customerId, Sender sender, Receiver receiver) {
        this.clientID = clientID;
        this.logisticProviderID = "YTO";
        this.txLogisticID = txLogisticID;
        this.customerId = customerId;
        this.orderType = "1";// 暂时未启用默认
        this.serviceType = "0";// 暂时未启用默认

        this.sender = sender;
        this.receiver = receiver;
    }


    public RequestOrder(String clientID, String logisticProviderID, String txLogisticID, String customerId, String orderType, String serviceType, Sender sender, Receiver receiver) {
        this.clientID = clientID;
        this.logisticProviderID = logisticProviderID;
        this.txLogisticID = txLogisticID;
        this.customerId = customerId;
        this.orderType = orderType;
        this.serviceType = serviceType;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Double getCouponsMoney() {
        return couponsMoney;
    }

    public void setCouponsMoney(Double couponsMoney) {
        this.couponsMoney = couponsMoney;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public String getTotalServiceFee() {
        return totalServiceFee;
    }

    public void setTotalServiceFee(String totalServiceFee) {
        this.totalServiceFee = totalServiceFee;
    }

    public String getCodSplitFee() {
        return codSplitFee;
    }

    public void setCodSplitFee(String codSplitFee) {
        this.codSplitFee = codSplitFee;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getAcceptUserName() {
        return acceptUserName;
    }

    public void setAcceptUserName(String acceptUserName) {
        this.acceptUserName = acceptUserName;
    }

    public String getAcceptUserCode() {
        return acceptUserCode;
    }

    public void setAcceptUserCode(String acceptUserCode) {
        this.acceptUserCode = acceptUserCode;
    }

    public String getAcceptOrgName() {
        return acceptOrgName;
    }

    public void setAcceptOrgName(String acceptOrgName) {
        this.acceptOrgName = acceptOrgName;
    }

    public String getAcceptOrgCode() {
        return acceptOrgCode;
    }

    public void setAcceptOrgCode(String acceptOrgCode) {
        this.acceptOrgCode = acceptOrgCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(String sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public String getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(String sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public String getItemsValue() {
        return itemsValue;
    }

    public void setItemsValue(String itemsValue) {
        this.itemsValue = itemsValue;
    }

    public String getItemsWeight() {
        return itemsWeight;
    }

    public void setItemsWeight(String itemsWeight) {
        this.itemsWeight = itemsWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(String insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public String getPackageOrNot() {
        return packageOrNot;
    }

    public void setPackageOrNot(String packageOrNot) {
        this.packageOrNot = packageOrNot;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
