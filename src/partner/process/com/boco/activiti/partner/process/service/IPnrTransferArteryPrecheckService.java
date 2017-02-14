package com.boco.activiti.partner.process.service;

import java.util.List;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
/**
 * 
   * @author wangyue
   * @ClassName: IPnrTransferArteryPrecheckService
   * @Version 版本 
   * @Copyright boco
   * @date Mar 6, 2015 9:02:17 AM
   * @description 干线预检预修工单service接口
 */
public interface IPnrTransferArteryPrecheckService extends
		CommonGenericService<PnrTransferOffice> {
	
	/**
	 * 工单抓回 条数
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel 查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 工单抓回 明细
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel	查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);

}
