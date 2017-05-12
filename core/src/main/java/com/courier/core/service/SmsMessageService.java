package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.SmsEnum;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.RandomValidateCodeUtils;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.mq.packet.MqPacketConvert;
import com.courier.core.mq.SMSMQClient;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.SmsMessageMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.SmsMessage;
import com.courier.db.entity.User;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class SmsMessageService extends BaseManager<SmsMessage> {
    private static final Logger logger = LoggerFactory.getLogger(SmsMessageService.class);

    @Autowired
    UserService userService;
    @Autowired
    CacheUtil cacheUtil;
    @Value("${sms.validate.expiration.time}")
    private String smsValidateExpirationTime;
    @Autowired
    private SMSMQClient smsmqClient;
    @Autowired
    SmsMessageMapper smsMessageMapper;
    @Autowired
    SmsTemplateService smsTemplateService;

    /**
     * 发送短信
     */
    public ResponseDTO sendSms(String uuid, String phone, Enumerate.CaptchaType smsType){
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();

        String validCode = buildValidCode(buildBindPhoneSmsKey(uuid, phone, smsType));
        //短信内容
        ResponseDTO findResult = smsTemplateService.findByType(smsType.getType());
        if(findResult.getCodeEnum() != CodeEnum.C1000) return findResult;

        String content = (String) findResult.getT2();
        content = content.format(content, validCode);

        insertSms(phone, content, smsType.getType(), user);

        return new ResponseDTO(CodeEnum.C1000, null, validCode);
    }
     /**
     * 验证短信
     */
    public ResponseDTO validateSms(String uuid, String phone, String code, Enumerate.CaptchaType smsType){
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if(responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;

        //短信内容
        String key = buildBindPhoneSmsKey(uuid, phone, smsType);

        String cacheCode = cacheUtil.getCacheByFromRedis(key, String.class);
        // todo 不存在code
        if(StringUtils.isEmpty(code)) return new ResponseDTO(CodeEnum.C2007);
        // todo code不一致
        if(!cacheCode.equals(code)) return new ResponseDTO(CodeEnum.C2007);

        cacheUtil.invalidByRedis(key);

        // todo 验证成功，给用户绑定手机
        if(Enumerate.CaptchaType.BIND_PHONE == smsType)
            userService.bindPhone(uuid, phone);

        return new ResponseDTO(CodeEnum.C1000);
    }






    /***************** begin private method *************/

    /**
     * 生成验证码并放入redis中
     * @param key
     * @return
     */
    private String buildValidCode(String key){
        String code = cacheUtil.getCacheByFromRedis(key, String.class);
        // todo  redis存在用最新的， 不存在重新生成并放入redis
        if(!StringUtils.isEmpty(code)) return code;

        code = RandomValidateCodeUtils.createValidateCode(4);

        try {
            cacheUtil.putData2RedisByTime(key, Integer.valueOf(smsValidateExpirationTime), code);
            logger.info("put binding phone validate code to redis, validate code is {}", code);
        } catch (Exception e) {
            logger.info("put binding phone validate code to redis error, validate code is {}, error is {}", code, e.getMessage());
        }

        return code;
    }

    /**
     * 生成key
     */
    private String buildBindPhoneSmsKey(String uuild, String phone,  Enumerate.CaptchaType smsType) {
        String middleKey = "";
        if(smsType == Enumerate.CaptchaType.BIND_PHONE)
            middleKey = CacheConstant.SMS_BIND_PHONE_KEY;

        return String.format(CacheConstant.SMS_VALIDATE_KEY, middleKey, uuild, phone);
    }

    /**
     * 保存msg
     */
    private void insertSms(String phone, String content, Byte type, User user) {
        SmsMessage msg = new SmsMessage();
        msg.setType(type);
        msg.setContent(content);
        msg.setPhone(phone);
        msg.setPriority(SmsEnum.SmsPriorityEnum.five.getCode());
        msg.setStatus(SmsEnum.SmsStatusEnum.SENDING.getCode());
        msg.setOrgCode(user.getOrgCode());
        msg.setJobNo(user.getJobNo());

        smsMessageMapper.insert(msg);

        //  todo 调用mq发送短信
        sendMQMessage(msg);
    }

    /**
     * 封装包体
     * @param smsMessage
     */
    private void sendMQMessage(SmsMessage smsMessage) {
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(smsMessage);

            smsmqClient.send(mqPacket);

            logger.info("send sms by mq, message is {}", smsMessage.toJson());
        } catch (Exception e) {
            logger.info("send sms by mq error, error is {}", smsMessage.toJson(), e.getMessage());
        }
    }

    /**
     * 批量更新状态
     * @param lst
     */
    public void batchUpdateSMSMessageStatus(List lst){
        smsMessageMapper.batchUpdateSMSMessageStatus(lst);
    }

    /**
     * 批量插入
     * @param lst
     */
    public void batchInsertSMSMessage(List lst){
        smsMessageMapper.batchInsertSMSMessage(lst);
    }

    @Override
    public SmsMessage getEntity() {
        return new SmsMessage();
    }

    @Override
    public BaseMapper<SmsMessage> getBaseMapper() {
        return smsMessageMapper;
    }
}
