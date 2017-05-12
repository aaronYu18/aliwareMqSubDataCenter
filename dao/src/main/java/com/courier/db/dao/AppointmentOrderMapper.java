package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.AppointmentOrder;

import org.apache.ibatis.annotations.SelectProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beyond on 2016/12/26.
 */
public interface AppointmentOrderMapper extends BaseMapper<AppointmentOrder> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public AppointmentOrder get(AppointmentOrder obj);

    @SelectProvider(type = CRUDTemplate.class,method = "update")
    @Override
    void update(Object obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<AppointmentOrder> findBy(AppointmentOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<AppointmentOrder> findAll(AppointmentOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    int batchUpdateByIds(ArrayList<AppointmentOrder> orders);
}
