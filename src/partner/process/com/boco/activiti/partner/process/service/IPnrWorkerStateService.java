package com.boco.activiti.partner.process.service;

import java.util.List;

import com.boco.activiti.partner.process.model.AccreditOrder;
import com.boco.activiti.partner.process.model.WorkerState;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 * 个人状态service
 * @author wangyue
 *
 */
public interface IPnrWorkerStateService extends CommonGenericService<WorkerState>{
	
	/**
	 * 根据登录人的id和个人状态查出授权记录的
	 * @param workerId
	 * @param state
	 * @return
	 */
	public WorkerState getWorkerId(String workerId,String state);
	/***
	 * 根据授权记录id查询所有授权的工单
	 * @param workerStateId
	 * @return
	 */
	public List<AccreditOrder> getAccreditOrder(String workerStateId);
}
