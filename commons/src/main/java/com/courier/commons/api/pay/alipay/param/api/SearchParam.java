package com.courier.commons.api.pay.alipay.param.api;

import com.courier.commons.entity.BaseEntity;

import java.util.Date;

/**
 * Created by ryan on 16/3/28.
 */
public class SearchParam extends BaseEntity{

    private static final long serialVersionUID = -2975475995128230623L;
    /** 用户Id*/
    private Long userId;
    /** 渠道类型*/
    private String channelType;
    /** 操作类型*/
    private Byte status;
    /** 支付类型*/
    private Byte payType;
    /** 费用类型*/
    private String costType;
    /** 起始时间*/
    private Date beginT;
    /** 终止时间*/
    private Date endT;

    public SearchParam(){}

    public SearchParam(Long userId, String channelType, Byte status, Byte payType,
                       String costType, Date beginT, Date endT) {
        this.userId = userId;
        this.channelType = channelType;
        this.status = status;
        this.payType = payType;
        this.costType = costType;
        this.beginT = beginT;
        this.endT = endT;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }


    public Date getBeginT() {
        return beginT;
    }

    public void setBeginT(Date beginT) {
        this.beginT = beginT;
    }

    public Date getEndT() {
        return endT;
    }

    public void setEndT(Date endT) {
        this.endT = endT;
    }
}
