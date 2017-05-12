package com.courier.sdk.packet.resp;
import com.courier.sdk.common.IdEntity;
/**
 * Created by vincent on 15/10/29.
 */
public class HomePageResp extends IdEntity {
    private static final long serialVersionUID = 5718027206200980026L;
    private Integer bothCount;         //当前待派及待取件数
    private Integer signCount;         //今日已签
    private Integer collectCount;      //今日已取
    private Integer questionCount;     //问题件
    private Double collectAmount = 0.00;     //今日收款

    public HomePageResp() {
    }

    public HomePageResp(Integer bothCount, Integer collectCount, Integer questionCount, Integer signCount) {
        this.bothCount = bothCount;
        this.collectCount = collectCount;
        this.questionCount = questionCount;
        this.signCount = signCount;
    }

    public Integer getBothCount() {
        return bothCount;
    }

    public void setBothCount(Integer bothCount) {
        this.bothCount = bothCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
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

    public Double getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
    }
}
