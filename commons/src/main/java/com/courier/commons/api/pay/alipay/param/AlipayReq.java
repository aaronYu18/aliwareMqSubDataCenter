package com.courier.commons.api.pay.alipay.param;

import com.courier.commons.annotations.MainField;
import com.courier.commons.annotations.RSAPrivateKey;
import com.courier.commons.annotations.RSAPublicKey;
import com.courier.commons.api.pay.alipay.AlipayConfig;
import com.courier.commons.entity.BaseEntity;
import com.courier.commons.util.DateUtil;

/**
 * Created by ryan on 16/3/29.
 */
public class AlipayReq extends BaseEntity{
    private static final long serialVersionUID = 475954073884542522L;
    /** 应用appid*/
    protected transient String app_id;
    /** 提交格式*/
    protected transient String charset = AlipayConfig.inputCharset;
    /** 签名格式*/
    protected transient String sign_type = AlipayConfig.signType;
    /** 签名*/
    protected transient String sign;
    /** 时戳*/
    protected transient String timestamp = DateUtil.getNowStr();
    /** 版本*/
    protected transient String version = "1.0";
    /** 支付宝方法*/
    protected transient String method;
    /** 支付宝商户私钥*/
    protected transient String privateKey;
    /** 支付宝商户公钥*/
    protected transient String publicKey;

    @MainField
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    @MainField
    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    @MainField
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
    @MainField
    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    @MainField
    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    @MainField
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @MainField
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    @RSAPrivateKey
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    @RSAPublicKey
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
