package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "report")
public class Report extends BaseEntity {

    private static final long serialVersionUID = 8054812050077695516L;
    public static final String GROUP_FILED_USERID = "userId";
    private Long id;
    private Long userId;
    private Double sendNo = 0.;             // 派件数
    private Double collectNo = 0.;          // 收件数
    private Double failedNo = 0.;           // 失败数
    private Date countTime;             // 统计时间
    private Byte type;                  // 类型(0:按小时 1:按天 2:按月 3:用户历史总数 2:全网平均)

    private String jobNo;
    private Double sendingNo = 0.;
    private Double collectingNo = 0.;
    private Double dayAmount = 0.;

    public Report() {
    }

    public Report(Long userId, Double collectNo, Date countTime, Double failedNo, Double sendNo, Byte type) {
        this.userId = userId;
        this.collectNo = collectNo;
        this.countTime = countTime;
        this.failedNo = failedNo;
        this.sendNo = sendNo;
        this.type = type;
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
    public Double getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Double collectNo) {
        this.collectNo = collectNo;
    }
    @Column
    public Double getFailedNo() {
        return failedNo;
    }

    public void setFailedNo(Double failedNo) {
        this.failedNo = failedNo;
    }
    @Column
    public Double getSendNo() {
        return sendNo;
    }

    public void setSendNo(Double sendNo) {
        this.sendNo = sendNo;
    }

    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
    @Column
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Double getCollectingNo() {
        return collectingNo;
    }

    public void setCollectingNo(Double collectingNo) {
        this.collectingNo = collectingNo;
    }

    public Double getSendingNo() {
        return sendingNo;
    }

    public void setSendingNo(Double sendingNo) {
        this.sendingNo = sendingNo;
    }

    public Double getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(Double dayAmount) {
        this.dayAmount = dayAmount;
    }
}
