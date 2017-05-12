package com.courier.core.service;

import com.courier.commons.constant.ParamKey;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.UserLocation;
import com.courier.core.convert.OrderOperationConvert;
import com.courier.core.vModel.VManagerCurrentData;
import com.courier.commons.vModel.VManagerSubCurrentData;
import com.courier.db.vModel.VOperationDayCount;
import com.courier.db.vModel.VOperationReport;
import com.courier.db.vModel.VYtoData;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VOperationData;
import com.courier.db.dao.OrderOperationMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.OrderOperation;
import com.courier.db.entity.Report;
import com.courier.sdk.constant.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by vincent on 15/11/4.
 */
@Service
@Transactional
public class OrderOperationService extends BaseManager<OrderOperation> {
    @Override
    public OrderOperation getEntity() {
        return new OrderOperation();
    }

    @Autowired
    private OrderOperationMapper orderOperationMapper;

    @Override
    public BaseMapper<OrderOperation> getBaseMapper() {
        return orderOperationMapper;
    }

    @Autowired
    private UserService userService;

    @Autowired
    UserLocationService userLocationService;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private RedisService redisService;


    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public ResponseDTO findByFilters(String uuid, Map<String, Object> filters, Integer pageNo, Integer pageSize, boolean isUpdateRedis) throws Exception {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Long userId = (Long) responseDTO.getT2();

        if (filters == null) filters = new HashMap<>();
        int total = 0;
        List<VOperationDayCount> vOperationDayCounts = null;

        filters.put("userId", userId);

        if (pageNo != null && pageSize != null) {
            if (pageNo < 1) pageNo = 1;
            filters.put(ParamKey.Page.NUM_KEY, (pageNo - 1) * pageSize);
            filters.put(ParamKey.Page.SIZE_KEY, pageSize);
            total = orderOperationMapper.countByFilters(filters);
            if (isUpdateRedis) redisService.refreshHomeFailed(userId, total);
        }
        List<OrderOperation> list = orderOperationMapper.findByFilters(filters);
        if (pageNo == 1) {
            vOperationDayCounts = orderOperationMapper.dayCountByFilters(filters);
        }
        VOperationData vOperationData = new VOperationData();
        vOperationData.setCount(total);
        vOperationData.setOrderOperations(list);
        vOperationData.setvOperationDayCounts(vOperationDayCounts);
        return new ResponseDTO(CodeEnum.C1000, null, vOperationData);
    }

    public ResponseDTO findByFiltersNoDayCounts(String uuid, Map<String, Object> filters, Integer pageNo, Integer pageSize, boolean isUpdateRedis) throws Exception {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Long userId = (Long) responseDTO.getT2();

        if (filters == null) filters = new HashMap<>();
        int total = 0;

        filters.put("userId", userId);

        if (pageNo != null && pageSize != null) {
            if (pageNo < 1) pageNo = 1;
            filters.put(ParamKey.Page.NUM_KEY, (pageNo - 1) * pageSize);
            filters.put(ParamKey.Page.SIZE_KEY, pageSize);
            total = orderOperationMapper.countByFilters(filters);
            if (isUpdateRedis) redisService.refreshHomeSigned(userId, total);
        }
        List<OrderOperation> list = orderOperationMapper.findByFilters(filters);
        list = OrderOperationConvert.buildListRegionName(cacheUtil, list);
        return new ResponseDTO(CodeEnum.C1000, list, total);
    }

    /**
     * 统计
     */
    public int countByFilters(Map<String, Object> filters) {
        return orderOperationMapper.countByFilters(filters);
    }
    /**
     * 统计
     */
    public List<OrderOperation> findByFilters(Map<String, Object> filters, Integer pageNo, Integer pageSize) {
        if(pageNo != null && pageSize != null){
            filters.put("pageNo", pageNo);
            filters.put("pageSize", pageSize);
        }
        return orderOperationMapper.findByFilters(filters);
    }

    /**
     * 统计时间内已签已取
     */
    public Integer[] countTodaySignAndCollect(Long userId, Date endT, Date beginT) throws Exception {
        Map<String, Object> filters = new HashMap<>();

        filters.put("userId", userId);
        filters.put("beginT", DateUtil.toSeconds(beginT));
        filters.put("endT", DateUtil.toSeconds(endT));

        Map<String, Object> result = orderOperationMapper.countTodaySignAndCollect(filters);
        int signNo = 0;
        int collectNo = 0;
        if (result != null) {
            signNo = Integer.valueOf(result.get("signNo") == null ? "0" : result.get("signNo").toString());
            collectNo = Integer.valueOf(result.get("collectNo") == null ? "0" : result.get("collectNo").toString());
        }

        return new Integer[]{collectNo, signNo};
    }

    public VYtoData countYtoData(Long userId, Date endT, Date beginT) {
        Map<String, Object> filters = new HashMap<>();

        filters.put("userId", userId);
        filters.put("beginT", DateUtil.toSeconds(beginT));
        filters.put("endT", DateUtil.toSeconds(endT));
        VYtoData vYtoData = orderOperationMapper.countYtoData(filters);

        return vYtoData;

    }

    public List<Report> findReportsByTime(Byte type, Date beginDate, Date endDate, Date countDate) {
        Map<String, Object> filters = new HashMap<>();

        filters.put("type", type);
        if (beginDate != null)
            filters.put("beginT", DateUtil.toSeconds(beginDate));
        if (endDate != null)
            filters.put("endT", DateUtil.toSeconds(endDate));

        if(countDate == null)countDate = new Date();
        filters.put("countT", DateUtil.toSeconds(countDate));

        return orderOperationMapper.findReportsByTime(filters);
    }

    /**
     * 备份orderOperationHistory到备份表
     * @param sign_fail_date
     * @param other_date
     * @return
     */
    public int[] backup(Date sign_fail_date, Date other_date) {
        int backup = orderOperationMapper.backup(sign_fail_date, other_date);
        int delete = orderOperationMapper.deleteHistory(sign_fail_date, other_date);
        return new int[]{backup, delete};
    }

    /**
     * 备份orderOperation到历史表
     * @param sign_fail_date
     * @param other_date
     * @return
     */
    public int[] backupToHistory(Date sign_fail_date, Date other_date) {
        int backup = orderOperationMapper.backupHistory(sign_fail_date, other_date);
        int delete = orderOperationMapper.deleteOperation(sign_fail_date, other_date);
        return new int[]{backup, delete};
    }

    public Report countAllByTime(Byte oneDayAllCode, Date beginDate, Date endDate) {
        Map<String, Object> filters = new HashMap<>();

        filters.put("type", oneDayAllCode);
        if (beginDate != null)
            filters.put("beginT", DateUtil.toSeconds(beginDate));
        if (endDate != null)
            filters.put("endT", DateUtil.toSeconds(endDate));

        return orderOperationMapper.countAllByTime(filters);
    }

    public List<VOperationReport> getVOperationReports(String beginT, String endT) {
        return orderOperationMapper.getVOperationReports(beginT, endT);
    }

    // todo 分公司 分部
    public VManagerCurrentData countForManage(String orgCode, byte type, Date beginDate, Date endDate) {
        List<Long> uIds = getUIdsByOrgCodeAndRole(orgCode, type);
        // todo 如果网店下不存在对应快递员直接返回null
        if(CollectionUtils.isEmpty(uIds)) return new VManagerCurrentData();

        return buildData(orgCode, beginDate, endDate, uIds);
    }

    public List<Long> getUIdsByOrgCodeAndRole(String orgCode, byte type) {
        Map<String, Object> filter = new HashMap<>();

        filter.put("orgCode", orgCode);
        filter.put("type", type);
        return orderOperationMapper.countUsersForManage(filter);
    }


    public VManagerCurrentData buildData(String orgCode, Date beginDate, Date endDate, List<Long> uIds) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("uIds", uIds);

        int collectingNo = orderOperationMapper.countCollectingNoForManage(filters);
        int sendingNo = orderOperationMapper.countSendingNoForManage(filters);

        if (beginDate != null)
            filters.put("beginT", DateUtil.toSeconds(beginDate));
        if (endDate != null)
            filters.put("endT", DateUtil.toSeconds(endDate));

        VOperationReport report = orderOperationMapper.countForManage(filters);
        int collectNo = 0, problemNo = 0, sendNo = 0;
        if(report != null){
            collectNo = report.getCollectNo();
            problemNo = report.getProblemNo();
            sendNo = report.getSendNo();
        }

        return new VManagerCurrentData(collectingNo, collectNo, problemNo, sendingNo, sendNo,
                CollectionUtils.isEmpty(uIds) ? 0 : uIds.size(), orgCode);
    }
    /******************** begin private methods ***********************************/


    public List<VManagerSubCurrentData> buildForManageList(String orgCode, Date beginT, Date endT, Integer pageNo, Integer pageSize, Integer showLocationBegin, Integer showLocationEnd) {
        Map<String, Object> filters = new HashMap<>();

        if (!StringUtils.isEmpty(orgCode)) filters.put("orgCode", orgCode);
        if (beginT != null) filters.put("beginT", DateUtil.toSeconds(beginT));
        if (endT != null) filters.put("endT", DateUtil.toSeconds(endT));
        if (pageNo != null) filters.put("pageNo", pageNo);
        if (pageSize != null) filters.put("pageSize", pageSize);

        List<VManagerSubCurrentData> result = orderOperationMapper.buildForManageList(filters);

        if(!CollectionUtils.isEmpty(result)){
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            for (VManagerSubCurrentData data : result){
                if(data == null) continue;
                Map<String, Object> map = new HashMap<>();
                List<Long> uIds = Arrays.asList(data.getUserId());

                map.put("uIds", uIds);
                int collectingNo = orderOperationMapper.countCollectingNoForManage(map);
                int sendingNo = orderOperationMapper.countSendingNoForManage(map);

                data.setCollectingNo(collectingNo);
                data.setSendingNo(sendingNo);

                if (hour >= showLocationBegin && hour < showLocationEnd) { //每天8点到19点之间可以查看地理位置
                    ResponseDTO responseDTO = userLocationService.getUserGps(data.getUserOrgCode(), data.getKey());
                    if(responseDTO != null && responseDTO.getCodeEnum().equals(CodeEnum.C1000)){
                        UserLocation userLocation = (UserLocation) responseDTO.getT2();

                        data.setLat(userLocation.getLat());
                        data.setLng(userLocation.getLng());
                    }
                }
            }
        }
        return result;
    }
}
