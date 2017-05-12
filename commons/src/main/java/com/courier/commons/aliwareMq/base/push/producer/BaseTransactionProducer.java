package com.courier.commons.aliwareMq.base.push.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.courier.commons.aliwareMq.base.AliwareMQConnectConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by aaron on 2017/3/8.
 */
public class BaseTransactionProducer extends TransactionProducerBean{
    private static final Logger logger = LoggerFactory.getLogger(BaseTransactionProducer.class);
    private String producerId;
    private AliwareMQConnectConfig config;
    private LocalTransactionChecker checker;

    public static final int EXCEPTION_CODE = 0;           // 系统异常code
    public static final int SUCCESS_CODE = 1;             // 成功code
    public static final int PARAM_ERROR_CODE = 2;         // 参数错误code

    public BaseTransactionProducer() {
    }

    public void init() throws Exception {
        if(config == null || StringUtils.isEmpty(producerId) || checker == null)
            throw new Exception(" init transaction producer failed, mqConfig or producerId is empty");

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, config.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, config.getSecretKey());
        properties.setProperty(PropertyKeyConst.ONSAddr, config.getOnsAddr());
        properties.setProperty(PropertyKeyConst.ProducerId, producerId);

        super.setProperties(properties);
        super.setLocalTransactionChecker(checker);
        super.start();

        logger.info("base producer started, producerId is {}", producerId);
    }

    public int sender(Message message, LocalTransactionExecuter executer, Object arg){
        if(null == message) return PARAM_ERROR_CODE;
        try{
            SendResult sendResult = super.send(message, executer, arg);
            return SUCCESS_CODE;
        }catch (Exception e){
            logger.error("send transaction message %s, topic is %s, tag is %s, key is %s, content is %s, exception is : ",
                    "failed", message.getTopic(), message.getTag(), message.getKey(), new String(message.getBody()), e.getMessage());
            return EXCEPTION_CODE;
        }
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

    public LocalTransactionChecker getChecker() {
        return checker;
    }

    public void setChecker(LocalTransactionChecker checker) {
        this.checker = checker;
    }
}
