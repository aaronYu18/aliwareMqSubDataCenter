package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

/**
 * 运单详情接口
 * Created by bin on 2015/11/26.
 */
public class JGMailNoInfoResult extends BaseEntity {
    private static final long serialVersionUID = -1848880055382665304L;
    private String airForbidden;                                //航空禁运标识
    private String billSourceOrgCode;                           // 面单发放地编号
    private String billSourceOrgName;                           // 面单发放地名称
    private String businessDomainCode;                          // 业务范围
    private String businessTypeCode;                            //业务类型
    private String collectWeight;                               // 揽收重量
    private String createTime;                                  // 创建时间
    private String customerTransferFee;                         // 客户运费
    private String deliveryEmpCode;                             // 派件员编号
    private String deliveryEmpName;                             // 派件员姓名
    private String deliveryOrgCode;                             // 派件网点编号
    private String deliveryOrgName;                             // 派件网点名称
    private String deliveryTime;                                // 派件时间
    private String desCity;                                     // 目的城市编号
    private String desCityName;                                 // 目的城市名称
    private String desCountry;                                  // 目的区县编号
    private String desCountryName;                              // 目的区县名称
    private String desOrgCode;                                  //目的网点
    private String desOrgName;                                  // 目的网点名称
    private String desProv;                                     // 目的省份
    private String desProvName;                                 // 目的省份名称
    private String dfFee;                                       // 到付费用
    private String dsAmt;                                       // 代收费用
    private String effectiveTypeCode;                           // 时效类型编号
    private String effectiveTypeName;                           // 时效名称
    private String exchFlag;                                    // 换单标识
    private String expType;                                     // 快件类型
    private String expTypeName;                                 // 快件类型名称
    private String expressContentCode;                          // 快件内容
    private String expressContentName;                          // 快件内容名称
    private String firstOpTime;                                 // 首次操作时间
    private String flag;
    private String inputType;                                   // 最后操作码
    private String lastAuxOpCode;                               //录单类型
    private String lastOpCode;                                  //  最后操作网点
    private String lastOpOrgCode;                               //最后操作网点
    private String lastOpTime;                                  // 最后操作时间
    private String maxBranchWeight;                             // 分公司最大重量
    private String maxTransferWeight;                           // 转运中心最大重量
    private String modifyTime;                                  // 修改时间
    private String payType;                                     // 付款方式
    private String receiverCustomerCode;                        // 收件客户编号
    private String receiverCustomerName;                        // 收件客户名称
    private String receiverDetailAddress;                       // 收件人详细地址
    private String receiverName;                                // 收件人
    private String receiverTel;                                 // 收件人座机
    private String remark;                                      // 备注
    private String sendTime;                                    // 寄件时间
    private String senderCity;                                  // 寄件城市
    private String senderCityName;                              // 寄件城市名称
    private String senderCustomerCode;                          // 寄件客户编号
    private String senderCustomerName;                          // 寄件客户名称
    private String senderDetailAddress;                         // 寄件详细地址
    private String senderName;                                  // 寄件人
    private String senderProv;                                  // 寄件省份
    private String senderProvName;                              // 寄件省份名称
    private String senderTel;                                   // 寄件人座机
    private String senderTown;                                  // 寄件人区县
    private String senderTownName;                              // 寄件人区县名称
    private String siginoffOrgCode;                             // 签收网点编码
    private String siginoffOrgName;                             // 签收网点名称
    private String smsFlag;                                     // 是否要求短信回复
    private String sourceOrgCode;                               // 始发网点
    private String sourceOrgName;                               // 始发网点名称
    private String status;                                      // 状态
    private String statusName;                                  // 状态名称
    private String takingEmpCode;                               // 揽收人
    private String takingEmpName;                               // 揽收名姓名
    private String waybillInputFlg;                             // 录单标识
    private String waybillInputWeight;                          // 录单重量
    private String waybillNo;                                   // 单号


    public String getAirForbidden() {
        return airForbidden;
    }

    public void setAirForbidden(String airForbidden) {
        this.airForbidden = airForbidden;
    }

    public String getBillSourceOrgCode() {
        return billSourceOrgCode;
    }

    public void setBillSourceOrgCode(String billSourceOrgCode) {
        this.billSourceOrgCode = billSourceOrgCode;
    }

    public String getBillSourceOrgName() {
        return billSourceOrgName;
    }

    public void setBillSourceOrgName(String billSourceOrgName) {
        this.billSourceOrgName = billSourceOrgName;
    }

    public String getBusinessDomainCode() {
        return businessDomainCode;
    }

    public void setBusinessDomainCode(String businessDomainCode) {
        this.businessDomainCode = businessDomainCode;
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getCollectWeight() {
        return collectWeight;
    }

    public void setCollectWeight(String collectWeight) {
        this.collectWeight = collectWeight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerTransferFee() {
        return customerTransferFee;
    }

    public void setCustomerTransferFee(String customerTransferFee) {
        this.customerTransferFee = customerTransferFee;
    }

    public String getDeliveryEmpCode() {
        return deliveryEmpCode;
    }

    public void setDeliveryEmpCode(String deliveryEmpCode) {
        this.deliveryEmpCode = deliveryEmpCode;
    }

    public String getDeliveryEmpName() {
        return deliveryEmpName;
    }

    public void setDeliveryEmpName(String deliveryEmpName) {
        this.deliveryEmpName = deliveryEmpName;
    }

    public String getDeliveryOrgCode() {
        return deliveryOrgCode;
    }

    public void setDeliveryOrgCode(String deliveryOrgCode) {
        this.deliveryOrgCode = deliveryOrgCode;
    }

    public String getDeliveryOrgName() {
        return deliveryOrgName;
    }

    public void setDeliveryOrgName(String deliveryOrgName) {
        this.deliveryOrgName = deliveryOrgName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDesCity() {
        return desCity;
    }

    public void setDesCity(String desCity) {
        this.desCity = desCity;
    }

    public String getDesCityName() {
        return desCityName;
    }

    public void setDesCityName(String desCityName) {
        this.desCityName = desCityName;
    }

    public String getDesCountry() {
        return desCountry;
    }

    public void setDesCountry(String desCountry) {
        this.desCountry = desCountry;
    }

    public String getDesCountryName() {
        return desCountryName;
    }

    public void setDesCountryName(String desCountryName) {
        this.desCountryName = desCountryName;
    }

    public String getDesOrgCode() {
        return desOrgCode;
    }

    public void setDesOrgCode(String desOrgCode) {
        this.desOrgCode = desOrgCode;
    }

    public String getDesOrgName() {
        return desOrgName;
    }

    public void setDesOrgName(String desOrgName) {
        this.desOrgName = desOrgName;
    }

    public String getDesProv() {
        return desProv;
    }

    public void setDesProv(String desProv) {
        this.desProv = desProv;
    }

    public String getDesProvName() {
        return desProvName;
    }

    public void setDesProvName(String desProvName) {
        this.desProvName = desProvName;
    }

    public String getDfFee() {
        return dfFee;
    }

    public void setDfFee(String dfFee) {
        this.dfFee = dfFee;
    }

    public String getDsAmt() {
        return dsAmt;
    }

    public void setDsAmt(String dsAmt) {
        this.dsAmt = dsAmt;
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

    public String getExchFlag() {
        return exchFlag;
    }

    public void setExchFlag(String exchFlag) {
        this.exchFlag = exchFlag;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpTypeName() {
        return expTypeName;
    }

    public void setExpTypeName(String expTypeName) {
        this.expTypeName = expTypeName;
    }

    public String getExpressContentCode() {
        return expressContentCode;
    }

    public void setExpressContentCode(String expressContentCode) {
        this.expressContentCode = expressContentCode;
    }

    public String getExpressContentName() {
        return expressContentName;
    }

    public void setExpressContentName(String expressContentName) {
        this.expressContentName = expressContentName;
    }

    public String getFirstOpTime() {
        return firstOpTime;
    }

    public void setFirstOpTime(String firstOpTime) {
        this.firstOpTime = firstOpTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getLastAuxOpCode() {
        return lastAuxOpCode;
    }

    public void setLastAuxOpCode(String lastAuxOpCode) {
        this.lastAuxOpCode = lastAuxOpCode;
    }

    public String getLastOpCode() {
        return lastOpCode;
    }

    public void setLastOpCode(String lastOpCode) {
        this.lastOpCode = lastOpCode;
    }

    public String getLastOpOrgCode() {
        return lastOpOrgCode;
    }

    public void setLastOpOrgCode(String lastOpOrgCode) {
        this.lastOpOrgCode = lastOpOrgCode;
    }

    public String getLastOpTime() {
        return lastOpTime;
    }

    public void setLastOpTime(String lastOpTime) {
        this.lastOpTime = lastOpTime;
    }

    public String getMaxBranchWeight() {
        return maxBranchWeight;
    }

    public void setMaxBranchWeight(String maxBranchWeight) {
        this.maxBranchWeight = maxBranchWeight;
    }

    public String getMaxTransferWeight() {
        return maxTransferWeight;
    }

    public void setMaxTransferWeight(String maxTransferWeight) {
        this.maxTransferWeight = maxTransferWeight;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getReceiverCustomerCode() {
        return receiverCustomerCode;
    }

    public void setReceiverCustomerCode(String receiverCustomerCode) {
        this.receiverCustomerCode = receiverCustomerCode;
    }

    public String getReceiverCustomerName() {
        return receiverCustomerName;
    }

    public void setReceiverCustomerName(String receiverCustomerName) {
        this.receiverCustomerName = receiverCustomerName;
    }

    public String getReceiverDetailAddress() {
        return receiverDetailAddress;
    }

    public void setReceiverDetailAddress(String receiverDetailAddress) {
        this.receiverDetailAddress = receiverDetailAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderCityName() {
        return senderCityName;
    }

    public void setSenderCityName(String senderCityName) {
        this.senderCityName = senderCityName;
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

    public String getSenderDetailAddress() {
        return senderDetailAddress;
    }

    public void setSenderDetailAddress(String senderDetailAddress) {
        this.senderDetailAddress = senderDetailAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderProv() {
        return senderProv;
    }

    public void setSenderProv(String senderProv) {
        this.senderProv = senderProv;
    }

    public String getSenderProvName() {
        return senderProvName;
    }

    public void setSenderProvName(String senderProvName) {
        this.senderProvName = senderProvName;
    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderTownName() {
        return senderTownName;
    }

    public void setSenderTownName(String senderTownName) {
        this.senderTownName = senderTownName;
    }

    public String getSiginoffOrgCode() {
        return siginoffOrgCode;
    }

    public void setSiginoffOrgCode(String siginoffOrgCode) {
        this.siginoffOrgCode = siginoffOrgCode;
    }

    public String getSiginoffOrgName() {
        return siginoffOrgName;
    }

    public void setSiginoffOrgName(String siginoffOrgName) {
        this.siginoffOrgName = siginoffOrgName;
    }

    public String getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(String smsFlag) {
        this.smsFlag = smsFlag;
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

    public String getWaybillInputFlg() {
        return waybillInputFlg;
    }

    public void setWaybillInputFlg(String waybillInputFlg) {
        this.waybillInputFlg = waybillInputFlg;
    }

    public String getWaybillInputWeight() {
        return waybillInputWeight;
    }

    public void setWaybillInputWeight(String waybillInputWeight) {
        this.waybillInputWeight = waybillInputWeight;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
}
