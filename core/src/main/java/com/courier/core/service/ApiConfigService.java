package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.dao.ApiConfigMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.ApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class ApiConfigService extends BaseManager<ApiConfig> {
    private static final Logger logger = LoggerFactory.getLogger(ApiConfigService.class);

    @Autowired private ApiConfigMapper apiConfigMapper;
    @Override public ApiConfig getEntity() { return new ApiConfig(); }
    @Override public BaseMapper<ApiConfig> getBaseMapper() { return apiConfigMapper; }
    @Autowired
    CacheUtil cacheUtil;



    /**
     * 检查是否有访问相应api权限
     */
    public boolean checkBy3Keys(Byte source, Byte permission, String token){
        if(source == null || permission == null || StringUtils.isEmpty(token))
            return false;

        Set<String> configs = cacheUtil.getCacheByFromRedis(CacheConstant.API_CONFIG_ALL_DATA, Set.class);
        // todo 缓存中有直接返回,没有查db并更新redis
        if(configs != null && configs.contains(buildVal(source, permission, token))) return true;

        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        filters.add(new SearchFilter("token", SearchFilter.Operator.EQ, token));
        filters.add(new SearchFilter("source", SearchFilter.Operator.EQ, source));
        filters.add(new SearchFilter("permission", SearchFilter.Operator.EQ, permission));
        List<ApiConfig> list = findAll(filters, null);

        if(list != null && !list.isEmpty()) {
            initData();
            return true;
        }

        return false;
    }

    /**
     * 初始化内存
     */
    public void initData() {
        initData(true);
    }

    public void initData(boolean isCheck) {
        //  todo  如果内存已加载，不再加载（系统启动时）
        if(isCheck && cacheUtil.isExistByRedis(CacheConstant.API_CONFIG_ALL_DATA))
            return;

        Set<String> set = new HashSet<String>();

        List<ApiConfig> list = findAll(new ArrayList<SearchFilter>(), null);
        if(list != null && list.size() > 0){
            for (ApiConfig obj : list){
                if(obj != null)
                    set.add(buildVal(obj.getSource(), obj.getPermission(), obj.getToken()));
            }
        }

        try{
            cacheUtil.putData2RedisByTime(CacheConstant.API_CONFIG_ALL_DATA, Global.THREE_DAY_AGE, set);
        }catch (Exception e){
            logger.error("put api configs to redis, error is {}", e.getMessage());
        }

    }

    private String buildVal(Byte source, Byte permission, String token) {
        return String.format("%d_%d_%s", source, permission, token);
    }

    public void updateObj(ApiConfig apiConfig) {
        ApiConfig dbObj = get(apiConfig.getId());
        dbObj.setName(apiConfig.getName());
        dbObj.setDescription(apiConfig.getDescription());
        dbObj.setPermission(apiConfig.getPermission());
        dbObj.setSource(apiConfig.getSource());
        dbObj.setToken(apiConfig.getToken());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }

    public Map<String, String> getSourceNames() {
        Map<String, String> ret = new LinkedHashMap<>();
        List<String> names = apiConfigMapper.getSourceNames();
        ret.put("", "全部");
        for (String name : names) {
            ret.put(name, name);
        }
        return ret;
    }
}
