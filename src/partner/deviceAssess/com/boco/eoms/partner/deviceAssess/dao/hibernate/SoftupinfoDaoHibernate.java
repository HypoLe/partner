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
import com.boco.eoms.partner.deviceAssess.dao.SoftupinfoDao;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;

/**
 * <p>
 * Title:软件升级事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class SoftupinfoDaoHibernate extends BaseDaoHibernate implements SoftupinfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftupinfoDao#getSoftupinfos()
	 *      
	 */
	public List getSoftupinfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Softupinfo softupinfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.SoftupinfoDao#getSoftupinfo(java.lang.String)
	 *
	 */
	public Softupinfo getSoftupinfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Softupinfo softupinfo where softupinfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Softupinfo) result.iterator().next();
				} else {
					return new Softupinfo();
				}
			}
		};
		return (Softupinfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftupinfoDao#saveSoftupinfos(com.boco.eoms.partner.deviceassess.Softupinfo)
	 *      
	 */
	public void saveSoftupinfo(final Softupinfo softupinfo) {
		if ((softupinfo.getId() == null) || (softupinfo.getId().equals("")))
			getHibernateTemplate().save(softupinfo);
		else
			getHibernateTemplate().saveOrUpdate(softupinfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftupinfoDao#removeSoftupinfos(java.lang.String)
	 *      
	 */
    public void removeSoftupinfo(final String id) {
		getHibernateTemplate().delete(getSoftupinfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Softupinfo softupinfo = this.getSoftupinfo(id);
		if(softupinfo==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftupinfoDao#getSoftupinfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getSoftupinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Softupinfo softupinfo";
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