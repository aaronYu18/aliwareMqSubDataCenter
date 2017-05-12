package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.PayUserInfo;
import com.courier.db.entity.ProvinceAuthPattern;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface ProvinceAuthPatternMapper extends BaseMapper<ProvinceAuthPattern> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public ProvinceAuthPattern get(ProvinceAuthPattern obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<ProvinceAuthPattern> findBy(ProvinceAuthPattern obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<ProvinceAuthPattern> findAll(ProvinceAuthPattern obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);
}
