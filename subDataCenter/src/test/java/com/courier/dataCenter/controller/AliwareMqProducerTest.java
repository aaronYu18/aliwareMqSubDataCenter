package com.courier.dataCenter.controller;

import com.courier.commons.aliwareMq.base.push.producer.BaseProducer;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.mq.packet.PacketBody;
import com.courier.commons.mq.packet.PacketHeader;
import com.courier.commons.push.JPush;
import com.courier.sdk.constant.Enumerate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bin on 2015/11/11.
 */
public class AliwareMqProducerTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("mq/producer.xml");
        BaseProducer producer = (BaseProducer) ctx.getBean("testProducer");

        MQPacket mqPacket = buildPushMQPacket("123","1234", "12345");
        producer.sender("aaron_mq_demo", "mq_test_tag", mqPacket);
    }



    private static MQPacket buildPushMQPacket(String jobNo, String title, String content){
        JPush jPush = new JPush();
        jPush.setAlias(jobNo);
        jPush.setContentType(Enumerate.ContentType.TEXTMESSAGE);
        jPush.setContent(title);
        jPush.getExtMap().put(JPush.JPushExtType.extcontent.toString(), content);

        MQPacket packet = new MQPacket();
        packet.setHeader(new PacketHeader());
        PacketBody body = new PacketBody();
        body.setContent(jPush);
        packet.setBody(body);
        return packet;
    }
}