package com.courier.db.entity;

import com.courier.commons.entity.IdEntity;

import java.util.Date;

/**
 * Created by admin on 2015/5/27.
 */

public abstract class BaseEntity extends IdEntity {
    private static final long serialVersionUID = 4283156633201404072L;
    private Date createTime = new Date();
    private Date updateTime = new Date();

    public abstract void setId(Long id);

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
