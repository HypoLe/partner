package com.boco.eoms.base.dao.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.base.model.PageModel;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class serves as the Base class for all other Daos - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.
 * </p>
 * 
 * <p>
 * <a href="BaseDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com"> add gong</a>
 */
public class BaseDaoHibernateContract extends HibernateDaoSupport implements Dao {
	//protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @see com.boco.eoms.base.dao.Dao#saveObject(java.lang.Object)
	 */
	public void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/**
	 * @see com.boco.eoms.base.dao.Dao#getObject(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public Object getObject(Class clazz, Serializable id) {
		Object o = getHibernateTemplate().get(clazz, id);

		if (o == null) {
			throw new ObjectRetrievalFailureException(clazz, id);
		}

		return o;
	}

	/**
	 * @see com.boco.eoms.base.dao.Dao#getObjects(java.lang.Class)
	 */
	public List getObjects(Class clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	/**
	 * @see com.boco.eoms.base.dao.Dao#removeObject(java.lang.Class,
	 *      java.io.Serializable)
	 */
	public void removeObject(Class clazz, Serializable id) {
		getHibernateTemplate().delete(getObject(clazz, id));
	}
	


	/**
	 * Used by the base DAO classes but here for your modification
	 * Get object matching the given key and return it.
	 */
	protected Object get(Class refClass, Serializable key) {
			return get(refClass, key, getSession(false));
	}

	/**
	 * Used by the base DAO classes but here for your modification
	 * Get object matching the given key and return it.
	 */
	protected Object get(Class refClass, Serializable key, Session s) {
		return s.get(refClass, key);
	}

	/**
	 * Used by the base DAO classes but here for your modification
	 * Load object matching the given key and return it.
	 */
	protected Object load(Class refClass, Serializable key) {
		return load(refClass, key, getSession(false));
	}

	/**
	 * Used by the base DAO classes but here for your modification
	 * Load object matching the given key and return it.
	 */
	protected Object load(Class refClass, Serializable key, Session s) {
		return s.load(refClass, key);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List findAll () {
    		return findAll(getSession(false));
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List findAll (Session s) {
   		return findAll(s, getDefaultOrder());
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List findAll (Order defaultOrder) {
			return findAll(getSession(false), defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List findAll (Session s, Order defaultOrder) {
		Criteria crit = s.createCriteria(getReferenceClass());
		if (null != defaultOrder) crit.addOrder(defaultOrder);
		return crit.list();
	}

	/**
	 * Return all objects related to the implementation of this DAO with a filter.
	 * Use the session given.
	 * @param propName the name of the property to use for filtering
	 * @param filter the value of the filter
	 */
	protected Criteria findFiltered (String propName, Object filter) {
		return findFiltered(propName, filter, getDefaultOrder());
	}

	/**
	 * Return all objects related to the implementation of this DAO with a filter.
	 * Use the session given.
	 * @param propName the name of the property to use for filtering
	 * @param filter the value of the filter
	 * @param orderProperty the name of the property used for ordering
	 */
	protected Criteria findFiltered (String propName, Object filter, Order order) {
		return findFiltered(getSession(false), propName, filter, order);
	}
	
	/**
	 * Return all objects related to the implementation of this DAO with a filter.
	 * Use the session given.
	 * @param s the Session
	 * @param propName the name of the property to use for filtering
	 * @param filter the value of the filter
	 * @param orderProperty the name of the property used for ordering
	 */
	protected Criteria findFiltered (Session s, String propName, Object filter, Order order) {
		Criteria crit = s.createCriteria(getReferenceClass());
		crit.add(Expression.eq(propName, filter));
		if (null != order) crit.addOrder(order);
		return crit;
	}
	
	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * @param name the name of a query defined externally 
	 * @return Query
	 */
	protected Query getNamedQuery(String name) {	
			return getNamedQuery(name, getSession(false));
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the session given.
	 * @param name the name of a query defined externally 
	 * @param s the Session
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Session s) {
		Query q = s.getNamedQuery(name);
		return q;
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * @param name the name of a query defined externally 
	 * @param param the first parameter to set
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Serializable param) {
			return getNamedQuery(name, param, getSession(false));
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the session given.
	 * @param name the name of a query defined externally 
	 * @param param the first parameter to set
	 * @param s the Session
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Serializable param, Session s) {
		Query q = s.getNamedQuery(name);
		q.setParameter(0, param);
		return q;
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given.
	 * @param name the name of a query defined externally 
	 * @param params the parameter array
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Serializable[] params) {
			return getNamedQuery(name, params, getSession(false));
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given and the Session given.
	 * @param name the name of a query defined externally 
	 * @param params the parameter array
	 * @s the Session
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Serializable[] params, Session s) {
		Query q = s.getNamedQuery(name);
		if (null != params) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q;
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given.
	 * @param name the name of a query defined externally 
	 * @param params the parameter Map
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Map params) {
			return getNamedQuery(name, params, getSession(false));
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given and the Session given.
	 * @param name the name of a query defined externally 
	 * @param params the parameter Map
	 * @s the Session
	 * @return Query
	 */
	protected Query getNamedQuery(String name, Map params, Session s) {
		Query q = s.getNamedQuery(name);
		if (null != params) {
			for (Iterator i=params.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry) i.next();
				q.setParameter((String) entry.getKey(), entry.getValue());
			}
		}
		return q;
	}

	/**
	 * Execute a query. 
	 * @param queryStr a query expressed in Hibernate's query language
	 * @return a distinct list of instances (or arrays of instances)
	 */
	public Query getQuery(String queryStr) {
			return getQuery(queryStr, getSession(false));
	}

	/**
	 * Execute a query but use the session given instead of creating a new one.
	 * @param queryStr a query expressed in Hibernate's query language
	 * @s the Session to use
	 */
	public Query getQuery(String queryStr, Session s) {
		return s.createQuery(queryStr);
	}

	/**
	 * Execute a query. 
	 * @param query a query expressed in Hibernate's query language
	 * @param queryStr the name of a query defined externally 
	 * @param param the first parameter to set
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Serializable param) {
			return getQuery(queryStr, param, getSession(false));
	}

	/**
	 * Execute a query but use the session given instead of creating a new one.
	 * @param queryStr a query expressed in Hibernate's query language
	 * @param param the first parameter to set
	 * @s the Session to use
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Serializable param, Session s) {
		Query q = getQuery(queryStr, s);
		q.setParameter(0, param);
		return q;
	}

	/**
	 * Execute a query. 
	 * @param queryStr a query expressed in Hibernate's query language
	 * @param params the parameter array
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Serializable[] params) {
		return getQuery(queryStr, params, getSession(false));
	}

	/**
	 * Execute a query but use the session given instead of creating a new one.
	 * @param queryStr a query expressed in Hibernate's query language
	 * @param params the parameter array
	 * @s the Session
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Serializable[] params, Session s) {
		Query q = getQuery(queryStr, s);
		if (null != params) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		return q;
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given.
	 * @param queryStr a query expressed in Hibernate's query language
	 * @param params the parameter Map
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Map params) {
		return getQuery(queryStr, params, getSession(false));
	}

	/**
	 * Obtain an instance of Query for a named query string defined in the mapping file.
	 * Use the parameters given and the Session given.
	 * @param queryStr a query expressed in Hibernate's query language
	 * @param params the parameter Map
	 * @s the Session
	 * @return Query
	 */
	protected Query getQuery(String queryStr, Map params, Session s) {
		Query q = getQuery(queryStr, s);
		if (null != params) {
			for (Iterator i=params.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry) i.next();
				q.setParameter((String) entry.getKey(), entry.getValue());
			}
		}
		return q;
	}

	protected Order getDefaultOrder () {
		return null;
	}

	/**
	 * Return the specific Object class that will be used for class-specific
	 * implementation of this DAO.
	 * @return the reference Class
	 */
	protected Class getReferenceClass() {
		//TODO: 我也不清楚干啥的
		return null;
	}


	/**
	 * Used by the base DAO classes but here for your modification
	 * Persist the given transient instance, first assigning a generated identifier. 
	 * (Or using the current value of the identifier property if the assigned generator is used.) 
	 */
	protected Serializable save(final Object obj) {
		return getHibernateTemplate().save(obj);
	}

	

	/**
	 * Used by the base DAO classes but here for your modification
	 * Either save() or update() the given instance, depending upon the value of its
	 * identifier property.
	 */
	public void saveOrUpdate(final Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	
	/**
	 * Used by the base DAO classes but here for your modification
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param obj a transient instance containing updated state
	 */
	public void update(final Object obj) {
		getHibernateTemplate().update(obj);
	}

	/**
	 * Delete all objects returned by the query
	 */
	protected int delete (Query query) {
		List list = query.list();
		for (Iterator i=list.iterator(); i.hasNext(); ) {
			delete(i.next());
		}
		return list.size();
	}

	/**
	 * Used by the base DAO classes but here for your modification
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 */
	public void delete(final Object obj) {
		getHibernateTemplate().delete(obj);
	}

	

	/**
	 * Used by the base DAO classes but here for your modification
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 */
	protected void refresh(Object obj) {
		getSession(false).refresh(obj);
	}

	protected void throwException (Throwable t) {
		if (t instanceof HibernateException) throw (HibernateException) t;
		else if (t instanceof RuntimeException) throw (RuntimeException) t;
		else throw new HibernateException(t);
	}
	public List find(String queryString, Object[] values) {
		return getHibernateTemplate().find(queryString, values);
	}
	
	public List find(String queryString, List values) {
		return find(queryString,values.toArray());
	}

	public List find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}
	
	public List findByVarParams(String queryString,Object... values ){
		return find(queryString,values);
	}
	private String getCountQuery(String hql) {
		String countHql = "";
		int fromIndex = hql.indexOf("from");
		int orderbyIndex = hql.indexOf("order by");
		
		if(orderbyIndex > 0){
			hql = hql.substring(0,orderbyIndex);
		}
		int groupbyIndex = hql.indexOf("group by");
		if(groupbyIndex < 0){
			countHql = "select count(*) " + hql.substring(fromIndex);
		}else{
			countHql = "select count(*) from " + hql + "";
		}
		return countHql;
	}
	
	/**
     * 分页
     * @param hql       hql查询语句
     * @param params    参数
     * @param offset    分页偏移量
     * @param pagesize  每页条数
     * @return
     */
    public PageModel searchPaginated(final String hql, final Object[] params, 
  		  final int offset,final int pagesize){
    	HibernateCallback callback = new HibernateCallback() {
  			public Object doInHibernate(Session session) throws HibernateException {
  				//获取记录总数
  				String countHql = getCountQuery(hql);
  				Query countquery = session.createQuery(countHql);
  				if (params != null && params.length > 0) {
  					for (int i = 0; i < params.length; i++) {
  						countquery.setParameter(i, params[i]);
  					}
  				}
  				Object a = countquery.uniqueResult();
  				int total = (Integer)a;
  				 
  				// 获取当前页的结果集
  				Query query  = session.createQuery(hql);
  				if (params != null && params.length > 0) {
  					for (int i = 0; i < params.length; i++) {
  						query.setParameter(i, params[i]);
  					}
  				}
  				query.setFirstResult(offset);
  				query.setMaxResults(pagesize);
  				List datas = query.list();
  				
  				PageModel pm = new PageModel();
  				pm.setTotal(total);
  				pm.setDatas(datas);
  				return pm;
  			}
  		};
  	return (PageModel) getHibernateTemplate().execute(callback);
    }
    
	public int bulkUpdate(String queryString, Object[] values) {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	public int bulkUpdate(String queryString) {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

}
