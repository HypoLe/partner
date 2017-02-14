package com.boco.eoms.partner.assess.AssFee.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssFee.dao.IFeeTotalDao;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;

/**
 * <p>
 * Title:计算结果信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTotalDaoHibernate extends BaseDaoHibernate implements IFeeTotalDao,
		ID2NameDAO {
	

	public List getFeeTotals() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeTotal feeTotal";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	

	public FeeTotal getFeeTotal(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeTotal feeTotal where feeTotal.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (FeeTotal) result.iterator().next();
				} else {
					return new FeeTotal();
				}
			}
		};
		return (FeeTotal) getHibernateTemplate().execute(callback);
	}
	

	public void saveFeeTotal(final FeeTotal feeTotal) {
		if ((feeTotal.getId() == null) || (feeTotal.getId().equals("")))
			getHibernateTemplate().save(feeTotal);
		else
			getHibernateTemplate().saveOrUpdate(feeTotal);
	}


    public void removeFeeTotal(final String id) {
		getHibernateTemplate().delete(getFeeTotal(id));
	}


	public String id2Name(String id) throws DictDAOException {
		FeeTotal feeTotal = this.getFeeTotal(id);
		if(feeTotal==null){
			return "";
		}
		return "";
	}
 

	public Map getFeeTotals(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeTotal feeTotal";
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
				map.put("total", Integer.valueOf(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 按条件得到计算结果信息
	 */	
	public List getFeeTotalsList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeTotal feeTotal ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}