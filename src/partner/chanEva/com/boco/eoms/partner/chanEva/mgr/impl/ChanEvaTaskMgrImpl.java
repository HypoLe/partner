package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.chanEva.dao.IChanEvaTaskDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaTask;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;

public class ChanEvaTaskMgrImpl implements IChanEvaTaskMgr {

	private IChanEvaTaskDao chanEvaTaskDao;

	public IChanEvaTaskDao getChanEvaTaskDao() {
		return chanEvaTaskDao;
	}

	public void setChanEvaTaskDao(IChanEvaTaskDao chanEvaTaskDao) {
		this.chanEvaTaskDao = chanEvaTaskDao;
	}

	public ChanEvaTask getTask(String id) {
		return chanEvaTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			ChanEvaTask task = (ChanEvaTask) it.next();
			chanEvaTaskDao.removeTask(task);
		}
	}

	public ChanEvaTask getTaskByTplAndOrg(String tplId, String orgId) {
		return chanEvaTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		ChanEvaTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return chanEvaTaskDao.listTaskOfOrg(orgId, activated);
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
		return chanEvaTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return chanEvaTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		IChanEvaTemplateMgr tplMgr = (IChanEvaTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IchanEvaTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			ChanEvaTask task = (ChanEvaTask) taskIt.next();
			ChanEvaTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(ChanEvaTask task) {
		chanEvaTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public ChanEvaTask getTaskById(String id){
		return chanEvaTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return chanEvaTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return chanEvaTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return chanEvaTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

}
