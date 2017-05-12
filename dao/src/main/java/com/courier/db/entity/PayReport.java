package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "payReport")
public class PayReport extends BaseEntity {

    public static final String GROUP_FILED_USER_ID = "userId";
    private static final long serialVersionUID = 2173704032208136991L;
    private Long id;
    private Long userId;
    private Double collectionAmount;        // 收款额
    private Double refundAmount;            // 退款额
    private Double cancelAmount;           // 用户取消额
    private Double sysCancelAmount;        // 系统取消额
    private Date countTime;
    private Byte type;                    // 类型(1:按天 2:按月 3:用户历史总数 4:全网平均 5:动态每月统计 6:动态全网平均)


    public PayReport() {
    }

    public PayReport(Double cancelAmount, Double collectionAmount, Date countTime, Double refundAmount, Double sysCancelAmount, Byte type, Long userId) {
        this.cancelAmount = cancelAmount;
        this.collectionAmount = collectionAmount;
        this.countTime = countTime;
        this.refundAmount = refundAmount;
        this.sysCancelAmount = sysCancelAmount;
        this.type = type;
        this.userId = userId;
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
    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    @Column(name = "countTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }
    @Column
    public Double getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(Double collectionAmount) {
        this.collectionAmount = collectionAmount;
    }
    @Column
    public Double getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(Double cancelAmount) {
        this.cancelAmount = cancelAmount;
    }
    @Column
    public Double getSysCancelAmount() {
        return sysCancelAmount;
    }

    public void setSysCancelAmount(Double sysCancelAmount) {
        this.sysCancelAmount = sysCancelAmount;
    }

}
