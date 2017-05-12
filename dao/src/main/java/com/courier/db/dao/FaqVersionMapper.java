package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.FaqVersion;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface FaqVersionMapper extends BaseMapper<FaqVersion> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public FaqVersion get(FaqVersion obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<FaqVersion> findBy(FaqVersion obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    public List<FaqVersion> findAll(FaqVersion object, List<FaqVersion> searchFilterList, ExtSqlProp sqlProp);

}
