package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/6/16.
 */
public class CRequestBody<T extends IdEntity>  {
    private Byte cmd;
    private T obj;
    private List<T> lst;
    private Map<String, String> extMap;
    private String uuid;
    private String version;


    public CRequestBody() {
    }

    public CRequestBody(Map<String, String> extMap, String uuid, String version) {
        this.extMap = extMap;
        this.uuid = uuid;
        this.version = version;
    }

    public Byte getCmd() {
        return cmd;
    }

    public void setCmd(Byte cmd) {
        this.cmd = cmd;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }

    public List<T> getLst() {
        return lst;
    }

    public void setLst(List<T> lst) {
        this.lst = lst;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
