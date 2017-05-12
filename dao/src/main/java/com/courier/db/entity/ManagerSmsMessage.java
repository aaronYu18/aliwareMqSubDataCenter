package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on 16/4/21.
 */
@Table(name="managerSmsMessage")
public class ManagerSmsMessage extends BaseEntity {
    private static final long serialVersionUID = -6249868674728391770L;

    private Long id;
    private String content;     //短信内容
    private String phone;       //接收短信手机号码
    private String errorInfo;   //错误信息
    private Byte priority;      //短信优先级 1，2，3，4，5数字越大，等级越高
    private Byte status;        //短信状态(1:发送中，2:已发送，3:发送失败)
    private Byte type;          //短信类型(0:绑定手机验证码 1:阿里短信，2:阿里电话)
    private String branchCode;  //分公司代码
    private String terminalCode;//分部代码
    private String orgCode;     //网点代码
    private String jobNo;       //工号
    private String aliResponseCode;//阿里返回结果code

    @Column
    public String getAliResponseCode() {
        return aliResponseCode;
    }

    public void setAliResponseCode(String aliResponseCode) {
        this.aliResponseCode = aliResponseCode;
    }

    @Column
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
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
    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
}
