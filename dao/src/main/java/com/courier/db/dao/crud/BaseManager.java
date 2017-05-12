package com.courier.db.dao.crud;

import java.util.List;

import com.courier.commons.entity.IdEntity;
import org.springframework.transaction.annotation.Transactional;


/**
 * 基于service层的crud模版
 * @author Ryan
 * @param <T>
 */
public abstract class BaseManager<T extends IdEntity> {
	
	@Transactional(readOnly = true)
	public T get(Long id) {
		T t = getEntity();
		t.setId(id);
		return getBaseMapper().get(t);
	}

	@Transactional(readOnly = false)
	public void save(T t) {
		getBaseMapper().insert(t);
	}

	@Transactional(readOnly = false)
	public void update(T t) {
		getBaseMapper().update(t);
	}

//	@Transactional(readOnly = false)
//	public void saveOrUpdate(T t){
//		getBaseMapper().saveOrUpdate(t);
//	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		T t = this.getEntity();
		t.setId(id);
		getBaseMapper().delete(t);
	}

	@Transactional(readOnly = false)
	public void delete(T t){
		getBaseMapper().delete(t);
	}

	@Transactional(readOnly = true)
	public List<T> findBy(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit) {
		return getBaseMapper().findBy(getEntity(), searchFilterList, sqlProp, pageNumber, limit);
	}
	
	@Transactional(readOnly = true)
	public List<T> findBy(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp) {
		return getBaseMapper().findBy(getEntity(), searchFilterList, sqlProp, 0, 0);
	}

	@Transactional(readOnly = true)
	public List<T> findAll(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp){
		return getBaseMapper().findAll(getEntity(), searchFilterList, sqlProp);
	}

	@Transactional(readOnly = true)
	public Page<T> findPage(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int pageSize){
		int totalCount = this.getBaseMapper().count(getEntity(), searchFilterList);
		Page<T> page = new Page<T>();
		List<T> result = findBy(searchFilterList, sqlProp, pageNumber, pageSize);
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		page.setResult(result);
		return page;
	}

	@Transactional(readOnly = true)
	public List<T> distinct(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp){
		return this.getBaseMapper().distinct(getEntity(), searchFilterList, sqlProp);
	}

	@Transactional(readOnly = true)
	public List<T> distinctBy(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int pageSize){
		return this.getBaseMapper().distinctBy(getEntity(), searchFilterList, sqlProp, pageNumber, pageSize);
	}

	@Transactional(readOnly = true)
	public Page<T> distinctPage(List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int pageSize){
		int totalCount = this.getBaseMapper().count(getEntity(), searchFilterList);
		Page<T> page = new Page<T>();
		List<T> result = distinctBy(searchFilterList, sqlProp, pageNumber, pageSize);
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		page.setResult(result);
		return page;
	}

    @Transactional(readOnly = true)
    public int count(List<SearchFilter> searchFilterList){
        return getBaseMapper().count(getEntity(), searchFilterList);
    }

	@Transactional(readOnly = true)
	public int count(T t, List<SearchFilter> searchFilterList){
		return getBaseMapper().count(t, searchFilterList);
	}
	
	public abstract T getEntity();

	public abstract BaseMapper<T> getBaseMapper();
	
}
