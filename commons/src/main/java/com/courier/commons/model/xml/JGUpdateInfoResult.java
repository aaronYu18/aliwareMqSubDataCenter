package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/10.
 */
public class JGUpdateInfoResult extends BaseEntity{
    private static final long serialVersionUID = -3490955674951225504L;
    private String txLogisticID;
    private String logisticProviderID;
    private String success;
    private String reason;
    private int httpStatus;

    public JGUpdateInfoResult() {
    }

    public JGUpdateInfoResult(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
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
