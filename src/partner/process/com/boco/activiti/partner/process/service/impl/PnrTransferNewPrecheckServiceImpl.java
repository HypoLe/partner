package com.boco.activiti.partner.process.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.task.Task;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.boco.activiti.partner.process.dao.IPnrTransferNewPrecheckJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneDetailModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrActCityBudgetAmountService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.DateUtils;
import com.googlecode.genericdao.search.Search;

/**
 * 
 * @author wangyue
 * @ClassName: PnrTransferNewPrecheckServiceImpl
 * @Version 版本
 * @Copyright boco
 * @date Feb 4, 2015 10:19:10 AM
 * @description 新版预检预修工单--service接口实现
 */
public class PnrTransferNewPrecheckServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferNewPrecheckService {

	private IPnrTransferNewPrecheckJDBCDao pnrTransferNewPrecheckDao;

	private IPnrTransferOfficeDao pnrTransferOfficeDao;

	private HistoryService historyService;
	
	//测试任意回退
	private TaskService taskService;
	
	//测试任意回退
	private RepositoryService repositoryService;

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public IPnrTransferNewPrecheckJDBCDao getPnrTransferNewPrecheckDao() {
		return pnrTransferNewPrecheckDao;
	}

	public void setPnrTransferNewPrecheckDao(
			IPnrTransferNewPrecheckJDBCDao pnrTransferNewPrecheckDao) {
		this.pnrTransferNewPrecheckDao = pnrTransferNewPrecheckDao;
	}

	public IPnrTransferOfficeDao getPnrTransferOfficeDao() {
		return pnrTransferOfficeDao;
	}

	public void setPnrTransferOfficeDao(
			IPnrTransferOfficeDao pnrTransferOfficeDao) {
		this.pnrTransferOfficeDao = pnrTransferOfficeDao;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	/** ****************************************实现方法区域************************************************ */

	@Override
	public int getNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		return pnrTransferNewPrecheckDao.getTransferNewPrecheckCount(userid,
				flag, processKey, conditionQueryModel);
	}

	@Override
	public List<TaskModel> getTransferNewPrecheckList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<TaskModel> list = pnrTransferNewPrecheckDao
				.getTransferNewPrecheckList(userid, flag, processKey,
						conditionQueryModel, firstResult, endResult, pageSize);
		// Iterator<TaskModel> i = list.iterator();
		// while (i.hasNext()) {
		// TaskModel model = i.next();
		// List<HistoricTaskInstance> historicTasks = historyService
		// .createHistoricTaskInstanceQuery().processInstanceId(
		// model.getProcessInstanceId()).taskId(
		// model.getTaskId())
		// .orderByHistoricTaskInstanceStartTime().desc().listPage(0,
		// 1);
		// if (historicTasks != null & historicTasks.size() > 0) {
		// model.setStatusName(historicTasks.get(0).getName());
		// String defKey = historicTasks.get(0).getTaskDefinitionKey();
		// model.setTaskDefKey(defKey);
		// }
		//
		// if (model.getState() != null && model.getState() == 6) {
		// model.setStatusName("专家会审");
		// }
		// }

		return list;
	}

	@Override
	public PrecheckShipModel getPrecheckShipModelBycountryCharge(
			String countryCharge) {
		List<Map> list = pnrTransferNewPrecheckDao
				.getPrecheckShipModelBycountryCharge(countryCharge);
		PrecheckShipModel precheckShipModel = new PrecheckShipModel();
		String countryDirector = "superUser";
		String cityLineCharge = "superUser";
		String cityLineDirector = "superUser";
		String cityManageCharge = "superUser";
		String cityManageDirector = "superUser";
		String cityCompany = "superUser";
		
		String cityXcCenterZg = "superUser"; //市现场中心主管
		String cityXcCenterZr = "superUser";//市现场中心主任

		if (list != null && list.size() > 0) {
			countryDirector = list.get(0).get("COUNTRY_DIRECTOR") == null ? "superUser"
					: list.get(0).get("COUNTRY_DIRECTOR").toString().trim();
			cityLineCharge = list.get(0).get("CITY_LINE_CHARGE") == null ? "superUser"
					: list.get(0).get("CITY_LINE_CHARGE").toString().trim();
			cityLineDirector = list.get(0).get("CITY_LINE_DIRECTOR") == null ? "superUser"
					: list.get(0).get("CITY_LINE_DIRECTOR").toString().trim();
			cityManageCharge = list.get(0).get("CITY_MANAGE_CHARGE") == null ? "superUser"
					: list.get(0).get("CITY_MANAGE_CHARGE").toString().trim();
			cityManageDirector = list.get(0).get("CITY_MANAGE_DIRECTOR") == null ? "superUser"
					: list.get(0).get("CITY_MANAGE_DIRECTOR").toString().trim();
			cityCompany = list.get(0).get("CITY_COMPANY") == null ? "superUser"
					: list.get(0).get("CITY_COMPANY").toString().trim();
			
			cityXcCenterZg = list.get(0).get("CITY_XC_CENTER_ZG") == null ? "superUser"
					: list.get(0).get("CITY_XC_CENTER_ZG").toString().trim();
			
			cityXcCenterZr = list.get(0).get("CITY_XC_CENTER_ZR") == null ? "superUser"
					: list.get(0).get("CITY_XC_CENTER_ZR").toString().trim();
		}

		precheckShipModel.setCountryCharge(countryCharge);
		precheckShipModel.setCountryDirector(countryDirector);
		precheckShipModel.setCityLineCharge(cityLineCharge);
		precheckShipModel.setCityLineDirector(cityLineDirector);
		precheckShipModel.setCityManageCharge(cityManageCharge);
		precheckShipModel.setCityManageDirector(cityManageDirector);
		precheckShipModel.setCityCompany(cityCompany);
		precheckShipModel.setCityXcCenterZg(cityXcCenterZg);
		precheckShipModel.setCityXcCenterZr(cityXcCenterZr);
		return precheckShipModel;
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
		return pnrTransferNewPrecheckDao.getHaveProcessList(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status, firstResult, endResult, pageSize);
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
		return pnrTransferNewPrecheckDao.getHaveProcessCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);
	}
	
	/**
	 * 待接口调用工单明细
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
	public List<TaskModel> workOrderRemind(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {
		return pnrTransferNewPrecheckDao.workOrderRemind(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
				firstResult, endResult, pageSize);
	}
	/**
	 * 待接口调用工单条数
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
	public int workOrderRemindCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return pnrTransferNewPrecheckDao.workOrderRemindCount(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);
	}

	/**
	 * 由我创建的工单条数
	 * 
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status) {
		return pnrTransferNewPrecheckDao.getByCreatingWorkOrderCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);
	}

	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<TaskModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId, String sendStartTime,
			String sendEndTime, String wsNum, String wsTitle, String status,
			int firstResult, int endResult, int pageSize) {
		return pnrTransferNewPrecheckDao.getByCreatingWorkOrderList(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status, firstResult, endResult, pageSize);
	}

	@Override
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,
			String photoDescribe, String createTime, String photoCreate,
			String faultLocation) {
		return pnrTransferNewPrecheckDao.getPrecheckPhotoes(userId,
				photoDescribe, createTime, photoCreate, faultLocation);
	}
	
	/**
	 * 查询事情照片
	 * 
	 * @author WangJun 
	 * @param param	参数可以任意的封装
	 * @return
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param){
		return pnrTransferNewPrecheckDao.getPrecheckPhotoes(param);
	}

	@Override
	public String getPhotoPlth(String id) {
		// String plth = pnrTransferNewPrecheckDao.getPhotoPlth(id);
		String plth = "wyphotoTest/qq.jpg";
		String name = "";
		if (plth != null && !"".equals(plth)) {
			String[] names = plth.split("/");
			name = names[names.length - 1];
		}
		return name;
	}

	@Override
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(
			String processInstanceId) {
		return pnrTransferNewPrecheckDao
				.showPhotoByProcessInstanceId(processInstanceId);
	}

	public List<RoomAssets> showAssetsByProcessInstanceId(
			String processInstanceId) {

		return pnrTransferNewPrecheckDao
				.showAssetsByProcessInstanceId(processInstanceId);
	}

	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		// TODO Auto-generated method stub
		return pnrTransferNewPrecheckDao.getOnePnrAndroidPhotoFileById(id);
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
		return pnrTransferNewPrecheckDao.getBackSheetCount(
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
		return pnrTransferNewPrecheckDao.getBackSheetList(processDefinitionKey,
				userId, conditionQueryModel, firstResult, endResult, pageSize);
	}

	@Override
	public void judgeIsExitBypgohoIdAndProcessInstanceId(
			String processInstanceId) {
		pnrTransferNewPrecheckDao
				.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
	}

	/**
	 * 已归档工单条数
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
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, String themeInterface) {
		return pnrTransferNewPrecheckDao.getArchivedCount(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
				themeInterface);
	}

	/**
	 * 已归档工单明细
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
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, String themeInterface,
			int firstResult, int endResult, int pageSize) {
		return pnrTransferNewPrecheckDao.getArchivedList(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
				themeInterface, firstResult, endResult, pageSize);
	}

	@Override
	public void setStateByPhotoId(String photoId, String state) {
		pnrTransferNewPrecheckDao.setStateByphotoId(photoId, state);

	}

	/**
	 * add by wyl 20150430 下载附件记录数
	 */
	@Override
	public int getDownAttachMentCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		return pnrTransferNewPrecheckDao.getDownAttachMentCount(userid,
				conditionQueryModel);
	}

	/**
	 * add by wyl 20150430 下载附件结果集合
	 */
	@Override
	public List<DownAttachMentModel> getDownAttachMentList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		List<DownAttachMentModel> list = pnrTransferNewPrecheckDao
				.getDownAttachMentList(userid, conditionQueryModel,
						firstResult, endResult, pageSize);

		return list;
	}

	@Override
	public List<DownAttachMentModel> getDownAttachMentListAll(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		List<DownAttachMentModel> list = pnrTransferNewPrecheckDao
				.getDownAttachMentListAll(userid, conditionQueryModel);

		return list;
	}

	@Override
	public Boolean IsEnableDownAttachMent(String userid) {
		// TODO Auto-generated method stub
		return pnrTransferNewPrecheckDao.IsEnableDownAttachMent(userid);
	}

	@Override
	public void addDownAttachMentInfo(String id, String userid) {
		// TODO Auto-generated method stub
		pnrTransferNewPrecheckDao.addDownAttachMentInfo(id, userid);
	}

	@Override
	public void deleteDownAttachMentInfo(String id) {
		pnrTransferNewPrecheckDao.deleteDownAttachMentInfo(id);
	}

	/**
	 * 通过照片ID删除事情照片
	 * 
	 * @param id
	 *            照片ID
	 */
	public void deletePictureById(String id) {
		pnrTransferNewPrecheckDao.deletePictureById(id);
	}

	/**
	 * add by wangchang 20150507 工单下载记录数
	 */
	public int getDownWorkOrderCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		return pnrTransferNewPrecheckDao.getDownWorkOrderCount(userid,
				conditionQueryModel);
	}

	/**
	 * add by wangchang 20150430 下载附件结果集合
	 */
	public List<DownWorkOrderModel> getDownWorkOrderList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		List<DownWorkOrderModel> list = pnrTransferNewPrecheckDao
				.getDownWorkOrderList(userid, conditionQueryModel, firstResult,
						endResult, pageSize);

		return list;
	}

	/**
	 * 查询所有工单信息
	 */
	public List getDownWorkOrderListAll(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		List list = pnrTransferNewPrecheckDao.getDownWorkOrderListAll(userid,
				conditionQueryModel);
		return list;
	}

	/**
	 * add by wangchang 20150507 下载附件记录数
	 */
	public int getDownImageImportCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		return pnrTransferNewPrecheckDao.getDownAttachMentCount(userid,
				conditionQueryModel);
	}

	/**
	 * add by wangchang 20150507 下载图片附件结果集合
	 */
	public List<DownAttachMentModel> getDownImageImportList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		List<DownAttachMentModel> list = pnrTransferNewPrecheckDao
				.getDownAttachMentList(userid, conditionQueryModel,
						firstResult, endResult, pageSize);

		return list;
	}

	public List excelExportToProcess(Search search, String userId,
			String deptId, String queryFlag, String processKey, String flag,
			HttpServletRequest request) {
		System.out.println("---------进新建的导出方法了吗");

		System.out.println("----------userId=" + userId);
		System.out.println("----------queryFlag=" + queryFlag);
		System.out.println("----------processKey=" + processKey);
		System.out.println("----------flag=" + flag);

		List<TaskModel> list = null;
		List<DownWorkOrderModel> list1 =null;
		if ("listBacklog".equals(queryFlag)) { // 待回复列表
			list = pnrTransferNewPrecheckDao.getTransferNewPrecheckList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0,
					1, 65000);
		} else if ("waitWorkOrderList".equals(queryFlag)) {// 待办工单
			list = pnrTransferNewPrecheckDao.getTransferNewPrecheckList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0,
					1, 65000);
		} else if ("localNetworkWorkOrder".equals(queryFlag)) {
			list = pnrTransferNewPrecheckDao.getLocalNetworkWorkOrderList(this
					.getUserAreaid(request), "", flag, processKey, this
					.getConditionQueryModel(request), 0, 1, 65000);
		}else if("downWorkOrder".equals(queryFlag)){//线路工单下载
			list1 = pnrTransferNewPrecheckDao.getDownWorkOrderList(userId,  
					this.getConditionQueryDAMModel(request), 0, 1, 
					65000);
		}
		System.out.println("-----------list长度=" + list.size());

		if("downWorkOrder".equals(queryFlag)){
			return list1;
		}else{
			return list;
		}
	}

	/**
	 * 通过request请求获取查询条件
	 * 
	 * @param request
	 * @return
	 */
	private ConditionQueryModel getConditionQueryModel(
			HttpServletRequest request) {
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 派单开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 派单结束时间
		String wsNum = request.getParameter("wsNum");// 工单号
		String wsTitle = request.getParameter("wsTitle");// 工单名称
		String status = request.getParameter("status");// 工单状态
		String totalFee = request.getParameter("totalFee");// 工费
		String totalMaterialCost = request.getParameter("totalMaterialCost");// 材料费

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		String lineType = request.getParameter("lineType");// 线路级别
		// String provName = request.getParameter("provName");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");// 工单类别
		String mainSceneId = request.getParameter("mainSceneId");// 场景
		String workOrderDegree = request.getParameter("workOrderDegree");
		
		String keyWord = StaticMethod.nullObject2String(request.getParameter("keyWord"));//关键字
		String subTypeName = StaticMethod.nullObject2String(request.getParameter("subTypeName"));//工单子类型
		String approveStartTime = StaticMethod.nullObject2String(request.getParameter("approveStartTime"));//批复开始时间
		String approveEndTime = StaticMethod.nullObject2String(request.getParameter("approveEndTime"));//批复结束时间
	

		System.out.println("----------sendStartTime=" + sendStartTime);
		System.out.println("----------sendEndTime=" + sendEndTime);
		System.out.println("----------wsNum=" + wsNum);
		System.out.println("----------wsTitle=" + wsTitle);
		System.out.println("----------status=" + status);
		System.out.println("----------region=" + region);
		System.out.println("----------country=" + country);
		System.out.println("----------lineType=" + lineType);
		System.out.println("----------precheckFlag=" + precheckFlag);
		System.out.println("----------mainSceneId=" + mainSceneId);

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		// conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setTotalFee(totalFee);
		conditionQueryModel.setTotalMaterialCost(totalMaterialCost);
		conditionQueryModel.setWorkOrderDegree(workOrderDegree);
		conditionQueryModel.setKeyWord(keyWord);
		conditionQueryModel.setSubTypeName(subTypeName);
		conditionQueryModel.setApproveStartTime(approveStartTime);
		conditionQueryModel.setApproveEndTime(approveEndTime);
		return conditionQueryModel;
	}
	
	/**
	 * 通过request请求获取查询条件
	 * 
	 * @param request
	 * @return
	 */
	private ConditionQueryDAMModel getConditionQueryDAMModel(HttpServletRequest request){
		
		
		/**派单开始时间*/
		String sendStartTime=request.getParameter("sheetAcceptLimit");//派单开始时间
		String sendEndTime=request.getParameter("sheetCompleteLimit");//派单结束时间
		String city=request.getParameter("region");//地市
		String country=request.getParameter("country");//区县
		String themeinterface=request.getParameter("themeinterface");//工单类型
		String areaid=this.getUserAreaid(request);
		
		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}
		
		
		
		System.out.println("sendStartTime============="+sendStartTime);
		System.out.println("sendEndTime============="+sendEndTime);
		System.out.println("city============="+city);
		System.out.println("country============="+country);
		System.out.println("themeinterface============="+themeinterface);
		System.out.println("areaid============="+areaid);
		System.out.println("taskdefkey============="+taskdefkey);
	
		
			
		
		ConditionQueryDAMModel conditionQueryDAMModel = new ConditionQueryDAMModel();
		conditionQueryDAMModel.setSendStartTime(sendStartTime);
		conditionQueryDAMModel.setSendEndTime(sendEndTime);
		conditionQueryDAMModel.setCity(city);
		conditionQueryDAMModel.setCountry(country);
		conditionQueryDAMModel.setArea(areaid);
		conditionQueryDAMModel.setThemeinterface(themeinterface);
		conditionQueryDAMModel.setTaskdefkey(taskdefkey);
		
		return conditionQueryDAMModel;
	}

	// 获取当前登陆人地区id
	public String getUserAreaid(HttpServletRequest request) {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid = com.boco.eoms.partner.process.util.CommonUtils
				.getAreaIdByDeptId(sessionForm.getDeptid());
		return areaid;

	}

	@Override
	public List<TaskModel> getLocalNetworkWorkOrderList(String areaid,
			String userid, String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<TaskModel> list = pnrTransferNewPrecheckDao
				.getLocalNetworkWorkOrderList(areaid, userid, flag, processKey,
						conditionQueryModel, firstResult, endResult, pageSize);
		return list;
	}

	@Override
	public int getLocalNetworkWorkOrderListCount(String areaid, String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel) {
		// TODO Auto-generated method stub
		return pnrTransferNewPrecheckDao.getLocalNetworkWorkOrderListCount(
				areaid, userid, flag, processKey, conditionQueryModel);
	}

	/**
	 * activiti驳回到任意节点的实现
	 * 
	 * @param procInstId
	 * @param destTaskKey
	 * @param rejectMessage
	 */
	public void rejectTask(String procInstId, String destTaskKey,
			String rejectMessage) {
		try{
			// TODO Auto-generated method stub
			// 获得当前任务的对应实列

			Task taskEntity = taskService.createTaskQuery().processInstanceId(
					procInstId).singleResult();
			// 当前任务key
			String taskDefKey = taskEntity.getTaskDefinitionKey();
			// 获得当前流程的定义模型

			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(taskEntity
							.getProcessDefinitionId());

			// 获得当前流程定义模型的所有任务节点

			List<ActivityImpl> activitilist = processDefinition.getActivities();
			// 获得当前活动节点和驳回的目标节点"draft"
			ActivityImpl currActiviti = null;// 当前活动节点
			ActivityImpl destActiviti = null;// 驳回目标节点
			int sign = 0;
			for (ActivityImpl activityImpl : activitilist) {
				// 确定当前活动activiti节点

				if (taskDefKey.equals(activityImpl.getId())) {
					currActiviti = activityImpl;

					sign++;
				} else if (destTaskKey.equals(activityImpl.getId())) {
					destActiviti = activityImpl;

					sign++;
				}
				// System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
				if (sign == 2) {
					break;// 如果两个节点都获得,退出跳出循环
				}
			}
			System.out.println("//-->currActiviti activityImpl.getId():"
					+ currActiviti.getId());
			System.out.println("//-->destActiviti activityImpl.getId():"
					+ destActiviti.getId());
			// 保存当前活动节点的流程想参数

			List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(
					0);

			for (PvmTransition pvmTransition : currActiviti
					.getOutgoingTransitions()) {
				hisPvmTransitionList.add(pvmTransition);
			}
			// 清空当前活动几点的所有流出项

			currActiviti.getOutgoingTransitions().clear();
			System.out
					.println("//-->currActiviti.getOutgoingTransitions().clear():"
							+ currActiviti.getOutgoingTransitions().size());
			// 为当前节点动态创建新的流出项

			TransitionImpl newTransitionImpl = currActiviti
					.createOutgoingTransition();
			// 为当前活动节点新的流出目标指定流程目标
			newTransitionImpl.setDestination(destActiviti);
			// 保存驳回意见

			taskEntity.setDescription(rejectMessage);// 设置驳回意见
			taskService.saveTask(taskEntity);
			// 设定驳回标志

			Map<String, Object> variables = new HashMap<String, Object>(0);
//			variables.put(WfConstant.WF_VAR_IS_REJECTED.value(),
//					WfConstant.IS_REJECTED.value());
			variables.put("workOrderCheck", "superUser");
			variables.put("cityManageChargeState", "rollback");
			// 执行当前任务驳回到目标任务draft

			taskService.complete(taskEntity.getId(), variables);

			// 清除目标节点的新流入项

			destActiviti.getIncomingTransitions().remove(newTransitionImpl);
			// 清除原活动节点的临时流程项

			currActiviti.getOutgoingTransitions().clear();
			// 还原原活动节点流出项参数

			currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * activiti驳回到任意节点的实现
	 * 
	 * @param processInstanceId	流程号
	 * @param toTaskKey	驳回到的任务key值
	 * @param rejectMessage	驳回原因
	 * @param variables	涉及到的流程参数	设定驳回标志	
	 */
	public void rejectToAnyTask(String processInstanceId, String toTaskKey,
			String rejectMessage,Map<String, Object> variables) {
		try{
			// TODO Auto-generated method stub
			// 获得当前任务的对应实列

			Task taskEntity = taskService.createTaskQuery().processInstanceId(
					processInstanceId).singleResult();
			// 当前任务key
			String taskDefKey = taskEntity.getTaskDefinitionKey();
			// 获得当前流程的定义模型

			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(taskEntity
							.getProcessDefinitionId());

			// 获得当前流程定义模型的所有任务节点

			List<ActivityImpl> activitilist = processDefinition.getActivities();
			// 获得当前活动节点和驳回的目标节点"draft"
			ActivityImpl currActiviti = null;// 当前活动节点
			ActivityImpl destActiviti = null;// 驳回目标节点
			int sign = 0;
			for (ActivityImpl activityImpl : activitilist) {
				// 确定当前活动activiti节点

				if (taskDefKey.equals(activityImpl.getId())) {
					currActiviti = activityImpl;

					sign++;
				} else if (toTaskKey.equals(activityImpl.getId())) {
					destActiviti = activityImpl;

					sign++;
				}
				// System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
				if (sign == 2) {
					break;// 如果两个节点都获得,退出跳出循环
				}
			}
			System.out.println("//-->currActiviti activityImpl.getId():"
					+ currActiviti.getId());
			System.out.println("//-->destActiviti activityImpl.getId():"
					+ destActiviti.getId());
			// 保存当前活动节点的流程想参数

			List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(
					0);

			for (PvmTransition pvmTransition : currActiviti
					.getOutgoingTransitions()) {
				hisPvmTransitionList.add(pvmTransition);
			}
			// 清空当前活动几点的所有流出项

			currActiviti.getOutgoingTransitions().clear();
			System.out
					.println("//-->currActiviti.getOutgoingTransitions().clear():"
							+ currActiviti.getOutgoingTransitions().size());
			// 为当前节点动态创建新的流出项

			TransitionImpl newTransitionImpl = currActiviti
					.createOutgoingTransition();
			// 为当前活动节点新的流出目标指定流程目标
			newTransitionImpl.setDestination(destActiviti);
			// 保存驳回意见

			taskEntity.setDescription(rejectMessage);// 设置驳回意见
			taskService.saveTask(taskEntity);
			// 设定驳回标志

			//Map<String, Object> variables = new HashMap<String, Object>(0);
//			variables.put(WfConstant.WF_VAR_IS_REJECTED.value(),
//					WfConstant.IS_REJECTED.value());
//			variables.put("workOrderCheck", "superUser");
//			variables.put("cityManageChargeState", "rollback");
			// 执行当前任务驳回到目标任务draft

			taskService.complete(taskEntity.getId(), variables);

			// 清除目标节点的新流入项

			destActiviti.getIncomingTransitions().remove(newTransitionImpl);
			// 清除原活动节点的临时流程项

			currActiviti.getOutgoingTransitions().clear();
			// 还原原活动节点流出项参数

			currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据环节的ID和工单号查询该环节的处理人
	 * 
	 * @param userTaskId	环节的userTask的Id
	 * @param processInstanceId	工单号
	 * @return
	 */
	public String getLinkProcessingUserId(String userTaskId,String processInstanceId){
		return pnrTransferNewPrecheckDao.getLinkProcessingUserId(userTaskId,processInstanceId);
	}
	
	/**
	 * 市运维主管审批的时候,对项目金额进行判断
	 * 
	 * @param sessionform	操作人
	 * @param processInstanceId	流程号
	 * @return
	 */
	public Map<String, String> validateBudgetAmount(TawSystemSessionForm sessionform,String processInstanceId,Map<String,String> paramMap){
		String returnMsg="";
		Map<String,String> returnMsgMap=new HashMap<String,String>();//返回结果
		//1.根据操作人获取对应的地市信息
        //String userId = sessionform.getUserid();
        ITawSystemDeptManager deptSys = (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
        TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionform
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		String cityId = "";
		if((StaticMethod.nullObject2String(areaid)).length()==4){
			cityId=areaid;
		}else if((StaticMethod.nullObject2String(areaid)).length()==6){
			cityId=areaid.substring(0,4);
		}
		if("".equals(cityId)){
			returnMsgMap.put("returnMsg","当前操作人的地市信息不正确。");
			return returnMsgMap;
		}
		
		//2.获取当前系统时间，获取当前季度和当前月
		Calendar now = Calendar.getInstance();
		String budgetYear=now.get(Calendar.YEAR)+"";//年
		String budgetMonth=(now.get(Calendar.MONTH) + 1)+"";//月
		String budgetQuarter=DateUtils.getQuarterByMonthTwo(budgetMonth);//季度
		String index = DateUtils.getIndexMonthByQuarter(budgetQuarter,budgetMonth);
		
		//3.获取地市该季度对应的预算金额A
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) ApplicationContextHolder.getInstance().getBean("pnrActCityBudgetAmountService");
		String tempCityBudgetAmount = pnrActCityBudgetAmountService.getCityBudgetAmount(cityId,budgetYear,budgetQuarter);
		double cityBudgetAmount=0.0;//地市该季度对应的预算金额A
		if("".equals(tempCityBudgetAmount)){
			returnMsgMap.put("returnMsg","没有预算金额信息。");
			return returnMsgMap;
		}else{
			try{
				 cityBudgetAmount = Double.parseDouble(tempCityBudgetAmount);
				 if(cityBudgetAmount==0.0){
					 returnMsgMap.put("returnMsg","本季度的预算金额为0。");
					 return returnMsgMap;
				 }
			}catch(Exception e){
				e.printStackTrace();
				 returnMsgMap.put("returnMsg","预算金额信息不正确。");
				 return returnMsgMap;
			}
		}
		System.out.println("------地市该季度对应的预算金额cityBudgetAmount="+cityBudgetAmount);
		
		//4.获取应急或者常规工单，当前季度已经通过市运维主管审批通过的项目金额总和
		
		//获取开始时间
		String quarterStartTime=DateUtils.getStartTimeOfQuarter(budgetYear,budgetQuarter);
		//获取结束时间：当前系统时间
		Date systemTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = formatter.format(systemTime);
		
		//应急 常规
		String precheckFlag="";
		if(paramMap!=null){
			precheckFlag= paramMap.get("precheckFlag");
		}
		if("".equals(precheckFlag)){
			returnMsgMap.put("returnMsg","应急/常规值为空。");
			return returnMsgMap; 
		}
		
		//查询条件
		Map<String,String> param=new HashMap<String,String>();
		param.put("tableName", "transfernew_key_relation");
		param.put("processType", "transferNewPrechechProcess");
		param.put("linkSerialNumber", "6");
		param.put("linkKey", "cityManageExamine");
		param.put("cityId", cityId);
		param.put("quarterStartTime",quarterStartTime);
		param.put("currentTime", currentTime);
		param.put("precheckFlag", precheckFlag);
		
		//当前季度已经通过市运维主管审批通过的项目金额总和
		String completedTotalAmountOfCurrentQuarter = this.getLinkMonthTotalProjectAmount(param);
		System.out.println("------当前季度已经通过市运维主管审批通过的项目金额总和completedTotalAmountOfCurrentQuarter="+completedTotalAmountOfCurrentQuarter);
		
		//5.预算金额百分比的值：应急10%；非应急：90%
		double standardBudgetAmount=0.0;
		if("101231401".equals(precheckFlag)){//应急工单
			standardBudgetAmount=CommonUtils.reservedDecimalPlaces(cityBudgetAmount*0.1,2);
		}else{//其他
			standardBudgetAmount=CommonUtils.reservedDecimalPlaces(cityBudgetAmount*0.9,2);
		}
		System.out.println("------预算金额百分比的值standardBudgetAmount="+standardBudgetAmount);
		
		//6.获取该工单的预算金额
		//判断是不是批量，是批量是工单的金额的总和进行比较的
		String isBatch="";//批量标识
		if(paramMap!=null){
			isBatch= paramMap.get("isBatch");
		}
		
		double projectAmount=0.0;//预算金额
		if("yes".equals(isBatch)){
			String tempTotalAmount =paramMap.get("totalAmount");
			projectAmount=Double.parseDouble(tempTotalAmount);
		}else{
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			PnrTransferOffice pnrTransferOffice = null;
			if (pnrTicketList != null && pnrTicketList.size() > 0) {
				pnrTransferOffice = pnrTicketList.get(0);
			}
			
			Double tempProjectAmount = pnrTransferOffice.getProjectAmount();
			
			//该工单的预算金额
			projectAmount=tempProjectAmount.doubleValue();
		}
		System.out.println("------该工单的预算金额projectAmount="+projectAmount);
		
		//7.当选择审批通过时要判断该工单的预算金额+当月已审批通过的预算金额是否已经超出该余额的预算封顶值，如果超出则提示用户：“无法审批通过，已临近本月预算金额封顶值”。
		if((standardBudgetAmount-Double.parseDouble(completedTotalAmountOfCurrentQuarter)) < projectAmount){
			returnMsg="exceed";//超出
			if("yes".equals(isBatch)){
				double exceedAmount=CommonUtils.reservedDecimalPlaces(projectAmount-(standardBudgetAmount-Double.parseDouble(completedTotalAmountOfCurrentQuarter)),2);
				System.out.println("------批量处理超出金额为exceedAmount="+exceedAmount);
				returnMsgMap.put("exceedAmount",Double.toString(exceedAmount));
			}
		}else{
			returnMsg="notExceed";//不超出
		}
		
		System.out.println("------returnMsg="+returnMsg);
		
		returnMsgMap.put("returnMsg",returnMsg);
		return returnMsgMap;
	}
	
	/**
	 * 查询流程某个环节的某个月的预算金额总和
	 * 
	 * @param conditionQueryModel	查询条件
	 * @return
	 */
	public String getLinkMonthTotalProjectAmount(Map<String,String> param) {
		return pnrTransferNewPrecheckDao.getLinkMonthTotalProjectAmount(param);
	}
	
	/**
	 * 	查询抽检工单条数
	 	 * @author WANGJUN
	 	 * @title: querySamplingCount
	 	 * @date Aug 4, 2016 10:40:41 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int querySamplingCount(String userId,ConditionQueryModel conditionQueryModel){
		return pnrTransferNewPrecheckDao.querySamplingCount(userId,conditionQueryModel);
	}
	/**
	 * 	 查询抽检工单明细
	 	 * @author WANGJUN
	 	 * @title: querySamplingList
	 	 * @date Aug 4, 2016 10:40:50 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> querySamplingList(String userId,ConditionQueryModel conditionQueryModel,int firstResult,int endResult,int pageSize){
		return pnrTransferNewPrecheckDao.querySamplingList(userId,conditionQueryModel,firstResult,endResult,pageSize);
	}

}
