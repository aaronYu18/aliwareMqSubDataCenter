package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/**
 * 订单反馈 （2.1接口 及2.2 接口）
 * Created by bin on 2015/11/9.
 */
public class JGAcceptResult extends BaseEntity {

    private static final long serialVersionUID = 8515417350791667000L;
    private String logisticProviderID;
    private String txLogisticID;
    private String success;
    private String reason;

    private int httpStatus;

    public JGAcceptResult() {
    }

    public JGAcceptResult(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
