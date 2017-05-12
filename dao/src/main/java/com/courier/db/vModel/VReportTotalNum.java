package com.courier.db.vModel;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/4/19.
 */
public class VReportTotalNum extends BaseEntity {
    private static final long serialVersionUID = -7810530840527144170L;
    private Double sendNoTotal=0.;
    private Double collectNoTotal=0.;
    private Double failedNoTotal=0.;
    private Long userId;
    private String time;
    private String orgCode ;
    private String jobNo;
    private String jobName;
    private String provinceCode;
    private String branchCode;
    public VReportTotalNum() {
    }

    public VReportTotalNum(Double sendNoTotal, Double collectNoTotal, Double failedNoTotal) {
        this.sendNoTotal = sendNoTotal;
        this.collectNoTotal = collectNoTotal;
        this.failedNoTotal = failedNoTotal;
    }

    public VReportTotalNum(Double sendNoTotal, Double collectNoTotal, Double failedNoTotal, String time) {
        this.sendNoTotal = sendNoTotal;
        this.collectNoTotal = collectNoTotal;
        this.failedNoTotal = failedNoTotal;
        this.time = time;
    }

    public VReportTotalNum(Double sendNoTotal, Double collectNoTotal, Double failedNoTotal, Long userId) {
        this.sendNoTotal = sendNoTotal;
        this.collectNoTotal = collectNoTotal;
        this.failedNoTotal = failedNoTotal;
        this.userId = userId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getSendNoTotal() {
        return sendNoTotal;
    }

    public void setSendNoTotal(Double sendNoTotal) {
        this.sendNoTotal = sendNoTotal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getFailedNoTotal() {
        return failedNoTotal;
    }

    public void setFailedNoTotal(Double failedNoTotal) {
        this.failedNoTotal = failedNoTotal;
    }

    public Double getCollectNoTotal() {
        return collectNoTotal;
    }

    public void setCollectNoTotal(Double collectNoTotal) {
        this.collectNoTotal = collectNoTotal;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
