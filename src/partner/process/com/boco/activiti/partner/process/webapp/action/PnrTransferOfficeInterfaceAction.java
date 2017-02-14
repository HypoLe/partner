package com.boco.activiti.partner.process.webapp.action;

import java.io.InputStream;
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
import org.activiti.engine.ProcessEngine;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description: 故障处理工单ACTION
 */

public class PnrTransferOfficeInterfaceAction extends SheetAction {
	private final String processDefinitionKey = "myTransferInterfaceProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	// 自维人员审核通过
	private final static String AUDIT_STATE_THROUGH = "through";
	// 自维人员审核驳回
	private final static String AUDIT_STATE_DISMISS = "dismiss";
	// 归档状态
	private final static Integer ARCHIVE_STATE = 5;
	// 环节--“传输局”
	private final static String TASK_TRANSFERTASK = "transferTask";
	// 环节--“施工队”
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 工单删除状态
	private final static Integer TASK_DELETE_STATE = 1;
	//发信息用的常量
	private final static String TASK_NO_STR="工单号:";
	private final static String TASK_TITLE_STR="主题:";
	private final static String TASK_DEADLINE_STR="截止时间:";
	private final static String TASK_CONTACT_STR="联系人及电话:";

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

		PnrTransferOffice pnrTransferOffice;

		pnrTransferOffice = getTransferOfficeByRequest(request);

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
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveOrUpateAttachment(processInstanceId,
					attachments);
		}
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);

		// 发短信--start
		String taskNo = pnrTransferOffice.getProcessInstanceId();
		String title = pnrTransferOffice.getTheme();
		String endTime = pnrTransferOffice.getEndTime() == null ? "" : sFormat
				.format(pnrTransferOffice.getEndTime());
		String contact = pnrTransferOffice.getConnectPerson();
		String taskAssignee = pnrTransferOffice.getTaskAssignee();
		String msg=TASK_NO_STR+taskNo+";"+TASK_TITLE_STR+title+";"+TASK_DEADLINE_STR+endTime;
		msg+=TASK_CONTACT_STR+contact;
		
		CommonUtils.sendMsg(msg,taskAssignee);
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
		
		//查询条件
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferOfficeService.getToreplyJobOfPreflightPreparationJobCount(
					userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferOfficeService.getToreplyJobOfPreflightPreparationJobList(
					userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
					firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("module", "pnrTransferOffice");
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime",sendEndTime);
		request.setAttribute("wsNum",wsNum);
		request.setAttribute("wsTitle",wsTitle);
		request.setAttribute("wsStatus",status);
		return mapping.findForward("backlogList");
	}

	/**
	 * 故障回复
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
			// attachments-end

			if (task.getTaskDefinitionKey().equalsIgnoreCase("audit")) {// 自维审核
				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);
				// 显示附件
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("goTransferCheck");
			}if (task.getTaskDefinitionKey().equalsIgnoreCase("auditGk")) {
//				公客接口D类工单（派单——》处理——》审核）
				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handleSize);
				// 显示附件
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("goTransferCheck");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferTask")) {// 外包派发
				String findForward = "mainSecond";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("newTask")) {
				return mapping.findForward("new");
			} else if(task.getTaskDefinitionKey().equalsIgnoreCase("newTaskGk")){
//				公客接口D类工单（派单——》处理——》审核）
				return mapping.findForward("new");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferHandle")) {// 传输局处理
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
				return mapping.findForward("transferHandle");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferHandleGk")) {
//				公客接口D类工单（派单——》处理——》审核）
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
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferAudit")) {// 传输局质检
				request.setAttribute("PnrTransferHandleList", handlelist);
				request.setAttribute("listsize", handlelist.size());

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getOneInitiator());
				}
				showReplySetAttribute(request, handlelist);

				return mapping.findForward("secondAudit");
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

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("auditState"));
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
		if (auditState.equals(AUDIT_STATE_DISMISS)) {
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
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
						processInstanceId).listPage(firstResult * pageSize,
						pageSize);
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
		 System.out.println(date+"date=============");
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
				map.put(name, request.getParameter(name));
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
		
		  String msg =TASK_NO_STR+entity.getProcessInstanceId()+";"+TASK_TITLE_STR+entity.getTheme()+";";
		  msg += TASK_DEADLINE_STR+deadlineTime+";"+TASK_CONTACT_STR+entity.getConnectPerson();
		  CommonUtils.sendMsg(msg, taskAssignee);
		  
		return mapping.findForward("success");

	}

	/**
	 * 处理保存--处理环节
	 */
	public ActionForward transferHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//发短信
		String deadLineTime = "",contact="";
		
		
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
		entity.setHandleDescription(mainRemark);
		entity.setIsRecover(isRecover);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOfficeService.save(pnrTransferOffice);
			
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

		
		  String msg =TASK_NO_STR+processInstanceId+";"+TASK_TITLE_STR+title+";";
		  msg += TASK_DEADLINE_STR+deadLineTime+";"+TASK_CONTACT_STR+contact+";已处理请审核";
		  CommonUtils.sendMsg(msg, auditor);
		 
		return mapping.findForward("success");

	}

	/**
	 * 任务审核保存---传输局质检
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
				.getParameter("auditor"));

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
		if (auditState.equals("reject")) {
			if(pnrTicket!=null){
				
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
				
				auditor = pnrTicket.getTaskAssignee();// 回退到二次处理人
			}
			isSend = false;
			
		}else if(auditState.equals("pass")){
			if(pnrTicket!=null){
			processInstanceId = pnrTicket.getProcessInstanceId();
			deadlineTime = pnrTicket.getEndTime()==null?"":sFormat.format(pnrTicket.getEndTime());
			contact = pnrTicket.getConnectPerson();
			}
			//归档后,调用接口通知
			//Thread aThread = new Thread(new CurrentThead(sFormat.format(new Date()),CommonUtils.GK_TRANSFER_INTERFACE_OVER_METHOD,));
		     // aThread.start();
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
		if (auditState.equals("reject")) {// 回退时的说法
			map.put("auditor", auditor);
		}
		formService.submitTaskFormData(taskId, map);
		request.setAttribute("success", "check");

		// 发短信
		if(isSend){
			
			String msg =TASK_NO_STR+processInstanceId+";"+TASK_TITLE_STR+theme+";";
			msg += TASK_DEADLINE_STR+deadlineTime+";"+TASK_CONTACT_STR+contact+";已处理请审核";
			CommonUtils.sendMsg(msg, auditor);
		}
		 
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
					troubleTicketModel.setStatusName("已归档");
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
					
					troubleTicketModel.setSendTime(list.get(0).getSendTime());
					troubleTicketModel.setTheme(list.get(0).getTheme());
					troubleTicketModel.setState(list.get(0).getState());
					troubleTicketModel.setInitiator(list.get(0).getInitiator());
					troubleTicketModel.setOneInitiator(list.get(0)
							.getOneInitiator());
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

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();

		if (handle.equals(TASK_TRANSFERTASK)) {

			map.put("initiator", initiator);
			map.put("transferState", "rollback");

		} else if (handle.equals(TASK_TRANSFERHANDLE)) {

			map.put("taskAssignee", initiator);
			map.put("handleState", "rollback");
		}

		formService.submitTaskFormData(taskId, map);

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
				String themeInterface = list.get(0).getThemeInterface();
				//判断是否是传输局运维商城接口数据
				if(themeInterface!=null && "interface".equals(themeInterface)){
					transferModel.setSendTime(list.get(0).getSendTime());
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
					
					troubleTicketModel.setSendTime(list.get(0).getSendTime());
					troubleTicketModel.setTheme(list.get(0).getTheme());
					troubleTicketModel.setInitiator(list.get(0).getInitiator());
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
				troubleTicketModel.setSendTime(list.get(0).getSendTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
				troubleTicketModel.setInitiator(list.get(0).getInitiator());
				troubleTicketModel.setOneInitiator(list.get(0).getOneInitiator());
				troubleTicketModel.setTwoInitiator(list.get(0).getSecondInitiator());
				String message = pnrTransferOfficeService.getLoginPersonStatus(
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
	 * ----------------------------------------------------------------- 根据
	 * request得到传输局对象
	 * 
	 * @throws ParseException
	 */

	private PnrTransferOffice getTransferOfficeByRequest(
			HttpServletRequest request) throws ParseException {
		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 传输局接口
		String themeInterface = StaticMethod.nullObject2String(request
				.getParameter("themeInterface"));
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
		// 处理结束时间
		String dueDate = request.getParameter("dueDate");
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
		pnrTransferOffice.setTheme(title);
		pnrTransferOffice.setThemeInterface(themeInterface);
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

		pnrTransferOffice.setEndTime(sFormat.parse(dueDate));
		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);

		return pnrTransferOffice;
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
		// 区分跳转的页面
		String skipPage = request.getParameter("skipPage");
		String forward = "query";

		if ("transfer".equals(skipPage)) {
			forward = "workOrderQueryTransfer";
		}

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		String endTime = dateFormat.format(calendar.getTime());
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		request.setAttribute("startTime", dateFormat.format(date));
		request.setAttribute("endTime", endTime);
		String areaid = tawSystemDept.getAreaid();
		if (areaid != null && areaid.length() > 4) {
			areaid = areaid.substring(0, 4);
		}
		request.setAttribute("city", PartnerCityByUser
				.getCityByRootArea(areaid));
		request.setAttribute("isPartner", 1);
		String country = sessionForm.getDeptid();
		if (sessionForm.getUserid().equals("admin")) {
			country = "-1";
		}
		request.setAttribute("country", country);

		return mapping.findForward(forward);
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
					.workOrderQuery1("interface",deptId, workerid, beginTime, endTime,
							subType, theme, city, firstResult * pageSize,
							endResult * pageSize);
			int total = pnrTransferOfficeService.workOrderCount1("interface",deptId,
					workerid, beginTime, endTime, subType, theme, city);
			
			request.setAttribute("taskList", taskModels);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", (int) total);
		request.setAttribute("taskType", taskType);

		return mapping.findForward("workOrderQueryList");
	}
	 public static String addDate(String day, int x) 
	    { 
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
	         //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
	         Date date = null; 
	        try 
	        { 
	            date = format.parse(day); 
	        } 
	        catch (Exception ex)    
	        { 
	            ex.printStackTrace(); 
	        } 
	        if (date == null) return ""; 
	        Calendar cal = Calendar.getInstance(); 
	        cal.setTime(date); 
	        cal.add(Calendar.HOUR_OF_DAY, x);//24小时制 
	        //cal.add(Calendar.HOUR, x);12小时制 
	        date = cal.getTime(); 
	        System.out.println("front:" + date); 
	        cal = null; 
	        return format.format(date); 
	    } 
	    
}
