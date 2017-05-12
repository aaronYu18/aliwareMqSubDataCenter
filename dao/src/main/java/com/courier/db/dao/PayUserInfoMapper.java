package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.PayUserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface PayUserInfoMapper extends BaseMapper<PayUserInfo> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public PayUserInfo get(PayUserInfo obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<PayUserInfo> findBy(PayUserInfo obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<PayUserInfo> findAll(PayUserInfo obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    int logicDelByUserId(@Param("userId")Long userId);

    List<PayUserInfo> findByUIdAndStatus(@Param("userId")Long userId, @Param("status")boolean status, @Param("channelType") String channelType);
}
