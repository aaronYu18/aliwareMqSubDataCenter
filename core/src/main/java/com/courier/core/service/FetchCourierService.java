package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.DistanceUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.UserBean;
import com.courier.core.cache.UserLocation;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by ryan on 15/11/10.
 */
@Service
@Transactional
public class FetchCourierService {

    @Value("${fetch.curFetchDistance}")
    private Long curFetchDistance;
    @Value("${fetch.parentFetchDistance}")
    private Long parentDistance;

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    UserLocationService userLocationService;
    @Autowired
    RegionService regionService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserBean> fetchCourier(@NotNull String provinceCode,
                             @NotNull String cityCode,
                             @NotNull double lng,
                             @NotNull double lat) {
        List<UserBean> lst = Lists.newLinkedList();

        Set<String> cityCourier = getCityCourier(cityCode);

        if(cityCourier != null && !cityCourier.isEmpty()){
            //得到当前区域内的快递员（公里数范围内）
            addNotifyCourier(lst, cityCourier, true, lng, lat);
            //判断是否存在该区域的快递员，没有则向上一级寻找
            if(lst.isEmpty()){
                getParentProvinceCourier(lst, provinceCode, cityCode, lng, lat);
            }
        } else
            getParentProvinceCourier(lst, provinceCode, cityCode, lng, lat);

        return lst;
    }

    /**
     * 得到当前市级的快递员
     * @param cityCode
     * @return
     */
    private Set<String> getCityCourier(String cityCode){
        return cacheUtil.getDataFromRedisSet(userLocationService.buildCityUserGpsKey(cityCode), String.class);
    }

    /**
     * 得到父级省下的某位快递员
     * @param lst
     * @param provinceCode
     * @param cityCode
     * @param lng
     * @param lat
     */
    private void getParentProvinceCourier(List<UserBean> lst, String provinceCode, String cityCode, double lng, double lat){
        //得到其他省市下的快递员（公里数范围内）

        //得到该省下的所有市级单位
        Set<String> citySet = cacheUtil.getDataFromRedisSet(regionService.buildProvinceCitiesKey(provinceCode), String.class);
        for(String cCode : citySet){
            //排除当前市级区域内搜索
            if(cityCode.equals(cCode))
                continue;

            //获取该市下的符合的快递员
            Set<String> set = getCityCourier(cityCode);
            if(CollectionUtils.isEmpty(set))
                continue;

            addNotifyCourier(lst, set, false, lng, lat);

            if(!lst.isEmpty())
                return;
        }
    }

    private void addNotifyCourier(List<UserBean> lst, Set<String> setUser, boolean isBelongCurCity, double lng, double lat){
        long dis = parentDistance;
        if(isBelongCurCity)
            dis = curFetchDistance;

        for(String userGpsKey : setUser){
            UserLocation location = cacheUtil.getCacheByFromRedis(userGpsKey, UserLocation.class);
            //判断当前距离内的用户是否符合，符合添加到list中
            if(null == location)
                continue;

            double curDis = DistanceUtil.getDistance(lng, lat, location.getLng(), location.getLat());

            logger.debug("curDis:{}, lng1:{}, lat1:{}, lng2:{}, lat2:{}, isBelongCurCity:{}",
                    curDis, lng, lat, location.getLng(), location.getLat(), isBelongCurCity);

            if(dis > curDis){
                UserBean userBean = cacheUtil.getCacheByFromRedis(String.format(CacheConstant.COURIER_USER_KEY_PREFIX
                        , location.getUserId())
                        , UserBean.class);

                if(null == userBean)
                    continue;

                lst.add(userBean);

                if(!isBelongCurCity) {
                    logger.debug("out of current city, curDis:{}, lng1:{}, lat1:{}, " +
                            "lng2:{}, lat2:{}, isBelongCurCity:{}",
                            curDis, lng, lat, location.getLng(), location.getLat(), isBelongCurCity);
                    return;
                }
            }

        }
    }

}
