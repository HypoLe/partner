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
import com.boco.eoms.partner.deviceAssess.dao.RepairinfoDao;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;

/**
 * <p>
 * Title:板件返修事件信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class RepairinfoDaoHibernate extends BaseDaoHibernate implements RepairinfoDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.RepairinfoDao#getRepairinfos()
	 *      
	 */
	public List getRepairinfos() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Repairinfo repairinfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.RepairinfoDao#getRepairinfo(java.lang.String)
	 *
	 */
	public Repairinfo getRepairinfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) 
					throws HibernateException {
				String queryStr = "from Repairinfo repairinfo where repairinfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (Repairinfo) result.iterator().next();
				} else {
					return new Repairinfo();
				}
			}
		};
		return (Repairinfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.RepairinfoDao#saveRepairinfos(com.boco.eoms.partner.deviceassess.Repairinfo)
	 *      
	 */
	public void saveRepairinfo(final Repairinfo repairinfo) {
		if ((repairinfo.getId() == null) || (repairinfo.getId().equals("")))
			getHibernateTemplate().save(repairinfo);
		else
			getHibernateTemplate().saveOrUpdate(repairinfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.RepairinfoDao#removeRepairinfos(java.lang.String)
	 *      
	 */
    public void removeRepairinfo(final String id) {
		getHibernateTemplate().delete(getRepairinfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		Repairinfo repairinfo = this.getRepairinfo(id);
		if(repairinfo==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.RepairinfoDao#getRepairinfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getRepairinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Repairinfo repairinfo";
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