package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by bin on 2015/11/10.
 */
public class JGBaseModelStatInfo extends BaseEntity   {
    private static final long serialVersionUID = -7604257841442215888L;
    private JGOrderInfoResult[] list;
    private String status;
    private String message;
    private JGStatInfoResult object;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JGOrderInfoResult[] getList() {
        return list;
    }

    public void setList(JGOrderInfoResult[] list) {
        this.list = list;
    }

    public JGStatInfoResult getObject() {
        return object;
    }

    public void setObject(JGStatInfoResult object) {
        this.object = object;
    }
}
