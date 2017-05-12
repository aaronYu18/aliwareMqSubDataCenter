package com.courier.subDataCenter.thread;

import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.service.AppointmentOrderService;
import com.courier.core.service.DeliveryOrderService;
import com.courier.core.vModel.PushDeliveryOrderDealResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2016/6/17.
 */
@Component
public class SyncPoolDeal{
    private static Logger jobLogger = LoggerFactory.getLogger("PUSH_DELIVERY_LOG");
    private static Logger deliveryOrdersLog = LoggerFactory.getLogger("delivery_orders_log");

    @Autowired private DeliveryOrderService deliveryOrderService;
    @Autowired private AppointmentOrderService appointmentOrderService;
    @Autowired private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired private CacheUtil cacheUtil;

    @Value("${sync.delivery.max.pool.size}") private int maxPoolSize;
    @Value("${sync.delivery.batch.insert.size}") private int batchDealNo;
    @Value("${sync.timer.delay.millisecond}") private int delayMillisecond;

    private final List<PushDeliveryOrderDealResult> orders = new ArrayList<>(maxPoolSize);
    private static Timer timer = new Timer();



    public void addToPool(PushDeliveryOrderDealResult result){
        if(result == null) return;

        synchronized (this){
            this.orders.add(result);
            //todo step 1. 取消定时器
            timer.cancel();

            //todo step 2. 判断缓存池是否已满
            if(this.orders.size() >= maxPoolSize) deal(false);

            //todo step 3. 启动定时器
            timer = new Timer();
            timer.schedule(new TimerTask() { public void run() { deal(true); } }, delayMillisecond);
        }

    }

    private void deal(boolean isTimer) {
        int size = this.orders.size();
        jobLogger.info("begin to exec deal, because {}, order number is {}", isTimer ? "scheduled time" : "pool is full", size);
        if(size == 0) return;

        DBDeal dbDeal = new DBDeal(batchDealNo, deliveryOrderService, appointmentOrderService, orders, cacheUtil);
        Thread thread = new Thread(dbDeal);
        threadPoolTaskExecutor.execute(thread);

        // todo 清空list
        orders.clear();
        jobLogger.info("cache pool is empty ");
    }


}
