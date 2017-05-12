package com.courier.commons.api.pay.alipay;

/**
 * Created by ryan on 16/3/21.
 */
public class AlipayConfig {

    //TODO ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    /** 合作身份者ID，以2088开头由16位纯数字组成的字符串 **/
    public static final String partner = "";
    /** 商户的私钥 **/
    public static final String private_key = "";

    /** 支付宝的公钥，无需修改该值 **/
    public static final String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    //TODO ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    /** 字符编码格式 目前支持 gbk 或 utf-8 **/
    public static final String inputCharset = "utf-8";

    /** 签名方式 不需修改 **/
    public static final String signType = "RSA";
    /** 授权方式*/
    public static final String grantType= "authorization_code";
    /** 授权方法*/
    public static final String authMethod = "alipay.system.oauth.token";
    /** 授权返回json的key*/
    public static final String authRespJsonKey = "alipay_system_oauth_token_response";
    /** 用户信息方法*/
    public static final String shareMethod = "alipay.user.userinfo.share";
    /** 用户信息返回json的key*/
    public static final String shareRespJsonKey = "alipay_user_userinfo_share_response";
    /** 支付方法*/
    public static final String payMethod = "alipay.trade.pay";
    /** 支付返回json的key*/
    public static final String payRespJsonKey = "alipay_trade_pay_response";
    /** 支付查询方法*/
    public static final String queryMethod = "alipay.trade.query";
    /** 支付查询返回json的key*/
    public static final String queryRespJsonKey = "alipay_trade_query_response";
    /** 支付取消方法*/
    public static final String cancelMethod = "alipay.trade.cancel";
    /** 支付取消返回json的key*/
    public static final String cancelRespJsonKey = "alipay_trade_cancel_response";
    /** 支付退款方法*/
    public static final String refundMethod = "alipay.trade.refund";
    /** 支付退款返回json的key*/
    public static final String refundRespJsonKey = "alipay_trade_refund_response";
    /** 支付退款查询返回json的key*/
    public static final String queryRefundRespJsonKey = "alipay_trade_fastpay_refund_query_response";
    /** 支付请求格式*/
    public static final String format_json = "json";

}
