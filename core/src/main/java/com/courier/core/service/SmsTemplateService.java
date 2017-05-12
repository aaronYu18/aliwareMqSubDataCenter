package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.SmsTemplateMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.SmsTemplate;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class SmsTemplateService extends BaseManager<SmsTemplate> {
    private static final Logger logger = LoggerFactory.getLogger(SmsTemplateService.class);

    @Autowired
    CacheUtil cacheUtil;

    @Autowired
    SmsTemplateMapper smsTemplateMapper;

    @Override
    public SmsTemplate getEntity() {
        return new SmsTemplate();
    }

    @Override
    public BaseMapper<SmsTemplate> getBaseMapper() {
        return smsTemplateMapper;
    }

    /**
     * 发送短信
     */
    public ResponseDTO findByType(Byte captchaType){
        if(captchaType == null) return new ResponseDTO(CodeEnum.C1091);
        String content = null;

        String key = buildCacheKey(captchaType);
        content = cacheUtil.getCacheByFromRedis(key, String.class);
        if(StringUtils.isEmpty(content)){
            SmsTemplate smsTemplate = smsTemplateMapper.findByType(captchaType);
            if(smsTemplate == null) return new ResponseDTO(CodeEnum.C1091);

            content = smsTemplate.getContent();

            try{
                cacheUtil.putSession2Redis(key, content);
                logger.debug("put sms template to redis, key is {}, value is {}", key, content);
            }catch (Exception e){
                logger.error("put sms template to redis error, error is {}, key is {}, value is {}", e.getMessage(), key, content);
            }
        }

        return new ResponseDTO(CodeEnum.C1000, null, content);
    }

    /***************** begin private method **********************
    /**
     * 获取redis中key值
     */
    private String buildCacheKey(Byte captchaType){
        if(captchaType == null) return null;

        return String.format(CacheConstant.SMS_TEMPLATE_KEY, captchaType);
    }

    public void updateObj(SmsTemplate smsTemplate){
        SmsTemplate dbObj = get(smsTemplate.getId());
        dbObj.setType(smsTemplate.getType());
        dbObj.setContent(smsTemplate.getContent());
        dbObj.setRemark(smsTemplate.getRemark());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }

}
