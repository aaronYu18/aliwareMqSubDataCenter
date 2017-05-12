package com.courier.commons.model.xml;

/**
 * Created by bin on 2015/12/21.
 */
public class MailNoAndShortAddress {
    private String mailNo;
    private String shortAddress;

    public MailNoAndShortAddress(String mailNo, String shortAddress) {
        this.mailNo = mailNo;
        this.shortAddress = shortAddress;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }
}
