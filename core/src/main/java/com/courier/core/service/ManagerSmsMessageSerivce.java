package com.courier.core.service;

import com.courier.db.dao.ManagerSmsMessageMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.ManagerSmsMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vincent on 16/4/21.
 */
@Service
@Transactional
public class ManagerSmsMessageSerivce extends BaseManager<ManagerSmsMessage> {
    private static final Logger logger = LoggerFactory.getLogger(ManagerSmsMessageSerivce.class);

    @Autowired
    private ManagerSmsMessageMapper managerSmsMessageMapper;


    public ManagerSmsMessage getEntity() {
        return new ManagerSmsMessage();
    }

    @Override
    public BaseMapper<ManagerSmsMessage> getBaseMapper() {
        return managerSmsMessageMapper;
    }


}
