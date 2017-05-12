package com.courier.commons.api;

import com.courier.commons.api.pay.alipay.AlipayCode;
import com.courier.commons.entity.BaseEntity;

import java.util.List;

/**
 * Created by ryan on 15/10/30.
 * RPC Api Result
 */
public class ApiResult<T> extends BaseEntity implements Cloneable{

    private static final long serialVersionUID = 1564156942003266582L;
    private int code;
    private String prompt;
    private T t;
    private List<T> lst;

    public ApiResult(){}

    public ApiResult(AlipayCode alipayCode){
        this.code = alipayCode.getCode();
        this.prompt = alipayCode.getDesc();
    }
    public ApiResult(AlipayCode alipayCode, Object o){
        this.code = alipayCode.getCode();
        this.prompt = alipayCode.getDesc();
        this.t = (T) o;
    }

    public ApiResult(AlipayCode alipayCode, List lst, Object t) {
        this.lst = lst;
        this.t = (T) t;
        this.code = alipayCode.getCode();
        this.prompt = alipayCode.getDesc();
    }

    public ApiResult(int code) {
        this.code = code;
    }

    public ApiResult(int code, String prompt) {
        this.code = code;
        this.prompt = prompt;
    }
    
    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public ApiResult setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public T getT() {
        return t;
    }

    public ApiResult setT(T t) {
        this.t = t;
        return this;
    }

    public List<T> getLst() {
        return lst;
    }

    public ApiResult setLst(List<T> lst) {
        this.lst = lst;
        return this;
    }

    @Override
    public ApiResult clone() {
        try {
            return (ApiResult) super.clone();
        } catch (Exception e){
            return null;
        }
    }
}
