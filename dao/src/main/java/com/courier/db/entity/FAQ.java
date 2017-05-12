package com.courier.db.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "faq")
public class FAQ extends BaseEntity {
    private static final long serialVersionUID = 5706237523888195419L;
    private Long id;
    private String name;                //     版本名称
    private String description;         //     版本说明
    private String path;                //
    private Boolean status;             //

    List<FAQ> children = new ArrayList<FAQ>();

    public FAQ() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Transient
    public List<FAQ> getChildren() {
        return children;
    }

    public void setChildren(List<FAQ> children) {
        this.children = children;
    }


}
