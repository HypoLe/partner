package com.boco.activiti.partner.process.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;

import com.boco.activiti.partner.process.dao.IPnrTransferOfficeDao;
import com.boco.activiti.partner.process.dao.IPnrTransferPrecheckJDBCDao;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class PnrTransferPrecheckServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferPrecheckService {

	private IPnrTransferPrecheckJDBCDao pnrTransferPrecheckDao;

	private IPnrTransferOfficeDao pnrTransferOfficeDao;

	private HistoryService historyService;

	public IPnrTransferPrecheckJDBCDao getPnrTransferPrecheckDao() {
		return pnrTransferPrecheckDao;
	}

	public void setPnrTransferPrecheckDao(
			IPnrTransferPrecheckJDBCDao pnrTransferPrecheckDao) {
		this.pnrTransferPrecheckDao = pnrTransferPrecheckDao;
	}

	public IPnrTransferOfficeDao getPnrTransferOfficeDao() {
		return pnrTransferOfficeDao;
	}

	public void setPnrTransferOfficeDao(
			IPnrTransferOfficeDao pnrTransferOfficeDao) {
		this.pnrTransferOfficeDao = pnrTransferOfficeDao;
		setCommonGenericDao(pnrTransferOfficeDao);
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType) {
		return pnrTransferPrecheckDao.getToreplyJobOfEmergencyJobCount(userId,
				sendStartTime, sendEndTime, wsNum, wsTitle, status,city,country,lineType,workType);

	}

	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType, int firstResult, int endResult,
			int pageSize) {

		List<TaskModel> list = pnrTransferPrecheckDao
				.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
						sendEndTime, wsNum, wsTitle, status,city,country,lineType,workType, firstResult,
						endResult, pageSize);
		Iterator<TaskModel> i = list.iterator();
		while (i.hasNext()) {
			TaskModel model = i.next();
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							model.getProcessInstanceId()).taskId(
							model.getTaskId())
					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
							1);
			if (historicTasks != null & historicTasks.size() > 0) {
				model.setStatusName(historicTasks.get(0).getName());
				String defKey = historicTasks.get(0).getTaskDefinitionKey();
				model.setTaskDefKey(defKey);

				if (defKey.equals("newTask") || defKey.equals("transferTask")
						|| defKey.equals("transferAudit")
						|| defKey.equals("audit")) {
					model.setInitiator(model.getOneInitiator());
				}
			}

			if(model.getState()!=null && model.getState()==6){
				model.setStatusName("专家会审");
			}
		}

		return list;
	}

	@Override
	public List<Map> getSGSByCountryCSJ(String countryCSJ, String roleid) {

		return pnrTransferPrecheckDao.getSGSByCountryCSJ(countryCSJ, roleid);
	}

	@Override
	public List<Map> getDaiWeiSGSByCountryCSJ(String countryCSJ) {
		return pnrTransferPrecheckDao.getDaiWeiSGSByCountryCSJ(countryCSJ);
	}

	@Override
	public List<Map> getCityCSJByCountryCSJ(String countryCSJ) {
		return pnrTransferPrecheckDao.getCityCSJByCountryCSJ(countryCSJ);
	}

	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {
		return pnrTransferPrecheckDao.getHaveProcessList(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
				firstResult, endResult, pageSize);
	}

	/**
	 * 已处理工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return pnrTransferPrecheckDao.getHaveProcessCount(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);
	}

	/**
	 * 已归档工单明细
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize){
		return pnrTransferPrecheckDao.getArchivedList( processDefinitionKey,
				 userId,  sendStartTime,  sendEndTime,
				 wsNum,  wsTitle,  status,  firstResult,
				 endResult,  pageSize);
	}

	/**
	 * 已归档工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status){
		return pnrTransferPrecheckDao.getArchivedCount( processDefinitionKey,  userId,
				 sendStartTime,  sendEndTime,  wsNum,
				 wsTitle,  status);
	}

	@Override
	public int getToreplyJobOfEmergencyJobForCountersignCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, String city, String country,
			String lineType, String workType) {
		
		return pnrTransferPrecheckDao.getToreplyJobOfEmergencyJobForCountersignCount(userId, sendStartTime, sendEndTime, wsNum, wsTitle, status, city, country, lineType, workType);
	}

	@Override
	public List<TaskModel> getToreplyJobOfEmergencyJobForCountersignList(
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, String city,
			String country, String lineType, String workType, int firstResult,
			int endResult, int pageSize) {
		
		return pnrTransferPrecheckDao.getToreplyJobOfEmergencyJobForCountersignList(userId, sendStartTime, sendEndTime, wsNum, wsTitle, status, city, country, lineType, workType, firstResult, endResult, pageSize);
	}

	
/*************************************************大修改造工单************************************************************/
	@Override
	public String getDeptByUserId(String userid) {
		
		return pnrTransferPrecheckDao.getDeptByUserId(userid);
	}
	@Override
	public int getOverhaulRemakCount(String userid,
			ConditionQueryModel conditionQueryModel) {
		return pnrTransferPrecheckDao.getOverhaulRemakCount(userid, conditionQueryModel);
	}

	@Override
	public List<TaskModel> getOverhaulRemakJobList(String userId,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		// TODO Auto-generated method stub
		List<TaskModel> list = pnrTransferPrecheckDao
		.getOverhaulRemakJobList(userId, conditionQueryModel, firstResult, endResult, pageSize);
		Iterator<TaskModel> i = list.iterator();
		while (i.hasNext()) {
			TaskModel model = i.next();
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							model.getProcessInstanceId()).taskId(
							model.getTaskId())
					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
							1);
			if (historicTasks != null & historicTasks.size() > 0) {
				model.setStatusName(historicTasks.get(0).getName());
				String defKey = historicTasks.get(0).getTaskDefinitionKey();
				model.setTaskDefKey(defKey);

				if (defKey.equals("newTask") || defKey.equals("transferTask")
						|| defKey.equals("transferAudit")
						|| defKey.equals("audit")) {
					model.setInitiator(model.getOneInitiator());
				}
			}

		}
		return list;
	}
	

	@Override
	public int getOverhaulRemakArchivedCount(String processOverhaulKey,
			String processRemakeKey, String userid,
			ConditionQueryModel conditionQueryModel) {

		return pnrTransferPrecheckDao.getOverhaulRemakArchivedCount(
				processOverhaulKey, processRemakeKey, userid,
				conditionQueryModel);
	}

	@Override
	public List<TaskModel> getOverhaulRemakeArchivedList(
			String processOverhaulKey, String processRemakeKey, String userid,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		return pnrTransferPrecheckDao.getOverhaulRemakeArchivedList(
				processOverhaulKey, processRemakeKey, userid,
				conditionQueryModel, firstResult, endResult, pageSize);
	}
	
	
	
	
}
