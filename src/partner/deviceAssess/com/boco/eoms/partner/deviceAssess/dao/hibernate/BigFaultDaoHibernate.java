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
import com.boco.eoms.partner.deviceAssess.dao.BigFaultDao;
import com.boco.eoms.partner.deviceAssess.model.BigFault;

/**
 * <p>
 * Title:厂家设备重大故障事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0 
 * 
 */
public class BigFaultDaoHibernate extends BaseDaoHibernate implements BigFaultDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BigFaultDao#getBigFaults()
	 *      
	 */
	public List getBigFaults() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BigFault bigFault";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.BigFaultDao#getBigFault(java.lang.String)
	 *
	 */
	public BigFault getBigFault(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BigFault bigFault where bigFault.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (BigFault) result.iterator().next();
				} else {
					return new BigFault();
				}
			}
		};
		return (BigFault) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BigFaultDao#saveBigFaults(com.boco.eoms.partner.deviceassess.BigFault)
	 *      
	 */
	public void saveBigFault(final BigFault bigFault) {
		if ((bigFault.getId() == null) || (bigFault.getId().equals("")))
			getHibernateTemplate().save(bigFault);
		else
			getHibernateTemplate().saveOrUpdate(bigFault);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BigFaultDao#removeBigFaults(java.lang.String)
	 *      
	 */
    public void removeBigFault(final String id) {
		getHibernateTemplate().delete(getBigFault(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		BigFault bigFault = this.getBigFault(id);
		if(bigFault==null){
			return "";
		}
		return bigFault.getBigFaultName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.BigFaultDao#getBigFaults(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getBigFaults(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BigFault bigFault";
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
	
}