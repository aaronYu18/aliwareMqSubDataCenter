package com.courier.commons.api.pay.alipay.param.resp;

import com.courier.commons.api.pay.alipay.param.AlipayResp;

/**
 * Created by ryan on 16/3/31.
 */
public class QueryRefundResp extends AlipayResp {

    private static final long serialVersionUID = 7156631603305035174L;
    /** 支付宝交易号*/
    private String trade_no;
    /** 创建交易传入的商户订单号*/
    private String out_trade_no;
    /** 本笔退款对应的退款请求号*/
    private String out_request_no;
    /** 发起退款时，传入的退款原因*/
    private String refund_reason;
    /** 该笔退款所对应的交易的订单金额*/
    private String total_amount;
    /** 本次退款请求，对应的退款金额*/
    private String refund_amount;

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

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }
}
