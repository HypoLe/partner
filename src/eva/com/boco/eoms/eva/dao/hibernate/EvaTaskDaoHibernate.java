package com.boco.eoms.eva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.eva.dao.IEvaTaskDao;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;

public class EvaTaskDaoHibernate extends BaseDaoHibernate implements
		IEvaTaskDao {

	public EvaTask getTask(String id) {
		EvaTask task = (EvaTask) getHibernateTemplate().load(EvaTask.class, id);
		if (null == task) {
			// throw new ObjectRetrievalFailureException(EvaTask.class, id);
			task = new EvaTask();
		}
		return task;
	}
	
	public EvaTask getTaskById(String id) {
		String hql = "from EvaTask task where task.id='"+ id +"'";
		hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ EvaConstants.TEMPLATE_ACTIVATED + "' or tpl.activated='"+EvaConstants.TEMPLATE_CLOSED+"')";
		List list = getHibernateTemplate().find(hql);
		EvaTask task = new EvaTask();
		if (list.iterator().hasNext()) {
			task = (EvaTask) list.iterator().next();
		}
		return task;
	}

	public void removeTask(EvaTask task) {
		getHibernateTemplate().delete(task);
	}

	public EvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		EvaTask task = new EvaTask();
		String sql = "from EvaTask task where task.templateId='" + tplId+"' ";
		if(!"".equals(orgId)){
			sql += " and task.orgId='" + orgId + "'";
		}
		List list = getHibernateTemplate().find(sql);
		if (list.iterator().hasNext()) {
			task = (EvaTask) list.iterator().next();
		}
		return task;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		String hql = "from EvaTask task where task.orgId='" + orgId + "'";
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ activated + "')";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end) {
		
		String hql = "from EvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			
			hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ activated + "' and startTime<='"+end+"'";

			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId = '"+templateTypeId+"'";
			}
			hql += ")";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	public List listTaskOfOrg(String orgId, String activated, String templateTypeId) {
				
		String hql = "from EvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ activated + "' and deleted = '0'";
			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId = '"+templateTypeId+"'";
			}
			hql += ")";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-5
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId) {
		
		String hql = "from EvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ activated + "' and deleted = '0'";
			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId = '"+templateTypeId+"'";
			}
			hql += ")";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end) {
		
		String hql = "from EvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
		
			hql += " and task.templateId in(select id from EvaTemplate tpl where tpl.activated='"
					+ activated + "' and startTime<='"+end+"'";
	
			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId = '"+templateTypeId+"'";
			}
			hql += ")";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}

	public List listTaskOfTpl(String tplId) {
		List list = getHibernateTemplate().find(
				"from EvaTask task where task.templateId='" + tplId + "'");
		return list;
	}

	public void saveTask(EvaTask task) {
		if (null == task.getId() || "".equals(task.getId())) {
			getHibernateTemplate().save(task);
		} else {
			getHibernateTemplate().saveOrUpdate(task);
		}
	}
}
