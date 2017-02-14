package com.boco.eoms.task.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.task.model.Task;

/**
 * <p>
 * Title:任务管理业务接口
 * </p>
 * <p>
 * Description:任务管理业务接口
 * </p>
 * <p>
 * Sun Jul 05 17:19:32 CST 2009
 * </p>
 * 
 * @author qiuzi
 * @version 3.5
 * 
 */
public interface ITaskMgr {

	/**
	 * 保存treegrid页面任务改动
	 * 
	 * @param xml
	 *            treegrid页面提交的任务改动信息
	 * @param userId
	 *            当前系统用户
	 */
	public void saveChangedTasks(String xml, String userId) throws Exception;

	/**
	 * 查询某人起草的全部任务（默认查询与本周相关的任务）
	 * 
	 * @param drafter
	 *            任务起草人
	 * @return 符合treegrid展现的xml串
	 */
	public String listTasksByDrafter(String drafter);

	/**
	 * 根据条件查询某人起草的全部任务
	 * 
	 * @param whereStr
	 *            查询条件
	 * @return 符合treegrid展现的xml串
	 */
	public String searchTasksByDrafter(String whereStr);

	/**
	 * 查询分配给某人的全部任务
	 * 
	 * @param principal
	 *            任务负责人
	 * @return 符合treegrid展现的xml串
	 */
	public String listTasksByPrincipal(String principal);

	/**
	 * 根据条件查询分配给某人的全部任务
	 * 
	 * @param whereStr
	 *            查询条件
	 * @return 符合treegrid展现的xml串
	 */
	public String searchTasksByPrincipal(String principal, String whereStr);

	/**
	 * 根据主键id查询task
	 * 
	 * @param id
	 * @return
	 */
	public Task getTask(String id);

	/**
	 * 根据任务id查询该任务下的子任务
	 * 
	 * @param id
	 * @return
	 */
	public List getChildTask(String id);

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
	 * 根据时间和负责人判断任务冲突
	 * 
	 * @param principal
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String listTaskConflict(String principal, String startTime,
			String endTime, String taskId);

	/**
	 * 保存任务
	 * 
	 * @param task
	 *            任务实例
	 */
	public void saveTask(Task task);
}