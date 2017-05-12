package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/13.
 */
public class AlipayInfoResp extends IdEntity {
    private static final long serialVersionUID = 5196707641400940767L;

    private String name;
    private String alipayAccount;

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
