package com.boco.eoms.partner.assiEva.mgr;

import java.util.List;

import com.boco.eoms.partner.assiEva.model.AssiEvaTaskDetail;

/**
 * 
 * <p>
 * Title:考核任务明细业务方法类
 * </p>
 * <p>
 * Description:考核任务明细业务方法类
 * </p>
 * <p>
 * Date:2009-1-8 上午10:22:41
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IAssiEvaTaskDetailMgr {

	/**
	 * 保存考核任务明细
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(AssiEvaTaskDetail taskDetail);

	/**
	 * 查询某任务的详细信息的最大列表编号
	 * 
	 * @param taskId
	 *            任务Id
	 * @return
	 */
	public int getMaxListNoOfTask(String taskId);

	/**
	 * 根据列表编号查询某任务的详细信息列表
	 * 
	 * @param taskId
	 *            任务Id
	 * @param listNo
	 *            列表编号
	 * @return
	 */
	public List listDetailOfTaskByListNo(String taskId, String listNo);
	
	/**
	 * 根据主键ID查询某任务的详细信息列表
	 * 
	 * @param id
	 *            任务Id
	 * @return AssiEvaTaskDetail
	 */
	public AssiEvaTaskDetail getTaskDetailById(String id);
}
