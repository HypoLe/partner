package com.boco.eoms.partner.taskManager.service;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.taskManager.model.CarTask;

public interface ICarTaskService extends CommonGenericService<CarTask>  {
	/**
	 * 
	 *@Description:根据车牌号和申请单主键获取车辆的当前任务;
	 *@date May 15, 2013 5:39:15 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber:车牌号
	 *@param applyId:申请单主键
	 *@return
	 *@throws Exception
	 */
	public CarTask getCarCurrentTask(String carNumber,String applyId) throws Exception;
}
