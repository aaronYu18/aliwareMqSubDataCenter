package com.courier.commons.api.pay.alipay;

/**
 * Created by ryan on 16/3/21.
 */
public class AlipayConstant {

    //todo  ext Map 键值对key
    public static final String EXT_USER_ID_KEY = "ext_user_id";

    //todo  验证频繁请求keys
    public static final String REDIS_USER_BIND_ALI_PAY_AUTH_FREQUENTLY_KEY = "ali_pay_user_bind_auth_frequently_%d";              // 支付宝绑定用户时使用
    public static final String REDIS_ALI_PAY_SERIAL_NO_FREQUENTLY_KEY = "ali_pay_serial_no_frequently_%s";              // 频繁支付唯一标示key
    public static final String REDIS_ALI_PAY_CANCEL_FREQUENTLY_KEY = "ali_pay_cancel_frequently_%s";              // 取消
    public static final String REDIS_ALI_PAY_REFUND_FREQUENTLY_KEY = "ali_pay_refund_frequently_%s";                //退款
    //todo  频繁请求时间定义
    public static final int REDIS_USER_BIND_AUTH_KEY_VAIL_TIME = 60;                            // 频繁请求间隔时间

    public static final String REDIS_USER_ALI_PAY_AUTH_KEY = "ali_pay_user_pay_auth_%d";              // 支付宝支付时使用(userId 与AliUserId对应关系)

    public static final String REDIS_ALI_PAY_ORDER_DETAIL = "ali_pay_order_detail_%s";              // 支付订单详情

    public static final int REDIS_PAY_ORDER_DETAIL_INVALID_TIME = 30*24*60*60;                   // 支付订单详情,redis保存时间

    public static final String TRUE = "T";

    public static final String FALSE = "F";

    public static final String MALE = "M";          // 男性
    public static final Byte MALE_BYTE = (byte)2;          // 男性

    public static final String FEMALE  = "F";        // 女性
    public static final Byte FEMALE_BYTE = (byte)1;          // 女性

    public static final Byte DEFAULT_PLATFORM_TYPE = 2;

    public static final String DEFAULT_CHANNEL_TYPE = "01";

    public static final Byte DEFAULT_PAY_TYPE = 1;

    public static final String DEFAULT_COST_TYPE = "01";

    public static final String REDIS_ALI_PAY_TODAY_PROFIT_KEY = "alipay_today_profit_%d_%s";               //当前的收入

    public static final String USER_ALI_PAY_INFO_KEY = "user_aliPay_info_%d";               //用户支付宝账户信息

    public static final String USER_ALI_PAY_MAX_TODAY_COUNT = "user_ali_pay_max_today_count_%d_%s";          //当天支付最大次数

    public static final String ALIPAY_SUCCESS = "10000";                                           //支付返回值10000成功

}
