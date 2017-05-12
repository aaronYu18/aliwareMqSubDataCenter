package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/10.
 */
public class SMSBeanRequest extends BaseEntity{
    private static final long serialVersionUID = 670914054381774914L;
    private String receiverNum;
    private String displayname;
    private String mobile;
    private String receiverName;
    private String waybillno;
    private String userId;
    private String smsDemoNo;
    private String address;
    private String jobNo;
    private String orgCode;
    private String smsType;// 短信类型 code 与number

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getReceiverNum() {
        return receiverNum;
    }

    public void setReceiverNum(String receiverNum) {
        this.receiverNum = receiverNum;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getWaybillno() {
        return waybillno;
    }

    public void setWaybillno(String waybillno) {
        this.waybillno = waybillno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSmsDemoNo() {
        return smsDemoNo;
    }

    public void setSmsDemoNo(String smsDemoNo) {
        this.smsDemoNo = smsDemoNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
