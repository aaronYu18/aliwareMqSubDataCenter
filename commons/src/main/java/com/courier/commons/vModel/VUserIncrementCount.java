package com.courier.commons.vModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/3/21.
 */
public class VUserIncrementCount implements Serializable {
    private static final long serialVersionUID = -379508757864182294L;
    private Date date;
    private int number = 0;

    public VUserIncrementCount() {
    }

    public VUserIncrementCount(Date date, int number) {
        this.date = date;
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
