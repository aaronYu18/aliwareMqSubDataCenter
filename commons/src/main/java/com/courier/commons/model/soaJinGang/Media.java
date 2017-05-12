package com.courier.commons.model.soaJinGang;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by beyond on 2016/6/15.
 */
public class Media extends BaseEntity {
    private static final long serialVersionUID = 7483188528522189241L;
    private String contentAddress;
    private String extendFlag;
    private String id;
    private String issueCreateTime;
    private String mediaId;
    private String sno;
    private String type;

    public String getContentAddress() {
        return contentAddress;
    }

    public void setContentAddress(String contentAddress) {
        this.contentAddress = contentAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getIssueCreateTime() {
        return issueCreateTime;
    }

    public void setIssueCreateTime(String issueCreateTime) {
        this.issueCreateTime = issueCreateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtendFlag() {
        return extendFlag;
    }

    public void setExtendFlag(String extendFlag) {
        this.extendFlag = extendFlag;
    }
}
