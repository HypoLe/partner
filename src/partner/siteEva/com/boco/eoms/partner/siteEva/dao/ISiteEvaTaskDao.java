package com.boco.eoms.partner.siteEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;

public interface ISiteEvaTaskDao extends Dao{

	/**
	 * 保存考核任务
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(SiteEvaTask task);
	
	/**
	 * 删除考核任务 
	 * 
	 * @param task
	 *            考核任务
	 */
	public void removeTask(SiteEvaTask task);
	
	/**
	 * 根据主键获得考核任务
	 * 
	 * @param id
	 *            考核任务主键
	 */
	public SiteEvaTask getTask(String id);
	
	/**
	 * 根据模板Id和组织Id获得考核任务
	 * 
	 * @param tplId
	 *            模板Id
	 * @param orgId
	 *            任务Id
	 * @return
	 */
	public SiteEvaTask getTaskByTplAndOrg(String tplId, String orgId);
	
	/**
	 * 查询某组织的全部任务（按生成时间倒序排）
	 * 
	 * @param orgId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @return
	 */
	public List listTaskOfOrg(String orgId, String activated);
	
	/**
	 * 查询某模板分类、某组织的全部任务（按生成时间倒序排）
	 * 
	 * @param orgId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @return
	 */
	public List listTaskOfOrg(String orgId, String activated, String templateTypeId);
	
	/**
	 * 查询某模板的所有任务
	 * 
	 * @param tplId
	 *            模板Id
	 * @return
	 */
	public List listTaskOfTpl(String tplId);
	
	
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public SiteEvaTask getTaskById(String id);
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId);
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end);
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end);
}
