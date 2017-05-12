package com.courier.sdk.manage.resp;

import com.courier.sdk.common.IdEntity;

import java.util.List;

/**
 * Created by vincent on 16/4/18.
 */
public class ManageHomePageResp extends IdEntity {
    private static final long serialVersionUID = -921540576022311993L;

    private Integer waitSignCount;
    private Integer waitCollectCount;
    private Integer signCount=0;
    private Integer collectCount=0;
    private Integer questionCount=0;
    private Integer courierCount;
    private Boolean isShowLocation;
    private String orgCode;
    private Integer totalNo = 0;

    private List<ManageDetailResp> detailList;

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCourierCount() {
        return courierCount;
    }

    public void setCourierCount(Integer courierCount) {
        this.courierCount = courierCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getWaitCollectCount() {
        return waitCollectCount;
    }

    public void setWaitCollectCount(Integer waitCollectCount) {
        this.waitCollectCount = waitCollectCount;
    }

    public Integer getWaitSignCount() {
        return waitSignCount;
    }

    public void setWaitSignCount(Integer waitSignCount) {
        this.waitSignCount = waitSignCount;
    }

    public List<ManageDetailResp> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ManageDetailResp> detailList) {
        this.detailList = detailList;
    }

    public Boolean getShowLocation() {
        return isShowLocation;
    }

    public void setShowLocation(Boolean showLocation) {
        isShowLocation = showLocation;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(Integer totalNo) {
        this.totalNo = totalNo;
    }
}
