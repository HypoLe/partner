package com.boco.activiti.partner.process.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;

import com.boco.activiti.partner.process.dao.IPnrTransferOverhaulRemakeJDBCDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrTransferOverhaulRemakeService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.googlecode.genericdao.search.Search;

public class PnrTransferOverhaulRemakeServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferOverhaulRemakeService {

	private IPnrTransferOverhaulRemakeJDBCDao pnrTransferOverhaulRemakeJDBCDao;

	private HistoryService historyService;

	/**
	 * 根据区县账号ID值获取市环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PrecheckShipModel getCitylinkOfOperationPerson(
			String areaCountyAccount) {
		List<Map> list = pnrTransferOverhaulRemakeJDBCDao
				.getCitylinkOfOperationPerson(areaCountyAccount);
		PrecheckShipModel precheckShipModel = new PrecheckShipModel();

		String cityLineDirector = "superUser";
		String cityManageCharge = "superUser";
		String cityManageDirector = "superUser";
		String cityCompany = "superUser";

		if (list != null && list.size() > 0) {
			cityLineDirector =list.get(0).get("CITY_LINE_DIRECTOR")==null?"superUser":list.get(0).get("CITY_LINE_DIRECTOR").toString().trim();
			cityManageCharge = list.get(0).get("CITY_MANAGE_CHARGE")==null?"superUser":list.get(0).get("CITY_MANAGE_CHARGE").toString().trim();
			cityManageDirector = list.get(0).get("CITY_MANAGE_DIRECTOR")==null?"superUser":list.get(0).get("CITY_MANAGE_DIRECTOR")
					.toString().trim();
			cityCompany =list.get(0).get("CITY_COMPANY")==null?"superUser": list.get(0).get("CITY_COMPANY").toString().trim();
		}

		// precheckShipModel.setCityLineCharge(sendUserId);
		precheckShipModel.setCityLineDirector(cityLineDirector);
		precheckShipModel.setCityManageCharge(cityManageCharge);
		precheckShipModel.setCityManageDirector(cityManageDirector);
		precheckShipModel.setCityCompany(cityCompany);
		return precheckShipModel;
	}

	/**
	 * 根据区县账号ID值和角色ID值获取省环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @param roleid
	 *            角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProvincelinkOfOperationPerson(String areaCountyAccount,
			String roleid) {
		return pnrTransferOverhaulRemakeJDBCDao
				.getProvincelinkOfOperationPerson(areaCountyAccount, roleid);
	}

	/**
	 * 获取区县账号下拉选的值
	 * 
	 * @param userId
	 *            建单人ID
	 * @return
	 */
	public List<Map<String, String>> getAreaCountyAccount(String userId) {
		return pnrTransferOverhaulRemakeJDBCDao.getAreaCountyAccount(userId);
	}

	/**
	 * 待回复工单 条数
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @return
	 */
	public int repairOrderCount(String userid, String flag, String processKey,
			ConditionQueryModel conditionQueryModel) {
		return pnrTransferOverhaulRemakeJDBCDao.repairOrderCount(userid, flag,
				processKey, conditionQueryModel);
	}

	/**
	 * 待回复工单 集合
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<TaskModel> repairOrderList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		List<TaskModel> list = pnrTransferOverhaulRemakeJDBCDao
				.repairOrderList(userid, flag, processKey, conditionQueryModel,
						firstResult, endResult, pageSize);
//		Iterator<TaskModel> i = list.iterator();
//		while (i.hasNext()) {
//			TaskModel model = i.next();
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							model.getProcessInstanceId()).taskId(
//							model.getTaskId())
//					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
//							1);
//			if (historicTasks != null & historicTasks.size() > 0) {
//				model.setStatusName(historicTasks.get(0).getName());
//				String defKey = historicTasks.get(0).getTaskDefinitionKey();
//				model.setTaskDefKey(defKey);
//			}
//
//			if (model.getState() != null && model.getState() == 6) {
//				model.setStatusName("专家会审");
//			}
//		}

		return list;
	}

	/**
	 * 已处理工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		return pnrTransferOverhaulRemakeJDBCDao.getHaveProcessCount(
				processDefinitionKey, userId, conditionQueryModel);
	}

	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		return pnrTransferOverhaulRemakeJDBCDao.getHaveProcessList(
				processDefinitionKey, userId, conditionQueryModel, firstResult,
				endResult, pageSize);
	}

	/**
	 * 由我创建的工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel) {
		return pnrTransferOverhaulRemakeJDBCDao.getByCreatingWorkOrderCount(
				processDefinitionKey, userId, conditionQueryModel);
	}

	/**
	 * 由我创建的工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return pnrTransferOverhaulRemakeJDBCDao.getByCreatingWorkOrderList(
				processDefinitionKey, userId, conditionQueryModel, firstResult,
				endResult, pageSize);
	}

	/**
	 * 工单抓回 查询条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey, String userId,
			String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel) {
		return pnrTransferOverhaulRemakeJDBCDao.getBackSheetCount(
				processDefinitionKey, userId, relationTableName, taskIds,
				conditionQueryModel);
	}

	/**
	 * 工单抓回 查询明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,
			String userId, String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return pnrTransferOverhaulRemakeJDBCDao.getBackSheetList(
				processDefinitionKey, userId, relationTableName, taskIds,
				conditionQueryModel, firstResult, endResult, pageSize);
	}

	/**
	 * 已归档工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param themeInterface
	 *            工单的类型
	 * @param onditionQueryModel
	 * @return
	 */
	public int getArchivedCount(String processDefinitionKey, String userId,
			String themeInterface, ConditionQueryModel conditionQueryModel) {
		return pnrTransferOverhaulRemakeJDBCDao.getArchivedCount(
				processDefinitionKey, userId, themeInterface,
				conditionQueryModel);

	}

	/**
	 * 已归档工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param themeInterface
	 * @param onditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String themeInterface,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return pnrTransferOverhaulRemakeJDBCDao.getArchivedList(
				processDefinitionKey, userId, themeInterface,
				conditionQueryModel, firstResult, endResult, pageSize);
	}
	
	/**
	 * 新建工单页面图片集合
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime) {
		return pnrTransferOverhaulRemakeJDBCDao.getPrecheckPhotoes(userId,photoDescribe,createTime);
	}

	public IPnrTransferOverhaulRemakeJDBCDao getPnrTransferOverhaulRemakeJDBCDao() {
		return pnrTransferOverhaulRemakeJDBCDao;
	}

	public void setPnrTransferOverhaulRemakeJDBCDao(
			IPnrTransferOverhaulRemakeJDBCDao pnrTransferOverhaulRemakeJDBCDao) {
		this.pnrTransferOverhaulRemakeJDBCDao = pnrTransferOverhaulRemakeJDBCDao;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Override
	public String[] getPhotoByProcessInstanceId(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return pnrTransferOverhaulRemakeJDBCDao.getPhotoByProcessInstanceId(processInstanceId);
	}
	
	public List excelExportToProcess(Search search, String userId,String deptId,String queryFlag,String processKey,String flag,HttpServletRequest request){
		System.out.println("---------进新建的导出方法了吗");
		
		System.out.println("----------userId="+userId);
		System.out.println("----------queryFlag="+queryFlag);
		System.out.println("----------processKey="+processKey);
		System.out.println("----------flag="+flag);
		
		List<TaskModel> list =null;
		if("listBacklog".equals(queryFlag)){	//待回复列表
			 list =  pnrTransferOverhaulRemakeJDBCDao.repairOrderList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0, 1,
					65000);
		}else if("waitWorkOrderList".equals(queryFlag)){//待办工单
			list =  pnrTransferOverhaulRemakeJDBCDao.repairOrderList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0, 1,
					65000);
		}
		System.out.println("-----------list长度="+list.size());
		
		return list;
	}
	
	/**
	 * 通过request请求获取查询条件
	 * 
	 * @param request
	 * @return
	 */
	private ConditionQueryModel getConditionQueryModel(HttpServletRequest request){
		String sendStartTime = request.getParameter("sheetAcceptLimit");//派单开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");//派单结束时间
		String wsNum = request.getParameter("wsNum");//工单号
		String wsTitle = request.getParameter("wsTitle");//工单名称 
		String status = request.getParameter("status");//工单状态 

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");//区县
		String lineType = request.getParameter("lineType");//线路级别
		String provName = request.getParameter("provName");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");//工单类别
		String mainSceneId = request.getParameter("mainSceneId");//场景
	
		System.out.println("----------sendStartTime="+sendStartTime);
		System.out.println("----------sendEndTime="+sendEndTime);
		System.out.println("----------wsNum="+wsNum);
		System.out.println("----------wsTitle="+wsTitle);
		System.out.println("----------status="+status);
		System.out.println("----------region="+region);
		System.out.println("----------country="+country);
		System.out.println("----------lineType="+lineType);
		System.out.println("----------precheckFlag="+precheckFlag);
		System.out.println("----------mainSceneId="+mainSceneId);
		
		
		
		
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		
		return conditionQueryModel;
	}

}
