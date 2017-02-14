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
import com.boco.eoms.partner.baseinfo.dao.IResumeDao;
import com.boco.eoms.partner.baseinfo.model.Resume;

/**
 * <p>
 * Title:工作简历 dao的hibernate实现
 * </p>
 * <p>
 * Description:工作简历
 * </p>
 * <p>
 * Fri Dec 18 16:50:43 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class ResumeDaoHibernate extends BaseDaoHibernate implements IResumeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#getResumes()
	 *      
	 */
	public List getResumes() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Resume resume";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#getResumes(final String where)
	 *      
	 */
	public List getResumes(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Resume resume";
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
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#getResume(java.lang.String)
	 *
	 */
	public Resume getResume(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Resume resume where resume.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Resume) result.iterator().next();
				} else {
					return new Resume();
				}
			}
		};
		return (Resume) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#saveResumes(com.boco.eoms.partner.baseinfo.Resume)
	 *      
	 */
	public void saveResume(final Resume resume) {
		if ((resume.getId() == null) || (resume.getId().equals("")))
			getHibernateTemplate().save(resume);
		else
			getHibernateTemplate().saveOrUpdate(resume);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#removeResumes(java.lang.String)
	 *      
	 */
    public void removeResume(final String id) {
		getHibernateTemplate().delete(getResume(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Resume resume = this.getResume(id);
		if(resume==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#getResumes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Resume resume";
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
	 * @see com.boco.eoms.partner.baseinfo.IResumeDao#getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date ccds ,final Date ccde ,final Date dds, final Date dde)
	 *      
	 */ 
	public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date ccds ,final Date ccde ,final Date dds, final Date dde) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Resume resume ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				
				StringBuffer date = new StringBuffer();
				if (ccds != null&&ccde!=null) {
					date.append(" and commencementDate >= :ccds and commencementDate <= :ccde ");
				}
				if (dds != null&&dde!=null) {
					date.append(" and dimissionDate >= :dds and dimissionDate <= :dde ");
				}
				queryStr = queryStr+date.toString();
//				String queryCountStr = "select count(*) " + queryStr;
//				int total = ((Integer) session.createQuery(queryCountStr)
//						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				if (ccds != null&&ccde!=null) {
					query.setTimestamp("ccds", ccds);
					query.setTimestamp("ccde", ccde);
					if(dds != null&&dde!=null){
						query.setTimestamp("dds", dds);
						query.setTimestamp("dde", dde);
					}
				}else{
					if(dds != null&&dde!=null){
						query.setTimestamp("dds", dds);
						query.setTimestamp("dde", dde);
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