package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on //3.
 */
@Table(name = "orderOperation")
public class OrderOperation extends BaseEntity {

    private static final long serialVersionUID = 8126541632991133720L;
    private Long id;
    private Long orderId;      //订单id
    private Byte dcType;    //收派类型 0:取件;1:派件
    private Long userId;    //快递员id
    private Byte operationType;    //操作类型0:接单;1:取件;2:签收;3:异常签收
    private String mailNo;    //面单号
    private String receiverName;    //收件人姓名
    private String receiverPhone;    //收件人电话
    private String receiverProvince;    //收件人省
    private String receiverCity;    //收件人市
    private String receiverArea;    //收件人区县
    private String receiverProvinceName;    //收件人省名称
    private String receiverCityName;    //收件人市名称
    private String receiverAreaName;    //收件人区县名称
    private String receiverAddress;    //收件人地址
    private Double receiverLat;         //收件人纬度
    private Double receiverLng;         //收件人经度
    private String failedDescription;     //失败原因描述

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

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public Byte getDcType() {
        return dcType;
    }

    public void setDcType(Byte dcType) {
        this.dcType = dcType;
    }

    @Column
    public String getFailedDescription() {
        return failedDescription;
    }

    public void setFailedDescription(String failedDescription) {
        this.failedDescription = failedDescription;
    }
    @Column
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
    @Column
    public Byte getOperationType() {
        return operationType;
    }

    public void setOperationType(Byte operationType) {
        this.operationType = operationType;
    }
    @Column
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    @Column
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    @Column
    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }
    @Column
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }
    @Column
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    @Column
    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
    @Column
    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }
    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }

    @Column
    public Double getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(Double receiverLng) {
        this.receiverLng = receiverLng;
    }

    public String getReceiverAreaName() {
        return receiverAreaName;
    }

    public void setReceiverAreaName(String receiverAreaName) {
        this.receiverAreaName = receiverAreaName;
    }

    public String getReceiverCityName() {
        return receiverCityName;
    }

    public void setReceiverCityName(String receiverCityName) {
        this.receiverCityName = receiverCityName;
    }

    public String getReceiverProvinceName() {
        return receiverProvinceName;
    }

    public void setReceiverProvinceName(String receiverProvinceName) {
        this.receiverProvinceName = receiverProvinceName;
    }
}
