package com.boco.eoms.partner.baseinfo.dao.hibernate;

import java.util.Date;
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
import com.boco.eoms.partner.baseinfo.dao.IAptitudeDao;
import com.boco.eoms.partner.baseinfo.model.Aptitude;

/**
 * <p>
 * Title:合作伙伴资质 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴资质
 * </p>
 * <p>
 * Fri Dec 18 14:11:52 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AptitudeDaoHibernate extends BaseDaoHibernate implements IAptitudeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#getAptitudes()
	 *      
	 */
	public List getAptitudes() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Aptitude aptitude";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#getAptitudes(final String where)
	 *      
	 */
	public List getAptitudes(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Aptitude aptitude";
				if(where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#getAptitude(java.lang.String)
	 *
	 */
	public Aptitude getAptitude(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Aptitude aptitude where aptitude.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Aptitude) result.iterator().next();
				} else {
					return new Aptitude();
				}
			}
		};
		return (Aptitude) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#saveAptitudes(com.boco.eoms.partner.baseinfo.Aptitude)
	 *      
	 */
	public void saveAptitude(final Aptitude aptitude) {
		if ((aptitude.getId() == null) || (aptitude.getId().equals("")))
			getHibernateTemplate().save(aptitude);
		else
			getHibernateTemplate().saveOrUpdate(aptitude);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#removeAptitudes(java.lang.String)
	 *      
	 */
    public void removeAptitude(final String id) {
		getHibernateTemplate().delete(getAptitude(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Aptitude aptitude = this.getAptitude(id);
		if(aptitude==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#getAptitudes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Aptitude aptitude";
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
	 * @see com.boco.eoms.partner.baseinfo.IAptitudeDao#getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date ccds ,final Date ccde ,final Date dds, final Date dde)
	 *      
	 */ 
	public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date asts ,final Date aste ,final Date aets, final Date aete) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Aptitude aptitude";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
//				String queryCountStr = "select count(*) " + queryStr;
//
//				int total = ((Integer) session.createQuery(queryCountStr)
//						.iterate().next()).intValue();
				
				StringBuffer date = new StringBuffer();
				if (asts != null&&aste!=null) {
					date.append(" and aptitudeStartTime >= :asts and aptitudeStartTime <= :aste ");
				}
				if (aets != null&&aete!=null) {
					date.append(" and aptitudeEndTime >= :aets and aptitudeEndTime <= :aete ");
				}
				queryStr = queryStr+date.toString();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				if (asts != null&&aste!=null) {
					query.setTimestamp("asts", asts);
					query.setTimestamp("aste", aste);
					if(aets != null&&aete!=null){
						query.setTimestamp("aets", aets);
						query.setTimestamp("aete", aete);
					}
				}else{
					if(aets != null&&aete!=null){
						query.setTimestamp("aets", aets);
						query.setTimestamp("aete", aete);
					}
				}
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(result.size()));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}