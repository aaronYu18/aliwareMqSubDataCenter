package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.entity.CollectOrder;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * Created by bin on 2015/11/9.
 */
@Service
@Transactional
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private HomePageService homePageService;
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private CollectOrderService collectOrderService;
    @Autowired
    private RegionService regionService;

    public void putToRedis(CollectOrder order) {
        if (order == null || order.getId() == null || StringUtils.isEmpty(order.getSenderCity()) || StringUtils.isEmpty(order.getSenderProvince())) {
            logger.info("rob collect order has null (province or city ) ");
            return;
        }
        try {
            //todo 单个collectOrder
            cacheUtil.putData2RedisByForever(collectOrderService.buildCollectOrderKey(order.getId()), order);

            //todo city 的list
            cacheUtil.putData2RedisSet(collectOrderService.buildCityCollectOrderKey(order.getSenderCity()), order.getId());

            //todo province 的list
            cacheUtil.putData2RedisSet(regionService.buildProvinceCitiesKey(order.getSenderProvince()), order.getSenderCity());

            //todo 计时
            String value = order.getSenderProvince() + "_" + order.getSenderCity() + "_" + order.getId() + "_" + new Date().getTime();
            cacheUtil.putData2RedisSet(CacheConstant.COLLECTION_ORDER_TIMESTEMP_KEY, value);
        } catch (Exception e) {
            logger.error("putToRedis error message:{},json:{}", e.getMessage(), order.toJson());
        }
    }

    /**
     * 得到城市的 订单列表集合 sets
     */
    public Set<Long> getRedisByCityCode(String cityCode) {
        return cacheUtil.getDataFromRedisSet(collectOrderService.buildCityCollectOrderKey(cityCode), Long.class);
    }

    public CollectOrder getCorderByCid(Long cID) {
        CollectOrder collectOrder = cacheUtil.getCacheByFromRedis(collectOrderService.buildCollectOrderKey(cID), CollectOrder.class);

        return collectOrder;
    }

    //更新redis里的homePage和各种列表
    public void invalidHomePageAndList(Long userId, Long orderUserId, Enumerate.OperationType operationType){
        if (Enumerate.OperationType.GRAB.equals(operationType)) {//接单
            homePageService.incCollectingNoCount(userId);
        }else if (Enumerate.OperationType.NOCOLLECT.equals(operationType)){//无单取件
            homePageService.incCollectNoCount(userId);
        }else if (Enumerate.OperationType.COLLECT.equals(operationType)){//取件
            homePageService.decrCollectingNoCount(userId);
            homePageService.incCollectNoCount(userId);
        }else if (Enumerate.OperationType.SIGN.equals(operationType)){//签收
            homePageService.decrSendingNoCount(orderUserId);
            homePageService.incSignNoCount(userId);
        }else if (Enumerate.OperationType.SIGN_FAIL.equals(operationType)){//异常签收
            homePageService.decrSendingNoCount(orderUserId);
            homePageService.incProblemNoCount(userId);
        }else if (Enumerate.OperationType.CANCEL.equals(operationType)){//取消
            homePageService.decrCollectingNoCount(userId);
        }else if (Enumerate.OperationType.SYNC.equals(operationType)){//同步派件
            //int sendingNo = statisticsService.sendIngNo(userId);
            homePageService.setSendingNoCount(userId, -1);
        }else if (Enumerate.OperationType.SCANDB.equals(operationType)){//扫描派件(db)
            if (!userId.equals(orderUserId)) {
                homePageService.decrSendingNoCount(orderUserId);
                homePageService.incSendingNoCount(userId);
            }
        }else if (Enumerate.OperationType.SCANJG.equals(operationType)) {//扫描派件(金刚)
            homePageService.incSendingNoCount(userId);
        }
        cacheUtil.invalidByRedis(OrderService.buildAllVOrderKey(userId, Enumerate.OrderType.ALL));
        cacheUtil.invalidByRedis(OrderService.buildAllVOrderKey(userId, Enumerate.OrderType.DELIVERY));
        cacheUtil.invalidByRedis(OrderService.buildAllVOrderKey(userId, Enumerate.OrderType.COLLECT));
    }
    //更新首页待派待取数据
    public void refreshHomePage(Long userId, int sendingNo, int collectingNo) {
        homePageService.setSendingNoCount(userId, sendingNo);
        homePageService.setCollectingNoCount(userId, collectingNo);
    }
    //更新首页今日签收数据
    public void refreshHomeSigned(Long userId, int signNo) {
        homePageService.setSignNoCount(userId, signNo);
    }
    //更新首页今日已取数据
    public void refreshHomeCollected(Long userId, int collectNo) {
        homePageService.setCollectNoCount(userId,  collectNo);
    }
    //更新首页问题件数据
    public void refreshHomeFailed(Long userId, int failedNo) {
        homePageService.setProblemNoCount(userId, failedNo);
    }
}
