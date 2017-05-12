package com.courier.commons.aliwareMq.base.push.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.json.GsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MQ消息处理类
 */
public abstract class BaseMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(BaseMessageListener.class);
    private boolean bPauseReceive = false;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        return this.dealMessage(message);
    }

    public abstract Action deal(MQPacket packet);

    protected Action dealMessage(Message message) {
        if(bPauseReceive) return Action.ReconsumeLater;
        if(message == null) return Action.CommitMessage;

        MQPacket packet = null;
        String body = new String(message.getBody());
        try {
            packet = GsonUtil.getBean(body, MQPacket.class);
        }catch (Exception e){
            logger.error("convert body to json failed, json is \"{}\"", body);
            //失败重试
            return Action.ReconsumeLater;
        }

        return deal(packet);
    }

    public boolean isbPauseReceive() {
        return bPauseReceive;
    }

    public void setbPauseReceive(boolean bPauseReceive) {
        logger.info("MQReceiveClient {} Connect Status >>{}<<", this.getClass().getSimpleName(), bPauseReceive ? "pause" : "resume");
        this.bPauseReceive = bPauseReceive;
    }

}
