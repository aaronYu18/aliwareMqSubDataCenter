package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/6/2.
 */
public class OrderMessage extends BaseEntity {
    private static final long serialVersionUID = -8035597148875151153L;
    private String mailNo;
    private String clientID;
    private String txLogisticID;
    private String bigPen;

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

    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    public String getBigPen() {
        return bigPen;
    }

    public void setBigPen(String bigPen) {
        this.bigPen = bigPen;
    }
}
