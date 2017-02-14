package com.boco.activiti.partner.process.webapp.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferOldPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrTransferOldPrecheckAction extends SheetAction {
	private static Logger logger = Logger.getLogger(PnrTransferPrecheckAction.class);
	private final String processDefinitionKey = "newTransferPrecheck";
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
	private final static String TASK_TRANSFERCOUNTRYCSJ = "program";
	private final static String TASK_TRANSFERCITYCJS = "csjCheck";
	private final static String TASK_TRANSFERCITYGS = "sgsCheck";
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
		// 默认为0，正常派单。
		int state = 0;

		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		setTransferOfficeByRequest(request, pnrTransferOffice);

		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String theme = pnrTransferOffice.getTheme();
		Date mainFaultOccurTime = pnrTransferOffice.getSubmitApplicationTime();
		String subType = pnrTransferOffice.getSubTypeName();
		String workOrderType = pnrTransferOffice.getWorkOrderTypeName();
		String workOrderDegree = pnrTransferOffice.getWorkOrderDegree();
		workOrderDegree = getDictNameByDictid(workOrderDegree);
		

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
		pnrTransferOffice.setThemeInterface("interface");

		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,"1");
		}
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);
		
		//发短信
		//短信接收人
		String messagePerson = pnrTransferOffice.getCountryCSJ();
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			workOrderDegree+","+TASK_WORKORDERTYPE+workOrderType+","+TASK_SUBTYPE+subType+"。";
			CommonUtils.sendMsg(msg, messagePerson);
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

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");

		IPnrTransferOldPrecheckService pnrTransferPrecheckService = (IPnrTransferOldPrecheckService) getBean("pnrTransferOldPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobCount(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferPrecheckService
					.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, status, firstResult,
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
		return mapping.findForward("backlogList");
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
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			//search.addSort("receiveTime", true);// 按回复时间排序

			SearchResult<PnrTransferOfficeHandle> results = transferHandleService
					.searchAndCount(search);
			PnrTransferOfficeHandle csjHandle = new PnrTransferOfficeHandle();
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
			String nn = task.getTaskDefinitionKey();
			// attachments-end
			if (task.getTaskDefinitionKey().equalsIgnoreCase("program")) {// 方案制定
				request.setAttribute("pnrTransferOfficeHandleList", handlelist);
				request.setAttribute("listsize", handleSize);
				// 显示附件
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("pnrTransferProgram");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("csjCheck")) {// 传输局审批
				request.setAttribute("listsize", handleSize);
				request.setAttribute("pnrTransferOfficeHandleList", handlelist);
				// 显示附件
				showReplySetAttribute(request, handlelist);

				String findForward = "csjCheck";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("sgsCheck")) {// 市公司审批
				request.setAttribute("listsize", handleSize);
				request.setAttribute("pnrTransferOfficeHandleList", handlelist);
				// 显示附件
				showReplySetAttribute(request, handlelist);

				String findForward = "sgsCheck";

				return mapping.findForward(findForward);
			}else if(task.getTaskDefinitionKey()//自动转派
					.equalsIgnoreCase("automation")){
				return mapping.findForward("automation");
			} 
			else if (task.getTaskDefinitionKey()
					.equalsIgnoreCase("sendOrder")) {// 工单转派
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
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"daiweiAudit")) {// 代维审核
				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getCreateWork());
				}
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("secondAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"orderAudit")) {// 工单审核

				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getSecondInitiator());
				}
				showReplySetAttribute(request, handlelist);
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
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
		request.setAttribute("success", "check");

		// 发短信
		if (isSend) {
			String messagePerson="";
			if("2".equals(step)){
				messagePerson = pnrTicket.getCityCSJ();
			}else if("3".equals(step)){
				messagePerson = pnrTicket.getCityGS();
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
			pnrTicket.setState(8);
		}
		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,"4");
		}
		// attachment--end
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		//formService.submitTaskFormData(taskId, map);
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
		transferHandleService.save(entity);
		// 保存审核意见--end
		
		if(!isSend){//审核未通过，短息通知
			 CommonUtils.sendMsg(msg, messagePerson);
			
		}

		request.setAttribute("success", "check");

		return mapping.findForward("success");
	}
	/**
	 * mainSecond -自动转派
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
		formService.submitTaskFormData(taskId, map);
		// 流程结束
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = new PnrTransferOffice();
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		entity.setInitiator(initiator);
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

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 接收人
		String taskAssignee = request.getParameter("auditor");
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

		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, taskAssignee);
			}
		}
		formService.submitTaskFormData(taskId, map);
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
		  CommonUtils.sendMsg(msg, taskAssignee);
		  
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
				.getParameter("transferAudit"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("taskAssignee"));

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
		transferHandleService.save(entity);
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
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
			map.put("auditor", auditor);
		}
		formService.submitTaskFormData(taskId, map);
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
				.getParameter("taskAssignee"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

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
		entity.setTaskId(taskId);
		
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
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
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
		
		try {
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
	
			String hjflag="";
			//当前所处环节
			String linkName="";
			if (handle.equals(TASK_TRANSFERCOUNTRYCSJ)) {//制定方案驳回

				map.put("initiator", returnPerson);
				map.put("handleState", "rollback");
				msgPerson = returnPerson;

			} else if (handle.equals(TASK_TRANSFERCITYCJS)) {//市传输局驳回

				map.put("taskAssignee", returnPerson);
				map.put("handleState", "rollback");
				msgPerson = returnPerson;
			}
			else if (handle.equals(TASK_TRANSFERCITYGS)) {//市公司驳回

				map.put("taskAssignee",returnPerson);
				map.put("handleState", "rollback");
				msgPerson=returnPerson;
			}
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
	 * 施工队--回退
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
		String theme = request.getParameter("theme");
		theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
	
			String hjflag="";
			//当前所处环节
			String linkName="";
			if (handle.equals(TASK_TRANSFERHANDLE)) {//施工队回退
				hjflag="施工队驳回";
				map.put("taskAssignee", initiator);
				map.put("handleState", "rollback");
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
	 * 驳回提交
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitReject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 System.out.println("是否进入提交驳回");
		 String taskId = request.getParameter("taskId");
		 System.out.println("驳回提交:taskId="+taskId);
		 String processInstanceId = request.getParameter("processInstanceId");
		 System.out.println("驳回提交:processInstanceId="+processInstanceId);
		 String marker = request.getParameter("createWork");
		 System.out.println("驳回提交:marker="+marker);
		 String rejectReason = request.getParameter("rejectReason");
		 System.out.println("rejectReason="+rejectReason);
		 //方案制定驳回
		 if("createWork".equals(marker)){
			 
		 }else if("countryCSJ".equals(marker)){
			 
		 }else if("cityGS".equals(marker)){//市公司驳回
			 
		 }
		 
		 	
		return null;
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
					troubleTicketModel.setStatusName("商城归档");
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
					if(state==8){
						troubleTicketModel.setStatusName("市公司审批完毕");
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
				String themeInterface = list.get(0).getThemeInterface();
				//判断是否是传输局运维商城接口数据
				if(themeInterface!=null && "interface".equals(themeInterface)){
					transferModel.setSendTime(list.get(0).getSubmitApplicationTime());
					transferModel.setTheme(list.get(0).getTheme());
					transferModel.setInitiator(list.get(0).getInitiator());
					TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
							.get(0).getTaskAssignee(), "");
					if (pu != null) {
						transferModel.setDeptId(pu.getDeptid());
					}
					rPnrTicketList.add(transferModel);
				}
			}

		}
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int)total);
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
			}
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if (list != null && list.size() != 0) {
				String themeInterface = list.get(0).getThemeInterface();
				if(themeInterface!=null && "interface".equals(themeInterface)){
					
					troubleTicketModel.setSendTime(list.get(0).getSubmitApplicationTime());
					troubleTicketModel.setTheme(list.get(0).getTheme());
					troubleTicketModel.setInitiator(list.get(0).getInitiator());
					int state = list.get(0).getState();
					if(state==8){
						troubleTicketModel.setStatusName("市公司审批完毕");
					}
					TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
							.get(0).getTaskAssignee(), "");
					if (pu != null) {
						troubleTicketModel.setDeptId(pu.getDeptid());
						
					}
					rPnrTicketList.add(troubleTicketModel);
				}
			}
		}
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
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
				int state = list.get(0).getState();
				if(state==8){
					troubleTicketModel.setStatusName("市公司审批完毕");
				}
				String message = pnrTransferOfficeService.getLoginPersonStatusToPrecheck(
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
		// 预检预修提交申请时间
		String mainFaultOccurTime = request.getParameter("mainFaultOccurTime");
		// 工单子类型
		String subType = request.getParameter("subType");
		// 故障描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 工单类型
		String workOrderType = StaticMethod.nullObject2String(request
				.getParameter("workOrderType"));
		// 关键字
		String keyWord = StaticMethod.nullObject2String(request
				.getParameter("keyWord"));
		// 紧急程度
		String workOrderDegree = StaticMethod.nullObject2String(request
				.getParameter("workOrderDegree"));
		// 接收人
		String taskAssignee = request.getParameter("taskAssignee");
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");
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
		// 接收人
		pnrTransferOffice.setTaskAssignee(taskAssignee);
		pnrTransferOffice.setCountryCSJ(taskAssignee);
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		String cityConpany = precheckRoleModel.getCityCompany();
		List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(taskAssignee,cityConpany);
		String cityGS = "superUser";
		if(list!=null && list.size()>0){
			if(list.get(0).get("USERID")!=null && !"".equals(list.get(0).get("USERID"))){
				cityGS = list.get(0).get("USERID").toString();
			}
		}
		List<Map> cityCSJList = pnrTransferPrecheckService.getCityCSJByCountryCSJ(taskAssignee);
		String cityCSJ = "superUser";
		if(cityCSJList!=null &&cityCSJList.size()>0){
			if(cityCSJList.get(0).get("CITY_CSJ_ID")!=null && !"".equals(cityCSJList.get(0).get("CITY_CSJ_ID"))){
				cityCSJ = cityCSJList.get(0).get("CITY_CSJ_ID").toString();
			}
		}
		pnrTransferOffice.setCityCSJ(cityCSJ);
		pnrTransferOffice.setCityGS(cityGS);

		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);

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
}

