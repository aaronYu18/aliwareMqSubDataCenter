package com.courier.core.jingang;

import com.courier.commons.constant.Global;
import com.courier.commons.model.JGLogResult;
import com.courier.commons.model.JGRecordOrder;
import com.courier.commons.model.JGRecordResponse;
import com.courier.commons.model.RealNameSystemRequest;
import com.courier.commons.model.baiDu.BDGpsResp;
import com.courier.commons.model.jinGang.*;
import com.courier.commons.model.xml.*;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.MD5Utils;
import com.courier.commons.util.ValidationUtil;
import com.courier.commons.util.http.HttpRequestResult;
import com.courier.commons.util.http.HttpUtils;
import com.courier.commons.util.json.GsonUtil;
import com.courier.commons.util.security.Coder;
import com.courier.core.jingang.convert.JGUserConvert;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.entity.User;
import com.courier.sdk.constant.CodeEnum;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 对外接口
 * 包含 金刚及百度地址接口
 * Created by admin on 2015/10/22.
 */
public class Interfaces {
    private final String SUCCESS_STATUS = "0";
    private final String JINGANG_CODE_PWD_ERROR = "4009";               //    金刚系统返回code, 登录密码错误
    private final String JINGANG_CODE_USER_NOT_EXIST = "4004";          //    金刚系统返回code, 用户不存在
    private final String LOGIN_PARAM_FORMAT = "{\"userid\":\"%s\",\"pwd\":\"%s\",\"mobile\":\"%s\"}";
    private final String LOGIN_TOKEN_KEY = "TOKEN=";
    private final String BAIDU_SUCCESS_OK = "OK";
    private final String BAIDU_SUCCESS_STATUS = "0";
    private final int HTTPSTATUSFAILURE = -1;
    private final int RETRY_TIMES = Global.REQUESTNUMBER;              // todo 重试次数
    private final int TIME_OUT = 10000;

    private static final Logger jgLogger = LoggerFactory.getLogger("JINGANG_INTERFACE_LOG");


    public JGBaseModelArray aliPayTrade(String url, JGTradeInfo request, String md5Constant) {
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(url)) return new JGBaseModelArray();
        HttpRequestResult httpRequestResult = null;
        try {
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json; charset=UTF-8");
            String token = MD5Utils.md5(md5Constant, "");
            header.put("Token", token);
            HttpUtils httpUtils = new HttpUtils();
            httpRequestResult = httpUtils.doPost(url, header, null, request.toJson());
            String jsonResult = httpUtils.getByteContent();
            if (StringUtils.isEmpty(jsonResult)) return new JGBaseModelArray(httpRequestResult.getCode());
            JGBaseModelArray baseModel = GsonUtil.getBean(jsonResult, JGBaseModelArray.class);
            if (baseModel!=null)baseModel.setHttpStatus(httpRequestResult.getCode());
            jgLogger.info("\"action\":\"aliPayTrade\",\"msg\":\"success\", \"js\":{}", new JGLogResult(request, baseModel).toJson());
            return baseModel;
        } catch (Exception e) {
            jgLogger.error("\"action\":\"aliPayTrade\",\"msg\":\"error\", \"url\":{}, \"js\":{},\"httpCode\":{}", url,request.toJson(),httpRequestResult.getCode());
        }
        return new JGBaseModelArray(httpRequestResult.getCode(),"json parse exception");
    }

    public JGBaseModelArray realNameSystem(String url, RealNameSystemRequest request)  {
        if (StringUtils.isEmpty(request) || StringUtils.isEmpty(url)) return new JGBaseModelArray();
        HttpRequestResult httpRequestResult = null;
        try {
            httpRequestResult = new HttpRequestResult();
            String jsonResult = dealRealName(httpRequestResult, url, request.toJson());
            if (StringUtils.isEmpty(jsonResult)) return new JGBaseModelArray(httpRequestResult.getCode());
            JGBaseModelArray baseModel = GsonUtil.getBean(jsonResult, JGBaseModelArray.class);
            if (baseModel!=null)baseModel.setHttpStatus(httpRequestResult.getCode());
            jgLogger.info("\"action\":\"realName\",\"msg\":\"success\", \"js\":{}", new JGLogResult(request, baseModel).toJson());
            return baseModel;
        } catch (Exception e) {
            jgLogger.error("\"action\":\"realName\",\"msg\":\"error\", \"url\":{}, \"js\":{},\"httpCode\":{}", url, request.toJson(), httpRequestResult.getCode());
        }
        return new JGBaseModelArray(httpRequestResult.getCode(),"json parse exception");
    }

    public JGVerificationInfo findByFm(String url,String fm){
        JGVerificationInfo jgVerificationInfo = null;
        if (StringUtils.isEmpty(url)|| StringUtils.isEmpty(fm)) return jgVerificationInfo;
        HttpRequestResult httpRequestResult = null;
        try{
            httpRequestResult = new HttpRequestResult();
            String req = "{\"FX\":\""+fm+"\"}";
            String jsonResult = dealRealName(httpRequestResult,url,req);
            if (StringUtils.isEmpty(jsonResult)) return null;
            jgVerificationInfo = GsonUtil.getBean(jsonResult,JGVerificationInfo.class);
            jgLogger.info("\"action\":\"findByFm\",\"msg\":\"success\", \"url\":{},\"js\":{}, \"result\":{},\"httpCode\":{}", url, fm,jsonResult, httpRequestResult.getCode());
        }catch (Exception e){
            jgLogger.error("\"action\":\"findByFm\",\"msg\":\"error\", \"url\":{}, \"js\":{},\"httpCode\":{}", url, fm, httpRequestResult.getCode());
        }
        return jgVerificationInfo;
    }
    public JGVerificationInfo findByTel(String url,String tel){
        JGVerificationInfo jgVerificationInfo = null;
        if (StringUtils.isEmpty(url)|| StringUtils.isEmpty(tel)) return jgVerificationInfo;
        HttpRequestResult httpRequestResult = null;
        try{
            httpRequestResult = new HttpRequestResult();
            String req = "{\"sjhm\":\""+tel+"\"}";
            String jsonResult = dealRealName(httpRequestResult,url,req);
            if (StringUtils.isEmpty(jsonResult)) return null;
            jgVerificationInfo = GsonUtil.getBean(jsonResult,JGVerificationInfo.class);
            jgLogger.info("\"action\":\"findByTel\",\"msg\":\"success\", \"url\":{}, \"js\":{}, \"result\":{},\"httpCode\":{}", url, tel,jsonResult, httpRequestResult.getCode());
        }catch (Exception e){
            jgLogger.error("\"action\":\"findByTel\",\"msg\":\"error\", \"url\":{}, \"js\":{},\"httpCode\":{}", url, tel, httpRequestResult.getCode());
        }
        return jgVerificationInfo;
    }

    private String dealRealName(HttpRequestResult httpRequestResult, String url, String requestJson) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("xl_params",requestJson);
        HttpUtils httpUtils = new HttpUtils();
        if (execPostRetry(httpRequestResult, url, null, params, httpUtils,0)) return null;
        String jsonData = httpUtils.getByteContent();
        return jsonData;
    }
    /**
     * 根据 工号及密码登录系统
     */
    public ResponseDTO login(String loginUrl, String courierCode, String password, String mobile) throws Exception {
        try {
            if (StringUtils.isEmpty(mobile)) mobile = "";

            String values = String.format(LOGIN_PARAM_FORMAT, courierCode, password, mobile);
            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            HttpRequestResult httpRequestResult = new HttpRequestResult();
            JGHttpResponse jgHttpResponse = dealSingleValPost(httpRequestResult, loginUrl, values, header, true);
            if (jgHttpResponse == null) return new ResponseDTO(CodeEnum.C2006);

            JGBaseModel jgBaseModel = jgHttpResponse.getBaseModel();
            Header[] responseHeaders = jgHttpResponse.getResponseHeaders();

            if (jgBaseModel == null) return new ResponseDTO(CodeEnum.C2006);

            //  密码错误
            if (jgBaseModel.getCode().equals(JINGANG_CODE_PWD_ERROR))
                return new ResponseDTO(CodeEnum.C1072);
            //  用户不存在
            if (jgBaseModel.getCode().equals(JINGANG_CODE_USER_NOT_EXIST))
                return new ResponseDTO(CodeEnum.C1081);
            //  成功
            if (jgBaseModel.getCode().equals(SUCCESS_STATUS)) {
                JGUser jgUser = (JGUser) GsonUtil.getBean(jgBaseModel.getResult(), JGUser.class);
                User user = JGUserConvert.convertObj(jgUser);
                //   提取金刚token
                String token = buildToken(responseHeaders);
                List<String> list = new ArrayList<String>();
                list.add(token);
                jgLogger.info("\"action\":\"login\",\"msg\":\"success\", \"js\":{}", new JGLogResult(jgBaseModel, jgUser).toJson());
                return new ResponseDTO(CodeEnum.C1000, list, user);
            }
            jgLogger.error("\"action\":\"login\",\"msg\":\"error\", \"js\":{}", new JGLogResult(jgBaseModel, null).toJson());
            return new ResponseDTO(CodeEnum.C2006);
        } catch (Exception e) {
            jgLogger.error("\"action\":\"login\",\"msg\":\"error\", \"cause\":{}", e.getMessage());
            return new ResponseDTO(CodeEnum.C2006);
        }

    }

    /**
     * 获取经纬度
     */
    public BDGpsResp.Location getGps(String url, String regEx, String province, String city, String area, String address) {
        if (province == null) province = "";
        if (city == null) city = "";
        if (area == null) area = "";
        if (StringUtils.isEmpty(address) || "N/A".equalsIgnoreCase(address)) return null;
        String fully = province + city + area + address;
        return getGpsByAddress(url, regEx, fully);
    }


    /**
     * @param url     请求参数
     * @param address 地址
     * @return 返回结果  116.30783584945,40.056876296398  否则返回 null
     * @throws Exception
     */
    public BDGpsResp.Location getGpsByAddress(String url, String regEx, String address) {
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(url)) return null;
        //  todo  取消字符串中所有空格

        try {
            HttpUtils httpUtils = new HttpUtils();

            address = ValidationUtil.filter(regEx, address);
            url = url + "&address=" + address;

            HttpRequestResult httpRequestResult = httpUtils.doGet(url);
            if (httpRequestResult == null || httpRequestResult.getCode() != HttpStatus.SC_OK || !httpUtils.isStream()) {
                jgLogger.error("\"action\":\"baiDu\",\"msg\":\"error\", \"js\":{}, \"url\":{}", httpRequestResult == null ? "-1" : httpRequestResult.getCode(), url);
                return null;
            }
            String result = httpUtils.getByteContent();
            result = result.replace(" ", "").replace("\n", "").replace("\t", "");
            if (result.equalsIgnoreCase("{\"status\":\"OK\",\"result\":[]}")) {
                jgLogger.error("\"action\":\"baiDu\",\"msg\":\"error\", \"js\":{}", result);
                return null;
            }

            BDGpsResp.Base base = GsonUtil.getBean(result, BDGpsResp.Base.class);
            if (base == null || (!base.getStatus().equalsIgnoreCase(BAIDU_SUCCESS_STATUS)&&!base.getStatus().equalsIgnoreCase(BAIDU_SUCCESS_OK))) return null;
            jgLogger.info("\"action\":\"baiDu\",\"msg\":\"success\", \"js\":{}, \"url\":{}", GsonUtil.toJson(base), url);
            return base.getResult().getLocation();
        } catch (Exception e) {
            jgLogger.error("\"action\":\"baiDu\",\"msg\":\"error\", \"cause\":{}, \"url\":{}", e.getMessage(), url);
        }
        return null;
    }

    /**
     * @param url     请求参数地址
     * @param signReq
     * @param token   TOKEN=mytoken;JESSIONID=myjessinId  金刚登录系统值 tokanId 会失效
     * @return
     * @throws Exception
     */
    public JGBaseModel sign(String url, JGSignReq signReq, String token) throws Exception {
        if (StringUtils.isEmpty(url) || signReq == null) return new JGBaseModel();
        JGBaseModel jgBaseModel = new JGBaseModel();
        try {
            String json = GsonUtil.toJson(signReq);
            Map<String, String> header = new HashMap<String, String>();
            header.put("Cookie", LOGIN_TOKEN_KEY + token);
            HttpRequestResult httpRequestResult = new HttpRequestResult();
            JGHttpResponse jgHttpResponse = dealSingleValPost(httpRequestResult, url, json, header, false);
            int httpStatus = fetchHttpStatus(httpRequestResult);
            if (jgHttpResponse == null) return new JGBaseModel(httpStatus);
            jgBaseModel = jgHttpResponse.getBaseModel();
            jgBaseModel.setHttpStatus(httpStatus);
        } catch (Exception e) {
            jgLogger.error("\"action\":\"sign\",\"msg\":\"error\", \"cause\":{}", e.getMessage());
        }

        return jgBaseModel;
    }

    /**
     * 无单取件
     *
     * @param url            请求地址
     * @param takingModelReq
     * @param tokenId        TOKEN=mytoken;JESSIONID=myjessinId  金刚登录系统值 tokanId 会失效
     * @return
     * @throws Exception
     */
    public JGBaseModel pickupByNo(String url, JGTakingModelReq takingModelReq, String tokenId) throws Exception {
        if (StringUtils.isEmpty(url) || takingModelReq == null) return new JGBaseModel();
        JGBaseModel jgBaseModel = new JGBaseModel();
        try {
            String json = GsonUtil.toJson(takingModelReq);
            Map<String, String> header = new HashMap<String, String>();
            header.put("Cookie", LOGIN_TOKEN_KEY + tokenId);
            HttpRequestResult httpRequestResult = new HttpRequestResult();
            JGHttpResponse jgHttpResponse = dealSingleValPost(httpRequestResult, url, json, header, false);
            int httpStatus = fetchHttpStatus(httpRequestResult);
            if (jgHttpResponse == null) return new JGBaseModel(httpStatus);
            jgBaseModel = jgHttpResponse.getBaseModel();
            jgBaseModel.setHttpStatus(httpStatus);
        } catch (Exception e) {
            e.printStackTrace();
            jgLogger.error("\"action\":\"pickupByNo\",\"msg\":\"error\", \"cause\":{}", e.getMessage());
        }
        return jgBaseModel;
    }


    /**
     * 走件接口2
     *
     * @param url
     * @param mailNo
     * @return
     */
    public List<DockResult> dockTrace2(String url, String mailNo) {
        String parameterData = "[{\"Number\":\"" + mailNo + "\"}]";
        String date = DateUtil.dateToStr(new Date());
        String secretKey = "eJqP7d";
        String appKey = "WoR2iY";
        String format = "json";
        String method = "yto.Marketing.WaybillTrace";
        String userId = "210152";
        String v = "1.0";
        String sign = secretKey + "app_key" + appKey + "format" + format + "method" + method + "timestamp" + date + "user_id" + userId + "v" + v;

        sign = Coder.md5Encode(sign);//MD5加密
        sign = sign.toUpperCase();//规则加密
        Map<String, String> params = getParamsMap(parameterData, date, appKey, format, method, userId, v, sign);
        List<DockResult> list = dealForTrace(url, params);
        if (list == null || list.size() == 0) {
            jgLogger.error("\"action\":\"dockTrace2\",\"msg\":\"error\", \"js\":{},\"params\":{}", GsonUtil.toJson(list), mailNo);
            return list;
        }
        jgLogger.info("\"action\":\"dockTrace2\",\"msg\":\"success\", \"js\":{},\"params\":{}", GsonUtil.toJson(list), mailNo);
        return list;
    }


    /**
     * 超时无人接单 队列 (新建订单) 。2.1
     */
    public JGAcceptResult noAccept(String url, RequestOrder order, String md5Constant) throws Exception {
        if (StringUtils.isEmpty(url) || order == null) return new JGAcceptResult();
        String resultXml = requestOrder2Xml(order);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPost(httpRequestResult, url, resultXml, md5Constant, order.getClientID());
        int httpStatus = fetchHttpStatus(httpRequestResult);
        if (StringUtils.isEmpty(xmlData)) return new JGAcceptResult(httpStatus);
        XStream xt = new XStream(new DomDriver());
        xt.alias("Response", JGAcceptResult.class);
        JGAcceptResult jgAcceptResult = (JGAcceptResult) xt.fromXML(xmlData);
        jgAcceptResult.setHttpStatus(httpStatus);
        return jgAcceptResult;
    }

    /**
     * 接单  队列 2.2
     */
    public JGAcceptResult accept(String url, RequestOrder order, String md5Constant) throws Exception {
        if (StringUtils.isEmpty(url) || order == null) return new JGAcceptResult();
        String resultXml = requestOrder2Xml(order);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPost(httpRequestResult, url, resultXml, md5Constant, order.getClientID());
        int httpStatus = fetchHttpStatus(httpRequestResult);
        if (StringUtils.isEmpty(xmlData)) return new JGAcceptResult(httpStatus);
        XStream xt = new XStream(new DomDriver());
        xt.alias("Response", JGAcceptResult.class);
        JGAcceptResult jgAcceptResult = (JGAcceptResult) xt.fromXML(xmlData);
        jgAcceptResult.setHttpStatus(httpStatus);
        return jgAcceptResult;
    }


    public JGRecordResponse recordOrderPickByNo(String url , JGRecordOrder order, String md5Constant){
        JGRecordResponse baseModel = null;
        if (StringUtils.isEmpty(url) || order == null) return new JGRecordResponse();
        try {
            HttpUtils httpUtils = new HttpUtils();
            String json = order.toJson();
            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json;charset=UTF-8");
            String date = DateUtil.toDay(new Date());
            String token = date + ""+md5Constant;
            String em = MD5Utils.employeeMd5(token);
            header.put("TOKEN",em);
            HttpRequestResult httpRequestResult = httpUtils.doPost(url, header, null, json);
            String result = httpUtils.getByteContent();
            int httpStatus = fetchHttpStatus(httpRequestResult);
            baseModel = GsonUtil.getBean(result, JGRecordResponse.class);
            if (baseModel == null) {
                jgLogger.error("\"action\":\"recordOrderPickByNo\",\"msg\":\"error\", \"js\":{}, \"params\":{}", new JGLogResult(baseModel).toJson());
                return new JGRecordResponse(httpStatus);
            }
            baseModel.setHttpCode(httpStatus);
            jgLogger.info("\"action\":\"recordOrderPickByNo\",\"msg\":\"success\", \"js\":{}, \"params\":{}", new JGLogResult(baseModel).toJson());
        }catch (Exception e){
            
        }
        return baseModel;
    }

    /**
     * 查询订单状态 2.3
     */
    public JGBaseModelArray findOrderInfo(String url, List<String> orderIds, String md5Constant, String clientId) throws Exception {
        JGBaseModelArray baseModel = new JGBaseModelArray();
        if (StringUtils.isEmpty(url) || orderIds == null) return baseModel;
        Map map = new HashMap<>();
        map.put("orderIds", orderIds);
        String resultXml = GsonUtil.toJson(map);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPost(httpRequestResult, url, resultXml, md5Constant, clientId);

        baseModel = GsonUtil.getBean(xmlData, JGBaseModelArray.class);
        if (baseModel == null) {
            jgLogger.error("\"action\":\"findOrderInfo\",\"msg\":\"error\", \"js\":{}, \"params\":{}", new JGLogResult(baseModel).toJson(), GsonUtil.toJson(orderIds));
            return baseModel;
        }
        jgLogger.info("\"action\":\"findOrderInfo\",\"msg\":\"success\", \"js\":{}, \"params\":{}", new JGLogResult(baseModel).toJson(), GsonUtil.toJson(orderIds));
        return baseModel;
    }

    /**
     * 有单取件接口 2.4
     * 先更新面单接口 再做无单取件
     */
    public JGUpdateInfoResult updateInfo(String url, JGTakingModelReq req, String md5Constant) throws Exception {
        JGUpdateInfoResult jgUpdateInfoResult = new JGUpdateInfoResult();
        if (StringUtils.isEmpty(url) || req == null) return jgUpdateInfoResult;

        try {
            UpdateInfo info = new UpdateInfo(req.getTxLogisticID(), req.getMailNo(), req.getClientId(), "update");
            String resultXml = updateInfo2Xml(info);
            HttpRequestResult httpRequestResult = new HttpRequestResult();
            String xmlData = dealMd5ClientPost(httpRequestResult, url, resultXml, md5Constant, req.getClientId());
            int httpStatus = fetchHttpStatus(httpRequestResult);
            if (StringUtils.isEmpty(xmlData)) {
                jgLogger.error("\"action\":\"updateInfo\",\"msg\":\"error\", \"js\":{}", new JGLogResult(jgUpdateInfoResult, req).toJson());
                return new JGUpdateInfoResult(httpStatus);
            }
            XStream xt = new XStream(new DomDriver());
            xt.alias("Response", JGUpdateInfoResult.class);

            jgUpdateInfoResult = (JGUpdateInfoResult) xt.fromXML(xmlData);
            jgUpdateInfoResult.setHttpStatus(httpStatus);
            jgLogger.info("\"action\":\"updateInfo\",\"msg\":\"success\", \"js\":{}", new JGLogResult(jgUpdateInfoResult, req).toJson());
            return jgUpdateInfoResult;
        } catch (Exception e) {
            jgLogger.error("\"action\":\"updateInfo\",\"msg\":\"error\", \"js\":{}, \"cause\":{}", new JGLogResult(null, req).toJson(), e.getMessage());
        }
        jgLogger.error("\"action\":\"updateInfo\",\"msg\":\"error\", \"js\":{}", new JGLogResult(null, req).toJson());
        return jgUpdateInfoResult;
    }


    /**
     * 获取电子面单号 3.2 B+ model
     */
    public ResponseMailNoResult findNewMailNo(String url, RequestOrder order, String md5Constant) throws Exception {
        ResponseMailNoResult responseMailNoResult = new ResponseMailNoResult();
        if (StringUtils.isEmpty(url) || order == null) return responseMailNoResult;
        String resultXml = requestOrder2Xml(order);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPostRetry(httpRequestResult, url, resultXml, md5Constant, order.getClientID());

        if (StringUtils.isEmpty(xmlData)) {
            jgLogger.error("\"action\":\"findNewMailNo\",\"msg\":\"error\", \"js\":{}", new JGLogResult(responseMailNoResult, order).toJson());
            return null;
        }
        XStream xt = new XStream(new DomDriver());
        xt.alias("Response", ResponseMailNoResult.class);
        xt.alias("distributeInfo", DistributeInfo.class);
        responseMailNoResult = (ResponseMailNoResult) xt.fromXML(xmlData);
        jgLogger.info("\"action\":\"findNewMailNo\",\"msg\":\"success\", \"js\":{}", new JGLogResult(responseMailNoResult, order).toJson());
        return responseMailNoResult;
    }

    public ResponseMailNoModelCResult findNewMailNOCModel(String url,RequestOrderCModel orderCModel,String md5Constant) throws Exception{
        ResponseMailNoModelCResult responseMailNoModelCResult = new ResponseMailNoModelCResult();
        if (StringUtils.isEmpty(url)|| orderCModel==null) return responseMailNoModelCResult;
        String resultXml = requestOrderCModel2Xml(orderCModel);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPostRetry(httpRequestResult, url, resultXml, md5Constant, orderCModel.getClientID());

        if (StringUtils.isEmpty(xmlData)) {
            jgLogger.error("\"action\":\"findNewMailNo\",\"msg\":\"error\", \"js\":{}", new JGLogResult(responseMailNoModelCResult, orderCModel).toJson());
            return null;
        }

        XStream xt = new XStream(new DomDriver());
        xt.alias("Response", ResponseMailNoModelCResult.class);
        xt.alias("orderMessage", OrderMessage.class);
        responseMailNoModelCResult = (ResponseMailNoModelCResult) xt.fromXML(xmlData);
        jgLogger.info("\"action\":\"findNewMailNoCModel\",\"msg\":\"success\", \"js\":{}", new JGLogResult(responseMailNoModelCResult, orderCModel).toJson());
        return responseMailNoModelCResult;
    }


    /**
     * 3.6
     *
     * @param url
     * @param secret
     * @param md5Constant
     * @return
     * @throws Exception
     */
    public ResponseSecret findSecretKey(String url, RequestSecret secret, String md5Constant) throws Exception {
        ResponseSecret responseSecret = new ResponseSecret();
        if (StringUtils.isEmpty(url) || secret == null) return responseSecret;
        String resultXml = recret2Xml(secret);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String xmlData = dealMd5ClientPostRetry(httpRequestResult, url, resultXml, md5Constant, secret.getClientID());

        if (StringUtils.isEmpty(xmlData)) {
            jgLogger.error("\"action\":\"findSecretKey\",\"msg\":\"error\", \"js\":{}", new JGLogResult(responseSecret, secret).toJson());
            return null;
        }
        XStream xt = new XStream(new DomDriver());
        xt.alias("Response", ResponseSecret.class);
        responseSecret = (ResponseSecret) xt.fromXML(xmlData);
        jgLogger.info("\"action\":\"findSecretKey\",\"msg\":\"success\", \"js\":{}", new JGLogResult(responseSecret, secret).toJson());
        return responseSecret;
    }


    /**
     * 根据面单号 从金刚系统拉取运单详细信息 3.1
     */
    public JGBaseModelMailNoInfo findMailNoInfo(String url, String mailNo, String md5Constant) throws Exception {
        JGBaseModelMailNoInfo baseModel = new JGBaseModelMailNoInfo();
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(mailNo)) return baseModel;

        Map map = new HashMap<>();
        map.put("mailNo", mailNo);
        String json = GsonUtil.toJson(map);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String jsonData = dealMd5Post(httpRequestResult, url, json, md5Constant, true);

        baseModel = GsonUtil.getBean(jsonData, JGBaseModelMailNoInfo.class);
        if (baseModel == null) {
            jgLogger.error("\"action\":\"findMailNoInfo\",\"msg\":\"error\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), mailNo);
            return baseModel;
        }
        jgLogger.info("\"action\":\"findMailNoInfo\",\"msg\":\"success\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), mailNo);
        return baseModel;
    }

    /**
     * 3.3 获取客户编码接口 辅助电子面单接口
     */
    public JGBaseModelArray findClientID4MailNo(String url, String orgCode, String md5Constant) throws Exception {
        Map map = new HashMap<>();
        map.put("operateOrgCode", orgCode);
        String json = GsonUtil.toJson(map);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String jsonData = dealMd5Post(httpRequestResult, url, json, md5Constant, true);

        JGBaseModelArray baseModel = GsonUtil.getBean(jsonData, JGBaseModelArray.class);
        if (baseModel == null) {
            jgLogger.error("\"action\":\"findClientID4MailNo\",\"msg\":\"error\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), orgCode);
            return baseModel;
        }
        jgLogger.info("\"action\":\"findClientID4MailNo\",\"msg\":\"success\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), orgCode);
        return baseModel;
    }

    /**
     * 获取快递员当月派件统计信息 3.4  日期只能延后一天
     */
    public JGBaseModelStatInfo statInfo(String url, JGStatInfoReq statInfoReq, String md5Constant) throws Exception {
        JGBaseModelStatInfo baseModel = new JGBaseModelStatInfo();
        if (StringUtils.isEmpty(url) || statInfoReq == null) return baseModel;

        String json = GsonUtil.toJson(statInfoReq);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String jsonData = dealMd5Post(httpRequestResult, url, json, md5Constant, false);

        baseModel = GsonUtil.getBean(jsonData, JGBaseModelStatInfo.class);
        if(baseModel==null){
            jgLogger.error("\"action\":\"statInfo\",\"msg\":\"error\", \"js\":{}", new JGLogResult(baseModel, statInfoReq).toJson());
            return baseModel;
        }
        jgLogger.info("\"action\":\"statInfo\",\"msg\":\"success\", \"js\":{}", new JGLogResult(baseModel, statInfoReq).toJson());
        return baseModel;
    }

    /**
     * 从金刚系统获取网点信息 3.5
     * (如果branchCode 为空 跑出 null)
     */
    public JGBaseModelBranchInfo getBranchInfo(String url, String orgCode, String md5Constant) {
        JGBaseModelBranchInfo baseModel = new JGBaseModelBranchInfo();
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(orgCode)) return baseModel;
        Map map = new HashMap<>();
        map.put("orgCode", orgCode.trim());
        String json = GsonUtil.toJson(map);
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        String jsonData = dealMd5Post(httpRequestResult, url, json, md5Constant, true);

        baseModel = GsonUtil.getBean(jsonData, JGBaseModelBranchInfo.class);
        if (baseModel==null){
            jgLogger.error("\"action\":\"branchInfo\",\"msg\":\"error\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), orgCode);
            return baseModel;
        }
        jgLogger.info("\"action\":\"branchInfo\",\"msg\":\"success\", \"js\":{},\"params\":{}", new JGLogResult(baseModel).toJson(), orgCode);
        return baseModel;

    }

    /**
     * ************************************************** begin private method *********************************************************
     */


    private List<DockResult> dealForTrace(String url, Map<String, String> params) {
        List<DockResult> list = new ArrayList<>();
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        HttpUtils httpUtils = new HttpUtils();
        HttpRequestResult httpRequestResult = new HttpRequestResult();
        if (execPostRetry(httpRequestResult, url, header, params, httpUtils, 0)) return null;
        if (HttpStatus.SC_OK == httpRequestResult.getCode() && httpUtils.isStream()) {
            String resultBuffer = httpUtils.getByteContent();
            if (resultBuffer.contains("<success>false</success>")) {
                jgLogger.info("\"action\":\"deal\",\"msg\":\"success\", \"js\":{}", new JGLogResult(httpRequestResult.getCode(), params).toJson());
            } else {
                list = (List<DockResult>) GsonUtil.getBean(resultBuffer, new TypeToken<List<DockResult>>() {
                }.getType());
            }
        } else {
            jgLogger.error("\"action\":\"deal\",\"msg\":\"error\", \"js\":{}", new JGLogResult(httpRequestResult.getCode(), params).toJson());
        }
        return list;
    }


    private String dealMd5Post(HttpRequestResult httpRequestResult, String url, String json, String md5Constant, boolean isRetry) {
        Map<String, String> params = new HashMap<>();
        params.put("params", json);
        params.put("key", MD5Utils.md5Base64Url(json + md5Constant));
        HttpUtils httpUtils = new HttpUtils();
        boolean bool;
        if (isRetry)
            bool = execPostRetry(httpRequestResult, url, null, params, httpUtils, 0);
        else
            bool = execPost(httpRequestResult, url, null, params, httpUtils);

        if (bool) return null;
        return httpUtils.getByteContent();
    }

    private String dealMd5ClientPost(HttpRequestResult httpRequestResult, String url, String resultXml, String md5Constant, String clientID) throws Exception {
        String data_digest = MD5Utils.md5(resultXml, md5Constant);
        Map<String, String> params = new HashMap<String, String>();
        params.put("logistics_interface", resultXml);
        params.put("data_digest", data_digest);
        params.put("clientId", clientID);

        HttpUtils httpUtils = new HttpUtils();
        if (execPost(httpRequestResult, url, null, params, httpUtils)) return null;
        String xmlData = httpUtils.getByteContent();
        return xmlData;
    }
    private String dealMd5ClientPostRetry(HttpRequestResult httpRequestResult, String url, String resultXml, String md5Constant, String clientID) throws Exception {
        String data_digest = MD5Utils.md5(resultXml, md5Constant);
        Map<String, String> params = new HashMap<String, String>();
        params.put("logistics_interface", resultXml);
        params.put("data_digest", data_digest);
        params.put("clientId", clientID);

        HttpUtils httpUtils = new HttpUtils();
        if (execPostRetry(httpRequestResult, url, null, params, httpUtils,0)) return null;
        String xmlData = httpUtils.getByteContent();
        return xmlData;
    }



    /**
     * @param url    请求的url地址
     * @param values 请求参数 json
     * @param header 头文件信息
     * @return
     */
    private JGHttpResponse dealSingleValPost(HttpRequestResult httpRequestResult, String url, String values, Map<String, String> header, boolean isRetry) {
        Map<String, String> params = new HashMap<String, String>();
        HttpUtils httpUtils = new HttpUtils();
        params.put("params", values);
        boolean bool;
        if (isRetry)
            bool = execPostRetry(httpRequestResult, url, header, params, httpUtils, 0);
        else
            bool = execPost(httpRequestResult, url, header, params, httpUtils);

        if (bool) return null;

        String resultBuffer = httpUtils.getByteContent();
        Header[] responseHeaders = httpUtils.getResponseHeaders();
        JGBaseModel baseModel = GsonUtil.getBean(resultBuffer, JGBaseModel.class);

        return new JGHttpResponse(baseModel, responseHeaders);
    }


    /**
     * 结果值为200返回false 结果值不是200 返回true
     */
    private boolean execPostRetry(HttpRequestResult resultInput, String url, Map<String, String> header, Map<String, String> params, HttpUtils httpUtils, int retryTime) {
        HttpRequestResult result = httpUtils.doPost(url, header, params, null);
        resultInput.setCode(result.getCode());
        resultInput.setContent(result.getContent());
        if (resultInput.getCode() != HttpStatus.SC_OK || !httpUtils.isStream()) {
            jgLogger.error("\"action\":\"deal\",\"msg\":\"error\",\"url\":{} ,\"js\":{} ", url, new JGLogResult(resultInput.getCode(), params).toJson());
            if (retryTime >= RETRY_TIMES) return true;
            int count = ++retryTime;
            return execPostRetry(resultInput, url, header, params, httpUtils, count);
        }

        return false;
    }

    /**
     * 结果值为200返回false 结果值不是200 返回true
     */
    private boolean execPost(HttpRequestResult resultInput, String url, Map<String, String> header, Map<String, String> params, HttpUtils httpUtils) {
        HttpRequestResult result = httpUtils.doPost(url, header, params, null,TIME_OUT, TIME_OUT, TIME_OUT);
        resultInput.setCode(result.getCode());
        resultInput.setContent(result.getContent());
        if (resultInput.getCode() != HttpStatus.SC_OK || !httpUtils.isStream()) {
            jgLogger.error("\"action\":\"deal\",\"msg\":\"error\",\"url\":{} ,\"js\":{} ", url, new JGLogResult(resultInput.getCode(), params).toJson());
            return true;
        }

        return false;
    }


    private int fetchHttpStatus(HttpRequestResult httpRequestResult) {
        return httpRequestResult == null ? HTTPSTATUSFAILURE : httpRequestResult.getCode();
    }

    /**
     * 提取金刚系统 token 值
     *
     * @return
     */
    private String buildToken(Header[] responseHeaders) throws UnsupportedEncodingException {
        String token = "";
        for (Header h : responseHeaders) {
            if (h.getName().equals("Set-Cookie")) {
                String value = h.getValue().split("; ")[0];
                if (value.contains(LOGIN_TOKEN_KEY)) {
                    token = value.replace(LOGIN_TOKEN_KEY, "");
                }
            }
        }

        if (!StringUtils.isEmpty(token)) token = URLEncoder.encode(token, "UTF-8");

        return token;
    }


    /**
     * ********************  begin xml convert ********************
     */
    private String recret2Xml(RequestSecret secret) {
        XStream xStream = new XStream();
        xStream.alias("RequestSecret", RequestSecret.class);
        return xStream.toXML(secret);
    }

    private String requestOrder2Xml(RequestOrder order) {
        XStream xStream = new XStream();
        xStream.alias("RequestOrder", RequestOrder.class);
        xStream.alias("sender", Sender.class);
        xStream.alias("receiver", Receiver.class);
        xStream.alias("item", Item.class);
        return xStream.toXML(order);
    }
    private String requestOrderCModel2Xml(RequestOrderCModel order) {
        XStream xStream = new XStream();
        xStream.alias("RequestOrder", RequestOrderCModel.class);
        xStream.alias("sender", Sender.class);
        xStream.alias("receiver", Receiver.class);
        xStream.alias("item", Item.class);
        return xStream.toXML(order);
    }
    private String updateInfo2Xml(UpdateInfo info) {
        XStream xStream = new XStream();
        xStream.alias("updateInfo", UpdateInfo.class);
        return xStream.toXML(info);
    }

    private Map<String, String> getParamsMap(String parameterData, String date, String appKey, String format, String method, String userId, String v, String sign) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", sign);
        params.put("app_key", appKey);
        params.put("format", format);
        params.put("method", method);
        params.put("timestamp", date);
        params.put("user_id", userId);
        params.put("v", v);
        params.put("param", parameterData);
        return params;
    }

    class JGHttpResponse {
        private JGBaseModel baseModel;
        private Header[] responseHeaders;

        public JGHttpResponse() {
        }

        public JGHttpResponse(JGBaseModel baseModel, Header[] responseHeaders) {
            this.baseModel = baseModel;
            this.responseHeaders = responseHeaders;
        }

        public JGBaseModel getBaseModel() {
            return baseModel;
        }

        public void setBaseModel(JGBaseModel baseModel) {
            this.baseModel = baseModel;
        }

        public Header[] getResponseHeaders() {
            return responseHeaders;
        }

        public void setResponseHeaders(Header[] responseHeaders) {
            this.responseHeaders = responseHeaders;
        }
    }
}
