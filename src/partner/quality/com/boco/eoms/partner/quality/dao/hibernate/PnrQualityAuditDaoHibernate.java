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
import com.boco.eoms.partner.quality.dao.IPnrQualityAuditDao;
import com.boco.eoms.partner.quality.model.PnrQualityAudit;

public class PnrQualityAuditDaoHibernate extends BaseDaoHibernate  implements IPnrQualityAuditDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrQualityAudit(){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityAudit pnrQualityAudit";
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
	public PnrQualityAudit getPnrQualityAudit(final String id){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityAudit pnrQualityAudit where pnrQualityAudit.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrQualityAudit) result.iterator().next();
				} else {
					return new PnrQualityAudit();
				}
			}
		};
		return (PnrQualityAudit) getHibernateTemplate().execute(callback);    	
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrQualityAudit(PnrQualityAudit pnrQualityAudit){
		if ((pnrQualityAudit.getId() == null) || (pnrQualityAudit.getId().equals("")))
			getHibernateTemplate().save(pnrQualityAudit);
		else
			getHibernateTemplate().saveOrUpdate(pnrQualityAudit);    	
		
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrQualityAudit(final String id){
		getHibernateTemplate().delete(getPnrQualityAudit(id));
		
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrQualityAudits(final Integer curPage, final Integer pageSize,
			final String whereStr){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
//				String queryStr = "from PnrQualityAudit pnrQualityAudit";
//				if (whereStr != null && whereStr.length() > 0)
//					queryStr += whereStr;
//				String queryCountStr = "select count(*) " + queryStr;
//
//				int total = ((Integer) session.createQuery(queryCountStr)
//						.iterate().next()).intValue();
//				Query query = session.createQuery(queryStr);
				
				


				String queryStr = "select {pnrQualityAudit.*} from Pnr_Customer_node pnrQualityAudit,rps_local_area area ";

				String queryCountStr = "select count(*) as c  from Pnr_Customer_node pnrQualityAudit,rps_local_area area " ;
//				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
			        queryCountStr += whereStr;
				SQLQuery query = session.createSQLQuery(queryStr + " and pnrQualityAudit.region = area.areaid order by area.pccode");
				SQLQuery queryCount = session.createSQLQuery(queryCountStr+" and pnrQualityAudit.region = area.areaid order by area.pccode" );				
				query.addEntity("pnrQualityAudit",PnrQualityAudit.class);
				queryCount.addScalar("c", Hibernate.INTEGER);				
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				List resultCount = queryCount.list();
				HashMap map = new HashMap();
//				map.put("total", new Integer(total));
				map.put("total", StaticMethod.nullObject2int(resultCount.get(0),0));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);    	  	
		
	};
	/**
	 * 根据条件查询质量管理报告列表 
	 */
	public List getPnrQualityAudits(final String where){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrQualityAudit pnrQualityAudit ";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);	   		
	};
}
