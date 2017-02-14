package base.service;

import java.util.List;

import base.dao.Nop3GenericDao;
import base.pojo.Nop3Search;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public class Nop3GenericServiceImpl<T> implements Nop3GenericService<T> {

	private Nop3GenericDao<T, String> nop3GenericDao;

	/**
	 * 在Spring配置文件中的继承类里面注入私有的Dao为泛型Dao所用
	 * 
	 * @param nop3GenericDao
	 */
	public void setNop3GenericDao(Nop3GenericDao<T, String> nop3GenericDao) {
		this.nop3GenericDao = nop3GenericDao;
	}

	public int count(ISearch search) {
		return nop3GenericDao.count(search);
	}

	public T find(String id) {
		return nop3GenericDao.find(id);
	}

	public T[] find(String... ids) {
		return nop3GenericDao.find(ids);
	}

	public List<T> findAll() {
		return nop3GenericDao.findAll();
	}

	public void flush() {
		nop3GenericDao.flush();
	}

	public Filter getFilterFromExample(T example) {
		return nop3GenericDao.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return nop3GenericDao.getFilterFromExample(example, options);
	}

	public T getReference(String id) {
		return nop3GenericDao.getReference(id);
	}

	public T[] getReferences(String... ids) {
		return nop3GenericDao.getReferences(ids);
	}

	public boolean isAttached(T entity) {
		return nop3GenericDao.isAttached(entity);
	}

	public void refresh(T... entities) {
		nop3GenericDao.refresh(entities);
	}

	public boolean remove(T entity) {
		return nop3GenericDao.remove(entity);
	}

	public void remove(T... entities) {
		nop3GenericDao.remove(entities);
	}

	public boolean removeById(String id) {
		return nop3GenericDao.removeById(id);
	}

	public void removeByIds(String... ids) {
		nop3GenericDao.removeByIds(ids);
	}

	public boolean save(T entity) {
		return nop3GenericDao.save(entity);
	}

	public boolean[] save(T... entities) {
		return nop3GenericDao.save(entities);
	}

	public List<T> search(ISearch search) {
		return nop3GenericDao.search(search);
	}

	public SearchResult<T> searchAndCount(ISearch search) {
		return nop3GenericDao.searchAndCount(search);
	}

	public T searchUnique(ISearch search) {
		return (T)nop3GenericDao.searchUnique(search);
	}

	public GenericDAO<T, String> getGenericDAO() {
		return nop3GenericDao;
	}

	public <RT> SearchResult<RT> searchAndCount(Nop3Search nop3Search) {
		return nop3GenericDao.searchAndCount(nop3Search);
	}

	public void remove(List<T> entities) {
		nop3GenericDao.remove(entities);
	}

	public boolean[] save(List<T> entities) {
		return nop3GenericDao.save(entities);
	}
}