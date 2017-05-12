package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.util.Date;

/**
 * Created by bin on 2015/12/14.
 */
public class DeliveryOrderSyncMqBean extends BaseEntity {
    private static final long serialVersionUID = 8971994449199326190L;
    private String excludeMailNo;
    private String beginT;
    private Long userId;
    private String jobNo;
    private String orgCode;

    public DeliveryOrderSyncMqBean(String excludeMailNo, String beginT, String jobNo, String orgCode, Long userId) {
        this.beginT = beginT;
        this.jobNo = jobNo;
        this.orgCode = orgCode;
        this.userId = userId;
        this.excludeMailNo = excludeMailNo;
    }

    public String getExcludeMailNo() {
        return excludeMailNo;
    }

    public void setExcludeMailNo(String excludeMailNo) {
        this.excludeMailNo = excludeMailNo;
    }

    public String getBeginT() {
        return beginT;
    }

    public void setBeginT(String beginT) {
        this.beginT = beginT;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
