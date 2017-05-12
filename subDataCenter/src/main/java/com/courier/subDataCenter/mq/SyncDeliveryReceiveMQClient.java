package com.courier.subDataCenter.mq;

import com.courier.commons.mq.client.base.MQReceiveClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.vModel.VDeliveryOrder;
import com.courier.core.jingang.convert.JGDeliveryOrderConvert;
import com.courier.core.service.BranchService;
import com.courier.core.service.UserService;
import com.courier.core.vModel.PushDeliveryOrderDealResult;
import com.courier.subDataCenter.listenter.DataCheck;
import com.courier.subDataCenter.thread.SyncPoolDeal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ryan on 15/11/9.
 */
public class SyncDeliveryReceiveMQClient extends MQReceiveClient {
    private static final Logger logger = LoggerFactory.getLogger(SyncDeliveryReceiveMQClient.class);
    private static Logger jobLogger = LoggerFactory.getLogger("PUSH_DELIVERY_LOG");
    private static Logger deliveryOrdersLog = LoggerFactory.getLogger("delivery_orders_log");

    @Autowired private CacheUtil cacheUtil;
    @Autowired private UserService userService;
    @Autowired private BranchService branchService;
    @Autowired private DataCheck dataCheck;
    @Autowired private SyncPoolDeal syncPoolDeal;

    @Value("${baidu.gps.url}") private String baiDuUrl;
    @Value("${baidu.gps.regEx}")private String regEx;
    @Value("${des.secret.key}") private String secretKey;
    @Value("${courier.push.url}") private String url;

    @Override
    public void receive() {
        dataCheck.addMQReceiveClient(this);
        this.receiveTopicOrQueue();
    }

    @Override
    public void sendSync(MQPacket packet) {
        if(packet == null) return;
        List<VDeliveryOrder> list = (List<VDeliveryOrder>) packet.getBody().getContent();
        if(CollectionUtils.isEmpty(list)) return;

        //  打印日志
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            deliveryOrdersLog.info("get delivery data from mq, size is {}, content is : {}", list.size(), objectMapper.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            deliveryOrdersLog.info("delivery order list convert to json error, error is {}", e.getMessage());
        }

        for (VDeliveryOrder vOrder : list){
            PushDeliveryOrderDealResult result = JGDeliveryOrderConvert.convertPushObj(vOrder, cacheUtil, branchService, userService, baiDuUrl, regEx, secretKey,url);
            if(result != null) syncPoolDeal.addToPool(result);
        }
    }
}
