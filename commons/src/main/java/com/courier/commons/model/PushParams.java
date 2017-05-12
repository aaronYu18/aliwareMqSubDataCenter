package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by bin on 2015/11/12.
 */
public class PushParams extends BaseEntity {
    private static final long serialVersionUID = 1471781081656824075L;
    private Byte type;
    private String filter;
    @NotBlank(message = "{push.params.title.required}")
    private String title;
    @NotBlank(message = "{push.params.message.required}")
    private String message;

    private String phone;
    private String jobNo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
