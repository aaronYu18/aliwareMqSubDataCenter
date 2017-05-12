package com.courier.db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "provinceAuthPattern")
public class ProvinceAuthPattern extends BaseEntity {
    private static final long serialVersionUID = 3968324737515078372L;
    private Long id;
    private String code;
    private String name;
    private Byte collectPattern;        //取件验证模式：0 : 无需验证, 1 : 全国通用模式, 2 : 浙江模式

    public ProvinceAuthPattern() {
    }

    public ProvinceAuthPattern(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public Byte getCollectPattern() {
        return collectPattern;
    }

    public void setCollectPattern(Byte collectPattern) {
        this.collectPattern = collectPattern;
    }
}
