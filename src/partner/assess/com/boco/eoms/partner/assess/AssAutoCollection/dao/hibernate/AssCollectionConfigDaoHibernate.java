package com.boco.eoms.partner.assess.AssAutoCollection.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionConfigDao;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;

/**
 * <p>
 * Title:采集配置 dao的hibernate实现
 * </p>
 * <p>
 * Description:采集配置
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssCollectionConfigDaoHibernate extends BaseDaoHibernate implements AssCollectionConfigDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfigDao#getAssCollectionConfigs()
	 *      
	 */
	public List getAssCollectionConfigs() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionConfig assCollectionConfig";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfigDao#getAssCollectionConfig(java.lang.String)
	 *
	 */
	public AssCollectionConfig getAssCollectionConfig(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionConfig assCollectionConfig where assCollectionConfig.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssCollectionConfig) result.iterator().next();
				} else {
					return new AssCollectionConfig();
				}
			}
		};
		return (AssCollectionConfig) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfigDao#saveAssCollectionConfigs(com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfig)
	 *      
	 */
	public void saveAssCollectionConfig(final AssCollectionConfig assCollectionConfig) {
		if ((assCollectionConfig.getId() == null) || (assCollectionConfig.getId().equals("")))
			getHibernateTemplate().save(assCollectionConfig);
		else
			getHibernateTemplate().saveOrUpdate(assCollectionConfig);
	}

	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfigDao#removeAssCollectionConfigs(java.lang.String)
	 *      
	 */
    public void removeAssCollectionConfig(final String id) {
		getHibernateTemplate().delete(getAssCollectionConfig(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		AssCollectionConfig assCollectionConfig = this.getAssCollectionConfig(id);
		if(assCollectionConfig==null){
			return "";
		}
		return assCollectionConfig.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionConfigDao#getAssCollectionConfigs(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getAssCollectionConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionConfig assCollectionConfig";
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
	
	public AssCollectionConfig getAssCollectionConfigByNodeId(final String nodeId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionConfig assCollectionConfig where assCollectionConfig.treeNodeId=:nodeId";
				Query query = session.createQuery(queryStr);
				query.setString("nodeId", nodeId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssCollectionConfig) result.iterator().next();
				} else {
					return new AssCollectionConfig();
				}
			}
		};
		return (AssCollectionConfig) getHibernateTemplate().execute(callback);
	}	
}