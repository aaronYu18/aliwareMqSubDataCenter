package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class UpdateInfo extends BaseEntity {
    private static final long serialVersionUID = -2493307757946989480L;
    private String logisticProviderID;
    private String txLogisticID;
    private String mailNo;
    private String clientID;
    private String infoType;
    private String infoContent;
    private String remark;

    public UpdateInfo(String txLogisticID, String mailNo, String clientID, String remark) {
        this.logisticProviderID = "YTO";
        this.txLogisticID = txLogisticID;
        this.mailNo = mailNo;
        this.clientID = clientID;
        this.remark = remark;
        this.infoType = "INSTRUCTION";
        this.infoContent = "UPDATE";
    }
    public UpdateInfo(String logisticProviderID, String txLogisticID, String mailNo, String clientID, String remark) {
        this.logisticProviderID = logisticProviderID;
        this.txLogisticID = txLogisticID;
        this.mailNo = mailNo;
        this.clientID = clientID;
        this.remark = remark;
        this.infoType = "INSTRUCTION";
        this.infoContent = "UPDATE";
    }

    public UpdateInfo(String logisticProviderID, String txLogisticID, String mailNo, String clientID, String infoType, String infoContent, String remark) {
        this.logisticProviderID = logisticProviderID;
        this.txLogisticID = txLogisticID;
        this.mailNo = mailNo;
        this.clientID = clientID;
        this.infoType = infoType;
        this.infoContent = infoContent;
        this.remark = remark;
    }



    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
