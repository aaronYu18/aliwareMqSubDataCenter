package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VHomePage;
import com.courier.db.entity.Branch;
import com.courier.db.entity.Manager;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ryan on 16/1/19.
 */
@Service
@Transactional
public class HomePageService {

    private static final Logger logger = LoggerFactory.getLogger(HomePageService.class);

    //24小时回收
    private int expTime = 60 * 60 * 24;
    private int waitSendExpTime = 60 * 30;

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    BranchService branchService;
    @Autowired
    UserService userService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private OrderOperationService orderOperationService;
    @Value("${delivery.question.day}")
    private Integer queryDays;



    /**
     * 获取首页实体bean
     *
     * @param userId
     * @return
     */
    public VHomePage getHomePageData(Long userId) throws Exception {
        VHomePage vHomePage = new VHomePage();
        vHomePage.setSignNo(getSignNoCount(userId));
        vHomePage.setCollectNo(getCollectNoCount(userId));
        vHomePage.setProblemNo(getProblemNoCount(userId));
        vHomePage.setSendingNo(getSendingNoCount(userId));
        vHomePage.setCollectingNo(getCollectingNoCount(userId));
        if (vHomePage.getSignNo() <= -1 || vHomePage.getCollectNo() <= -1) {
            Date now = new Date();
            Date beginT = DateUtil.getBeginT(now);
            Date endT = DateUtil.getEndT(now);
            Integer[] nos = orderOperationService.countTodaySignAndCollect(userId, endT, beginT);
            vHomePage.setCollectNo(nos[0]);
            vHomePage.setSignNo(nos[1]);
            String signKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
            String collectKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
            cacheUtil.invalidByRedis(signKey);
            cacheUtil.incKeyByTime(signKey, vHomePage.getSignNo(), expTime);
            cacheUtil.invalidByRedis(collectKey);
            cacheUtil.incKeyByTime(collectKey, vHomePage.getCollectNo(), expTime);
        }
        if (vHomePage.getProblemNo() <= -1) {
            int problemNo = statisticsService.problemNo(userId);
            vHomePage.setProblemNo(problemNo);
            String problemKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
            cacheUtil.invalidByRedis(problemKey);
            cacheUtil.incKeyByTime(problemKey, vHomePage.getProblemNo(), expTime);
        }
        if (vHomePage.getSendingNo() == null || vHomePage.getSendingNo() <= -1) {
            int sendingNo = statisticsService.sendIngNo(userId);
            vHomePage.setSendingNo(sendingNo);
            String sendingKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
            cacheUtil.invalidByRedis(sendingKey);
            cacheUtil.incKeyByTime(sendingKey, vHomePage.getSendingNo(), waitSendExpTime);
        }
        if (vHomePage.getCollectingNo() <= -1) {
            int collectingNo = statisticsService.collectingNo(userId);
            vHomePage.setCollectingNo(collectingNo);
            String collectingKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
            cacheUtil.invalidByRedis(collectingKey);
            cacheUtil.incKeyByTime(collectingKey, vHomePage.getCollectingNo(), expTime);
        }
        if(vHomePage.getSignNo() <= 0 && vHomePage.getCollectNo() <= 0 && vHomePage.getProblemNo() <= 0
                 && vHomePage.getCollectingNo() <= 0)
            return null;
        return vHomePage;
    }



    /**
     * 保存至redis
     *
     * @param userId
     * @param vHomePage
     */
    public void saveHomePage(Long userId, VHomePage vHomePage) {
        String signKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        String collectKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        String problemKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        String sendingKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        String collectingKey = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        //存
        if (vHomePage != null) {
            cacheUtil.incKeyByTime(signKey, vHomePage.getSignNo(), expTime);
            cacheUtil.incKeyByTime(collectKey, vHomePage.getCollectNo(), expTime);
            cacheUtil.incKeyByTime(problemKey, vHomePage.getProblemNo(), expTime);
            cacheUtil.invalidByRedis(sendingKey);
            cacheUtil.incKeyByTime(sendingKey, vHomePage.getSendingNo(), waitSendExpTime);
            cacheUtil.incKeyByTime(collectingKey, vHomePage.getCollectingNo(), expTime);
        }
    }



    /**
     * 增加签收（+1）
     *
     * @param userId
     * @return
     */
    public int incSignNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            return cacheUtil.incKey(key).intValue();
        }
        return 0;
    }

    public void setSignNoCount(Long userId, int count){
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            cacheUtil.invalidByRedis(key);
            cacheUtil.incKeyByTime(key, count, expTime);
        }
    }

    /**
     * 减少签收（-1）
     *
     * @param userId
     * @return
     */
    public int decrSignNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.decrKey(key).intValue();
        return 0;
    }

    /**
     * 清除签收（0）
     *
     * @param userId
     * @return
     */
    public void clearSignNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        cacheUtil.invalidByRedis(key);
    }

    /**
     * 增加已取（+1）
     *
     * @param userId
     * @return
     */
    public int incCollectNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.incKey(key).intValue();
        return 0;
    }

    public void setCollectNoCount(Long userId, int count) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            cacheUtil.invalidByRedis(key);
            cacheUtil.incKeyByTime(key, count, expTime);
        }
    }

    /**
     * 减少已取（-1）
     *
     * @param userId
     * @return
     */
    public int decrCollectNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.decrKey(key).intValue();
        return 0;
    }

    /**
     * 清除已取（0）
     *
     * @param userId
     * @return
     */
    public void clearCollectNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        cacheUtil.invalidByRedis(key);
    }

    /**
     * 增加问题件（+1）
     *
     * @param userId
     * @return
     */
    public int incProblemNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.incKey(key).intValue();
        return 0;
    }

    public void setProblemNoCount(Long userId, int count) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            cacheUtil.invalidByRedis(key);
            cacheUtil.incKeyByTime(key, count, expTime);
        }
    }

    /**
     * 减少问题件（-1）
     *
     * @param userId
     * @return
     */
    public int decrProblemNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.decrKey(key).intValue();
        return 0;
    }

    /**
     * 清除问题件（0）
     *
     * @param userId
     * @return
     */
    public void clearProblemNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        cacheUtil.invalidByRedis(key);
    }

    /**
     * 增加派件（+1）
     *
     * @param userId
     * @return
     */
    public Integer incSendingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.incKey(key).intValue();
        return null;
    }

    public void setSendingNoCountOnExist(Long userId, int count) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            cacheUtil.invalidByRedis(key);
            cacheUtil.incKeyByTime(key, count, waitSendExpTime);
        }
    }

    public void setSendingNoCount(Long userId, int count) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        cacheUtil.invalidByRedis(key);
        cacheUtil.incKeyByTime(key, count, waitSendExpTime);
    }

    /**
     * 减少派件（-1）
     *
     * @param userId
     * @return
     */
    public Integer decrSendingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.decrKey(key).intValue();
        return null;
    }

    /**
     * 清除派件（0）
     *
     * @param userId
     * @return
     */
    public void clearSendingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        cacheUtil.invalidByRedis(key);
    }

    /**
     * 增加待取（+1）
     *
     * @param userId
     * @return
     */
    public int incCollectingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.incKey(key).intValue();
        return 0;
    }

    public void setCollectingNoCount(Long userId, int count) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key)) {
            cacheUtil.invalidByRedis(key);
            cacheUtil.incKeyByTime(key, count, expTime);
        }
    }

    /**
     * 减少待取（-1）
     *
     * @param userId
     * @return
     */
    public int decrCollectingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.decrKey(key).intValue();
        return 0;
    }

    /**
     * 清空待取（0）
     *
     * @param userId
     * @return
     */
    public void clearCollectingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        cacheUtil.invalidByRedis(key);
    }



    /**
     * 获取签收
     *
     * @param userId
     * @return
     */
    public int getSignNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SIGN_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.getIncKey(key).intValue();
        return 0;
    }

    /**
     * 获取取件
     *
     * @param userId
     * @return
     */
    public int getCollectNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECT_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.getIncKey(key).intValue();
        return 0;
    }

    /**
     * 获取问题件
     *
     * @param userId
     * @return
     */
    public int getProblemNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_PROBLEM_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.getIncKey(key).intValue();
        return 0;
    }

    /**
     * 获取派件
     *
     * @param userId
     * @return
     */
    public Integer getSendingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_SENDING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.getIncKey(key).intValue();
        return null;
    }

    /**
     * 获取待取
     *
     * @param userId
     * @return
     */
    public int getCollectingNoCount(Long userId) {
        String key = buildHomePageKeyBy(userId, CacheConstant.HOME_PAGE_COLLECTING_DATA_KEY);
        if(cacheUtil.isExistByRedis(key))
            return cacheUtil.getIncKey(key).intValue();
        return 0;
    }


    /**
     * 创建每天的key
     * @param userId
     * @return
     */
    private String buildHomePageKeyBy(Long userId, String key) {
        String dateStr = DateUtil.toShortDay(new Date());
        return String.format(key, userId) + "_" + dateStr;
    }
}
