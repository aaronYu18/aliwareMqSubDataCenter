package com.courier.db.dao.crud;

import java.util.List;

import org.apache.ibatis.annotations.*;

/**
 * 本来打算使用泛型。但其它它只是个信息收集的类。真正的功能实现在myBatis里面。所以其实泛型在这里没有意义。
 * 返回的时候不能识别正确的泛型。
 * 
 * 所有查询的东西都放在子类中。Base只负责基本的增删改。然后定义一些查询功能。但需要在子接口中加上调用@SelectProvider的注释
 * 
 * MyBatis CRUD基接口
 * @author Ryan
 * @param <T> 处理的POJO对象
 */
public interface BaseMapper<T> {

	public T get(T obj);

	/**
	 * Insert语句从CRUDTemplate类中生成
	 * @param obj
	 */
	@InsertProvider(type = CRUDTemplate.class, method = "insert")
	@Options(keyColumn="id", keyProperty="id", useGeneratedKeys=true)
	@SelectKey(before = false, resultType = Long.class, keyProperty = "id", statement = {"SELECT LAST_INSERT_ID() AS id"})
	public void insert(Object obj);

	/**
	 * Update语句CRUDTemplate类中生成
	 * @param obj
	 */
	@UpdateProvider(type = CRUDTemplate.class, method = "update")
	public void update(Object obj);

//	/**
//	 * SaveOrUpdate语句从CRUDTemplate类生成
//	 * @param obj
//	 */
//	@InsertProvider(type = CRUDTemplate.class, method = "saveOrUpdate")
//	@Options(keyColumn="id", keyProperty="id", useGeneratedKeys=true)
//	@SelectKey(before = false, resultType = Long.class, keyProperty = "id", statement = {"SELECT LAST_INSERT_ID() AS id"})
//	public void saveOrUpdate(Object obj);

	/**
	 * Delete语句从CRUDTemplate类中生成
	 * @param obj
	 */
	@DeleteProvider(type = CRUDTemplate.class, method = "delete")
	public void delete(Object obj);

	@SelectProvider(type = CRUDTemplate.class,method = "count")
	public int count(Object obj, List<SearchFilter> searchFilterList);
	
	public Long getKey();

	public abstract List<T> findBy(T obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

	public abstract List<T> findAll(T obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

	public abstract List<T> distinct(T obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);

	public abstract List<T> distinctBy(T obj, List<SearchFilter> searchFilters, ExtSqlProp sqlProp, int pageNumber, int limit);
}
