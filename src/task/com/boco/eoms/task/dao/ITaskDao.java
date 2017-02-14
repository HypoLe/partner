package com.boco.eoms.task.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.task.model.Task;

public interface ITaskDao {

	/**
	 * 根据主键删除任务
	 * 
	 * @param taskId
	 *            任务表主键
	 */
	public void delTask(String taskId);

	/**
	 * 根据主键查询任务
	 * 
	 * @param taskId
	 *            任务表主键
	 * @return 任务
	 */
	public Task getTask(String taskId);

	/**
	 * 根据任务的taskId查询该任务下的子任务
	 * 
	 * @param taskId
	 * @return
	 */
	public List getChildTask(String taskId);

	/**
	 * 根据条件分页查询task
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	public Map getTasks(Integer curPage, Integer pageSize, String whereStr);

	/**
	 * 保存任务
	 * 
	 * @param task
	 *            任务
	 */
	public void saveTask(Task task);

	/**
	 * 查询直属下级任务
	 * 
	 * @param taskId
	 *            父任务Id
	 */
	public List listNextLevelTask(String taskId);

	/**
	 * 列出某人起草的全部大任务（默认查询与本周相关的任务）
	 * 
	 * @param drafter
	 *            起草人
	 * @return 某人起草的大任务列表
	 */
	public List listTopTaskByDrafter(String drafter);

	/**
	 * 根据条件查询任务
	 * 
	 * @param whereStr
	 *            查询条件
	 * @return 符合treegrid展现的xml串
	 */
	public List searchTasks(String whereStr);

	/**
	 * 列出某人待处理的全部大任务，多个任务处于同一棵树则只找出最大节点
	 * 
	 * @param principal
	 *            任务负责人
	 * @return 某人起草的大任务列表
	 */
	public List listTopTaskByPrincipal(String principal);

	/**
	 * 根据负责人和时间判断任务冲突
	 * 
	 * @param principal
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List listTaskConflict(String principal, String startTime,
			String endTime, String taskId);
}
