package com.boco.eoms.partner.record.dao.hibernate;

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
import com.boco.eoms.partner.record.dao.IPnrRecordDao;
import com.boco.eoms.partner.record.model.PnrRecord;

public class PnrRecordDaoHibernate extends BaseDaoHibernate  implements IPnrRecordDao {

	/**
	 * 返回质量管理报告列表 
	 */
	public List getPnrRecord(){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrRecord pnrRecord";
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
	public PnrRecord getPnrRecord(final String id){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrRecord pnrRecord where pnrRecord.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrRecord) result.iterator().next();
				} else {
					return new PnrRecord();
				}
			}
		};
		return (PnrRecord) getHibernateTemplate().execute(callback);    	
	};
	/**
	 * 保存质量管理报告 
	 */
	public void savePnrRecord(PnrRecord pnrRecord){
		if ((pnrRecord.getId() == null) || (pnrRecord.getId().equals("")))
			getHibernateTemplate().save(pnrRecord);
		else
			getHibernateTemplate().saveOrUpdate(pnrRecord);    	
		
	};
	/**
	 * 删除质量管理报告
	 */
	public void removePnrRecord(final String id){
		getHibernateTemplate().delete(getPnrRecord(id));
		
	};
	/**
	 * 分页查询质量管理报告列表 
	 */
	public Map getPnrRecords(final Integer curPage, final Integer pageSize,
			final String whereStr){

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
					String queryStr = "from PnrRecord pnrRecord";
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
	public List getPnrRecords(final String where){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrRecord pnrRecord ";
				if (where != null && where.length() > 0)
					queryStr += where;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);	   		
	};
}
