package com.boco.eoms.task.dao;

import com.boco.eoms.task.model.TaskAccessories;

public interface ITaskAccessoriesDao {

	/**
	 * 根据主键删除附件
	 * 
	 * @param id
	 *            附件表主键
	 */
	public void delAccessories(String id);

	/**
	 * 根据主键查询附件
	 * 
	 * @param id
	 *            附件表主键
	 * @return 任务
	 */
	public TaskAccessories getAccessories(String id);

	/**
	 * 保存附件
	 * 
	 * @param accessories
	 *            附件
	 * @return 附件Id
	 */
	public String saveAccessories(TaskAccessories accessories);

}
