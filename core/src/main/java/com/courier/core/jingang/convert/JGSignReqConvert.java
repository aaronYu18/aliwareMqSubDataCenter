package com.courier.core.jingang.convert;

import com.courier.commons.model.jinGang.JGSignReq;
import com.courier.commons.util.DateUtil;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;
import com.courier.sdk.constant.Enumerate;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by bin on 2015/11/9.
 */
public class JGSignReqConvert {

    public static JGSignReq convertObj(DeliveryOrder order, User user, UserLoginRecord userLoginRecord, String uuid) {
        if (order == null || user == null || userLoginRecord == null) return null;

        JGSignReq signReq = new JGSignReq();
        signReq.setUuid(uuid);
        String mac = order.getMac();
        String jobNo = user.getJobNo();
        String mailNo = order.getMailNo();
        String orgCode = user.getOrgCode();
        String signPic = order.getSignPic();
        String username = user.getUsername();
        Date signTime = order.getSignTime();
        Date jGSignDate = order.getjGCreateTime();
        Byte orderStatus = order.getOrderStatus();
        String failedCode = order.getFailedCode();
        Double freightMoney = order.getFreightMoney();
        String ytoToken = userLoginRecord.getYtoToken();
        Byte signPersonType = order.getSignPersonType();
        Byte deviceType = userLoginRecord.getDeviceType();
        String signPersonName = order.getSignPersonName();
        Double collectionMoney = order.getCollectionMoney();
        String failedDescription = order.getFailedDescription();
        if (StringUtils.isEmpty(failedDescription))
            failedDescription = Enumerate.SignFailReasonDetail.getNameByCode(failedCode);

        signReq.setMac(mac == null ? "" : mac);

        signReq.setRouteCode("1");
        signReq.setAuxOpCode("NEW");
        signReq.setWaybillNo(mailNo);
        signReq.setTokenID(ytoToken);
        signReq.setHasSignPic(signPic == null ? "" : signPic);
        signReq.setCreateUserCode(jobNo);
        signReq.setCreateOrgCode(orgCode);
        signReq.setCreateUserName(username);
        signReq.setReceiverSignoff(signPersonName == null ? "" : signPersonName);

        signReq.setDeliveryFailReasonCode(trimFailedCode(failedCode));
        signReq.setDeliveryFailReason(failedDescription == null ? "" : failedDescription);
        signReq.setCreateTime(signTime == null ? DateUtil.dateToStr(new Date()) : DateUtil.dateToStr(signTime));
        signReq.setAmountAgency(freightMoney == null ? "" : freightMoney.toString());
        signReq.setModifytime(signTime == null ? DateUtil.dateToStr(new Date()) : DateUtil.dateToStr(signTime));

        signReq.setAmountCollected(collectionMoney == null ? "" : collectionMoney.toString());
        signReq.setDeviceType(Enumerate.PlatformType.getByCode(deviceType == null ? (byte) 0 : deviceType));
        if (orderStatus == Enumerate.DeliveryOrderStatus.normalSign.getType()) {
            signReq.setSignoffTypeCode(signPersonType == null ? Enumerate.SignTypeEnum.getTypeByName(signPersonName) : Enumerate.SignTypeEnum.getTypeByCode(signPersonType));//兼容老版本 signPersonType为null
            signReq.setOpcode("745");
        }
        if (orderStatus == Enumerate.DeliveryOrderStatus.unusualSign.getType()) {
            signReq.setSignoffTypeCode(signPersonType == null ? "" : Enumerate.SignTypeEnum.getTypeByCode(signPersonType));//兼容老版本 signPersonType为null
            signReq.setOpcode("746");
        }

        return signReq;
    }
    private static String trimFailedCode(String code){
        if (StringUtils.isEmpty(code)||code.trim().equals("")) return "";
        return String.format("%4s",code).replaceAll(" ","0");
    }

}
