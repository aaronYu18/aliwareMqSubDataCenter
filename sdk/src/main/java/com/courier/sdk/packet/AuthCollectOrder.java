package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/10/23.
 */
public class AuthCollectOrder extends IdEntity{

    private Long id;                     //揽件订单ID
    private Byte expressType;            //快递单类型
    private String expressNo;            //快递单号
    private String senderName;           //寄件人姓名
    private Byte senderSex;              //寄件人性别
    private String senderMobile;         //寄件人电话
    private String senderPhone;          //寄件人固话
    private Byte certificateType;      //寄件人证件类型
    private String certificateNo;        //寄件人证件号码
    private String senderAddress;        //寄件人地址
    private String senderProvince;       //寄件人省
    private String senderCity;           //寄件人市
    private String senderArea;           //寄件人区县
    private String senderProvinceName;       //寄件人省
    private String senderCityName;           //寄件人市
    private String senderAreaName;           //寄件人区县
    private String receiverName;         //收件人姓名
    private String receiverMobile;       //收件人电话
    private String receiverAddress;      //收件人地址
    private String receiverProvince;     //收件人省
    private String receiverCity;         //收件人市
    private String receiverArea;         //收件人区县
    private String receiverProvinceName;     //收件人省
    private String receiverCityName;         //收件人市
    private String receiverAreaName;         //收件人区县
    private Byte ageing;                 //时效
    private Double weight;               //重量
    private Double collection;           //代收货款
    private Double freight;              //运费
    private String remark;               //订单备注
    private String shortAddress;         //大头笔及二段码
    private String pictureSerialNo;      //开包照片序列号(仅浙江用)
    private String fm;      //赋码(仅全国用)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public Byte getAgeing() {
        return ageing;
    }

    public void setAgeing(Byte ageing) {
        this.ageing = ageing;
    }

    public Double getCollection() {
        return collection;
    }

    public void setCollection(Double collection) {
        this.collection = collection;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Byte getExpressType() {
        return expressType;
    }

    public void setExpressType(Byte expressType) {
        this.expressType = expressType;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Byte getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Byte certificateType) {
        this.certificateType = certificateType;
    }

    public String getPictureSerialNo() {
        return pictureSerialNo;
    }

    public void setPictureSerialNo(String pictureSerialNo) {
        this.pictureSerialNo = pictureSerialNo;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public Byte getSenderSex() {
        return senderSex;
    }

    public void setSenderSex(Byte senderSex) {
        this.senderSex = senderSex;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
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

    public String getSenderAreaName() {
        return senderAreaName;
    }

    public void setSenderAreaName(String senderAreaName) {
        this.senderAreaName = senderAreaName;
    }

    public String getSenderCityName() {
        return senderCityName;
    }

    public void setSenderCityName(String senderCityName) {
        this.senderCityName = senderCityName;
    }

    public String getSenderProvinceName() {
        return senderProvinceName;
    }

    public void setSenderProvinceName(String senderProvinceName) {
        this.senderProvinceName = senderProvinceName;
    }
}
