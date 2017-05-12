package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VReportTotalNum;
import com.courier.db.vModel.VYtoData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface ReportMapper extends BaseMapper<Report> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public Report get(Report obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<Report> findBy(Report obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<Report> findAll(Report obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<Report> findByFilters(Map<String, Object> filters);

    Report findOneByKeys(Map<String, Object> filters);

    int countByFilters(Map<String, Object> filters);

    int batchInsert(List<Report> reports);

    int batchDelByType(@Param("code")byte code);

    VYtoData findYtoData(@Param("userId")Long userId);

    List<Report> findByUidTypeAndCreateTime(@Param("userId")Long userId, @Param("type")byte type, @Param("beginT")String beginT);

    List<Report> buildByFilters(Map<String, Object> filters);

    int delDataForInsert();

    Report findMonthAvg(Map<String, Object> filters);

    List<Report> findThisMonth(@Param("userId") Long userId,@Param("time") Date time);

    Report findThisDay(@Param("userId") Long userId, @Param("date") String date);

    List<Report> findStatByMonths(@Param("userId") Long userId ,@Param("labels") List<String> labels);

    Report findByUserIds(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userIds") Long[] userIds);

    List<Report> findDetail(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userIds") Long[] userIds);

    List<Long> findFrequentUIds(@Param("minSendNo") int minSendNo, @Param("beginT") Date beginT, @Param("endT") Date endT);

    /** userId 分组查询*/
    List<VReportTotalNum> findReportGroupByUserId(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userIds") Set<Long> userIds);
    /**通过 (0:省区网管;1:分公司;2:分部) 查询*/
    VReportTotalNum findTotalByRegionRole(@Param("beginDate") String beginDate,@Param("endDate") String endDate,@Param("userIds") Set<Long> userIds);
    /** 时间 分组查询*/
    List<VReportTotalNum> findReportGroupByTime(@Param("beginDate") String beginDate,@Param("endDate") String endDate,@Param("userIds") Set<Long> userIds);

    List<VReportTotalNum>  findReportGroupByUserIdDesc(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("orgCode") String orgCode ,
                                                       @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);


    VReportTotalNum queryReportByUserId(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("userId") Long userId);

}
