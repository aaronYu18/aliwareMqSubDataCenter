package com.courier.core.jingang.convert;

import com.courier.commons.model.jinGang.JGTakingModelReq;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有单取件转换 与 无单取件转换
 * Created by bin on 2015/11/8.
 */
public class JGTakingModelReqConvert {
    public static final Logger logger = LoggerFactory.getLogger(JGTakingModelReqConvert.class);

    public static JGTakingModelReq convertObj(CollectOrder order, User user, String uuid, Byte deviceType,UserLoginRecord userLoginRecord,String updateInfoClient) {
        if (order == null) return null;

        JGTakingModelReq req = new JGTakingModelReq();
        String mailNo = order.getMailNo();

        req.setUuid(uuid);
        req.setOpcode("311");
        req.setWeight(judgeNull(order.getWeight(), "0"));
        req.setTimeEffectiveCode(judgeNull(order.getAgeing(), ""));
        req.setDesCity(judgeNull(order.getReceiverCityName(), ""));
        req.setDesCountry(judgeNull(order.getReceiverAreaName(), ""));
        req.setDesprov(judgeNull(order.getReceiverProvinceName(), ""));
        req.setDesplace(judgeNull(order.getReceiverAddress(), ""));
        req.setOper_code(judgeNull(user.getJobNo(), ""));
        req.setMobile(judgeNull(user.getPhone(), ""));
        req.setOrgCode(judgeNull(user.getOrgCode(), ""));
        req.setDesOrgCode("");
        req.setUserName(judgeNull(user.getUsername(), ""));
        req.setDeviceType(Enumerate.PlatformType.getByCode(deviceType == null ? (byte) 0 : deviceType));
        req.setWaybillNo(mailNo);
        if (userLoginRecord != null) req.setTokenId(userLoginRecord.getYtoToken());
        /**
         * 更新面单
         */
        req.setClientId(updateInfoClient);
        req.setTxLogisticID(order.getSourceKey());
        req.setMailNo(mailNo);


        return req;
    }


    /**************** begin private method ***************/
    // todo 如果input为null，使用默认值
    private static String judgeNull(Object input, String defaultVal){
        if(input == null) return defaultVal;

        return input.toString();
    }
}
