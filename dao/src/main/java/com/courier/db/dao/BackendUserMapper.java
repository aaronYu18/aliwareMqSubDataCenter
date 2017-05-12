package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.BackendUser;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface BackendUserMapper extends BaseMapper<BackendUser> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public BackendUser get(BackendUser obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<BackendUser> findBy(BackendUser obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<BackendUser> findAll(BackendUser admin, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

}
