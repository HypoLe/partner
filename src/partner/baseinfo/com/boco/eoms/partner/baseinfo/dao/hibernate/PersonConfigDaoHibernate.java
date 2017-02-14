package com.boco.eoms.partner.baseinfo.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.baseinfo.dao.IPersonConfigDao;
import com.boco.eoms.partner.baseinfo.model.PersonConfig;

/**
 * <p>
 * Title:人员配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:人员配置
 * </p>
 * <p>
 * Tue Dec 08 15:14:23 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PersonConfigDaoHibernate extends BaseDaoHibernate implements IPersonConfigDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IPersonConfigDao#getPersonConfigs()
	 *      
	 */
	public List getPersonConfigs() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PersonConfig personConfig";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PersonConfigDao#getPersonConfigs(final String where)
	 *      
	 */
	public List getPersonConfigs(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PersonConfig personConfig";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.IPersonConfigDao#getPersonConfig(java.lang.String)
	 *
	 */
	public PersonConfig getPersonConfig(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PersonConfig personConfig where personConfig.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PersonConfig) result.iterator().next();
				} else {
					return new PersonConfig();
				}
			}
		};
		return (PersonConfig) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IPersonConfigDao#savePersonConfigs(com.boco.eoms.partner.baseinfo.PersonConfig)
	 *      
	 */
	public void savePersonConfig(final PersonConfig personConfig) {
		if ((personConfig.getId() == null) || (personConfig.getId().equals("")))
			getHibernateTemplate().save(personConfig);
		else
			getHibernateTemplate().saveOrUpdate(personConfig);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IPersonConfigDao#removePersonConfigs(java.lang.String)
	 *      
	 */
    public void removePersonConfig(final String id) {
		getHibernateTemplate().delete(getPersonConfig(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PersonConfig personConfig = this.getPersonConfig(id);
		if(personConfig==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IPersonConfigDao#getPersonConfigs(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPersonConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PersonConfig personConfig";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}