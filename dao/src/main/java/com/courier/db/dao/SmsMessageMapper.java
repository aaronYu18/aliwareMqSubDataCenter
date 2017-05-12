package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.SmsMessage;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface SmsMessageMapper extends BaseMapper<SmsMessage> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public SmsMessage get(SmsMessage obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<SmsMessage> findBy(SmsMessage obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<SmsMessage> findAll(SmsMessage obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    public void batchUpdateSMSMessageStatus(List lst);

    public void batchInsertSMSMessage(List lst);

}
