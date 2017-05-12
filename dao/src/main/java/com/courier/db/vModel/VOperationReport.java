package com.courier.db.vModel;

/**
 * Created by vincent on 16/3/10.
 */
public class VOperationReport {
    private String date;
    private Integer sendNo;
    private Integer collectNo;
    private Integer problemNo;

    public Integer getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(Integer collectNo) {
        this.collectNo = collectNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getProblemNo() {
        return problemNo;
    }

    public void setProblemNo(Integer problemNo) {
        this.problemNo = problemNo;
    }

    public Integer getSendNo() {
        return sendNo;
    }

    public void setSendNo(Integer sendNo) {
        this.sendNo = sendNo;
    }
}
