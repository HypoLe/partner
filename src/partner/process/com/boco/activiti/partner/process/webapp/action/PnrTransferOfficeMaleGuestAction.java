package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.CycleCollarReportModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneStatisticsModel;
import com.boco.activiti.partner.process.po.MaterialQuantityModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.service.INetResInspectService;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.CurrentThead;
import com.boco.eoms.partner.process.util.MaleGuestFlagThead;
import com.boco.eoms.partner.process.util.MaleGuestReturnThead;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description: 故障处理工单ACTION
 */

public class PnrTransferOfficeMaleGuestAction extends SheetAction {
	private final String processDefinitionKey = "myMaleGuestProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	// 自维人员审核通过
	private final static String AUDIT_STATE_THROUGH = "handle";
	// 自维人员审核驳回
	private final static String AUDIT_STATE_DISMISS = "rollback";
	// 归档状态
	private final static Integer ARCHIVE_STATE = 5;
	// 环节--“传输局”
	private final static String TASK_TRANSFERTASK = "auditor";
	// 环节--“施工队”
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 环节--“外包公司”
	private final static String TASK_EPIBOLYTASK = "epibolyTask";
	// 工单删除状态
	private final static Integer TASK_DELETE_STATE = 1;
	// 发信息用的常量
	private final static String TASK_NO_STR = "工单号:";
	private final static String TASK_TITLE_STR = "主题:";
	private final static String TASK_DEADLINE_STR = "截止时间:";
	private final static String TASK_CONTACT_STR = "联系人及电话:";
	
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");

	/**
	 * 新增页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("new");
	}

	/**
	 * 保存工单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 默认为0，正常派单。
		int state = 0;

		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();

		setTransferOfficeByRequest(request, pnrTransferOffice);

		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");

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

		TaskFormData taskFormData = formService.getTaskFormData(taskId);

		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}

		formService.submitTaskFormData(taskId, map);
		// 流程结束

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveOrUpateAttachment(processInstanceId,
					attachments);
		}
		// attachment--end
		processTaskService.setTvalueOfTask(processInstanceId,pnrTransferOffice.getTaskAssignee(), pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTransferOffice);
		

		// 判断是否是接口派单,是就进行接口通知
		String maleGuestNumber = pnrTransferOfficeService
				.getMaleGuestNumberByThemeId(themeId);
		if (maleGuestNumber != null
				&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
			// 调用方法,查询接口需要的数据
			TransferMaleGuestFlag maleGuestFlag = pnrTransferOfficeService
					.getMaleGuestData(maleGuestNumber);
			Thread aThread = new Thread(new MaleGuestFlagThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_STATUS_METHOD, maleGuestFlag));
			aThread.start();
		}
		// 发短信--start
		String taskNo = pnrTransferOffice.getProcessInstanceId();
		String title = pnrTransferOffice.getTheme();
		String endTime = pnrTransferOffice.getEndTime() == null ? "" : sFormat
				.format(pnrTransferOffice.getEndTime());
		String contact = pnrTransferOffice.getConnectPerson();
		String taskAssignee = pnrTransferOffice.getTaskAssignee();
		String msg = TASK_NO_STR + taskNo + ";" + TASK_TITLE_STR + title + ";"
				+ TASK_DEADLINE_STR + endTime;
		msg += TASK_CONTACT_STR + contact;
		System.out.println(msg);
		CommonUtils.sendMsg(msg, taskAssignee);
		// 发短信--end

		return mapping.findForward("success");

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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		// 工单管理-"公客-传输局工单"-待回复工单 集合条数
		int total = 0;
			total = pnrTransferOfficeService
					.getToreplyJobOfMaleGuestTransmissionBureauJobCount(userId,
							selectAttribute);

		// 工单管理-"公客-传输局工单"-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
			rPnrTransferList = pnrTransferOfficeService
					.getToreplyJobOfMaleGuestTransmissionBureauJobList(userId,
							selectAttribute,
							firstResult, endResult, pageSize);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("backlogList");
	}

	/**
	 * 故障回复
	 */
	public ActionForward todo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String queryFlag = StaticMethod.nullObject2String(request.getParameter("queryFlag"));//查询标识
		request.setAttribute("queryFlag", queryFlag);
		
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
		
		if("".equals(taskId)||"-".equals(taskId)){
			request.setAttribute("msg", "错误编号：sd-taskId-004;工单有问题,请联系管理员");
			return mapping.findForward("failure");
		}
		
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
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			search.addSort("receiveTime", true);// 按回复时间排序

			SearchResult<PnrTransferOfficeHandle> results = transferHandleService
					.searchAndCount(search);

			int handleSize = results.getTotalCount();
			List<PnrTransferOfficeHandle> handlelist = null;
			if (handleSize > 0) {
				handlelist = results.getResult();

			}

			request.setAttribute("pnrTransferOffice", list.get(0));

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();

			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceId(processInstanceId);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain", ticket);
			String ss = task.getTaskDefinitionKey();
			// attachments-end
			if (task.getTaskDefinitionKey().equalsIgnoreCase("auditor")) {// 施工队处理
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

					request.setAttribute("auditor", list.get(0).getInitiator());
				}
							//限制下 若是 测试地市的账号则进入 新的页面
				String page = "transferHandle";
//				if(CommonUtils.isHaveCountry(list.get(0).getCountry())){//抢修归集测试区县（济南市中区） 2016-04-11 
			//if(1==1){
					String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
//					 areaid ="282601";						
					String acheckAssignee = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");
					if("".equals(acheckAssignee)){
						acheckAssignee="superUser";
					}
					
					//回显场景模板信息
					ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
					Map<String, Object> resultMap = sceneTemplateService.echoChildScene(processInstanceId,"auditor",null);
					request.setAttribute("mainSceneList", resultMap.get("mainSceneList"));
					request.setAttribute("childSceneWholeModelList", resultMap.get("childSceneWholeModelList"));
					request.setAttribute("sceneDetailWholeModelList", resultMap.get("sceneDetailWholeModelList"));
					request.setAttribute("totalAmount", resultMap.get("totalAmount"));
					request.setAttribute("auditorSceneExistFlag",resultMap.get("auditorSceneExistFlag").toString());
					
					request.setAttribute("acheckAssignee",acheckAssignee);
					page = "transferHandleChange";
//				}				
			return mapping.findForward(page);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"newMaleGuest")) {
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferAudit")) {// 传输局质检
				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getOneInitiator());
				}
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("secondAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"epibolyAudit")) {// 外包质检

				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getSecondInitiator());
				}
				showReplySetAttribute(request, handlelist);
				return mapping.findForward("epibolyAudit");
			}
		}

		return mapping.findForward("error");

	}

	/**
	 * 自维任务审核保存
	 */
	public ActionForward goTransferCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 返回结果
		String returnResult = StaticMethod.nullObject2String(request
				.getParameter("returnResult"));
		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("auditState"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("initiator"));

		String taskId = request.getParameter("taskId");

		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", taskList.get(0)
				.getProcessInstanceId());
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (auditState.equals(AUDIT_STATE_DISMISS)) {
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
				auditor = pnrTicket.getTaskAssignee();
			}

		} else if (auditState.equals(AUDIT_STATE_THROUGH)) {
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTicket = pnrTicketList.get(0);
				pnrTicket.setArchivingTime(new Date());
				pnrTicket.setState(ARCHIVE_STATE);
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
		if (auditState.equals("rollback")) {// 回退时的说法
			map.put("taskAssignee", auditor);
		}
		formService.submitTaskFormData(taskId, map);
		// 保存审核意见
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(auditor);
		entity.setReturnResult(returnResult);
		transferHandleService.save(entity);
		// 保存审核意见--end
		// 判断是否是接口派单,是就进行接口通知
		String maleGuestNumber = pnrTransferOfficeService
				.getMaleGuestNumberByThemeId(themeId);
		if (maleGuestNumber != null
				&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
			// 调用方法,查询接口需要的数据
			TransferMaleGuestFlag maleGuestFlag = pnrTransferOfficeService
					.getMaleGuestData(themeId);
			TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
					.getMaleGuestReturnData(themeId);
			Thread aThread = new Thread(new CurrentThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_STATUS_METHOD, maleGuestFlag));
			Thread bThread = new Thread(new CurrentThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_STATUS_METHOD, maleGuestReturn));
			aThread.start();
			bThread.start();
		}

		request.setAttribute("success", "check");

		return mapping.findForward("success");
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
	/*
	 * 16-03-29 改变流程图之后的回单方法，为了实施测试，希望没有参与测试的工单都正常
	 */
		public ActionForward transferHandleChange(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			// 发短信
			String deadLineTime = "", contact = "";

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
//			String auditor = StaticMethod.nullObject2String(request
//					.getParameter("auditor"));

			// 回单时间
			Date createTime = new Date();
			// 处理描述
//			String mainRemark = StaticMethod.nullObject2String(request
//					.getParameter("mainRemark"));
			// 故障原因
			//String faultCause = request.getParameter("faultCause");
			// 处理人
			String dealPerformer2 = request.getParameter("dealPerformer2");

			String taskId = StaticMethod.nullObject2String(request
					.getParameter("taskId"));

			String processInstanceId = StaticMethod.nullObject2String(request
					.getParameter("processInstanceId"));
			String totalAmount = StaticMethod.nullObject2String(request
					.getParameter("totalAmount"),"0");
			String makeupFlag = StaticMethod.nullObject2String(request.getParameter("makeupFlag"));			
			String acheckAssignee = StaticMethod.nullObject2String(request.getParameter("acheckAssignee"));//一次核验人
			
			//故障原因
			//String faultHandle = StaticMethod.nullObject2String(request.getParameter("faultHandle"));
			String faultType = StaticMethod.nullObject2String(request.getParameter("faultType"));//故障类型
			String troubleReason = "";
			if("10123190101".equals(faultType)){ //故障工单
				 troubleReason = StaticMethod.nullObject2String(request.getParameter("troubleReason1"));//故障原因
			}else if("10123190102".equals(faultType)){ //非故障工单
				 troubleReason = StaticMethod.nullObject2String(request.getParameter("troubleReason2"));//故障原因
			}
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			PnrTransferOffice pnrTransferOffice = null;
			
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
			
			//公客工单处理之后流程停留在施工队，但工单状态改变--隐藏工单
			formService.submitTaskFormData(taskId, map);
			if (pnrTicketList != null) {
				pnrTransferOffice = pnrTicketList.get(0);
				pnrTransferOffice.setLastReplyTime(new Date());
				pnrTransferOffice.setMakeupPhotoFlag(makeupFlag);//补录照片标识
				//改变工单状态，在待回复中不在显示
//				pnrTransferOffice.setState(8);
				//新增将流转环节添加到主表中 start
//				processTaskService.setTvalueOfTask(processInstanceId,auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				processTaskService.setTvalueOfTask(processInstanceId,acheckAssignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				//新增将流转环节添加到主表中 end
				pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(totalAmount));//场景模板金额--回单环节
				pnrTransferOffice.setRollbackFlag("0");
				
				//获取主场景和子场景的中文值，用于列表中的主场景和子场景的显示
				Map<String, String> mainScenesAndCopyScenesName = pnrTransferOfficeService.getMainScenesAndCopyScenesName(request, null);
				pnrTransferOffice.setTransferMainScenesName(mainScenesAndCopyScenesName.get("mainScenesName"));
				pnrTransferOffice.setTransferCopyScenesName(mainScenesAndCopyScenesName.get("copyScenesName"));
				pnrTransferOffice.setRecentMainScenesName(mainScenesAndCopyScenesName.get("mainScenesName"));
				pnrTransferOffice.setRecentCopyScenesName(mainScenesAndCopyScenesName.get("copyScenesName"));
				
				//往主表里添加四个字段
				pnrTransferOfficeService.save(pnrTransferOffice);

				deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
						: sFormat.format(pnrTransferOffice.getEndTime());
				contact = pnrTransferOffice.getConnectPerson();
			}
			
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
			entity.setTheme(title);
			entity.setThemeId(titleId);
			entity.setReceivePerson(userId);
			entity.setAuditor(acheckAssignee);
			entity.setDoPersons(dealPerformer2);
			entity.setReceiveTime(createTime);
			//entity.setHandleDescription(mainRemark);
			entity.setFaultCause("2044");//faultCause 故障原因---固定：光缆故障 2044 ---接口为了回复公客系统的接口 -必填
			entity.setProcessInstanceId(processInstanceId);
			entity.setTaskId(taskId);
			//entity.setFaultHandle(faultHandle);//故障原因
			entity.setFaultType(faultType);//故障类型
			entity.setTroubleReason(troubleReason);//故障原因

			entity.setCheckTime(createTime);
			entity.setLinkName("auditor");
			entity.setHandleDescription("web端提交");
			entity.setOperation("01");
			// 处理人关系表数据保存--end
			transferHandleService.save(entity);		
			
			// 流程提交到下一级-end		
//			场景模板的数据存储
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
			Map<String,String> param = new HashMap<String,String>();
			param.put("processType", "maleGuest");//流程类型：公客
			sceneTemplateService.saveSceneTemplate(request,processInstanceId, "auditor", param);
			
			// 判断是否是接口派单,是就进行接口通知
			String maleGuestState = pnrTransferOffice.getMaleGuestState();	
			TransferMaleGuestReturn maleGuest  = new TransferMaleGuestReturn();			
			String fault_Name="光缆故障-宽带";
			String faultCause ="2044";
			
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			maleGuest.setConfCRMTicketNo(pnrTransferOffice.getMaleGuestNumber());	
			// 回单标示
			maleGuest.setFlag("1");	
			// 回单时间
			maleGuest.setBack_dt(sFormat.format(createTime));
			// 处理人姓名id
			maleGuest.setBack_userid(userId);
			// 处理人姓名
			maleGuest.setBack_username(sessionform.getUsername());
			// 故障原因id
			maleGuest.setReason_id(faultCause);
			// 描述
			//maleGuest.setBack_info(mainRemark);		
			maleGuest.setBack_info("已处理");		
			// 故障原因
			maleGuest.setReason_name(fault_Name);
			pnrTransferOfficeService.maleGuestHandleInterfaceCall(maleGuest,maleGuestState,processInstanceId,titleId);
			/*String maleGuestNumber = pnrTransferOfficeService
					.getMaleGuestNumberByThemeId(titleId);
			if (maleGuestNumber != null
					&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
				// 调用方法,查询接口需要的数据
				TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
						.getMaleGuestReturnData(maleGuestNumber);
				Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
						.format(new Date()),
						CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
				aThread.start();
			}*/

			String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
					+ title + ";";
			msg += TASK_DEADLINE_STR + deadLineTime + ";" + TASK_CONTACT_STR
					+ contact + ";进入一个环节";
			CommonUtils.sendMsg(msg, acheckAssignee);

			return mapping.findForward("success");
		}

	/**
	 * 处理保存--施工队
	 */
	public ActionForward transferHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 发短信
		String deadLineTime = "", contact = "";

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
				.getParameter("auditor"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障原因
		String faultCause = request.getParameter("faultCause");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice = null;
		
		// 处理人关系表数据保存--start
		/*
		 * String[] personStrings = dealPerformer2.split(",");
		 * pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
		 * personStrings);
		 */
		
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
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
		
		//公客工单处理之后流程停留在施工队，但工单状态改变--隐藏工单
		//formService.submitTaskFormData(taskId, map);
		if (pnrTicketList != null) {
			pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			
			//改变工单状态，在待回复中不在显示
			pnrTransferOffice.setState(8);
			//新增将流转环节添加到主表中 start
			processTaskService.setTvalueOfTask(processInstanceId,auditor, pnrTransferOffice, PnrTransferOffice.class, "archive", "归档",true);
			//新增将流转环节添加到主表中 end
			pnrTransferOfficeService.save(pnrTransferOffice);

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setFaultCause(faultCause);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		// 处理人关系表数据保存--end
		transferHandleService.save(entity);
		
		// 流程提交到下一级-end
		// 判断是否是接口派单,是就进行接口通知
		String maleGuestNumber = pnrTransferOfficeService
				.getMaleGuestNumberByThemeId(titleId);
		if (maleGuestNumber != null
				&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
			// 调用方法,查询接口需要的数据
			TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
					.getMaleGuestReturnData(maleGuestNumber);
			Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
			aThread.start();
		}

		String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
				+ title + ";";
		msg += TASK_DEADLINE_STR + deadLineTime + ";" + TASK_CONTACT_STR
				+ contact + ";已归档";
		CommonUtils.sendMsg(msg, auditor);

		return mapping.findForward("success");

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
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		//页面条件接收--start
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		//页面条件接收--end
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//公客故障工单--由我创建--总数据集合--包括完成和未完成
		List<TaskModel> list = pnrTransferOfficeService.getMaleGuestByInitiator(userId, selectAttribute, firstResult, endResult, pageSize);
		////公客故障工单--由我创建--集合总数--包括完成和未完成
		long total = pnrTransferOfficeService.getMaleGuestCountByInitiator(userId, selectAttribute);
		
		request.setAttribute("taskList", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		request.setAttribute("selectAttribute", selectAttribute);
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
	 * 回退任务
	 * 
	 * @return
	 */
	public ActionForward rollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String initiator = request.getParameter("initiator");

		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		
		String processInstanceId = request.getParameter("processInstanceId");

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();

		if (handle.equals(TASK_TRANSFERHANDLE)) {

			map.put("initiator", initiator);
			map.put("handleState", "rollback");

		}

		formService.submitTaskFormData(taskId, map);
		
		
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice = null;
		if (pnrTicketList != null) {
			pnrTransferOffice = pnrTicketList.get(0);
		}
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, initiator, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		//新增将流转环节添加到主表中 end
		
		pnrTransferOfficeService.save(pnrTransferOffice);
		
		
		return mapping.findForward("success");
	}

	/**
	 * 按照流程实例ID删除流程
	 */
	public ActionForward removeProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		runtimeService.deleteProcessInstance(processInstanceId, "delete");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTransferOffice> tResult = pnrTransferOfficeService
				.searchAndCount(search);
		List<PnrTransferOffice> eList = tResult.getResult();
		PnrTransferOffice entity = eList.get(0);
		entity.setState(TASK_DELETE_STATE);
		pnrTransferOfficeService.save(entity);

		return mapping.findForward("success");
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
		List<HistoricProcessInstance> historicProcessInstancesList = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey(
						processDefinitionKey).involvedUser(userId).finished()
				.listPage(firstResult * pageSize, endResult * pageSize);
		long total = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey)
				.involvedUser(userId).finished().count();

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();

		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
			String processInstanceId = historicProcessInstance.getId();
			TaskModel transferModel = new TaskModel();
			transferModel.setProcessInstanceId(historicProcessInstance.getId());
			Search search = new Search();
			search.addFilterEqual("processInstanceId", historicProcessInstance
					.getId());
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							processInstanceId).orderByTaskId().desc().list();
			if (historicTasks != null & historicTasks.size() > 0) {
				if (historicTasks.get(0).getDeleteReason().equals("delete")) {
					transferModel.setStatusName(CommonUtils.taskDetele);

				} else {

					transferModel.setStatusName(CommonUtils.taskComplete);
				}
			}
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if (list != null && list.size() != 0) {
				transferModel.setSendTime(list.get(0).getSendTime());
				transferModel.setTheme(list.get(0).getTheme());
				transferModel.setInitiator(list.get(0).getInitiator());
				transferModel.setInstallAddress(list.get(0).getInstallAddress());
				transferModel.setBarrierNumber(list.get(0).getBarrierNumber());
				TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
						.get(0).getTaskAssignee(), "");
				if (pu != null) {
					transferModel.setDeptId(pu.getDeptid());
				}
				if ("maleGuest".equals(list.get(0).getThemeInterface())) {

					rPnrTicketList.add(transferModel);
				}
			}

		}
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("involvedProcessInstancesList");
	}

	/**
	 * 用户参已经未完成的流程
	 * 
	 * @return
	 */
	public ActionForward listInvolvedUnfinishedProcessInstances(
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
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//页面条件接收--start
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		
		
		//公客故障工单--已处理和归档集合
		List<TaskModel> taskList = pnrTransferOfficeService.getMaleGuestByIntiatorOrAssignee(userId, selectAttribute, firstResult, endResult, pageSize);
		//公客故障工单--已处理和归档集合总数
		long total = pnrTransferOfficeService.getMaleGuestCountByInitiatorOrAssignee(userId, selectAttribute);
		request.setAttribute("taskList", taskList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		request.setAttribute("selectAttribute",selectAttribute);
		return mapping.findForward("involvedProcessInstancesList");
	}

	/**
	 * 列表查询提交
	 */
	public ActionForward performListQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * String beanName = mapping.getAttribute(); beanName = beanName +
		 * "List"; //ISheetListService baseSheet = (ISheetListService)
		 * getBean(beanName); baseSheet.performListQuery(mapping, form, request,
		 * response); String findForward =
		 * StaticMethod.nullObject2String(request.getParameter("findForward"));
		 * //车辆调度 request.setAttribute("carApprove",
		 * StaticMethod.null2String(request.getParameter("carApprove")));
		 */
		return mapping.findForward("new");
	}

	public ActionForward troublePhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processInstanceId = request.getParameter("pid");
		IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService) getBean("pnrAndroidWorkOderPhotoFileService");

		Search search = new Search();
		search.addFilterEqual("picture_id", processInstanceId);
		search.addSortAsc("createTime");

		List list = pnrService.search(search);

		request.setAttribute("list", list);
		return mapping.findForward("troublePhotos");
	}

	/**
	 * ----------------------------------------------------------------- 根据
	 * request得到传输局对象
	 * 
	 * @throws ParseException
	 */

	private void setTransferOfficeByRequest(HttpServletRequest request,
			PnrTransferOffice pnrTransferOffice) throws ParseException {

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 派单人
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		// 派单时间
		Date createTime = new Date();
		// 处理时限
		String sheetCompleteLimit = StaticMethod.nullObject2String(request
				.getParameter("sheetCompleteLimit"));
		// 故障来源
		String faultSource = StaticMethod.nullObject2String(request
				.getParameter("faultSource"));
		// 故障发生时间
		String mainFaultOccurTime = request.getParameter("mainFaultOccurTime");
		// 工单子类型
		String mainFaultNet = request.getParameter("mainFaultNet");
		// 故障联系人+手机号
		String mainpeople = StaticMethod.nullObject2String(request
				.getParameter("mainpeople"));
		// 故障描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 接收人
		String taskAssignee = request.getParameter("taskAssignee");
		// 工单种类
		String themeInterface = request.getParameter("themeInterface");
		// 处理结束时间
		String dueDate = request.getParameter("dueDate");
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 新增附件
		// String attachments = request.getParameter("sheetAccessories");
		// int attachmentsNum = 0;
		/*
		 * if (attachments != null && attachments.length() > 0) {
		 * 
		 * attachmentsNum = attachments.split(",").length; }
		 */
		// 后添加项
		// 接机员
		String engineer = StaticMethod.nullObject2String(request
				.getParameter("engineer"));
		// 装机地址
		String installAddress = StaticMethod.nullObject2String(request
				.getParameter("installAddress"));
		// 接入方式
		String pattern = StaticMethod.nullObject2String(request
				.getParameter("pattern"));
		// 地市
		String city = StaticMethod.nullObject2String(request
				.getParameter("city"));
		// 局站名称
		String stationName = StaticMethod.nullObject2String(request
				.getParameter("stationName"));
		// 交接箱名称
		String spliceBoxName = StaticMethod.nullObject2String(request
				.getParameter("spliceBoxName"));
		// 主干电缆编码
		String cableNumber = StaticMethod.nullObject2String(request
				.getParameter("cableNumber"));
		// 分线盒编码
		String branchBoxNumber = StaticMethod.nullObject2String(request
				.getParameter("branchBoxNumber"));
		// 一级分光器编码
		String firstOpticalNumber = StaticMethod.nullObject2String(request
				.getParameter("firstOpticalNumber"));
		// 一级分光器端口
		String firstOpticalPort = StaticMethod.nullObject2String(request
				.getParameter("firstOpticalPort"));
		// 二级分光器编码
		String secondOpticalNumber = StaticMethod.nullObject2String(request
				.getParameter("secondOpticalNumber"));
		// 二级分光器端口
		String secondOpticalPort = StaticMethod.nullObject2String(request
				.getParameter("secondOpticalPort"));
		// 光交接箱编码
		String spliceBoxNumber = StaticMethod.nullObject2String(request
				.getParameter("spliceBoxNumber"));
		// 光交接箱端口
		String spliceBoxPort = StaticMethod.nullObject2String(request
				.getParameter("spliceBoxPort"));
		// 后添加项结束

		String processInstanceId = request.getParameter("processInstanceId");

		pnrTransferOffice.setId(themeId);
		pnrTransferOffice.setTheme(title);
		pnrTransferOffice.setInitiator(initiator);

		pnrTransferOffice.setOneInitiator(initiator);

		pnrTransferOffice.setCreateTime(sFormat.parse(mainFaultOccurTime));
		pnrTransferOffice
				.setSendTime(sFormat.parse(sFormat.format(createTime)));
		// 接收人
		pnrTransferOffice.setTaskAssignee(taskAssignee);
		pnrTransferOffice.setConnectPerson(mainpeople);
		if ("".equals(sheetCompleteLimit)) {
			sheetCompleteLimit = "2";// 如果程序出现错误，默认处理时限2小时。
		}
		pnrTransferOffice.setProcessLimit(Double
				.parseDouble(sheetCompleteLimit));
		pnrTransferOffice.setFaultSource(faultSource);
		pnrTransferOffice.setFaultDescription(mainRemark);
		pnrTransferOffice.setSubType(mainFaultNet);
		pnrTransferOffice.setTaskAssigneeJSON(taskAssigneeJSON);
		pnrTransferOffice.setThemeInterface(themeInterface);

		pnrTransferOffice.setEndTime(sFormat.parse(dueDate));

		// 后添加项
		pnrTransferOffice.setEngineer(engineer);
		pnrTransferOffice.setInstallAddress(installAddress);
		pnrTransferOffice.setPattern(pattern);
		pnrTransferOffice.setCity(city);
		pnrTransferOffice.setSpliceBoxName(spliceBoxName);
		pnrTransferOffice.setStationName(stationName);
		pnrTransferOffice.setCableNumber(cableNumber);
		pnrTransferOffice.setBranchBoxNumber(branchBoxNumber);
		pnrTransferOffice.setFirstOpticalNumber(firstOpticalNumber);
		pnrTransferOffice.setFirstOpticalPort(firstOpticalPort);
		pnrTransferOffice.setSecondOpticalNumber(secondOpticalNumber);
		pnrTransferOffice.setSecondOpticalPort(secondOpticalPort);
		pnrTransferOffice.setSpliceBoxNumber(spliceBoxNumber);
		pnrTransferOffice.setSpliceBoxPort(spliceBoxPort);
		// 附件个数
		// pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);

	}

	/**
	 * 显示回复的附件！
	 * 
	 * @param request
	 * @param handlist
	 */
	private void showReplySetAttribute(HttpServletRequest request,
			List<PnrTransferOfficeHandle> handlist) {
		int handSize = handlist.size();
		if (handSize > 0) {
			for (int i = 0; i < handSize; i++) {
				request.setAttribute("sheetReply" + i, handlist.get(i));
			}
		}
	}

	/**
	 * 显示工单查询页面
	 */
	public ActionForward showQueryPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		//导出
		String exportType = StaticMethod.null2String(request
				.getParameter(new org.displaytag.util.ParamEncoder("taskList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		System.out.println("------exportType="+exportType);
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");
		String status = request.getParameter("status");

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setStatus(status);
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		int total = 0;
		
		List<TaskModel> rPnrTransferList = null;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			try {
				total = pnrTransferOfficeService.getMaleGuestWorkOrderQueryCount(areaid, userId, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rPnrTransferList = pnrTransferOfficeService.getMaleGuestWorkOrderQueryList(areaid, userId, conditionQueryModel, firstResult, endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("tishiFlag","Y");//没选查询条件时，给出提示
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("region", region);
		request.setAttribute("country", country);
		request.setAttribute("wsStatus", status);
		
		return mapping.findForward("workOrderQueryTransfer");
	}

	public ActionForward workOrderQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 2013-12-18 add 区县筛选条件
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		String userId = sessionForm.getUserid();
		String candidateGroup = request.getParameter("candidateGroup");
		if (candidateGroup != null && candidateGroup.length() > 0) {
			deptId = candidateGroup;
		}

		// 2013-12-18 end
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");
		String taskType = request.getParameter("taskType");
		String city = request.getParameter("mainCity");
		String theme = request.getParameter("title").trim();
		String workerid = request.getParameter("workerid");
		String searchRange = request.getParameter("searchRange");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String subType = request.getParameter("mainFaultNet");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		List<TaskModel> taskModels = pnrTransferOfficeService
				.workOrderMaleGuestQuery(searchRange, userId, "maleGuest",
						deptId, workerid, beginTime, endTime, subType, theme,
						city, firstResult * pageSize, endResult * pageSize);
		int total = pnrTransferOfficeService.workOrderMaleGuestCount(
				searchRange, userId, "maleGuest", deptId, workerid, beginTime,
				endTime, subType, theme, city);

		request.setAttribute("taskList", taskModels);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		request.setAttribute("taskType", taskType);

		return mapping.findForward("workOrderQueryList");
	}

	/**
	 * 抓回功能
	 * 
	 * @return
	 */
	public ActionForward getBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		
		//页面条件接收--start
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		//页面条件接收--end
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//公客故障工单--由我创建--总数据集合--包括完成和未完成
		List<TaskModel> list = pnrTransferOfficeService.getMaleGeustToGetBack(userId, selectAttribute, firstResult, endResult, pageSize);
		////公客故障工单--由我创建--集合总数--包括完成和未完成
		long total = pnrTransferOfficeService.getMaleGeustCountToGetBack(userId, selectAttribute);

		
		request.setAttribute("taskList", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("getBack");
	}
	
	/**
	 * 打开批量回复界面
	 */
	public ActionForward showBatchReplyView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String[] parameterValues = request.getParameterValues("Fruit");
		String result = "";
		for (int i = 0; i < parameterValues.length; i++) {
			System.out.println("多选框的值=" + parameterValues[i]);
			result += parameterValues[i] + "#";
		}
		System.out.println("result=" + result);

		request.setAttribute("result", result);
		return mapping.findForward("batchReplyView");
	}

	/**
	 * 保存批量回复
	 */
	public ActionForward saveBatchReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long pre= System.currentTimeMillis();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String deadLineTime = "", contact = "";

		// 主题
		String title = "";

		// 主题ID
		String titleId = "";

		// 回单人
		String userId = sessionform.getUserid();

		// 审核人
		String auditor = "";

		// 回单时间
		Date createTime = null;
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 故障原因
		String faultCause = request.getParameter("faultCause");

		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

		String parameter = request.getParameter("result");// 获取taskId和processInstanceId的混合值

		// 任务ID
		String taskId = "";

		// 流程ID
		String processInstanceId = "";

		String[] tempTpValues = parameter.split("#");
		
		// 获取PnrTransferOffice
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		
		for (int i = 0; i < tempTpValues.length; i++) {
			// 拆分出任务ID和流程ID
			String[] TpValues = tempTpValues[i].split(",");
			taskId = TpValues[0];
			processInstanceId = TpValues[1];

			
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			PnrTransferOffice pnrTransferOffice = list.get(0);
//			PnrTransferOffice pnrTransferOffice=null;
//			try {
//				
//				 pnrTransferOffice=pnrTransferOfficeService.getPnrTransferOfficeByProcessInstanceId(processInstanceId);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}

			// 获取工单主题等的值
			title = pnrTransferOffice.getTheme();
			titleId = pnrTransferOffice.getId();
			auditor = pnrTransferOffice.getInitiator();

			// 获取工单主题等的值 
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOffice.setState(8);
			pnrTransferOfficeService.save(pnrTransferOffice);

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();

			
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
			entity.setTheme(title);
			entity.setThemeId(titleId);
			entity.setReceivePerson(userId);
			entity.setAuditor(auditor);
			entity.setDoPersons(dealPerformer2);
			createTime = new Date();
			entity.setReceiveTime(createTime);
			entity.setHandleDescription(mainRemark);
			entity.setFaultCause(faultCause);
			entity.setProcessInstanceId(processInstanceId);
			entity.setTaskId(taskId);

			// 处理人关系表数据保存--end
			transferHandleService.save(entity);
			
			
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
			//流程停留在原状态
			//formService.submitTaskFormData(taskId, map);
			// 流程提交到下一级-end
			// 判断是否是接口派单,是就进行接口通知
			String maleGuestNumber = pnrTransferOfficeService
					.getMaleGuestNumberByThemeId(titleId);
			if (maleGuestNumber != null
					&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
				// 调用方法,查询接口需要的数据
				TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
						.getMaleGuestReturnData(maleGuestNumber);
				Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
						.format(new Date()),
						CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
				aThread.start();
			}

			String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
			+ title + ";";
	msg += TASK_DEADLINE_STR + deadLineTime + ";" + TASK_CONTACT_STR
			+ contact + ";已归档";
	CommonUtils.sendMsg(msg, auditor);
			CommonUtils.sendMsg(msg, auditor);
			
			
		}

		System.out.println("parameter=" + parameter);

		// request.setAttribute("result", result);
		long post= System.currentTimeMillis();
		
		System.out.println("方法执行时间="+(post-pre));
		
		return mapping.findForward("success");
	}
	
	
	/**
	 * 显示已处理公客故障工单
	 */
	public ActionForward manualArchiveList(ActionMapping mapping, ActionForm form,
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		
		//获取当前系统时间
		Date b = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(f.format(b));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONDAY, -2);//开始时间为当前日期的前两个月日期
		String startDate=f.format(c.getTime())+" 00:00:00";
		String endDate  =f.format(b)+" 23:59:59";//结束时间为当前日期
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		// 工单管理-"公客-传输局工单"-待回复工单 集合条数
		int total = 0;
			total = pnrTransferOfficeService
					.manualArchiveCount(userId,startDate,endDate,
							selectAttribute);

		// 工单管理-"公客-传输局工单"-手动归档工单 集合
		List<TaskModel> rPnrTransferList = null;
			rPnrTransferList = pnrTransferOfficeService
					.manualArchiveList(userId,startDate,endDate,
							selectAttribute,
							firstResult, endResult, pageSize);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("manualArchiveList");
	}
	
	
	/**
	 * 公客故障工单--归档操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doOneWorkOrderArchiving(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		PrintWriter out = response.getWriter();
		String currentUserId = sessionForm.getUserid();
		String ids=request.getParameter("ids");
		String[] taskids=ids.split(";");
		for(int i=0;i<taskids.length;i++){
			String[] str =taskids[i].split(",");
			String taskId = str[0];
			String processInstanceId = str[1];
			String isSpotCheck = StaticMethod.nullObject2String(request
					.getParameter("isSpotCheck"));
			System.out.println("taskid====="+taskId);
			System.out.println("processInstanceId====="+processInstanceId);
		
			// 流程提交到下一级
			FormService formService = (FormService) getBean("formService");
			//TaskFormData taskFormData = formService.getTaskFormData(taskId);
			Map<String, String> map = new HashMap<String, String>();
	//		for (FormProperty formProperty : taskFormData.getFormProperties()) {
	//			if (formProperty.isWritable()) {
	//				String name = formProperty.getId();
	//				map.put(name, request.getParameter(name));
	//			}
	//		}
			map.put("handleState", "handle");
			formService.submitTaskFormData(taskId, map);
	
			// 存储归档时间
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
	
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
				pnrTransferOffice.setArchivingTime(sFormat.parse(sFormat
						.format(new Date())));
				pnrTransferOffice.setState(5);
				pnrTransferOffice.setOrderAuditPerson(currentUserId);
				pnrTransferOfficeService.save(pnrTransferOffice);
	
				// 审减金额-工费
				String shenJianOperatingCosts = request
						.getParameter("shenJianOperatingCosts");
				// 审减金额-材料费
				String shenJianMaterialsCosts = request
						.getParameter("shenJianMaterialsCosts");
				String userId = request.getParameter("userId");
				String linkName = request.getParameter("linkName");
				// 1.保存流转信息
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTransferOffice.getId());
				entity.setTheme(pnrTransferOffice.getTheme());
				entity.setCheckTime(new Date());
				entity.setAuditor(userId);
				entity.setProcessInstanceId(processInstanceId);
				entity.setLinkName(linkName);
				if (shenJianOperatingCosts != null
						&& !"".equals(shenJianOperatingCosts)) {
					entity.setShenJianOperatingCosts(Double
							.parseDouble(shenJianOperatingCosts));
	
				}
				if (shenJianMaterialsCosts != null
						&& !"".equals(shenJianMaterialsCosts)) {
					entity.setShenJianMaterialsCosts(Double
							.parseDouble(shenJianMaterialsCosts));
	
				}
				if ("10".equals(isSpotCheck)) {
					entity.setHandleDescription("手机端确认抽查，WEB端回单归档");
					entity.setOperation("10");
				} else {
					entity.setHandleDescription("手机端没有提交抽查，WEB端直接归档");
					entity.setOperation("11");
				}
	
				transferHandleService.save(entity);
	
				// 保存附件
				String attachments = request.getParameter("orderAudit");
				String step = request.getParameter("step");
				if (attachments != null && !"".equals(attachments)) {
					pnrTransferOfficeService.saveAttachment(processInstanceId,
							attachments, step);
				}
	
			}
		}
			 out.print("true");
		return null;
	}
	
	/**
	 * 批量处理保存--施工队
	 */
	public ActionForward batchApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		// 发短信
		String deadLineTime = "", contact = "";
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();	
		
		// 主题
		String title = "";
		// 主题ID
		String titleId = "";
		// 审核人
		String auditor = "";
		// 回单时间
		Date createTime = new Date();		

		String processInstanceId="";
		
		String taskIds = request.getParameter("taskids");
		String[] taskIdArr=taskIds.split(";");
		for(int i=0;i<taskIdArr.length;i++){
			String ids[]=taskIdArr[i].split(",");
			String taskId=ids[0];
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
					.list();
			request.setAttribute("taskId", taskId);
			if (taskList != null && taskList.size() != 0) {
				Task task = taskList.get(0);
				processInstanceId = task.getProcessInstanceId();
				auditor=task.getAssignee();
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				Search search = new Search();
				search.addFilterEqual("processInstanceId", task
						.getProcessInstanceId());
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				
				PnrTransferOffice pnrTransferOffice1 =list.get(0);
				title=pnrTransferOffice1.getTheme();
				titleId=pnrTransferOffice1.getId();
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setTheme(title);
				entity.setThemeId(titleId);
				entity.setReceivePerson(userId);
				entity.setAuditor(auditor);
	//			entity.setDoPersons(dealPerformer2);
				entity.setReceiveTime(createTime);
	//			entity.setHandleDescription(mainRemark);
	//			entity.setFaultCause(faultCause);
				entity.setProcessInstanceId(processInstanceId);
				entity.setTaskId(taskId);
				Search search1 = new Search();
				search1.addFilterEqual("processInstanceId", processInstanceId);
				List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
						.search(search1);
				if (pnrTicketList != null) {
					PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
					pnrTransferOffice.setLastReplyTime(new Date());
					
					//改变工单状态，在待回复中不在显示
					pnrTransferOffice.setState(8);
					pnrTransferOfficeService.save(pnrTransferOffice);
	
					deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
							: sFormat.format(pnrTransferOffice.getEndTime());
					contact = pnrTransferOffice.getConnectPerson();
				}
				// 处理人关系表数据保存--end
				transferHandleService.save(entity);
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
				
				
				// 流程提交到下一级-end
				// 判断是否是接口派单,是就进行接口通知
				String maleGuestNumber = pnrTransferOfficeService
						.getMaleGuestNumberByThemeId(titleId);
				if (maleGuestNumber != null
						&& !"".equals(maleGuestNumber = maleGuestNumber.trim())) {
					// 调用方法,查询接口需要的数据
					TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
							.getMaleGuestReturnData(maleGuestNumber);
					Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
							.format(new Date()),
							CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
					aThread.start();
				}
	
				String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
						+ title + ";";
				msg += TASK_DEADLINE_STR + deadLineTime + ";" + TASK_CONTACT_STR
						+ contact + ";已归档";
				CommonUtils.sendMsg(msg, auditor);	
			}
			out.print("true");
		}
		return null;
	}
	
	/**
	 * 归集工单列表查询
	 */
	public ActionForward querySingleImputationList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		FormService formService = (FormService) getBean("formService");
//		Map<String, String> map = new HashMap<String, String>();
//	//	map.put("auditReportAssignee","superUser3");
//		map.put("auditReportState","handle");
//		formService.submitTaskFormData("359097", map);
		
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		int total = 0;
		total = pnrTransferOfficeService.querySingleImputationCount(userId,selectAttribute);

		List<TaskModel> rPnrTransferList = null;
		rPnrTransferList = pnrTransferOfficeService.querySingleImputationList(userId,selectAttribute,firstResult, endResult, pageSize);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		request.setAttribute("queryFlag", "single");//查询标识
		return mapping.findForward("singleImputationList");
		//return mapping.findForward("success");
		
	}
	
	/**
	 * 未归集工单列表查询
	 */
	public ActionForward queryNoSingleImputationList(ActionMapping mapping, ActionForm form,
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		int total = 0;
		total = pnrTransferOfficeService.queryNoSingleImputationCount(userId,selectAttribute);

		List<TaskModel> rPnrTransferList = null;
		rPnrTransferList = pnrTransferOfficeService.queryNoSingleImputationList(userId,selectAttribute,firstResult, endResult, pageSize);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		request.setAttribute("queryFlag", "noSingle");//查询标识
		return mapping.findForward("noSingleImputationList");
	}
	
/**
	 * 获取主场景多选框
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMainScene(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String resultJson = "[";
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql =" select mn.scene_no,mn.scene_name From master_male_scene mn";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		for (ListOrderedMap listOrderedMap : resultList) {
			
			resultJson+="{\"id\":\""+listOrderedMap.get("scene_no")+"\",\"name\":\""+listOrderedMap.get("scene_name")+"\"},";

		}
		resultJson =resultJson.substring(0,resultJson.length()-1);
		resultJson +="]";
		System.out.println("getMainScene-resultJson:"+resultJson);
		PrintWriter out = response.getWriter();
		out.print(resultJson);
	
/*		out
				.print("[{\"id\":\"1\",\"name\":\"光电缆\"},{\"id\":\"2\",\"name\":\"杆路\"},{\"id\":\"3\",\"name\":\"管道\"},{\"id\":\"4\",\"name\":\"交接箱\"}]");
*/
		return null;
	}
	
	
	
	/**
	 * 获取子场景多选框
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getChildScene(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String resultJson = "[";
		String mainId = StaticMethod.nullObject2String(request.getParameter("mainId"));

		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql =" select sc.copy_no,sc.copy_name from maste_male_copy_scene sc where sc.scene_no='"+mainId+"'";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		for (ListOrderedMap listOrderedMap : resultList) {
		
			resultJson+="{\"id\":\""+listOrderedMap.get("copy_no")+"\",\"name\":\""+listOrderedMap.get("copy_name")+"\"},";
		}	
		resultJson =resultJson.substring(0,resultJson.length()-1);
		resultJson +="]";
		
		System.out.println("getChildScene-resultJson:"+resultJson);
		PrintWriter out = response.getWriter();
		out.print(resultJson);
		
	/*	if("1".equals(mainId)){
			out
			.print("[{\"id\":\"11\",\"name\":\"光缆断或备用纤芯断（无需布放）\"},{\"id\":\"12\",\"name\":\"光缆断或备用纤芯断（需布放）\"},{\"id\":\"13\",\"name\":\"多条光缆中断\"},{\"id\":\"14\",\"name\":\"光缆衰耗大（普通）\"}]");
		}else if("2".equals(mainId)){
			out
			.print("[{\"id\":\"21\",\"name\":\"电杆断（拆除）\"},{\"id\":\"22\",\"name\":\"电杆断（更换）\"}]");
		}else if("3".equals(mainId)){
			out
			.print("[{\"id\":\"31\",\"name\":\"人井盖损坏或丢失\"},{\"id\":\"32\",\"name\":\"多处人井盖损坏或丢失\"},{\"id\":\"33\",\"name\":\"管道人手孔积水处理、杂物清理\"}]");
		}else if("4".equals(mainId)){
			out
			.print("[{\"id\":\"41\",\"name\":\"成端内部维修\"},{\"id\":\"42\",\"name\":\"成端尾纤维修\"},{\"id\":\"43\",\"name\":\"箱体进水\"}]");
		}*/	
	
//		 out.print(returnJson.toString());
//		 System.out.println(returnJson.toString());

		return null;
	}
	
	/**
	 * 获取子场景的详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSceneDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//主场景的值
		String mainId = StaticMethod.nullObject2String(request.getParameter("mainId"));
		//子场景的值
		String childId = StaticMethod.nullObject2String(request.getParameter("childId"));
		
		String resultJsonStr = "";
		String mainJsonStr="";
		String childJsonStr="";
		String disposeJsonStr="";
		String unitJsonStr="";
		String itemRefJsonStr="";

		String flag ="";
		
		List item_noList = new ArrayList();
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select m.scene_no,m.scene_name,'main' flag ,'' dispose,'' unit,'' is_have from master_male_scene m " +
				"where m.scene_no='" +mainId+"'";
		searchSql+=" union all";
		searchSql+=" select mc.copy_no,mc.copy_name,'copy',mc.dispose,mc.unit,mc.is_have from maste_male_copy_scene mc ";
		searchSql+=" where mc.copy_no='" +childId+"'";
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);	
		
		String isHave = "0";
		for (ListOrderedMap listOrderedMap : resultList) {
			flag = (String) listOrderedMap.get("flag");
			
			if("main".equals(flag)){
				mainJsonStr = "{\"id\": \""+listOrderedMap.get("scene_no")+"\",\"name\": \""+listOrderedMap.get("scene_name")+"\"}";
			}else if("copy".equals(flag)){
			    childJsonStr = "{\"id\": \""+listOrderedMap.get("scene_no")+"\",\"name\": \""+listOrderedMap.get("scene_name")+"\"}";
			    disposeJsonStr ="{\"id\": \""+listOrderedMap.get("scene_no")+"_dispose\",\"name\": \""+listOrderedMap.get("dispose")+"\"}";
			    unitJsonStr ="{\"id\": \""+listOrderedMap.get("scene_no")+"_unit\",\"name\": \""+listOrderedMap.get("unit")+"\"}";
			    
			    isHave =listOrderedMap.get("is_have")==null?"0":listOrderedMap.get("is_have").toString();
                String copyItemSql = "select m.item_no from maste_male_item m where m.copy_no='"+childId+"'";
            	List<ListOrderedMap> copyItemresultList = jdbcService.queryForList(copyItemSql);	
            	//每个子场景 包含了几组材料
            	
            	for(ListOrderedMap copyItemMap : copyItemresultList){
            		item_noList.add(copyItemMap.get("item_no"));
            	}
			}
			
		}
		
		//每组的材料	
		String item_no="";
		int itemSize =item_noList.size();
		for (int i=0;i<itemSize;i++){
			
			item_no=(String) item_noList.get(i);
			 String relDataItemSql = " select d.data_no,d.per_price,d.data_name,ite.unit,ite.limit_num,d.per_price*ite.limit_num total_price " +
     		" from maste_male_item ite" +
     		" left join maste_male_item_rel_data reld on ite.item_no = reld.item_no" +
     		" left join maste_male_data d on reld.data_no = d.data_no" +
     		" where ite.copy_no='"+childId+"' and ite.item_no='"+item_no+"'";
			 System.out.println("抢修归集-2201-获取下拉列表的sql:"+relDataItemSql);
		 	List<ListOrderedMap> relDataItemresultList = jdbcService.queryForList(relDataItemSql);	
		     int mergeNum =relDataItemresultList.size();
		     
		     String itemMoneyJsonStr ="";
		     String limitNumStr ="" ;
		     String unitStr = "";
		     String perPriceStr ="";
		     String totalStr = "";
		     itemRefJsonStr+="{\"cname\": {\"ctype\": \"select\",\"cvalue\":[";
		//   每组材料下拉值
		     int first =0;
		     for(ListOrderedMap relDataItemMap :relDataItemresultList){
		     	first ++;
		     	String dataNo= relDataItemMap.get("data_no")==null?"no":relDataItemMap.get("data_no")+"";
		     	String dataName= relDataItemMap.get("data_name")==null?"无":relDataItemMap.get("data_name")+"";
		     	
		     	itemRefJsonStr+="{\"id\": \""+dataNo+"\",\"name\": \""+dataName+"\"},";
		     	if(1==first){
		     		limitNumStr = relDataItemMap.get("limit_num")==null?"1":relDataItemMap.get("limit_num").toString();
		     		unitStr = relDataItemMap.get("unit")==null?"无":relDataItemMap.get("unit").toString();
		     		perPriceStr = relDataItemMap.get("per_price")==null?"0":relDataItemMap.get("per_price").toString();
		     		totalStr = relDataItemMap.get("total_price")==null?"0":relDataItemMap.get("total_price").toString();
		     	}
		         
		     }
		     first=0;
		//   每组材料下拉值--end
		     itemRefJsonStr = itemRefJsonStr.substring(0,itemRefJsonStr.length()-1);
		     itemRefJsonStr+= "]},";                    
		     itemMoneyJsonStr="\"item_no\": \""+item_no+"\",\"cnum\": \""+limitNumStr+"\",\"cunit\": \""+unitStr+"\",\"cprice\": \""+perPriceStr+"\",\"ctotal\": \""+totalStr+"\"},";
		     itemRefJsonStr+=itemMoneyJsonStr;
		}
		
       
    	itemRefJsonStr = itemRefJsonStr.substring(0,itemRefJsonStr.length()-1);
		
    	
		
		
		resultJsonStr ="{\"main\": "+mainJsonStr+",\"child\": "+childJsonStr+",\"itemSize\": "+itemSize+",\"isHave\": "+isHave+",\"measure\": "+disposeJsonStr+",\"unit\":"+unitJsonStr+"," +
		 "\"materials\":{\"mdetail\":["+itemRefJsonStr+"]}" + "}";

		System.out.println("公客回单-getSceneDetail-resultJsonStr:"+resultJsonStr);
		PrintWriter out = response.getWriter();
		out.print(resultJsonStr);
		/*if("1".equals(mainId)&&"11".equals(childId)){
			out
			.print("{\"main\": "+mainJsonStr+",\"child\": "+childJsonStr+",\"measure\": "+disposeJsonStr+",\"unit\":"+unitJsonStr+"," +
                   "\"materials\":{\"mdetail\":[{"+
                   
                   "\"cname\": {\"ctype\": \"select\",\"cvalue\": " +
                   "[{\"id\": \"aa\",\"name\": \"光缆_GYTA-4B1.3\"}," +
                   "{\"id\": \"bb\",\"name\": \"光缆_GYTA-8B1.3\"}," +
                   "{\"id\": \"cc\",\"name\": \"光缆_GYTA-12B1.3\"}]}," +
                   "\"cnum\": \"1\",\"cunit\": \"套\",\"cprice\": \"35.393\",\"ctotal\": \"35.393\"}," +
                   
                   "{\"cname\":" +
                   " {\"ctype\": \"select\",\"cvalue\":" +
                   " [" +
                   "{\"id\": \"dd\",\"name\": \"光缆_GYTA-4B1.5\"}," +
                   "{\"id\": \"ee\",\"name\": \"光缆_GYTA-8B1.5\"}," +
                   "{\"id\": \"ff\",\"name\": \"光缆_GYTA-12B1.5\"}" +
                   "]}," +
                   
                   "\"cnum\": \"2\",\"cunit\": \"套\",\"cprice\": \"100.393\",\"ctotal\": \"100.393\"" +
                   "}" +
                   
                   "]}" +
                   
                   "}");
		}

		if("1".equals(mainId)&&"11".equals(childId)){
			out
			.print("{\"main\": {\"id\": \"1\",\"name\": \"光电缆\"},\"child\": {\"id\": \"11\",\"name\": \"光缆断或备用纤芯断（无需布放）\"},\"measure\": {"+
                   "\"id\": \"\",\"name\": \"倒放预留、光缆接续\"},\"unit\": {\"id\": \"\",\"name\": \"处\"}," +
                   "\"materials\":{\"mdetail\":[{"+
                   
                   "\"cname\": {\"ctype\": \"select\",\"cvalue\": " +
                   "[{\"id\": \"aa\",\"name\": \"光缆_GYTA-4B1.3\"}," +
                   "{\"id\": \"bb\",\"name\": \"光缆_GYTA-8B1.3\"}," +
                   "{\"id\": \"cc\",\"name\": \"光缆_GYTA-12B1.3\"}]}," +
                   "\"cnum\": \"1\",\"cunit\": \"套\",\"cprice\": \"35.393\",\"ctotal\": \"35.393\"}," +
                   
                   "{\"cname\":" +
                   " {\"ctype\": \"select\",\"cvalue\":" +
                   " [" +
                   "{\"id\": \"dd\",\"name\": \"光缆_GYTA-4B1.5\"}," +
                   "{\"id\": \"ee\",\"name\": \"光缆_GYTA-8B1.5\"}," +
                   "{\"id\": \"ff\",\"name\": \"光缆_GYTA-12B1.5\"}" +
                   "]}," +
                   
                   "\"cnum\": \"2\",\"cunit\": \"套\",\"cprice\": \"100.393\",\"ctotal\": \"100.393\"" +
                   "}" +
                   
                   "]}" +
                   
                   "}");
		}else if("1".equals(mainId)&&"13".equals(childId)){
			out
			.print("{\"main\": {\"id\": \"1\",\"name\": \"光电缆\"},\"child\": {\"id\": \"11\",\"name\": \"光缆断或备用纤芯断（无需布放）\"},\"measure\": {"+
                   "\"id\": \"\",\"name\": \"倒放预留、光缆接续\"},\"unit\": {\"id\": \"\",\"name\": \"处\"},\"materials\":{\"mdetail\":[{"+
                   "\"cname\": {\"ctype\": \"select\",\"cvalue\": [{\"id\": \"aa\",\"name\": \"光缆_GYTA-4B1.3\"},{\"id\": \"bb\",\"name\": \"光缆_GYTA-8B1.3\""+
                   "},{\"id\": \"cc\",\"name\": \"光缆_GYTA-12B1.3\"}]},\"cnum\": \"1\",\"cunit\": \"套\",\"cprice\": \"35.393\",\"ctotal\": \"35.393\""+
                   "},{\"cname\": {\"ctype\": \"select\",\"cvalue\": [{\"id\": \"dd\",\"name\": \"光缆_GYTA-4B1.5\"},{\"id\": \"ee\",\"name\": \"光缆_GYTA-8B1.5\""+
                   "},{\"id\": \"ff\",\"name\": \"光缆_GYTA-12B1.5\"}]},\"cnum\": \"2\",\"cunit\": \"套\",\"cprice\": \"100.393\",\"ctotal\": \"100.393\"}]}}");
		}*/
		return null;
	}
	
	/**
	 * 归集工单列表 查看功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward querySingleImputationDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		System.out.println("--------------------归集工单列表 查看功能");
		//工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		//任务号
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		//所属区域
//		String genusArea = StaticMethod.nullObject2String(request.getParameter("genusArea"));
		//局址名称/编码
//		String stationName = StaticMethod.nullObject2String(request.getParameter("stationName"));
		//代维公司
//		String daiWeiCompany = StaticMethod.nullObject2String(request.getParameter("daiWeiCompany"));
		
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		
		//上面的归集工单查询
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		String city ="28";
		if(areaid.length()>4){
			city = areaid.substring(0, 4);
		}
		country = areaid.substring(0,areaid.length()>5?6:areaid.length());
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		
		selectAttribute.setProcessInstanceId(processInstanceId);
		String siteCd = request.getParameter("siteCd");
		selectAttribute.setSiteCd(siteCd);
		
		selectAttribute.setCounty(country);//区县

		List<TaskModel> prPnrTransferList = pnrTransferOfficeService
				.getToreplyJobOfMaleGuestTransmissionBureauJobParentList(userId,
						selectAttribute,
						0, 0,0);
		
		//下面的未归集的查询列表
		//int pageSize = CommonUtils.PAGE_SIZE;
		int pageSize =CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
	
		// 工单管理-"公客-传输局工单"-待回复工单 集合条数
		int total = 0;
		// 工单管理-"公客-传输局工单"-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		String queryFlag="no";//不进行查询
		if(!"".equals(maleGuestNumber)||!"".equals(wsTitle)||!"".equals(country)){		


			
				total = pnrTransferOfficeService
						.getToreplyJobOfMaleGuestTransmissionBureauJobCount(userId,
								selectAttribute);

		
				rPnrTransferList = pnrTransferOfficeService
						.getToreplyJobOfMaleGuestTransmissionBureauJobList(userId,
								selectAttribute,
								firstResult, endResult, pageSize);
		
			
			
			
			queryFlag="yes";
		}
		
		request.setAttribute("queryFlag", queryFlag);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("ptaskList", prPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("ppageSize", 100);
		request.setAttribute("total", total);
		request.setAttribute("ptotal", prPnrTransferList.size());

		request.setAttribute("selectAttribute", selectAttribute);
		
		
		request.setAttribute("processInstanceId", processInstanceId);
		request.setAttribute("siteCd", siteCd);		
		
		request.setAttribute("country", country);		//区县
		request.setAttribute("city", city);		
//		request.setAttribute("genusArea", genusArea);
//		request.setAttribute("stationName", stationName);
//		request.setAttribute("daiWeiCompany", daiWeiCompany);
		return mapping.findForward("singleImputationDetail");
	}
	
	
	/**
	 * 未归集工单列表 增加功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addNoSingleImputationDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		String city ="28";
		if(areaid.length()>4){
			city = areaid.substring(0, 4);
		}		
		country = areaid.substring(0,areaid.length()>5?6:areaid.length());//默认登录人的区县
//		System.out.println("--------------------未归集工单列表 增加功能");
		//工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		//局站编号
		String siteCd = StaticMethod.nullObject2String(request.getParameter("siteCd"));
	/*	//所属区域
		String genusArea = StaticMethod.nullObject2String(request.getParameter("genusArea"));
		//局址名称/编码
		String stationName = StaticMethod.nullObject2String(request.getParameter("stationName"));
		//代维公司
		String daiWeiCompany = StaticMethod.nullObject2String(request.getParameter("daiWeiCompany"));
		*/
		//上面的归集工单查询
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
		String wsNum = request.getParameter("wsNum");
		selectAttribute.setWsNum(wsNum);
		String wsTitle = request.getParameter("wsTitle");
		selectAttribute.setTheme(wsTitle);
		String status = request.getParameter("status");
		selectAttribute.setStatus(status);
		String installAddress = request.getParameter("installAddress");
		selectAttribute.setInstallAddress(installAddress);
		String dept = request.getParameter("dept");
		selectAttribute.setDept(dept);
		String person = request.getParameter("person");
		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);

		selectAttribute.setSiteCd(siteCd);
		selectAttribute.setProcessInstanceId(processInstanceId);
		selectAttribute.setCounty(country);
		
		List<TaskModel> ptaskList = pnrTransferOfficeService
		.getToreplyJobOfMaleGuestTransmissionBureauJobParentNoList(userId,
				selectAttribute,
				0, 0, 0);
		
		//下面的未归集的查询列表
		int pageSize = CommonUtils.PAGE_SIZE;
		
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		// 工单管理-"公客-传输局工单"-待回复工单 集合条数
		int total = 0;
		// 工单管理-"公客-传输局工单"-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		String queryFlag="no";//不进行查询
		if(!"".equals(maleGuestNumber)||!"".equals(wsTitle)||!"".equals(country)){
			
				total = pnrTransferOfficeService
						.getToreplyJobOfMaleGuestTransmissionBureauJobNoCount(userId,
								selectAttribute);

		
				rPnrTransferList = pnrTransferOfficeService
						.getToreplyJobOfMaleGuestTransmissionBureauJobNoList(userId,
								selectAttribute,
								firstResult, endResult, pageSize);
		
			
			
			
			queryFlag="yes";
		}
		
		request.setAttribute("queryFlag", queryFlag);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		
		request.setAttribute("ptaskList", ptaskList);
		request.setAttribute("ptotal", 1);
		request.setAttribute("ppageSize", 1);
		
		request.setAttribute("processInstanceId", processInstanceId);
		request.setAttribute("siteCd", siteCd);
		/*request.setAttribute("genusArea", genusArea);
		request.setAttribute("stationName", stationName);
		request.setAttribute("daiWeiCompany", daiWeiCompany);*/

		request.setAttribute("country", country);		//区县
		request.setAttribute("city", city);	
		
		return mapping.findForward("noSingleImputationDetail");
	}
	
	/**
	 * 解锁
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
//		System.out.println("--------------------解锁");
		//子工单号
		String subProcessInstanceId = request.getParameter("subProcessInstanceId");
		//主工单号
		String mainProcessInstanceId = StaticMethod.nullObject2String(request.getParameter("mainProcessInstanceId"));
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", subProcessInstanceId);
        PnrTransferOffice p = pnrTransferOfficeService.searchUnique(search);        
        p.setMaleGuestState("3");
        pnrTransferOfficeService.save(p);
        
        //更新主工单的告警时间和派单时间
        this.updateMinDateForMainOrder(pnrTransferOfficeService, mainProcessInstanceId);
        
		PrintWriter out = response.getWriter();
		String returnJson = "{\"result\":\"success\",\"content\":\"解锁成功\"}";
		out.print(returnJson);	
		return null;
	}
	
	private void updateMinDateForMainOrder(IPnrTransferOfficeService pnrTransferOfficeService,String mainProcessInstanceId){
		 //1.取子工单中的最小告警时间和最小派单时间
        Date minCreateDate = pnrTransferOfficeService.getMinDateForSubWorkOrder(mainProcessInstanceId, null, "create", null);
		Date minSendDate = pnrTransferOfficeService.getMinDateForSubWorkOrder(mainProcessInstanceId, null, "send", null);
		if(minCreateDate != null && minCreateDate != null){
			//2.更新主工单的告警时间和派单时间
			Search search2 = new Search();
			search2.addFilterEqual("processInstanceId", mainProcessInstanceId);
			PnrTransferOffice mainOffice = pnrTransferOfficeService.searchUnique(search2);
			if(mainOffice != null){
				mainOffice.setCreateTime(minCreateDate);
				mainOffice.setSendTime(minSendDate);
				pnrTransferOfficeService.save(mainOffice);
			}
		}
	}
	
	/**
	 * 加入归集
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward joinCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//归集的工单号
		String singleProId = StaticMethod.nullObject2String(request.getParameter("singleProId"));
		
		//未归集的工单号
		String noProId = StaticMethod.nullObject2String(request.getParameter("noProId"));
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", noProId);
        PnrTransferOffice p = pnrTransferOfficeService.searchUnique(search);        
        p.setMaleGuestState("2");
        p.setParentProcessInstanceId(singleProId);
        pnrTransferOfficeService.save(p);
        
        //更新主工单的告警时间和派单时间
        this.updateMinDateForMainOrder(pnrTransferOfficeService, singleProId);
		
		PrintWriter out = response.getWriter();
		String returnJson = "{\"result\":\"success\",\"content\":\"地市金额更新成功\"}";
		out.print(returnJson);	
		return null;
	}
	
	/**
	 * 通过工单号判断该归集工单是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward existsSingleWorkOrderByProId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String country = StaticMethod.nullObject2String(request.getParameter("country"));

		//归集的工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", processInstanceId);
        search.addFilterEqual("maleGuestState", "1");
        search.addFilterEqual("taskDefKey", "auditor");
        search.addFilterEqual("country", country);
        PnrTransferOffice p = pnrTransferOfficeService.searchUnique(search);       
        
        PrintWriter out = response.getWriter();
        String returnJson = "{\"result\":\"yes\",\"content\":\"存在\"}";
        if(p==null){
    	   returnJson = "{\"result\":\"no\",\"content\":\"不存在\"}";
        }
		out.print(returnJson);	
		
		return null;
	}
	
	/**
	 * 通过材料动态加载单价
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPriceByMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//材料值
		String materialVal = StaticMethod.nullObject2String(request.getParameter("materialVal"));
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="  select d.per_price from MASTE_male_DATA d where d.data_no='"+materialVal+"'";
		String returnJson = "{\"cpric\":\"0\"}";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		for (ListOrderedMap listOrderedMap : resultList) {
		
			returnJson="{\"cpric\":\""+listOrderedMap.get("per_price")+"\"}"; 
		}	
		
		PrintWriter out = response.getWriter();
		out.print(returnJson);	
		return null;
	}
	/**
	 * 抢修工单审核 一次核验
	 */
	public ActionForward firstVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		int pageSize = CommonUtils.PAGE_SIZE;
		//int pageSize = 2;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		
		//界面初始化的时候，初始化地市，区县
		String operateFlag = StaticMethod.nullObject2String(request.getParameter("operateFlag"));//控制当前的登录人在界面的操作权限
		String isCity = StaticMethod.nullObject2String(request.getParameter("isCity"));//是地市还是区县
		if("".equals(region)&&"".equals(country)){
			//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
			String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
			/*areaid="2819";
			areaid="281903";*/
			System.out.println("-------------areaid="+areaid);
			if(areaid.length()==4){//地市的人
				region=areaid;
				operateFlag="no";
				isCity="city";
			}else if(areaid.length()==6){//区县的人
				isCity="country";
				region=areaid.substring(0,4);
				country=areaid;
				//取该区县的一次核验人
				String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");
				System.out.println("-------------dealingPeople="+dealingPeople);
				if(dealingPeople.equals(userId)){
					operateFlag="yes";
				}else{
					operateFlag="no";
				}
			}else{
				operateFlag="no";
				isCity="other";
			}
		}
		System.out.println("------country="+country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setCounty(country);
		
		String showFlag="yes";//提交按钮是否显示标识
		String tishiFlag="no";//查询条件不全时候的提示标识
	    String exportFlag="yes";//导出按钮标识
		String approvalSign="";//审批人签字
		String approvalPhone="";//审批人联系方式
		String statisticsFlag="no";//是否显示统计信息（默认不显示）
		int total = 0;
		List<TaskModel> rPnrTransferList = null;
		if(!"".equals(country)&&!"".equals(sendStartTime)){
			//集合条数
			total = pnrTransferOfficeService.getFirstVerifyCount(userId,selectAttribute);

			//集合
			rPnrTransferList = pnrTransferOfficeService.getFirstVerifyList(userId,selectAttribute,firstResult, endResult, pageSize);
			
			//获取报表未审批的工单个数
			if(total > 0){
				selectAttribute.setReportType("firstVerify");
				int notApprovedSheetCount = pnrTransferOfficeService.getNotApprovedSheetCount(selectAttribute);
				if(notApprovedSheetCount==0){
					//一次核验报表，审批人签字
					//approvalSign = pnrTransferOfficeService.getApprovalSign(selectAttribute);
					approvalSign = pnrTransferOfficeService.getAttributeValueOfFirstAndSecond(userId, selectAttribute,"user");
					if(approvalSign!=null&&!"".equals(approvalSign)){
						approvalPhone = pnrTransferOfficeService.getAttributeValueOfFirstAndSecond(userId, selectAttribute,"phone");
						showFlag="no";
					}
				}
				
				//获取材料的统计信息
				MaleSceneStatisticsModel maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId, selectAttribute, "acheck", null);
				request.setAttribute("maleSceneStatisticsModel", maleSceneStatisticsModel);
				statisticsFlag="yes";
			}else{
				showFlag="no";
				exportFlag="no";//导出按钮标识
			}
		}else{
			showFlag="no";
			tishiFlag="yes";
			exportFlag="no";//导出按钮标识
		}
		//查询条件里的地市相关
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		request.setAttribute("statisticsFlag", statisticsFlag);
		request.setAttribute("exportFlag",exportFlag);
		request.setAttribute("isCity",isCity);
		request.setAttribute("operateFlag",operateFlag);
		request.setAttribute("tishiFlag",tishiFlag);
		request.setAttribute("approvalSign",approvalSign);
		request.setAttribute("approvalPhone",approvalPhone);
		request.setAttribute("showFlag", showFlag);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("region", region);
		request.setAttribute("country", country);
		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("firstVerify");
	}
	
	/**
	 * 抢修工单审核 二次抽检
	 */
	public ActionForward secondInspection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		int pageSize = CommonUtils.PAGE_SIZE;
		//int pageSize = 2;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		//界面初始化的时候，初始化地市，区县
		String operateFlag = StaticMethod.nullObject2String(request.getParameter("operateFlag"));//控制当前的登录人在界面的操作权限
		String isCity = StaticMethod.nullObject2String(request.getParameter("isCity"));//是地市还是区县
		if("".equals(region)&&"".equals(country)){
			//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
			String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
			/*areaid="2819";
			areaid="281903";*/
			System.out.println("-------------areaid="+areaid);
			if(areaid.length()==4){//地市的人
				region=areaid;
				operateFlag="no";
				isCity="city";
			}else if(areaid.length()==6){//区县的人
				isCity="country";
				region=areaid.substring(0,4);
				country=areaid;
				//取该区县的一次核验人
				String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6903");
				if("".equals(dealingPeople)){
					dealingPeople = "superUser";
				}
				System.out.println("-------------dealingPeople="+dealingPeople);
				if(dealingPeople.equals(userId)){
					operateFlag="yes";
				}else{
					operateFlag="no";
				}
			}else{
				operateFlag="no";
				//isCity="country";
				isCity="other";
			}
		}
		
		System.out.println("------country="+country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setCounty(country);
		
		String showFlag="yes";
		String tishiFlag="no";
		String exportFlag="yes";//导出按钮标识
		String approvalSign="";//审批人签字
		String approvalPhone="";//审批人签字
		String statisticsFlag="no";//是否显示统计信息（默认不显示）
		int total = 0;
		List<TaskModel> rPnrTransferList = null;
		if(!"".equals(country)&&!"".equals(sendStartTime)){
			//集合条数
			total = pnrTransferOfficeService.getSecondInspectionCount(userId,selectAttribute);

			//集合
			rPnrTransferList = pnrTransferOfficeService.getSecondInspectionList(userId,selectAttribute,firstResult, endResult, pageSize);
			
			//获取报表未审批的工单个数
			if(total > 0){
				selectAttribute.setReportType("secondInspection");
//				//审批率
//			    double approvalRate = pnrTransferOfficeService.calApprovalRate(userId, selectAttribute);
			    int notApprovedSheetCount = pnrTransferOfficeService.getNotApprovedSheetCount(selectAttribute);
//				if(approvalRate >= 0.2){
			    if(notApprovedSheetCount==0){
					//二次抽检报表，审批人签字
					//approvalSign = pnrTransferOfficeService.getApprovalSign(selectAttribute);
			    	approvalSign = pnrTransferOfficeService.getAttributeValueOfFirstAndSecond(userId, selectAttribute,"user");
					if(approvalSign!=null&&!"".equals(approvalSign)){
						approvalPhone = pnrTransferOfficeService.getAttributeValueOfFirstAndSecond(userId, selectAttribute,"phone");
						showFlag="no";
					}
				}
			    
			  //获取材料的统计信息
				MaleSceneStatisticsModel maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId, selectAttribute, "twoSpotChecks", null);
				request.setAttribute("maleSceneStatisticsModel", maleSceneStatisticsModel);
				statisticsFlag="yes";
			}else{
				showFlag="no";
				exportFlag="no";//导出按钮标识
			}
		}else{
			showFlag="no";
			tishiFlag="yes";
			exportFlag="no";//导出按钮标识
		}
		//查询条件里的地市相关
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		request.setAttribute("statisticsFlag", statisticsFlag);
		request.setAttribute("exportFlag",exportFlag);
		request.setAttribute("isCity",isCity);
		request.setAttribute("operateFlag",operateFlag);
		request.setAttribute("tishiFlag",tishiFlag);
		request.setAttribute("approvalSign",approvalSign);
		request.setAttribute("approvalPhone",approvalPhone);
		request.setAttribute("showFlag", showFlag);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("region", region);
		request.setAttribute("country", country);
		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("secondInspection");
	}
	/**
	 * 抢修工单审核 抢修材料周期领用表
	 */
	public ActionForward repairMaterialCycleTable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		System.out.println("------country="+country);
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		
		//界面初始化的时候，初始化地市，区县
		String operateFlag = StaticMethod.nullObject2String(request.getParameter("operateFlag"));//控制当前的登录人在界面的操作权限
		String isCity = StaticMethod.nullObject2String(request.getParameter("isCity"));//是地市还是区县
		if("".equals(region)&&"".equals(country)){
			//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
			String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
			/*areaid="2819";
			areaid="281903";*/
			System.out.println("-------------areaid="+areaid);
			if(areaid.length()==4){//地市的人
				region=areaid;
				operateFlag="no";
				isCity="city";
			}else if(areaid.length()==6){//区县的人
				isCity="country";
				region=areaid.substring(0,4);
				country=areaid;
				//取该区县的一次核验人
				String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");//一次核验人
				System.out.println("-------------dealingPeople="+dealingPeople);
				if(dealingPeople.equals(userId)){
					operateFlag="yes";
				}else{
					operateFlag="no";
				}
			}else{
				operateFlag="no";
				//isCity="country";
				isCity="other";
			}
		}
		
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		
		String tishiFlag="no";//查询提示
		String createFlag="yes";//生成报表按钮标识
		String exportFlag="yes";//导出按钮标识
		String submitFlag="yes";//提交按钮标识
		String statisticsFlag="no";//是否显示统计信息（默认不显示）
		String cycleCollarReportMsg="";
		String reportNumber="";
		String approvalPhone="";//联系方式
		int total = 0;
		List<TaskModel> rPnrTransferList = null;
		if(!"".equals(country)&&!"".equals(sendStartTime)&&!"".equals(endTime)){
			//1.先判断一下该区县开始日期到结束日期所在范围内是否已经生成了报表
			total = pnrTransferOfficeService.getCycleCollarReportCountByStartDateAndEndDate(userId,selectAttribute);
			if(total > 0){
				rPnrTransferList = pnrTransferOfficeService.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,firstResult, endResult, pageSize);
				//取一下报表的编号，回显给前台
				reportNumber = pnrTransferOfficeService.getAttributeValueOfCycleCollarReport(userId, selectAttribute, "num");
				//判断一下是否提交了
				String submitUserId = pnrTransferOfficeService.getAttributeValueOfCycleCollarReport(userId, selectAttribute, "user");
				if(!"".equals(submitUserId)){//提交人不为空，代表已提交
					createFlag="no";
					exportFlag="yes";	
					submitFlag="over";//代表完成
					
					//去联系方式
					approvalPhone = pnrTransferOfficeService.getAttributeValueOfCycleCollarReport(userId, selectAttribute, "phone");
					//取上传的附件信息
					String accessories = pnrTransferOfficeService.getAttachmentsOfCycleCollarReport(reportNumber);
				    PnrTransferOffice ticket = new PnrTransferOffice();
				    //ticket1.setSheetAccessories("'201603291800196061.txt','201603291800158270.txt'");
				    ticket.setSheetAccessories(accessories);
				    request.setAttribute("sheetMain", ticket);
					
				}else{
					//如果只是生成，未提交，取生成报表时间，和当前时间进行比较，超过7天的，就不让提交了。
					String reportCreateTime = pnrTransferOfficeService.getAttributeValueOfCycleCollarReport(userId, selectAttribute, "time");
					System.out.println("-------riseTime="+reportCreateTime);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date riseDate = format.parse(reportCreateTime);
					int days = (int)((new Date().getTime() - riseDate.getTime())/86400000);
					System.out.println("-------days="+days);
					if(days > 7){
						createFlag="no";
						exportFlag="yes";	
						submitFlag="timeOut";//代表超时
					}else{
						createFlag="no";
						exportFlag="yes";	
						submitFlag="yes";//代表超时
					}
				}
				
				//获取材料的统计信息
				MaleSceneStatisticsModel maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId, selectAttribute, "auditReport", null);
				request.setAttribute("maleSceneStatisticsModel", maleSceneStatisticsModel);
				
				//获取材料数量统计
				MaterialQuantityModel materialQuantityModel = pnrTransferOfficeService.getMaterialQuantityOfCycleReport(userId, selectAttribute, null);
				request.setAttribute("materialQuantityModel", materialQuantityModel);
				statisticsFlag="yes";
			}else{
				//2.判断选择的范围内，是不是有已经有日期生成了周期领用表
				List<CycleCollarReportModel> cycleCollarReportList = pnrTransferOfficeService.getCycleCollarReportListByHomeDate(userId,selectAttribute);
				if(cycleCollarReportList.size() > 0){
					//给出提示
					cycleCollarReportMsg = pnrTransferOfficeService.getCycleCollarReportMsg(cycleCollarReportList);
					createFlag="no";
					exportFlag="no";	
					submitFlag="no";
				}else{
					//集合条数
					total = pnrTransferOfficeService.getRepairMaterialCycleTableCount(userId,selectAttribute);
					if(total > 0){
						//集合
						rPnrTransferList = pnrTransferOfficeService.getRepairMaterialCycleTableList(userId,selectAttribute,firstResult, endResult, pageSize);
						createFlag="yes";
						exportFlag="no";	
						submitFlag="no";
					}else{
						createFlag="no";
						exportFlag="no";	
						submitFlag="no";
					}
				}
			}
		}else{
			tishiFlag="yes";
			createFlag="no";
			exportFlag="no";	
			submitFlag="no";
		}
			
		//查询条件里的地市相关
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);	
		
		request.setAttribute("isCity",isCity);
		request.setAttribute("operateFlag",operateFlag);
		
		request.setAttribute("statisticsFlag", statisticsFlag);
		request.setAttribute("cycleCollarReportMsg", cycleCollarReportMsg);
		request.setAttribute("tishiFlag", tishiFlag);
		request.setAttribute("createFlag", createFlag);
		request.setAttribute("exportFlag", exportFlag);
		request.setAttribute("submitFlag", submitFlag);
		request.setAttribute("reportNumber", reportNumber);
		request.setAttribute("approvalPhone", approvalPhone);//联系方式
		
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("region", region);
		request.setAttribute("country", country);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("repairMaterialCycleTable");
	}
	
	/**
	 * 	 一次核验 审批操作
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward firstVerifyApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvePId = StaticMethod.nullObject2String(request.getParameter("approvePId"));
		String approveTaskId = StaticMethod.nullObject2String(request.getParameter("approveTaskId"));
		String approveFlag = StaticMethod.nullObject2String(request.getParameter("approveFlag"));
		String tcountry = StaticMethod.nullObject2String(request.getParameter("tcountry"));//区县
		String tdate = StaticMethod.nullObject2String(request.getParameter("tdate"));//日期
		String approveProcessType = StaticMethod.nullObject2String(request.getParameter("approveProcessType"));//流程标识
		String msgStr="";
		String returnJson = "";
		
		//获取主表信息
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", approvePId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			if(pnrTransferOffice!=null){
				FormService formService = (FormService) getBean("formService");
				Map<String, String> map = new HashMap<String, String>();
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = null;
				if("1".equals(approveFlag)||"2".equals(approveFlag)){
					TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				    String userId = sessionform.getUserid(); // 当前操作人
				    String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
				    //二次抽查人
					String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6903");//二次抽查人
					if("".equals(dealingPeople)){
						dealingPeople = "superUser";
					}
					String nextPerson="superUser";
					entity = new PnrTransferOfficeHandle();
					Date date = null;
					if("1".equals(approveFlag)){ //通过
						//流程流转参数
						map.put("twoSpotChecksAssignee",dealingPeople);//二次人怎么取？存哪里？一次核验人存哪里
						map.put("acheckState","handle");
						nextPerson=dealingPeople;
						date = new Date();
				        pnrTransferOffice.setFirstVerifyDate(date);//一次核验审批通过时间，用于生成二次抽查报表
				        pnrTransferOffice.setRollbackFlag("0"); 
				        entity.setOperation("01");
					}else if("2".equals(approveFlag)){ //驳回
						//流程流转参数
						if("maleGuest".equals(approveProcessType)){ //公客
							map.put("taskAssignee",pnrTransferOffice.getTaskAssignee());//施工队人？
							nextPerson = pnrTransferOffice.getTaskAssignee();
						}else if("transferOffice".equals(approveProcessType)){ //抢修
							map.put("worker",pnrTransferOffice.getProjectStartPoint());//施工队人
							nextPerson = pnrTransferOffice.getProjectStartPoint();
						}
						map.put("acheckState","rollback");
						
						pnrTransferOffice.setRollbackFlag("1"); //驳回标识
						pnrTransferOffice.setBatch(""); //清空重复采集标识
						pnrTransferOffice.setRecentMainScenesName(pnrTransferOffice.getTransferMainScenesName()); //将最新的主场景中文值重置回施工队的主场景，用于列表显示
						pnrTransferOffice.setRecentCopyScenesName(pnrTransferOffice.getTransferCopyScenesName()); //将最新的子场景中文值重置回施工队的子场景，用于列表显示
						pnrTransferOffice.setMakeupPhotoDate(null); //清空补录照片的时间
						pnrTransferOffice.setMakeupPhotoFlag(""); //清空是否补录照片标识
					entity.setOperation("02");
					}
					
					//流转信息表
					entity.setTheme(pnrTransferOffice.getTheme());
					entity.setThemeId(pnrTransferOffice.getId());
					entity.setReceivePerson(userId);
			        entity.setAuditor(nextPerson);
////					entity.setDoPersons(dealPerformer2);
					entity.setReceiveTime(new Date());
////					entity.setHandleDescription(mainRemark);
////					entity.setFaultHandle(faultHandle);
////					entity.setFaultCause(faultCause);
					entity.setCheckTime(new Date());//审批时间
			        entity.setLinkName("acheck");
					entity.setProcessInstanceId(approvePId);
					entity.setTaskId(approveTaskId);
					
					//工单主表
					pnrTransferOffice.setFirstVerifyDate(new Date());//一次核验完成时间
//					
					transferHandleService.save(entity);
					formService.submitTaskFormData(approveTaskId, map);
					
					processTaskService.setTvalueOfTask(approvePId, nextPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
					pnrTransferOfficeService.save(pnrTransferOffice);
					
					if("2".equals(approveFlag)){ //驳回时，删除对应的场景模板数据
						ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
						Map<String, String> param = new HashMap<String,String>();
						param.put("processType", approveProcessType);
						sceneTemplateService.deleteSceneTemplate(approvePId,"acheck",param);
					}
					
					//更新一次核验报表中的审批状态
					Map<String,String> param=new HashMap<String,String>();
					param.put("approveFlag", approveFlag);
					param.put("approvePId", approvePId);
					param.put("tcountry", tcountry);
					param.put("tdate", tdate);
					param.put("approveUserId", userId);
					param.put("approveProcessType", approveProcessType);
					param.put("reportType", "firstVerify");
					pnrTransferOfficeService.updateApproveFlag(param);
				}else{//其他情况
					msgStr="审批异常！";
				}
			}else{
				msgStr="该工单在工单主表中不存在！";
			}
		}else{
			msgStr="该工单在工单主表中不存在！";
		}
		
		if("".equals(msgStr)){
			returnJson = "{\"result\":\"success\",\"content\":\"审批完成\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\"" + msgStr + "\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}

	/**
	 * 	 更新一次核验报表审批人
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward submitFirstVerifyApprovalSign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionform.getUserid(); // 当前操作人
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("tdate"));//日期
		selectAttribute.setBeginTime(sendStartTime);
		String country = StaticMethod.nullObject2String(request.getParameter("tcountry"));//区县
		selectAttribute.setCounty(country);
		String approvalSign = StaticMethod.nullObject2String(request.getParameter("approvalSign"));//审批人签字
		String name = URLDecoder.decode(approvalSign,"UTF-8");
		selectAttribute.setApprovalSign(name);
		String approvalPhone = StaticMethod.nullObject2String(request.getParameter("approvalPhone"));//联系方式
		selectAttribute.setApprovalPhone(approvalPhone);
		//selectAttribute.setApprovalSign(approvalSign);
		selectAttribute.setPerson(userId);
		//1.先判断该报表的所有工单是不是已经全部审批了。
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		selectAttribute.setReportType("firstVerify");
		int notApprovedSheetCount = pnrTransferOfficeService.getNotApprovedSheetCount(selectAttribute);
		String msgStr="";
		String returnJson="";
		MaleSceneStatisticsModel maleSceneStatisticsModel = null;
		if(notApprovedSheetCount==0){
			pnrTransferOfficeService.submitApprovalSign(selectAttribute);
			//获取材料的统计信息
			maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId,selectAttribute,"acheck",null);
		}else{
			msgStr="不能提交！存在未审批的工单，请全部审批完毕后，再重新提交。";
		}
		if("".equals(msgStr)){
			returnJson = "{\"result\":\"success\",\"content\":\"提交成功\",\"totalAmount\":\""+maleSceneStatisticsModel.getTotalAmount()+"\",\"poleNum\":\""+maleSceneStatisticsModel.getPoleNum()+"\",\"jointBoxNum\":\""+maleSceneStatisticsModel.getJointBoxNum()+"\",\"opticalCableLength\":\""+maleSceneStatisticsModel.getOpticalCableLength()+"\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\"" + msgStr + "\"}";
		}
		PrintWriter out = response.getWriter();
		//System.out.print("-----------------returnJson="+returnJson);
		out.print(returnJson);
		return null;
	}
	
	/**
	 * 	 二次抽查 审批操作
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward secondInspectionApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvePId = StaticMethod.nullObject2String(request.getParameter("approvePId"));
		String approveTaskId = StaticMethod.nullObject2String(request.getParameter("approveTaskId"));
		String approveFlag = StaticMethod.nullObject2String(request.getParameter("approveFlag"));
		String tcountry = StaticMethod.nullObject2String(request.getParameter("tcountry"));//区县
		String tdate = StaticMethod.nullObject2String(request.getParameter("tdate"));//日期
		String approveProcessType = StaticMethod.nullObject2String(request.getParameter("approveProcessType"));//流程标识
		String msgStr="";
		String returnJson = "";
		
		//获取主表信息
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", approvePId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			if(pnrTransferOffice!=null){
				FormService formService = (FormService) getBean("formService");
				Map<String, String> map = new HashMap<String, String>();
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = null;
				if("1".equals(approveFlag)||"2".equals(approveFlag)){
					TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				    String userId = sessionform.getUserid(); // 当前操作人
				    String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
				    //取周期领用表人（即一次核验人）
					String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");//取周期领用表人（即一次核验人）
					if("".equals(dealingPeople)){
						dealingPeople = "superUser";
					}
					String nextPerson="superUser";
					entity = new PnrTransferOfficeHandle();
					Date date = null;
					if("1".equals(approveFlag)){//通过
						//流程流转参数
						map.put("auditReportAssignee",dealingPeople);//综合报表审核人人怎么取？存哪里？
						map.put("twoSpotChecksState","handle");
						nextPerson=dealingPeople;
						date = new Date();
						pnrTransferOffice.setSecondInspectionDate(date);//二次抽查审批通过时间
						pnrTransferOffice.setRollbackFlag("0"); //通过
					entity.setOperation("01");
					}else if("2".equals(approveFlag)){
						//流程流转参数
						if("maleGuest".equals(approveProcessType)){ //公客
							map.put("taskAssignee",pnrTransferOffice.getTaskAssignee());//施工队人
							nextPerson = pnrTransferOffice.getTaskAssignee();
						}else if("transferOffice".equals(approveProcessType)){ //抢修
							map.put("worker",pnrTransferOffice.getProjectStartPoint());//施工队人
							nextPerson = pnrTransferOffice.getProjectStartPoint();
						}
						map.put("twoSpotChecksState","rollback");
						
						pnrTransferOffice.setRollbackFlag("1"); //驳回标识
						pnrTransferOffice.setBatch(""); //清除一次采集标识
						pnrTransferOffice.setJobOrderType(""); //清除二次采集标识
						pnrTransferOffice.setRecentMainScenesName(pnrTransferOffice.getTransferMainScenesName()); //将最新的主场景中文值重置回施工队的主场景，用于列表显示
						pnrTransferOffice.setRecentCopyScenesName(pnrTransferOffice.getTransferCopyScenesName()); //将最新的子场景中文值重置回施工队的子场景，用于列表显示
						pnrTransferOffice.setMakeupPhotoDate(null); //清空补录照片的时间
						pnrTransferOffice.setMakeupPhotoFlag(""); //清空是否补录照片标识
					entity.setOperation("02");
					}
					
					//流转信息表
					entity.setTheme(pnrTransferOffice.getTheme());
					entity.setThemeId(pnrTransferOffice.getId());
					entity.setReceivePerson(userId);
					entity.setAuditor(nextPerson);
////					entity.setDoPersons(dealPerformer2);
					entity.setReceiveTime(new Date());
////					entity.setHandleDescription(mainRemark);
////					entity.setFaultHandle(faultHandle);
////					entity.setFaultCause(faultCause);
					entity.setCheckTime(new Date());//审批时间
					entity.setLinkName("twoSpotChecks");
					entity.setProcessInstanceId(approvePId);
					entity.setTaskId(approveTaskId);
//					
//					//工单主表
					
					transferHandleService.save(entity);
					formService.submitTaskFormData(approveTaskId, map);
					
					processTaskService.setTvalueOfTask(approvePId, nextPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
					pnrTransferOfficeService.save(pnrTransferOffice);
					
					if("2".equals(approveFlag)){ //驳回时，删除对应的场景模板数据
						ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
						Map<String, String> param = new HashMap<String,String>();
						param.put("processType", approveProcessType);
						sceneTemplateService.deleteSceneTemplate(approvePId,"twoSpotChecks",param);
					}
					
					//更新二次抽查报表中的审批状态
					Map<String,String> param=new HashMap<String,String>();
					param.put("approveFlag", approveFlag);
					param.put("approvePId", approvePId);
					param.put("tcountry", tcountry);
					param.put("tdate", tdate);
					param.put("approveUserId", userId);
					param.put("approveProcessType", approveProcessType);
					param.put("reportType", "secondInspection");
					pnrTransferOfficeService.updateApproveFlag(param);
				}else{//其他情况
					msgStr="审批异常！";
				}
			}else{
				msgStr="该工单在工单主表中不存在！";
			}
		}else{
			msgStr="该工单在工单主表中不存在！";
		}
		
		if("".equals(msgStr)){
			returnJson = "{\"result\":\"success\",\"content\":\"审批完成\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\"" + msgStr + "\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	/**
	 * 	 更新二次抽检报表审批人
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward submitSecondInspectionApprovalSign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionform.getUserid(); // 当前操作人
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("tdate"));//日期
		selectAttribute.setBeginTime(sendStartTime);
		String country = StaticMethod.nullObject2String(request.getParameter("tcountry"));//区县
		selectAttribute.setCounty(country);
		String approvalSign = StaticMethod.nullObject2String(request.getParameter("approvalSign"));//审批人签字
		String name = URLDecoder.decode(approvalSign,"UTF-8");
		selectAttribute.setApprovalSign(name);
		String approvalPhone = StaticMethod.nullObject2String(request.getParameter("approvalPhone"));//联系方式
		selectAttribute.setApprovalPhone(approvalPhone);
		//selectAttribute.setApprovalSign(approvalSign);
		selectAttribute.setPerson(userId);
		//1.先判断该报表的审批率是否已经达到了20%以上。
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		selectAttribute.setReportType("secondInspection");
		int notApprovedSheetCount = pnrTransferOfficeService.getNotApprovedSheetCount(selectAttribute);
//		//审批率
//	    double approvalRate = pnrTransferOfficeService.calApprovalRate(userId, selectAttribute);
		String msgStr="";
		String returnJson="";
//		//审批率达到20%以上
//		if(approvalRate >= 0.2){
		MaleSceneStatisticsModel maleSceneStatisticsModel = null;
		if(notApprovedSheetCount==0){
			pnrTransferOfficeService.submitApprovalSign(selectAttribute);
			//当单个工单超时处在二次抽检环节（针对流程），系统跳转到周期领用表环节
			List<String> timeOverList=pnrTransferOfficeService.getTimeOverListOfSecond(selectAttribute);
			if(timeOverList != null && timeOverList.size() > 0){
				//取周期领用表人（即一次核验人）
				String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
			    String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");
			    if("".equals(dealingPeople)){
			    	dealingPeople = "superUser";
			    }
				FormService formService = (FormService) getBean("formService");
				Map<String, String> map = new HashMap<String, String>();
				//流程流转参数
				map.put("auditReportAssignee",dealingPeople);
				map.put("twoSpotChecksState","handle");
				
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTransferOffice = null;
				Iterator<String> it = timeOverList.iterator();  
				while(it.hasNext()){   
					String processInstanceId=it.next();
					Search search = new Search();
					search.addFilterEqual("processInstanceId", processInstanceId);
					pnrTicketList=pnrTransferOfficeService.search(search);
					if (pnrTicketList != null) {
						pnrTransferOffice = pnrTicketList.get(0);
						if(pnrTransferOffice!=null){
							formService.submitTaskFormData(pnrTransferOffice.getTaskId(), map);//提交流程
							processTaskService.setTvalueOfTask(processInstanceId, dealingPeople, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
							pnrTransferOffice.setSecondInspectionDate(new Date());//二次抽查审批通过时间
							pnrTransferOffice.setRollbackFlag("0");
							pnrTransferOfficeService.save(pnrTransferOffice);
						}
					}       
				}   
			}
			
			//获取材料的统计信息
			maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId,selectAttribute,"twoSpotChecks",null);
		}else{
			msgStr="不能提交！存在未审批的工单，请全部审批完毕后，再重新提交。";
			//msgStr="不能提交！抽检比率未达到20%以上，请继续审批，再重新提交。";
		}
		if("".equals(msgStr)){
			returnJson = "{\"result\":\"success\",\"content\":\"提交成功\",\"totalAmount\":\""+maleSceneStatisticsModel.getTotalAmount()+"\",\"poleNum\":\""+maleSceneStatisticsModel.getPoleNum()+"\",\"jointBoxNum\":\""+maleSceneStatisticsModel.getJointBoxNum()+"\",\"opticalCableLength\":\""+maleSceneStatisticsModel.getOpticalCableLength()+"\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\"" + msgStr + "\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}

	
	/**
	 * 	 生成周期领用表
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward createCycleCollarReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String region = StaticMethod.nullObject2String(request.getParameter("tRegion"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("tCountry"));//区县
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("tStartTime"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("tEndTime"));
		//总条数
		String tTotal = StaticMethod.nullObject2String(request.getParameter("tTotal"));
		selectAttribute.setRegion(region);
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//生成的报表编号
		String reportNumber = pnrTransferOfficeService.createCycleCollarReport(userId,tTotal,selectAttribute);
		
		//获取材料的统计信息
		MaleSceneStatisticsModel maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics(userId, selectAttribute, "auditReport", null);
		//获取材料数量
		MaterialQuantityModel materialQuantityModel = pnrTransferOfficeService.getMaterialQuantityOfCycleReport(userId, selectAttribute, null);
		String materialJson = this.maleSceneStatisticsModelToJson(materialQuantityModel);
		
		String returnJson = "{\"result\":\"success\",\"content\":\""+reportNumber+"\"" +
				",\"totalAmount\":\""+maleSceneStatisticsModel.getTotalAmount()+"\"" +
				",\"poleNum\":\""+maleSceneStatisticsModel.getPoleNum()+"\"" +
				",\"jointBoxNum\":\""+maleSceneStatisticsModel.getJointBoxNum()+"\"" +
				",\"opticalCableLength\":\""+maleSceneStatisticsModel.getOpticalCableLength()+"\",\"materialQuantity\":["+materialJson+"]}";
		
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		
		return null;
	}
	
	//把MaleSceneStatisticsModel里的值装换为json串
	private String maleSceneStatisticsModelToJson(MaterialQuantityModel materialQuantityModel){
		String returnJson = "";
		if(materialQuantityModel != null){
			//反射publicFiled类的所有字段 
			Class cla = materialQuantityModel.getClass();
			//获得该类下面所有的字段集合 
			Field[] filed = cla.getDeclaredFields();
			for(Field fd : filed){
				String filedName = fd.getName(); 
				if(!"serialVersionUID".equals(filedName)){
					String getMethodName = "get"+filedName; //转换成字段的get方法 
					Method getMethod;
					try {
						getMethod = cla.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(materialQuantityModel, new Object[] {}); //这个对象字段get方法的值
						returnJson+="{\"filedName\":\""+filedName+"\",\"filedVal\":\""+value.toString()+"\"},";
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
		
			}
			returnJson = returnJson.substring(0, returnJson.length()-1);
		}
		return returnJson;
	}
	
	/**
	 * 	 提交周期领用表
	 	 * @author WangJun
	 	 * @title: firstVerifyApprove
	 	 * @date Nov 27, 2015 11:08:06 AM
	 */
	public ActionForward submitCreateCycleCollarReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String reportNumber = StaticMethod.nullObject2String(request.getParameter("reportNumber"));
		String fuJianVal = StaticMethod.nullObject2String(request.getParameter("fuJianVal"));
		//联系方式
		String approvalPhone = StaticMethod.nullObject2String(request.getParameter("approvalPhone"));
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		selectAttribute.setApprovalPhone(approvalPhone);
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		pnrTransferOfficeService.submitCreateCycleCollarReport(userId, reportNumber, fuJianVal, selectAttribute);
		
		//周期领用表提交审批联系方式和附件成功后，批量归档周期领用下的所有的工单
		String region = StaticMethod.nullObject2String(request.getParameter("tRegion"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("tCountry"));//区县
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("tStartTime"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("tEndTime"));
		selectAttribute.setRegion(region);
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		
		int total = 0;
		List<TaskModel> rPnrTransferList = null;
		total = pnrTransferOfficeService.getCycleCollarReportCountByStartDateAndEndDate(userId,selectAttribute);
		rPnrTransferList = pnrTransferOfficeService.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,0, 1, total);
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if(rPnrTransferList != null && rPnrTransferList.size() > 0){
			for(int i=0;i<rPnrTransferList.size();i++){
				String processInstanceId = rPnrTransferList.get(i).getProcessInstanceId();
				String taskId = rPnrTransferList.get(i).getTaskId();
				Search search = new Search();
				search.addFilterEqual("processInstanceId",processInstanceId);
				List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null) {
					PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
					if(pnrTransferOffice != null){
						//1.先保存流转信息
						PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
						entity.setThemeId(pnrTransferOffice.getId());//工单主键
						entity.setTheme(pnrTransferOffice.getTheme());//工单主题
						entity.setCheckTime(new Date());//审批时间
						entity.setAuditor(userId);//审批人
						entity.setHandleDescription("领用表提交后台批量归档");//处理描述
						entity.setProcessInstanceId(processInstanceId);//工单号
						entity.setTaskId(taskId);//任务ID
						entity.setOperation("01");//代表审批
						entity.setLinkName("auditReport");//当前环节
						entity.setOpinion("批量");//审核意见
						transferHandleService.save(entity);
						
						//2.提交流程
						formService.submitTaskFormData(taskId, map);
						
						//3.获取流程相关数据，保存到主表
						processTaskService.setTvalueOfTask(processInstanceId, "", pnrTransferOffice, PnrTransferOffice.class, "archive", "已归档",true);
						
						//4.更新主表信息
						pnrTransferOffice.setState(5);
						pnrTransferOffice.setOrderAuditPerson(userId);
						pnrTransferOffice.setArchivingTime(sFormat.parse(sFormat.format(new Date())));
						pnrTransferOfficeService.save(pnrTransferOffice);
						
						try{
							//将巡检众筹流程归档
							String netResInspectId=pnrTransferOffice.getNetResInspectId();
							if(netResInspectId !=null && !"".equals(netResInspectId)){
								netResInspectService.workOrderArchiving(netResInspectId, userId);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		String returnJson = "{\"result\":\"success\",\"content\":\"提交成功！\"}";
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
		
	/**
	 * 列表中查看/修改场景模板功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewUpdateSceneTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processType = StaticMethod.nullObject2String(request.getParameter("processType"));//流程类型
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));//流程号
		String linkType = StaticMethod.nullObject2String(request.getParameter("linkType"));//环节类型(前台也需要修改)
		String operateFlag = StaticMethod.nullObject2String(request.getParameter("operateFlag"));//操作权限标识
		String handleFlag = StaticMethod.nullObject2String(request.getParameter("handleFlag"));//单条工单处理还是没处理标识 N：未处理；Y：已处理
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
		String forward =""; //跳转的界面
		String task_key = "";
		String auditorSceneExistFlag = "N";
		try{
			//获取场景数据
			Map<String, Object> resultMap = sceneTemplateService.echoChildScene(processInstanceId, linkType, null);
			task_key = resultMap.get("task_key").toString();
			
			request.setAttribute("mainSceneList", resultMap.get("mainSceneList"));
			request.setAttribute("childSceneWholeModelList", resultMap.get("childSceneWholeModelList"));
			request.setAttribute("sceneDetailWholeModelList", resultMap.get("sceneDetailWholeModelList"));
			request.setAttribute("totalAmount", resultMap.get("totalAmount"));
			
		    auditorSceneExistFlag = resultMap.get("auditorSceneExistFlag").toString();
			forward ="viewUpdateSceneTemplate";
		}catch(Exception e){
			request.setAttribute("msg", "错误编号：sd-task_key-004;工单有问题,请联系管理员(linkType-"+linkType+";task_key-"+task_key+";-"+processInstanceId);
			forward ="failure";
		}
		
		request.setAttribute("processType", processType);
		request.setAttribute("processInstanceId", processInstanceId);
		request.setAttribute("linkType", linkType);
		request.setAttribute("operateFlag", operateFlag);
		request.setAttribute("handleFlag", handleFlag);
		request.setAttribute("auditorSceneExistFlag",auditorSceneExistFlag);
		return mapping.findForward(forward);
	}
	
	/**
	 * 列表中归集工单查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSingleCollection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		//工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		selectAttribute.setProcessInstanceId(processInstanceId);
//		selectAttribute.setProcessInstanceId("358577");
		String siteCd = StaticMethod.nullObject2String(request.getParameter("siteCd"));
		selectAttribute.setSiteCd(siteCd);
//		selectAttribute.setSiteCd("cb00405");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		List<TaskModel> prPnrTransferList = pnrTransferOfficeService.getToreplyJobOfMaleGuestTransmissionBureauJobParentList("",selectAttribute,0, 0,0);
		request.setAttribute("ptaskList", prPnrTransferList);
		request.setAttribute("ptotal", prPnrTransferList.size());
		request.setAttribute("ppageSize", 100);
		return mapping.findForward("viewSingleCollection");
	}
	/**
	 * 列表中归集工单查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//工单号
		String processType = StaticMethod.nullObject2String(request.getParameter("processType"));
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		String linkType = StaticMethod.nullObject2String(request.getParameter("linkType"));
		
		//更新主表中的最新主场景中文名和最新子场景中文名
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		SearchResult t = pnrTransferOfficeService.searchAndCount(search);
		List<PnrTransferOffice> list = t.getResult();
		if(list != null && list.size() > 0){
			PnrTransferOffice pnrTransferOffice = list.get(0);
			if(pnrTransferOffice != null){
				//获取主场景和子场景的中文值，用于列表中的主场景和子场景的显示
				Map<String, String> mainScenesAndCopyScenesName = pnrTransferOfficeService.getMainScenesAndCopyScenesName(request, null);
				pnrTransferOffice.setRecentMainScenesName(mainScenesAndCopyScenesName.get("mainScenesName"));
				pnrTransferOffice.setRecentCopyScenesName(mainScenesAndCopyScenesName.get("copyScenesName"));
				pnrTransferOfficeService.save(pnrTransferOffice);
			}
		}
	
		//场景模板的数据存储
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
		Map<String,String> param = new HashMap<String,String>();
		param.put("processType", processType);//流程类型：公客
		sceneTemplateService.saveSceneTemplate(request,processInstanceId, linkType, param);
		return mapping.findForward("success");
	}
	

	/**
	 * 列表中查看现场拍照
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLiveCamera(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
	    IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService)getBean("pnrAndroidWorkOderPhotoFileService");
	    Search search = new Search();
	    search.addFilterEqual("picture_id", processInstanceId);
	    search.addSortAsc("createTime");

	    List list = pnrService.search(search);
		if (list != null) {
			request.setAttribute("total", list.size());
		} else {
			request.setAttribute("total", 0);
		}
	    request.setAttribute("list", list);
		return mapping.findForward("viewLiveCamera");
	}
	
	/**
	 * 施工队照片补录 工单列表查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward makeupPhotos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		selectAttribute.setEndTime(sendEndTime);
//		String wsNum = request.getParameter("wsNum");
//		selectAttribute.setWsNum(wsNum);
//		String wsTitle = request.getParameter("wsTitle");
//		selectAttribute.setTheme(wsTitle);
//		String status = request.getParameter("status");
//		selectAttribute.setStatus(status);
//		String installAddress = request.getParameter("installAddress");
//		selectAttribute.setInstallAddress(installAddress);
//		String dept = request.getParameter("dept");
//		selectAttribute.setDept(dept);
//		String person = request.getParameter("person");
//		selectAttribute.setPerson(person);
		String maleGuestNumber = request.getParameter("maleGuestNumber");
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		
		selectAttribute.setReportType("maleGuest");//公客

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		//集合条数
		int total = 0;
		total = pnrTransferOfficeService.getMakeupPhotosCount(userId,selectAttribute);

		//集合
		List<TaskModel> rPnrTransferList = null;
		rPnrTransferList = pnrTransferOfficeService.getMakeupPhotosList(userId,selectAttribute,firstResult, endResult, pageSize);
		
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("selectAttribute", selectAttribute);
		
		return mapping.findForward("makeupPhotos");
	}
	
	//施工队补录照片查看
	public ActionForward conditionSelectPhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
//		request.setAttribute("deptId", deptId);
//		String country = deptId;
//		if (country.length() > 5) {
//			country = country.substring(0, 5);
//		}
//		if ("admin".equals(userId) || "superUser".equals(userId)) {// 超级管理员
//			country = "";
//		}
//		request.setAttribute("country", country);
		
		
		// 开始时间
		String sheetAcceptLimit = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		// 结束时间
		String sheetCompleteLimit = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		// 图片描述
		String photoDescribe = StaticMethod.nullObject2String(request.getParameter("photoDescribe"));
		// 故障地址
		String faultLocation = StaticMethod.nullObject2String(request.getParameter("faultLocation"));
//		// 拍照时间
//		String createPhotoTime = StaticMethod.nullObject2String(request
//				.getParameter("createPhotoTime"));
//		// 拍照人
//		String photoCreate = StaticMethod.nullObject2String(request
//				.getParameter("photoCreate"));
//		//已选择的照片IDS
//		String selectedPhotoIds =StaticMethod.nullObject2String(request.getParameter("selectedPhotoIds"));
//		//标签名
//		String tagName =StaticMethod.nullObject2String(request.getParameter("tagName"));
		//照片类型
		String photoType = StaticMethod.nullObject2String(request.getParameter("photoType"));
//		//照片子类型（是起点照片还是终点照片）
//		String photoSubType = StaticMethod.nullObject2String(request.getParameter("photoSubType"));
		//工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		//回单时间
		String replyTime = StaticMethod.nullObject2String(request.getParameter("replyTime"));
		if("".equals(replyTime)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date lastReplyTime = format.parse("2016-01-01 00:00:00");
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if(list != null && list.size() > 0){
				PnrTransferOffice pnrTransferOffice = list.get(0);
				if(pnrTransferOffice != null){
					if(pnrTransferOffice.getLastReplyTime() != null){
						lastReplyTime = pnrTransferOffice.getLastReplyTime();
					}
				}
			}
			
			replyTime = format.format(lastReplyTime); 
		}
		
		//封装查询条件
		Map<String,String> param=new HashMap<String,String>();
		param.put("userId", userId);
		param.put("sheetAcceptLimit", sheetAcceptLimit);
		param.put("sheetCompleteLimit", sheetCompleteLimit);
		param.put("photoDescribe", photoDescribe);
		param.put("faultLocation", faultLocation);
		param.put("photoType", photoType);
		//param.put("photoSubType", photoSubType);
		param.put("replyTime", replyTime);
		
		// 根据登录人所述地市查询一定时间内的服务器上的图片
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		List<PnrAndroidPhotoFile> photoList = pnrTransferOfficeService.getPrecheckPhotoes(param);
		if (photoList != null) {
			request.setAttribute("total", photoList.size());
		} else {
			request.setAttribute("total", 0);
		}
		request.setAttribute("list", photoList);
		request.setAttribute("sheetAcceptLimit", sheetAcceptLimit);
		request.setAttribute("sheetCompleteLimit", sheetCompleteLimit);
		request.setAttribute("photoDescribe", photoDescribe);
		request.setAttribute("faultLocation", faultLocation);
		request.setAttribute("photoType", photoType);
		//request.setAttribute("photoSubType", photoSubType);
		request.setAttribute("processInstanceId", processInstanceId);
		request.setAttribute("replyTime", replyTime);
		return mapping.findForward("openSelectPhotoView");
	}
	
	//选择补录照片提交 照片和工单关联上
	public ActionForward submitMakeupPhotos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String errMsg=""; //错误信息
		String photoesIds = StaticMethod.nullObject2String(request.getParameter("photoes"));//选择的照片的id串
		if(!"".equals(photoesIds)){
			String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));//工单号
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if(list != null && list.size() > 0){
				PnrTransferOffice pnrTransferOffice = list.get(0);
				if(pnrTransferOffice != null){
					//取照片中的上传的最早时间
					String tempPhotoesIds=photoesIds.substring(0, photoesIds.length()-1);
					String tempMinDate = pnrTransferOfficeService.getMinDateOfPhoto(tempPhotoesIds,null);
					if(!"".equals(tempMinDate)){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date minDate = format.parse(tempMinDate);//最早时间
						//保存照片的关联关系
						// PNR_ANDROID_WORKORDER_PHOTO
						IPnrAndroidWorkOderPhotoFileService pnrAndroidWorkOderPhotoFileService = (IPnrAndroidWorkOderPhotoFileService) getBean("pnrAndroidWorkOderPhotoFileService");
						//pnr_android_photo
						IPnrAndroidMgr ipnrAndroidMgrImpl = (IPnrAndroidMgr) getBean("ipnrAndroidMgrImpl");
						String photoId="";
						//IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
						
						String[] photoesId = photoesIds.split(",");
						if (photoesId != null && photoesId.length > 0) {
							for (String str : photoesId) {
								if (str != null && !"".equals(str)) {
									photoId=str.replaceAll("'", "");
									//根据照片id取pnr_android_photo数据
									PnrAndroidPhotoFile pnrAndroidPhotoFile = this.getPnrAndroidPhotoFileById(ipnrAndroidMgrImpl, photoId);
									if(pnrAndroidPhotoFile != null){
										PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile = new PnrAndroidWorkOderPhotoFile();
										pnrAndroidWorkOderPhotoFile.setPicture_id(processInstanceId);//工单号
										pnrAndroidWorkOderPhotoFile.setCreateTime(new Date());//创建时间
										pnrAndroidWorkOderPhotoFile.setUserId(pnrAndroidPhotoFile.getUserId());//拍照人
										pnrAndroidWorkOderPhotoFile.setPath(pnrAndroidPhotoFile.getPath());//路径
										pnrAndroidWorkOderPhotoFile.setImgPath(pnrAndroidPhotoFile.getPath());
										pnrAndroidWorkOderPhotoFile.setState(2);//代表 接口看图片的方式
										pnrAndroidWorkOderPhotoFile.setLatitude(pnrAndroidPhotoFile.getLatitude());
										pnrAndroidWorkOderPhotoFile.setLongitude(pnrAndroidPhotoFile.getLongitude());
										pnrAndroidWorkOderPhotoFile.setSystemTime(new Date());
										//pnrAndroidWorkOderPhotoFile.setDistance(distance);
										pnrAndroidWorkOderPhotoFile.setPhotoType(pnrAndroidPhotoFile.getPhotoType());
										pnrAndroidWorkOderPhotoFile.setId_type("makeup");
									//	pnrAndroidWorkOderPhotoFile.setPhotoSubType(photoSubType);
										pnrAndroidWorkOderPhotoFileService.save(pnrAndroidWorkOderPhotoFile);//往pnr_android_workorder_photo表存数据
										
										pnrAndroidPhotoFile.setState("1");//更新pnr_android_photo的照片状态
										ipnrAndroidMgrImpl.save(pnrAndroidPhotoFile);
									}
								}
							}
						}
						
						//更新工单主表里的 状态和时间
						pnrTransferOffice.setMakeupPhotoDate(minDate);
						pnrTransferOffice.setMakeupPhotoFlag("2");
						pnrTransferOfficeService.save(pnrTransferOffice);
				}else{
					errMsg = "工单不存在！";	
				}
			}else{
				errMsg = "工单不存在！";
			}
			}else{
				errMsg = "未获取到最小的拍照时间";
			}
		}else{
			errMsg = "未选择照片！";
		}
		//返回结果
		String returnJson = "";
		if("".equals(errMsg)){
			returnJson = "{\"result\":\"success\",\"content\":\"提交成功！\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\""+errMsg+"\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	private PnrAndroidPhotoFile getPnrAndroidPhotoFileById(IPnrAndroidMgr ipnrAndroidMgrImpl,String id){
		Search search = new Search();
		search.addFilterEqual("id", id);
		SearchResult t = ipnrAndroidMgrImpl.searchAndCount(search);
		List<PnrAndroidPhotoFile> list = t.getResult();
		// 回复message
		PnrAndroidPhotoFile pnrAndroidPhotoFile = list.get(0);
		return pnrAndroidPhotoFile;
	}
	
	//打开驳回界面
	public ActionForward showRejectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String queryFlag = StaticMethod.nullObject2String(request.getParameter("queryFlag"));//查询标识
//		request.setAttribute("queryFlag", queryFlag);
		return mapping.findForward("showRejectPage");		
	}
	
	//施工队 驳回
	public ActionForward transferRollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String errorMsg = ""; //返回的错误信息
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));//工单号
		String rejectReason = StaticMethod.nullObject2String(request.getParameter("rejectReason"));//工单号
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));//任务号
		
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		if(pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			if(pnrTransferOffice != null){
				Date date = new Date();
				
				//pnrTransferOffice.setLastReplyTime(new Date());
				//改变工单状态：改为8，达到隐藏工单的效果
				pnrTransferOffice.setState(8);
				
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTransferOffice.getId());//工单主键
				entity.setTheme(pnrTransferOffice.getTheme());//工单主题
				entity.setReceiveTime(date);//回单时间
				entity.setReceivePerson(userId);//回单人ID
				entity.setHandleDescription("施工队驳回");//处理描述
				entity.setProcessInstanceId(processInstanceId);//工单流程ID
				entity.setTaskId(taskId);//任务ID
				entity.setState("dismiss");
				entity.setCheckTime(date);//审批时间
				entity.setFaultCause(rejectReason);//故障原因
				entity.setLinkName("auditor");//当前环节的task_def_key_
				entity.setOperation("2");//代表驳回
				
				//流转信息
				transferHandleService.save(entity);
				
				//工单主表更新
				pnrTransferOfficeService.save(pnrTransferOffice);
				
				//判断是否是接口派单,是就进行接口通知
				/*String maleGuestNumber =  pnrTransferOfficeService.getMaleGuestNumberByThemeId(pnrTransferOffice.getId());
				if(maleGuestNumber!=null && !"".equals(maleGuestNumber=maleGuestNumber.trim())){
					// 调用方法,查询接口需要的数据
					TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService.getMaleGuestReturnData(maleGuestNumber);
					Thread aThread = new Thread(new MaleGuestReturnThead(sFormat.format(new Date()),CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
					aThread.start();
				}*/
				
				String maleGuestState = pnrTransferOffice==null?"":pnrTransferOffice.getMaleGuestState();	
				String titleId = pnrTransferOffice==null?"":pnrTransferOffice.getId();

				TransferMaleGuestReturn maleGuest  = new TransferMaleGuestReturn();			
				String fault_Name="";
				if("2".equals(rejectReason)){
					fault_Name="障碍非当前分局责任";
				}else if("3".equals(rejectReason)){
					fault_Name="障碍非传输局责任";
				}
				maleGuest.setConfCRMTicketNo(pnrTransferOffice.getMaleGuestNumber());	
				// 回单标示
				maleGuest.setFlag(rejectReason);	
				// 回单时间
				maleGuest.setBack_dt(sFormat.format(date));
				// 处理人姓名id
				maleGuest.setBack_userid(userId);
				// 处理人姓名
				maleGuest.setBack_username(sessionform.getUsername());
				// 故障原因id
				maleGuest.setReason_id(rejectReason);
				// 描述
				maleGuest.setBack_info("");		
				// 故障原因
				maleGuest.setReason_name(fault_Name);
				
				pnrTransferOfficeService.maleGuestHandleInterfaceCall(maleGuest,maleGuestState,processInstanceId,titleId);
				
			} else {
				errorMsg = "未查询到工单";
			}
		} else {
			errorMsg = "查询工单信息异常";
		}
		//返回结果
		String returnJson = "";
		if("".equals(errorMsg)){
			returnJson = "{\"result\":\"success\",\"content\":\"提交成功！\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\""+errorMsg+"\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;		
	}
	
	//查看关联工单
	public ActionForward viewRelatedWorkOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		selectAttribute.setProcessInstanceId(processInstanceId);
		String siteCd = StaticMethod.nullObject2String(request.getParameter("siteCd"));
		selectAttribute.setSiteCd(siteCd);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
	//	List<TaskModel> prPnrTransferList = pnrTransferOfficeService.getToreplyJobOfMaleGuestTransmissionBureauJobParentList(userId,selectAttribute,0, 0,0);
		List<TaskModel> prPnrTransferList = pnrTransferOfficeService.getSubWorkOrderOfSingleImputationList(userId, selectAttribute, 0, 0,0);
		request.setAttribute("ptaskList", prPnrTransferList);
		request.setAttribute("ptotal", prPnrTransferList.size());
		request.setAttribute("ppageSize", 100);
		return mapping.findForward("viewRelatedWorkOrder");	
	}
	
	//修改工单主题
	public ActionForward updateTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String errorMsg = ""; //错误提示
		String returnJson = ""; //返回信息
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId")); //工单号
		String theme = StaticMethod.nullObject2String(request.getParameter("theme")); //主题
		if(!"".equals(processInstanceId)){
			if(!"".equals(theme)){
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				Search search = new Search();
				search.addFilterEqual("processInstanceId",processInstanceId);
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if(list != null && list.size() > 0){
					PnrTransferOffice pnrTransferOffice = list.get(0);
					if(pnrTransferOffice != null){
						pnrTransferOffice.setTheme(theme);
						pnrTransferOfficeService.save(pnrTransferOffice);
					}else{
						errorMsg = "该工单不存在!";
					}
				}else{
					errorMsg = "未查询到改工单信息!";
				}
			}else{
				errorMsg = "工单主题不能为空!";
			}
		}else{
			errorMsg = "未获取到工单号!";
		}
		
		//返回结果信息
		if("".equals(errorMsg)){
			returnJson = "{\"result\":\"success\",\"content\":\"工单主题更新成功\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\""+errorMsg+"\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	//导出一次核验、二次抽检、周期报表详情
	public ActionForward exportDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		//报表标识
		String reportFlag = StaticMethod.nullObject2String(request.getParameter("reportFlag"));
		// 地市
		//String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//区县
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		HSSFWorkbook wb = pnrTransferOfficeService.exportDetails(userId,reportFlag,selectAttribute,null);
	
		/*写入临时文件-----------------------------------------------------------------*/	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
     		String nowDate=df.format(new Date());// new Date()为获取当前系统时间
     		String path=System.getProperty("catalina.home")+"/webapps"+request.getContextPath()+"/sceneExcelAccessory/temporary/";
//     		String path="D:/";
     		String fileName=nowDate+".xls";
     		OutputStream output = new FileOutputStream(path+fileName);
     		fileName = new String(fileName.getBytes("GBK"), "utf-8");  
            response.reset();  
            response.setHeader("Content-Disposition", "attachment;filename="  
                    + fileName);// 指定下载的文件名   
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Pragma", "no-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0);  
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
           
			
         try {   
	            bufferedOutPut.flush();  
	            wb.write(bufferedOutPut);  
	            bufferedOutPut.close();
	            /*下载文件-----------------------------------------------------------------------------*/
	            String downFilename="报表导出.xls";//要下载的文件名称
	            if("acheck".equals(reportFlag)){//一次核验
	            	downFilename="一次核验导出.xls";
	    		}else if("twoSpotChecks".equals(reportFlag)){//二次抽检
	    			downFilename="二次抽检导出.xls";
	    		}else if("auditReport".equals(reportFlag)){//周期领用表
	    			downFilename="周期领用表导出.xls";
	    		}
	            String filepath=path+fileName;//要下载的文件完整路径
			    response.setContentType("text/plain");
			    response.setHeader("Location",downFilename);
			    response.setHeader("Content-Disposition", "attachment; filename=" +new String(downFilename.getBytes("gb2312"),"iso8859-1")); 
			    response.setCharacterEncoding("utf-8"); 
			    OutputStream outputStream = response.getOutputStream();
			    InputStream inputStream = new FileInputStream(filepath);
			    byte[] buffer = new byte[1024];
			    int i = -1;
			    while ((i = inputStream.read(buffer)) != -1) {
			    	outputStream.write(buffer, 0, i);
			    }
		        outputStream.flush();
		        outputStream.close();
		        inputStream.close();

    	 } catch (IOException e) {  
	            e.printStackTrace();  
	            System.out.println("Output   is   closed ");  
    	 } finally {
    		 	File fileXls =new File(path+fileName);
    		 	if(fileXls.exists()){
    		 		fileXls.delete();
    		 	}
    	 }
         
		return null;

	}
	
	/**
	 * 	 导出周期领用表的材料数量汇总
	 	 * @author WANGJUN
	 	 * @title: exportMaterialQuantity
	 	 * @date May 26, 2016 9:17:11 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward exportMaterialQuantity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		//报表标识
		String reportFlag = StaticMethod.nullObject2String(request.getParameter("reportFlag"));
		// 地市
		//String region = StaticMethod.nullObject2String(request.getParameter("region"));
		//区县
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		HSSFWorkbook wb = pnrTransferOfficeService.exportMaterialQuantity(userId,selectAttribute, reportFlag, null);
	
		/*写入临时文件-----------------------------------------------------------------*/	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
     		String nowDate=df.format(new Date());// new Date()为获取当前系统时间
     		String path=System.getProperty("catalina.home")+"/webapps"+request.getContextPath()+"/sceneExcelAccessory/temporary/";
//     		String path="D:/";
     		String fileName=nowDate+".xls";
     		OutputStream output = new FileOutputStream(path+fileName);
     		fileName = new String(fileName.getBytes("GBK"), "utf-8");  
            response.reset();  
            response.setHeader("Content-Disposition", "attachment;filename="  
                    + fileName);// 指定下载的文件名   
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Pragma", "no-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0);  
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
           
			
         try {   
	            bufferedOutPut.flush();  
	            wb.write(bufferedOutPut);  
	            bufferedOutPut.close();
	            /*下载文件-----------------------------------------------------------------------------*/
	            String downFilename="材料数量导出.xls";//要下载的文件名称
//	            if("acheck".equals(reportFlag)){//一次核验
//	            	downFilename="一次核验导出.xls";
//	    		}else if("twoSpotChecks".equals(reportFlag)){//二次抽检
//	    			downFilename="二次抽检导出.xls";
//	    		}else if("auditReport".equals(reportFlag)){//周期领用表
//	    			downFilename="周期领用表导出.xls";
//	    		}
	            String filepath=path+fileName;//要下载的文件完整路径
			    response.setContentType("text/plain");
			    response.setHeader("Location",downFilename);
			    response.setHeader("Content-Disposition", "attachment; filename=" +new String(downFilename.getBytes("gb2312"),"iso8859-1")); 
			    response.setCharacterEncoding("utf-8"); 
			    OutputStream outputStream = response.getOutputStream();
			    InputStream inputStream = new FileInputStream(filepath);
			    byte[] buffer = new byte[1024];
			    int i = -1;
			    while ((i = inputStream.read(buffer)) != -1) {
			    	outputStream.write(buffer, 0, i);
			    }
		        outputStream.flush();
		        outputStream.close();
		        inputStream.close();

    	 } catch (IOException e) {  
	            e.printStackTrace();  
	            System.out.println("Output   is   closed ");  
    	 } finally {
    		 	File fileXls =new File(path+fileName);
    		 	if(fileXls.exists()){
    		 		fileXls.delete();
    		 	}
    	 }
         
		return null;
	}

	/**
	 * 	 查询 周期领用表提交审批后没有归档的工单
	 *   给现场人员维护用
	 *   非用户提出的需求
	 	 * @author WangJun
	 	 * @title: manualBatchArchiveForCycleReport
	 	 * @date Jun 8, 2016 9:58:59 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward manualBatchArchiveForCycleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		selectAttribute.setBeginTime(sendStartTime);
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		selectAttribute.setEndTime(sendEndTime);
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		selectAttribute.setTheme(wsTitle);
		String maleGuestNumber = StaticMethod.nullObject2String(request.getParameter("maleGuestNumber"));
		selectAttribute.setMaleGuestNumber(maleGuestNumber);
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		int total = 0;
		total = pnrTransferOfficeService.manualBatchArchiveForCycleReportCount(userId,selectAttribute);

		// 工单管理-"公客-传输局工单"-手动归档工单 集合
		List<TaskModel> rPnrTransferList = null;
	    rPnrTransferList = pnrTransferOfficeService.manualBatchArchiveForCycleReportList(userId,selectAttribute,firstResult,endResult,pageSize);
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("selectAttribute", selectAttribute);
		return mapping.findForward("manualBatchArchiveForCycleReport");
	}
	
	/**
	 * 	周期领用表提交审批后没有归档的工单 批量归档操作
	 	 * @author WANGJUN
	 	 * @title: doManualBatchArchiveForCycleReport
	 	 * @date Jun 8, 2016 1:34:56 PM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward doManualBatchArchiveForCycleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PrintWriter out = response.getWriter();
		String userId = sessionForm.getUserid();
		String ids = StaticMethod.nullObject2String(request.getParameter("ids"));
		String[] taskids=ids.split(";");
		// 存储归档时间
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		for(int i=0;i<taskids.length;i++){
			String[] str =taskids[i].split(",");
			String taskId = str[0];
			String processInstanceId = str[1];
			String submitUserId = str[2];
			String submitDate= str[3];
			
			System.out.println(i+"------taskId="+taskId);
			System.out.println(i+"------processInstanceId="+processInstanceId);
			System.out.println(i+"------submitUserId="+submitUserId);
			System.out.println(i+"------submitDate="+submitDate);
			
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
				if(pnrTransferOffice != null){
					//1.先保存流转信息
					PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
					entity.setThemeId(pnrTransferOffice.getId());//工单主键
					entity.setTheme(pnrTransferOffice.getTheme());//工单主题
					entity.setCheckTime(new Date());//审批时间
					entity.setReceivePerson(submitUserId);//回单人ID
					try{
						entity.setReceiveTime(sdf.parse(submitDate));
					}catch(Exception e){
						e.printStackTrace();
					}
					entity.setAuditor(userId);//审批人
					entity.setHandleDescription("前台手工批量归档");//处理描述
					entity.setProcessInstanceId(processInstanceId);//工单号
					entity.setTaskId(taskId);//任务ID
					entity.setOperation("01");//代表审批
					entity.setLinkName("auditReport");//当前环节
					entity.setOpinion("批量");//审核意见
					transferHandleService.save(entity);
					
					//2.提交流程
					formService.submitTaskFormData(taskId, map);
					
					//3.获取流程相关数据，保存到主表
					processTaskService.setTvalueOfTask(processInstanceId, "", pnrTransferOffice, PnrTransferOffice.class, "archive", "已归档",true);
					
					//4.更新主表信息
					pnrTransferOffice.setState(5);
					pnrTransferOffice.setOrderAuditPerson(submitUserId);
					try{
						pnrTransferOffice.setArchivingTime(sdf.parse(submitDate));
					}catch(Exception e){
						e.printStackTrace();
					}
					pnrTransferOfficeService.save(pnrTransferOffice);
				}
			}
		}
	    out.print("true");
		return null;
	}
	
	/**
	 * 	
	 	 * @author WANGJUN
	 	 * @title: gotoDetailCycleReport
	 	 * @date Jun 30, 2016 3:07:41 PM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward gotoDetailCycleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnView = "";
		String reportNum = StaticMethod.nullObject2String(request.getParameter("processInstanceId")); //此处为报表编号
		if(!"".equals(reportNum)){
			//根据报表编号取开始日期、结束日期、区县
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Map<String,String> paramMap = pnrTransferOfficeService.getTimeCountyByReportNum(reportNum);
			String sendStartTime = StaticMethod.nullObject2String(paramMap.get("sendStartTime"));
			String endTime = StaticMethod.nullObject2String(paramMap.get("endTime"));
			String country = StaticMethod.nullObject2String(paramMap.get("county"));
			
			if(!"".equals(sendStartTime)&&!"".equals(endTime)&&!"".equals(country)){
				int pageSize = CommonUtils.PAGE_SIZE;
				String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
				int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
				int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				sendStartTime = sdf.format(sdf.parse(sendStartTime));
				endTime = sdf.format(sdf.parse(endTime));
				
				//封装查询条件
				MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
				selectAttribute.setBeginTime(sendStartTime);
				selectAttribute.setEndTime(endTime);
				selectAttribute.setCounty(country);

				//1.材料表单
				//获取材料的统计信息
				MaleSceneStatisticsModel maleSceneStatisticsModel = pnrTransferOfficeService.getMaleSceneStatistics("", selectAttribute, "auditReport", null);
				request.setAttribute("maleSceneStatisticsModel", maleSceneStatisticsModel);
				
				//获取材料数量统计
				MaterialQuantityModel materialQuantityModel = pnrTransferOfficeService.getMaterialQuantityOfCycleReport("", selectAttribute, null);
				request.setAttribute("materialQuantityModel", materialQuantityModel);
				
				//2.工单详情
				int total = pnrTransferOfficeService.getCycleCollarReportCountByStartDateAndEndDate("",selectAttribute);
				List<TaskModel> rPnrTransferList = pnrTransferOfficeService.getCycleCollarReportListByStartDateAndEndDate("",selectAttribute,firstResult, endResult, pageSize);
				
				//3.在列表中可以点开查看场景模板，可以查看工单照片和归集的子工单。
				//查询条件里的地市相关

				request.setAttribute("operateFlag","no");
				request.setAttribute("statisticsFlag","yes");
				
				request.setAttribute("taskList", rPnrTransferList);
				request.setAttribute("pageSize", pageSize);
				request.setAttribute("total", total);
				request.setAttribute("reportNum", reportNum);
				
				returnView = "cycleReportDetail";
			}else{
				request.setAttribute("msg","开始时间、结束时间、区县不能为空!");
				returnView = "failure";
			}
		}else{
			request.setAttribute("msg", "报表编号不能为空！");
			returnView = "failure";
		}
		return mapping.findForward(returnView);
	}
	
}
