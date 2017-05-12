package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.AppVersionMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.AppVersion;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class AppVersionService extends BaseManager<AppVersion> {
    private static final Logger logger = LoggerFactory.getLogger(AppVersionService.class);
    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion getEntity() {
        return new AppVersion();
    }

    @Override
    public BaseMapper<AppVersion> getBaseMapper() {
        return appVersionMapper;
    }

    /**
     * 根据用户类型获取版本对象 返回空不需要更新 ,需要更新返回对象
     *
     * @param type
     * @return
     */
    public ResponseDTO getAppVersion(String type, String version) throws Exception {
        AppVersion appVersion = appVersionMapper.getAppVersion(type);
        if (appVersion == null) return new ResponseDTO(CodeEnum.C1000);

        String code = appVersion.getCode();
        Double db = Double.valueOf(code);
        Double input = Double.valueOf(version);
        if (db <= input)
            return new ResponseDTO(CodeEnum.C1000);

        return new ResponseDTO(CodeEnum.C1000, null, appVersion);
    }


    public String getUrlByType(Byte type) {
        if(type == null) return null;
        return appVersionMapper.getByType(type);
    }

    public void updateObj(AppVersion appVersion){
        AppVersion dbObj = get(appVersion.getId());
        dbObj.setCode(appVersion.getCode());
        dbObj.setDescription(appVersion.getDescription());
        dbObj.setDownloadUrl(appVersion.getDownloadUrl());
        dbObj.setForceUpdate(appVersion.getForceUpdate());
        dbObj.setName(appVersion.getName());
        dbObj.setType(appVersion.getType());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }
}
