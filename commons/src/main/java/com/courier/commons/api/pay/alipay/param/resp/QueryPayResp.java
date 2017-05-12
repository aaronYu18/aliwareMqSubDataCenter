package com.courier.commons.api.pay.alipay.param.resp;

import com.courier.commons.api.pay.alipay.param.AlipayResp;

import java.util.Date;

/**
 * Created by ryan on 16/3/30.
 */
public class QueryPayResp extends AlipayResp {

    private static final long serialVersionUID = 4484804004798350483L;
    /** 支付宝交易号*/
    private String trade_no;
    /** 商户订单号*/
    private String out_trade_no;
    /** 买家支付宝账号。*/
    private String buyer_logon_id;
    /** 交易状态*/
    private String trade_status;
    /** 交易金额*/
    private Double total_amount;
    /** 实收金额*/
    private Double receipt_amount;
    /** 买家付款的金额*/
    private Double buyer_pay_amount;
    /** 使用积分宝付款的金额*/
    private Double point_amount;
    /** 交易中可给用户开具发票的金额*/
    private Double invoice_amount;
    /** 本次交易打款给卖家的时间*/
    private Date send_pay_date;
    /** 支付宝店铺编号*/
    private String alipay_store_id;
    /** 商户门店编号*/
    private String store_id;
    /** 商户机具终端编号*/
    private String terminal_id;
    /** 发生支付交易的商户门店名称*/
    private String store_name;
    /** 买家在支付宝的用户id*/
    private String buyer_user_id;
    /** 本次交易支付所使用的单品券优惠的商品优惠信息*/
    private String discount_goods_detail;

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

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(Double receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public Double getBuyer_pay_amount() {
        return buyer_pay_amount;
    }

    public void setBuyer_pay_amount(Double buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }

    public Double getPoint_amount() {
        return point_amount;
    }

    public void setPoint_amount(Double point_amount) {
        this.point_amount = point_amount;
    }

    public Double getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(Double invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public Date getSend_pay_date() {
        return send_pay_date;
    }

    public void setSend_pay_date(Date send_pay_date) {
        this.send_pay_date = send_pay_date;
    }

    public String getAlipay_store_id() {
        return alipay_store_id;
    }

    public void setAlipay_store_id(String alipay_store_id) {
        this.alipay_store_id = alipay_store_id;
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

    public String getDiscount_goods_detail() {
        return discount_goods_detail;
    }

    public void setDiscount_goods_detail(String discount_goods_detail) {
        this.discount_goods_detail = discount_goods_detail;
    }
}
