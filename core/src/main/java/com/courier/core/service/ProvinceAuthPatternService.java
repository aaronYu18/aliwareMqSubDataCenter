package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.dao.ProvinceAuthPatternMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.ProvinceAuthPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bin on 2015/11/11.
 */
@Service
@Transactional
public class ProvinceAuthPatternService  extends BaseManager<ProvinceAuthPattern> {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceAuthPatternService.class);

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ProvinceAuthPatternMapper provinceAuthPatternMapper;


    @Override
    public ProvinceAuthPattern getEntity() {
        return new ProvinceAuthPattern();
    }

    @Override
    public BaseMapper<ProvinceAuthPattern> getBaseMapper() {
        return provinceAuthPatternMapper;
    }


    // todo 初始化缓存
    public void initCache(boolean forceRefresh) {
        // todo 如果为非强制刷新，且在redis已存在，直接退出
        if(!forceRefresh && checkAllIsExist())
            return;

        Map<String, Object> map = new HashMap();

        List<ProvinceAuthPattern> all = provinceAuthPatternMapper.findAll(new ProvinceAuthPattern(), null, null);
        if(!CollectionUtils.isEmpty(all)){
            for (ProvinceAuthPattern pattern : all){
                map.put(pattern.getCode(), pattern.getCollectPattern().toString());
            }

            //  todo  存储
            try {
                cacheUtil.putData2RedisMap(CacheConstant.ALL_PROVINCE_AUTH_PATTERN_KEY, map);
            } catch (Exception e) {
                logger.error("put all province auth pattern to redis failed, error is {}", e.getMessage());
            }
        }
    }


    /***************** begin private method **********************/

    private boolean checkAllIsExist() {
        return cacheUtil.isExistByRedis(CacheConstant.ALL_PROVINCE_AUTH_PATTERN_KEY) && cacheUtil.getMapLeng(CacheConstant.ALL_PROVINCE_AUTH_PATTERN_KEY) > 0;
    }
}
