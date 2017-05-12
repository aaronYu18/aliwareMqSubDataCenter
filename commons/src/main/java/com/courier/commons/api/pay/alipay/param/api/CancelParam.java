package com.courier.commons.api.pay.alipay.param.api;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by ryan on 16/3/28.
 */
public class CancelParam extends BaseEntity{

    private static final long serialVersionUID = -4448762924434925435L;
    /** 用户Id*/
    private Long userId;
    /** 交易流水号*/
    private String serialNo;

    public CancelParam(){}

    public CancelParam(Long userId, String serialNo) {
        this.userId = userId;
        this.serialNo = serialNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
