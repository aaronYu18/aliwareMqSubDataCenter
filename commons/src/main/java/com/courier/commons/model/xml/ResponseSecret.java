package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class ResponseSecret  extends BaseEntity {
    private static final long serialVersionUID = -5602478256688788945L;
    private String clientID;
    private String secretKey;
    private String success;
    private String reason;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
