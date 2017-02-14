package com.boco.activiti.partner.process.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;

import com.boco.activiti.partner.process.dao.INetResInspectPhotoDao;
import com.boco.activiti.partner.process.dao.INetResInspectPhotoJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeJDBCDao;
import com.boco.activiti.partner.process.model.NetResInspectPhoto;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.INetResInspectPhotoService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
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
public class NetResInspectPhotoServiceImpl extends
		CommonGenericServiceImpl<NetResInspectPhoto> implements
		INetResInspectPhotoService {
	
	private INetResInspectPhotoJDBCDao netResInspectPhotoJDBCDao;

	private INetResInspectPhotoDao netResInspectPhotoDao;

	private HistoryService historyService;
	
	private IPnrTransferOfficeJDBCDao pnrTransferOfficeDaoJDBC;

	public INetResInspectPhotoJDBCDao getNetResInspectPhotoJDBCDao() {
		return netResInspectPhotoJDBCDao;
	}

	public void setNetResInspectPhotoJDBCDao(
			INetResInspectPhotoJDBCDao netResInspectPhotoJDBCDao) {
		this.netResInspectPhotoJDBCDao = netResInspectPhotoJDBCDao;
	}

	public INetResInspectPhotoDao getNetResInspectPhotoDao() {
		return netResInspectPhotoDao;
	}

	public void setNetResInspectPhotoDao(
			INetResInspectPhotoDao netResInspectPhotoDao) {
		this.netResInspectPhotoDao = netResInspectPhotoDao;
		this.setCommonGenericDao(netResInspectPhotoDao);
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	public IPnrTransferOfficeJDBCDao getPnrTransferOfficeDaoJDBC() {
		return pnrTransferOfficeDaoJDBC;
	}

	public void setPnrTransferOfficeDaoJDBC(
			IPnrTransferOfficeJDBCDao pnrTransferOfficeDaoJDBC) {
		this.pnrTransferOfficeDaoJDBC = pnrTransferOfficeDaoJDBC;
	}

	/******************************************实现方法区域*************************************************/

	@Override
	public int getNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		return netResInspectPhotoJDBCDao.getTransferNewPrecheckCount(userid,
				flag, processKey, conditionQueryModel);
	}

	@Override
	public List<RoomDemolitionModel> getTransferNewPrecheckList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<RoomDemolitionModel> list =  netResInspectPhotoJDBCDao.getTransferNewPrecheckList(userid,
				flag, processKey, conditionQueryModel, firstResult, endResult,
				pageSize);
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

	@Override
	public PrecheckShipModel getPrecheckShipModelBycountryCharge(
			String countryCharge) {
		List<Map> list = netResInspectPhotoJDBCDao.getPrecheckShipModelBycountryCharge(countryCharge);
		PrecheckShipModel precheckShipModel = new PrecheckShipModel();
		String countryDirector = "superUser";
		String cityLineCharge = "superUser";
		String cityLineDirector = "superUser";
		String cityManageCharge = "superUser";
		String cityManageDirector = "superUser";
		String cityCompany = "superUser";
		
		if(list!=null && list.size()>0){
			countryDirector = list.get(0).get("COUNTRY_DIRECTOR")==null?"superUser":list.get(0).get("COUNTRY_DIRECTOR").toString().trim();
			cityLineCharge = list.get(0).get("CITY_LINE_CHARGE")==null?"superUser":list.get(0).get("CITY_LINE_CHARGE").toString().trim();
			cityLineDirector = list.get(0).get("CITY_LINE_DIRECTOR")==null?"superUser":list.get(0).get("CITY_LINE_DIRECTOR").toString().trim();
			cityManageCharge = list.get(0).get("CITY_MANAGE_CHARGE")==null?"superUser":list.get(0).get("CITY_MANAGE_CHARGE").toString().trim();
			cityManageDirector = list.get(0).get("CITY_MANAGE_DIRECTOR")==null?"superUser":list.get(0).get("CITY_MANAGE_DIRECTOR").toString().trim();
			cityCompany = list.get(0).get("CITY_COMPANY")==null?"superUser":list.get(0).get("CITY_COMPANY").toString().trim();
		}
		
		precheckShipModel.setCountryCharge(countryCharge);
		precheckShipModel.setCountryDirector(countryDirector);
		precheckShipModel.setCityLineCharge(cityLineCharge);
		precheckShipModel.setCityLineDirector(cityLineDirector);
		precheckShipModel.setCityManageCharge(cityManageCharge);
		precheckShipModel.setCityManageDirector(cityManageDirector);
		precheckShipModel.setCityCompany(cityCompany);
		return precheckShipModel;
	}
	
	/**
	 * 已处理工单条数
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		return netResInspectPhotoJDBCDao.getHaveProcessCount(processDefinitionKey,
				userId,conditionQueryModel);
	}
	
	/**
	 * 已处理工单明细
	 */
	public List<RoomDemolitionModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return netResInspectPhotoJDBCDao.getHaveProcessList(processDefinitionKey,
				userId,conditionQueryModel,
				firstResult, endResult, pageSize);
	}

	
	
	/**
	 * 由我创建的工单条数
	 * 
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return netResInspectPhotoJDBCDao.getByCreatingWorkOrderCount(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);
	}
	
	
	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<TaskModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {
		return netResInspectPhotoJDBCDao.getByCreatingWorkOrderList(processDefinitionKey,
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
				firstResult, endResult, pageSize);
	}

	@Override
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation) {
		return netResInspectPhotoJDBCDao.getPrecheckPhotoes(userId,photoDescribe,createTime,photoCreate,faultLocation);
	}

	@Override
	public String getPhotoPlth(String id) {
		// String plth = pnrTransferNewPrecheckDao.getPhotoPlth(id);
		 String plth = "wyphotoTest/qq.jpg";
		 String name= "";
		 if(plth!=null && !"".equals(plth)){
			 String[] names = plth.split("/");
			 name = names[names.length-1];
		 }
		return name;
	}
	
	
	
	@Override
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(
			String processInstanceId) {
		return netResInspectPhotoJDBCDao.showPhotoByProcessInstanceId(processInstanceId);
	}

	
	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		// TODO Auto-generated method stub
		return netResInspectPhotoJDBCDao.getOnePnrAndroidPhotoFileById(id);
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
		return netResInspectPhotoJDBCDao.getBackSheetCount(
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
		return netResInspectPhotoJDBCDao.getBackSheetList(processDefinitionKey,
				userId, conditionQueryModel, firstResult, endResult, pageSize);
	}

	@Override
	public void judgeIsExitBypgohoIdAndProcessInstanceId(
			String processInstanceId) {
		netResInspectPhotoJDBCDao.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
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
			String wsTitle, String status,String themeInterface){
		return netResInspectPhotoJDBCDao.getArchivedCount( processDefinitionKey,  userId,
				 sendStartTime,  sendEndTime,  wsNum,
				 wsTitle,  status,themeInterface);
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
			String wsNum, String wsTitle, String status,String themeInterface, int firstResult,
			int endResult, int pageSize){
		return netResInspectPhotoJDBCDao.getArchivedList( processDefinitionKey,
				 userId,  sendStartTime,  sendEndTime,
				 wsNum,  wsTitle,  status,themeInterface,  firstResult,
				 endResult,  pageSize);
	}
	
	@Override
	public void setStateByPhotoId(String photoId, String state) {
		netResInspectPhotoJDBCDao.setStateByphotoId(photoId, state);
		
	}
	
	@Override
	public String[] getPhotoByProcessInstanceId(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return netResInspectPhotoJDBCDao.getPhotoByProcessInstanceId(processInstanceId);
	}
	
	/**
	 * 生成工单编号
	 * @param processKey
	 * @param initator
	 * @param type
	 * @param shortName
	 * @return
	 */
	public String getSheetId(String processKey,String initator,String type,String shortName){
		String sheetId = "";
		int maxNumberValue =0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();		
		
		maxNumberValue = pnrTransferOfficeDaoJDBC.getCurrDateSheetCountNum(processKey);
		
    	int instr =initator.lastIndexOf("-");
    	if(instr > 0){
    		
    		sheetId =initator.substring(0,instr)+"-"+shortName;
    	}else{
    		sheetId ="SD"+"-"+shortName;//没有找到账号的前缀
    	}    	
    	sheetId = sheetId.toUpperCase();
    	
    	sheetId =sheetId+"-"+dateFormat.format(date)+"-"+maxNumberValue;
		
		return sheetId;
	
	}
	
	/**
	 * 根据机房ID删除机房
	 * @param id
	 */
	public String deleteRoomResourceById(String id){
		return netResInspectPhotoJDBCDao.deleteRoomResourceById(id);
	}
	
	public List excelExportToProcess(Search search, String userId,String deptId,String queryFlag,String processKey,String flag,HttpServletRequest request){
		System.out.println("---------进新建的导出方法了吗");
		
		System.out.println("----------userId="+userId);
		System.out.println("----------queryFlag="+queryFlag);
		System.out.println("----------processKey="+processKey);
		System.out.println("----------flag="+flag);
		
		List<RoomDemolitionModel> list =null;
		if("listBacklog".equals(queryFlag)){	//待回复列表
			 list =  netResInspectPhotoJDBCDao.getTransferNewPrecheckList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0, 1,
					65000);
		}else if("waitWorkOrderList".equals(queryFlag)){//待办工单
			list =  netResInspectPhotoJDBCDao.getTransferNewPrecheckList(userId,
					flag, processKey, this.getConditionQueryModel(request), 0, 1,
					65000);
		}else if("getQueryWorkOrder".equals(queryFlag)){
			list = 	netResInspectPhotoJDBCDao.getQueryWorkOrderList(this.getUserAreaid(request),"",processKey,this.getConditionQueryModel(request),0,1,65000);
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
		String mainSceneId = request.getParameter("mainSceneId");//场景
	
		System.out.println("----------sendStartTime="+sendStartTime);
		System.out.println("----------sendEndTime="+sendEndTime);
		System.out.println("----------wsNum="+wsNum);
		System.out.println("----------wsTitle="+wsTitle);
		System.out.println("----------status="+status);
		System.out.println("----------region="+region);
		System.out.println("----------country="+country);
		
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setMainSceneId(mainSceneId);
		
		return conditionQueryModel;
	}
	
	//获取当前登陆人地区id
	public String getUserAreaid(HttpServletRequest request){
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		return areaid;
		
	}
	
	@Override
	public List<RoomDemolitionModel> getQueryWorkOrderList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<RoomDemolitionModel> list =  netResInspectPhotoJDBCDao.getQueryWorkOrderList(userid,
				flag, processKey, conditionQueryModel, firstResult, endResult,
				pageSize);
		return list;
	}
	
	@Override
	public int getQueryWorkOrderCount(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		return netResInspectPhotoJDBCDao.getQueryWorkOrderCount(areaid,
				flag, processKey, conditionQueryModel);
	}
	
}
