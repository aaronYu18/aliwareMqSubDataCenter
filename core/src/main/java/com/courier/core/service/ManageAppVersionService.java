package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.ManageAppVersionMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.ManageAppVersion;
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
public class ManageAppVersionService extends BaseManager<ManageAppVersion> {
    private static final Logger logger = LoggerFactory.getLogger(ManageAppVersionService.class);
    @Autowired
    private ManageAppVersionMapper manageAppVersionMapper;

    @Override
    public ManageAppVersion getEntity() {
        return new ManageAppVersion();
    }

    @Override
    public BaseMapper<ManageAppVersion> getBaseMapper() {
        return manageAppVersionMapper;
    }

    /**
     * 根据用户类型获取版本对象 返回空不需要更新 ,需要更新返回对象
     *
     * @param type
     * @return
     */
    public ResponseDTO getAppVersion(String type, String version) throws Exception {
        ManageAppVersion manageAppVersion = manageAppVersionMapper.getAppVersion(type);
        if (manageAppVersion == null) return new ResponseDTO(CodeEnum.C1000);

        String code = manageAppVersion.getCode();
        Double db = Double.valueOf(code);
        Double input = Double.valueOf(version);
        if (db <= input)
            return new ResponseDTO(CodeEnum.C1000);

        return new ResponseDTO(CodeEnum.C1000, null, manageAppVersion);
    }


    public String getUrlByType(Byte type) {
        if(type == null) return null;
        return manageAppVersionMapper.getByType(type);
    }

    public void updateObj(ManageAppVersion manageAppVersion){
        ManageAppVersion dbObj = get(manageAppVersion.getId());
        dbObj.setCode(manageAppVersion.getCode());
        dbObj.setDescription(manageAppVersion.getDescription());
        dbObj.setDownloadUrl(manageAppVersion.getDownloadUrl());
        dbObj.setForceUpdate(manageAppVersion.getForceUpdate());
        dbObj.setName(manageAppVersion.getName());
        dbObj.setType(manageAppVersion.getType());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }
}
