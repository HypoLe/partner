package com.boco.eoms.base.dao.jdbc;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.base.model.PageModel;

/**
 *
 *panlong
 *下午08:31:00
 */
public class BaseDaoJdbc extends JdbcDaoSupport implements Dao {

	public Object getObject(Class clazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getObjects(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeObject(Class clazz, Serializable id) {
		// TODO Auto-generated method stub

	}

	public void saveObject(Object o) {
		// TODO Auto-generated method stub

	}

	public List find(String queryString, Object[] values) {
		return null;
	}
	
	public List find(String queryString, List values) {
		return null;
	}

	public List find(String queryString) {
		return null;
	}
	
	public List findByVarParams(String queryString,Object... values ){
		return null;
	}
	public PageModel searchPaginated(final String hql, final Object[] params, 
	  		  final int offset,final int pagesize){
		return null;
	}
	
	public int bulkUpdate(String queryString, Object[] values) {
		return 0;
	}

	public int bulkUpdate(String queryString) {
		return 0;
	}

}

