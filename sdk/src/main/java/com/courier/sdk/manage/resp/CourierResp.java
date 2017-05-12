package com.courier.sdk.manage.resp;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/5/10.
 */
public class CourierResp extends IdEntity {

    private String jobNo;
    private String name;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
