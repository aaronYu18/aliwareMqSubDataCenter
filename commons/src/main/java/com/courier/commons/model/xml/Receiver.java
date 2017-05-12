package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class Receiver extends BaseEntity{
    private static final long serialVersionUID = -4664936592432494787L;
    private String name;
    private String postCode;
    private String phone;
    private String mobile;
    private String prov;             //中文省市区
    private String city;
    private String address;

    public Receiver(){
        this.name = "";
        this.postCode = "";
        this.phone ="";
        this.mobile ="";
        this.prov = "";
        this.city = "";
        this.address = "";
    }
    public Receiver(String name, String postCode, String phone, String mobile, String prov, String city, String address) {
        this.name = name;
        this.postCode = postCode;
        this.phone = phone;
        this.mobile = mobile;
        this.prov = prov;
        this.city = city;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
