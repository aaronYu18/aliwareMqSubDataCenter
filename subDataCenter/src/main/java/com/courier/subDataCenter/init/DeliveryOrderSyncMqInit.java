package com.courier.subDataCenter.init;

import com.courier.subDataCenter.mq.SyncDeliveryReceiveMQClient;
import com.courier.subDataCenter.mq.SyncSignReceiveMQClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by admin on 2016/3/25.
 */
@Component
public class DeliveryOrderSyncMqInit {
    @Autowired
    SyncDeliveryReceiveMQClient syncDeliveryReceiveMQClient;
    @Autowired
    SyncSignReceiveMQClient syncSignReceiveMQClient;

    @PostConstruct
    public void init(){
        syncDeliveryReceiveMQClient.run();
        syncSignReceiveMQClient.run();
    }
}
