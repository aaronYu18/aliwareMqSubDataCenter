package com.courier.core.convert;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.enums.CommonEnum;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.core.service.BranchService;
import com.courier.db.entity.Branch;
import com.courier.db.entity.DeliveryOrder;
import com.courier.db.entity.Region;
import com.courier.sdk.constant.CodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2015/12/11.
 */
public class CommonConvert {
    public static final Logger logger = LoggerFactory.getLogger(CommonConvert.class);
    private final static Map<String, String> pRelations = new HashMap<>();//金刚直辖市/特别行政区(省级)编号关联行者直辖市/特别行政区(省级)编号
    private final static Map<String, String> cRelations = new HashMap<>();//金刚没传cityCode时，金刚直辖市(省级)编号关联行者市级编号
    private final static Map<String, String> pcRelations = new HashMap<>();//金刚市级编号关联行者市级编号
//    private final static Set<String> sars = new HashSet<>();//特别行政区(pCode为特别行政区时,cCode,aCode不入库)

    static {
        pRelations.put("010", "110000");//北京市
        pRelations.put("023", "500000");//重庆市
        pRelations.put("021", "310000");//上海市
        pRelations.put("022", "120000");//天津市
        pRelations.put("00852", "810000");//香港
        pRelations.put("00853", "820000");//澳门
        pRelations.put("886001", "710000");//台湾

        pcRelations.put("010", "110100");//北京市 市辖区
        pcRelations.put("023", "500100");//重庆市 市辖区
        pcRelations.put("021", "310100");//上海市 市辖区
        pcRelations.put("022", "120100");//天津市 市辖区

        cRelations.put("110000", "110100");//北京市 市辖区
        cRelations.put("500000", "500100");//重庆市 市辖区
        cRelations.put("310000", "310100");//上海市 市辖区
        cRelations.put("120000", "120100");//天津市 市辖区

      /*  sars.add("00852");//香港
        sars.add("00853");//澳门
        sars.add("886001");//台湾*/
    }



    /**
     * 特殊省转化
     * 1. 查省： relations = null
     */
    public static Region provinceConvert(CacheUtil cacheUtil, String code, Map<String, String> relations){
        if (StringUtils.isEmpty(code) || "N/A".equals(code)) return new Region(null, null);

        if(relations == null) relations = pRelations;
        if(relations.containsKey(code))
            code = relations.get(code);

        return getName(cacheUtil, code, CommonEnum.RegionEnum.PROVINCE);
    }



    /**
     * 特殊市转化
     * 如果cCode为空，则根据pCode 和 pcRelations查询相关数据； 如果不为空；则根据cCode查询
     */
    public static Region cityConvert(CacheUtil cacheUtil, String pCode, String cCode){
        if(StringUtils.isEmpty(cCode)) return provinceConvert(cacheUtil, pCode, pcRelations);

        if(cRelations.containsKey(cCode))
            cCode = cRelations.get(cCode);

        return getName(cacheUtil, cCode, CommonEnum.RegionEnum.CITY);
    }


    public static Region areaConvert(CacheUtil cacheUtil, String pCode, String code) {
        if (StringUtils.isEmpty(code) || "N/A".equals(code)) return new Region(null, null);

        return getName(cacheUtil, code, CommonEnum.RegionEnum.AREA);
    }

    /**
     * 省市区转化，如果省或市为空，返回网点省市区
     */
    public static void pcaConvertWithDeliveryOrder(Branch branch, CacheUtil cacheUtil, String pCode, String cCode, String aCode,
                                                   DeliveryOrder deliveryOrder){
        Region pRegion = provinceConvert(cacheUtil, pCode, null);
        Region cRegion = cityConvert(cacheUtil, pCode, cCode);
        Region aRegion = areaConvert(cacheUtil, pCode, aCode);
        deliveryOrder.setReceiverProvince(pRegion.getCode());
        deliveryOrder.setReceiverProvinceName(pRegion.getName());
        deliveryOrder.setReceiverCity(cRegion.getCode());
        deliveryOrder.setReceiverCityName(cRegion.getName());
        deliveryOrder.setReceiverArea(aRegion.getCode());
        deliveryOrder.setReceiverAreaName(aRegion.getName());

        if (StringUtils.isEmpty(pRegion.getCode())||StringUtils.isEmpty(cRegion.getCode())) {
            if (branch == null) return;
            deliveryOrder.setReceiverProvince(branch.getProvinceCode());
            deliveryOrder.setReceiverProvinceName(branch.getProvince());
            deliveryOrder.setReceiverCity(branch.getCityCode());
            deliveryOrder.setReceiverCityName(branch.getCity());
            deliveryOrder.setReceiverArea(branch.getAreaCode());
            deliveryOrder.setReceiverAreaName(branch.getArea());
        }
    }

    public static String getNameByCode(CacheUtil cacheUtil, String code) {
        Region region = getName(cacheUtil, code, null);
        String name = region.getName();

        return name;
    }

    /************** begin to private method *************************/

    /**
     * 根据code 从标准region获取中文名
     */
    private static Region getName(CacheUtil cacheUtil, String code, CommonEnum.RegionEnum regionEnum) {
        String name = getNameFromStandardRegion(cacheUtil, code);
        // todo 先从标准库中查，查到直接返回；
        if(!StringUtils.isEmpty(name))
            return new Region(code, name);

        // todo 查不到再从金刚code表中查，查到code设为null,直接返回；查不到都设为null,返回
        name = getNameFromJGRegion(cacheUtil, code);
        if(!StringUtils.isEmpty(name))
            return new Region(null, name);

//        logger.error("convert jinGang region to local region error, type is {}, code is {} ", regionEnum == null ? "unknown" : regionEnum.name(), code);

        return new Region(null, null);
    }

    /**
     * 根据code 从标准region获取中文名
     */
    private static String getNameFromStandardRegion(CacheUtil cacheUtil, String code) {
        if(StringUtils.isEmpty(code)) return null;
        return cacheUtil.getDataFromMapWithLocal(CacheConstant.ALL_REGIONS_KEY, code, String.class);
    }
    /**
     * 根据code 从金刚无效表中获取中文名
     */
    private static String getNameFromJGRegion(CacheUtil cacheUtil, String code) {
        if(StringUtils.isEmpty(code)) return null;
        return cacheUtil.getDataFromMapWithLocal(CacheConstant.ALL_STALE_REGIONS_KEY, code, String.class);
    }

    public static String replaceNullIfNA(String input){
        if ("N/A".equals(input)) return null;
        return input;
    }
}
