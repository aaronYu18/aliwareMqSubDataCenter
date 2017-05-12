package com.courier.commons.api.pay.alipay;

/**
 * Created by admin on 2016/3/30.
 */
public enum AlipayCode {
    C1000(1000,	"成功"),
    C1001(1001,	"网络异常，再刷刷试试"),
    C1002(1002,	"请求支付宝接口失败……"),
    C1003(1003,	"支付宝账户未进行实名认证"),
    C1004(1004,	"订单状态非支付成功，不能做此操作"),
    C1005(1005,	"退款金额不能大于支付金额"),
    C1007(1007,	"您的请求太过频繁，请稍后再试"),
    C1008(1008,	"该账户还未绑定"),
    C1009(1009,	"该账户名字与支付宝真实姓名不匹配"),
    C1010(1010,	"获取用户信息出错"),
    C1011(1011,	"不能取消支付，该订单已超过24小时"),
    C1012(1012,	"不能退款，该订单没有超过24小时，请选择取消支付"),
    C1013(1013,	"不能退款，该订单已超过3个月"),
    C1014(1014, "支付超时，现取消订单支付"),
    C1015(1015, "超过支付规定次数，暂停支付宝支付"),
    ;
    private Integer code;
    private String desc;

    private AlipayCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getNameByCode(Integer code) {
        for (AlipayCode c : AlipayCode.values()) {
            if (c.getCode().equals(code)) {
                return c.getDesc();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
