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
import com.boco.eoms.partner.deviceAssess.dao.FactoryDisposeDao;
import com.boco.eoms.partner.deviceAssess.model.FactoryDispose;

/**
 * <p>
 * Title:厂家处理设备故障事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 15:01:06 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FactoryDisposeDaoHibernate extends BaseDaoHibernate implements FactoryDisposeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FactoryDisposeDao#getFactoryDisposes()
	 *       
	 */
	public List getFactoryDisposes() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FactoryDispose factoryDispose";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.FactoryDisposeDao#getFactoryDispose(java.lang.String)
	 *
	 */
	public FactoryDispose getFactoryDispose(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FactoryDispose factoryDispose where factoryDispose.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (FactoryDispose) result.iterator().next();
				} else {
					return new FactoryDispose();
				}
			}
		};
		return (FactoryDispose) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FactoryDisposeDao#saveFactoryDisposes(com.boco.eoms.partner.deviceassess.FactoryDispose)
	 *      
	 */
	public void saveFactoryDispose(final FactoryDispose factoryDispose) {
		if ((factoryDispose.getId() == null) || (factoryDispose.getId().equals("")))
			getHibernateTemplate().save(factoryDispose);
		else
			getHibernateTemplate().saveOrUpdate(factoryDispose);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FactoryDisposeDao#removeFactoryDisposes(java.lang.String)
	 *      
	 */
    public void removeFactoryDispose(final String id) {
		getHibernateTemplate().delete(getFactoryDispose(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		FactoryDispose factoryDispose = this.getFactoryDispose(id);
		if(factoryDispose==null){
			return "";
		} else {
			return factoryDispose.getAffairName();
		}
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FactoryDisposeDao#getFactoryDisposes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getFactoryDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FactoryDispose factoryDispose";
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