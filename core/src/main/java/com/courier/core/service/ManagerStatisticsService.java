package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VManagerCurrentData;
import com.courier.commons.vModel.VManagerSubCurrentData;
import com.courier.core.vModel.VYtoPage;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.Manager;
import com.courier.db.entity.OrderOperation;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
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
public class ManagerStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerStatisticsService.class);
    @Autowired CacheUtil cacheUtil;
    @Autowired BranchService branchService;
    @Autowired ManagerService managerService;
    @Autowired ManagerDataService managerDataService;
    @Autowired OrderOperationService orderOperationService;
    @Autowired DeliveryOrderService deliveryOrderService;
    @Autowired UserService userService;




    /**
     *  行者管理 - 首页统计数据
     */
    public ResponseDTO homeData(String uuid) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        Date endT = DateUtil.getEndT(new Date());
        Date beginT = DateUtil.getBeginT(new Date());
        String orgCode = manager.getOrgCode();

        VManagerCurrentData result = buildTotalData(orgCode, role, beginT, endT);
        if (result == null) return new ResponseDTO(CodeEnum.C2000);

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }

    /**
     * todo 今日概况
     *  todo  targetRole 再返回值中已给出，为VManagerSubCurrentData中的 role字段
     */
    public ResponseDTO todayData(String uuid, String targetOrgCode, Byte targetRole, int pageNo, int pageSize) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        String orgCode = manager.getOrgCode();
        Byte role = manager.getRole();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        if(StringUtils.isEmpty(targetOrgCode) || targetRole == null){
            targetOrgCode = orgCode;
            targetRole = role.byteValue();
        }
        //  todo 权限校验
        if(role > targetRole) return new ResponseDTO(CodeEnum.C4002);
        // 根据角色及网点代码 查找其所能管辖的网点
        List<String> orgCodes = getOrgCodes(role, orgCode);
        if(!orgCodes.contains(targetOrgCode)) return new ResponseDTO(CodeEnum.C4002);

        VManagerCurrentData result = null;
        Date endT = DateUtil.getEndT(new Date());
        Date beginT = DateUtil.getBeginT(new Date());

        String redisKey = String.format(CacheConstant.MANAGER_TODAY_DATA, targetOrgCode, targetRole, pageNo, pageSize);

        //  todo  先从redis中取
        Page<VManagerSubCurrentData> page = cacheUtil.getCacheShortTimeByFromRedis(redisKey, Page.class);
        if(page == null){
            page = managerDataService.buildSubList(targetOrgCode, targetRole, pageNo, pageSize, beginT, endT);
            //  todo  将结果存入redis
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, page);
            }catch (Exception e){
                logger.error("put all courier manager today data pages to redis, redisKey is {}, error is {}", redisKey, e.getMessage());
            }
        }

        if(pageNo == 1){
            result = buildTotalData(targetOrgCode, targetRole, beginT, endT);
            if (result == null) return new ResponseDTO(CodeEnum.C2000);

            result.setPage(page);
            result.setOrgCode(targetOrgCode);
        }else {
            result = new VManagerCurrentData(page);
        }

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }
    /**
     * todo 今日概况 -- 查询
     */
    public ResponseDTO todayDataQuery(String uuid, String parentOrgCode, Byte targetRole, String key) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        String orgCode = manager.getOrgCode();
        Byte role = manager.getRole();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(parentOrgCode) || targetRole == null)
            return new ResponseDTO(CodeEnum.C2000);
        //  todo 权限校验
        if(role > targetRole) return new ResponseDTO(CodeEnum.C4002);

        List<String> keys = new ArrayList<>();
        if(targetRole.equals(Enumerate.ManagerRole.provinceCode.getCode().byteValue())) {                    //  todo 分公司
            keys = branchService.fetchCompanyByOrgCode(orgCode, CommonEnum.BranchType.BRANCH.getCode(), null, null);

        }else if(targetRole.equals(Enumerate.ManagerRole.companyCode.getCode().byteValue())){                //  todo 分部
            keys = branchService.findByBCodeType(parentOrgCode, null);

        }else if(targetRole.equals(Enumerate.ManagerRole.branchCode.getCode().byteValue())){                //  todo 快递员
            keys = userService.findJobNosByOrgCode(parentOrgCode);
        }
        if(!keys.contains(key)) return new ResponseDTO(CodeEnum.C1000);

        String redisKey = String.format(CacheConstant.MANAGER_TODAY_DATA_QUERY, key, targetRole);

        //  todo  先从redis中取
        VManagerSubCurrentData data = cacheUtil.getCacheShortTimeByFromRedis(redisKey, VManagerSubCurrentData.class);
        if(data == null){
            Date endT = DateUtil.getEndT(new Date());
            Date beginT = DateUtil.getBeginT(new Date());
            data = managerDataService.todayDataQuery(key, targetRole, beginT, endT);
            //  todo  将结果存入redis
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, data);
            }catch (Exception e){
                logger.error("put all courier manager today query data pages to redis, redisKey is {}, error is {}", redisKey, e.getMessage());
            }
        }

        if (data != null) data.setUserOrgCode(parentOrgCode);
        return new ResponseDTO(CodeEnum.C1000, null, data);
    }

    /**
     *  行者管理 - 历史已签详情
     */
    public ResponseDTO historyOrders(String uuid, Long userId, String mailNo, String beginTime, String endTime,
                                     Integer pageNo, Integer pageSize) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        String orgCode = manager.getOrgCode();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        if(userId == null || userId == 0) return new ResponseDTO(CodeEnum.C2000);

        List<Long> uIds = orderOperationService.getUIdsByOrgCodeAndRole(orgCode, role);

        if(!uIds.contains(userId)) return new ResponseDTO(CodeEnum.C4002);

        String  redisKey = String.format(CacheConstant.MANAGER_HISTORY_DATA_SIGN_DATA, userId, beginTime, endTime, mailNo,
                pageNo == null ? 0 : pageNo, pageSize == null ? 0 : pageSize);
        Page<DeliveryOrder> result = cacheUtil.getCacheByFromRedis(redisKey, Page.class);
        //  todo  查db，并放入redis
        if (result == null){
            result = deliveryOrderService.getHistorySignData(userId, mailNo, beginTime, endTime, pageNo, pageSize);
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, result);
            }catch (Exception e){
                logger.error("put sign details data to redis, uId is {}, key is {}, error is {}", manager.getId(), redisKey, e.getMessage());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, result.getResult(), result.getTotalCount());
    }

    /**
     *  行者管理 - 今日已签详情
     */
    public ResponseDTO signOrders(String uuid, Long userId, String mailNo, Integer pageNo, Integer pageSize) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        String orgCode = manager.getOrgCode();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        if(userId == null || userId == 0) return new ResponseDTO(CodeEnum.C2000);
        List<Long> uIds = orderOperationService.getUIdsByOrgCodeAndRole(orgCode, role);
        if(!uIds.contains(userId)) return new ResponseDTO(CodeEnum.C4002);

        String  redisKey = String.format(CacheConstant.MANAGER_TODAY_DATA_SIGN_DATA, userId, mailNo,
                pageNo == null ? 0 : pageNo, pageSize == null ? 0 : pageSize);
        Page<DeliveryOrder> result = cacheUtil.getCacheByFromRedis(redisKey, Page.class);
        //  todo  查db，并放入redis
        if (result == null){
            Date endT = DateUtil.getEndT(new Date());
            Date beginT = DateUtil.getBeginT(new Date());
            result = deliveryOrderService.getSignData(userId, mailNo, beginT, endT, pageNo, pageSize);
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, result);
            }catch (Exception e){
                logger.error("put sign details data to redis, uId is {}, key is {}, error is {}", manager.getId(), redisKey, e.getMessage());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, result.getResult(), result.getTotalCount());
    }
    /**
     *  行者管理 - 今日待派详情
     */
    public ResponseDTO sendingOrders(String uuid, Long userId, String mailNo, Integer pageNo, Integer pageSize) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();
        String orgCode = manager.getOrgCode();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        if(userId == null || userId == 0) return new ResponseDTO(CodeEnum.C2000);
        List<Long> uIds = orderOperationService.getUIdsByOrgCodeAndRole(orgCode, role);
        if(!uIds.contains(userId)) return new ResponseDTO(CodeEnum.C4002);

        String  redisKey = String.format(CacheConstant.MANAGER_TODAY_DATA_SENDING_DATA, userId, mailNo,
                pageNo == null ? 0 : pageNo, pageSize == null ? 0 : pageSize);
        Page<DeliveryOrder> result = cacheUtil.getCacheByFromRedis(redisKey, Page.class);
        //  todo  查db，并放入redis
        if (result == null){
            result = deliveryOrderService.getSendingData(userId, mailNo, pageNo, pageSize);
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, result);
            }catch (Exception e){
                logger.error("put sending details data to redis, uId is {}, key is {}, error is {}", manager.getId(), redisKey, e.getMessage());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, result.getResult(), result.getTotalCount());
    }
    /**
     *  行者管理 - 我的网点
     */
    public ResponseDTO myOrg(String uuid) throws Exception {
        ResponseDTO responseDTO = managerService.getManagerByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();
        Byte role = manager.getRole();

        // todo 判断用户是否有效
        if(!manager.getStatus().equals(Enumerate.ManagerStatus.VERIFY.getType()) || role == null)
            return new ResponseDTO(CodeEnum.C4001);

        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -1);
        }
        String redisKey = buildOrgPageKey(manager.getId(), calendar.getTime());
        VYtoPage result = cacheUtil.getCacheByFromRedis(redisKey, VYtoPage.class);
        //  todo  查db，并放入redis
        if (result == null){
            result = managerDataService.buildMyOrg(manager.getOrgCode(), role);
            try{
                cacheUtil.putData2RedisByTime(redisKey, Global.ONE_DAY_AGE, result);
            }catch (Exception e){
                logger.error("put org page data to redis, uId is {}, error is {}", manager.getId(), e.getMessage());
            }
        }
        if (result == null) return new ResponseDTO(CodeEnum.C2000);

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }
    /*******************************  begin private method ********************************/

    private String buildOrgPageKey(Long managerId, Date d) {
        String date = DateUtil.toShortDay(d);
        return String.format(CacheConstant.ORG_PAGE_DATA_KEY, managerId, date);
    }

    // todo 查找相应角色下的所有orgCode
    private List<String> getOrgCodes(Byte role, String orgCode) {
        List<String> orgCodes = new ArrayList<>();
        if(role.equals(Enumerate.ManagerRole.provinceCode.getCode().byteValue())) {                    //  todo 省区网管
            orgCodes = branchService.fetchCompanyByOrgCode(orgCode, null, null, null);

        }else if(role.equals(Enumerate.ManagerRole.companyCode.getCode().byteValue())){                //  todo 分公司
            orgCodes = branchService.findByBCodeType(orgCode, null);

        }else{               //  todo 分部
            orgCodes.add(orgCode);
        }
        return orgCodes;
    }


    private VManagerCurrentData buildTotalData(String orgCode, Byte role, Date beginT, Date endT) {
        //  todo  先从redis中取
        String redisKey = String.format(CacheConstant.MANAGER_TODAY_DATA_TOTAL, orgCode, role);
        VManagerCurrentData result = cacheUtil.getCacheShortTimeByFromRedis(redisKey, VManagerCurrentData.class);
        if(result != null)  return result;

        result = orderOperationService.countForManage(orgCode, role, beginT, endT);
        if (result != null){
            //  todo  将结果存入redis
            try{
                cacheUtil.putData2RedisByTime(redisKey, 60, result);
            }catch (Exception e){
                logger.error("put all courier manager today total data to redis, redisKey is {}, error is {}", redisKey, e.getMessage());
            }
        }

        return result;
    }
}
