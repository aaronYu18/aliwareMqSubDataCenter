package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.FaqMapper;
import com.courier.db.dao.FaqVersionMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.ApiConfig;
import com.courier.db.entity.FAQ;
import com.courier.db.entity.FaqVersion;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class FAQVersionService extends BaseManager<FaqVersion> {
    private static final Logger logger = LoggerFactory.getLogger(FAQVersionService.class);

    @Autowired
    private FaqVersionMapper faqVersionMapper;
    @Autowired
    CacheUtil cacheUtil;

    @Override
    public FaqVersion getEntity() {
        return new FaqVersion();
    }

    @Override
    public BaseMapper<FaqVersion> getBaseMapper() {
        return faqVersionMapper;
    }

    public ResponseDTO check(Integer versionNo) {
        if(versionNo == null && versionNo == 0) return new ResponseDTO(CodeEnum.C1036);
        FaqVersion faqVersion = find();

        if(faqVersion != null){
            Integer version = faqVersion.getVersion();
            if(version > versionNo) return new ResponseDTO(CodeEnum.C1000, null, faqVersion);
        }

        return new ResponseDTO(CodeEnum.C1000);
    }


    /******************************** begin private method  *********************************************/

    private FaqVersion find() {
        if(cacheUtil.isExistByRedis(CacheConstant.FAQ_VERSION))
            return cacheUtil.getCacheByFromRedis(CacheConstant.FAQ_VERSION, FaqVersion.class);

        List<FaqVersion> list = findAll(new ArrayList<>(), null);
        if(list != null && list.size() > 0){
            FaqVersion faqVersion = list.get(0);
            try{
                cacheUtil.putData2RedisByTime(CacheConstant.FAQ_VERSION, Global.THREE_DAY_AGE, faqVersion);
            }catch (Exception e){
                logger.error("put faq version info to redis, error is {}", e.getMessage());
            }

            return faqVersion;
        }

        return null;
    }
}
