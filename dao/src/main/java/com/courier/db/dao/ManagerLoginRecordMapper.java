package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.ManagerLoginRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by vincent on 16/4/21.
 */
public interface ManagerLoginRecordMapper extends BaseMapper<ManagerLoginRecord> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    ManagerLoginRecord get(ManagerLoginRecord obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<ManagerLoginRecord> findBy(ManagerLoginRecord obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<ManagerLoginRecord> findAll(ManagerLoginRecord obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<ManagerLoginRecord> findByManagerAndStatus(@Param("managerId") Long managerId, @Param("status") Boolean status);

    ManagerLoginRecord findValidRecordByInvalidUuid(@Param("uuid") String uuid);

    ManagerLoginRecord findByUuid(@Param("uuid") String uuid);
}
