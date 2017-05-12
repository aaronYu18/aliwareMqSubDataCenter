package com.courier.db.dao;

import com.courier.commons.vModel.VUserIncrementCount;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface UserMapper extends BaseMapper<User> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public User get(User obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<User> findBy(User obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<User> findAll(User user, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    List<User> findByAccount(String jobNo);

    public String findJobNoByPhone(String... phones);

    User findByOrgCodeJobNo(@Param("orgCode")String orgCode, @Param("jobNo")String jobNo);

    List<User> findByOrgCode(String orgCode);

    public String findJobNoByUserId(Long... userIds);

    List<User> findValidUser();

    List<User> findForSystemLogin(@Param("password")String defaultPassword);

    int countValidNo(@Param("endT") String endT);

    List<User> findForSyncByPage(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countForSysLogin(@Param("defaultPwd") String defaultPwd);

    List<User> findForSysLogin(@Param("defaultPwd") String defaultPwd, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<VUserIncrementCount> countIncrementByTime(@Param("beginT") String beginT, @Param("endT") String endT, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countTotalPageNo(@Param("beginT") String beginT, @Param("endT") String endT);

    int countIncrementNo(@Param("beginT") String beginT, @Param("endT") String endT);

    void updateBindPay(@Param("userId") Long userId, @Param("bindPay") Integer bindPay);

    Set<Long> findByOrgCodes(@Param("orgCodes") List<String> orgCodes,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countByOrgCode(@Param("orgCode") String orgCode);

    List<User> findPageByOrgCode(@Param("orgCode") String orgCode, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);

    List<String> findJobNosByOrgCode(@Param("orgCode") String orgCode);

}
