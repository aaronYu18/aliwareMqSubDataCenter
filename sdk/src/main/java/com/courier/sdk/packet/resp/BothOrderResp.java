package com.courier.sdk.packet.resp;
import com.courier.sdk.common.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class BothOrderResp extends IdEntity {
    private static final long serialVersionUID = -3067364895639380007L;
    private Long id;                  //id
    private Byte type;                //类型(0:取件，1:派件)
    private String expressNo;            //快递单号
    private String name;             //收(派)件人姓名
    private String mobile;             //收(派)件人电话
    private String address;             //收(派)件人地址
    private String province;            //收(派)件人省
    private String city;                //收(派)件人市
    private String area;                //收(派)件人区县
    private String provinceName;            //收(派)件人省
    private String cityName;                //收(派)件人市
    private String areaName;                //收(派)件人区县
    private Double lat;                 //收(派)件人经度
    private Double lng;                 //收(派)件人纬度
    private Double distance;            //收(派)件人距离
    private Boolean isSelected=false;         //是否选中(app用)
    private Date date;

    private String collectType;     //取件单类型描述:抢,系,奖等
    private Byte collectCode;     //取件单类型

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCollectType() {
        return collectType;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getCollectCode() {
        return collectCode;
    }

    public void setCollectCode(Byte collectCode) {
        this.collectCode = collectCode;
    }
}
