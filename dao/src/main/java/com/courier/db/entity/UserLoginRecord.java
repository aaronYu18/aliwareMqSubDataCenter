package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "userLoginRecord")
public class UserLoginRecord extends BaseEntity {

    private static final long serialVersionUID = -7209867844032888444L;
    private Long id;
    private Long userId;                  //  快递员id
    private String uuid;                  //  经度
    private String ytoToken;              //  经度
    private Byte deviceType;              //  经度
    private String deviceNo;              //  纬度
    private String version;               //  app版本
    private Boolean status = true;        //  状态


    public UserLoginRecord() {
    }

    public UserLoginRecord(Long userId, String uuid, Byte deviceType, String deviceNo, String ytoToken, String version) {
        this.userId = userId;
        this.uuid = uuid;
        this.deviceType = deviceType;
        this.deviceNo = deviceNo;
        this.ytoToken = ytoToken;
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }
    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column
    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
    @Column
    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }
    @Column
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Column
    public String getYtoToken() {
        return ytoToken;
    }

    public void setYtoToken(String ytoToken) {
        this.ytoToken = ytoToken;
    }
    @Column
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
