package com.courier.commons.aliwareMq.base.push.listener;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;

import java.util.Date;

/**
 * MQ消息处理类
 */
public class BaseOrderMessageListener implements MessageOrderListener{


    @Override
    public OrderAction consume(Message message, ConsumeOrderContext consumeOrderContext) {
        System.out.println(new Date() + " Receive message, Topic is:" +
                message.getTopic() + ", MsgId is:" + message.getMsgID() + ", content is:" + new String(message.getBody()));
        //如果想测试消息重投的功能,可以将Action.CommitMessage 替换成Action.ReconsumeLater
        return OrderAction.Success;
    }
}
