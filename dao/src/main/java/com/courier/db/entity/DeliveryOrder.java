package com.courier.db.entity;

import com.courier.commons.enums.CommonEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "deliveryOrder")
public class DeliveryOrder extends BaseEntity {

    private static final long serialVersionUID = -6011622161836700658L;
    private Long id;
    private Long userId;	                   //       派件人id
    private String mailNo;	                   //       面单号
    private Double collectionMoney;	           //       代收金额
    private Double freightMoney;	           //       到付金额
    private String receiverName;	           //       收件人姓名
    private String receiverPhone;	           //       收件人电话

    private String receiverProvince;	       //       收件人省
    private String receiverCity;	           //       收件人市
    private String receiverArea;	           //       收件人区县
    private String receiverProvinceName;	   //       收件人省
    private String receiverCityName;	       //       收件人市
    private String receiverAreaName;	       //       收件人区县

    private String receiverAddress;	           //       收件人地址
    private Double receiverLat;                //       纬度
    private Double receiverLng;                //       经度
    private Byte paymentType;	               //       付款类型(0:预付;1:到付;2:代收;3:到付并代收)
    private Byte orderType;	                   //       订单类型(0:金刚拉取;1:快递员录入)
    private Date signTime;	                   //       服务器签收时间
    private Date appSignTime = null;	       //       app签收时间
    private String signPersonName;	           //       签收人姓名
    private Byte signPersonType;	           //       签收人类型(0:本人签收;1:门卫;2:邮件收发章;3:代办点签收;4:他人代收)
    private String failedCode;	               //       失败原因Code
    private String failedDescription;	       //       失败原因描述
    private Boolean hasPicture;	               //       是否有签收图片（0:无; 1:有）
    private String signPic;	                   //       签收图片
    private String deviceType;	               //       设备类型（0:android; 1:ios）
    private String mac;	                       //       mac地址
    private Byte orderStatus;	               //       订单状态(0:派送中;1:正常签收;2:异常签收;3:代理点签收)
    private Boolean flag;	                   //       逻辑删除(0:删除;1:正常)
    private Date jGCreateTime;	               //       金刚创建时间

    private Byte isProblem = CommonEnum.INVALID.getCode();    //       问题件标记
    private Byte isWanted = CommonEnum.INVALID.getCode();     //       通缉件标记
    private Byte isCnAppoint = CommonEnum.INVALID.getCode();                   //是否预约件
    private Integer cnAppointBeginT = null;            //预约时间起点
    private Integer cnAppointEndT = null;              //预约时间终点
    private Double moneyDeliver;                //小件员分账信息

    public DeliveryOrder() {
    }

    public DeliveryOrder(String receiverAddress) {
        this.receiverAddress = receiverAddress;
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAppSignTime() {
        return appSignTime;
    }

    public void setAppSignTime(Date appSignTime) {
        this.appSignTime = appSignTime;
    }
    @Column
    public Double getCollectionMoney() {
        return collectionMoney;
    }

    public void setCollectionMoney(Double collectionMoney) {
        this.collectionMoney = collectionMoney;
    }
    @Column
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Column
    public String getFailedCode() {
        return failedCode;
    }

    public void setFailedCode(String failedCode) {
        this.failedCode = failedCode;
    }
    @Column
    public String getFailedDescription() {
        return failedDescription;
    }

    public void setFailedDescription(String failedDescription) {
        this.failedDescription = failedDescription;
    }
    @Column
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    @Column
    public Double getFreightMoney() {
        return freightMoney;
    }

    public void setFreightMoney(Double freightMoney) {
        this.freightMoney = freightMoney;
    }
    @Column
    public Boolean getHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(Boolean hasPicture) {
        this.hasPicture = hasPicture;
    }
    @Column
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
    @Column
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
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
    public Byte getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Byte paymentType) {
        this.paymentType = paymentType;
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
    public String getSignPersonName() {
        return signPersonName;
    }

    public void setSignPersonName(String signPersonName) {
        this.signPersonName = signPersonName;
    }
    @Column
    public Byte getSignPersonType() {
        return signPersonType;
    }

    public void setSignPersonType(Byte signPersonType) {
        this.signPersonType = signPersonType;
    }
    @Column
    public String getSignPic() {
        return signPic;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }
    @Column
    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
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

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getjGCreateTime() {
        return jGCreateTime;
    }

    public void setjGCreateTime(Date jGCreateTime) {
        this.jGCreateTime = jGCreateTime;
    }
    @Column
    public String getReceiverAreaName() {
        return receiverAreaName;
    }

    public void setReceiverAreaName(String receiverAreaName) {
        this.receiverAreaName = receiverAreaName;
    }
    @Column
    public String getReceiverCityName() {
        return receiverCityName;
    }

    public void setReceiverCityName(String receiverCityName) {
        this.receiverCityName = receiverCityName;
    }
    @Column
    public String getReceiverProvinceName() {
        return receiverProvinceName;
    }

    public void setReceiverProvinceName(String receiverProvinceName) {
        this.receiverProvinceName = receiverProvinceName;
    }
    @Column
    public Byte getIsWanted() {
        return isWanted;
    }

    public void setIsWanted(Byte isWanted) {
        this.isWanted = isWanted;
    }
    @Column
    public Byte getIsProblem() {
        return isProblem;
    }

    public void setIsProblem(Byte isProblem) {
        this.isProblem = isProblem;
    }
    @Column
    public Byte getIsCnAppoint() {
        return isCnAppoint;
    }

    public void setIsCnAppoint(Byte isCnAppoint) {
        this.isCnAppoint = isCnAppoint;
    }
    @Column
    public Integer getCnAppointBeginT() {
        return cnAppointBeginT;
    }

    public void setCnAppointBeginT(Integer cnAppointBeginT) {
        this.cnAppointBeginT = cnAppointBeginT;
    }
    @Column
    public Integer getCnAppointEndT() {
        return cnAppointEndT;
    }

    public void setCnAppointEndT(Integer cnAppointEndT) {
        this.cnAppointEndT = cnAppointEndT;
    }
    @Column
    public Double getMoneyDeliver() {
        return moneyDeliver;
    }

    public void setMoneyDeliver(Double moneyDeliver) {
        this.moneyDeliver = moneyDeliver;
    }
}
