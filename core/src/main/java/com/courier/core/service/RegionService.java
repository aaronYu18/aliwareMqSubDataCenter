package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.RegionMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.Region;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class RegionService extends BaseManager<Region> {
    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    CacheUtil cacheUtil;

    @Override
    public Region getEntity() {
        return new Region();
    }

    @Override
    public BaseMapper<Region> getBaseMapper() {
        return regionMapper;
    }

    private Integer initRegionsPageSize = 500; //初始化省市区到redis 每次查询的数量
    /**
     * 根据父code, 查询类型 获取子一级数据
     */
    public ResponseDTO findByParentCode(CommonEnum.RegionEnum regionEnum, String parentCode) {
        String codeKey = null;
        if (regionEnum == CommonEnum.RegionEnum.PROVINCE) {
            codeKey = "%0000";
        } else {
            if (StringUtils.isEmpty(parentCode)) return new ResponseDTO(CodeEnum.C1036);
            if (regionEnum == CommonEnum.RegionEnum.CITY) {
                codeKey = parentCode.substring(0, 2) + "%00";
            } else {
                codeKey = parentCode.substring(0, 4) + "%";
            }
        }

        List<Region> regions = regionMapper.findByParentCode(regionEnum.ordinal(), codeKey, parentCode);

        return new ResponseDTO(CodeEnum.C1000, regions, null);
    }

    /**
     * 通过省获取 cities
     *
     * @param provinceCode
     * @return
     */
    public ResponseDTO getCitiesByProvince(String provinceCode) {
        if (StringUtils.isEmpty(provinceCode)) return new ResponseDTO(CodeEnum.C1034);

        Set<String> sets = cacheUtil.getDataFromRedisSet(buildProvinceCitiesKey(provinceCode), String.class);
        if (sets == null || sets.size() == 0) return new ResponseDTO(CodeEnum.C1036);

        return new ResponseDTO(CodeEnum.C1000,Arrays.asList(sets.toArray(new String[]{})), null);
    }

    /**
     * 初始化缓存数据
     */
    public void initRegionCache(boolean forceRefresh) {
        // 初始化省市关系
        initProvinceCityRelationsCache(forceRefresh);
        // 初始化全部省市
        initRegionMapCache(forceRefresh);
    }



    // todo cache初始化无效code数据
    public void initStaleRegionCache(boolean forceRefresh){
        // todo 如果为非强制刷新，且在redis已存在，直接退出
        if(!forceRefresh && checkStaleRegionExist())
            return;

        int totalNum = regionMapper.countStaleRegion();
        if(totalNum == 0) return;

        Map<String, Object> allRegions = new HashMap();

        int pageCount = (totalNum + initRegionsPageSize - 1) / initRegionsPageSize;
        for (int i = 1; i <= pageCount; i++) {
            List<Region> regions = regionMapper.findStaleRegionByPage((i-1) * initRegionsPageSize, initRegionsPageSize);
            if (!CollectionUtils.isEmpty(regions)) {
                for (Region region : regions)
                    allRegions.put(region.getCode(), region.getName());
            }
        }

        //  todo  存储
        try {
            cacheUtil.putData2RedisMap(CacheConstant.ALL_STALE_REGIONS_KEY, allRegions);
        } catch (Exception e) {
            logger.error("put all stale regions to redis failed, error is {}", e.getMessage());
        }
    }


    /**
     * 暂时直接数据库，以后Redis
     *
     * @return
     */
    public String getByCode(String code) {
        if (StringUtils.isEmpty(code)) return "";
        String name = null;
        try {
            name = (String) cacheUtil.getCacheByFromRedis(buildRegionCode(code));
            if (StringUtils.isEmpty(name)) {
                Region region = regionMapper.getByCode(code);
                name = region.getName();
                cacheUtil.putResouce2Redis(buildRegionCode(name), code);
            }
        } catch (Exception e) {
            logger.error("message:{},code:{}", e.getMessage(), code);
        }
        return name;
    }

    /**
     * 暂时直接数据库，以后Redis
     *
     * @return
     */
    public String getByName(String name) {
        if (StringUtils.isEmpty(name)) return "";
        String code = null;
        try {
            code = (String) cacheUtil.getCacheByFromRedis(buildRegionName(name));
            if (StringUtils.isEmpty(code)) {
                Region region = regionMapper.getByName(name);
                code = region.getCode();
                cacheUtil.putResouce2Redis(buildRegionName(name), code);
            }
        } catch (Exception e) {
            logger.error("message:{},name:{}", e.getMessage(), name);
        }
        return code;
    }


    /**
     * 组装 省-城市 关系key
     */
    public static String buildProvinceCitiesKey(String code) {
        return String.format(CacheConstant.PROVINCES_CITIES_RELATIONSHIP_KEY, code);
    }

    public String buildRegionCode(String code) {
        return String.format(CacheConstant.REGION_CODE_NAME, code);
    }

    public String buildRegionName(String name) {
        return String.format(CacheConstant.REGION_NAME_CODE, name);
    }

    /***************** begin private method **********************/

    private void initProvinceCityRelationsCache(boolean forceRefresh) {
        // 获取所有省
        ResponseDTO provinceResp = findByParentCode(CommonEnum.RegionEnum.PROVINCE, null);
        if (provinceResp.getCodeEnum() != CodeEnum.C1000) {
            logger.error("init cache region data failed, error is {}", provinceResp.getCodeEnum().getDesc());
            return;
        }

        List<Region> provinces = (List<Region>) provinceResp.getList();

        if (provinces != null && provinces.size() > 0) {
            for (Region province : provinces) {
                String code = province.getCode();
                String provinceKey = buildProvinceCitiesKey(code);

                if (!forceRefresh && cacheUtil.isExistByRedis(provinceKey)) continue;

                ResponseDTO cityResp = findByParentCode(CommonEnum.RegionEnum.CITY, code);
                if (cityResp.getCodeEnum() != CodeEnum.C1000) {
                    logger.error("init cache region data failed for city, code is {}, error is {}", code, provinceResp.getCodeEnum().getDesc());
                    continue;
                }

                List<Region> cities = (List<Region>) cityResp.getList();
                Set<String> cityCodes = buildCityCodes(cities);
                // 排除其他地区 港澳台 下面无地级市
                if (cityCodes == null) continue;
                try {
                    //  todo 此处将值为set<String>, 将set<String>整个放入set<String>中 需验证是否是正确用法（王旭东）
                    cacheUtil.putData2RedisSet(provinceKey, cityCodes.toArray(new String[]{}));
                } catch (Exception e) {
                    logger.error("put province-cities to redis failed, province code is {}, error is {}", code, e.getMessage());
                }
            }
        }
    }

    private void initRegionMapCache(boolean forceRefresh) {
        // todo 如果已经加载过，不在加载到redis中
        if(!forceRefresh && checkExist()) return;

        //将所有省市区放入redis
        int count = count(null);
        if (count == 0) return;

        //  todo  加载有效region数据
        Map<String, Object> allRegions = new HashMap();
        int pageCount = (count + initRegionsPageSize - 1) / initRegionsPageSize;
        for (int i = 1; i <= pageCount; i++) {
            List<Region> regions = findBy(null, null, i, initRegionsPageSize);
            if (!CollectionUtils.isEmpty(regions)) {
                for (Region region : regions)
                    allRegions.put(region.getCode(), region.getName());
            }
        }

        //  todo  存储
        try {
            cacheUtil.putData2RedisMap(CacheConstant.ALL_REGIONS_KEY, allRegions);
        } catch (Exception e) {
            logger.error("put all-regions to redis failed, error is {}", e.getMessage());
        }
    }
    /**
     * 生成city code set
     */
    private Set<String> buildCityCodes(List<Region> cities) {
        if (cities == null || cities.size() <= 0) return null;

        Set<String> result = new HashSet<String>();
        for (Region city : cities) {
            result.add(city.getCode());
        }
        return result;
    }

    private boolean checkExist() {
        return cacheUtil.isExistByRedis(CacheConstant.ALL_REGIONS_KEY) && cacheUtil.getMapLeng(CacheConstant.ALL_REGIONS_KEY) > 0;
    }

    private boolean checkStaleRegionExist() {
        return cacheUtil.isExistByRedis(CacheConstant.ALL_STALE_REGIONS_KEY) && cacheUtil.getMapLeng(CacheConstant.ALL_STALE_REGIONS_KEY) > 0;
    }
}
