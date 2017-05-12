package com.courier.commons.model;

import com.courier.commons.entity.BaseEntity;

import java.util.Date;

/**
 * Created by beyond on 2016/5/16.
 */
public class RealNameSystemRequest extends BaseEntity {

    private String pseudocodeOfID;      //赋码
    private String name;                //寄件人姓名    必填
    private String typeOfID;            //寄件人ID类型   必填
    private String numberOfID;          //寄件人有效证件号码   必填
    private String mobileNumber;        //移动电话号码
    private String phone;               //固话
    private String provinceOfAddress;   //寄件人地址  必填
    private String cityOfAddress;       //寄件人城市code 必填
    private String countyOfAddress;     //寄件人区县code 必填
    private String otherOfAddress;      //寄件人详细地址 必填
    private String codeOfWeixin;        //寄件人微信
    private String typeOfUser;          //寄件人 用户类型
    private String sex;                 //寄件人

    private String ydh;             //运单号
    private Date jjsj;              //寄件时间
    private String jjgs;            //寄件公司
    private String sjdz;            //收件地址
    private String sjgs;            //收件公司
    private String sjr;             //收件人 被寄人
    private String sjdh;            //收件电话 被寄人
    private String sjy;             //收件员

    private String mdd;             //目的地
    private String jfkhbm;          //寄方客户编码
    private String fkfs;            //付款方式
    private String dsdk;            //代收货款
    private String tjw;             //托寄物
    private String jfzl;            //计费重量
    private String js;              //件数
    private String yf;              //运费
    private String mdddq;           //目的地地区 区县code
    private String mddcs;           //目的地城市
    private String sjrzjhm;         //收件人证件号码
    private String sjrzjzl;         //收件人证件种类

    private String jd;              //寄件位置经度
    private String wd;              //寄件位置纬度
    private String kdwddm;          //当前快递员网点
    private String ktysjh;          //快递员手机号
    private String type;            //类型 全国：0 浙江：1
    private String pic;
    private Long picLen;            //图片1长度
    private String pic2;
    private Long picLen2;           //图片2长度
    private String ext;             //预留字段

    public String getPseudocodeOfID() {
        return pseudocodeOfID;
    }

    public void setPseudocodeOfID(String pseudocodeOfID) {
        this.pseudocodeOfID = pseudocodeOfID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfID() {
        return typeOfID;
    }

    public void setTypeOfID(String typeOfID) {
        this.typeOfID = typeOfID;
    }

    public String getNumberOfID() {
        return numberOfID;
    }

    public void setNumberOfID(String numberOfID) {
        this.numberOfID = numberOfID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceOfAddress() {
        return provinceOfAddress;
    }

    public void setProvinceOfAddress(String provinceOfAddress) {
        this.provinceOfAddress = provinceOfAddress;
    }

    public String getCityOfAddress() {
        return cityOfAddress;
    }

    public void setCityOfAddress(String cityOfAddress) {
        this.cityOfAddress = cityOfAddress;
    }

    public String getCountyOfAddress() {
        return countyOfAddress;
    }

    public void setCountyOfAddress(String countyOfAddress) {
        this.countyOfAddress = countyOfAddress;
    }

    public String getOtherOfAddress() {
        return otherOfAddress;
    }

    public void setOtherOfAddress(String otherOfAddress) {
        this.otherOfAddress = otherOfAddress;
    }

    public String getCodeOfWeixin() {
        return codeOfWeixin;
    }

    public void setCodeOfWeixin(String codeOfWeixin) {
        this.codeOfWeixin = codeOfWeixin;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYdh() {
        return ydh;
    }

    public void setYdh(String ydh) {
        this.ydh = ydh;
    }

    public Date getJjsj() {
        return jjsj;
    }

    public void setJjsj(Date jjsj) {
        this.jjsj = jjsj;
    }

    public String getJjgs() {
        return jjgs;
    }

    public void setJjgs(String jjgs) {
        this.jjgs = jjgs;
    }

    public String getSjdz() {
        return sjdz;
    }

    public void setSjdz(String sjdz) {
        this.sjdz = sjdz;
    }

    public String getSjgs() {
        return sjgs;
    }

    public void setSjgs(String sjgs) {
        this.sjgs = sjgs;
    }

    public String getSjr() {
        return sjr;
    }

    public void setSjr(String sjr) {
        this.sjr = sjr;
    }

    public String getSjdh() {
        return sjdh;
    }

    public void setSjdh(String sjdh) {
        this.sjdh = sjdh;
    }

    public String getSjy() {
        return sjy;
    }

    public void setSjy(String sjy) {
        this.sjy = sjy;
    }

    public String getMdd() {
        return mdd;
    }

    public void setMdd(String mdd) {
        this.mdd = mdd;
    }

    public String getJfkhbm() {
        return jfkhbm;
    }

    public void setJfkhbm(String jfkhbm) {
        this.jfkhbm = jfkhbm;
    }

    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    public String getDsdk() {
        return dsdk;
    }

    public void setDsdk(String dsdk) {
        this.dsdk = dsdk;
    }

    public String getTjw() {
        return tjw;
    }

    public void setTjw(String tjw) {
        this.tjw = tjw;
    }

    public String getJfzl() {
        return jfzl;
    }

    public void setJfzl(String jfzl) {
        this.jfzl = jfzl;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf;
    }

    public String getMdddq() {
        return mdddq;
    }

    public void setMdddq(String mdddq) {
        this.mdddq = mdddq;
    }

    public String getMddcs() {
        return mddcs;
    }

    public void setMddcs(String mddcs) {
        this.mddcs = mddcs;
    }

    public String getSjrzjhm() {
        return sjrzjhm;
    }

    public void setSjrzjhm(String sjrzjhm) {
        this.sjrzjhm = sjrzjhm;
    }

    public String getSjrzjzl() {
        return sjrzjzl;
    }

    public void setSjrzjzl(String sjrzjzl) {
        this.sjrzjzl = sjrzjzl;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getKdwddm() {
        return kdwddm;
    }

    public void setKdwddm(String kdwddm) {
        this.kdwddm = kdwddm;
    }

    public String getKtysjh() {
        return ktysjh;
    }

    public void setKtysjh(String ktysjh) {
        this.ktysjh = ktysjh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getPicLen() {
        return picLen;
    }

    public void setPicLen(Long picLen) {
        this.picLen = picLen;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public Long getPicLen2() {
        return picLen2;
    }

    public void setPicLen2(Long picLen2) {
        this.picLen2 = picLen2;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
