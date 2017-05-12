package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.constant.ParamKey;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.model.jinGang.*;
import com.courier.commons.mq.client.base.MQSendClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.CommonUtil;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.UserBean;
import com.courier.core.convert.DeliveryOrderConvert;
import com.courier.commons.mq.packet.MqPacketConvert;
import com.courier.core.convert.OrderOperationConvert;
import com.courier.core.jingang.Interfaces;
import com.courier.core.jingang.convert.JGSignReqConvert;
import com.courier.core.mq.DeliverySignMQClient;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.util.SignResultResp;
import com.courier.db.dao.DeliveryOrderMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.OrderOperation;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class DeliveryOrderService extends BaseManager<DeliveryOrder> {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryOrderService.class);
    @Autowired
    private DeliveryOrderMapper deliveryOrderMapper;

    @Override
    public DeliveryOrder getEntity() {
        return new DeliveryOrder();
    }

    @Override
    public BaseMapper<DeliveryOrder> getBaseMapper() {
        return deliveryOrderMapper;
    }


    @Autowired
    UserService userService;

    @Autowired
    DeliverySignMQClient deliverySignMQClient;

    @Autowired
    UserLocationService locationService;

    @Autowired
    OrderOperationService orderOperationService;

    @Autowired
    BranchService branchService;

    @Autowired
    CacheUtil cacheUtil;
    @Autowired
    SyncJGDeliveryOrderService syncJGDeliveryOrderService;

    private MQSendClient sendClient;

    @Autowired
    RedisService redisService;
    @Value("${baidu.gps.url}")
    private String baiduUrl;            //百度接口地址
    @Value("${baidu.gps.regEx}")
    private String regEx;
    @Value("${jingang.mailNoDetail.info.url}")
    private String jg_mailno_url;       //金刚运单信息


    @Value("${jingang.docktrace.url}")
    private String jg_docktrace_url;


    @Value("${jingang.mailNoDetail.info.md5}")
    private String mailNoDetailMd5;

    @Value("${query.other.delivery.times}")
    private Integer queryOtherTimes;


    public int batchInsert(List<DeliveryOrder> orders) {
        if (orders == null || orders.size() <= 0) return 1;
        return deliveryOrderMapper.batchInsert(orders);
    }

    public int batchUpdateByUidAndMailNo(ArrayList<DeliveryOrder> orders) {
        if (orders == null || orders.size() <= 0) return 1;
        return deliveryOrderMapper.batchUpdateByUidAndMailNo(orders);
    }

    public int batchDeleteByMailNoAndStatus(List<String> mailNos) {
        if (mailNos == null || mailNos.size() <= 0) return 1;
        return deliveryOrderMapper.batchDeleteByMailNoAndStatus(mailNos);
    }

    /**
     * 多条件查询  (pageNo PAGE_SIZE 不传默认查所有）
     */
    public ResponseDTO findByFilters(String uuid, Map<String, Object> filters, Integer pageNo, Integer pageSize) throws Exception {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        Long userId = (Long) responseDTO.getT2();
        Page<DeliveryOrder> page = findByFilters(filters, pageNo, pageSize, userId);

        return new ResponseDTO(CodeEnum.C1000, page.getResult(), page.getTotalCount());
    }


    public Page<DeliveryOrder> getSignData(Long userId, String mailNo, Date beginT, Date endT, Integer pageNo, Integer pageSize) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("expressNo", mailNo);
        filters.put("flag", CommonEnum.VALID.getCode());
        filters.put("timeField", Enumerate.TimeFields.signTime.getName());
        filters.put("status", Arrays.asList(Enumerate.DeliveryOrderStatus.normalSign.getType()));
        filters.put("sortName", "signTime");
        filters.put("sortType", Enumerate.SortType.DESC.getType());
        if(beginT != null) filters.put("beginT", DateUtil.toSeconds(beginT));
        if(endT != null) filters.put("endT", DateUtil.toSeconds(endT));

        return findByFilters(filters, pageNo, pageSize, userId);
    }

    public Page<DeliveryOrder> getHistorySignData(Long userId, String mailNo, String beginTime, String endTime, Integer pageNo, Integer pageSize) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("expressNo", mailNo);
        filters.put("flag", CommonEnum.VALID.getCode());
        filters.put("status", Arrays.asList(Enumerate.DeliveryOrderStatus.normalSign.getType()));
        filters.put("sortName", "signTime");
        filters.put("sortType", Enumerate.SortType.DESC.getType());
        if(beginTime != null) filters.put("beginT", DateUtil.getBeginT(DateUtil.strToDate(beginTime, "yyyy-MM-dd")));
        if(endTime != null) filters.put("endT", DateUtil.getEndT(DateUtil.strToDate(endTime, "yyyy-MM-dd")));

        return findHistoryByFilters(filters, pageNo, pageSize, userId);
    }

    public Page<DeliveryOrder> getSendingData(Long userId, String mailNo, Integer pageNo, Integer pageSize) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("expressNo", mailNo);
        filters.put("flag", CommonEnum.VALID.getCode());
        filters.put("status", Arrays.asList(Enumerate.DeliveryOrderStatus.sending.getType()));
        filters.put("sortName", "jGCreateTime");
        filters.put("sortType", Enumerate.SortType.DESC.getType());

        return findByFilters(filters, pageNo, pageSize, userId);
    }

    /**
     * 统计
     */
    public int countByFilters(Map<String, Object> filters) {
        return deliveryOrderMapper.countByFilters(filters);
    }



    /**
     * 单件签收处理
     */
    public ResponseDTO sign(String uuid, DeliveryOrder order) throws Exception {
        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();

        return signDeal(order, userBean, uuid);
    }

    /**
     * 批量操作签收
     */
    public ResponseDTO batchSign(String uuid, List<DeliveryOrder> deliveryOrders) throws Exception {
        if (deliveryOrders == null || deliveryOrders.size() == 0)
            return new ResponseDTO(CodeEnum.C1034);

        ResponseDTO responseDTO = userService.getUserBeanByUuid(uuid);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();

        List<SignResultResp> resultList = new LinkedList<>();
        for (DeliveryOrder order : deliveryOrders) {
            ResponseDTO signResp = signDeal(order, userBean, uuid);
            resultList.add(new SignResultResp(signResp.getCodeEnum(), order));
        }

        return new ResponseDTO(CodeEnum.C1000, resultList, null);
    }

    /**
     * 通过mailNo查找轨迹信息
     *
     * @param mailNo
     * @return
     */
    public ResponseDTO findDockTrace(String mailNo) {
        if (StringUtils.isEmpty(mailNo)) return new ResponseDTO(CodeEnum.C1034);
        Interfaces interfaces = new Interfaces();
        List<DockResult> list = interfaces.dockTrace2(jg_docktrace_url, mailNo);
        return new ResponseDTO(CodeEnum.C1000, null, list);
    }

    public void batchDelete(List<DeliveryOrder> deletes) {
        deliveryOrderMapper.batchDelete(deletes);
    }

    public DeliveryOrder findDelivery(Long userId, String mailNo){
        return deliveryOrderMapper.findDelivery(userId, mailNo);
    }

    /********************************** begin private method ****************************************************/




    /**
     * 封装包体 发送mq
     */
    private void sendMQMessage(JGSignReq jgSignReq) {
        logger.debug("message:{}", jgSignReq.toJson());
        try {
            MQPacket mqPacket = MqPacketConvert.buildMqPacket(jgSignReq);

            deliverySignMQClient.send(mqPacket);

            logger.info("send deliveryOrder by mq, message is {}", jgSignReq.toJson());
        } catch (Exception e) {
            logger.error("send deliveryOrder by mq error, error is {}", jgSignReq.toJson(), e.getMessage());
        }
    }

    /**
     * sign step 1.1; 参数校验
     */
    private ResponseDTO signDeal(DeliveryOrder order, UserBean userBean, String uuid) {
        //  todo  参数校验
        if (order == null) return new ResponseDTO(CodeEnum.C1034);
        Long oId = order.getId();
        if (oId == null || oId == 0l) return new ResponseDTO(CodeEnum.C1034);
        DeliveryOrder dbOrder = get(oId);
        if (dbOrder == null) return new ResponseDTO(CodeEnum.C1036);

        // 签收状态 必须为（正常签收、异常签收）
        Byte orderStatus = order.getOrderStatus();
        if (!orderStatus.equals(Enumerate.DeliveryOrderStatus.normalSign.getType()) && !orderStatus.equals(Enumerate.DeliveryOrderStatus.unusualSign.getType()))
            return new ResponseDTO(CodeEnum.C1036);

        orderStatus = dbOrder.getOrderStatus();
        //  问题件 无效件提示:不能签收
        if (!dbOrder.getFlag() || Enumerate.DeliveryOrderStatus.unusualSign.getType().equals(orderStatus))
            return new ResponseDTO(CodeEnum.C1097, null, order);

        // 已正常签收 /自提点签收 不能再次签收
        if (Enumerate.DeliveryOrderStatus.normalSign.getType().equals(orderStatus) || Enumerate.DeliveryOrderStatus.shopSign.getType().equals(orderStatus))
            return new ResponseDTO(CodeEnum.C2012);

        //  todo  业务处理
        String cacheKey = String.format(CacheConstant.SIGN_LOCK_KEY, userBean.getUser().getId(), dbOrder.getId());
        if (cacheUtil.putData2RedisCache(cacheKey, 60, 1)) { //redis存放60秒
            try {
                dbOrder = DeliveryOrderConvert.sign(order, dbOrder);
                doSign(dbOrder, userBean, uuid);
            } finally {
                cacheUtil.invalidByRedis(cacheKey);
            }
        }else {
            logger.error("repeat sign delivery order, jobNo:{}, order:{}", userBean.getUser().getJobNo(), order.toJson());
            return new ResponseDTO(CodeEnum.C1007);
        }

        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * sign step 1.2; 逻辑处理
     */
    private void doSign(DeliveryOrder dbOrder, UserBean userBean, String uuid) {
        User user = userBean.getUser();
        Long userId = user.getId();
        UserLoginRecord record = userBean.getUserLoginRecord();
        Long orderUserId = dbOrder.getUserId();
        // todo  更新db
        dbOrder.setUserId(userId);
        update(dbOrder);
        // todo 存入操作表
        Enumerate.OperationType operationType = Enumerate.OperationType.SIGN;
        Byte orderStatus = dbOrder.getOrderStatus();
        if (Enumerate.DeliveryOrderStatus.unusualSign.getType().equals(orderStatus))
            operationType = Enumerate.OperationType.SIGN_FAIL;
        OrderOperation orderOperation = OrderOperationConvert.convertByDelivery(dbOrder, userId, operationType);
        orderOperationService.save(orderOperation);
        //todo 更新redis的homePage
        redisService.invalidHomePageAndList(userId, orderUserId, operationType);
        // todo 发送mq
        JGSignReq transferSignModelReq = JGSignReqConvert.convertObj(dbOrder, user, record, uuid);
        sendMQMessage(transferSignModelReq);
    }


    private Page<DeliveryOrder> findByFilters(Map<String, Object> filters, Integer pageNo, Integer pageSize, Long userId) {
        if (filters == null) filters = new HashMap<>();
        int total = 0;
        filters.put("userId", userId);
        filters.put("flag", CommonEnum.VALID.getCode());

        if (pageNo != null && pageSize != null) {
            if (pageNo < 1) pageNo = 1;
            filters.put(ParamKey.Page.NUM_KEY, (pageNo - 1) * pageSize);
            filters.put(ParamKey.Page.SIZE_KEY, pageSize);
            total = deliveryOrderMapper.countByFilters(filters);
        }
        List<DeliveryOrder> list = deliveryOrderMapper.findByFilters(filters);
        list = DeliveryOrderConvert.buildListRegionName(cacheUtil, list);

        return new Page<>(list, total);
    }

    private Page<DeliveryOrder> findHistoryByFilters(Map<String, Object> filters, Integer pageNo, Integer pageSize, Long userId) {
        if (filters == null) filters = new HashMap<>();
        int total = 0;
        filters.put("userId", userId);
        filters.put("flag", CommonEnum.VALID.getCode());

        if (pageNo != null && pageSize != null) {
            if (pageNo < 1) pageNo = 1;
            filters.put(ParamKey.Page.NUM_KEY, (pageNo - 1) * pageSize);
            filters.put(ParamKey.Page.SIZE_KEY, pageSize);
            total = deliveryOrderMapper.countHistoryByFilters(filters);
        }
        List<DeliveryOrder> list = deliveryOrderMapper.findHistoryByFilters(filters);
        list = DeliveryOrderConvert.buildListRegionName(cacheUtil, list);

        return new Page<>(list, total);
    }


}