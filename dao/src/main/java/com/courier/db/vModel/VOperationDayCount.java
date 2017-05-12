package com.courier.db.vModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vincent on 15/11/6.
 */
public class VOperationDayCount implements Serializable{
    private static final long serialVersionUID = 7412378018393984249L;
    private Date day;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
