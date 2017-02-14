package com.boco.eoms.partner.deviceAssess.dao.hibernate;

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
import com.boco.eoms.partner.deviceAssess.dao.BackEstimateDao;
import com.boco.eoms.partner.deviceAssess.model.BackEstimate;

/**
 * <p>
 * Title:后评估指标体系 dao的hibernate实现
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010
 * </p> 
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class BackEstimateDaoHibernate extends BaseDaoHibernate implements BackEstimateDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BackEstimateDao#getBackEstimates()
	 *      
	 */
	public List getBackEstimates() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BackEstimate backEstimate";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.BackEstimateDao#getBackEstimate(java.lang.String)
	 *
	 */
	public BackEstimate getBackEstimate(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BackEstimate backEstimate where backEstimate.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (BackEstimate) result.iterator().next();
				} else {
					return new BackEstimate();
				}
			}
		};
		return (BackEstimate) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BackEstimateDao#saveBackEstimates(com.boco.eoms.partner.deviceassess.BackEstimate)
	 *      
	 */
	public void saveBackEstimate(final BackEstimate backEstimate) {
		if ((backEstimate.getId() == null) || (backEstimate.getId().equals("")))
			getHibernateTemplate().save(backEstimate);
		else
			getHibernateTemplate().saveOrUpdate(backEstimate);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BackEstimateDao#removeBackEstimates(java.lang.String)
	 *      
	 */
    public void removeBackEstimate(final String id) {
		getHibernateTemplate().delete(getBackEstimate(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		BackEstimate backEstimate = this.getBackEstimate(id);
		if(backEstimate==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BackEstimateDao#getBackEstimates(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getBackEstimates(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BackEstimate backEstimate";
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
				map.put("total",Integer.valueOf(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	public List getBackEstimatesList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BackEstimate backEstimate ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}