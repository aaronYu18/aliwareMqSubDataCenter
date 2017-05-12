package com.courier.core.convert;

import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.OrderOperation;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/11/9.
 */
public class OrderOperationConvert{
    // todo  派件转换
    public static OrderOperation convertByDelivery(DeliveryOrder order, Long userId, Enumerate.OperationType operationType){
        if(order == null) return null;

        OrderOperation result = new OrderOperation();

        result.setOrderId(order.getId());
        result.setDcType(Enumerate.DCType.DELIVERY.getCode());
        result.setUserId(userId);
        result.setOperationType(operationType.getCode());
        result.setMailNo(order.getMailNo());
        result.setReceiverProvince(order.getReceiverProvince());
        result.setReceiverCity(order.getReceiverCity());
        result.setReceiverArea(order.getReceiverArea());
        result.setReceiverPhone(order.getReceiverPhone());
        result.setReceiverName(order.getReceiverName());
        result.setReceiverAddress(order.getReceiverAddress());
        result.setReceiverLat(order.getReceiverLat());
        result.setReceiverLng(order.getReceiverLng());
        result.setFailedDescription(order.getFailedDescription());

        return result;
    }
    //todo 收件转换
    public static OrderOperation converByCollect(CollectOrder order){
        if (order == null) return null;

        OrderOperation result = new OrderOperation();

        result.setUserId(order.getUserId());
        result.setMailNo(order.getMailNo());
        result.setDcType(Enumerate.DCType.COLLECT.getCode());
        result.setOrderId(order.getId());
        result.setReceiverArea(order.getReceiverArea());
        result.setReceiverProvince(order.getReceiverProvince());
        result.setReceiverCity(order.getReceiverCity());
        result.setReceiverPhone(order.getReceiverPhone());
        result.setReceiverName(order.getReceiverName());
        result.setReceiverAddress(order.getReceiverAddress());
        Byte orderStatus = order.getOrderStatus();
        if (Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT.getCode() == orderStatus) {
            result.setOperationType(Enumerate.OperationType.GRAB.getCode());
        }else if (Enumerate.CollectOrderStatus.COLLECTED.getCode() == orderStatus) {
            result.setOperationType(Enumerate.OperationType.COLLECT.getCode());
        }
        return result;
    }


    public static List<OrderOperation> buildListRegionName(CacheUtil cacheUtil, List<OrderOperation> list) {
        if (cacheUtil == null || CollectionUtils.isEmpty(list)) return null;

        List<OrderOperation> result = new ArrayList<>();
        for (OrderOperation dOrder : list) {
            dOrder = OrderOperationConvert.buildRegionName(cacheUtil, dOrder);
            if (dOrder != null) result.add(dOrder);
        }

        return result;
    }


    public static OrderOperation buildRegionName(CacheUtil cacheUtil, OrderOperation order) {
        if (cacheUtil == null || order == null) return null;

        if (!StringUtils.isEmpty(order.getReceiverProvince()) && StringUtils.isEmpty(order.getReceiverProvinceName())) {
            String name = CommonConvert.getNameByCode(cacheUtil, order.getReceiverProvince());
            order.setReceiverProvinceName(name);
        }
        if (!StringUtils.isEmpty(order.getReceiverCity()) && StringUtils.isEmpty(order.getReceiverCityName())) {
            String name = CommonConvert.getNameByCode(cacheUtil, order.getReceiverCity());
            order.setReceiverCityName(name);
        }
        if (!StringUtils.isEmpty(order.getReceiverArea()) && StringUtils.isEmpty(order.getReceiverAreaName())) {
            String name = CommonConvert.getNameByCode(cacheUtil, order.getReceiverArea());
            order.setReceiverAreaName(name);
        }

        return order;
    }
}
