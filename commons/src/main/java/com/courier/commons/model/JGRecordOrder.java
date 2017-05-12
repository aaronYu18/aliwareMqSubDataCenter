package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * 录单接口类javaBean
 * Created by beyond on 2016/7/1.
 */
public class JGRecordOrder  extends BaseEntity {
    private String id="";
    //add xk 2013-09-09 新增订单号
    private String orderNo;
    // 运单号码
    private String waybillNo;
    // 单据类型
    private String billType;
    // *始发网点
    private String sourceOrgCode;
    // *寄件时间
    private String sendTime;
    // *收件员编号
    private String takingEmpCode;
    // *收件员姓名
    private String takingEmpName;
    // *寄件客户编号
    private String senderCustomerCode;
    // *寄件客户名称
    private String senderCustomerName;
    // 寄件人姓名
    private String senderName="";
    // 寄件公司名称
    private String senderCorp;
    // 寄件地址省份
    private String senderProv;
    // 寄件地址市县
    private String senderCity;
    // 寄件地址区镇
    private String senderTown;
    // 寄件地址附加
    private String senderApp;
    // 寄件人手机
    private String senderMobilePhone;
    // 寄件人固话
    private String senderTel;
    // 托寄物内容
    private String goods;
    // 托寄物数量
    private Integer goodsNumber;
    // 收件客户编号
    private String receiverCustomerCode;
    // 收件人姓名
    private String receiverName="";
    // 收件公司名称
    private String receiverCorp;
    // 收件地址省份
    private String receiverProv;
    // 收件地址市县
    private String receiverCity;
    // 收件地址区镇
    private String receiverTown;
    // 收件地址附加
    private String receiverApp;
    // 收件人手机
    private String receiverMobilePhone;
    // 收件人固话
    private String receiverTel;
    // *目的网点 notify 调用三段码会补上
    private String desOrgCode;
    // *业务类型
    private String businessTypeCode;
    // *快件内容
    private String expressContentCode;
    // *时效
    private String effectiveTypeCode;
    // *业务范围
    private String businessDomainCode;
    // *付款方式
    private String payType;
    // 输入重量
    private Double inputWeight;
    // 长
    private Double pkgLength;
    // 宽
    private Double pkgWidth;
    // 高
    private Double pkgHeight;
    // 体积
    private Double volume;
    // *是否要求短信回复
    private String smsFlag;
    // 托寄物申明价值
    private Double goodsValue;
    // 客户费用总计
    private Double toatalAmt;
    // 客户运费
    private Double transAmt;
    // 增值服务费小计
    private Double incrementAmt;
    // 收件人签名
    private String receiverSignoff;
    // 收件人证件号
    private String receiverIdNo;
    // 备注
    private String remark;
    // 上传时间
    private String uploadTime;
    // 状态
    private String status;
    // *转换状态
    private String transferStatus;
    // *关联ID,记录被修改/删除后生成的新纪录保留原记录的ID
    private String refId;
    // 创建时间
    private String createTime;
    // 创建人编号
    private String createUserCode;
    // 创建人
    private String createUserName;
    // 创建组织编号
    private String createOrgCode;
    // 修改时间
    private String modifyTime;
    // 修改人编号
    private String modifyUserCode;
    // 修改人
    private String modifyUserName;
    // 修改组织编号
    private String modifyOrgCode;
    // 辅助操作码
    private String auxOpCode;
    // 操作码
    private Integer opCode;
    private Set<JGExpIncrementService> expIncrementServices = new HashSet<JGExpIncrementService>(0);
    //数据源
    private String datasource;
    private String uploadTimeStr;
    private String createTimeStr;
    private String modifyTimeStr;
    //是否支持前置数据库
    private String isSupportFrontDb;
    //时间后三位，毫秒数，供OCC使用
    private String milliSecond;
    private String orgName;//录单网点名称
    private String gisStatus;//GIS匹配状况
    private String isGisSuccess;

    public String getIsGisSuccess() {
        return isGisSuccess;
    }

    public void setIsGisSuccess(String isGisSuccess) {
        this.isGisSuccess = isGisSuccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getSourceOrgCode() {
        return sourceOrgCode;
    }

    public void setSourceOrgCode(String sourceOrgCode) {
        this.sourceOrgCode = sourceOrgCode;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderCorp() {
        return senderCorp;
    }

    public void setSenderCorp(String senderCorp) {
        this.senderCorp = senderCorp;
    }

    public String getSenderProv() {
        return senderProv;
    }

    public void setSenderProv(String senderProv) {
        this.senderProv = senderProv;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderApp() {
        return senderApp;
    }

    public void setSenderApp(String senderApp) {
        this.senderApp = senderApp;
    }

    public String getSenderMobilePhone() {
        return senderMobilePhone;
    }

    public void setSenderMobilePhone(String senderMobilePhone) {
        this.senderMobilePhone = senderMobilePhone;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getReceiverCustomerCode() {
        return receiverCustomerCode;
    }

    public void setReceiverCustomerCode(String receiverCustomerCode) {
        this.receiverCustomerCode = receiverCustomerCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCorp() {
        return receiverCorp;
    }

    public void setReceiverCorp(String receiverCorp) {
        this.receiverCorp = receiverCorp;
    }

    public String getReceiverProv() {
        return receiverProv;
    }

    public void setReceiverProv(String receiverProv) {
        this.receiverProv = receiverProv;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverTown() {
        return receiverTown;
    }

    public void setReceiverTown(String receiverTown) {
        this.receiverTown = receiverTown;
    }

    public String getReceiverApp() {
        return receiverApp;
    }

    public void setReceiverApp(String receiverApp) {
        this.receiverApp = receiverApp;
    }

    public String getReceiverMobilePhone() {
        return receiverMobilePhone;
    }

    public void setReceiverMobilePhone(String receiverMobilePhone) {
        this.receiverMobilePhone = receiverMobilePhone;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getDesOrgCode() {
        return desOrgCode;
    }

    public void setDesOrgCode(String desOrgCode) {
        this.desOrgCode = desOrgCode;
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getExpressContentCode() {
        return expressContentCode;
    }

    public void setExpressContentCode(String expressContentCode) {
        this.expressContentCode = expressContentCode;
    }

    public String getEffectiveTypeCode() {
        return effectiveTypeCode;
    }

    public void setEffectiveTypeCode(String effectiveTypeCode) {
        this.effectiveTypeCode = effectiveTypeCode;
    }

    public String getBusinessDomainCode() {
        return businessDomainCode;
    }

    public void setBusinessDomainCode(String businessDomainCode) {
        this.businessDomainCode = businessDomainCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Double getInputWeight() {
        return inputWeight;
    }

    public void setInputWeight(Double inputWeight) {
        this.inputWeight = inputWeight;
    }

    public Double getPkgLength() {
        return pkgLength;
    }

    public void setPkgLength(Double pkgLength) {
        this.pkgLength = pkgLength;
    }

    public Double getPkgWidth() {
        return pkgWidth;
    }

    public void setPkgWidth(Double pkgWidth) {
        this.pkgWidth = pkgWidth;
    }

    public Double getPkgHeight() {
        return pkgHeight;
    }

    public void setPkgHeight(Double pkgHeight) {
        this.pkgHeight = pkgHeight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(String smsFlag) {
        this.smsFlag = smsFlag;
    }

    public Double getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(Double goodsValue) {
        this.goodsValue = goodsValue;
    }

    public Double getToatalAmt() {
        return toatalAmt;
    }

    public void setToatalAmt(Double toatalAmt) {
        this.toatalAmt = toatalAmt;
    }

    public Double getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Double transAmt) {
        this.transAmt = transAmt;
    }

    public Double getIncrementAmt() {
        return incrementAmt;
    }

    public void setIncrementAmt(Double incrementAmt) {
        this.incrementAmt = incrementAmt;
    }

    public String getReceiverSignoff() {
        return receiverSignoff;
    }

    public void setReceiverSignoff(String receiverSignoff) {
        this.receiverSignoff = receiverSignoff;
    }

    public String getReceiverIdNo() {
        return receiverIdNo;
    }

    public void setReceiverIdNo(String receiverIdNo) {
        this.receiverIdNo = receiverIdNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
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

    public String getAuxOpCode() {
        return auxOpCode;
    }

    public void setAuxOpCode(String auxOpCode) {
        this.auxOpCode = auxOpCode;
    }

    public Integer getOpCode() {
        return opCode;
    }

    public void setOpCode(Integer opCode) {
        this.opCode = opCode;
    }

    public Set<JGExpIncrementService> getExpIncrementServices() {
        return expIncrementServices;
    }

    public void setExpIncrementServices(Set<JGExpIncrementService> expIncrementServices) {
        this.expIncrementServices = expIncrementServices;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getUploadTimeStr() {
        return uploadTimeStr;
    }

    public void setUploadTimeStr(String uploadTimeStr) {
        this.uploadTimeStr = uploadTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getModifyTimeStr() {
        return modifyTimeStr;
    }

    public void setModifyTimeStr(String modifyTimeStr) {
        this.modifyTimeStr = modifyTimeStr;
    }

    public String getIsSupportFrontDb() {
        return isSupportFrontDb;
    }

    public void setIsSupportFrontDb(String isSupportFrontDb) {
        this.isSupportFrontDb = isSupportFrontDb;
    }

    public String getMilliSecond() {
        return milliSecond;
    }

    public void setMilliSecond(String milliSecond) {
        this.milliSecond = milliSecond;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getGisStatus() {
        return gisStatus;
    }

    public void setGisStatus(String gisStatus) {
        this.gisStatus = gisStatus;
    }
}
