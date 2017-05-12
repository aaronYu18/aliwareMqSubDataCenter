package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on 16/4/21.
 */
@Table(name="managerLoginRecord")
public class ManagerLoginRecord extends BaseEntity {
    private static final long serialVersionUID = 1903088898581258658L;

    private Long id;
    private Long managerId;
    private String uuid;
    private Byte deviceType;
    private String deviceNo;
    private String version;               //  app版本
    private Boolean status = true;

    public ManagerLoginRecord() {
    }

    public ManagerLoginRecord(String deviceNo, Byte deviceType, Long managerId, String uuid, String version) {
        this.deviceNo = deviceNo;
        this.deviceType = deviceType;
        this.managerId = managerId;
        this.uuid = uuid;
        this.version = version;
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
    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Column
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
