package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.SourceClientIdRelation;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface SourceClientIdRelationMapper extends BaseMapper<SourceClientIdRelation> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public SourceClientIdRelation get(SourceClientIdRelation obj);

    @SelectProvider(type = CRUDTemplate.class,method = "update")
    @Override
    void update(Object obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<SourceClientIdRelation> findBy(SourceClientIdRelation obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<SourceClientIdRelation> findAll(SourceClientIdRelation obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);


}
