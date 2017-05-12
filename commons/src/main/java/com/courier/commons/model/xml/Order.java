package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 6045349256484288811L;
    private String mailNo;

    public Order(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
}
