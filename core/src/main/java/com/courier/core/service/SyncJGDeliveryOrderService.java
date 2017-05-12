package com.courier.core.service;

import com.courier.commons.model.jinGang.DeliveryOrderSyncMqBean;
import com.courier.commons.mq.client.base.MQSendClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.DateUtil;
import com.courier.core.convert.MqPacketConvert;
import com.courier.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by vincent on 16/2/29.
 */
@Service
public class SyncJGDeliveryOrderService {
    private static final Logger logger = LoggerFactory.getLogger(SyncJGDeliveryOrderService.class);

    @Value("${sync.back.days}") private int BACK_DAYS;

    // todo 同步单个用户
    public void dealUser(User user, String mailNo, MQSendClient deliveryOrderSyncMQClient) {
        if(user == null || user.getId() == null || user.getId() == 0l) return;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -BACK_DAYS);
        String beginT = DateUtil.toSeconds(calendar.getTime());

        DeliveryOrderSyncMqBean bean = new DeliveryOrderSyncMqBean(mailNo, beginT, user.getJobNo(), user.getOrgCode(), user.getId());
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(bean);
            deliveryOrderSyncMQClient.send(mqPacket);
        } catch (Exception e) {
            logger.error("send delivery order sync bean by mq error, userId is {}, message:{}", user.getId(), e.getMessage());
        }
    }
}
