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
import com.boco.eoms.partner.deviceAssess.dao.FacilityinfoDao;
import com.boco.eoms.partner.deviceAssess.model.Facilityinfo;

/**
 * <p>
 * Title:厂家设备问题事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 *  
 */
public class FacilityinfoDaoHibernate extends BaseDaoHibernate implements FacilityinfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityinfoDao#getFacilityinfos()
	 *      
	 */
	public List getFacilityinfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Facilityinfo facilityinfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.FacilityinfoDao#getFacilityinfo(java.lang.String)
	 *
	 */
	public Facilityinfo getFacilityinfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Facilityinfo facilityinfo where facilityinfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Facilityinfo) result.iterator().next();
				} else {
					return new Facilityinfo();
				}
			}
		};
		return (Facilityinfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityinfoDao#saveFacilityinfos(com.boco.eoms.partner.deviceassess.Facilityinfo)
	 *      
	 */
	public void saveFacilityinfo(final Facilityinfo facilityinfo) {
		if ((facilityinfo.getId() == null) || (facilityinfo.getId().equals("")))
			getHibernateTemplate().save(facilityinfo);
		else
			getHibernateTemplate().saveOrUpdate(facilityinfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityinfoDao#removeFacilityinfos(java.lang.String)
	 *      
	 */
    public void removeFacilityinfo(final String id) {
		getHibernateTemplate().delete(getFacilityinfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Facilityinfo facilityinfo = this.getFacilityinfo(id);
		if(facilityinfo==null){
			return "";
		}
		return facilityinfo.getIssueName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityinfoDao#getFacilityinfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getFacilityinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Facilityinfo facilityinfo";
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