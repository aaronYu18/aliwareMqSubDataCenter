package com.courier.core.cache;

import com.courier.commons.entity.BaseEntity;

import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
public class UserLocation extends BaseEntity {
    private static final long serialVersionUID = -7224947102060989512L;
    private Long userId;             //  快递员id
    private Double lat;              //  纬度
    private Double lng;              //  经度
    private Date createTime = new Date();
    private Date updateTime = new Date();

    public UserLocation() {
    }

    public UserLocation(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public UserLocation(Long userId, Double lat, Double lng) {
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
