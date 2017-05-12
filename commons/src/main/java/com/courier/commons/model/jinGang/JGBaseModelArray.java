package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/10.
 */
public class JGBaseModelArray extends BaseEntity  {
    private static final long serialVersionUID = -3326541735894734707L;
    private JGOrderInfoResult[] list;
    private String status;
    private String message;
    private String object;
    private String code ;
    private Integer httpStatus;

    public JGBaseModelArray() {
    }

    public JGBaseModelArray(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public JGBaseModelArray(Integer httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

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

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
