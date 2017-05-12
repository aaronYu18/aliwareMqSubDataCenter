package com.courier.subDataCenter.thread;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.vModel.VSignOrder;
import com.courier.core.service.DeliveryOrderService;
import com.courier.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 2016/6/17.
 */
public class SyncSignReceiveDeal implements Runnable {
    private static Logger jobLogger = LoggerFactory.getLogger("SIGN_DELIVERY_LOG");
    private static Logger signOrdersLog = LoggerFactory.getLogger("sign_orders_log");

    private int batchDealNo;              // 批量操作数目
    private CopyOnWriteArrayList<VSignOrder> list = new CopyOnWriteArrayList<>();
    private DeliveryOrderService deliveryOrderService;
    private UserService userService;
    private CacheUtil cacheUtil;


    public SyncSignReceiveDeal(int batchDealNo, DeliveryOrderService deliveryOrderService, List<VSignOrder> list,
                               UserService userService, CacheUtil cacheUtil) {
        this.batchDealNo = batchDealNo;
        this.deliveryOrderService = deliveryOrderService;
        this.list.addAll(list);
        this.userService = userService;
        this.cacheUtil = cacheUtil;
    }

    @Override
    public void run() {
        if(list == null || list.size() == 0) return;

        List<String> deletes = new ArrayList<>();
        int redisSize = 0;

        int totalSize = list.size();
        for(VSignOrder obj : list){
            if (obj == null) continue;

            if (StringUtils.isEmpty(obj.getWaybillNo())){
                signOrdersLog.error("convert failed, mailNo is null, order info is : {}", obj.toJson());
                continue;
            }

            String waybillNo = obj.getWaybillNo().toUpperCase();
            deletes.add(waybillNo);

            String jobNo = obj.getCreateUserCode();
            if(!StringUtils.isEmpty(jobNo) && !"null".equalsIgnoreCase(jobNo)) {
                Long userId = userService.getUserIdByJobNo(jobNo);
                if(userId != null){
                    String lock = String.format(CacheConstant.SYNC_LOCK_USER_MAILNO_KEY, userId, waybillNo);
                    cacheUtil.invalidByRedis(lock);
                    redisSize++;
                }else{
                    signOrdersLog.error("clear redis failed, find userId by jobNo failed, order info is : {}", obj.toJson());
                }
            }else {
                signOrdersLog.error("clear redis failed, jobNo is invalid, order info is : {}", obj.toJson());
            }
        }

        int deleteSize = 0;
        // todo 删除
        if(deletes != null && deletes.size() > 0){
            deleteSize = deletes.size();
            for (int i=0; i<deleteSize; i += batchDealNo) {
                int k = i + batchDealNo;
                if (k >= deleteSize) k = deleteSize;

                List<String> subList = deletes.subList(i, k);
                deliveryOrderService.batchDeleteByMailNoAndStatus(new ArrayList<>(subList));
            }
        }

        /*int deleteSize = deletes == null ? 0 : deletes.size();*/

        jobLogger.info("deal push sign data over, total number is {}, delete {}, clear redis {}", totalSize, deleteSize, redisSize);
    }



       /* final int[] invalidRedisSize = {0};
        // todo 删除
        for (int i=0; i<totalSize; i += batchDealNo) {
            int k = i + batchDealNo;
            if (k >= deleteSize) k = deleteSize;

            CopyOnWriteArrayList<VSignOrder> subList = (CopyOnWriteArrayList<VSignOrder>) list.subList(i, k);
            Stream<VSignOrder> baseStream = subList.stream()
                    .filter(obj -> obj != null && !StringUtils.isEmpty(obj.getWaybillNo()));

            Stream<String> mailNoStream = baseStream
                    .map(obj -> obj.getWaybillNo().toUpperCase());

            // todo 删除数据库
            deleteSize += mailNoStream.count();
            List<String> mailNos = mailNoStream.collect(Collectors.toList());
            deliveryOrderService.batchDeleteByMailNoAndStatus(mailNos);

            // todo 删除redis
            baseStream.map(obj -> {
                Long userId = userService.getUserIdByJobNo(obj.getCreateUserCode());
                if(userId != null)
                    return String.format(CacheConstant.SYNC_LOCK_USER_MAILNO_KEY, userId, obj.getWaybillNo().toUpperCase());
                return null;
            }).forEach(str -> {
                if (!StringUtils.isEmpty(str)){
                    jobLogger.info("invalid lock key is {}", str);
                    cacheUtil.invalidByRedis(str);
                    invalidRedisSize[0]++;
                }
            });
        }*/
}
