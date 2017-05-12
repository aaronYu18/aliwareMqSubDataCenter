package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 16/4/21.
 */
public interface ManagerMapper extends BaseMapper<Manager> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    Manager get(Manager obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<Manager> findBy(Manager obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<Manager> findAll(Manager obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<Manager> findByAccount(String username);

    List<Manager> findByJobNo(String jobNo);

    Integer countForUpdate(@Param("id") Long id, @Param("jobNo") String jobNo, @Param("orgCode") String orgCode);

    Integer countByOrgCodeAndStatus(@Param("orgCode") String orgCode, @Param("status") Byte status);

    Integer countManager(Map<String, Object> map);

    List<Manager> findManagerPage(Map<String, Object> map);

}
