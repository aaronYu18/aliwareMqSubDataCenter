package com.courier.core.service;

import com.courier.commons.api.ApiResult;
import com.courier.commons.api.pay.alipay.AlipayApi;
import com.courier.commons.api.pay.alipay.AlipayCode;
import com.courier.commons.api.pay.alipay.param.api.AlipayParam;
import com.courier.commons.api.pay.alipay.param.api.CancelParam;
import com.courier.commons.api.pay.alipay.param.api.RefundParam;
import com.courier.commons.api.pay.alipay.param.api.SearchParam;
import com.courier.commons.api.pay.alipay.utils.ConstantBuilder;
import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.BindPayOffset;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.*;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by bin on 2015/11/11.
 */
@Service
@Transactional
public class AlipayService {
    private static final Logger logger = LoggerFactory.getLogger(AlipayService.class);

    @Autowired UserService userService;
    @Autowired AlipayApi alipayApi;
    @Autowired CacheUtil cacheUtil;
    @Autowired CollectOrderService collectOrderService;
    @Autowired DeliveryOrderService deliveryOrderService;
    @Autowired ReportService reportService;

    public ResponseDTO bindAliPay(String uuid, String authCode) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();
        boolean bindAliPay = user.isBindAliPay();

        if (bindAliPay) return new ResponseDTO(CodeEnum.C3001);

        ApiResult apiResult = alipayApi.bindAlipay(user.getId(), authCode);

        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            if(AlipayCode.C1007.getCode().equals(code)) return new ResponseDTO(CodeEnum.C1007);
            return new ResponseDTO(CodeEnum.C3000);
        }
        return new ResponseDTO(CodeEnum.C1000);
    }

    public ResponseDTO queryBindAliPay(String uuid) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        String relationKey = ConstantBuilder.uId2aliUIdRelation(user.getId());
        ApiResult apiResult = cacheUtil.getCacheByFromRedis(relationKey, ApiResult.class);
        if (apiResult == null) {
            apiResult = alipayApi.getBindAlipayResult(user.getId());
        }
        if (apiResult != null) {
            int code = apiResult.getCode();
            if(AlipayCode.C1000.getCode() != code){
                if(AlipayCode.C1002.getCode() == code) return new ResponseDTO(CodeEnum.C3002);
                if(AlipayCode.C1003.getCode() == code) return new ResponseDTO(CodeEnum.C3003);
                if(AlipayCode.C1009.getCode() == code) return new ResponseDTO(CodeEnum.C3009);
            }else {
                //查询绑定结果为成功,更新数据库
                Map<Byte, Boolean> bindMap = new HashMap<>();
                bindMap.put(BindPayOffset.ALIPAY.getOffset(), true);
                userService.updateBindPay(uuid, user.getId(), bindMap);
                return new ResponseDTO(CodeEnum.C1000);
            }
        }
        return new ResponseDTO(CodeEnum.C3006);
    }

    public ResponseDTO unBindAliPay(String uuid) {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        ApiResult apiResult = alipayApi.unBindAliPay(user.getId());
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            return new ResponseDTO(CodeEnum.C3000);
        }else {
            //解绑成功,更新数据库
            Map<Byte, Boolean> bindMap = new HashMap<>();
            bindMap.put(BindPayOffset.ALIPAY.getOffset(), false);
            userService.updateBindPay(uuid, user.getId(), bindMap);
            return new ResponseDTO(CodeEnum.C1000);
        }
    }

    public ResponseDTO pay(String uuid, Byte dcType, Long orderId, Double payAmount, String payCode, Byte platformType) {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        String costType = null;
        String mailNo = null;
        Double cMoney = 0.00;
        Double fMoney = 0.00;
        Double amount = null;
        String sourceKey = null;
        if (Enumerate.DCType.COLLECT.getCode().equals(dcType)) { //取件单处理
            CollectOrder co = collectOrderService.get(orderId);
            if (co == null) {
                return new ResponseDTO(CodeEnum.C2015);
            }else {
                sourceKey = co.getSourceKey();
                mailNo = co.getMailNo();
                fMoney = payAmount;
                amount = fMoney;
                costType = Enumerate.CollectOrderType.NOORDER.getCode().equals(co.getOrderType())?
                        Enumerate.CostType.NO_ORDER.getCode():Enumerate.CostType.HAS_ORDER.getCode();
            }
        }else { //派件单处理
            DeliveryOrder dOrder = deliveryOrderService.get(orderId);
            if (dOrder == null) {
                return new ResponseDTO(CodeEnum.C2015);
            }else {
                mailNo = dOrder.getMailNo();
                cMoney = dOrder.getCollectionMoney()==null?0:dOrder.getCollectionMoney();
                fMoney = dOrder.getFreightMoney()==null?0:dOrder.getFreightMoney();
                if (Enumerate.DeliveryOrderPaymentType.freight.getType().equals(dOrder.getPaymentType())) {
                    amount = fMoney;
                    costType = Enumerate.CostType.DF.getCode();
                }else if (Enumerate.DeliveryOrderPaymentType.collection.getType().equals(dOrder.getPaymentType())){
                    amount = cMoney;
                    costType = Enumerate.CostType.DS.getCode();
                }else if (Enumerate.DeliveryOrderPaymentType.freightAndcollection.getType().equals(dOrder.getPaymentType())){
                    amount = fMoney + cMoney;
                    costType = Enumerate.CostType.DFDS.getCode();
                }else {
                    return new ResponseDTO(CodeEnum.C3004);
                }
            }
        }
        AlipayParam alipayParam = new AlipayParam(user.getId(), payCode, mailNo, platformType, orderId, sourceKey, dcType,
                Enumerate.PayChannelType.ALIPAY.getCode(), Enumerate.PayType.barcode.getCode(), costType,
                "圆通快递支付", amount, cMoney, fMoney, null, null);
        ApiResult apiResult =  alipayApi.pay(alipayParam);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            if(AlipayCode.C1007.getCode().equals(code)) return new ResponseDTO(CodeEnum.C1007);
            if(AlipayCode.C1008.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3007);
            if(AlipayCode.C1015.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3015);
            return new ResponseDTO(CodeEnum.C3000);
        }
        String tradeNo = apiResult.getT().toString();
        return new ResponseDTO(CodeEnum.C1000, null, tradeNo);
    }

    public ResponseDTO queryPay(String uuid, String tradeNo) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        ApiResult apiResult = alipayApi.getPayResult(user.getId(), tradeNo);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            if(AlipayCode.C1014.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3014);
            return new ResponseDTO(CodeEnum.C3000);
        }
        return new ResponseDTO(CodeEnum.C1000, null, apiResult.getT());
    }

    public ResponseDTO cancelPay(String uuid, String tradeNo) {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        CancelParam cancelParam = new CancelParam(user.getId(), tradeNo);
        ApiResult apiResult = alipayApi.cancelPay(cancelParam);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            if(AlipayCode.C1007.getCode().equals(code)) return new ResponseDTO(CodeEnum.C1007);
            if(AlipayCode.C1004.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3005);
            if(AlipayCode.C1011.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3011);
            return new ResponseDTO(CodeEnum.C3000);
        }else {
            return new ResponseDTO(CodeEnum.C1000);
        }
    }

    public ResponseDTO refund(String uuid, String tradeNo, Double refundAmount, String refundReason) {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        RefundParam refundParam = new RefundParam(user.getId(), refundAmount, tradeNo, refundReason);
        ApiResult apiResult = alipayApi.refundPay(refundParam);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            if(AlipayCode.C1004.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3005);
            if(AlipayCode.C1007.getCode().equals(code)) return new ResponseDTO(CodeEnum.C1007);
            if(AlipayCode.C1012.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3012);
            if(AlipayCode.C1013.getCode().equals(code)) return new ResponseDTO(CodeEnum.C3013);
            return new ResponseDTO(CodeEnum.C3000);
        } else {
            return new ResponseDTO(CodeEnum.C1000);
        }
    }

    public ResponseDTO myPayList(String uuid, Date beginTime, Date endTime, Integer pageNo, Integer pageSize){
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        SearchParam searchParam = new SearchParam(user.getId(), Enumerate.PayChannelType.ALIPAY.getCode(),
                Enumerate.PayStatus.pay_success.getCode(), Enumerate.PayType.barcode.getCode(), null, beginTime, endTime);

        ApiResult apiResult = alipayApi.getPayList(searchParam, pageNo, pageSize);
        if (AlipayCode.C1000.getCode() != apiResult.getCode()) {
            return new ResponseDTO(CodeEnum.C3000);
        }
        Page page = (Page) apiResult.getT();
        List<PayOrderInfo> list = page.getResult();
        for (PayOrderInfo payOrderInfo : list) {
            if (Enumerate.DCType.COLLECT.getCode().equals(payOrderInfo.getDcType())) {
                //取件单查询
                CollectOrder collectOrder = collectOrderService.findCollect(user.getId(), payOrderInfo.getMailNo());
                if (collectOrder != null) {
                    payOrderInfo.setSignOrCollectAddress(collectOrder.getSenderAddress());
                    payOrderInfo.setSignOrCollectTime(collectOrder.getCollectTime());
                    payOrderInfo.setLng(collectOrder.getSenderLng());
                    payOrderInfo.setLat(collectOrder.getSenderLat());
                }
            }else {
                //派件单查询
                DeliveryOrder deliveryOrder = deliveryOrderService.findDelivery(user.getId(), payOrderInfo.getMailNo());
                if (deliveryOrder != null) {
                    payOrderInfo.setSignOrCollectAddress(deliveryOrder.getReceiverAddress());
                    payOrderInfo.setSignOrCollectTime(deliveryOrder.getSignTime());
                    payOrderInfo.setLng(deliveryOrder.getReceiverLng());
                    payOrderInfo.setLat(deliveryOrder.getReceiverLat());
                }
            }
        }
        return new ResponseDTO(CodeEnum.C1000, null, apiResult.getT());
    }

    public ResponseDTO queryCancelPay(String uuid, String tradeNo) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        ApiResult apiResult = alipayApi.getCancelResult(user.getId(), tradeNo);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            return new ResponseDTO(CodeEnum.C3000);
        }
        return new ResponseDTO(CodeEnum.C1000, null, apiResult.getT());
    }

    public ResponseDTO queryRefund(String uuid, String tradeNo) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        ApiResult apiResult = alipayApi.getRefundResult(user.getId(), tradeNo);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            return new ResponseDTO(CodeEnum.C3000);
        }
        return new ResponseDTO(CodeEnum.C1000, null, apiResult.getT());
    }

    public Double todayCollectAmount(Long userId) {
        Double ret = 0.00;
        ApiResult apiResult = alipayApi.getToday(userId);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() == code && apiResult.getT() != null){
            ret = (Double) apiResult.getT();
        }
        return ret;
    }
    public Double todayCollectAmount(String uuid) {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return 0.00;
        User user = (User) responseDTO.getT2();
        return todayCollectAmount(user.getId());
    }

    public Report getReportData(String uuid, String date) {
        Report report = new Report();
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return report;
        User user = (User) responseDTO.getT2();

        Report r = reportService.findThisDay(user.getId(), date);
        if (r != null) report = r;
        ApiResult apiResult = alipayApi.getPayReport(user.getId(), date);
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            return report;
        }
        PayReport payReport = (PayReport) apiResult.getT();
        report.setDayAmount(payReport==null?0.0:payReport.getCollectionAmount());
        return report;
    }

    public ResponseDTO getAliPayInfo(String uuid) {
        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        User user = (User) responseDTO.getT2();

        ApiResult apiResult = alipayApi.getAliPayInfo(user.getId());
        int code = apiResult.getCode();
        if(AlipayCode.C1000.getCode() != code){
            return new ResponseDTO(CodeEnum.C3000);
        }
        return new ResponseDTO(CodeEnum.C1000, null, apiResult.getT());
    }
}