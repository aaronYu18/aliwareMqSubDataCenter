package com.courier.core.service;

import com.courier.db.dao.AliCallAndMsgMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.AliCallAndMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ryan on 15/12/14.
 */
@Service
@Transactional
public class AliCallAndMsgService extends BaseManager<AliCallAndMsg> {
    private static final Logger logger = LoggerFactory.getLogger(AliCallAndMsgService.class);

    @Autowired
    private AliCallAndMsgMapper aliCallAndMsgMapper;

    @Override
    public AliCallAndMsg getEntity() {
        return new AliCallAndMsg();
    }

    @Override
    public BaseMapper<AliCallAndMsg> getBaseMapper() {
        return aliCallAndMsgMapper;
    }

    public void batchInsertAliList(List<AliCallAndMsg> lst){
        aliCallAndMsgMapper.batchInsertAliCallAndMsg(lst);
    }
}
