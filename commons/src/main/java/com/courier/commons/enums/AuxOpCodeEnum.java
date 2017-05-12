package com.courier.commons.enums;

/**
 * Created by beyond on 2016/4/8.
 */
public enum AuxOpCodeEnum {
    UPDATE("update","更新"),
    DELETE("delete","删除"),
    INSERT("new","新增")
    ;

    AuxOpCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static boolean isExist(String code){
        AuxOpCodeEnum[] values = AuxOpCodeEnum.values();
        for (AuxOpCodeEnum codeEnum:values){
            if (codeEnum.getCode().equalsIgnoreCase(code)){
                return true;
            }
        }
        return false;
    }

    private String code;
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
