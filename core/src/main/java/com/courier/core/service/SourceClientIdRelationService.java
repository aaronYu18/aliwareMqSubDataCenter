package com.courier.core.service;

import com.courier.commons.constant.CacheConstant;
import com.courier.commons.constant.Global;
import com.courier.commons.util.cache.CacheUtil;
import com.courier.commons.util.json.GsonUtil;
import com.courier.db.dao.SourceClientIdRelationMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.SourceClientIdRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class SourceClientIdRelationService extends BaseManager<SourceClientIdRelation> {
    private static final Logger logger = LoggerFactory.getLogger(SourceClientIdRelationService.class);

    @Autowired
    private SourceClientIdRelationMapper sourceClientIdRelationMapper;
    @Autowired
    CacheUtil cacheUtil;

    @Override
    public SourceClientIdRelation getEntity() {
        return new SourceClientIdRelation();
    }

    @Override
    public BaseMapper<SourceClientIdRelation> getBaseMapper() {
        return sourceClientIdRelationMapper;
    }


    /**
     * 根据source查找
     */
    public Byte findSourceByClientId(String clientId) {
        if (StringUtils.isEmpty(clientId)) return null;
        String value = cacheUtil.getDataFromRedisMap(CacheConstant.SOURCEID_CLIENTID_RELATION_REVERSAL, clientId, String.class);
        if (value != null) return Byte.valueOf(value);

        SourceClientIdRelation relation = getByClientId(clientId);
        if (relation != null) {
            List<SourceClientIdRelation> list = new ArrayList<>();
            list.add(relation);
            putRelationToRedis(list);
            return relation.getSource();
        }

        return null;
    }

    /**
     * 根据source查找
     */
    public String findClientIdBySource(Byte source) {
        String clientId = "";
        if (source == null) return clientId;
        String value = cacheUtil.getDataFromRedisMap(CacheConstant.SOURCEID_CLIENTID_RELATION, source.toString(), String.class);
        if (!StringUtils.isEmpty(value)) {
            logger.info("findClientIdBySource read redis message:{}", clientId);
            return value;
        }
        SourceClientIdRelation relation = getBySource(source);
        if (relation != null) {
            clientId = relation.getClientId();
            List<SourceClientIdRelation> list = new ArrayList<>();
            list.add(relation);
            putRelationToRedis(list);
        }
        logger.info("findClientIdBySource read db message:{}", clientId);
        return clientId;
    }

    public void initAllRelationsToRedis() {
        List<SourceClientIdRelation> list = sourceClientIdRelationMapper.findAll(new SourceClientIdRelation(), null, null);
        if (!cacheUtil.isExistByRedis(CacheConstant.SOURCEID_CLIENTID_RELATION_REVERSAL)
                || !cacheUtil.isExistByRedis(CacheConstant.SOURCEID_CLIENTID_RELATION)) {
            putRelationToRedis(list);
        }
    }

    public void initAllRelationByForce() {
        List<SourceClientIdRelation> list = sourceClientIdRelationMapper.findAll(new SourceClientIdRelation(), null, null);
        cacheUtil.invalidByRedis(CacheConstant.SOURCEID_CLIENTID_RELATION_REVERSAL);
        cacheUtil.invalidByRedis(CacheConstant.SOURCEID_CLIENTID_RELATION);
        putRelationToRedis(list);

    }

    /**
     * ***************** begin private method ************************
     */
    private void putRelationToRedis(List<SourceClientIdRelation> list) {
        if (list == null) return;
        // todo source clientId 对应关系
        putCS(list);
        // todo clientId source 对应关系（key value 反转）
        putSC(list);
    }

    private void putCS(List<SourceClientIdRelation> list) {
        Map<String, Object> csMap = new HashMap<>();
        for (SourceClientIdRelation relation : list) {
            String source = relation.getSource().toString();
            String clientId = relation.getClientId();
            csMap.put(clientId, source);
        }
        cacheUtil.putData2RedisMap(CacheConstant.SOURCEID_CLIENTID_RELATION_REVERSAL, csMap);
        cacheUtil.expireKey(CacheConstant.SOURCEID_CLIENTID_RELATION_REVERSAL, Global.THREE_DAY_AGE);
        logger.info(" put relation to redis csMap:{}", GsonUtil.toJson(csMap));
    }

    private void putSC(List<SourceClientIdRelation> list) {
        Map<String, Object> scMap = new HashMap<>();
        for (SourceClientIdRelation relation : list) {
            String source = relation.getSource().toString();
            String clientId = relation.getClientId();
            scMap.put(source, clientId);
        }
        cacheUtil.putData2RedisMap(CacheConstant.SOURCEID_CLIENTID_RELATION, scMap);
        cacheUtil.expireKey(CacheConstant.SOURCEID_CLIENTID_RELATION, Global.THREE_DAY_AGE);
        logger.info(" put relation to redis scMap:{}", GsonUtil.toJson(scMap));
    }

    private SourceClientIdRelation getBySource(Byte source) {
        List<SearchFilter> searchList = new ArrayList<>();
        SearchFilter searchFilter = new SearchFilter("source", SearchFilter.Operator.EQ, source);
        searchList.add(searchFilter);
        List<SourceClientIdRelation> list = sourceClientIdRelationMapper.findBy(new SourceClientIdRelation(), searchList, new ExtSqlProp(), 1, 1);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }


    private SourceClientIdRelation getByClientId(String clientId) {
        List<SearchFilter> searchList = new ArrayList<>();
        SearchFilter searchFilter = new SearchFilter("clientId", SearchFilter.Operator.EQ, clientId);
        searchList.add(searchFilter);
        List<SourceClientIdRelation> list = findAll(searchList, null);
        if (list == null || list.size() == 0) return null;

        return list.get(0);
    }

    public void updateObj(SourceClientIdRelation relation) {
        SourceClientIdRelation dbObj = get(relation.getId());
        dbObj.setSource(relation.getSource());
        dbObj.setClientId(relation.getClientId());
        dbObj.setUpdateTime(new Date());
        update(dbObj);
    }


}
