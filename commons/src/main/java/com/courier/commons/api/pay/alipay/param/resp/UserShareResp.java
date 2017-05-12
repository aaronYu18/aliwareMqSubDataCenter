package com.courier.commons.api.pay.alipay.param.resp;

import com.courier.commons.api.pay.alipay.param.AlipayResp;

/**
 * Created by ryan on 16/3/29.
 */
public class UserShareResp extends AlipayResp {

    private static final long serialVersionUID = -2778812897801925201L;
    /** 用户头像*/
    private String avatar;
    /** 用户的userId*/
    private String user_id;
    /** 用户类型（1/2） 1代表公司账户2代表个人账户*/
    private String user_type_value;
    /** 用户状态（Q/T/B/W）。 Q代表快速注册用户 T代表已认证用户 B代表被冻结账户 W代表已注册，未激活的账户*/
    private String user_status;
    /** 公司名称（用户类型是公司类型时公司名称才有此字段）。*/
    private String firm_name;
    /** 用户的真实姓名。*/
    private String real_name;
    /** 用户支付宝账号绑定的邮箱地址*/
    private String email;
    /** 证件号码*/
    private String cert_no;
    /** 性别（F：女性；M：男性）*/
    private String gender;
    /** 电话号码。*/
    private String phone;
    /** 手机号码。*/
    private String mobile;
    /** 是否通过实名认证。T是通过 F是没有实名认证*/
    private String is_certified;
    /** T为是银行卡认证，F为非银行卡认证。*/
    private String is_bank_auth;
    /** T为是身份证认证，F为非身份证认证。*/
    private String is_id_auth;
    /** T为是手机认证，F为非手机认证*/
    private String is_mobile_auth;
    /** T为通过营业执照认证，F为没有通过*/
    private String is_licence_auth;
    /** 0:身份证 1:护照 2:军官证 3:士兵证 4:回乡证 5:临时身份证 6:户口簿 7:警官证 8:台胞证 9:营业执照 10其它证件*/
    private String cert_type_value;
    /** 收货地址的联系人固定电话*/
    private String deliver_phone;
    /** 收货地址的联系人移动电话*/
    private String deliver_mobile;
    /** 收货人全称*/
    private String deliver_fullname;
    /** 省份名称。*/
    private String province;
    /** 市名称*/
    private String city;
    /** 区县名称*/
    private String area;
    /** 详细地址*/
    private String address;
    /** 邮政编码。*/
    private String zip;
    /** 收货人所在省份*/
    private String deliver_province;
    /** 收货人所在省份*/
    private String deliver_city;
    /** 收货人所在区县*/
    private String deliver_area;
    /** 是否默认收货地址，暂时不返回值*/
    private String default_deliver_address;
    /** 区域编码，暂时不返回值*/
    private String address_code;
    /** 区域编码，暂时不返回值*/
    private String is_student_certified;
    /** 区域编码，暂时不返回值*/
    private String is_certify_grade_a;
    /** 支付宝用户ID*/
    private String alipay_user_id;
    /** 用户生日*/
    private String birthday;
    /** 用户昵称*/
    private String nick_name;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type_value() {
        return user_type_value;
    }

    public void setUser_type_value(String user_type_value) {
        this.user_type_value = user_type_value;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCert_no() {
        return cert_no;
    }

    public void setCert_no(String cert_no) {
        this.cert_no = cert_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_certified() {
        return is_certified;
    }

    public void setIs_certified(String is_certified) {
        this.is_certified = is_certified;
    }

    public String getIs_bank_auth() {
        return is_bank_auth;
    }

    public void setIs_bank_auth(String is_bank_auth) {
        this.is_bank_auth = is_bank_auth;
    }

    public String getIs_id_auth() {
        return is_id_auth;
    }

    public void setIs_id_auth(String is_id_auth) {
        this.is_id_auth = is_id_auth;
    }

    public String getIs_mobile_auth() {
        return is_mobile_auth;
    }

    public void setIs_mobile_auth(String is_mobile_auth) {
        this.is_mobile_auth = is_mobile_auth;
    }

    public String getIs_licence_auth() {
        return is_licence_auth;
    }

    public void setIs_licence_auth(String is_licence_auth) {
        this.is_licence_auth = is_licence_auth;
    }

    public String getCert_type_value() {
        return cert_type_value;
    }

    public void setCert_type_value(String cert_type_value) {
        this.cert_type_value = cert_type_value;
    }

    public String getDeliver_phone() {
        return deliver_phone;
    }

    public void setDeliver_phone(String deliver_phone) {
        this.deliver_phone = deliver_phone;
    }

    public String getDeliver_mobile() {
        return deliver_mobile;
    }

    public void setDeliver_mobile(String deliver_mobile) {
        this.deliver_mobile = deliver_mobile;
    }

    public String getDeliver_fullname() {
        return deliver_fullname;
    }

    public void setDeliver_fullname(String deliver_fullname) {
        this.deliver_fullname = deliver_fullname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDeliver_province() {
        return deliver_province;
    }

    public void setDeliver_province(String deliver_province) {
        this.deliver_province = deliver_province;
    }

    public String getDeliver_city() {
        return deliver_city;
    }

    public void setDeliver_city(String deliver_city) {
        this.deliver_city = deliver_city;
    }

    public String getDeliver_area() {
        return deliver_area;
    }

    public void setDeliver_area(String deliver_area) {
        this.deliver_area = deliver_area;
    }

    public String getDefault_deliver_address() {
        return default_deliver_address;
    }

    public void setDefault_deliver_address(String default_deliver_address) {
        this.default_deliver_address = default_deliver_address;
    }

    public String getAddress_code() {
        return address_code;
    }

    public void setAddress_code(String address_code) {
        this.address_code = address_code;
    }

    public String getIs_student_certified() {
        return is_student_certified;
    }

    public void setIs_student_certified(String is_student_certified) {
        this.is_student_certified = is_student_certified;
    }

    public String getIs_certify_grade_a() {
        return is_certify_grade_a;
    }

    public void setIs_certify_grade_a(String is_certify_grade_a) {
        this.is_certify_grade_a = is_certify_grade_a;
    }

    public String getAlipay_user_id() {
        return alipay_user_id;
    }

    public void setAlipay_user_id(String alipay_user_id) {
        this.alipay_user_id = alipay_user_id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
