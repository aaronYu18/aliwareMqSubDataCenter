package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.PayOperationInfo;
import com.courier.db.entity.PayReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface PayOperationInfoMapper extends BaseMapper<PayOperationInfo> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public PayOperationInfo get(PayOperationInfo obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<PayOperationInfo> findBy(PayOperationInfo obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<PayOperationInfo> findAll(PayOperationInfo obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<PayReport> findReportsByTime(Map<String, Object> filters);

    Double getTodayProfile(@Param("userId") Long userId);
}
