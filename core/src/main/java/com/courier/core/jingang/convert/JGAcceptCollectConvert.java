package com.courier.core.jingang.convert;

import com.courier.commons.model.xml.Receiver;
import com.courier.commons.model.xml.RequestOrder;
import com.courier.commons.model.xml.Sender;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.User;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bin on 2015/11/8.
 */
public class JGAcceptCollectConvert {
    public static final Logger logger = LoggerFactory.getLogger(JGAcceptCollectConvert.class);

    public static RequestOrder convertObj(CollectOrder order, User user, String clientID, String customerId) {
        if (order == null) return null;

        RequestOrder requestOrder = new RequestOrder(clientID, order.getSourceKey(), customerId, new Sender(), new Receiver());
        Sender sender = new Sender(order.getSenderName(), "", order.getSenderPhone(), order.getSenderPhone(), order.getSenderProvinceName(),
                order.getSenderCityName() + "," + order.getSenderAreaName(), order.getSenderAddress());
        Receiver receiver = new Receiver(order.getReceiverName(), "", order.getReceiverPhone(), order.getReceiverPhone(), order.getReceiverProvinceName(),
                order.getReceiverCityName() + "," + order.getReceiverAreaName(), order.getReceiverAddress());
        requestOrder.setSender(sender);
        requestOrder.setReceiver(receiver);
        requestOrder.setCustomerId(order.getCustomerID());
        requestOrder.setSpecial(Enumerate.GoodSpecial.normal.getCode().toString());
        /**
         * 用户接单了
         */
        if (user != null) {
            requestOrder.setAcceptUserName(user.getUsername());
            requestOrder.setAcceptOrgName("");
            requestOrder.setAcceptOrgCode(user.getOrgCode());
            requestOrder.setAcceptUserCode(user.getJobNo());

        }

        return requestOrder;
    }

}
