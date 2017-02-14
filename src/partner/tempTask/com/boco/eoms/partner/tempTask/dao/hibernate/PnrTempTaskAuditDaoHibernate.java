package com.boco.eoms.partner.tempTask.dao.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskAuditDao;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
/**
 * <p>
 * Title:服务临时任务审核
 * </p>
 * <p>
 * Description:服务临时任务审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskAuditDaoHibernate extends BaseDaoHibernate implements IPnrTempTaskAuditDao,
		ID2NameDAO {
	

	public List getPnrTempTaskAudits(final String tempTaskId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskAudit pnrTempTaskAudit where pnrTempTaskAudit.tempTaskId=:tempTaskId  order by pnrTempTaskAudit.createTime";
				Query query = session.createQuery(queryStr);
				query.setString("tempTaskId", tempTaskId);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public Map getPnrTempTaskUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId) {
		
	
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskAudit pnrTempTaskAudit where pnrTempTaskAudit.state ='0' and ((pnrTempTaskAudit.toOrgId = '"+userId+"' and pnrTempTaskAudit.toOrgType = 'user') or (pnrTempTaskAudit.toOrgId = '"+deptId+"' and pnrTempTaskAudit.toOrgType = 'dept')) ";
				String queryCountStr = "select count(*) " + queryStr;
				String queryOrder = " order by pnrTempTaskAudit.createTime";
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr+queryOrder);
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
	
	
	
	public PnrTempTaskAudit getPnrTempTaskAuditByState(final String tempTaskId,final String state) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskAudit pnrTempTaskAudit where pnrTempTaskAudit.tempTaskId=:tempTaskId and pnrTempTaskAudit.state=:state order by pnrTempTaskAudit.createTime desc";
				Query query = session.createQuery(queryStr);
				query.setString("tempTaskId", tempTaskId);
				query.setString("state", state);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrTempTaskAudit) result.iterator().next();
				} else {
					return new PnrTempTaskAudit();
				}
			}
		};
		return (PnrTempTaskAudit) getHibernateTemplate().execute(callback);
	}
	
	
	

	public PnrTempTaskAudit getPnrTempTaskAudit(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskAudit pnrTempTaskAudit where pnrTempTaskAudit.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrTempTaskAudit) result.iterator().next();
				} else {
					return new PnrTempTaskAudit();
				}
			}
		};
		return (PnrTempTaskAudit) getHibernateTemplate().execute(callback);
	}
	

	public void savePnrTempTaskAudit(final PnrTempTaskAudit pnrTempTaskAudit) {
		if ((pnrTempTaskAudit.getId() == null) || (pnrTempTaskAudit.getId().equals("")))
			getHibernateTemplate().save(pnrTempTaskAudit);
		else
			getHibernateTemplate().saveOrUpdate(pnrTempTaskAudit);
	}
	
	public String id2Name(String id) throws DictDAOException {
		//TODO 请修改代码
		return null;
	}
}