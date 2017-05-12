package com.courier.commons.api.pay.alipay.utils;

import com.courier.commons.api.pay.alipay.AlipayConstant;
import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.DateUtil;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by admin on 2016/3/30.
 */
public class ConstantBuilder {

    //  todo 阿里user auth_code 格式化 频繁请求
    public static String userBindAuthFrequentlyKey(Long userId) {
        if(userId == null || userId == 0l) return null;

        return String.format(AlipayConstant.REDIS_USER_BIND_ALI_PAY_AUTH_FREQUENTLY_KEY, userId);
    }

    public static String payFrequentlyKey(String serialNo) {
        if(StringUtils.isEmpty(serialNo)) return null;

        return String.format(AlipayConstant.REDIS_ALI_PAY_SERIAL_NO_FREQUENTLY_KEY, serialNo);
    }

    public static String payCancelFrequentlyKey(String serialNo) {
        if(StringUtils.isEmpty(serialNo)) return null;

        return String.format(AlipayConstant.REDIS_ALI_PAY_CANCEL_FREQUENTLY_KEY, serialNo);
    }

    public static String payRefundFrequentlyKey(String serialNo) {
        if(StringUtils.isEmpty(serialNo)) return null;

        return String.format(AlipayConstant.REDIS_ALI_PAY_REFUND_FREQUENTLY_KEY, serialNo);
    }

    //  todo 订单记录key值
    public static String buildOrderKey(String serialNo) {
        if(StringUtils.isEmpty(serialNo)) return null;

        return String.format(AlipayConstant.REDIS_ALI_PAY_ORDER_DETAIL, serialNo);
    }

     //  todo 阿里userId与本地db对应关系key
    public static String uId2aliUIdRelation(Long userId) {
        if(userId == null || userId == 0l) return null;

        return String.format(AlipayConstant.REDIS_USER_ALI_PAY_AUTH_KEY, userId);
    }

    /**
     * 获取当日收入的Redis key
     * @param userId
     * @return
     */
    public static String buildTodayProfitKey(Long userId){
        if(null == userId || userId == 0l) return null;

        return String.format(AlipayConstant.REDIS_ALI_PAY_TODAY_PROFIT_KEY, userId, DateUtil.toDay(new Date()));
    }

    /**
     * 获取当日支付次数的key
     * @param userId
     * @return
     */
    public static String buildTodayAlipayCount(Long userId){
        if(null == userId || userId == 0l) return null;

        return String.format(AlipayConstant.USER_ALI_PAY_MAX_TODAY_COUNT, userId, DateUtil.toDay(new Date()));
    }

    /**
     * 获取支付宝账户信息的Redis key
     * @param userId
     * @return
     */
    public static String buildAliPayInfoKey(Long userId){
        if(null == userId || userId == 0l) return null;

        return String.format(AlipayConstant.USER_ALI_PAY_INFO_KEY, userId);
    }

    /**
     * courierBean key 值
     */
    public static String buildUserKey(Long userId) {
        return String.format(CacheConstant.COURIER_USER_KEY_PREFIX, userId);
    }

    //  todo 阿里性别转换
    public static Byte aliGender(String gender){
        if(StringUtils.isEmpty(gender)) return null;

        if(AlipayConstant.MALE.equals(gender)){
            return AlipayConstant.MALE_BYTE;
        }else if(AlipayConstant.FEMALE.equals(gender)){
            return AlipayConstant.FEMALE_BYTE;
        }

        return null;
    }


    //  todo 阿里 true false 转换
    public static Boolean bool(String gender){
        if(StringUtils.isEmpty(gender)) return null;

        if(AlipayConstant.TRUE.equals(gender)){
            return true;
        }else if (AlipayConstant.FALSE.equals(gender)) {
            return false;
        }
        return null;
    }


}
