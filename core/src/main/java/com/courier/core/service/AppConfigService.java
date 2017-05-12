package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.AppConfigMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.AppConfig;
import com.courier.sdk.constant.CodeEnum;
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
public class AppConfigService extends BaseManager<AppConfig> {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigService.class);

    @Autowired
    private AppConfigMapper appConfigMapper;

    @Override
    public AppConfig getEntity() {
        return new AppConfig();
    }

    @Override
    public BaseMapper<AppConfig> getBaseMapper() {
        return appConfigMapper;
    }


    /**
     * 检查配置文件是否需要更新， t2为null : 不需要，否则需要更新
     */
    public ResponseDTO compareBykey(String appConfigKey){
        if(appConfigKey == null) return new ResponseDTO(CodeEnum.C1036);
        List<AppConfig> all = findAll(null, null);

        if(all == null || all.size() <= 0)
            return new ResponseDTO(CodeEnum.C1000);

        AppConfig appConfig = all.get(0);
        if(!appConfig.getAppConfigKey().equals(appConfigKey))
            return new ResponseDTO(CodeEnum.C1000, null, appConfig);

        return new ResponseDTO(CodeEnum.C1000);
    }

    public void updateObj(AppConfig appConfig) {
        AppConfig dbObj = get(appConfig.getId());
        dbObj.setAppConfigKey(appConfig.getAppConfigKey());
        dbObj.setGpsUploadInterval(appConfig.getGpsUploadInterval());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }
}
