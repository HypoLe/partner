package com.boco.eoms.summary.dao.hibernate;

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
import com.boco.eoms.summary.dao.TawDutyWeekDao;
import com.boco.eoms.summary.model.TawDutyWeek;

/**
 * <p>
 * Title:值周数据 dao的hibernate实现
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Tue Jun 16 17:25:37 CST 2009
 * </p>
 * 
 * @author LXM
 * @version 3.5
 * 
 */
public class TawDutyWeekDaoHibernate extends BaseDaoHibernate implements TawDutyWeekDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.summary.TawDutyWeekDao#getTawDutyWeeks()
	 *      
	 */
	public List getTawDutyWeeks() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawDutyWeek tawDutyWeek";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.summary.TawDutyWeekDao#getTawDutyWeek(java.lang.String)
	 *
	 */
	public TawDutyWeek getTawDutyWeek(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawDutyWeek tawDutyWeek where tawDutyWeek.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TawDutyWeek) result.iterator().next();
				} else {
					return new TawDutyWeek();
				}
			}
		};
		return (TawDutyWeek) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.summary.TawDutyWeekDao#saveTawDutyWeeks(com.boco.eoms.summary.TawDutyWeek)
	 *      
	 */
	public void saveTawDutyWeek(final TawDutyWeek tawDutyWeek) {
		if ((tawDutyWeek.getId() == null) || (tawDutyWeek.getId().equals("")))
			getHibernateTemplate().save(tawDutyWeek);
		else
			getHibernateTemplate().saveOrUpdate(tawDutyWeek);
	}

	/**
	 * 
	 * @see com.boco.eoms.summary.TawDutyWeekDao#removeTawDutyWeeks(java.lang.String)
	 *      
	 */
    public void removeTawDutyWeek(final String id) {
		getHibernateTemplate().delete(getTawDutyWeek(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		TawDutyWeek tawDutyWeek = this.getTawDutyWeek(id);
		if(tawDutyWeek==null){
			return "";
		}
		//TODO 请修改代码
		return tawDutyWeek.getUserId();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.summary.TawDutyWeekDao#getTawDutyWeeks(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,final String title,final String weekFlag,final String userName,final String startTime,final String endTime) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawDutyWeek tawDutyWeek where deleted = 0";
				if(title!=null&&!title.equals("")){
					queryStr += " and title like '%"+title+"%'";
				}
				if(weekFlag!=null&&!weekFlag.equals("")){
					queryStr += " and weekFlag = '"+weekFlag+"'";
				}
				if(userName!=null&&!userName.equals("")){
					queryStr += " and userName = '"+userName+"'";
				}
				if(startTime!=null&&!startTime.equals("")){
					queryStr += " and insertTime >= '"+startTime+"'";
				}
				if(endTime!=null&&!endTime.equals("")){
					queryStr += " and insertTime <= '"+endTime+"'";
				}
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue()
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
	public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,final String userid) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawDutyWeek tawDutyWeek where deleted = 0 and userId = '"+ userid +"'";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue()
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
	
}