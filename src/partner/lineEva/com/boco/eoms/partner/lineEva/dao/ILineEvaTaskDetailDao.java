package com.boco.eoms.partner.lineEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.lineEva.model.LineEvaTaskDetail;

public interface ILineEvaTaskDetailDao extends Dao {

	/**
	 * 保存考核任务明细
	 * 
	 * @param task
	 *            考核任务
	 */
	public void saveTask(LineEvaTaskDetail taskDetail);

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
	 * @return LineEvaTaskDetail
	 */
	public LineEvaTaskDetail getTaskDetailById(String id);
}
