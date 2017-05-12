package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.DeliveryOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface DeliveryOrderMapper extends BaseMapper<DeliveryOrder> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public DeliveryOrder get(DeliveryOrder obj);

    @SelectProvider(type = CRUDTemplate.class,method = "update")
    @Override
    void update(Object obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<DeliveryOrder> findBy(DeliveryOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<DeliveryOrder> findAll(DeliveryOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    int countByFilters(Map<String, Object> filters);

    List<DeliveryOrder> findByFilters(Map<String, Object> filters);

    List<DeliveryOrder> findByUIdAndMailNo(@Param("userId") Long userId, @Param("mailNo") String mailNo);

    int batchUpdateByUidAndMailNo(List<DeliveryOrder> orders);

    int batchInsert(List<DeliveryOrder> orders);

    void batchDelete(List<DeliveryOrder> orders);

    DeliveryOrder findDelivery(@Param("userId") Long userId, @Param("mailNo") String mailNo);

    int countHistoryByFilters(Map<String, Object> filters);

    List<DeliveryOrder> findHistoryByFilters(Map<String, Object> filters);

    int batchDeleteByMailNoAndStatus(List<String> mailNos);
}
