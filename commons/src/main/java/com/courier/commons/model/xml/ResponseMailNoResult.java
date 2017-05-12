package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * 获取电子面单的object
 * Created by bin on 2015/11/28.
 */
public class ResponseMailNoResult extends BaseEntity {
    private static final long serialVersionUID = 1390373379147975171L;
    private String logisticProviderID;
    private String txLogisticID;
    private String clientID;
    private String mailNo;
    private DistributeInfo distributeInfo;
    private String code;
    private String success;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public DistributeInfo getDistributeInfo() {
        return distributeInfo;
    }

    public void setDistributeInfo(DistributeInfo distributeInfo) {
        this.distributeInfo = distributeInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
