package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.DistanceUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.convert.CollectOrderConvert;
import com.courier.core.convert.DeliveryOrderConvert;
import com.courier.core.convert.VOrderConvert;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VOrder;
import com.courier.core.vModel.VOrderAndNum;
import com.courier.db.entity.*;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by admin on 2015/11/2.
 */
@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired CacheUtil cacheUtil;
    @Autowired UserService userService;
    @Autowired CollectOrderService collectOrderService;
    @Autowired DeliveryOrderService deliveryOrderService;
    @Autowired UserLocationService userLocationService;
    @Autowired RedisService redisService;

    /**
     * 查询所有订单 派件 揽件
     */
    public ResponseDTO find(String uuid, Enumerate.OrderType orderType, Integer pageNo, Integer pageSize, Double lat, Double lng,
                            Map<String, Object> deliveryFilters, Map<String, Object> collectFilters) throws Exception {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Long userId = (Long) responseDTO.getT2();

        if(lat == null || lng == null || orderType == null)
            return new ResponseDTO(CodeEnum.C1034);

        //  todo 先从redis中查，没有再查db
        String redistKey = buildAllVOrderKey(userId, orderType);
        VOrderAndNum vOrderAndNum = cacheUtil.getCacheShortTimeByFromRedis(redistKey, VOrderAndNum.class);
        //  todo  查db，并放入redis
        if (vOrderAndNum == null){
            int deliveryNo = 0;
            int collectNo = 0;
            vOrderAndNum = buildVOrders(uuid, orderType, lat, lng, deliveryFilters, collectFilters, deliveryNo, collectNo);
            putToRedis(userId, redistKey, vOrderAndNum);
        }
        //刷新首页待派待取件数据
        redisService.refreshHomePage(userId, vOrderAndNum.getDeliveryNo(), vOrderAndNum.getCollectNo());

        return buildAllResult(pageNo, pageSize, vOrderAndNum);
    }
    /**
     * 根据用户所在省市，查询未抢单的取件信息
     * todo 抢单变化频率高，所以查询结果不放入redis, 每一次都从redis（市取件列表）中查最新的
     */
    public ResponseDTO findUnRobs(String uuid, Integer pageNo, Integer pageSize, double distance, Double lat, Double lng) throws Exception {
        if(lat == null || lng == null)
            return new ResponseDTO(CodeEnum.C1034);

        ResponseDTO unRobs = collectOrderService.findUnRobs(uuid, distance, lat, lng);
        if (unRobs.getCodeEnum() != CodeEnum.C1000) return unRobs;
        List<CollectOrder> collectOrders = unRobs.getList();
        if(collectOrders == null || collectOrders.size() <= 0)
            return new ResponseDTO(CodeEnum.C1000);

        List<VOrder> collects = getDistanceAndSortC(lat, lng, collectOrders);

        // todo 排序
        Collections.sort(collects);

        return buildResult(pageNo, pageSize, collects);
    }



    /***************** begin private method **********************/

    /**
     * 截取list实现分页
     */
    private ResponseDTO buildResult(Integer pageNo, Integer pageSize, List<VOrder> vOrders) {
        if (vOrders == null || vOrders.size() <= 0)
            return new ResponseDTO(CodeEnum.C1000);

        if(pageNo != null && pageSize != null){
            int total = vOrders.size();
            int begin = (pageNo - 1) * pageSize;
            int end = begin + pageSize;

            //  判断分页是否有效
            if( begin <= total && total < end){
                List<VOrder> result = vOrders.subList(begin, end);

                return new ResponseDTO(CodeEnum.C1000, new ArrayList<>(result), total);
            }else {
                return new ResponseDTO(CodeEnum.C1036);
            }
        }

        return new ResponseDTO(CodeEnum.C1000, vOrders, null);
    }

    /**
     * 截取list实现分页
     */
    private ResponseDTO buildAllResult(Integer pageNo, Integer pageSize, VOrderAndNum vOrderAndNum) {
        List<VOrder> vOrders = vOrderAndNum.getvOrders();
        if (vOrders == null || vOrders.size() <= 0)
            return new ResponseDTO(CodeEnum.C1000);

        if(pageNo != null && pageSize != null){
            int total = vOrders.size();
            int begin = (pageNo - 1) * pageSize;
            int end = begin + pageSize;

            end = end >= total ? total : end;
            //  判断分页是否有效
            if( begin <= total && total >= end){
                List<VOrder> result = vOrders.subList(begin, end);
                result = VOrderConvert.initOrdersRegions(cacheUtil, new ArrayList<>(result));
                vOrderAndNum.setvOrders(result);
                return new ResponseDTO(CodeEnum.C1000, null, vOrderAndNum);
            }else {
                return new ResponseDTO(CodeEnum.C1036);
            }
        }
        vOrders = VOrderConvert.initOrdersRegions(cacheUtil, vOrders);
        vOrderAndNum.setvOrders(vOrders);
        return new ResponseDTO(CodeEnum.C1000, null, vOrderAndNum);
    }


    /**
     * 按距离排序
     */
    private VOrderAndNum buildVOrders(String uuid, Enumerate.OrderType orderType, Double lat, Double lng, Map<String, Object> deliveryFilters, Map<String, Object> collectFilters, int deliveryNo, int collectNo) throws Exception {
        List<VOrder> result = new ArrayList<VOrder>();
        Future<List<VOrder>> deliverFuture = null;
        Future<List<VOrder>> collectFuture = null;

        ExecutorService pool = null;

        try {
            if (Enumerate.OrderType.ALL == orderType) {
                pool = Executors.newFixedThreadPool(2);
                deliverFuture = pool.submit(new BuildResultList(DeliveryOrder.class, uuid, lat, lng, deliveryFilters));
                collectFuture = pool.submit(new BuildResultList(CollectOrder.class, uuid, lat, lng, collectFilters));
            } else if (Enumerate.OrderType.DELIVERY == orderType) {
                pool = Executors.newFixedThreadPool(1);
                deliverFuture = pool.submit(new BuildResultList(DeliveryOrder.class, uuid, lat, lng, deliveryFilters));
            } else {
                pool = Executors.newFixedThreadPool(1);
                collectFuture = pool.submit(new BuildResultList(CollectOrder.class, uuid, lat, lng, collectFilters));
            }

            List<VOrder> delivery = deliverFuture.get();
            List<VOrder> collect = collectFuture.get();
            if (delivery != null) {
                deliveryNo = delivery.size();
                result.addAll(delivery);
            }
            if (collect != null) {
                collectNo = collect.size();
                result.addAll(collect);
            }

            // todo 排序
            Collections.sort(result);
        } finally {
            //TODO 销毁线程
            if(pool != null){
                pool.shutdown();
                pool.shutdownNow();
                pool = null;
            }
        }

        return new VOrderAndNum(result, collectNo, deliveryNo);
    }


    class BuildResultList implements Callable{
        Class<? extends BaseEntity> cls;
        String uuid;
        Double lat;
        Double lng;
        Map<String, Object> fileter;

        public BuildResultList(Class<? extends BaseEntity> cls, String uuid, Double lat, Double lng, Map<String, Object> filters){
            this.cls = cls;
            this.uuid = uuid;
            this.lat = lat;
            this.lng = lng;
            this.fileter = filters;
        }

        @Override
        public Object call() throws Exception{
            List<VOrder> result = Lists.newArrayList();
            if(DeliveryOrder.class.getName().equals(cls.getName())){
                result = buildDeliverys(uuid, lat, lng, fileter);
            } else if(CollectOrder.class.getName().equals(cls.getName())){
                result = buildCollects(uuid, lat, lng, fileter);
            }
            return result;
        }
    }


    /**
     * 封装为对象
     */
    private List<VOrder> buildCollects(String uuid, Double lat, Double lng, Map<String, Object> filters) throws Exception {
        ResponseDTO responseDTO = collectOrderService.findByFilters(uuid, filters, null, null, false);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return null;

        List<CollectOrder> collectOrders = responseDTO.getList();

        return getDistanceAndSortC(lat, lng, collectOrders);
    }

    /**
     * 计算距离
     */
    private List<VOrder> getDistanceAndSortC(Double lat, Double lng, List<CollectOrder> collectOrders) {
        if(collectOrders == null || collectOrders.size() <= 0) return null;

        List<VOrder> result = new ArrayList<VOrder>();

        for(CollectOrder collectOrder : collectOrders){
            Double senderLng = collectOrder.getSenderLng();
            Double senderLat = collectOrder.getSenderLat();
            Double distance = null;

            if(senderLng != null && senderLat != null)
                distance = DistanceUtil.getDistance(lng, lat, senderLng, senderLat);

            VOrder vOrder = new VOrder(collectOrder, null, distance, VOrder.Type.COLLECT);
            result.add(vOrder);
        }

        return result;
    }

    /**
     * 计算距离， 并封装为对象
     */
    private List<VOrder> buildDeliverys(String uuid, Double lat, Double lng, Map<String, Object> filters) throws Exception {
        ResponseDTO responseDTO = deliveryOrderService.findByFilters(uuid, filters, null, null);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return null;

        List<DeliveryOrder> sendingDeliverys = responseDTO.getList();
        if(sendingDeliverys == null || sendingDeliverys.size() <= 0) return null;

        List<VOrder> result = new ArrayList<VOrder>();

        for(DeliveryOrder deliveryOrder : sendingDeliverys){
            Double receiverLat = deliveryOrder.getReceiverLat();
            Double receiverLng = deliveryOrder.getReceiverLng();
            Double distance = null;

            if(receiverLat != null && receiverLng != null)
                distance = DistanceUtil.getDistance(lng, lat, receiverLng, receiverLat);

            VOrder vOrder = new VOrder(null, deliveryOrder, distance, VOrder.Type.DELIVERY);
            result.add(vOrder);
        }

        return result;
    }

    /**
     * courierBean key 值
     */
    public static String buildAllVOrderKey(Long userId, Enumerate.OrderType orderType){
        if(Enumerate.OrderType.ALL == orderType){
            return String.format(CacheConstant.ALL_VORDER_LIST_LEY, userId);
        }else if(Enumerate.OrderType.DELIVERY == orderType){
            return String.format(CacheConstant.DELIVERY_VORDER_LIST_LEY, userId);
        }else{
            return String.format(CacheConstant.COLLECT_VORDER_LIST_LEY, userId);
        }
    }

    private void putToRedis(Long userId, String redistKey, VOrderAndNum vOrderAndNum) {
        try{
//            cacheUtil.put2RedisCacheByShortTime(redistKey, vOrderAndNum);
            cacheUtil.putData2RedisByTime(redistKey, 20, vOrderAndNum);
        }catch (Exception e){
            logger.error("put all orders to redis, uId is {}, error is {}", userId, e.getMessage());
        }
    }



}
