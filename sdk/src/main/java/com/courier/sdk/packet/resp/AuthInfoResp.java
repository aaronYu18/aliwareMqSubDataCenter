package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 16/5/13.
 */
public class AuthInfoResp extends IdEntity {
    private String senderName;
    private Byte senderSex;
    private Byte certificateType;
    private String certificateNo;
    private String fm;

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public Byte getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Byte certificateType) {
        this.certificateType = certificateType;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Byte getSenderSex() {
        return senderSex;
    }

    public void setSenderSex(Byte senderSex) {
        this.senderSex = senderSex;
    }
}
