package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.AppVersion;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface AppVersionMapper extends BaseMapper<AppVersion> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public AppVersion get(AppVersion obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<AppVersion> findBy(AppVersion obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<AppVersion> findAll(AppVersion obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);


    public AppVersion getAppVersion(String type);

    String getByType(Byte type);
}
