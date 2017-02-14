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
import com.boco.eoms.partner.baseinfo.dao.IOilEngineConfigureDao;
import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;

/**
 * <p>
 * Title:油机配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class OilEngineConfigureDaoHibernate extends BaseDaoHibernate implements IOilEngineConfigureDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IOilEngineConfigureDao#getOilEngineConfigures()
	 *      
	 */
	public List getOilEngineConfigures() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from OilEngineConfigure oilEngineConfigure";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	
	public List isUnique(final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from OilEngineConfigure oilEngineConfigure " + whereStr ;
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.IOilEngineConfigureDao#getOilEngineConfigure(java.lang.String)
	 *
	 */
	public OilEngineConfigure getOilEngineConfigure(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from OilEngineConfigure oilEngineConfigure where oilEngineConfigure.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (OilEngineConfigure) result.iterator().next();
				} else {
					return new OilEngineConfigure();
				}
			}
		};
		return (OilEngineConfigure) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IOilEngineConfigureDao#saveOilEngineConfigures(com.boco.eoms.partner.baseinfo.OilEngineConfigure)
	 *      
	 */
	public void saveOilEngineConfigure(final OilEngineConfigure oilEngineConfigure) {
		if ((oilEngineConfigure.getId() == null) || (oilEngineConfigure.getId().equals("")))
			getHibernateTemplate().save(oilEngineConfigure);
		else
			getHibernateTemplate().saveOrUpdate(oilEngineConfigure);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IOilEngineConfigureDao#removeOilEngineConfigures(java.lang.String)
	 *      
	 */
    public void removeOilEngineConfigure(final String id) {
		getHibernateTemplate().delete(getOilEngineConfigure(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		OilEngineConfigure oilEngineConfigure = this.getOilEngineConfigure(id);
		if(oilEngineConfigure==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IOilEngineConfigureDao#getOilEngineConfigures(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getOilEngineConfigures(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from OilEngineConfigure oilEngineConfigure";
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