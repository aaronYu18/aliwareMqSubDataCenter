package com.courier.core.convert;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.util.SignResultResp;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.Region;
import com.courier.sdk.constant.Enumerate;
import com.courier.sdk.packet.req.BatchSignReq;
import com.courier.sdk.packet.resp.BatchSignResp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vincent on 15/10/28.
 */
public class DeliveryOrderConvert {

    public static void standardCode(CacheUtil cacheUtil, DeliveryOrder order) {
        String receiveProvince = order.getReceiverProvince();
        String receiveCity = order.getReceiverCity();
        String receiveArea = order.getReceiverArea();
        Region regionP = CommonConvert.provinceConvert(cacheUtil, receiveProvince, null);
        if (regionP != null) {
            order.setReceiverProvince(regionP.getCode());
            order.setReceiverProvinceName(regionP.getName());
        }
        Region regionC = CommonConvert.cityConvert(cacheUtil, receiveProvince, receiveCity);
        if (regionC != null) {
            order.setReceiverCity(regionC.getCode());
            order.setReceiverCityName(regionC.getName());
        }
        Region regionA = CommonConvert.areaConvert(cacheUtil, receiveProvince, receiveArea);
        if (regionA != null) {
            order.setReceiverArea(regionA.getCode());
            order.setReceiverAreaName(regionA.getName());
        }
    }

    public static List<DeliveryOrder> buildListRegionName(CacheUtil cacheUtil, List<DeliveryOrder> list) {
        if (cacheUtil == null || CollectionUtils.isEmpty(list)) return null;

        List<DeliveryOrder> result = new ArrayList<>();
        for (DeliveryOrder dOrder : list) {
            dOrder = DeliveryOrderConvert.buildRegionName(cacheUtil, dOrder);
            if (dOrder != null) result.add(dOrder);
        }

        return result;
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

    public static DeliveryOrder sign(DeliveryOrder order, DeliveryOrder db) {
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
        Date now = new Date();
        db.setSignTime(now);
        db.setUpdateTime(now);
        db.setAppSignTime(order.getAppSignTime());

        return db;
    }
}
