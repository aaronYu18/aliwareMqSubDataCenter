package com.courier.commons.constant;

/**
 * Created by bin on 2015/6/23.
 */
public class CacheConstant {
    public final static String COURIER_USER_KEY_PREFIX = "courierBean_%d";                                          //  courier bean redis key 值
    public final static String COURIER_SESSION_KEY_PREFIX = "courierSession_%s";                                    //  courier session key 值
    public final static String SMS_VALIDATE_KEY = "courierSmsValidate%s_%s_%s";                                     //  sms验证码key 值
    public final static String SMS_BIND_PHONE_KEY = "BindPhone";                                                    //  绑定手机sms验证码key 值
    public final static String SMS_TEMPLATE_KEY = "BindPhone_%d";                                                   //  验证码模板 key 值
    public final static String USER_GPS_KEY = "userGps_%s_%s";                                                      //  用户在缓存中的Gps  (网点代码、工号)
    public final static String USER_GPS_STACK_KEY = "userGpsStack_%s_%s";                                           //  用户在缓存中的Gps堆栈 存放多条数据
    public final static String CITIES_USER_GPS_RELATIONSHIP_KEY = "cities_user_gps_relationship_%s";                //  省市关系key值  return:Set<String> string 为用户最新经纬度的key
    public final static String PROVINCES_CITIES_RELATIONSHIP_KEY = "provinces_cities_relationship_%s";              //  省市关系key值  return:Set<String>

    public final static String ALL_REGIONS_KEY = "all_regions";                                                     //  所有省市区key值  return:Map<String,String>
    public final static String ALL_STALE_REGIONS_KEY = "all_stale_regions";                                         //  所有非正规省市区key值  return:Map<String,String>
    public final static String ALL_PROVINCE_AUTH_PATTERN_KEY = "all_province_auth_patterns";                        //  所有省市对应验证模式（取件时实名验证）key值 return:Map<String,Byte>
    public final static String ALL_JOBNO_TO_USERID_KEY = "all_jobNo_to_userId";                                         //所有工号对应userId的key值  return:Map<String,Long>
    public final static String SENDNO_JOBNO_TO_USERID_KEY = "sendNo_jobNo_to_userId";                                   //派件数大于某值的工号对应userId的key值  return:Map<String,Long>

    public final static String COLLECTION_ORDER_KEY = "collection_order_key_%d";                                    //  收件的redis key值:collection_order_key_courierId  value值 : 快件Bean对象
    public final static String COLLECTION_ORDER_CITY_KEY = "collection_order_city_key_%s";                          //  城市收件的redis key值:collection_order_city_key_cityCode value值 : 所在城市的收件的list
    public final static String COLLECTION_ORDER_PROVINCE_KEY = "collection_order_province_key_%s";                  //  省市的
    public final static String COLLECTION_ORDER_TIMESTEMP_KEY = "collection_order_timestamp_key";                 //  计时
    public final static String COLLECTION_ORDER_UNROBS_EY = "collection_order_unrobs_key_%s";                       // 用户未抢单
    public final static String ALL_VORDER_LIST_LEY = "all_vOrder_list_%d";                                          //  单个用户全部派件、揽件列表（排序后）param1:uId  value值：List<VOrder>
    public final static String DELIVERY_VORDER_LIST_LEY = "delivery_vOrder_list_%d";                                //  单个用户全部派件列表（排序后）param1:uId  value值：List<VOrder>
    public final static String COLLECT_VORDER_LIST_LEY = "collect_vOrder_list_%d";                                  //  单个用户全部揽件列表（排序后）param1:uId  value值：List<VOrder>
    public final static String HOME_PAGE_DATA_KEY = "home_page_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_SIGN_DATA_KEY = "home_page_sign_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_COLLECT_DATA_KEY = "home_page_collect_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_PROBLEM_DATA_KEY = "home_page_problem_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_SENDING_DATA_KEY = "home_page_sending_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_COLLECTING_DATA_KEY = "home_page_collecting_data_%d";                                            //  首页数据
    public final static String HOME_PAGE_DATA_KEY_SET = "home_page_data_set_%d";                                            //  首页数据
    public final static String YTO_PAGE_DATA_KEY = "yto_page_data_%d_%s";                                              //  我的圆通数据
    public final static String YTO_PAGE_AVG_KEY = "yto_page_avg_%s";                                              //  我的圆通上月全网平均
    public final static String YTO_PAGE_AVG_TEMP_KEY = "yto_page_avg_temp_%s";                                              //  我的圆通当月全网动态平均

    public final static String PUSH_FILTER_KEY = "push_filter_key_%d";
    public final static String ONLINE_USER_COURIER = "online_user_courier";                                         //在线用户
    public final static String FETCH_ORDER_LIST = "fetch_order_list";                                               //抢单信息

    public final static String REGION_CODE_NAME = "region_code_name_%s";                                            //region的code与name的键值对
    public final static String REGION_NAME_CODE = "region_name_code_%s";                                            //region的name与code的键值对

    public final static String PLUSE_COLLECT_ORDERID = "pluse_collect_orderID_%s";                                  //记录快递员已经推送的订单 快递员位置实时上传

    //public final static String ORGCODE_CLIENTID_ORDERID = "orgcode_clientid_orderid";                               // orgcode 的对应的clientId
    public final static String SECRET_ORGCODE_CLIENT = "secret_orgcode_client_%s";                                  //clientId 的对应 secret

    public final static String QUERY_OTHER_DELIVERY_TIMES = "query_other_delivery_times_%s";                        //查询别人派件次数
    public final static String QUERY_OTHER_DELIVERY_KEYS = "query_other_delivery_keys_%s";                        //(查询别人派件次数)key存放的set

    public final static String QUEUE_DELIVERYSIGN_SET = "queue_deliverysign_set";                                   //回收签收队列
    public final static String QUEUE_RECORDORDER_SET = "queue_recordorder_set";                                     //回收无单取件录单队列
    public final static String QUEUE_COLLECTPICKUPBYNO_SET = "queue_collectpickupbyno_set";                         //回收无单取件队列
    public final static String QUEUE_COLLECTPICKUP_SET = "queue_collectpickup_set";                                 //回收有单取件队列
    public final static String QUEUE_COLLECTNOACCEPT_SET = "queue_collectnoaccept_set";                             //回收无人接单队列
    public final static String QUEUE_COLLECTACCEPT_SET = "queue_collectaccept_set";                                 //回收接单队列
    public final static String QUEUE_TRADEINFO_SET = "queue_tradeinfo_set";                                         //回收交易流水号队列

    public final static String SIGN_LOCK_KEY = "sign_lock_%d_%d";

    public final static String API_CONFIG_ALL_DATA = "api_config_all_data";                       //api config 所有数据 value值为set<String>
    public final static String SOURCEID_CLIENTID_RELATION = "sourceId_clientId_relations";                         //渠道ID与clientID值的map
    public final static String SOURCEID_CLIENTID_RELATION_REVERSAL = "sourceId_clientId_relations_reversal";        //渠道ID与clientID值的map(key value 互换)

    public final static String NOTIFY_PROP_KEY = "notify_prop_key";

    public final static String SYNC_LOCK_USER_KEY = "sync_lock_userid_%d";                                          //interface与datacenter的用户同步锁
    public final static String SYNC_LOCK_USER_MAILNO_KEY = "sync_lock_userid_mailno_%d_%s";                                          //interface与datacenter的用户同步锁

    public final static String REPORT_USER_TABLE_KEY = "report_user_table_%d_%s_%s";     // report table cache key
    public final static String REPORT_USER_PLOT_KEY = "report_user_plot_%d_%s";       // report plot cache key

    public final static String USER_ORGCODE_JOBNO = "user_orgcode_jobno_%s_%s";         //orgcode jobno

    public final static String FAQ_VERSION = "faq_version";         //orgcode jobno

    public final static String DELIVERY_ORDER_SYNC_DATE_UIDS = "delivery_order_sync_uIds_%s";         // 数据同步前一日用户ids


    public final static String MANAGER_USER_KEY_PREFIX = "managerBean_%d";                                          //  manager bean redis key 值
    public final static String MANAGER_SESSION_KEY_PREFIX = "managerSession_%s";                                    //  manager session key 值
    public final static String MANAGER_SMS_VALIDATE_KEY = "manager_%d_smsValidate_%s";                      //  manager sms验证码key 值



    public final static String LAST_7DAY_REGION_KEY = "last_7day_region_%s_%s_%s_%s";

    public final static String MYWORK_GROUPBY_DATE_REGION_KEY = "mywork_groupby_date_region_%s_%s_%s_%s";

    public final static String MYWORK_REGION_KEY = "mywork_region_%s_%s_%s_%s";
    public final static String QUERYDETAILBYREGIONKEY = "queryregionkey_%s_%s_%s_%s";


    public final static String GETORGCODES_ROLE_ORGCODE = "getorgcodes_role_orgcode_%s_%s";

    public final static String COLLECT_PICTURE = "collect_picture_%d_%s";


    // todo 行者管理app相关数据
    public final static String MANAGER_TODAY_DATA_TOTAL = "manager_today_data_%s_%d";           // 工号：角色

    public final static String MANAGER_TODAY_DATA = "manager_today_data_%s_%d_%d_%d";           // 工号：角色：页码：每页显示条目

    public final static String MANAGER_HISTORY_DATA_SIGN_DATA = "manager_history_sign_data_%d_%s_%s_%s_%d_%d";           // userId：mailNo :beginTime:endTime:pageNo :pageSize

    public final static String MANAGER_TODAY_DATA_SIGN_DATA = "manager_today_sign_data_%d_%s_%d_%d";           // userId：mailNo:pageNo :pageSize

    public final static String MANAGER_TODAY_DATA_SENDING_DATA = "manager_today_sending_data_%d_%s_%d_%d";           // userId：mailNo:pageNo :pageSize

    public final static String MANAGER_TODAY_DATA_QUERY = "manager_today_data_query_%s_%d";           // key：角色

    public final static String ORG_PAGE_DATA_KEY = "org_page_data_%d_%s";                                              //  我的网点数据
    public final static String ORG_PAGE_AVG_KEY = "org_page_avg_%s";                                              //  我的网点上月全网平均
    public final static String ORG_PAGE_AVG_TEMP_KEY = "org_page_avg_temp_%s";                                              //  我的网点当月全网动态平均


    public final static String SOURCE_NAME_KEY = "source_name_key_%d";                                              //source取name key

    public final static String BRANCH_KEY = "branch_key";

    public final static String THIRD_WANTED_MAILNO_KEY="third_wanted_mailno_key_%s";                                       // 通缉件key
    public final static String THIRD_PROBLEM_MAILNO_KEY="third_problem_mailno_key_%s";                                     // 问题件key

    public final static String APPOINTMENT_ORDER_MAIL_NO_KEY ="appointment_order_mail_no_%s";                                              //菜鸟预约订单
}
