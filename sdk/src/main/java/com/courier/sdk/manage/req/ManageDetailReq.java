package com.courier.sdk.manage.req;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/4/18.
 */
public class ManageDetailReq extends IdEntity {
    private static final long serialVersionUID = 4717787308606278378L;
    private String orgCode;
    private Byte type;
    private String beginTime;
    private String endTime;
    private Byte queryType;
    private String key;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Byte getQueryType() {
        return queryType;
    }

    public void setQueryType(Byte queryType) {
        this.queryType = queryType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
