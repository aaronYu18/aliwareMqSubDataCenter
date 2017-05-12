package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.constant.ParamKey;
import com.courier.commons.model.JGRecordOrder;
import com.courier.commons.model.jinGang.DockResult;
import com.courier.commons.model.jinGang.JGTakingModelReq;
import com.courier.commons.model.xml.*;
import com.courier.commons.mq.client.buiness.PickUpByNoRecordMQClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.mq.packet.MqPacketConvert;
import com.courier.commons.push.JPush;
import com.courier.commons.util.DistanceUtil;
import com.courier.commons.util.Uuid;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.util.json.GsonUtil;
import com.courier.core.cache.UserBean;
import com.courier.core.cache.UserLocation;
import com.courier.core.convert.CollectOrderConvert;
import com.courier.core.convert.JGRecordOrderConvert;
import com.courier.core.convert.OrderOperationConvert;
import com.courier.core.convert.VOrderConvert;
import com.courier.core.jingang.Interfaces;
import com.courier.core.jingang.convert.JGAcceptCollectConvert;
import com.courier.core.jingang.convert.JGTakingModelReqConvert;
import com.courier.core.mq.*;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VOrder;
import com.courier.db.dao.ApiConfigMapper;
import com.courier.db.dao.CollectOrderMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.*;
import com.courier.db.vModel.VCollectData;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import com.courier.sdk.packet.PushMessage;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 * 收件信息
 */
@Service
@Transactional
public class CollectOrderService extends BaseManager<CollectOrder> {
    private static final Logger logger = LoggerFactory.getLogger(CollectOrderService.class);
    @Autowired
    private CollectOrderMapper collectOrderMapper;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private ApiConfigMapper apiConfigMapper;

    @Override
    public CollectOrder getEntity() {
        return new CollectOrder();
    }

    @Override
    public BaseMapper<CollectOrder> getBaseMapper() {
        return collectOrderMapper;
    }

    final static String FAIL_URE_BOOLEAN = "false";
    final static String MAIL_NO_CODE = "S02";  //表示md5 秘钥无效

    @Autowired
    UserService userService;
    @Autowired
    UserLocationService userLocationService;
    @Autowired
    RegionService regionService;
    @Autowired
    BranchService branchService;
    @Autowired
    CacheUtil cacheUtil;

    @Autowired
    CollectAcceptMQClient collectAcceptMQClient;

    @Autowired
    CollectNoAcceptMQClient collectNoAcceptMQClient;

    @Autowired
    CollectPickUpMQClient collectPickUpMQClient;

    @Autowired
    CollectPickUpByNoMQClient collectPickUpByNoMQClient;

    @Autowired
    PushMQClient pushMQClient;

    @Autowired
    UserLocationService locationService;

    @Autowired
    OrderOperationService orderOperationService;

    @Autowired
    RedisService redisService;
    @Autowired
    FetchCourierService fetchCourierService;

    @Autowired
    SourceClientIdRelationService sourceClientIdRelationService;

    @Value("${baidu.gps.url}")
    private String baiduUrl;
    @Value("${baidu.gps.regEx}")
    private String regEx;
    @Value("${jingang.getmailno.info.url}") //电子面单
    private String mailnoUrl;

    @Value("${jingang.secret.info.url}")
    private String secretUrl;
    @Value("${jingang.secret.info.md5}")
    private String secretmd5;


    @Value("${fetch.curFetchDistance}")
    private Long curFetchDistance;

    @Value("${jingang.docktrace.url}")
    private String jg_docktrace_url;

    @Value("${pulse.expiration.time}")
    private Integer pulseExpirationTime;

    @Value("${collect.order.default.sourceName}")
    private String defaultSourceName;

    @Value("${jingang.getmailno.info.cModel.url}")
    private String mailnoCPlusUrl;


//    @Value("${jingang.createOrder.info.clientid}")
//    private String createOrderMd5ClientId;

//    @Value("${jingang.createOrder.info.customerId}")
//    private String createOrderMd5customerId;

//    @Value("${jingang.updateOrder.info.clientid}")
//    private String updateInfoClient;

    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public ResponseDTO findByFilters(String uuid, Map<String, Object> filters, Integer pageNo, Integer pageSize, boolean isUpdateRedis) throws Exception {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Long userId = (Long) responseDTO.getT2();

        if (filters == null) filters = new HashMap<String, Object>();
        int total = 0;
        filters.put("userId", userId);

        if (pageNo != null && pageSize != null) {
            if (pageNo < 1) pageNo = 1;
            filters.put(ParamKey.Page.NUM_KEY, (pageNo - 1) * pageSize);
            filters.put(ParamKey.Page.SIZE_KEY, pageSize);
            total = collectOrderMapper.countByFilters(filters);
            if (isUpdateRedis) redisService.refreshHomeCollected(userId, total);
        }

        List<CollectOrder> list = collectOrderMapper.findByFilters(filters);
        return new ResponseDTO(CodeEnum.C1000, CollectOrderConvert.initRegionList(cacheUtil, list), total);
    }


    /**
     * 统计
     */
    public int countByFilters(Map<String, Object> filters) {
        return collectOrderMapper.countByFilters(filters);
    }


    /**
     * 获取电子面单号(B+)
     */
    public ResponseDTO electronicMailNo(String uuid, Sender sender, Receiver receiver) throws Exception {
        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        String orgCode = user.getOrgCode();
        Branch branch = userBean.getBranch();
        // md5码是否读取redis 如果是读取接口 不会获取两次
        Interfaces interfaces = new Interfaces();
        boolean flag = true;
        // todo 获取orgCode 对应的clientID  如果 userBean 中没有 调用接口
        String clientId = branch.getCustomerCode();
        if (StringUtils.isEmpty(clientId)) {
            clientId = branchService.getCustomerCode(orgCode);
            if (!StringUtils.isEmpty(clientId)) {
                updateBranch(uuid, userBean, user, branch, clientId);
            } else {
                return new ResponseDTO(CodeEnum.C2020);
            }
        }
        // todo 获取orgCode对应的ClientID 的密码
        String md5Secret = cacheUtil.getCacheByFromRedis(buildSecretMailNoKey(clientId), String.class);
        if (StringUtils.isEmpty(md5Secret)) {
            md5Secret = findSecretKeyByRT(interfaces, clientId);
            flag = false;//标记是实时读取接口
        }
        // todo 获取电子面单 mailNo 如果秘钥来自 redis 异常情况 再实时读取接口获取md5码
        ResponseMailNoResult newMailNo = findNewMailNoByRT(interfaces, sender, receiver, clientId, md5Secret);
        if (newMailNo != null && FAIL_URE_BOOLEAN.equalsIgnoreCase(newMailNo.getSuccess()) && MAIL_NO_CODE.equalsIgnoreCase(newMailNo.getCode()) && flag) {
            md5Secret = findSecretKeyByRT(interfaces, clientId);//更新md5
            newMailNo = findNewMailNoByRT(interfaces, sender, receiver, clientId, md5Secret);//再获取一次
        }
        // todo 排除没有足够的面单号的问题
        if (newMailNo == null || FAIL_URE_BOOLEAN.equalsIgnoreCase(newMailNo.getSuccess()))
            return new ResponseDTO(CodeEnum.C2020);
        String shortAddress = newMailNo.getDistributeInfo() == null ? "" : newMailNo.getDistributeInfo().getShortAddress();
        return new ResponseDTO(CodeEnum.C1000, null, new MailNoAndShortAddress(newMailNo.getMailNo(), shortAddress));
    }

    /**
     * 获取电子面单号(C+)
     */
    public ResponseDTO electronicMailNoCPlus(String uuid, Sender sender, Receiver receiver, Long orderId) throws Exception {
        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        String orgCode = user.getOrgCode();
        Branch branch = userBean.getBranch();
        // md5码是否读取redis 如果是读取接口 不会获取两次
        Interfaces interfaces = new Interfaces();
        boolean flag = true;
        // todo 获取orgCode 对应的clientID  如果 userBean 中没有 调用接口
        String clientId = branch.getCustomerCode();
        if (StringUtils.isEmpty(clientId)) {
            clientId = branchService.getCustomerCode(orgCode);
            if (!StringUtils.isEmpty(clientId)) {
                updateBranch(uuid, userBean, user, branch, clientId);
            } else {
                return new ResponseDTO(CodeEnum.C2020);
            }
        }
        // todo 获取orgCode对应的ClientID 的密码
        String md5Secret = cacheUtil.getCacheByFromRedis(buildSecretMailNoKey(clientId), String.class);
        if (StringUtils.isEmpty(md5Secret)) {
            md5Secret = findSecretKeyByRT(interfaces, clientId);
            flag = false;//标记是实时读取接口
        }
        // todo 获取电子面单 mailNo 如果秘钥来自 redis 异常情况 再实时读取接口获取md5码
        String sourceKey = null;
        String sourceName = null;
        if (orderId != null) {
            CollectOrder co = new CollectOrder();
            co.setId(orderId);
            co = collectOrderMapper.get(co);
            if (co != null) {
                sourceKey = co.getSourceKey();
                sourceName = getSourceName(co.getSource());
            }
        }
        ResponseMailNoModelCResult newMailNo = findNewMailNoByCModelRT(interfaces, sender, receiver, clientId, md5Secret, sourceKey, user.getJobNo(), sourceName);
        if (newMailNo != null && FAIL_URE_BOOLEAN.equalsIgnoreCase(newMailNo.getSuccess()) && flag) {
            md5Secret = findSecretKeyByRT(interfaces, clientId);//更新md5
            newMailNo = findNewMailNoByCModelRT(interfaces, sender, receiver, clientId, md5Secret, sourceKey, user.getJobNo(), sourceName);
        }
        // todo 排除没有足够的面单号的问题
        if (newMailNo == null || FAIL_URE_BOOLEAN.equalsIgnoreCase(newMailNo.getSuccess()))
            return new ResponseDTO(CodeEnum.C2020);
        String shortAddress = newMailNo.getOrderMessage() == null ? "" : newMailNo.getOrderMessage().getBigPen();
        return new ResponseDTO(CodeEnum.C1000, null, new MailNoAndShortAddress(newMailNo.getOrderMessage().getMailNo(), shortAddress));
    }

    private String getSourceName(Byte source) {
        String key = buildSourceNameKey(source);
        ApiConfig apiConfig = cacheUtil.getCacheByFromRedis(key, ApiConfig.class);
        if (apiConfig == null) {
            apiConfig = apiConfigMapper.getBySource(source);
            cacheUtil.putData2RedisByTime(key, Global.THREE_DAY_AGE, apiConfig);
        }
        return apiConfig.getName();
    }

    private static String buildSourceNameKey(Byte source){
        return String.format(CacheConstant.SOURCE_NAME_KEY, source);
    }

    /**
     * 推送文本文件
     */
    public void pushTextMessage(String text, String provinceCode, String cityCode) throws Exception {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(provinceCode) || StringUtils.isEmpty(cityCode)) return;

        ResponseDTO byRegion = userLocationService.getUidsByRegion(provinceCode, cityCode);
        if (CodeEnum.C1000 != byRegion.getCodeEnum())
            logger.error("get userIds by provinceCode/cityCode error,provinceCode is {}, cityCode is {}, error is {}", provinceCode, cityCode, byRegion.getCodeEnum().getDesc());

        List<Long> userIds = byRegion.getList();
        if (userIds == null || userIds.isEmpty()) return;

        String[] jobNos = userService.findJobNoByUserIds(userIds.toArray(new Long[]{}));

        Map extMap = new HashMap();
        extMap.put(JPush.extdata, text);
        JPush jPush = new JPush(jobNos, extMap, Enumerate.ContentType.TEXTMESSAGE);
        sendMQMessage(jPush);
    }


    /**
     * 接受第三方系统推送给行者
     * 对于city province 为无效的  也需要 返回给 金刚重新分配
     */
    public ResponseDTO pushRob(CollectOrder order) throws Exception {
        // todo  校验
        if (order == null) return new ResponseDTO(CodeEnum.C1034);
        Byte source = order.getSource();
        String sourceKey = order.getSourceKey();
        if (source == null || StringUtils.isEmpty(sourceKey))
            return new ResponseDTO(CodeEnum.C1034);

        //todo 标准code
        CollectOrderConvert.standardCode(cacheUtil, order);
        order = CollectOrderConvert.initRegion(cacheUtil, order);
        //todo cityCode proviceCode 都为空 直接 推送给 圆通app 分配
        if (StringUtils.isEmpty(order.getSenderCity()) || StringUtils.isEmpty(order.getSenderProvince())) {
            String createOrderMd5ClientId = getClientIdBySourceId(source);
            RequestOrder requestOrder = JGAcceptCollectConvert.convertObj(order, null, createOrderMd5ClientId, "");
            sendMQMessage(requestOrder, Enumerate.MQType.COLLECTACCEPTNO);
            return new ResponseDTO(CodeEnum.C1000, null, order);
        }
        if (!StringUtils.isEmpty(order.getMailNo())) {
            ResponseDTO existByMailNo = existByMailNo(order.getMailNo());
            if (CodeEnum.C1000 != existByMailNo.getCodeEnum()) return new ResponseDTO(CodeEnum.C1096);
        }
        //todo 判断是否能插入
        List<CollectOrder> orderList = collectOrderMapper.findBySourceKey(source, sourceKey);
        if (orderList != null && orderList.size() > 0) return new ResponseDTO(CodeEnum.C2016);
        //todo 存入DB中
        order.setSerialNo(new Uuid().toString());
        String fullyAddress = translateToAddress(order);
        order = CollectOrderConvert.fillGps(order, baiduUrl, regEx, fullyAddress);

        //todo orderType 订单类型不能为空 订单状态不能为空
        order.setOrderType(Enumerate.CollectOrderType.GRAB.getCode());
        order.setOrderStatus(Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode());

        save(order);
        //todo 放入redis中
        redisService.putToRedis(order);
        // todo 推送

        pushOrderMessageToScopeAll(order, Enumerate.CollectOrderStatus.WAIT_ACCEPT, Enumerate.ContentType.ROB);

        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * 接收指定快递员的分配的订单信息(未取)
     */
    public ResponseDTO pushUserOrder(CollectOrder order) throws Exception {
        if (order == null || order.getSource() == null || StringUtils.isEmpty(order.getSourceKey()) || order.getUserId() == null)
            return new ResponseDTO(CodeEnum.C1034);
        Long userId = order.getUserId();
        logger.debug("begin push user order message:{}", GsonUtil.toJson(order));
        //todo 判断是否是 已分配的订单
        List<CollectOrder> list = collectOrderMapper.findBySourceKey(order.getSource(), order.getSourceKey());
        if (list == null || list.size() == 0) {
            CollectOrderConvert.standardCode(cacheUtil, order);
            order = CollectOrderConvert.initRegion(cacheUtil, order);
            order = newCollectOrder(order);
        } else {
            CollectOrder orderDB = list.get(0);
            orderDB = CollectOrderConvert.initRegion(cacheUtil, orderDB);
            if (orderDB == null) return new ResponseDTO(CodeEnum.C2015);
            //todo 订单已经被 接单了或揽件了
            ResponseDTO statusAndType = checkOrderStatusAndType(userId, orderDB);
            if (CodeEnum.C1000 != statusAndType.getCodeEnum()) return statusAndType;
            Boolean isSameUser = (Boolean) statusAndType.getT2();
            if (isSameUser != null && isSameUser) return new ResponseDTO(CodeEnum.C1000);
            // todo 多次指派同一人直接返回
            //todo 系统指派时，可以多次指派，但是需要发送push cancel
            Byte orderType = orderDB.getOrderType();
            if (Enumerate.CollectOrderType.SYSTEM.getCode().equals(orderType)) {//系统指派
                User user = userService.get(orderDB.getUserId());
                pushOrderMessageToOneByDirect(orderDB, user, Enumerate.CollectOrderStatus.CANCEL, Enumerate.ContentType.CANCEL);
            }
            //todo 重新回来的订单
            orderDB = CollectOrderConvert.convert(order, orderDB, userId, Enumerate.CollectOrderType.SYSTEM, Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT);
            update(orderDB);
            saveOrderOperation(orderDB);
            order = orderDB;
        }
        //todo push 推送
        User user = userService.get(userId);
        pushOrderMessageToOneByDirect(order, user, Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT, Enumerate.ContentType.ASSIGN);
        redisService.invalidHomePageAndList(userId, null, Enumerate.OperationType.GRAB);
        logger.debug("end push user order message:{}", GsonUtil.toJson(order));
        return new ResponseDTO(CodeEnum.C1000);
    }


    /**
     * 取消订单 2 4 不能取消
     */
    public ResponseDTO cancelOrder(Byte source, String sourceKey) throws Exception {
        if (StringUtils.isEmpty(sourceKey) || source == null)
            return new ResponseDTO(CodeEnum.C1034);

        List<CollectOrder> list = collectOrderMapper.findBySourceKey(source, sourceKey);
        if (list == null || list.size() == 0) return new ResponseDTO(CodeEnum.C2015);

        CollectOrder orderDB = list.get(0);
        if (orderDB == null) return new ResponseDTO(CodeEnum.C2015);

        Byte orderStatus = orderDB.getOrderStatus();
        if (Enumerate.CollectOrderStatus.COLLECTED.getCode().equals(orderStatus))
            return new ResponseDTO(CodeEnum.C2017);
        else if (Enumerate.CollectOrderStatus.CANCEL.getCode().equals(orderStatus))
            return new ResponseDTO((CodeEnum.C2019));
            //todo 待接单是可以取消 但从 redis 中去除
        else if (Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode().equals(orderStatus)) {
            Long cId = orderDB.getId();
            String provinceCode = orderDB.getSenderProvince();
            String cityCode = orderDB.getSenderCity();
            cacheUtil.fetchOrder(provinceCode, cityCode, cId);
        }

        orderDB.setOrderStatus(Enumerate.CollectOrderStatus.CANCEL.getCode());
        orderDB.setUpdateTime(new Date());
        update(orderDB);
        //todo 更新redis的homePage
        redisService.invalidHomePageAndList(orderDB.getUserId(), null, Enumerate.OperationType.CANCEL);
        //todo 推送提醒用户 (仅仅是给接单的用户)
        if (orderDB.getUserId() != null) {
            User user = userService.get(orderDB.getUserId());
            pushOrderMessageToOneByDirect(orderDB, user, Enumerate.CollectOrderStatus.CANCEL, Enumerate.ContentType.CANCEL);
        }
        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * 快递员位置上传时  调用 有单就推送
     */
    public void pulsePush(UserBean userBean, Double lat, Double lng) throws Exception {
        ResponseDTO responseDTO = findUnRobs(userBean, curFetchDistance, lng, lat);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return;
        List<VOrder> list = responseDTO.getList();
        if (list == null || list.size() == 0) {
            logger.debug("no rob collect order for the user :{} ", userBean.toJson());
            return;
        }
        User user = userBean.getUser();
        for (VOrder vOrder : list) {
            CollectOrder order = vOrder.getCollectOrder();
            //todo 开始时判读是否有过推送
            if (order == null || existSetPulseOrder(order.getId(), user.getId())) continue;
            order.setUserId(user.getId());
            pushOrderMessageToOne(order, user, Enumerate.CollectOrderStatus.WAIT_ACCEPT, Enumerate.ContentType.ROB);
            //todo 结束 存入reids中
            putDataForPulsePush(order.getId(), user.getId());
        }
    }


    /**
     * 获取未抢单列表  (只取所属市)
     */
    public ResponseDTO findUnRobs(String uuid, double distance, double lng, double lat) {
        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();

        return findUnRobs(userBean, distance, lng, lat);
    }


    /**
     * 接单接口 (快递员接单了)
     *
     * @param cId
     * @return
     */
    public ResponseDTO accept(String uuid, Long cId) throws Exception {
        if (cId == null || cId == 0l) return new ResponseDTO(CodeEnum.C1034);

        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        User user = (User) responseDTO.getT2();

        //todo redis CollectOrder 键值对
        CollectOrder orderRedis = redisService.getCorderByCid(cId);
        if (orderRedis == null) return new ResponseDTO(CodeEnum.C2015, null, cId);
        String provinceCode = orderRedis.getSenderProvince();
        String cityCode = orderRedis.getSenderCity();
        //todo redis中存在 被抢到  删除redis中的数据 ，更新数据库 (数据同步) fetChFlag（1:抢到 0:没有抢到）
        int fetchFlag = cacheUtil.fetchOrder(provinceCode, cityCode, cId);
        if (0 == fetchFlag)
            return new ResponseDTO(CodeEnum.C2013, null, cId);
        //todo 抢到了去读下DB
        CollectOrder orderDB = get(orderRedis.getId());

        if (!Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode().equals(orderDB.getOrderStatus()))
            return new ResponseDTO(CodeEnum.C2013, null, cId);
        orderDB.setUserId(user.getId());
        orderDB.setOrderStatus(Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT.getCode());
        Date now = new Date();
        orderDB.setReceiveTime(now);
        orderDB.setUpdateTime(now);
        update(orderDB);
        saveOrderOperation(orderDB);

        orderDB = CollectOrderConvert.initRegion(cacheUtil, orderDB);
        String createOrderMd5ClientId = getClientIdBySourceId(orderDB.getSource());
        RequestOrder requestOrder = JGAcceptCollectConvert.convertObj(orderDB, user, createOrderMd5ClientId, "");
        sendMQMessage(requestOrder, Enumerate.MQType.COLLECTACCEPT);
        redisService.invalidHomePageAndList(user.getId(), null, Enumerate.OperationType.GRAB);
        return new ResponseDTO(CodeEnum.C1000, null, orderDB);
    }


    /**
     * 没有快递员接单（30 分钟无人接单） 不需要调用redis 的抢单 方法
     */
    public ResponseDTO acceptNoUser(Long cId, String cityCode, String provinceCode) throws Exception {
        if (cId == null || StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(provinceCode))
            return new ResponseDTO(CodeEnum.C1034);
        //todo 先删除Redis中的 flag  为false 已经被抢
        CollectOrder orderRedis = redisService.getCorderByCid(cId);
        if (orderRedis == null)
            return new ResponseDTO(CodeEnum.C2013, null, cId);
        logger.debug("begin accept no user orderID:{},cityCode:{},provinceCode:{}", cId, cityCode, provinceCode);
        int fetchFlag = cacheUtil.fetchOrder(provinceCode, cityCode, cId);
        if (0 == fetchFlag)
            return new ResponseDTO(CodeEnum.C2013, null, cId);

        CollectOrder dbOrder = get(orderRedis.getId());
        if (!dbOrder.getOrderStatus().equals(Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode())) {
            return new ResponseDTO(CodeEnum.C2013, null, cId);
        }
        dbOrder.setOrderStatus(Enumerate.CollectOrderStatus.WAIT_ACCEPT_TIMEOUT.getCode());
        dbOrder.setUpdateTime(new Date());
        update(dbOrder);

        //todo  插入 无人接单mq队列
        dbOrder = CollectOrderConvert.initRegion(cacheUtil, dbOrder);
        String createOrderMd5ClientId = getClientIdBySourceId(dbOrder.getSource());
        RequestOrder requestOrder = JGAcceptCollectConvert.convertObj(dbOrder, null, createOrderMd5ClientId, "");
        sendMQMessage(requestOrder, Enumerate.MQType.COLLECTACCEPTNO);
        logger.debug("end accept no user orderID:{},cityCode:{},provinceCode:{}", cId, cityCode, provinceCode);
        return new ResponseDTO(CodeEnum.C1000, null, dbOrder);

    }

    public List<CollectOrder> findWaitAcceptOrders() {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("status", new Byte[]{Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode()});
		filters.put("types", new Byte[]{Enumerate.CollectOrderType.GRAB.getCode()});
        List<CollectOrder> list = collectOrderMapper.findByFilters(filters);
        return list;
    }

    /**
     * 有单取件 (别的快递员的单子也能取)
     * CollectOrder的主键ID不能为空(其他人也可以取)
     */
    public ResponseDTO pickUp(String uuid, CollectOrder order, Enumerate.PlatformType platformType) throws Exception {
        if (order == null || order.getId() == null || StringUtils.isEmpty(order.getMailNo()))
            return new ResponseDTO(CodeEnum.C1034);

        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        UserLoginRecord userLoginRecord = userBean.getUserLoginRecord();
        Long userId = user.getId();

        ResponseDTO responseMailNo = existByMailNo(order.getMailNo());
        if (CodeEnum.C1000 != responseMailNo.getCodeEnum()) return responseMailNo;
        //todo 根据单子的ID
        CollectOrder orderDb = get(order.getId());
        if (orderDb == null) return new ResponseDTO(CodeEnum.C2015);
        Byte orderStatus = orderDb.getOrderStatus();

        if (Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode().equals(orderStatus)
                || Enumerate.CollectOrderStatus.CANCEL.getCode().equals(orderStatus)
                || Enumerate.CollectOrderStatus.WAIT_ACCEPT_TIMEOUT.getCode().equals(orderStatus)) {
            return new ResponseDTO(CodeEnum.C2015);
        }
        if (Enumerate.CollectOrderStatus.COLLECTED.getCode().equals(orderStatus)) {
            return new ResponseDTO(CodeEnum.C1089);
        }

        orderDb = CollectOrderConvert.convert(order, orderDb, userId, null, Enumerate.CollectOrderStatus.COLLECTED);
        orderDb = CollectOrderConvert.initRegionForce(cacheUtil, orderDb);
        update(orderDb);
        saveOrderOperation(orderDb);
        //todo 调用MQ 触发金刚取件接口
        String updateInfoClient = getClientIdBySourceId(orderDb.getSource());
        JGTakingModelReq takingModelReq = JGTakingModelReqConvert.convertObj(orderDb, user, uuid, platformType.getType(), userLoginRecord, updateInfoClient);

        sendMQMessageOrder(takingModelReq);
        redisService.invalidHomePageAndList(userId, null, Enumerate.OperationType.COLLECT);

        return new ResponseDTO(CodeEnum.C1000, null, orderDb.getId());
//        //如果传入支付授权码,要做支付处理
//        String tradeNo = null;
//        if (StringUtils.isNotEmpty(payCode)) {
//            ResponseDTO responsePay = alipayService.pay(uuid, Enumerate.DCType.COLLECT.getCode(), orderDb.getId(),
//                    payCode, platformType.getType());
//            if (!CodeEnum.C1000.equals(responsePay.getCodeEnum()))
//                return responsePay;
//            tradeNo = responsePay.getT2().toString();
//        }
//
//        return new ResponseDTO(CodeEnum.C1000, null, tradeNo);
    }

    public ResponseDTO existByMailNo(String uuid, String mailNo) {
        if (StringUtils.isEmpty(mailNo)) return new ResponseDTO(CodeEnum.C1034);
        ResponseDTO responseDTO = userService.getUserByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        return existByMailNo(mailNo);
    }


    /**
     * 无单取件
     */
    public ResponseDTO pickUpByNo(String uuid, CollectOrder order, Enumerate.PlatformType platformType) throws Exception {
        if (order == null || StringUtils.isEmpty(order.getMailNo()))
            return new ResponseDTO(CodeEnum.C1034);

        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        UserLoginRecord userLoginRecord = userBean.getUserLoginRecord();

        ResponseDTO responseMailNo = existByMailNo(order.getMailNo());
        if (CodeEnum.C1000 != responseMailNo.getCodeEnum()) return responseMailNo;
        order.setUserId(user.getId());
        order.setOrderStatus(Enumerate.CollectOrderStatus.COLLECTED.getCode());
        order.setOrderType(Enumerate.CollectOrderType.NOORDER.getCode());

        order = CollectOrderConvert.initRegion(cacheUtil, order);
        String fullyAddress = translateToAddress(order);
        order = CollectOrderConvert.fillGps(order, baiduUrl, regEx, fullyAddress);
        order = CollectOrderConvert.initWithOutOrder(order);

        save(order);
        saveOrderOperation(order);
        redisService.invalidHomePageAndList(user.getId(), null, Enumerate.OperationType.NOCOLLECT);
        //todo 调用MQ 触发金刚无单取件接口

        JGTakingModelReq takingModelReq = JGTakingModelReqConvert.convertObj(order, user, uuid, platformType.getType(), userLoginRecord, "");
        sendMQMessage(takingModelReq);

        return new ResponseDTO(CodeEnum.C1000, null, order.getId());
//        //如果传入支付授权码,要做支付处理
//        String tradeNo = null;
//        if (StringUtils.isNotEmpty(payCode)) {
//            ResponseDTO responsePay = alipayService.pay(uuid, Enumerate.DCType.COLLECT.getCode(), order.getId(),
//                    payCode, platformType.getType());
//            if (!CodeEnum.C1000.equals(responsePay.getCodeEnum()))
//                return responsePay;
//            tradeNo = responsePay.getT2().toString();
//        }
//
//        return new ResponseDTO(CodeEnum.C1000, null, tradeNo);
    }


    public int delInvalidData(int minutes) {
        return collectOrderMapper.delInvalidData(minutes);
    }

    public static String buildCollectOrderKey(Long id) {
        return String.format(CacheConstant.COLLECTION_ORDER_KEY, id);
    }

    public static String buildCityCollectOrderKey(String cityCode) {
        return String.format(CacheConstant.COLLECTION_ORDER_CITY_KEY, cityCode);
    }

    public static String buildNuRobsOrderKey(Long userId) {
        return String.format(CacheConstant.COLLECTION_ORDER_UNROBS_EY, userId);
    }


    public static String buildCollectPulseSetKey(Long id) {
        return String.format(CacheConstant.PLUSE_COLLECT_ORDERID, id);
    }

    public static String buildSecretMailNoKey(String clientID) {
        return String.format(CacheConstant.SECRET_ORGCODE_CLIENT, clientID);
    }

    /**
     * *********************************************************************************************************
     * begin private method
     * *********************************************************************************************************
     */
    /**
     * 根据渠道sourceId 获得clientId
     * 先读取redis 没有读取Db，并插入redis
     *
     * @param sourceId
     * @return
     */
    public String getClientIdBySourceId(Byte sourceId) {
        return sourceClientIdRelationService.findClientIdBySource(sourceId);
    }

    private ResponseDTO checkOrderStatusAndType(Long userId, CollectOrder orderDB) {
        Byte orderType = orderDB.getOrderType();
        Byte orderStatus = orderDB.getOrderStatus();
        if (Enumerate.CollectOrderStatus.COLLECTED.getCode().equals(orderStatus) ||
                Enumerate.CollectOrderStatus.CANCEL.getCode().equals(orderStatus)) {
            return new ResponseDTO(CodeEnum.C2015);

        } else if (Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode().equals(orderStatus)) {
            //todo 处于待接单
            int fetchFlag = cacheUtil.fetchOrder(orderDB.getSenderProvince(), orderDB.getSenderCity(), orderDB.getId());
            if (0 == fetchFlag) return new ResponseDTO(CodeEnum.C2021, null, orderDB.getId());
        }

        //todo 系统指派时同一个人
        if (Enumerate.CollectOrderType.SYSTEM.getCode().equals(orderType)) {
            if (userId.equals(orderDB.getUserId()))
                return new ResponseDTO(CodeEnum.C1000, null, true);

        } else if (Enumerate.CollectOrderType.GRAB.getCode().equals(orderType)) {
            if (Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT.getCode().equals(orderStatus))
                return new ResponseDTO(CodeEnum.C2021);
        }
        return new ResponseDTO(CodeEnum.C1000);
    }

    /*
    接收指定快递员的分配
     */
    private CollectOrder newCollectOrder(CollectOrder order) {
        order.setOrderType(Enumerate.CollectOrderType.SYSTEM.getCode());
        order.setOrderStatus(Enumerate.CollectOrderStatus.ACCEPTED_WAIT_COLLECT.getCode());
        order.setSerialNo(new Uuid().toString());
        order.setReceiveTime(new Date());
        String fullyAddress = translateToAddress(order);
        order = CollectOrderConvert.fillGps(order, baiduUrl, regEx, fullyAddress);

        save(order);
        saveOrderOperation(order);
        return order;
    }

    /**
     * 更新 branch 中的customerID
     */
    private void updateBranch(String uuid, UserBean userBean, User user, Branch branch, String clientId) {
        branch.setCustomerCode(clientId);
        branchService.update(branch);
        userBean.setBranch(branch);
        userService.putToRedis(uuid, userBean, user.getId());
    }

    /**
     * 通过mailNo查找轨迹信息
     *
     * @param mailNo
     * @return false :不存在走件信息(可用)  true ：存在走件信息(不可用)
     */
    private boolean isExistDockTrace(String mailNo) {
        Interfaces interfaces = new Interfaces();
        List<DockResult> list = interfaces.dockTrace2(jg_docktrace_url, mailNo);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return true;
    }

    private void putDataForPulsePush(Long orderId, Long userId) {
        cacheUtil.putData2RedisSet(buildCollectPulseSetKey(orderId), userId);
    }

    private ResponseDTO findUnRobs(UserBean userBean, double distance, double lng, double lat) {
        User user = userBean.getUser();
        Branch branch = userBean.getBranch();
        Long id = userBean.getUser().getId();
        if (branch == null) {
            logger.error("branch info is null, user id is {}", id);
            return new ResponseDTO(CodeEnum.C1000);
        }

        String cityCode = branch.getCityCode();
        String provinceCode = branch.getProvinceCode();
        if (StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(provinceCode)) {
            logger.error("branch info, cityCode or provinceCode is empty, user id is {}", id);
            return new ResponseDTO(CodeEnum.C1000);
        }
        //先从redis中读取
        String key = buildNuRobsOrderKey(user.getId());
        List<VOrder> list = cacheUtil.getCacheShortTimeByFromRedis(key, List.class);
        if (list != null && list.size() > 0) return new ResponseDTO(CodeEnum.C1000, list, null);

        //  todo 先从redis中取订单ID (先根据city取，如果没有取到，再根据province取)
        Set<Long> ids = getUnRobOrders(provinceCode, cityCode);
        if (ids.isEmpty()) return new ResponseDTO(CodeEnum.C1000);
        // todo 快递员的位置信息 排序
        List<CollectOrder> orders = getOrderByOIds(ids);
        list = sortOrders(distance, orders, lng, lat);

        cacheUtil.put2RedisCacheByShortTime(key, list);
        return new ResponseDTO(CodeEnum.C1000, list, user);
    }


    private boolean existSetPulseOrder(Long id, Long userId) {
        return cacheUtil.isExistInSet(buildCollectPulseSetKey(id), userId);
    }

    /**
     * 推送 订单 (抢单，取消，接单)  //没有用户 推送全员 (3公里 可以设置)
     */
    private void pushOrderMessageToScopeAll(CollectOrder order, Enumerate.CollectOrderStatus status, Enumerate.ContentType type) throws Exception {
        if (order == null) return;
        if (order.getSenderLat() == null || order.getSenderLng() == null ||
                StringUtils.isEmpty(order.getSenderProvince()) ||
                StringUtils.isEmpty(order.getSenderCity()))
            return;

        JPush jPush = getJPushByAll(order, status, type);

        if (jPush != null) {
            sendMQMessage(jPush);
            logger.debug("##### end sendMQMessage, orderId:{}, message:{}", order.getId(), GsonUtil.toJson(order));
            return;
        }
        logger.info("the scope of iquery without courier !!! ");
    }

    /**
     * 推送 订单 ( 接单)  //没有用户 推送全员 (3公里 可以设置)
     */
    private void pushOrderMessageToOne(CollectOrder order, User user, Enumerate.CollectOrderStatus status, Enumerate.ContentType type) throws Exception {
        if (order == null) return;
        if (order.getSenderLat() == null || order.getSenderLng() == null
                || StringUtils.isEmpty(order.getSenderProvince()) || StringUtils.isEmpty(order.getSenderCity()))
            return;
        //todo 开始时判读是否有过推送
        if (user == null || StringUtils.isEmpty(user.getJobNo())) return;
        String jobNo = user.getJobNo();
        JPush jPush = getJPushByOne(order, jobNo, status, type);
        sendMQMessage(jPush);
    }

    /**
     * direct push no filter by lat&lng or address
     *
     * @param order
     * @param user
     * @param status
     * @param type
     * @throws Exception
     */
    private void pushOrderMessageToOneByDirect(CollectOrder order, User user, Enumerate.CollectOrderStatus status, Enumerate.ContentType type) throws Exception {
        if (order == null) return;
        //todo 开始时判读是否有过推送
        if (user == null || StringUtils.isEmpty(user.getJobNo())) return;
        String jobNo = user.getJobNo();
        if (StringUtils.isEmpty(order.getSenderAddress())) order.setSenderAddress("寄件人电话" + order.getSenderPhone());

        if (order.getSenderLat() == null || order.getSenderLng() == null) {
            UserLocation location = getCurrentGPS(user);
            order.setSenderLat(location.getLat());
            order.setSenderLng(location.getLng());
        }

        JPush jPush = getJPushByOne(order, jobNo, status, type);
        sendMQMessage(jPush);
    }


    private UserLocation getCurrentGPS(User user) {
        try {
            ResponseDTO userGps = userLocationService.getUserGps(user.getOrgCode(), user.getJobNo());
            CodeEnum codeEnum = userGps.getCodeEnum();
            if (CodeEnum.C1000 == codeEnum) {
                UserLocation userLocation = (UserLocation) userGps.getT2();
                if (userLocation != null) return userLocation;
            }
        } catch (Exception e) {

        }
        return new UserLocation(0d, 0d);
    }

    /**
     * 通过省市查找
     *
     * @param order
     * @param status
     * @return
     */
    private JPush getJPushByAll(CollectOrder order, Enumerate.CollectOrderStatus status, Enumerate.ContentType type) {

        logger.debug("start fetchCourier orderId:{} message:{}", order.getId(), GsonUtil.toJson(order));

        List<UserBean> userBeans = fetchCourierService.fetchCourier(order.getSenderProvince(), order.getSenderCity(), order.getSenderLng(), order.getSenderLat());
        if (userBeans == null || userBeans.size() == 0) {
            logger.info("get none user ids by provinceCode {} and cityCode {} ", order.getSenderProvince(), order.getSenderCity());
            return null;
        }

        logger.debug("end fetchCourier orderId:{} message:{}", order.getId(), GsonUtil.toJson(order));

        List<String> jobNos = new ArrayList<String>();
        for (UserBean userBean : userBeans) {
            String jobNo = userBean.getUser().getJobNo();
            jobNos.add(jobNo);
            //todo 标记已经推送过
            putDataForPulsePush(order.getId(), userBean.getUser().getId());
        }
        PushMessage pushObj = getPushObj(order, status);
        String[] alias = jobNos.toArray(new String[]{});
        Map extMap = new HashMap();
        extMap.put(JPush.extdata, GsonUtil.toJson(pushObj));

        return new JPush(alias, extMap, type);
    }

    /**
     * 推动单个的
     *
     * @param order
     * @param jobNo
     * @param status
     * @return
     */
    private JPush getJPushByOne(CollectOrder order, String jobNo, Enumerate.CollectOrderStatus status, Enumerate.ContentType type) {
        Map extMap = new HashMap();

        PushMessage pushObj = getPushObj(order, status);
        extMap.put(JPush.extdata, GsonUtil.toJson(pushObj));
        return new JPush(new String[]{jobNo}, extMap, type.getTitle(), type.getContent(), type);
    }


    /**
     * 获取push的对象
     */
    private PushMessage getPushObj(CollectOrder order, Enumerate.CollectOrderStatus status) {
        Long id = order.getId();
        String mailNo = order.getMailNo();
        Byte orderType = order.getOrderType();
        Byte code = status.getCode();
        String senderAddress = order.getSenderAddress();
        Double senderLat = order.getSenderLat();
        Double senderLng = order.getSenderLng();

        PushMessage message = new PushMessage(id, mailNo, senderAddress, orderType, code, senderLng, senderLat);

        return message;
    }


    /**
     * 用户的订单(有序)
     */
    private List<VOrder> sortOrders(double distance, List<CollectOrder> list, double lng, double lat) {
        if (list == null || list.size() == 0) return null;

        List<VOrder> vOrders = new ArrayList<>();

        for (CollectOrder cOrder : list) {
            Double senderLat = cOrder.getSenderLat();
            Double senderLng = cOrder.getSenderLng();

            if (senderLat == null || senderLng == null) {
                //todo 经纬度为空不放入list中
                if (distance == 0d) {
                    VOrder vOrder = new VOrder(cOrder, null, null, VOrder.Type.COLLECT);
                    vOrders.add(vOrder);
                }
                continue;
            }

            double dis = DistanceUtil.getDistance(senderLng, senderLat, lng, lat);
            if (dis <= distance) {
                VOrder vOrder = new VOrder(cOrder, null, dis, VOrder.Type.COLLECT);
                vOrders.add(vOrder);
            }
        }
        Collections.sort(vOrders);

        return vOrders;
    }

    /**
     * 如果city 没有订单 去查询全省的订单
     */
    private Set<Long> getUnRobOrders(String provinceCode, String cityCode) {
        Set<Long> sets = Sets.newHashSet();

        Set<Long> ordersCity = redisService.getRedisByCityCode(cityCode);
        sets.addAll(ordersCity);
        if (sets.isEmpty()) return sets;

        ResponseDTO responseDTO = regionService.getCitiesByProvince(provinceCode);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) {
            logger.info("find a no city province from redis, provinceCode is {}", provinceCode);
            return sets;
        }

        List<String> list = responseDTO.getList();
        if (list == null || list.size() == 0) return sets;

        for (String code : list) {
            Set<Long> temps = redisService.getRedisByCityCode(code);
            sets.addAll(temps);
        }

        return sets;
    }

    /**
     * ID 转Obj
     *
     * @param sets
     * @return
     */
    private List<CollectOrder> getOrderByOIds(Set<Long> sets) {
        if (sets == null || sets.size() == 0) return null;

        List<CollectOrder> orders = new ArrayList<CollectOrder>();
        for (Long id : sets) {
            CollectOrder cOrder = redisService.getCorderByCid(id);
            if (cOrder != null) orders.add(cOrder);
        }
        return orders;
    }


    /**
     * @param mailNo
     * @return true 已经存在 false 不存在
     */
    private boolean isExistByMailNo(String mailNo) {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("expressNo", mailNo);

        int i = collectOrderMapper.countByFilters(filters);
        if (i > 0) return true;

        return false;
    }

    public void saveOrderOperation(CollectOrder order) {
        OrderOperation orderOperation = OrderOperationConvert.converByCollect(order);
        if (orderOperation == null || orderOperation.getUserId() == null) {
            logger.info("no courier operator message:{}", orderOperation);
            return;
        }
        orderOperationService.save(orderOperation);
    }

    /**
     * 接单接口 (接单与无人接单)
     *
     * @param order
     */
    private void sendMQMessage(RequestOrder order, Enumerate.MQType mqType) {
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(order);
            if (Enumerate.MQType.COLLECTACCEPT.getCode().equals(mqType.getCode())) {
                collectAcceptMQClient.send(mqPacket);
                logger.info("courier pick up, message is {}", GsonUtil.toJson(order));
                return;
            }
            if (Enumerate.MQType.COLLECTACCEPTNO.getCode().equals(mqType.getCode())) {
                collectNoAcceptMQClient.send(mqPacket);
                logger.info("no courier pick up., message is {}", GsonUtil.toJson(order));
                return;
            }

        } catch (Exception e) {
            logger.error("send  by mq error, error is {},message:{}", GsonUtil.toJson(order), e.getMessage());
        }
    }

    private void sendMQMessage(JPush jPush) {
        logger.debug("push Message:{}", jPush.toJson());
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(jPush);
            pushMQClient.send(mqPacket);
            logger.info("send  by mq, message is {}", GsonUtil.toJson(jPush));
        } catch (Exception e) {
            logger.error("send  by mq error,message:{}", e.getMessage());
        }

    }

    /**
     * 有单取件MQ  包体里是 CollectOrder  对象 因为需要转为为两个对象 JGUpdateInfo和JGTakingModelReq
     *
     * @param takingModelReq
     */
    public void sendMQMessageOrder(JGTakingModelReq takingModelReq) {
        logger.debug("message:{}", takingModelReq.toJson());
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(takingModelReq);
            collectPickUpMQClient.send(mqPacket);
            logger.info("send order by mq, message is {}", takingModelReq.toJson());
        } catch (Exception e) {
            logger.error("send order by mq error, error is {},message:{}", takingModelReq.toJson(), e.getMessage());
        }
    }

    /**
     * 无单取件MQ
     * @param takingModelReq
     */
    public void sendMQMessage(JGTakingModelReq takingModelReq) {
        logger.debug("message:{}", takingModelReq.toJson());
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(takingModelReq);
            collectPickUpByNoMQClient.send(mqPacket);
            logger.info("send no order by mq, message is {}", takingModelReq.toJson());
        } catch (Exception e) {
            logger.error("send no order by mq error, error is {},message:{}", takingModelReq.toJson(), e.getMessage());
        }
    }


    public String translateToAddress(CollectOrder order) {
        String senderProvince = order.getSenderProvinceName();
        String senderCity = order.getSenderCityName();
        String senderArea = order.getSenderAreaName();
        String senderAddress = order.getSenderAddress();
        //return VOrderConvert.buildAddress(cacheUtil, senderProvince, senderCity, senderArea, senderAddress);
        return VOrderConvert.buildAddress(senderProvince, senderCity, senderArea, senderAddress);
    }


    /**
     * 实时读取接口 B+
     */
    private ResponseMailNoResult findNewMailNoByRT(Interfaces interfaces, Sender sender, Receiver receiver, String clientId, String md5Secret) throws Exception {
        RequestOrder order = new RequestOrder(clientId, (new Uuid()).toString(), clientId, sender, receiver);

        order.setSpecial(Enumerate.GoodSpecial.normal.getCode().toString());
        order.setOrderType(Enumerate.MailNoOrderType.normal.getCode());
        return interfaces.findNewMailNo(mailnoUrl, order, md5Secret);
    }
    /**
     * 实时读取接口 C+
     */
    private ResponseMailNoModelCResult findNewMailNoByCModelRT(Interfaces interfaces, Sender sender, Receiver receiver,
                                                               String clientId, String md5Secret, String orderId, String acceptCanvassEmpCode,String orderChannelCode) throws Exception {
        if (orderId == null || orderChannelCode == null) {
            orderId = acceptCanvassEmpCode + "-" + new Date().getTime();
            orderChannelCode = defaultSourceName;
        }
        RequestOrderCModel order = new RequestOrderCModel(clientId, orderId, clientId, sender, receiver, acceptCanvassEmpCode, orderChannelCode);
        order.setSpecial(Enumerate.GoodSpecial.normal.getCode().toString());
        order.setOrderType(Enumerate.MailNoOrderType.app.getCode());
        return interfaces.findNewMailNOCModel(mailnoCPlusUrl, order, md5Secret);
    }
    /**
     * 实时读取 密码接口
     */
    private String findSecretKeyByRT(Interfaces interfaces, String clientId) throws Exception {
        String md5Secret = "";
        RequestSecret requestSecret = new RequestSecret(clientId);
        ResponseSecret responseSecret = interfaces.findSecretKey(secretUrl, requestSecret, secretmd5);
        if (responseSecret == null) return md5Secret;
        md5Secret = responseSecret.getSecretKey();
        cacheUtil.putResouce2Redis(buildSecretMailNoKey(clientId), md5Secret);
        return md5Secret;
    }


    public ResponseDTO existByMailNo(String mailNo) {
        // todo 判断面单号是否有效 存在就无效
        if (isExistByMailNo(mailNo)) return new ResponseDTO(CodeEnum.C1096);
        // todo 如果不是电子面单 再到金刚验证下

        if (isExistDockTrace(mailNo)) {
            return new ResponseDTO(CodeEnum.C1096);
        }
        return new ResponseDTO(CodeEnum.C1000);
    }

    public List<CollectOrder> findForSync(Integer pageNo, Integer pageSize) {
        return collectOrderMapper.findForSync(pageNo,pageSize);
    }
    public int findForSyncTotalNum(){
        return collectOrderMapper.findForSyncTotalNum();
    }

    public void batchUpdate(List<CollectOrder> orders) {
        if (CollectionUtils.isEmpty(orders)) return;
        collectOrderMapper.updateBatch(orders);
    }

    public CollectOrder findCollect(Long userId, String mailNo) {
        return collectOrderMapper.findCollect(userId, mailNo);
    }

    public ResponseDTO fuzzyCollect(String uuid, String mailNo) {
        if (StringUtils.isEmpty(mailNo)) return new ResponseDTO(CodeEnum.C1034);
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;

        long userId = (long) responseDTO.getT2();

        List<CollectOrder> list = collectOrderMapper.fuzzyCollect(userId, mailNo);
        return new ResponseDTO(CodeEnum.C1000, list, null);
    }

    public int[] backup(Date date) {
        int backup = collectOrderMapper.backup(date);
        int delete = collectOrderMapper.deleteBackup(date);
        return new int[]{backup, delete};
    }

    public int[] history(Date date) {
        int backup = collectOrderMapper.history(date);
        int delete = collectOrderMapper.deleteHistory(date);
        return new int[]{backup, delete};
    }

    public Page<VCollectData> findGrabProvincePage(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer count = collectOrderMapper.countGrabProvince(beginTime, endTime);
        Integer index = (pageNo - 1) * pageSize;
        List<VCollectData> vCollectDatas = collectOrderMapper.listGrabProvince(beginTime, endTime, index, pageSize);
        return new Page<>(pageNo, pageSize, vCollectDatas, count);
    }
    public Collection<VCollectData> listGrabProvince(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer index = (pageNo - 1) * pageSize;
        return collectOrderMapper.listGrabProvince(beginTime, endTime, index, pageSize);
    }
    public Integer countGrabProvince(String beginTime, String endTime) {
        return collectOrderMapper.countGrabProvince(beginTime, endTime);
    }

    public Page<VCollectData> findGrabOrgPage(String beginTime, String endTime, Integer pageNo, Integer pageSize){
        Integer count = collectOrderMapper.countGrabOrg(beginTime, endTime);
        Integer index = (pageNo - 1) * pageSize;
        List<VCollectData> vCollectDatas = collectOrderMapper.listGrabOrg(beginTime, endTime, index, pageSize);
        return new Page<>(pageNo, pageSize, vCollectDatas, count);
    }
    public Collection<VCollectData> listGrabOrg(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer index = (pageNo - 1) * pageSize;
        return collectOrderMapper.listGrabOrg(beginTime, endTime, index, pageSize);
    }
    public Integer countGrabOrg(String beginTime, String endTime) {
        return collectOrderMapper.countGrabOrg(beginTime, endTime);
    }

    public Page<VCollectData> findGrabGatherPage(String beginTime, String endTime, Integer pageNo, Integer pageSize){
        Integer count = collectOrderMapper.countGrabGather(beginTime, endTime);
        Integer index = (pageNo - 1) * pageSize;
        List<VCollectData> vCollectDatas = collectOrderMapper.listGrabGather(beginTime, endTime, index, pageSize);
        return new Page<>(pageNo, pageSize, vCollectDatas, count);
    }
    public Collection<VCollectData> listGrabGather(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer index = (pageNo - 1) * pageSize;
        return collectOrderMapper.listGrabGather(beginTime, endTime, index, pageSize);
    }
    public Integer countGrabGather(String beginTime, String endTime) {
        return collectOrderMapper.countGrabGather(beginTime, endTime);
    }

    public Page<VCollectData> findWaitCollectPage(String beginTime, String endTime, Integer pageNo, Integer pageSize){
        Integer count = collectOrderMapper.countWaitCollect(beginTime, endTime);
        Integer index = (pageNo - 1) * pageSize;
        List<VCollectData> vCollectDatas = collectOrderMapper.listWaitCollect(beginTime, endTime, index, pageSize);
        return new Page<>(pageNo, pageSize, vCollectDatas, count);
    }
    public Collection<VCollectData> listWaitCollect(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer index = (pageNo - 1) * pageSize;
        return collectOrderMapper.listWaitCollect(beginTime, endTime, index, pageSize);
    }
    public Integer countWaitCollect(String beginTime, String endTime) {
        return collectOrderMapper.countWaitCollect(beginTime, endTime);
    }

    public Page<VCollectData> findWaitGrabPage(String beginTime, String endTime, Integer pageNo, Integer pageSize){
        Integer count = collectOrderMapper.countWaitGrab(beginTime, endTime);
        Integer index = (pageNo - 1) * pageSize;
        List<VCollectData> vCollectDatas = collectOrderMapper.listWaitGrab(beginTime, endTime, index, pageSize);
        return new Page<>(pageNo, pageSize, vCollectDatas, count);
    }
    public Collection<VCollectData> listWaitGrab(String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Integer index = (pageNo - 1) * pageSize;
        return collectOrderMapper.listWaitGrab(beginTime, endTime, index, pageSize);
    }
    public Integer countWaitGrab(String beginTime, String endTime) {
        return collectOrderMapper.countWaitGrab(beginTime, endTime);
    }
}
