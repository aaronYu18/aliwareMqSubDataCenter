package com.courier.commons.enums;

import org.springframework.util.StringUtils;

/**
 * Created by beyond on 2016/3/27.
 */
public enum SyncEnum {
    DELIVERY((byte)101, "发送中"),
    SIGN((byte)102, "已发送");
    private Byte code;
    private String desc;
    private SyncEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
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
    public enum MessageTipEnum{
        SUCCESS(1000,"操作成功"),
        KEYERROR(1001, "授权失败"),
        PARAMETEREMPTY(1002,"无效参数"),
        SYSTEMERROR(1003,"请求参数中内容过长"),
        JSONFORMATERROR(1004,"解析出错（json/xml/byte[]/）"),
        OTHER(1005,"其他问题"),
        GZIPERROR(1006,"Gzip压缩格式错误");

        private int code ;
        private String desc;
        private MessageTipEnum(int code,String desc){
            this.code = code;
            this.desc = desc;
        }
        public static MessageTipEnum findById(String code){
            if (StringUtils.isEmpty(code))return  null;
            for (MessageTipEnum status :values()){
                if (code.equalsIgnoreCase(status.getCode()+""))
                    return status;
            }
            return MessageTipEnum.OTHER;
        }
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
