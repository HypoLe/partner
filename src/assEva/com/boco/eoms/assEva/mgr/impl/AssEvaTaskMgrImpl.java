package com.boco.eoms.assEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.assEva.dao.IAssEvaTaskDao;
import com.boco.eoms.assEva.mgr.IAssEvaTaskMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.model.AssEvaTask;
import com.boco.eoms.assEva.model.AssEvaTemplate;

public class AssEvaTaskMgrImpl implements IAssEvaTaskMgr {

	private IAssEvaTaskDao assEvaTaskDao;

	public IAssEvaTaskDao getAssEvaTaskDao() {
		return assEvaTaskDao;
	}

	public void setAssEvaTaskDao(IAssEvaTaskDao assEvaTaskDao) {
		this.assEvaTaskDao = assEvaTaskDao;
	}

	public AssEvaTask getTask(String id) {
		return assEvaTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssEvaTask task = (AssEvaTask) it.next();
			assEvaTaskDao.removeTask(task);
		}
	}

	public AssEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		return assEvaTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		AssEvaTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return assEvaTaskDao.listTaskOfOrg(orgId, activated);
	}
	/**
	 * 查询某模板分类、某组织的全部任务（按生成时间倒序排）
	 * 
	 * @param orgId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @return
	 */
	public List listTaskOfOrg(String orgId, String activated, String templateTypeId){
		return assEvaTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return assEvaTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		IAssEvaTemplateMgr tplMgr = (IAssEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassEvaTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			AssEvaTask task = (AssEvaTask) taskIt.next();
			AssEvaTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(AssEvaTask task) {
		assEvaTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public AssEvaTask getTaskById(String id){
		return assEvaTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return assEvaTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return assEvaTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return assEvaTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

}
