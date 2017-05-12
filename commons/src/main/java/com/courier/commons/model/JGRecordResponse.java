package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/7/1.
 */
public class JGRecordResponse  extends BaseEntity {
    private String failMessage;
    private String havaNextPage;
    private String retVal;
    private Integer httpCode;

    public JGRecordResponse() {
    }

    public JGRecordResponse(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getHavaNextPage() {
        return havaNextPage;
    }

    public void setHavaNextPage(String havaNextPage) {
        this.havaNextPage = havaNextPage;
    }

    public String getRetVal() {
        return retVal;
    }

    public void setRetVal(String retVal) {
        this.retVal = retVal;
    }
}
