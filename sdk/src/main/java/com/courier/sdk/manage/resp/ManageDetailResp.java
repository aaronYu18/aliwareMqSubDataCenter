package com.courier.sdk.manage.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 16/4/18.
 */
public class ManageDetailResp extends IdEntity{
    private static final long serialVersionUID = -7262237555162572331L;

    private String code;
    private String name;
    private Long userId;
    private Date date;
    private Byte type;
    private Integer waitSignCount;
    private Integer waitCollectCount;
    private Integer signCount;
    private Integer collectCount;
    private Integer questionCount;
    private Integer courierCount;
    private Double lng;
    private Double lat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getWaitCollectCount() {
        return waitCollectCount;
    }

    public void setWaitCollectCount(Integer waitCollectCount) {
        this.waitCollectCount = waitCollectCount;
    }

    public Integer getWaitSignCount() {
        return waitSignCount;
    }

    public void setWaitSignCount(Integer waitSignCount) {
        this.waitSignCount = waitSignCount;
    }

    public Integer getCourierCount() {
        return courierCount;
    }

    public void setCourierCount(Integer courierCount) {
        this.courierCount = courierCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
