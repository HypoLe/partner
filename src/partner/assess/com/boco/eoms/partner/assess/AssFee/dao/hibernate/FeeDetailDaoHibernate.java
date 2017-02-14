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
import com.boco.eoms.partner.assess.AssFee.dao.IFeeDetailDao;
import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;

/**
 * <p>
 * Title:光缆线路付费信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeDetailDaoHibernate extends BaseDaoHibernate implements IFeeDetailDao,
		ID2NameDAO {
	

	public List getFeeDetails() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeDetail feeDetail";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	

	public FeeDetail getFeeDetail(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeDetail feeDetail where feeDetail.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (FeeDetail) result.iterator().next();
				} else {
					return new FeeDetail();
				}
			}
		};
		return (FeeDetail) getHibernateTemplate().execute(callback);
	}
	

	public void saveFeeDetail(final FeeDetail feeDetail) {
		if ((feeDetail.getId() == null) || (feeDetail.getId().equals("")))
			getHibernateTemplate().save(feeDetail);
		else
			getHibernateTemplate().saveOrUpdate(feeDetail);
	}


    public void removeFeeDetail(final String id) {
		getHibernateTemplate().delete(getFeeDetail(id));
	}


	public String id2Name(String id) throws DictDAOException {
		FeeDetail feeDetail = this.getFeeDetail(id);
		if(feeDetail==null){
			return "";
		}
		return "";
	}
 

	public Map getFeeDetails(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeDetail feeDetail";
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
	 * 按条件得到光缆线路付费信息
	 */	
	public List getFeeDetailList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FeeDetail feeDetail ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
}