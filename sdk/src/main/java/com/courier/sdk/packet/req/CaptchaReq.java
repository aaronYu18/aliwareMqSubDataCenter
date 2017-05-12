package com.courier.sdk.packet.req;
import com.courier.sdk.common.IdEntity;
/**
 * Created by vincent on 15/10/29.
 */
public class CaptchaReq extends IdEntity {
    private static final long serialVersionUID = 3319840729764163242L;
    private String mobile;   //用户手机
    private Byte type;       //验证码类型
    private String captcha;  //验证码

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
