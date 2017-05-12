package com.courier.core.vModel;

import com.courier.commons.entity.BaseEntity;


/**
 * Created by admin on 2015/11/4.
 */
public class VHomePage extends BaseEntity {

    private static final long serialVersionUID = -5244326383483278916L;
    private int signNo = 0;
    private int collectNo = 0;
    private int problemNo = 0;
    private Integer sendingNo = 0;
    private int collectingNo = 0;
    private double collectAmount = 0.00;


    public VHomePage() {
    }

    public VHomePage(int collectNo, int signNo) {
        this.collectNo = collectNo;
        this.signNo = signNo;
    }



    public int getCollectingNo() {
        return collectingNo;
    }

    public void setCollectingNo(int collectingNo) {
        this.collectingNo = collectingNo;
    }

    public int getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(int collectNo) {
        this.collectNo = collectNo;
    }

    public int getProblemNo() {
        return problemNo;
    }

    public void setProblemNo(int problemNo) {
        this.problemNo = problemNo;
    }

    public Integer getSendingNo() {
        return sendingNo;
    }

    public void setSendingNo(Integer sendingNo) {
        this.sendingNo = sendingNo;
    }

    public int getSignNo() {
        return signNo;
    }

    public void setSignNo(int signNo) {
        this.signNo = signNo;
    }

    public double getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(double collectAmount) {
        this.collectAmount = collectAmount;
    }

}
