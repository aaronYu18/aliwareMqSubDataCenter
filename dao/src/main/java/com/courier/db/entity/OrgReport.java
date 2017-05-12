package com.courier.db.entity;

import com.courier.commons.util.excel.ExcelCell;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "orgReport")
public class OrgReport extends BaseEntity {

    private static final long serialVersionUID = -3791740819013341339L;
    private Long id;
    @ExcelCell(indexes = {-1,2,-1,-1})
    private String orgCode;
    @ExcelCell(indexes = {-1,-1,2,-1})
    private String branchCode;
    @ExcelCell(indexes = {-1,0,0,0})
    private String provinceCode;
    @ExcelCell(indexes = {-1,4,4,2})
    private Double sendNo = 0.;             // 派件数
    @ExcelCell(indexes = {-1,5,5,3})
    private Double collectNo = 0.;          // 收件数
    @ExcelCell(indexes = {-1,6,6,4})
    private Double failedNo = 0.;           // 失败数
    private Date countTime;             // 统计时间
    private Byte type;                  // 类型(0:分部（或分公司直属网点） 1:分公司 2:省区)

    @ExcelCell(indexes = {-1,3,-1,-1})
    private String orgName;
    @ExcelCell(indexes = {-1,-1,3,-1})
    private String branchName;
    @ExcelCell(indexes = {-1,1,1,1})
    private String ProvinceName;

    public OrgReport() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
    public Double getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Double collectNo) {
        this.collectNo = collectNo;
    }
    @Column
    public Double getFailedNo() {
        return failedNo;
    }

    public void setFailedNo(Double failedNo) {
        this.failedNo = failedNo;
    }
    @Column
    public Double getSendNo() {
        return sendNo;
    }

    public void setSendNo(Double sendNo) {
        this.sendNo = sendNo;
    }

    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }
    @Column
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @Column
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }
}
