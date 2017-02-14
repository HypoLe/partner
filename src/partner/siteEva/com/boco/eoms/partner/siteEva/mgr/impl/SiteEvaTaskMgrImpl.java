package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaTaskDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;

public class SiteEvaTaskMgrImpl implements ISiteEvaTaskMgr {

	private ISiteEvaTaskDao siteEvaTaskDao;

	public ISiteEvaTaskDao getSiteEvaTaskDao() {
		return siteEvaTaskDao;
	}

	public void setSiteEvaTaskDao(ISiteEvaTaskDao siteEvaTaskDao) {
		this.siteEvaTaskDao = siteEvaTaskDao;
	}
 
	public SiteEvaTask getTask(String id) {
		return siteEvaTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			SiteEvaTask task = (SiteEvaTask) it.next();
			siteEvaTaskDao.removeTask(task);
		}
	}

	public SiteEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		return siteEvaTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		SiteEvaTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return siteEvaTaskDao.listTaskOfOrg(orgId, activated);
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
		return siteEvaTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return siteEvaTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		ISiteEvaTemplateMgr tplMgr = (ISiteEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IsiteEvaTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			SiteEvaTask task = (SiteEvaTask) taskIt.next();
			SiteEvaTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(SiteEvaTask task) {
		siteEvaTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public SiteEvaTask getTaskById(String id){
		return siteEvaTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return siteEvaTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return siteEvaTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return siteEvaTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

}
