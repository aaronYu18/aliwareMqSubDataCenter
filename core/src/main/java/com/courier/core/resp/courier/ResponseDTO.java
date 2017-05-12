package com.courier.core.resp.courier;



import com.courier.sdk.constant.CodeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bin on 2015/6/19.
 * Desc: 调用业务逻辑方法返回的值对象
 */
public class ResponseDTO<T1,T2> implements Serializable{
    private static final long serialVersionUID = -4206384556994181696L;
    private CodeEnum codeEnum;      //状态码
    private List<T1> list;
    private T2  t2;
    private int totalNum;

    public ResponseDTO() {
    }


    public ResponseDTO(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public ResponseDTO(CodeEnum codeEnum, List<T1> list, T2 t2) {
        this.codeEnum = codeEnum;
        this.list = list;
        this.t2 = t2;
    }
    public ResponseDTO(CodeEnum codeEnum, List<T1> list, T2 t2,int totalNum) {
        this.codeEnum = codeEnum;
        this.list = list;
        this.t2 = t2;
        this.totalNum = totalNum;
    }

    public CodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(T2 t2) {
        this.t2 = t2;
    }

    public List<T1> getList() {
        return list;
    }

    public void setList(List<T1> list) {
        this.list = list;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
