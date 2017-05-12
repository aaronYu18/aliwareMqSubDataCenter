package com.courier.core.service;

import com.courier.commons.constant.Global;
import com.courier.commons.entity.RespMessage;
import com.courier.commons.enums.BindPayOffset;
import com.courier.commons.mq.client.base.MQSendClient;
import com.courier.commons.util.CommonUtil;
import com.courier.commons.util.DateUtil;
import com.courier.commons.vModel.VUserIncrementCount;
import com.courier.core.cache.InitCacheData;
import com.courier.core.cache.UserBean;
import com.courier.commons.constant.CacheConstant;
import com.courier.commons.util.Uuid;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.util.security.AES256Encryption;
import com.courier.core.jingang.Interfaces;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VLoginStatus;
import com.courier.db.dao.UserMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.Branch;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class UserService extends BaseManager<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getEntity() {
        return new User();
    }

    @Override
    public BaseMapper<User> getBaseMapper() {
        return userMapper;
    }

    private Integer initJobNo2UserIdPageSize = 1000; //初始jobNo2UserId到redis 每次查询的数量

    @Autowired
    CacheUtil cacheUtil;
    @Autowired
    private BranchService branchService;
    @Value("${ase.secret}")
    private String asESecret;
    @Value("${jingang.login.url}")
    private String jGLoginUrl;
    @Value("${uuid.expiration.time}")
    private String uuidExpirationTime;
    @Autowired
    private UserLoginRecordService userLoginRecordService;
    @Autowired
    private SyncJGDeliveryOrderService syncJGDeliveryOrderService;

    private MQSendClient sendClient;

    /**
     * 登录处理
     * 1. 不变/更新/插入 courier 表
     * 2. 插入login record 表
     */
    public ResponseDTO loginDeal(String jobNo, String password, Byte deviceType, String deviceNo, String version) throws Exception {
        if (StringUtils.isEmpty(jobNo)) return new ResponseDTO(CodeEnum.C1080);
        if (StringUtils.isEmpty(password)) return new ResponseDTO(CodeEnum.C1009);
        //  todo  工号8位数，不够则左侧补零
        jobNo = CommonUtil.formatJobNo(jobNo);

        Interfaces interfaces = new Interfaces();
        String token = "";
        User user = null;

        List<User> userList = userMapper.findByAccount(jobNo);
        String secretPwd = AES256Encryption.encrypt(password, asESecret);

        //  todo 工号在本地存在
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
            if (user.getPassword().equals(secretPwd)) {
                // todo 密码相同，登录成功,插入登录记录
                UserLoginRecord record = userLoginRecordService.findNewestByCourierId(user.getId());
                if (record != null) token = record.getYtoToken();
            } else {
                // todo 密码不一致，调用金刚接口登录；登录成功修改密码，插入登录记录
                ResponseDTO loginResp = interfaces.login(jGLoginUrl, jobNo, password, "");
                if (loginResp.getCodeEnum() != CodeEnum.C1000) return loginResp;
                // todo 针对系统插入的用户 设置相关属性
                updateInfoByJG(user, loginResp);

                user = saveUpdate(secretPwd, user, true);
                token = (String) loginResp.getList().get(0);
            }
        } else {
            // todo 调用金刚接口登录，登录成功插入新的用户记录，更新token……
            ResponseDTO loginResp = interfaces.login(jGLoginUrl, jobNo, password, "");
            if (loginResp.getCodeEnum() != CodeEnum.C1000) return loginResp;

            user = saveUpdate(secretPwd, (User) loginResp.getT2(), false);
            cacheUtil.putData2RedisMap(CacheConstant.ALL_JOBNO_TO_USERID_KEY, user.getJobNo(), user.getId());
//            syncJGDeliveryOrderService.dealUser(user, null, sendClient);
            token = (String) loginResp.getList().get(0);
        }

        Uuid uuid = new Uuid();
        String uuidStr = uuid.toString();
        //  todo 将老的登录记录设置为无效， 插入登录记录
        Long courierId = user.getId();
        // todo 插入最新记录
        UserLoginRecord userLoginRecord = inserUserLoginRecord(courierId, uuidStr, deviceType, deviceNo, token, version);

        UserBean userBean = buildUserBean(user, userLoginRecord);
        // todo 存储redis
        putToRedis(uuidStr, userBean, courierId);

        return new ResponseDTO(CodeEnum.C1000, null, userBean);
    }


    /**
     * 检查uuid是否超过三天有效期，
     * uuid 存在：判断是否超时； 不存在： 判断是否在另一设备登陆
     * 1. 超过： redis及db中都置为无效
     * 2. 未超过： 正常登录
     */
    public ResponseDTO checkUuidIsValid(String uuid) {
        ResponseDTO responseDTO = checkIsKickOut(uuid);
        VLoginStatus vLoginStatus = new VLoginStatus();
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) {
            if (responseDTO.getT2() != null) {
                vLoginStatus.setStatusCode(Enumerate.LoginStatus.kicked.getCode());
                vLoginStatus.setTickedTime((Date) responseDTO.getT2());
            } else {
                vLoginStatus.setStatusCode(Enumerate.LoginStatus.invalid.getCode());
            }
            responseDTO.setT2(vLoginStatus);
            return responseDTO;
        }


        //  todo 根据uuid获取courierBean
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        UserLoginRecord userLoginRecord = userBean.getUserLoginRecord();
        Date createTime = userLoginRecord.getCreateTime();

        //  todo  超过规定时间视为过期(false:过期； true: 有效)
        if (dayInterval(createTime) >= Integer.parseInt(uuidExpirationTime)) {
            userLoginRecordService.setInvaildByUserId(user.getId());
            vLoginStatus.setStatusCode(Enumerate.LoginStatus.timeout.getCode());
            return new ResponseDTO(CodeEnum.C1033, null, vLoginStatus);
        }
        vLoginStatus.setStatusCode(Enumerate.LoginStatus.normal.getCode());
        return new ResponseDTO(CodeEnum.C1000, null, vLoginStatus);
    }

    /**
     * 判断用户是否被踢（另一设备登陆）
     */
    public ResponseDTO checkIsKickOut(String uuid) {
        ResponseDTO responseDTO = getUserBeanByUuid(uuid);

        if (responseDTO.getCodeEnum() != CodeEnum.C1000) {
            UserLoginRecord record = userLoginRecordService.findByUuid(uuid);
            UserLoginRecord userLoginRecord = userLoginRecordService.findValidRecordByInvalidUuid(uuid);
            // todo 如果有有效的记录， 且设备号不同
            if (userLoginRecord != null && record != null && !record.getDeviceNo().equalsIgnoreCase(userLoginRecord.getDeviceNo()))
                return new ResponseDTO(CodeEnum.C1033, null, userLoginRecord.getCreateTime());
            else
                return new ResponseDTO(CodeEnum.C1033);
        }

        return responseDTO;
    }

    /**
     * 该查询为数据同步提供
     */
    public List<User> findValidUser() {
        return userMapper.findValidUser();
    }

    /**
     * 该查询为系统登录提供
     *
     * @param defaultPassword
     */
    public List<User> findForSystemLogin(String defaultPassword) {
        return userMapper.findForSystemLogin(defaultPassword);
    }

    /**
     * 判断uuid在redis中是否有效，有效则重新登录，无效则返回
     */
    public ResponseDTO systemLogin(User user) throws Exception {
        String uuid = null;
        UserLoginRecord record = userLoginRecordService.findValidByUserId(user.getId());
        if (record != null) {
            uuid = record.getUuid();
            Long userId = getUIdByUuid(uuid);
            if (userId == null) uuid = null;
        }

        ResponseDTO loginResp = dealErrorPwd(uuid, user);
        if (CodeEnum.C1000 != loginResp.getCodeEnum()) return loginResp;

        //  todo 判断用户是否在线，在线需要更新redis

        getTokenUpdateRedis(uuid, user, record, loginResp);

        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * 需要调用更新金刚token（取件、签收时）
     */
    public ResponseDTO updateToken(String uuid) throws Exception {
        Long userId = null;
        ResponseDTO uIdResponse = getUserIdByUuid(uuid);
        if (CodeEnum.C1000 != uIdResponse.getCodeEnum()) {
            userId = userLoginRecordService.findUserIdByUuid(uuid);
            uuid = null;
        } else
            userId = (Long) uIdResponse.getT2();

        if (userId == null || userId == 0l) return new ResponseDTO(CodeEnum.C1033);

        User user = get(userId);
        //  todo  重新登录获取最新token
        ResponseDTO loginResp = dealErrorPwd(uuid, user);
        if (CodeEnum.C1000 != loginResp.getCodeEnum()) return loginResp;

        String token = getTokenUpdateRedis(uuid, user, null, loginResp);
        return new ResponseDTO(CodeEnum.C1000, null, token);
    }


    /**
     * 绑定手机
     */
    public ResponseDTO bindPhone(String uuid, String phone) {
        ResponseDTO responseDTO = getUserBeanByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return responseDTO;

        if (StringUtils.isEmpty(phone)) return new ResponseDTO(CodeEnum.C1034);

        //  todo 根据uuid获取courierBean
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();

        user.setPhone(phone);
        user.setUpdateTime(new Date());
        update(user);

        userBean.setUser(user);
        // todo 存储redis
        putToRedis(uuid, userBean, user.getId());

        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * 绑定银行卡
     */
    public ResponseDTO bindBank(String uuid, String bankName, String bankBranchName, String bankCardNo) {
        if (StringUtils.isEmpty(bankName) || StringUtils.isEmpty(bankBranchName) || StringUtils.isEmpty(bankCardNo))
            return new ResponseDTO(CodeEnum.C1034);

        //  todo 根据uuid获取courierBean
        ResponseDTO responseDTO = getUserBeanByUuid(uuid);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return responseDTO;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();

        user.setBankName(bankName);
        user.setBankBranchName(bankBranchName);
        user.setBankCardNo(bankCardNo);
        user.setUpdateTime(new Date());
        update(user);

        userBean.setUser(user);
        // todo 存储redis
        putToRedis(uuid, userBean, user.getId());

        return new ResponseDTO(CodeEnum.C1000);
    }

    /**
     * 根据网点代码、工号获取用户
     */
    public ResponseDTO findByOrgCodeJobNo(String orgCode, String jobNo) {
        if (StringUtils.isEmpty(orgCode) || StringUtils.isEmpty(jobNo))
            return new ResponseDTO(CodeEnum.C1033);
        User cacheUser = cacheUtil.getCacheByFromRedis(buildOrgCodeJobNo(orgCode, jobNo), User.class);
        if (cacheUser != null) return new ResponseDTO(CodeEnum.C1000, null, cacheUser);

        User user = userMapper.findByOrgCodeJobNo(orgCode, jobNo);
        if (user != null) cacheUtil.putData2RedisByTime(buildOrgCodeJobNo(orgCode, jobNo), Global.THREE_DAY_AGE, user);
        return new ResponseDTO(CodeEnum.C1000, null, user);
    }

    public String buildOrgCodeJobNo(String orgCode, String jobNo) {
        return String.format(CacheConstant.USER_ORGCODE_JOBNO, orgCode, jobNo);
    }

    /**
     * 根据工号获取用户
     */
    public User findByJobNo(String jobNo) {
        if (StringUtils.isEmpty(jobNo))
            return null;
        User user = userMapper.findByOrgCodeJobNo(null, jobNo);
        return user;
    }

    public List<User> findByOrgCode(String orgCode){
        return userMapper.findByOrgCode(orgCode);
    }

    public List<User> findByAllOrgCodes(List<String> orgCodes){
        if (CollectionUtils.isEmpty(orgCodes)) return null;
        List<SearchFilter> filters = new ArrayList<>();
        for (String orgCode:orgCodes){
            SearchFilter searchFilter = new SearchFilter("orgCode", SearchFilter.Operator.EQ,orgCode, SearchFilter.Link.OR);
            filters.add(searchFilter);
        }
        return userMapper.findAll(new User(),filters,new ExtSqlProp());
    }

    /**
     * 根据uuid获取userId
     */
    public ResponseDTO getUserIdByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) return new ResponseDTO(CodeEnum.C1033);
        Long userId = getUIdByUuid(uuid);
        // todo  uuid 无效请重新登录
        if (userId == null || userId == 0) return new ResponseDTO(CodeEnum.C1033);

        return new ResponseDTO(CodeEnum.C1000, null, userId);
    }

    public Long getUIdByUuid(String uuid) {
        return cacheUtil.getCacheByFromRedis(buildSessionKey(uuid), Long.class);
    }

    /**
     * 根据uuid获取User
     * redis找不到就从缓存中获取
     */
    public ResponseDTO getUserByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) return new ResponseDTO(CodeEnum.C1033);
        Long courierId = getUIdByUuid(uuid);

        ResponseDTO responseDTO = getUserByUid(courierId);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;

        User result = (User) responseDTO.getT2();

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }

    /**
     * 根据uuid获取UserBean
     * redis找不到就从缓存中获取
     */
    public ResponseDTO getUserBeanByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) return new ResponseDTO(CodeEnum.C1033);
        Long courierId = getUIdByUuid(uuid);

        ResponseDTO responseDTO = getUserBeanByUid(courierId);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;

        UserBean result = (UserBean) responseDTO.getT2();

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }

    /**
     * 根据uid获取user
     * redis找不到就从缓存中获取
     */
    public ResponseDTO getUserByUid(Long userId) {
        User user = null;
        // todo  uuid 无效请重新登录
        if (userId == null || userId == 0) return new ResponseDTO(CodeEnum.C1033);

        UserBean userBean = cacheUtil.getCacheByFromRedis(buildUserKey(userId), UserBean.class);

        if (userBean != null)
            user = userBean.getUser();
        else
            user = this.get(userId);

        // todo  uuid 无效请重新登录
        if (user == null) return new ResponseDTO(CodeEnum.C1033);

        return new ResponseDTO(CodeEnum.C1000, null, user);
    }

    /**
     * 根据uid获取UserBean
     * redis找不到就从缓存中获取
     */
    public ResponseDTO getUserBeanByUid(Long userId) {
        User user = null;
        UserLoginRecord userLoginRecord = null;
        // todo  uuid 无效请重新登录
        if (userId == null || userId == 0) return new ResponseDTO(CodeEnum.C1033);

        UserBean userBean = cacheUtil.getCacheByFromRedis(buildUserKey(userId), UserBean.class);

        if (userBean != null) {
            user = userBean.getUser();
            userLoginRecord = userBean.getUserLoginRecord();
        } else
            user = this.get(userId);

        // todo  uuid 无效请重新登录
        if (user == null) return new ResponseDTO(CodeEnum.C1033);

        if (userLoginRecord == null) {
            List<UserLoginRecord> records = userLoginRecordService.findByCourierAndStatus(userId, true);
            if (records == null || records.size() <= 0) return new ResponseDTO(CodeEnum.C1033);

            userLoginRecord = records.get(0);
        }

        UserBean result = buildUserBean(user, userLoginRecord);

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }


    /**
     * courierBean key 值
     */
    public static String buildUserKey(Long userId) {
        return String.format(CacheConstant.COURIER_USER_KEY_PREFIX, userId);
    }

    /**
     * uuid key 值
     */
    public static String buildSessionKey(String uuid) {
        return String.format(CacheConstant.COURIER_SESSION_KEY_PREFIX, uuid);
    }

    /**
     * 根据手机获取工号
     *
     * @param phones
     * @return
     */
    public String[] findJobNoByPhone(final String... phones) {

        if (phones != null && phones.length > 0) {
            String key = String.format(CacheConstant.PUSH_FILTER_KEY, phones.hashCode());
            return cacheUtil.getCacheRedisFromOther(key, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    String jobNoStr = userMapper.findJobNoByPhone(phones);
                    if (!StringUtils.isEmpty(jobNoStr))
                        return jobNoStr.split("\\,");
                    return new String[]{};
                }
            }, String[].class);
        }
        return new String[]{};
    }

    public String[] findJobNoByUserIds(Long... userIds) {
        if (userIds == null || userIds.length == 0) return null;

        String jobNoStr = userMapper.findJobNoByUserId(userIds);
        if (!StringUtils.isEmpty(jobNoStr))
            return jobNoStr.split("\\,");

        return new String[]{};
    }

    /**
     * 存储session
     */
    public void putToRedis(String uuid, UserBean userBean, Long userId) {
        try {
            cacheUtil.putData2RedisByForever(buildUserKey(userId), userBean);
            if (!StringUtils.isEmpty(uuid)) cacheUtil.putSession2Redis(buildSessionKey(uuid), userId);
        } catch (Exception e) {
            logger.error("put userBean to redis, uuid is {}, uId is {}, user is {}, error is {}", uuid, userId, userBean.toJson(), e.getMessage());
        }
    }

    public List<String> findJobNosByOrgCode(String orgCode){
        if(StringUtils.isEmpty(orgCode)) return null;

        return userMapper.findJobNosByOrgCode(orgCode);
    }

    // todo 离职用户，密码设为默认密码、手机号设为null；如id在redis中有对应uuid则置为无效
    public RespMessage dimissionUserDeal(String orgCode, String jobNo){
        if(StringUtils.isEmpty(orgCode) || StringUtils.isEmpty(jobNo))
            return new RespMessage(RespMessage.Status.error, "orgCode or jobNo is null");
        User user = userMapper.findByOrgCodeJobNo(orgCode, jobNo);

        if(user == null) return new RespMessage(RespMessage.Status.error, "user is not exist");

        // todo 1. 修改密码 手机号
        user.setPassword(Global.DEFAULT_PASSWORD);
        user.setPhone(null);
        user.setUpdateTime(new Date());

        userMapper.update(user);
        // todo 2. redis置为无效
        Long userId = user.getId();
        UserLoginRecord record = userLoginRecordService.findValidByUserId(userId);
        if(record != null) cacheUtil.invalidByRedis(buildSessionKey(record.getUuid()));

        // todo 3. 登录记录置为无效
        userLoginRecordService.setInvaildByUserId(userId);

        return new RespMessage(RespMessage.Status.success);
    }

    /**************************** begin private method ******************/


    /**
     * 插入登录记录
     */
    private UserLoginRecord inserUserLoginRecord(Long courierId, String uuid, Byte deviceType, String deviceNo, String token, String version) {
        //  todo  设置以前登录产生的uuid为无效
        userLoginRecordService.setInvaildByUserId(courierId);

        UserLoginRecord obj = new UserLoginRecord(courierId, uuid, deviceType, deviceNo, token, version);
        userLoginRecordService.save(obj);
        return obj;
    }

    private User saveUpdate(String secretPwd, User user, boolean isUpdate) {
        user.setPassword(secretPwd);
        if (isUpdate) {
            user.setUpdateTime(new Date());
            update(user);
        } else {
            save(user);
        }
        return user;
    }

    /**
     * 获取branch，构造userBean
     */
    private UserBean buildUserBean(User user, UserLoginRecord userLoginRecord) {
        Branch branch = null;
        Byte collectPattern = null;
        String orgCode = user.getOrgCode();

        Integer bindPay = user.getBindPay();
        user.setBindAliPay(bind2Binary(bindPay, BindPayOffset.ALIPAY.getOffset()));
        user.setBindWechatPay(bind2Binary(bindPay, BindPayOffset.WECHATPAY.getOffset()));
        user.setBindBaiduPay(bind2Binary(bindPay, BindPayOffset.BAIDUPAY.getOffset()));
        user.setBindJDPay(bind2Binary(bindPay, BindPayOffset.JDPAY.getOffset()));
        ResponseDTO branchR = branchService.getByOrgCode(orgCode);
        if (branchR.getCodeEnum() != CodeEnum.C1000) {
            logger.error("get branch by orgCode failed, orgCode is {}", orgCode);
        } else {
            branch = (Branch) branchR.getT2();
            collectPattern = getPatternFromRedis(branch.getProvinceCode());
        }

        return new UserBean(user, branch, userLoginRecord, collectPattern);
    }

    /**
     * 根据code 获取中文名取件实名认证模式
     */
    private Byte getPatternFromRedis(String provinceCode) {
        if(StringUtils.isEmpty(provinceCode)) return null;

        String result = cacheUtil.getDataFromRedisMap(CacheConstant.ALL_PROVINCE_AUTH_PATTERN_KEY, provinceCode, String.class);
        return StringUtils.isEmpty(result) ? null : Byte.valueOf(result);
    }

    /**
     * 计算时间长（单位：天）
     */
    private int dayInterval(Date createTime) {
        createTime = DateUtil.getBeginT(createTime);
        Date beginT = DateUtil.getBeginT(new Date());

        Double v = DateUtil.dayInterval(beginT, createTime);
        return v.intValue();
    }


    private void updateInfoByJG(User user, ResponseDTO loginResp) {
        User realUser = (User) loginResp.getT2();

        user.setNickname(realUser.getNickname());
        user.setUsername(realUser.getUsername());
        user.setOrgCode(realUser.getOrgCode());
        user.setUpdateTime(new Date());
    }

    private ResponseDTO dealErrorPwd(String uuid, User user) throws Exception {
        if (user == null) return new ResponseDTO(CodeEnum.C1033);

        Interfaces interfaces = new Interfaces();
        String password = null;

        try {
            password = AES256Encryption.decryptByStr(user.getPassword(), asESecret);
        } catch (Exception e) {
            logger.error("decrypt filed, password is {}", user.getPassword());
        }
        //  todo 解密失败，说明密码错误
        if (password == null) {
            setDefault(uuid, user);
            return new ResponseDTO(CodeEnum.C1072);
        }

        ResponseDTO loginResp = interfaces.login(jGLoginUrl, user.getJobNo(), password, "");

        // todo 只要登录失败，都设为默认密码
        if (loginResp.getCodeEnum() != CodeEnum.C1000) {
//            if (loginResp.getCodeEnum() == CodeEnum.C1072)
                setDefault(uuid, user);
            return loginResp;
        }
        return loginResp;
    }

    private void setDefault(String uuid, User user) {
        // todo 登录记录全部设为无效
        userLoginRecordService.setInvaildByUserId(user.getId());
        // todo uuid设为无效
        if (!StringUtils.isEmpty(uuid))
            cacheUtil.invalidByRedis(buildSessionKey(uuid));
        // todo 设置默认密码
        user.setPassword(Global.DEFAULT_PASSWORD);
        user.setUpdateTime(new Date());
        update(user);
    }

    /**
     * 获取token,同时，更新db及redis
     */
    private String getTokenUpdateRedis(String uuid, User user, UserLoginRecord record, ResponseDTO loginResp) {
        if (user == null || user.getId() == null || user.getId() == 0l) return null;

        String token = null;
        long userId = user.getId();

        List<String> list = loginResp.getList();
        if (list != null && list.size() > 0) token = list.get(0);
        // todo 更新用户信息
        updateInfoByJG(user, loginResp);
        update(user);

        if (record == null)
            record = userLoginRecordService.findValidByUserId(userId);

        // todo 更新登录记录信息
        if (record != null) {
            record.setYtoToken(token);
            record.setUpdateTime(new Date());
            userLoginRecordService.update(record);
        }

        UserBean userBean = buildUserBean(user, record);
        // todo 存储redis
        if (!StringUtils.isEmpty(uuid))
            putToRedis(uuid, userBean, userId);
        return token;
    }

    // todo 根据orgCode获取有效的用户Ids
    public Set<Long> findByOrgCodes(List<String> orgCodes,Integer pageNo, Integer pageSize){
        if(org.springframework.util.CollectionUtils.isEmpty(orgCodes)) return null;
        return userMapper.findByOrgCodes(orgCodes,pageNo,pageSize);
    }
    // todo 根据orgCode获取有效的用户Ids
    public Set<Long> findByOrgCodes(List<String> orgCodes){
        if(org.springframework.util.CollectionUtils.isEmpty(orgCodes)) return null;
        return userMapper.findByOrgCodes(orgCodes,null,null);
    }
    public Set<Long> findUidByOrgCode(String orgCode){
        return findUidByOrgCode(orgCode,null,null);
    }
    public Set<Long> findUidByOrgCode(String orgCode,Integer pageNo, Integer pageSize){
        List<String> set = new ArrayList<>();
        set.add(orgCode);
        return findByOrgCodes(set,pageNo,pageSize);
    }
    public int countByOrgCode(String orgCode){
        return  userMapper.countByOrgCode(orgCode);
    }


    public int countValidNo(String endT) {
        return userMapper.countValidNo(endT);
    }

    public List<User> findForSyncByPage(Integer pageNo, Integer pageSize) {
        return userMapper.findForSyncByPage(pageNo, pageSize);
    }

    public int countForSysLogin(String defaultPwd) {
        return userMapper.countForSysLogin(defaultPwd);
    }

    public List<User> findForSysLogin(String defaultPwd, Integer pageNo, Integer pageSize) {
        return userMapper.findForSysLogin(defaultPwd, pageNo, pageSize);
    }

    public List<VUserIncrementCount> countIncrementByTime(String beginT, String endT, Integer pageNo, Integer pageSize) {
        return userMapper.countIncrementByTime(beginT, endT, pageNo, pageSize);
    }

    public int countTotalPageNo(String beginT, String endT) {
        return userMapper.countTotalPageNo(beginT, endT);
    }

    public int countIncrementNo(String beginT, String endT) {
        return userMapper.countIncrementNo(beginT, endT);
    }


    public void updateBindPay(String uuid, Long userId, Map<Byte, Boolean> bindMap) {
        ResponseDTO responseDTO = getUserBeanByUid(userId);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000)
            return;
        UserBean userBean = (UserBean) responseDTO.getT2();
        User user = userBean.getUser();
        Boolean aliPay = bindMap.get(BindPayOffset.ALIPAY.getOffset());
        if (aliPay == null) {
            bindMap.put(BindPayOffset.ALIPAY.getOffset(), user.isBindAliPay());
            aliPay = user.isBindAliPay();
        }
        Boolean weChat = bindMap.get(BindPayOffset.WECHATPAY.getOffset());
        if (weChat == null) {
            bindMap.put(BindPayOffset.WECHATPAY.getOffset(), user.isBindWechatPay());
            weChat = user.isBindWechatPay();
        }
        Boolean baidu = bindMap.get(BindPayOffset.BAIDUPAY.getOffset());
        if (baidu == null) {
            bindMap.put(BindPayOffset.BAIDUPAY.getOffset(), user.isBindBaiduPay());
            baidu = user.isBindBaiduPay();
        }
        Boolean jd = bindMap.get(BindPayOffset.JDPAY.getOffset());
        if (jd == null) {
            bindMap.put(BindPayOffset.JDPAY.getOffset(), user.isBindJDPay());
            jd = user.isBindJDPay();
        }
        int bindPay = binary2Bind(bindMap);
        userMapper.updateBindPay(userId, bindPay);

        user.setBindPay(bindPay);
        user.setBindAliPay(aliPay != null?aliPay:false);
        user.setBindWechatPay(weChat != null?weChat:false);
        user.setBindBaiduPay(baidu != null?baidu:false);
        user.setBindJDPay(jd != null?jd:false);
        putToRedis(uuid, userBean, userId);
    }

    private static boolean bind2Binary(Integer bindPay, byte offset) {
        if (bindPay == null || bindPay == 0) return false;
        if (offset < 1) return false;
        return (bindPay >> (offset - 1) & 1) == 1;
    }
    private static int binary2Bind(Map<Byte, Boolean> binaries) {
        int bind = 0;
        boolean[] booleans = new boolean[BindPayOffset.values().length];
        for (byte b : binaries.keySet()) {
            booleans[b - 1] = binaries.get(b);
        }
        for (int i = booleans.length - 1; i >= 0; i--) {
            bind = bind | (booleans[i]?1:0);
            if (0 != i) bind = bind << 1;
        }
        return bind;
    }

    public void setSendClient(MQSendClient sendClient) {
        this.sendClient = sendClient;
    }

    public List<User> findPageByOrgCode(String orgCode, Integer pageNo, Integer pageSize) {
        return userMapper.findPageByOrgCode(orgCode, pageNo, pageSize);
    }

    // todo cache初始化jobNo2userId数据
    public void initJobNo2UserIdCache(boolean forceRefresh){
        // todo 如果为非强制刷新，且在redis已存在，直接退出
        if(!forceRefresh && checkJobNo2UserIdExist())
            return;

        int totalNum = userMapper.countValidNo(null);
        if(totalNum == 0) return;

        Map<String, Object> allUserIds = new HashMap();

        int pageCount = (totalNum + initJobNo2UserIdPageSize - 1) / initJobNo2UserIdPageSize;
        for (int i = 1; i <= pageCount; i++) {
            List<User> users = userMapper.findForSyncByPage((i-1) * initJobNo2UserIdPageSize, initJobNo2UserIdPageSize);
            if (!CollectionUtils.isEmpty(users)) {
                for (User user : users)
                    allUserIds.put(user.getJobNo(), user.getId());
            }
        }

        //  todo  存储
        try {
            cacheUtil.putData2RedisMap(CacheConstant.ALL_JOBNO_TO_USERID_KEY, allUserIds);
        } catch (Exception e) {
            logger.error("put all jobNos 2 userIds to redis failed, error is {}", e.getMessage());
        }
    }

    private boolean checkJobNo2UserIdExist() {
        return cacheUtil.isExistByRedis(CacheConstant.ALL_JOBNO_TO_USERID_KEY) && cacheUtil.getMapLeng(CacheConstant.ALL_JOBNO_TO_USERID_KEY) > 0;
    }

	public Long getUserIdByJobNo(String jobNo) {
        if (StringUtils.isEmpty(jobNo) || "null".equalsIgnoreCase(jobNo)) return null;
        jobNo = jobNo.trim();

        Long userId = cacheUtil.getDataFromMapWithLocal(CacheConstant.ALL_JOBNO_TO_USERID_KEY, jobNo, Long.class);

        if (userId != null && userId == Global.IF_USER_NOT_EXIST_DEFAULT_USER_ID) {
            List<User> users = userMapper.findByAccount(jobNo);
            if (!CollectionUtils.isEmpty(users)) return getUIdFromDbList(jobNo, users);

            return null;
        }

        if (userId == null) {
            List<User> users = userMapper.findByAccount(jobNo);
            if (!CollectionUtils.isEmpty(users)) {
                return getUIdFromDbList(jobNo, users);
            } else {
                cacheUtil.putData2RedisMap(CacheConstant.ALL_JOBNO_TO_USERID_KEY, jobNo, Global.IF_USER_NOT_EXIST_DEFAULT_USER_ID);
                try {
                    User user = new User();
                    user.setJobNo(jobNo);
                    user.setPassword(Global.DEFAULT_PASSWORD);
                    user.setOrgCode(Global.DEFAULT_ORGCODE);
                    Date now = new Date();
                    user.setCreateTime(now);
                    user.setUpdateTime(now);
                    save(user);
                    userId = user.getId();
                    cacheUtil.putData2RedisMap(CacheConstant.ALL_JOBNO_TO_USERID_KEY, jobNo, userId);
                } catch (Exception e) {
                    logger.error("insert user to db, error is {}", e.getMessage());
                    return null;
                }
            }
        }

        return userId;
    }

    private Long getUIdFromDbList(String jobNo, List<User> users) {
        User user = users.get(0);
        if(user == null || user.getId() == null) return null;
        Long userId = user.getId();
        cacheUtil.putData2RedisMap(CacheConstant.ALL_JOBNO_TO_USERID_KEY, jobNo, userId);
        return userId;
    }

}
