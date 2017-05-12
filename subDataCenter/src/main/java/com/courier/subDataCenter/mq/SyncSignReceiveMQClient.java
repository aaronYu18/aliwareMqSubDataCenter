package com.courier.subDataCenter.mq;

import com.courier.commons.mq.client.base.MQReceiveClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.vModel.VSignOrder;
import com.courier.core.service.DeliveryOrderService;
import com.courier.core.service.UserService;
import com.courier.subDataCenter.listenter.DataCheck;
import com.courier.subDataCenter.thread.SyncSignReceiveDeal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2016/6/23.
 */
public class SyncSignReceiveMQClient extends MQReceiveClient {
    private static final Logger logger = LoggerFactory.getLogger(SyncSignReceiveMQClient.class);
    private static Logger jobLogger = LoggerFactory.getLogger("SIGN_DELIVERY_LOG");
    private static Logger signOrdersLog = LoggerFactory.getLogger("sign_orders_log");

    @Autowired private DeliveryOrderService deliveryOrderService;
    @Autowired private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired private DataCheck dataCheck;
    @Autowired private UserService userService;
    @Autowired private CacheUtil cacheUtil;

    @Value("${sync.timer.delay.millisecond}") private int delayMillisecond;
    @Value("${sync.sign.max.pool.size}") private int maxPoolSize;
    @Value("${sync.sign.batch.insert.size}") private int batchDealNo;

    private final List<VSignOrder> orders = new ArrayList<>(maxPoolSize);
    private static Timer timer = new Timer();


    @Override
    public void receive() {
        dataCheck.addMQReceiveClient(this);
        this.receiveTopicOrQueue();
    }

    @Override
    public void sendSync(MQPacket packet) {
        if(packet == null) return;
        List<VSignOrder> list = (List<VSignOrder>) packet.getBody().getContent();
        if(CollectionUtils.isEmpty(list)) return;

        //  打印日志
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            signOrdersLog.info("get sign data from mq, size is {}, content is : {}", list.size(), objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            signOrdersLog.info("sign order list convert to json error, error is {}", e.getMessage());
        }

        synchronized (this){
            this.orders.addAll(list);
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

        SyncSignReceiveDeal deal = new SyncSignReceiveDeal(batchDealNo, deliveryOrderService, orders, userService, cacheUtil);
        Thread thread = new Thread(deal);
        threadPoolTaskExecutor.execute(thread);

        // todo 清空list
        orders.clear();
        jobLogger.info("cache pool is empty ");
    }
}
