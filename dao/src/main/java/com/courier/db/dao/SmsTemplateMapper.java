package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.SmsTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public SmsTemplate get(SmsTemplate obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<SmsTemplate> findBy(SmsTemplate obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<SmsTemplate> findAll(SmsTemplate obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    SmsTemplate findByType(@Param("type")Byte type);
}
