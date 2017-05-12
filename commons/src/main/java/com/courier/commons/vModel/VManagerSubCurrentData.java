package com.courier.commons.vModel;

import com.courier.commons.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by admin on 2015/11/4.
 */
public class VManagerSubCurrentData  extends BaseEntity {


    private static final long serialVersionUID = 717249282949201392L;
    private String key;         //  网点代码 或 工号
    private String username;
    private String userOrgCode;
    private Long userId;
    private int signNo = 0;
    private int collectNo = 0;
    private int problemNo = 0;
    private int sendingNo = 0;
    private int collectingNo = 0;
    private int totalUserNo = 0;
    private Double lat;              //  经度
    private Double lng;              //  纬度
    private Byte role;              //  点此条记录查看所属快递员|网点详情 （可为空）
    private Boolean isJobNo;              //  点此条记录查看所属快递员|网点详情 （可为空）


    public VManagerSubCurrentData() {
    }

    public VManagerSubCurrentData(String key) {
        this.key = key;
    }

    public VManagerSubCurrentData(String key, Byte role) {
        this.key = key;
        this.role = role;
    }

    public VManagerSubCurrentData(int collectingNo, int collectNo, String key, String username, int problemNo, int sendingNo, int signNo,
                                  Byte role, boolean isJobNo, String userOrgCode, int totalUserNo) {
        this.collectingNo = collectingNo;
        this.collectNo = collectNo;
        this.key = key;
        this.problemNo = problemNo;
        this.sendingNo = sendingNo;
        this.signNo = signNo;
        this.role = role;
        this.username = username;
        this.isJobNo = isJobNo;
        this.userOrgCode = userOrgCode;
        this.totalUserNo = totalUserNo;
    }



    public int getCollectingNo() {
        return collectingNo;
    }

    public void setCollectingNo(int collectingNo) {
        this.collectingNo = collectingNo;
    }

    public int getCollectNo() {
        return collectNo;
    }

    public void setCollectNo(int collectNo) {
        this.collectNo = collectNo;
    }

    public int getProblemNo() {
        return problemNo;
    }

    public void setProblemNo(int problemNo) {
        this.problemNo = problemNo;
    }

    public int getSendingNo() {
        return sendingNo;
    }

    public void setSendingNo(int sendingNo) {
        this.sendingNo = sendingNo;
    }

    public int getSignNo() {
        return signNo;
    }

    public void setSignNo(int signNo) {
        this.signNo = signNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsJobNo() {
        return isJobNo;
    }

    public void setIsJobNo(Boolean isJobNo) {
        this.isJobNo = isJobNo;
    }

    public String getUserOrgCode() {
        return userOrgCode;
    }

    public void setUserOrgCode(String userOrgCode) {
        this.userOrgCode = userOrgCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getTotalUserNo() {
        return totalUserNo;
    }

    public void setTotalUserNo(int totalUserNo) {
        this.totalUserNo = totalUserNo;
    }
}
