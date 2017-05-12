package com.courier.subDataCenter.listenter;

import com.google.common.collect.Sets;

import com.courier.commons.mq.client.base.MQReceiveClient;
import com.courier.commons.util.io.CloserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by admin on 2016/6/28.
 */
@Component
public class DataCheck {
    private static final Logger logger = LoggerFactory.getLogger(DataCheck.class);

    private final String mqPauseStatusKey = "activemq.pause.receive";

    @Value("${activemq.pause.receive}")
    private boolean bPauseReceive;

    private Set<MQReceiveClient> sets = Sets.newHashSet();

    public void loadProp2Redis(File propFile){
        InputStream is = null;
        Properties properties = null;
        try{
            if(!propFile.exists()) {
                logger.info("config file is not exist...");
                return;
            }
            is = new FileInputStream(propFile);
            properties = new Properties();
            properties.load(is);
            addProperties(properties);
        } catch (Exception e){
            logger.error("read config error, cause:{}", e.getMessage());
        } finally {
            CloserUtil.closeIO(is);
        }
    }

    private void addProperties(Properties properties){
        this.bPauseReceive = Boolean.parseBoolean(properties.getProperty(mqPauseStatusKey));
        if(this.bPauseReceive){
            logger.info("Pause receive MQPacket");
        } else {
            logger.info("Resume receive MQPacket");
        }
        for(MQReceiveClient client : sets){
            client.setbPauseReceive(this.bPauseReceive);
        }
    }





    public void addMQReceiveClient(MQReceiveClient client){
        if(null == client)
            return;
        logger.info("Register MQReceiveClient, name:{}", client.getClass().getSimpleName());
        sets.add(client);
    }

}
