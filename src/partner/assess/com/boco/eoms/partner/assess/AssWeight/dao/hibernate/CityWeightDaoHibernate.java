package com.boco.eoms.partner.assess.AssWeight.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssWeight.dao.ICityWeightDao;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeight;

/**
 * <p>
 * Title:地市配置权重 dao的hibernate实现
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public abstract class CityWeightDaoHibernate extends BaseDaoHibernate implements ICityWeightDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.parnter.asseva.assweight.ICityWeightDao#getCityWeights()
	 *      
	 */
	public List getCityWeights() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from CityWeight cityWeight";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.parnter.asseva.assweight.ICityWeightDao#getCityWeight(java.lang.String)
	 *
	 */
	public CityWeight getCityWeight(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from CityWeight cityWeight where cityWeight.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (CityWeight) result.iterator().next();
				} else {
					return new CityWeight();
				}
			}
		};
		return (CityWeight) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.parnter.asseva.assweight.ICityWeightDao#saveCityWeights(com.boco.eoms.parnter.asseva.assweight.CityWeight)
	 *      
	 */
	public void saveCityWeight(final CityWeight cityWeight) {
		if ((cityWeight.getId() == null) || (cityWeight.getId().equals("")))
			getHibernateTemplate().save(cityWeight);
		else
			getHibernateTemplate().saveOrUpdate(cityWeight);
	}

	/**
	 * 
	 * @see com.boco.eoms.parnter.asseva.assweight.ICityWeightDao#removeCityWeights(java.lang.String)
	 *      
	 */
    public void removeCityWeight(final String id) {
		getHibernateTemplate().delete(getCityWeight(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		CityWeight cityWeight = this.getCityWeight(id);
		if(cityWeight==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.parnter.asseva.assweight.ICityWeightDao#getCityWeights(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getCityWeights(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from CityWeight cityWeight";
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
	
	public List getCityWeights(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from CityWeight cityWeight where deleted = '0'";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}		
}