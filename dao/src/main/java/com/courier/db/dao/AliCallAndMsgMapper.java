package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.AliCallAndMsg;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Ryan
 */
public interface AliCallAndMsgMapper extends BaseMapper<AliCallAndMsg> {

    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public AliCallAndMsg get(AliCallAndMsg obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<AliCallAndMsg> findBy(AliCallAndMsg obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<AliCallAndMsg> findAll(AliCallAndMsg obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    public void batchInsertAliCallAndMsg(List lst);

}
