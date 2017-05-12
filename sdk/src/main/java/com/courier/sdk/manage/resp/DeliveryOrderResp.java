package com.courier.sdk.manage.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/10/27.
 * 据订单状态、订单类型、时间区间、快递单号等获取派件订单列表 返还结果
 */
public class DeliveryOrderResp extends IdEntity{

    private static final long serialVersionUID = -9193853763589979134L;
    private Long id;                        //id
    private String expressNo;               //快递单号
    private Double collection;              //代收金额
    private Double freight;                 //运费
    private Date signTime;                  //签收时间
    private String signName;                //签收人姓名
    private Byte status;                    //订单状态
    private String statusName;
    private Byte type;                      //订单类型
    private String typeName;
    private String receiverName;            //收件人姓名
    private String receiverMobile;          //收件人电话
    private String receiverAddress;         //收件人地址
    private String receiverProvince;        //收件人省
    private String receiverCity;            //收件人市
    private String receiverArea;            //收件人区县
    private String receiverProvinceName;        //收件人省
    private String receiverCityName;            //收件人市
    private String receiverAreaName;            //收件人区县
    private Double receiverLng;             //收件人经度
    private Double receiverLat;             //收件人纬度
    private Date createTime;                //创建时间
    private Date updateTime;                //更新时间
    private String failedCode;                //失败原因code
    private String failedCodeStr;           //失败原因code描述
    private String fialedDesc;              //失败描述


    public Double getCollection() {
        return collection;
    }

    public void setCollection(Double collection) {
        this.collection = collection;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFailedCode() {
        return failedCode;
    }

    public void setFailedCode(String failedCode) {
        this.failedCode = failedCode;
    }

    public String getFialedDesc() {
        return fialedDesc;
    }

    public void setFialedDesc(String fialedDesc) {
        this.fialedDesc = fialedDesc;
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

    public String getFailedCodeStr() {
        return failedCodeStr;
    }

    public void setFailedCodeStr(String failedCodeStr) {
        this.failedCodeStr = failedCodeStr;
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