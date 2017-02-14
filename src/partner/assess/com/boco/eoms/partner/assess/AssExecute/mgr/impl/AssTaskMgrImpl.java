package com.boco.eoms.partner.assess.AssExecute.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.assess.AssExecute.dao.IAssTaskDao;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.base.util.ApplicationContextHolder;
/**
 * <p>
 * Title:考核任务方法类
 * </p>
 * <p>
 * Description:考核任务方法类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */

public abstract class AssTaskMgrImpl implements IAssTaskMgr {

	private IAssTaskDao assTaskDao;

	public IAssTaskDao getAssTaskDao() {
		return assTaskDao;
	}

	public void setAssTaskDao(IAssTaskDao assTaskDao) {
		this.assTaskDao = assTaskDao;
	}

	public AssTask getTask(String id) {
		return assTaskDao.getTask(id);
	}

	public void removeTaskOfTemplate(String templateId) {
		List list = listTaskOfTpl(templateId);
		for (Iterator it = list.iterator(); it.hasNext();) {
			AssTask task = (AssTask) it.next();
			assTaskDao.removeTask(task);
		}
	}

	public AssTask getTaskByTplAndOrg(String tplId, String orgId) {
		return assTaskDao.getTaskByTplAndOrg(tplId, orgId);
	}

	public boolean isTaskExists(String tplId, String orgId) {
		boolean flag = false;
		AssTask task = getTaskByTplAndOrg(tplId, orgId);
		if (null != task.getId() && !"".equals(task.getId())) {
			flag = true;
		}
		return flag;
	}

	public List listTaskOfOrg(String orgId, String activated) {
		return assTaskDao.listTaskOfOrg(orgId, activated);
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
		return assTaskDao.listTaskOfOrg(orgId, activated, templateTypeId);
	}

	public List listTaskOfTpl(String tplId) {
		return assTaskDao.listTaskOfTpl(tplId);
	}

	public List listTemplateOfOrg(String orgId, String activated) {
		List tplList = new ArrayList();
		List taskList = listTaskOfOrg(orgId, activated);
		IAssTemplateMgr tplMgr = (IAssTemplateMgr) ApplicationContextHolder
				.getInstance().getBean("IassTemplateMgr");
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			AssTask task = (AssTask) taskIt.next();
			AssTemplate tpl = tplMgr.getTemplate(task.getTemplateId());
			if (null != tpl.getId() && !"".equals(tpl.getId())) {
				tplList.add(tpl);
			}
		}
		return tplList;
	}

	public void saveTask(AssTask task) {
		assTaskDao.saveTask(task);
	}
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public AssTask getTaskById(String id){
		return assTaskDao.getTaskById(id);
	}
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId){
		return assTaskDao.listTaskOfProvinceAdmin(activated, templateTypeId);
	}
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end){
		return assTaskDao.listTaskOfProvinceAdminInTime(activated, templateTypeId, start, end);
	}
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end){
		return assTaskDao.listTaskOfOrgInTime(orgId, activated, templateTypeId, start, end);
	}

}
