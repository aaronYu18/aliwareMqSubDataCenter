package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.PayReport;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VYtoData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface PayReportMapper extends BaseMapper<PayReport> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public PayReport get(PayReport obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<PayReport> findBy(PayReport obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<PayReport> findAll(PayReport obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    int batchInsert(List<PayReport> reports);

    List<PayReport> buildByFilters(Map<String, Object> filters);

    int delDataForInsert();

    PayReport findMonthAvg(Map<String, Object> filters);

    List<PayReport> findThisMonth(@Param("userId") Long userId,@Param("time") Date time);

    PayReport findThisDay(@Param("userId") Long userId,@Param("date") String date);
}
