package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "collectOrder")
public class CollectOrder extends BaseEntity {

    private static final long serialVersionUID = -3636905302244332552L;
    private Long id;
    private Long userId;                            //  快递员id
    private String serialNo;                        //  流水号uuid，对外使用
    private Byte source;                            //  渠道（-1:无单取件,0:移动官网使用,1:微信使用,2:安卓App使用,3:C5使用,4:支付宝使用,5:会员平台使用,6:官网使用,7:IOSApp使用,8:百度轻应用使用）
    private String sourceKey;                       //  来源系统中的唯一标示
    private String mailNo;                          //  面单号
    private Byte mailType;                          //  面单类型（0:纸质面单;1:电子面单）
    private String senderName;                      //  寄件人姓名
    private String senderPhone;                     //  寄件人手机
    private String senderTelPhone;                  //  寄件人固话
    private String senderProvince;                  //  寄件人省
    private String senderCity;                      //  寄件人市
    private String senderArea;                      //  寄件人区县
    private String senderProvinceName;              //  寄件人省
    private String senderCityName;                  //  寄件人市
    private String senderAreaName;                  //  寄件人区县
    private String senderAddress;                   //  寄件人地址
    private String goodsName;                         //  物品名称
    private Double weight;                            //  重量
    private Byte ageing;                            //  时效
    private String remark;                        //  备注
    private Byte bespeakTime;                       //  预约时间段
    private Double freightMoney;                    //  运费
    private Double collectionMoney;                 //  代收金额
    private Double senderLat;                       //  寄件人纬度
    private Double senderLng;                       //  寄件人经度
    private String receiverName;                    //  收件人姓名
    private String receiverPhone;                   //  收件人电话
    private String receiverProvince;                //  收件人省
    private String receiverCity;                    //  收件人市
    private String receiverArea;                    //  收件人区县
    private String receiverProvinceName;            //  收件人省
    private String receiverCityName;                //  收件人市
    private String receiverAreaName;                //  收件人区县
    private String receiverAddress;                 //  收件人地址
    private Byte orderType;                         //  订单类型(0:抢单;1:系统指派)
    private Byte orderStatus;                       //  订单状态(0:待接单;1:接单待揽收;2:已揽收;3:待接单超时;4:取消)
    private Date receiveTime;                       //  接单时间
    private Date collectTime;                       //  揽收时间
    private String customerID;                      //客户编号
    private String shortAddress;                    //大头笔及二段码
    private Double coupon;
    private Byte couponType;                        //  0:普通订单,1:使用抵用券,2:物品兑换（兼容后期物品兑换功能）

    public CollectOrder() {
    }

    public CollectOrder(String senderAddress) {
        this.senderAddress = senderAddress;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public Byte getAgeing() {
        return ageing;
    }

    public void setAgeing(Byte ageing) {
        this.ageing = ageing;
    }

    @Column
    public Byte getBespeakTime() {
        return bespeakTime;
    }

    public void setBespeakTime(Byte bespeakTime) {
        this.bespeakTime = bespeakTime;
    }

    @Column
    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    @Column
    public Double getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(Double collectionMoney) {
        this.collectionMoney = collectionMoney;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    public Double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(Double freightMoney) {
        this.freightMoney = freightMoney;
    }

    @Column
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Column
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column
    public Byte getMailType() {
        return mailType;
    }

    public void setMailType(Byte mailType) {
        this.mailType = mailType;
    }

    @Column
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Column
    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column
    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    @Column
    public String getSenderArea() {
        return senderArea;
    }

    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    @Column
    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    @Column
    public Double getSenderLat() {
        return senderLat;
    }

    public void setSenderLat(Double senderLat) {
        this.senderLat = senderLat;
    }

    @Column
    public Double getSenderLng() {
        return senderLng;
    }

    public void setSenderLng(Double senderLng) {
        this.senderLng = senderLng;
    }

    @Column
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column
    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    @Column
    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    @Column
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Column
    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    @Column
    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    @Column
    public Double getWeight() {
        return weight;
    }

    @Column
    public Double getCoupon() {
        return coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    @Column
    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    @Column
    public String getSenderTelPhone() {
        return senderTelPhone;
    }

    public void setSenderTelPhone(String senderTelPhone) {
        this.senderTelPhone = senderTelPhone;
    }
}
