package com.boco.eoms.partner.eva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaTask;

public interface IPnrEvaTaskDao extends Dao{

	/**
	 * 保存考核任务
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(PnrEvaTask task);
	
	/**
	 * 删除考核任务
	 * 
	 * @param task
	 *            考核任务
	 */
	public void removeTask(PnrEvaTask task);
	
	/**
	 * 根据主键获得考核任务
	 * 
	 * @param id
	 *            考核任务主键
	 */
	public PnrEvaTask getTask(String id);
	
	/**
	 * 根据模板Id和组织Id获得考核任务
	 * 
	 * @param tplId
	 *            模板Id
	 * @param orgId
	 *            任务Id
	 * @return
	 */
	public PnrEvaTask getTaskByTplAndOrg(String tplId, String orgId);
	
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
	 * 查询某地域的全部任务（按生成时间倒序排）
	 * 
	 * @param areaId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @param templateTypeId
	 *            模板类型id
	 * @param executeOrg
	 *            评分执行者 0-各地域；1-网络中心；2-网络部
	 * @return
	 */
	public List listTaskOfArea(String areaId, String activated,String templateTypeId, String executeOrg);
	
	/**
	 * 查询某地域的对应templateId标记为叶子节点的全部任务（按生成时间倒序排序）
	 * 贾智会 2009-11-16
	 * @param areaId
	 * 				地域Id
	 * @param activeted
	 * 
	 * 				模板激活状态
	 * @return
	 */
	public List listTaskOfAreaOnTemplateLeaf(String areaId,String activated,String templateTypeId);
	
	
	/**
	 *根据模板Id和orgId查询出PnrEvaTask
	 *贾智会 2009-11-17
	 *@param templateId
	 *				模板Id
	 *@param orgId
	 *				组织Id（地域Id）
	 *return 
	 */
	public PnrEvaTask getPnrEvaTaskByTemplateIdAndOrgId(String templateId,String orgId);
	
	/**
	 * 根据模版激活时间显示任务
	 * 
	 * @param areaId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @param templateTypeId
	 *            模板分类id
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public List listTaskOfAreaAndTime(String areaId, String activated, String templateTypeId,String start,String end);
	
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
	public PnrEvaTask getTaskById(String id);
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId);
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end);
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end);
}
