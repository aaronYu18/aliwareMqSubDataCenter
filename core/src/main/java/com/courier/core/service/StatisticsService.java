package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.db.entity.User;
import com.courier.db.vModel.VYtoData;
import com.courier.core.cache.UserBean;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VHomePage;
import com.courier.core.vModel.VYtoPage;
import com.courier.db.entity.Report;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class StatisticsService{
    private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);
    @Autowired CacheUtil cacheUtil;
    @Autowired UserService userService;
    @Autowired ReportService reportService;
    @Autowired CollectOrderService collectOrderService;
    @Autowired DeliveryOrderService deliveryOrderService;
    @Autowired OrderOperationService orderOperationService;
    @Autowired HomePageService homePageService;
    @Autowired AlipayService alipayService;
    @Value("${delivery.question.day}")
    private Integer queryDays;
    private final int YTO_LAST_DAYS = 7;

    /**
     * 首页统计数据
     */
    public ResponseDTO homePage(String uuid) throws Exception {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();
        Long userId = user.getId();

        VHomePage vHomePage = homePageService.getHomePageData(userId);
        if (vHomePage == null) {
            vHomePage = getvHomePage(userId);
            homePageService.saveHomePage(userId, vHomePage);
        }
        vHomePage.setCollectAmount(alipayService.todayCollectAmount(userId));
        return new ResponseDTO(CodeEnum.C1000, null, vHomePage);
    }

    /**
     * 首页统计数据
     */
    /*public ResponseDTO homePage1(String uuid) throws Exception {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();
        Long userId = user.getId();

        String redisKey = buildHomePageKey(userId);
        VHomePage vHomePage = cacheUtil.getCacheShortTimeByFromRedis(redisKey, VHomePage.class);
        //  todo  查db，并放入redis
        if (vHomePage == null){
            vHomePage = getvHomePage(userId);
            try{
                cacheUtil.put2RedisCacheByShortTime(redisKey, vHomePage);
            }catch (Exception e){
                logger.error("put home page data to redis, uId is {}, error is {}", userId, e.getMessage());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, null, vHomePage);
    }
*/
    /**
     * 我的圆通数据统计
     */
    public ResponseDTO ytoData(String uuid) throws Exception {
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();
        Long userId = user.getId();

        Calendar calendar = Calendar.getInstance();
        if (DateUtil.toShotHHInteger(new Date()) <= 5) {
            calendar.add(Calendar.DATE, -YTO_LAST_DAYS-1);
        }else {
            calendar.add(Calendar.DATE, -YTO_LAST_DAYS);
        }
        String beginT = DateUtil.toDay(calendar.getTime());

        String redisKey = buildYtoPageKey(userId, calendar.getTime());
        VYtoPage vYtoPage = cacheUtil.getCacheByFromRedis(redisKey, VYtoPage.class);
        //  todo  查db，并放入redis
        if (vYtoPage == null){
            // todo  数据统计
            VYtoData vYtoData = reportService.findYtoData(userId);
            // todo  每天统计
            List<Report> reports = reportService.findByUidTypeAndCreateTime(userId, CommonEnum.ReportEnum.DAY.getCode(), beginT);
            vYtoPage = new VYtoPage(reports, vYtoData);
            try{
                cacheUtil.putData2RedisByTime(redisKey, Global.ONE_DAY_AGE, vYtoPage);
            }catch (Exception e){
                logger.error("put yto page data to redis, uId is {}, error is {}", userId, e.getMessage());
            }
        }

        return new ResponseDTO(CodeEnum.C1000, null, vYtoPage);
    }



    /************** private method *******************/
    public VHomePage getvHomePage(Long userId) throws Exception {
        // todo  待派件数 (表: D; status：待派； flag: 有效； 时间：不限)
        int sendIngNo = sendIngNo(userId);
        // todo  问题件数 (表: orderOperation; status：异常签收； flag: 有效； 时间：不限)
        int problemNo = problemNo(userId); //只查当天
        // todo  待取件数 (表: C; status：接单代取； 时间：不限)
        int collectingNo = collectingNo(userId);
        // todo  今日已签件数 (表: orderOperation; status：正常签收； 时间：当天)
        // todo  今日已取件数 (表: orderOperation; status：已取件； 时间：当天)
        Date now = new Date();
        Date beginT = DateUtil.getBeginT(now);
        Date endT = DateUtil.getEndT(now);
        Integer[] nos = orderOperationService.countTodaySignAndCollect(userId, endT, beginT);
        VHomePage vHomePage = new VHomePage(nos[0], nos[1]);

        vHomePage.setSendingNo(sendIngNo);
        vHomePage.setCollectingNo(collectingNo);
        vHomePage.setProblemNo(problemNo);
        return vHomePage;
    }

    // todo  待派件数
    public int sendIngNo(Long userId) {
        Map<String, Object> filters = new HashMap<String, Object>();

        Byte[] status = {Enumerate.DeliveryOrderStatus.sending.getType()};
        filters.put("userId", userId);
        filters.put("status", status);
        filters.put("flag", CommonEnum.VALID.getCode());

        return deliveryOrderService.countByFilters(filters);
    }
    // todo  问题件数
    public int problemNo(Long userId) {
        Map<String, Object> filters = new HashMap<String, Object>();

        Byte[] dcTypes = {Enumerate.DCType.DELIVERY.getCode()};
        Byte[] operations = {Enumerate.OperationType.SIGN_FAIL.getCode()};
        Date beginT = DateUtil.getBeginT(Calendar.getInstance());
        Date endT = DateUtil.getEndT(Calendar.getInstance());

        filters.put("userId", userId);
        filters.put("dcTypes", dcTypes);
        filters.put("operations", operations);
        filters.put("beginT", DateUtil.toSeconds(beginT));
        filters.put("endT", DateUtil.toSeconds(endT));

        return orderOperationService.countByFilters(filters);
    }
    // todo  待取件数
    public int collectingNo(Long userId) {
        Map<String, Object> filters = new HashMap<String, Object>();

        Byte[] status = {Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT.getCode()};
        filters.put("userId", userId);
        filters.put("status", status);

        return collectOrderService.countByFilters(filters);
    }


    private String buildHomePageKey(Long userId) {
        return String.format(CacheConstant.HOME_PAGE_DATA_KEY, userId);
    }

    private String buildYtoPageKey(Long userId, Date d) {
        String date = DateUtil.toShortDay(d);
        return String.format(CacheConstant.YTO_PAGE_DATA_KEY, userId, date);
    }
}
