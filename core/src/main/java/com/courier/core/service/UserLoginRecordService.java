package com.courier.core.service;

import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.InitCacheData;
import com.courier.db.dao.UserLoginRecordMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class UserLoginRecordService extends BaseManager<UserLoginRecord> {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRecordService.class);
    @Autowired
    private UserLoginRecordMapper userLoginRecordMapper;

    @Override
    public UserLoginRecord getEntity() {
        return new UserLoginRecord();
    }

    @Override
    public BaseMapper<UserLoginRecord> getBaseMapper() {
        return userLoginRecordMapper;
    }

    @Autowired
    CacheUtil cacheUtil;

    @Autowired
    private InitCacheData initCacheData;

    @Autowired
    UserService userService;

    /**
     * 获取用户最新登录记录
     */
    public UserLoginRecord findNewestByCourierId(Long courierId){
        return userLoginRecordMapper.findNewestByCourierId(courierId);
    }

    /**
     * 根据状态查找用户登录记录
     */
    public List<UserLoginRecord> findByCourierAndStatus(Long courierId, Boolean status){
        return userLoginRecordMapper.findByCourierAndStatus(courierId, status);
    }

    /**
     * 将用户登录状态设置为无效
     */
    public void setInvaildByUserId(Long userId) {
        List<UserLoginRecord> records = userLoginRecordMapper.findByCourierAndStatus(userId, true);
        if(records != null && records.size() > 0){
            for (UserLoginRecord record : records){
                //  todo  将redis系统中的uuid 设置为无效
                cacheUtil.invalidByRedis(userService.buildSessionKey(record.getUuid()));

                //  todo  将db中的登录记录 设置为无效
                record.setStatus(false);
                record.setUpdateTime(new Date());
                userLoginRecordMapper.update(record);
            }
        }
    }

    public Long findUserIdByUuid(String uuid) {
        return userLoginRecordMapper.findUserIdByUuid(uuid);
    }
    public UserLoginRecord findByUuid(String uuid) {
        return userLoginRecordMapper.findByUuid(uuid);
    }


    public UserLoginRecord findValidByUserId(Long userId) {
        return userLoginRecordMapper.findValidByUserId(userId);
    }

    public UserLoginRecord findValidRecordByInvalidUuid(String uuid) {
        return userLoginRecordMapper.findValidRecordByInvalidUuid(uuid);
    }

    public int[] backup(Date date) {
        int backup = userLoginRecordMapper.backup(date);
        int delete = userLoginRecordMapper.deleteBackup(date);
        return new int[]{backup, delete};
    }

    public int[] history(Date date) {
        int backup = userLoginRecordMapper.history(date);
        int delete = userLoginRecordMapper.deleteHistory(date);
        return new int[]{backup, delete};
    }


    /************** private method *******************/



}
