package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.tranEva.dao.ITranEvaTaskDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTaskMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTemplateMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaTask;
import com.boco.eoms.partner.tranEva.model.TranEvaTemplate;

public class TranEvaTaskMgrImpl implements ITranEvaTaskMgr {

	private ITranEvaTaskDao tranEvaTaskDao;

	public ITranEvaTaskDao getTranEvaTaskDao() {
		return tranEvaTaskDao;
	}

	public void setTranEvaTaskDao(ITranEvaTaskDao tranEvaTaskDao) {
		this.tranEvaTaskDao = tranEvaTaskDao;
	}

	public TranEvaTask getTask(String id) {
		return tranEvaTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			TranEvaTask task = (TranEvaTask) it.next();
			tranEvaTaskDao.removeTask(task);
		}
	}

	public TranEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		return tranEvaTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		TranEvaTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return tranEvaTaskDao.listTaskOfOrg(orgId, activated);
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
		return tranEvaTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return tranEvaTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		ITranEvaTemplateMgr tplMgr = (ITranEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("ItranEvaTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			TranEvaTask task = (TranEvaTask) taskIt.next();
			TranEvaTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(TranEvaTask task) {
		tranEvaTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public TranEvaTask getTaskById(String id){
		return tranEvaTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return tranEvaTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return tranEvaTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return tranEvaTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

}
