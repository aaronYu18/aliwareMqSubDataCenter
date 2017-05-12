package com.courier.commons.api.pay.alipay.param.resp;

import com.courier.commons.api.pay.alipay.param.AlipayResp;

/**
 * Created by ryan on 16/3/29.
 */
public class AuthResp extends AlipayResp {

    private static final long serialVersionUID = 158566686224847417L;
    /** 交换令牌*/
    private String access_token;
    /** 用户的userid*/
    private String user_id;
    /** 用户的open_id*/
    private String alipay_user_id;
    /** 令牌有效期*/
    private Integer expires_in;
    /** 刷新令牌有效期*/
    private Integer re_expires_in;
    /** 刷新令牌*/
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAlipay_user_id() {
        return alipay_user_id;
    }

    public void setAlipay_user_id(String alipay_user_id) {
        this.alipay_user_id = alipay_user_id;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public Integer getRe_expires_in() {
        return re_expires_in;
    }

    public void setRe_expires_in(Integer re_expires_in) {
        this.re_expires_in = re_expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
