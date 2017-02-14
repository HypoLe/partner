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
import com.boco.eoms.partner.serviceArea.dao.ISitePapersDao;
import com.boco.eoms.partner.serviceArea.model.SitePapers;

/**
 * <p>
 * Title:基站证件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:基站证件信息
 * </p>
 * <p>
 * Wed Nov 18 11:29:29 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SitePapersDaoHibernate extends BaseDaoHibernate implements ISitePapersDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#getSitePaperss()
	 *      
	 */
	public List getSitePaperss() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SitePapers sitePapers";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#getSitePapers(java.lang.String)
	 *
	 */
	public SitePapers getSitePapers(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SitePapers sitePapers where sitePapers.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (SitePapers) result.iterator().next();
				} else {
					return new SitePapers();
				}
			}
		};
		return (SitePapers) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#saveSitePaperss(com.boco.eoms.partner.servicearea.SitePapers)
	 *      
	 */
	public void saveSitePapers(final SitePapers sitePapers) {
		if ((sitePapers.getId() == null) || (sitePapers.getId().equals("")))
			getHibernateTemplate().save(sitePapers);
		else
			getHibernateTemplate().saveOrUpdate(sitePapers);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#removeSitePaperss(java.lang.String)
	 *      
	 */
    public void removeSitePapers(final String id) {
		getHibernateTemplate().delete(getSitePapers(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		SitePapers sitePapers = this.getSitePapers(id);
		if(sitePapers==null){
			return "";
		}
		return"";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#getSitePaperss(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getSitePaperss(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SitePapers sitePapers";
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
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#getSitePapersByPaperNo(java.lang.String)
	 *      
	 */
	public List getSitePapersByPaperNo( final String papersNo ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SitePapers sitePapers where sitePapers.papersNo = '"+papersNo+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.servicearea.ISitePapersDao#getSitePapersBySiteNo(java.lang.String)
	 *      
	 */	
	public List getSitePapersBySiteNo( final Integer siteNo ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SitePapers sitePapers where sitePapers.siteNo = '"+siteNo+"'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
}