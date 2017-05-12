package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.ManageAppVersion;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface ManageAppVersionMapper extends BaseMapper<ManageAppVersion> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public ManageAppVersion get(ManageAppVersion obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<ManageAppVersion> findBy(ManageAppVersion obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<ManageAppVersion> findAll(ManageAppVersion obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);


    public ManageAppVersion getAppVersion(String type);

    String getByType(Byte type);
}
