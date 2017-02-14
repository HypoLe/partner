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
import com.boco.eoms.partner.baseinfo.dao.PartnerUserAndDeptDao;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;

/**

 * 
 */
public class PartnerUserAndDeptDaoHibernate extends BaseDaoHibernate implements PartnerUserAndDeptDao,
		ID2NameDAO {

	@Override
	public String id2Name(String id) throws DictDAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserAndAreaDao#getPartnerUserAndAreas(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerUserAndDepts(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndDept partnerUserAndDept";
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

	@Override
	public PartnerUserAndDept getPartnerUserAndDept(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndDept partnerUserAndDept where partnerUserAndDept.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerUserAndDept) result.iterator().next();
				} else {
					return new PartnerUserAndDept();
				}
			}
		};
		return (PartnerUserAndDept) getHibernateTemplate().execute(callback);
	}

	@Override
	public Boolean isunique(final String userId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndDept partnerUserAndDept where partnerUserAndDept.userId=:userId";
				Query query = session.createQuery(queryStr);
				query.setString("userId", userId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return Boolean.valueOf(false);
				} else {
					return Boolean.valueOf(true);
				}
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
	}
	@Override
	public void savePartnerUserAndDept(final PartnerUserAndDept partnerUserAndDept) {
		if ((partnerUserAndDept.getId() == null) || (partnerUserAndDept.getId().equals("")))
			getHibernateTemplate().save(partnerUserAndDept);
		else
			getHibernateTemplate().saveOrUpdate(partnerUserAndDept);
	}

	@Override
	public void removePartnerUserAndDept(String id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(getPartnerUserAndDept(id));
	}

	@Override
	public PartnerUserAndDept getPartnerUserAndDeptByDeptId(
			final String deptId) {
		// TODO Auto-generated method stub
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUserAndDept p where p.deptId = :d1 or p.deptId like :d2 or p.deptId like :d3 or p.deptId like :d4";
				Query query = session.createQuery(queryStr);
				query.setString("d1", deptId);
				query.setString("d2", deptId+",%");
				query.setString("d3", "%,"+deptId+",%");
				query.setString("d4", "%,"+deptId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerUserAndDept) result.iterator().next();
				} else {
					return new PartnerUserAndDept();
				}
			}
		};
		return (PartnerUserAndDept) getHibernateTemplate().execute(callback);
	}
	
}