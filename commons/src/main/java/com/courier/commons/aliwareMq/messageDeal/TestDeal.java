package com.courier.commons.aliwareMq.messageDeal;

import com.aliyun.openservices.ons.api.Action;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.json.GsonUtil;

import org.springframework.stereotype.Component;

/**
 * Created by user on 2017/3/9.
 */
@Component
public class TestDeal extends com.courier.commons.aliwareMq.base.push.listener.BaseMessageListener {
    @Override
    public Action deal(MQPacket packet) {
        System.out.println(GsonUtil.toJson(packet));

        return Action.CommitMessage;
    }
}
