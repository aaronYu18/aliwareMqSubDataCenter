package com.courier.core.service;

import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.dao.ManagerLoginRecordMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.ManagerLoginRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by vincent on 16/4/21.
 */
@Service
@Transactional
public class ManagerLoginRecordService extends BaseManager<ManagerLoginRecord>{

    @Autowired
    private ManagerLoginRecordMapper managerLoginRecordMapper;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ManagerService managerService;

    @Override
    public ManagerLoginRecord getEntity() {
        return new ManagerLoginRecord();
    }

    @Override
    public BaseMapper<ManagerLoginRecord> getBaseMapper() {
        return managerLoginRecordMapper;
    }

    public List<ManagerLoginRecord> findByManagerAndStatus(Long managerId, Boolean status) {
        return managerLoginRecordMapper.findByManagerAndStatus(managerId, status);
    }

    /**
     * 将用户登录状态设置为无效
     */
    public void setInvaildByManagerId(Long userId) {
        List<ManagerLoginRecord> records = managerLoginRecordMapper.findByManagerAndStatus(userId, true);
        if(records != null && records.size() > 0){
            for (ManagerLoginRecord record : records){
                //  todo  将redis系统中的uuid 设置为无效
                cacheUtil.invalidByRedis(managerService.buildSessionKey(record.getUuid()));

                //  todo  将db中的登录记录 设置为无效
                record.setStatus(false);
                record.setUpdateTime(new Date());
                managerLoginRecordMapper.update(record);
            }
        }
    }

    public ManagerLoginRecord findValidRecordByInvalidUuid(String uuid) {
        return managerLoginRecordMapper.findValidRecordByInvalidUuid(uuid);
    }

    public ManagerLoginRecord findByUuid(String uuid) {
        return managerLoginRecordMapper.findByUuid(uuid);
    }
}
