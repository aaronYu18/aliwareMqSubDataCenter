package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.util.Date;

/**
 * Created by ryan on 16/5/16.
 */
public class JGTradeInfo extends BaseEntity{

    private static final long serialVersionUID = -4373607353142891938L;
    /** 行者内部交易流水号*/
    private String serialNo;
    /** 支付宝交易流水号*/
    private String aliSerialNo;
    /** 面单号*/
    private String mailNo;
    /** 支付信息*/
    private String subject;
    /** 支付信息体*/
    private String body;
    /** 交易金额*/
    private Double amount;
    /** 平台渠道类型*/
    private String platformType;
    /** 第三方支付类型*/
    private String channelType;
    /** 支付类型*/
    private String payType;
    /** 费用类型*/
    private String costType;
    /** 操作状态， 只有成功*/
    private String status;
    /** 支付宝打款时间*/
    private Date paymentTime;
    /** 代收金额*/
    private Double collectionMoney;
    /** 到付金额*/
    private Double freightMoney;
    /** 收派类型 0：取件，1：派件*/
    private String dcType;
    /** 支付创建时间*/
    private Date createTime;
    /** 金刚订单ID*/
    private String sourceKeyID;
    /** 工号*/
    private String jobNo;
    /** 支付宝账户*/
    private String account;
    /** 网点代码*/
    private String orgCode;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAliSerialNo() {
        return aliSerialNo;
    }

    public void setAliSerialNo(String aliSerialNo) {
        this.aliSerialNo = aliSerialNo;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
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

    public String getDcType() {
        return dcType;
    }

    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSourceKeyID() {
        return sourceKeyID;
    }

    public void setSourceKeyID(String sourceKeyID) {
        this.sourceKeyID = sourceKeyID;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
