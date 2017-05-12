package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

import java.util.Date;
/**
 * Created by vincent on 15/10/28.
 */
public class BatchSignReq extends IdEntity{
    private static final long serialVersionUID = 635611488910454176L;
    private Long orderId;       //订单id
    private Date signTime;     //app签收时间
    private String signName;   //签收人姓名
    private Byte signType;     //签收类型
    private String failedCode;   //失败原因code
    private String failedDesc; //失败描述
    private Byte signPersonType;

    public Byte getSignPersonType() {
        return signPersonType;
    }

    public void setSignPersonType(Byte signPersonType) {
        this.signPersonType = signPersonType;
    }

    public String getFailedCode() {
        return failedCode;
    }

    public void setFailedCode(String failedCode) {
        this.failedCode = failedCode;
    }

    public String getFailedDesc() {
        return failedDesc;
    }

    public void setFailedDesc(String failedDesc) {
        this.failedDesc = failedDesc;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Byte getSignType() {
        return signType;
    }

    public void setSignType(Byte signType) {
        this.signType = signType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
