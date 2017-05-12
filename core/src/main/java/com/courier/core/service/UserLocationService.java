package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.CommonUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.InitCacheData;
import com.courier.core.cache.UserBean;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.entity.Branch;
import com.courier.db.entity.User;
import com.courier.core.cache.UserLocation;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class UserLocationService {
    private static final Logger logger = LoggerFactory.getLogger(CollectOrderService.class);
    private static final Logger userLogGPS = LoggerFactory.getLogger("USER_GPS_LOG");

    @Autowired
    UserService userService;
    @Autowired
    CacheUtil cacheUtil;
    @Autowired
    BranchService branchService;
    @Autowired
    RegionService regionService;
    @Autowired
    CollectOrderService collectOrderService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 记录用户Gps位置
     */
    public ResponseDTO uploadGPS(String uuid, Double lat, Double lng) throws Exception {
        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return  new ResponseDTO(CodeEnum.C1000);
        UserBean userBean = (UserBean) responseDTO.getT2();

        User user = userBean.getUser();
        Long userId = user.getId();
        UploadGps uploadGps = new UploadGps(userId, lat, lng, userBean);
        threadPoolTaskExecutor.execute(uploadGps);
        return new ResponseDTO(CodeEnum.C1000);
    }
    /**
     * 记录用户Gps位置 -- 根据UID
     */
    public ResponseDTO uploadGPS(Long userId, Double lat, Double lng) throws Exception {
        ResponseDTO responseDTO = userService.getUserBeanByUid(userId);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return  new ResponseDTO(CodeEnum.C1000);
        UserBean userBean = (UserBean) responseDTO.getT2();

        UploadGps uploadGps = new UploadGps(userId, lat, lng, userBean);
        threadPoolTaskExecutor.execute(uploadGps);
        return new ResponseDTO(CodeEnum.C1000);
    }

    private class UploadGps implements Runnable {
        private Long userId;
        private Double lat;
        private Double lng;
        private UserBean userBean;

        public UploadGps(Long userId, Double lat, Double lng, UserBean userBean) {
            this.userId = userId;
            this.lat = lat;
            this.lng = lng;
            this.userBean = userBean;
        }

        @Override
        public void run() {
            try {
                UserLocation userLocation = new UserLocation(userId, lat, lng);
                putToRedis(userBean, userLocation);
                collectOrderService.pulsePush(userBean, lat, lng);

            } catch (Exception e) {
                logger.error(" upload gps is error :{}", e.getMessage());
            }
        }
    }

    /**
     * 对内接口， 根据网点代码 工号获取gps
     */
    public ResponseDTO getUserGps(String orgCode, String jobNo) {
        jobNo = CommonUtil.formatJobNo(jobNo);
        String userGpsKey = buildUserGpsKey(orgCode, jobNo);
        UserLocation userLocation = cacheUtil.getCacheByFromRedis(userGpsKey, UserLocation.class);

        if (userLocation == null) {
            logger.info("get user gps from redis error, key is {}", userGpsKey);
            return new ResponseDTO(CodeEnum.C2010);
        }

        return new ResponseDTO(CodeEnum.C1000, null, userLocation);
    }

    public UserLocation getCurrentGPS(User user) {
        try {
            ResponseDTO userGps = getUserGps(user.getOrgCode(), user.getJobNo());
            CodeEnum codeEnum = userGps.getCodeEnum();
            if (CodeEnum.C1000 == codeEnum) {
                UserLocation userLocation = (UserLocation) userGps.getT2();
                if (userLocation != null) return userLocation;
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 根据省市code 获取区域内的快递员id集合List<String>
     */
    public ResponseDTO getUidsByRegion(String provinceCode, String cityCode) {
        if (StringUtils.isEmpty(provinceCode) || StringUtils.isEmpty(cityCode))
            return new ResponseDTO(CodeEnum.C1036);

        List<UserLocation> list = getByRegion(provinceCode, cityCode);

        List<Long> result = new ArrayList<Long>();
        if (list != null && list.size() > 0) {
            for (UserLocation location : list) {
                if (location != null && location.getUserId() != null)
                    result.add(location.getUserId());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, result, null);
    }

    /**
     * 组装 城市-用户gps 关系key
     */
    public static String buildCityUserGpsKey(String cityCode) {
        return String.format(CacheConstant.CITIES_USER_GPS_RELATIONSHIP_KEY, cityCode);
    }

    /***************** begin private method **********************/

    /**
     * 根据省市code 获取区域内的快递员 (登录用户)
     */
    public List<UserLocation> getByRegion(String provinceCode, String cityCode) {
        List<UserLocation> locations = new ArrayList<UserLocation>();

        Set<String> userGpsKeys = cacheUtil.getDataFromRedisSet(buildCityUserGpsKey(cityCode), String.class);
        //  todo  先根据市查； 如果没有再根据省查下边的所有市； 最终得到用户位置key值的集合
        if (userGpsKeys == null || userGpsKeys.size() <= 0) {
            Set<String> cityCodes = cacheUtil.getDataFromRedisSet(regionService.buildProvinceCitiesKey(provinceCode), String.class);

            if (cityCodes != null && cityCodes.size() > 0) {
                for (String code : cityCodes) {
                    Set<String> temp = cacheUtil.getDataFromRedisSet(buildCityUserGpsKey(code), String.class);
                    userGpsKeys.addAll(temp);
                }
            }
        }

        //  todo  遍历key值，取得所有的gps信息
        if (userGpsKeys != null && userGpsKeys.size() > 0) {
            for (String userGpsKey : userGpsKeys) {
                UserLocation userLocation = cacheUtil.getCacheByFromRedis(userGpsKey, UserLocation.class);
                locations.add(userLocation);
            }
        }

        return locations;
    }

    /**
     * 存储session
     */
    private void putToRedis(UserBean userBean, UserLocation userLocation) {
        User user = userBean.getUser();
        String orgCode = user.getOrgCode();
        Branch branch = userBean.getBranch();
        //  todo 如果用户branch信息为空，不存储gps信息
        if (branch == null)  return;
        /*if (branch == null) {
            ResponseDTO branchR = branchService.getByOrgCode(orgCode);
            if (branchR.getCodeEnum() != CodeEnum.C1000) {
                logger.error("get branch by orgCode failed, orgCode is {}", orgCode);
            } else {
                branch = (Branch) branchR.getT2();
            }
        }*/
        String jobNo = user.getJobNo();

        try {
            //  放入最新 gps 位置
            String userGpsKey = buildUserGpsKey(orgCode, jobNo);
            cacheUtil.put2RedisCache(userGpsKey, userLocation);
            //  放入一个用户的历史gps
            userLogGPS.info("UserGPS:({},{}), orgCode:{}, jobNo:{}, deviceType:{}, deviceNo:{}, userId:{}",
                    userLocation.getLat(), userLocation.getLng(), orgCode, jobNo,
                    userBean.getUserLoginRecord().getDeviceType(), userBean.getUserLoginRecord().getDeviceNo(), user.getId());

            String province = branch.getProvinceCode();
            String city = branch.getCityCode();

            //  todo 此处将值为String, 将String整个放入set<String>中 需验证是否是正确用法（王旭东）
            cacheUtil.putData2RedisSet(buildCityUserGpsKey(city), userGpsKey);
            cacheUtil.putData2RedisSet(regionService.buildProvinceCitiesKey(province), city);
        } catch (Exception e) {
            logger.error("put gps to redis, userLocation is {}, error is {}", userLocation.toJson(), e.getMessage());
        }
    }

    /**
     * 组装 redis gps key
     */
    private String buildUserGpsKey(String orgCode, String jobNo) {
        return String.format(CacheConstant.USER_GPS_KEY, orgCode, jobNo);
    }

    /**
     * 组装 redis gps 组 key
     */
    private String buildUserGpsStackKey(String orgCode, String jobNo) {
        return String.format(CacheConstant.USER_GPS_STACK_KEY, orgCode, jobNo);
    }

}
