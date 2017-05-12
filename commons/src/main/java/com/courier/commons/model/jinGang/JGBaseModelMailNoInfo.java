package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/**
 * 运单详情返回对象
 * Created by bin on 2015/11/26.
 */
public class JGBaseModelMailNoInfo extends BaseEntity   {
    private static final long serialVersionUID = -4816069845421079335L;
    private JGOrderInfoResult[] list;
    private String status;
    private String message;
    private JGMailNoInfoResult object;

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

    public JGMailNoInfoResult getObject() {
        return object;
    }

    public void setObject(JGMailNoInfoResult object) {
        this.object = object;
    }
}
