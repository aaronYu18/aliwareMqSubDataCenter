package com.courier.core.service;

import com.courier.commons.model.RealNameSystemRequest;
import com.courier.commons.model.jinGang.JGTakingModelReq;
import com.courier.commons.model.jinGang.JGVerificationInfo;
import com.courier.commons.mq.client.base.MQSendClient;
import com.courier.commons.mq.packet.MQPacket;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.authBean.BaseAuthBean;
import com.courier.core.authBean.GPOAuthBean;
import com.courier.core.authBean.ZhejiangAuthBean;
import com.courier.core.cache.UserBean;
import com.courier.core.cache.UserLocation;
import com.courier.core.convert.CollectOrderConvert;
import com.courier.core.convert.MqPacketConvert;
import com.courier.core.jingang.Interfaces;
import com.courier.core.jingang.convert.JGTakingModelReqConvert;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.CollectOrderMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.UserLoginRecord;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aaron_yu on 15/5/10.
 * 收件信息
 */
@Service
@Transactional
public class CollectOrderAuthService extends BaseManager<CollectOrder> {

    private static final Logger logger = LoggerFactory.getLogger(CollectOrderAuthService.class);
    @Autowired
    private CollectOrderMapper collectOrderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CollectOrderService collectOrderService;
    @Autowired
    private UserLocationService userLocationService;

    @Value("${baidu.gps.url}")
    private String baiduUrl;
    @Value("${baidu.gps.regEx}")
    private String regEx;

    @Value("${jg.fm.auth.url}")
    private String jgFmAuthUrl;
    @Value("${jg.phone.auth.url}")
    private String jgPhoneAuthUrl;

    @Override
    public CollectOrder getEntity() {
        return new CollectOrder();
    }

    @Override
    public BaseMapper<CollectOrder> getBaseMapper() {
        return collectOrderMapper;
    }

    private ResponseDTO pickUp(String uuid, CollectOrder order, BaseAuthBean baseAuthBean,
                               Enumerate.PlatformType platformType, MQSendClient sendClient) throws Exception {
        boolean bPickUp = true;

        //todo 校验参数(有单)
        ResponseDTO responseDTO = fileterData(uuid, order, baseAuthBean, bPickUp);
        if(!checkResponseOK(responseDTO))
            return responseDTO;

        UserBean userBean = (UserBean)BeanUtils.cloneBean(responseDTO.getT2());

        //todo 校验取件状态
        CollectOrder orderDb = collectOrderService.get(order.getId());
        responseDTO = checkOrderStatus(orderDb);
        if(!checkResponseOK(responseDTO))
            return responseDTO;

        orderDb = CollectOrderConvert.convert(order, orderDb, userBean.getUser().getId(),
                null, Enumerate.CollectOrderStatus.COLLECTED);
        orderDb = CollectOrderConvert.initRegion(cacheUtil, orderDb);
        saveOrUpdateCollectOrderAndUpdateOperation(orderDb, bPickUp);

        String updateInfoClient = collectOrderService.getClientIdBySourceId(orderDb.getSource());
        UserLoginRecord userLoginRecord = userBean.getUserLoginRecord();
        JGTakingModelReq takingModelReq = JGTakingModelReqConvert.convertObj(orderDb, userBean.getUser(), uuid, platformType.getType(), userLoginRecord, updateInfoClient);

        //todo 取件接口
        collectOrderService.sendMQMessageOrder(takingModelReq);
        redisService.invalidHomePageAndList(userBean.getUser().getId(), null, Enumerate.OperationType.COLLECT);
        sendMQMessageByAuth(sendClient, baseAuthBean, orderDb, userBean);

        return new ResponseDTO(CodeEnum.C1000, null, orderDb.getId());
    }

    private ResponseDTO pickUpWithoutNO(String uuid, CollectOrder order, BaseAuthBean baseAuthBean,
                                        Enumerate.PlatformType platformType, MQSendClient sendClient) throws Exception {
        boolean bPickUp = false;

        //todo 校验参数(无单)
        ResponseDTO responseDTO = fileterData(uuid, order, baseAuthBean, bPickUp);
        if(!checkResponseOK(responseDTO))
            return responseDTO;

        UserBean userBean = (UserBean)responseDTO.getT2();

        order.setUserId(userBean.getUser().getId());
        order.setOrderStatus(Enumerate.CollectOrderStatus.COLLECTED.getCode());
        order.setOrderType(Enumerate.CollectOrderType.NOORDER.getCode());

        order = CollectOrderConvert.initRegion(cacheUtil, order);
        String fullyAddress = collectOrderService.translateToAddress(order);
        order = CollectOrderConvert.fillGps(order, baiduUrl, regEx, fullyAddress);
        order = CollectOrderConvert.initWithOutOrder(order);

        saveOrUpdateCollectOrderAndUpdateOperation(order, bPickUp);

        UserLoginRecord userLoginRecord = userBean.getUserLoginRecord();
        JGTakingModelReq takingModelReq = JGTakingModelReqConvert.convertObj(order, userBean.getUser(), uuid, platformType.getType(), userLoginRecord, "");

        collectOrderService.sendMQMessage(takingModelReq);

        redisService.invalidHomePageAndList(userBean.getUser().getId(), null, Enumerate.OperationType.NOCOLLECT);
        sendMQMessageByAuth(sendClient, baseAuthBean, order, userBean);

        return new ResponseDTO(CodeEnum.C1000, null, order.getId());
    }

    /**
     * 赋码实名认证
     * @param uuid
     * @param fm
     * @return
     */
    public ResponseDTO fmAuth(String uuid, String fm) {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if(responseDTO.getCodeEnum()!= CodeEnum.C1000) return new ResponseDTO(responseDTO.getCodeEnum());
        Interfaces interfaces = new Interfaces();
        JGVerificationInfo jgVerificationInfo = interfaces.findByFm(jgFmAuthUrl, fm);
        if (jgVerificationInfo == null) return new ResponseDTO(CodeEnum.C5002, null, null);
        return new ResponseDTO(CodeEnum.C1000, null, jgVerificationInfo);
    }

    /**
     * 手机号实名认证
     * @param uuid
     * @param phone
     * @return
     */
    public ResponseDTO phoneAuth(String uuid, String phone) {
        ResponseDTO responseDTO = userService.getUserIdByUuid(uuid);
        if(responseDTO.getCodeEnum()!= CodeEnum.C1000) return new ResponseDTO(responseDTO.getCodeEnum());
        Interfaces interfaces = new Interfaces();
        JGVerificationInfo jgVerificationInfo = interfaces.findByTel(jgFmAuthUrl, phone);
        if (jgVerificationInfo == null) return new ResponseDTO(CodeEnum.C5002, null, null);
        return new ResponseDTO(CodeEnum.C1000, null, jgVerificationInfo);
    }

    /**
     * 有单取件（浙江）
     * @param uuid
     * @param collectOrder
     * @param zhejiangAuthBean
     * @param platformType
     * @param zhejiangClient
     * @return
     * @throws Exception
     */
    public ResponseDTO pickUpByZheJiang(String uuid, CollectOrder collectOrder, ZhejiangAuthBean zhejiangAuthBean,
                                        Enumerate.PlatformType platformType, MQSendClient zhejiangClient) throws Exception {
        return pickUp(uuid, collectOrder, zhejiangAuthBean, platformType, zhejiangClient);
    }

    /**
     * 有单取件（全国）
     * @param uuid
     * @param collectOrder
     * @param gpoAuthBean
     * @param platformType
     * @param countryClient
     * @return
     * @throws Exception
     */
    public ResponseDTO pickUpByCountry(String uuid, CollectOrder collectOrder, GPOAuthBean gpoAuthBean,
                                       Enumerate.PlatformType platformType, MQSendClient countryClient) throws  Exception {
        return pickUp(uuid, collectOrder, gpoAuthBean, platformType, countryClient);
    }

    /**
     * 无单取件（浙江）
     * @return
     * @throws Exception
     */
    public ResponseDTO pickUpWithoutNOByZheJiang(String uuid, CollectOrder collectOrder, ZhejiangAuthBean zhejiangAuthBean,
                                                 Enumerate.PlatformType platformType, MQSendClient zhejiangClient) throws Exception {
        return pickUpWithoutNO(uuid, collectOrder, zhejiangAuthBean, platformType, zhejiangClient);
    }

    /**
     * 无单取件（全国）
     * @return
     * @throws Exception
     */
    public ResponseDTO pickUpWithoutNOByCountry(String uuid, CollectOrder collectOrder, GPOAuthBean gpoAuthBean,
                                                Enumerate.PlatformType platformType, MQSendClient countryClient) throws Exception {
        return pickUpWithoutNO(uuid, collectOrder, gpoAuthBean, platformType, countryClient);
    }

    private ResponseDTO fileterData(String uuid, CollectOrder order, BaseAuthBean baseAuthBean, boolean checkPickUp) throws Exception{
        //todo 校验参数
        ResponseDTO responseDTO = checkParam(uuid, order, baseAuthBean, checkPickUp);
        if(!checkResponseOK(responseDTO))
            return responseDTO;

        //todo 校验面单号
        responseDTO = collectOrderService.existByMailNo(order.getMailNo());
        if(!checkResponseOK(responseDTO)) {
            return responseDTO;
        }

        //todo 校验用户信息
        responseDTO = userService.getUserBeanByUuid(uuid);
        if(!checkResponseOK(responseDTO))
            return responseDTO;
        UserBean userBean = (UserBean)responseDTO.getT2();

        return new ResponseDTO(CodeEnum.C1000, null, userBean);
    }

    private boolean checkResponseOK(ResponseDTO responseDTO){
        if(CodeEnum.C1000 == responseDTO.getCodeEnum())
            return true;
        return false;
    }

    private void sendMQMessageByAuth(MQSendClient sendClient, BaseAuthBean baseAuthBean,
                                     CollectOrder collectOrder, UserBean userBean){
        RealNameSystemRequest realNameSystemRequest = convert2Realname(baseAuthBean, collectOrder);
        if(StringUtils.isEmpty(realNameSystemRequest.getJd()) ||
                StringUtils.isEmpty(realNameSystemRequest.getWd())){
            UserLocation userLocation = userLocationService.getCurrentGPS(userBean.getUser());
            if (userLocation != null) {
                realNameSystemRequest.setJd(convert2Str(userLocation.getLng()));
                realNameSystemRequest.setWd(convert2Str(userLocation.getLat()));
            }

            if(StringUtils.isEmpty(realNameSystemRequest.getJd()))
                realNameSystemRequest.setJd("0");
            if(StringUtils.isEmpty(realNameSystemRequest.getWd()))
                realNameSystemRequest.setWd("0");
        }
        MQPacket packet = MqPacketConvert.buildMqPacket(realNameSystemRequest);
        sendClient.send(packet);
    }

    private RealNameSystemRequest convert2Realname(BaseAuthBean baseAuthBean, CollectOrder collectOrder){
        RealNameSystemRequest request = new RealNameSystemRequest();
        request.setTypeOfUser(null);
        request.setSex(null);
        request.setCodeOfWeixin("");
        request.setPhone(baseAuthBean.getSenderPhone());
        request.setYdh(baseAuthBean.getExpressNo());
        request.setJjsj(baseAuthBean.getSendTime());
        request.setOtherOfAddress(baseAuthBean.getSenderAddress());
        request.setJjgs("");
        request.setName(baseAuthBean.getSenderName());
        request.setMobileNumber(baseAuthBean.getSenderMobile());
        request.setSjdz(baseAuthBean.getReceiverAddress());
        request.setSjgs("");
        request.setSjr(baseAuthBean.getReceiverName());
        request.setSjdh(baseAuthBean.getReceiverMobile());
        request.setSjy(baseAuthBean.getJobNo());
        request.setProvinceOfAddress(baseAuthBean.getSenderProvince());
        request.setMdd(baseAuthBean.getReceiverProvince());
        request.setJfkhbm("");
        request.setFkfs("");
        request.setDsdk(convert2Str(baseAuthBean.getCollectionMoney()));
        request.setTjw(baseAuthBean.getGoodsName());
        request.setJfzl(convert2Str(baseAuthBean.getWeight()));
        request.setJs("");
        request.setYf(convert2Str(baseAuthBean.getFreightMoney()));
        request.setCountyOfAddress(StringUtils.isEmpty(baseAuthBean.getSenderArea()) ? baseAuthBean.getSenderCity() : baseAuthBean.getSenderArea());
        request.setMdddq(StringUtils.isEmpty(baseAuthBean.getReceiverArea()) ? baseAuthBean.getReceiverCity() : baseAuthBean.getReceiverArea());
        request.setCityOfAddress(baseAuthBean.getSenderCity());
        request.setMddcs(baseAuthBean.getReceiverCity());
        request.setNumberOfID(baseAuthBean.getCertificateNo());
        request.setTypeOfID(convert2Str(baseAuthBean.getCertificateType()));
        request.setSjrzjhm("");
        request.setSjrzjzl("");
        request.setJd(convert2Str(baseAuthBean.getLng()));
        request.setWd(convert2Str(baseAuthBean.getLat()));
        request.setKdwddm(baseAuthBean.getOrgCode());
        request.setPseudocodeOfID(baseAuthBean.getFm());
        if(baseAuthBean instanceof GPOAuthBean)
            request.setType("0");
        else if(baseAuthBean instanceof ZhejiangAuthBean) {
            ZhejiangAuthBean bean = (ZhejiangAuthBean)baseAuthBean;
            request.setType("1");
            request.setPic(convertByte2Str(bean.getPicture()));
            request.setPicLen(bean.getPictureSize());
        }
        return request;
    }

    private String convertByte2Str(byte[] pic){
        StringBuilder sb = new StringBuilder();
        sb.append(new Base64().encodeAsString(pic));
        return sb.toString();
    }

    private String convert2Str(Object object){
        if(object != null)
            return String.valueOf(object);
        return "";
    }

    private ResponseDTO checkParam(String uuid, CollectOrder order, BaseAuthBean baseAuthBean, boolean checkPickUp){
        if(checkPickUp && null == order.getId())
            return new ResponseDTO(CodeEnum.C1034);

        if(StringUtils.isEmpty(uuid) || null == order || StringUtils.isEmpty(order.getMailNo())
                || null == baseAuthBean || StringUtils.isEmpty(baseAuthBean.getExpressNo()))
            return new ResponseDTO(CodeEnum.C1034);

        return new ResponseDTO(CodeEnum.C1000);
    }

    private ResponseDTO checkOrderStatus(CollectOrder collectOrder){
        if(null == collectOrder
                || Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode().equals(collectOrder.getOrderStatus())
                || Enumerate.CollectOrderStatus.CANCEL.getCode().equals(collectOrder.getOrderStatus())
                || Enumerate.CollectOrderStatus.WAIT_ACCEPT_TIMEOUT.getCode().equals(collectOrder.getOrderStatus()))
            return new ResponseDTO(CodeEnum.C2015);
        else if(Enumerate.CollectOrderStatus.COLLECTED.getCode().equals(collectOrder.getOrderStatus()))
            return new ResponseDTO(CodeEnum.C1089);
        else
            return new ResponseDTO(CodeEnum.C1000);
    }

    private void saveOrUpdateCollectOrderAndUpdateOperation(CollectOrder collectOrder, boolean bUpdate){
        if(bUpdate)
            collectOrderService.update(collectOrder);
        else
            collectOrderService.save(collectOrder);
        collectOrderService.saveOrderOperation(collectOrder);
    }
}
