package com.courier.commons.api.pay.alipay.param.req;

import com.courier.commons.api.pay.alipay.AlipayConfig;
import com.courier.commons.api.pay.alipay.param.AlipayReq;

/**
 * Created by ryan on 16/3/29.
 */
public class AuthReq extends AlipayReq {

    private static final long serialVersionUID = -8194085059587649280L;
    /** 授权类型*/
    private String grant_type = AlipayConfig.grantType;
    /** 授权码*/
    private String code;

    public AuthReq(){
        this.setMethod(AlipayConfig.authMethod);
    }

    public AuthReq(String grant_type, String code) {
        this.setMethod(AlipayConfig.authMethod);
        this.grant_type = grant_type;
        this.code = code;
    }

    public AuthReq(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
