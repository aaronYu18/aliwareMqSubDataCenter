package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "smsMessage")
public class SmsMessage extends BaseEntity {

    private static final long serialVersionUID = 197722173204732412L;
    private Long id;
    private String content;             //   短信内容
    private String phone;               //   接收短信手机号码
    private String errorInfo;           //   错误信息
    private Byte priority;              //   短信优先级 1，2，3，4，5数字越大，等级越高
    private Byte status;                //   短信状态(1:发送中，2:已发送，3:发送失败)
    private Byte type;                  //   短信类型(0:绑定手机验证码)
    private String orgCode;             //   网点代码
    private String jobNo;               //   工号
    private String aliResponseCode;     //   阿里返回结果code


    String createOrgCode="210152";
    String createUserCode="";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }
    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Column
    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
    @Column
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column
    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }
    @Column
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Column
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
    @Column
    public String getAliResponseCode() {
        return aliResponseCode;
    }

    public void setAliResponseCode(String aliResponseCode) {
        this.aliResponseCode = aliResponseCode;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }
}
