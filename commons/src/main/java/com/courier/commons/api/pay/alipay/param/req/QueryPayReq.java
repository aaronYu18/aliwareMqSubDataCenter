package com.courier.commons.api.pay.alipay.param.req;

import com.courier.commons.api.pay.alipay.AlipayConfig;
import com.courier.commons.api.pay.alipay.param.AlipayReq;

/**
 * Created by ryan on 16/3/30.
 */
public class QueryPayReq extends AlipayReq {

    private static final long serialVersionUID = -3797607032646834904L;
    /** 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no*/
    private String out_trade_no;
    /** 支付宝交易号，和商户订单号不能同时为空*/
    private String trade_no;

    public QueryPayReq(){
        this.setMethod(AlipayConfig.queryMethod);
    }

    public QueryPayReq(String out_trade_no, String trade_no) {
        this.setMethod(AlipayConfig.queryMethod);
        this.out_trade_no = out_trade_no;
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
}
