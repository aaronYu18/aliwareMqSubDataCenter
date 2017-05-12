package com.courier.dataCenter.controller;

import com.courier.commons.aliwareMq.base.push.consumer.BaseConsumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bin on 2015/11/11.
 */
public class AliwareMqConsumerTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("mq/consumer.xml");
        BaseConsumer consumer = (BaseConsumer) ctx.getBean("testConsumer");
        System.out.println("BaseConsumer Started");
    }
}