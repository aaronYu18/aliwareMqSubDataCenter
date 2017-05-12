package com.courier.db.vModel;


import com.courier.commons.entity.BaseEntity;
import com.courier.db.entity.User;

import java.util.List;

/**
 * Created by vincent on 16/3/15.
 */
public class VBranch extends BaseEntity {
    private Long id;
    private String provinceCode;
    private String provinceName;
    private String orgCode;
    private String branchCode;
    private Integer courierCount;
    private Integer sendNo;
    private Integer collectNo;
    private Integer failNo;
    private String couriers;

    public Integer getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Integer collectNo) {
        this.collectNo = collectNo;
    }

    public Integer getCourierCount() {
        return courierCount;
    }

    public void setCourierCount(Integer courierCount) {
        this.courierCount = courierCount;
    }

    public String getCouriers() {
        return couriers;
    }

    public void setCouriers(String couriers) {
        this.couriers = couriers;
    }

    public Integer getFailNo() {
        return failNo;
    }

    public void setFailNo(Integer failNo) {
        this.failNo = failNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getSendNo() {
        return sendNo;
    }

    public void setSendNo(Integer sendNo) {
        this.sendNo = sendNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
