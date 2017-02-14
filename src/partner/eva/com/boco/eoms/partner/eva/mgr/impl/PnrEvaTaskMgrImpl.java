package com.boco.eoms.partner.eva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.eva.dao.IPnrEvaTaskDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;

public class PnrEvaTaskMgrImpl implements IPnrEvaTaskMgr {

	
	private IPnrEvaTaskDao pnrEvaTaskDao;

	public IPnrEvaTaskDao getPnrEvaTaskDao() {
		return pnrEvaTaskDao;
	}

	public void setPnrEvaTaskDao(IPnrEvaTaskDao pnrEvaTaskDao) {
		this.pnrEvaTaskDao = pnrEvaTaskDao;
	}

	public PnrEvaTask getTask(String id) {
		return pnrEvaTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			PnrEvaTask task = (PnrEvaTask) it.next();
			pnrEvaTaskDao.removeTask(task);
		}
	}

	public PnrEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		return pnrEvaTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		PnrEvaTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return pnrEvaTaskDao.listTaskOfOrg(orgId, activated);
	}
	
	public List listTaskOfArea(String areaId, String activated, String templateTypeId, String executeOrg) {
		return pnrEvaTaskDao.listTaskOfArea(areaId, activated,templateTypeId, executeOrg);
	}
	public List listTaskOfAreaOnTemplateLeaf(String areaId, String activated,
			String templateTypeId) {
		return pnrEvaTaskDao.listTaskOfAreaOnTemplateLeaf(areaId, activated, templateTypeId);
	}
	public List listTaskOfAreaAndTime(String areaId, String activated, String templateTypeId,String start,String end) {
		return pnrEvaTaskDao.listTaskOfAreaAndTime(areaId, activated, templateTypeId,start,end);
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
		return pnrEvaTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return pnrEvaTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("iPnrEvaTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			PnrEvaTask task = (PnrEvaTask) taskIt.next();
			PnrEvaTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(PnrEvaTask task) {
		pnrEvaTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public PnrEvaTask getTaskById(String id){
		return pnrEvaTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return pnrEvaTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return pnrEvaTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return pnrEvaTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

	public PnrEvaTask getPnrEvaTaskByTemplateIdAndOrgId(String templateId,
			String orgId) {
		return pnrEvaTaskDao.getPnrEvaTaskByTemplateIdAndOrgId(templateId, orgId);
	}

}
