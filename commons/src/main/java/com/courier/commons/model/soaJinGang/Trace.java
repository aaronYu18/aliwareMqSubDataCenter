package com.courier.commons.model.soaJinGang;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/6/15.
 */
public class Trace extends BaseEntity {
    private static final long serialVersionUID = 6936072717943791746L;
    private String effectiveTypeName;
    private String empCode;
    private String empName;
    private String id;
    private String ioType;
    private String nextOrgName;
    private String opCode;
    private String opEmpCode;
    private String opEmpName;
    private String opName;
    private String opOrgCode;
    private String opOrgName;
    private String opTime;
    private String orderInfo;
    private String previousOrgName;
    private String uploadTime;
    private String waybillNo;
    private String weight;

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getEffectiveTypeName() {
        return effectiveTypeName;
    }

    public void setEffectiveTypeName(String effectiveTypeName) {
        this.effectiveTypeName = effectiveTypeName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    public String getNextOrgName() {
        return nextOrgName;
    }

    public void setNextOrgName(String nextOrgName) {
        this.nextOrgName = nextOrgName;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpEmpCode() {
        return opEmpCode;
    }

    public void setOpEmpCode(String opEmpCode) {
        this.opEmpCode = opEmpCode;
    }

    public String getOpEmpName() {
        return opEmpName;
    }

    public void setOpEmpName(String opEmpName) {
        this.opEmpName = opEmpName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getOpOrgCode() {
        return opOrgCode;
    }

    public void setOpOrgCode(String opOrgCode) {
        this.opOrgCode = opOrgCode;
    }

    public String getOpOrgName() {
        return opOrgName;
    }

    public void setOpOrgName(String opOrgName) {
        this.opOrgName = opOrgName;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPreviousOrgName() {
        return previousOrgName;
    }

    public void setPreviousOrgName(String previousOrgName) {
        this.previousOrgName = previousOrgName;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
