package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 15/11/20.
 */
public class AliReq extends IdEntity {
    private static final long serialVersionUID = 3825703156404463673L;
    private String mailNoOrMobile;
    private String type;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMailNoOrMobile() {
        return mailNoOrMobile;
    }

    public void setMailNoOrMobile(String mailNoOrMobile) {
        this.mailNoOrMobile = mailNoOrMobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
