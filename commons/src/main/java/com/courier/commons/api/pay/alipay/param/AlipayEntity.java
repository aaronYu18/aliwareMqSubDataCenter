package com.courier.commons.api.pay.alipay.param;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by ryan on 16/3/29.
 */
public class AlipayEntity extends BaseEntity{

    private static final long serialVersionUID = -7187032788095378956L;
    private AlipayReq alipayReq;
    private AlipayResp alipayResp;

    public AlipayEntity(){}

    public AlipayEntity(AlipayReq alipayReq, AlipayResp alipayResp) {
        this.alipayReq = alipayReq;
        this.alipayResp = alipayResp;
    }

    public AlipayReq getAlipayReq() {
        return alipayReq;
    }

    public void setAlipayReq(AlipayReq alipayReq) {
        this.alipayReq = alipayReq;
    }

    public AlipayResp getAlipayResp() {
        return alipayResp;
    }

    public void setAlipayResp(AlipayResp alipayResp) {
        this.alipayResp = alipayResp;
    }
}
