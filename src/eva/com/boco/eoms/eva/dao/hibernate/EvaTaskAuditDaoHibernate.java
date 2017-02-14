package com.boco.eoms.eva.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.eva.dao.IEvaTaskAuditDao;
import com.boco.eoms.eva.model.EvaTaskAudit;

public class EvaTaskAuditDaoHibernate extends BaseDaoHibernate implements
		IEvaTaskAuditDao {

	public List getEvaTaskAuditByTaskId(final String taskId,final String partner) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaTaskAudit where taskId = ? and partner = ? order by auditCreate desc ";
				Query query = session.createQuery(queryStr);
				query.setString(0, taskId);
				query.setString(1, partner);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	public List getEvaTaskAudit(String taskId, String auditTime,
			String auditCycle,String partner) {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select ta from EvaTaskAudit ta where 1=1 ");
		if(!(null == partner || "".equals(partner)))
			queryStr.append(" and ta.partner = '"+partner+"'");
		if(!(null == auditCycle || "".equals(auditCycle)))
			queryStr.append(" and ta.auditCycle = '"+auditCycle+"'");
		if(!(null == auditTime || "".equals(auditTime)))
			queryStr.append(" and ta.auditTime = '"+auditTime+"'");
		if(!(null == taskId || "".equals(taskId)))
			queryStr.append(" and ta.taskId = '"+taskId+"' ");
		List list = this.getHibernateTemplate().find(queryStr.toString());	
		return list;
	}
	public EvaTaskAudit getEvaTaskAudit(String id) {
		EvaTaskAudit taskAudit = (EvaTaskAudit) this.getHibernateTemplate().get(EvaTaskAudit.class, id);
		if(null == taskAudit){
			throw new ObjectRetrievalFailureException(EvaTaskAudit.class,id);
		}
		return taskAudit;
	}

	public Map getEvaTaskAuditByOrgType(final Integer curPage,final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from EvaTaskAudit";

				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;

				String queryCountStr = "select count(*) " + queryStr;
				// 按送审时间排序
				queryStr += " order by auditCreate desc";

				Integer total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next());
				Query query = session.createQuery(queryStr);
				query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
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

	public void saveEvaTaskAudit(EvaTaskAudit taskAudit) {
		if("".equals(taskAudit.getId()) || null == taskAudit.getId()){
			this.getHibernateTemplate().save(taskAudit);
		}else{
			this.getHibernateTemplate().saveOrUpdate(taskAudit);
		}
		
	}

	
}
