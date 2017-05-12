package com.courier.db.entity;

import com.courier.sdk.constant.Enumerate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on 16/4/21.
 */
@Table(name="manager")
public class Manager extends BaseEntity {
    private static final long serialVersionUID = 2059915363286747760L;

    private Long id;
    private String jobNo;       //工号
    private String username;    //用户名
    private String nickname;    //昵称
    private String password;    //密码
    private String orgCode;     //网点代码
    private String phone;       //手机
    private String email;       //邮箱
    private Byte role;          //角色(0:省区网管;1:分公司;2:分部)
    private Byte status = Enumerate.ManagerStatus.WAIT_VERIFY.getType();     //状态(0:未审核;1:已审核;2.关闭)

    private String roleName;
    private String statusName;

    private Integer verifyNo=0;    //相同网点编码已审核帐号个数
    private Integer signNo=0;     //昨日签收
    private Integer collectNo=0;  //昨日取件
    private Integer failNo=0;     //昨日异常签收
    private Byte orgType;  //网点类型
    private String orgTypeStr;  //网点类型
    private String orgName; //网点名称
    private String province;    //省code
    private String provinceName;    //省
    private String jobNoName;   //工号对应姓名
    private String jobNoOrgTypeStr;  //工号所在网点类型
    private String jobNoOrgCode;  //工号所在网点编码
    private String jobNoOrgName;  //工号所在网点名称

    public Manager() {
    }

    public Manager(String jobNo, String nickname, String orgCode, String password, String phone, String username) {
        this.jobNo = jobNo;
        this.nickname = nickname;
        this.orgCode = orgCode;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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


    public Integer getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Integer collectNo) {
        this.collectNo = collectNo;
    }

    public Integer getFailNo() {
        return failNo;
    }

    public void setFailNo(Integer failNo) {
        this.failNo = failNo;
    }

    public String getJobNoOrgCode() {
        return jobNoOrgCode;
    }

    public void setJobNoOrgCode(String jobNoOrgCode) {
        this.jobNoOrgCode = jobNoOrgCode;
    }

    public String getJobNoOrgName() {
        return jobNoOrgName;
    }

    public void setJobNoOrgName(String jobNoOrgName) {
        this.jobNoOrgName = jobNoOrgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Byte getOrgType() {
        return orgType;
    }

    public void setOrgType(Byte orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeStr() {
        return orgTypeStr;
    }

    public void setOrgTypeStr(String orgTypeStr) {
        this.orgTypeStr = orgTypeStr;
    }

    public Integer getSignNo() {
        return signNo;
    }

    public void setSignNo(Integer signNo) {
        this.signNo = signNo;
    }

    public String getJobNoOrgTypeStr() {
        return jobNoOrgTypeStr;
    }

    public void setJobNoOrgTypeStr(String jobNoOrgTypeStr) {
        this.jobNoOrgTypeStr = jobNoOrgTypeStr;
    }

    public String getJobNoName() {
        return jobNoName;
    }

    public void setJobNoName(String jobNoName) {
        this.jobNoName = jobNoName;
    }

    public Integer getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(Integer verifyNo) {
        this.verifyNo = verifyNo;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
