package com.courier.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by beyond on 2016/12/26.
 */
@Table(name = "appointmentOrder")
public class AppointmentOrder extends BaseEntity {
    private static final long serialVersionUID = -6011622161836700658L;
    private Long id;
    private Long userId;                       //派件人id
    private String mailNo;                     //面单号

    private String cnOrderId;
    private String lpCode;
    private String receiverName;
    private String receiverAddress;
    private String threeSegmentCode;

    private Byte cnBizType;
    private String appointTimeStart;
    private String appointTimeEnd;
    private String moneyCp;
    private String moneyDeliver;
    private Byte cnOrderStatus;

    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private String receiverAreaId;
    private Date gmtCreate;
    private Date gmtModified;

    private String orgCode;
    private String branchCode;
    private String terminalCode;

    private Byte orderStatus;
    private Date signTime;
    private String receiveBranchName;  //末端网点信息

    private Byte CnValueInterface ;     //用于区分接口名称
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
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @Column
    public String getCnOrderId() {
        return cnOrderId;
    }

    public void setCnOrderId(String cnOrderId) {
        this.cnOrderId = cnOrderId;
    }

    @Column
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column
    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    @Column
    public String getThreeSegmentCode() {
        return threeSegmentCode;
    }

    public void setThreeSegmentCode(String threeSegmentCode) {
        this.threeSegmentCode = threeSegmentCode;
    }

    @Column
    public Byte getCnBizType() {
        return cnBizType;
    }

    public void setCnBizType(Byte cnBizType) {
        this.cnBizType = cnBizType;
    }

    @Column
    public String getAppointTimeStart() {
        return appointTimeStart;
    }

    public void setAppointTimeStart(String appointTimeStart) {
        this.appointTimeStart = appointTimeStart;
    }

    @Column
    public String getAppointTimeEnd() {
        return appointTimeEnd;
    }

    public void setAppointTimeEnd(String appointTimeEnd) {
        this.appointTimeEnd = appointTimeEnd;
    }

    @Column
    public String getMoneyCp() {
        return moneyCp;
    }

    public void setMoneyCp(String moneyCp) {
        this.moneyCp = moneyCp;
    }

    @Column
    public String getMoneyDeliver() {
        return moneyDeliver;
    }

    public void setMoneyDeliver(String moneyDeliver) {
        this.moneyDeliver = moneyDeliver;
    }

    @Column
    public Byte getCnOrderStatus() {
        return cnOrderStatus;
    }

    public void setCnOrderStatus(Byte cnOrderStatus) {
        this.cnOrderStatus = cnOrderStatus;
    }
    @Column
    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }
    @Column
    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }
    @Column
    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }
    @Column
    public String getReceiverAreaId() {
        return receiverAreaId;
    }

    public void setReceiverAreaId(String receiverAreaId) {
        this.receiverAreaId = receiverAreaId;
    }
    @Column
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    @Column
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
    @Column
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @Column
    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }
    @Column
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Column
    public String getReceiveBranchName() {
        return receiveBranchName;
    }

    public void setReceiveBranchName(String receiveBranchName) {
        this.receiveBranchName = receiveBranchName;
    }

    @Column
    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }
    @Column
    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Byte getCnValueInterface() {
        return CnValueInterface;
    }

    public void setCnValueInterface(Byte cnValueInterface) {
        CnValueInterface = cnValueInterface;
    }
}
