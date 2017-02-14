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
import com.boco.eoms.partner.deviceAssess.dao.LserveinfoDao;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;

/**
 * <p>
 * Title:现场服务事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class LserveinfoDaoHibernate extends BaseDaoHibernate implements LserveinfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.LserveinfoDao#getLserveinfos()
	 *      
	 */
	public List getLserveinfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Lserveinfo lserveinfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.LserveinfoDao#getLserveinfo(java.lang.String)
	 *
	 */
	public Lserveinfo getLserveinfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Lserveinfo lserveinfo where lserveinfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Lserveinfo) result.iterator().next();
				} else {
					return new Lserveinfo();
				}
			}
		};
		return (Lserveinfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.LserveinfoDao#saveLserveinfos(com.boco.eoms.partner.deviceassess.Lserveinfo)
	 *      
	 */
	public void saveLserveinfo(final Lserveinfo lserveinfo) {
		if ((lserveinfo.getId() == null) || (lserveinfo.getId().equals("")))
			getHibernateTemplate().save(lserveinfo);
		else
			getHibernateTemplate().saveOrUpdate(lserveinfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.LserveinfoDao#removeLserveinfos(java.lang.String)
	 *      
	 */
    public void removeLserveinfo(final String id) {
		getHibernateTemplate().delete(getLserveinfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Lserveinfo lserveinfo = this.getLserveinfo(id);
		if(lserveinfo==null){
			return "";
		}
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.LserveinfoDao#getLserveinfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getLserveinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Lserveinfo lserveinfo";
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