package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "user")
public class User extends BaseEntity{

    private static final long serialVersionUID = 1679991870887086266L;
    private Long id;
    private String orgCode;             //     网点代码
    private String jobNo;               //     工号
    private String username;            //     用户名
    private String nickname;            //     昵称
    private String bankName;            //     开户行
    private String bankBranchName;      //     支行名称
    private String bankCardNo;          //     银行卡号
    private String headPic;             //     头像
    private String password;            //     密码
    private String phone;           	//     手机
    private String email;               //     邮箱
    private Integer bindPay;            //     付款类型绑定情况（顺序： |百度|微信|支付宝）

    // todo 以下字段不做数据库映射
    private boolean isBindAliPay = false;       // 支付宝
    private boolean isBindWechatPay = false;    // 微信
    private boolean isBindBaiduPay = false;     // 百度
    private boolean isBindJDPay = false;        // 京东

    public User() {
    }

    public User(String orgCode, String jobNo) {
        this.orgCode = orgCode;
        this.jobNo = jobNo;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column
    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    @Column
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @Column
    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
    @Column
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column
    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    @Column
    public Integer getBindPay() {
        return bindPay;
    }

    public void setBindPay(Integer bindPay) {
        this.bindPay = bindPay;
    }
    @Transient
    public boolean isBindAliPay() {
        return isBindAliPay;
    }

    public void setBindAliPay(boolean isBindAliPay) {
        this.isBindAliPay = isBindAliPay;
    }
    @Transient
    public boolean isBindBaiduPay() {
        return isBindBaiduPay;
    }

    public void setBindBaiduPay(boolean isBindBaiduPay) {
        this.isBindBaiduPay = isBindBaiduPay;
    }
    @Transient
    public boolean isBindWechatPay() {
        return isBindWechatPay;
    }

    public void setBindWechatPay(boolean isBindWechatPay) {
        this.isBindWechatPay = isBindWechatPay;
    }
    @Transient
    public boolean isBindJDPay() {
        return isBindJDPay;
    }

    public void setBindJDPay(boolean isBindJDPay) {
        this.isBindJDPay = isBindJDPay;
    }

}
