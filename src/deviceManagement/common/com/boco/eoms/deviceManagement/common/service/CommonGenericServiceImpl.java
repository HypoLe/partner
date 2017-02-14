package com.boco.eoms.deviceManagement.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.partner.contact.util.PageData;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class CommonGenericServiceImpl<T> implements CommonGenericService<T> {

	private CommonGenericDao<T, String> commonGenericDao;

	/**
	 * 在Spring配置文件中的继承类里面注入私有的Dao为泛型Dao所用
	 * 
	 * @param commonGenericDao
	 */
	public void setCommonGenericDao(CommonGenericDao<T, String> commonGenericDao) {
		this.commonGenericDao = commonGenericDao;
	}

	public int count(ISearch search) {
		return commonGenericDao.count(search);
	}

	public T find(String id) {
		return commonGenericDao.find(id);
	}

	public T[] find(String... ids) {
		return commonGenericDao.find(ids);
	}

	public List<T> findAll() {
		return commonGenericDao.findAll();
	}

	public void flush() {
		commonGenericDao.flush();
	}

	public Filter getFilterFromExample(T example) {
		return commonGenericDao.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return commonGenericDao.getFilterFromExample(example, options);
	}

	public T getReference(String id) {
		return commonGenericDao.getReference(id);
	}

	public T[] getReferences(String... ids) {
		return commonGenericDao.getReferences(ids);
	}

	public boolean isAttached(T entity) {
		return commonGenericDao.isAttached(entity);
	}

	public void refresh(T... entities) {
		commonGenericDao.refresh(entities);
	}

	public boolean remove(T entity) {
		return commonGenericDao.remove(entity);
	}

	public void remove(T... entities) {
		commonGenericDao.remove(entities);
	}

	public boolean removeById(String id) {
		return commonGenericDao.removeById(id);
	}

	public void removeByIds(String... ids) {
		commonGenericDao.removeByIds(ids);
	}

	public boolean save(T entity) {
		return commonGenericDao.save(entity);
	}

	public boolean[] save(T... entities) {
		return commonGenericDao.save(entities);
	}

	public List<T> search(ISearch search) {
		return commonGenericDao.search(search);
	}

	public SearchResult<T> searchAndCount(ISearch search) {
		return commonGenericDao.searchAndCount(search);
	}

	public T searchUnique(ISearch search) {
		return (T)commonGenericDao.searchUnique(search);
	}

	public GenericDAO<T, String> getGenericDAO() {
		return commonGenericDao;
	}

	public <RT> SearchResult<RT> searchAndCount(CommonSearch nop3Search) {
		return commonGenericDao.searchAndCount(nop3Search);
	}
	
	public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request){
		return search;
	}

	public  Map getDataList(Class<T> entryClass,String alias,String entrySql,String countSql,int pageIndex,int pageSize){
		return commonGenericDao.getDataList(entryClass,alias,entrySql,countSql,pageIndex,pageSize);
	}

	@Override
	public List excelExportToProcess(Search search, String userId,
			String deptId, String queryFlag, String processKey, String flag,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}