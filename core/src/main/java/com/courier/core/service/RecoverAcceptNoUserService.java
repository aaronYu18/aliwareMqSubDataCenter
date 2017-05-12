package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.dao.CollectOrderMapper;
import com.courier.db.entity.CollectOrder;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by bin on 2015/12/18.
 */
@Service
public class RecoverAcceptNoUserService {

    public static final Logger logger = LoggerFactory.getLogger(RecoverAcceptNoUserService.class);

    @Autowired
    private CollectOrderService collectOrderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CacheUtil cacheUtil;

    public void recoverAcceptNoUser() {
        List<CollectOrder> list = collectOrderService.findWaitAcceptOrders();
        Map<String, List> map = coverListMap(list);
        Iterator<Map.Entry<String, List>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List> object = iterator.next();
            String key = object.getKey();
            List<CollectOrder> valueList = object.getValue();
            String orderListKey = String.format(CacheConstant.COLLECTION_ORDER_CITY_KEY, key);
            boolean existOrderListKey = cacheUtil.isExistByRedis(orderListKey);
            if (!existOrderListKey) {
                for (CollectOrder order:valueList) {
                    redisService.putToRedis(order);
                }
            }
        }

    }

    private Map<String, List> coverListMap(List<CollectOrder> list) {
        Map<String, List> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            String senderCity = null;
            for (CollectOrder collectOrder : list) {
                senderCity = collectOrder.getSenderCity();
                List tempList = map.get(senderCity);

                if (CollectionUtils.isEmpty(tempList))
                    tempList = new ArrayList<>();
                tempList.add(collectOrder);

                map.put(senderCity, tempList);
            }
        }
        return map;
    }


}
