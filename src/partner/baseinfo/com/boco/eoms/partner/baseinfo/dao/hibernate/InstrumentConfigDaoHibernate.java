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
import com.boco.eoms.partner.baseinfo.dao.IInstrumentConfigDao;
import com.boco.eoms.partner.baseinfo.model.InstrumentConfig;

/**
 * <p>
 * Title:仪器仪表配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:仪器仪表配置
 * </p>
 * <p>
 * Mon Dec 14 14:07:13 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class InstrumentConfigDaoHibernate extends BaseDaoHibernate implements IInstrumentConfigDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IInstrumentConfigDao#getInstrumentConfigs()
	 *      
	 */
	public List getInstrumentConfigs() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InstrumentConfig instrumentConfig";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.InstrumentConfigDao#getInstrumentConfigs(final String where)
	 *      
	 */
	public List getInstrumentConfigs(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InstrumentConfig instrumentConfig";
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
	 * @see com.boco.eoms.partner.baseinfo.IInstrumentConfigDao#getInstrumentConfig(java.lang.String)
	 *
	 */
	public InstrumentConfig getInstrumentConfig(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InstrumentConfig instrumentConfig where instrumentConfig.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (InstrumentConfig) result.iterator().next();
				} else {
					return new InstrumentConfig();
				}
			}
		};
		return (InstrumentConfig) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IInstrumentConfigDao#saveInstrumentConfigs(com.boco.eoms.partner.baseinfo.InstrumentConfig)
	 *      
	 */
	public void saveInstrumentConfig(final InstrumentConfig instrumentConfig) {
		if ((instrumentConfig.getId() == null) || (instrumentConfig.getId().equals("")))
			getHibernateTemplate().save(instrumentConfig);
		else
			getHibernateTemplate().saveOrUpdate(instrumentConfig);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IInstrumentConfigDao#removeInstrumentConfigs(java.lang.String)
	 *      
	 */
    public void removeInstrumentConfig(final String id) {
		getHibernateTemplate().delete(getInstrumentConfig(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		InstrumentConfig instrumentConfig = this.getInstrumentConfig(id);
		if(instrumentConfig==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IInstrumentConfigDao#getInstrumentConfigs(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getInstrumentConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InstrumentConfig instrumentConfig";
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