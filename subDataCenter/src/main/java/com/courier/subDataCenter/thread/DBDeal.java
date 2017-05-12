package com.courier.subDataCenter.thread;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.service.AppointmentOrderService;
import com.courier.core.service.DeliveryOrderService;
import com.courier.core.vModel.PushDeliveryOrderDealResult;
import com.courier.db.entity.AppointmentOrder;
import com.courier.db.entity.DeliveryOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/6/29.
 */
public class DBDeal implements Runnable{
    private static Logger jobLogger = LoggerFactory.getLogger("PUSH_DELIVERY_LOG");
    private static Logger deliveryOrdersLog = LoggerFactory.getLogger("delivery_orders_log");


    private AppointmentOrderService appointmentOrderService;
    private DeliveryOrderService deliveryOrderService;
    private CacheUtil cacheUtil;
    private int batchDealNo;
    private CopyOnWriteArrayList<PushDeliveryOrderDealResult> orders  = new CopyOnWriteArrayList<>();

    public DBDeal(int batchDealNo, DeliveryOrderService deliveryOrderService, AppointmentOrderService appointmentOrderService, List<PushDeliveryOrderDealResult> orders, CacheUtil cacheUtil) {
        this.batchDealNo = batchDealNo;
        this.deliveryOrderService = deliveryOrderService;
        this.appointmentOrderService = appointmentOrderService;
        this.orders.addAll(orders);
        this.cacheUtil = cacheUtil;
    }

    @Override
    public void run() {
        if(CollectionUtils.isEmpty(orders)) return;

        List<DeliveryOrder> inserts = new ArrayList<>();
        List<DeliveryOrder> updates = new ArrayList<>();
        List<DeliveryOrder> deletes = new ArrayList<>();
        List<AppointmentOrder> appointmentOrders = new ArrayList<>();

        for(PushDeliveryOrderDealResult result : orders){
            if(result != null){
                PushDeliveryOrderDealResult.Type type = result.getType();
                DeliveryOrder deliveryOrder = result.getDeliveryOrder();
                AppointmentOrder appointmentOrder = result.getAppointmentOrder();

                if(PushDeliveryOrderDealResult.Type.delete.equals(type)) {
                    deletes.add(deliveryOrder);
                }else if(PushDeliveryOrderDealResult.Type.update.equals(type)){
                    updates.add(deliveryOrder);
                }else if(PushDeliveryOrderDealResult.Type.insert.equals(type)){
                    inserts.add(deliveryOrder);
                }

                if(null != appointmentOrder){
                    appointmentOrders.add(appointmentOrder);
                }
            }
        }

        int insertSize = 0, updateSize =0, deleteSize = 0, appointUpdateSize = 0;
        final int[] invalidRedisSize = {0};
        // todo 插入
        if(inserts != null && inserts.size() > 0){
            insertSize = inserts.size();
            for (int i=0; i<insertSize; i += batchDealNo) {
                int k = i + batchDealNo;
                if (k >= insertSize) k = insertSize;

                List<DeliveryOrder> subList = inserts.subList(i, k);
                try{
                    deliveryOrderService.batchInsert(new ArrayList<>(subList));
                }catch (Exception e){
                    jobLogger.error("batch insert into DB error, error info is {}", e.getMessage());
                    for (DeliveryOrder order : subList) {
                        try {
                            deliveryOrderService.save(order);
                        } catch (Exception e1) {
                            deliveryOrdersLog.error("single insert into DB error, order info is : {}, error is {}", order.toJson(), e1.getMessage());
                        }
                    }
                }
            }
        }
        // todo 更新
        if(updates != null && updates.size() > 0){
            updateSize = updates.size();
            for (int i=0; i<updateSize; i += batchDealNo) {
                int k = i + batchDealNo;
                if (k >= updateSize) k = updateSize;

                List<DeliveryOrder> subList = updates.subList(i, k);
                deliveryOrderService.batchUpdateByUidAndMailNo(new ArrayList<>(subList));
            }
        }
        // todo 删除
        if(deletes != null && deletes.size() > 0){
            deleteSize = deletes.size();
            for (int i=0; i<deleteSize; i += batchDealNo) {
                int k = i + batchDealNo;
                if (k >= deleteSize) k = deleteSize;

                // todo 删除数据库
                List<DeliveryOrder> subList = deletes.subList(i, k);
                List<String> mailNos = subList.stream().map(obj -> obj.getMailNo()).collect(Collectors.toList());
                deliveryOrderService.batchDeleteByMailNoAndStatus(mailNos);

                // todo 删除redis锁
                subList.stream().
                        map(obj -> String.format(CacheConstant.SYNC_LOCK_USER_MAILNO_KEY, obj.getUserId(), obj.getMailNo()))
                        .forEach(str -> {
                            if (!StringUtils.isEmpty(str)){
                                cacheUtil.invalidByRedis(str);
                                invalidRedisSize[0]++;
                            }
                        });
            }
        }
        // todo 更新appointmentOrder信息
        if(!CollectionUtils.isEmpty(appointmentOrders)){
            appointUpdateSize = appointmentOrders.size();
            for (int i=0; i<appointUpdateSize; i += batchDealNo) {
                int k = i + batchDealNo;
                if (k >= appointUpdateSize) k = appointUpdateSize;

                List<AppointmentOrder> subList = appointmentOrders.subList(i, k);
                appointmentOrderService.batchUpdateByIds(new ArrayList<>(subList));
            }
        }

        /*int insertSize = inserts == null ? 0 : inserts.size(), updateSize = updates == null ? 0 : updates.size(), deleteSize = deletes == null ? 0 : deletes.size();*/
        jobLogger.info("deal push delivery data over, total number is {}, insert {},  update {}, delete {}, clear redis {}; update appointmentOrder {}",
                orders.size(), insertSize, updateSize, deleteSize, invalidRedisSize[0], appointUpdateSize);

    }
}
