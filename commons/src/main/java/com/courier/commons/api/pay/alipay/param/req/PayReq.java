package com.courier.commons.api.pay.alipay.param.req;

import com.courier.commons.api.pay.alipay.AlipayConfig;
import com.courier.commons.api.pay.alipay.param.AlipayReq;

/**
 * Created by ryan on 16/3/29.
 */
public class PayReq extends AlipayReq {

    private static final long serialVersionUID = 4607363947815068395L;
    /** 商户订单号*/
    private String out_trade_no;
    /** 支付场景*/
    private String scene;
    /** 付款码*/
    private String auth_code;
    /** 卖家支付宝用户ID*/
    private String seller_id;
    /** 订单总金额*/
    private Double total_amount;
    /** 可打折金额*/
    private Double discountable_amount;
    /** 不可打折金额*/
    private Double undiscountable_amount;
    /** 订单标题*/
    private String subject;
    /** 订单描述*/
    private String body;
    /** 商户操作员编号*/
    private String operator_id;
    /** 商户门店编号*/
    private String store_id;
    /** 机具终端编号*/
    private String terminal_id;
    /** 支付超时时间表达式*/
    private String timeout_express;

    public PayReq(){
        this.setMethod(AlipayConfig.payMethod);
    }

    public PayReq(String out_trade_no, String scene, String auth_code, String seller_id, Double total_amount, Double discountable_amount, Double undiscountable_amount, String subject,
                  String body, String operator_id, String store_id, String terminal_id, String timeout_express) {
        this.setMethod(AlipayConfig.payMethod);
        this.out_trade_no = out_trade_no;
        this.scene = scene;
        this.auth_code = auth_code;
        this.seller_id = seller_id;
        this.total_amount = total_amount;
        this.discountable_amount = discountable_amount;
        this.undiscountable_amount = undiscountable_amount;
        this.subject = subject;
        this.body = body;
        this.operator_id = operator_id;
        this.store_id = store_id;
        this.terminal_id = terminal_id;
        this.timeout_express = timeout_express;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getDiscountable_amount() {
        return discountable_amount;
    }

    public void setDiscountable_amount(Double discountable_amount) {
        this.discountable_amount = discountable_amount;
    }

    public Double getUndiscountable_amount() {
        return undiscountable_amount;
    }

    public void setUndiscountable_amount(Double undiscountable_amount) {
        this.undiscountable_amount = undiscountable_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }
}
