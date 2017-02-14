package com.boco.activiti.partner.process.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.boco.activiti.partner.process.dao.INetResInspectDao;
import com.boco.activiti.partner.process.dao.INetResInspectInfoDao;
import com.boco.activiti.partner.process.dao.INetResInspectJDBCDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeJDBCDao;
import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.NetResInspectHandle;
import com.boco.activiti.partner.process.model.NetResInspectParam;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.OptionUtil;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.NetResInspectModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.INetResInspectDraftService;
import com.boco.activiti.partner.process.service.INetResInspectHandleService;
import com.boco.activiti.partner.process.service.INetResInspectService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.webapp.action.NetResInspectAction;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * 
   * @author wangyue
   * @ClassName: PnrTransferNewPrecheckServiceImpl
   * @Version 版本 
   * @Copyright boco
   * @date Feb 4, 2015 10:19:10 AM
   * @description 新版预检预修工单--service接口实现
 */
public class NetResInspectServiceImpl extends
		CommonGenericServiceImpl<NetResInspect> implements
		INetResInspectService {
	
	private INetResInspectJDBCDao netResInspectJDBCDao;

	private INetResInspectDao netResInspectDao;

	private HistoryService historyService;
	
	private IPnrTransferOfficeJDBCDao pnrTransferOfficeDaoJDBC;
	
	private INetResInspectHandleService netResInspectHandleService;
	
	private FormService formService;
	
	private TaskService taskService;
	
	private IdentityService identityService;
	
	private RuntimeService runtimeService;
	
	private INetResInspectDraftService netResInspectDraftService;
	
	private IProcessTaskService processTaskService;
	
	private INetResInspectInfoDao netResInspectInfoDao;
	

	public INetResInspectInfoDao getNetResInspectInfoDao() {
		return netResInspectInfoDao;
	}

	public void setNetResInspectInfoDao(INetResInspectInfoDao netResInspectInfoDao) {
		this.netResInspectInfoDao = netResInspectInfoDao;
	}

	public INetResInspectJDBCDao getNetResInspectJDBCDao() {
		return netResInspectJDBCDao;
	}

	public void setNetResInspectJDBCDao(INetResInspectJDBCDao netResInspectJDBCDao) {
		this.netResInspectJDBCDao = netResInspectJDBCDao;
	}

	public INetResInspectDao getNetResInspectDao() {
		return netResInspectDao;
	}

	public void setNetResInspectDao(INetResInspectDao netResInspectDao) {
		this.netResInspectDao = netResInspectDao;
		this.setCommonGenericDao(netResInspectDao);
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

	public INetResInspectHandleService getNetResInspectHandleService() {
		return netResInspectHandleService;
	}

	public void setNetResInspectHandleService(
			INetResInspectHandleService netResInspectHandleService) {
		this.netResInspectHandleService = netResInspectHandleService;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public INetResInspectDraftService getNetResInspectDraftService() {
		return netResInspectDraftService;
	}

	public void setNetResInspectDraftService(
			INetResInspectDraftService netResInspectDraftService) {
		this.netResInspectDraftService = netResInspectDraftService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public IProcessTaskService getProcessTaskService() {
		return processTaskService;
	}

	public void setProcessTaskService(IProcessTaskService processTaskService) {
		this.processTaskService = processTaskService;
	}

	/******************************************实现方法区域*************************************************/

	@Override
	public int getNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		return netResInspectJDBCDao.getTransferNewPrecheckCount(userid,
				flag, processKey, conditionQueryModel);
	}

	@Override
	public List<NetResInspectModel> getTransferNewPrecheckList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<NetResInspectModel> list =  netResInspectJDBCDao.getTransferNewPrecheckList(userid,flag, processKey, conditionQueryModel,
				firstResult, endResult,pageSize);
		return list;
	}

	@Override
	public PrecheckShipModel getPrecheckShipModelBycountryCharge(
			String countryCharge) {
		List<Map> list = netResInspectJDBCDao.getPrecheckShipModelBycountryCharge(countryCharge);
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
		return netResInspectJDBCDao.getHaveProcessCount(processDefinitionKey,
				userId,conditionQueryModel);
	}
	
	/**
	 * 已处理工单明细
	 */
	public List<NetResInspectModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return netResInspectJDBCDao.getHaveProcessList(processDefinitionKey,
				userId,conditionQueryModel,
				firstResult, endResult, pageSize);
	}

	
	
	/**
	 * 由我创建的工单条数
	 * 
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,ConditionQueryModel conditionQueryModel) {
		return netResInspectJDBCDao.getByCreatingWorkOrderCount(processDefinitionKey,userId,conditionQueryModel);
	}
	
	
	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<NetResInspectModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		return netResInspectJDBCDao.getByCreatingWorkOrderList(processDefinitionKey,userId,conditionQueryModel,firstResult,
				endResult, pageSize);
	}

	@Override
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation) {
		return netResInspectJDBCDao.getPrecheckPhotoes(userId,photoDescribe,createTime,photoCreate,faultLocation);
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
		return netResInspectJDBCDao.showPhotoByProcessInstanceId(processInstanceId);
	}

	
	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		// TODO Auto-generated method stub
		return netResInspectJDBCDao.getOnePnrAndroidPhotoFileById(id);
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
		return netResInspectJDBCDao.getBackSheetCount(
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
		return netResInspectJDBCDao.getBackSheetList(processDefinitionKey,
				userId, conditionQueryModel, firstResult, endResult, pageSize);
	}

	@Override
	public void judgeIsExitBypgohoIdAndProcessInstanceId(
			String processInstanceId) {
		netResInspectJDBCDao.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
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
		return netResInspectJDBCDao.getArchivedCount( processDefinitionKey,  userId,
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
		return netResInspectJDBCDao.getArchivedList( processDefinitionKey,
				 userId,  sendStartTime,  sendEndTime,
				 wsNum,  wsTitle,  status,themeInterface,  firstResult,
				 endResult,  pageSize);
	}
	
	@Override
	public void setStateByPhotoId(String photoId, String state) {
		netResInspectJDBCDao.setStateByphotoId(photoId, state);
		
	}
	
	@Override
	public String[] getPhotoByProcessInstanceId(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return netResInspectJDBCDao.getPhotoByProcessInstanceId(processInstanceId);
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
		return netResInspectJDBCDao.deleteRoomResourceById(id);
	}
	
//	public List excelExportToProcess(Search search, String userId,String deptId,String queryFlag,String processKey,String flag,HttpServletRequest request){
//		System.out.println("---------进新建的导出方法了吗");
//		
//		System.out.println("----------userId="+userId);
//		System.out.println("----------queryFlag="+queryFlag);
//		System.out.println("----------processKey="+processKey);
//		System.out.println("----------flag="+flag);
//		
//		List<RoomDemolitionModel> list =null;
//		if("listBacklog".equals(queryFlag)){	//待回复列表
//			 list =  netResInspectJDBCDao.getTransferNewPrecheckList(userId,
//					flag, processKey, this.getConditionQueryModel(request), 0, 1,
//					65000);
//		}else if("waitWorkOrderList".equals(queryFlag)){//待办工单
//			list =  netResInspectJDBCDao.getTransferNewPrecheckList(userId,
//					flag, processKey, this.getConditionQueryModel(request), 0, 1,
//					65000);
//		}else if("getQueryWorkOrder".equals(queryFlag)){
//			list = 	netResInspectJDBCDao.getQueryWorkOrderList(this.getUserAreaid(request),"",processKey,this.getConditionQueryModel(request),0,1,65000);
//		}
//		System.out.println("-----------list长度="+list.size());
//		
//		return list;
//	}
	
	/**
	 * 通过request请求获取查询条件
	 * 
	 * @param request
	 * @return
	 */
	private ConditionQueryModel getConditionQueryModel(HttpServletRequest request){
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));//派单开始时间
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));//派单结束时间
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));//工单号
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));//工单名称
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		
		String resourceType = StaticMethod.nullObject2String(request.getParameter("resourceType"));//资源类型
		String questionType = StaticMethod.nullObject2String(request.getParameter("questionType"));//问题类型
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));//区专业

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setResourceType(resourceType);
		conditionQueryModel.setQuestionType(questionType);
		conditionQueryModel.setSpecialty(specialty);
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
	public List<NetResInspectModel> getQueryWorkOrderList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		List<NetResInspectModel> list =  netResInspectJDBCDao.getQueryWorkOrderList(userid,
				flag, processKey, conditionQueryModel, firstResult, endResult,
				pageSize);
		return list;
	}
	
	@Override
	public int getQueryWorkOrderCount(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		return netResInspectJDBCDao.getQueryWorkOrderCount(areaid,
				flag, processKey, conditionQueryModel);
	}
	
	/**
	 * 	 根据区县和专业获取现场核实人
	 	 * @author WANGJUN
	 	 * @title: getChecker
	 	 * @date Jun 21, 2016 9:01:39 AM
	 	 * @param county
	 	 * @param specialty
	 	 * @return String
	 */
	public String getChecker(String county,String specialty){
		String checker = netResInspectJDBCDao.getChecker(county,specialty);
		if("".equals(checker)){
			checker = "superUser"; //未找到人，默认superUser
		}
		return checker;
	}
	
	/**
	 * 	 自动派发子流程
	 	 * @author WANGJUN
	 	 * @title: autoSendSubProcess
	 	 * @date Jun 23, 2016 2:20:48 PM
	 	 * @param netResInspect 工单信息
	 	 * @param type	子流程类型
	 	 * @return String
	 */
	public Map<String,String> autoSendSubProcess(NetResInspect netResInspect,String type){
		Map<String,String> resultMap = null;
		if("interface".equals(type)){ //本地网
			resultMap = autoSendTransferNewPrechechProcess(netResInspect);
		}else if("transferOffice".equals(type)){ //抢修
			resultMap =autoSendMyTransferProcess(netResInspect);
		}else{
			resultMap = new HashMap<String,String>();
			resultMap.put("result", "error");
			resultMap.put("resultMsg", "没有指定要派发的子流程的类型！");
		}
		return resultMap;
	}
	
	/**
	 * 	 自动派发本地网预检预修
	 	 * @author WANGJUN
	 	 * @title: autoSendTransferNewPrechechProcess
	 	 * @date Jun 23, 2016 2:32:24 PM
	 	 * @param netResInspect
	 	 * @return Map<String,String>
	 */
	private Map<String,String> autoSendTransferNewPrechechProcess(NetResInspect netResInspect){
		Map<String,String> resultMap = null;
		if(netResInspect != null){
//			1、根据上报资源的区县获取到对应的本地网工单发起人账号
			//String initiator = "superUser";
			String initiator = netResInspectJDBCDao.getSubProcessInitiator(netResInspect.getCounty(),"interface");
			if(initiator != null && !"".equals(initiator)){
				//操作人部门（根据操作人来，如果系统没存操作人部门则空着）
				TawSystemUser initiatorUser = com.boco.eoms.partner.process.util.CommonUtils.getTawSystemUserByUserId(initiator, "");
				if(initiatorUser != null){
					//工单主题：巡检众筹转派-【地市】-【资源名称】-【问题类型】-年月日
					String theme = createSubProcessTitle(netResInspect);
					String faultDescription = createFaultDescription(netResInspect); 
					//资源地址
					String locatedAddress = netResInspect.getLocatedAddress();
					NetResInspectDraft model = new NetResInspectDraft();
					model.setTheme(theme);
					model.setFaultSource("巡检众筹");
					model.setFaultDescription(faultDescription);
					model.setLocatedAddress(locatedAddress);
					model.setInitiator(initiator);
					model.setCreateTime(netResInspect.getReportedDate());
					model.setCity(netResInspect.getCity());
					model.setCountry(netResInspect.getCounty());
					model.setDegree(netResInspect.getDegree());
					model.setImportance(netResInspect.getImportance());
					
					//3.派发本地网流程
				    identityService.setAuthenticatedUserId(initiator);
				    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("transferNewPrechechProcess");
				    String processInstanceId = processInstance.getId();
				    
				    List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
					String taskId = taskList.get(0).getId();
					
					model.setProcessInstanceId(processInstanceId); //工单号
					model.setTaskId(taskId); //任务ID
					model.setProcessType("interface"); //流程类型：本地网 预检预修
					model.setInspectProcessInstanceId(netResInspect.getProcessInstanceId()); //众筹流程ID(父ID)
					netResInspectDraftService.save(model);
					
					List<PnrAndroidPhotoFile> pnrAndroidPhotoFileList=netResInspectInfoDao.serchAndroidPhoto(netResInspect.getProcessInstanceId());
					netResInspectInfoDao.savePhoneSubFlow(pnrAndroidPhotoFileList,processInstanceId);
					
					//回传
					resultMap = new HashMap<String,String>();
					resultMap.put("result", "success");
					resultMap.put("resultMsg", "");	
					resultMap.put("initiator", initiator);
					resultMap.put("processInstanceId", processInstanceId);	
					
					//发短信
		    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
						+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
						+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。";
		    		CommonUtils.sendMsg(msg, initiator);
					
				}else{
					resultMap = createErrorResultInfor("error","在系统中不存在用户："+initiator+"的信息");
				}
			}else{
				resultMap = createErrorResultInfor("error","在数据库中未找到预检预修工单的发起人账号，请联系管理员进行配置");
			}
		}else{
			resultMap = createErrorResultInfor("error","autoSendTransferNewPrechechProcess中为获取到实体信息");
		}
		return resultMap;
	}
	
	/**
	 * 	 自动派发抢修
	 	 * @author WANGJUN
	 	 * @title: autoSendTransferNewPrechechProcess
	 	 * @date Jun 23, 2016 2:32:24 PM
	 	 * @param netResInspect
	 	 * @return Map<String,String>
	 */
	private Map<String,String> autoSendMyTransferProcess(NetResInspect netResInspect){
		Map<String,String> resultMap = null;
		if(netResInspect != null){
//			1、根据上报资源的区县获取到对应的抢修工单发起人账号
			//String initiator = "superUser";
			String initiator = netResInspectJDBCDao.getSubProcessInitiator(netResInspect.getCounty(),"transferOffice");
			if(initiator != null && !"".equals(initiator)){
				//2、补充抢修新建派发界面的字段
				//操作人部门（根据操作人来，如果系统没存操作人部门则空着）
				TawSystemUser initiatorUser = com.boco.eoms.partner.process.util.CommonUtils.getTawSystemUserByUserId(initiator, "");
				if(initiatorUser != null){
//					工单主题：巡检众筹转派-【地市】-【资源名称】-【问题类型】-年月日
					String theme = createSubProcessTitle(netResInspect);
					String faultDescription = createFaultDescription(netResInspect); 
				
//					操作人（维护中心人）
					String initiatorName = initiatorUser.getUsername();
					String initiatorMobile = initiatorUser.getMobile();
					String initiatorDept = initiatorUser.getDeptid();
					
//					故障处理时限（24h，可修改）
					
//					故障来源（巡检众筹）
//					故障发生时间（上报日期）
//					工单子类型（空着）
//					故障联系人+手机号（维护中心人员）
//					故障描述（资源类型-资源地址-资源名称-问题类型-描述）
					NetResInspectDraft model = new NetResInspectDraft();
					model.setTheme(theme);
					model.setInitiator(initiator);
					model.setInitiatorDept(initiatorDept);
					model.setProcessLimit(Double.parseDouble("24")); //默认24h，可以修改
					model.setFaultSource("巡检众筹");
					model.setCreateTime(netResInspect.getReportedDate());
					model.setConnectPerson(initiatorName+initiatorMobile);
					model.setFaultDescription(faultDescription);
					
					//3.派发抢修流程
				    identityService.setAuthenticatedUserId(initiator);
				    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myTransferProcess");
				    String processInstanceId = processInstance.getId();
				    
				    List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
					String taskId = taskList.get(0).getId();
					
					model.setProcessInstanceId(processInstanceId); //工单号
					model.setTaskId(taskId); //任务ID
					model.setProcessType("transferOffice"); //流程类型：抢修
					model.setInspectProcessInstanceId(netResInspect.getProcessInstanceId()); //众筹流程ID(父ID)
					netResInspectDraftService.save(model);
					
					List<PnrAndroidPhotoFile> pnrAndroidPhotoFileList=netResInspectInfoDao.serchAndroidPhoto(netResInspect.getProcessInstanceId());
					netResInspectInfoDao.savePhoneSubFlow(pnrAndroidPhotoFileList,processInstanceId);
					
					//回传
					resultMap = new HashMap<String,String>();
					resultMap.put("result", "success");
					resultMap.put("resultMsg", "");	
					resultMap.put("initiator", initiator);
					resultMap.put("processInstanceId", processInstanceId);	
					
//					3、附件（照片作为附件自动转派）
					
					//发短信
		    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
						+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
						+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。";
		    		CommonUtils.sendMsg(msg, initiator);
				}else{
					resultMap = createErrorResultInfor("error","在系统中不存在用户："+initiator+"的信息");
				}
			}else{
				resultMap = createErrorResultInfor("error","在数据库中未找到抢修工单的发起人账号，请联系管理员进行配置");
			}
		}else{
			resultMap = createErrorResultInfor("error","autoSendMyTransferProcess中为获取到实体信息");
		}
		return resultMap;
	}
	
	/**
	 *   返回错误信息 
	 	 * @author WANGJUN
	 	 * @title: createErrorResultInfor
	 	 * @date Jun 28, 2016 10:25:38 AM
	 	 * @param flag
	 	 * @param msg
	 	 * @return Map<String,String>
	 */
	private Map<String,String> createErrorResultInfor(String flag,String msg){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("result", flag);
		resultMap.put("resultMsg", msg);	
		return resultMap;
	}
	
	/**
	 * 	 生成故障描述
	 	 * @author WANGJUN
	 	 * @title: createFaultDescription
	 	 * @date Jun 27, 2016 3:16:44 PM
	 	 * @param netResInspect
	 	 * @return String
	 */
	private String createFaultDescription(NetResInspect netResInspect){
		//故障描述（资源类型-资源地址-资源名称-问题类型-描述）
		String faultDescription = "";
		//第一位：资源类型
		String resourceTypeName = CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",");
		
		//第二位：资源地址
		String locatedAddress ="";
		//地市
		String cityId = netResInspect.getCity();
		String cityName = CommonUtils.getId2NameString(cityId, 2, ",");
		if("".equals(cityName)){
			cityName = "未知";
		}
		//区县
		String countyId = netResInspect.getCounty();
		String countyName = CommonUtils.getId2NameString(countyId, 2, ",");
		if("".equals(countyName)){
			countyName = "未知";
		}
		//街道
		String street = StaticMethod.nullObject2String(netResInspect.getStreet());
		if("".equals(street)){
			street = "未知";
		}
		locatedAddress=cityName+countyName+street;
		//String locatedAddress = netResInspect.getLocatedAddress();
		
		//第三位：资源名称
		String resourceName =  netResInspect.getResourceName();
		
		//第四位：问题类型
		String questionTypeName = CommonUtils.getId2NameString(netResInspect.getQuestionType(), 1, ",");
		
		//第五位：描述
		String reportedDescribe = netResInspect.getReportedDescribe();
		
		faultDescription = resourceTypeName+"-"+locatedAddress+"-"+resourceName+"-"+questionTypeName+"-"+reportedDescribe;
		
		return faultDescription;
	}
	
	/**
	 * 	 生成工单主题
	 	 * @author WANGJUN
	 	 * @title: createSubProcessTitle
	 	 * @date Jun 27, 2016 3:17:05 PM
	 	 * @param netResInspect
	 	 * @return String
	 */
	private String createSubProcessTitle(NetResInspect netResInspect){
		//工单主题：巡检众筹转派-【地市】-【资源名称】-【问题类型】-年月日
		String title = "";
		//第一位
		String title1 = "巡检众筹转派";
		//第二位：地市
		String cityId = netResInspect.getCity();
		String title2 = CommonUtils.getId2NameString(cityId, 2, ",");
		if("".equals(title2)){
			title2 = "未知";
		}
		//第三位：资源名称
		String title3 = netResInspect.getResourceName();
		if("".equals(title3)){
			title3 = "未知";
		}
		//第四位：问题类型
		String questionType = netResInspect.getQuestionType();
		String title4 = CommonUtils.getId2NameString(questionType, 1, ",");
		if("".equals(title2)){
			title4 = "未知";
		}
		//第五位：
		DateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String title5 = sFormat.format(new Date());
		
		title = title1 +"-"+title2+"-"+title3+"-"+title4+"-"+title5;
		
		return title;
	}
	
	/**
	 * 	 判断下一步流程走向
	 	 * @author WANGJUN
	 	 * @title: judgmentFlowTrend
	 	 * @date Jun 24, 2016 3:29:58 PM
	 	 * @param isHiddenDanger 专业
	 	 * @param isHiddenDanger 是否隐患
	 	 * @param isBuild 是否建设
	 	 * @param isLineHiddenDanger 是否线路隐患
	 	 * @param autoSendProcess 派发流程
	 	 * @return String
	 */
	public String judgmentFlowTrend(String specialty,String isHiddenDanger,String isBuild,String isLineHiddenDanger,String autoSendProcess){
		String msg = "";
		if("1".equals(isHiddenDanger)){ //是隐患
			if("1280103".equals(specialty)){ //是无线专业
				if("1".equals(isBuild)){ //转建设
					//先到建设环节，然后归档
					msg = "buildArchive";
				}else if("0".equals(isBuild)){ //不转建设
					//派发到工单处理环节
					msg = "treated";
				}else{
					msg = "是否建设枚举值异常！";
				}
			}else if("1280101".equals(specialty)){ // 设备专业
				msg = "treated";
			}else if("1280102".equals(specialty)){//线路专业
				if("1".equals(autoSendProcess)){ //派发本地网
					msg = "interface";
				}else if("0".equals(autoSendProcess)){ //派发抢修
					msg = "transferOffice";
				}else{
					msg = "派发的子流程异常！";
				}
				
			}else{
				msg = "专业枚举值异常！";
			}
		}else if("0".equals(isHiddenDanger)){ //不是隐患
			//直接归档
			msg = "archive";
		}else{
			msg = "是否隐患枚举值异常！";
		}
		return msg;
	}
	
	/**
	 * 	 现场核实环节处理方法	 
	 	 * @author WANGJUN
	 	 * @title: siteCheckSubmit
	 	 * @date Jun 27, 2016 10:11:13 AM
	 	 * @param param
	 	 * @return String
	 */
	public String siteCheckSubmit(NetResInspectParam param){
		String processInstanceId = param.getProcessInstanceId(); //流程号
		String errorMsg = "";
		//2.派发子流程
		//	String processInstanceId = "375653"; //工单流水号
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		SearchResult t = this.searchAndCount(search);
		List<NetResInspect> list = t.getResult();
		if(list !=null && list.size() > 0){
			NetResInspect netResInspect =list.get(0);
			if(netResInspect != null){
				//获取action传过来的参数
				String userId = param.getUserId();
				String taskId = param.getTaskId();//任务id
				String resourceName = param.getResourceName();//资源名称
				String isOurResources = param.getIsOurResources();//是否我方资源
				String isSiteOperation = param.getIsSiteOperation();//是否现场施工作业
				String degree = param.getDegree();//紧急程度
				String validity = param.getValidity();//有效性
				String importance = param.getImportance();//重要程度
				String isHiddenDanger = param.getIsHiddenDanger();//是否隐患
				String isBuild = param.getIsBuild();//是否建设
				String isLineHiddenDanger = param.getIsLineHiddenDanger();//是否线路隐患
				String autoSendProcess = param.getAutoSendProcess();//派发流程
				String siteCheckRemark = param.getSiteCheckRemark();//描述
				String specialty = param.getSpecialty();//专业
				
				//1.主表信息
				netResInspect.setResourceName(resourceName);
				netResInspect.setIsOurResources(isOurResources);
				netResInspect.setIsSiteOperation(isSiteOperation);
				netResInspect.setDegree(degree);
				netResInspect.setValidity(validity);
				netResInspect.setImportance(importance);
				
				if("0".equals(isHiddenDanger)){
					netResInspect.setIsHiddenDanger(isHiddenDanger);
				}else if("1".equals(isHiddenDanger)){
					netResInspect.setIsHiddenDanger(isHiddenDanger);
					if("1280102".equals(specialty)){//线路专业
						netResInspect.setAutoSendProcess(autoSendProcess);
					}else if("1280103".equals(specialty)){//无线专业
						netResInspect.setIsBuild(isBuild);
					}
				}
				//netResInspect.setIsHiddenDanger(isHiddenDanger);
				//netResInspect.setIsLineHiddenDanger(isLineHiddenDanger);
				//netResInspect.setAutoSendProcess(autoSendProcess);
				netResInspect.setSiteCheckRemark(siteCheckRemark);
				
				//2.流转表信息
				NetResInspectHandle handle = new NetResInspectHandle();
				handle.setProcessInstanceId(processInstanceId);//工单号
				handle.setCheckTime(new Date());//审批时间
				handle.setUserId(userId);//审批人
				handle.setLinkFlag("siteCheck");//环节标识
				handle.setRemark(siteCheckRemark);//备注（处理描述）
				handle.setOperationFlag("web");//手机端、web端操作标识
				handle.setTaskId(taskId);//任务ID
				
				//3.流程跳转
				Map<String, String> map = null ;
				String nextPerson = ""; //下一个环节处理人
				String nextHandle = this.judgmentFlowTrend(specialty,isHiddenDanger,isBuild,isLineHiddenDanger,autoSendProcess);
				if("buildArchive".equals(nextHandle)){ //自动转建设流程后归档
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("siteCheckState", "build");
					map2.put("buildPerson", "superUser");
					formService.submitTaskFormData(taskId, map2); //派发到建设流程
					
					//取到建设环节的任务ID
					List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
					String buildTaskId = taskList.get(0).getId();
					if(!"".equals(buildTaskId)&&!taskId.equals(buildTaskId)){
						taskId = buildTaskId;
					}else{
						errorMsg = "转建设环节异常！";
					}
					map = new HashMap<String, String>(); //归档的参数
					//发短信
		    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
						+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
						+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。(处理完毕并归档)";
		    		CommonUtils.sendMsg(msg, netResInspect.getReportedUserId());
				}else if("treated".equals(nextHandle)){ //派发到工单处理
					map = new HashMap<String, String>();
					map.put("siteCheckState", "treatment");
					map.put("handlePerson",userId);
					nextPerson = userId;
					netResInspect.setHandlePerson(userId); //工单处理环节的人（即隐患处理环节的人）
					//发短信
		    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
						+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
						+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。";
		    		CommonUtils.sendMsg(msg, userId);
				}else if("archive".equals(nextHandle)){ //直接归档
					map = new HashMap<String, String>();
					map.put("siteCheckState", "archive");
					//发短信
		    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
						+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
						+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。(非隐患已归档)";
		    		CommonUtils.sendMsg(msg, netResInspect.getReportedUserId());
				}else if("interface".equals(nextHandle)||"transferOffice".equals(nextHandle)){ //派发本地网或者抢修
					Map<String, String> autoSendSubProcess = this.autoSendSubProcess(netResInspect, nextHandle);
					String result = autoSendSubProcess.get("result");
					if(!"success".equals(result)){
						errorMsg = autoSendSubProcess.get("resultMsg");
					}else{
						String initiator = autoSendSubProcess.get("initiator");
						if("".equals(initiator)){
							initiator = "superUser";
						}
						map = new HashMap<String, String>();
						map.put("siteCheckState", "line");
						map.put("linePerson",initiator);
						nextPerson = initiator;
						String subProcessInstanceId = autoSendSubProcess.get("processInstanceId");
						if(subProcessInstanceId !=null && !"".equals(subProcessInstanceId)){
							//设置子工单号
							netResInspect.setSubProcessInstanceId(subProcessInstanceId);
							netResInspect.setSubSendUserId(initiator);
						}else{
							errorMsg = "未找到派发的子流程的工单号";
						}
					}
				}else{
					errorMsg = nextHandle;
				}
				
				if("".equals(errorMsg)){
					netResInspectHandleService.save(handle);
					try{
						formService.submitTaskFormData(taskId, map);
					}catch(Exception e){
						e.printStackTrace();
					}
					
					if("buildArchive".equals(nextHandle)||"archive".equals(nextHandle)){ //直接归档
						processTaskService.setTvalueOfTask(processInstanceId, "", netResInspect, NetResInspect.class, "archive", "已归档",true);
						netResInspect.setState(5);
						netResInspect.setArchiveTime(new Date());
					}else{
						processTaskService.setTvalueOfTask(processInstanceId, nextPerson, netResInspect, NetResInspect.class, null, null,false);
					}
					netResInspect.setSiteCheckDate(new Date());
					this.save(netResInspect);
				}
			}else{
				errorMsg = "工单号："+processInstanceId+"未找到！";
			}
		}else{
			errorMsg = "工单号："+processInstanceId+"不存在！";
		}
			return errorMsg;
	}
	/**
	 * 	  现场核实环节提交 
	 	 * @author zhoukeqing
	 	 * @title: worOrderProcessing
	 	 * @date Jun 27, 2016 10:11:13 AM
	 	 * @param param
	 	 * @return String
	 */
	public String worOrderProcessing(NetResInspectParam param) {
		String processInstanceId = param.getProcessInstanceId(); //流程号
		String errorMsg = "";
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		SearchResult t = this.searchAndCount(search);
		List<NetResInspect> list = t.getResult();
		if(list !=null && list.size() > 0){
			NetResInspect netResInspect =list.get(0);
			if(netResInspect != null){
				//获取action传过来的参数
				String userId = param.getUserId();
				String taskId = param.getTaskId();//任务id
				String handleDescribe=param.getHandleDescribe();
				String isFinish=param.getIsFinish();
				
				//2.流转表信息
				NetResInspectHandle handle = new NetResInspectHandle();
				handle.setProcessInstanceId(processInstanceId);//工单号
				handle.setCheckTime(new Date());//审批时间
				handle.setUserId(userId);//审批人
				handle.setLinkFlag("siteCheck");//环节标识
				handle.setRemark(handleDescribe);//备注（工单处理描述）
				handle.setOperationFlag("web");//手机端、web端操作标识
				handle.setTaskId(taskId);//任务ID
				
				//3.流程跳转
				Map<String, String> map =  new HashMap<String, String>();
				String nextPerson = ""; //下一个环节处理人
				String nextHandle = "archive";
				netResInspectHandleService.save(handle);
				formService.submitTaskFormData(taskId, map);
				processTaskService.setTvalueOfTask(processInstanceId, "", netResInspect, NetResInspect.class, "archive", "已归档",true);
				netResInspect.setHandleDescribe(handleDescribe);
				netResInspect.setIsFinish(isFinish);
				netResInspect.setState(5);
				netResInspect.setArchiveTime(new Date());
				
				this.save(netResInspect);
				
				//发短信
	    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
					+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
					+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "(已归档)。";
	    		CommonUtils.sendMsg(msg, netResInspect.getReportedUserId());
			}else{
				errorMsg = "工单号："+processInstanceId+"未找到！";
			}
		}else{
			errorMsg = "工单号："+processInstanceId+"不存在！";
		}
		return errorMsg;
	}
	/**
	 * 	  工单归档
	 	 * @author zhoukeqing
	 	 * @title: workOrderArchiving
	 	 * @date Jun 27, 2016 10:11:13 AM
	 	 * @param processInstanceId
	 	 * @return String
	 */
	public String workOrderArchiving(String processInstanceId,String userId) {
		String errorMsg = "";
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		SearchResult t = this.searchAndCount(search);
		List<NetResInspect> list = t.getResult();
		if(list !=null && list.size() > 0){
			NetResInspect netResInspect =list.get(0);
			if(netResInspect != null){
				//2.流转表信息
				NetResInspectHandle handle = new NetResInspectHandle();
				handle.setProcessInstanceId(processInstanceId);//工单号
				handle.setCheckTime(new Date());//审批时间
				handle.setUserId(userId);//审批人
				handle.setLinkFlag("siteCheck");//环节标识
				handle.setOperationFlag("web");//手机端、web端操作标识
				handle.setTaskId(netResInspect.getTaskId());//任务ID
				netResInspectHandleService.save(handle);
				//3.流程跳转
				Map<String, String> map =  new HashMap<String, String>();
				String nextPerson = ""; //下一个环节处理人
				String nextHandle = "archive";
				netResInspectHandleService.save(handle);
				formService.submitTaskFormData(netResInspect.getTaskId(), map);
				processTaskService.setTvalueOfTask(processInstanceId, "", netResInspect, NetResInspect.class, "archive", "已归档",true);
				netResInspect.setState(5);
				netResInspect.setArchiveTime(new Date());
				
				this.save(netResInspect);
				
				//发短信
	    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
					+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
					+ CommonUtils.getId2NameString(netResInspect.getResourceType(), 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "(处理完成并归档)。";
	    		CommonUtils.sendMsg(msg, netResInspect.getReportedUserId());
			}
		}
		return null;
	}
	/**
	 * 	 查询自动派发的子工单数量
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getSubCount(String userId,String processType,ConditionQueryModel conditionQueryModel){
		return netResInspectJDBCDao.getSubCount(userId,processType,conditionQueryModel);
	}
	
	/**
	 * 	 查询自动派发的子工单集合
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public List<TaskModel> getSubList(String userid,String processKey,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize){
		return netResInspectJDBCDao.getSubList(userid,processKey,conditionQueryModel,firstResult,endResult,pageSize);
	}
	/**
	 * 	 查询自动派发的子工单信息
	 	 * @author ZHOUKEQING
	 	 * @title: getNetResInspectDraft
	 	 * @date Jun 29, 2016 2:23:33 PM
	 	 * @param id
	 	 * @return NetResInspectDraft
	 */
	@Override
	public NetResInspectDraft getNetResInspectDraft(String processInstanceId) {
		return netResInspectJDBCDao.getNetResInspectDraft(processInstanceId);
	}

	@Override
	public void updateNetResInspectDraft(String netResInspectId) {
		netResInspectJDBCDao.updateNetResInspectDraft(netResInspectId);
	}

	
	public List excelExportToProcess(Search search, String userId,String deptId, String queryFlag, String processKey, String flag,HttpServletRequest request) {
		System.out.println("---------进新建的导出方法了吗");

		System.out.println("----------userId=" + userId);
		System.out.println("----------queryFlag=" + queryFlag);
		System.out.println("----------processKey=" + processKey);
		System.out.println("----------flag=" + flag);
		List<NetResInspectModel> list = null;
		if ("listBacklog".equals(queryFlag)) { // 待回复列表
			list = netResInspectJDBCDao.getTransferNewPrecheckList(userId,flag, processKey, this.getConditionQueryModel(request), 0,1, 65000);
		} 
		return list;
	}
	
	/**
	 * 	 现场核实环节 转派
	 	 * @author 你的名字
	 	 * @title: siteCheckTurnSend
	 	 * @date Jul 7, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public String siteCheckTurnSend(NetResInspectParam param){
		String errorMsg = "";
		String processInstanceId = param.getProcessInstanceId(); //流程号
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		SearchResult t = this.searchAndCount(search);
		List<NetResInspect> list = t.getResult();
		if(list !=null && list.size() > 0){
			NetResInspect netResInspect =list.get(0);
			if(netResInspect != null){
				String taskId=netResInspect.getTaskId();
				String oldSpecialty = netResInspect.getSpecialty(); //原来的专业
				String oldResourceType = netResInspect.getResourceType(); //原来的资源类型
				String oldChecker = netResInspect.getChecker(); //原来的现场核验人
				String oldCounty=netResInspect.getCounty();//原来的区县
				//根据转派后的资源类型，获取到专业类型
				String resourceType = param.getIsSendType(); //资源类型
				String specialty = CommonUtils.getDictRemark(resourceType); //专业
				String county = param.getCountry(); //区县(新)
				
				//根据专业取对应的现场核实人
				String checker = this.getChecker(county,specialty);//需求变更，人员先默认，后续再做逻辑
				
				//更新activiti表中的人
					//任务中对应的人
				Task taskEntity = taskService.createTaskQuery().taskId(taskId).singleResult();
	            taskEntity.setAssignee(checker);
	    		taskService.saveTask(taskEntity);
	    		
	    		NetResInspectTurnToSendModel netResInspectTurnToSendModel=new NetResInspectTurnToSendModel();
	    		netResInspectTurnToSendModel.setProcessInstanceId(processInstanceId);
	    		netResInspectTurnToSendModel.setOldCounty(oldCounty);
	    		netResInspectTurnToSendModel.setOldChecker(oldChecker);
	    		netResInspectTurnToSendModel.setOldResourceType(oldResourceType);
	    		netResInspectTurnToSendModel.setOldSpecialty(oldSpecialty);
	    		netResInspectTurnToSendModel.setChecker(checker);
	    		netResInspectTurnToSendModel.setCounty(county);
	    		netResInspectTurnToSendModel.setResourceType(resourceType);
	    		netResInspectTurnToSendModel.setSpecialty(specialty);
	    		netResInspectTurnToSendModel.setOperateTime(new Date());
	    		//try{
	    			netResInspectInfoDao.saveTurnToSendInfo(netResInspectTurnToSendModel);
	    		//}catch(Exception e){
	    			//e.printStackTrace();
	    		//}
	    		
	    		
				
				//更新主表中的人
					//专业
					//资源类型
					//区县 ？？
					//现场核实人
				netResInspect.setChecker(checker);
					//主表流程中的人
				netResInspect.setAssignee(checker);
				netResInspect.setResourceType(resourceType);
				netResInspect.setSpecialty(specialty);
				netResInspect.setCounty(county);
					//转派时间
				Date nowDate = new Date();
				netResInspect.setTurnSendDate(nowDate);
				try{
					this.save(netResInspect);
	    		}catch(Exception e){
	    			e.printStackTrace();
	    		}
				
				
				//保存流转信息
				
				//保存转派日志
					//改啥把新和旧的值都保存到日志中
				//发短信
	    		String msg = NetResInspectAction.TASK_MESSAGE + "  " + NetResInspectAction.TASK_NO_STR + processInstanceId
					+ "," + NetResInspectAction.TASK_TITLE_STR + netResInspect.getTheme() + "," + NetResInspectAction.TASK_RESOURCETYPE
					+ CommonUtils.getId2NameString(resourceType, 1, ",") + "," + NetResInspectAction.TASK_REPORTEDDESCRIBE + netResInspect.getReportedDescribe()+ "。";
	    		CommonUtils.sendMsg(msg, checker);
		
			}else{
				errorMsg = "工单号："+processInstanceId+"未被找到！";
			}
		}else{
			errorMsg = "工单号："+processInstanceId+"是不存在！";
		}
		return errorMsg;
	}

	@Override
	public List<OptionUtil> getCountrySelect(String city) {
		List<OptionUtil> optionList = netResInspectJDBCDao.getCountrySelect(city);
		return optionList;
	}
	/**
	 * 	 手机工单代办列表查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundo
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public List<NetResInspect> searchListsendundo(String userid,int pageSize,int pageIndex) {
		List<NetResInspect> netResInspectList = netResInspectInfoDao.searchListsendundo(userid,pageSize,pageIndex);
		return netResInspectList;
	}
	/**
	 * 	 手机工单代办列表总数查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundoTotal
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public int searchListsendundoTotal(String userid){
		int total = netResInspectInfoDao.searchListsendundoTotal(userid);
		return total;
	}
	/**
	 * 	 手机工单详情页面查询
	 	 * @author zhoukeqing
	 	 * @title: serchDetailAndroid
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public NetResInspect serchDetailAndroid(String processInstanceId) {
		NetResInspect netResInspect=netResInspectInfoDao.serchDetailAndroid(processInstanceId);
		return netResInspect;
	}
	/**
	 * 	 查询pnr_android_photo
	 	 * @author zhoukeqing
	 	 * @title: serchAndroidPhoto
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public List<PnrAndroidPhotoFile> serchAndroidPhoto(String processInstanceId){
		List<PnrAndroidPhotoFile> pnrAndroidPhotoFileList=netResInspectInfoDao.serchAndroidPhoto(processInstanceId);
		return pnrAndroidPhotoFileList;
	}
	/**
	 * 	 将手机拍摄的照片和流程关联
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public void saveFlowPhone(String processInstanceId,String type) {
		netResInspectInfoDao.saveFlowPhone(processInstanceId,type);
	}
	/**
	 * 	 将手机拍摄的照片和流程关联  新建工单用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneStarFlow(List<PnrAndroidPhotoFile> list,String processInstanceId){
		netResInspectInfoDao.savePhoneStarFlow(list,processInstanceId);
	}
	/**
	 * 巡检众筹流程--附件显示
	 */
	public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag)
			throws Exception {
		List<TawCommonsAccessoriesForm> list = netResInspectJDBCDao.getRoomDemolitionAccessoriesList(processInstanceId,flag);
		return list;
	}
}
