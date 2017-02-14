package com.boco.eoms.partner.statistically.utils;

import java.util.List;

import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T, String> BaseDao;

	/**
	 * 在Spring配置文件中的继承类里面注入私有的Dao为泛型Dao所用
	 * 
	 * @param nop3GenericDao
	 */
	public void setBaseDao(BaseDao<T, String> BaseDao) {
		this.BaseDao = BaseDao;
	}

	public int count(ISearch search) {
		return BaseDao.count(search);
	}

	public T find(String id) {
		return BaseDao.find(id);
	}

	public T[] find(String... ids) {
		return BaseDao.find(ids);
	}

	public List<T> findAll() {
		return BaseDao.findAll();
	}

	public void flush() {
		BaseDao.flush();
	}

	public Filter getFilterFromExample(T example) {
		return BaseDao.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return BaseDao.getFilterFromExample(example, options);
	}

	public T getReference(String id) {
		return BaseDao.getReference(id);
	}

	public T[] getReferences(String... ids) {
		return BaseDao.getReferences(ids);
	}

	public boolean isAttached(T entity) {
		return BaseDao.isAttached(entity);
	}

	public void refresh(T... entities) {
		BaseDao.refresh(entities);
	}

	public boolean remove(T entity) {
		return BaseDao.remove(entity);
	}

	public void remove(T... entities) {
		BaseDao.remove(entities);
	}

	public boolean removeById(String id) {
		return BaseDao.removeById(id);
	}

	public void removeByIds(String... ids) {
		BaseDao.removeByIds(ids);
	}

	public boolean save(T entity) {
		return BaseDao.save(entity);
	}

	public boolean[] save(T... entities) {
		return BaseDao.save(entities);
	}

	public List<T> search(ISearch search) {
		return BaseDao.search(search);
	}

	public SearchResult<T> searchAndCount(ISearch search) {
		return BaseDao.searchAndCount(search);
	}

	public T searchUnique(ISearch search) {
		return (T)BaseDao.searchUnique(search);
	}

	public GenericDAO<T, String> getGenericDAO() {
		return BaseDao;
	}

	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch) {
		return BaseDao.searchAndCount(commonSearch);
	}
}