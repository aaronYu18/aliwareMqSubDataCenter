package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.UserLoginRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface UserLoginRecordMapper extends BaseMapper<UserLoginRecord> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public UserLoginRecord get(UserLoginRecord obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<UserLoginRecord> findBy(UserLoginRecord obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<UserLoginRecord> findAll(UserLoginRecord obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    UserLoginRecord findNewestByCourierId(Long courierId);

//    void setInvaildByCourierId(@Param("updateTime")String dateStr,  @Param("courierId")Long courierId);

    List<UserLoginRecord> findByCourierAndStatus(@Param("userId") Long userId, @Param("status") Boolean status);

    Long findUserIdByUuid(@Param("uuid") String uuid);

    UserLoginRecord findByUuid(@Param("uuid") String uuid);

    UserLoginRecord findValidRecordByInvalidUuid(@Param("uuid") String uuid);

    UserLoginRecord findValidByUserId(@Param("userId") Long userId);

    int backup(Date date);

    int history(Date date);

    int deleteBackup(Date date);

    int deleteHistory(Date date);


}