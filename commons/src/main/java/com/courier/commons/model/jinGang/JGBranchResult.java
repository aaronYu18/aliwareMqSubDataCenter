package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by bin on 2015/10/29.
 * 金刚网点信息
 */
public class JGBranchResult extends BaseEntity {
    private static final long serialVersionUID = -7061071439803700127L;
    public enum Type{
        SUB_DEPARTMENT,BRANCH;
    }

    private String provinceCode;
    private String cityCode;
    private String areaCode;

    private String province;
    private String city;
    private String area;

    private String compName;                    //分公司
    private String compCode;
    private String terminalName;                //分部
    private String terminalCode;

    private Type compType;                    //  网点类型
    private String comPare;                     // 上级网点编号

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }



    public String getComPare() {
        return comPare;
    }

    public void setComPare(String comPare) {
        this.comPare = comPare;
    }

    public Type getCompType() {
        return compType;
    }

    public void setCompType(Type compType) {
        this.compType = compType;
    }
}
