package com.courier.commons.util.http;

import com.courier.commons.util.json.GsonUtil;

import javax.validation.constraints.NotNull;

/**
 * Created by ryan on 15/5/5.
 */
public class HttpRequestResult{
    private int code;
    private String content;

    public HttpRequestResult() {
    }

    public HttpRequestResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJson(){
        return GsonUtil.toJson(this);
    }

    public static Object getBean(@NotNull String json, Class cls){
        return GsonUtil.getBean(json, cls);
    }
}