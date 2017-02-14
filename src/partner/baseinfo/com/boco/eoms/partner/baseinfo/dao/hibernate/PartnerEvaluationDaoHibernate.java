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
import com.boco.eoms.partner.baseinfo.dao.PartnerEvaluationDao;
import com.boco.eoms.partner.baseinfo.model.PartnerEvaluation;

/**
 * <p>
 * Title:合作伙伴服务评价 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴服务评价
 * </p>
 * <p>
 * Tue Apr 27 16:50:24 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PartnerEvaluationDaoHibernate extends BaseDaoHibernate implements PartnerEvaluationDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerEvaluationDao#getPartnerEvaluations()
	 *      
	 */
	public List getPartnerEvaluations() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerEvaluation partnerEvaluation";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.PartnerEvaluationDao#getPartnerEvaluation(java.lang.String)
	 *
	 */
	public PartnerEvaluation getPartnerEvaluation(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerEvaluation partnerEvaluation where partnerEvaluation.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerEvaluation) result.iterator().next();
				} else {
					return new PartnerEvaluation();
				}
			}
		};
		return (PartnerEvaluation) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerEvaluationDao#savePartnerEvaluations(com.boco.eoms.partner.baseinfo.PartnerEvaluation)
	 *      
	 */
	public void savePartnerEvaluation(final PartnerEvaluation partnerEvaluation) {
		if ((partnerEvaluation.getId() == null) || (partnerEvaluation.getId().equals("")))
			getHibernateTemplate().save(partnerEvaluation);
		else
			getHibernateTemplate().saveOrUpdate(partnerEvaluation);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerEvaluationDao#removePartnerEvaluations(java.lang.String)
	 *      
	 */
    public void removePartnerEvaluation(final String id) {
		getHibernateTemplate().delete(getPartnerEvaluation(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerEvaluation partnerEvaluation = this.getPartnerEvaluation(id);
		if(partnerEvaluation==null){
			return "";
		}
		return partnerEvaluation.getPartnerId();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerEvaluationDao#getPartnerEvaluations(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerEvaluations(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerEvaluation partnerEvaluation";
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