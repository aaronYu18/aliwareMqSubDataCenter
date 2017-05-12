package com.courier.commons.aliwareMq.base;

/**
 * Created by aaron on 2017/3/8.
 */
public class AliwareMQConnectConfig {
    private String accessKey;
    private String secretKey;
    private String onsAddr;


    public AliwareMQConnectConfig() {
    }

    public AliwareMQConnectConfig(String accessKey, String secretKey, String onsAddr) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.onsAddr = onsAddr;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnsAddr() {
        return onsAddr;
    }

    public void setOnsAddr(String onsAddr) {
        this.onsAddr = onsAddr;
    }

}
