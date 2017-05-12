package com.courier.commons.enums;

import org.springframework.util.StringUtils;

/**
 * Created by beyond on 2016/4/28.
 */
public enum CouponsTypeEnum {
    ORDINARY((byte)0, "普通订单"),
    COUPON((byte)1, "使用抵用券");

    private Byte code;
    private String desc;


    CouponsTypeEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static boolean exist(Byte code){
        if (code ==null) return true;
        for (CouponsTypeEnum status :values()){
            if (status.getCode()==code)
                return true;
        }
        return false;
    }
    public static CouponsTypeEnum findById(Byte code){
        if (StringUtils.isEmpty(code))return  ORDINARY;
        for (CouponsTypeEnum status :values()){
            if (status.getCode()==code)
                return status;
        }
        return ORDINARY;
    }
    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
