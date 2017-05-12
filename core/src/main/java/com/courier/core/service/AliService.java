package com.courier.core.service;

import com.courier.commons.model.AliConstant;
import com.courier.commons.model.DouCallBeanRequest;
import com.courier.commons.model.SMSBeanRequest;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.mq.packet.MqPacketConvert;
import com.courier.core.mq.AliCallMQClient;
import com.courier.core.mq.AliMessageMQClient;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.entity.User;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by bin on 2015/11/11.
 */
@Service
@Transactional
public class AliService {

    private static final Logger logger = LoggerFactory.getLogger(AliService.class);

    @Autowired
    private AliCallMQClient aliCallMQClient;

    @Autowired
    private AliMessageMQClient aliMessageMQClient;

    @Autowired
    private UserService userService;

    /**
     * 阿里短信服务 (支持两种：code 与number)
     *
     * @param uuid
     * @param mailNoOrTel
     * @param content
     * @param smsType     Enumerate.AliQin.CodeSendsms  或 Enumerate.AliQin.NumberSendsms
     * @return
     */
    public ResponseDTO message(String uuid, String mailNoOrTel, String content, String smsType) {
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(mailNoOrTel)) return new ResponseDTO(CodeEnum.C1034);
        if (!Enumerate.AliQin.CodeSendsms.getMethod().equalsIgnoreCase(smsType) && !Enumerate.AliQin.NumberSendsms.getMethod().equalsIgnoreCase(smsType))
            return new ResponseDTO(CodeEnum.C1036);
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();
        try {
            MQPacket mqPacket = null;
            if (Enumerate.AliQin.CodeSendsms.getMethod().equalsIgnoreCase(smsType)) {
                mqPacket = codeSMS(mailNoOrTel, user, content, smsType);
            } else if (Enumerate.AliQin.NumberSendsms.getMethod().equalsIgnoreCase(smsType)) {
                mqPacket = numberSMS(mailNoOrTel, user, content, smsType);
            }

            if(mqPacket != null)
                aliMessageMQClient.send(mqPacket);
            logger.info("uuid:{},mailNoOrTel:{},smsType:{},sysNum:{}",uuid,mailNoOrTel,smsType,System.nanoTime());
            return new ResponseDTO(CodeEnum.C1000);
        } catch (Exception e) {
            logger.error("message:{}", e.getMessage());
        }
        return new ResponseDTO(CodeEnum.C2001);

    }

    /**
     * 面单号发信息
     *
     * @param mailNo
     * @param user
     * @param content
     * @param smsType
     * @return
     * @throws Exception
     */
    private MQPacket codeSMS(String mailNo, User user, String content, String smsType) throws Exception {
        SMSBeanRequest request = new SMSBeanRequest();
        request.setSmsType(smsType);
        request.setWaybillno(mailNo);
        request.setSmsDemoNo(AliConstant.sms_code6);
        request.setMobile(user.getPhone());                                                 //快递员电话
        request.setDisplayname(StringUtils.isEmpty(content) ? user.getUsername() : content);    //填写的话
        request.setJobNo(user.getJobNo());
        request.setOrgCode(user.getOrgCode());
        return MqPacketConvert.buildMqPacket(request);

    }

    /**
     * 手机号发信息
     *
     * @param telephone
     * @param user
     * @param content
     * @param smsType
     * @return
     * @throws Exception
     */
    private MQPacket numberSMS(String telephone, User user, String content, String smsType) throws Exception {
        SMSBeanRequest request = new SMSBeanRequest();
        request.setSmsType(smsType);
        request.setReceiverNum(telephone);
        request.setSmsDemoNo(AliConstant.sms_code5);
        request.setMobile(user.getPhone());                                                 //快递员电话
        request.setDisplayname(StringUtils.isEmpty(content) ? user.getUsername() : content);    //填写的话
        request.setJobNo(user.getJobNo());
        request.setOrgCode(user.getOrgCode());
        return MqPacketConvert.buildMqPacket(request);
    }

    /**
     * 阿里电话服务 (支持两种：code 与number)
     *
     * @param uuid
     * @param mailNoOrTel
     * @param callType    Enumerate.AliQin.CodeDoublecall  或 Enumerate.AliQin.NumberDoublecall
     * @return
     */
    public ResponseDTO call(String uuid, String mailNoOrTel, String callType) {
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(mailNoOrTel)) return new ResponseDTO(CodeEnum.C1034);
        if (!Enumerate.AliQin.CodeDoublecall.getMethod().equalsIgnoreCase(callType) && !Enumerate.AliQin.NumberDoublecall.getMethod().equalsIgnoreCase(callType))
            return new ResponseDTO(CodeEnum.C1036);
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();
        try {
            MQPacket mqPacket = null;
            if (Enumerate.AliQin.CodeDoublecall.getMethod().equalsIgnoreCase(callType)) {
                mqPacket = codeCall(mailNoOrTel, user, callType);
            }
            if (Enumerate.AliQin.NumberDoublecall.getMethod().equalsIgnoreCase(callType)) {
                mqPacket = numberCall(mailNoOrTel, user, callType);
            }
            aliCallMQClient.send(mqPacket);
            return new ResponseDTO(CodeEnum.C1000);
        } catch (Exception e) {
            logger.error("message:{}", e.getMessage());
        }
        return new ResponseDTO(CodeEnum.C2001);
    }

    /**
     * 面单号打电话
     *
     * @param mailNo
     * @param user
     * @param callType
     * @return
     * @throws Exception
     */
    private MQPacket codeCall(String mailNo, User user, String callType) throws Exception {
        DouCallBeanRequest request = new DouCallBeanRequest();
        request.setCallType(callType);

        request.setWaybillno(mailNo);
        request.setCallerShowNum(AliConstant.ytoMobile);
        request.setCalledShowNum(user.getPhone());
        request.setCallerNum(user.getPhone());
        request.setJobNo(user.getJobNo());
        request.setOrgCode(user.getOrgCode());
        return MqPacketConvert.buildMqPacket(request);

    }

    /**
     * 手机号打电话
     *
     * @param telephone
     * @param user
     * @param callType
     * @return
     * @throws Exception
     */
    private MQPacket numberCall(String telephone, User user, String callType) throws Exception {
        DouCallBeanRequest request = new DouCallBeanRequest();
        request.setCallType(callType);
        request.setCallType(Enumerate.AliQin.NumberDoublecall.getMethod());
        request.setCalledNum(telephone);
        request.setCallerShowNum(AliConstant.ytoMobile);
        request.setCalledShowNum(user.getPhone());
        request.setCallerNum(user.getPhone());
        request.setJobNo(user.getJobNo());
        request.setOrgCode(user.getOrgCode());
        return MqPacketConvert.buildMqPacket(request);

    }
}
