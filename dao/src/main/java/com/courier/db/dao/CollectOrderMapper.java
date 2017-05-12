package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.CollectOrder;
import com.courier.db.vModel.VCollectData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface CollectOrderMapper extends BaseMapper<CollectOrder> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public CollectOrder get(CollectOrder obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<CollectOrder> findBy(CollectOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<CollectOrder> findAll(CollectOrder obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

    int countByFilters(Map<String, Object> filters);

    List<CollectOrder> findByFilters(Map<String, Object> filters);

    List<CollectOrder> findBySourceKey(@Param("source")Byte source,@Param("sourceKey")String sourceKey);

    int delInvalidData(@Param("minutes")int minutes);

    List<CollectOrder> findForSync(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int findForSyncTotalNum();

    void updateBatch(List<CollectOrder> orders);

    int backup(Date date);

    int history(Date date);

    int deleteBackup(Date date);

    int deleteHistory(Date date);

    CollectOrder findCollect(@Param("userId")Long userId, @Param("mailNo")String mailNo);

    List<CollectOrder> fuzzyCollect(@Param("userId")Long userId, @Param("mailNo")String mailNo);

    List<VCollectData> listGrabProvince(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") Integer index, @Param("limit") Integer limit);
    Integer countGrabProvince(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<VCollectData> listGrabOrg(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") Integer index, @Param("limit") Integer limit);
    Integer countGrabOrg(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<VCollectData> listGrabGather(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") Integer index, @Param("limit") Integer limit);
    Integer countGrabGather(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<VCollectData> listWaitCollect(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") Integer index, @Param("limit") Integer limit);
    Integer countWaitCollect(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<VCollectData> listWaitGrab(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") Integer index, @Param("limit") Integer limit);
    Integer countWaitGrab(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
}