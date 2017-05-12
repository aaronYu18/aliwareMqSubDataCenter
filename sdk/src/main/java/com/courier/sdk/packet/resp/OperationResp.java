package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/11/3.
 */
public class OperationResp extends IdEntity{
    private static final long serialVersionUID = 555314000703856265L;
    private Long id;
    private Long orderId;    //订单id
    private Byte dcType;     //收派件类型
    private Long userId;    //快递员id
    private Byte operation;     //操作类型
    private String expressNo;   //快递编号
    private String receiverName;         //收件人姓名
    private String receiverMobile;       //收件人电话
    private String receiverAddress;      //收件人地址
    private String receiverProvince;     //收件人省
    private String receiverCity;         //收件人市
    private String receiverArea;         //收件人区县
    private Double receiverLat;          //收件人纬度
    private Double receiverLng;          //收件人经度
    private String failDesc;            //失败原因描述
    private Date createTime;            //创建时间

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getDcType() {
        return dcType;
    }

    public void setDcType(Byte dcType) {
        this.dcType = dcType;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getFailDesc() {
        return failDesc;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getOperation() {
        return operation;
    }

    public void setOperation(Byte operation) {
        this.operation = operation;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }

    public Double getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(Double receiverLng) {
        this.receiverLng = receiverLng;
    }
}
