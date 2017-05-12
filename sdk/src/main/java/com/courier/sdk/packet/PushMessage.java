package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;

import java.util.Calendar;

/**
 * Created by bin on 2015/11/12.
 */
public class PushMessage extends IdEntity {
    private static final long serialVersionUID = -3116555719463407362L;
    private Long id;
    private String mailNo;
    private String address;
    private Byte type;
    private Byte status;
    private Double lng;
    private Double lat;

    public PushMessage() {
    }

    public PushMessage(Long id, String mailNo, String address, Byte type, Byte status, Double lng, Double lat) {
        this.id = id;
        this.mailNo = mailNo;
        this.address = address;
        this.type = type;
        this.status = status;
        this.lng = lng;
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
