package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.FAQ;
import com.courier.db.entity.FaqVersion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface FaqMapper extends BaseMapper<FAQ> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public FAQ get(FAQ obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<FAQ> findBy(FAQ obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    public List<FAQ> findAll(FAQ object, List<FAQ> searchFilterList, ExtSqlProp sqlProp);

    List<FAQ> findCategory();

    List<FAQ> findByParent(@Param("path")String path);
}
