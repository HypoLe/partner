package com.boco.eoms.base.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.base.model.PageModel;

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
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDaoHibernate extends HibernateDaoSupport implements Dao {
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
			countHql = "select count(*) from (" + hql + ")";
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
