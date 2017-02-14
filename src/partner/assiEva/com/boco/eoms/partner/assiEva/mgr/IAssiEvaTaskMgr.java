package com.boco.eoms.partner.assiEva.mgr;

import java.util.List;

import com.boco.eoms.partner.assiEva.model.AssiEvaTask;

/**
 * 
 * <p>
 * Title:考核任务业务方法类
 * </p>
 * <p>
 * Description:考核任务业务方法类
 * </p>
 * <p>
 * Date:2009-1-4 下午05:14:58
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IAssiEvaTaskMgr {

	/**
	 * 保存考核任务
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(AssiEvaTask task);

	/**
	 * 根据主键获得考核任务
	 * 
	 * @param id
	 *            考核任务主键
	 */
	public AssiEvaTask getTask(String id);

	/**
	 * 删除模板对应的所有任务
	 * 
	 * @param templateId
	 *            模板Id
	 */
	public void removeTaskOfTemplate(String templateId);

	/**
	 * 根据模板Id和组织Id获得考核任务
	 * 
	 * @param tplId
	 *            模板Id
	 * @param orgId
	 *            任务Id
	 * @return
	 */
	public AssiEvaTask getTaskByTplAndOrg(String tplId, String orgId);

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
	 * 查询某组织的全部模板（按生成时间倒序排）
	 * 
	 * @param orgId
	 *            组织Id
	 * @param activated
	 *            模板激活状态（不区分状态请传入空字符串）
	 * @return
	 */
	public List listTemplateOfOrg(String orgId, String activated);

	/**
	 * 查询某模板的所有任务
	 * 
	 * @param tplId
	 *            模板Id
	 * @return
	 */
	public List listTaskOfTpl(String tplId);

	/**
	 * 判断某模板分配给某组织的任务是否存在
	 * 
	 * @param tplId
	 *            模板Id
	 * @param orgId
	 *            组织Id
	 * @return
	 */
	public boolean isTaskExists(String tplId, String orgId);
	/**
	 * 根据主键获得考核任务
	 * 王思轩 09-1-22
	 * @param id
	 *            考核任务主键
	 */
	public AssiEvaTask getTaskById(String id);
	
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。2009-8-4
	public List listTaskOfProvinceAdmin(String activated, String templateTypeId);
	//考核模板区负责人（省负责人），能看到所有模版，而不需要有所属公司的限制。加上时间段限制条件 2009-8-7
	public List listTaskOfProvinceAdminInTime(String activated, String templateTypeId,String start,String end);
	//根据模版激活时间显示任务 2009-8-7
	public List listTaskOfOrgInTime(String orgId, String activated, String templateTypeId,String start,String end);
}
