package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

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

import sun.misc.BASE64Decoder;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.AccreditOrder;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.model.PnrTroubleTicketHandle;
import com.boco.activiti.partner.process.model.WorkerState;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.service.IPnerAccreditOrderService;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.activiti.partner.process.service.IPnrWorkerStateService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.CurrentThead;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description: 故障处理工单ACTION
 */

public class PnrTroubleTicketAction extends SheetAction {
	String processDefinitionKey = "troubleTicketProcess";

	/**
	 * 故障处理保存
	 */
	public ActionForward goTodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean doFlag=false;
		String workOrderNo="";
		
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		String title = request.getParameter("title").trim();
		// 主题ID
		String titleId = request.getParameter("titleId").trim();
		// 回单人
		String userId = request.getParameter("userId").trim();
		// 审核人
		String auditor = request.getParameter("auditor").trim();

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = request.getParameter("mainRemark").trim();
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 交通方式
		String transport = request.getParameter("transport");
		// 里程
		String mileage = request.getParameter("mileage").trim();
		// 附件
		String attachments = request.getParameter("sheetAccessories");

		
		String taskId = request.getParameter("taskId").trim();
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		IPnrTroubleTicketHandleService pnrTroubleTicketHandleService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		PnrTroubleTicketHandle entity = new PnrTroubleTicketHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setIsRecover(isRecover);
		entity.setTransport(transport);
		entity.setMileage(Double.parseDouble(mileage));
		entity.setProcessInstanceId(processInstanceId);
		entity.setSheetAccessories(attachments);
		entity.setTaskId(taskId);
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTroubleTicket> pnrTroubleTicketList = pnrTroubleTicketService
				.search(search);
		if (pnrTroubleTicketList != null) {
			PnrTroubleTicket pnrTroubleTicket = pnrTroubleTicketList.get(0);
			pnrTroubleTicket.setLastReplyTime(new Date());
			pnrTroubleTicketService.save(pnrTroubleTicket);
			
			//公客系统派发的工单
			if(pnrTroubleTicket.getGkSerialNo()!=null){
				doFlag=true;
				workOrderNo= pnrTroubleTicket.getGkSerialNo();
			}
		}
		// 处理人关系表数据保存--start
		String[] personStrings = dealPerformer2.split(",");
		pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
				personStrings);
		// 处理人关系表数据保存--end
		pnrTroubleTicketHandleService.save(entity);
		//发短信		
		String mainResName="";
		if (pnrTroubleTicketList != null) {
			mainResName =  pnrTroubleTicketList.get(0).getFailedSite();
		}
		//流程提交到下一级
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
		//流程提交到下一级-end 
		
		String msg = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";已处理请审核";
		CommonUtils.sendMsg(msg, auditor);
		
	   //公客系统
		 if (doFlag)
		    {
		      Thread thead = new Thread(new CurrentThead(sFormat.format(new Date()), workOrderNo,CommonUtils.GK_STATUS_METHOD, "工单处理完毕,进入质检环节"));
		      thead.start();
		    }

		
		return mapping.findForward("success");

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
		if(country.length()>5){
			country=country.substring(0,5);
		}
		if("admin".equals(userId)||"superUser".equals(userId)){//超级管理员
			country="";
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
			IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();

			// 回复message
			IPnrTroubleTicketHandleService troubleHandleService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
			search.addSort("receiveTime", true);//按回复时间排序

			List<PnrTroubleTicketHandle> handlelist = troubleHandleService
					.searchAndCount(search).getResult();
			String specialty="";
			if(null !=list.get(0))
			{
				specialty = CommonUtils.getId2NameString(list.get(0).getSpecialty(), 1, ",");
			}
			request.setAttribute("pnrTroubleTicket", list.get(0));
			
			request.setAttribute("specialty", specialty);
			//attachments 
			PnrTroubleTicket ticket = new PnrTroubleTicket();
			String sheetAccessories = pnrTroubleTicketService.getAttachmentNamesByProcessInstanceId(processInstanceId);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain",ticket);
			//attachments-end
			
			if (task.getTaskDefinitionKey().equalsIgnoreCase("audit")) {
				request.setAttribute("PnrTroubleHandleList", handlelist);
				request.setAttribute("listsize", handlelist.size());
				//显示附件
				showReplySetAttribute(request,handlelist);
				
				return mapping.findForward("goTroubleCheck");
			}
			
			if (task.getTaskDefinitionKey().equalsIgnoreCase("troubleShooting")) {
				String findForward = "troubleShooting";
				if(null !=list.get(0))
				{
					int state = list.get(0).getState();
					String auditor="";
					if(state==6){
						findForward= "mainSecond";
					}else if(state==0){
						auditor=list.get(0).getInitiator();
						request.setAttribute("auditor",auditor);
					}
				
				}
				

				return mapping.findForward(findForward);
			}
			if (task.getTaskDefinitionKey().equalsIgnoreCase("newDistribution")) {
				return mapping.findForward("new");
			}
			
			//处理阶段-代维处理
			if (task.getTaskDefinitionKey().equalsIgnoreCase("twoHandle")) {
				if(list!=null){
					//回退原因，需要补录上
					String id = list.get(0).getId();
					Search search2 = new Search();
					search2.addFilterEqual("themeId", id);
					search2.addFilterEqual("state", "rollback");
					search2.addSort("checkTime", true);
					
					List<PnrTroubleTicketHandle> Handlelist2 = troubleHandleService
					.searchAndCount(search2).getResult();
					
					if(Handlelist2!=null&&Handlelist2.size()>0){
						request.setAttribute("reason",Handlelist2.get(0).getOpinion());
						
					}
					
					request.setAttribute("auditor",list.get(0).getInitiator());
				}
				return mapping.findForward("twoHandleDoing");
			}
			if (task.getTaskDefinitionKey().equalsIgnoreCase("twoAudit")) {//代维审核
				request.setAttribute("PnrTroubleHandleList", handlelist);
				request.setAttribute("listsize", handlelist.size());
				
				if(list.size()>0){					
					request.setAttribute("auditor",list.get(0).getOneInitiator());
				}
				showReplySetAttribute(request, handlelist);
				
				return mapping.findForward("secondAudit");
			}
		}

		return mapping.findForward("error");

	}

	/**
	 * 详细信息呈现
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String processId = request.getParameter("processInstanceId");

		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processId);
		SearchResult t = pnrTroubleTicketService.searchAndCount(search);
		List<PnrTroubleTicket> list = t.getResult();
		// 回复message
		IPnrTroubleTicketHandleService troubleHandleService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		search.addSort("receiveTime", true);//按回复时间排序

		List<PnrTroubleTicketHandle> handlelist = troubleHandleService
				.searchAndCount(search).getResult();
		// 涉及专业-start
		String specialty = "";
		if (list.get(0) != null) {
			specialty = CommonUtils.getId2NameString(list.get(0).getSpecialty(), 1, ",");
		}
		// 涉及专业-end
		//attachments 
		PnrTroubleTicket ticket = new PnrTroubleTicket();
		String sheetAccessories = pnrTroubleTicketService.getAttachmentNamesByProcessInstanceId(processId);
		ticket.setSheetAccessories(sheetAccessories);
		request.setAttribute("sheetMain",ticket);
		//attachments-end
		showReplySetAttribute(request,handlelist);

		request.setAttribute("specialty", specialty);
		request.setAttribute("pnrTroubleTicket", list.get(0));
		request.setAttribute("PnrTroubleHandleList", handlelist);
		request.setAttribute("listsize", handlelist.size());
		request.setAttribute("processInstanceId", processId);

		return mapping.findForward("gotoDetail");

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
		// 流程开始
		ProcessEngine processEngine = (ProcessEngine) getBean("processEngine");
		TaskService taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(userId)
				// .processDefinitionKey(processDefinitionKey).taskDefinitionKey(definitionKey).active()
				.processDefinitionKey(processDefinitionKey).active().orderByTaskCreateTime().desc().listPage(
						firstResult * pageSize, endResult * pageSize);
		long total = taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey(processDefinitionKey).active().count();
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		HistoryService historyService = (HistoryService) getBean("historyService");
		for (Task task : taskList) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
//			search.
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				PnrTroubleTicket pnrTroubleTicket = list.get(0);
				TaskModel troubleTicketModel = new TaskModel();
				troubleTicketModel.setTaskId(task.getId());
				troubleTicketModel
						.setInitiator(pnrTroubleTicket.getInitiator());
				troubleTicketModel.setOneInitiator(pnrTroubleTicket.getOneInitiator());
				troubleTicketModel.setProcessInstanceId(pnrTroubleTicket
						.getProcessInstanceId());
				troubleTicketModel.setSendTime(pnrTroubleTicket.getSendTime());
				troubleTicketModel.setTheme(pnrTroubleTicket.getTheme());
				// 所处环节
				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								task.getProcessInstanceId()).taskId(task.getId()).orderByTaskId()
						.desc().listPage(0,1);
				if (historicTasks != null & historicTasks.size() > 0) {
					troubleTicketModel.setStatusName(historicTasks.get(0)
							.getName());
					troubleTicketModel.setTaskDefKey(historicTasks.get(0).getTaskDefinitionKey());
				}
			
				TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(pnrTroubleTicket.getTaskAssignee(),"");
				if(pu!=null){
					troubleTicketModel.setDeptId(pu.getDeptid());
					
				}
				troubleTicketModel.setState(pnrTroubleTicket.getState());
				rPnrTroubleTicketList.add(troubleTicketModel);
			}
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("backlogList");
	}

	/**
	 * 根据类型，显示快要到期的任务列表
	 */
	public ActionForward listDueBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		String definitionKey = request.getParameter("definitionKey");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		// 流程开始
		ProcessEngine processEngine = (ProcessEngine) getBean("processEngine");
		TaskService taskService = processEngine.getTaskService();
		List<Task> taskList = taskService.createTaskQuery()
				.processVariableValueEquals("initiator", userId)
				.processDefinitionKey(processDefinitionKey).taskDefinitionKey(
						definitionKey).dueBefore(calendar.getTime()).active().orderByTaskCreateTime().desc()
				.listPage(firstResult * pageSize, endResult * pageSize);
		long total = taskService.createTaskQuery().processVariableValueEquals(
				"initiator", userId).processDefinitionKey(processDefinitionKey)
				.taskDefinitionKey(definitionKey).dueBefore(calendar.getTime())
				.active().count();
		
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		HistoryService historyService = (HistoryService) getBean("historyService");
		
		for (Task task : taskList) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				PnrTroubleTicket pnrTroubleTicket = list.get(0);
				TaskModel troubleTicketModel = new TaskModel();
				troubleTicketModel.setTaskId(task.getId());
				troubleTicketModel
						.setInitiator(pnrTroubleTicket.getTaskAssignee());
				troubleTicketModel.setProcessInstanceId(pnrTroubleTicket
						.getProcessInstanceId());
				troubleTicketModel.setEndTime(pnrTroubleTicket.getEndTime());
				troubleTicketModel.setTheme(pnrTroubleTicket.getTheme());
				String  hours ="已超时";
				if(pnrTroubleTicket.getEndTime().after(new Date())){
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try
					{
					    Date d1 = pnrTroubleTicket.getEndTime();
					    Date d2 = new Date();
					    long diff = d1.getTime() - d2.getTime();
					  
					    long day=diff/(24*60*60*1000);
					    long hour=(diff/(60*60*1000)-day*24);
					    long min=((diff/(60*1000))-day*24*60-hour*60);
					    long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
//					    System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
					    hours =""+day+"天"+hour+"小时"+min+"分"+s+"秒";
					}
					catch (Exception e)
					{
					}

				}
			    troubleTicketModel.setTimeDifference(hours);

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								task.getProcessInstanceId()).orderByTaskId()
						.desc().listPage(0, 1);
				if (historicTasks != null & historicTasks.size() > 0) {
					troubleTicketModel.setStatusName(historicTasks.get(0)
							.getName());
				}
				TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(pnrTroubleTicket.getTaskAssignee(),"");
				if(pu!=null){
					troubleTicketModel.setDeptId(pu.getDeptid());
					
				}
				rPnrTroubleTicketList.add(troubleTicketModel);
			}
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("duelogList");
	}
	/**
	 * 新增工单初始化
	 */

	public ActionForward showNewSheetPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		request.setAttribute("city", PartnerCityByUser
				.getCityByProvince(province));
		CommonUtils.getCompetenceLimit(request);
		return mapping.findForward("new");
		// return super.showNewSheetPage(mapping, form, request, response);
		
	}

	/**
	 * performAdd 保存故障工单
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");		
		String deptId = sessionForm.getDeptid();
		
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 主题
		String title = request.getParameter("title").trim();
		// 派单人
		String initiator = request.getParameter("initiator").trim();
		// 派单时间
		Date createTime = new Date();
		// 处理时限
		String sheetCompleteLimit = request.getParameter("sheetCompleteLimit").trim();
		// 故障站点
		String mainResName = request.getParameter("mainResName").trim();
		// 故障站点ID
		String mainResId = request.getParameter("mainResId").trim();
		// 故障来源
		String faultSource = request.getParameter("faultSource").trim();
		// 故障发生时间
		String mainFaultOccurTime = request.getParameter("mainFaultOccurTime");
		// 涉及专业
		String[] specialty = request.getParameterValues("mainSpecialty");
		String mainSpecialty = "";
		for (int i = 0; i < specialty.length; i++) {
			if (i == (specialty.length - 1)) {
				mainSpecialty += specialty[i];
			} else {
				mainSpecialty += specialty[i] + ",";
			}
		}
		// 是否集团客户
		String mainIsGroupBusi = request.getParameter("mainIsGroupBusi");
		// 工单子类型
		String mainFaultNet = request.getParameter("mainFaultNet");
		// 故障联系人
		String mainpeople =StaticMethod.nullObject2String(request.getParameter("mainpeople"));
		// 故障描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 接收人
		String taskAssignee = request.getParameter("taskAssignee");
		
		// 处理人
		// String dealPerformer2 = request.getParameter("dealPerformer2");
		String dueDate = request.getParameter("dueDate");
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");
		// 处理人字符串
		// String doPersonJSON = request.getParameter("sendObject1TotalJSON");
		// firstCreateTime
		String firstCreateTime = request.getParameter("firstCreateTime");
		// firstInitiator
		String firstInitiator = request.getParameter("firstInitiator");
		//查看是否是二次调度
		TawSystemUser tawSystemUser = CommonUtils.getTawSystemUserByUserId(taskAssignee, "");
		String tawDeptId = tawSystemUser.getDeptid();
		String deptIdSub = deptId.substring(0, 3);//派发人部门-截取前3个字符
		String tawDeptIdSub = tawDeptId.substring(0, 3);//接收人部门-截取前3个字符
		int state =0;
		boolean sendFlag = false;
		boolean reciveFlag = false;
	    //首先派发人是101或201打头的部门
		if(deptIdSub.equals("201")||deptIdSub.equals("101")){
			sendFlag=true;//是自维
		}
        //接收是代维还是自维
		if(tawDeptIdSub.equals("201")||tawDeptIdSub.equals("101")){
			reciveFlag = true; //是自维
		}
		
		if(sendFlag && !reciveFlag){//需要二次调度
			state=6;
		}
		
		//新增附件
		String attachments = request.getParameter("sheetAccessories");
		
		int attachmentsNum = 0;
		
		if(attachments!=null&&attachments.length()>0){
			attachmentsNum=	attachments.split(",").length;
		}
		
		// 流程开始
		FormService formService = (FormService) getBean("formService");
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		TaskService taskService = (TaskService) getBean("taskService");
		String processInstanceId = request.getParameter("processInstanceId");
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
				if(!"taskAssignee".equals(name)){
					
					map.put(name, request.getParameter(name));
				}
			}
		}
		
		IPnrWorkerStateService pnrWorkerStateService = (IPnrWorkerStateService)getBean("pnrWorkerStateService");
		//根据接收人查看是否有授权人
		WorkerState workerState = pnrWorkerStateService.getWorkerId(taskAssignee, "101011102");
		//不为空，表示接收人正处于请假状态，所有派发给他的工单将授权给指定的同组人
		if(workerState != null){
			taskAssignee = workerState.getAccredit();
			IPnerAccreditOrderService pnrAccreditOrderService = (IPnerAccreditOrderService)getBean("pnrAccreditOrderService");
			
			AccreditOrder accreditOrder = new AccreditOrder();
			accreditOrder.setAccreditId(workerState.getId());
			accreditOrder.setOrderId(processInstanceId);
			//定义时间格式
			String  nowTime = sFormat.format(new Date());
			accreditOrder.setPayoutTime(sFormat.parse(nowTime));
			accreditOrder.setZhuTi(title);
			pnrAccreditOrderService.save(accreditOrder);
		}
		map.put("taskAssignee",taskAssignee);
		
		formService.submitTaskFormData(taskId, map);
		// 流程结束
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");

		PnrTroubleTicket entity = new PnrTroubleTicket();
		if (themeId != null && themeId.length()>0) {
			entity = pnrTroubleTicketService.find(themeId);//如果存在就取出数据实体
		}
		entity.setTheme(title);
		entity.setCreateTime(sFormat.parse(mainFaultOccurTime));
		entity.setSendTime(sFormat.parse(sFormat.format(createTime)));

		entity.setInitiator(initiator);
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		if(state==6){
		entity.setOneInitiator(initiator);
		entity.setInitiator(taskAssignee);
		}
		// 处理人
		// entity.setDoPerson(dealPerformer2);
		entity.setConnectPerson(mainpeople);
		entity.setProcessLimit(Double.parseDouble(sheetCompleteLimit));
		entity.setFailedSite(mainResName);
		entity.setMainResId(mainResId);
		entity.setFaultSource(faultSource);
		entity.setFaultDescription(mainRemark);
		entity.setIsCustomers(Integer.parseInt(mainIsGroupBusi));
		entity.setSubType(mainFaultNet);
		entity.setSpecialty(mainSpecialty);
		//工单与专业关系 --start
		pnrTroubleTicketService.saveOrUpateSpecialty(processInstanceId, specialty);
		//工单与专业关系 --end
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		// entity.setDoPersonJSON(doPersonJSON);
		entity.setProcessInstanceId(processInstanceId);
		entity.setState(state);
		entity.setEndTime(sFormat.parse(dueDate));
		//附件个数
		entity.setAttachmentsNum(attachmentsNum);
		
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		PnrResConfig pnrResConfig = pnrResConfigMgr.find(mainResId);
		if(pnrResConfig!=null){
			entity.setCity(pnrResConfig.getCity());
			entity.setCountry(pnrResConfig.getCountry());
		}
		//巡检隐患
		if(firstCreateTime.length()>0&&firstCreateTime!=null){
			entity.setFirstCreateTime(sFormat.parse(firstCreateTime));
			entity.setFirstInitiator(firstInitiator);
		}
		//attachment--start
		if(!"".equals(attachments)){			
			pnrTroubleTicketService.saveOrUpateAttachment(processInstanceId,attachments);
		}
		//attachment--end
		pnrTroubleTicketService.save(entity);
		//发短信
		
		String msg = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";处理时间截止到:"+dueDate;
		CommonUtils.sendMsg(msg, taskAssignee);
		return mapping.findForward("success");

	}
	

	/**
	 * 按照流程实例ID删除流程
	 */
	public ActionForward removeHistoricProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		HistoryService historyService = (HistoryService) getBean("historyService");
		historyService.deleteHistoricProcessInstance(processInstanceId);
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
		
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		
		Search search  = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTroubleTicket> tResult =pnrTroubleTicketService.searchAndCount(search);
		List<PnrTroubleTicket>  eList= tResult.getResult();
		PnrTroubleTicket  entity =eList.get(0);
		
		entity.setState(1);
		pnrTroubleTicketService.save(entity);
		
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
						processDefinitionKey).startedBy(userId).orderByProcessInstanceStartTime().desc().listPage(
						firstResult * pageSize, endResult * pageSize);
		long total = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey).startedBy(userId)
				.count();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
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
								processInstanceId).orderByTaskId().desc().listPage(0, 1);
				if (historicTasks != null & historicTasks.size() > 0) {
					troubleTicketModel.setStatusName(historicTasks.get(0)
							.getName());
					troubleTicketModel.setTaskDefKey(historicTasks.get(0).getTaskDefinitionKey());
					troubleTicketModel.setTaskId(historicTasks.get(0).getId());
				}
			}
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTroubleTicket> list = pnrTroubleTicketService
					.search(search);
			if (list != null && list.size() != 0) {
				troubleTicketModel.setSendTime(list.get(0).getSendTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
				troubleTicketModel.setState(list.get(0).getState());
				troubleTicketModel.setInitiator(list.get(0).getInitiator());
				troubleTicketModel.setOneInitiator(list.get(0).getOneInitiator());
			}
			rPnrTroubleTicketList.add(troubleTicketModel);
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("runningProcessInstancesList");
	}

	/**
	 * 用户所属部门下的所有工单
	 * 
	 * @return
	 */
	public ActionForward listDeptProcessInstances(ActionMapping mapping,
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
		String involvedUser = sessionForm.getUserid();
		ITawSystemUserManager tawSystemUserManager = (ITawSystemUserManager) getBean("itawSystemUserManager");
		List<String> userIdList = tawSystemUserManager
				.getUserIdsBydeptid(sessionForm.getDeptid());
		for (String s : userIdList) {
			if (involvedUser == null) {
				involvedUser = s;
			} else {
				involvedUser = involvedUser + "','" + s;
			}
		}
		List<HistoricProcessInstance> historicProcessInstancesList = historyService
				.createNativeHistoricProcessInstanceQuery()
				.sql(
						"SELECT RES.* FROM ACT_HI_PROCINST RES where "
								+ "(exists(select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ in ('"
								+ involvedUser
								+ "') and LINK.PROC_INST_ID_ = RES.ID_))  and RES.PROC_DEF_ID_ like #{processDefinitionIdLike} and RES.END_TIME_ IS NULL")
				.parameter("processDefinitionIdLike",
						processDefinitionKey + "%").listPage(
						firstResult * pageSize, endResult * pageSize);
		long total = historyService
				.createNativeHistoricProcessInstanceQuery()
				.sql(
						"SELECT count(*) FROM ACT_HI_PROCINST RES where "
								+ "(exists(select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ in ('"
								+ involvedUser
								+ "') and LINK.PROC_INST_ID_ = RES.ID_))  and RES.PROC_DEF_ID_ like #{processDefinitionIdLike} and RES.END_TIME_ IS NULL")
				.parameter("processDefinitionIdLike",
						processDefinitionKey + "%").count();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
			String processInstanceId = historicProcessInstance.getId();
			TaskModel troubleTicketModel = new TaskModel();
			troubleTicketModel.setProcessInstanceId(processInstanceId);
			if (historicProcessInstance.getEndTime() != null) {
				troubleTicketModel.setStatusName("已归档");
			} else {
				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								processInstanceId).orderByTaskId().desc().listPage(0, 1);
				if (historicTasks != null & historicTasks.size() > 0) {
					troubleTicketModel.setStatusName(historicTasks.get(0)
							.getName());
				}
			}
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTroubleTicket> list = pnrTroubleTicketService
					.search(search);
			if (list != null && list.size() != 0) {
				troubleTicketModel.setSendTime(list.get(0).getSendTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
			}
			rPnrTroubleTicketList.add(troubleTicketModel);
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("runningProcessInstancesList");
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
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
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
							processInstanceId).orderByTaskId().desc().list();
			if (historicTasks != null & historicTasks.size() > 0) {
				if(historicTasks.get(0).getDeleteReason().equals("delete")){
					troubleTicketModel.setStatusName(CommonUtils.taskDetele);
            		
            	}else{
            		
            		troubleTicketModel.setStatusName(CommonUtils.taskComplete);
            	}
			}
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				troubleTicketModel.setSendTime(list.get(0).getSendTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
				troubleTicketModel.setInitiator(list.get(0).getInitiator());
				TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(list.get(0).getTaskAssignee(),"");
				if(pu!=null){
					troubleTicketModel.setDeptId(pu.getDeptid());
					
				}
			}

			rPnrTroubleTicketList.add(troubleTicketModel);
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
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
		List<HistoricProcessInstance> historicProcessInstancesList = historyService
				.createHistoricProcessInstanceQuery().processDefinitionKey(
						processDefinitionKey).involvedUser(userId).unfinished()
				.listPage(firstResult * pageSize, endResult * pageSize);
		long total = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(processDefinitionKey)
				.involvedUser(userId).unfinished().count();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
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
							processInstanceId).orderByTaskId().desc().listPage(0, 1);
			if (historicTasks != null & historicTasks.size() > 0) {
				troubleTicketModel
						.setStatusName(historicTasks.get(0).getName());
			}
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				troubleTicketModel.setSendTime(list.get(0).getSendTime());
				troubleTicketModel.setTheme(list.get(0).getTheme());
				troubleTicketModel.setInitiator(list.get(0).getInitiator());
				TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(list.get(0).getTaskAssignee(),"");
				if(pu!=null){
					troubleTicketModel.setDeptId(pu.getDeptid());
					
				}
			}

			rPnrTroubleTicketList.add(troubleTicketModel);
		}
		request.setAttribute("taskList", rPnrTroubleTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", (int) total);
		return mapping.findForward("involvedProcessInstancesList");
	}

	public ActionForward workOrderStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String subType = request.getParameter("subType");
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTaskTicketService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrTroubleTicketService
				.workOrderStatistics(beginTime, endTime, subType);
		return mapping.findForward("involvedProcessInstancesList");
	}

	public ActionForward workOrderQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		String city = request.getParameter("mainCity");
		String theme = request.getParameter("title").trim();
        String workerid =request.getParameter("workerid");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String subType = request.getParameter("mainFaultNet");
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
	
		return mapping.findForward("troubleQueryList");
	}

	/**
	 * 显示工单查询页面
	 */
	public ActionForward showQueryPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		String endTime = dateFormat.format(calendar.getTime());

		request.setAttribute("startTime", dateFormat.format(date));
		request.setAttribute("endTime", endTime);
		request.setAttribute("city", PartnerCityByUser
				.getCityByProvince(province));
        request.setAttribute("isPartner", 1);

		return mapping.findForward("query");
	}

	
	/**
	 * 任务审核保存---代维审核
	 */
	public ActionForward secondAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");
		
		// 审核时间
		Date createTime = new Date();
		
		// 审核描述
		String mainRemark = request.getParameter("mainRemark").trim();
		
		// 审核意见
		String auditState = request.getParameter("twoAuditState").trim();
		// 审核人
		String auditor = request.getParameter("auditor").trim();
		
		String taskId = request.getParameter("taskId");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
		.list();
		
		IPnrTroubleTicketHandleService pnrService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		PnrTroubleTicketHandle entity = new PnrTroubleTicketHandle();
		
		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(userId);
		pnrService.save(entity);
//		用于更新时间字段或发短信用
		IPnrTroubleTicketService iPnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", taskList.get(0).getProcessInstanceId());
		List<PnrTroubleTicket> pnrTicketList = iPnrTroubleTicketService
		.search(search);
		if (auditState != null && auditState.equals("rollback")) {
			if (pnrTicketList != null) {
//				设置回复时间问题
				PnrTroubleTicket pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);				
				iPnrTroubleTicketService.save(pnrTicket);
				
				auditor=pnrTicket.getTaskAssignee();//回退到二次处理人
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
		if(auditState.equals("rollback")){//回退时的说法
			map.put("auditor",auditor);
		}
		formService.submitTaskFormData(taskId, map);
		request.setAttribute("success", "check");
	
//		发短信
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mainResName="";
		String dueDate="";
		String flagMsg=";已处理请审核";
		if(pnrTicketList != null){
			mainResName=pnrTicketList.get(0).getFailedSite();
			dueDate =dateFormat.format(pnrTicketList.get(0).getEndTime());
		}
		if(auditState.equals("rollback")){
			flagMsg=";处理时间截止到:"+dueDate;
		}
		String msg = "故障工单号:"+taskList.get(0).getProcessInstanceId()+";主题:"+theme+";站点:"+mainResName+flagMsg;
		CommonUtils.sendMsg(msg, auditor);
		return mapping.findForward("success");
	}
	/**
	 * 任务审核保存
	 */
	public ActionForward goTroubleCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//公客系统
		boolean doFlag=false;
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String workOrderNo="";
		
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = request.getParameter("mainRemark").trim();

		// 审核意见
		String auditState = request.getParameter("auditState").trim();
		// 审核人
		String auditor = request.getParameter("auditor").trim();
		

		String taskId = request.getParameter("taskId");

		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
		.list();
		IPnrTroubleTicketHandleService pnrService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		PnrTroubleTicketHandle entity = new PnrTroubleTicketHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(auditor);
		pnrService.save(entity);

		IPnrTroubleTicketService iPnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", taskList.get(0).getProcessInstanceId());
		List<PnrTroubleTicket> pnrTicketList = iPnrTroubleTicketService
		.search(search);
		if (auditState != null && auditState.equals("auditDismissed")) {
			if (pnrTicketList != null) {
				PnrTroubleTicket pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);
				iPnrTroubleTicketService.save(pnrTicket);
			}
			
		}else{
			//公客系统
			if (pnrTicketList != null) {
				PnrTroubleTicket pnrTicket = pnrTicketList.get(0);
				if(pnrTicket.getGkSerialNo()!=null){
					doFlag=true;					
					workOrderNo=pnrTicket.getGkSerialNo();
				}
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
		request.setAttribute("success", "check");
		
		//公客系统
		if (doFlag)
	    {
	      Thread aThread = new Thread(new CurrentThead(sFormat.format(new Date()), workOrderNo,CommonUtils.GK_RECEIPT_METHOD, "工单归档"));
	      aThread.start();
	    }

		
		return mapping.findForward("success");
	}
	 /**
	 * 回退任务
	 * 
	 * @return
	 */
	public ActionForward rollback(ActionMapping mapping,
    ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
		String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		
		String taskId = request.getParameter("taskId");
		String sort = request.getParameter("sort");
			
		/* sort 1:（当前处于故障处理）回退到派发工单；
				2：(当前处于代维故障）走二次派发,退回到第二调度人；
			
		 */
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		
		if(sort.equals("1")){
			
			map.put("initiator", initiator);
			map.put("handleState", handle);
			
		}else if(sort.equals("2")){
			
//			map.put("taskAssignee", initiator);
			map.put("towHandleState", handle);
		}		 
		formService.submitTaskFormData(taskId, map);
	        
	    return mapping.findForward("success");
	}
	
	/**
	 * 催单
	 */
	public ActionForward reminders(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//工单号
		String processInstanceId = request.getParameter("processInstanceId");
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTroubleTicket> searchList =pnrTroubleTicketService.searchAndCount(search);
		List<PnrTroubleTicket> pnrList= searchList.getResult();
		String taskAssignee= "";
		String title = "";
		String mainResName = "";
		String dueDate="";
		String flagMsg ="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(pnrList.size()>0){
			taskAssignee=pnrList.get(0).getTaskAssignee();
			title = pnrList.get(0).getTheme();
			mainResName = pnrList.get(0).getFailedSite();
			dueDate = dateFormat.format(pnrList.get(0).getEndTime());
			//规定的截止时间在当前时间之后（及超时未完成）
			if(pnrList.get(0).getEndTime().before(new Date())){
				flagMsg="(已超时)";
			}
			
		}	
//		发短信
		String msg = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";处理时间截止到:"+dueDate+flagMsg;
		CommonUtils.sendMsg(msg, taskAssignee);
		request.setAttribute("success", "duelog");

		return mapping.findForward("success");
	}
	
	/**
	 * 故障处理保存--代维处理环节
	 */
	public ActionForward twoHandleDoing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		String title = request.getParameter("title").trim();
		// 主题ID
		String titleId = request.getParameter("titleId").trim();
		// 回单人
		String userId = request.getParameter("userId").trim();
		// 审核人
		String auditor = request.getParameter("taskAssignee").trim();
		
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = request.getParameter("mainRemark").trim();
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 交通方式
		String transport = request.getParameter("transport");
		// 里程
		String mileage = request.getParameter("mileage").trim();
		String taskId = request.getParameter("taskId").trim();
		
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		IPnrTroubleTicketHandleService pnrTroubleTicketHandleService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		PnrTroubleTicketHandle entity = new PnrTroubleTicketHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setIsRecover(isRecover);
		entity.setTransport(transport);
		entity.setMileage(Double.parseDouble(mileage));
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTroubleTicket> pnrTroubleTicketList = pnrTroubleTicketService
		.search(search);
		if (pnrTroubleTicketList != null) {
			PnrTroubleTicket pnrTroubleTicket = pnrTroubleTicketList.get(0);
			pnrTroubleTicket.setLastReplyTime(new Date());
			pnrTroubleTicketService.save(pnrTroubleTicket);
		}
		// 处理人关系表数据保存--start
		String[] personStrings = dealPerformer2.split(",");
		pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
				personStrings);
		// 处理人关系表数据保存--end
		pnrTroubleTicketHandleService.save(entity);
		//发短信		
		String mainResName="";
		if (pnrTroubleTicketList != null) {
			mainResName =  pnrTroubleTicketList.get(0).getFailedSite();
		}
		//流程提交到下一级
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
		//流程提交到下一级-end 
		
		String msg = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";已处理请审核";
		CommonUtils.sendMsg(msg, auditor);
		return mapping.findForward("success");
		
	}
	/**
	 * mainSecond -代维派发
	 */
	public ActionForward mainSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");		
		String deptId = sessionForm.getDeptid();
		
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 派单时间
		Date createTime = new Date();		
		// 接收人
		String taskAssignee = request.getParameter("auditor");
	
		String dueDate = request.getParameter("dueDate");
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");
	
		// 工单ID
		String themeId = request.getParameter("themeId");
		//taskId
		String taskId = request.getParameter("taskId");
		
		// 流程开始
		FormService formService = (FormService) getBean("formService");
		
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();				
				map.put(name, request.getParameter(name))	;
			}
		}
		formService.submitTaskFormData(taskId, map);
		// 流程结束
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		
		PnrTroubleTicket entity = new PnrTroubleTicket();
		if (themeId != null && themeId.length()>0) {
			entity = pnrTroubleTicketService.find(themeId);
		}
		entity.setSecondSendTime(sFormat.parse(sFormat.format(createTime)));
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		entity.setEndTime(sFormat.parse(dueDate));
		pnrTroubleTicketService.save(entity);
		
//		发短信
		
		String msg = "故障工单号:"+entity.getProcessInstanceId()+";主题:"+entity.getTheme()+";站点:"+entity.getFailedSite()+";处理时间截止到:"+dueDate;
		CommonUtils.sendMsg(msg, taskAssignee);
		return mapping.findForward("success");
		
	}
	/**
	 * 显示回复的附件！
	 * @param request
	 * @param handlist
	 */
	private void showReplySetAttribute(HttpServletRequest request,List<PnrTroubleTicketHandle> handlist){
		int handSize=handlist.size();
		if(handSize>0){
			for(int i=0;i<handSize;i++){						
				request.setAttribute("sheetReply"+i,handlist.get(i));
			}
		}
	}
	/**
	 * 故障工单照片列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
 * 故障工单照片查看
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
  public ActionForward testShowPicture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String pageIndexString = request.getParameter("curPage");
    getClass().getClassLoader().getResource("images/login.png");
//    System.out.println("--测试----" + pageIndexString);
    int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

    PnrAndroidWorkOderPhotoFile taskFile = null;
    IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService)getBean("pnrAndroidWorkOderPhotoFileService");
    Search search = new Search();
    search.addFilterEqual("id", id);
    List list = pnrService.search(search);
    if ((list != null) && (list.size() > 0))
      taskFile = (PnrAndroidWorkOderPhotoFile)list.get(0);

    Clob clob = taskFile.getFileData();

    if (clob != null)
    {
      File tempFile = File.createTempFile("tmp", ".png");
//      System.out.println("--tempFilePath-" + tempFile.getPath());
      try {
        int len;
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("image/jpeg");

        char[] buf = new char[255];
        Reader reader = clob.getCharacterStream();
        FileOutputStream fos = new FileOutputStream(tempFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        while ((len = reader.read(buf)) > 0) {
          bw.write(buf, 0, len);
          bw.flush();
        }
        bw.close();
        FileInputStream fis = new FileInputStream(tempFile);
        BASE64Decoder base64 = new BASE64Decoder();
        base64.decodeBuffer(fis, response.getOutputStream());
      }
      finally
      {
        tempFile.deleteOnExit();
      }
    }

    return null;
  }
  /**
   * 故障工单照片查看
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward showPicture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  throws Exception
  {
	  String id = request.getParameter("id");
	    String pageIndexString = request.getParameter("curPage");
	    getClass().getClassLoader().getResource("images/login.png");
//	    System.out.println("--测试----" + pageIndexString);
	    int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

	    PnrAndroidWorkOderPhotoFile taskFile = null;
	    IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService)getBean("pnrAndroidWorkOderPhotoFileService");
	    Search search = new Search();
	    search.addFilterEqual("id", id);
	    List list = pnrService.search(search);
	    if ((list != null) && (list.size() > 0))
	      taskFile = (PnrAndroidWorkOderPhotoFile)list.get(0);

	    String path = taskFile.getPath();
	    String patch = path;  
	    
	   /* //获取盘符
	    String strClassName = getClass().getName();
	    String strPackageName="";
	    if(getClass().getPackage()!=null){
	    	strPackageName = getClass().getPackage().getName();
	    }
	    String strFileName = "";
	    if(!"".equals(strPackageName)){
	    	strFileName = strClassName.substring(strPackageName.length()+1,strClassName.length());
	    }else{
	    	strFileName = strClassName;
	    }
	    URL url =null ;
	    url = getClass().getResource(strFileName+".class");
	    String strUrl = url.toString();
	    strUrl = strUrl.substring(strUrl.indexOf('/')+1,strUrl.lastIndexOf('W'));
	    
	    String flag = File.separator;
	    if("\\".equals(flag)){//windows环境下的分隔符是\\
	    	patch = strUrl.substring(0,2)+patch;
	    }else if("/".equals(flag)){//Linux环境下的分隔符是/
	    	patch = patch;
	    }
//	    System.out.println(patch);
	    //肉哥思路
	    FileInputStream is = new FileInputStream(patch);  
		int i = is.available(); // 得到文件大小  
		byte data[] = new byte[i];  
		is.read(data); // 读数据  
		is.close();  
		response.setContentType("image/*"); // 设置返回的文件类型  
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
		toClient.write(data); // 输出数据  
		toClient.close(); */
	    
	    InputStream in = null ;
        ByteArrayOutputStream out = null ;
        try {
            //创建远程文件对象
            //String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL+patch;
        	String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl()+patch;
            SmbFile remoteFile = new SmbFile(remotePhotoUrl);
            remoteFile.connect(); //尝试连接
            //创建文件流
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new ByteArrayOutputStream((int)remoteFile.length());
            //读取文件内容
            byte[] buffer = new byte[4096];
            int len = 0; //读取长度
            while ((len = in.read(buffer, 0, buffer.length)) != - 1) {
                out.write(buffer, 0, len);
            }

            out.flush(); //刷新缓冲的输出流
           
            response.setContentType("image/*"); // 设置返回的文件类型  
    		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
    		toClient.write(out.toByteArray()); // 输出数据  
    		toClient.close(); 
        }
        catch (Exception e) {
            String msg = "工单照片查看下载远程文件出错：" + e.getLocalizedMessage();
            System.out.println(msg);
        }
        finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            }
            catch (Exception e) {}
        }
	   
	  return null;
  }
 
}
