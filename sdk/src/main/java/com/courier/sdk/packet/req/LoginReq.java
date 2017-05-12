package com.courier.sdk.packet.req;


import com.courier.sdk.common.IdEntity;
import com.courier.sdk.common.IdEntity;
/**
 * Created by vincent on 15/6/19.
 */
public class LoginReq extends IdEntity {
    private static final long serialVersionUID = -6110228667498583322L;
    /**账户(工号)*/
    private String account;
    /**登陆密码*/
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}