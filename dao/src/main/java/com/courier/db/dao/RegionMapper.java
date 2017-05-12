package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.Region;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface RegionMapper extends BaseMapper<Region> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public Region get(Region obj);

    @SelectProvider(type = CRUDTemplate.class, method = "update")
    @Override
    void update(Object obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<Region> findBy(Region obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<Region> findAll(Region obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<Region> findByParentCode(@Param("regionEnum") int regionEnum, @Param("codeKey") String codeKey, @Param("parentCode") String key);

    List<Region> findStaleRegionByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    int countStaleRegion();

    public Region getByCode(@Param("code")String code);

    public Region getByName(@Param("name")String name);
}
