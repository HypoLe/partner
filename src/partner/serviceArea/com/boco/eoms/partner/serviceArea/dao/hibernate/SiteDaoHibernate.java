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
import com.boco.eoms.partner.serviceArea.dao.ISiteDao;
import com.boco.eoms.partner.serviceArea.model.Site;

/**
 * <p>
 * Title:站点信息管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:站点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SiteDaoHibernate extends BaseDaoHibernate implements ISiteDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getSites()
	 *      
	 */
	public List getSites() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site";
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
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getSite(java.lang.String)
	 *
	 */
	public Site getSite(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site where site.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Site) result.iterator().next();
				} else {
					return new Site();
				}
			}
		};
		return (Site) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#saveSites(com.boco.eoms.partner.servicearea.Site)
	 *      
	 */
	public void saveSite(final Site site) {
		if ((site.getId() == null) || (site.getId().equals("")))
			getHibernateTemplate().save(site);
		else
			getHibernateTemplate().saveOrUpdate(site);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#removeSites(java.lang.String)
	 *      
	 */
    public void removeSite(final String id) {
		getHibernateTemplate().delete(getSite(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Site site = this.getSite(id);
		if(site==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getSites(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site";
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
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getSitesNo(final String whereStr)
	 *      
	 */ 
	public Integer getSitesNo(final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site";
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
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getBySitesSiteNo(java.lang.String)
	 *      
	 */
	public List getSitesBySiteNo( final String siteNo ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site where site.siteNo = '"+siteNo+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	/**
	 *
	 * @see com.boco.eoms.partner.servicearea.ISiteDao#getSiteBySiteNo(java.lang.String)
	 *
	 */
	public Site getSiteBySiteNo(final String siteNo) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Site site where site.siteNo=:siteNo";
				Query query = session.createQuery(queryStr);
				query.setString("siteNo", siteNo);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Site) result.iterator().next();
				} else {
					return new Site();
				}
			}
		};
		return (Site) getHibernateTemplate().execute(callback);
	}
	
}