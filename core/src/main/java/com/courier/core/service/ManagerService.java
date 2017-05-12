package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.CommonUtil;
import com.courier.commons.util.DateUtil;
import com.courier.commons.util.Uuid;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.util.security.AES256Encryption;
import com.courier.core.cache.ManagerBean;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VLoginStatus;
import com.courier.db.dao.ManagerMapper;
import com.courier.db.dao.OrgReportMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.*;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by vincent on 16/4/15.
 */
@Service
@Transactional
public class ManagerService extends BaseManager<Manager>{
    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private ManagerLoginRecordService managerLoginRecordService;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private OrgReportMapper orgReportMapper;
    @Autowired
    private BranchService branchService;
    @Autowired
    private UserService userService;

    @Value("${ase.secret}")
    private String asESecret;
    @Value("${uuid.expiration.time}")
    private String uuidExpirationTime;
    @Override
    public Manager getEntity() {
        return new Manager();
    }

    @Override
    public BaseMapper<Manager> getBaseMapper() {
        return managerMapper;
    }

    public ResponseDTO loginDeal(String mobile, String password, Byte deviceType, String deviceNo, String version) throws Exception {
        if (StringUtils.isEmpty(mobile)) return new ResponseDTO(CodeEnum.C1080);
        if (StringUtils.isEmpty(password)) return new ResponseDTO(CodeEnum.C1009);

        Manager manager = null;

        List<Manager> managerList = managerMapper.findByAccount(mobile);
        String secretPwd = AES256Encryption.encrypt(password, asESecret);

        if (managerList != null && managerList.size() > 0) {
            manager = managerList.get(0);
            if (manager.getPassword().equals(secretPwd)) {
                // todo 密码相同，登录成功,插入登录记录
                Uuid uuid = new Uuid();
                String uuidStr = uuid.toString();
                //  todo 将老的登录记录设置为无效， 插入登录记录
                Long managerId = manager.getId();
                // todo 插入最新记录
                ManagerLoginRecord managerLoginRecord = insertManagerLoginRecord(managerId, uuidStr, deviceType, deviceNo, version);
                manager.setOrgName(branchService.queryOrgName(manager.getOrgCode(), manager.getRole()));
                ManagerBean managerBean = buildManagerBean(manager, managerLoginRecord);
                // todo 存储redis
                putToRedis(uuidStr, managerBean, managerId);
                return new ResponseDTO(CodeEnum.C1000, null, managerBean);
            } else {
                return new ResponseDTO(CodeEnum.C1072, null, null);
            }
        }else {
            return new ResponseDTO(CodeEnum.C1081, null, null);
        }
    }


    public ResponseDTO register(String mobile, String password, String jobNo, String name, String orgCode,
                                Byte deviceType, String deviceNo, String version) throws Exception {
        jobNo = CommonUtil.formatJobNo(jobNo);

        List<Manager> managerList = managerMapper.findByAccount(mobile);
        if (managerList != null && managerList.size() > 0) {
            return new ResponseDTO(CodeEnum.C1003, null, null);
        }
        managerList = managerMapper.findByJobNo(jobNo);
        if (managerList != null && managerList.size() > 0) {
            return new ResponseDTO(CodeEnum.C4003, null, null);
        }
        String secretPwd = AES256Encryption.encrypt(password, asESecret);

        Manager manager = new Manager(jobNo, name, orgCode, secretPwd, mobile, mobile);
        managerMapper.insert(manager);
        Uuid uuid = new Uuid();
        String uuidStr = uuid.toString();
        //  todo 将老的登录记录设置为无效， 插入登录记录
        Long managerId = manager.getId();
        // todo 插入最新记录
        ManagerLoginRecord managerLoginRecord = insertManagerLoginRecord(managerId, uuidStr, deviceType, deviceNo, version);
        ManagerBean managerBean = buildManagerBean(manager, managerLoginRecord);
        // todo 存储redis
        putToRedis(uuidStr, managerBean, managerId);
        return new ResponseDTO(CodeEnum.C1000, null, managerBean);
    }

    public ResponseDTO forgetPwd(String mobile, String password) throws Exception {
        List<Manager> managerList = managerMapper.findByAccount(mobile);
        String secretPwd = AES256Encryption.encrypt(password, asESecret);
        if (managerList != null && managerList.size() > 0) {
            Manager manager = managerList.get(0);
            manager.setPassword(secretPwd);
            update(manager);
            return new ResponseDTO(CodeEnum.C1000, null, null);
        }else {
            return new ResponseDTO(CodeEnum.C1081, null, null);
        }
    }

    public ResponseDTO updatePwd(String uuid, String oldPassword, String password) throws Exception {
        ResponseDTO responseDTO = getManagerByUuid(uuid);
        if (!responseDTO.getCodeEnum().equals(CodeEnum.C1000))
            return new ResponseDTO(responseDTO.getCodeEnum(), null, null);
        String oldSecretPwd = AES256Encryption.encrypt(oldPassword, asESecret);
        String secretPwd = AES256Encryption.encrypt(password, asESecret);
        Manager manager = (Manager) responseDTO.getT2();
        if (manager.getPassword().equals(oldSecretPwd)) {
            manager.setPassword(secretPwd);
            update(manager);
            return new ResponseDTO(CodeEnum.C1000, null, null);
        }
        return new ResponseDTO(CodeEnum.C1073, null, null);
    }
    /**
     * 插入登录记录
     */
    private ManagerLoginRecord insertManagerLoginRecord(Long managerId, String uuid, Byte deviceType, String deviceNo, String version) {
        //  todo  设置以前登录产生的uuid为无效
        managerLoginRecordService.setInvaildByManagerId(managerId);

        ManagerLoginRecord obj = new ManagerLoginRecord(deviceNo, deviceType, managerId, uuid, version);
        managerLoginRecordService.save(obj);
        return obj;
    }

    private ManagerBean buildManagerBean(Manager manager, ManagerLoginRecord managerLoginRecord) {
        return new ManagerBean(manager, managerLoginRecord);
    }

    /**
     * 存储session
     */
    public void putToRedis(String uuid, ManagerBean managerBean, Long managerId) {
        try {
            cacheUtil.putData2RedisByForever(buildManagerKey(managerId), managerBean);
            if (!StringUtils.isEmpty(uuid)) cacheUtil.putSession2Redis(buildSessionKey(uuid), managerId);
        } catch (Exception e) {
            logger.error("put userBean to redis, uuid is {}, uId is {}, user is {}, error is {}", uuid, managerId, managerBean.toJson(), e.getMessage());
        }
    }

    /**
     * managerBean key 值
     */
    public static String buildManagerKey(Long managerId) {
        return String.format(CacheConstant.MANAGER_USER_KEY_PREFIX, managerId);
    }

    /**
     * manager uuid key 值
     */
    public static String buildSessionKey(String uuid) {
        return String.format(CacheConstant.MANAGER_SESSION_KEY_PREFIX, uuid);
    }

    public ResponseDTO getManagerByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) return new ResponseDTO(CodeEnum.C1033);
        Long managerId = getUIdByUuid(uuid);

        ResponseDTO responseDTO = getManagerByUid(managerId);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;

        Manager result = (Manager) responseDTO.getT2();

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }
    public ResponseDTO getManagerBeanByUuid(String uuid) {
        if (StringUtils.isEmpty(uuid)) return new ResponseDTO(CodeEnum.C1033);
        Long managerId = getUIdByUuid(uuid);

        ResponseDTO responseDTO = getManagerBeanByUid(managerId);
        if (CodeEnum.C1000 != responseDTO.getCodeEnum()) return responseDTO;

        ManagerBean result = (ManagerBean) responseDTO.getT2();

        return new ResponseDTO(CodeEnum.C1000, null, result);
    }
    public Long getUIdByUuid(String uuid) {
        return cacheUtil.getCacheByFromRedis(buildSessionKey(uuid), Long.class);
    }

    public ResponseDTO getManagerByUid(Long managerId) {
        Manager manager = null;
        // todo  uuid 无效请重新登录
        if (managerId == null || managerId == 0) return new ResponseDTO(CodeEnum.C1033);

        ManagerBean managerBean = cacheUtil.getCacheByFromRedis(buildManagerKey(managerId), ManagerBean.class);

        if (managerBean != null)
            manager = managerBean.getManager();
        else
            manager = this.get(managerId);

        // todo  uuid 无效请重新登录
        if (manager == null) return new ResponseDTO(CodeEnum.C1033);

        return new ResponseDTO(CodeEnum.C1000, null, manager);
    }

    public ResponseDTO getManagerBeanByUid(Long managerId) {
        Manager manager = null;
        ManagerLoginRecord managerLoginRecord = null;
        // todo  uuid 无效请重新登录
        if (managerId == null || managerId == 0) return new ResponseDTO(CodeEnum.C1033);

        ManagerBean managerBean = cacheUtil.getCacheByFromRedis(buildManagerKey(managerId), ManagerBean.class);

        if (managerBean != null) {
            manager = managerBean.getManager();
            managerLoginRecord = managerBean.getManagerLoginRecord();
        } else
            manager = this.get(managerId);

        // todo  uuid 无效请重新登录
        if (manager == null) return new ResponseDTO(CodeEnum.C1033);

        if (managerLoginRecord == null) {
            List<ManagerLoginRecord> records = managerLoginRecordService.findByManagerAndStatus(managerId, true);
            if (records == null || records.size() <= 0) return new ResponseDTO(CodeEnum.C1033);

            managerLoginRecord = records.get(0);
        }

        ManagerBean result = buildManagerBean(manager, managerLoginRecord);

        return new ResponseDTO(CodeEnum.C1000, null, result);
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

        //  todo 根据uuid获取managerBean
        ManagerBean managerBean = (ManagerBean) responseDTO.getT2();
        Manager manager = managerBean.getManager();
        ManagerLoginRecord managerLoginRecord = managerBean.getManagerLoginRecord();
        Date createTime = managerLoginRecord.getCreateTime();

        //  todo  超过规定时间视为过期(false:过期； true: 有效)
        if (dayInterval(createTime) >= Integer.parseInt(uuidExpirationTime)) {
            managerLoginRecordService.setInvaildByManagerId(manager.getId());
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
        ResponseDTO responseDTO = getManagerBeanByUuid(uuid);

        if (responseDTO.getCodeEnum() != CodeEnum.C1000) {
            ManagerLoginRecord record = managerLoginRecordService.findByUuid(uuid);
            ManagerLoginRecord managerLoginRecord = managerLoginRecordService.findValidRecordByInvalidUuid(uuid);
            // todo 如果有有效的记录， 且设备号不同
            if (managerLoginRecord != null && record != null && !record.getDeviceNo().equalsIgnoreCase(managerLoginRecord.getDeviceNo()))
                return new ResponseDTO(CodeEnum.C1033, null, managerLoginRecord.getCreateTime());
            else
                return new ResponseDTO(CodeEnum.C1033);
        }

        return responseDTO;
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

    public Manager updateObj(Manager manager) {
        Manager managerDB = get(manager.getId());
        if (managerDB == null) return null;
        managerDB.setNickname(manager.getNickname());
        managerDB.setJobNo(manager.getJobNo());
        managerDB.setOrgCode(manager.getOrgCode());
        managerDB.setRole(manager.getRole());
        managerDB.setUpdateTime(new Date());
        update(managerDB);
        Long managerId = manager.getId();
        managerLoginRecordService.setInvaildByManagerId(managerId);
        cacheUtil.invalidByRedis(buildManagerKey(managerId));
        return managerDB;
    }

    public Manager unVerifyManager(Long id) {
        Manager manager = get(id);
        if (manager == null) return null;
        manager.setRole(null);
        manager.setStatus(Enumerate.ManagerStatus.CLOSE.getType());
        manager.setUpdateTime(new Date());
        update(manager);
        Long managerId = manager.getId();
        managerLoginRecordService.setInvaildByManagerId(managerId);
        cacheUtil.invalidByRedis(buildManagerKey(managerId));
        return manager;
    }

    public void verifyManager(Long id, Byte role, String nickname, String orgCode, String jobNo) {
        Manager manager = get(id);
        if (manager == null) return;
        manager.setRole(role);
        manager.setStatus(Enumerate.ManagerStatus.VERIFY.getType());
        manager.setNickname(nickname);
        manager.setJobNo(jobNo);
        manager.setOrgCode(orgCode);
        manager.setUpdateTime(new Date());
        update(manager);
        Long managerId = manager.getId();
        managerLoginRecordService.setInvaildByManagerId(managerId);
        cacheUtil.invalidByRedis(buildManagerKey(managerId));
    }

    public Integer countForUpdate(Long id, String jobNo, String orgCode){
        return managerMapper.countForUpdate(id, jobNo, orgCode);
    }

    public void getManagerOtherInfo(Manager manager) {
        getManagerOtherInfo(manager, null);
    }

    public void getManagerOtherInfo(Manager manager, Byte role) {
        if (manager == null) return;
        String orgCode = manager.getOrgCode();
        ResponseDTO responseDTO = branchService.getByOrgCode(orgCode);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return;

        Integer count = managerMapper.countByOrgCodeAndStatus(orgCode, Enumerate.ManagerStatus.VERIFY.getType());
        if (count != null && count > 0) manager.setVerifyNo(count);

        Branch branch = (Branch) responseDTO.getT2();
        List<Report> reports = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        manager.setProvinceName(branch.getProvince());
        manager.setOrgType(branch.getType());
        manager.setOrgTypeStr(CommonEnum.BranchType.getNameByCode(branch.getType()));
        if (role != null && Enumerate.ManagerRole.provinceCode.getCode().equals(role)) {
            manager.setOrgName(branch.getBranchName());
            reports = orgReportMapper.findByCodeAndType(null, null, branch.getProvinceCode(), CommonEnum.OrgReportEnum.province.getCode(),
                    DateUtil.toDay(calendar.getTime()), 1);
        } else {
            if (CommonEnum.BranchType.BRANCH.getCode().equals(branch.getType())) {
                manager.setOrgName(branch.getBranchName());
                reports = orgReportMapper.findByCodeAndType(null, orgCode, null, CommonEnum.OrgReportEnum.branch.getCode(),
                        DateUtil.toDay(calendar.getTime()), 1);
            } else {
                manager.setOrgName(branch.getTerminalName());
                reports = orgReportMapper.findByCodeAndType(orgCode, null, null, CommonEnum.OrgReportEnum.terminal.getCode(),
                        DateUtil.toDay(calendar.getTime()), 1);
            }
        }

        if (reports != null && reports.size() > 0) {
            Report r = reports.get(0);
            manager.setSignNo(r.getSendNo().intValue());
            manager.setCollectNo(r.getCollectNo().intValue());
            manager.setFailNo(r.getFailedNo().intValue());
        }
        String jobNo = manager.getJobNo();
        User user = userService.findByJobNo(jobNo);
        if (user != null) {
            manager.setJobNoName(user.getNickname());
            manager.setJobNoOrgCode(user.getOrgCode());
            responseDTO = branchService.getByOrgCode(user.getOrgCode());
            if (responseDTO.getCodeEnum() != CodeEnum.C1000) return;
            branch = (Branch) responseDTO.getT2();
            manager.setJobNoOrgTypeStr(CommonEnum.BranchType.getNameByCode(branch.getType()));
            manager.setJobNoOrgName(CommonEnum.BranchType.BRANCH.getCode().equals(branch.getType())?
                    branch.getBranchName():branch.getTerminalName());
        }
    }

    public Page<Manager> findManagerPage(Map<String, Object> map, Integer pageNumber, Integer pageSize){
        Integer totalCount = managerMapper.countManager(map);
        Page<Manager> page = new Page<Manager>();
        Integer index = null;
        if (pageNumber != null && pageSize != null) index = (pageNumber - 1) * pageSize;
        map.put("index", index);
        map.put("limit", pageSize);
        List<Manager> managers = managerMapper.findManagerPage(map);
        if (pageNumber != null && pageSize != null){
            page.setPageNo(pageNumber);
            page.setPageSize(pageSize);
        }
        page.setTotalCount(totalCount);
        page.setResult(managers);
        return page;
    }

    public ResponseDTO findWorkMatePage(String uuid, Integer pageNumber, Integer pageSize){
        ResponseDTO responseDTO = getManagerByUuid(uuid);
        if(responseDTO.getCodeEnum()!=CodeEnum.C1000) return responseDTO;
        Manager manager = (Manager) responseDTO.getT2();

        Map<String, Object> map = new HashMap<>();
        map.put("orgCode", manager.getOrgCode());
        map.put("role", manager.getRole());
        map.put("status", Enumerate.ManagerStatus.VERIFY.getType());
        map.put("id", manager.getId());
        Page<Manager> managerPage = findManagerPage(map, pageNumber, pageSize);
        if (managerPage != null && managerPage.getResult() != null) {
            return new ResponseDTO(CodeEnum.C1000, managerPage.getResult(), managerPage.getTotalCount());
        }
        return new ResponseDTO(CodeEnum.C1000);
    }
}
