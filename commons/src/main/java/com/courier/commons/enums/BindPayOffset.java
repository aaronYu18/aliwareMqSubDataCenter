package com.courier.commons.enums;

/**
 * Created by vincent on 16/3/31.
 */
public enum BindPayOffset {
    ALIPAY((byte)1, "支付宝"),
    WECHATPAY((byte)2, "微信支付"),
    BAIDUPAY((byte)3, "百度支付"),
    JDPAY((byte)4, "京东支付"),
    ;
    private byte offset;
    private String name;

    BindPayOffset(byte offset, String name) {
        this.offset = offset;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getOffset() {
        return offset;
    }

    public void setOffset(byte offset) {
        this.offset = offset;
    }
}
