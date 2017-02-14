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
import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionResultDao;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionResult;

/**
 * <p>
 * Title:采集结果 dao的hibernate实现
 * </p>
 * <p>
 * Description:采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssCollectionResultDaoHibernate extends BaseDaoHibernate implements AssCollectionResultDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.assess.assautocollection.AssCollectionResultDao#getAssCollectionResults()
	 *      
	 */
	public List getAssCollectionResults() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionResult assCollectionResult";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.assess.assautocollection.AssCollectionResultDao#getAssCollectionResult(java.lang.String)
	 *
	 */
	public AssCollectionResult getAssCollectionResult(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionResult assCollectionResult where assCollectionResult.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssCollectionResult) result.iterator().next();
				} else {
					return new AssCollectionResult();
				}
			}
		};
		return (AssCollectionResult) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.assess.assautocollection.AssCollectionResultDao#saveAssCollectionResults(com.boco.eoms.partner.assess.assautocollection.AssCollectionResult)
	 *      
	 */
	public void saveAssCollectionResult(final AssCollectionResult assCollectionResult) {
		if ((assCollectionResult.getId() == null) || (assCollectionResult.getId().equals("")))
			getHibernateTemplate().save(assCollectionResult);
		else
			getHibernateTemplate().saveOrUpdate(assCollectionResult);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.assess.assautocollection.AssCollectionResultDao#removeAssCollectionResults(java.lang.String)
	 *      
	 */
    public void removeAssCollectionResult(final String id) {
		getHibernateTemplate().delete(getAssCollectionResult(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		AssCollectionResult assCollectionResult = this.getAssCollectionResult(id);
		if(assCollectionResult==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.assess.assautocollection.AssCollectionResultDao#getAssCollectionResults(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getAssCollectionResults(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionResult assCollectionResult";
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
	
	/**
	 * 按条件得到采集结果
	 */	
	public List getAssCollectionResults( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssCollectionResult assCollectionResult ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}