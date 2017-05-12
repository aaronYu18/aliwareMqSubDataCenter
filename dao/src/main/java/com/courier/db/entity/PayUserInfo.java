package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2016/3/31.
 */
@Table(name = "payUserInfo")
public class PayUserInfo extends BaseEntity{
    private static final long serialVersionUID = 7392580940316829120L;
    private long id;
    private Long userId;
    private String jobNo;
    private String orgCode;
    private String avatar;
    private String payUserId;
    private String channelType;
    private Byte payUserType;
    private String payUserStatus;
    private String firmName;
    private String realName;
    private String email;
    private String certNo;
    private Byte gender;
    private String phone;
    private String mobile;
    private Boolean isCertified;
    private Boolean isBankAuth;
    private Boolean isIdAuth;
    private Boolean isMobileAuth;
    private Boolean isLicenceAuth;
    private Byte certType;
    private String deliverPhone;
    private String deliverMobile;
    private String deliverFullname;
    private String province;
    private String city;
    private String area;
    private String address;
    private String zip;
    private String deliverProvince;
    private String deliverCity;
    private String deliverArea;
    private Byte defaultDeliverAddress;
    private String addressCode;
    private Boolean isCertifyGradeA;
    private Boolean isStudentCertified;
    private String alipayUserId;
    private String birthday;
    private String nickName;
    private Boolean status = true;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }


    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column
    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
    @Column
    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }
    @Column
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    @Column
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    @Column
    public String getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }
    @Column
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    @Column
    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    @Column
    public Byte getCertType() {
        return certType;
    }

    public void setCertType(Byte certType) {
        this.certType = certType;
    }
    @Column
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column
    public Byte getDefaultDeliverAddress() {
        return defaultDeliverAddress;
    }

    public void setDefaultDeliverAddress(Byte defaultDeliverAddress) {
        this.defaultDeliverAddress = defaultDeliverAddress;
    }
    @Column
    public String getDeliverArea() {
        return deliverArea;
    }

    public void setDeliverArea(String deliverArea) {
        this.deliverArea = deliverArea;
    }
    @Column
    public String getDeliverCity() {
        return deliverCity;
    }

    public void setDeliverCity(String deliverCity) {
        this.deliverCity = deliverCity;
    }
    @Column
    public String getDeliverFullname() {
        return deliverFullname;
    }

    public void setDeliverFullname(String deliverFullname) {
        this.deliverFullname = deliverFullname;
    }
    @Column
    public String getDeliverMobile() {
        return deliverMobile;
    }

    public void setDeliverMobile(String deliverMobile) {
        this.deliverMobile = deliverMobile;
    }
    @Column
    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }
    @Column
    public String getDeliverProvince() {
        return deliverProvince;
    }

    public void setDeliverProvince(String deliverProvince) {
        this.deliverProvince = deliverProvince;
    }
    @Column
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column
    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }
    @Column
    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }
    @Column
    public Boolean getIsBankAuth() {
        return isBankAuth;
    }

    public void setIsBankAuth(Boolean isBankAuth) {
        this.isBankAuth = isBankAuth;
    }
    @Column
    public Boolean getIsCertified() {
        return isCertified;
    }

    public void setIsCertified(Boolean isCertified) {
        this.isCertified = isCertified;
    }
    @Column
    public Boolean getIsIdAuth() {
        return isIdAuth;
    }

    public void setIsIdAuth(Boolean isIdAuth) {
        this.isIdAuth = isIdAuth;
    }
    @Column
    public Boolean getIsLicenceAuth() {
        return isLicenceAuth;
    }

    public void setIsLicenceAuth(Boolean isLicenceAuth) {
        this.isLicenceAuth = isLicenceAuth;
    }
    @Column
    public Boolean getIsMobileAuth() {
        return isMobileAuth;
    }

    public void setIsMobileAuth(Boolean isMobileAuth) {
        this.isMobileAuth = isMobileAuth;
    }
    @Column
    public Boolean getIsStudentCertified() {
        return isStudentCertified;
    }

    public void setIsStudentCertified(Boolean isStudentCertified) {
        this.isStudentCertified = isStudentCertified;
    }
    @Column
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
    @Column
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Column
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Column
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @Column
    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getPayUserStatus() {
        return payUserStatus;
    }

    public void setPayUserStatus(String payUserStatus) {
        this.payUserStatus = payUserStatus;
    }
    @Column
    public Byte getPayUserType() {
        return payUserType;
    }

    public void setPayUserType(Byte payUserType) {
        this.payUserType = payUserType;
    }
    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    @Column
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    @Column
    public Boolean getIsCertifyGradeA() {
        return isCertifyGradeA;
    }

    public void setIsCertifyGradeA(Boolean isCertifyGradeA) {
        this.isCertifyGradeA = isCertifyGradeA;
    }
}
