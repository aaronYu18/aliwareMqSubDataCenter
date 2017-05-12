package com.courier.commons.constant;


/**
 * Created by ryan on 15/5/5.
 */
public class Global {

    public static final String DEFAULT_PASSWORD = "0000000000";       // 系统默认密码明文 十个零（0000000000）
    public static final String DEFAULT_ORGCODE = "0000000000";       // 系统默认网点代码 十个零（0000000000）
    public static final String MCODE_PATTERN = "^\\d{4}";       // mCode
    public static final String CMD_FIELD = "cmd";               // 常量cmd
    public static final String GZIP = "gzip";                   //java原始的gzip流
    public static int bufferLen = 1024 * 4;
    //MQ 队列
    public final static String MQ_HEADER_STATUS_KEY = "1";
    public final static String MQ_EXT_TYPE_KEY = "mqtype";

    //MQ 队列
    public static final String NUMBER = "^[0-9]+$";  //数字
    public static final String CHARNUMBER = "^[a-zA-Z0-9]+$";  //英文数字
    public static final String REGEX_PHONE = "^0{0,1}(13[0-9]|15[0-9]|18[0-9]|14[5|6|7]|17[0-9])[0-9]{8}$"; // 手机号码
    public static final String REGEX_MOBILE = "^1[0-9]{10}$";  // 手机号码
    public static final String TIME_REQ = "(2[0-9])[0-9][0-9][0|1][0-9]";
    public static final String REGEX_PASSWORD = "^(\\w|\\d|_){6,20}$";          // 账户密码
    public static final String REGEX_SENDER_NAME = "^[a-zA-Z\u4e00-\u9fa5\\.]{0,20}$";     // 寄件人姓名
    public static final String REMEMBER_ME_KEY = "rememberMeKey";
    public static final String COOKIE_SECRET = "9048";
    public static final String COOKIE_SEPARATOR = "&&&&";
    public static final int DB_BATCH_PER_COUNT = 200;
    public static final int ONE_SECOND = 1000;

    public static final int ONE_MICSECOND = 1;
    public static final int ONE_DAY_AGE= 24*60*60;

    public static final int THREE_DAY_AGE =ONE_DAY_AGE * 3;//3天
    public static final int LOGIN_COOIKE_MAX_AGE = 30 * ONE_DAY_AGE;  //30天

    public static final int REPORT_MONTH_LIMIT = 6;

    public static final Integer Global_Cache_Time = 5;

    public static final byte NO_ORDER_PICK_UP_SOURCE = -1;    //  无单取件 对应source的常量

    public static final int DAY_MONTH_LIMIT = 7;             //我的战绩中查询的天数

    public static final int REQUESTNUMBER = 3;              //接口请求3次

    public static final long IF_USER_NOT_EXIST_DEFAULT_USER_ID = 0l;              //接口请求3次

    public static final int FireDeliveryTime = 5 * 60;

}
