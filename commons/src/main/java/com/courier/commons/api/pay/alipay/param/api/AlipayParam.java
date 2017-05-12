package com.courier.commons.api.pay.alipay.param.api;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by ryan on 16/3/28.
 */
public class AlipayParam extends BaseEntity {

    private static final long serialVersionUID = -2293508765839805516L;
    /** 用户Id*/
    private Long userId;
    /** 支付宝Id*/
    private String aliUserId;

    /** 对方用户的付款码*/
    private String payCode;

    /** 运单号*/
    private String mailNo;

    /**
     * 支付信息
     */
    /** 平台类型*/
    private Byte platformType;
    /** 渠道渠道*/
    private String channelType;
    /** 支付类型*/
    private Byte payType;
    /** 费用类型*/
    private String costType;
    /** 支付信息*/
    private String subject;
    /** 支付详情*/
    private String body;
    private Double collectionMoney;	           //代收金额
    private Double freightMoney;	           //到付金额
    /** 支付金额*/
    private Double amount;
    private Long orderId;                      //订单id
    private String sourceKey;                  //金刚订单id
    private Byte dcType;
    /** 可打折金额*/
    private Double discountAmount;
    /** 不可打折金额*/
    private Double undiscountAmount;

    public AlipayParam(){}

    public AlipayParam(Long userId, String payCode) {
        this.userId = userId;
        this.payCode = payCode;
    }

    public AlipayParam(Long userId, String payCode, String mailNo, Byte platformType, Long orderId, String sourceKey, Byte dcType,
                       String channelType, Byte payType, String costType, String subject, Double amount,
                       Double collectionMoney, Double freightMoney, Double discountAmount, Double undiscountAmount) {
        this.userId = userId;
        this.payCode = payCode;
        this.mailNo = mailNo;
        this.platformType = platformType;
        this.channelType = channelType;
        this.payType = payType;
        this.costType = costType;
        this.subject = subject;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.undiscountAmount = undiscountAmount;
        this.dcType = dcType;
        this.collectionMoney = collectionMoney;
        this.freightMoney = freightMoney;
        this.orderId = orderId;
        this.sourceKey = sourceKey;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public Byte getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Byte platformType) {
        this.platformType = platformType;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getUndiscountAmount() {
        return undiscountAmount;
    }

    public void setUndiscountAmount(Double undiscountAmount) {
        this.undiscountAmount = undiscountAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAliUserId() {
        return aliUserId;
    }

    public void setAliUserId(String aliUserId) {
        this.aliUserId = aliUserId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(Double collectionMoney) {
        this.collectionMoney = collectionMoney;
    }

    public Double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(Double freightMoney) {
        this.freightMoney = freightMoney;
    }

    public Byte getDcType() {
        return dcType;
    }

    public void setDcType(Byte dcType) {
        this.dcType = dcType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }
}
