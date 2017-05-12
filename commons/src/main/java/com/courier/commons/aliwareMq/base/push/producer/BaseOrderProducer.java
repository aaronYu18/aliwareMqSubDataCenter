package com.courier.commons.aliwareMq.base.push.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.courier.commons.aliwareMq.base.AliwareMQConnectConfig;
import com.courier.commons.util.json.GsonUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by aaron on 2017/3/8.
 */
public class BaseOrderProducer extends OrderProducerBean {
    private static final Logger logger = LoggerFactory.getLogger(BaseOrderProducer.class);
    private String producerId;
    private AliwareMQConnectConfig config;
    public static final int EXCEPTION_CODE = 0;           // 系统异常code
    public static final int SUCCESS_CODE = 1;             // 成功code
    public static final int PARAM_ERROR_CODE = 2;         // 参数错误code

    public BaseOrderProducer() {
    }

    public void init() throws Exception {
        if(config == null || StringUtils.isEmpty(producerId))
            throw new Exception(" init order producer failed, mqConfig or producerId is empty");

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, config.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, config.getSecretKey());
        properties.setProperty(PropertyKeyConst.ONSAddr, config.getOnsAddr());
        properties.setProperty(PropertyKeyConst.ProducerId, producerId);

        super.setProperties(properties);
        super.start();
        logger.info("order producer started, producerId is {}", producerId);
    }

    public int sender(Message message, String shardingKey){
        if(null == message) return PARAM_ERROR_CODE;
        try{
            SendResult sendResult = super.send(message, shardingKey);
            return SUCCESS_CODE;
        }catch (Exception e){
            logger.error("send order message %s, topic is %s, tag is %s, key is %s, content is %s, exception is : ",
                    "failed", message.getTopic(), message.getTag(), message.getKey(), new String(message.getBody()), e.getMessage());
            return EXCEPTION_CODE;
        }
    }


    public int sender(String topic, String tag, String key, Object obj, String shardingKey){
        if(StringUtils.isEmpty(topic) || StringUtils.isEmpty(tag) || null == obj) return PARAM_ERROR_CODE;
        try{
            byte[] bytes = GsonUtil.toJson(obj).getBytes();
            Message message = new Message(topic, tag, bytes);
            SendResult sendResult = super.send(message, shardingKey);
            return SUCCESS_CODE;
        }catch (Exception e){
            logger.error("send order message %s, topic is %s, tag is %s, key is %s, content is %s, exception is : ",
                    "failed", topic, tag, key, obj.toString(), e.getMessage());
            return EXCEPTION_CODE;
        }
    }


    public int sender(String topic, String tag, Object obj, String shardingKey){
        return sender(topic, tag, null, obj, shardingKey);
    }


    /****************** begin private method *****************************/



    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public AliwareMQConnectConfig getConfig() {
        return config;
    }

    public void setConfig(AliwareMQConnectConfig config) {
        this.config = config;
    }
}
