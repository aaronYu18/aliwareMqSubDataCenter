package com.courier.db.dao.crud;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * 主要的sql生成类
 * @author Ryan
 * obj -> map
 * 0:entity
 * 1:List<SearchFilter>
 * 2:ExtSqlProp
 * 3:start
 * 4:pageSize
 */
public class CRUDTemplate{

	public static Logger logger = LoggerFactory.getLogger(CRUDTemplate.class);
	
	public String insert(Object obj) throws Exception {
		BEGIN();

		INSERT_INTO(EntityUtil.tablename(obj));
		
		EntityUtil.caculationColumnList(obj);
		VALUES(EntityUtil.returnInsertColumnsName(obj), EntityUtil.returnInsertColumnsDefine(obj));
		String sql = SQL();
		logger.debug("insert sql:{}", sql);
		return sql;
	}

	public String insertBatch(Object obj) throws Exception {

		String sql = SQL();
		logger.debug("insert batch sql:{}", sql);
		return sql;
	}
	
	public String update(Object obj) throws Exception {
		String idname = EntityUtil.id(obj);
		
		BEGIN();
		
		UPDATE(EntityUtil.tablename(obj));
		EntityUtil.caculationColumnList(obj);
		SET(EntityUtil.returnWhereDefine(obj));
		WHERE(idname + "=#{" + idname + "}");

		String sql = SQL();
		logger.debug("update sql:{}", sql);
		return sql;
	}

//	public String saveOrUpdate(Object obj) throws Exception {
//		String idValue = EntityUtil.idValue(obj);
//
//		if(StringUtils.isEmpty(idValue)){
//			return insert(obj);
//		} else {
//			return update(obj);
//		}
//	}

	public String delete(Object obj) throws Exception {
		String idname = EntityUtil.id(obj);
		
		BEGIN();
		
		DELETE_FROM(EntityUtil.tablename(obj));
		WHERE(idname + "=#{" + idname + "}");

		String sql = SQL();
		logger.debug("delete sql:{}", sql);
		return sql;
	}

	/**
	 * 增加where条件
	 * @param entity
	 * @param searchFilterListObj
	 */
	private void appendWhere(Object entity, Object searchFilterListObj){
		List<SearchFilter> searchFilterList = (List<SearchFilter>)searchFilterListObj;
		if(null == searchFilterList){
			searchFilterList = Lists.newArrayList();
		}
		String paramString = EntityUtil.returnWhereDefine(entity, searchFilterList);
		if (StringUtils.isNotBlank(paramString)) {
			WHERE(paramString);
		}
	}

	/**
	 * 增加扩展条件
	 * @param extSqlPro
	 */
	private void appendExtSql(Object extSqlPro){
		ExtSqlProp sqlProp = (ExtSqlProp)extSqlPro;
		if(sqlProp != null){
			if(StringUtils.isNotEmpty(sqlProp.getGroupBy())){
				GROUP_BY(sqlProp.getGroupBy());
			}
			if(StringUtils.isNotEmpty(sqlProp.getOrderBy())){
				ORDER_BY(sqlProp.getOrderBy());
			}
		}
	}


	/**
	 * 基础查询
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private String findBaseAll(Object obj) throws Exception {
		Map map = (Map)obj;
		Object entity = map.get("0");
		EntityUtil.caculationColumnList(entity);
		BEGIN(); // Clears ThreadLocal variable
		SELECT(EntityUtil.returnSelectColumnsName(entity));
		FROM(EntityUtil.tablename(entity));

		appendWhere(entity, map.get("1"));

		appendExtSql(map.get("2"));

		return SQL();
	}

	public String findAll(Object obj) throws Exception {
		String sql = findBaseAll(obj);
		logger.debug("findAll sql:{}", sql);
		return sql;
	}

	public String findBy(Object obj) throws Exception {
		Map map = (Map)obj;
		StringBuilder sql = new StringBuilder(findBaseAll(obj));

		appendPageSql(sql, map.get("3"), map.get("4"));

		logger.debug("findBy sql:{}", sql.toString());
		return sql.toString();
	}
	
	public String count(Object obj) throws Exception {
		Map map = (Map)obj;
		Object entity = map.get("0");
		EntityUtil.caculationColumnList(entity);
		BEGIN(); // Clears ThreadLocal variable

		String idname = EntityUtil.id(entity);
		
		SELECT("count(" + idname + ")");
		FROM(EntityUtil.tablename(entity));

		appendWhere(entity, map.get("1"));

		String sql = SQL();
		logger.debug("count sql:{}", sql);
		return sql;
	}
	
	public String get(Object obj) throws Exception {
		EntityUtil.caculationColumnList(obj);
		String idname = EntityUtil.id(obj);
		
		BEGIN();
		SELECT(EntityUtil.returnSelectColumnsName(obj));
		
		FROM(EntityUtil.tablename(obj));
		WHERE(idname + "=#{" + idname + "}");

		String sql = SQL();
		logger.debug("get sql:{}", sql);
		return sql;
	}

	public String distinct(Object obj) throws Exception {
		Map map = (Map)obj;
		Object entity = map.get("0");

		ExtSqlProp sqlProp = (ExtSqlProp)map.get("2");

		BEGIN();
		SELECT_DISTINCT(EntityUtil.returnDistinctColumnsName(entity, sqlProp.getAllDistinctField()));
		FROM(EntityUtil.tablename(entity));

		appendWhere(entity, map.get("1"));

		StringBuilder sql = new StringBuilder(SQL());
		logger.debug("distinct sql:{}", sql.toString());
		return sql.toString();
	}

	public String distinctBy(Object obj) throws Exception {
		Map map = (Map)obj;
		Object entity = map.get("0");

		ExtSqlProp sqlProp = (ExtSqlProp)map.get("2");

		BEGIN();
		SELECT_DISTINCT(EntityUtil.returnDistinctColumnsName(entity, sqlProp.getAllDistinctField()));
		FROM(EntityUtil.tablename(entity));

		appendWhere(entity, map.get("1"));

		StringBuilder sql = new StringBuilder(SQL());

		appendPageSql(sql, map.get("3"), map.get("4"));

		logger.debug("distinct sql:{}", sql.toString());
		return sql.toString();
	}

	private void appendPageSql(StringBuilder sql, Object start, Object pageSize){
		if(start != null && pageSize != null && !start.equals(0) && !pageSize.equals(0)){
			int startInt = (Integer)start;
			int pageSizeInt = (Integer)pageSize;
			sql.append(" limit " +(startInt-1)*pageSizeInt + "," + pageSizeInt);

		}
	}

}

