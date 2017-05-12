package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2016/3/10.
 */
public class VReportTables extends IdEntity {
    private static final long serialVersionUID = 6372504306702927819L;

    private String year;
    private String month;
    private Integer totalSendNo = 0;
    private Integer totalCollectNo = 0;
    private Integer totalFailedNo = 0;
    private Double collectAmount = 0.00;
    private List<VDatas> vData = new ArrayList<>();
    private String prevUrl;
    private String nextUrl;

    public VReportTables() {
    }

    public VReportTables(String month, String nextUrl, String prevUrl, String year) {
        this.month = month;
        this.nextUrl = nextUrl;
        this.prevUrl = prevUrl;
        this.year = year;
    }

    public VReportTables(String month, String nextUrl, String prevUrl, Integer totalCollectNo,
                         Integer totalSendNo, Integer totalFailedNo, Double collectAmount, List<VDatas> vData, String year) {
        this.month = month;
        this.nextUrl = nextUrl;
        this.prevUrl = prevUrl;
        this.totalCollectNo = totalCollectNo;
        this.totalSendNo = totalSendNo;
        this.totalFailedNo = totalFailedNo;
        this.collectAmount = collectAmount;
        this.vData = vData;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getTotalCollectNo() {
        return totalCollectNo;
    }

    public void setTotalCollectNo(Integer totalCollectNo) {
        this.totalCollectNo = totalCollectNo;
    }

    public Integer getTotalSendNo() {
        return totalSendNo;
    }

    public void setTotalSendNo(Integer totalSendNo) {
        this.totalSendNo = totalSendNo;
    }

    public List<VDatas> getvData() {
        return vData;
    }

    public void setvData(List<VDatas> vData) {
        this.vData = vData;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public static class VDatas implements Serializable{
        private static final long serialVersionUID = -609993155779597629L;
        private String date;
        private Integer sendNo;
        private Integer collectNo;
        private Integer failedNo;
        private Double dayAmount = 0.00;

        public VDatas(Integer collectNo, String date, Integer failedNo, Integer sendNo, Double dayAmount) {
            this.collectNo = collectNo;
            this.date = date;
            this.failedNo = failedNo;
            this.sendNo = sendNo;
            this.dayAmount = dayAmount;
        }

        public VDatas() {
        }

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

        public Integer getFailedNo() {
            return failedNo;
        }

        public void setFailedNo(Integer failedNo) {
            this.failedNo = failedNo;
        }

        public Integer getSendNo() {
            return sendNo;
        }

        public void setSendNo(Integer sendNo) {
            this.sendNo = sendNo;
        }

        public Double getDayAmount() {
            return dayAmount;
        }

        public void setDayAmount(Double dayAmount) {
            this.dayAmount = dayAmount;
        }
    }

    public Integer getTotalFailedNo() {
        return totalFailedNo;
    }

    public void setTotalFailedNo(Integer totalFailedNo) {
        this.totalFailedNo = totalFailedNo;
    }

    public Double getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
    }
}
