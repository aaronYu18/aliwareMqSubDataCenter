package com.courier.commons.api.pay.alipay.param.resp;

import com.courier.commons.api.pay.alipay.param.AlipayResp;

import java.util.Date;

/**
 * Created by ryan on 16/3/30.
 */
public class RefundPayResp extends AlipayResp {

    private static final long serialVersionUID = 1032441262489445517L;
    /** 支付宝交易号*/
    private String trade_no;
    /** 商户订单号*/
    private String out_trade_no;
    /** 用户的登录id*/
    private String buyer_logon_id;
    /** 本次退款是否发生了资金变化*/
    private String fund_change;
    /** 本次发生的退款金额*/
    private String refund_fee;
    /** 退款支付时间*/
    private Date gmt_refund_pay;
    /** 交易在支付时候的门店名称*/
    private String store_name;
    /** 买家在支付宝的用户id*/
    private String buyer_user_id;
    /** 实际退回给用户的金额*/
    private String send_back_fee;

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }

    public String getFund_change() {
        return fund_change;
    }

    public void setFund_change(String fund_change) {
        this.fund_change = fund_change;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public Date getGmt_refund_pay() {
        return gmt_refund_pay;
    }

    public void setGmt_refund_pay(Date gmt_refund_pay) {
        this.gmt_refund_pay = gmt_refund_pay;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getBuyer_user_id() {
        return buyer_user_id;
    }

    public void setBuyer_user_id(String buyer_user_id) {
        this.buyer_user_id = buyer_user_id;
    }

    public String getSend_back_fee() {
        return send_back_fee;
    }

    public void setSend_back_fee(String send_back_fee) {
        this.send_back_fee = send_back_fee;
    }
}
