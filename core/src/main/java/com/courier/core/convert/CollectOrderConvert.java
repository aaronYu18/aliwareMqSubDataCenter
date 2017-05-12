package com.courier.core.convert;

import com.courier.commons.constant.Global;
import com.courier.commons.model.baiDu.BDGpsResp;
import com.courier.commons.util.Uuid;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.jingang.Interfaces;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.Region;
import com.courier.sdk.constant.Enumerate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2015/11/14.
 */
public class CollectOrderConvert {

    public static void standardCode(CacheUtil cacheUtil, CollectOrder order) {

        String senderProvince = order.getSenderProvince();
        String senderCity = order.getSenderCity();
        String senderArea = order.getSenderArea();
        Region regionP = CommonConvert.provinceConvert(cacheUtil, senderProvince, null);
        if (regionP != null) {
            order.setSenderProvince(regionP.getCode());
            order.setSenderProvinceName(regionP.getName());
        }
        Region regionC = CommonConvert.cityConvert(cacheUtil, senderProvince, senderCity);
        if (regionC != null) {
            order.setSenderCity(regionC.getCode());
            order.setSenderCityName(regionC.getName());
        }
        Region regionA = CommonConvert.areaConvert(cacheUtil, senderProvince, senderArea);
        if (regionA != null) {
            order.setSenderArea(regionA.getCode());
            order.setSenderAreaName(regionA.getName());
        }

        String receiveProvince = order.getReceiverProvince();
        String receiveCity = order.getReceiverCity();
        String receiveArea = order.getReceiverArea();
        Region regionP1 = CommonConvert.provinceConvert(cacheUtil, receiveProvince, null);
        if (regionP1 != null) {
            order.setReceiverProvince(regionP1.getCode());
            order.setReceiverProvinceName(regionP1.getName());
        }
        Region regionC1 = CommonConvert.cityConvert(cacheUtil, receiveProvince, receiveCity);
        if (regionC1 != null) {
            order.setReceiverCity(regionC1.getCode());
            order.setReceiverCityName(regionC1.getName());
        }
        Region regionA1 = CommonConvert.areaConvert(cacheUtil, receiveProvince, receiveArea);
        if (regionA1 != null) {
            order.setReceiverArea(regionA1.getCode());
            order.setReceiverAreaName(regionA1.getName());
        }
    }

    // todo  填充gps
    public static CollectOrder fillGps(CollectOrder input, String url, String regEx, String fullAddress) {
        if (input == null) return null;

        if (!StringUtils.isEmpty(url)) {
            Interfaces interfaces = new Interfaces();
            BDGpsResp.Location location = interfaces.getGpsByAddress(url, regEx, fullAddress);
            if (location != null) {
                input.setSenderLat(location.getLat());
                input.setSenderLng(location.getLng());
            }
        }

        return input;
    }

    // todo  初始化无单取件
    public static CollectOrder initWithOutOrder(CollectOrder input) {
        if (input == null) return null;
        input.setSerialNo(new Uuid().toString());
        input.setSource(Global.NO_ORDER_PICK_UP_SOURCE);
        input.setOrderType(Enumerate.CollectOrderType.NOORDER.getCode());
        input.setOrderStatus(Enumerate.CollectOrderStatus.COLLECTED.getCode());
        Date now = new Date();
        input.setCollectTime(now);
        input.setUpdateTime(now);

        return input;
    }

    public static List<CollectOrder> initRegionList(CacheUtil cacheUtil, List<CollectOrder> list) {
        if (CollectionUtils.isEmpty(list)) return null;

        List<CollectOrder> result = new ArrayList<>();
        for (CollectOrder order : list) {
            CollectOrder cOrder = initRegion(cacheUtil, order);
            if (cOrder != null) result.add(cOrder);
        }

        return result;
    }

    public static CollectOrder initRegion(CacheUtil cacheUtil, CollectOrder order) {
        if (order == null) return null;

        if (StringUtils.isEmpty(order.getSenderProvinceName())) {
            String sProvinceName = CommonConvert.getNameByCode(cacheUtil, order.getSenderProvince());
            order.setSenderProvinceName(sProvinceName);
        }
        if (StringUtils.isEmpty(order.getSenderCityName())) {
            String sCityName = CommonConvert.getNameByCode(cacheUtil, order.getSenderCity());
            order.setSenderCityName(sCityName);
        }
        if (StringUtils.isEmpty(order.getSenderAreaName())) {
            String sAreaName = CommonConvert.getNameByCode(cacheUtil, order.getSenderArea());
            order.setSenderAreaName(sAreaName);
        }
        if (StringUtils.isEmpty(order.getReceiverProvinceName())) {
            String rProvinceName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverProvince());
            order.setReceiverProvinceName(rProvinceName);
        }
        if (StringUtils.isEmpty(order.getReceiverCityName())) {
            String rCityName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverCity());
            order.setReceiverCityName(rCityName);
        }
        if (StringUtils.isEmpty(order.getReceiverAreaName())) {
            String rAreaName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverArea());
            order.setReceiverAreaName(rAreaName);
        }

        return order;
    }

    public static CollectOrder initRegionForce(CacheUtil cacheUtil, CollectOrder order) {
        if (order == null) return null;

        String sProvinceName = CommonConvert.getNameByCode(cacheUtil, order.getSenderProvince());
        order.setSenderProvinceName(sProvinceName);


        String sCityName = CommonConvert.getNameByCode(cacheUtil, order.getSenderCity());
        order.setSenderCityName(sCityName);

        String sAreaName = CommonConvert.getNameByCode(cacheUtil, order.getSenderArea());
        order.setSenderAreaName(sAreaName);

        String rProvinceName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverProvince());
        order.setReceiverProvinceName(rProvinceName);

        String rCityName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverCity());
        order.setReceiverCityName(rCityName);

        String rAreaName = CommonConvert.getNameByCode(cacheUtil, order.getReceiverArea());
        order.setReceiverAreaName(rAreaName);

        return order;
    }

    public static CollectOrder convert(CollectOrder order, CollectOrder dbOrder, Long userId, Enumerate.CollectOrderType orderType, Enumerate.CollectOrderStatus orderStatus) {
        if (order == null) return null;

        Date now = new Date();
        Byte ageing = order.getAgeing();
        Double weight = order.getWeight();
        Byte mailType = order.getMailType();
        String mailNo = order.getMailNo();
        String remark = order.getRemark();
        String senderCity = order.getSenderCity();
        String senderArea = order.getSenderArea();
        String senderName = order.getSenderName();
        String senderPhone = order.getSenderPhone();
        String senderTelPhone = order.getSenderTelPhone();
        String receiverCity = order.getReceiverCity();
        Double freightMoney = order.getFreightMoney();
        String receiverArea = order.getReceiverArea();
        String receiverName = order.getReceiverName();
        String receiverPhone = order.getReceiverPhone();
        String senderAddress = order.getSenderAddress();
        String senderProvince = order.getSenderProvince();
        Double collectionMoney = order.getCollectionMoney();
        String receiverAddress = order.getReceiverAddress();
        String receiverProvince = order.getReceiverProvince();
        String shortAddress = order.getShortAddress();
        if (ageing != null) dbOrder.setAgeing(ageing);
        if (weight != null) dbOrder.setWeight(weight);
        if (mailType != null) dbOrder.setMailType(mailType);
        if (!StringUtils.isEmpty(mailNo)) dbOrder.setMailNo(mailNo);
        if (!StringUtils.isEmpty(remark)) dbOrder.setRemark(remark);
        if (freightMoney != null) dbOrder.setFreightMoney(freightMoney);
        if (orderType != null) dbOrder.setOrderType(orderType.getCode());
        if (orderStatus != null) dbOrder.setOrderStatus(orderStatus.getCode());
        if (!StringUtils.isEmpty(senderCity)) dbOrder.setSenderCity(senderCity);
        dbOrder.setSenderArea(senderArea);
        if (!StringUtils.isEmpty(senderName)) dbOrder.setSenderName(senderName);
        if (collectionMoney != null) dbOrder.setCollectionMoney(collectionMoney);
        if (!StringUtils.isEmpty(senderPhone)) dbOrder.setSenderPhone(senderPhone);
        if (!StringUtils.isEmpty(senderTelPhone)) dbOrder.setSenderTelPhone(senderTelPhone);
        if (!StringUtils.isEmpty(receiverCity)) dbOrder.setReceiverCity(receiverCity);
        dbOrder.setReceiverArea(receiverArea);
        if (!StringUtils.isEmpty(receiverName)) dbOrder.setReceiverName(receiverName);
        if (!StringUtils.isEmpty(receiverPhone)) dbOrder.setReceiverPhone(receiverPhone);
        if (!StringUtils.isEmpty(senderAddress)) dbOrder.setSenderAddress(senderAddress);
        if (!StringUtils.isEmpty(senderProvince)) dbOrder.setSenderProvince(senderProvince);
        if (!StringUtils.isEmpty(receiverAddress)) dbOrder.setReceiverAddress(receiverAddress);
        if (!StringUtils.isEmpty(receiverProvince)) dbOrder.setReceiverProvince(receiverProvince);
        if (!StringUtils.isEmpty(shortAddress)) dbOrder.setShortAddress(shortAddress);
        dbOrder.setUserId(userId);
        dbOrder.setUpdateTime(now);
        if (Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT == orderStatus) {
            dbOrder.setReceiveTime(now);
        } else if (Enumerate.CollectOrderStatus.COLLECTED == orderStatus) {
            dbOrder.setCollectTime(now);
        }

        return dbOrder;
    }
}
