package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class RequestSecret extends BaseEntity {
    private static final long serialVersionUID = 3096952688616081553L;
    private String clientID;

    public RequestSecret(String clientID) {
        this.clientID = clientID;
    }

    public RequestSecret() {
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
