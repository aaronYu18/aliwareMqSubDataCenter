package com.courier.sdk.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2015/6/18.
 */
public enum Enumerate {
    disable((byte) 0, "不可用"), enable((byte) 1, "可用");

    private static final String IOS = "ios";
    private static final String ANDROID = "android";
    private static final String ANDROID_DZ = "android定制";
    private static final String ANDROID_PDA = "androidPDA";
    private static final String PC = "pc";
    private static final String COURIER_APP_CODE = "w";
    private static final String MANAGER_APP_CODE = "m";
    private static final String CUSTOMER_APP_CODE = "c";

    private Byte type;
    private String name;

    private Enumerate(Byte type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public enum SortType {
        ASC((byte) 0, "asc"),
        DESC((byte) 1, "desc");

        private Byte type;
        private String name;

        private SortType(Byte type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    public enum ContentType {
        TEXTMESSAGE((byte) 0, "快递员师傅，您有新的消息，请查看", "消息", "message.m4a"),
        ROB((byte) 1, "快递员师傅，您有一个新的寄件订单，请查看", "抢单", "order.m4a"),
        CANCEL((byte) 2, "快递员师傅，取件订单已取消", "取消", "orderCancel.m4a"),
        LOGOUT((byte) 3, "您的帐号于在另一台设备上登录。如非本人操作，则密码可能已泄露，建议及时修改密码。", "下线", "logoff.m4a"),
        ASSIGN((byte) 4, "您被指派一个寄件上门取件任务，请尽快上门取件", "指派", "orderAssign.m4a"),
        MANAGE((byte) 5, "您有一条新的消息通知", "您有一条新的消息通知", "message.m4a"),;

        private Byte code;
        private String content;
        private String title;
        private String sound;

        ContentType(Byte code, String content, String title, String sound) {
            this.code = code;
            this.content = content;
            this.title = title;
            this.sound = sound;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }
    }

    /**
     * 派件订单 - 订单状态
     */
    public enum DeliveryOrderStatus {
        sending((byte) 0, "派送中"),
        normalSign((byte) 1, "正常签收"),//寄件
        unusualSign((byte) 2, "异常签收"),
        shopSign((byte) 3, "自提点签收");


        private Byte type;
        private String name;

        private DeliveryOrderStatus(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static String getNameByType(Byte type) {
            for (DeliveryOrderStatus deliveryOrderStatus : values()) {
                if (deliveryOrderStatus.getType().equals(type))
                    return deliveryOrderStatus.getName();
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    /**
     * 派件订单 - 订单类型
     */
    public enum DeliveryOrderType {
        jingang((byte) 0, "金刚拉取"),
        courier((byte) 1, "快递员录入"),
        jgsync((byte) 2, "派件同步");

        private Byte type;
        private String name;

        private DeliveryOrderType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static String getNameByType(Byte type) {
            for (DeliveryOrderType deliveryOrderType : values()) {
                if (deliveryOrderType.getType().equals(type))
                    return deliveryOrderType.getName();
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    /**
     * 派件订单 - 收款类型
     */
    public enum DeliveryOrderPaymentType {
        nothing((byte) 0, "无"),
        freight((byte) 1, "到付"),//寄件
        collection((byte) 2, "代收货款"),//寄件
        freightAndcollection((byte) 3, "到付+代收货款"),
        ;


        private Byte type;
        private String name;

        private DeliveryOrderPaymentType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }


    /**
     * 取件订单类型
     */
    public enum CollectOrderType {
        GRAB((byte) 0, "抢单", ""),
        SYSTEM((byte) 1, "系统指派", ""),
        NOORDER((byte) 2, "无单揽收", "");
        private Byte code;
        private String name;
        private String type;

        CollectOrderType(Byte code, String name, String type) {
            this.name = name;
            this.code = code;
            this.type = type;
        }

        public static String getNameByCode(Byte code) {
            for (CollectOrderType collectOrderType : values()) {
                if (collectOrderType.getCode().equals(code)) {
                    return collectOrderType.getName();
                }
            }
            return null;
        }
        public static String getTypeByCode(Byte code) {
            for (CollectOrderType collectOrderType : values()) {
                if (collectOrderType.getCode().equals(code)) {
                    return collectOrderType.getType();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public enum TimeFields {
        createTime("createTime", "创建时间"),
        updateTime("updateTime", "更新时间"),

        receiveTime("receiveTime", "接单时间"),
        collectTime("collectTime", "取件时间"),

        signTime("signTime", "签收时间"),
        appSignTime("appSignTime", "app签收时间"),
        jGCreateTime("jGCreateTime", "金刚创建时间");

        private String name;
        private String desc;

        TimeFields(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * 取件订单状态
     */
    public enum CollectOrderStatus {
        WAIT_ACCEPT((byte) 0, "待接单"),
        ACCEPTED_WAIT_COLLECT((byte) 1, "接单待揽收"),
        COLLECTED((byte) 2, "已揽收"),
        WAIT_ACCEPT_TIMEOUT((byte) 3, "待接单超时"),
        CANCEL((byte) 4, "取消"),
        JG_PICKUP_SUCCESS((byte) 5, "金刚-揽收成功"),
        JG_PICKUP_FAILED((byte) 6, "金刚-揽收失败"),
        JG_CANCEL((byte) 7, "金刚-取消订单"),
        ;


        private Byte code;
        private String name;

        private CollectOrderStatus(Byte code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getNameByCode(Byte code) {
            for (CollectOrderStatus collectOrderStatus : values()) {
                if (collectOrderStatus.getCode().equals(code)) {
                    return collectOrderStatus.getName();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }
    }

    /**
     *
     */
    public enum CollectSourceType {
        YTO_APP((byte) 0, "圆通APP"),
        JIN_GANG((byte) 1, "金刚系统"),
        NOORDER((byte) 2, "无单揽收"),;

        private Byte code;
        private String name;

        private CollectSourceType(Byte code, String name) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }
    }

    /**
     * 取件订单时效
     */
    public enum CollectOrderAgeing {
        SAME_CITY_SAME_DAY((byte) 0, "同城当天件"),
        SAME_AREA_SAME_DAY((byte) 1, "区域当天件"),
        INTERNAL_MORROW_MORNING((byte) 2, "国内次晨达"),
        INTERNAL_MORROW((byte) 3, "国内次日达"),
        ALTERNATE_DAY((byte) 4, "隔日达"),
        SEVEN_TWO((byte) 5, "72小时件"),
        SPECIAL_PLANE((byte) 6, "专机件");

        private Byte code;
        private String name;

        private CollectOrderAgeing(Byte code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getNameByCode(Byte code) {
            for (CollectOrderAgeing collectOrderAgeing : values()) {
                if (collectOrderAgeing.getCode().equals(code)) {
                    return collectOrderAgeing.getName();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }
    }

    /**
     * 快递单类型
     */
    public enum ExpressType {
        NORMAL((byte) 0, "普通"),
        ELECTRONIC((byte) 1, "电子");

        private Byte code;
        private String name;

        private ExpressType(Byte code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getNameByCode(Byte code) {
            for (ExpressType expressType : values()) {
                if (expressType.getCode().equals(code)) {
                    return expressType.getName();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }
    }

    /**
     * 签收失败原因大类
     */
    public enum SignFailReason {
        SFR1("1", "送无人，无法联系客户"),
        SFR2("2", "运单破损或不规范"),
        SFR3("3", "更址"),
        SFR4("4", "客户拒收"),
        SFR5("5", "违禁品"),
        SFR6("6", "收件客户要求暂放、自提"),
        SFR7("7", "不可抗力"),
        SFR8("8", "特殊区域件"),
        SFR9("9", "企事业单位放假"),
        SFR10("10", "错分"),
        SFR11("11", "快件破损短少不符"),
        SFR12("12", "快件污染"),
        SFR13("13", "签单返还"),
        SFR14("14", "中转费、超标费"),
        SFR15("15", "有单无货"),
        SFR16("16", "代办点派送"),
        SFR17("17", "到付、代收货款金额不符"),
        SFR18("18", "退回件");

        private String code;
        private String name;

        private SignFailReason(String code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getNameByCode(Byte code) {
            for (SignFailReason signFailReason : values()) {
                if (signFailReason.getCode().equals(code)) {
                    return signFailReason.getName();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 签收失败原因明细
     */
    public enum SignFailReasonDetail {
        SFRD101("101", "送去客户处无人,电话联系无人接听,收件人电话:XXXXX(手动输入)"),
        SFRD102("102", "送去客户处无人,电话联系关机,收件人电话:XXXXX(手动输入)"),
        SFRD103("103", "送去客户地址处无此收件人、无联系电话,收件人电话:XXXXX(手动输入)"),
        SFRD104("104", "送去客户处无人,电话为传真并无人接听,收件人电话:XXXXX(手动输入)"),
        SFRD105("105", "无法联系收件人,且门卫不让进,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD106("106", "已送过,收件客户已离职,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD201("201", "未填写收件人姓名、联系方式或收件地址"),
        SFRD202("202", "收件地址不详细、电话号码错误"),
        SFRD203("203", "运单破损、脱落导致收件人联系方式或信息不清楚"),
        SFRD301("301", "送去客户处无人,客户要求改送其他地址,收件人电话:XXXXXX(详细地址PC补录)"),
        SFRD302("302", "派送前与客户电话联系好续送,收件人电话:XXXXX(手动输入)"),
        SFRD303("303", "客户地址搬迁,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD401("401", "送去客户处,快件破损,变形"),
        SFRD402("402", "送去客户处,到付金额,代收货款金额有争议拒收"),
        SFRD403("403", "送去客户处,因派送延误拒收"),
        SFRD404("404", "送去客户处,因其他原因拒收"),
        SFRD501("501", "属于违禁品,正确表述(上传物品图片)(详细表述PC补录)"),
        SFRD601("601", "客户要求暂不派送或等客户通知再派送,收件人电话:XXXXX(手动输入)"),
        SFRD602("602", "客户要求自提,收件人电话:XXXXX(手动输入)"),
        SFRD701("701", "自然灾害、政府道路管制、恶劣天气、重要赛事等不可抗力"),
        SFRD801("801", "收件地址属政府机关、学校、部队、监狱、仓库等特殊单位的快件"),
        SFRD901("901", "收件地址属国家机关、企事业单位放假"),

        SFRD0101("0101", "送去客户处无人,电话联系无人接听,收件人电话:XXXXX(手动输入)"),
        SFRD0102("0102", "送去客户处无人,电话联系关机,收件人电话:XXXXX(手动输入)"),
        SFRD0103("0103", "送去客户地址处无此收件人、无联系电话,收件人电话:XXXXX(手动输入)"),
        SFRD0104("0104", "送去客户处无人,电话为传真并无人接听,收件人电话:XXXXX(手动输入)"),
        SFRD0105("0105", "无法联系收件人,且门卫不让进,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD0106("0106", "已送过,收件客户已离职,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD0201("0201", "未填写收件人姓名、联系方式或收件地址"),
        SFRD0202("0202", "收件地址不详细、电话号码错误"),
        SFRD0203("0203", "运单破损、脱落导致收件人联系方式或信息不清楚"),
        SFRD0301("0301", "送去客户处无人,客户要求改送其他地址,收件人电话:XXXXXX(详细地址PC补录)"),
        SFRD0302("0302", "派送前与客户电话联系好续送,收件人电话:XXXXX(手动输入)"),
        SFRD0303("0303", "客户地址搬迁,请发件公司确认处理方式,收件人电话:XXXXX(手动输入)"),
        SFRD0401("0401", "送去客户处,快件破损,变形"),
        SFRD0402("0402", "送去客户处,到付金额,代收货款金额有争议拒收"),
        SFRD0403("0403", "送去客户处,因派送延误拒收"),
        SFRD0404("0404", "送去客户处,因其他原因拒收"),
        SFRD0501("0501", "属于违禁品,正确表述(上传物品图片)(详细表述PC补录)"),
        SFRD0601("0601", "客户要求暂不派送或等客户通知再派送,收件人电话:XXXXX(手动输入)"),
        SFRD0602("0602", "客户要求自提,收件人电话:XXXXX(手动输入)"),
        SFRD0701("0701", "自然灾害、政府道路管制、恶劣天气、重要赛事等不可抗力"),
        SFRD0801("0801", "收件地址属政府机关、学校、部队、监狱、仓库等特殊单位的快件"),
        SFRD0901("0901", "收件地址属国家机关、企事业单位放假"),

        /*SFRD1001("1001", "记号笔错写导致错分"),
        SFRD1002("1002", "错建包导致错分"),
        SFRD1003("1003", "中心错发导致错分"),
        SFRD1004("1004", "内部错分"),*/
        SFRD1001("1001", "电子面单记号笔错写导致错分"),
        SFRD1002("1002", "普通手写面单记号笔错写导致错分"),
        SFRD1003("1003", "普通打印面单记号笔错写导致错分"),
        SFRD1004("1004", "错建包导致错分"),
        SFRD1005("1005", "中心错分导致错分"),
        SFRD1006("1006", "内部错分"),

        SFRD1101("1101", "送去客户处,外包装完好 、内件破损"),
        SFRD1102("1102", "外包装破损、内件破损"),
        SFRD1103("1103", "外包装完好,客户签字后验货时内件短少、内件不符"),
        SFRD1104("1104", "包装不规范导致快件破损"),
        SFRD1201("1201", "快件在中转环节被污染,污染源单号:XXXXX(手动输入)"),
        SFRD1202("1202", "快件在派送环节被污染,污染源单号:XXXXX(手动输入)"),
        SFRD1301("1301", "签单返还未绑定"),
        SFRD1401("1401", "超标件未带费用,快件重量:XXXXX(手动输入),须带费用:XXXXX(手动输入)"),
        SFRD1501("1501", "有单无货"),
        SFRD1601("1601", "代办点派送"),
        SFRD1701("1701", "到付、代收货款系统录入金额与运单填写金额不符"),
        SFRD1801("1801", "收件客户要求退回,收件人电话:XXXXX(手动输入)"),
        SFRD1802("1802", "发件公司要求退回"),
        SFRD1803("1803", "通缉件要求退回"),

        SFRD1901("1901", "有发未到"),
        SFRD2001("2001", "有到未发"),
        SFRD2101("2101", "有拆包未建包"),
        SFRD2201("2201", "有建包未拆包"),
        SFRD2301("2301", "三段码模板未调整");

        private String code;
        private String name;

        private SignFailReasonDetail(String code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getNameByCode(String code) {
            for (SignFailReasonDetail signFailReasonDetail : values()) {
                if (signFailReasonDetail.getCode().equals(code)) {
                    return signFailReasonDetail.getName();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    //(0:本人签收;1:门卫;2:邮件收发章;3:代办点签收;4:他人代收;5:家人;6:同事;7前台)
    public enum SignTypeEnum{
        selfSign("ST10",(byte)0,"本人签收"),
        familySign("BYOTHER",(byte)5,"家人"),
        colleagueSign("BYOTHER",(byte)6,"同事"),

        guardSign("ST20",(byte)1,"门卫"),
        wySign("ST20",(byte)8,"物业"),

        mailChapterSign("ST30",(byte)2,"邮件收发章"),
        receptionSign("ST30",(byte)7,"前台"),

        agentPointSign("ST40",(byte)3,"转入代办点"),

        othersCollectionSign("BYOTHER",(byte)4,"他人代收"),;

        private String type;
        private String name;
        private Byte code;

        private SignTypeEnum(String type,Byte code, String name) {
            this.type = type;
            this.code = code;
            this.name = name;
        }
        public static  String getTypeByCode(Byte code){
            for (SignTypeEnum signTypeEnum :values()){
                if (signTypeEnum.getCode().equals(code)) {
                    return signTypeEnum.getType();
                }
            }
            return selfSign.getType();
        }
        public static String getTypeByName(String name){
            for (SignTypeEnum signTypeEnum :values()){
                if (signTypeEnum.getName().equals(name)){
                    return signTypeEnum.getType();
                }
            }
            return selfSign.getType();
        }
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }
    }
    /**
     * 短信模版类型
     */
    public enum SmsTemplateType {
        def((byte) 0, "默认"),
        ;


        private Byte type;
        private String name;

        private SmsTemplateType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            SmsTemplateType[] values = values();
            for (SmsTemplateType smsTemplateType : values) {
                map.put(smsTemplateType.type, smsTemplateType.name);
            }
            return map;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }


    /**
     * 手机端平台类型
     */
    public enum PlatformType {
        android((byte) 0, ANDROID),
        ios((byte) 1, IOS),
        android_dz((byte) 2, ANDROID_DZ),
        android_pda((byte) 3, ANDROID_PDA)
        ;


        private Byte type;
        private String name;

        private PlatformType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            PlatformType[] values = values();
            for (PlatformType platformType : values) {
                map.put(platformType.type, platformType.name);
            }
            return map;
        }

        public static PlatformType getByName(String name) {
            if (name.equalsIgnoreCase(IOS))
                return ios;
            else if (name.equalsIgnoreCase(ANDROID))
                return android;
            else if (name.equalsIgnoreCase(ANDROID_DZ))
                return android_dz;
            else
                return null;
        }

        public static String getByCode(byte code) {
            for (PlatformType expressType : values()) {
                if (expressType.getType().equals(code)) {
                    return expressType.getName();
                }
            }
            return "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }


    /**
     * app版本类型
     * 此处type 与角色类型一直 均为 1 、2、3
     */
    public enum UserType {
        customer((byte) 1, "c", "买家版"),
        worker((byte) 2, "w", "快递版"),
        manager((byte) 3, "m", "掌柜版");

        private Byte type;
        private String code;
        private String name;

        private UserType(Byte type, String code, String name) {
            this.name = name;
            this.code = code;
            this.type = type;
        }

        public static UserType getByCode(String code) {
            if (code.equalsIgnoreCase(CUSTOMER_APP_CODE))
                return customer;
            else if (code.equalsIgnoreCase(MANAGER_APP_CODE))
                return manager;
            else if (code.equalsIgnoreCase(COURIER_APP_CODE))
                return worker;
            else
                return null;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            UserType[] values = values();
            for (UserType userType : values) {
                map.put(userType.type, userType.name);
            }
            return map;
        }
    }

    /**
     * 验证码类型（手机端用）
     */
    public enum CaptchaType {
        BIND_PHONE((byte) 0, "绑定手机");

        private Byte type;
        private String name;

        private CaptchaType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static CaptchaType getByType(Byte type) {
            for (CaptchaType captchaType : values()) {
                if (captchaType.getType().equals(type)) {
                    return captchaType;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    /**
     * 全部；派件；揽件
     */
    public enum OrderType {
        ALL, DELIVERY, COLLECT;
    }

    /**
     * 订单操作表 收派件类型
     */
    public enum DCType {
        COLLECT((byte) 0, "取件"),
        DELIVERY((byte) 1, "派件");

        private Byte code;
        private String name;

        DCType(Byte code, String name) {
            this.code = code;
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 订单操作表 操作类型 0:接单;1:取件;2:签收;3:异常签收
     */
    public enum OperationType {
        GRAB((byte) 0, "接单"),
        COLLECT((byte) 1, "取件"),
        SIGN((byte) 2, "签收"),
        SIGN_FAIL((byte) 3, "异常签收"),

        //下面只在更新redis里homePage时用到
        CANCEL((byte) 4, "取消"),
        SYNC((byte) 5, "同步派件"),
        SCANDB((byte) 6, "扫描派件(本地)"),
        SCANJG((byte) 7, "扫描派件(金刚)"),
        NOCOLLECT((byte) 8, "无单取件"),
        ;

        private Byte code;
        private String name;

        OperationType(Byte code, String name) {
            this.code = code;
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum MQType {
        COLLECTROB((byte) 0, "待抢单"),
        COLLECTACCEPT((byte) 1, "有快递员接单"),
        COLLECTACCEPTNO((byte) 2, "无快递员接单"),
        COLLECTPICTH((byte) 3, "有单取件"),
        COLLECTPICTHNO((byte) 4, "无单取件"),
        DELIVERYSIGN((byte) 5, "签收"),
        PUSHMESSAGE((byte) 6, "push message"),;
        private Byte code;
        private String name;

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        MQType(Byte code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    public enum AliQin {
        CodeSinglecall("codeSinglecall", "编号tts单呼"),
        NumberSinglecall("numberSinglecall", "号码tts单呼"),
        CodeSendsms("codeSendsms", "编号发送短信"),
        NumberSendsms("numberSendsms", "号码发送短信"),
        CodeDoublecall("codeDoublecall", "编码双呼"),
        NumberDoublecall("numberDoublecall", "号码双呼"),;
        private String method;
        private String name;

        AliQin(String method, String name) {
            this.method = method;
            this.name = name;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum MailNoOrderType{
        cod("0", "COD"),
        normal("1", "普通订单"),
        app("2", "便携式订单"),
        ret("3", "退货单");
        private String code;
        private String name;
        MailNoOrderType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum GoodSpecial {
        normal((byte) 0, "普通"),
        frangible((byte) 1, "易碎"),
        liquid((byte) 2, "液态"),
        chemicals((byte) 3, "化学品"),
        whitePowder((byte) 4, "白色粉末状"),
        cigarettes((byte) 5, "香烟"),
        other((byte) 6, "其他");

        private Byte code;
        private String name;

        GoodSpecial(Byte code, String name) {
            this.code = code;
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum LoginStatus {
        normal((byte) 0, "正常"),
        timeout((byte) 1, "超时"),
        invalid((byte) 2, "无效"),
        kicked((byte) 3, "被踢"),;

        private Byte code;
        private String name;

        LoginStatus(Byte code, String name) {
            this.code = code;
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum JgDStat {
        sending("35", "派件中"),
        collected("10", "已揽收"),
        transfer("20", "中转中"),
        change("30", "换单"),
        failed("39", "派送失败"),
        success("40", "派送成功"),
        signed("50", "签单返回"),
        ;

        private String code;
        private String name;

        JgDStat(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum JgDOp {
        pda("311", "PDA收件扫描"),
        collected("310", "揽收扫描"),
        ;

        private String code;
        private String name;

        JgDOp(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public enum PayChannelType {
        ALIPAY("01", "支付宝"),
        WECHAT("02", "微信"),
        BAIDU("03", "百度"),
        JINGDONG("04", "京东"),
        ;

        private String code;
        private String name;

        private PayChannelType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getByCode(String code) {
            for (PayChannelType payChannelType : values()) {
                if (payChannelType.getCode().equals(code)) {
                    return payChannelType.getName();
                }
            }
            return "";
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public enum PayStatus {
        paying((byte)1, "支付中"),
        pay_success((byte)2, "支付成功"),
        pay_failed((byte)3, "支付失败"),
        canceling((byte)4, "取消中"),
        cancel_success((byte)5, "取消成功"),
        cancel_failed((byte)6, "取消失败"),
        returning((byte)7, "退款中"),
        return_success((byte)8, "退款成功"),
        return_failed((byte)9, "退款失败"),
        ;

        private Byte code;
        private String name;

        private PayStatus(Byte code, String name) {
            this.code = code;
            this.name = name;
        }

        public static PayStatus getByCode(byte code) {
            for (PayStatus status : values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            return null;
        }

        public static String getNameByCode(byte code) {
            for (PayStatus status : values()) {
                if (status.getCode() == code) {
                    return status.getName();
                }
            }
            return "";
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 手机端平台类型
     */
    public enum PayType {
        barcode((byte) 1, "bar_code", "条形支付"),
        scancode((byte) 2, "scan_code", "扫码支付"),
        wavecode((byte) 3, "wave_code", "声波支付");


        private Byte code;
        private String name;
        private String desc;

        private PayType(Byte code, String name, String desc) {
            this.name = name;
            this.code = code;
            this.desc = desc;
        }

        public static PayType getByCode(Byte code) {
            if(barcode.getCode() == code)
                return barcode;
            else if(scancode.getCode() == code)
                return scancode;
            else if(wavecode.getCode() == code)
                return wavecode;
            else
                return null;
        }

        public static String getDescByCode(byte code) {
            for (PayType payType : values()) {
                if (payType.getCode() == code) {
                    return payType.getDesc();
                }
            }
            return "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum CostType {
        DF("01", "到付"),
        DS("02", "代收"),
        DFDS("03", "到付+代收货款"),
        NO_ORDER("04", "无单支付"),
        HAS_ORDER("05", "有单支付"),
        ;

        private String code;
        private String name;

        CostType(String code, String name) {
            this.name = name;
            this.code = code;
        }

        public static String getByCode(String code) {
            for (CostType costType : values()) {
                if (costType.getCode().equals(code)) {
                    return costType.getName();
                }
            }
            return "";
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum ManagerRole {
        provinceCode((byte) 0, "省区网管"),
        companyCode((byte) 1, "分公司"),
        branchCode((byte) 2, "分部"),
        courier((byte) 3, "快递员"),
        ;

        private Byte code;
        private String name;

        private ManagerRole(Byte code, String name) {
            this.name = name;
            this.code = code;
        }
        public static ManagerRole getByCode(Byte code) {
            for (ManagerRole type : values()) {
                if (type.getCode() == code) {
                    return type;
                }
            }
            return ManagerRole.branchCode;
        }
        public static ManagerRole getNextLevel(byte code){
            switch(code) {
                case  0 : return companyCode;
                case  1 : return branchCode;
                case  2 : return courier;

                default : return null;
            }
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put(-1, "全部");
            ManagerRole[] values = values();
            for (ManagerRole managerRole : values) {
                if (managerRole.getCode() == 3) continue;
                map.put(managerRole.code, managerRole.name);
            }
            return map;
        }
        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public enum ManagerDataType {
        branch((byte) 0, "网点"),
        courier((byte) 1, "快递员");

        private Byte code;
        private String name;

        private ManagerDataType(Byte code, String name) {
            this.name = name;
            this.code = code;
        }

        public Byte getCode() {
            return code;
        }

        public void setCode(Byte code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 管理app验证码类型（手机端用）
     */
    public enum ManageCaptchaType {
        REG((byte) 0, "注册"),
        FORGET_PWD((byte) 1, "忘记密码");

        private Byte type;
        private String name;

        private ManageCaptchaType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    /**
     * 管理app查询返回类型
     */
    public enum RespDataType {
        ORGCODE((byte) 0, "网点编码"),
        JOBNO((byte) 1, "工号"),
        DATE((byte) 2, "日期"),
        ;

        private Byte type;
        private String name;

        RespDataType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }
    public enum GroupByDataType {
        DAY((byte) 0, "按天"),
        JOBNOORREGION((byte) 1, "按工号或网点")
        ;

        private Byte type;
        private String name;

        GroupByDataType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    public enum PushType {
        ASSIGN((byte) 1, "指定"),
        ALL((byte) 0, "全部")
        ;

        private Byte type;
        private String name;

        PushType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    public enum ManagerStatus {
        WAIT_VERIFY((byte) 0, "未审核"),
        VERIFY((byte) 1, "已审核"),
        CLOSE((byte) 2, "关闭")
        ;

        private Byte type;
        private String name;

        ManagerStatus(Byte type, String name) {
            this.name = name;
            this.type = type;
        }
        public static String getNameByType(Byte type) {
            for (ManagerStatus status : values()) {
                if (status.getType() == type) {
                    return status.getName();
                }
            }
            return "";
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            ManagerStatus[] values = values();
            for (ManagerStatus managerStatus : values) {
                map.put(managerStatus.type, managerStatus.name);
            }
            return map;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }
    public enum ProvinceAuthPatternEnum {
        none((byte) 0, "无验证模式"),
        gpo((byte) 1, "邮政总局通用模式"),
        zheJiang((byte) 2, "浙江模式")
        ;

        private Byte type;
        private String name;

        ProvinceAuthPatternEnum(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            ProvinceAuthPatternEnum[] values = values();
            for (ProvinceAuthPatternEnum obj : values) {
                map.put(obj.type, obj.name);
            }
            return map;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    public enum CertificateType {
        IDCARD((byte) 0, "居民身份证、临时居民身份证、临时或者户口簿"),
        SOLDIER((byte) 1, "中国人民解放军身份证件、中国人民武装警察身份证件"),
        GAT((byte) 2, "港澳居民来往内地通行证、台湾居民来往内地通行证或者其他有效旅行证件"),
        PASSPORT((byte) 3, "外国公民护照"),
        OHTER((byte) 4, "法律、行政规和国家定的其他有效身份证件"),
        ;

        private Byte type;
        private String name;

        CertificateType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            CertificateType[] values = values();
            for (CertificateType obj : values) {
                map.put(obj.type, obj.name);
            }
            return map;
        }

        public static String getNameByType(Byte type) {
            for (CertificateType c : CertificateType.values()) {
                if (c.getType().equals(type)) {
                    return c.getName();
                }
            }
            return null;
        }

        public static Byte getTypeByName(String name) {
            for (CertificateType c : CertificateType.values()) {
                if (c.getName().equals(name)) {
                    return c.getType();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }

    public enum SexType {
        WOMAN((byte) 0, "女"),
        MAN((byte) 1, "男"),
        ;

        private Byte type;
        private String name;

        SexType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            SexType[] values = values();
            for (SexType obj : values) {
                map.put(obj.type, obj.name);
            }
            return map;
        }

        public static String getNameByType(Byte type) {
            for (SexType c : SexType.values()) {
                if (c.getType().equals(type)) {
                    return c.getName();
                }
            }
            return null;
        }

        public static Byte getTypeByName(String name) {
            for (SexType c : SexType.values()) {
                if (c.getName().equals(name)) {
                    return c.getType();
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getType() {
            return type;
        }

        public void setType(Byte type) {
            this.type = type;
        }
    }
}
