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
import com.boco.eoms.partner.deviceAssess.dao.InsideDisposeDao;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;

/**
 * <p>
 * Title:移动内部处理的设备故障事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2010
 * </p>
 * 
 * @author benweiwei 
 * @version 1.0
 * 
 */
public class InsideDisposeDaoHibernate extends BaseDaoHibernate implements InsideDisposeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.InsideDisposeDao#getInsideDisposes()
	 *      
	 */
	public List getInsideDisposes() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InsideDispose insideDispose";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.InsideDisposeDao#getInsideDispose(java.lang.String)
	 *
	 */
	public InsideDispose getInsideDispose(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InsideDispose insideDispose where insideDispose.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (InsideDispose) result.iterator().next();
				} else {
					return new InsideDispose();
				}
			}
		};
		return (InsideDispose) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.InsideDisposeDao#saveInsideDisposes(com.boco.eoms.partner.deviceassess.InsideDispose)
	 *      
	 */
	public void saveInsideDispose(final InsideDispose insideDispose) {
		if ((insideDispose.getId() == null) || (insideDispose.getId().equals("")))
			getHibernateTemplate().save(insideDispose);
		else
			getHibernateTemplate().saveOrUpdate(insideDispose);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.InsideDisposeDao#removeInsideDisposes(java.lang.String)
	 *      
	 */
    public void removeInsideDispose(final String id) {
		getHibernateTemplate().delete(getInsideDispose(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		InsideDispose insideDispose = this.getInsideDispose(id);
		if(insideDispose==null){
			return "";
		} else {
			return insideDispose.getAffairName();
		}
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.InsideDisposeDao#getInsideDisposes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getInsideDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InsideDispose insideDispose";
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