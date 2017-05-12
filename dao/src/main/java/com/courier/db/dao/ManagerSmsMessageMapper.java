package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.ManagerSmsMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by vincent on 16/4/21.
 */
public interface ManagerSmsMessageMapper extends BaseMapper<ManagerSmsMessage> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public ManagerSmsMessage get(ManagerSmsMessage obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<ManagerSmsMessage> findBy(ManagerSmsMessage obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<ManagerSmsMessage> findAll(ManagerSmsMessage obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    Integer countByPhoneAndDate(@Param("phone") String phone, @Param("date")String date);
}
