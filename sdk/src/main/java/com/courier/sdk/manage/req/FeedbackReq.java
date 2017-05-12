package com.courier.sdk.manage.req;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 15/11/30.
 */
public class FeedbackReq extends IdEntity {
    private static final long serialVersionUID = -8591960185962670550L;
    private String title;
    private String content;
    private String telephone;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
