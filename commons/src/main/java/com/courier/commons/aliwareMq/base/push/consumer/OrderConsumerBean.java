package com.courier.commons.aliwareMq.base.push.consumer;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.bean.SubscriptionExt;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by aaron on 2017/3/8.
 */
public class OrderConsumerBean implements OrderConsumer{
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumerBean.class);
    private Properties properties;
    private Map<Subscription, MessageOrderListener> subscriptionTable;
    private OrderConsumer consumer;

    public OrderConsumerBean() {
    }

    public void start() {
        if(null == this.properties) {
            throw new ONSClientException("properties not set");
        } else if(null == this.subscriptionTable) {
            throw new ONSClientException("subscriptionTable not set");
        } else {
            this.consumer = ONSFactory.createOrderedConsumer(this.properties);
            Iterator it = this.subscriptionTable.entrySet().iterator();

            while(true) {
                while(true) {
                    while(it.hasNext()) {
                        Map.Entry next = (Map.Entry)it.next();
                        if(this.consumer.getClass().getCanonicalName().equals("com.aliyun.openservices.ons.api.impl.notify.ConsumerImpl") && next.getKey() instanceof SubscriptionExt) {
                            SubscriptionExt subscription = (SubscriptionExt)next.getKey();
                            Method[] arr$ = this.consumer.getClass().getMethods();
                            int len$ = arr$.length;

                            for(int i$ = 0; i$ < len$; ++i$) {
                                Method method = arr$[i$];
                                if("subscribeNotify".equals(method.getName())) {
                                    try {
                                        method.invoke(this.consumer, new Object[]{subscription.getTopic(), subscription.getExpression(), Boolean.valueOf(subscription.isPersistence()), next.getValue()});
                                        break;
                                    } catch (Exception var9) {
                                        throw new ONSClientException("subscribeNotify invoke exception", var9);
                                    }
                                }
                            }
                        } else {
                            this.subscribe(((Subscription)next.getKey()).getTopic(), ((Subscription)next.getKey()).getExpression(), (MessageOrderListener)next.getValue());
                        }
                    }

                    this.consumer.start();
                    return;
                }
            }
        }
    }

    public void shutdown() {
        if(this.consumer != null) {
            this.consumer.shutdown();
        }

    }

    public void subscribe(String topic, String subExpression, MessageOrderListener messageOrderListener) {
        if(null == this.consumer) {
            throw new ONSClientException("subscribe must be called after consumerBean started");
        } else {
            this.consumer.subscribe(topic, subExpression, messageOrderListener);
        }
    }



    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Map<Subscription, MessageOrderListener> getSubscriptionTable() {
        return this.subscriptionTable;
    }

    public void setSubscriptionTable(Map<Subscription, MessageOrderListener> subscriptionTable) {
        this.subscriptionTable = subscriptionTable;
    }

    public boolean isStarted() {
        return this.consumer.isStarted();
    }

    public boolean isClosed() {
        return this.consumer.isClosed();
    }
}
