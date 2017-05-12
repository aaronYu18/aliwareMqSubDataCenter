package com.courier.commons.model.soaJinGang;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by beyond on 2016/6/16.
 */
public class ExpWaybillWanted implements Serializable {

    private String id;

    private String waybillNo;   // 运单号码
    private String expType;     // 实物类型
    private String wantedType;  // 通缉类型
    private String wantedScope; // 通缉范围
    private String wantedDesc;  // 通缉描述

    private Date expireTime;    // 失效时间
    private String claimOrgCode;// 上报网点编号
    private String claimOrgName;// 上报网点名称
    private String claimer;     // 上报人
    private String claimTel;    // 上报人联系方式

    private String dealOrgCode; // 处理网点编码
    private String dealOrgName; // 处理网点名称
    private String dealDesc;    // 处理描述
    private Date dealTime;      // 处理时间
    private String dealerCode;  // 处理人编号

    private String dealerName;  // 处理人名称
    private String dealerTel;   // 处理人联系方式
    private String status;      // 有效状态
    private String remark;      // 备注
    private String refId;       // 关联ID

    private Date createTime;      // 创建时间
    private String createUserCode;// 创建人代号
    private String createUserName;// 创建人名称
    private String createOrgCode; // 创建组织编号
    private String createOrgName; // 创建组织名称

    private Date modifyTime;      // 修改时间
    private String modifyUserCode;// 修改人编号
    private String modifyUserName;// 修改人名称
    private String modifyOrgCode; // 修改组织编号
    private String modifyOrgName; // 修改组织名称

    private Long versionNo; //版本号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getWantedType() {
        return wantedType;
    }

    public void setWantedType(String wantedType) {
        this.wantedType = wantedType;
    }

    public String getWantedScope() {
        return wantedScope;
    }

    public void setWantedScope(String wantedScope) {
        this.wantedScope = wantedScope;
    }

    public String getWantedDesc() {
        return wantedDesc;
    }

    public void setWantedDesc(String wantedDesc) {
        this.wantedDesc = wantedDesc;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getClaimOrgCode() {
        return claimOrgCode;
    }

    public void setClaimOrgCode(String claimOrgCode) {
        this.claimOrgCode = claimOrgCode;
    }

    public String getClaimOrgName() {
        return claimOrgName;
    }

    public void setClaimOrgName(String claimOrgName) {
        this.claimOrgName = claimOrgName;
    }

    public String getClaimer() {
        return claimer;
    }

    public void setClaimer(String claimer) {
        this.claimer = claimer;
    }

    public String getClaimTel() {
        return claimTel;
    }

    public void setClaimTel(String claimTel) {
        this.claimTel = claimTel;
    }

    public String getDealOrgCode() {
        return dealOrgCode;
    }

    public void setDealOrgCode(String dealOrgCode) {
        this.dealOrgCode = dealOrgCode;
    }

    public String getDealOrgName() {
        return dealOrgName;
    }

    public void setDealOrgName(String dealOrgName) {
        this.dealOrgName = dealOrgName;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerTel() {
        return dealerTel;
    }

    public void setDealerTel(String dealerTel) {
        this.dealerTel = dealerTel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    public String getModifyOrgName() {
        return modifyOrgName;
    }

    public void setModifyOrgName(String modifyOrgName) {
        this.modifyOrgName = modifyOrgName;
    }
}
