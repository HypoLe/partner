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
import com.boco.eoms.partner.deviceAssess.dao.CounselDao;
import com.boco.eoms.partner.deviceAssess.model.Counsel;

/**
 * <p>
 * Title:咨询服务事件信息表 dao的hibernate实现
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p>
 * <p> 
 * Mon Sep 27 15:01:30 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class CounselDaoHibernate extends BaseDaoHibernate implements CounselDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.CounselDao#getCounsels()
	 *      
	 */
	public List getCounsels() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Counsel counsel";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.CounselDao#getCounsel(java.lang.String)
	 *
	 */
	public Counsel getCounsel(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Counsel counsel where counsel.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Counsel) result.iterator().next();
				} else {
					return new Counsel();
				}
			}
		};
		return (Counsel) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.CounselDao#saveCounsels(com.boco.eoms.partner.deviceassess.Counsel)
	 *      
	 */
	public void saveCounsel(final Counsel counsel) {
		if ((counsel.getId() == null) || (counsel.getId().equals("")))
			getHibernateTemplate().save(counsel);
		else
			getHibernateTemplate().saveOrUpdate(counsel);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.CounselDao#removeCounsels(java.lang.String)
	 *      
	 */
    public void removeCounsel(final String id) {
		getHibernateTemplate().delete(getCounsel(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Counsel counsel = this.getCounsel(id);
		if(counsel==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.CounselDao#getCounsels(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getCounsels(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Counsel counsel";
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