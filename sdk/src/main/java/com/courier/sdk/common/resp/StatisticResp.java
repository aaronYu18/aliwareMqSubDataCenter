package com.courier.sdk.common.resp;

import com.courier.sdk.common.IdEntity;

import java.util.List;

/**
 * Created by vincent on 15/10/29.
 */
public class StatisticResp extends IdEntity {
    private Integer collectCount;           //总收件数
    private Integer deliveryCount;          //总派件数
    private Integer mCollectCount;          //当月收件数
    private Integer mDeliveryCount;         //当月派件数
    private Double mCollectAvgCount;       //当月全网平均收件数
    private Double mDeliveryAvgCount;      //当月全网平均派件数
    private List<DayBothResp> dayBothList;  //当日时段收派列表

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public List<DayBothResp> getDayBothList() {
        return dayBothList;
    }

    public void setDayBothList(List<DayBothResp> dayBothList) {
        this.dayBothList = dayBothList;
    }

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public Integer getmCollectCount() {
        return mCollectCount;
    }

    public void setmCollectCount(Integer mCollectCount) {
        this.mCollectCount = mCollectCount;
    }

    public Double getmCollectAvgCount() {
        return mCollectAvgCount;
    }

    public void setmCollectAvgCount(Double mCollectAvgCount) {
        this.mCollectAvgCount = mCollectAvgCount;
    }

    public Double getmDeliveryAvgCount() {
        return mDeliveryAvgCount;
    }

    public void setmDeliveryAvgCount(Double mDeliveryAvgCount) {
        this.mDeliveryAvgCount = mDeliveryAvgCount;
    }

    public Integer getmDeliveryCount() {
        return mDeliveryCount;
    }

    public void setmDeliveryCount(Integer mDeliveryCount) {
        this.mDeliveryCount = mDeliveryCount;
    }
}
