package com.boco.eoms.partner.serviceArea.dao.hibernate;

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
import com.boco.eoms.partner.serviceArea.dao.IResidentSiteDao;
import com.boco.eoms.partner.serviceArea.model.ResidentSite;

/**
 * <p>
 * Title:驻点信息管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:驻点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @version 1.0
 * 
 */
public class ResidentSiteDaoHibernate extends BaseDaoHibernate implements IResidentSiteDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getResidentSites()
	 *      
	 */
	public List getResidentSites() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

	
    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *      
	 */
	public List listCityOfArea(final String areaId,final String len) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from TawSystemArea t  where t.areaid like '" + areaId + "%' and length(areaid)="+ len +" ";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

    /**
	 * 
	 * @see com.boco.eoms.partner.maintenance.CheckDao#getChecks()
	 *      
	 */
	public List listCountryOfCity(final String cityId,final String len) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String hql = "from TawSystemArea t  where t.areaid like '" + cityId + "%' and length(areaid)="+ len +" ";
				List list = getHibernateTemplate().find(hql);
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	

	
	/**
	 *
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getResidentSite(java.lang.String)
	 *
	 */
	public ResidentSite getResidentSite(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite where residentSite.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (ResidentSite) result.iterator().next();
				} else {
					return new ResidentSite();
				}
			}
		};
		return (ResidentSite) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#saveResidentSites(com.boco.eoms.partner.servicearea.ResidentSite)
	 *      
	 */
	public void saveResidentSite(final ResidentSite residentSite) {
		if ((residentSite.getId() == null) || (residentSite.getId().equals("")))
			getHibernateTemplate().save(residentSite);
		else
			getHibernateTemplate().saveOrUpdate(residentSite);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#removeResidentSites(java.lang.String)
	 *      
	 */
    public void removeResidentSite(final String id) {
		getHibernateTemplate().delete(getResidentSite(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		ResidentSite residentSite = this.getResidentSite(id);
		if(residentSite==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getResidentSites(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getResidentSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite";
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
 	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getResidentSitesNo(final String whereStr)
	 *      
	 */ 
	public Integer getResidentSitesNo(final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				return new Integer(total);
			}
		};
		return (Integer) getHibernateTemplate().execute(callback);
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getByResidentSitesResidentSiteNo(java.lang.String)
	 *      
	 */
	public List getResidentSitesByResidentSiteNo( final String residentSiteNo ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite where residentSite.residentSiteNo = '"+residentSiteNo+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	/**
	 *
	 * @see com.boco.eoms.partner.servicearea.IResidentSiteDao#getResidentSiteByResidentSiteNo(java.lang.String)
	 *
	 */
	public ResidentSite getResidentSiteByResidentSiteNo(final String residentSiteNo) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from ResidentSite residentSite where residentSite.residentSiteNo=:residentSiteNo";
				Query query = session.createQuery(queryStr);
				query.setString("residentSiteNo", residentSiteNo);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (ResidentSite) result.iterator().next();
				} else {
					return new ResidentSite();
				}
			}
		};
		return (ResidentSite) getHibernateTemplate().execute(callback);
	}
	
}