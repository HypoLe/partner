package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.eva.dao.IPnrEvaTaskDao;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;

public class PnrEvaTaskDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaTaskDao {

	public PnrEvaTask getPnrEvaTaskByTemplateIdAndOrgId(final String templateId,
			final String orgId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrEvaTask where templateId = ? and orgId = ?";
				Query query = session.createQuery(queryStr);
				query.setString(0, templateId);
				query.setString(1, orgId);
				List list = query.list();
				if (list.iterator().hasNext()) {
					return (PnrEvaTask) list.iterator().next();
				}
				return new PnrEvaTask();
			}
		};
		return (PnrEvaTask) getHibernateTemplate().execute(callback);
	}


	public PnrEvaTask getTask(String id) {
		PnrEvaTask task = (PnrEvaTask) getHibernateTemplate().get(PnrEvaTask.class, id);
		if (null == task) {
			// throw new ObjectRetrievalFailureException(EvaTask.class, id);
			task = new PnrEvaTask();
		}
		return task;
	}
	
	public PnrEvaTask getTaskById(String id) {
		String hql = "from PnrEvaTask task where task.id='"+ id +"'";
		hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
					+ PnrEvaConstants.TEMPLATE_ACTIVATED + "')";
		List list = getHibernateTemplate().find(hql);
		PnrEvaTask task = new PnrEvaTask();
		if (list.iterator().hasNext()) {
			task = (PnrEvaTask) list.iterator().next();
		}
		return task;
	}

	public void removeTask(PnrEvaTask task) {
		getHibernateTemplate().delete(task);
	}

	public PnrEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		PnrEvaTask task = new PnrEvaTask();
		List list = getHibernateTemplate().find(
				"from PnrEvaTask task where task.templateId='" + tplId
						+ "' and task.orgId='" + orgId + "'");
		if (list.iterator().hasNext()) {
			task = (PnrEvaTask) list.iterator().next();
		}
		return task;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		String hql = "from PnrEvaTask task where task.orgId='" + orgId + "'";
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
					+ activated + "')";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}

	public List listTaskOfArea(String areaId, String activated) {
		String hql = "from PnrEvaTask task where task.orgId='" + areaId + "'";
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
					+ activated + "')";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	public List listTaskOfArea(String areaId, String activated,String templateTypeId, String executeOrg) {
		String hql = "from PnrEvaTask task where 1=1 ";
		if(executeOrg !=null && !"".equals(executeOrg)){
			hql += " and task.executeOrg = '"+executeOrg+"' ";
		}
		if(!"".equals(areaId)){
			hql += " and task.orgId='" + areaId + "' ";
		}
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
					+ activated + "' and deleted = '0' ";
			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId like '"+templateTypeId+"%'";
			}
			hql += ") ";
		}
		hql += " order by task.templateId";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	public List listTaskOfAreaAndTime(String areaId, String activated, String templateTypeId,String start,String end) {
		String hql = "from PnrEvaTask task where task.orgId='" + areaId + "'";
		
		if (null != activated && !"".equals(activated)) {
			
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
					+ activated + "' and startTime<='"+end+"'";

			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql = hql + " and tpl.templateTypeId like '"+templateTypeId+"%'";
			}
			hql += ")";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end) {
		
		String hql = "from PnrEvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
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
				
		String hql = "from PnrEvaTask task where task.orgId='" + orgId + "'";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
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
		
		String hql = "from PnrEvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
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
		
		String hql = "from PnrEvaTask task where 1=1 ";
							
		if (null != activated && !"".equals(activated)) {
		
			hql += " and task.templateId in(select id from PnrEvaTemplate tpl where tpl.activated='"
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
				"from PnrEvaTask task where task.templateId='" + tplId + "'");
		return list;
	}

	public void saveTask(PnrEvaTask task) {
		if (null == task.getId() || "".equals(task.getId())) {
			getHibernateTemplate().save(task);
		} else {
			getHibernateTemplate().saveOrUpdate(task);
		}
	}

	public List listTaskOfAreaOnTemplateLeaf(String areaId, String activated,
			String templateTypeId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select tpl from PnrEvaTree tree ,PnrEvaTemplate tpl");
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();			
		if(null != activated && !"".equals(activated)){
				hql.append(" where tree.objectId=tpl.id");
			//省用户
			if((areaId.length()-rootAreaId.length())/2 == 0){
				hql.append(" and tpl.executeType in ('0','1','2')");
			}
			//地市用户
			if((areaId.length()-rootAreaId.length())/2 == 1){
				hql.append(" and tpl.executeType in ('1','2')");
			}
			//县用户
			if((areaId.length()-rootAreaId.length())/2 == 2){
				hql.append(" and tpl.executeType in ('2')");
			}		
			hql.append(" and tpl.activated='" + activated + "' and tpl.deleted = '0' and tpl.leaf = '1'");
			if(templateTypeId != null && !"".equals(templateTypeId)){
				hql.append(" and tpl.templateTypeId like '"+templateTypeId+"%'");
			}
		}
		List list = getHibernateTemplate().find(hql.toString());
		return list;
	}
}
