package com.courier.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/3/31.
 */
@Table(name = "payOperationInfo")
public class PayOperationInfo extends BaseEntity{
    private static final long serialVersionUID = -3697292827115888059L;
    private Long id;
    private Long payOrderInfoId;
    private String serialNo;
    private String aliSerialNo;
    private String mailNo;
    private Long userId;
    private String authCode;
    private String payUserId;
    private String subject;
    private String body;
    private Double collectionMoney;	           //       代收金额
    private Double freightMoney;	           //       到付金额
    private Double amount;
    private Long orderId;      //订单id
    private Byte dcType;                        //收派类型 0:取件;1:派件
    private Byte platformType;
    private String channelType;
    private Byte operType;
    private Byte payType;
    private String costType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Column
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    @Column
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @Column
    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    @Column
    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    @Column
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
    @Column
    public Byte getOperType() {
        return operType;
    }

    public void setOperType(Byte operType) {
        this.operType = operType;
    }
    @Column
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }
    @Column
    public String getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }
    @Column
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column
    public Byte getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Byte platformType) {
        this.platformType = platformType;
    }
    @Column
    public String getAliSerialNo() {
        return aliSerialNo;
    }

    public void setAliSerialNo(String aliSerialNo) {
        this.aliSerialNo = aliSerialNo;
    }
    @Column
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    @Column
    public Long getPayOrderInfoId() {
        return payOrderInfoId;
    }

    public void setPayOrderInfoId(Long payOrderInfoId) {
        this.payOrderInfoId = payOrderInfoId;
    }
    @Column
    public Double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(Double freightMoney) {
        this.freightMoney = freightMoney;
    }
    @Column
    public Double getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(Double collectionMoney) {
        this.collectionMoney = collectionMoney;
    }
    @Column
    public Byte getDcType() {
        return dcType;
    }

    public void setDcType(Byte dcType) {
        this.dcType = dcType;
    }
    @Column
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
