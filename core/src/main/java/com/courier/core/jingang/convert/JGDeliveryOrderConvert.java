package com.courier.core.jingang.convert;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.enums.AuxOpCodeEnum;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.model.PushParams;
import com.courier.commons.model.baiDu.BDGpsResp;
import com.courier.commons.model.soaJinGang.ExpWaybillWanted;
import com.courier.commons.push.PushFilter;
import com.courier.commons.util.CommonUtil;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.util.http.HttpRequestResult;
import com.courier.commons.util.http.HttpUtils;
import com.courier.commons.util.json.GsonUtil;
import com.courier.commons.vModel.VDeliveryOrder;
import com.courier.core.convert.CommonConvert;
import com.courier.core.jingang.Interfaces;
import com.courier.core.service.BranchService;
import com.courier.core.service.UserService;
import com.courier.core.vModel.PushDeliveryOrderDealResult;
import com.courier.db.entity.AppointmentOrder;
import com.courier.db.entity.Branch;
import com.courier.db.entity.DeliveryOrder;
import com.courier.sdk.constant.Enumerate;
import com.courier.sdk.utils.DESUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/10/23.
 */
public class JGDeliveryOrderConvert {
    public static final Logger logger = LoggerFactory.getLogger(JGDeliveryOrderConvert.class);
    private static Logger deliveryOrdersLog = LoggerFactory.getLogger("delivery_orders_log");


    /**
     * 金刚推送派件对象转换为 行者本地对象
     * @return
     */
    public static PushDeliveryOrderDealResult convertPushObj(VDeliveryOrder obj, CacheUtil cacheUtil, BranchService branchService, UserService userService,
                                                             String bdUrl, String regEx, String secretKey,String url) {
        if (obj == null) return null;
        DeliveryOrder result = new DeliveryOrder();

        if (StringUtils.isEmpty(obj.getWaybillNo()) || "null".equalsIgnoreCase(obj.getWaybillNo())) {
            deliveryOrdersLog.error("convert failed, mailNo is null, order info is : {}", obj.toJson());
            return null;
        }
        String waybillNo = obj.getWaybillNo().toUpperCase();
        String jobNo = obj.getEmpCode();

        Long userId = userService.getUserIdByJobNo(jobNo);
        if (userId == null) {
            if (obj.getAuxOpCode() != null && obj.getAuxOpCode().equalsIgnoreCase(AuxOpCodeEnum.DELETE.getCode())) {
                deliveryOrdersLog.info("auxOpCode is delete order info is {}", obj.toJson());
            } else {
                deliveryOrdersLog.error("convert failed, find userId by jobNo result is null, order info is : {}", obj.toJson());
                return null;
            }
        }

        String auxOpCode = obj.getAuxOpCode();
        String empName = obj.getEmpName();
        // todo 派件人姓名以.结尾的 直接丢弃
        if(!StringUtils.isEmpty(empName) && empName.endsWith(".")){
            deliveryOrdersLog.error("convert failed, empName end with '.', order info is : {}", obj.toJson());
            return null;
        }

        //  查找redis中是否有菜鸟预约件
        String cnAppointKey = String.format(CacheConstant.APPOINTMENT_ORDER_MAIL_NO_KEY, waybillNo);
        AppointmentOrder appointmentOrder = cacheUtil.getCacheByFromRedis(cnAppointKey, AppointmentOrder.class);

        Branch branch = branchService.getBranchByUId(userId);
        AppointmentOrder order = buildNeedUpdateAppointInfo(appointmentOrder, branch);

        if(AuxOpCodeEnum.DELETE.getCode().equalsIgnoreCase(auxOpCode)){
            result.setMailNo(waybillNo);
            result.setUserId(userId);
            PushDeliveryOrderDealResult.Type type = PushDeliveryOrderDealResult.Type.delete;
            deliveryOrdersLog.info("deal type is {}, userId is {}, mailNo is {}", type.toString(), userId, waybillNo);
            return new PushDeliveryOrderDealResult(type, result, order);
        }

        String createTime = obj.getCreateTime();
        String province = obj.getReceiverProv();
        String city = obj.getReceiverCity();
        String area = obj.getReceiverTown();

        String receiverAddress = obj.getReceiverApp();
        String collectionPay = obj.getCollectionPay();
        String toPayment = obj.getToPayment();

        province = CommonConvert.replaceNullIfNA(province);
        city = CommonConvert.replaceNullIfNA(city);
        area = CommonConvert.replaceNullIfNA(area);


        //todo 省市区
        CommonConvert.pcaConvertWithDeliveryOrder(branch, cacheUtil, province, city, area, result);
        String provinceName = result.getReceiverProvinceName(), cityName = result.getReceiverCityName(), areaName = result.getReceiverAreaName();

        Interfaces interfaces = new Interfaces();
        BDGpsResp.Location location = interfaces.getGps(bdUrl, regEx, provinceName, cityName, areaName, receiverAddress);


        Date jGCTime = convertByString(createTime);

        Double freightMoney = getDoubleExculeZero(toPayment, "toPayment");
        Double collectionMoney = getDoubleExculeZero(collectionPay, "collectionPay");
        byte paymentType = getPaymentType(collectionMoney, freightMoney);
        result.setFreightMoney(freightMoney);
        result.setCollectionMoney(collectionMoney);

        String receiverName = obj.getReceiverName();
        String receiverMobile = obj.getReceiverMobile();
        receiverName = CommonConvert.replaceNullIfNA(receiverName);
        receiverAddress = CommonConvert.replaceNullIfNA(receiverAddress);
        result.setUserId(userId);
        result.setjGCreateTime(jGCTime);
        try {
            // 匹配手机号规则才进行加密操作
            if(!StringUtils.isEmpty(receiverMobile) && Pattern.matches(Global.REGEX_MOBILE, receiverMobile))
                receiverMobile = DESUtil.encryptDES(receiverMobile, secretKey);
        } catch (Exception e) {
            logger.error("convertSyncObj des Encryption failed, receiverMobile is {}", receiverMobile);
        }
        result.setReceiverPhone(receiverMobile);
        result.setReceiverName(receiverName);
        result.setReceiverAddress(receiverAddress);
        result.setMailNo(waybillNo);
        result.setPaymentType(paymentType);
        result.setOrderType(Enumerate.DeliveryOrderType.jgsync.getType());
        result.setOrderStatus(Enumerate.DeliveryOrderStatus.sending.getType());
        result.setFlag(true);
        result.setHasPicture(false);

        if (location != null) {
            result.setReceiverLat(location.getLat());
            result.setReceiverLng(location.getLng());
        }

        addTags(cacheUtil, result, waybillNo, jobNo, empName, url, appointmentOrder);

        PushDeliveryOrderDealResult.Type update = PushDeliveryOrderDealResult.Type.update;
        if(AuxOpCodeEnum.INSERT.getCode().equalsIgnoreCase(auxOpCode)){
            //todo 判断是否已通过 金刚面单号接口 插入
            String key = String.format(CacheConstant.SYNC_LOCK_USER_MAILNO_KEY, userId, waybillNo);
            boolean flag = cacheUtil.putData2RedisCache(key, Global.FireDeliveryTime, 1);
            if(!flag){
                // todo 已存在
                deliveryOrdersLog.info("deal type is {}, userId is {}, mailNo is {}", update.toString(), userId, waybillNo);
                return new PushDeliveryOrderDealResult(update, result, order);
            }else{
                cacheUtil.putData2RedisCache(key, Global.FireDeliveryTime, 1);
                PushDeliveryOrderDealResult.Type insert = PushDeliveryOrderDealResult.Type.insert;

                deliveryOrdersLog.info("deal type is {} and locked, userId is {}, mailNo is {}", insert.toString(), userId, waybillNo);
                return new PushDeliveryOrderDealResult(insert, result, order);
            }
        }else if(AuxOpCodeEnum.UPDATE.getCode().equalsIgnoreCase(auxOpCode)){
            deliveryOrdersLog.info("deal type is {}, userId is {}, mailNo is {}", update.toString(), userId, waybillNo);
            return new PushDeliveryOrderDealResult(update, result, order);
        }

        return null;
    }


    public static Byte getPaymentType(Double dsFee, Double dfFee) {
        Byte paymentType = Enumerate.DeliveryOrderPaymentType.nothing.getType();
        if (dsFee != null && dfFee != null) {
            paymentType = Enumerate.DeliveryOrderPaymentType.freightAndcollection.getType();
        } else if (dsFee != null) {
            paymentType = Enumerate.DeliveryOrderPaymentType.collection.getType();
        } else if (dfFee != null) {
            paymentType = Enumerate.DeliveryOrderPaymentType.freight.getType();
        }
        return paymentType;
    }

    /**
     * if moneyString is 0  return null
     *
     * @param moneyString
     * @param logKey
     * @return
     */
    public static Double getDoubleExculeZero(String moneyString, String logKey) {
        Double money = null;
        if (!StringUtils.isEmpty(moneyString)) {
            try {
                money = Double.valueOf(moneyString);
                if (money == 0d) money = null;
            } catch (NumberFormatException e) {
                logger.error("jinGang {} data convert error, date is {}, error info is {}", logKey, moneyString, e.getMessage());
            }
        }
        return money;
    }

    /********************************* private method *************************/

    private static Date convertByString(String createTime) {
        Date jGCTime = null;
        if (!StringUtils.isEmpty(createTime)) {
            try {
                jGCTime = DateUtil.valueof(createTime, DateUtil.FULL_FORMAT);
            } catch (Exception e) {
                logger.error("getDeliveryOrder, jinGang createTime data convert error, date is {}, error info is {}", createTime, e.getMessage());
            }
        }
        return jGCTime;
    }

    /**
     * 填充appointOrder 网点信息
     */
    private static AppointmentOrder buildNeedUpdateAppointInfo(AppointmentOrder appointmentOrder, Branch branch){
        if(appointmentOrder == null || branch == null) return null;

        AppointmentOrder order = null;

        String orgCode = appointmentOrder.getOrgCode();
        String realOrgCode = branch.getOrgCode();
        String realBranchCode = branch.getBranchCode();
        String realTerminalCode = branch.getTerminalCode();

        if(StringUtils.isEmpty(orgCode) || !orgCode.equals(realOrgCode)){
            order = new AppointmentOrder();
            order.setId(appointmentOrder.getId());
            order.setOrgCode(realOrgCode);
            order.setBranchCode(realBranchCode);
            order.setTerminalCode(realTerminalCode);
        }

        return order;
    }
    /**
     * 添加标签
     */
    private static void addTags(CacheUtil cacheUtil, DeliveryOrder result, String waybillNo, String jobNo, String userName, String url, AppointmentOrder appointmentOrder) {
        Byte code = CommonEnum.BoolEnum.TRUE.getCode();
        //  通缉件 (todo: 通缉件的key对应的对象修改过)
        try {
            String mailNoKey = String.format(CacheConstant.THIRD_WANTED_MAILNO_KEY, waybillNo.toUpperCase());
            Object cacheByFromRedis = cacheUtil.getCacheByFromRedis(mailNoKey);
            if (cacheByFromRedis != null) {
                result.setIsWanted(code);
                // 发送push 消息通知快递员，有预约件需要派送
                if (cacheByFromRedis instanceof ExpWaybillWanted) {
                    ExpWaybillWanted tempWanted = (ExpWaybillWanted)cacheByFromRedis;
                    PushParams pushParams = getPushParams(result, jobNo, tempWanted);
                    pushCourierMessage(pushParams, url);
                }
            }
        }catch (Exception e){
            logger.error("wanted is error mailno:{}",waybillNo);
        }

        // 菜鸟预约件填充……
        if(null != appointmentOrder){
            String timeStart = null, timeEnd = null, moneyDeliver = null;
            try {
                timeStart = appointmentOrder.getAppointTimeStart();
                timeEnd = appointmentOrder.getAppointTimeEnd();
                moneyDeliver = appointmentOrder.getMoneyDeliver();

                Integer begin = DateUtil.convertHMToMins(timeStart);
                Integer end = DateUtil.convertHMToMins(timeEnd);
                Double money = CommonUtil.convertString(moneyDeliver);

                result.setIsCnAppoint(code);
                result.setCnAppointBeginT(begin);
                result.setCnAppointEndT(end);
                result.setMoneyDeliver(money);

                // 发送push 消息通知快递员，有预约件需要派送
                PushParams pushParams = getPushParams(result, waybillNo, jobNo, userName, timeStart, timeEnd, moneyDeliver);
                pushCourierMessage(pushParams, url);
            }catch (Exception e){
                logger.error("add tags failed, begin str {}, begin str {}, exception is {}", timeStart, timeEnd, e.getMessage());
            }
        }
    }

    private static PushParams getPushParams(DeliveryOrder result, String waybillNo, String jobNo, String userName, String timeStart, String timeEnd, String moneyDeliver) {
        PushParams pushParams = new PushParams();
        String message = format(userName,waybillNo,result.getReceiverAddress(),timeStart,timeEnd,moneyDeliver);
        pushParams.setMessage(message);
        pushParams.setTitle("预约派件通知");
        pushParams.setType((byte)1);//单发
        PushFilter pushFilter = new PushFilter();
        pushFilter.setCourierCode(jobNo);
        pushParams.setFilter(GsonUtil.toJson(pushFilter));
        return pushParams;
    }
    private static PushParams getPushParams(DeliveryOrder result,String jobNo, ExpWaybillWanted expWaybillWanted) {
        PushParams pushParams = new PushParams();
        String message = format(result,expWaybillWanted);
        pushParams.setMessage(message);
        pushParams.setTitle("通缉件通知");
        pushParams.setType((byte)1);//单发
        PushFilter pushFilter = new PushFilter();
        pushFilter.setCourierCode(jobNo);
        pushParams.setFilter(GsonUtil.toJson(pushFilter));
        return pushParams;
    }

    private static void pushCourierMessage(PushParams pushParams, String url) {
        if (StringUtils.isEmpty(url)){
            logger.error("push courier message url is null");
            return;
        }
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json");
        HttpUtils httpUtils = new HttpUtils();
        HttpRequestResult httpRequestResult = httpUtils.doPost(url, header, null, GsonUtil.toJson(pushParams),10000,10000,10000);
        String content = httpUtils.getByteContent();
        logger.info("action:{},httpcode:{},req:{},res:{}","postCourierAppointment",httpRequestResult.getCode(),GsonUtil.toJson(pushParams),content);
    }

    private static String format(String userName, String mailNo, String receiveAddress, String beginTime,
                                 String endTime, String fee) {
        if (StringUtils.isEmpty(userName)) userName = "";
        if (StringUtils.isEmpty(mailNo)) mailNo = "";
        if (StringUtils.isEmpty(receiveAddress)) receiveAddress = "";
        if (StringUtils.isEmpty(beginTime)) beginTime = "";
        if (StringUtils.isEmpty(endTime)) endTime = "";
        String key = "%s快递员师傅，您被指派了一个预约派送服务，运单号：%s，派件地址：%s，预约时段：%s-%s，请按要求派送，如客户变更预约时间，请先上报问题件再派送并实时签收，辛苦了！";
        if (StringUtils.isEmpty(fee)) {
            return String.format(key, userName, mailNo, receiveAddress, beginTime, endTime, fee);
        } else {
            key = "%s快递员师傅，您被指派了一个预约派送服务，运单号：%s，派件地址：%s，预约时段：%s-%s，您的服务费：%s元，请按要求派送，如客户变更预约时间，请先上报问题件再派送并实时签收，辛苦了！";

            return String.format(key, userName, mailNo, receiveAddress, beginTime, endTime, fee);
        }

    }

    private static String format(DeliveryOrder order, ExpWaybillWanted expWaybillWanted) {
        String key = "您被派件了一个通缉件，运单号：" + order.getMailNo();
        if (!StringUtils.isEmpty(order.getReceiverAddress())) {
            key += "，派件地址：" + order.getReceiverAddress();
        }
        if (!StringUtils.isEmpty(expWaybillWanted.getWantedDesc())) {
            key += "，通缉原因：" + expWaybillWanted.getWantedDesc();
        }
        key += ",请按要求规范操作！";
        return key;
    }
}
