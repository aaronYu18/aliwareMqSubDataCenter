package com.courier.core.convert;

import com.courier.commons.model.JGRecordOrder;
import com.courier.commons.mq.client.buiness.PickUpByNoRecordMQClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.Uuid;
import com.courier.db.entity.Branch;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 无单取件 录单转换类
 * Created by beyond on 2016/7/4.
 */
public class JGRecordOrderConvert {
    private static final Logger logger = LoggerFactory.getLogger(JGRecordOrderConvert.class);

    public static JGRecordOrder convert(CollectOrder order, User user, Branch branch) {
        if (order== null) return null;
        JGRecordOrder jgRecordOrder = new JGRecordOrder();
        jgRecordOrder.setId(new Uuid().getUIDStr());
        jgRecordOrder.setOrderNo("");
        jgRecordOrder.setWaybillNo(order.getMailNo());
        jgRecordOrder.setBillType("FT");
        jgRecordOrder.setSourceOrgCode(branch.getOrgCode());
        jgRecordOrder.setSendTime(DateUtil.toSeconds(order.getCreateTime()));
        jgRecordOrder.setTakingEmpCode(user.getJobNo());
        jgRecordOrder.setTakingEmpName(user.getUsername());
        jgRecordOrder.setSenderName(order.getSenderName());

        jgRecordOrder.setSenderProv(order.getSenderProvince());
        jgRecordOrder.setSenderCity(order.getSenderCity());
        jgRecordOrder.setSenderTown(order.getSenderArea());
        jgRecordOrder.setSenderApp(order.getSenderAddress());
        jgRecordOrder.setSenderMobilePhone(order.getSenderPhone());
        jgRecordOrder.setSenderTel(order.getSenderTelPhone());
        jgRecordOrder.setGoods(order.getGoodsName());
        jgRecordOrder.setReceiverName(StringUtils.isEmpty(order.getReceiverName())?order.getReceiverPhone():order.getReceiverName());
        jgRecordOrder.setReceiverProv(order.getReceiverProvince());
        jgRecordOrder.setReceiverCity(order.getReceiverCity());
        jgRecordOrder.setReceiverTown(order.getReceiverArea());
        jgRecordOrder.setReceiverApp(order.getReceiverAddress());
        jgRecordOrder.setReceiverMobilePhone(order.getReceiverPhone());
        jgRecordOrder.setReceiverTel(order.getReceiverPhone());
//        jgRecordOrder.setDesOrgCode();
        jgRecordOrder.setStatus("0");
        jgRecordOrder.setCreateTime(DateUtil.toSeconds(order.getCreateTime()));
        jgRecordOrder.setCreateUserCode(user.getJobNo());
        jgRecordOrder.setCreateUserName(user.getUsername());
        jgRecordOrder.setCreateOrgCode(user.getOrgCode());
        jgRecordOrder.setModifyTime(DateUtil.toSeconds(order.getCreateTime()));
        jgRecordOrder.setModifyUserCode(user.getJobNo());
        jgRecordOrder.setModifyUserName(user.getUsername());
        jgRecordOrder.setModifyOrgCode(user.getOrgCode());
        jgRecordOrder.setAuxOpCode("NEW");
        jgRecordOrder.setOpCode(350);
        jgRecordOrder.setIsGisSuccess("0");
        jgRecordOrder.setOrgName(branch==null?"":(StringUtils.isEmpty(branch.getTerminalName())?branch.getBranchName():branch.getTerminalName()));
        return jgRecordOrder;
    }

    /**
     * 如果必填字段为空  不放入MQ
     * @param recordOrder
     * @param pickUpByNoRecordMQClient
     */
    public static void sendMQMessage(JGRecordOrder recordOrder, PickUpByNoRecordMQClient pickUpByNoRecordMQClient) {
        if (!filter(recordOrder)){
            logger.info("recordOrder field is empty send  by PickUpByNoRecordMQClient , message is {}", recordOrder.toJson());
            return;
        }
        try {
            MQPacket mqPacket = com.courier.commons.mq.packet.MqPacketConvert.buildMqPacket(recordOrder);
            pickUpByNoRecordMQClient.send(mqPacket);
            logger.info("send  by PickUpByNoRecordMQClient , message is {}", recordOrder.toJson());
        } catch (Exception e) {
            logger.error("send PickUpByNoRecordMQClient  by mq error, error is {},message:{}", recordOrder.toJson(), e.getMessage());
        }
    }
    /*
        返回false 不合法
     */
    private static boolean filter(JGRecordOrder order){
        if (order==null) return false;
        if (StringUtils.isEmpty(order.getSenderName())||
                StringUtils.isEmpty(order.getSenderProv())||
                StringUtils.isEmpty(order.getSenderCity())||
                StringUtils.isEmpty(order.getSenderApp())||
                StringUtils.isEmpty(order.getReceiverName())||
                StringUtils.isEmpty(order.getReceiverProv())||
                StringUtils.isEmpty(order.getReceiverCity())||
                StringUtils.isEmpty(order.getReceiverApp())
        ) return false;
        return true;
    }
}
