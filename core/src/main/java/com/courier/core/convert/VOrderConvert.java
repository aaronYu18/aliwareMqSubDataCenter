package com.courier.core.convert;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.vModel.VOrder;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.DeliveryOrder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 15/10/28.
 */
public class VOrderConvert {


    public static List<VOrder> initOrdersRegions(CacheUtil cacheUtil, List<VOrder> vOrders) {
        if (CollectionUtils.isEmpty(vOrders)) return null;

        List<VOrder> result = new ArrayList<>();

        for (VOrder vOrder : vOrders) {
            vOrder = initRegions(cacheUtil, vOrder);
            if (vOrder != null) result.add(vOrder);
        }

        return result;
    }

    private static VOrder initRegions(CacheUtil cacheUtil, VOrder vOrder) {
        if (vOrder == null) return null;

        if (vOrder.getType().equals(VOrder.Type.COLLECT)) {
            CollectOrder cOrder = vOrder.getCollectOrder();
            cOrder = CollectOrderConvert.initRegion(cacheUtil, cOrder);
            vOrder.setCollectOrder(cOrder);
        } else if (vOrder.getType().equals(VOrder.Type.DELIVERY)) {
            DeliveryOrder dOrder = vOrder.getDeliveryOrder();
            dOrder = DeliveryOrderConvert.buildRegionName(cacheUtil, dOrder);
            vOrder.setDeliveryOrder(dOrder);
        }

        return vOrder;
    }

    /**
     * 省市区县code 转为中文
     */
    public static String buildAddress(CacheUtil cacheUtil, String pCode, String cCode, String aCode, String address) {
        String province = CommonConvert.getNameByCode(cacheUtil, pCode);
        String city = CommonConvert.getNameByCode(cacheUtil, cCode);
        String area = CommonConvert.getNameByCode(cacheUtil, aCode);

        province = org.apache.commons.lang3.StringUtils.isEmpty(province) ? "" : province;
        city = org.apache.commons.lang3.StringUtils.isEmpty(city) ? "" : city;
        area = org.apache.commons.lang3.StringUtils.isEmpty(area) ? "" : area;

        return province + city + area + address;
    }

    /**
     * 省市区县code 转为中文
     */
    public static String buildAddress(String pCodeName, String cCodeName, String aCodeName, String address) {
        pCodeName = org.apache.commons.lang3.StringUtils.isEmpty(pCodeName) ? "" : pCodeName;
        cCodeName = org.apache.commons.lang3.StringUtils.isEmpty(cCodeName) ? "" : cCodeName;
        aCodeName = org.apache.commons.lang3.StringUtils.isEmpty(aCodeName) ? "" : aCodeName;

        return pCodeName + cCodeName + aCodeName + address;
    }

    public static DeliveryOrder buildRegionName(CacheUtil cacheUtil, DeliveryOrder order) {
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

    public static DeliveryOrder buildDbObj(DeliveryOrder order, DeliveryOrder db) {
        if (order == null) return db;
        if (db == null) db = new DeliveryOrder();

        String signPersonName = order.getSignPersonName();
        String failedCode = order.getFailedCode();
        String failedDescription = order.getFailedDescription();
        String signPic = order.getSignPic();
        Double collectionMoney = order.getCollectionMoney();
        Double freightMoney = order.getFreightMoney();

        if (!StringUtils.isEmpty(signPersonName)) db.setSignPersonName(signPersonName);
        if (!StringUtils.isEmpty(failedCode)) db.setFailedCode(failedCode);
        if (!StringUtils.isEmpty(failedDescription)) db.setFailedDescription(failedDescription);
        if (!StringUtils.isEmpty(signPic)) db.setSignPic(signPic);
        if (collectionMoney != null) db.setCollectionMoney(collectionMoney);
        if (freightMoney != null) db.setFreightMoney(freightMoney);

        db.setOrderStatus(order.getOrderStatus());
        db.setFlag(true);

        return db;
    }
}
