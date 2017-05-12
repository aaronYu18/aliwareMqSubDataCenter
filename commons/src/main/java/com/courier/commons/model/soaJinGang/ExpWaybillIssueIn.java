package com.courier.commons.model.soaJinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by beyond on 2016/5/31.
 */
public class ExpWaybillIssueIn extends BaseEntity {
    private static final long serialVersionUID = 4057948506401081370L;
    /**
     * 编号
     */
    private String id;
    /**
     * 运单号码
     */
    private String waybillNo;
    /**
     * 快件类型
     */
    private String expType;
    /**
     * 始发管理区编号
     */
    private String sourceManageCode;
    private String sourceManageName;
    /**
     * 始发省区编号
     */
    private String sourceRegionCode;
    private String sourceRegionName;
    /**
     * 始发分公司编号
     */
    private String sourceBranchCode;
    private String sourceBranchName;
    /**
     * 始发网点编号
     */
    private String sourceOrgCode;
    private String sourceOrgName;
    /**
     * 时效类型编号
     */
    private String effectiveTypeCode;
    private String effectiveTypeName;
    /**
     * 发件客户编码
     */
    private String senderCustomerCode;
    private String senderCustomerName;
    /**
     * 揽收业务员编号
     */
    private String takingEmpCode;
    private String takingEmpName;
    /**
     * 问题大类
     */
    private String issueType;
    private String issueTypeName;
    /**
     * 问题描述
     */
    private String issueDesc;
    /**
     * 上报管理区编号
     */
    private String manageCode;
    private String manageName;
    /**
     * 上报省区编号
     */
    private String regionCode;
    private String regionName;
    /**
     * 上报分公司编号
     */
    private String branchCode;
    private String branchName;
    /**
     * 上报网点编号
     */
    private String orgCode;
    private String orgName;
    /**
     * 接收管理区编号
     */
    private String recManageCode;
    private String recManageName;
    /**
     * 接收省区编号
     */
    private String recRegionCode;
    private String recRegionName;
    /**
     * 接收分公司编号
     */
    private String recBranchCode;
    private String recBranchName;
    /**
     * 接收网点编号
     */
    private String recOrgCode;
    private String recOrgName;
    /**
     * 问题件状态编号
     */
    private String statusId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建用户编号
     */
    private String createUserCode;
    private String createUserName;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改网点编号
     */
    private String modifyOrgCode;
    private String modifyOrgName;
    /**
     * 修改用户编号
     */
    private String modifyUserCode;
    private String modifyUserName;
    /**
     * 上传系统编号
     */
    private String customerCode;
    /**
     * 问题件辅助媒体编号
     */
    private String mediaId;

    //------
    /**
     * 上报状态
     */
    private String status;
    private String statusName;
    /**
     * 处理管理区编号
     */
    private String dealManageCode;
    private String dealManageName;
    /**
     * 处理省区编号
     */
    private String dealRegionCode;
    private String dealRegionName;
    /**
     * 处理分公司编号
     */
    private String dealBranchCode;
    private String dealBranchName;
    /**
     * 处理网点编号
     */
    private String dealOrgCode;
    private String dealOrgName;
    /**
     * 最后处理网点编号
     */
    private String lastDealOrgCode;
    private String lastDealOrgName;
    /**
     * 最后处理时间
     */
    private String lastDealTime;
    /**
     * 问题件处理次数
     */
    private Integer dealNumber;
    /**
     * 问题件上报时间
     */
    private Date issueCreateTime;
    /**
     * 展开检索时间
     */
    private Date jobTime;
    /**
     * 版本号
     */
    private String versionNo;
    /**
     * 线程号
     */
    private Integer sno;
    /**
     * 展开状态
     */
    private Integer extendFlag;

    /**
     * 散key
     */
    private String key;

    /**
     * 问题件小类
     */
    private String issueSmallType;

    /**
     * 上报至其他地方
     */
    private String issueDistr;

    /**
     * 最后处理人
     */
    private String lastDealUserCode;
    private String lastDealUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSourceManageCode() {
        return sourceManageCode;
    }

    public void setSourceManageCode(String sourceManageCode) {
        this.sourceManageCode = sourceManageCode;
    }

    public String getSourceManageName() {
        return sourceManageName;
    }

    public void setSourceManageName(String sourceManageName) {
        this.sourceManageName = sourceManageName;
    }

    public String getSourceRegionCode() {
        return sourceRegionCode;
    }

    public void setSourceRegionCode(String sourceRegionCode) {
        this.sourceRegionCode = sourceRegionCode;
    }

    public String getSourceRegionName() {
        return sourceRegionName;
    }

    public void setSourceRegionName(String sourceRegionName) {
        this.sourceRegionName = sourceRegionName;
    }

    public String getSourceBranchCode() {
        return sourceBranchCode;
    }

    public void setSourceBranchCode(String sourceBranchCode) {
        this.sourceBranchCode = sourceBranchCode;
    }

    public String getSourceBranchName() {
        return sourceBranchName;
    }

    public void setSourceBranchName(String sourceBranchName) {
        this.sourceBranchName = sourceBranchName;
    }

    public String getSourceOrgCode() {
        return sourceOrgCode;
    }

    public void setSourceOrgCode(String sourceOrgCode) {
        this.sourceOrgCode = sourceOrgCode;
    }

    public String getSourceOrgName() {
        return sourceOrgName;
    }

    public void setSourceOrgName(String sourceOrgName) {
        this.sourceOrgName = sourceOrgName;
    }

    public String getEffectiveTypeCode() {
        return effectiveTypeCode;
    }

    public void setEffectiveTypeCode(String effectiveTypeCode) {
        this.effectiveTypeCode = effectiveTypeCode;
    }

    public String getEffectiveTypeName() {
        return effectiveTypeName;
    }

    public void setEffectiveTypeName(String effectiveTypeName) {
        this.effectiveTypeName = effectiveTypeName;
    }

    public String getSenderCustomerCode() {
        return senderCustomerCode;
    }

    public void setSenderCustomerCode(String senderCustomerCode) {
        this.senderCustomerCode = senderCustomerCode;
    }

    public String getSenderCustomerName() {
        return senderCustomerName;
    }

    public void setSenderCustomerName(String senderCustomerName) {
        this.senderCustomerName = senderCustomerName;
    }

    public String getTakingEmpCode() {
        return takingEmpCode;
    }

    public void setTakingEmpCode(String takingEmpCode) {
        this.takingEmpCode = takingEmpCode;
    }

    public String getTakingEmpName() {
        return takingEmpName;
    }

    public void setTakingEmpName(String takingEmpName) {
        this.takingEmpName = takingEmpName;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getManageCode() {
        return manageCode;
    }

    public void setManageCode(String manageCode) {
        this.manageCode = manageCode;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public String getRecManageCode() {
        return recManageCode;
    }

    public void setRecManageCode(String recManageCode) {
        this.recManageCode = recManageCode;
    }

    public String getRecManageName() {
        return recManageName;
    }

    public void setRecManageName(String recManageName) {
        this.recManageName = recManageName;
    }

    public String getRecRegionCode() {
        return recRegionCode;
    }

    public void setRecRegionCode(String recRegionCode) {
        this.recRegionCode = recRegionCode;
    }

    public String getRecRegionName() {
        return recRegionName;
    }

    public void setRecRegionName(String recRegionName) {
        this.recRegionName = recRegionName;
    }

    public String getRecBranchCode() {
        return recBranchCode;
    }

    public void setRecBranchCode(String recBranchCode) {
        this.recBranchCode = recBranchCode;
    }

    public String getRecBranchName() {
        return recBranchName;
    }

    public void setRecBranchName(String recBranchName) {
        this.recBranchName = recBranchName;
    }

    public String getRecOrgCode() {
        return recOrgCode;
    }

    public void setRecOrgCode(String recOrgCode) {
        this.recOrgCode = recOrgCode;
    }

    public String getRecOrgName() {
        return recOrgName;
    }

    public void setRecOrgName(String recOrgName) {
        this.recOrgName = recOrgName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDealManageCode() {
        return dealManageCode;
    }

    public void setDealManageCode(String dealManageCode) {
        this.dealManageCode = dealManageCode;
    }

    public String getDealManageName() {
        return dealManageName;
    }

    public void setDealManageName(String dealManageName) {
        this.dealManageName = dealManageName;
    }

    public String getDealRegionCode() {
        return dealRegionCode;
    }

    public void setDealRegionCode(String dealRegionCode) {
        this.dealRegionCode = dealRegionCode;
    }

    public String getDealRegionName() {
        return dealRegionName;
    }

    public void setDealRegionName(String dealRegionName) {
        this.dealRegionName = dealRegionName;
    }

    public String getDealBranchCode() {
        return dealBranchCode;
    }

    public void setDealBranchCode(String dealBranchCode) {
        this.dealBranchCode = dealBranchCode;
    }

    public String getDealBranchName() {
        return dealBranchName;
    }

    public void setDealBranchName(String dealBranchName) {
        this.dealBranchName = dealBranchName;
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

    public String getLastDealOrgCode() {
        return lastDealOrgCode;
    }

    public void setLastDealOrgCode(String lastDealOrgCode) {
        this.lastDealOrgCode = lastDealOrgCode;
    }

    public String getLastDealOrgName() {
        return lastDealOrgName;
    }

    public void setLastDealOrgName(String lastDealOrgName) {
        this.lastDealOrgName = lastDealOrgName;
    }

    public String getLastDealTime() {
        return lastDealTime;
    }

    public void setLastDealTime(String lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    public Integer getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Integer dealNumber) {
        this.dealNumber = dealNumber;
    }

    public Date getIssueCreateTime() {
        return issueCreateTime;
    }

    public void setIssueCreateTime(Date issueCreateTime) {
        this.issueCreateTime = issueCreateTime;
    }

    public Date getJobTime() {
        return jobTime;
    }

    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public Integer getExtendFlag() {
        return extendFlag;
    }

    public void setExtendFlag(Integer extendFlag) {
        this.extendFlag = extendFlag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIssueSmallType() {
        return issueSmallType;
    }

    public void setIssueSmallType(String issueSmallType) {
        this.issueSmallType = issueSmallType;
    }

    public String getIssueDistr() {
        return issueDistr;
    }

    public void setIssueDistr(String issueDistr) {
        this.issueDistr = issueDistr;
    }

    public String getLastDealUserCode() {
        return lastDealUserCode;
    }

    public void setLastDealUserCode(String lastDealUserCode) {
        this.lastDealUserCode = lastDealUserCode;
    }

    public String getLastDealUserName() {
        return lastDealUserName;
    }

    public void setLastDealUserName(String lastDealUserName) {
        this.lastDealUserName = lastDealUserName;
    }
}
