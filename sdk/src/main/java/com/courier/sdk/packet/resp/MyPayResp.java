package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 16/4/6.
 */
public class MyPayResp extends IdEntity{
    private static final long serialVersionUID = 1969932164102209983L;
    private Long id;
    private String tradeNo;
    private String mailNo;
    private Double amount;
    private Byte platformType;
    private String platformName;
    private String channelType;
    private String channelName;
    private Byte status;
    private String statusName;
    private Byte payType;
    private String payTypeName;
    private String costType;
    private String costName;
    private Date tradeTime;
    private Date updateTime;
    private Double collectionMoney;	           //       代收金额
    private Double freightMoney;	           //       到付金额
    private Date signOrCollectTime;
    private String signOrCollectAddress;
    private Double lng;
    private Double lat;
    private Byte dcType;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Byte getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Byte platformType) {
        this.platformType = platformType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getSignOrCollectAddress() {
        return signOrCollectAddress;
    }

    public void setSignOrCollectAddress(String signOrCollectAddress) {
        this.signOrCollectAddress = signOrCollectAddress;
    }

    public Date getSignOrCollectTime() {
        return signOrCollectTime;
    }

    public void setSignOrCollectTime(Date signOrCollectTime) {
        this.signOrCollectTime = signOrCollectTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Byte getDcType() {
        return dcType;
    }

    public void setDcType(Byte dcType) {
        this.dcType = dcType;
    }
}
