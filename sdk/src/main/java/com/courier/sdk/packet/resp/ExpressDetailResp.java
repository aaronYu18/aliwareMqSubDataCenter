package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/11/13.
 */
public class ExpressDetailResp extends IdEntity {
    private static final long serialVersionUID = 6710939956729490193L;
    private String expressNo;                  //快递单号
    private Byte type;                //类型(0:待派，1:待取)
    private String name;                //收(派)件人姓名
    private String phone;               //收(派)件人电话
    private String address;             //收(派)件人地址
    private String province;            //收(派)件人省
    private String city;                //收(派)件人市
    private String area;                //收(派)件人区县
    private Double lat;                 //收(派)件人经度
    private Double lng;                 //收(派)件人纬度
    private String failedDesc;          //问题件描述
    private String failedCodeStr;       //问题件原因
    private Byte status;                //状态
    private String statusDesc;          //状态描述
    private Date time;                  //时间

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getFailedCodeStr() {
        return failedCodeStr;
    }

    public void setFailedCodeStr(String failedCodeStr) {
        this.failedCodeStr = failedCodeStr;
    }

    public String getFailedDesc() {
        return failedDesc;
    }

    public void setFailedDesc(String failedDesc) {
        this.failedDesc = failedDesc;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
