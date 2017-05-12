package com.courier.commons.enums;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by admin on 2015/10/31.
 */
public enum CommonEnum {

    VALID((byte)1, "有效"), INVALID((byte)0, "无效");

    public enum BoolEnum {
        TRUE((byte)1, "是"), FALSE((byte)0, "否");
        private Byte code;
        private String name;

        private BoolEnum(Byte code, String name) {
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

    public enum JgStatus{
        received_success((byte)0, "接单成功"),
        not_received((byte)1, "未接单"),
        received_failed((byte)2, "接单失败"),
        cancel((byte)3, "取消订单"),
        pickup_success((byte)4, "揽收成功"),
        pickup_failed((byte)5, "揽收失败");

        private Byte code;
        private String name;

        private JgStatus(Byte code, String name) {
            this.name = name;
            this.code = code;
        }
        public static JgStatus findById(Byte code){
            if (StringUtils.isEmpty(code))return  null;
            for (JgStatus status :values()){
                if (status.getCode()==code)
                    return status;
            }
            return null;
        }

        public static Byte getByteCode(JgStatus jgStatus){
            return Byte.valueOf(jgStatus.getCode());
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
    public enum ApiPermission {
        COLLECT_ROB((byte)0, "取件-抢单通知"),
        COLLECT_SYSTEM_ALLOCATION((byte)1, "取件-系统分配"),
        COLLECT_CANCEL((byte)2, "取件-取消订单"),
        SEND_PUSH((byte)3, "发送push"),
        GET_COURIER_GPS((byte)4, "获取用户gps"),
        ;

        private Byte code;
        private String name;

        ApiPermission(Byte code, String name) {
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

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<>();
            if (hasAll) map.put("", "全部");
            ApiPermission[] values = values();
            for (ApiPermission apiPermission : values) {
                map.put(apiPermission.code, apiPermission.name);
            }
            return map;
        }
    }

    public enum JGInterfaceStatus {
        SUCCESS(200, "正常"),
        EXCEPTION(-1, "第三协议的错误或参数错误");

        private Integer code;
        private String name;

        JGInterfaceStatus(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
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
     * 省 市 县
     */
    public enum RegionEnum {
        PROVINCE, CITY, AREA;
    }
    /**
     * 验证码类型（backend端用）
     */
    public enum CodeType {
        forgetPwd((byte)1, "忘记密码验证码"),
        register((byte)2, "注册验证码");


        private Byte type;
        private String name;

        private CodeType(Byte type, String name) {
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
     * 报表类型
     */
    public enum ReportEnum {
//        HOUR((byte)0, "小时"),
        DAY((byte)1, "用户天总计"),
        MONTH((byte)2, "用户月总计"),
        USER_TOTAL((byte)3, "用户历史总计"),
        AVG((byte)4, "月全网平均"),
        MONTH_TEMP((byte)5, "用户动态当月总计"),
        AVG_TEMP((byte)6, "当月动态全网平均"),
        ONE_DAY_ALL((byte)7, "每天全网总计"),
        ;
        private Byte code;
        private String desc;

        private ReportEnum(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put(-1, "全部");
            ReportEnum[] values = values();
            for (ReportEnum reportEnum : values) {
                map.put(reportEnum.code, reportEnum.desc);
            }
            return map;
        }
    }
    /**
     * 网点数据-报表类型
     */
    public enum OrgReportEnum {
        terminal((byte)1, "分部|直属网点"),
        branch((byte)2, "分公司"),
        province((byte)3, "省区"),
        terminal_avg_temp((byte)4, "当月动态分部平均"),
        branch_avg_temp((byte)5, "当月动态分公司平均"),
        province_avg_temp((byte)6, "当月动态省管平均"),
        terminal_avg((byte)7, "月分部平均"),
        branch_avg((byte)8, "月分公司平均"),
        province_avg((byte)9, "月省管平均"),
        terminal_month_temp((byte)10, "当月动态分部总计"),
        branch_month_temp((byte)11, "当月动态分公司总计"),
        province_month_temp((byte)12, "当月动态省管总计"),
        terminal_month((byte)13, "月分部总计"),
        branch_month((byte)14, "月分公司总计"),
        province_month((byte)15, "月省管总计"),
        ;

        private Byte code;
        private String desc;

        private OrgReportEnum(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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

    /**
     * 报表类型
     */
    public enum PayReportEnum {
        DAY((byte)1, "用户天总计"),
        MONTH((byte)2, "用户月总计"),
        USER_TOTAL((byte)3, "用户历史总计"),
        AVG((byte)4, "月全网平均"),
        MONTH_TEMP((byte)5, "用户动态当月总计"),
        AVG_TEMP((byte)6, "当月动态全网平均"),
        ONE_DAY_ALL((byte)7, "每天全网总计")
        ;
        private Byte code;
        private String desc;

        private PayReportEnum(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put(-1, "全部");
            PayReportEnum[] values = values();
            for (PayReportEnum reportEnum : values) {
                map.put(reportEnum.code, reportEnum.desc);
            }
            return map;
        }
    }

    /**
     * 省 市 县
     */
    public enum BranchType {
        TERMINAL((byte)0, "分部"),
        BRANCH((byte)1, "分公司")
        ;
        private Byte code;
        private String desc;

        private BranchType(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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

        public static String getNameByCode(Byte code) {
            for (BranchType branchType : values()) {
                if (branchType.getCode().equals(code))
                    return branchType.getDesc();
            }
            return null;
        }
    }
    /**
     *
     */
    public enum PayOperType {
        pay_request((byte)1, "支付请求"),
        pay_success((byte)2, "支付成功"),
        pay_failed((byte)3, "支付失败"),
        return_request((byte)4, "退款请求"),
        return_success((byte)5, "退款成功"),
        return_failed((byte)6, "退款失败"),
        cancel_request((byte)7, "用户取消请求"),
        cancel_success((byte)8, "用户取消成功"),
        cancel_failed((byte)9, "用户取消失败"),
        system_cancel_request((byte)10, "系统取消请求"),
        system_cancel_success((byte)11, "系统取消成功"),
        system_cancel_failed((byte)12, "系统取消失败"),
        ;
        private Byte code;
        private String desc;

        private PayOperType(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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
    public enum  EmployeeEnumType{
        onjob((byte)0, "在职"),
        dismissing((byte)2, "解聘员工"),
        retirement((byte)3, "退休"),
        removed((byte)4, "调离"),;
        private Byte code;
        private String desc;
        private EmployeeEnumType(Byte code, String desc) {
            this.code = code;
            this.desc = desc;
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


    /**
     * 手机端平台类型
     */
    public enum ManagePlatformType {
        android((byte) 0, "android"),
        ios((byte) 1, "ios");


        private Byte type;
        private String name;

        private ManagePlatformType(Byte type, String name) {
            this.name = name;
            this.type = type;
        }

        public static Map<Object, String> getMap() {
            return getMap(false);
        }

        public static Map<Object, String> getMap(boolean hasAll) {
            Map<Object, String> map = new LinkedHashMap<Object, String>();
            if (hasAll) map.put("", "全部");
            ManagePlatformType[] values = values();
            for (ManagePlatformType platformType : values) {
                map.put(platformType.type, platformType.name);
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

    private Byte code;
    private String desc;

    private CommonEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
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
