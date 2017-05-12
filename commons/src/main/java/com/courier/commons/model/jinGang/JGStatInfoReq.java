package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

import java.io.Serializable;

/**
 * Created by bin on 2015/10/30.
 */
public class JGStatInfoReq extends BaseEntity {
    private static final long serialVersionUID = 2224487669856563148L;
    private String orgCode;
    private String courierCode;
    private String optDate;

    public JGStatInfoReq(String orgCode, String courierCode, String optDate) {
        this.orgCode = orgCode;
        this.courierCode = courierCode;
        this.optDate = optDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public String getOptDate() {
        return optDate;
    }

    public void setOptDate(String optDate) {
        this.optDate = optDate;
    }
}
