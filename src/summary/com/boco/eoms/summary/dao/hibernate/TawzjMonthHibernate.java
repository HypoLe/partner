package com.boco.eoms.summary.dao.hibernate;

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
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.summary.dao.ITawzjMonthDao;
import com.boco.eoms.summary.model.TawzjMonth;

/**
 * <p>
 * Title:月工作总结 dao的hibernate实现
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:13:03 CST 2009
 * </p>
 * 
 * @author hanlu
 * @version 3.5
 * 
 */
public class TawzjMonthHibernate extends BaseDaoHibernate implements ITawzjMonthDao {
	
	/**
	 * 
	 * @see com.boco.eoms.summary.TawzjMonthDao#getTawzjMonths()
	 *      
	 */
	public List getTawzjMonths() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawzjMonth tawzjMonth";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.summary.TawzjMonthDao#getTawzjMonth(java.lang.String)
	 *
	 */
	public TawzjMonth getTawzjMonth(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawzjMonth tawzjMonth where tawzjMonth.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TawzjMonth) result.iterator().next();
				} else {
					return new TawzjMonth();
				}
			}
		};
		return (TawzjMonth) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.summary.TawzjMonthDao#saveTawzjMonths(com.boco.eoms.summary.TawzjMonth)
	 *      
	 */
	public void saveTawzjMonth(final TawzjMonth tawzjMonth) {
		if ((tawzjMonth.getId() == null) || (tawzjMonth.getId().equals("")))
			getHibernateTemplate().save(tawzjMonth);
		else
			getHibernateTemplate().saveOrUpdate(tawzjMonth);
	}

	/**
	 * 
	 * @see com.boco.eoms.summary.TawzjMonthDao#removeTawzjMonths(java.lang.String)
	 *      
	 */
    public void removeTawzjMonth(final String id) {
		getHibernateTemplate().delete(getTawzjMonth(id));
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.summary.TawzjMonthDao#getTawzjMonths(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getTawzjMonths(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawzjMonth tawzjMonth where tawzjMonth.deleted='0'";
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