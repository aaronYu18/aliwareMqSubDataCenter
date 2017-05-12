package com.courier.commons.vModel;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/3/27.
 */
public class VDeliveryOrder extends BaseEntity {
    private static final long serialVersionUID = 7737180193186222406L;
    private String id;
    private String waybillNo;                            //    	面单号
    private String receiverName;                        //    	收件人姓名
    private String receiverMobile;                        //    	电话
    private String receiverProv;                        //    	省份
    private String receiverCity;                        //    	城市
    private String receiverTown;                        //    	区
    private String receiverApp;                            //    	详细地址
    private String toPayment;                            //    	货到付款金额
    private String collectionPay;                        //    	代收货款金额
    private String incrementType;                        //    	增值服务类型()
    private String empCode;                              //收派员编号 快递员
    private String empName;                             //快递员姓名

    private String orgCode;                             //操作网点
    private int opCode;                                 //操作码
    private String auxOpCode;                           //辅助操作码
    private String status;                              //状态
    private String createTime;                          //创建时间

    private String createUserCode;                      //创建人编号
    private String createUserName;                      //创建人

    private String createOrgCode;                       //创建人组织编号

    private String createTerminal;                      //创建终端

    private String deviceType;                          //操作设备类型

    private String refId;                               //关联ID
    private String modifyTime;                          //修改时间

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
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

    public String getToPayment() {
        return toPayment;
    }

    public void setToPayment(String toPayment) {
        this.toPayment = toPayment;
    }

    public String getCollectionPay() {
        return collectionPay;
    }

    public void setCollectionPay(String collectionPay) {
        this.collectionPay = collectionPay;
    }

    public String getIncrementType() {
        return incrementType;
    }

    public void setIncrementType(String incrementType) {
        this.incrementType = incrementType;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getOpCode() {
        return opCode;
    }

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public String getAuxOpCode() {
        return auxOpCode;
    }

    public void setAuxOpCode(String auxOpCode) {
        this.auxOpCode = auxOpCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreateTerminal() {
        return createTerminal;
    }

    public void setCreateTerminal(String createTerminal) {
        this.createTerminal = createTerminal;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
