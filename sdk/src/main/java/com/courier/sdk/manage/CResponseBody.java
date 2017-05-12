package com.courier.sdk.manage;

import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Constant;
import com.courier.sdk.common.IdEntity;
import com.courier.sdk.utils.GsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/16.
 */
public class CResponseBody<T extends IdEntity> {
    private T obj;
    private Byte cmd;
    private List<T> lst;
    private String redirectUrl;
    private Map<String, Object> extMap;
    private Integer code = CodeEnum.C1000.getCode();
    private String prompt = CodeEnum.C1000.getDesc();

    public CResponseBody() {
    }

    public CResponseBody(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public CResponseBody(Class clz, List<T> lst,Integer paegNo,int pageSize,int totalCount) {
        Byte cmd = CMDFactory.getCMDCodeByClass(clz);
        this.cmd = cmd;
        this.lst = lst;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.PAGE_NO_KEY,paegNo);
        map.put(Constant.PAGE_SIZE_KEY,pageSize);
        map.put(Constant.TOTAL_COUNT_KEY,totalCount);
        this.extMap = map;
    }

    public CResponseBody(Class clz, List<T> lst) {
        Byte cmd = CMDFactory.getCMDCodeByClass(clz);
        this.cmd = cmd;
        this.lst = lst;
    }

    public CResponseBody(Class clz, T obj, Integer paegNo,int pageSize,int totalCount) {
        Byte cmd = CMDFactory.getCMDCodeByClass(clz);
        this.cmd = cmd;
        this.obj = obj;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.PAGE_NO_KEY,paegNo);
        map.put(Constant.PAGE_SIZE_KEY,pageSize);
        map.put(Constant.TOTAL_COUNT_KEY,totalCount);
        this.extMap = map;
    }

    public CResponseBody(Class clz, T obj) {
        Byte cmd = CMDFactory.getCMDCodeByClass(clz);
        this.cmd = cmd;
        this.obj = obj;
    }

    public CResponseBody(CodeEnum codeEnum, Map<String, Object> map) {
        this.extMap = map;
        this.code = codeEnum.getCode();
        this.prompt = codeEnum.getDesc();
    }
    public CResponseBody(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.prompt = codeEnum.getDesc();
    }
    public CResponseBody(Integer code, String prompt) {
        this.code = code;
        this.prompt = prompt;
    }

    public CResponseBody(CodeEnum codeEnum, Class clz, T obj) {
        Byte cmd = CMDFactory.getCMDCodeByClass(clz);
        this.code = codeEnum.getCode();
        this.prompt = codeEnum.getDesc();
        this.cmd = cmd;
        this.obj = obj;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public List<T> getLst() {
        return lst;
    }

    public void setLst(List<T> lst) {
        this.lst = lst;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String toJson(){
        return GsonUtil.toJson(this);
    }

    public static <T> T getBean(String json, Class<T> cls){
        return GsonUtil.getBean(json, cls);
    }

}
