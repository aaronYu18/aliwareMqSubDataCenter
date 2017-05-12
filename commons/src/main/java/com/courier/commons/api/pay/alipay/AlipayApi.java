package com.courier.commons.api.pay.alipay;

import com.courier.commons.api.pay.alipay.param.api.AlipayParam;
import com.courier.commons.api.pay.alipay.param.api.CancelParam;
import com.courier.commons.api.pay.alipay.param.api.RefundParam;
import com.courier.commons.api.pay.alipay.param.api.SearchParam;
import com.courier.commons.entity.BaseEntity;
import com.courier.commons.api.ApiResult;

/**
 * Created by ryan on 16/3/23.
 * 定义支付宝的接口
 */
public interface AlipayApi<T> {

    public ApiResult<T> bindAlipay(Long userId, String authCode);

    public ApiResult<T> unBindAliPay(Long userId);

    public ApiResult<T> getBindAlipayResult(Long userId);

    public ApiResult<T> pay(AlipayParam alipayParam);

    public ApiResult<T> getPayResult(Long userId, String serialNo);

    public ApiResult<T> cancelPay(CancelParam cancelParam);

    public ApiResult<T> getCancelResult(Long userId, String serialNo);

    public ApiResult<T> refundPay(RefundParam refundParam);

    public ApiResult<T> getRefundResult(Long userId, String serialNo);

    public ApiResult<T> getPayList(SearchParam searchParam, Integer pageNo, Integer pageSize);

    public ApiResult<T> getToday(Long userId);

    public ApiResult<T> getAliPayInfo(Long userId);

    public ApiResult<T> getPayReport(Long userId, String date);
}
