package com.boco.eoms.partner.quality.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.quality.dao.IPnrQualityMainDao;
import com.boco.eoms.partner.quality.model.PnrQualityMain;

public class PnrQualityMainDaoHibernate extends BaseDaoHibernate  implements IPnrQualityMainDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityMain(){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityMain pnrQualityMain";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);	    
	};
	/**
	 * 根据主键ID查询质量管理报告列表
	 *
	 */
	public PnrQualityMain getPnrQualityMain(final String id){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityMain pnrQualityMain where pnrQualityMain.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrQualityMain) result.iterator().next();
				} else {
					return new PnrQualityMain();
				}
			}
		};
		return (PnrQualityMain) getHibernateTemplate().execute(callback);    	
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityMain(PnrQualityMain pnrQualityMain){
		if ((pnrQualityMain.getId() == null) || (pnrQualityMain.getId().equals("")))
			getHibernateTemplate().save(pnrQualityMain);
		else
			getHibernateTemplate().saveOrUpdate(pnrQualityMain);    	
		
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityMain(final String id){
		getHibernateTemplate().delete(getPnrQualityMain(id));
		
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityMains(final Integer curPage, final Integer pageSize,
			final String whereStr){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
					String queryStr = "from PnrQualityMain pnrQualityMain";
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
		
	};
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityMains(final String where){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityMain pnrQualityMain ";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);	   		
	};
}
