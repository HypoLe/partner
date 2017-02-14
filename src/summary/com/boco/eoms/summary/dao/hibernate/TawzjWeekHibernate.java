package com.boco.eoms.summary.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.summary.dao.ITawzjWeekDao;
import com.boco.eoms.summary.model.TawzjWeek;
import com.boco.eoms.summary.model.TawzjWeekCheck;
import com.boco.eoms.summary.model.TawzjWeekSub;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TawzjWeekHibernate extends BaseDaoHibernate implements
		ITawzjWeekDao {

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#getTawzjWeeks(com.boco.eoms.TawzjWeek.model.TawzjWeek)
	 */
	public List getTawzjWeeks(final TawzjWeek TawzjWeek) {
		return getHibernateTemplate().find("from TawzjWeek");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (TawzjWeek == null) { return
		 * getHibernateTemplate().find("from TawzjWeek"); } else { // filter on
		 * properties set in the TawzjWeek HibernateCallback callback = new
		 * HibernateCallback() { public Object doInHibernate(Session session)
		 * throws HibernateException { Example ex =
		 * Example.create(TawzjWeek).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return session.createCriteria(TawzjWeek.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#getTawzjWeek(String id)
	 */
	public TawzjWeek getTawzjWeek(final String id) {
		TawzjWeek TawzjWeek = (TawzjWeek) getHibernateTemplate().get(
				TawzjWeek.class, id);
		if (TawzjWeek == null) {
			throw new ObjectRetrievalFailureException(TawzjWeek.class, id);
		}

		return TawzjWeek;
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#saveTawzjWeek(TawzjWeek
	 *      TawzjWeek)
	 */
	public String  saveTawzjWeek(final TawzjWeek TawzjWeek) {
		if ((TawzjWeek.getId() == null) || (TawzjWeek.getId().equals("")))
			getHibernateTemplate().save(TawzjWeek);
		else
			getHibernateTemplate().saveOrUpdate(TawzjWeek);
		
		return TawzjWeek.getId();
		
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#removeTawzjWeek(String id)
	 */
	public void removeTawzjWeek(final String id) {
		getHibernateTemplate().delete(getTawzjWeek(id));
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#getTawzjWeeks(final Integer
	 *      curPage, final Integer pageSize,final String whereStr)
	 */
	public Map getTawzjWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		// filter on properties set in the TawzjWeek
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawzjWeek";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				Integer total = (Integer) session.createQuery(queryCountStr)
						.iterate().next();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#getTawzjWeeks(final Integer
	 *      curPage, final Integer pageSize)
	 */
	public Map getTawzjWeeks(final Integer curPage, final Integer pageSize) {
		return this.getTawzjWeeks(curPage, pageSize, null);
	}

	/**
	 * @see com.boco.eoms.TawzjWeek.dao.TawzjWeekDao#getChildList(String
	 *      parentId)
	 */
	public ArrayList getChildList(String parentId) {
		String hql = "";
		if (parentId.equals("-1")) {
			hql = " from TawzjWeek obj order by obj.id";
		}
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	public TawzjWeek getTawzjWeeks(String weekid,String userid) {
		TawzjWeek tawzjWeek = null; 
		List list = (List) getHibernateTemplate().find(
				"from TawzjWeek obj where obj.weekid = '" + weekid + "' and obj.cruser = '"+userid+"'");
		if(list.size()>0){
			tawzjWeek = (TawzjWeek)list.iterator().next();
		}
		
		return tawzjWeek;
	}

	/**
	 * 根据主id得到每天填写的内容
	 * @param tawzjWeekId
	 * @return
	 */
	public List getTawzjWeekSub(final String tawzjWeekId) {
		String sql = "from TawzjWeekSub obj where obj.weekid ='" + tawzjWeekId
				+ "' order by obj.week";
		return (ArrayList) getHibernateTemplate().find(sql);
	}
	
	
	/**
	 * @param tawzjWeekSub
	 * @return
	 */
	public String  saveTawzjWeekSub(final TawzjWeekSub tawzjWeekSub) {
		if ((tawzjWeekSub.getId() == null) || (tawzjWeekSub.getId().equals("")))
			getHibernateTemplate().save(tawzjWeekSub);
		else
			getHibernateTemplate().saveOrUpdate(tawzjWeekSub);
		
		return tawzjWeekSub.getId();
		
	}
	
	/**
	 * @param tawzjWeekcheck
	 * @return
	 */
	public String  saveTawzjWeekCheck(final TawzjWeekCheck tawzjWeekCheck) {
		if ((tawzjWeekCheck.getId() == null) || (tawzjWeekCheck.getId().equals("")))
			getHibernateTemplate().save(tawzjWeekCheck);
		else
			getHibernateTemplate().saveOrUpdate(tawzjWeekCheck);
		return tawzjWeekCheck.getId();
		
	}
	

	/**
	 * 根据主id得到审核信息
	 * @param tawzjWeekId
	 * @return
	 */
	public List getTawzjWeekCheck(final String tawzjWeekId) {
		String sql = "from TawzjWeekCheck obj where obj.weekid ='" + tawzjWeekId
				+ "' order by obj.id";
		return (ArrayList) getHibernateTemplate().find(sql);
	}
	
	/**
	 * 根据审核人查找审核列表
	 * @param tawzjWeekId
	 * @return
	 */
	public List getTawzjWeekCheckList(final String userid) {
		String sql = "from TawzjWeek obj where obj.auditer ='" + userid
				+ "'  and obj.state in (1,2,5,6)";
		return (ArrayList) getHibernateTemplate().find(sql);
	}
	
	
	  public String  findauditer(final String userid,final String weekid){
			String sql = "from TawzjWeekCheck obj where obj.auditer ='" + userid
			+ "'  and obj.flag =2 and obj.weekid = '"+weekid+"'";
			List list = (List) getHibernateTemplate().find(sql);
			String sender="";
			if(list!=null&&list.size()>0){
				TawzjWeekCheck check = (TawzjWeekCheck)list.get(0);
				sender = check.getSender();
			}
			return sender;
	  }
}
