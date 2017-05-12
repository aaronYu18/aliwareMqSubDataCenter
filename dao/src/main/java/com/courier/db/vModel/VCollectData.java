package com.courier.db.vModel;

import com.courier.commons.entity.BaseEntity;
import com.courier.commons.util.excel.ExcelCell;

import java.util.Date;

/**
 * Created by vincent on 16/5/19.
 */
public class VCollectData extends BaseEntity {
    @ExcelCell(indexes = {0,2,0,0,0})
    private Date date;              //日期
    @ExcelCell(indexes = {1,0,5,-1,2})
    private String provinceName;    //省份
    @ExcelCell(indexes = {-1,-1,-1,-1,3})
    private String cityName;    //城市
    @ExcelCell(indexes = {-1,-1,-1,-1,4})
    private String areaName;    //区县
    @ExcelCell(indexes = {-1,-1,-1,-1,5})
    private String address;     //详细地址
    @ExcelCell(indexes = {-1,1,1,1,-1})
    private String orgCode;     //网点编码
    @ExcelCell(indexes = {-1,-1,2,2,-1})
    private String orgName;     //网点名称
    @ExcelCell(indexes = {-1,-1,3,-1,-1})
    private String branchCode;  //分公司编码
    @ExcelCell(indexes = {-1,-1,4,-1,-1})
    private String branchName;  //分公司名称
    @ExcelCell(indexes = {-1,-1,-1,4,-1})
    private String jobNo;       //快递员工号
    @ExcelCell(indexes = {-1,-1,-1,5,-1})
    private String name;        //快递员姓名
    @ExcelCell(indexes = {-1,-1,-1,3,1})
    private String sourceKey;   //订单号
    @ExcelCell(indexes = {2,-1,-1,-1,-1})
    private Integer totalNo;    //发单数
    @ExcelCell(indexes = {3,3,6,-1,-1})
    private Integer receiveNo;  //接单数
    @ExcelCell(indexes = {4,4,7,-1,-1})
    private Integer collectNo;  //取件数
    @ExcelCell(indexes = {5,5,8,-1,-1})
    private Integer waitCollectNo;  //未取件数

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Integer collectNo) {
        this.collectNo = collectNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(Integer receiveNo) {
        this.receiveNo = receiveNo;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public Integer getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(Integer totalNo) {
        this.totalNo = totalNo;
    }

    public Integer getWaitCollectNo() {
        return waitCollectNo;
    }

    public void setWaitCollectNo(Integer waitCollectNo) {
        this.waitCollectNo = waitCollectNo;
    }
}
