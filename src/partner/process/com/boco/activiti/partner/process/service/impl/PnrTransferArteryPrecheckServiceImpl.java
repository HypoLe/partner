package com.boco.activiti.partner.process.service.impl;

import java.util.List;

import org.activiti.engine.HistoryService;

import com.boco.activiti.partner.process.dao.IPnrTransferArteryPrecheckJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeDao;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrTransferArteryPrecheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 * 
 * @author wangyue
 * @ClassName: PnrTransferArteryPrecheckServiceImpl
 * @Version 版本
 * @Copyright boco
 * @date Mar 6, 2015 9:13:26 AM
 * @description 干线预检预修工单--service实现类
 */
public class PnrTransferArteryPrecheckServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferArteryPrecheckService {
	
	private IPnrTransferArteryPrecheckJDBCDao pnrTransferArteryPrecheckDao;
	private IPnrTransferOfficeDao pnrTransferOfficeDao;
	private HistoryService historyService;
	
	
	
	
	
	/********************************************get 和 set 方法 ***************************************************/
	public IPnrTransferArteryPrecheckJDBCDao getPnrTransferArteryPrecheckDao() {
		return pnrTransferArteryPrecheckDao;
	}
	public void setPnrTransferArteryPrecheckDao(
			IPnrTransferArteryPrecheckJDBCDao pnrTransferArteryPrecheckDao) {
		this.pnrTransferArteryPrecheckDao = pnrTransferArteryPrecheckDao;
	}
	public IPnrTransferOfficeDao getPnrTransferOfficeDao() {
		return pnrTransferOfficeDao;
	}
	public void setPnrTransferOfficeDao(IPnrTransferOfficeDao pnrTransferOfficeDao) {
		this.pnrTransferOfficeDao = pnrTransferOfficeDao;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	/**
	 * 工单抓回 条数
	 * 
	 * @param processDefinitionKey
	 *            流程ID
	 * @param userTaskFlag
	 *            能抓回的环节ID集合
	 * @param userId
	 *            登录用户ID
	 * @param conditionQueryModel
	 *            查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		return pnrTransferArteryPrecheckDao.getBackSheetCount(
				processDefinitionKey, userId, conditionQueryModel);
	}
	
	/**
	 * 工单抓回 明细
	 * 
	 * @param processDefinitionKey
	 *            流程ID
	 * @param userTaskFlag
	 *            能抓回的环节ID集合
	 * @param userId
	 *            登录用户ID
	 * @param conditionQueryModel
	 *            查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		return pnrTransferArteryPrecheckDao.getBackSheetList(processDefinitionKey,
				userId, conditionQueryModel, firstResult, endResult, pageSize);
	}
	

}
