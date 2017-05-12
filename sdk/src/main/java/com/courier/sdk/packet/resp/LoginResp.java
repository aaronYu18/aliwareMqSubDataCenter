package com.courier.sdk.packet.resp;
import com.courier.sdk.common.IdEntity;

/**
 * Created by ryan on 15/6/16.
 */
public class LoginResp extends IdEntity {

    private static final long serialVersionUID = 7501794503423667620L;
    private String uuid;         //UUID
    private String nickname;     //昵称
    private String mobile;       //手机
    private String bankCardNo;   //银行卡号
    private String bankName;     //银行名称
    private String bankBranch;   //银行分支
    private boolean isBindAliPay;       // 支付宝
    private boolean isBindWechatPay;    // 微信
    private boolean isBindBaiduPay;     // 百度
    private boolean isBindJDPay;        // 京东
    private Byte collectPattern;        //取件验证模式：0 : 无需验证, 1 : 全国通用模式, 2 : 浙江模式
    private Long userId;    //用户id
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public boolean isBindAliPay() {
        return isBindAliPay;
    }

    public void setBindAliPay(boolean isBindAliPay) {
        this.isBindAliPay = isBindAliPay;
    }

    public boolean isBindBaiduPay() {
        return isBindBaiduPay;
    }

    public void setBindBaiduPay(boolean isBindBaiduPay) {
        this.isBindBaiduPay = isBindBaiduPay;
    }

    public boolean isBindWechatPay() {
        return isBindWechatPay;
    }

    public void setBindWechatPay(boolean isBindWechatPay) {
        this.isBindWechatPay = isBindWechatPay;
    }

    public boolean isBindJDPay() {
        return isBindJDPay;
    }

    public void setBindJDPay(boolean isBindJDPay) {
        this.isBindJDPay = isBindJDPay;
    }

    public Byte getCollectPattern() {
        return collectPattern;
    }

    public void setCollectPattern(Byte collectPattern) {
        this.collectPattern = collectPattern;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
