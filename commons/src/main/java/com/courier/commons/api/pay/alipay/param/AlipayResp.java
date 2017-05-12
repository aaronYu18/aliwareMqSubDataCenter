package com.courier.commons.api.pay.alipay.param;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by ryan on 16/3/29.
 */
public class AlipayResp extends BaseEntity{

    public enum ReqStatus {
        Waiting, Success, Error, Retry, Cancel, Refund
    }

    private static final long serialVersionUID = 1057169269670817886L;
    private ReqStatus reqStatus = null;
    private String code;
    private String msg;
    private String sub_code;
    private String sub_msg;
    private int httpCode = -1;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_msg() {
        return sub_msg;
    }

    public void setSub_msg(String sub_msg) {
        this.sub_msg = sub_msg;
    }

    public ReqStatus getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(ReqStatus reqStatus) {
        this.reqStatus = reqStatus;
    }
}
