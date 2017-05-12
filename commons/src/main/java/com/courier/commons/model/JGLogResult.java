package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;

import java.util.Map;

/**
 * Created by bin on 2015/12/14.
 */
public class JGLogResult extends BaseEntity{

    private static final long serialVersionUID = -3278323113683364424L;
    private BaseEntity req;
    private BaseEntity res;

    private Integer errorCode;
    private Object paramsMap ;
    public JGLogResult(BaseEntity res, BaseEntity req) {
        this.res = res;
        this.req = req;
    }

    public Object getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map paramsMap) {
        this.paramsMap = paramsMap;
    }

    public JGLogResult(int errorCode, Map paramsMap) {
        this.errorCode = errorCode;
        this.paramsMap = paramsMap;
    }



    public JGLogResult(BaseEntity res, BaseEntity req, Map paramsMap,int errorCode) {
        this.req = req;
        this.res = res;
        this.errorCode = errorCode;
        this.paramsMap = paramsMap;
    }

    public JGLogResult(BaseEntity res) {
        this.res = res;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }



    public BaseEntity getRes() {
        return res;
    }

    public void setRes(BaseEntity res) {
        this.res = res;
    }

    public BaseEntity getReq() {
        return req;
    }

    public void setReq(BaseEntity req) {
        this.req = req;
    }
}
