package com.courier.core.vModel;

import com.courier.commons.entity.BaseEntity;
import com.courier.commons.vModel.VManagerSubCurrentData;
import com.courier.db.dao.crud.Page;


/**
 * Created by admin on 2015/11/4.
 */
public class VManagerCurrentData extends BaseEntity {

    private static final long serialVersionUID = -5244326383483278916L;
    private String orgCode;         // todo 概况对应的orgCode
    private int signNo = 0;
    private int collectNo = 0;
    private int problemNo = 0;
    private int sendingNo = 0;
    private int collectingNo = 0;
    private int totalUserNo = 0;
    private Page<VManagerSubCurrentData> page;

    public VManagerCurrentData() {
    }

    public VManagerCurrentData(int collectNo, int signNo) {
        this.collectNo = collectNo;
        this.signNo = signNo;
    }

    public VManagerCurrentData(int collectingNo, int collectNo, int problemNo, int sendingNo, int signNo, int totalUserNo, String orgCode) {
        this.collectingNo = collectingNo;
        this.collectNo = collectNo;
        this.problemNo = problemNo;
        this.sendingNo = sendingNo;
        this.signNo = signNo;
        this.totalUserNo = totalUserNo;
        this.orgCode = orgCode;
    }

    public VManagerCurrentData(Page<VManagerSubCurrentData> page) {
        this.page = page;
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

    public int getSendingNo() {
        return sendingNo;
    }

    public void setSendingNo(int sendingNo) {
        this.sendingNo = sendingNo;
    }

    public int getSignNo() {
        return signNo;
    }

    public void setSignNo(int signNo) {
        this.signNo = signNo;
    }

    public int getTotalUserNo() {
        return totalUserNo;
    }

    public void setTotalUserNo(int totalUserNo) {
        this.totalUserNo = totalUserNo;
    }

    public Page<VManagerSubCurrentData> getPage() {
        return page;
    }

    public void setPage(Page<VManagerSubCurrentData> page) {
        this.page = page;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
