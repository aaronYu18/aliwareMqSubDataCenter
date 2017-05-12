package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.model.jinGang.JGBaseModelArray;
import com.courier.commons.model.jinGang.JGBaseModelBranchInfo;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.cache.UserBean;
import com.courier.core.jingang.Interfaces;
import com.courier.core.jingang.convert.JGBranchConvert;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.vModel.VHomePage;
import com.courier.db.dao.BranchMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.Page;
import com.courier.db.entity.Branch;
import com.courier.db.entity.Report;
import com.courier.db.entity.User;
import com.courier.db.vModel.VBranch;
import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.constant.Enumerate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by bin on 2015/10/29.
 */
@Service
@Transactional
public class BranchService extends BaseManager<Branch> {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryOrderService.class);
    static final String status = "error";
    @Autowired
    private BranchMapper branchMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    public Branch getEntity() {
        return new Branch();
    }

    @Override
    public BaseMapper<Branch> getBaseMapper() {
        return branchMapper;
    }


    @Value("${jingang.branch.info.url}")
    private String jgBranchInfoUrl;
    @Value("${jingang.branch.info.md5}")
    private String branchInfoMd5;
    @Autowired
    CacheUtil cacheUtil;
    @Value("${jingang.getClientID.info.url}") //辅助电子面单
    private String clientIdURl;
    @Value("${jingang.getClientID.info.md5}") //辅助电子面单
    private String clientIdMd5;

    /**
     * 通过网点代码获取网点组织结构
     */
    public ResponseDTO getByOrgCode(String orgCode) {

        if(StringUtils.isEmpty(orgCode)) return new ResponseDTO(CodeEnum.C1036);
        Branch branch = cacheUtil.getDataFromRedisMap(CacheConstant.BRANCH_KEY, orgCode, Branch.class);
        if (branch != null)
            return new ResponseDTO(CodeEnum.C1000, null, branch);

        branch = branchMapper.findByOrgCode(orgCode);
        if (branch != null) {
            cacheUtil.putData2RedisMap(CacheConstant.BRANCH_KEY, branch.getOrgCode(), branch);
            return new ResponseDTO(CodeEnum.C1000, null, branch);
        }

        // todo 数据库无
        Interfaces interfaces = new Interfaces();
        JGBaseModelBranchInfo baseModel = interfaces.getBranchInfo(jgBranchInfoUrl, orgCode, branchInfoMd5);
        if (baseModel == null || status.equalsIgnoreCase(baseModel.getStatus()))
            return new ResponseDTO(CodeEnum.C2018);

        branch = JGBranchConvert.convertObj(cacheUtil, baseModel.getObject(), orgCode, branchMapper);
        if(branch == null) return new ResponseDTO(CodeEnum.C1036);

        // todo 获取客户编码
        String customerCode = getCustomerCode(orgCode);
        if(!StringUtils.isEmpty(customerCode))
            branch.setCustomerCode(customerCode);
        save(branch);
        cacheUtil.putData2RedisMap(CacheConstant.BRANCH_KEY, branch.getOrgCode(), branch);
        return new ResponseDTO(CodeEnum.C1000, null, branch);
    }

    /**
     * 通过网点代码获取网点组织结构
     */
    public boolean updateInfo(Branch branch) {
        String orgCode = branch.getOrgCode();

        Interfaces interfaces = new Interfaces();
        JGBaseModelBranchInfo baseModel = interfaces.getBranchInfo(jgBranchInfoUrl, orgCode, branchInfoMd5);
        if (baseModel == null || status.equalsIgnoreCase(baseModel.getStatus()))
            return false;

        Branch obj = JGBranchConvert.convertObj(cacheUtil, baseModel.getObject(), orgCode, branchMapper);
        if(obj == null) return false;

        if(buildKey(obj).equals(buildKey(branch))) return true;

        branch.setBranchCode(obj.getBranchCode());
        branch.setBranchName(obj.getBranchName());
        branch.setTerminalCode(obj.getTerminalCode());
        branch.setTerminalName(obj.getTerminalName());
        branch.setProvinceCode(obj.getProvinceCode());
        branch.setProvince(obj.getProvince());
        branch.setCity(obj.getCity());
        branch.setCityCode(obj.getCityCode());
        branch.setArea(obj.getArea());
        branch.setAreaCode(obj.getAreaCode());
        branch.setType(obj.getType());
        branch.setUpdateTime(new Date());

        update(branch);
        return true;
    }



    /**
     * 获取客户编码
     */
    public String getCustomerCode(String orgCode){
        Interfaces interfaces = new Interfaces();
        JGBaseModelArray clientID4MailNo = null;
        try {
            clientID4MailNo = interfaces.findClientID4MailNo(clientIdURl, orgCode, clientIdMd5);
        } catch (Exception e) {
            logger.error("request get customer code exception, orgCode is {}", orgCode);
            return null;
        }

        if (clientID4MailNo != null) {
            String customerCode = clientID4MailNo.getObject();
            if(StringUtils.isEmpty(customerCode))
                logger.error("get customer code failed, result is empty, orgCode is {}", orgCode);
            return customerCode;
        }

        return null;
    }
    /**
     * 根据uid获取branch
     */
    public Branch getBranchByUId(Long userId){
        if(userId == null || userId == 0l) return null;
        Branch branch = null;

        ResponseDTO responseDTO = userService.getUserBeanByUid(userId);
        if(responseDTO != null && CodeEnum.C1000 == responseDTO.getCodeEnum()){
            UserBean userBean = (UserBean) responseDTO.getT2();
            if(userBean != null) branch = userBean.getBranch();
        }

        if(branch != null) return branch;
        branch = branchMapper.getBranchByUId(userId);
        User user = userService.get(userId);

        if(branch != null && user != null){
            UserBean userBean = new UserBean(branch, user);
            userService.putToRedis(null, userBean, userId);
        }
        return branch;
    }

    public List<String> findNotExistsBranchCodes() {
        return branchMapper.findNotExistsBranchCodes();
    }

    /**
     * 获取省网下的所有分公司
     * @param orgCode
     * @return
     */
    public List<String> fetchCompanyByOrgCode(String orgCode, Byte code, Integer pageNo, Integer pageSize) {
        return branchMapper.fetchCompanyByOrgCode(orgCode,code,pageNo,pageSize);
    }

    /**
     * 获取orgcode省网下所有的分公司及分部
     * @param orgCode
     * @return
     */
    public List<String> fetchTerminalByOrgCode(String orgCode){
        return branchMapper.fetchTerminalByOrgCode(orgCode);
    }

    public Page<VBranch> findVBranchPage(String provinceCode, String orgCode, String beginDate, String endDate, Integer pageNumber, Integer pageSize){
        Integer totalCount = branchMapper.countVBranch(provinceCode, orgCode);
        Page<VBranch> page = new Page<VBranch>();
        Integer index = null;
        if (pageNumber != null && pageSize != null) index = (pageNumber - 1) * pageSize;
        List<VBranch> vBranches = branchMapper.findVBranchPage(provinceCode, orgCode, index, pageSize);
        for (VBranch vBranch : vBranches) {
            String org = vBranch.getOrgCode();
            List<User> users = userService.findByOrgCode(org);
            String[] jobNos = new String[users.size()];
            Long[] userIds = new Long[users.size()];
            for (int i = 0; i < users.size(); i++) {
                jobNos[i] = users.get(i).getJobNo();
                userIds[i] = users.get(i).getId();
            }
            vBranch.setCouriers(StringUtils.join(jobNos, ","));
            Report report = reportService.findByUserIds(beginDate, endDate, userIds);
            vBranch.setSendNo(report.getSendNo().intValue());
            vBranch.setCollectNo(report.getCollectNo().intValue());
            vBranch.setFailNo(report.getFailedNo().intValue());
        }
        if (pageNumber != null && pageSize != null){
            page.setPageNo(pageNumber);
            page.setPageSize(pageSize);
        }
        page.setTotalCount(totalCount);
        page.setResult(vBranches);
        return page;
    }

    public List<Report> findBranchTodayDetail(String orgCode) throws Exception {
        List<User> users = userService.findByOrgCode(orgCode);
        List<Report> reports = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            VHomePage vHomePage = homePageService.getHomePageData(user.getId());
            if (vHomePage == null) {
                vHomePage = statisticsService.getvHomePage(user.getId());
            }
            Report report = new Report();
            report.setJobNo(user.getJobNo());
            report.setSendNo((double) vHomePage.getSignNo());
            report.setCollectNo((double) vHomePage.getCollectNo());
            report.setFailedNo((double) vHomePage.getProblemNo());
            report.setSendingNo((double) vHomePage.getSendingNo());
            report.setCollectingNo((double) vHomePage.getCollectingNo());
            reports.add(report);
        }
        return reports;
    }

    public List<Report> findBranchOtherDetail(String beginDate, String endDate, String orgCode){
        List<User> users = userService.findByOrgCode(orgCode);
        if (CollectionUtils.isEmpty(users)) return null;
        Long[] userIds = new Long[users.size()];
        Map<Long, String> idJobNoMap = new HashedMap();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userIds[i] = user.getId();
            idJobNoMap.put(user.getId(), user.getJobNo());
        }
        List<Report> reports = reportService.findDetail(beginDate, endDate, userIds);
        for (Report report : reports) {
            report.setJobNo(idJobNoMap.get(report.getUserId()));
            idJobNoMap.remove(report.getUserId());
        }
        for (String noDataJobNo : idJobNoMap.values()) {
            Report report = new Report();
            report.setJobNo(noDataJobNo);
            report.setSendNo(0.);
            report.setCollectNo(0.);
            report.setFailedNo(0.);
            reports.add(report);
        }
        return reports;
    }

    public VBranch sumVBranch(String provinceCode, String orgCode, String beginDate, String endDate){
        if (StringUtils.isEmpty(provinceCode) && StringUtils.isEmpty(orgCode)) return null;
        VBranch branch = branchMapper.sumVBranch(provinceCode, orgCode, beginDate, endDate);
        if (branch == null) branch = new VBranch();
        return branch;
    }

    public int countCompanyByOrgCode(String orgCode, Byte type){
        return branchMapper.countCompanyByOrgCode(orgCode, type);
    }


    // todo  根据分公司code查询
    public List<String> findByBranchCode(String branchCode,Byte type, Integer pageNo, Integer pageSize) {
        List<String> set = new ArrayList<>();
        set.add(branchCode);
        return branchMapper.findByBranchCodes(set,type, pageNo, pageSize);
    }

    public List<String> findByBCodeType(String branchCode, Byte type) {
        return branchMapper.findByBCodeType(branchCode, type);
    }

    public Branch findByOrgCode(String orgCode) {
        if (StringUtils.isEmpty(orgCode)) return null;
        Branch branch = branchMapper.findByOrgCode(orgCode);
        return branch;
    }

    public int countTerminalsByBranchCode(String orgCode) {
        return branchMapper.countTerminalsByBranchCode(orgCode);
    }

    public List<String> findTerminalsByBranchCode(String orgCode, Integer pageNo, Integer pageSize) {
        return branchMapper.findTerminalsByBranchCode(orgCode, pageNo, pageSize);
    }

    List<String> findByPCodeAndType(String provinceCode, Byte type, Integer pageNo, Integer pageSize) {
        return branchMapper.findByPCodeAndType(provinceCode, type, pageNo, pageSize);
    }

    int countByPCodeAndType(String provinceCode, Byte type) {
        return branchMapper.countByPCodeAndType(provinceCode, type);
    }

    int countByBranchCode(String branchCode, Byte type) {
        return branchMapper.countByBranchCode(branchCode, type);
    }

    // todo 查找相应角色下的所有orgCode
    public List<String> getOrgCodes(Byte role, String orgCode) {
        String key = String.format(CacheConstant.GETORGCODES_ROLE_ORGCODE,role,orgCode);
        List<String> cacheList = cacheUtil.getCacheByFromRedis(key, List.class);
        if (cacheList!= null) return  cacheList;

        List<String> orgCodes = new ArrayList<>();
        if(role == Enumerate.ManagerRole.provinceCode.getCode().byteValue()){                    //  todo 省区网管
            orgCodes = fetchCompanyByOrgCode(orgCode, null, null, null);                          // 省区下的所有分公司分部

        }else{               //  todo 分公司 分部|直属网点
            orgCodes = findByBCodeType(orgCode, null);
        }
        if (!CollectionUtils.isEmpty(orgCodes))cacheUtil.putData2RedisByTime(key, Global.ONE_DAY_AGE, orgCodes);
        return orgCodes;
    }



    // todo  转换 提取 key
    private String buildKey(Branch obj) {
        if(obj == null) return "";
        String branchCode = obj.getBranchCode();
        String branchName = obj.getBranchName();
        String terminalCode = obj.getTerminalCode();
        String terminalName = obj.getTerminalName();
        String provinceCode = obj.getProvinceCode();
        String province = obj.getProvince();
        String cityCode = obj.getCityCode();
        String city = obj.getCity();
        String area = obj.getArea();
        String areaCode = obj.getAreaCode();
        Byte type = obj.getType();

        String key = branchCode + branchName + terminalCode + terminalName + provinceCode + province + cityCode + city + area + areaCode + type;

        return key;
    }

    public String queryOrgName(String orgCode, Byte managerRole){
        if (managerRole != null && orgCode != null) {
            ResponseDTO responseDTO = getByOrgCode(orgCode);
            if (responseDTO.getCodeEnum() != CodeEnum.C1000) return null;
            Branch branch = (Branch) responseDTO.getT2();
            if (Enumerate.ManagerRole.provinceCode.getCode().equals(managerRole)) {
                return branch.getProvince();
            } else if (Enumerate.ManagerRole.companyCode.getCode().equals(managerRole)){
                return branch.getBranchName();
            } else if (Enumerate.ManagerRole.branchCode.getCode().equals(managerRole)){
                return branch.getTerminalName();
            }
        }
        return null;
    }

    /**
     * 仅仅支持 分公司 分部的 名称
     * 针对直属分部 分部名称为空，返回分公司名称
     * @param orgCode
     * @return
     */
    public String queryOrgName(String orgCode){
        if (StringUtils.isEmpty(orgCode)) return null;
        ResponseDTO responseDTO = getByOrgCode(orgCode);
        if (responseDTO.getCodeEnum() != CodeEnum.C1000) return null;
        Branch branch = (Branch) responseDTO.getT2();
        if (!StringUtils.isEmpty(branch.getTerminalName())) return branch.getTerminalName();
        if (!StringUtils.isEmpty(branch.getBranchName())) return branch.getBranchName();
        return null;
    }
}
