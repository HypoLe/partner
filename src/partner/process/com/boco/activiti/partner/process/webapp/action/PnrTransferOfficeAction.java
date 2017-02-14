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
import java.sql.Clob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import sun.misc.BASE64Decoder;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.service.INetResInspectService;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrStatisticsService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.dao.hibernate.TawSystemAreaDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
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

public class PnrTransferOfficeAction extends SheetAction {
	private static Logger logger = Logger.getLogger(PnrTransferOfficeAction.class);
	
	private final String processDefinitionKey = "myTransferProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	// 自维人员审核通过
	public final static String AUDIT_STATE_THROUGH = "handle";
	// 自维人员审核驳回
	public final static String AUDIT_STATE_DISMISS = "rollback";
	// 归档状态
	public final static Integer ARCHIVE_STATE = 5;
	// 环节--“传输局”
	public final static String TASK_TRANSFERTASK = "transferTask";
	// 环节--“施工队”
	public final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 环节--“外包公司”
	public final static String TASK_EPIBOLYTASK = "epibolyTask";
	// 工单删除状态
	public final static Integer TASK_DELETE_STATE = 1;
	// 发信息用的常量
	public final static String TASK_NO_STR = "工单号:";
	public final static String TASK_TITLE_STR = "主题:";
	public final static String TASK_DEADLINE_STR = "截止时间:";
	public final static String TASK_CONTACT_STR = "联系人及电话:";
	
	
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
		request.setAttribute("access", 1);//保存草稿按钮标示，1为显示
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

		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionForm.getUserid();
			String isDraft = request.getParameter("isDraft");
		
		// 默认为0，正常派单。
		int state = 0;
		
		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		if("true".equals(isDraft)){//如果是保存草稿 状态为2 设置保存草稿时间
			state = 2;
			pnrTransferOffice.setSaveDraftDate(new Date());
		}
		setTransferOfficeByRequest(request, pnrTransferOffice);

		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String netResInspectId = StaticMethod.nullObject2String(request.getParameter("netResInspectId"));//众筹流程id号
		pnrTransferOffice.setNetResInspectId(netResInspectId);
		
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
		if(!"true".equals(isDraft)){
			// 流程开始
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();

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
			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:派发工单;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
			formService.submitTaskFormData(taskId, map);
			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:派发工单;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
			
			
			// 流程结束
		}
		
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		pnrTransferOffice.setThemeInterface("transferOffice");

		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveOrUpateAttachment(processInstanceId,
					attachments);
		}
		// attachment--end
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, pnrTransferOffice.getTaskAssignee(), pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		//新增将流转环节添加到主表中 end
		
		//往主表中保存地市区县信息
		//取传输局环节的处理人的地市区县作为工单的地市和区县
		String city = "";//地市
		String country = "";//区县
		String areaid = "";
		String cityCountryUserId = StaticMethod.nullObject2String(request.getParameter("taskAssignee"));//接收人
		TawSystemUser cityCountryUser = com.boco.eoms.partner.process.util.CommonUtils.getTawSystemUserByUserId(cityCountryUserId, "");
		if(cityCountryUser != null){
			areaid = com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(cityCountryUser.getDeptid());
		}
		if(areaid.length()==6){//区县的人
			city=areaid.substring(0,4);
			country=areaid;
		}else{
			city = areaid;
			country = areaid;
		}
		pnrTransferOffice.setCity(city); //地市
		pnrTransferOffice.setCountry(country); //区县
		pnrTransferOfficeService.save(pnrTransferOffice);
		//更改众筹流程派发来的草稿表的状态
		if(!"".equals(netResInspectId)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
			netResInspectService.updateNetResInspectDraft(netResInspectId);
			List<PnrAndroidPhotoFile> photoFileList=netResInspectService.serchAndroidPhoto(netResInspectId);
			IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService) getBean("pnrAndroidWorkOderPhotoFileService");
			if(photoFileList!=null&&photoFileList.size()>0){
				for(int i=0;i<photoFileList.size();i++){
					PnrAndroidPhotoFile pnrAndroidPhotoFile=photoFileList.get(i);
					PnrAndroidWorkOderPhotoFile pnrAndroidWorkOderPhotoFile=new PnrAndroidWorkOderPhotoFile();
					pnrAndroidWorkOderPhotoFile.setPicture_id(processInstanceId);
					pnrAndroidWorkOderPhotoFile.setId_type(pnrAndroidPhotoFile.getPhotoType()==null?null:pnrAndroidPhotoFile.getPhotoType());
					pnrAndroidWorkOderPhotoFile.setCreateTime(pnrAndroidPhotoFile.getCreateTime()==null?null:sdf.parse(pnrAndroidPhotoFile.getCreateTime()));
					pnrAndroidWorkOderPhotoFile.setLatitude(pnrAndroidPhotoFile.getLatitude()==null?null:pnrAndroidPhotoFile.getLatitude());
					pnrAndroidWorkOderPhotoFile.setLongitude(pnrAndroidPhotoFile.getLongitude()==null?null:pnrAndroidPhotoFile.getLongitude());
					pnrAndroidWorkOderPhotoFile.setPath(pnrAndroidPhotoFile.getPath()==null?null:pnrAndroidPhotoFile.getPath());
					pnrAndroidWorkOderPhotoFile.setSystemTime(pnrAndroidPhotoFile.getSystemTime()==null?null:pnrAndroidPhotoFile.getSystemTime());
					pnrAndroidWorkOderPhotoFile.setImgPath(pnrAndroidPhotoFile.getPath()==null?null:pnrAndroidPhotoFile.getPath());
					pnrAndroidWorkOderPhotoFile.setState(2);
					pnrAndroidWorkOderPhotoFile.setUserId(pnrAndroidPhotoFile.getUserId()==null?null:pnrAndroidPhotoFile.getUserId());
					pnrAndroidWorkOderPhotoFile.setPhotoType(pnrAndroidPhotoFile.getPhotoType()==null?null:pnrAndroidPhotoFile.getPhotoType());
					pnrAndroidWorkOderPhotoFile.setPhotoSubType("start");
					pnrService.savePhoto(pnrAndroidWorkOderPhotoFile);
				}
			}
		}
		
		
		if(!"true".equals(isDraft)){//保存草稿不需要发短信提醒
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
		}
		

		return mapping.findForward("new_success");

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

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		// 工单管理-传输局工单-抢修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferOfficeService.getToreplyJobOfEmergencyJobCount(
					userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-抢修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferOfficeService.getToreplyJobOfEmergencyJobList(
					userId, sendStartTime, sendEndTime, wsNum, wsTitle, status,
					firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
		if("".equals(taskId)||"-".equals(taskId)){
			request.setAttribute("msg", "错误编号：sd-taskId-004;账号"+userId+"下的工单有问题,请联系管理员");
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
			PnrTransferOffice pnrTransferOffice=list.get(0);
			try{
				INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
				NetResInspectDraft netResInspectDraft=netResInspectService.getNetResInspectDraft(processInstanceId);
				if(netResInspectDraft != null){
					String draft = netResInspectDraft.getInspectProcessInstanceId();
					if(draft != null && !"".equals(draft)){
						pnrTransferOffice.setNetResInspectId(draft);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);
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
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferTask")) {// 传输局派发
				String findForward = "mainSecond";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("newTask")) {
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"epibolyTask")) {// 外包派发
				String findForward = "mainEpiboly";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"transferHandle")) {// 施工队处理
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
				
				//取一次核验人(add)
				String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
				String acheckAssignee = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");
				if("".equals(acheckAssignee)){
					acheckAssignee = "superUser";
				}
				request.setAttribute("acheckAssignee", acheckAssignee);
				
				//回显场景模板信息(add)
				ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
				Map<String, Object> resultMap = sceneTemplateService.echoChildScene(processInstanceId,"auditor",null);
				request.setAttribute("mainSceneList", resultMap.get("mainSceneList"));
				request.setAttribute("childSceneWholeModelList", resultMap.get("childSceneWholeModelList"));
				request.setAttribute("sceneDetailWholeModelList", resultMap.get("sceneDetailWholeModelList"));
				request.setAttribute("totalAmount", resultMap.get("totalAmount"));
				request.setAttribute("auditorSceneExistFlag",resultMap.get("auditorSceneExistFlag").toString());
				
				return mapping.findForward("transferHandle");
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
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		

		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单号
		String processInstanceId = request.getParameter("processInstanceId");
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

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (auditState.equals(AUDIT_STATE_DISMISS)) {
			if (pnrTicketList != null) {
				pnrTicket = pnrTicketList.get(0);
				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);
			}

		} else if (auditState.equals(AUDIT_STATE_THROUGH)) {
			if (pnrTicketList != null) {
				pnrTicket = pnrTicketList.get(0);
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
		
		
		System.out.println("-------审核auditState的值是："+auditState);
		String hjflag="";
		if(auditState.equals(AUDIT_STATE_THROUGH)){
			hjflag="审核";
		}else if(auditState.equals(AUDIT_STATE_DISMISS)){
			hjflag="审核回退";
		}else{
			hjflag="审核未知";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, "archive", "归档",true);
		pnrTransferOfficeService.save(pnrTicket);
		//新增将流转环节添加到主表中 end
		
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

	/**
	 * mainSecond -传输局派发
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
		// 派单时间
		Date createTime = new Date();
		// 接收人
		String taskAssignee = request.getParameter("auditor");

		String dueDate = request.getParameter("dueDate");
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
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
				tempKeyValue+=name+":"+request.getParameter(name)+";";
			}
		}
		
		String processInstanceId=request.getParameter("processInstanceId");//从前台隐藏域获取
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:传输局;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:传输局;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程结束
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = null;
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		entity.setSecondSendTime(sFormat.parse(sFormat.format(createTime)));
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		entity.setEndTime(sFormat.parse(dueDate));
		// 派发人更改
		entity.setInitiator(initiator);
		// 存入第二派单人
		entity.setSecondInitiator(initiator);
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, taskAssignee, entity, PnrTransferOffice.class, null, null,false);
		//新增将流转环节添加到主表中 end
		
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime() == null ? "" : sFormat
				.format(entity.getEndTime());

		String msg = TASK_NO_STR + entity.getProcessInstanceId() + ";"
				+ TASK_TITLE_STR + entity.getTheme() + ";";
		msg += TASK_DEADLINE_STR + deadlineTime + ";" + TASK_CONTACT_STR
				+ entity.getConnectPerson();
		CommonUtils.sendMsg(msg, taskAssignee);

		return mapping.findForward("success");

	}

	/**
	 * mainSecond 外包派发
	 */
	public ActionForward mainEpiboly(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		// 派发人

		String initiator = sessionForm.getUserid();
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 派单时间
		Date createTime = new Date();
		// 接收人
		String taskAssignee = request.getParameter("worker");
		String dueDate = request.getParameter("dueDate");
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
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
				tempKeyValue+=name+":"+request.getParameter(name)+";";
			}
		}
		
		String processInstanceId=request.getParameter("processInstanceId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:外包公司;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:外包公司;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程结束
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = null;
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		// 存入第三派发时间
		entity.setThirdSendTime(sFormat.parse(sFormat.format(createTime)));
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		entity.setProjectStartPoint(taskAssignee);//把施工队的人，暂时存在项目起点这个字段里面
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		// 派发人更改
		entity.setInitiator(initiator);

		entity.setEndTime(sFormat.parse(dueDate));
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, taskAssignee, entity, PnrTransferOffice.class, null, null,false);
		//新增将流转环节添加到主表中 end
		
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime() == null ? "" : sFormat
				.format(entity.getEndTime());

		String msg = TASK_NO_STR + entity.getProcessInstanceId() + ";"
				+ TASK_TITLE_STR + entity.getTheme() + ";";
		msg += TASK_DEADLINE_STR + deadlineTime + ";" + TASK_CONTACT_STR
				+ entity.getConnectPerson();
		CommonUtils.sendMsg(msg, taskAssignee);

		return mapping.findForward("success");

	}

	/**
	 * 处理保存--处理环节
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
//		String mainRemark = StaticMethod.nullObject2String(request
//				.getParameter("mainRemark"));
		// 故障是否恢复
//		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
		
		String totalAmount = StaticMethod.nullObject2String(request.getParameter("totalAmount"),"0");//场景模板总金额
		String makeupFlag = StaticMethod.nullObject2String(request.getParameter("makeupFlag"));//是否补录照片标识
		
		String acheckAssignee = StaticMethod.nullObject2String(request.getParameter("acheckAssignee"));//一次核验人
		
		String faultType = StaticMethod.nullObject2String(request.getParameter("faultType"));//故障类型
		String troubleReason = "";
		if("10123190101".equals(faultType)){ //故障工单
			 troubleReason = StaticMethod.nullObject2String(request.getParameter("troubleReason1"));//故障原因
		}else if("10123190102".equals(faultType)){ //非故障工单
			 troubleReason = StaticMethod.nullObject2String(request.getParameter("troubleReason2"));//故障原因
		}
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
//		entity.setHandleDescription(mainRemark);
//		entity.setIsRecover(isRecover);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		entity.setCheckTime(new Date());
		entity.setLinkName("transferHandle");
		entity.setHandleDescription("web端提交");
		entity.setOperation("01");
		entity.setFaultType(faultType);//故障类型
		entity.setTroubleReason(troubleReason);//故障原因
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
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:施工队;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:施工队;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			
			//新增将流转环节添加到主表中 start
			//processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			processTaskService.setTvalueOfTask(processInstanceId, acheckAssignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			//新增将流转环节添加到主表中 end
			pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(totalAmount));//场景模板金额--回单环节
			pnrTransferOffice.setMakeupPhotoFlag(makeupFlag);//补录照片标识
			pnrTransferOffice.setRollbackFlag("0");
			
			//获取主场景和子场景的中文值，用于列表中的主场景和子场景的显示
			Map<String, String> mainScenesAndCopyScenesName = pnrTransferOfficeService.getMainScenesAndCopyScenesName(request, null);
			pnrTransferOffice.setTransferMainScenesName(mainScenesAndCopyScenesName.get("mainScenesName"));
			pnrTransferOffice.setTransferCopyScenesName(mainScenesAndCopyScenesName.get("copyScenesName"));
			pnrTransferOffice.setRecentMainScenesName(mainScenesAndCopyScenesName.get("mainScenesName"));
			pnrTransferOffice.setRecentCopyScenesName(mainScenesAndCopyScenesName.get("copyScenesName"));
			
			pnrTransferOfficeService.save(pnrTransferOffice);

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		
		//场景模板的数据存储
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
		Map<String,String> param = new HashMap<String,String>();
		param.put("processType", "transferOffice");//流程类型：抢修
		sceneTemplateService.saveSceneTemplate(request,processInstanceId, "auditor", param);
		
		// 流程提交到下一级-end

		String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
				+ title + ";";
		msg += TASK_DEADLINE_STR + deadLineTime + ";" + TASK_CONTACT_STR
				+ contact + ";已处理请审核";
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

		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
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
				.getParameter("teskAssignee"));

		String taskId = request.getParameter("taskId");

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
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (auditState.equals("rollback")) {
			if (pnrTicket != null) {

				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);

				auditor = pnrTicket.getInitiator();// 回退到二次处理人
			}
			isSend = false;

		} else if (auditState.equals("handle")) {
			if (pnrTicket != null) {
				deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
						.format(pnrTicket.getEndTime());
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
	
		System.out.println("-------传输局质检auditState的值是："+auditState);
		String hjflag="";
		if(auditState.equals("handle")){
			hjflag="传输局质检";
		}else if(auditState.equals("rollback")){
			hjflag="传输局质检回退";
		}else{
			hjflag="传输局质检未知";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		request.setAttribute("success", "check");
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTicket);
		//新增将流转环节添加到主表中 end

		// 发短信
		if (isSend) {

			String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
					+ theme + ";";
			msg += TASK_DEADLINE_STR + deadlineTime + ";" + TASK_CONTACT_STR
					+ contact + ";已处理请审核";
			CommonUtils.sendMsg(msg, auditor);
		}

		return mapping.findForward("success");
	}

	/**
	 * 任务审核保存---外包质检
	 * 
	 */
	public ActionForward epibolyAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("epibolyState"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("auditor"));

		String taskId = request.getParameter("taskId");

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
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;

		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (auditState.equals("rollback")) {
			if (pnrTicket != null) {

				pnrTicket.setLastReplyTime(null);
				pnrTransferOfficeService.save(pnrTicket);

				auditor = pnrTicket.getTaskAssignee();

			}
			isSend = false;

		} else if (auditState.equals("handle")) {
			if (pnrTicket != null) {
				deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
						.format(pnrTicket.getEndTime());
				contact = pnrTicket.getConnectPerson();
			}
		}

		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		if (auditState.equals("rollback")) {// 回退时的说法
			map.put("worker", auditor);
			map.remove("auditor");
		}
		
		
		
		
		System.out.println("---------外包质检auditState的值是："+auditState);
		String hjflag="";
		if(auditState.equals("handle")){
			hjflag="外包质检";
		}else if(auditState.equals("rollback")){
			hjflag="外包质检回退";
		}else{
			hjflag="外包质检未知";
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
		request.setAttribute("success", "check");
		
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTicket);
		//新增将流转环节添加到主表中 end

		// 发短信
		if (isSend) {

			String msg = TASK_NO_STR + processInstanceId + ";" + TASK_TITLE_STR
					+ theme + ";";
			msg += TASK_DEADLINE_STR + deadlineTime + ";" + TASK_CONTACT_STR
					+ contact + ";已处理请审核";
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
//		List<HistoricProcessInstance> historicProcessInstancesList = historyService
//				.createHistoricProcessInstanceQuery().processDefinitionKey(
//						processDefinitionKey).startedBy(userId)
//				.orderByProcessInstanceStartTime().desc().listPage(
//						firstResult * pageSize, endResult * pageSize);
//		long total = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey(processDefinitionKey).startedBy(userId)
//				.count();
//		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//		List<TaskModel> rPnrTransferOfficeList = new ArrayList<TaskModel>();
//		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
//			String processInstanceId = historicProcessInstance.getId();
//			TaskModel troubleTicketModel = new TaskModel();
//			troubleTicketModel.setProcessInstanceId(processInstanceId);
//
//			if (historicProcessInstance.getEndTime() != null) {
//				if (historicProcessInstance.getDeleteReason() == null) {
//					troubleTicketModel.setStatusName("已归档");
//				} else {
//					troubleTicketModel.setStatusName("已删除");
//				}
//			} else {
//				List<HistoricTaskInstance> historicTasks = historyService
//						.createHistoricTaskInstanceQuery().processInstanceId(
//								processInstanceId)
//						.orderByHistoricTaskInstanceStartTime().desc()
//						.listPage(0, 1);
//				if (historicTasks != null & historicTasks.size() > 0) {
//					troubleTicketModel.setStatusName(historicTasks.get(0)
//							.getName());
//					troubleTicketModel.setTaskDefKey(historicTasks.get(0)
//							.getTaskDefinitionKey());
//					troubleTicketModel.setTaskId(historicTasks.get(0).getId());
//				}
//			}
//			Search search = new Search();
//			search.addFilterEqual("processInstanceId", processInstanceId);
//			List<PnrTransferOffice> list = pnrTransferOfficeService
//					.search(search);
//			if (list != null && list.size() != 0) {
//				troubleTicketModel.setSendTime(list.get(0).getSendTime());
//				troubleTicketModel.setTheme(list.get(0).getTheme());
//				troubleTicketModel.setState(list.get(0).getState());
//				troubleTicketModel.setInitiator(list.get(0).getInitiator());
//				troubleTicketModel.setOneInitiator(list.get(0)
//						.getOneInitiator());
//				troubleTicketModel.setTwoInitiator(list.get(0)
//						.getSecondInitiator());
//			}
//			rPnrTransferOfficeList.add(troubleTicketModel);
//		}
//		request.setAttribute("taskList", rPnrTransferOfficeList);
//		request.setAttribute("pageSize", pageSize);
//		request.setAttribute("total", (int) total);
		
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		//工单号
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));
		//工单主题
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		//工单状态
		String status = StaticMethod.nullObject2String(request.getParameter("status"));

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 由我创建的工单条数
		int total = pnrTransferNewPrecheckService.getByCreatingWorkOrderCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);

		// 由我创建的工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService
				.getByCreatingWorkOrderList(processDefinitionKey, userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status,
						firstResult, endResult, pageSize);

		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime",sendEndTime);
		request.setAttribute("wsNum",wsNum);
		request.setAttribute("wsTitle",wsTitle);
		request.setAttribute("wsStatus",status);
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
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		
		String initiator = request.getParameter("initiator");

		String handle = request.getParameter("handle");
		System.out.println("-------handle的值是："+handle);
		String taskId = request.getParameter("taskId");

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		
		String hjflag="";//环节标识
		if (handle.equals(TASK_TRANSFERTASK)) {
			hjflag="传输局回退";
			map.put("initiator", initiator);
			map.put("transferState", "rollback");

		} else if (handle.equals(TASK_TRANSFERHANDLE)) {
			hjflag="施工队回退";
			map.put("auditor", initiator);
			map.put("handleState", "rollback");
		} else if (handle.equals(TASK_EPIBOLYTASK)) {
			hjflag="外包公司回退";
			map.put("taskAssignee", initiator);
			map.put("epiloyState", "rollback");
		}

		String	processInstanceId=request.getParameter("processInstanceId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		
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
		
		//新增将流转环节添加到主表中 start
		processTaskService.setTvalueOfTask(processInstanceId, initiator, pnrTicket, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTicket);
		//新增将流转环节添加到主表中 end
		
		//施工队驳回时，删除之前填写的施工队的场景模板
		if(handle.equals(TASK_TRANSFERHANDLE)){
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
			Map<String, String> param = new HashMap<String,String>();
			param.put("processType", "transferOffice");
			sceneTemplateService.deleteSceneTemplate(processInstanceId,"auditor",param);	
		}
		
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
		if (eList != null && eList.size() > 0) {
			PnrTransferOffice entity = eList.get(0);
			entity.setState(TASK_DELETE_STATE);
			processTaskService.setTvalueOfTask(processInstanceId, "", entity, PnrTransferOffice.class, "delete", "已删除",true);
			pnrTransferOfficeService.save(entity);
		}
		return mapping.findForward("delete_success");
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
		// String status = request.getParameter("status");
		String status = "archived";
		String themeInterface = "transferOffice";

		//和本地网共用归档查询
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 已归档工单条数
		int total = 0 ;
		List<TaskModel> rPnrTicketList = null ;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			total = pnrTransferNewPrecheckService.getArchivedCount(processDefinitionKey, userId, sendStartTime, sendEndTime,
					wsNum, wsTitle, status, themeInterface);

			// 已归档工单明细
			rPnrTicketList = pnrTransferNewPrecheckService.getArchivedList(processDefinitionKey, userId, sendStartTime,
							sendEndTime, wsNum, wsTitle, status, themeInterface,
							firstResult, endResult, pageSize);
		}else{
			request.setAttribute("tishiFlag","Y");//没选查询条件时，给出提示
		}
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
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							processInstanceId)
//					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
//							1);
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
//				transferModel.setSendTime(list.get(0).getSendTime());
//				transferModel.setTheme(list.get(0).getTheme());
//				transferModel.setInitiator(list.get(0).getInitiator());
//				transferModel.setOneInitiator(list.get(0).getOneInitiator());
//				TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
//						.get(0).getTaskAssignee(), "");
//				if (pu != null) {
//					transferModel.setDeptId(pu.getDeptid());
//
//				}
//			}
//
//			rPnrTicketList.add(transferModel);
//		}
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		//request.setAttribute("total", (int) total);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		//return mapping.findForward("involvedProcessInstancesList");
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
		
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		String status = StaticMethod.nullObject2String(request.getParameter("status"));
		
		//和本地网预检预修公用已处理工单查询
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 已处理工单条数
		int total = pnrTransferNewPrecheckService.getHaveProcessCount(processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);

		// 已处理工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService.getHaveProcessList(processDefinitionKey, userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status,
						firstResult, endResult, pageSize);
//		List<HistoricProcessInstance> historicProcessInstancesList = historyService
//				.createHistoricProcessInstanceQuery().processDefinitionKey(
//						processDefinitionKey).involvedUser(userId).unfinished()
//				.listPage(firstResult * pageSize, endResult * pageSize);
//		long total = historyService.createHistoricProcessInstanceQuery()
//				.processDefinitionKey(processDefinitionKey)
//				.involvedUser(userId).unfinished().count();
//
//		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//		List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();
//		for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
//		String processInstanceId = historicProcessInstance.getId();
//			TaskModel troubleTicketModel = new TaskModel();
//			troubleTicketModel.setProcessInstanceId(historicProcessInstance
//					.getId());
//			Search search = new Search();
//			search.addFilterEqual("processInstanceId", historicProcessInstance
//					.getId());
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							processInstanceId)
//					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
//							1);
//			if (historicTasks != null & historicTasks.size() > 0) {
//				troubleTicketModel
//						.setStatusName(historicTasks.get(0).getName());
//				troubleTicketModel.setTaskDefKey(historicTasks.get(0)
//						.getTaskDefinitionKey());
//				troubleTicketModel.setTaskId(historicTasks.get(0).getId());
//			}
//			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
//			List<PnrTransferOffice> list = t.getResult();
//			if (list != null && list.size() != 0) {
//				troubleTicketModel.setSendTime(list.get(0).getSendTime());
//			troubleTicketModel.setTheme(list.get(0).getTheme());
//				troubleTicketModel.setInitiator(list.get(0).getInitiator());
//				troubleTicketModel.setOneInitiator(list.get(0)
//						.getOneInitiator());
//				troubleTicketModel.setTwoInitiator(list.get(0)
//						.getSecondInitiator());
//				String message = pnrTransferOfficeService.getLoginPersonStatus(
//						userId, list.get(0));
//				troubleTicketModel.setPersonStatus(message);
//				TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
//						.get(0).getTaskAssignee(), "");
//			if (pu != null) {
//					troubleTicketModel.setDeptId(pu.getDeptid());
//
//				}
//		}
//
//			rPnrTicketList.add(troubleTicketModel);
//		}
		request.setAttribute("taskList", rPnrTicketList);
		//request.setAttribute("total",(int) total);
		request.setAttribute("total",total);
		request.setAttribute("pageSize", pageSize);
		
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
	public ActionForward getBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
				troubleTicketModel.setOneInitiator(list.get(0)
						.getOneInitiator());
				troubleTicketModel.setTwoInitiator(list.get(0)
						.getSecondInitiator());
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
	 * 当月第一天
	 * 
	 * @return
	 */
	private String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(
				" 00:00:00");
		return str.toString();

	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	private String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();

	}

	/**
	 * 显示工单统计页面
	 */
	public ActionForward showStatisticsPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ITawSystemDictTypeManager taw = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List<TawSystemDictType> transferOfficeList = taw
				.getDictSonsByDictid("1012301");
		List<TawSystemDictType> transferInterfaceList = taw
				.getDictSonsByDictid("1012306");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		request.setAttribute("transferOfficeList", transferOfficeList);
		request.setAttribute("transferInterfaceList", transferInterfaceList);
		request.setAttribute("firstDay", getFirstDay().substring(0, 10));
		request.setAttribute("lastDay", getLastDay().substring(0, 10));

		return mapping.findForward("showStatisticsPage");
	}

	/**
	 * 显示工单统计钻取页面-工单列表
	 */
	public ActionForward workOrderStatisticsDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");
		String[] subTypeArray = null;
		if (subTypeString == null || "".equals(subTypeString)) {

			subTypeArray = (String[]) request.getSession().getAttribute(
					"subTypeArray");
			if (subTypeArray == null) {
				subTypeArray = (String[]) request.getSession().getAttribute(
						"subTypeArray");
			}
			if (subTypeArray != null) {
				for (String s : subTypeArray) {
					if (subTypeString == null) {
						subTypeString = s;
					} else {
						subTypeString = subTypeString + "','" + s;
					}
				}
			}
		}

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.transferOfficeStatisticsDrill(taskType,
				flag, city, beginTime, endTime, subTypeString, firstResult
						* pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 个人工作台--统计数据钻取
	 * 
	 * @author wangyue
	 * @title: personWorkOrderStatisticsDrill
	 * @date Sep 19, 2014 4:22:50 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward personWorkOrderStatisticsDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;

		String subTypeString = request.getParameter("subType");
		String[] subTypeArray = null;
		if (subTypeString == null || "".equals(subTypeString)) {

			subTypeArray = (String[]) request.getSession().getAttribute(
					"subTypeArray");
			if (subTypeArray == null) {
				subTypeArray = (String[]) request.getSession().getAttribute(
						"subTypeArray");
			}
			if (subTypeArray != null) {
				for (String s : subTypeArray) {
					if (subTypeString == null) {
						subTypeString = s;
					} else {
						subTypeString = subTypeString + "," + s;
					}
				}
			}
		}

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrill("transferOffice",
				flag, city, beginTime, endTime, subTypeString, firstResult
						* pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);

		return mapping.findForward("personWorkOrderStatisticsDrill");
	}

	/**
	 * 在处理人工单页面 - 钻取
	 */
	public ActionForward workOrderStatisticsDrillbyperson(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";

		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");

		String person = request.getParameter("person");
		System.out.println(city + "     " + person);
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.transferOfficeStatisticsDrillbyperson(
				person, taskType, flag, city, beginTime, endTime,
				subTypeString, firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 在处理人工单页面 - 钻取--个人工作台
	 */
	public ActionForward newWorkOrderStatisticsDrillbyperson(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";

		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");

		String person = request.getParameter("person");
		System.out.println(city + "     " + person);
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrillbyperson(person,
				taskType, flag, city, beginTime, endTime, subTypeString,
				firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("newWorkOrderStatisticsDrill");
	}

	/**
	 * 显示工单统计钻取页面-工单列表
	 */
	public ActionForward workOrderStatisticsDrillbycity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");
		String[] subTypeArray = null;
		if (subTypeString == null || "".equals(subTypeString)) {

			subTypeArray = (String[]) request.getSession().getAttribute(
					"subTypeArray");
			if (subTypeArray == null) {
				subTypeArray = (String[]) request.getSession().getAttribute(
						"subTypeArray");
			}
			if (subTypeArray != null) {
				for (String s : subTypeArray) {
					if (subTypeString == null) {
						subTypeString = s;
					} else {
						subTypeString = subTypeString + "','" + s;
					}
				}
			}
		}

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.transferOfficeStatisticsDrillbycity(
				taskType, flag, city, beginTime, endTime, subTypeString,
				firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 显示工单统计钻取页面-工单列表--个人工作台
	 */
	public ActionForward personWorkOrderStatisticsDrillbycity(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;

		String subTypeString = request.getParameter("subType");
		String[] subTypeArray = null;
		if (subTypeString == null || "".equals(subTypeString)) {

			subTypeArray = (String[]) request.getSession().getAttribute(
					"subTypeArray");
			if (subTypeArray == null) {
				subTypeArray = (String[]) request.getSession().getAttribute(
						"subTypeArray");
			}
			if (subTypeArray != null) {
				for (String s : subTypeArray) {
					if (subTypeString == null) {
						subTypeString = s;
					} else {
						subTypeString = subTypeString + "," + s;
					}
				}
			}
		}

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrillbycity(
				"transferOffice", flag, city, beginTime, endTime,
				subTypeString, firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);

		return mapping.findForward("personWorkOrderStatisticsDrill");
	}

	/**
	 * 首页工单统计钻取页面-工单列表--归档统计
	 */
	public ActionForward statisticsPartnerIndexDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		String flag = request.getParameter("flag");

		String city = request.getParameter("city");
		String level = request.getParameter("level");
		String person = null;
		// 点击地市页面的数字时
		if ("city".equals(level)) {
			request.setAttribute("code", "country");

		}
		// 点击区县页面的数字时
		else if ("country".equals(level)) {
			request.setAttribute("code", "person");
		}
		// 当点击到处理人页面时；city获取到的是人的ID，真正的city需要传过来
		else if ("person".equals(level)) {
			city = request.getParameter("country");
			person = request.getParameter("city");
		}

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		String subType = request.getParameter("subType");

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.statisticsPartnerIndexDrill(flag, city,
				beginTime, endTime, subType, level, person, pageSize
						* firstResult, pageSize * endResult);
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");
		int total = 0;
		total = Integer.parseInt(map.get("size").toString());

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("statisticsPartnerIndexDrill");
	}

	/**
	 * 首页工单统计钻取页面-工单列表--在途统计; 与归档统计同用一个页面。
	 */
	public ActionForward statisticsPartnerIndexDrill3(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		String flag = request.getParameter("flag");

		String city = request.getParameter("city");
		String level = request.getParameter("level");
		String person = null;
		// 点击地市页面的数字时
		if ("city".equals(level)) {
			request.setAttribute("code", "country");

		}
		// 点击区县页面的数字时
		else if ("country".equals(level)) {
			request.setAttribute("code", "person");
		}
		// 当点击到处理人页面时；city获取到的是人的ID，真正的city需要传过来
		else if ("person".equals(level)) {
			city = request.getParameter("country");
			person = request.getParameter("city");
		}

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		String subType = request.getParameter("subType");

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map = pnrStatisticsService
				.statisticsPartnerIndexDrill3(flag, city, beginTime, endTime,
						subType, level, person, firstResult * pageSize,
						endResult * pageSize);
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");
		int total = 0;
		total = Integer.parseInt(map.get("size").toString());

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("statisticsPartnerIndexDrill");
	}

	/**
	 * 提交action判断走哪个action方--权限解决
	 */

	public ActionForward workOrderStatisticstijiao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		// ItawSystemDeptManager
		ITawSystemDeptManager systemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = systemDeptManager.getDeptinfobydeptid(
				deptId, "0");
		String AreaId = tawSystemDept.getAreaid();

		TawSystemAreaDaoHibernate tawSystemAreaDao = (TawSystemAreaDaoHibernate) getBean("tawSystemAreaDao");

		int ordercode = 1;
		String areaname = null;
		try {
			areaname = tawSystemAreaDao.id2Name(AreaId);
			ordercode = tawSystemAreaDao.getAreaByAreaId(AreaId).getOrdercode();
		} catch (DictDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(AreaId);
		System.out.println(areaname);

		request.setAttribute("city", AreaId);
		request.setAttribute("cityname", areaname);
		request.setAttribute("deptId", deptId);

		String beginTime = request.getParameter("bTime");
		String endTime = request.getParameter("eTime");
		String[] subTypeArray = request.getParameterValues("subType");

		if (ordercode == 1) {
			return workOrderStatistics(mapping, form, request, response);
		} else if (ordercode == 2) {
			return workOrderStatisticsByCity(mapping, form, request, response);
		} else if (ordercode == 3) {
			return workOrderStatisticsByCountry(mapping, form, request,
					response);
		}

		return workOrderStatistics(mapping, form, request, response);
	}

	/**
	 * 提交action判断走哪个action方--权限解决--公客接口用
	 */

	public ActionForward personWorkOrderStatisticstijiao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		// ItawSystemDeptManager
		ITawSystemDeptManager systemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = systemDeptManager.getDeptinfobydeptid(
				deptId, "0");
		String AreaId = tawSystemDept.getAreaid();

		TawSystemAreaDaoHibernate tawSystemAreaDao = (TawSystemAreaDaoHibernate) getBean("tawSystemAreaDao");

		int ordercode = 1;
		String areaname = null;
		try {
			areaname = tawSystemAreaDao.id2Name(AreaId);
			ordercode = tawSystemAreaDao.getAreaByAreaId(AreaId).getOrdercode();
		} catch (DictDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(AreaId);
		System.out.println(areaname);

		request.setAttribute("city", AreaId);
		request.setAttribute("cityname", areaname);
		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;

		String[] subTypeArray = null;

		if (ordercode == 1) {
			return personWorkOrderStatistics(mapping, form, request, response);
		} else if (ordercode == 2) {
			return personWorkOrderStatisticsByCity(mapping, form, request,
					response);
		} else if (ordercode == 3) {
			return personWorkOrderStatisticsByCountry(mapping, form, request,
					response);
		}

		return personWorkOrderStatistics(mapping, form, request, response);
	}

	/**
	 * 工单查询统计
	 */
	public ActionForward workOrderStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String beginTime = request.getParameter("bTime");
		String endTime = request.getParameter("eTime");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = (String) request.getSession().getAttribute(
					"firstBeginTime");
		}
		if (endTime == null || endTime.equals("")) {
			endTime = (String) request.getSession()
					.getAttribute("firstEndTime");
		}
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		String[] subTypeArray = request.getParameterValues("subType");
		if (subTypeArray == null) {
			subTypeArray = (String[]) request.getSession().getAttribute(
					"firstSubTypeArray");
		}
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "','" + s;
				}
			}
		}
		String taskType = request.getParameter("taskType");
		if (taskType == null || "".equals(taskType)) {
			taskType = (String) request.getSession().getAttribute(
					"firstTaskType");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.transferOfficeStatistics(taskType, beginTime, endTime,
						subTypeString);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);
		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());
		return mapping.findForward("performStatisticsQuery");
	}

	/**
	 * 工单查询统计
	 */
	public ActionForward personWorkOrderStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;
		String[] subTypeArray = request.getParameterValues("subType");
		if (subTypeArray == null) {
			subTypeArray = (String[]) request.getSession().getAttribute(
					"firstSubTypeArray");
		}
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "," + s;
				}
			}
		}
		String taskType = request.getParameter("taskType");
		if (taskType == null || "".equals(taskType)) {
			taskType = (String) request.getSession().getAttribute(
					"firstTaskType");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.workOrderStatistics("transferOffice", beginTime, endTime,
						subTypeString);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		request.getSession().setAttribute("firstBeginTime", beginTime);
		request.getSession().setAttribute("firstEndTime", endTime);
		request.getSession().setAttribute("firstSubTypeArray", subTypeArray);
		request.getSession().setAttribute("firstTaskType", taskType);
		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());
		return mapping.findForward("performPersonStatisticsQuery");
	}

	/**
	 * 工单查询统计 按地市钻取
	 */
	public ActionForward workOrderStatisticsByCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String endTime = request.getParameter("eTime");
		String beginTime = request.getParameter("bTime");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = (String) request.getSession().getAttribute(
					"secondBeginTime");
		}
		if (endTime == null || endTime.equals("")) {
			endTime = (String) request.getSession().getAttribute(
					"secondEndTime");
		}

		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		String[] subTypeArray = request.getParameterValues("subType");
		if (subTypeArray == null) {
			subTypeArray = (String[]) request.getSession().getAttribute(
					"secondSubTypeArray");
		}
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "','" + s;
				}
			}
		}
		String city = null;
		String cityname = null;

		cityname = request.getParameter("cityname");
		city = request.getParameter("city");
		if (city == null || "".equals(city)) {
			city = (String) request.getSession().getAttribute("secondCity");
		}

		if (cityname == null || "".equals(cityname)) {
			cityname = (String) request.getSession().getAttribute(
					"secondCityName");
		}
		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		if (cityname == null) {
			cityname = (String) request.getAttribute("cityname");
		}

		String taskType = request.getParameter("taskType");

		if (taskType == null || "".equals(taskType)) {
			taskType = (String) request.getSession().getAttribute(
					"secondTaskType");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.transferOfficeStatisticsbyCity(city, cityname, taskType,
						beginTime, endTime, subTypeString);

		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);
		request.setAttribute("city", city);
		request.setAttribute("cityName", cityname);

		request.getSession().setAttribute("secondBeginTime", beginTime);
		request.getSession().setAttribute("secondEndTime", endTime);
		request.getSession().setAttribute("secondSubTypeArray", subTypeArray);
		request.getSession().setAttribute("secondTaskType", taskType);
		request.getSession().setAttribute("secondCity", city);
		request.getSession().setAttribute("secondCityName", cityname);

		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());

		return mapping.findForward("performStatisticsQuerybyCity");
	}

	/**
	 * 传输局个人工作台--城市钻取
	 * 
	 * @author wangyue
	 * @title: personWorkOrderStatisticsByCity
	 * @date Sep 19, 2014 2:23:45 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward personWorkOrderStatisticsByCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;

		String[] subTypeArray = request.getParameterValues("subType");
		if (subTypeArray == null) {
			subTypeArray = (String[]) request.getSession().getAttribute(
					"secondSubTypeArray");
		}
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "," + s;
				}
			}
		}
		String city = null;
		String cityname = null;

		cityname = request.getParameter("cityname");
		city = request.getParameter("city");
		if (city == null || "".equals(city)) {
			city = (String) request.getSession().getAttribute("secondCity");
		}

		if (cityname == null || "".equals(cityname)) {
			cityname = (String) request.getSession().getAttribute(
					"secondCityName");
		}
		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		if (cityname == null) {
			cityname = (String) request.getAttribute("cityname");
		}

		String taskType = request.getParameter("taskType");

		if (taskType == null || "".equals(taskType)) {
			taskType = (String) request.getSession().getAttribute(
					"secondTaskType");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.workOrderStatisticsbyCity(city, cityname, "transferOffice",
						beginTime, endTime, subTypeString);

		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);
		request.setAttribute("city", city);
		request.setAttribute("cityName", cityname);

		request.getSession().setAttribute("secondBeginTime", beginTime);
		request.getSession().setAttribute("secondEndTime", endTime);
		request.getSession().setAttribute("secondSubTypeArray", subTypeArray);
		request.getSession().setAttribute("secondTaskType", taskType);
		request.getSession().setAttribute("secondCity", city);
		request.getSession().setAttribute("secondCityName", cityname);

		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());

		return mapping.findForward("performPersonStatisticsQuerybyCity");
	}

	/**
	 * 工单查询统计--县区级钻取
	 */
	public ActionForward workOrderStatisticsByCountry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");

		String[] subTypeArray = request.getParameterValues("subType");
		if (subTypeArray == null) {
			subTypeArray = (String[]) request.getSession().getAttribute(
					"firstSubTypeArray");
		}
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "','" + s;
				}
			}
		}

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");

		Map<String, Object> map;
		map = pnrStatisticsService.transferOfficeStatisticsbyCountry(city,
				taskType, beginTime, endTime, subTypeString, firstResult
						* pageSize, endResult * pageSize);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsModel> workOrder;
		workOrder = (List<WorkOrderStatisticsModel>) map.get("list");
		for (int i = 0; i < workOrder.size(); i++) {
			System.out.println(workOrder.get(i).getCitylength() + "===");
		}
		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("performStatisticsQuerybycountry");

	}

	/**
	 * 个人工作台--县级钻取
	 * 
	 * @author wangyue
	 * @title: personWorkOrderStatisticsByCountry
	 * @date Sep 19, 2014 4:29:27 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward personWorkOrderStatisticsByCountry(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";

		String subTypeString = request.getParameter("subType");
		// 获取当月的第一天和最后一天

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String beginTime = firstDay;
		String endTime = lastDay;
		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");

		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsbyCountry(city,
				"transferOffice", beginTime, endTime, subTypeString,
				firstResult * pageSize, endResult * pageSize);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsModel> workOrder;
		workOrder = (List<WorkOrderStatisticsModel>) map.get("list");
		for (int i = 0; i < workOrder.size(); i++) {
			System.out.println(workOrder.get(i).getCitylength() + "===");
		}
		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("performPersonStatisticsQuerybycountry");

	}
	
	/**
	 * 抢修工单（非故障引发抢修工单）施工队照片补录 工单列表查看
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
		
		//抢修工单标识
		selectAttribute.setReportType("transferOffice");

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
	
	/**
	 * 	 抢修工单的工单查询功能
	 	 * @author WANGJUN
	 	 * @title: workOrderQueryOfTransferOfficeList
	 	 * @date Jun 7, 2016 9:09:23 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward workOrderQueryOfTransferOfficeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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

		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		String status = StaticMethod.nullObject2String(request.getParameter("status"));

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		// 本地网-预检预修工单集合条数
		int total = 0;
		// 本地网-预检预修工单集合
		List<TaskModel> rPnrTransferList = null;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			try {
				total = pnrTransferOfficeService.getWorkOrderQueryOfTransferOfficeListCount(areaid,userId,
						"",processDefinitionKey,conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}


			try {
				rPnrTransferList = pnrTransferOfficeService.getWorkOrderQueryOfTransferOfficeList(areaid,userId,"",processDefinitionKey,conditionQueryModel,
								firstResult, endResult, pageSize);
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
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);	
		return mapping.findForward("workOrderQueryOfTransferOfficeList");
	}
	/**
	 * 	 
	 	 * @author zhoukeqing
	 	 * @title: doFreeFlow
	 	 * @date Jan 14, 2016 9:32:09 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward forStartInterFaceSubFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processInstanceId = StaticMethod.null2String(request.getParameter("processInstanceId"));
		INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
		NetResInspectDraft netResInspectDraft=netResInspectService.getNetResInspectDraft(processInstanceId);
		PnrTransferOffice pnrTransferOffice=new PnrTransferOffice();
		if(netResInspectDraft!=null){
			pnrTransferOffice.setTheme(netResInspectDraft.getTheme());
			pnrTransferOffice.setProcessLimit(netResInspectDraft.getProcessLimit());
			pnrTransferOffice.setFaultSource("101230208");
			pnrTransferOffice.setCreateTime(netResInspectDraft.getCreateTime());
			pnrTransferOffice.setConnectPerson(netResInspectDraft.getConnectPerson());
			pnrTransferOffice.setFaultDescription(netResInspectDraft.getFaultDescription());
			pnrTransferOffice.setNetResInspectId(netResInspectDraft.getInspectProcessInstanceId());
			pnrTransferOffice.setProcessInstanceId(processInstanceId);
		}
		CommonUtils.getCompetenceLimit(request);
		request.setAttribute("mainFaultOccurTime", new Date());// 申请提交时间显示为系统当前时间
		request.setAttribute("pnrTransferOffice", pnrTransferOffice);
		request.setAttribute("processInstanceId", processInstanceId);
		return mapping.findForward("new");
	}
	/***
	   * 工单详情中，新建工单图片查看
	    * @author wangyue
	    * @title: showCreateWorkPhoto
	    * @date Mar 19, 2015 4:06:39 PM
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception ActionForward
	   */
	  public ActionForward showCreateWorkPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
		// 工单号
			String processInstanceId = StaticMethod.nullObject2String(request
					.getParameter("pid"));
			IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
			List<PnrAndroidPhotoFile> photoList = pnrTransferNewPrecheckService.showPhotoByProcessInstanceId(processInstanceId);
			request.setAttribute("list", photoList);
			int total = 0 ;
			if(photoList != null){
				total = photoList.size();
			}
			request.setAttribute("total",total);
			return mapping.findForward("showPhotoView");
	  }
	  /**
		 * 本地网和干线工单照片查看--查看的图片以二进制流存在数据库中
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward precheckShowPicture(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			String id = request.getParameter("id");
			String pageIndexString = request.getParameter("curPage");
			getClass().getClassLoader().getResource("images/login.png");
			// System.out.println("--测试----" + pageIndexString);
			int firstResult = (com.google.common.base.Strings
					.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(
					pageIndexString).intValue() - 1;

			PnrAndroidPhotoFile taskFile = null;
			IPnrAndroidMgr pnrAndroidMgr = (IPnrAndroidMgr) getBean("ipnrAndroidMgrImpl");
			Search search = new Search();
			search.addFilterEqual("id", id);
			List list = pnrAndroidMgr.search(search);
			if ((list != null) && (list.size() > 0))
				taskFile = (PnrAndroidPhotoFile) list.get(0);

			Clob clob = taskFile.getFileData();

			if (clob != null) {
				File tempFile = File.createTempFile("tmp", ".png");
				// System.out.println("--tempFilePath-" + tempFile.getPath());
				try {
					int len;
					response.setHeader("Pragma", "no-cache");
					response.setHeader("Cache-Control", "no-cache");
					response.setDateHeader("Expires", 0L);
					response.setContentType("image/jpeg");

					char[] buf = new char[255];
					Reader reader = clob.getCharacterStream();
					FileOutputStream fos = new FileOutputStream(tempFile);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
							fos));
					while ((len = reader.read(buf)) > 0) {
						bw.write(buf, 0, len);
						bw.flush();
					}
					bw.close();
					FileInputStream fis = new FileInputStream(tempFile);
					BASE64Decoder base64 = new BASE64Decoder();
					base64.decodeBuffer(fis, response.getOutputStream());
				} finally {
					tempFile.deleteOnExit();
				}
			}

			return null;
		}
		/**
		 * 本地网、干线工单照片查看--照片以路径形式存储在数据库中
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward showPicture(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String id = request.getParameter("id");
			String pageIndexString = request.getParameter("curPage");
			// getClass().getClassLoader().getResource("images/login.png");
			// System.out.println("--测试----" + pageIndexString);
			int firstResult = (com.google.common.base.Strings
					.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(
					pageIndexString).intValue() - 1;

			PnrAndroidPhotoFile taskFile = null;
			// IPnrAndroidMgrImpl pnrAndroidMgrImpl =
			// (IPnrAndroidMgrImpl)getBean("ipnrAndroidMgrImpl");
			IPnrAndroidMgr pnrAndroidMgrImpl = (IPnrAndroidMgr) getBean("ipnrAndroidMgrImpl");

			Search search = new Search();
			search.addFilterEqual("id", id);
			List list = pnrAndroidMgrImpl.search(search);
			if ((list != null) && (list.size() > 0))
				taskFile = (PnrAndroidPhotoFile) list.get(0);

			String path = taskFile.getPath();
			// String path="wyphotoTest/qq.jpg";
			String patch = path;

			/*
			 * //获取盘符 String strClassName = getClass().getName(); String
			 * strPackageName=""; if(getClass().getPackage()!=null){ strPackageName =
			 * getClass().getPackage().getName(); } String strFileName = "";
			 * if(!"".equals(strPackageName)){ strFileName =
			 * strClassName.substring(strPackageName.length()+1,strClassName.length());
			 * }else{ strFileName = strClassName; } URL url =null ; url =
			 * getClass().getResource(strFileName+".class"); String strUrl =
			 * url.toString(); strUrl =
			 * strUrl.substring(strUrl.indexOf('/')+1,strUrl.lastIndexOf('W'));
			 * 
			 * String flag = File.separator;
			 * if("\\".equals(flag)){//windows环境下的分隔符是\\ patch = strUrl+patch; }else
			 * if("/".equals(flag)){//Linux环境下的分隔符是/ patch = "/"+strUrl+patch; }
			 * System.out.println(patch); //肉哥思路 FileInputStream is = new
			 * FileInputStream(patch); int i = is.available(); // 得到文件大小 byte data[] =
			 * new byte[i]; is.read(data); // 读数据 is.close();
			 * response.setContentType("image/*"); // 设置返回的文件类型 OutputStream
			 * toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
			 * toClient.write(data); // 输出数据 toClient.close();
			 */

			InputStream in = null;
			ByteArrayOutputStream out = null;
			try {
				// 创建远程文件对象
				// String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL
				// + patch;
				String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl()
						+ patch;
				SmbFile remoteFile = new SmbFile(remotePhotoUrl);
				remoteFile.connect(); // 尝试连接
				// 创建文件流
				in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
				out = new ByteArrayOutputStream((int) remoteFile.length());
				// 读取文件内容
				byte[] buffer = new byte[4096];
				int len = 0; // 读取长度
				while ((len = in.read(buffer, 0, buffer.length)) != -1) {
					out.write(buffer, 0, len);
				}

				out.flush(); // 刷新缓冲的输出流

				response.setContentType("image/*"); // 设置返回的文件类型
				OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
				toClient.write(out.toByteArray()); // 输出数据
				toClient.close();
			} catch (Exception e) {
				String msg = "工单照片选择-下载远程文件出错：" + e.getLocalizedMessage();
//				System.out.println(msg);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
				}
			}

			return null;
		}
}
