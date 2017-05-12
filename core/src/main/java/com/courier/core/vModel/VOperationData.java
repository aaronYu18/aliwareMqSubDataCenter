package com.courier.core.vModel;

import com.courier.db.entity.OrderOperation;
import com.courier.db.vModel.VOperationDayCount;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vincent on 15/11/6.
 */
public class VOperationData implements Serializable{
    private static final long serialVersionUID = 6031036848285931102L;
    private Integer count;
    private List<OrderOperation> orderOperations;
    private List<VOperationDayCount> vOperationDayCounts;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<OrderOperation> getOrderOperations() {
        return orderOperations;
    }

    public void setOrderOperations(List<OrderOperation> orderOperations) {
        this.orderOperations = orderOperations;
    }

    public List<VOperationDayCount> getvOperationDayCounts() {
        return vOperationDayCounts;
    }

    public void setvOperationDayCounts(List<VOperationDayCount> vOperationDayCounts) {
        this.vOperationDayCounts = vOperationDayCounts;
    }
}
