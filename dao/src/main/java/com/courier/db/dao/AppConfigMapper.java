package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.AppConfig;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface AppConfigMapper extends BaseMapper<AppConfig> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public AppConfig get(AppConfig obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<AppConfig> findBy(AppConfig obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<AppConfig> findAll(AppConfig obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);
}
