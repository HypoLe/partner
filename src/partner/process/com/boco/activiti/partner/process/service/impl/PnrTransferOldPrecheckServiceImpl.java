package com.boco.activiti.partner.process.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;

import com.boco.activiti.partner.process.dao.IPnrTransferOfficeDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOldPrecheckJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTransferPrecheckJDBCDao;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrTransferOldPrecheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 * 旧预检预修工单--service
 * 
 * @author Administrator
 * 
 */
public class PnrTransferOldPrecheckServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferOldPrecheckService {

	private IPnrTransferOldPrecheckJDBCDao pnrTransferOldPrecheckDao;

	private IPnrTransferOfficeDao pnrTransferOfficeDao;

	private HistoryService historyService;

	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return pnrTransferOldPrecheckDao.getToreplyJobOfEmergencyJobCount(
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);

	}

	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize) {

		List<TaskModel> list = pnrTransferOldPrecheckDao
				.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
						sendEndTime, wsNum, wsTitle, status, firstResult,
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

		}

		return list;
	}

	@Override
	public List<Map> getSGSByCountryCSJ(String countryCSJ, String roleid) {

		return pnrTransferOldPrecheckDao.getSGSByCountryCSJ(countryCSJ, roleid);
	}

	@Override
	public List<Map> getDaiWeiSGSByCountryCSJ(String countryCSJ) {
		return pnrTransferOldPrecheckDao.getDaiWeiSGSByCountryCSJ(countryCSJ);
	}

	@Override
	public List<Map> getCityCSJByCountryCSJ(String countryCSJ) {
		return pnrTransferOldPrecheckDao.getCityCSJByCountryCSJ(countryCSJ);
	}

	/**
	 * ********************************************get 和 set
	 * ****************************************
	 */
	public IPnrTransferOldPrecheckJDBCDao getPnrTransferOldPrecheckDao() {
		return pnrTransferOldPrecheckDao;
	}

	public void setPnrTransferOldPrecheckDao(
			IPnrTransferOldPrecheckJDBCDao pnrTransferOldPrecheckDao) {
		this.pnrTransferOldPrecheckDao = pnrTransferOldPrecheckDao;
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

}
