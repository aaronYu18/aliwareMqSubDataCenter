package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.OrgReport;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VReportTotalNum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface OrgReportMapper extends BaseMapper<OrgReport> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public OrgReport get(OrgReport obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<OrgReport> findBy(OrgReport obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<OrgReport> findAll(OrgReport obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    void deleteDynaData();

    void insertTerminalData(@Param("countT")Date countT, @Param("type")byte type);

    void insertBranchData(@Param("countT")Date countT, @Param("type")byte type, @Param("nextLevelType")byte nextLevelType);

    void insertProvinceData(@Param("countT")Date countT, @Param("type")byte type, @Param("nextLevelType")byte nextLevelType);

    void insertAvgData(Map<String, String> filters);

    void insertMonthTotalData(Map<String, String> filters);

    List<Report> findOneByKeys(Map<String, Object> filters);

    List<VReportTotalNum> findOrgReportGroupByTime(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("type") Byte type,
                                                   @Param("provinceCode") String provinceCode,
                                                   @Param("branchCode") String branchCode,
                                                   @Param("orgCode") String orgCode);
    VReportTotalNum countOrgReportGroupByTime(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("type") Byte type,
                                                   @Param("provinceCode") String provinceCode,
                                                   @Param("branchCode") String branchCode,
                                                   @Param("orgCode") String orgCode);
    List<VReportTotalNum> findTotalByRegionRole(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("type") Byte type,
                                                @Param("branchCodes") List<String> branchCodes,
                                                @Param("orgCodes") List<String> orgCodes);

    VReportTotalNum findByOrgAndBranchCode(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("branchCode") String branchCode);

    List<Report> findByCodeAndType(@Param("orgCode")String orgCode, @Param("branchCode")String branchCode,
                                   @Param("provinceCode")String provinceCode, @Param("type")byte type, @Param("beginT")String beginT, @Param("limit")Integer limit);
    List<VReportTotalNum> findMyWorkByBranchDesc(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("branchCode") String branchCode, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<VReportTotalNum> findMyWorkByProvinceDesc(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("provinceCode") String provinceCode, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    VReportTotalNum queryByRegionKey(@Param("type") Byte type, @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                     @Param("provinceCode") String provinceCode, @Param("branchCode") String branchCode, @Param("key") String key);

    List<OrgReport> findBySort(@Param("type") Byte type, @Param("beginT") Date beginT, @Param("endT") Date endT, @Param("index") Integer index, @Param("limit") Integer limit);

    Integer countBySort(@Param("type") Byte type, @Param("beginT") Date beginT, @Param("endT") Date endT);
}
