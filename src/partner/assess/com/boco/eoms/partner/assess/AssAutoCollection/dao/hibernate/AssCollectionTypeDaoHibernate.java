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
import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionTypeDao;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;

/**
 * <p>
 * Title:采集类型 dao的hibernate实现
 * </p>
 * <p>
 * Description:采集类型
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssCollectionTypeDaoHibernate extends BaseDaoHibernate implements AssCollectionTypeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionTypeDao#getAssCollectionTypes()
	 *      
	 */
	public List getAssCollectionTypes() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionType assCollectionType";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionTypeDao#getAssCollectionType(java.lang.String)
	 *
	 */
	public AssCollectionType getAssCollectionType(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionType assCollectionType where assCollectionType.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssCollectionType) result.iterator().next();
				} else {
					return new AssCollectionType();
				}
			}
		};
		return (AssCollectionType) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionTypeDao#saveAssCollectionTypes(com.boco.eoms.parnter.assess.assautocollection.AssCollectionType)
	 *      
	 */
	public void saveAssCollectionType(final AssCollectionType assCollectionType) {
		if ((assCollectionType.getId() == null) || (assCollectionType.getId().equals("")))
			getHibernateTemplate().save(assCollectionType);
		else
			getHibernateTemplate().saveOrUpdate(assCollectionType);
	}

	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionTypeDao#removeAssCollectionTypes(java.lang.String)
	 *      
	 */
    public void removeAssCollectionType(final String id) {
		getHibernateTemplate().delete(getAssCollectionType(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		AssCollectionType assCollectionType = this.getAssCollectionType(id);
		if(assCollectionType==null){
			return "";
		}
		return assCollectionType.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.parnter.assess.assautocollection.AssCollectionTypeDao#getAssCollectionTypes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getAssCollectionTypes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionType assCollectionType";
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
	
	public AssCollectionType getAssCollectionTypeByNodeId(final String nodeId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionType assCollectionType where assCollectionType.treeNodeId=:nodeId";
				Query query = session.createQuery(queryStr);
				query.setString("nodeId", nodeId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssCollectionType) result.iterator().next();
				} else {
					return new AssCollectionType();
				}
			}
		};
		return (AssCollectionType) getHibernateTemplate().execute(callback);
	}	
}