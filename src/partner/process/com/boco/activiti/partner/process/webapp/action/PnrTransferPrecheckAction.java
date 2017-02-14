package com.boco.activiti.partner.process.webapp.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrActReviewStaff;
import com.boco.activiti.partner.process.model.PnrActRuCountersign;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.PnrActReviewStaffService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Service.State;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrTransferPrecheckAction extends SheetAction {
	private static Logger logger = Logger.getLogger(PnrTransferPrecheckAction.class);

	private final String processDefinitionKey = "newTwoTransferPrecheck";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	// 发信息用的常量
	public final static String TASK_MESSAGE = "预检预修工单已派至您工位待处理：";
	public final static String TASK_WORKORDERDEGREE = "紧急程度：";
	public final static String TASK_WORKORDERTYPE = "类型：";
	public final static String TASK_SUBTYPE = "子类型：";
	public final static String TASK_NO_STR = "工单号:";
	public final static String TASK_TITLE_STR = "主题:";
	public final static String TASK_DEADSUBMIT_STR = "申请提交时间:";
	public final static String TASK_DEADLINE_STR = "截止时间:";
	public final static String TASK_CONTACT_STR = "联系人及电话:";
	//施工队
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	//市运维
	public final static String TASK_CITYMANAGEEXAMINE = "cityManageExamine";
	public final static String TASK_CITYMANAGEEXAMINE_STATUS = "市运维审批完毕";
	//市线路
	public final static String TASK_CITYLINEEXAMINE = "cityLineExamine";
	
	public final static String TASK_PROVINCEMANAGEEXAMINE = "provinceManageExamine";
	public final static String TASK_PROVINCEMANAGEEXAMINE_STATUS = "省运维审批完毕";
	private final static String TASK_TRANSFERCITYCJS = "csjCheck";
	private final static String TASK_TRANSFERCITYGS = "sgsCheck";
	private final static String TASK_PROVINCELINE = "provinceLineCheck";
	private final static String TASK_PROVINCEMANAGE = "provinceManageCheck";
	private PrecheckRoleModel precheckRoleModel=(PrecheckRoleModel)ApplicationContextHolder.getInstance().getBean("precheckRoleModel");
	/**
	 * 预检预修工单新增页面
	 * 
	 * @author wangyue
	 * @title: newTask
	 * @date Nov 10, 2014 11:23:43 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward newTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("new");
	}

	/**
	 * 工单发起
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();	
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		String taskAssignee = StaticMethod.nullObject2String(request
				.getParameter("taskAssignee"));
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		// 流程开始
		FormService formService = (FormService) getBean("formService");
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		TaskService taskService = (TaskService) getBean("taskService");
		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}

		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();

		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";//输出key-value用
		map.put("cityLineCheck", taskAssignee);
		tempKeyValue+="cityLineCheck"+":"+taskAssignee+";";
					
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:需求发起;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:需求发起;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		// 流程结束

		//发短信
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);		
		SearchResult t = pnrTransferOfficeService.searchAndCount(search);
		List<PnrTransferOffice> list = t.getResult();
		// 回复message
		PnrTransferOffice pnrTransferOffice =list.get(0);
		//短信接收人
		String messagePerson = pnrTransferOffice.getCountryCSJ();
		String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
		pnrTransferOffice.getWorkOrderDegree()+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderType()+","+TASK_SUBTYPE+pnrTransferOffice.getSubType()+"。";
		CommonUtils.sendMsg(msg, messagePerson);
		
		return mapping.findForward("performAddSuccess");		
	}
	
	/**
	 * 保存工单
	 */
	public ActionForward performSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
	
		//根据派发人的部门所属区域，确定工单的区域
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		String city = areaid;
		if((StaticMethod.nullObject2String(areaid)).length()>0 && areaid.length()>4){
			city = areaid.substring(0, 4);
		}
		
		// 默认为0，正常派单。
		int state = 0;
		// 是否调用接口标志
		String viewFlag = StaticMethod.nullObject2String(request
				.getParameter("viewFlag"));
		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		setTransferOfficeByRequest(request, pnrTransferOffice);
		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String workOrderDegree = pnrTransferOffice.getWorkOrderDegree();
		workOrderDegree = getDictNameByDictid(workOrderDegree);
		
		//设置工单的区域
		pnrTransferOffice.setCity(city);
		pnrTransferOffice.setCountry(areaid);
		
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");

		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		pnrTransferOffice.setThemeInterface("interface");

		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,"1");
		}
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);

		//判断是否调用接口
		if(viewFlag!=null && "newJob".equals(viewFlag) ){//属于新建页面，需要调用预算接口
			System.out.println("需要调用预算接口！！！！！！！！");
			return mapping.findForward("success");
		}else{
			return listBacklog(mapping, form, request, response);
		}
	}
	
	/**
	 * 派到会审--countersign 15-01-29更改会审方式，不再走流程
	 */
	public ActionForward countersign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {		
		// 确定会审人员--start
		String userStrs ="";
		String city =  request.getParameter("city");		
		PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) getBean("pnrActReviewStaffService");
		String whereStr = " where 1=1";
		// 组装查询条件
		if (city != null && !city.equals("")) {
			whereStr += " and cityId = '"+city+"'";
		}
		
		Map mapList = (Map) pnrActReviewStaffService.queryPnrActReviewStaff(
				0, 1, whereStr);
		List list = (List) mapList.get("result");
		
		if(list.size() >0 ){
			PnrActReviewStaff pnrActReviewStaff = (PnrActReviewStaff)list.get(0);
			userStrs = pnrActReviewStaff.getUserId();
		}
		String processInstanceId = request.getParameter("processInstanceId");	
		
		List<String> assigneeList=null;
		if(StaticMethod.nullObject2String(userStrs).length()>0){
			
			assigneeList= Arrays.asList(StaticMethod.nullObject2String(userStrs).split(","));
			
		}else{
			request.setAttribute("msg","本地市,设置的会审人员有问题，请联系管理员");
			return mapping.findForward("failure");
			
		}
		// 确定会审人员--end
		
		/**
		 *  1、更改主表的state状态，为6时，代表会审
		 *  2、会审结束为7
		 */
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterNotEqual("state", 1);
		List<PnrTransferOffice> pList = pnrTransferOfficeService.search(search);
		
		if(pList!=null && pList.size()>0){
			PnrTransferOffice pnrTransferOffice= pList.get(0);
			pnrTransferOffice.setState(CommonUtils.TASK_COUNTERSIGNING_STATE);
			pnrTransferOfficeService.save(pnrTransferOffice);
			
		}else{
			request.setAttribute("msg","工单号:"+processInstanceId+"的工单不存在，请联系管理员");
			return mapping.findForward("failure");
		}
		
	/*	// 流程开始		
		TaskService taskService = (TaskService) getBean("taskService");		
		
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("assigneeList", assigneeList);  
		map.put("provinceManageHandleState", "next");  

		taskService.complete(taskId, map); 
		
			*/
		return listBacklog(mapping, form, request, response);
		
	}
	/**
	 * 会审-操作
	 */
	public ActionForward expertTeamTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		
		String processInstanceId = request.getParameter("processInstanceId");
		String provinceManage = StaticMethod.nullObject2String(request.getParameter("provinceManage"));
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String title = request.getParameter("title");
		String titleId = request.getParameter("titleId");
		String linkName = request.getParameter("linkName");
		String checkState = request.getParameter("checkState");
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
				
		
		
		// 流程开始
	
		TaskService taskService = (TaskService) getBean("taskService");
		
				
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).taskAssignee(userId).active().list();
		String taskId = taskList.get(0).getId();
		
		Map<String, Object> map = new HashMap<String, Object>();		
	
		map.put("provinceManageCheck", provinceManage);         
		taskService.complete(taskId, map); 	
		// 流程开始--end
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(titleId);
		entity.setTheme(title);
		entity.setCheckTime(new Date());
		entity.setHandleDescription(mainRemark);
		entity.setState(checkState);
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLinkName(linkName);
		entity.setOperation("03");
		transferHandleService.save(entity);
	
		return listCountersign(mapping, form, request, response);
	}
	/**
	 * 会审-操作--改造后的会审操作 15-01-28
	 */
	public ActionForward expertTeamTestDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		
		String processInstanceId = request.getParameter("processInstanceId");
		String provinceManage = StaticMethod.nullObject2String(request.getParameter("provinceManage"));
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String title = request.getParameter("title");
		String titleId = request.getParameter("titleId");
		String linkName = request.getParameter("linkName");
		String checkState = request.getParameter("checkState");
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterNotEqual("state", 6);
		int totalCount = pnrTransferOfficeService.searchAndCount(search).getTotalCount();
		
		if(totalCount>0){
			request.setAttribute("msg","当前工单("+processInstanceId+"),已会审结束");
			return mapping.findForward("failure");
		}
		
		PnrActRuCountersign pnrActRuCountersign = new PnrActRuCountersign();
		pnrActRuCountersign.setUserId(userId);
		pnrActRuCountersign.setProcessInstanceId(processInstanceId);
		pnrTransferOfficeService.insertCountersignInfo(pnrActRuCountersign);
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		
		entity.setThemeId(titleId);
		entity.setTheme(title);
		entity.setCheckTime(new Date());
		entity.setHandleDescription(mainRemark);
		entity.setState(checkState);
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLinkName(linkName);
		entity.setOperation("03");
		transferHandleService.save(entity);

		return listCountersign(mapping, form, request, response);
	}
	/**
	 * 会审列表中的处理列
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward todoCountersign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String processInstanceId = request.getParameter("processInstanceId");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		
		SearchResult t = pnrTransferOfficeService.searchAndCount(search);
		List<PnrTransferOffice> list = t.getResult();
		// 回复message
		PnrTransferOffice pnrTransferOffice =list.get(0);
		request.setAttribute("pnrTransferOffice", pnrTransferOffice);
		request.setAttribute("processInstanceId", pnrTransferOffice);

		String findForward = "expertTeamTestUpdate";

		return mapping.findForward(findForward);	
		
	}
	
	/**
	 * 根据类型，显示未处理列表
	 */
	public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		String provName = request.getParameter("provName");//工单类型
		

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobCount(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, status,region,country,lineType,provName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, status,region,country,lineType,provName, firstResult,
							endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		return mapping.findForward("backlogList");
	}
	/**
	 * 会审列表
	 */
	public ActionForward listCountersign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		String provName = request.getParameter("provName");//工单类型
		

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobForCountersignCount(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, "interface",region,country,lineType,provName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferPrecheckService.getToreplyJobOfEmergencyJobForCountersignList(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, "interface",region,country,lineType,provName, firstResult,
							endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合---原来的数据
		int totalOld = 0;
		try {
			totalOld = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobCount(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, "expertTeamTest",region,country,lineType,provName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	
		List<TaskModel> rPnrTransferListOld = null;
		try {
			rPnrTransferListOld = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, "expertTeamTest",region,country,lineType,provName, firstResult,
							endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合---原来的数据
		total += totalOld;		
		rPnrTransferList.addAll(rPnrTransferListOld);
		
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		return mapping.findForward("countersignList");
	}
	/**
	 * 预检预修回复
	 * 
	 * @author wangyue
	 * @title: todo
	 * @date Nov 11, 2014 9:26:03 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward todo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		request.setAttribute("userId", userId);
		String deptId = sessionform.getDeptid();
		request.setAttribute("deptId", deptId);
		String country = deptId;
		if (country.length() > 5) {
			country = country.substring(0, 5);
		}
		if ("admin".equals(userId) || "superUser".equals(userId)) {// 超级管理员
			country = "";
		}
		request.setAttribute("country", country);

		String taskId = request.getParameter("taskId");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		request.setAttribute("taskId", taskId);

		if (taskList != null && taskList.size() != 0) {
			Task task = taskList.get(0);
			String processInstanceId = task.getProcessInstanceId();
			request.setAttribute("processInstanceId", processInstanceId);
			request.setAttribute("auditor", task.getAssignee());
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			// 回复message
			PnrTransferOffice pnrTransferOffice =list.get(0);
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);
			
			
			
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			Map<String, Object> gMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> handlelist = null;
			int handleSize =0;		
			gMap=getHandleList(gMap,transferHandleService, search, handlelist,handleSize);
			search.addSort("checkTime", true);// 按回复时间排序
			
			//获取审批信息
			Map<String, Object> approveMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveHandlelist = null;
			int approveHandleSize =0;
			Search approveSearch = new Search();
			approveSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveSearch.addFilterEqual("operation", "01");
			approveMap=getHandleList(approveMap,transferHandleService, approveSearch, approveHandlelist,approveHandleSize);
			//获取审批驳回信息
			Map<String, Object> approveBackMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveBackHandlelist = null;
			int approveBackHandleSize =0;	
			Search approveBackSearch = new Search();
			approveBackSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveBackSearch.addFilterEqual("operation", "02");
			approveBackMap=getHandleList(approveBackMap,transferHandleService, approveBackSearch, approveBackHandlelist,approveBackHandleSize);
			//获取专家会审信息
			Map<String, Object> triageMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> triagelist = null;
			int triageSize =0;	
			Search triageSearch = new Search();
			triageSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			triageSearch.addFilterEqual("operation", "03");
			triageMap=getHandleList(triageMap,transferHandleService, triageSearch, triagelist,triageSize);
			//获取施工队处理信息
			Map<String, Object> transferMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> transferlist = null;
			int transferHandleSize =0;	
			Search transferSearch = new Search();
			transferSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			transferSearch.addFilterEqual("operation", "04");
			transferMap=getHandleList(transferMap,transferHandleService, transferSearch, transferlist,transferHandleSize);
			//获取审核信息
			Map<String, Object> auditMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> auditlist = null;
			int auditSize =0;	
			Search auditSearch = new Search();
			auditSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			auditSearch.addFilterEqual("operation", "05");
			auditSearch.addFilterEqual("state", "rollback");
			auditMap=getHandleList(auditMap,transferHandleService, auditSearch, auditlist,auditSize);
			

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();

			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceId(processInstanceId);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain", ticket);
			String nn = task.getTaskDefinitionKey();
			// attachments-end
			
			if (task.getTaskDefinitionKey().equalsIgnoreCase("cityLineExamine")) {// 市线路审批
				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));
				 //审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));

				String findForward = "csjCheck";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("cityManageExamine")) {// 市运维审批
			
				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));
				
				 //审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				
				//步骤及人员刷选--start
				String cityManageHandleState ="";
				String nextTaskAssignee="";
				String provinceLine ="";
				String provinceManage ="";
				
				String workTypeStr = CommonUtils.getId2NameString(pnrTransferOffice.getWorkType(), 1, ",");
				Double projectAmount = pnrTransferOffice.getProjectAmount();
				
				String firstMoneyLimitDicId = precheckRoleModel.getFirstMoneyLimitDicId();
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
				
				TawSystemDictType dictypeFirst = mgr.getDictByDictId(firstMoneyLimitDicId);
				double doubleFirst = 0L;
				
				if(dictypeFirst != null && StaticMethod.getFloatValue(dictypeFirst.getDictRemark())>-1){
					doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
				}else{
					request.setAttribute("msg","是否干线处-金额限制，有问题；请联系管理员");
					return mapping.findForward("failure");				
				}
				
				if("干线".equals(workTypeStr)||(!"干线".equals(workTypeStr)&&projectAmount>doubleFirst)){
					cityManageHandleState = "next";	
					IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
					String provinceLineExamineRoleId = precheckRoleModel.getProvinceLineExamine();
					List<Map> listMap = pnrTransferPrecheckService.getSGSByCountryCSJ(pnrTransferOffice.getCountryCSJ(),provinceLineExamineRoleId);//此值要改
					 nextTaskAssignee = "superUser";
					if(listMap!=null && listMap.size()>0){
						if(listMap.get(0).get("USERID")!=null && !"".equals(listMap.get(0).get("USERID"))){
							nextTaskAssignee = listMap.get(0).get("USERID").toString();
						}
					}
					pnrTransferOffice.setProvinceLine(nextTaskAssignee);
					String provinceManageExamineRoleId = precheckRoleModel.getProvinceManageExamine();
					List<Map> listMap2 = pnrTransferPrecheckService.getSGSByCountryCSJ(pnrTransferOffice.getCountryCSJ(),provinceManageExamineRoleId);//此值要改
					provinceManage = "superUser";
					if(listMap2!=null && listMap2.size()>0){
						if(listMap2.get(0).get("USERID")!=null && !"".equals(listMap2.get(0).get("USERID"))){
							provinceManage = listMap2.get(0).get("USERID").toString();
						}
					}
					pnrTransferOffice.setProvinceManage(provinceManage);
				
					pnrTransferOfficeService.save(pnrTransferOffice);
					
				}else {					
						cityManageHandleState = "handle";
						nextTaskAssignee="";					
				}
				
				
				
				request.setAttribute("cityManageHandleState", cityManageHandleState);
				request.setAttribute("nextTaskAssignee", nextTaskAssignee);
				//步骤及人员刷选--end
				
				String findForward = "sgsCheck";

				return mapping.findForward(findForward);
			}else if(task.getTaskDefinitionKey().equalsIgnoreCase("provinceLineExamine")){//省线路审批
				
				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));
				
				 //审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request, (List<PnrTransferOfficeHandle>)gMap.get("list"));

				String findForward = "provinceLineExamine";

				return mapping.findForward(findForward);				
			}else if(task.getTaskDefinitionKey().equalsIgnoreCase("expertTeamTest")){//专家审批
				

				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));
				// 显示附件
				showReplySetAttribute(request, (List<PnrTransferOfficeHandle>)gMap.get("list"));

				String findForward = "expertTeamTest";

				return mapping.findForward(findForward);				
			}else if(task.getTaskDefinitionKey().equalsIgnoreCase("provinceManageExamine")){//省运维审批
				
				search.addFilterEqual("linkName", "expertTeamTest");
				//专家会审消息
				request.setAttribute("triageListsize", triageMap.get("size"));
				request.setAttribute("triageList", triageMap.get("list"));
				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				
				request.setAttribute("cityManageHandleState", "handle");
				
				double ratio = 1d;
				if(pnrTransferOffice.getState()!=null && pnrTransferOffice.getState()==6){
					
					//专家会审的审批率
					ratio =pnrTransferOfficeService.getCountersignRatioByProcessInstanceId(processInstanceId, pnrTransferOffice.getCity());
				}
				String isAllowSubmitStr = "1";
				if(ratio < 0.5){
					isAllowSubmitStr = "0";
				}
				
				request.setAttribute("isAllowSubmitStr", isAllowSubmitStr);
				request.setAttribute("ratio", ratio);
				
				return mapping.findForward("provinceManageExamine");
				
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("sendOrder")) {// 工单转派
				return mapping.findForward("mainSecond");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("worker")) {// 工单处理
				if (list != null) {
					// 回退原因，需要补录上
					String id = list.get(0).getId();
					Search search2 = new Search();
					search2.addFilterEqual("themeId", id);
					search2.addFilterEqual("state", "rollback");
					search2.addSort("checkTime", true);

					List<PnrTransferOfficeHandle> Handlelist2 = transferHandleService
							.searchAndCount(search2).getResult();

					if (Handlelist2 != null && Handlelist2.size() > 0) {
						request.setAttribute("reason", Handlelist2.get(0)
								.getOpinion());

					}
						
					request.setAttribute("auditor", list.get(0).getCountryCSJ());
					
				}
				//驳回信息
				request.setAttribute("auditListsize", auditMap.get("size"));
				request.setAttribute("auditList", auditMap.get("list"));
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("daiweiAudit")) {// 审核-区县传输局
				//金额限制--start
				String secondMoneyLimitDicId = precheckRoleModel.getSecondMoneyLimitDicId();
				String threeMoneyLimitDicId = precheckRoleModel.getThreeMoneyLimtDicId();
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
				
				TawSystemDictType dictypeSencond = mgr.getDictByDictId(secondMoneyLimitDicId);
				TawSystemDictType dictypeThree = mgr.getDictByDictId(threeMoneyLimitDicId);
				double doubleSecond = 0L;
				double doubleThree = 0L;
				
				if(dictypeSencond !=null && StaticMethod.getFloatValue(dictypeSencond.getDictRemark())>-1){
					doubleSecond = Double.parseDouble(dictypeSencond.getDictRemark());
				}else{
					request.setAttribute("msg","派单审核后-金额限制，有问题；请联系管理员");
					return mapping.findForward("failure");
				}
				
				if(dictypeThree !=null && StaticMethod.getFloatValue(dictypeThree.getDictRemark())>-1){
					doubleThree = Double.parseDouble(dictypeThree.getDictRemark());
				}else{
					
					request.setAttribute("msg","最后一处-金额设置，有问题，请联系管理员");
					return mapping.findForward("failure");
				}
				//金额限制--end
				
				search.addFilterEqual("linkName", "worker");
				//gMap = getHandleList(gMap,transferHandleService, search, handlelist,handleSize);

				request.setAttribute("PnrTransferHandleList", gMap.get("list"));
				request.setAttribute("listsize", gMap.get("size"));

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getCreateWork());
				}
				showReplySetAttribute(request, (List<PnrTransferOfficeHandle>)gMap.get("list"));
				//根据不同等级的项目估算金额获取不同的抽查人员
				Double projectAmount = pnrTransferOffice.getProjectAmount();
				String testAudit = "";
					if(projectAmount== null || projectAmount<doubleSecond){//区县维护中心抽查
						testAudit=pnrTransferOffice.getCountryCSJ();
					}else if(projectAmount<doubleThree){//市线路维护中心抽查
						testAudit=pnrTransferOffice.getCityCSJ();
					}else{//市运维部抽查
						testAudit=pnrTransferOffice.getCityGS();
					}
				request.setAttribute("testAudit",testAudit);
				//施工队处理信息
				request.setAttribute("transferListsize", transferMap.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));
				
				return mapping.findForward("secondAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("orderAudit")) {// 工单审核

				request.setAttribute("listsize", gMap.get("size"));
				request.setAttribute("pnrTransferOfficeHandleList", gMap.get("list"));

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getSecondInitiator());
				}
				showReplySetAttribute(request, (List<PnrTransferOfficeHandle>)gMap.get("list"));
				return mapping.findForward("goTransferCheck");
			}
		}

		return mapping.findForward("error");
	}

	/**
	 * 显示回复的附件！
	 * 
	 * @param request
	 * @param handlist
	 */
	private void showReplySetAttribute(HttpServletRequest request,
			List<PnrTransferOfficeHandle> handlist) {
		if (handlist != null) {
			int handSize = handlist.size();
			if (handSize > 0) {
				for (int i = 0; i < handSize; i++) {
					request.setAttribute("sheetReply" + i, handlist.get(i));
				}
			}
		}
	}

	/**
	 * 查看历史【包含流程跟踪、任务列表（完成和未完成）、流程变量】
	 * 
	 * @return
	 */
	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "historicTasks");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int totalSize = 0;

		HistoryService historyService = (HistoryService) getBean("historyService");
		String processInstanceId = request.getParameter("processInstanceId");
		List<HistoricTaskInstance> historicTasks = historyService
				.createHistoricTaskInstanceQuery().processInstanceId(
						processInstanceId)
				.orderByHistoricTaskInstanceStartTime().asc().listPage(
						firstResult * pageSize, pageSize);
		totalSize = (int) historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).count();
		List<HistoricVariableInstance> historicVariableInstances = historyService
				.createHistoricVariableInstanceQuery().processInstanceId(
						processInstanceId).list();
		// historyService

		request.setAttribute("historicTasks", historicTasks);
		request.setAttribute("historicTasksPageSize", pageSize);
		request.setAttribute("historicTasksTotal", totalSize);
		request.setAttribute("historicVariableInstances",
				historicVariableInstances);
		request.setAttribute("historicVariableInstancesPageSize", pageSize);
		request.setAttribute("historicVariableInstancesTotal",
				historicVariableInstances.size());
		request.setAttribute("processInstanceId", processInstanceId);
		return mapping.findForward("viewHistory");
	}

	/**
	 * 流程跟踪
	 * 
	 * @throws Exception
	 */
	public void graphHistoryProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RepositoryService repositoryService = (RepositoryService) getBean("repositoryService");
		String processInstanceId = request.getParameter("processInstanceId");
		Command<InputStream> cmd = null;
		cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);

		InputStream is = ((ServiceImpl) repositoryService).getCommandExecutor()
				.execute(cmd);
		response.setContentType("image/png");

		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	// 审批通过
	public ActionForward transferProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");
	

		// 审批时间
		Date createTime = new Date();

		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("transferAudit"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("teskAssignee"));
		
		String taskId = request.getParameter("taskId");
		//单位
		String company = request.getParameter("companyName");
		//电话
		String telephone = request.getParameter("telephone");
		
		//附件
		String attachments = request.getParameter("sheetAccessories");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		
		String step = request.getParameter("step");

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(userId);
		entity.setCompany(company);
		entity.setTelephone(telephone);
		entity.setProcessInstanceId(processInstanceId);
		entity.setDoPerson(auditor);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		transferHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (pnrTicket != null) {
			deadlineTime = pnrTicket.getSubmitApplicationTime() == null ? "" : sFormat
					.format(pnrTicket.getSubmitApplicationTime());
			contact = pnrTicket.getConnectPerson();
		}
		// attachment--start
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		// attachment--end
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
				tempKeyValue+=name+":"+request.getParameter(name)+";";
			}
		}
		
		String hjflag=request.getParameter("hjflag");//环节标识
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		request.setAttribute("success", "check");

		// 发短信
		if (isSend) {
			String messagePerson="";
			if("2".equals(step)){
				messagePerson = pnrTicket.getCityCSJ();
			}else if("3".equals(step)){
				messagePerson = pnrTicket.getCityGS();
			}else if("5".equals(step)){
				messagePerson = pnrTicket.getProvinceManage();
			}
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, messagePerson);
		}
		return mapping.findForward("showSuccess");
	}
	
	// 审批通过
	public ActionForward transferProgramSGS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");

		// 审批时间
		Date createTime = new Date();

		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));

		// 审核意见---暂时没有用
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("transferAudit"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("teskAssignee"));
		
		String taskId = request.getParameter("taskId");
		//单位
		String company = request.getParameter("companyName");
		//电话
		String telephone = request.getParameter("telephone");
		//附件
		String attachments = request.getParameter("sheetAccessories");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		
		String step = request.getParameter("step");

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(userId);
		entity.setCompany(company);
		entity.setTelephone(telephone);
		entity.setProcessInstanceId(processInstanceId);
		entity.setDoPerson(auditor);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		transferHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (pnrTicket != null) {
			deadlineTime = pnrTicket.getSubmitApplicationTime() == null ? "" : sFormat
					.format(pnrTicket.getSubmitApplicationTime());
			contact = pnrTicket.getConnectPerson();
		}
		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		// attachment--end
		
		String cityManageHandleState ="";
			// 状态-往哪步走
	     cityManageHandleState= request.getParameter("cityManageHandleState");
		String tempKeyValue="";
		
		if(cityManageHandleState.equals("handle")){
			pnrTicket.setState(8);
			String msg = "您申请的预检预修工单 "+pnrTicket.getProcessInstanceId()+"-"+theme+" 已审批通过，请根据预算情况发起商城订单";
			CommonUtils.sendMsg(msg, pnrTicket.getOneInitiator());
			
		}else{
			String nextTaskAssignee = request.getParameter("nextTaskAssignee");
			FormService formService = (FormService) getBean("formService");
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			Map<String, String> map = new HashMap<String, String>();
			for (FormProperty formProperty : taskFormData.getFormProperties()) {
				if (formProperty.isWritable()) {
					String name = formProperty.getId();
					map.put(name, request.getParameter(name));
					tempKeyValue+=name+":"+request.getParameter(name)+";";
				}
			}
			formService.submitTaskFormData(taskId, map);
			
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, nextTaskAssignee);
		}
		
		pnrTransferOfficeService.deleteCountersignInfo(processInstanceId);//删除辅助表中会审人员的会审记录
		
		request.setAttribute("success", "check");
		
		pnrTransferOfficeService.save(pnrTicket);
		return mapping.findForward("showSuccess");
	}
	
	
	/**
	 * 自维任务审核保存
	 */
	public ActionForward goTransferCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		boolean isSend=true;
		String msg = "";
		String messagePerson="";
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("handleState"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("auditor"));

		String taskId = request.getParameter("taskId");

		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		String processInstanceId=taskList.get(0).getProcessInstanceId();
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		
		search.addFilterEqual("processInstanceId", taskList.get(0)
				.getProcessInstanceId());
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (auditState.equals("rollback")) {
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
				messagePerson = pnrTicket.getInitiator();
				
				msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTicket.getProcessInstanceId()+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			}
			isSend = false;

		} else if (auditState.equals("handle")) {
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTicket = pnrTicketList.get(0);
				pnrTicket.setArchivingTime(new Date());
				pnrTicket.setState(5);
				pnrTransferOfficeService.save(pnrTicket);
			}
		}

		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		
		String hjflag="";
		if(auditState.equals("handle")){
			hjflag="工单审核";
		}else if(auditState.equals("rollback")){
			hjflag="工单审核驳回";
		}else{
			hjflag="工单审核未知";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		// 保存审核意见
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(auditor);
		entity.setOperation("05");
		transferHandleService.save(entity);
		// 保存审核意见--end
		
		if(!isSend){//审核未通过，短息通知
			 CommonUtils.sendMsg(msg, messagePerson);
			
		}

		request.setAttribute("success", "check");

		return mapping.findForward("success");
	}
	/**
	 * automation -自动转派
	 */
	public ActionForward automation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 派发人
		String initiator = sessionForm.getUserid();

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 接收人
		String taskAssignee = request.getParameter("auditor");
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");

		// 工单ID
		String themeId = request.getParameter("themeId");
		// taskId
		String taskId = request.getParameter("taskId");
		
		// 流程开始
		FormService formService = (FormService) getBean("formService");

		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		String time2 = sFormat.format(new Date());
		Map<String, String> map = new HashMap<String, String>();
		 for (int i = 0; i < taskFormData.getFormProperties().size(); i++) {
		      String name = ((FormProperty)taskFormData.getFormProperties().get(i)).getId();
		      if (i == 0)
		        map.put(name, taskAssignee);
		      else if (i == 1)
		        map.put(name, time2);
		    }
		String processInstanceId=request.getParameter("processInstanceId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:自动派发（无代维公司，手动）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:自动派发（无代维公司，手动）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程结束
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = new PnrTransferOffice();
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		pnrTransferOfficeService.save(entity);

		// 发短信
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+entity.getProcessInstanceId()+","+TASK_TITLE_STR+entity.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(entity.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+entity.getWorkOrderTypeName()+","+TASK_SUBTYPE+entity.getSubTypeName()+"。";
		  CommonUtils.sendMsg(msg, taskAssignee);
		  
		return mapping.findForward("showSuccess");

	}
	
	/**
	 * mainSecond -外包公司派发
	 */
	public ActionForward mainSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		// 派发人

		String initiator = sessionForm.getUserid();
		// 接收人
		String handleWorker = request.getParameter("handleWorker");
		//时限
		String limit = request.getParameter("sheetCompleteLimit");
		//工单创建时间
		String createTime = request.getParameter("createTime");
		//计算结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar cal = Calendar.getInstance();
		 Date date= new Date();
		 try{
			 date = sdf.parse(createTime);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 cal.setTime(date);
		 //默认时限为2小时
		 int timeNum=2;
		 try{
			 timeNum = Integer.parseInt(limit);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 cal.add(Calendar.HOUR_OF_DAY, timeNum);
		 date = cal.getTime();
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");

		// 工单ID
		String themeId = request.getParameter("themeId");
		// taskId
		String taskId = request.getParameter("taskId");
		
		// 流程开始
		FormService formService = (FormService) getBean("formService");

		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		map.put("handleWorker", handleWorker);
		map.put("dueDate", sdf.format(date));
		tempKeyValue+="handleWorker"+":"+handleWorker+";"+"dueDate"+":"+date+";";
		
		String processInstanceId=request.getParameter("processInstanceId");
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程结束
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = new PnrTransferOffice();
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		
		// 接收人
		entity.setTaskAssignee(handleWorker);
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		if ("".equals(limit)) {
			limit = "2";// 如果程序出现错误，默认处理时限2小时。
		}
		entity.setProcessLimit(Double
				.parseDouble(limit));
		
		entity.setEndTime(sdf.parse(sdf.format(date)));
		// 派发人更改
		entity.setInitiator(initiator);
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime()==null?"":sFormat.format(entity.getEndTime());
		
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+entity.getProcessInstanceId()+","+TASK_TITLE_STR+entity.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(entity.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+entity.getWorkOrderTypeName()+","+TASK_SUBTYPE+entity.getSubTypeName()+"。";
		  CommonUtils.sendMsg(msg, handleWorker);
		  
		return mapping.findForward("showSuccess");

	}
	
	
	
	/**
	 * 任务审核保存---代维公司质检
	 * 
	 */
	public ActionForward secondAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		
		//发短信
		String processInstanceId="",deadlineTime="",contact="";
		boolean isSend=true;
		
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("initiatorHandleState"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("testAudit"));
		//当前所处环节
		String linkName = StaticMethod.nullObject2String(request
				.getParameter("linkName"));

		String taskId = request.getParameter("taskId");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(userId);
		entity.setLinkName(linkName);
		entity.setProcessInstanceId(taskList.get(0).getProcessInstanceId());
		entity.setOperation("05");
		transferHandleService.save(entity);
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		processInstanceId=taskList.get(0).getProcessInstanceId();
		search.addFilterEqual("processInstanceId", taskList.get(0)
				.getProcessInstanceId());
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket=null;
		if (pnrTicketList != null) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (auditState.equals("rollback")) {
			if(pnrTicket!=null){
				
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
				
				auditor = pnrTicket.getTaskAssignee();// 回退到二次处理人
			}
			isSend = false;
			
		}else if(auditState.equals("handle")){
			if(pnrTicket!=null){
			processInstanceId = pnrTicket.getProcessInstanceId();
			deadlineTime = pnrTicket.getEndTime()==null?"":sFormat.format(pnrTicket.getEndTime());
			contact = pnrTicket.getConnectPerson();
			}
		}

		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		if (auditState.equals("rollback")) {// 回退时的说法
			map.put("handleWorker", auditor);
		}
		
		String hjflag="";//区县传输局审核 提交 驳回 标识
		if(auditState.equals("handle")){
			hjflag="区县传输局审核";
		}else if(auditState.equals("rollback")){
			hjflag="区县传输局审核驳回";
		}else{
			hjflag="区县传输局审核（未知）";
		};
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		request.setAttribute("success", "check");

		// 发短信
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTicket.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
		CommonUtils.sendMsg(msg, auditor);
		return mapping.findForward("showSuccess");
	}
	/**
	 * 处理保存--处理环节
	 */
	public ActionForward transferHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//发短信
		String deadLineTime = "",contact="";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userName = sessionForm.getUsername();
		
		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request
				.getParameter("titleId"));
		// 回单人
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userId"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("initiatorCheck"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 当前所处环节
		String linkName = request.getParameter("linkName");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setCheckTime(createTime);
		entity.setDoPerson(userName);
		entity.setHandleDescription(mainRemark);
		entity.setIsRecover(isRecover);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLinkName(linkName);
		entity.setTaskId(taskId);
		entity.setOperation("04");
		
		String msg="";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			
			msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			
			
			deadLineTime =pnrTransferOffice.getEndTime()==null?"":sFormat.format(pnrTransferOffice.getEndTime());
			contact=pnrTransferOffice.getConnectPerson();
		}
		// 处理人关系表数据保存--start
		/*
		 * String[] personStrings = dealPerformer2.split(",");
		 * pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
		 * personStrings);
		 */
		// 处理人关系表数据保存--end
		transferHandleService.save(entity);
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
				tempKeyValue+=name+":"+request.getParameter(name)+";";
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程提交到下一级-end

		
		
		  CommonUtils.sendMsg(msg, auditor);
		 
		return mapping.findForward("showSuccess");

	}

	/**
	 * 回退任务
	 * 
	 * @return
	 */
	public ActionForward rollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=response.getWriter();	
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();
		
		//短息接收人
		String msgPerson = "";
		//退回提示信息
		String returnMsg ="该工单已驳回，请重新处理！";
		
		String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		String returnPerson = request.getParameter("returnPerson");
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");
		//theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");
		
	try{
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();

		String hjflag="";
		//当前所处环节
		String linkName="";
		if (handle.equals(TASK_TRANSFERCITYCJS)) {//市传输局驳回
			hjflag="市传输局驳回";
			map.put("initiator", returnPerson);
			map.put("cityLineHandleState", "rollback");
			msgPerson = returnPerson;
			linkName = "cityLineExamine";
		}
		else if (handle.equals(TASK_TRANSFERCITYGS)) {//市公司驳回
			hjflag="市公司驳回";
			map.put("cityLineCheck",returnPerson);
			map.put("cityManageHandleState", "rollback");
			msgPerson=returnPerson;
			linkName = "cityManageExamine";
		}
		else if (handle.equals(TASK_PROVINCELINE)) {//省线路审批驳回
			hjflag="省线路驳回";
			map.put("cityManageCheck",returnPerson);
			map.put("provinceLineHandleState", "rollback");
			msgPerson=returnPerson;
			linkName = "provinceLineExamine";
		}
		else if (handle.equals(TASK_PROVINCEMANAGE)) {//省运维审批驳回
			hjflag="省运维审驳回";
			map.put("nextTaskAssignee",returnPerson);
			map.put("provinceManageHandleState", "rollback");
			msgPerson=returnPerson;
			linkName = "provinceManageExamine";
		}
		/*else if (handle.equals(TASK_TRANSFERHANDLE)) {//施工队回退
			hjflag="施工队驳回";
			map.put("nextTaskAssignee", initiator);
			map.put("workerHandleState", "rollback");
			msgPerson=initiator;
			returnMsg = "该工单未能处理，已回退，请重新处理！";
			linkName = "worker";
		}*/
		
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
	   formService.submitTaskFormData(taskId, map);
	   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
	   
		if(!handle.equals(TASK_TRANSFERHANDLE)){
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
			//将回退原因存入操作表中
			String rejectReason = request.getParameter("rejectReason");
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");

			String userId = sessionform.getUserid();
			// 审批时间
			Date createTime = new Date();
			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setTaskId(taskId);
			entity.setProcessInstanceId(processInstanceId);
			entity.setAuditor(userId);
			entity.setHandleDescription(rejectReason);
			entity.setLinkName(linkName);
			entity.setOperation("02");
			transferHandleService.save(entity);
		}
		
		String msg="";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			
			msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
		
			
			if (handle.equals(TASK_PROVINCEMANAGE)){
				//删除会审辅助表记录的辅助信息
				pnrTransferOfficeService.deleteCountersignInfo(processInstanceId);
				
				pnrTransferOffice.setState(7);//停止会审
				pnrTransferOfficeService.save(pnrTransferOffice);
			}
			
		}
		
		//当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
		  CommonUtils.sendMsg(msg, msgPerson);
		 out.print("true"); 
			  
		  } catch (Exception e) {
			  out.print("false");
			  e.printStackTrace();
		  }
  return null; 
	}
	
	
	/**
	 * 工单执行驳回（施工队驳回）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward workerRollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();
		
		//短息接收人
		String msgPerson = "";
		//退回提示信息
		String returnMsg ="该工单已驳回，请重新处理！";
		
		String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		String returnPerson = request.getParameter("returnPerson");
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = StaticMethod.nullObject2String(request
				.getParameter("theme"));
		theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");
		
		
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
	
			String hjflag="";
			//当前所处环节
			String linkName="";
			if (handle.equals(TASK_TRANSFERHANDLE)) {//施工队回退
				hjflag="施工队驳回";
				map.put("nextTaskAssignee", initiator);
				map.put("workerHandleState", "rollback");
				msgPerson=initiator;
				returnMsg = "该工单未能处理，已回退，请重新处理！";
				linkName = "worker";
			}
			
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		   formService.submitTaskFormData(taskId, map);
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
			
			String msg="";
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
				
				msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			}
			
			//当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
			  CommonUtils.sendMsg(msg, msgPerson);
			  
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 新预检预修工单--工单抓回
	  * @author wangyue
	  * @title: newPrecheckRollback
	  * @date Jan 28, 2015 3:35:09 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward newPrecheckRollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//抓回--工单接收人
		String initiator = request.getParameter("initiator");
		//抓回步骤
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if(handle.equals(TASK_TRANSFERHANDLE)){//施工队 -抓回
			map.put("nextTaskAssignee", initiator);
			map.put("workerHandleState", "rollback");
		}else if(handle.equals(TASK_CITYLINEEXAMINE)){//市线路--抓回
			map.put("initiator", initiator);
			map.put("cityLineHandleState", "rollback");
		}else if(handle.equals(TASK_CITYMANAGEEXAMINE)){//市运维--抓回
			map.put("cityLineCheck", initiator);
			map.put("cityManageHandleState", "rollback");
		}
		formService.submitTaskFormData(taskId, map);
		
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 打开驳回页面，给驳回界面传递相关的数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openRejectView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 
		return mapping.findForward("openRejectView");
	}
	
	/**
	 * 预检预修--批量归档
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doMoreWorkOrderArchiving(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String[] parameterValues = request.getParameterValues("Fruit");
		for (int i = 0; i < parameterValues.length; i++) {
			String oneParameter = parameterValues[i].trim();
			String[] results = oneParameter.split(",");
			//taskid
			String oneTaskId = results[0].trim();
			//工单号
			String processInstanceId = results[1].trim();
			
			//存储归档时间
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			
			if(pnrTicketList!=null ){
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
				pnrTransferOffice.setArchivingTime(sFormat.parse(sFormat.format(new Date())));
				pnrTransferOfficeService.save(pnrTransferOffice);
			}
			// 流程提交到下一级
			FormService formService = (FormService) getBean("formService");
			TaskFormData taskFormData = formService.getTaskFormData(oneTaskId);
			Map<String, String> map = new HashMap<String, String>();
			for (FormProperty formProperty : taskFormData.getFormProperties()) {
				if (formProperty.isWritable()) {
					String name = formProperty.getId();
					map.put(name, request.getParameter(name));
				}
			}
			formService.submitTaskFormData(oneTaskId, map);
		}
		return listBacklog(mapping,form,request,response);
	}
	
	/**
	 * 预检预修工单--归档操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doOneWorkOrderArchiving(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String taskId = request.getParameter("taskId");
		String processInstanceId = request.getParameter("processInstanceId");
		
		//存储归档时间
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		
		if(pnrTicketList!=null ){
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setArchivingTime(sFormat.parse(sFormat.format(new Date())));
			pnrTransferOfficeService.save(pnrTransferOffice);
		}
		
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
		
		return listBacklog(mapping,form,request,response);
	}
	/**
	 * 用户发起的流程（包含已经完成和未完成）
	 * 
	 * @return
	 */
	public ActionForward listRunningProcessInstances(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		List<HistoricProcessInstance> historicProcessInstancesList = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey(
						processDefinitionKey).startedBy(userId)
				.orderByProcessInstanceStartTime().desc().listPage(
						firstResult * pageSize, endResult * pageSize);
		long total = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey).startedBy(userId)
				.count();
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		List<TaskModel> rPnrTransferOfficeList = new ArrayList<TaskModel>();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
			String processInstanceId = historicProcessInstance.getId();
			TaskModel troubleTicketModel = new TaskModel();
			troubleTicketModel.setProcessInstanceId(processInstanceId);

			if (historicProcessInstance.getEndTime() != null) {
				if (historicProcessInstance.getDeleteReason() == null) {
					troubleTicketModel.setStatusName("归档");
				} else {
					troubleTicketModel.setStatusName("已删除");
				}
			} else {
				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								processInstanceId).orderByTaskId().desc()
						.listPage(0, 1);
				if (historicTasks != null & historicTasks.size() > 0) {
					troubleTicketModel.setStatusName(historicTasks.get(0)
							.getName());
					troubleTicketModel.setTaskDefKey(historicTasks.get(0)
							.getTaskDefinitionKey());
					troubleTicketModel.setTaskId(historicTasks.get(0).getId());
				}
			}
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> list = pnrTransferOfficeService
					.search(search);
			if (list != null && list.size() != 0) {
				String themeInterface = list.get(0).getThemeInterface();
				if(themeInterface!=null && "interface".equals(themeInterface)){
					
					troubleTicketModel.setSendTime(list.get(0).getSubmitApplicationTime());
					troubleTicketModel.setTheme(list.get(0).getTheme());
					troubleTicketModel.setState(list.get(0).getState());
					troubleTicketModel.setInitiator(list.get(0).getInitiator());
					troubleTicketModel.setOneInitiator(list.get(0)
							.getOneInitiator());
					int state = list.get(0).getState();
					if(state==8 && troubleTicketModel.getTaskDefKey().equals(TASK_CITYMANAGEEXAMINE)){
						troubleTicketModel.setStatusName(TASK_CITYMANAGEEXAMINE_STATUS);
					}else if(state==8 && troubleTicketModel.getTaskDefKey().equals(TASK_PROVINCEMANAGEEXAMINE)){
						troubleTicketModel.setStatusName(TASK_PROVINCEMANAGEEXAMINE_STATUS);
						
					}
					rPnrTransferOfficeList.add(troubleTicketModel);
				}
			}
		}
		request.setAttribute("taskList", rPnrTransferOfficeList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("runningProcessInstancesList");
	}
	
	/**
	 * 详细信息呈现
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String processId = request.getParameter("processInstanceId");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processId);
		SearchResult t = pnrTransferOfficeService.searchAndCount(search);
		List<PnrTransferOffice> list = t.getResult();
		// 回复message
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		search.addSort("receiveTime", true);// 按回复时间排序

		List<PnrTransferOfficeHandle> handlelist = transferHandleService
				.searchAndCount(search).getResult();
		// attachments
		PnrTransferOffice ticket = new PnrTransferOffice();
		String sheetAccessories = pnrTransferOfficeService
				.getAttachmentNamesByProcessInstanceId(processId);
		ticket.setSheetAccessories(sheetAccessories);
		request.setAttribute("sheetMain", ticket);
		// attachments-end
		showReplySetAttribute(request, handlelist);

		request.setAttribute("pnrTransferOffice", list.get(0));
		request.setAttribute("PnrTransferHandleList", handlelist);
		request.setAttribute("listsize", handlelist.size());
		request.setAttribute("processInstanceId", processId);

		return mapping.findForward("gotoDetail");

	}

	/**
	 * 用户参已经完成的流程
	 * 
	 * @return
	 */
	public ActionForward listInvolvedFinishedProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
	//	String status = request.getParameter("status");
		String status="";//工单状态 预留
		
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		
		//已归档工单条数
		int total = pnrTransferPrecheckService.getArchivedCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//已归档工单明细
		List<TaskModel> rPnrTicketList = pnrTransferPrecheckService.getArchivedList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);
		
//		List<HistoricProcessInstance> historicProcessInstancesList = historyService
//				.createHistoricProcessInstanceQuery().processDefinitionKey(
//						processDefinitionKey).involvedUser(userId).finished()
//				.listPage(firstResult * pageSize, endResult * pageSize);
//		long total = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey(processDefinitionKey)
//				.involvedUser(userId).finished().count();
//		
//		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//
//		List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();
//		
//		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
//			String processInstanceId = historicProcessInstance.getId();
//			TaskModel transferModel = new TaskModel();
//			transferModel.setProcessInstanceId(historicProcessInstance.getId());
//			Search search = new Search();
//			search.addFilterEqual("processInstanceId", historicProcessInstance
//					.getId());
//			
//
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							processInstanceId).orderByTaskId().desc().list();
//		
//			
//			
//			if (historicTasks != null & historicTasks.size() > 0) {
//				if (historicTasks.get(0).getDeleteReason().equals("delete")) {
//					transferModel.setStatusName(CommonUtils.taskDetele);
//
//				} else {
//
//					transferModel.setStatusName(CommonUtils.taskComplete);
//				}
//			}
//			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
//			List<PnrTransferOffice> list = t.getResult();
//			if (list != null && list.size() != 0) {
//				String themeInterface = list.get(0).getThemeInterface();
//				//判断是否是传输局运维商城接口数据
//				if(themeInterface!=null && "interface".equals(themeInterface)){
//					transferModel.setSendTime(list.get(0).getSubmitApplicationTime());
//					transferModel.setTheme(list.get(0).getTheme());
//					transferModel.setInitiator(list.get(0).getInitiator());
//					TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
//							.get(0).getTaskAssignee(), "");
//					if (pu != null) {
//						transferModel.setDeptId(pu.getDeptid());
//					}
//					rPnrTicketList.add(transferModel);
//				}
//			}
//
//		}
		
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		//request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedFinishedList");
	}

	/**
	 * 用户参已经未完成的流程
	 * 
	 * @return
	 */
	public ActionForward listInvolvedUnfinishedProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//logger.info("预检预修工单-已处理工单-查询开始");
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
	//	HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		//IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		
		//已处理工单条数
		int total = pnrTransferPrecheckService.getHaveProcessCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//已处理工单明细
		List<TaskModel> rPnrTicketList = pnrTransferPrecheckService.getHaveProcessList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);
		
//		logger.info("预检预修工单-已处理工单-historicProcessInstancesList开始");
//		List<HistoricProcessInstance> historicProcessInstancesList = historyService
//				.createHistoricProcessInstanceQuery().processDefinitionKey(
//						processDefinitionKey).involvedUser(userId).unfinished()
//				.listPage(firstResult * pageSize, endResult * pageSize);
//		
//		logger.info("预检预修工单-已处理工单-historicProcessInstancesList结束");
//		
//		logger.info("预检预修工单-已处理工单-total开始");
//		
//		long total = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey(processDefinitionKey)
//				.involvedUser(userId).unfinished().count();
//		
//		//int total = pnrTransferOfficeService.getToreplyJobOfEmergencyJobCount()
//		
//		logger.info("预检预修工单-已处理工单-total结束");
//
//	
//		List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();
//		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
//			String processInstanceId = historicProcessInstance.getId();
//			TaskModel troubleTicketModel = new TaskModel();
//			troubleTicketModel.setProcessInstanceId(historicProcessInstance
//					.getId());
//			Search search = new Search();
//			search.addFilterEqual("processInstanceId", historicProcessInstance
//					.getId());
//			
//			logger.info("预检预修工单-已处理工单-historicTasks开始");
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							processInstanceId).orderByTaskId().desc().listPage(
//							0, 1);
//			logger.info("预检预修工单-已处理工单-historicTasks结束");
//			
//			if (historicTasks != null & historicTasks.size() > 0) {
//				troubleTicketModel
//						.setStatusName(historicTasks.get(0).getName());
//			}
//			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
//			List<PnrTransferOffice> list = t.getResult();
//			if (list != null && list.size() != 0) {
//				String themeInterface = list.get(0).getThemeInterface();
//				if(themeInterface!=null && "interface".equals(themeInterface)){
//					
//					troubleTicketModel.setSendTime(list.get(0).getSubmitApplicationTime());
//					troubleTicketModel.setTheme(list.get(0).getTheme());
//					troubleTicketModel.setInitiator(list.get(0).getInitiator());
//					int state = list.get(0).getState();
//					if(state==8){
//						troubleTicketModel.setStatusName("市公司审批完毕");
//					}
//					TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
//							.get(0).getTaskAssignee(), "");
//					if (pu != null) {
//						troubleTicketModel.setDeptId(pu.getDeptid());
//						
//					}
//					rPnrTicketList.add(troubleTicketModel);
//				}
//			}
//		}
//		
//		logger.info("预检预修工单-已处理工单-查询结束");
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedProcessInstancesList");
	}
	/**
	 * 抓回功能
	 * 
	 * @return
	 */
	public ActionForward getBack(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		List<HistoricProcessInstance> historicProcessInstancesList = historyService
		.createHistoricProcessInstanceQuery().processDefinitionKey(
				processDefinitionKey).involvedUser(userId).unfinished()
				.listPage(firstResult * pageSize, endResult * pageSize);
		long total = historyService.createHistoricProcessInstanceQuery()
		.processDefinitionKey(processDefinitionKey)
		.involvedUser(userId).unfinished().count();
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
			String processInstanceId = historicProcessInstance.getId();
			TaskModel troubleTicketModel = new TaskModel();
			troubleTicketModel.setProcessInstanceId(historicProcessInstance
					.getId());
			Search search = new Search();
			search.addFilterEqual("processInstanceId", historicProcessInstance
					.getId());
			List<HistoricTaskInstance> historicTasks = historyService
			.createHistoricTaskInstanceQuery().processInstanceId(
					processInstanceId).orderByTaskId().desc().listPage(
							0, 1);
			if (historicTasks != null & historicTasks.size() > 0) {
				troubleTicketModel
				.setStatusName(historicTasks.get(0).getName());
				troubleTicketModel.setTaskDefKey(historicTasks.get(0)
						.getTaskDefinitionKey());
				troubleTicketModel.setTaskId(historicTasks.get(0).getId());
			}
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if (list != null && list.size() != 0) {
				troubleTicketModel.setSendTime(list.get(0).getSubmitApplicationTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
				troubleTicketModel.setInitiator(list.get(0).getInitiator());
				troubleTicketModel.setOneInitiator(list.get(0).getOneInitiator());
				troubleTicketModel.setTwoInitiator(list.get(0).getSecondInitiator());
				troubleTicketModel.setCreatePerson(list.get(0).getCreateWork());
				troubleTicketModel.setCountryPerson(list.get(0).getCountryCSJ());
				troubleTicketModel.setCityCSJ(list.get(0).getCityCSJ());
				troubleTicketModel.setCitySGS(list.get(0).getCityGS());
				troubleTicketModel.setCityLineExamine(list.get(0).getCityCSJ());
				troubleTicketModel.setCityManageExamine(list.get(0).getCityGS());
				troubleTicketModel.setDaiwei(list.get(0).getInitiator());
				int state = list.get(0).getState();
			
				if(state==8 && troubleTicketModel.getTaskDefKey().equals(TASK_CITYMANAGEEXAMINE)){
							troubleTicketModel.setStatusName(TASK_CITYMANAGEEXAMINE_STATUS);
				 }else if(state==8 && troubleTicketModel.getTaskDefKey().equals(TASK_PROVINCEMANAGEEXAMINE)){
							troubleTicketModel.setStatusName(TASK_PROVINCEMANAGEEXAMINE_STATUS);										
			     }
				
				String message = pnrTransferOfficeService.getLoginPersonStatusToNewPrecheck(
						userId, list.get(0));
				troubleTicketModel.setPersonStatus(message);
				TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
						.get(0).getTaskAssignee(), "");
				if (pu != null) {
					troubleTicketModel.setDeptId(pu.getDeptid());
					
				}
			}
			
			rPnrTicketList.add(troubleTicketModel);
		}
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("getBack");
	}

	
	public ActionForward troublePhotos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String processInstanceId = request.getParameter("pid");
    IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService)getBean("pnrAndroidWorkOderPhotoFileService");

    Search search = new Search();
    search.addFilterEqual("picture_id", processInstanceId);
    search.addSortAsc("createTime");

    List list = pnrService.search(search);

    request.setAttribute("list", list);
    return mapping.findForward("troublePhotos");
  }
	
	/**
	 * request得到传输局对象
	 * 
	 * @author wangyue
	 * @title: setTransferOfficeByRequest
	 * @date Nov 10, 2014 4:08:01 PM
	 * @param request
	 * @param pnrTransferOffice
	 * @throws ParseException
	 *             void
	 */
	private void setTransferOfficeByRequest(HttpServletRequest request,
			PnrTransferOffice pnrTransferOffice) throws ParseException {

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 第一派单人
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		// 工单类型
		String workOrderType = StaticMethod.nullObject2String(request
				.getParameter("workOrderType"));
		// 工单子类型
		String subType = request.getParameter("subType");
		// 关键字
		String keyWord = StaticMethod.nullObject2String(request
				.getParameter("keyWord"));
		// 预检预修提交申请时间
		String mainFaultOccurTime = request.getParameter("mainFaultOccurTime");
		// 紧急程度
		String workOrderDegree = StaticMethod.nullObject2String(request
				.getParameter("workOrderDegree"));
		// 故障描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 干线类型
		String workType = StaticMethod.nullObject2String(request
				.getParameter("workType"));
		// 项目金额估算
		String projectAmount = StaticMethod.nullObject2String(request
				.getParameter("projectAmount"));
		// 接收人
		String taskAssignee = "";
		// 接收人字符串
		String taskAssigneeJSON = "";
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 新增附件
		String attachments = request.getParameter("sheetAccessories");
		int attachmentsNum = 0;
		if (attachments != null && attachments.length() > 0) {

			attachmentsNum = attachments.split(",").length;
		}

		String processInstanceId = request.getParameter("processInstanceId");

		pnrTransferOffice.setId(themeId);
		// 主题
		pnrTransferOffice.setTheme(title);
		// 第一派发人
		pnrTransferOffice.setInitiator(initiator);
		pnrTransferOffice.setOneInitiator(initiator);
		pnrTransferOffice.setCreateWork(initiator);
		// 预检预修提交申请时间
		pnrTransferOffice.setSubmitApplicationTime(sFormat
				.parse(mainFaultOccurTime));
		// 描述
		pnrTransferOffice.setFaultDescription(mainRemark);
		pnrTransferOffice.setTaskAssigneeJSON(taskAssigneeJSON);
		// 工单类型
		pnrTransferOffice.setWorkOrderType(workOrderType);
		pnrTransferOffice.setSubType(subType);
		pnrTransferOffice.setSubTypeName(CommonUtils.getId2NameString(subType,1, ","));
		pnrTransferOffice.setWorkOrderTypeName(CommonUtils.getId2NameString(workOrderType,1, ","));
		// 紧急程度
		pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
		// 关键字
		pnrTransferOffice.setKeyWord(keyWord);
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getCityCompany());
		String cityGS = "superUser";
		if(list!=null && list.size()>0){
			if(list.get(0).get("USERID")!=null && !"".equals(list.get(0).get("USERID"))){
				cityGS = list.get(0).get("USERID").toString();
			}
		}
		List<Map> cityCSJList = pnrTransferPrecheckService.getCityCSJByCountryCSJ(initiator);
		String cityCSJ = "superUser";
		if(cityCSJList!=null &&cityCSJList.size()>0){
			if(cityCSJList.get(0).get("CITY_CSJ_ID")!=null && !"".equals(cityCSJList.get(0).get("CITY_CSJ_ID"))){
				cityCSJ = cityCSJList.get(0).get("CITY_CSJ_ID").toString();
			}
		}
		// 接收人
		pnrTransferOffice.setTaskAssignee(cityCSJ);
		pnrTransferOffice.setCountryCSJ(initiator);
		
		pnrTransferOffice.setCityCSJ(cityCSJ);
		pnrTransferOffice.setCityGS(cityGS);
		//版本标志：1 代表新预检预修工单，旧预检预修工单为空
		pnrTransferOffice.setVersionFlag("1");

		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		
		pnrTransferOffice.setWorkType(workType);
		pnrTransferOffice.setProjectAmount(Double.parseDouble(projectAmount));
		pnrTransferOffice.setSendTime(new Date());

	}
	/**
	 * display attachments
	 */
	public ActionForward showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		pnrTransferOfficeService.showSheetAccessoriesList(mapping, form, request, response);
		
		return mapping.findForward("sheetAccessories");
	}
	
	/**
	 * downLoad attachments
	 */
	public ActionForward downLoadSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		pnrTransferOfficeService.showSheetAccessoriesList(mapping, form, request, response);
		
		return mapping.findForward("downLoadSheetAccessories");
	}
	
	
	/**
	 * 根据紧急程度id，获取紧急程度汉字
	 * @param dictId
	 * @return
	 */
	public String getDictNameByDictid(String dictId){
		String msg="";
		if("101230901".equals(dictId)){
			msg="特急";
		}else if("101230902".equals(dictId)){
			msg="紧急";
			
		}else if("101230903".equals(dictId)){
			msg="一般";
		}
		return msg;
	}
	/**
	 * 获取回复信息
	 * @param transferHandleService
	 * @param search
	 * @param handlelist
	 * @return
	 */
	private Map<String, Object> getHandleList(Map<String, Object> gMap,IPnrTransferOfficeHandleService transferHandleService,Search search,List<PnrTransferOfficeHandle> handlelist,int handleSize ){
	
		SearchResult<PnrTransferOfficeHandle> results = transferHandleService
				.searchAndCount(search);
		handleSize = results.getTotalCount();
		if (handleSize > 0) {
			handlelist = results.getResult();

		}		
		
		gMap.put("size", handleSize);
		gMap.put("list", handlelist);
		
		return gMap;
		
	}
	
	/**
	 * 打开省运维审批，批量处理子窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchApproveView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 
		return mapping.findForward("openBatchApproveView");
	}
	
	/**
	 * 省运维审批-批量审批
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
			PrintWriter writer = response.getWriter();
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
			String userId = sessionform.getUserid();
			//当前所处环节
			String linkName = request.getParameter("linkName");
			//审批人
			String teskAssignee = request.getParameter("teskAssignee");
			//单位
			String companyName = request.getParameter("companyName");
			//电话
			String telephone = request.getParameter("telephone");
			//简要描述
			String dealPerformer = request.getParameter("dealPerformer");
			//工单号+任务ID
			String selectedIDs=request.getParameter("selectedIDs");	
			
			
			String[] split = selectedIDs.split("#");			
			for(int i=0;i<split.length;i++){
				String tempValues=split[i];
				String[] split2 = tempValues.split(",");
				//String taskId=split2[0];
				String processInstanceId=split2[1];
				
				//保存流程信息
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setProcessInstanceId(processInstanceId);
				entity.setAuditor(userId);
				Date createTime = new Date();// 审批时间
				entity.setCheckTime(createTime);
				entity.setCompany(companyName);
				entity.setHandleDescription(dealPerformer);
				entity.setDoPerson(teskAssignee);
				entity.setTelephone(telephone);
				entity.setLinkName(linkName);
				transferHandleService.save(entity);
				
				//更新主表状态
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
						.search(search);
				PnrTransferOffice pnrTicket = null;
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}
				pnrTicket.setState(8);
				pnrTransferOfficeService.save(pnrTicket);
				
			}
		
			writer.print("true");
		
		return null;
	}
	
	/**
	 * 修改项目金额估算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateProjectAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 String processInstanceId=request.getParameter("processInstanceId");
		 String theme=request.getParameter("theme");
		 String themeId=request.getParameter("themeId");
		
		
		//这里需要调接口,解析json串
		//String returnJson="{state=success,info=操作成功}";
		
		String result="success";//结果值需要根据json解析出来的
		String returnResult="";//返回给jquery的结果
		
		
		if(result.equals("success")){
			try {
				//更新主表中的state为0
//				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//				PnrTransferOffice pnrTransferOffice = pnrTransferOfficeService.find(themeId);
//				pnrTransferOffice.setState(0);
//				pnrTransferOfficeService.save(pnrTransferOffice);
			returnResult="success";
			} catch (Exception e) {
				returnResult="调用接口成功，但更新表中状态异常";
				e.printStackTrace();
			}
		}else if(result.equals("error")){
			returnResult="调用接口失败！";
		}	
		
		PrintWriter out = response.getWriter();
		out.print(returnResult);
		
		return null;
	}

}
