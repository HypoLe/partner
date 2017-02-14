package com.boco.eoms.partner.assess.AssFlow.dao.hibernate;

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
import com.boco.eoms.partner.assess.AssFlow.dao.IAssOperateStepDao;
import com.boco.eoms.partner.assess.AssFlow.model.AssOperateStep;

/**
 * <p>
 * Title:后评估主表信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:后评估主表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssOperateStepDaoHibernate extends BaseDaoHibernate implements IAssOperateStepDao,
		ID2NameDAO {
	

	public List getAssOperateSteps() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssOperateStep assOperateStep order by operateTime desc";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
 
	public List getAssOperateSteps(final String reportId) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssOperateStep assOperateStep where assOperateStep.reportId='"+reportId+"' order by assOperateStep.createTime desc";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	

	public AssOperateStep getAssOperateStep(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssOperateStep assOperateStep where assOperateStep.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (AssOperateStep) result.iterator().next();
				} else {
					return new AssOperateStep();
				}
			}
		};
		return (AssOperateStep) getHibernateTemplate().execute(callback);
	}
	

	public void saveAssOperateStep(final AssOperateStep assOperateStep) {
		if ((assOperateStep.getId() == null) || (assOperateStep.getId().equals("")))
			getHibernateTemplate().save(assOperateStep);
		else
			getHibernateTemplate().saveOrUpdate(assOperateStep);
	}


    public void removeAssOperateStep(final String id) {
		getHibernateTemplate().delete(getAssOperateStep(id));
	}


	public String id2Name(String id) throws DictDAOException {
		AssOperateStep assOperateStep = this.getAssOperateStep(id);
		if(assOperateStep==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 
	public Map getAssOperateSteps(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssReportInfo report, AssOperateStep step where  report.id=step.reportId ";
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