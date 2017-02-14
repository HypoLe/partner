package com.boco.eoms.partner.tempTask.dao.hibernate;

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
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskExeDao;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;

/**
 * <p>
 * Title:合作伙伴临时任务管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskExeDaoHibernate extends BaseDaoHibernate implements IPnrTempTaskExeDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskExeDao#getPnrTempTaskExes()
	 *      
	 */
	public List getPnrTempTaskExes() {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskExe pnrTempTaskExe";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public List getPnrTempTaskExes(final String tempTaskId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskExe pnrTempTaskExe where pnrTempTaskExe.tempTaskId=:tempTaskId ";
				Query query = session.createQuery(queryStr);
				query.setString("tempTaskId", tempTaskId);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	/**
	 *
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskExeDao#getPnrTempTaskExe(java.lang.String)
	 *
	 */
	public PnrTempTaskExe getPnrTempTaskExe(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskExe pnrTempTaskExe where pnrTempTaskExe.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrTempTaskExe) result.iterator().next();
				} else {
					return new PnrTempTaskExe();
				}
			}
		};
		return (PnrTempTaskExe) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskExeDao#savePnrTempTaskExes(com.boco.eoms.partner.tempTask.PnrTempTaskExe)
	 *      
	 */
	public void savePnrTempTaskExe(final PnrTempTaskExe pnrTempTaskExe) {
		if ((pnrTempTaskExe.getId() == null) || (pnrTempTaskExe.getId().equals("")))
			getHibernateTemplate().save(pnrTempTaskExe);
		else
			getHibernateTemplate().saveOrUpdate(pnrTempTaskExe);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskExeDao#removePnrTempTaskExes(java.lang.String)
	 *      
	 */
    public void removePnrTempTaskExe(final String id) {
		getHibernateTemplate().delete(getPnrTempTaskExe(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		//TODO 请修改代码
		return null;
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskExeDao#getPnrTempTaskExes(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPnrTempTaskExes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskExe pnrTempTaskExe";
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
	}
	public Map getPnrTempTaskUndo(final Integer curPage,final Integer pageSize,final String userId,final String deptId,final String date) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskMain main,PnrTempTaskWork work where work.tempTaskId=main.id ";
				queryStr +=" and (work.startTime  < '"+date+"' ) and ((main.toOrgId = '"+userId+"' and main.toOrgType = 'user') or (main.toOrgId = '"+deptId+"' and main.toOrgType = 'dept')) ";
				queryStr +=" and work.workFlag ='0' and main.state = '2'";
				String queryCountStr = "select count(*) " + queryStr;
				String queryOrder = " order by main.createTime";
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery("select work "+queryStr+queryOrder);
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
	public Map getPnrTempTaskForExecute(final Integer curPage,final Integer pageSize,final String userId,final String deptId,final String date) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskMain main,PnrTempTaskWork work where work.tempTaskId=main.id ";
				queryStr +=" and (work.warnTime  < '"+date+"' or work.warnTime is null) and ((main.toOrgId = '"+userId+"' and main.toOrgType = 'user') or (main.toOrgId = '"+deptId+"' and main.toOrgType = 'dept')) ";
				queryStr +=" and work.workFlag ='0' and main.state = '2'";
				queryStr +=" and work.workType <> 'subjective'";
				String queryCountStr = "select count(*) " + queryStr;
				String queryOrder = " order by main.createTime";
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery("select work "+queryStr+queryOrder);
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