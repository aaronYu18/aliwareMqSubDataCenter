package com.courier.commons.aliwareMq.base.push.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.ons.api.order.OrderProducer;

import java.util.Properties;

/**
 * Created by aaron on 2017/3/8.
 */
public class OrderProducerBean implements OrderProducer{
    private Properties properties;
    private OrderProducer producer;

    public OrderProducerBean() {
    }

    public void start() {
        if(null == this.properties) {
            throw new ONSClientException("properties not set");
        } else {
            this.producer = ONSFactory.createOrderProducer(this.properties);
            this.producer.start();
        }
    }

    public void shutdown() {
        if(this.producer != null) {
            this.producer.shutdown();
        }

    }

    public SendResult send(Message message, String shardingKey) {
        return this.producer.send(message, shardingKey);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean isStarted() {
        return this.producer.isStarted();
    }

    public boolean isClosed() {
        return this.producer.isClosed();
    }
}
