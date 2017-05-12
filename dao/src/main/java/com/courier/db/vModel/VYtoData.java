package com.courier.db.vModel;


import java.io.Serializable;

/**
 * Created by admin on 2015/11/4.
 */
public class VYtoData implements Serializable {
    private static final long serialVersionUID = -4018585386726242923L;
    private int totalSendNo = 0;            // 总派件数
    private int totalCollectNo = 0;         // 总取件数
    private int monthSendNo = 0;            // 当月总派件数
    private int monthCollectNo = 0;         // 当月总取件数
    private double monthMeanSendNo = 0;        // 全网当月平均总派件数
    private double monthMeanCollectdNo = 0;    // 全网当月平均总取件数


    public VYtoData() {
    }

    public VYtoData(double monthMeanCollectdNo, double monthMeanSendNo, int totalCollectNo, int totalSendNo) {
        this.monthMeanCollectdNo = monthMeanCollectdNo;
        this.monthMeanSendNo = monthMeanSendNo;
        this.totalCollectNo = totalCollectNo;
        this.totalSendNo = totalSendNo;
    }

    public int getMonthCollectNo() {
        return monthCollectNo;
    }

    public void setMonthCollectNo(int monthCollectNo) {
        this.monthCollectNo = monthCollectNo;
    }

    public double getMonthMeanCollectdNo() {
        return monthMeanCollectdNo;
    }

    public void setMonthMeanCollectdNo(double monthMeanCollectdNo) {
        this.monthMeanCollectdNo = monthMeanCollectdNo;
    }

    public double getMonthMeanSendNo() {
        return monthMeanSendNo;
    }

    public void setMonthMeanSendNo(double monthMeanSendNo) {
        this.monthMeanSendNo = monthMeanSendNo;
    }

    public int getMonthSendNo() {
        return monthSendNo;
    }

    public void setMonthSendNo(int monthSendNo) {
        this.monthSendNo = monthSendNo;
    }

    public int getTotalCollectNo() {
        return totalCollectNo;
    }

    public void setTotalCollectNo(int totalCollectNo) {
        this.totalCollectNo = totalCollectNo;
    }

    public int getTotalSendNo() {
        return totalSendNo;
    }

    public void setTotalSendNo(int totalSendNo) {
        this.totalSendNo = totalSendNo;
    }
}
