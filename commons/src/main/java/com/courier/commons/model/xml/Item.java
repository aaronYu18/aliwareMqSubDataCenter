package com.courier.commons.model.xml;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/11/28.
 */
public class Item extends BaseEntity {
    private static final long serialVersionUID = 8969421884385471140L;
    private String itemName;
    private String number;
    private String remark;

    public Item() {
    }

    public Item(String itemName, String number) {
        this.itemName = itemName;
        this.number = number;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
