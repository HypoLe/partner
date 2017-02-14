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
import com.boco.eoms.partner.deviceAssess.dao.FacilityNumDao;
import com.boco.eoms.partner.deviceAssess.model.FacilityNum;

/**
 * <p>
 * Title:设备量信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p>
 * Wed Sep 29 11:28:40 CST 2010
 * </p>
 * 
 * @author benweiwei 
 * @version 1.0
 * 
 */
public class FacilityNumDaoHibernate extends BaseDaoHibernate implements FacilityNumDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityNumDao#getFacilityNums()
	 *      
	 */
	public List getFacilityNums() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FacilityNum facilityNum";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.FacilityNumDao#getFacilityNum(java.lang.String)
	 *
	 */
	public FacilityNum getFacilityNum(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FacilityNum facilityNum where facilityNum.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (FacilityNum) result.iterator().next();
				} else {
					return new FacilityNum();
				}
			}
		};
		return (FacilityNum) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityNumDao#saveFacilityNums(com.boco.eoms.partner.deviceassess.FacilityNum)
	 *      
	 */
	public void saveFacilityNum(final FacilityNum facilityNum) {
		if ((facilityNum.getId() == null) || (facilityNum.getId().equals("")))
			getHibernateTemplate().save(facilityNum);
		else
			getHibernateTemplate().saveOrUpdate(facilityNum);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityNumDao#removeFacilityNums(java.lang.String)
	 *      
	 */
    public void removeFacilityNum(final String id) {
		getHibernateTemplate().delete(getFacilityNum(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		FacilityNum facilityNum = this.getFacilityNum(id);
		if(facilityNum==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.FacilityNumDao#getFacilityNums(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getFacilityNums(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FacilityNum facilityNum";
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