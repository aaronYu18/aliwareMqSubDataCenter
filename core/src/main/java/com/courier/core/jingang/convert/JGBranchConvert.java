package com.courier.core.jingang.convert;

import com.courier.commons.enums.CommonEnum;
import com.courier.commons.model.jinGang.JGBranchResult;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.convert.CommonConvert;
import com.courier.core.resp.courier.ResponseDTO;
import com.courier.core.service.BranchService;
import com.courier.db.dao.BranchMapper;
import com.courier.db.entity.Branch;
import com.courier.db.entity.CollectOrder;
import com.courier.db.entity.Region;
import com.courier.sdk.constant.CodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bin on 2015/10/29.
 */
public class JGBranchConvert {
    private static final Logger logger = LoggerFactory.getLogger(JGBranchConvert.class);

    /**
     * 把金刚的对象 转换成数据库的金刚组织结构对象
     */
    public static Branch convertObj(CacheUtil cacheUtil, JGBranchResult obj, String orgCode, BranchMapper branchMapper) {
        if (obj == null) return null;

        Branch branch = new Branch();

        String pCode = obj.getProvinceCode();
        String cCode = obj.getCityCode();
        String aCode = obj.getAreaCode();
        JGBranchResult.Type compType = obj.getCompType();
        String parentCode = obj.getComPare();
        String compCode = obj.getCompCode();
        String compName = obj.getCompName();

        if( StringUtils.isEmpty(parentCode) || compType == null){
            logger.error("mandatory parameter is empty, comPare is {}, compType is {}", parentCode, compType);
            return null;
        }
        if(StringUtils.isEmpty(pCode) || "null".equalsIgnoreCase(pCode) || StringUtils.isEmpty(cCode) || "null".equalsIgnoreCase(cCode)){
            if(!JGBranchResult.Type.BRANCH.equals(compType)){
                Branch parentBranch = branchMapper.findByOrgCode(parentCode);
                if (parentBranch == null) return null;

                if(org.springframework.util.StringUtils.isEmpty(compName) || "null".equalsIgnoreCase(compName))
                    compName = parentBranch.getBranchName();

                pCode = parentBranch.getProvinceCode();
                cCode = parentBranch.getCityCode();
            }else{
                logger.error("mandatory parameter is empty, pCode is {}, cCode is {}, comPare is {}, compType is {}", pCode, cCode, parentCode, compType);
                return null;
            }
        }


        Region pRegion = CommonConvert.provinceConvert(cacheUtil, pCode, null);
        Region cRegion = CommonConvert.cityConvert(cacheUtil, pCode, cCode);
        Region aRegion = CommonConvert.areaConvert(cacheUtil, pCode, aCode);


        //  todo 不管是分部还是分公司（compCode、compName为必传字段）
        if(pRegion.getCode() == null || cRegion.getCode() == null){
            logger.error("province code or city code is error, pCode is {}, cCode is {}", pCode, cCode);
            return null;
        }

        String terminalCode = obj.getTerminalCode();
        branch.setOrgCode(orgCode);

        branch.setTerminalCode(terminalCode);
        branch.setTerminalName(obj.getTerminalName());
        branch.setBranchName(compName);
        //  省市区县信息
        branch.setProvince(pRegion.getName());
        branch.setCity(cRegion.getName());
        branch.setArea(aRegion.getName());

        branch.setProvinceCode(pRegion.getCode());
        branch.setCityCode(cRegion.getCode());
        branch.setAreaCode(aRegion.getCode());

        // todo 判断branch类型
        if(JGBranchResult.Type.BRANCH.equals(compType)){
            branch.setType(CommonEnum.BranchType.BRANCH.getCode());
            branch.setBranchCode(compCode);
        } else{
            branch.setBranchCode(parentCode);
            branch.setType(CommonEnum.BranchType.TERMINAL.getCode());
        }

        return branch;
    }

}
