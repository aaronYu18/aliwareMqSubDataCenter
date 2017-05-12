package com.courier.commons.model.jinGang;

/**
 * Created by beyond on 2016/5/19.
 */
public class JGVerificationInfo {
    private String pseudocodeOfID ;//赋码
    private String sfzhm;               //身份证号码
    private String xm;                  //实名制姓名
    private String zzlx;                //证件类型 寄件人ID类型(枚举CertificateType)   必填
    private String xb;                  //性别

    private String sjhm;                //手机号码
    private String userId;              //用户序列号
    private String phone;               //固话
    private String txt;                 //预留字段

    private Integer httpStatus;

    public String getPseudocodeOfID() {
        return pseudocodeOfID;
    }

    public void setPseudocodeOfID(String pseudocodeOfID) {
        this.pseudocodeOfID = pseudocodeOfID;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getZzlx() {
        return zzlx;
    }

    public void setZzlx(String zzlx) {
        this.zzlx = zzlx;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }
}
