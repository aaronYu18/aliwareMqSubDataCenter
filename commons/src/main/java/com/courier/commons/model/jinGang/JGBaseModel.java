package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/** 金刚系统的返回值model
 * Created by admin on 2015/10/22.
 */
public class JGBaseModel extends BaseEntity   {
    private static final long serialVersionUID = -7223905866201670181L;
    private String code;
    private String error;
    private String result;
    private String count;

    private int httpStatus;

    public JGBaseModel() {
    }

    public JGBaseModel(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
