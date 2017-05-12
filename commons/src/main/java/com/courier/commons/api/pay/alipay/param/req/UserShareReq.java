package com.courier.commons.api.pay.alipay.param.req;

import com.courier.commons.api.pay.alipay.AlipayConfig;
import com.courier.commons.api.pay.alipay.param.AlipayReq;

/**
 * Created by ryan on 16/3/29.
 */
public class UserShareReq extends AlipayReq{

    private static final long serialVersionUID = -7626316602735122032L;
    /** 交换令牌*/
    private String auth_token;

    public UserShareReq(){
        this.setMethod(AlipayConfig.shareMethod);
    }

    public UserShareReq(String auth_token) {
        this.setMethod(AlipayConfig.shareMethod);
        this.auth_token = auth_token;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
