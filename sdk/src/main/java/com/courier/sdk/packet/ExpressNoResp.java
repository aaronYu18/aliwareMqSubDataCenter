package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 15/12/21.
 */
public class ExpressNoResp extends IdEntity {
    private String expressNo;
    private String shortAddress;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }
}
