package com.boco.eoms.partner.assess.AssExecute.mgr;

import java.util.List;

import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
/**
 * <p>
 * Title:考核任务明细业务方法类
 * </p>
 * <p>
 * Description:考核任务明细业务方法类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssTaskDetailMgr {

	/**
	 * 保存考核任务明细
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(AssTaskDetail taskDetail);

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
	 * @return AssTaskDetail
	 */
	public AssTaskDetail getTaskDetailById(String id);
	
	/**
	 * 得到当前任务某节点所有叶子节点
	 * 
	 * @param taskId 
	 * 			任务Id
	 * @param parentNodeId
	 *            父结点nodeId
	 * @return String
	 */
	public String getLeafNodesOfChild(String taskId,String parentNodeId);
	
	/**
	 * 得到当前任务某节点所有叶子节点不包括全扣分部分
	 * 
	 * @param taskId 
	 * 			任务Id
	 * @param parentNodeId
	 *            父结点nodeId
	 * @return String
	 */
	public String getLeafNodesOfChildForDeduct(String taskId,String parentNodeId);
	
	/**
	 * 按条件得到当前任务
	 * 
	 * @param where 
	 * 			条件
	 * @return AssTaskDetail
	 */	
	public AssTaskDetail getTaskDetail(String where) ;	
}
