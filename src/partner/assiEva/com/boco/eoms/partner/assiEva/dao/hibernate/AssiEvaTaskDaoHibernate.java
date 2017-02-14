package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaTaskDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaTask;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;

public class AssiEvaTaskDaoHibernate extends BaseDaoHibernate implements
		IAssiEvaTaskDao {

	public AssiEvaTask getTask(String id) {
		AssiEvaTask task = (AssiEvaTask) getHibernateTemplate().load(AssiEvaTask.class, id);
		if (null == task) {
			// throw new ObjectRetriassiEvalFailureException(AssiEvaTask.class, id);
			task = new AssiEvaTask();
		}
		return task;
	}
	
	public AssiEvaTask getTaskById(String id) {
		String hql = "from AssiEvaTask task where task.id='"+ id +"'";
		hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
					+ AssiEvaConstants.TEMPLATE_ACTIVATED + "')";
		List list = getHibernateTemplate().find(hql);
		AssiEvaTask task = new AssiEvaTask();
		if (list.iterator().hasNext()) {
			task = (AssiEvaTask) list.iterator().next();
		}
		return task;
	}

	public void removeTask(AssiEvaTask task) {
		getHibernateTemplate().delete(task);
	}

	public AssiEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		AssiEvaTask task = new AssiEvaTask();
		List list = getHibernateTemplate().find(
				"from AssiEvaTask task where task.templateId='" + tplId
						+ "' and task.orgId='" + orgId + "'");
		if (list.iterator().hasNext()) {
			task = (AssiEvaTask) list.iterator().next();
		}
		return task;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		String hql = "from AssiEvaTask task where task.orgId='" + orgId + "'";
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
					+ activated + "')";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end) {
		
		String hql = "from AssiEvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			
			hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
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
				
		String hql = "from AssiEvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
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
		
		String hql = "from AssiEvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
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
		
		String hql = "from AssiEvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
		
			hql += " and task.templateId in(select id from AssiEvaTemplate tpl where tpl.activated='"
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
				"from AssiEvaTask task where task.templateId='" + tplId + "'");
		return list;
	}

	public void saveTask(AssiEvaTask task) {
		if (null == task.getId() || "".equals(task.getId())) {
			getHibernateTemplate().save(task);
		} else {
			getHibernateTemplate().saveOrUpdate(task);
		}
	}
}
