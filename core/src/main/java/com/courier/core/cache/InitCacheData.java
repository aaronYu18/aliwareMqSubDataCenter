package com.courier.core.cache;

import com.courier.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化缓存数据
 * Created by admin on 2015/10/31.
 */
public class InitCacheData {

    public static final Logger logger = LoggerFactory.getLogger(InitCacheData.class);

    RegionService regionService;
    ApiConfigService apiConfigService;
    RecoverAcceptNoUserService recoverAcceptNoUserService;
    SourceClientIdRelationService sourceClientIdRelationService;
    ProvinceAuthPatternService provinceAuthPatternService;
    UserService userService;



    /**
     * 初始化执行
     */
    public void init() {
        logger.info("begin to init cache data .....");
        //  初始化省市级联关系
        regionService.initRegionCache(false);
        //  初始化省市级联关系(金刚无法对应数据)
        regionService.initStaleRegionCache(false);
        //  防止数据丢失
        recoverAcceptNoUserService.recoverAcceptNoUser();
        //  加载api健全数据
        apiConfigService.initData();

        // 渠道ID与clientId 关联
        sourceClientIdRelationService.initAllRelationsToRedis();

         // 加载省市对应验证模式（取件时实名验证）
        provinceAuthPatternService.initCache(false);
        // 初始化工号对应userId
        userService.initJobNo2UserIdCache(false);
    }

    public RegionService getRegionService() {
        return regionService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public RecoverAcceptNoUserService getRecoverAcceptNoUserService() {
        return recoverAcceptNoUserService;
    }

    public void setRecoverAcceptNoUserService(RecoverAcceptNoUserService recoverAcceptNoUserService) {
        this.recoverAcceptNoUserService = recoverAcceptNoUserService;
    }

    public ApiConfigService getApiConfigService() {
        return apiConfigService;
    }

    public void setApiConfigService(ApiConfigService apiConfigService) {
        this.apiConfigService = apiConfigService;
    }

    public SourceClientIdRelationService getSourceClientIdRelationService() {
        return sourceClientIdRelationService;
    }

    public void setSourceClientIdRelationService(SourceClientIdRelationService sourceClientIdRelationService) {
        this.sourceClientIdRelationService = sourceClientIdRelationService;
    }

    public ProvinceAuthPatternService getProvinceAuthPatternService() {
        return provinceAuthPatternService;
    }

    public void setProvinceAuthPatternService(ProvinceAuthPatternService provinceAuthPatternService) {
        this.provinceAuthPatternService = provinceAuthPatternService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
