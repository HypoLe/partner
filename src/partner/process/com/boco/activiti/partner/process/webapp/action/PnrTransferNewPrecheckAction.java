package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Decoder;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.MasteSelCopyTask;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.MasterScene;
import com.boco.activiti.partner.process.po.MaterialModel;
import com.boco.activiti.partner.process.po.ParameterModel;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.SceneTableModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TheadModel;
import com.boco.activiti.partner.process.po.TheadModelSel;
import com.boco.activiti.partner.process.po.TransferOfficeHandleModel;
import com.boco.activiti.partner.process.service.IMasteSelCopyTaskService;
import com.boco.activiti.partner.process.service.INetResInspectService;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferOverhaulRemakeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.activiti.partner.process.service.PnrPrecheckPhotoService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.common.util.UUIDHexGenerator;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.accessories.util.CompressBook;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.home.mgr.MaterialLibMgr;
import com.boco.eoms.partner.home.model.MaterialLib;
import com.boco.eoms.partner.home.model.MatlibScopeDept;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.RejectToAnyTaskUtil;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.boco.eoms.util.InterfaceUtil;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrTransferNewPrecheckAction extends SheetAction {

	private final String processDefinitionKey = "transferNewPrechechProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger
			.getLogger(PnrTransferPrecheckAction.class);
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

	// 施工队
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 市运维
	public final static String TASK_CITYMANAGEEXAMINE_STATUS = "市运维审批完毕";

	public final static String TASK_PROVINCEMANAGEEXAMINE_STATUS = "省运维审批完毕";

	private final static String TASK_WORKORDERCHECK = "workOrderCheck";
	private final static String TASK_CITYLINEEXAMINE = "cityLineExamine";
	private final static String TASK_CITYLINEDIRECTOR = "cityLineDirectorAudit";
	// private final static String TASK_PROVINCEMANAGE = "provinceManageCheck";
	private final static String TASK_CITYMANAGEEXAMINE = "cityManageExamine";
	private final static String TASK_CITYMANAGEDIRECTOR = "cityManageDirectorAudit";
	private final static String TASK_CITYVICEAUDIT = "cityViceAudit";
	private final static String TASK_PROVINCELINEEXAMINE = "provinceLineExamine";
	private final static String TASK_PROVINCELINEVICE = "provinceLineViceAudit";
	private final static String TASK_PROVINCEMANAGEEXAMINE = "provinceManageExamine";
	private final static String TASK_PROVINCEMANAGEVICE = "provinceManageViceAudit";
	// 工单删除状态
	public final static Integer TASK_DELETE_STATE = 1;
	/** 动态获取角色id实体类 */
	private PrecheckRoleModel precheckRoleModel = (PrecheckRoleModel) ApplicationContextHolder
			.getInstance().getBean("precheckRoleModel");
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	private RuntimeService runtimeService = (RuntimeService) ApplicationContextHolder.getInstance().getBean("runtimeService");
	
	/**
	 * 预检预修工单--新增页面
	 * 
	 * @author wangyue
	 * @title: newTask
	 * @date Feb 4, 2015 10:27:41 AM
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
		request.setAttribute("mainFaultOccurTime", new Date());// 申请提交时间显示为系统当前时间
		return mapping.findForward("new");
	}

	/**
	 * 预检预修工单--工单发起
	 * 
	 * @author wangyue
	 * @title: performSave
	 * @date Feb 4, 2015 10:38:49 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskDefKey = "workOrderCheck";
		String taskDefKeyName = "工单发起审核";
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String isDraft =request.getParameter("isDraft");
		// 根据派发人的部门所属区域，确定工单的区域
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		String city = areaid;
		if ((StaticMethod.nullObject2String(areaid)).length() > 0
				&& areaid.length() > 4) {
			city = areaid.substring(0, 4);
		}

		// isDraft =="" 走派发流程 不等于空是保存草稿流程 
		int state = 0;
		if("true".equals(isDraft)){
		    state = 2;
		}
		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		setTransferOfficeByRequest(request, pnrTransferOffice);

		String taskAssignee = pnrTransferOffice.getTaskAssignee();
		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String theme = pnrTransferOffice.getTheme();
		String subType = pnrTransferOffice.getSubTypeName();
		String workOrderType = pnrTransferOffice.getWorkOrderTypeName();
		String workOrderDegree = pnrTransferOffice.getWorkOrderDegree();
		workOrderDegree = getDictNameByDictid(workOrderDegree);
		// 图片信息
		String photoesIds = request.getParameter("photoIds");
		String photoNames = request.getParameter("photoName");

		// 设置工单的区域
		pnrTransferOffice.setCity(city);
		pnrTransferOffice.setCountry(areaid);
		String netResInspectId = StaticMethod.nullObject2String(request.getParameter("netResInspectId"));//众筹流程id号
		pnrTransferOffice.setNetResInspectId(netResInspectId);

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		String sheetId = "";
		String tempSheetId = request.getParameter("sheetId");
		if (tempSheetId != null && !tempSheetId.equals("")) {
			sheetId = tempSheetId;
		} else {
			if (processInstanceId == null || "".equals(processInstanceId)) {

				try {
					sheetId = pnrTransferOfficeService.getSheetId(
							processDefinitionKey, userId, "B", "JX");

				} catch (Exception e) {
					request.setAttribute("msg", "请联系管理员，生成工单编号错误："
							+ e.toString());
					return mapping.findForward("failure");

				}
			}
		}
		
		
		FormService formService = (FormService) getBean("formService");
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		TaskService taskService = (TaskService) getBean("taskService");
		
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}
		
		// 流程开始
		if(!"true".equals(isDraft)){
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();
			Map<String, String> map = new HashMap<String, String>();
			String tempKeyValue = "";// 输出key-value用
			map.put("workOrderCheck", taskAssignee);
			tempKeyValue += "workOrderAudit" + ":" + taskAssignee + ";";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try{
				formService.submitTaskFormData(taskId, map);
			}catch(Exception e){
				e.printStackTrace();
			}
			// 流程结束			
		}

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		pnrTransferOffice.setThemeInterface("interface");

		// 工单号
		pnrTransferOffice.setSheetId(sheetId);
		// attachment--start

		// 清空该环节的附件
		if (processInstanceId != null && !"".equals(processInstanceId)) {
			pnrTransferOfficeService
					.deleteAttachmentNamesByProcessInstanceIdAndStep(
							processInstanceId, "1");
		}

		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, "1");
		}

		//场景模板--start
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		//先清除原来存储的场景相关存储数据
		sceneTemplateService.deleteSceneTemplate(processInstanceId, "need", null);
		//保存场景模板数据
		Map<String, Object> result = sceneTemplateService.saveSceneTemplate(request, processInstanceId, "need", null);
		//存储创建和拆除字段
		pnrTransferOffice.setCreateStr(result.get("createStr").toString());
		pnrTransferOffice.setDeleteStr(result.get("deleteStr").toString());
		//场景模板--end
		
		// 多附件框上传
		// String temp1 = request.getParameter("temp1");
		// pnrTransferOfficeService.saveAttachment(processInstanceId,
		// attachments, "1");
		//		
		// String temp2 = request.getParameter("temp1");
		//		

		// attachment--end
		
		/*pnrTransferOffice.setTaskDefKey(taskDefKey);
		pnrTransferOffice.setTaskDefKeyName(taskDefKeyName);*/
		
	/*	Task task = processTaskService.getTaskByProcessInstanceId(processInstanceId, taskAssignee, "");
		String taskId = task.getId();
		pnrTransferOffice.setTaskId(taskId);*/
		processTaskService.setTvalueOfTask(processInstanceId, taskAssignee, pnrTransferOffice, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false);
		
		pnrTransferOfficeService.save(pnrTransferOffice);
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 将工单与图片关联起来,将图片的state状态设置成1
		PnrPrecheckPhotoService pnrPrecheckPhotoService = (PnrPrecheckPhotoService) getBean("pnrPrecheckPhotoService");
		
		//更改众筹流程派发来的草稿表的状态
		if(!"".equals(netResInspectId)){
			INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
			netResInspectService.updateNetResInspectDraft(netResInspectId);
		}else{
			String[] photoesId = photoesIds.split(",");
			if (photoesId != null && photoesId.length > 0) {
				pnrTransferNewPrecheckService
						.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
				for (String str : photoesId) {
					if (str != null && !"".equals(str)) {
						PnrPrecheckPhoto pnrPrecheckPhoto = new PnrPrecheckPhoto();
						pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
						pnrPrecheckPhoto.setPhotoId(str);
						pnrPrecheckPhotoService.save(pnrPrecheckPhoto);
						pnrTransferNewPrecheckService.setStateByPhotoId(str, "1");
					}
				}
			}
		}

		// 发短信
		// 短信接收人
		if(!"true".equals(isDraft)){//如果是保存草稿不用短信提醒
			String messagePerson = pnrTransferOffice.getCountryCSJ();
			String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "," + TASK_WORKORDERDEGREE
					+ workOrderDegree + "," + TASK_WORKORDERTYPE + workOrderType
					+ "," + TASK_SUBTYPE + subType + "。";
			CommonUtils.sendMsg(msg, messagePerson);
		}
		return mapping.findForward("success");
	}

	/**
	 * 预检预修工单--工单保存
	 * 
	 * @author wangyue
	 * @title: performSave
	 * @date Feb 4, 2015 10:38:49 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward performSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		// 默认为0，正常派单。

		int state = 0;

		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		// setTransferOfficeByRequest(request, pnrTransferOffice);
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String initiator = pnrTransferOffice.getInitiator();
		// 获取工单流程类型
		String processType = pnrTransferOffice.getThemeInterface();
		// 是否调用接口标志
		String viewFlag = StaticMethod.nullObject2String(request
				.getParameter("viewFlag"));

		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		String processDefinitionKey = "";
		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			// setTransferOfficeByRequest(request, pnrTransferOffice);//
			// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);

		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, "1");
		}
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);
		// 判断是否调用接口
		if (viewFlag != null && "newJob".equals(viewFlag)) {// 属于新建页面，需要调用预算接口
//			System.out.println("需要调用预算接口！！！！！！！！");
		}
		return mapping.findForward("success");
	}

	/**
	 * 预检预修工单--待回复查询（待办工单除外）
	 * 
	 * @author wangyue
	 * @title: listBacklog
	 * @date Feb 6, 2015 3:28:21 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//int pageSize = CommonUtils.PAGE_SIZE;		
		int pageSize = 50;
		String tempPageSize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

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

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		//String provName = request.getParameter("provName");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");
		String mainSceneId = request.getParameter("mainSceneId");//场景

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		//conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getNewPrecheckCount(userId,
					"backlog", "transferNewPrechechProcess",
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		double sumProjectAmount = 0.0D;//总的项目金额
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getTransferNewPrecheckList(userId, "backlog",
							"transferNewPrechechProcess", conditionQueryModel,
							firstResult, endResult, pageSize);
			
			//用来计算总的项目金额
			List<TaskModel> transferNewPrecheckList = pnrTransferNewPrecheckService.getTransferNewPrecheckList(userId, "backlog","transferNewPrechechProcess", conditionQueryModel,
					firstResult, endResult, -1);
			for(TaskModel m:transferNewPrecheckList){
				sumProjectAmount += m.getProjectAmount();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		String batchApprovalFlag = "no";
//		System.out.println("--------------status=" + status);
		if (status != null) {
			// if (status.equals("workOrderCheck") ||
			// status.equals("cityLineExamine")
			// || status.equals("cityLineDirectorAudit")
			// || status.equals("cityManageExamine")
			// || status.equals("cityManageDirectorAudit")
			// || status.equals("cityViceAudit")
			// || status.equals("provinceLineExamine")
			// || status.equals("provinceLineViceAudit")
			// || status.equals("provinceManageExamine")
			// || status.equals("provinceManageViceAudit")) {
			// batchApprovalFlag="yes";
			// }

			if (status.equals("provinceLineExamine")
					|| status.equals("provinceLineViceAudit")
					|| status.equals("provinceManageExamine")
					|| status.equals("provinceManageViceAudit")) {
				batchApprovalFlag = "yes";
			}

		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		//request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("mainSceneId", mainSceneId);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);
		// 登录人，为了在页面区分是否是省级别的四个工作人员（是否显示批量处理按钮）
		request.setAttribute("loginUserId", userId);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		request.setAttribute("sumProjectAmount", sumProjectAmount);
		return mapping.findForward("backlogList");
	}
	
	//拼接待回复列表查询条件
	private String jointListBacklogCondition(ConditionQueryModel conditionQueryModel){
		String condition="";
		//派单开始时间
		condition+="&sheetAcceptLimit="+StaticMethod.nullObject2String(conditionQueryModel.getSendStartTime());
		//派单结束时间
		condition+="&sheetCompleteLimit="+StaticMethod.nullObject2String(conditionQueryModel.getSendEndTime());
		//工单号
		condition+="&wsNum="+StaticMethod.nullObject2String(conditionQueryModel.getWorkNumber());
		//工单名称
		condition+="&wsTitle="+StaticMethod.nullObject2String(conditionQueryModel.getTheme());
		//地市
		condition+="&region="+StaticMethod.nullObject2String(conditionQueryModel.getCity());
		//区县
		condition+="&country="+StaticMethod.nullObject2String(conditionQueryModel.getCountry());
		//线路级别
		condition+="&lineType="+StaticMethod.nullObject2String(conditionQueryModel.getLineType());
		//场景
		condition+="&mainSceneId="+StaticMethod.nullObject2String(conditionQueryModel.getMainSceneId());
		//工单状态
		condition+="&status="+StaticMethod.nullObject2String(conditionQueryModel.getStatus());
		//工单类别
		condition+="&precheckFlag="+StaticMethod.nullObject2String(conditionQueryModel.getPrecheckFlag());
		//关键字
		//condition+="&keyWord="+StaticMethod.nullObject2String(conditionQueryModel.getKeyWord());
		//紧急程度
		//condition+="&workOrderDegree="+StaticMethod.nullObject2String(conditionQueryModel.getWorkOrderDegree());
		
		//每页显示条数
		if(conditionQueryModel.getPageSize()==null||"".equals(conditionQueryModel.getPageSize())){
			condition+="&pagesize=50";//默认条数50
		}else{
			condition+="&pagesize="+StaticMethod.nullObject2String(conditionQueryModel.getPageSize());
		}
		
//		System.out.println("-------------拼接待回复列表查询条件字符串="+condition);
		
		return condition;
	}
	
	//获取request传递的查询条件封装到ConditionQueryModel中
	private ConditionQueryModel doRequestToConditionQueryModel(HttpServletRequest request){
		//拼接查询条件
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		String status = StaticMethod.nullObject2String(request.getParameter("status"));

		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));
		String lineType = StaticMethod.nullObject2String(request.getParameter("lineType"));
		String precheckFlag = StaticMethod.nullObject2String(request.getParameter("precheckFlag"));
		String mainSceneId = StaticMethod.nullObject2String(request.getParameter("mainSceneId"));//场景
		String pageSize = StaticMethod.nullObject2String(request.getParameter("pagesize"));//每页显示条数

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(pageSize);
		return conditionQueryModel;
	}
	

	/**
	 * 新预检预修工单--回复
	 * 
	 * @author wangyue
	 * @title: todo
	 * @date Feb 9, 2015 9:32:45 AM
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
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));

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
			PnrTransferOffice pnrTransferOffice = list.get(0);
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);

			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			Map<String, Object> gMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> handlelist = null;
			int handleSize = 0;
			search.addSort("checkTime", true);// 按回复时间排序
			gMap = getHandleList(gMap, transferHandleService, search,
					handlelist, handleSize);

			// 获取审批信息
			Map<String, Object> approveMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveHandlelist = null;
			int approveHandleSize = 0;
			Search approveSearch = new Search();
			approveSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveSearch.addFilterEqual("operation", "01");
			approveSearch.addSort("checkTime", true);// 按回复时间排序
			approveMap = getHandleList(approveMap, transferHandleService,
					approveSearch, approveHandlelist, approveHandleSize);
			// 获取审批驳回信息
			Map<String, Object> approveBackMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveBackHandlelist = null;
			int approveBackHandleSize = 0;
			Search approveBackSearch = new Search();
			approveBackSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveBackSearch.addFilterEqual("operation", "02");
			approveBackSearch.addSort("checkTime", true);// 按回复时间排序
			approveBackMap = getHandleList(approveBackMap,
					transferHandleService, approveBackSearch,
					approveBackHandlelist, approveBackHandleSize);
			// 获取专家会审信息
			Map<String, Object> triageMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> triagelist = null;
			int triageSize = 0;
			Search triageSearch = new Search();
			triageSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			triageSearch.addFilterEqual("operation", "03");
			triageSearch.addSort("checkTime", true);// 按回复时间排序
			triageMap = getHandleList(triageMap, transferHandleService,
					triageSearch, triagelist, triageSize);
			// 获取施工队处理信息
			Map<String, Object> transferMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> transferlist = null;
			int transferHandleSize = 0;
			Search transferSearch = new Search();
			transferSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			transferSearch.addFilterEqual("operation", "04");
			transferSearch.addSort("checkTime", true);// 按回复时间排序
			transferMap = getHandleList(transferMap, transferHandleService,
					transferSearch, transferlist, transferHandleSize);
			// 获取审核信息
			Map<String, Object> auditMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> auditlist = null;
			int auditSize = 0;
			Search auditSearch = new Search();
			auditSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			auditSearch.addFilterEqual("operation", "05");
			auditSearch.addFilterEqual("state", "rollback");
			auditSearch.addSort("checkTime", true);// 按回复时间排序
			auditMap = getHandleList(auditMap, transferHandleService,
					auditSearch, auditlist, auditSize);

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();
			String userTaskId = task.getTaskDefinitionKey();
			String step = "";// 环节所在步骤的值
			if (userTaskId.equals("need")) {// 工单发起
				step = "1";
			} else if (userTaskId.equals("workOrderCheck")) {// 工单发起审核
				step = "2";
			} else if (userTaskId.equals("cityLineExamine")) {// 市线路业务主管审核
				step = "3";
			} else if (userTaskId.equals("cityLineDirectorAudit")) {// 市线路主任审核
				step = "4";
			} else if (userTaskId.equals("cityManageExamine")) {// 市运维主管审核
				step = "5";
			} else if (userTaskId.equals("cityManageDirectorAudit")) {// 市运维主任审核
				step = "6";
			} else if (userTaskId.equals("cityViceAudit")) {// 市公司副总审核
				step = "7";
			} else if (userTaskId.equals("provinceLineExamine")) {// 省线路主管审核
				step = "8";
			} else if (userTaskId.equals("provinceLineViceAudit")) {// 省线路总经理审核
				step = "9";
			} else if (userTaskId.equals("usertask11")) {// 专家会审
				step = "1";
			} else if (userTaskId.equals("provinceManageExamine")) {// 省运维主管审核
				step = "10";
			} else if (userTaskId.equals("provinceManageViceAudit")) {// 省运维总经理审批
				step = "11";
			} else if (userTaskId.equals("daiweiAudit")) {// 审核
				step = "14";
			} else if (userTaskId.equals("orderAudit")) {// 抽查
				step = "15";
			}

			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceIdAndUserTaskId(
							processInstanceId, step);
			// String sheetAccessories = pnrTransferOfficeService
			// .getAttachmentNamesByProcessInstanceId(processInstanceId);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain", ticket);
			// String nn = task.getTaskDefinitionKey();
			// attachments-end
			List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService
					.newShowSheetAccessoriesList(processInstanceId);
			request.setAttribute("sheetAccessories", accessoriesList);

			// 判断是否为抓回
			String tempRollbackFlag = request.getParameter("rollbackFlag");
			Map<String, String> tempMap = null;
			if ("2".equals(tempRollbackFlag)) {
				tempMap = new HashMap<String, String>();
				tempMap.put("processInstanceId", processInstanceId);
				tempMap.put("linkName", userTaskId);
			}
			
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			int needNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "need", null);
			if(needNum > 0){
				request.setAttribute("showNeedScene", "yes");//是否显示新建派发时候的场景模板
			}

			if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {// 工单发起
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				// 查询出该工单所关联的图片，回显到页面中
				IPnrTransferOverhaulRemakeService pnrTransferOverhaulRemakeService = (IPnrTransferOverhaulRemakeService) getBean("pnrTransferOverhaulRemakeService");
				String[] photoList = pnrTransferOverhaulRemakeService
						.getPhotoByProcessInstanceId(processInstanceId);
				// 遍历photoID字符串，将图片关联标志state设置成0
				if (photoList[0] != null && !"".equals(photoList[0])) {
					String[] photoesId = photoList[0].split(",");
					if (photoesId != null && photoesId.length > 0) {
						for (String str : photoesId) {
							if (str != null && !"".equals(str)) {
								pnrTransferNewPrecheckService
										.setStateByPhotoId(str, "0");
							}
						}
					}
				}
				// 回显的图片id串
				request.setAttribute("photoIds", photoList[0]);
				// 回显的图片详细信息
				request.setAttribute("photoList", photoList[1]);
				request.setAttribute("mainFaultOccurTime", pnrTransferOffice
						.getSubmitApplicationTime());// 回显申请提交时间
				request.setAttribute("mobilePhone",  pnrTransferOffice
						.getInitiatorMobilePhone());//回显发起人电话

				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));

				//回显子场景
				Map<String, Object> echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "need", null);
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
				//this.echoChildScene(request, processInstanceId);
				
				String findForward = "new";
				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"workOrderCheck")) {// 工单发起审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				return mapping.findForward("workOrderAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityLineExamine")) {// 市线路业务主管审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				return mapping.findForward("cityLineChargeAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityLineDirectorAudit")) {// 市线路主任审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				return mapping.findForward("cityLineDirectorAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityManageExamine")) {// 市运维主管审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}

				if ((pnrTransferOffice == null ? 0 : pnrTransferOffice
						.getState()) == 3) {
					request.setAttribute("directList", "waitWorkOrderList");
				}

				return mapping.findForward("cityManageChargeAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityManageDirectorAudit")) {// 市运维主任审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				return mapping.findForward("cityManageDirectorAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityViceAudit")) {// 市公司副总审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				return mapping.findForward("cityViceAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceLineExamine")) {// 省线路主管审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				return mapping.findForward("provinceLineChargeAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceLineViceAudit")) {// 省线路总经理审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				return mapping.findForward("provinceLineViceAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceManageExamine")) {// 省运维主管审核
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				return mapping.findForward("provinceManageChargeAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"usertask11")) {// 专家会审
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceManageViceAudit")) {// 省运维总经理审批
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				// 显示附件
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				String findForward = "provinceManageViceAudit";

				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey()
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

					request
							.setAttribute("auditor", list.get(0)
									.getCreateWork());

				}
				// 驳回信息
				request.setAttribute("auditListsize", auditMap.get("size"));
				request.setAttribute("auditList", auditMap.get("list"));
				
				//显示新建派发A的子场景模板
				Map<String, Object> echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "need", null);
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
			
				request.setAttribute("showWorkerScene","no");//不显示工单处理环节的场景模板
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"daiweiAudit")) {// 审核-区县传输局
				// 金额限制--start
//				String threeMoneyLimitDicId = precheckRoleModel
//						.getThreeMoneyLimtDicId();
//				String fourMoneyLimitDicId = precheckRoleModel
//						.getFourMoneyLimtDicId();
//				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

//				TawSystemDictType dictypeThree = mgr
//						.getDictByDictId(threeMoneyLimitDicId);
//				TawSystemDictType dictypeFour = mgr
//						.getDictByDictId(fourMoneyLimitDicId);
//				double doubleThree = 0L;
//				double doubleFour = 0L;

//				if (dictypeThree != null
//						&& StaticMethod.getFloatValue(dictypeThree
//								.getDictRemark()) > -1) {
//					doubleThree = Double.parseDouble(dictypeThree
//							.getDictRemark());
//				} else {
//
//					request.setAttribute("msg", "派单审核后-金额设置，有问题，请联系管理员");
//					return mapping.findForward("failure");
//				}
//
//				if (dictypeFour != null
//						&& StaticMethod.getFloatValue(dictypeFour
//								.getDictRemark()) > -1) {
//					doubleFour = Double
//							.parseDouble(dictypeFour.getDictRemark());
//				} else {
//					request.setAttribute("msg", "最后一处-金额限制，有问题；请联系管理员");
//					return mapping.findForward("failure");
//				}
				// 金额限制--end

				search.addFilterEqual("linkName", "worker");
				// gMap = getHandleList(gMap,transferHandleService, search,
				// handlelist,handleSize);

				request.setAttribute("PnrTransferHandleList", gMap.get("list"));
				request.setAttribute("listsize", gMap.get("size"));

				if (list.size() > 0) {
					request
							.setAttribute("auditor", list.get(0)
									.getCreateWork());
				}
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 根据不同等级的项目估算金额获取不同的抽查人员
//				Double projectAmount = pnrTransferOffice.getProjectAmount() == null ? 0.0
//						: pnrTransferOffice.getProjectAmount();
//				String testAudit = "";
//				if (projectAmount == 0.0 || projectAmount < doubleThree) {// 区县维护中心抽查
//					testAudit = pnrTransferOffice.getCreateWork();
//				} else if (projectAmount < doubleFour) {// 市线路维护中心抽查
//					testAudit = pnrTransferOffice.getCityLineCharge();
//				} else {// 市运维部抽查
//					testAudit = pnrTransferOffice.getCityManageCharge();
//				}
//		
	
				String testAudit = pnrTransferOffice.getCityLineCharge();//市线路主管
				if(testAudit == null || "".equals(testAudit)){
					testAudit = "superUser";
				}
				request.setAttribute("testAudit", testAudit);
				// 施工队处理信息
				request.setAttribute("transferListsize", transferMap
						.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));

				// 距离显示
				try {
					String distance = pnrTransferOfficeService.getDistanceShow(
							processInstanceId, "pnrtranfault");
					request.setAttribute("distanceShow", distance);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				int workerNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "worker", null);
				if(workerNum > 0){
					request.setAttribute("showWorkerScene", "yes");//显示工单处理环节的场景模板
				}
				
				//回显施工队的场景模板
				Map<String, Object> echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "worker", null);
			    request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
				
				return mapping.findForward("secondAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"orderAudit")) {// 抽查
				int workerNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "worker", null);
				if(workerNum > 0){
					request.setAttribute("showWorkerScene", "yes");//显示工单处理环节的场景模板
				}
				
				Map<String, Object> echoChildScene = null;
				String flagLinkType="daiweiAudit";
				int daiweiAuditNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "daiweiAudit", null);
				if(daiweiAuditNum > 0){
					request.setAttribute("showDaiweiAuditScene", "yes");//显示区县抽检场景模板
					//显示区县抽检场景模板
					echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "daiweiAudit", null);
				}else{
					//针对老工单没有区县抽检的场景模板数据，默认显示施工队场景模板
					echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "worker", null);
					flagLinkType="worker";
				}
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
				request.setAttribute("flagLinkType", flagLinkType);
				
				return mapping.findForward("orderAudit");
			}
		}

		return mapping.findForward("error");
	}

	/**
	 * 预检预修工单--普通审核通过
	 * 
	 * @author wangyue
	 * @title: transferProgram
	 * @date Feb 9, 2015 10:40:03 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward transferProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BocoLog.info(this,1260,"PnrTransferNewPrecheckAction-transferProgram:进入方法！");

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		PrintWriter out = response.getWriter();
		//处理页面传过来的参数用于判断返回页面
		String pages=request.getParameter("Pages");
		// 发短信
		String processInstanceId = "";
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
		// 审批人填写的审批人-一般所填写的都是中文名称
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		// 工单下一步接收人
		String nextPerson = StaticMethod.nullObject2String(request
				.getParameter("nextPerson"));
		BocoLog.info(this,1289,"PnrTransferNewPrecheckAction-transferProgram-传递的参数nextPerson(下一环节接收人)："+nextPerson);

		String taskId = request.getParameter("taskId");
		// 当前所处环节
		String linkName = request.getParameter("linkName");
		BocoLog.info(this,1294,"PnrTransferNewPrecheckAction-transferProgram-传递的参数linkName(当前环节)："+linkName);

		// 附件
		String attachments = request.getParameter(linkName);

		String step = request.getParameter("step");

		String handleId = request.getParameter("handleId");

		String directList = request.getParameter("directList");// 区分处理完毕后跳转的页面

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = null;
		if (handleId != null && !handleId.equals("")) {
			Search search = new Search();
			search.addFilterEqual("id", handleId);
			SearchResult t = transferHandleService.searchAndCount(search);
			List<PnrTransferOfficeHandle> list = t.getResult();
			entity = list.get(0);
			entity.setHandleDescription(mainRemark);
			entity.setDoPerson(auditor);
		} else {
			entity = new PnrTransferOfficeHandle();
			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setHandleDescription(mainRemark);
			entity.setAuditor(userId);
			entity.setProcessInstanceId(processInstanceId);
			entity.setDoPerson(auditor);
			entity.setLinkName(linkName);
			entity.setOperation("01");
		}
		transferHandleService.save(entity);

		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		

		// 清空该环节的附件
		pnrTransferOfficeService
				.deleteAttachmentNamesByProcessInstanceIdAndStep(
						processInstanceId, step);

		// attachment--start
		if (!"".equals(attachments)) {

			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}
		// attachment--end
//		BocoLog.info(this,1350,"PnrTransferNewPrecheckAction-transferProgram-传递的参数processInstanceId（工单号）："+processInstanceId);
//		BocoLog.info(this,1351,"PnrTransferNewPrecheckAction-transferProgram-传递的参数taskId（当前流程Id）:"+taskId);
//		
//		String msgStr ="<opDetail>"+
//		"<recordInfo>\n" +
//		"<fieldInfo>\n" + 
//		"<fieldChName>taskId工单任务id</fieldChName>\n" + 
//		"<fieldEnName>taskId</fieldEnName>\n" + 
//		"<fieldContent>"+taskId+"</fieldContent>\n" + 
//		"</fieldInfo>\n" + 
//		"</recordInfo>";
//
//		String url ="http://localhost:8080/partner/services/TaskFlowsService?wsdl"; //测试地址
////		String url ="http://134.34.63.6:8085/partner/services/TaskFlowsService?wsdl";

		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name,request.getParameter(name));
//				msgStr+="<recordInfo>\n" +
//				"<fieldInfo>\n" + 
//				"<fieldChName>"+name+"</fieldChName>\n" + 
//				"<fieldEnName>"+name+"</fieldEnName>\n" + 
//				"<fieldContent>"+request.getParameter(name)+"</fieldContent>\n" + 
//				"</fieldInfo>\n" + 
//				"</recordInfo>";
//				BocoLog.info(this,1359,"PnrTransferNewPrecheckAction-transferProgram-传递的参数:流程引擎-"+name+":"+request.getParameter(name));
			}
		}
//		formService.submitTaskFormData(taskId, map);
//		msgStr+="</opDetail>";
//		InterfaceUtil.gkAgencyMethod(url,"submitTaskFormData" , msgStr);
//		BocoLog.info(this,1363,"PnrTransferNewPrecheckAction-transferProgram-流程引擎提交结果:提交完成@！");
		
		//通过webservice调用流程
		this.submitTaskFromWSDL(processInstanceId,taskId, map);
		
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			// 将被处理的工单驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
			pnrTicket.setState(0);
			
		//	Task task = processTaskService.getTaskByProcessInstanceId(processInstanceId, nextPerson, "");
		//	String nextTaskId = task.getId();
			
		//	pnrTicket.setTaskId(nextTaskId);
			
			processTaskService.setTvalueOfTask(processInstanceId, nextPerson, pnrTicket, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTicket);
		}
		
		// 跳转页面 check
		String goPage = "check";
		if ("waitWorkOrderList".equals(directList)) {
			goPage = "waitWorkOrderList";
		}
		request.setAttribute("success", goPage);
		request.setAttribute("condition", request.getParameter("condition"));

		// 发短信
		if (isSend) {
			String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "," + TASK_WORKORDERDEGREE
					+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
					+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName()
					+ "," + TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
			CommonUtils.sendMsg(msg, nextPerson);
		}
		BocoLog.info(this,1398,"PnrTransferNewPrecheckAction-transferProgram-结束方法！");
//		System.out.println("*************************PnrTransferNewPrecheckAction-transferProgram:hello");
		//如果pages有值说明是从待回复列表中的处理按钮页面请求来的.
		if(pages!=null && !"".equals(pages)){
			out.print("true");
			return null;
		}else{	
			return mapping.findForward("showSuccess");
		}
	}

	/**
	 * 预检预修工单--市运维主任审批
	 * 
	 * @author wangyue
	 * @title: cityManageDirectorAudit
	 * @date Feb 9, 2015 5:30:00 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward cityManageDirectorAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		//处理页面传过来的参数用于判断返回页面
		String pages=request.getParameter("Pages");
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
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
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));

		String taskId = request.getParameter("taskId");
		// 当前所处环节
		String linkName = request.getParameter("linkName");
		// 附件
		String attachments = request.getParameter("cityManageDirector");

		String step = request.getParameter("step");

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
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
			// 将驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
		}

		// 清空该环节的附件
		pnrTransferOfficeService
				.deleteAttachmentNamesByProcessInstanceIdAndStep(
						processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {

			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}
		// attachment--end
		// 在此判断金额大小，根据不同的金额走不同的流程
		// 工单发起填写的预算金额
		double projectAmount = 0.0;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
			projectAmount = pnrTicket.getProjectAmount() == null ? 0.0
					: pnrTicket.getProjectAmount();
		}
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String firstMoneyLimitDicId = precheckRoleModel
				.getFirstMoneyLimitDicId();
		String secondMoneyLimitDicId = precheckRoleModel
				.getSecondMoneyLimitDicId();
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

		TawSystemDictType dictypeFirst = mgr
				.getDictByDictId(firstMoneyLimitDicId);
		TawSystemDictType dictypeSecond = mgr
				.getDictByDictId(secondMoneyLimitDicId);
		double doubleFirst = 0L;
		double doubleSecond = 0L;

		if (dictypeFirst != null
				&& StaticMethod.getFloatValue(dictypeFirst.getDictRemark()) > -1) {
			doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
		} else {
			request.setAttribute("msg", "市运维主任审批下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");
		}
		if (dictypeSecond != null
				&& StaticMethod.getFloatValue(dictypeSecond.getDictRemark()) > -1) {
			doubleSecond = Double.parseDouble(dictypeSecond.getDictRemark());
		} else {
			request.setAttribute("msg", "市公司副总下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");
		}
		String nextPerson = "";
		String person="";//在工单主表中添加处理人
		String taskDefKey = null;//在工单主表中添加环节KEY
		String taskDefKeyName = null;//在工单主表中添加环节NAME
		if (projectAmount > doubleFirst) {// 金额大于一千--走市公司副总审核
			map.put("cityManageDirectorState", "city");
			map.put("nextTaskAssignee", pnrTicket.getCityGS());
			nextPerson = pnrTicket.getCityGS();
			person = pnrTicket.getCityGS();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		} else if (projectAmount > doubleSecond) {// 走省线路主管审核
			nextPerson = pnrTicket.getProvinceManageCharge();// 省运维主管
			person =pnrTicket.getProvinceManageCharge();// 省运维主管
			this.autoByPassProvincialLine(processInstanceId, taskId, nextPerson, "cityManageDirectorAudit", themeId, theme, userId);
			pnrTicket.setState(0);
		} else if (projectAmount <= doubleFirst
				&& projectAmount <= doubleSecond) {// 调用商城接口,流程停留在当前环节
			pnrTicket.setState(8);
			isSend = false;
			taskDefKey="waitingCallInterface";
			taskDefKeyName="省公司审批通过-等待商城";
		}
		
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false); 
		pnrTransferOfficeService.save(pnrTicket);
		
		if (isSend) {

			String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "," + TASK_WORKORDERDEGREE
					+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
					+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName()
					+ "," + TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
			CommonUtils.sendMsg(msg, nextPerson);
		}
		request.setAttribute("success", "check");
		request.setAttribute("condition",request.getParameter("condition"));
		//如果pages有值说明是从待回复列表中的处理按钮页面请求来的.
		if(pages!=null && !"".equals(pages)){
			out.print("true");
			return null;
		}else{	
			return mapping.findForward("showSuccess");
		}
		
	}

	/**
	 * 预检预修工单--市公司副总审核
	 * 
	 * @author wangyue
	 * @title: cityViceAudit
	 * @date Feb 9, 2015 5:29:17 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward cityViceAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		//处理页面传过来的参数用于判断返回页面
		String pages=request.getParameter("Pages");
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
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
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));

		String taskId = request.getParameter("taskId");
		// 当前所处环节
		String linkName = request.getParameter("linkName");
		// 附件
		String attachments = request.getParameter(linkName);

		String step = request.getParameter("step");

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
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
			// 将驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
		}

		// 清空该环节的附件
		pnrTransferOfficeService
				.deleteAttachmentNamesByProcessInstanceIdAndStep(
						processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {

			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}
		// attachment--end
		// 工单发起填写的预算金额
		double projectAmount = 0.0;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
			projectAmount = pnrTicket.getProjectAmount() == null ? 0.0
					: pnrTicket.getProjectAmount();
		}
		// 在此判断金额大小，根据不同的金额走不同的流程
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String secondMoneyLimitDicId = precheckRoleModel
				.getSecondMoneyLimitDicId();
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

		TawSystemDictType dictypeSecond = mgr
				.getDictByDictId(secondMoneyLimitDicId);
		double doubleSecond = 0L;

		if (dictypeSecond != null
				&& StaticMethod.getFloatValue(dictypeSecond.getDictRemark()) > -1) {
			doubleSecond = Double.parseDouble(dictypeSecond.getDictRemark());
		} else {
			request.setAttribute("msg", "市公司副总下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");
		}
		
		String person="";//在工单主表中添加处理人
		String taskDefKey = null;//在工单主表中添加环节KEY
		String taskDefKeyName = null;//在工单主表中添加环节NAME
		if (projectAmount > doubleSecond) {// 金额大于一万--走省线路主管审核
			person = pnrTicket.getProvinceManageCharge();// 省运维主管
			this.autoByPassProvincialLine(processInstanceId, taskId, person, "cityViceAudit", themeId, theme, userId);
			pnrTicket.setState(0);
		} else {// 调用工单接口,流程停留在当前环节，并将工单状态state设置成8
			pnrTicket.setState(8);
			isSend = false;
			taskDefKey="waitingCallInterface";
			taskDefKeyName="省公司审批通过-等待商城";
		}
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false);
		pnrTransferOfficeService.save(pnrTicket);
		if (isSend) {
			String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "," + TASK_WORKORDERDEGREE
					+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
					+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName()
					+ "," + TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
			CommonUtils.sendMsg(msg,person);
		}
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		//如果pages有值说明是从待回复列表中的处理按钮页面请求来的.
		if(pages!=null && !"".equals(pages)){
			out.print("true");
			return null;
		}else{	
			return mapping.findForward("showSuccess");
		}
	}

	/**
	 * 预检预修工单--省运维部总经理审批
	 * 
	 * @author wangyue
	 * @title: provinceManageViceAudit
	 * @date Feb 9, 2015 5:28:36 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward provinceManageViceAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		//处理页面传过来的参数用于判断返回页面
		String pages=request.getParameter("Pages");
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
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
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));

		String taskId = request.getParameter("taskId");
		// 当前所处环节
		String linkName = request.getParameter("linkName");
		// 附件
		String attachments = request.getParameter(linkName);

		String step = request.getParameter("step");

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
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
			pnrTicket.setRollbackFlag("0");
		}

		// 清空该环节的附件
		pnrTransferOfficeService
				.deleteAttachmentNamesByProcessInstanceIdAndStep(
						processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {

			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}
		// 调用工单接口,流程停留在当前环节，并将工单状态state设置成8
		pnrTicket.setState(8);
		pnrTicket.setDistributedInterfaceTime(new Date());// 省运维总经理审批通过时间，即派发接口时间
		//在工单主表中添加流程信息
	    processTaskService.setTvalueOfTask(processInstanceId, "", pnrTicket, PnrTransferOffice.class, "waitingCallInterface", "省公司审批通过-等待商城",false);
		pnrTransferOfficeService.save(pnrTicket);
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		//如果pages有值说明是从待回复列表中的处理按钮页面请求来的.
		if(pages!=null && !"".equals(pages)){
			out.print("true");
			return null;
		}else{	
			return mapping.findForward("showSuccess");
		}
	}

	/**
	 * 预检预修工单--待办功能
	 * 
	 * @author wangyue
	 * @title: waitWorkOrder
	 * @date Feb 9, 2015 5:49:55 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward waitWorkOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//封装查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
		}
		// 并将工单状态state设置成3
		pnrTicket.setState(3);
		pnrTransferOfficeService.save(pnrTicket);

		request.setAttribute("success", "check");
		return mapping.findForward("showSuccess");
	}

	/**
	 * 预检预修工单--待办工单查询
	 * 
	 * @author wangyue
	 * @title: waitWorkOrderList
	 * @date Feb 10, 2015 8:32:32 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward waitWorkOrderList(ActionMapping mapping,
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

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		//String status = request.getParameter("status");

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		//String provName = request.getParameter("provName");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");// 预检预修工单类别
		String mainSceneId = request.getParameter("mainSceneId");//场景

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		//conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		//conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getNewPrecheckCount(userId,
					"wait", "transferNewPrechechProcess", conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getTransferNewPrecheckList(userId, "wait",
							"transferNewPrechechProcess", conditionQueryModel,
							firstResult, endResult, pageSize);
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
		//request.setAttribute("wsStatus", status);
		//request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("mainSceneId", mainSceneId);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("waitWorkOrderList");
	}

	/**
	 * 打开驳回页面，给驳回界面传递相关的数据信息
	 * 
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
	 * 预检预修工单--回退
	 * 
	 * @author wangyue
	 * @title: rollback
	 * @date Feb 10, 2015 10:15:50 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward rollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();

		// 短息接收人
		String msgPerson = "";
		// 退回提示信息
		String returnMsg = "该工单已驳回，请重新处理！";

		String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		String returnPerson = request.getParameter("returnPerson");
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");
		// theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");
//		String taskDefKey="";
//		String taskDefKeyName="";
		try {
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();

			String hjflag = "";
			// 当前所处环节
			String linkName = "";
			if (handle.equals(TASK_WORKORDERCHECK)) {// 工单发起审核驳回
				hjflag = "工单发起审核驳回";
				map.put("initiator", returnPerson);
				map.put("workOrderState", "rollback");
				msgPerson = returnPerson;
				linkName = "workOrderCheck";
				
//				taskDefKey="need";
//				taskDefKeyName="工单发起";
			} else if (handle.equals(TASK_CITYLINEEXAMINE)) {// 市线路业务主管驳回
				hjflag = "市线路业务主管驳回";
				map.put("workOrderCheck", returnPerson);
				map.put("cityLineChargeState", "rollback");
				msgPerson = returnPerson;
				linkName = "cityLineExamine";
				
//				taskDefKey="workOrderCheck";
//				taskDefKeyName="工单发起审核";
			} else if (handle.equals(TASK_CITYLINEDIRECTOR)) {// 市线路主任驳回
				hjflag = "市线路主任驳回";
				map.put("cityLineChargeAduit", returnPerson);
				map.put("cityLineDirectorState", "rollback");
				msgPerson = returnPerson;
				linkName = "cityLineDirectorAudit";
				
//				taskDefKey="cityLineExamine";
//				taskDefKeyName="市线路主管审核";
			} else if (handle.equals(TASK_CITYMANAGEEXAMINE)) {// 市运维主管驳回
				hjflag = "市运维主管驳回";
				map.put("cityLineDirectorAudit", returnPerson);
				map.put("cityManageChargeState", "rollback");
				msgPerson = returnPerson;
				linkName = "cityManageExamine";
				
//				taskDefKey="cityLineDirectorAudit";
//				taskDefKeyName="市线路主任审核";
			} else if (handle.equals(TASK_CITYMANAGEDIRECTOR)) {// 市运维主任驳回
				hjflag = "市运维主任驳回";
				map.put("cityManageChargeAudit", returnPerson);
				map.put("cityManageDirectorState", "rollback");
				msgPerson = initiator;
				linkName = "cityManageDirectorAudit";
				
//				taskDefKey="cityManageExamine";
//				taskDefKeyName="市运维主管审核";
			} else if (handle.equals(TASK_CITYVICEAUDIT)) {// 市公司副总驳回
				hjflag = "市公司副总驳回";
				map.put("cityManageDirectorAudit", returnPerson);
				map.put("cityDiveState", "rollback");
				msgPerson = initiator;
				linkName = "cityViceAudit";
				
//				taskDefKey="cityManageDirectorAudit";
//				taskDefKeyName="市运维主任审核";
			} else if (handle.equals(TASK_PROVINCELINEVICE)) {// 省线路总经理驳回
				hjflag = "省线路总经理驳回";
				map.put("nextTaskAssignee", returnPerson);
				map.put("provinceLineViceState", "rollback");
				msgPerson = initiator;
				linkName = "provinceLineViceAudit";
				
//				taskDefKey="provinceLineExamine";
//				taskDefKeyName="省线路主管审核";
			} else if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {// 省运维主管驳回
				hjflag = "省运维主管驳回";
				map.put("provinceLineViceAudit", returnPerson);
				map.put("provinceManageChargeState", "rollback");
				msgPerson = initiator;
				linkName = "provinceManageExamine";
				
//				taskDefKey="provinceLineViceAudit";
//				taskDefKeyName="省线路总经理审核";
			} else if (handle.equals(TASK_PROVINCEMANAGEVICE)) {// 省运维总经理驳回
				hjflag = "省运维总经理驳回";
				map.put("provinceManageCharge", returnPerson);
				map.put("provinceManageViceState", "rollback");
				msgPerson = initiator;
				linkName = "provinceManageViceAudit";
				
//				taskDefKey="provinceManageExamine";
//				taskDefKeyName="省运维主管审核";
			} else if (handle.equals("orderAudit")) {// 抽查
				hjflag = "抽查";
				map.put("initiatorCheck", returnPerson);
				map.put("orderAuditHandleState", "rollback");
				msgPerson = returnPerson;
				linkName = "orderAudit";
				
//				taskDefKey="daiweiAudit";
//				taskDefKeyName="审核";
			}

			String msg = "";
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			double projectAmount = 0.0;
			PnrTransferOffice pnrTransferOffice=null;
			if (pnrTicketList != null) {
				pnrTransferOffice = pnrTicketList.get(0);

				projectAmount = pnrTransferOffice.getProjectAmount() == null ? -1
						: pnrTransferOffice.getProjectAmount();

				msg = TASK_MESSAGE
						+ "  "
						+ TASK_NO_STR
						+ pnrTransferOffice.getProcessInstanceId()
						+ ","
						+ TASK_TITLE_STR
						+ pnrTransferOffice.getTheme()
						+ ","
						+ TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTransferOffice
								.getWorkOrderDegree()) + ","
						+ TASK_WORKORDERTYPE
						+ pnrTransferOffice.getWorkOrderTypeName() + ","
						+ TASK_SUBTYPE + pnrTransferOffice.getSubTypeName()
						+ "。";

				if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {
					// 删除会审辅助表记录的辅助信息
					pnrTransferOfficeService
							.deleteCountersignInfo(processInstanceId);

					pnrTransferOffice.setState(7);// 停止会审
				}

				// 市运维主管（有待办权利）-驳回时，将待办状态回执
				if (handle.equals(TASK_CITYMANAGEEXAMINE)) {
					if (3 == pnrTransferOffice.getState()) {

						pnrTransferOffice.setState(0);// 正常派发时刻
					}
				}
				// 将驳回标志置成1，代表该工单是驳回工单
				pnrTransferOffice.setRollbackFlag("1");
				

			}
			// 由于省线路主管审批特殊，可能回退到市公司副总或者市运维主任
			if (handle.equals(TASK_PROVINCELINEEXAMINE)) {// 省线路主管驳回
				hjflag = "省线路主管驳回";
				String firstMoneyLimitDicId = precheckRoleModel
						.getFirstMoneyLimitDicId();
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

				TawSystemDictType dictypeFirst = mgr
						.getDictByDictId(firstMoneyLimitDicId);
				double doubleFirst = 0L;

				if (dictypeFirst != null
						&& StaticMethod.getFloatValue(dictypeFirst
								.getDictRemark()) > -1) {
					doubleFirst = Double.parseDouble(dictypeFirst
							.getDictRemark());
				} else {
					request.setAttribute("msg", "是否干线处-金额限制，有问题；请联系管理员");
					return mapping.findForward("failure");
				}
				if (projectAmount > doubleFirst) {// 回退到市公司副总
					map.put("nextTaskAssignee", returnPerson);
					map.put("provinceLineChargeState", "rollback");
					
//					taskDefKey="cityViceAudit";
//					taskDefKeyName="市公司副总审核";
				} else {// 回退到市运维主任
					map.put("cityManageDirectorAudit", returnPerson);
					map.put("provinceLineChargeState", "cityManage");
					
//					taskDefKey="cityManageDirectorAudit";
//					taskDefKeyName="市运维主任审核";
				}
				msgPerson = initiator;
				// linkName = "cityManageDirectorAudit";
				linkName = "provinceLineExamine";
			}

			if (!handle.equals(TASK_TRANSFERHANDLE)) {
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				// 将回退原因存入操作表中
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
				if ("orderAudit".equals(handle)) {
					entity.setOperation("09");// 抽查环节驳回 标识值为09
				} else {
					entity.setOperation("02");
				}
				transferHandleService.save(entity);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--start");
			formService.submitTaskFormData(taskId, map);
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--end");
			
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, returnPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTransferOffice);

			// 当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
			CommonUtils.sendMsg(msg, msgPerson);
			out.print("true");

		} catch (Exception e) {
			out.print("false");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 预检预修工单--外包公司派发
	 * 
	 * @author wangyue
	 * @title: mainSecond
	 * @date Feb 13, 2015 9:20:22 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
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
		
		// 时限
		String limit = request.getParameter("sheetCompleteLimit");
		// 工单创建时间
		String createTime = request.getParameter("createTime");
		// 计算结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		try {
			date = sdf.parse(createTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		// 默认时限为2小时
		int timeNum = 2;
		try {
			timeNum = Integer.parseInt(limit);

		} catch (Exception e) {
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
		String tempKeyValue = "";
		map.put("handleWorker", handleWorker);
		map.put("dueDate", sdf.format(date));
		tempKeyValue += "handleWorker" + ":" + handleWorker + ";" + "dueDate"
				+ ":" + date + ";";

		String processInstanceId = request.getParameter("processInstanceId");
		logger.info("当前的操作人：" + initiator + ";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"
				+ processInstanceId + ";当前的taskid:" + taskId + ";当前的map值:{"
				+ tempKeyValue + "};当前时间:" + sdf.format(new Date())
				+ ";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人：" + initiator + ";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"
				+ processInstanceId + ";当前的taskid:" + taskId + ";当前的map值:{"
				+ tempKeyValue + "};当前时间:" + sdf.format(new Date()) + ";--end");

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
		entity.setProcessLimit(Double.parseDouble(limit));

		entity.setEndTime(sdf.parse(sdf.format(date)));
		// 派发人更改
		entity.setInitiator(initiator);
		entity.setRollbackFlag("0");// 未驳回标识
		
		//在工单主表中添加流程信息
    	//processTaskService.setTvalueOfTask(processInstanceId, handleWorker, entity, PnrTransferOffice.class, "worker", "工单处理");
		processTaskService.setTvalueOfTask(processInstanceId, handleWorker, entity, PnrTransferOffice.class, null, null,false);
    	
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime() == null ? "" : sFormat
				.format(entity.getEndTime());

		String msg = TASK_MESSAGE + "  " + TASK_NO_STR
				+ entity.getProcessInstanceId() + "," + TASK_TITLE_STR
				+ entity.getTheme() + "," + TASK_WORKORDERDEGREE
				+ getDictNameByDictid(entity.getWorkOrderDegree()) + ","
				+ TASK_WORKORDERTYPE + entity.getWorkOrderTypeName() + ","
				+ TASK_SUBTYPE + entity.getSubTypeName() + "。";
		CommonUtils.sendMsg(msg, handleWorker);
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");

	}

	/**
	 * 预检预修工单--施工队处理
	 * 
	 * @author wangyue
	 * @title: transferHandle
	 * @date Feb 13, 2015 9:31:13 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward transferHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		String msg = "";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			BocoLog.info("本地网-预检预修流程-施工队回单web端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"单号<"+processInstanceId+">---开始操作；"+new Date());
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOffice.setRollbackFlag("0");// 未驳回标识
			pnrTransferOffice.setWorkerSceneHandleFlag("2");//手机端和web端都处理完成了。

			// 流程提交到下一级
			FormService formService = (FormService) getBean("formService");
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			Map<String, String> map = new HashMap<String, String>();
			String tempKeyValue = "";
			for (FormProperty formProperty : taskFormData.getFormProperties()) {
				if (formProperty.isWritable()) {
					String name = formProperty.getId();
					map.put(name, request.getParameter(name));
					tempKeyValue += name + ":" + request.getParameter(name) + ";";
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("当前的操作人：" + userId + ";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"
					+ processInstanceId + ";当前的taskid:" + taskId + ";当前的map值:{"
					+ tempKeyValue + "};当前时间:" + sdf.format(new Date())
					+ ";--start");
			formService.submitTaskFormData(taskId, map);
			logger.info("当前的操作人：" + userId + ";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"
					+ processInstanceId + ";当前的taskid:" + taskId + ";当前的map值:{"
					+ tempKeyValue + "};当前时间:" + sdf.format(new Date()) + ";--end");
			
			//保存主表信息
			//工单处理环节的乙方费用总额
			String sumCostOfPartyB = StaticMethod.nullObject2String(request.getParameter("sumCostOfPartyB"));
			
			pnrTransferOffice.setSumWorkerCostOfPartyB(Double.parseDouble(sumCostOfPartyB.isEmpty()?"0":sumCostOfPartyB));
			//工单处理环节的项目金额
			String projectAmount = StaticMethod.nullObject2String(request.getParameter("projectAmount"));
			if(!"".equals(projectAmount)){
				pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(projectAmount));
			}
			//工单处理环节的收支比
			String calculateIncomeRatio = StaticMethod.nullObject2String(request.getParameter("incomeRatio"));
			if(!"".equals(calculateIncomeRatio)){
				pnrTransferOffice.setWorkerIncomeRatio(Double.parseDouble(calculateIncomeRatio));
			}
			// 子场景IDs
			String workerChildIds = StaticMethod.nullObject2String(request.getParameter("childSceneSelect"));
			pnrTransferOffice.setWorkerChildIds(workerChildIds);
			//子场景Names		
			String workerChildNames = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
			pnrTransferOffice.setWorkerChildNames(workerChildNames);	
			
			//在工单主表中添加流程信息
//			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, "daiweiAudit", "审核");
			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			//保存流转表中的信息 
			transferHandleService.save(entity);
			
			//保存场景模板
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.saveSceneTemplate(request, processInstanceId, "worker", null);

			//发短信
			msg = TASK_MESSAGE
			+ "  "
			+ TASK_NO_STR
			+ pnrTransferOffice.getProcessInstanceId()
			+ ","
			+ TASK_TITLE_STR
			+ pnrTransferOffice.getTheme()
			+ ","
			+ TASK_WORKORDERDEGREE
			+ getDictNameByDictid(pnrTransferOffice
					.getWorkOrderDegree()) + "," + TASK_WORKORDERTYPE
			+ pnrTransferOffice.getWorkOrderTypeName() + ","
			+ TASK_SUBTYPE + pnrTransferOffice.getSubTypeName() + "。";

			CommonUtils.sendMsg(msg, auditor);
			request.setAttribute("success", "check");
			request.setAttribute("condition", request.getParameter("condition"));
			BocoLog.info("本地网-预检预修流程-施工队回单web端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"单号<"+processInstanceId+">---结束操作；"+new Date());
			return mapping.findForward("showSuccess");
		}
		return mapping.findForward("error");

	}

	/**
	 * 预检预修工单--区县维护中心审核
	 * 
	 * @author wangyue
	 * @title: secondAudit
	 * @date Feb 13, 2015 9:56:19 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
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
		// 当前所处环节
		String linkName = StaticMethod.nullObject2String(request
				.getParameter("linkName"));

		// 决算金额-工费
		String balanceOperatingCosts = request
				.getParameter("balanceOperatingCosts");

		// 决算金额-材料费
		String balanceMaterialsCosts = request
				.getParameter("balanceMaterialsCosts");

		// 附件
		String attachments = request.getParameter("daiweiAudit");

		// 环节步骤值
		String step = request.getParameter("step");

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
		// 决算金额-工费
		if (balanceOperatingCosts != null && !"".equals(balanceOperatingCosts)) {
			entity.setBalanceOperatingCosts(Double
					.parseDouble(balanceOperatingCosts));
		}
		// 决算金额-材料费
		if (balanceMaterialsCosts != null && !"".equals(balanceMaterialsCosts)) {
			entity.setBalanceMaterialsCosts(Double
					.parseDouble(balanceMaterialsCosts));
		}

		transferHandleService.save(entity);
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		processInstanceId = taskList.get(0).getProcessInstanceId();
		search.addFilterEqual("processInstanceId", taskList.get(0)
				.getProcessInstanceId());
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
//		String taskDefKey="";
//		String taskDefKeyName="";
		if (pnrTicketList != null) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}

		if (auditState.equals("rollback")) {
			if (pnrTicket != null) {
//				taskDefKey="worker";
//				taskDefKeyName="工单处理";

				pnrTicket.setLastReplyTime(null);
				pnrTicket.setRollbackFlag("1");// 未驳回标识
				auditor = pnrTicket.getTaskAssignee();// 回退到二次处理人
				pnrTicket.setDaiweiAuditSceneHandleFlag(null);// 驳回时，重置区县抽检场景的状态标识
			}
			isSend = false;

		} else if (auditState.equals("handle")) {
			if (pnrTicket != null) {
//				taskDefKey="orderAudit";
//				taskDefKeyName="抽查";
				
				processInstanceId = pnrTicket.getProcessInstanceId();
				deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
						.format(pnrTicket.getEndTime());
				contact = pnrTicket.getConnectPerson();
				pnrTicket.setRollbackFlag("0");// 未驳回标识
				pnrTicket.setDaiweiAuditPerson(userId);//保存审核处理人
				pnrTicket.setDaiweiAuditSceneHandleFlag("2"); 
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

		String hjflag = "";// 区县维护中心审核 提交 驳回 标识
		if (auditState.equals("handle")) {
			hjflag = "区县传输局审核";
		} else if (auditState.equals("rollback")) {
			hjflag = "区县传输局审核驳回";
		} else {
			hjflag = "区县传输局审核（未知）";
		}
		;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人：" + userId + ";当前的流程:预检预修;当前的操作环节:" + hjflag
				+ ";当前的工单号:" + processInstanceId + ";当前的taskid:" + taskId
				+ ";当前的map值:" + map.toString() + ";当前时间:"
				+ sdf.format(new Date()) + ";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人：" + userId + ";当前的流程:预检预修;当前的操作环节:" + hjflag
				+ ";当前的工单号:" + processInstanceId + ";当前的taskid:" + taskId
				+ ";当前的map值:" + map.toString() + ";当前时间:"
				+ sdf.format(new Date()) + ";--end");

		//在工单主表中添加流程信息
		//processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName);
		processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, null, null,false);
		
		if(auditState.equals("handle")){ //保存场景模板部分
			//保存主表信息
			//区县抽检乙方费用总额
			String sumCostOfPartyB = StaticMethod.nullObject2String(request.getParameter("sumCostOfPartyB"));
			pnrTicket.setDaiweiAuditCostOfPartyB(Double.parseDouble(sumCostOfPartyB.isEmpty()?"0":sumCostOfPartyB));
			
			//区县抽检项目金额
			String projectAmount = StaticMethod.nullObject2String(request.getParameter("projectAmount"));
			if(!"".equals(projectAmount)){
				pnrTicket.setDaiweiAuditProjectAmount(Double.parseDouble(projectAmount));
			}
			//区县抽检收支比
			String calculateIncomeRatio = StaticMethod.nullObject2String(request.getParameter("incomeRatio"));
			if(!"".equals(calculateIncomeRatio)){
				pnrTicket.setDaiweiAuditIncomeRatio(Double.parseDouble(calculateIncomeRatio));
			}
			// 子场景IDs
			String daiweiAuditChildIds = StaticMethod.nullObject2String(request.getParameter("childSceneSelect"));
			pnrTicket.setDaiweiAuditChildIds(daiweiAuditChildIds);
			//子场景Names		
			String daiweiAuditChildNames = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
			pnrTicket.setDaiweiAuditChildNames(daiweiAuditChildNames);	
			
			//保存区县抽检场景模板
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.saveSceneTemplate(request,processInstanceId, "daiweiAudit", null);
			
		}
		pnrTransferOfficeService.save(pnrTicket);
		
		if (auditState.equals("rollback")) {
			//删除区县抽检环节已保存的场景模板数据
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.deleteSceneTemplate(processInstanceId, "daiweiAudit", null);
		}
		// 保存附件
		pnrTransferOfficeService
				.deleteAttachmentNamesByProcessInstanceIdAndStep(
						processInstanceId, step);

		if (attachments != null && !"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}

		request.setAttribute("success", "check");

		// 发短信
		String msg = TASK_MESSAGE + "  " + TASK_NO_STR
				+ pnrTicket.getProcessInstanceId() + "," + TASK_TITLE_STR
				+ pnrTicket.getTheme() + "," + TASK_WORKORDERDEGREE
				+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
				+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName() + ","
				+ TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
		CommonUtils.sendMsg(msg, auditor);
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
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
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();

		// 短息接收人
		String msgPerson = "";
		// 退回提示信息
		String returnMsg = "该工单已驳回，请重新处理！";

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
		theme = new String(theme.getBytes("ISO-8859-1"), "UTF-8");

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();

		String hjflag = "";
		// 当前所处环节
		String linkName = "";
		if (handle.equals(TASK_TRANSFERHANDLE)) {// 施工队回退
			hjflag = "施工队驳回";
			map.put("nextTaskAssignee", initiator);
			map.put("workerHandleState", "rollback");
			msgPerson = initiator;
			returnMsg = "该工单未能处理，已回退，请重新处理！";
			linkName = "worker";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:" + hjflag
				+ ";当前的工单号:" + processInstanceId + ";当前的taskid:" + taskId
				+ ";当前的map值:" + map.toString() + ";当前时间:"
				+ sdf.format(new Date()) + ";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:" + hjflag
				+ ";当前的工单号:" + processInstanceId + ";当前的taskid:" + taskId
				+ ";当前的map值:" + map.toString() + ";当前时间:"
				+ sdf.format(new Date()) + ";--end");

		String msg = "";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setRollbackFlag("1");// 驳回标识
			pnrTransferOffice.setWorkerSceneHandleFlag(null);//工单处理环节的处理标识（主要针对该环节的场景模板）
			
			//在工单主表中添加流程信息
			//processTaskService.setTvalueOfTask(processInstanceId, initiator, pnrTransferOffice, PnrTransferOffice.class, "sendOrder", "工单派发");
			processTaskService.setTvalueOfTask(processInstanceId, initiator, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			//删除工单处理环节已经选择的场景模板数据
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.deleteSceneTemplate(processInstanceId, "worker", null);
			
			msg = TASK_MESSAGE
					+ "  "
					+ TASK_NO_STR
					+ pnrTransferOffice.getProcessInstanceId()
					+ ","
					+ TASK_TITLE_STR
					+ pnrTransferOffice.getTheme()
					+ ","
					+ TASK_WORKORDERDEGREE
					+ getDictNameByDictid(pnrTransferOffice
							.getWorkOrderDegree()) + "," + TASK_WORKORDERTYPE
					+ pnrTransferOffice.getWorkOrderTypeName() + ","
					+ TASK_SUBTYPE + pnrTransferOffice.getSubTypeName() + "。";
		}

		// 当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
		CommonUtils.sendMsg(msg, msgPerson);
		
		request.setAttribute("success", "check");
		return mapping.findForward("showSuccess");
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
		// 承载业务级别
		String bearService = StaticMethod.nullObject2String(request
				.getParameter("bearService"));
		//乙方费用的总额
		String sumNeedCostOfPartyB = StaticMethod.nullObject2String(request
				.getParameter("sumCostOfPartyB"));
		// 项目金额估算
		String projectAmount = StaticMethod.nullObject2String(request
				.getParameter("projectAmount"));
		//实物赔补
		String kindRestitution = StaticMethod.nullObject2String(request.getParameter("kindRestitution"));
		// 线路名称
		String lineName = StaticMethod.nullObject2String(request
				.getParameter("lineName"));
		// 项目起点
		String projectStartPoint = StaticMethod.nullObject2String(request
				.getParameter("projectStartPoint"));
		// 项目终点
		String projectEndPoint = StaticMethod.nullObject2String(request
				.getParameter("projectEndPoint"));
		// 具体地点
		String specificLocation = StaticMethod.nullObject2String(request
				.getParameter("specificLocation"));
		
		/**15-5-27新建派单改造*/
		// 发起人部门
		String initiatorDept = StaticMethod.nullObject2String(request
				.getParameter("initiatorDept"));
		// 发起人电话
		String initiatorMobilePhone = StaticMethod.nullObject2String(request
				.getParameter("initiatorMobilePhone"));
		// 主场景ID---mainSceneSelect
		String mainSceneId = StaticMethod.nullObject2String(request
				.getParameter("mainSceneSelect"));
		// 驳回时展现的子场景ID
		String showChildSceneId = StaticMethod.nullObject2String(request
				.getParameter("childSceneSelect"));
		// 子场景ID串
		String childSceneIds = StaticMethod.nullObject2String(request
				.getParameter("childSceneIds"));
		// 部门负责人	
		String deptHead = StaticMethod.nullObject2String(request
				.getParameter("deptHead"));
		// 部门负责人电话	
		String deptHeadMobilePhone = StaticMethod.nullObject2String(request
				.getParameter("deptHeadMobilePhone"));
		// 收支比	
		String calculateIncomeRatio = StaticMethod.nullObject2String(request
				.getParameter("incomeRatio"));
		// 建设原因及必要性
		String constructionReasons = StaticMethod.nullObject2String(request
				.getParameter("constructionReasons"));
		// 网络现状描述
		String networkStatus = StaticMethod.nullObject2String(request
				.getParameter("networkStatus"));
		// 主要建设内容及规模
		String constructionMainContent = StaticMethod.nullObject2String(request
				.getParameter("constructionMainContent"));
		// 敷设光缆
		String layingCable = StaticMethod.nullObject2String(request
				.getParameter("layingCable"));
		// 开挖揽沟
		String excavationTrench = StaticMethod.nullObject2String(request
				.getParameter("excavationTrench"));
		// 整修管道
		String repairPipeline = StaticMethod.nullObject2String(request
				.getParameter("repairPipeline"));
		// 扶正（拆除）电杆
		String rightingDemolitionPole = StaticMethod.nullObject2String(request
				.getParameter("rightingDemolitionPole"));
		// 敷设钢绞线
		String wireLaying = StaticMethod.nullObject2String(request
				.getParameter("wireLaying"));
		// 光缆接头
		String fiberOpticCableConnector = StaticMethod.nullObject2String(request
				.getParameter("fiberOpticCableConnector"));
		// 其它
		String mainQuantityOther = StaticMethod.nullObject2String(request
				.getParameter("mainQuantityOther"));
		//是否是草稿
		String isDraft =StaticMethod.nullObject2String(request
				.getParameter("isDraft"));
		// 接收人
		String taskAssignee = "";
		// 接收人字符串
		String taskAssigneeJSON = "";
		// 工单ID
		String themeId = request.getParameter("themeId");

		// 未做任何操作--start
		// 经度
		String longitude = StaticMethod.nullObject2String(request
				.getParameter("longitude"));
		// 纬度
		String latitude = StaticMethod.nullObject2String(request
				.getParameter("latitude"));
		// 图片
		String picture = request.getParameter("picture");
		// 工单类别
		String precheckFlag = request.getParameter("precheckFlag");
		//
		String compensate = request.getParameter("compensate");
		// --end

		// 新增附件
		String attachments = request.getParameter("sheetAccessories");
		int attachmentsNum = 0;
		if (attachments != null && attachments.length() > 0) {

			attachmentsNum = attachments.split(",").length;
		}

		String processInstanceId = request.getParameter("processInstanceId");
		String subTypeName = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
		String workOrderTypeName = StaticMethod.nullObject2String(request.getParameter("workOrderTypeName"));
		String netResInspectId = StaticMethod.nullObject2String(request.getParameter("netResInspectId"));
		pnrTransferOffice.setNetResInspectId(netResInspectId);
		pnrTransferOffice.setIsDraft(isDraft);
		pnrTransferOffice.setId(themeId);
		// 主题
		pnrTransferOffice.setTheme(title);
		pnrTransferOffice.setBearService(bearService);
		// 第一派发人
		pnrTransferOffice.setInitiator(initiator);
		pnrTransferOffice.setOneInitiator(initiator);
		pnrTransferOffice.setCreateWork(initiator);
		pnrTransferOffice.setCountryCSJ(initiator);
		// 预检预修提交申请时间
		pnrTransferOffice.setSubmitApplicationTime(sFormat
				.parse(mainFaultOccurTime));
		// 描述
		pnrTransferOffice.setFaultDescription(mainRemark);
		pnrTransferOffice.setTaskAssigneeJSON(taskAssigneeJSON);
		// 工单类型
		pnrTransferOffice.setWorkOrderType(workOrderType);
		pnrTransferOffice.setSubType(showChildSceneId);
		pnrTransferOffice.setSubTypeName(subTypeName);
		pnrTransferOffice.setWorkOrderTypeName(workOrderTypeName);
		// 紧急程度
		pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
		// 关键字
		pnrTransferOffice.setKeyWord(keyWord);
		// 没有确定的人，只是添加--默认superUser
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		PrecheckShipModel precheckShipModel = pnrTransferNewPrecheckService
				.getPrecheckShipModelBycountryCharge(initiator);
		// 区县主任或者市城区班长
		pnrTransferOffice.setSecondCreateWork(precheckShipModel
				.getCountryDirector());
		pnrTransferOffice.setTaskAssignee(precheckShipModel
				.getCountryDirector());
		// 市线路主管
//		pnrTransferOffice.setCityLineCharge(precheckShipModel
//				.getCityLineCharge());
		// 市线路主任
//		pnrTransferOffice.setCityLineDirector(precheckShipModel
//				.getCityLineDirector());
		// 市线路主管
		pnrTransferOffice.setCityLineCharge(precheckShipModel.getCityXcCenterZg());
		// 市线路主任
		pnrTransferOffice.setCityLineDirector(precheckShipModel.getCityXcCenterZr());
		// 市运维主管
		pnrTransferOffice.setCityManageCharge(precheckShipModel
				.getCityManageCharge());
		// 市运维主任
		pnrTransferOffice.setCityManageDirector(precheckShipModel
				.getCityManageDirector());
		// 市公司副总
		pnrTransferOffice.setCityGS(precheckShipModel.getCityCompany());
		// 角色获取
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(
				initiator, precheckRoleModel.getProvinceLineExamine());
		String provinceLineCharge = "superUser";
		if (list != null && list.size() > 0) {
			if (list.get(0).get("USERID") != null
					&& !"".equals(list.get(0).get("USERID"))) {
				provinceLineCharge = list.get(0).get("USERID").toString();
			}
		}
		// 省线路主管
		pnrTransferOffice.setProvinceLineCharge(provinceLineCharge);

		List<Map> provinceLineDirectorList = pnrTransferPrecheckService
				.getSGSByCountryCSJ(initiator, precheckRoleModel
						.getProvinceLineConductor());
		String provinceLineDirector = "superUser";
		if (provinceLineDirectorList != null
				&& provinceLineDirectorList.size() > 0) {
			if (provinceLineDirectorList.get(0).get("USERID") != null
					&& !""
							.equals(provinceLineDirectorList.get(0).get(
									"USERID"))) {
				provinceLineDirector = provinceLineDirectorList.get(0).get(
						"USERID").toString();
			}
		}
		// 省线路总经理
		pnrTransferOffice.setProvinceLine(provinceLineDirector);

		List<Map> provinceManageChargeList = pnrTransferPrecheckService
				.getSGSByCountryCSJ(initiator, precheckRoleModel
						.getProvinceManageExamine());
		String provinceManageCharge = "superUser";
		if (provinceManageChargeList != null
				&& provinceManageChargeList.size() > 0) {
			if (provinceManageChargeList.get(0).get("USERID") != null
					&& !""
							.equals(provinceManageChargeList.get(0).get(
									"USERID"))) {
				provinceManageCharge = provinceManageChargeList.get(0).get(
						"USERID").toString();
			}
		}
		// 省运维主管
		pnrTransferOffice.setProvinceManageCharge(provinceManageCharge);

		List<Map> provinceManageDirectorList = pnrTransferPrecheckService
				.getSGSByCountryCSJ(initiator, precheckRoleModel
						.getProvinceManageConductor());
		String provinceManageDirector = "superUser";
		if (provinceManageDirectorList != null
				&& provinceManageDirectorList.size() > 0) {
			if (provinceManageDirectorList.get(0).get("USERID") != null
					&& !"".equals(provinceManageDirectorList.get(0).get(
							"USERID"))) {
				provinceManageDirector = provinceManageDirectorList.get(0).get(
						"USERID").toString();
			}
		}
		// 省运维总经理
		pnrTransferOffice.setProvinceManage(provinceManageDirector);

		// 版本标志：1 代表新预检预修工单，旧预检预修工单为空
		pnrTransferOffice.setVersionFlag("2");

		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);

		pnrTransferOffice.setWorkType(workType);
		pnrTransferOffice.setSumNeedCostOfPartyB(Double.parseDouble(sumNeedCostOfPartyB.isEmpty()?"0":sumNeedCostOfPartyB));//乙方费用总额
		pnrTransferOffice.setProjectAmount(Double.parseDouble(projectAmount.isEmpty()?"0":projectAmount));
		pnrTransferOffice.setKindRestitution(Double.parseDouble(kindRestitution.isEmpty()?"0":kindRestitution));
		// 线路名称
		pnrTransferOffice.setLineName(lineName);
		// 项目起点
		pnrTransferOffice.setProjectStartPoint(projectStartPoint);
		// 项目终点
		pnrTransferOffice.setProjectEndPoint(projectEndPoint);
		// 具体地点
		pnrTransferOffice.setSpecificLocation(specificLocation);
		pnrTransferOffice.setSendTime(new Date());
		pnrTransferOffice.setRollbackFlag("0");
		pnrTransferOffice.setPrecheckFlag(precheckFlag);
		pnrTransferOffice.setCompensate(Double
				.parseDouble(compensate == "" ? "0" : compensate));
		if("true".equals(isDraft)){
			pnrTransferOffice.setSaveDraftDate(new Date());
		}
		/**15-5-27新建派单改造*/
		//发起人部门
		pnrTransferOffice.setInitiatorDept(initiatorDept);
		//发起人电话
		pnrTransferOffice.setInitiatorMobilePhone(initiatorMobilePhone);
		//主场景ID
		pnrTransferOffice.setMainSceneId(mainSceneId);
		//驳回时展现的子场景ID
		pnrTransferOffice.setShowChildSceneId(showChildSceneId);
		//子场景ID串
		pnrTransferOffice.setChildSceneIds(childSceneIds);
		//部门负责人
		pnrTransferOffice.setDeptHead(deptHead);
		//部门负责人电话
		pnrTransferOffice.setDeptHeadMobilePhone(deptHeadMobilePhone);
		//收支比
		if(!"".equals(calculateIncomeRatio)){
			pnrTransferOffice.setCalculateIncomeRatio(Double.parseDouble(calculateIncomeRatio));
		}
		//建设原因及必要性
		pnrTransferOffice.setConstructionReasons(constructionReasons);
		//网络现状描述
		pnrTransferOffice.setNetworkStatus(networkStatus);
		//主要建设内容及规模
		pnrTransferOffice.setConstructionMainContent(constructionMainContent);
		//敷设光缆
		if(!"".equals(layingCable)){
			pnrTransferOffice.setLayingCable(Double.parseDouble(layingCable));
		}
		//开挖揽沟
		if(!"".equals(excavationTrench)){
			pnrTransferOffice.setExcavationTrench(Double.parseDouble(excavationTrench));
		}
		//整修管道
		if(!"".equals(repairPipeline)){
			pnrTransferOffice.setRepairPipeline(Double.parseDouble(repairPipeline));
		}
		//扶正（拆除）电杆
		if(!"".equals(rightingDemolitionPole)){
			pnrTransferOffice.setRightingDemolitionPole(Double.parseDouble(rightingDemolitionPole));
		}
		//敷设钢绞线
		if(!"".equals(wireLaying)){
			pnrTransferOffice.setWireLaying(Double.parseDouble(wireLaying));
		}
		//光缆接头
		if(!"".equals(fiberOpticCableConnector)){
			pnrTransferOffice.setFiberOpticCableConnector(Double.parseDouble(fiberOpticCableConnector));
		}
		//其他
		pnrTransferOffice.setMainQuantityOther(mainQuantityOther);
		
		//当派单用户为李忠时，流程所有环节的处理人都是李忠。检查作弊用。
		//接口之前
		
		//调接口时insert into pnr_act_transfer_relationship values('200','lizhong','莱芜设备组','lizhong','莱芜设备组','lizhong','莱芜设备组');
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		if("lizhong".equals(userId)){
			// 区县主任或者市城区班长
			pnrTransferOffice.setSecondCreateWork("lizhong");
			pnrTransferOffice.setTaskAssignee("lizhong");
			// 市线路主管
			pnrTransferOffice.setCityLineCharge("lizhong");
			// 市线路主任
			pnrTransferOffice.setCityLineDirector("lizhong");
			// 市运维主管
			pnrTransferOffice.setCityManageCharge("lizhong");
			// 市运维主任
			pnrTransferOffice.setCityManageDirector("lizhong");
			// 市公司副总
			pnrTransferOffice.setCityGS("lizhong");
			// 省线路主管
			pnrTransferOffice.setProvinceLineCharge("lizhong");
			// 省线路总经理
			pnrTransferOffice.setProvinceLine("lizhong");
			// 省运维主管
			pnrTransferOffice.setProvinceManageCharge("lizhong");
			// 省运维总经理
			pnrTransferOffice.setProvinceManage("lizhong");
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

		logger.info("historicTasks开始" + processInstanceId + ";");
		List<HistoricTaskInstance> historicTasks = historyService
				.createHistoricTaskInstanceQuery().processInstanceId(
						processInstanceId)
				.orderByHistoricTaskInstanceStartTime().asc().listPage(
						firstResult * pageSize, pageSize);
		logger.info("historicTasks结束" + processInstanceId + ";");

		logger.info("createHistoricTaskInstanceQuery开始" + processInstanceId
				+ ";");
		totalSize = (int) historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).count();
		logger.info("createHistoricTaskInstanceQuery结束" + processInstanceId
				+ ";");

		logger.info("historicVariableInstances开始" + processInstanceId + ";");
		List<HistoricVariableInstance> historicVariableInstances = historyService
				.createHistoricVariableInstanceQuery().processInstanceId(
						processInstanceId).list();
		logger.info("historicVariableInstances结束" + processInstanceId + ";");

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
	 * 获取回复信息
	 * 
	 * @param transferHandleService
	 * @param search
	 * @param handlelist
	 * @return
	 */
	private Map<String, Object> getHandleList(Map<String, Object> gMap,
			IPnrTransferOfficeHandleService transferHandleService,
			Search search, List<PnrTransferOfficeHandle> handlelist,
			int handleSize) {

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
	 * display attachments
	 */
	public ActionForward showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		pnrTransferOfficeService.showSheetAccessoriesList(mapping, form,
				request, response);

		return mapping.findForward("sheetAccessories");
	}

	/**
	 * 根据紧急程度id，获取紧急程度汉字
	 * 
	 * @param dictId
	 * @return
	 */
	public String getDictNameByDictid(String dictId) {
		String msg = "";
		if ("101230901".equals(dictId)) {
			msg = "特急";
		} else if ("101230902".equals(dictId)) {
			msg = "紧急";

		} else if ("101230903".equals(dictId)) {
			msg = "一般";
		}
		return msg;
	}

	/**
	 * 更改本地网预检预修工单--紧急程度
	 * 
	 * @author wangyue
	 * @title: changeDegree
	 * @date Mar 10, 2015 2:51:04 PM
	 * @return String
	 */
	public String changeDegree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workOrderDegree = request.getParameter("degree");
		String processInstanceId = request.getParameter("processInstanceId");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if (processInstanceId != null && !"".equals(processInstanceId)) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);

			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			response.setCharacterEncoding("utf-8");

			if (list != null && list.size() > 0) {
				// 更改工单紧急程度
				PnrTransferOffice pnrTransferOffice = list.get(0);
				pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
				pnrTransferOfficeService.save(pnrTransferOffice);
				response.getWriter().write("紧急程度修改成功！");
			} else {
				response.getWriter().write("紧急程度修改失败！");

			}
		} else {
			response.getWriter().write("紧急程度修改失败！");
		}
		return "";
	}

	/**
	 * 打开选择图片窗口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openSelectPhotoView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("photoDescribe", "");
		request.setAttribute("createPhotoTime", "");
		return mapping.findForward("openSelectPhotoView");
	}

	/***************************************************************************
	 * 条件查询本地网和干线图片
	 * 
	 * @author wangyue
	 * @title: conditionSelectPhoto
	 * @date Mar 19, 2015 2:36:04 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward conditionSelectPhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
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
		// 图片描述
		String photoDescribe = StaticMethod.nullObject2String(request
				.getParameter("photoDescribe"));
		// 故障地点
		String faultLocation = StaticMethod.nullObject2String(request
				.getParameter("faultLocation"));
		// 拍照时间
		String createPhotoTime = StaticMethod.nullObject2String(request
				.getParameter("createPhotoTime"));
		// 拍照人
		String photoCreate = StaticMethod.nullObject2String(request
				.getParameter("photoCreate"));
		//已选择的照片IDS
		String selectedPhotoIds =StaticMethod.nullObject2String(request.getParameter("selectedPhotoIds"));
		//标签名
		String tagName =StaticMethod.nullObject2String(request.getParameter("tagName"));
		//照片类型
		String photoType = StaticMethod.nullObject2String(request.getParameter("photoType"));
		//照片子类型（是起点照片还是终点照片）
		String photoSubType = StaticMethod.nullObject2String(request.getParameter("photoSubType"));

		//封装查询条件
		Map<String,String> param=new HashMap<String,String>();
		param.put("userId", userId);
		param.put("photoDescribe", photoDescribe);
		param.put("createPhotoTime", createPhotoTime);
		param.put("photoCreate", photoCreate);
		param.put("faultLocation", faultLocation);
		param.put("photoType", photoType);
		param.put("photoSubType", photoSubType);
		
		
		// 根据登录人所述地市查询一定时间内的服务器上的图片
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
//		List<PnrAndroidPhotoFile> photoList = pnrTransferNewPrecheckService
//				.getPrecheckPhotoes(userId, photoDescribe, createPhotoTime,
//						photoCreate,faultLocation);
		
		List<PnrAndroidPhotoFile> photoList = pnrTransferNewPrecheckService.getPrecheckPhotoes(param);
		if (photoList != null) {
			request.setAttribute("total", photoList.size());
		} else {
			request.setAttribute("total", 0);
		}
		request.setAttribute("list", photoList);
		request.setAttribute("photoDescribe", photoDescribe);
		request.setAttribute("createPhotoTime", createPhotoTime);
		request.setAttribute("faultLocation", faultLocation);
		// 拍照人回显
		String showPhotoCreate = photoCreate;
		if (!photoCreate.equals("")) {
			showPhotoCreate = "[{id:'" + photoCreate
					+ "',nodeType:'user',categoryId:'photoCreate'}]";
		}
		request.setAttribute("selectedPhotoIds", selectedPhotoIds);
		request.setAttribute("photoCreate", showPhotoCreate);
		request.setAttribute("tagName", tagName);
		request.setAttribute("photoType", photoType);
		request.setAttribute("photoSubType", photoSubType);
		return mapping.findForward("openSelectPhotoView");
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
//			System.out.println(msg);
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
	 * 处理添加页面选择的图片
	 * 
	 * @author wangyue
	 * @title: selectPhotoTodo
	 * @date Mar 16, 2015 2:54:00 PM
	 * @param mapping
	 * @param form
	 * @param request0
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward selectPhotoTodo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		String photoes = request.getParameter("photoes");
		String[] ids = photoes.split(",");
		String names = "";
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		JSONArray json = new JSONArray();
		// 获取每一张图片的信息
		for (String str : ids) {
			if (!"".equals(str)) {
				IPnrAndroidMgr pnrAndroidMgr = (IPnrAndroidMgr) getBean("ipnrAndroidMgrImpl");
				Search search = new Search();
				search.addFilterEqual("id", str);
				photoList = pnrAndroidMgr.search(search);
				if (photoList != null && photoList.size() > 0) {

					JSONObject jo = new JSONObject();
					jo.put("id", photoList.get(0).getId());
					jo.put("longitude",
							photoList.get(0).getLongitude() == null ? '无'
									: photoList.get(0).getLongitude());
					jo.put("latitude",
							photoList.get(0).getLatitude() == null ? '无'
									: photoList.get(0).getLatitude());
					jo.put("createtime",
							photoList.get(0).getCreateTime() == null ? '无'
									: photoList.get(0).getCreateTime());
					jo.put("userId", photoList.get(0).getUserId() == null ? '无'
							: CommonUtils.getId2NameString(photoList.get(0)
									.getUserId(), 4, ","));
					json.put(jo);
				}
			}
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/***************************************************************************
	 * 工单详情中，新建工单图片查看
	 * 
	 * @author wangyue
	 * @title: showCreateWorkPhoto
	 * @date Mar 19, 2015 4:06:39 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward showCreateWorkPhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 工单号
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("pid"));
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		List<PnrAndroidPhotoFile> photoList = pnrTransferNewPrecheckService
				.showPhotoByProcessInstanceId(processInstanceId);
		request.setAttribute("list", photoList);
		int total = 0;
		if(photoList != null){
			total = photoList.size();
		}
		request.setAttribute("total", total);
		return mapping.findForward("showPhotoView");
	}

	/**
	 * 打开批量处理子窗口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchApproveView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 跳转到处理页面
		String forwardView = "";
		forwardView = "openBatchApproveView";
		return mapping.findForward(forwardView);
	}

	/**
	 * 批量处理-总方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doBatchApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		// 接受参数
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid(); // 当前操作人
		String taskAssignee = StaticMethod.nullObject2String(request
				.getParameter("teskAssignee")); // 审批人
		String dealPerformer = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer")); // 简要描述
		String selectedIDs = request.getParameter("selectedIDs"); // 任务号+流程号+所在环节
		boolean b = false;
		boolean flag = true;
		String returnJson = "";
		String error = "";
		boolean autoByPass = false;//流程自动跳转标识
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		FormService formService = (FormService) getBean("formService");
		// 解析任务号+流程号+所在环节
		try {
			String[] ids = selectedIDs.split("#");
			// 判断是否有工单需要处理
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					List<PnrTransferOffice> pnrTicketList = null;
					PnrTransferOffice pnrTicket = null;
					String[] id = ids[i].split(",");
					String taskId = id[0] == null ? "" : id[0].trim();
					String processInstanceId = id[1] == null ? "" : id[1]
							.trim();
					String taskKey = id[2] == null ? "" : id[2].trim();
					try {
						// 获取该处理工单
						Search search = new Search();
						search.addFilterEqual("processInstanceId",
								processInstanceId);
						pnrTicketList = pnrTransferOfficeService.search(search);
						if (pnrTicketList != null && pnrTicketList.size() > 0) {
							pnrTicket = pnrTicketList.get(0);
							pnrTicket.setState(0);
							// 改变回退工单状态
							pnrTicket.setRollbackFlag("0");
							
							String nextPerson = "";// 流程人员标志名
							String nextAssigness = "";// 流程下一步人员
							String transferHandleFlag = "";// 处理标志名
							String transferHandleFlagValue = "";// 处理标志值
							
							// 解析字符串，获取到所在环节字段，并根据环节获取下一流程人
							if ("provinceManageViceAudit".equals(taskKey)) {
								pnrTicket.setDistributedInterfaceTime(new Date());// 省运维总经理审批通过时间，即派发接口时间
								pnrTicket.setState(8);
								b = true;
								flag = false;
							} else {
								Map<String, String> taskMap = new HashMap<String, String>();
//								String taskDefKey="";
//								String taskDefKeyName="";
								if("workOrderCheck".equals(taskKey)){//工单发起审核
									nextAssigness = pnrTicket.getCityLineCharge();
									nextPerson = "cityLineChargeAduit";
									transferHandleFlag = "workOrderState";
									transferHandleFlagValue = "handle";
									b = true;
//									taskDefKey="cityLineExamine";
//									taskDefKeyName="市线路主管审核";
								}else if ("cityLineExamine".equals(taskKey)) {// 市线路主管审核
									nextAssigness = pnrTicket
											.getCityLineDirector();
									nextPerson = "cityLineDirectorAudit";
									transferHandleFlag = "cityLineChargeState";
									transferHandleFlagValue = "handle";
									b = true;
//									taskDefKey="cityLineDirectorAudit";
//									taskDefKeyName="市线路主任审核";
								} else if ("cityLineDirectorAudit"
										.equals(taskKey)) {// 市线路主任审核
									nextAssigness = pnrTicket
											.getCityManageCharge();
									nextPerson = "cityManageChargeAudit";
									transferHandleFlag = "cityLineDirectorState";
									transferHandleFlagValue = "handle";
									b = true;
//									taskDefKey="cityManageExamine";
//									taskDefKeyName="市运维主管审核";
								} else if ("cityManageExamine".equals(taskKey)) {// 市运维主管审核
									nextAssigness = pnrTicket
											.getCityManageDirector();
									nextPerson = "cityManageDirectorAudit";
									transferHandleFlag = "cityManageChargeState";
									transferHandleFlagValue = "handle";
									b = true;
//									taskDefKey="cityManageDirectorAudit";
//									taskDefKeyName="市运维主任审核";
								} else if ("cityManageDirectorAudit"
										.equals(taskKey)) {// 市运维主任审核
									double projectAmount = pnrTicket
											.getProjectAmount() == null ? 0.0
											: pnrTicket.getProjectAmount();

									String firstMoneyLimitDicId = precheckRoleModel
											.getFirstMoneyLimitDicId();
									String secondMoneyLimitDicId = precheckRoleModel
											.getSecondMoneyLimitDicId();
									TawSystemDictType dictypeFirst = mgr
											.getDictByDictId(firstMoneyLimitDicId);
									TawSystemDictType dictypeSecond = mgr
											.getDictByDictId(secondMoneyLimitDicId);
									double doubleFirst = 0L;
									double doubleSecond = 0L;

									if (dictypeFirst != null
											&& StaticMethod
													.getFloatValue(dictypeFirst
															.getDictRemark()) > -1) {
										doubleFirst = Double
												.parseDouble(dictypeFirst
														.getDictRemark());
									} else {
										error += "工单号:" + processInstanceId
												+ ",市运维主任审批下-金额限制,有问题,请联系管理员;";
										b = false;
										continue;
									}
									if (dictypeSecond != null
											&& StaticMethod
													.getFloatValue(dictypeSecond
															.getDictRemark()) > -1) {
										doubleSecond = Double
												.parseDouble(dictypeSecond
														.getDictRemark());
									} else {
										error += "工单号:" + processInstanceId
												+ ",市公司副总下-金额限制,有问题,请联系管理员;";
										b = false;
										continue;
									}

									if (projectAmount > doubleFirst) {// 金额大于一千--走市公司副总审核
										nextAssigness = pnrTicket.getCityGS();
										nextPerson = "nextTaskAssignee";
										transferHandleFlag = "cityManageDirectorState";
										transferHandleFlagValue = "city";
										b = true;
										
									} else if (projectAmount > doubleSecond) {// 走省线路主管审核
//										nextAssigness = pnrTicket
//												.getProvinceLineCharge();
//										nextPerson = "nextTaskAssignee";
//										transferHandleFlag = "cityManageDirectorState";
//										transferHandleFlagValue = "province";
										
										// 记录处理信息
										PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
										entity.setThemeId(pnrTicket.getId());
										entity.setTheme(pnrTicket.getTheme());
										entity.setCheckTime(new Date());
										entity.setHandleDescription(dealPerformer);
										entity.setAuditor(userId);
										entity.setProcessInstanceId(processInstanceId);
										entity.setDoPerson(taskAssignee);
										entity.setLinkName(taskKey);
										entity.setReturnResult("批量处理");
										entity.setOperation("01");
										transferHandleService.save(entity);
										
										nextAssigness = pnrTicket.getProvinceManageCharge();// 省运维主管
										this.autoByPassProvincialLine(processInstanceId, taskId, nextAssigness, "cityManageDirectorAudit", pnrTicket.getId(), pnrTicket.getTheme(), userId);
										b = true;
										autoByPass = true;
									} else if (projectAmount <= doubleFirst
											&& projectAmount <= doubleSecond) {// 调用商城接口,流程停留在当前环节
										pnrTicket.setState(8);
										b = true;
									}
								} else if ("cityViceAudit".equals(taskKey)) {// 市公司副总审核
									double projectAmount = pnrTicket
											.getProjectAmount() == null ? 0.0
											: pnrTicket.getProjectAmount();
									double doubleSecond = 0L;
									String secondMoneyLimitDicId = precheckRoleModel
											.getSecondMoneyLimitDicId();
									TawSystemDictType dictypeSecond = mgr
											.getDictByDictId(secondMoneyLimitDicId);
									if (dictypeSecond != null
											&& StaticMethod
													.getFloatValue(dictypeSecond
															.getDictRemark()) > -1) {
										doubleSecond = Double
												.parseDouble(dictypeSecond
														.getDictRemark());
									} else {
										error += "工单号:" + processInstanceId
												+ ",市公司副总下-金额限制,有问题,请联系管理员;";
										b = false;
										continue;
									}
									if (projectAmount > doubleSecond) {// 金额大于一万--走省线路主管审核
//										nextAssigness = pnrTicket
//												.getProvinceLineCharge();
//										nextPerson = "nextTaskAssignee";
//										transferHandleFlag = "cityDiveState";
//										transferHandleFlagValue = "handle";
										
										// 记录处理信息
										PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
										entity.setThemeId(pnrTicket.getId());
										entity.setTheme(pnrTicket.getTheme());
										entity.setCheckTime(new Date());
										entity.setHandleDescription(dealPerformer);
										entity.setAuditor(userId);
										entity.setProcessInstanceId(processInstanceId);
										entity.setDoPerson(taskAssignee);
										entity.setLinkName(taskKey);
										entity.setReturnResult("批量处理");
										entity.setOperation("01");
										transferHandleService.save(entity);
										
										nextAssigness = pnrTicket.getProvinceManageCharge();// 省运维主管
										this.autoByPassProvincialLine(processInstanceId, taskId, nextAssigness, "cityViceAudit", pnrTicket.getId(), pnrTicket.getTheme(), userId);
										autoByPass = true;
										b = true;
									} else {// 调用工单接口,流程停留在当前环节，并将工单状态state设置成8
										pnrTicket.setState(8);
										b = true;
										flag = false;
									}
								} else if ("provinceLineExamine"
										.equals(taskKey)) {// 省线路主管
									nextAssigness = pnrTicket.getProvinceLine();
									nextPerson = "provinceLineViceAudit";
									transferHandleFlag = "provinceLineChargeState";
									transferHandleFlagValue = "handle";
									b = true;
								} else if ("provinceLineViceAudit"
										.equals(taskKey)) {// 省线路总经理
									nextAssigness = pnrTicket
											.getProvinceManageCharge();
									nextPerson = "provinceManageCharge";
									transferHandleFlag = "provinceLineViceState";
									transferHandleFlagValue = "handle";
									b = true;
								} else if ("provinceManageExamine"
										.equals(taskKey)) {// 省运维主管
									nextAssigness = pnrTicket
											.getProvinceManage();
									nextPerson = "provinceManageVice";
									transferHandleFlag = "provinceManageChargeState";
									transferHandleFlagValue = "handle";
									b = true;
								}

								if (b&&flag) {
									if(!autoByPass){
										taskMap.put(nextPerson, nextAssigness);
										taskMap.put(transferHandleFlag,
												transferHandleFlagValue);
										formService.submitTaskFormData(taskId,
												taskMap);
									}
								}
							}

							//在工单主表中添加流程信息
							if(flag){
								processTaskService.setTvalueOfTask(processInstanceId, nextAssigness, pnrTicket, PnrTransferOffice.class, null,null,false);
							}else{//提交接口
								processTaskService.setTvalueOfTask(processInstanceId, "", pnrTicket, PnrTransferOffice.class, "waitingCallInterface", "省公司审批通过-等待商城",false);
							}

							if (b) {
								// 保存主表
								pnrTransferOfficeService.save(pnrTicket);
								
								if(!autoByPass){
									// 记录处理信息
									PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
									entity.setThemeId(pnrTicket.getId());
									entity.setTheme(pnrTicket.getTheme());
									entity.setCheckTime(new Date());
									entity.setHandleDescription(dealPerformer);
									entity.setAuditor(userId);
									entity.setProcessInstanceId(processInstanceId);
									entity.setDoPerson(taskAssignee);
									entity.setLinkName(taskKey);
									entity.setReturnResult("批量处理");
									entity.setOperation("01");
									transferHandleService.save(entity);
								}
							}

						} else {
							error += "工单号:" + processInstanceId + ",不存在;";
						}

						b = false;
						flag=true;
					} catch (Exception e) {
						b = false;
						flag=true;
						e.printStackTrace();
						error += "工单号:" + processInstanceId + ",处理异常;";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = "{\"result\":\"error\",\"content\":\"工单ID解析异常\"}";
			out.print(returnJson);
			return null;
		}
		if ("".equals(error)) {
			returnJson = "{\"result\":\"success\",\"content\":\"批量审批成功\"}";

		} else {
			returnJson = "{\"result\":\"error\",\"content\":\"" + error + "\"}";

		}
		out.print(returnJson);
		return null;

	}

	/**
	 * 批量处理-共用
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String communalBatchApprove(List<Map<String, String>> list,
			Map<String, String> paramMap) {
//		System.out.println("--------进到批量处理-共用方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		String wsStatus = paramMap.get("wsStatus");
		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				if (wsStatus.equals("workOrderCheck")) { // 工单发起审核
					taskMap.put("workOrderState", "handle"); // 工单发起审核审核标志
					taskMap.put("cityLineChargeAduit", pnrTicket
							.getCityLineCharge()); // 市线路主管审核人

				} else if (wsStatus.equals("cityLineExamine")) { // 市线路主管审核
					taskMap.put("cityLineChargeState", "handle"); // 市线路主管审核标志
					taskMap.put("cityLineDirectorAudit", pnrTicket
							.getCityLineDirector()); // 市线路主任审批人

				} else if (wsStatus.equals("cityLineDirectorAudit")) { // 市线路主任审核
					taskMap.put("cityLineDirectorState", "handle"); // 市线路主任审核标志
					taskMap.put("cityManageChargeAudit", pnrTicket
							.getCityManageCharge()); // 市运维主管审核人

				} else if (wsStatus.equals("cityManageExamine")) { // 市运维主管审核
					taskMap.put("cityManageChargeState", "handle"); // 标志
					taskMap.put("cityManageDirectorAudit", pnrTicket
							.getCityManageDirector()); // 市运维主任审核

				}

				formService.submitTaskFormData(taskId, taskMap);

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(wsStatus);
				entity.setOperation("01");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-市运维主任审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String cityManageDirectorAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
//		System.out.println("--------进到批量处理-市运维主任审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				// 省线路主管审核
				taskMap.put("provinceLineChargeState", "handle"); // 省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket
						.getProvinceLine()); // 省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-市公司副总审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String cityViceAuditBatchApprove(List<Map<String, String>> list,
			Map<String, String> paramMap) {
//		System.out.println("--------进到批量处理-市公司副总审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				// 省线路主管审核
				taskMap.put("provinceLineChargeState", "handle"); // 省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket
						.getProvinceLine()); // 省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-省线路主管审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String provinceLineExamineBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
//		System.out.println("--------进到批量处理-省线路主管审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				// 省线路主管审核
				taskMap.put("provinceLineChargeState", "handle"); // 省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket
						.getProvinceLine()); // 省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);

				// 发短信
				String msg = TASK_MESSAGE + "  " + TASK_NO_STR
						+ processInstanceId + "," + TASK_TITLE_STR
						+ pnrTicket.getTheme() + "," + TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTicket.getWorkOrderDegree())
						+ "," + TASK_WORKORDERTYPE
						+ pnrTicket.getWorkOrderTypeName() + "," + TASK_SUBTYPE
						+ pnrTicket.getSubTypeName() + "。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceLine());

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-省线路总经理审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String provinceLineViceAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
//		System.out.println("--------进到批量处理-省线路总经理审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				// 省线路总经理审核
				taskMap.put("provinceLineViceState", "handle"); // 省线路总经理审核标志
				taskMap.put("provinceManageCharge", pnrTicket
						.getProvinceManageCharge()); // 省运维主管审核人
				formService.submitTaskFormData(taskId, taskMap);

				// 发短信
				String msg = TASK_MESSAGE + "  " + TASK_NO_STR
						+ processInstanceId + "," + TASK_TITLE_STR
						+ pnrTicket.getTheme() + "," + TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTicket.getWorkOrderDegree())
						+ "," + TASK_WORKORDERTYPE
						+ pnrTicket.getWorkOrderTypeName() + "," + TASK_SUBTYPE
						+ pnrTicket.getSubTypeName() + "。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceManageCharge());

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-省运维主管审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String provinceManageExamineBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				// 提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				// 省运维主管审核
				taskMap.put("provinceManageChargeState", "handle"); // 省运维主管审核标志
				taskMap
						.put("provinceManageVice", pnrTicket
								.getProvinceManage()); // 省运维总经理审批人
				formService.submitTaskFormData(taskId, taskMap);

				// 发短信
				String msg = TASK_MESSAGE + "  " + TASK_NO_STR
						+ processInstanceId + "," + TASK_TITLE_STR
						+ pnrTicket.getTheme() + "," + TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTicket.getWorkOrderDegree())
						+ "," + TASK_WORKORDERTYPE
						+ pnrTicket.getWorkOrderTypeName() + "," + TASK_SUBTYPE
						+ pnrTicket.getSubTypeName() + "。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceManage());

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 批量处理-省运维总经理审批
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	private String provinceManageViceAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				// entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				pnrTicket.setState(8);
				pnrTransferOfficeService.save(pnrTicket);
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 打开批量回退子窗口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchRegressionView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("openBatchRegressionView");
	}

	/**
	 * 批量回退-总方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doBatchRegression(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		// 接受参数
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid(); // 当前操作人
		String rejectReason = StaticMethod.nullObject2String(request
				.getParameter("rejectReason")); // 驳回原因
		String selectedIDs = request.getParameter("selectedIDs"); // 任务号+流程号+当前环节
		boolean b = false;
		String error = ""; // 回复内容
		String returnJson = "";
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");
		// 解析任务号+流程号+所在环节
		try {
			String[] ids = selectedIDs.split("#");
			// 判断是否有工单需要处理
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					List<PnrTransferOffice> pnrTicketList = null;
					PnrTransferOffice pnrTicket = null;
					String[] id = ids[i].split(",");
					String taskId = id[0] == null ? "" : id[0].trim();
					String processInstanceId = id[1] == null ? "" : id[1]
							.trim();
					String taskKey = id[2] == null ? "" : id[2].trim();
					try {
						// 获取该处理工单
						Search search = new Search();
						search.addFilterEqual("processInstanceId",
								processInstanceId);
						pnrTicketList = pnrTransferOfficeService.search(search);
						if (pnrTicketList != null && pnrTicketList.size() > 0) {
							pnrTicket = pnrTicketList.get(0);
							// 改变回退工单状态
							pnrTicket.setRollbackFlag("1");

							Map<String, String> taskMap = new HashMap<String, String>();
							String nextPerson = "";
							String beforeAssigness = "";// 流程上一步人员
							String transferHandleFlag = "";// 回退标志
							String returnFlag = "rollback";
							if("workOrderCheck".equals(taskKey)){//工单发起审核
								nextPerson = "nextTaskAssignee";
								beforeAssigness = pnrTicket.getCreateWork();
								transferHandleFlag = "workOrderState";
								b = true;
							}
							else if ("cityLineExamine".equals(taskKey)) {// 市线路主管审核
								nextPerson = "workOrderCheck";
								beforeAssigness = pnrTicket
										.getSecondCreateWork();
								transferHandleFlag = "cityLineChargeState";
								b = true;
							} else if ("cityLineDirectorAudit".equals(taskKey)) {// 市线路主任审核
								nextPerson = "cityLineChargeAduit";
								beforeAssigness = pnrTicket.getCityLineCharge();
								transferHandleFlag = "cityLineDirectorState";
								b = true;
							} else if ("cityManageExamine".equals(taskKey)) {// 市运维主管审核
								nextPerson = "cityLineDirectorAudit";
								beforeAssigness = pnrTicket
										.getCityLineDirector();
								transferHandleFlag = "cityManageChargeState";
								b = true;
							} else if ("cityManageDirectorAudit"
									.equals(taskKey)) {// 市运维主任审核
								nextPerson = "cityManageChargeAudit";
								beforeAssigness = pnrTicket
										.getCityManageCharge();
								transferHandleFlag = "cityManageDirectorState";
								b = true;
							} else if ("cityViceAudit".equals(taskKey)) {// 市公司副总审核
								nextPerson = "cityManageDirectorAudit";
								beforeAssigness = pnrTicket
										.getCityManageDirector();
								transferHandleFlag = "cityDiveState";
								b = true;
							} else if ("provinceLineExamine".equals(taskKey)) {// 省线路主管
								// 获取限制金额--start
								String firstMoneyLimitDicId = precheckRoleModel
										.getFirstMoneyLimitDicId();
								ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

								TawSystemDictType dictypeFirst = mgr
										.getDictByDictId(firstMoneyLimitDicId);
								double doubleFirst = 0L;
								if (dictypeFirst != null
										&& StaticMethod
												.getFloatValue(dictypeFirst
														.getDictRemark()) > -1) {
									doubleFirst = Double
											.parseDouble(dictypeFirst
													.getDictRemark());
								} else {
									error += "工单号:" + processInstanceId
											+ ",市运维主任审批下-金额限制,有问题,请联系管理员;";
									b = false;
									continue;
								}
								// 获取限制金额--end

								// 工单发起填写的预算金额
								double projectAmount = 0.0;
								projectAmount = pnrTicket.getProjectAmount() == null ? 0.0
										: pnrTicket.getProjectAmount();
								if (projectAmount > doubleFirst) {// 回退到市公司总经理审核
									nextPerson = "nextTaskAssignee";
									beforeAssigness = pnrTicket.getCityGS();
									transferHandleFlag = "provinceLineChargeState";
									b = true;
								} else if (projectAmount <= doubleFirst) {// 回退到市运维审核
									nextPerson = "cityManageDirectorAudit";
									beforeAssigness = pnrTicket
											.getCityManageDirector();
									transferHandleFlag = "provinceLineChargeState";
									returnFlag = "cityManage";
									b = true;
								}
							} else if ("provinceLineViceAudit".equals(taskKey)) {// 省线路总经理
								nextPerson = "nextTaskAssignee";
								beforeAssigness = pnrTicket
										.getProvinceLineCharge();
								transferHandleFlag = "provinceLineViceState";
								b = true;
							} else if ("provinceManageExamine".equals(taskKey)) {// 省运维主管
								nextPerson = "provinceLineViceAudit";
								beforeAssigness = pnrTicket.getProvinceLine();
								transferHandleFlag = "provinceManageChargeState";
								// 删除会审辅助表记录的辅助信息
								pnrTransferOfficeService
										.deleteCountersignInfo(processInstanceId);
								pnrTicket.setState(7);// 停止会审
								b = true;
							} else if ("provinceManageViceAudit"
									.equals(taskKey)) {// 省运维总经理
								nextPerson = "provinceManageCharge";
								beforeAssigness = pnrTicket
										.getProvinceManageCharge();
								transferHandleFlag = "provinceManageViceState";
								b = true;
							}

							if (b) {
								taskMap.put(nextPerson, beforeAssigness);
								taskMap.put(transferHandleFlag, returnFlag);
								formService.submitTaskFormData(taskId, taskMap);

								//在工单主表中添加流程信息
								processTaskService.setTvalueOfTask(processInstanceId, beforeAssigness, pnrTicket, PnrTransferOffice.class, null, null,false);
								
								// 保存主表
								pnrTransferOfficeService.save(pnrTicket);

								// 保存流转信息
								PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
								entity.setThemeId(pnrTicket.getId());
								entity.setTheme(pnrTicket.getTheme());
								Date createTime = new Date();// 审批时间
								entity.setCheckTime(createTime);
								entity.setTaskId(taskId);
								entity.setProcessInstanceId(processInstanceId);
								entity.setAuditor(userId);
								entity.setHandleDescription(rejectReason);
								entity.setLinkName(taskKey);
								entity.setOperation("02");
								transferHandleService.save(entity);
							}

						} else {
							error += "工单号:" + processInstanceId + ",不存在;";
						}

						b = false;
					} catch (Exception e) {
						b = false;
						e.printStackTrace();
						error += "工单号:" + processInstanceId + ",处理异常;";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = "{\"result\":\"error\",\"content\":\"工单ID解析异常\"}";
			out.print(returnJson);
			return null;
		}

		// 给前台返回异常信息
		if (error.equals("")) {
			returnJson = "{\"result\":\"success\",\"content\":\"批量回退成功\"}";
		} else {
			returnJson = "{\"result\":\"error\",\"content\":\"" + error + "\"}";
		}
		out.print(returnJson);
		return null;

	}

	/**
	 * 批量回退-共用
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */

	private String communalBatchRegression(List<Map<String, String>> list,
			Map<String, String> paramMap) {
//		System.out.println("--------进到批量回退-共用方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		FormService formService = (FormService) getBean("formService");

		String wsStatus = paramMap.get("wsStatus");
		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			String tempMsg = "";
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				Map<String, String> taskMap = new HashMap<String, String>();
				String msgPerson = "";
				String linkName = "";
				if (wsStatus.equals("provinceLineExamine")) {// 省线路主管
					// provinceLineExamine
					double projectAmount = 0.0;
					projectAmount = pnrTicket.getProjectAmount() == null ? -1
							: pnrTicket.getProjectAmount();
					String firstMoneyLimitDicId = precheckRoleModel
							.getFirstMoneyLimitDicId();
					TawSystemDictType dictypeFirst = mgr
							.getDictByDictId(firstMoneyLimitDicId);
					double doubleFirst = 0L;
					if (dictypeFirst != null
							&& StaticMethod.getFloatValue(dictypeFirst
									.getDictRemark()) > -1) {
						doubleFirst = Double.parseDouble(dictypeFirst
								.getDictRemark());
					} else {
						tempMsg = "是否干线处-金额限制，有问题；请联系管理员";
						erorrStr += "工单号:" + processInstanceId + ":" + tempMsg
								+ ";";
						continue;
					}
					if (projectAmount > doubleFirst) {// 回退到市公司副总
						taskMap.put("nextTaskAssignee", pnrTicket.getCityGS());
						taskMap.put("provinceLineChargeState", "rollback");
						msgPerson = pnrTicket.getCityGS();
					} else {// 回退到市运维主任
						taskMap.put("cityManageDirectorAudit", pnrTicket
								.getCityManageDirector());
						taskMap.put("provinceLineChargeState", "cityManage");
						msgPerson = pnrTicket.getCityManageDirector();
					}
					linkName = "provinceLineExamine";
				} else if (wsStatus.equals("provinceLineViceAudit")) {// 省线路总经理
					// provinceLineViceAudit
					taskMap.put("nextTaskAssignee", pnrTicket
							.getProvinceLineCharge());
					taskMap.put("provinceLineViceState", "rollback");
					msgPerson = pnrTicket.getProvinceLineCharge();
					linkName = "provinceLineViceAudit";
				} else if (wsStatus.equals("provinceManageExamine")) {// 省运维主管
					// provinceManageExamine
					taskMap.put("provinceLineViceAudit", pnrTicket
							.getProvinceLine());
					taskMap.put("provinceManageChargeState", "rollback");
					msgPerson = pnrTicket.getProvinceLine();
					linkName = "provinceManageExamine";
				} else if (wsStatus.equals("provinceManageViceAudit")) {// 省运维总经理
					// provinceManageViceAudit
					taskMap.put("provinceManageCharge", pnrTicket
							.getProvinceManageCharge());
					taskMap.put("provinceManageViceState", "rollback");
					msgPerson = pnrTicket.getProvinceManageCharge();
					linkName = "provinceManageViceAudit";
				}

				// 调用流程引擎
				formService.submitTaskFormData(taskId, taskMap);

				// 发短信
				String msg = TASK_MESSAGE + "  " + TASK_NO_STR
						+ pnrTicket.getProcessInstanceId() + ","
						+ TASK_TITLE_STR + pnrTicket.getTheme() + ","
						+ TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTicket.getWorkOrderDegree())
						+ "," + TASK_WORKORDERTYPE
						+ pnrTicket.getWorkOrderTypeName() + "," + TASK_SUBTYPE
						+ pnrTicket.getSubTypeName() + "。";

				CommonUtils.sendMsg(msg, msgPerson);

				// 省运维主管环节驳回，停止会审
				if (wsStatus.equals("provinceManageExamine")) {
					// 删除会审辅助表记录的辅助信息
					pnrTransferOfficeService
							.deleteCountersignInfo(processInstanceId);
					pnrTicket.setState(7);// 停止会审
					pnrTransferOfficeService.save(pnrTicket);
				}

				// 保存流转信息
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				Date createTime = new Date();// 审批时间
				entity.setCheckTime(createTime);
				entity.setTaskId(taskId);
				entity.setProcessInstanceId(processInstanceId);
				entity.setAuditor(paramMap.get("userId"));
				entity.setHandleDescription(paramMap.get("rejectReason"));
				entity.setLinkName(linkName);
				entity.setOperation("02");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}

	/**
	 * 已处理的工单
	 * 
	 * @return
	 */
	public ActionForward listInvolvedUnfinishedProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// logger.info("预检预修工单-已处理工单-查询开始");
		int pageSize = CommonUtils.PAGE_SIZE;
		// 手动设置每页显示条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		// HistoryService historyService = (HistoryService)
		// getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");

		// IPnrTransferOfficeService pnrTransferOfficeService =
		// (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		// IPnrTransferPrecheckService pnrTransferPrecheckService =
		// (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 已处理工单条数
		int total = pnrTransferNewPrecheckService.getHaveProcessCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);

		// 已处理工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService
				.getHaveProcessList(processDefinitionKey, userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status,
						firstResult, endResult, pageSize);

		// logger.info("预检预修工单-已处理工单-historicProcessInstancesList开始");
		// List<HistoricProcessInstance> historicProcessInstancesList =
		// historyService
		// .createHistoricProcessInstanceQuery().processDefinitionKey(
		// processDefinitionKey).involvedUser(userId).unfinished()
		// .listPage(firstResult * pageSize, endResult * pageSize);
		//		
		// logger.info("预检预修工单-已处理工单-historicProcessInstancesList结束");
		//		
		// logger.info("预检预修工单-已处理工单-total开始");
		//		
		// long total = historyService.createHistoricProcessInstanceQuery()
		// .processDefinitionKey(processDefinitionKey)
		// .involvedUser(userId).unfinished().count();
		//		
		// //int total =
		// pnrTransferOfficeService.getToreplyJobOfEmergencyJobCount()
		//		
		// logger.info("预检预修工单-已处理工单-total结束");
		//
		//	
		// List<TaskModel> rPnrTicketList = new ArrayList<TaskModel>();
		// for (HistoricProcessInstance historicProcessInstance :
		// historicProcessInstancesList) {
		// String processInstanceId = historicProcessInstance.getId();
		// TaskModel troubleTicketModel = new TaskModel();
		// troubleTicketModel.setProcessInstanceId(historicProcessInstance
		// .getId());
		// Search search = new Search();
		// search.addFilterEqual("processInstanceId", historicProcessInstance
		// .getId());
		//			
		// logger.info("预检预修工单-已处理工单-historicTasks开始");
		// List<HistoricTaskInstance> historicTasks = historyService
		// .createHistoricTaskInstanceQuery().processInstanceId(
		// processInstanceId).orderByTaskId().desc().listPage(
		// 0, 1);
		// logger.info("预检预修工单-已处理工单-historicTasks结束");
		//			
		// if (historicTasks != null & historicTasks.size() > 0) {
		// troubleTicketModel
		// .setStatusName(historicTasks.get(0).getName());
		// }
		// SearchResult t = pnrTransferOfficeService.searchAndCount(search);
		// List<PnrTransferOffice> list = t.getResult();
		// if (list != null && list.size() != 0) {
		// String themeInterface = list.get(0).getThemeInterface();
		// if(themeInterface!=null && "interface".equals(themeInterface)){
		//					
		// troubleTicketModel.setSendTime(list.get(0).getSubmitApplicationTime());
		// troubleTicketModel.setTheme(list.get(0).getTheme());
		// troubleTicketModel.setInitiator(list.get(0).getInitiator());
		// int state = list.get(0).getState();
		// if(state==8){
		// troubleTicketModel.setStatusName("市公司审批完毕");
		// }
		// TawSystemUser pu = CommonUtils.getTawSystemUserByUserId(list
		// .get(0).getTaskAssignee(), "");
		// if (pu != null) {
		// troubleTicketModel.setDeptId(pu.getDeptid());
		//						
		// }
		// rPnrTicketList.add(troubleTicketModel);
		// }
		// }
		// }
		//		
		// logger.info("预检预修工单-已处理工单-查询结束");
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
//		System.out.println("------------------工单名称wsTitle=" + wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedProcessInstancesList");
	}

	/**
	 * 详细信息呈现
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		System.out.println("##############详细信息呈现");

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

		List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService
				.newShowSheetAccessoriesList(processId);
		
		//场景模板的显示
		//场景模板--start
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		int needNum = sceneTemplateService.countSceneTemplateNum(processId, "need", null);
		if(needNum > 0){
			request.setAttribute("showNeedScene", "yes");
		}

		int workerNum = sceneTemplateService.countSceneTemplateNum(processId, "worker", null);
		if(workerNum > 0){
			request.setAttribute("showWorkerScene", "yes");
		}
		
		int orderAuditNum = sceneTemplateService.countSceneTemplateNum(processId, "orderAudit", null);
		if(orderAuditNum > 0){
			request.setAttribute("showOrderAuditScene", "yes");
		}
		
		String samplingFlag = StaticMethod.nullObject2String(request.getParameter("samplingFlag")); //省公司抽检标识 1代表省公司抽检
		String condition = StaticMethod.nullObject2String(request.getParameter("condition")); //省公司抽检查询条件
		request.setAttribute("samplingFlag",samplingFlag);
		request.setAttribute("condition", condition);
		
		request.setAttribute("sheetAccessories", accessoriesList);
		request.setAttribute("pnrTransferOffice", list.get(0));
		request.setAttribute("PnrTransferHandleList", handlelist);
		request.setAttribute("listsize", handlelist.size());
		request.setAttribute("processInstanceId", processId);

		return mapping.findForward("gotoDetail");

	}

	/**
	 * 删除流程实例
	 */
	public ActionForward deleteProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		System.out.println("##############详细信息呈现");

		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		String deleteReason = "垃圾数据";
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);

		return mapping.findForward("showSuccess");
	}

	/**
	 * 由我创建的工单查询
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
		// HistoryService historyService = (HistoryService)
		// getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 由我创建的工单条数
		int total = pnrTransferNewPrecheckService.getByCreatingWorkOrderCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);

		// 由我创建的工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService
				.getByCreatingWorkOrderList(processDefinitionKey, userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status,
						firstResult, endResult, pageSize);

		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("runningProcessInstancesList");
	}

	/**
	 * 按照流程实例ID删除流程
	 */
	public ActionForward removeProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");

		try {
			runtimeService.deleteProcessInstance(processInstanceId, "delete");
		} catch (Exception e) {
			e.printStackTrace();
		}

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTransferOffice> tResult = pnrTransferOfficeService
				.searchAndCount(search);
		List<PnrTransferOffice> eList = tResult.getResult();
		if (eList != null && eList.size() > 0) {
			PnrTransferOffice entity = eList.get(0);
			entity.setState(TASK_DELETE_STATE);
			//在工单主表中添加流程信息
		    processTaskService.setTvalueOfTask(processInstanceId, "", entity, PnrTransferOffice.class, "delete", "已删除",true);	
			pnrTransferOfficeService.save(entity);
		}

		return mapping.findForward("delete_success");
	}

	/**
	 * 工单抓回 查询
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
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		//String userTaskFlag = request.getParameter("userTaskFlag");

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getBackSheetCount(
					processDefinitionKey, userId, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService.getBackSheetList(
					processDefinitionKey, userId, conditionQueryModel,
					firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("getBack");
	}

	/**
	 * 抓回工单
	 * 
	 * @return
	 */
	public ActionForward catchBackWorkOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
		String processInstanceId = request.getParameter("processInstanceId");
		String taskId = request.getParameter("taskId");
		String taskDefKey = request.getParameter("taskDefKey");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice = null;
		if (pnrTicketList != null) {
			pnrTransferOffice = pnrTicketList.get(0);
		}

		String person="";//工单主表中添加环节人
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if (taskDefKey.equals("workOrderCheck")) {// 工单发起审核
			map.put("initiator", pnrTransferOffice.getCreateWork());
			map.put("workOrderState", "rollback");
			person = pnrTransferOffice.getCreateWork();
		} else if (taskDefKey.equals("cityLineExamine")) {// 市线路主管审核
			map.put("workOrderCheck", pnrTransferOffice.getSecondCreateWork());
			map.put("cityLineChargeState", "rollback");
			person = pnrTransferOffice.getSecondCreateWork();
		} else if (taskDefKey.equals("cityLineDirectorAudit")) {// 市线路主任审核
			map.put("cityLineChargeAduit", pnrTransferOffice
					.getCityLineCharge());
			map.put("cityLineDirectorState", "rollback");
			person = pnrTransferOffice.getCityLineCharge();
		} else if (taskDefKey.equals("cityManageExamine")) {// 市运维主管审核
			map.put("cityLineDirectorAudit", pnrTransferOffice
					.getCityLineDirector());
			map.put("cityManageChargeState", "rollback");
			person = pnrTransferOffice.getCityLineDirector();
		} else if (taskDefKey.equals("cityManageDirectorAudit")) {// 市运维主任审核
			map.put("cityManageChargeAudit", pnrTransferOffice
					.getCityManageCharge());
			map.put("cityManageDirectorState", "rollback");
			person = pnrTransferOffice.getCityManageCharge();
		}
		formService.submitTaskFormData(taskId, map);

		// 2代表抓回
		pnrTransferOffice.setRollbackFlag("2");
		
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId,person, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTransferOffice);

		return mapping.findForward("catchBackSuccess");
	}

	/**
	 * 已归档工单
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
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		// String status = request.getParameter("status");
		String status = "archived";
		String themeInterface = "interface";

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 已归档工单条数
		int total = pnrTransferNewPrecheckService.getArchivedCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status, themeInterface);

		// 已归档工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService
				.getArchivedList(processDefinitionKey, userId, sendStartTime,
						sendEndTime, wsNum, wsTitle, status, themeInterface,
						firstResult, endResult, pageSize);

		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedFinishedList");
	}

	/**
	 * 预检预修工单--归档操作
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
		String currentUserId = sessionForm.getUserid();

		String taskId = request.getParameter("taskId");
		String processInstanceId = request.getParameter("processInstanceId");
		String isSpotCheck = StaticMethod.nullObject2String(request
				.getParameter("isSpotCheck"));

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
			pnrTransferOffice.setWorkerSceneOrderAuditFlag("2");
			pnrTransferOffice.setOrderAuditPerson(currentUserId);
			
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
			
			//if ("10".equals(isSpotCheck)) {
			//抽查环节的乙方费用总额
			String sumCostOfPartyB = StaticMethod.nullObject2String(request.getParameter("sumCostOfPartyB"));
			pnrTransferOffice.setSumOrderAuditCostOfPartyB(Double.parseDouble(sumCostOfPartyB.isEmpty()?"0":sumCostOfPartyB));
			//抽查环节的项目金额
			String projectAmount = StaticMethod.nullObject2String(request.getParameter("projectAmount"));
			if(!"".equals(projectAmount)){
				pnrTransferOffice.setOrderauditProjectAmount(Double.parseDouble(projectAmount));
			}
			//抽查环节环节的收支比
			String calculateIncomeRatio = StaticMethod.nullObject2String(request.getParameter("incomeRatio"));
			if(!"".equals(calculateIncomeRatio)){
				pnrTransferOffice.setOrderauditIncomeRatio(Double.parseDouble(calculateIncomeRatio));
			}
			// 子场景IDs
			String orderauditChildIds = StaticMethod.nullObject2String(request.getParameter("childSceneSelect"));
			pnrTransferOffice.setOrderauditChildIds(orderauditChildIds);
			//子场景Names		
			String orderauditChildNames = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
			pnrTransferOffice.setOrderauditChildNames(orderauditChildNames);
			//} 
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, "", pnrTransferOffice, PnrTransferOffice.class, "archive", "已归档",true);
			pnrTransferOfficeService.save(pnrTransferOffice);

			// 审减金额-工费
//			String shenJianOperatingCosts = request.getParameter("shenJianOperatingCosts");
//			// 审减金额-材料费
//			String shenJianMaterialsCosts = request.getParameter("shenJianMaterialsCosts");
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
//			if (shenJianOperatingCosts != null
//					&& !"".equals(shenJianOperatingCosts)) {
//				entity.setShenJianOperatingCosts(Double
//						.parseDouble(shenJianOperatingCosts));
//
//			}
//			if (shenJianMaterialsCosts != null
//					&& !"".equals(shenJianMaterialsCosts)) {
//				entity.setShenJianMaterialsCosts(Double
//						.parseDouble(shenJianMaterialsCosts));
//
//			}
			if ("10".equals(isSpotCheck)) {
				entity.setHandleDescription("手机端确认抽查，WEB端回单归档");
				entity.setOperation("10");
			} else {
				entity.setHandleDescription("手机端没有提交抽查，WEB端直接归档");
				entity.setOperation("11");
			}
			//保存流转表中的信息
			transferHandleService.save(entity);

			//if ("10".equals(isSpotCheck)) {
			//保存场景模板
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.saveSceneTemplate(request, processInstanceId, "orderAudit", null);
			//}

			// 保存附件
			String attachments = request.getParameter("orderAudit");
			String step = request.getParameter("step");
			if (attachments != null && !"".equals(attachments)) {
				pnrTransferOfficeService.saveAttachment(processInstanceId,
						attachments, step);
			}
			
			request.setAttribute("success", "check");
			request.setAttribute("condition", request.getParameter("condition"));
			
			try{
				//将巡检众筹流程归档
				String netResInspectId=pnrTransferOffice.getNetResInspectId();
				if(netResInspectId != null && !"".equals(netResInspectId)){
					INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
					netResInspectService.workOrderArchiving(netResInspectId, userId);
				}
			}catch(Exception e){
				e.printStackTrace();
				return mapping.findForward("error");
			}
			
			return mapping.findForward("showSuccess");

		}
		return mapping.findForward("error");
	}

	/**
	 * 下载工单附件
	 * 
	 * @author wyl
	 * @title: downAttachment
	 * @date 20150429
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward downAttachMent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += themeinterfaces[i] + ",";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += taskdefkeys[i] + ",";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setCity(region);// 地市
		conditionQueryModel.setCountry(country);// 区县

		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节

		// 获取地市列表
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 待下载附件 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getDownAttachMentCount(
					userId, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 待下载附件 集合
		List<DownAttachMentModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getDownAttachMentList(userId, conditionQueryModel,
							firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 回传数据 供界面使用
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("startTime", sendStartTime);// 开始时间
		request.setAttribute("endTime", sendEndTime);// 结束时间
		request.setAttribute("region", region);// 地市
		request.setAttribute("country", country);// 区县
		request.setAttribute("themeinterface", themeinterface);// 工单类型
		request.setAttribute("taskdefkey", taskdefkey);// 所属环节

		return mapping.findForward("downAttachMentList");
	}

	/**
	 * 下载工单附件
	 * 
	 * @author wyl
	 * @title: downAttachMentToZip
	 * @date 20150429
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward downAttachMentToZip(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县

		String themeinterface = request.getParameter("themeinterface");// 工单类型
		String taskdefkey = request.getParameter("taskdefkey");// 所属环节

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setCity(region);// 地市
		conditionQueryModel.setCountry(country);// 区县

		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// =================附件下载人数限制=================
		if (!pnrTransferNewPrecheckService.IsEnableDownAttachMent(userId)) {
			// 提示下载人数超限
			request.setAttribute("downlimiterror", "同时下载人数超限，请稍后再试！");

			// 获取地市列表
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			List city = PartnerCityByUser.getCityByProvince(province);
			request.setAttribute("city", city);

			// IPnrTransferNewPrecheckService pnrTransferNewPrecheckService =
			// (IPnrTransferNewPrecheckService)
			// getBean("pnrTransferNewPrecheckService");
			// 待下载附件 集合条数
			int total = 0;
			try {
				total = pnrTransferNewPrecheckService.getDownAttachMentCount(
						userId, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 待下载附件 集合
			List<DownAttachMentModel> rPnrTransferList = null;
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getDownAttachMentList(userId, conditionQueryModel,
								firstResult, endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 回传数据 供界面使用
			request.setAttribute("taskList", rPnrTransferList);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", total);

			request.setAttribute("startTime", sendStartTime);// 开始时间
			request.setAttribute("endTime", sendEndTime);// 结束时间
			request.setAttribute("region", region);// 地市
			request.setAttribute("country", country);// 区县
			request.setAttribute("themeinterface", themeinterface);// 工单类型
			request.setAttribute("taskdefkey", taskdefkey);// 所属环节

			return mapping.findForward("downAttachMentList");

		}
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String serial = f.format(date);
		java.util.Random r = new java.util.Random(100);
		int rdm = r.nextInt(100);

		String downInfoId = userId + "-" + serial + "-" + rdm;

		try {
			pnrTransferNewPrecheckService.addDownAttachMentInfo(downInfoId,
					userId);
			// =============================================================================

			// 待下载附件 集合
			List<DownAttachMentModel> rPnrTransferList = null;
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getDownAttachMentListAll(userId, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ==============================================================================
			String zipurl = "";
			// 附件打包 下载
			try {
				// ITawCommonsAccessoriesManager mgr =
				// (ITawCommonsAccessoriesManager)
				// getBean("ItawCommonsAccessoriesManager");
				// TawCommonsAccessories accessories = null;

				// String path =
				// AccessoriesMgrLocator.getTawCommonsAccessoriesManagerCOS().getFilePath(accessories.getAppCode());
				String uploadpath = AccessoriesMgrLocator
						.getAccessoriesAttributes().getUploadPath();

				zipurl = MyZipUtil(rPnrTransferList, uploadpath, userId);// 调用本类内方法
				String[] zipName = zipurl.split("/");
				String name = zipName[zipName.length - 1];

				InputStream inStream = new FileInputStream(zipurl);// 文件的存放路径
				// 设置输出的格式
				response.reset();
				response.setContentType("application/x-msdownload;charset=GBK");
				response.setCharacterEncoding("UTF-8");

				response.setHeader("Content-Disposition",
						"attachment; filename="
								+ new String(name.getBytes("UTF-8"), "GBK"));

				byte[] b = new byte[128];
				int len;
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 删除临时文件
				File file = new File(zipurl);
				// file.deleteOnExit();
				deleteAll(file);
			}
		} catch (Exception e) {

		} finally {
			pnrTransferNewPrecheckService.deleteDownAttachMentInfo(downInfoId);
		}

		return null;
	}

	// 将传进来的文件剪切到制定目录下
	public String MyZipUtil(List<DownAttachMentModel> rPnrTransferList,
			String filePath, String userId) throws Exception {

		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = f.format(date);
		String path = filePath + result + "ZIP" + "-" + userId;
		File dirName = new File(path);

		try {

			if (!dirName.exists()) {
				dirName.mkdir();// 新建一个文件夹
			}

			for (int i = 0; i < rPnrTransferList.size(); i++) {

				String sheetId = rPnrTransferList.get(i).getProcessInstanceId();// 工单号
				String cnName = rPnrTransferList.get(i).getAccessoriescnname();// 附件中文名
				String filename = rPnrTransferList.get(i).getAccessoriesname();// 英文名称
				String city = rPnrTransferList.get(i).getCity();// 城市
				String country = rPnrTransferList.get(i).getCountry();// 区县

				String fileStr = cnName.substring(0, cnName.lastIndexOf("."));
				String newpath = filePath + "/" + sheetId + "-" + city + "-"
						+ country + "-" + fileStr + "-" + filename;

				String fullpath = AccessoriesMgrLocator
						.getTawCommonsAccessoriesManagerCOS().getFilePath(
								rPnrTransferList.get(i).getAppcode());

				File linshi = new File(fullpath);
				if (!linshi.exists()) {
					linshi.mkdir();
				}

				String fullName = fullpath + filename;
				File myfile = new File(fullName);
				if (!myfile.exists()) {
					continue;
				}

				FileInputStream fis = new FileInputStream(fullName);
				FileOutputStream fos = new FileOutputStream(newpath);
				byte[] buff = new byte[1024];
				int readed = -1;
				while ((readed = fis.read(buff)) > 0)
					fos.write(buff, 0, readed);
				fis.close();
				fos.close();

				File subfile = new File(newpath);
				int index = newpath.lastIndexOf("/");
				String newfilename = newpath.substring(index);

				File newfile = new File(path + newfilename);
				subfile.renameTo(newfile);
			}

			CompressBook book = new CompressBook();
			// 循环剪切
			String zipfile = path + ".zip";// 给指定生成ZIP文件
			book.zip(zipfile, new File(path));

			return zipfile;

		} catch (Exception e) {
			return "";
		} finally {
			// 删除临时文件夹 path
			// if (dirName.exists()) {
			// dirName.delete();// 删除临时文件夹
			// }
			deleteAll(dirName);
		}

	}

	/**
	 * 删除对象f下的所有文件和文件夹
	 * 
	 * @param f
	 *            文件路径
	 */
	public void deleteAll(File f) {
		// 文件
		if (f.isFile()) {
			f.delete();
		} else { // 文件夹
			// 获得当前文件夹下的所有子文件和子文件夹
			File f1[] = f.listFiles();
			// 循环处理每个对象
			int len = f1.length;
			for (int i = 0; i < len; i++) {
				// 递归调用，处理每个文件对象
				deleteAll(f1[i]);
			}
			// 删除当前文件夹
			f.delete();
		}
	}

	/**
	 * 事情照片删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePictureById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		String returnJson = "";
		try {
			pnrTransferNewPrecheckService.deletePictureById(id);
			returnJson = "{\"result\":\"success\",\"content\":\"照片删除成功\"}";
		} catch (Exception e) {
			returnJson = "{\"result\":\"error\",\"content\":\"照片删除失败\"}";
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}

	/**
	 * 工单下载
	 * 
	 * @author wangchang
	 * @title: downAttachment
	 * @date 20150507
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward downWorkOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		//获取用户地市id
		String areaid =com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		
		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setCity(region);// 地市
		conditionQueryModel.setCountry(country);// 区县

		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节
		conditionQueryModel.setArea(areaid);// 当前登陆人地市id

		// 获取地市列表
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 待下载附件 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getDownWorkOrderCount(userId,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 待下载附件 集合
		List<DownWorkOrderModel> rPnrTransferList = null;
		try {
			// 查询订单信息
			rPnrTransferList = pnrTransferNewPrecheckService
					.getDownWorkOrderList(userId, conditionQueryModel,
							firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 回传数据 供界面使用
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		
		request.setAttribute("startTime", sendStartTime);// 开始时间
		request.setAttribute("endTime", sendEndTime);// 结束时间
		request.setAttribute("region", region);// 地市
		request.setAttribute("country", country);// 区县
		request.setAttribute("themeinterface", themeinterface);// 工单类型
		request.setAttribute("taskdefkey", taskdefkey);// 所属环节
//		System.out.println("==============================");
		return mapping.findForward("downWorkOrderList");
	}

	/**
	 * 导出excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downInfoAlls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int curPage = 1; // 当前页数
		int pageNum = 0; // 总页数
		int count = 0; // 总记录数
		int pageSize = 10; // 每页显示10条

		// 获取service
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节
		// 调取service 里的业务方法
		try {
			// 查询工单总数
			count = pnrTransferNewPrecheckService.getDownWorkOrderCount(userId,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageNum = count % pageSize == 0 ? count / pageSize : count / pageSize
				+ 1;
		// 生成文件
		// 将无线参数列表数据 导出到 excel 中 存储在 ftp的服务器上
		// ExportExcel 中需要四个参数 HashMap fieldName sheetNameList output
		String sheetName = "工单列表数据";
		String uploadpath = AccessoriesMgrLocator.getAccessoriesAttributes()
				.getUploadPath();
		String ftpurl = "d:/test.xls";
		HashMap result = new HashMap();
		List sheetNameList = new ArrayList();
		sheetNameList.add(sheetName);
		String[] fieldName = { "工单id", "工单标题" };

		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String serial = f.format(date);
		java.util.Random r = new java.util.Random(100);
		int rdm = r.nextInt(100);
		String downInfoId = userId + "-" + serial + "-" + rdm;
		pnrTransferNewPrecheckService.addDownAttachMentInfo(downInfoId, userId);
		// =============================================================================

		// 待下载附件 集合
		List rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getDownWorkOrderListAll(userId, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		result.put(sheetName, rPnrTransferList);
		OutputStream output = new FileOutputStream(ftpurl);
		// ExportExcel(HashMap result, String[] fieldName, List sheetNameList,
		// OutputStream output)
		ExcelUtil.ExportExcel(result, fieldName, sheetNameList, output);

		// 下载文件
		String filename = "d:/test.xlsss";

		InputStream inStream = new FileInputStream(filename);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("UTF-8");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(filename.getBytes("UTF-8"), "GBK"));

		byte[] b = new byte[128];
		int len;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();

		return null;

	}

	/**
	 * 下载图片附件
	 * 
	 * @author wangchang
	 * @title: downAttachment
	 * @date 20150507
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward downImageImport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setCity(region);// 地市
		conditionQueryModel.setCountry(country);// 区县

		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节

		// 获取地市列表
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 待下载附件 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getDownImageImportCount(
					userId, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 待下载附件 集合
		List<DownAttachMentModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getDownAttachMentList(userId, conditionQueryModel,
							firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 回传数据 供界面使用
		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		request.setAttribute("startTime", sendStartTime);// 开始时间
		request.setAttribute("endTime", sendEndTime);// 结束时间
		request.setAttribute("region", region);// 地市
		request.setAttribute("country", country);// 区县
		request.setAttribute("themeinterface", themeinterface);// 工单类型
		request.setAttribute("taskdefkey", taskdefkey);// 所属环节

		return mapping.findForward("downImageImportList");
	}

	/**
	 * 获取主场景下拉选的值
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
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select s.scene_name,s.scene_no from master_scene s";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
//		System.out.println("--------主场景searchSql="+searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			MasterScene m = new MasterScene();
			m.setId((String)listOrderedMap.get("scene_no"));
			m.setName((String)listOrderedMap.get("scene_name"));
			list.add(m);
		}
		
		PrintWriter out = response.getWriter();
		// List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		// Map<String,String> maps = new HashMap<String, String>();
		// maps.put("id", "1");
		// maps.put("name", "杆路");
		// lists.add(maps);
		//         
		 JSONArray returnJson = JSONArray.fromObject(list);
	/*	out
				.print("[{\"id\":\"1\",\"name\":\"电杆\"},{\"id\":\"2\",\"name\":\"光缆\"}]");*/
		 out.print(returnJson.toString());
//		 System.out.println(returnJson.toString());

		return null;
	}

	/**
	 * 根据主场景的ID值获取到对应的子场景
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
		String id = request.getParameter("id");// 主场景的ID值
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select sc.copy_name,sc.copy_no from maste_copy_scene sc " +
				"where sc.scene_no='" +id+"'";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			MasterScene m = new MasterScene();
			m.setId((String)listOrderedMap.get("copy_no"));
			m.setName((String)listOrderedMap.get("copy_name"));
			list.add(m);
		}
		PrintWriter out = response.getWriter();
	
		// List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		// if(id!=null&&!id.equals("")){ //类别不为空的时候
		// Map<String,String> maps = new HashMap<String, String>();
		// maps.put("id", "2");
		// maps.put("name", "立电杆");
		// lists.add(maps);
		//	         
		// Map<String,String> maps2 = new HashMap<String, String>();
		// maps2.put("id", "4");
		// maps2.put("name", "扶正电杆");
		// lists.add(maps2);
		// }
		//         
		 JSONArray returnJson = JSONArray.fromObject(list);

		// out.print("[{id:\"1\",name:\"amdin\"},{id:\"1\",name:\"amdin\"}]");
		// out.print(json.toString());

		/*if ("1".equals(id)) {
			out
					.print("[{\"id\":\"11\",\"name\":\"11敷设电杆\"},{\"id\":\"12\",\"name\":\"12电杆接续\"}]");
		} else if ("2".equals(id)) {
			out
					.print("[{\"id\":\"21\",\"name\":\"21敷设光缆\"},{\"id\":\"22\",\"name\":\"22光缆接续\"}]");
		}*/
         out.print(returnJson.toString());
		return null;
	}

	/**
	 * 根据子场景ID值获取对应的属性
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPropertisByChildSceneId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");// 子场景的ID值
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select d.class_name,d.thead_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota,is_require from  maste_copy_thead d  " +
				"where d.copy_no='" +id+"' order by d.seq";
		
//		System.out.println("------searchSql="+searchSql);
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			TheadModel m = new TheadModel();
			m.setProChineseName((String)listOrderedMap.get("thead_name"));
			
			String thead_no = (String)listOrderedMap.get("thead_no");
			m.setProEnglishName(thead_no);
			m.setClassName((String)listOrderedMap.get("class_name"));
			m.setIsQuota((String)listOrderedMap.get("is_quota"));
			m.setProType((String)listOrderedMap.get("type"));
			m.setIsRequire((String)listOrderedMap.get("is_require"));
			
			if("select".equals((String)listOrderedMap.get("type"))){
				
				String bodySql ="select tv.tbody_name,tv.tbody_no from maste_rel_thead_value tv " +
						"where tv.thead_no='" +thead_no+"' and tv.copy_no='" +id+"'";
				
//				System.out.println("-----bodySql="+bodySql);
				
		         List<ListOrderedMap> bodyList = jdbcService.queryForList(bodySql);
		         List<MasterScene> proValue = new ArrayList<MasterScene>();
			        if(bodyList.size()>0){
				 		for (ListOrderedMap listMap : bodyList) {
							MasterScene mt = new MasterScene();
							mt.setId((String)listMap.get("tbody_no"));
							mt.setName((String)listMap.get("tbody_name"));
							proValue.add(mt);
				         	}
				 		m.setProValue(proValue);
			         }
		    }
			list.add(m);
		}
		PrintWriter out = response.getWriter();
		// List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		// if(id!=null&&!id.equals("")){ //类别不为空的时候
		// Map<String,String> maps = new HashMap<String, String>();
		// maps.put("id", "2");
		// maps.put("name", "立电杆");
		// lists.add(maps);
		//	         
		// Map<String,String> maps2 = new HashMap<String, String>();
		// maps2.put("id", "4");
		// maps2.put("name", "扶正电杆");
		// lists.add(maps2);
		// }
		//        
		 JSONArray returnJson = JSONArray.fromObject(list);

		// out.print("[{id:\"1\",name:\"amdin\"},{id:\"1\",name:\"amdin\"}]");
		// out.print(json.toString());

		/*if ("11".equals(id)) {
			out
					.print("[{\"proChineseName\":\"下拉属性1\"," +
							"\"proType\":\"select\"," +
							"\"proEnglishName\":\"aa\"," +
							"\"proValue\":" +
							          "[{\"id\":\"11\",\"name\":\"11敷设电杆\"}," +
							          "{\"id\":\"12\",\"name\":\"12电杆接续\"}]," +
							"\"isQuota\":\"YES\"}," +
						
							"{\"proChineseName\":\"文本框属性\"," +
							"\"proType\":\"inputText\"," +
							"\"proEnglishName\":\"bb\"," +
							"\"proValue\":\"\"," +
							"\"isQuota\":\"NO\"}," +
						
							"{\"proChineseName\":\"文本属性\"," +
							"\"proType\":\"text\"," +
							"\"proEnglishName\":\"cc\"," +
							"\"proValue\":\"逗比\"," +
							"\"isQuota\":\"NO\"}," +
							
							"{\"proChineseName\":\"按钮属性\"," +
							"\"proType\":\"button\"," +
							"\"proEnglishName\":\"dd\"," +
							"\"proValue\":\"主材选择\"," +
							"\"isQuota\":\"NO\"," +
							"\"buttonType\":\"main\"}," +
							
							"{\"proChineseName\":\"下拉属性2\"," +
							"\"proType\":\"select\"," +
							"\"proEnglishName\":\"ee\"," +
							"\"proValue\":[{\"id\":\"333\",\"name\":\"333敷设电杆\"},{\"id\":\"444\",\"name\":\"44电杆接续\"}]," +
							"\"isQuota\":\"YES\"}]");
			
		} else if ("12".equals(id)) {
			out
			.print("[{\"proChineseName\":\"下拉属性1\",\"proType\":\"select\",\"proEnglishName\":\"aa\",\"proValue\":[{\"id\":\"11\",\"name\":\"11敷设电杆\"},{\"id\":\"12\",\"name\":\"12电杆接续\"}],\"isQuota\":\"YES\"},{\"proChineseName\":\"文本框属性\",\"proType\":\"inputText\",\"proEnglishName\":\"bb\",\"proValue\":\"\",\"isQuota\":\"NO\"},{\"proChineseName\":\"文本属性\",\"proType\":\"text\",\"proEnglishName\":\"cc\",\"proValue\":\"逗比\",\"isQuota\":\"NO\"},{\"proChineseName\":\"按钮属性\",\"proType\":\"button\",\"proEnglishName\":\"dd\",\"proValue\":\"按钮\",\"isQuota\":\"NO\"},{\"proChineseName\":\"下拉属性2\",\"proType\":\"select\",\"proEnglishName\":\"ee\",\"proValue\":[{\"id\":\"333\",\"name\":\"333敷设电杆\"},{\"id\":\"444\",\"name\":\"44电杆接续\"}],\"isQuota\":\"YES\"}]");
		} else if ("21".equals(id)) {
			out
			.print("[{\"proChineseName\":\"下拉属性1\",\"proType\":\"select\",\"proEnglishName\":\"aa\",\"proValue\":[{\"id\":\"11\",\"name\":\"11敷设电杆\"},{\"id\":\"12\",\"name\":\"12电杆接续\"}],\"isQuota\":\"YES\"},{\"proChineseName\":\"文本框属性\",\"proType\":\"inputText\",\"proEnglishName\":\"bb\",\"proValue\":\"\",\"isQuota\":\"NO\"},{\"proChineseName\":\"文本属性\",\"proType\":\"text\",\"proEnglishName\":\"cc\",\"proValue\":\"逗比\",\"isQuota\":\"NO\"},{\"proChineseName\":\"按钮属性\",\"proType\":\"button\",\"proEnglishName\":\"dd\",\"proValue\":\"按钮\",\"isQuota\":\"NO\"},{\"proChineseName\":\"下拉属性2\",\"proType\":\"select\",\"proEnglishName\":\"ee\",\"proValue\":[{\"id\":\"333\",\"name\":\"333敷设电杆\"},{\"id\":\"444\",\"name\":\"44电杆接续\"}],\"isQuota\":\"YES\"}]");
		} else if ("22".equals(id)) {
			out
			.print("[{\"proChineseName\":\"下拉属性1\",\"proType\":\"select\",\"proEnglishName\":\"aa\",\"proValue\":[{\"id\":\"11\",\"name\":\"11敷设电杆\"},{\"id\":\"12\",\"name\":\"12电杆接续\"}],\"isQuota\":\"YES\"},{\"proChineseName\":\"文本框属性\",\"proType\":\"inputText\",\"proEnglishName\":\"bb\",\"proValue\":\"\",\"isQuota\":\"NO\"},{\"proChineseName\":\"文本属性\",\"proType\":\"text\",\"proEnglishName\":\"cc\",\"proValue\":\"逗比\",\"isQuota\":\"NO\"},{\"proChineseName\":\"按钮属性\",\"proType\":\"button\",\"proEnglishName\":\"dd\",\"proValue\":\"按钮\",\"isQuota\":\"NO\"},{\"proChineseName\":\"下拉属性2\",\"proType\":\"select\",\"proEnglishName\":\"ee\",\"proValue\":[{\"id\":\"333\",\"name\":\"333敷设电杆\"},{\"id\":\"444\",\"name\":\"44电杆接续\"}],\"isQuota\":\"YES\"}]");
		}*/
//		 System.out.println(returnJson.toString());
	     out.print(returnJson.toString());
		  return null;
	}
	
	/**
	 * 查询主材
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMainMaterial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String mainIds = request.getParameter("mainIds");//已选择的主材ID值，用逗号分隔
		String rowIndexValue = request.getParameter("rowIndexValue");
		String materialType = request.getParameter("materialType");
		
		String childSceneId = request.getParameter("childSceneId");
		String quotaVals = request.getParameter("quotaVals");
		String[] split = quotaVals.split(",");
/*		for(int i=0;i<split.length;i++){
		System.out.println("-----------------"+split[i]);
		}*/
		
		
//		System.out.println("-------------------childSceneId="+childSceneId);
//		System.out.println("-------------------quotaVals="+quotaVals);
		
		//缺少根据infor回显数据的功能
		String infor = request.getParameter("infor");//包含材料ID、数量、单价、合价、材料是否利旧信息的字符串
		String totalCost =StaticMethod.nullObject2String(request.getParameter("totalCost"));//合计金额
		
//		System.out.println("-------------------infor="+infor);
//		System.out.println("-------------------totalCost="+totalCost);
		
		//根据子场景id动态拼写sql：通过已配的定额找主材、辅材---start
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select rq.con_strs from maste_condition_rel_quota rq " +
				"where rq.copy_no ='" +childSceneId+"'";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List<MaterialModel>  list =null;
		for (ListOrderedMap listOrderedMap : resultList) {
			
			String con_strs =  (String)listOrderedMap.get("con_strs");
			String[] args = con_strs.split("#");
					 
			String materialSql ="select dtt.item_no,dtt.id,dtt.sort_num,dtt.name,dtt.standard,dtt.unit,dtt.num," +
					"dtt.per_price,dtt.total_price" +
					" from maste_data dtt" +
					" left join maste_rel_thead_item mt on dtt.item_no = mt.item_no where mt.copy_no='"+childSceneId+"' ";
			
			for(int i=0 ;i<split.length;i++){
				if("null".equals(split[i])){
					
					materialSql += " and mt."+args[i]+" is null";
				}else{
					
					materialSql += " and mt."+args[i]+"='"+split[i]+"'";
				}
			}
			
			if("main".equals(materialType)){
				materialSql +=" and dtt.type='1'";
			}else{
				materialSql +=" and dtt.type='0'";
				
			}
			
			materialSql +=" order by to_number(nvl(dtt.sort_num,'0')) asc ";
			List<ListOrderedMap> materialList = jdbcService.queryForList(materialSql);
			
			int sort= 1;//生成序号，不使用数据库中的sort_num字段
			if(materialList.size()>0){
				list =  new ArrayList<MaterialModel>();
				for (ListOrderedMap lists : materialList) {					
					MaterialModel m = new MaterialModel();
					m.setId((String)lists.get("id"));
					//m.setSortNum((String)lists.get("sort_num"));
					m.setSortNum(Integer.toString(sort++));
					m.setName((String)lists.get("name"));
					m.setStandard((String)lists.get("standard"));
					m.setUnit((String)lists.get("unit"));
					m.setItemNo((String)lists.get("item_no"));
					m.setNum(lists.get("num")==null?0:Double.parseDouble(lists.get("num").toString()));
					m.setOriginalNum(lists.get("num")==null?0:Double.parseDouble(lists.get("num").toString()));//基准单价
					m.setPerPrice(lists.get("per_price")==null?0:Double.parseDouble(lists.get("per_price").toString()));
					m.setOriginalPerPrice(lists.get("per_price")==null?0:Double.parseDouble(lists.get("per_price").toString()));//基准数量
					m.setTotalPrice(lists.get("total_price")==null?0:Double.parseDouble(lists.get("total_price").toString()));
					m.setIsWhetherOld("NO");//是否利旧
					list.add(m);
				}
			}
		}
		//根据子场景id动态拼写sql：通过已配的定额找主材、辅材---end
		
	    //回显：已选择的主材辅材，需要到存储表中回显
		if(infor!=null&&!"".equals(infor)){
			List<MaterialModel> echoList=new ArrayList<MaterialModel>();
			String[] split2 = infor.split(";");
			for(int i=0;i<split2.length;i++){
				String[] split3 = split2[i].split(",");
				MaterialModel model=new MaterialModel();
				model.setId(split3[0]);
				//System.out.println("split3[1]="+split3[1]);
				model.setNum(Double.parseDouble(split3[1]));
				model.setPerPrice(Double.parseDouble(split3[2]));
				model.setTotalPrice(Double.parseDouble(split3[3]));
				model.setIsWhetherOld(split3[4]);
				echoList.add(model);
			}
			
			if (echoList != null && echoList.size() > 0) {
				for(int m=0;m<list.size();m++){
					for(int e=0;e<echoList.size();e++){
						if(list.get(m).getId().equals(echoList.get(e).getId())){
							list.get(m).setNum(echoList.get(e).getNum());
							list.get(m).setPerPrice(echoList.get(e).getPerPrice());
							list.get(m).setTotalPrice(echoList.get(e).getTotalPrice());
							list.get(m).setIsWhetherOld(echoList.get(e).getIsWhetherOld());
							break;
						}
					}
				}
			}
		}
		
		request.setAttribute("materialModelList", list);		
		
		request.setAttribute("childSceneId", childSceneId);
		request.setAttribute("quotaVals", quotaVals);
		request.setAttribute("mainIds",mainIds);
		
		request.setAttribute("materialType", materialType);
		request.setAttribute("rowIndexValue", rowIndexValue);
		request.setAttribute("totalCost", totalCost);

		return mapping.findForward("mainMaterial");
	}

	
	/**
	 * 通过定额获取其他属性的值，比如编码、折扣率等
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOtherIndex(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		System.out.println("----------------111111111111111111");
		//查看登录人的地市
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
		//根据派发人的部门所属区域，确定工单的区域
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		
		
		String childSceneId = request.getParameter("childSceneId");//子场景ID
		String quotaVals = request.getParameter("quotaVals");//定额的字段的值，按","分隔
		//定额编号,工费单价（打折部分）,工费单价（不打折部分）,折扣率,工费单价（非标准定额）,工费单价（非定额）
		/*
	    ahsc--工费单价（非标准定额）;COST_PER_NO_STANDARD
		edza--工费单价（打折部分）;COST_PER_SALE
		gvyh--折扣率;DISCOUNT
		hsok--定额编号;NORM
		nzym--工费单价（不打折部分）;COST_PER_NO_SALE
		qnhn--工费单价（非定额）COST_PER_NO_QUOTA
		*/
		String[] split = quotaVals.split(",");
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select rq.con_strs from maste_condition_rel_quota rq " +
				"where rq.copy_no ='" +childSceneId+"'";
		
		//非定额场景，管道人孔升降、人井上覆的更换用非定额工单折扣，其他场景用定额折扣 0826
		//QMFDKEMG  --非定额场景
		//I8Y1G9YA -- 管道人孔升降、人井上覆的更换
		String gvyhColumn = "t.fault_dis";
		if("QMFDKEMG".equals(childSceneId)||"I8Y1G9YA".equals(childSceneId)){ 
			gvyhColumn = "t.fde_dis";
		}
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list =null;
		for (ListOrderedMap listOrderedMap : resultList) {
			
			String con_strs =  (String)listOrderedMap.get("con_strs");
			if(null!=con_strs){
			String[] args = con_strs.split("#");
			
			String str = "";
			for(int i=0 ;i<split.length;i++){
				str += " and t."+args[i]+"='"+split[i]+"'";
			}
			
			str +=" and t.copy_no='"+childSceneId+"'";
					 
			String quotaSql =" select * from (" +
					"  select nvl(t.cost_per_no_standard,'0') v,'ahsc' k,t.item_no" +
					"  from maste_rel_thead_item t  where 1=1 " +str+
					"  union all select '0', 'ahsc','' from dual) " +
					"   t where rownum =1" +
					
					"  union all "+
					
					"   select * from(  select nvl(t.cost_per_sale,'0'),'edza',t.item_no" +
					" from maste_rel_thead_item t   where 1=1 " +str+
					"  union all   select '0', 'edza','' from dual) t where rownum =1" +

					" union all "+
					        
					"select "+gvyhColumn+",'gvyh','' from" +
					" (select "+gvyhColumn+" from maste_area_discount t" +
					"  where instr(t.country_id,rpad('"+areaid+"',6,'0'))>0  " +
					"  union all" +
					"  select '1' from dual) t" +
					"  where rownum=1 "+
					
					" union all "+
					"  select * from (select nvl(t.norm,'-'),'hsok',t.item_no" +
					" from maste_rel_thead_item t   where 1=1 " +str+
					"  union all   select '-', 'hsok','' from dual) t where rownum =1" +

					" union all "+
					"  select * from (select nvl(t.cost_per_no_sale,'0'),'nzym',t.item_no" +
					" from maste_rel_thead_item t   where 1=1 " +str+
					"  union all   select '0', 'nzym','' from dual) t where rownum =1" +

					" union all "+
					"  select * from (select nvl(t.cost_per_no_quota,'0'),'qnhn',t.item_no" +
					" from maste_rel_thead_item t  where 1=1 " +str+
					"  union all   select '0', 'qnhn','' from dual) t where rownum =1" ;
;

		//System.out.println("--------quotaSql-:"+quotaSql);
			List<ListOrderedMap> quotaList = jdbcService.queryForList(quotaSql);
			
			String uniqueId = UUIDHexGenerator.getInstance().getID();//获取唯一标识
			if(quotaList.size()>0){
				list =  new ArrayList();
				for (ListOrderedMap lists : quotaList) {					
					TheadModelSel m = new TheadModelSel();
					m.setProEnglishName((String)lists.get("k"));
					m.setProValue((String)lists.get("v"));
					m.setItemNo((String)lists.get("item_no"));
					m.setUniqueId(uniqueId);
					list.add(m);
				}
			}
		}
		}
	
		PrintWriter out = response.getWriter();
	
		/*out
				.print("[{\"proEnglishName\":\"hsok\"," +"\"proValue\":\"1.2\"}," +
						"{\"proEnglishName\":\"edza\",\"proValue\":\"1.3\"}," +
						"{\"proEnglishName\":\"qnhn\",\"proValue\":\"1.4\"}," +
						"{\"proEnglishName\":\"nzym\",\"proValue\":\"1.4\"}," +
						"{\"proEnglishName\":\"ahsc\",\"proValue\":\"1.4\"}," +
						"{\"proEnglishName\":\"gvyh\",\"proValue\":\"1.5\"}]");*/
		 JSONArray returnJson = JSONArray.fromObject(list);

	     out.print(returnJson.toString());
		
		return null;
	}
	
	/**
	 * 查看子场景详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkChildSceneForDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");//流程号
		String linkType = request.getParameter("linkType");//环节类型
		
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		Map<String, Object> result = sceneTemplateService.checkChildSceneForDetails(processInstanceId, linkType, null);
		
		request.setAttribute("sceneTableList", result.get("sceneTableList"));
		request.setAttribute("processInstanceId",processInstanceId);
		request.setAttribute("linkType",linkType);
		
		return mapping.findForward("childSceneForDetails");
	}
	
	/**
	 * 查看材料（主材或者辅材）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkMaterialForDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId=request.getParameter("processInstanceId");//工单号
		String linkType = request.getParameter("linkType");//环节号
		String itemNo=request.getParameter("itemNo");
		String uniqueId = StaticMethod.nullObject2String(request.getParameter("uniqueId"));
		String materialType = request.getParameter("materialType");
		
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		Map<String,String> param=new HashMap<String,String>();
		param.put("itemNo", itemNo);
		param.put("uniqueId", uniqueId);
		param.put("materialType", materialType);
		Map<String, Object> result = sceneTemplateService.checkMaterialForDetails(processInstanceId, linkType, param);
		
		request.setAttribute("materialTableList", result.get("materialTableList"));
		return mapping.findForward("materialForDetails");//要新加页面
	}
	
	
	/**
	 * 查看主材
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkMainMaterialForDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String processInstanceId=request.getParameter("processInstanceId");
		String itemNo=request.getParameter("itemNo");
		String uniqueId = StaticMethod.nullObject2String(request
				.getParameter("uniqueId"));
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
				"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
				"left join maste_data d on k.data_id = d.id " +
				"where k.type='1' and k.item_no='"+itemNo+"' and k.process_instance_id='"+processInstanceId+"'";
		if(!"".equals(uniqueId)){
			searchSql+=" and k.unique_id='"+uniqueId+"'";
			searchSql+=" order by k.seq asc";
		} else{
			searchSql+=" order by to_number(nvl(d.sort_num,'0')) asc";
		}
		
		
 		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		
		List<SceneTableModel> mainMaterialTableList=new ArrayList<SceneTableModel>();
		SceneTableModel sceneTableModel=new SceneTableModel();
		//设置表头
		int tableHeaderLength=8;
		String[] tableHeader=new String[tableHeaderLength];  
		tableHeader[0]="序号";
		tableHeader[1]="名称";
		tableHeader[2]="规格程式";
		tableHeader[3]="单位";
		tableHeader[4]="数量";
		tableHeader[5]="单价";
		tableHeader[6]="合价";
		tableHeader[7]="材料是否利旧";
		sceneTableModel.setTableHeader(tableHeader);
		
		//设置数据
		//int tableDateLength=4;
		List<String[]> tableData=new ArrayList<String[]>();
		String[] tr=null;
		
		int seqNum = 1;//序号
		for(ListOrderedMap listOrderedMap : resultList){
			tr=new String[tableHeaderLength];
			//tr[0]= (String)listOrderedMap.get("sort_num");
			tr[0]= Integer.valueOf(seqNum++).toString();
			tr[1]= (String)listOrderedMap.get("name");
			tr[2]= (String)listOrderedMap.get("standard");
			tr[3]= (String)listOrderedMap.get("unit");
			
			tr[4]= listOrderedMap.get("num")==null?"0":Double.parseDouble(listOrderedMap.get("num").toString())+"";
			tr[5]= listOrderedMap.get("per_price")==null?"0":Double.parseDouble(listOrderedMap.get("per_price").toString())+"";
			tr[6]= listOrderedMap.get("total_price")==null?"0":Double.parseDouble(listOrderedMap.get("total_price").toString())+"";
			tr[7]= (String)listOrderedMap.get("w");

			tableData.add(tr);
		}
	
		
	/*	String[] tr2=new String[tableHeaderLength];
		tr2[0]="2";
		tr2[1]="光缆2";
		tr2[2]="长飞纤";
		tr2[3]="米";
		tr2[4]="0";
		tr2[5]="24";
		tr2[6]="0";
		tr2[7]="否";
		tableData.add(tr2);*/
		
		sceneTableModel.setTableData(tableData);
		
		//返回集合
		mainMaterialTableList.add(sceneTableModel);
	
		request.setAttribute("mainMaterialTableList", mainMaterialTableList);
		return mapping.findForward("mainMaterialForDetails");
	}
	
	/**
	 * 查看辅材
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAssistMaterialForDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String processInstanceId=request.getParameter("processInstanceId");
		String itemNo=request.getParameter("itemNo");
		String uniqueId = StaticMethod.nullObject2String(request
				.getParameter("uniqueId"));
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
				"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
				"left join maste_data d on k.data_id = d.id " +
				"where k.type='0' and k.item_no='"+itemNo+"' and k.process_instance_id='"+processInstanceId+"'";
		if(!"".equals(uniqueId)){
			searchSql+=" and k.unique_id='"+uniqueId+"'";
			searchSql+=" order by k.seq asc";
		}else{
			searchSql+=" order by to_number(nvl(d.sort_num,'0')) asc";
		}
		
		
//		System.out.println("-------------------查看辅材详情-------searchSql="+searchSql);
 		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		
		
		List<SceneTableModel> assistMaterialTableList=new ArrayList<SceneTableModel>();
		SceneTableModel sceneTableModel=new SceneTableModel();
		//设置表头
		int tableHeaderLength=8;
		String[] tableHeader=new String[tableHeaderLength];  
		tableHeader[0]="序号";
		tableHeader[1]="名称";
		tableHeader[2]="规格程式";
		tableHeader[3]="单位";
		tableHeader[4]="数量";
		tableHeader[5]="单价";
		tableHeader[6]="合价";
		tableHeader[7]="材料是否利旧";
		sceneTableModel.setTableHeader(tableHeader);
		
		//设置数据
		List<String[]> tableData=new ArrayList<String[]>();
		String[] tr=null;
		
		int seqNum = 1;//序号
		for(ListOrderedMap listOrderedMap : resultList){
			tr=new String[tableHeaderLength];
			//tr[0]= (String)listOrderedMap.get("sort_num");
			tr[0]= Integer.valueOf(seqNum++).toString();
			tr[1]= (String)listOrderedMap.get("name");
			tr[2]= (String)listOrderedMap.get("standard");
			tr[3]= (String)listOrderedMap.get("unit");
			
			tr[4]= listOrderedMap.get("num")==null?"0":Double.parseDouble(listOrderedMap.get("num").toString())+"";
			tr[5]= listOrderedMap.get("per_price")==null?"0":Double.parseDouble(listOrderedMap.get("per_price").toString())+"";
			tr[6]= listOrderedMap.get("total_price")==null?"0":Double.parseDouble(listOrderedMap.get("total_price").toString())+"";
			tr[7]= (String)listOrderedMap.get("w");

			tableData.add(tr);
		}
	/*	
		//int tableDateLength=4;
		List<String[]> tableData=new ArrayList<String[]>();
		String[] tr1=new String[tableHeaderLength];
		tr1[0]="1";
		tr1[1]="光缆11111";
		tr1[2]="GYTS-8B1.3(长飞纤)";
		tr1[3]="米";
		tr1[4]="2000.00";
		tr1[5]="23.00";
		tr1[6]="23000.00";
		tr1[7]="是";
		tableData.add(tr1);
		
		String[] tr2=new String[tableHeaderLength];
		tr2[0]="2";
		tr2[1]="光缆222222";
		tr2[2]="长飞纤";
		tr2[3]="米";
		tr2[4]="0";
		tr2[5]="24";
		tr2[6]="0";
		tr2[7]="否";
		tableData.add(tr2);
		*/
		sceneTableModel.setTableData(tableData);
		
		//返回集合
		assistMaterialTableList.add(sceneTableModel);
	
		request.setAttribute("assistMaterialTableList", assistMaterialTableList);
		
		return mapping.findForward("assistMaterialForDetails");
	}
	
	/**
	 * 新建派单页面回显子场景
	 * @param request
	 * @param processInstanceId
	 * @throws ParseException
	 */
	private void echoChildScene(HttpServletRequest request,
			String processInstanceId) throws ParseException {
				
		List<SceneTableModel> echoChildSceneTableList=new ArrayList<SceneTableModel>();
		
		IMasteSelCopyTaskService masteSelCopyTaskService = (IMasteSelCopyTaskService)getBean("masteSelCopyTaskService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
		
		int num = resultTaskList.size();
		
		String childSceneIds="";
		String oneChildSceneId="";
		String oneChildSceneName="";//子场景中文值
		String quotaField ="";
		String type =""; //子场景表头-各个的类型
		String theadNo ="";//子场景表头-编号
		String className ="";//子场景表头-
		String isrequire ="";//子场景表头-
		String twoTr="";//子场景第一行数据
		
		SceneTableModel sceneTableModel =null;
		MasteSelCopyTask  entity=null;
		
		for(int i=0;i<num;i++){//子场景循环
		
			sceneTableModel=new SceneTableModel();

			entity= resultTaskList.get(i);
			//设置子场景id值
			oneChildSceneId=entity.getChildSceneId();
			if(oneChildSceneId!=null&&!"".equals(oneChildSceneId)){//判断获取到的子场景id是否为空
				
				sceneTableModel.setChildSceneId(oneChildSceneId);
				childSceneIds+=oneChildSceneId+",";
				//定额字段 用子场景id和字段名以"_"拼接
				quotaField=entity.getQuotaField();
				sceneTableModel.setQuotaField(quotaField);
				//主材已选择的定额值 //已选的定额--主材
				String existQuotaValue=entity.getExistquotaValue();
				sceneTableModel.setExistQuotaValue(existQuotaValue);
				//辅材已选择的定额值 //已选的定额--辅材
				String assistExistQuotaValue=entity.getAssistExistquotaValue();
				sceneTableModel.setAssistExistQuotaValue(assistExistQuotaValue);
				
				// 表头-数据
				CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
				String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,d.is_quota,d.is_require,d.class_name from  maste_copy_thead d  " +
						"where d.copy_no='" +oneChildSceneId+"' order by d.seq";
				List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
				
//				System.out.println("------------searchSql="+searchSql);
				
				int tableHeaderLength=resultList.size();
				String[] tableHeader=new String[tableHeaderLength+1];  
		
				List  list = new ArrayList();
				int j =0;
				List<String[]> tableData=new ArrayList<String[]>();
				String[] tr=null;//存储现有数据的
				String[] tr1=new String[tableHeaderLength];//存标
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					tableHeader[j]=(String)listOrderedMap.get("thead_name");
				
					oneChildSceneName =(String)listOrderedMap.get("copy_name");
					
					theadNo = (String)listOrderedMap.get("thead_no");
					type=(String)listOrderedMap.get("type");
					className = (String)listOrderedMap.get("class_name");
					isrequire = (String)listOrderedMap.get("is_require");
					
					if("select".equals(type)){
								twoTr="<select onchange=\"getOtherIndex(this,&quot;"+
								oneChildSceneId+"_quotaField&quot;,&quot;"+
								oneChildSceneId+"_table&quot;,&quot;"+
								oneChildSceneId+"&quot;)\" name=\""+
								oneChildSceneId+"_"+theadNo+"\">" ;
								
								String bodySql ="select tv.tbody_name,tv.tbody_no from maste_rel_thead_value tv " +
								"where tv.thead_no='" +theadNo+"' and tv.copy_no='" +oneChildSceneId+"'";
						         List<ListOrderedMap> bodyList = jdbcService.queryForList(bodySql);
							        if(bodyList.size()>0){
								 		for (ListOrderedMap listMap : bodyList) {
								 			String tbody_no=(String)listMap.get("tbody_no");
								 			String tbody_name=(String)listMap.get("tbody_name");
								 			
											twoTr+="<option value=\""+tbody_no+"\">"+tbody_name+"</option>";
											
								         	}
						         twoTr+="</select>";
		 		
			                    }
					}else if("inputText".equals(type)){
						 twoTr="<input type=\"text\"";
						
						 if("1".equals(isrequire)){
							 twoTr+=" alt=\"allowBlank:false,maxLength:120,vtext:&quot;请输入值，不能为空！&quot;\"";
						 }
						 if(!"".equals(className)&&className!=null){
							if(className.equals("lengthen")){
								twoTr+=" class=\""+className+"\" value='XYZ' size='90' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
							}else{
								twoTr+=" class=\""+className+"\" value='XYZ' size='4' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
							}    					
	  			         }else{
	      					twoTr+=" value='XYZ' size='4' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
	  			         }
					
					}else if("text".equals(type)){
						twoTr="<div>XYZ</div><input type=\"hidden\" value=\"XYZ\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
						
					}else if("mainButton".equals(type)){
						twoTr="<input type=\"button\" onclick=\"chooseMainMaterial(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"主材选择\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
					}else if("assistButton".equals(type)){
						twoTr="<input type=\"button\" onclick=\"chooseAssistMaterial(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"辅材选择\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
					}
					
					tr1[j]=twoTr;
					
					twoTr="";
					j++;
					
				}
				
				tableHeader[tableHeaderLength]="<input type=\"button\" onclick=\"addtr(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"添加\">";
				
				j=0;
				
				//设置子场景中文值
				sceneTableModel.setChildSceneName(oneChildSceneName);
				
				//设置表头	
				sceneTableModel.setTableHeader(tableHeader);
				
				//设置数据
				//int tableDateLength=4;
				//设置数据--获取需要查找的sql
				String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+oneChildSceneId+"'";
//				System.out.println("-------dataSqlConstr="+dataSqlConstr);
				List<ListOrderedMap> dataConstr = jdbcService.queryForList(dataSqlConstr);
				
				String task_rel_strs ="";
				String con_strs ="";
				for(ListOrderedMap listOrderedMap : dataConstr){
					task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
					con_strs = (String)listOrderedMap.get("con_strs");
				}
				
			   //设置数据--获取已经设置的定额信息
				String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO,main_hid,assist_hid,quotavalue_hid,informain_hid,inforassist_hid,totalcostmain_hid,totalcostassist_hid,unique_id from maste_rel_task_item where CHILD_SCENE_ID='"+oneChildSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"' order by seq";
					
				
				List<ListOrderedMap> dataList = jdbcService.queryForList(selDataSql);
//				System.out.println("----------selDataSql="+selDataSql);
				
				String[] dataFiled = task_rel_strs.split("#"); 

				String main_hid="";
				String assist_hid="";
				String quotavalue_hid="";
				String informain_hid="";
				String inforassist_hid="";
				String totalcostmain_hid="";
				String totalcostassist_hid="";
				String item_no="";
				String unique_id="";
				 
				for(ListOrderedMap listOrderedMap : dataList){
					main_hid = listOrderedMap.get("main_hid")==null?"":(String)listOrderedMap.get("main_hid");
					assist_hid = listOrderedMap.get("assist_hid")==null?"":(String)listOrderedMap.get("assist_hid");
					quotavalue_hid = listOrderedMap.get("quotavalue_hid")==null?"":(String)listOrderedMap.get("quotavalue_hid");
					informain_hid = listOrderedMap.get("informain_hid")==null?"":(String)listOrderedMap.get("informain_hid");
					inforassist_hid = listOrderedMap.get("inforassist_hid")==null?"":(String)listOrderedMap.get("inforassist_hid");
					totalcostmain_hid = listOrderedMap.get("totalcostmain_hid")==null?"":(String)listOrderedMap.get("totalcostmain_hid");
					totalcostassist_hid = listOrderedMap.get("totalcostassist_hid")==null?"":(String)listOrderedMap.get("totalcostassist_hid");
					item_no = listOrderedMap.get("item_no")==null?"":(String)listOrderedMap.get("item_no");
					unique_id = listOrderedMap.get("unique_id")==null?"":(String)listOrderedMap.get("unique_id");
					
					tr =new String[tableHeaderLength+1];
					
					for(int r=0;r<tableHeaderLength;r++){
						boolean flag = tr1[r].contains("XYZ");
						if(flag){
							
							tr[r] = tr1[r].replaceAll("XYZ", listOrderedMap.get(dataFiled[r])==null?"":(String)listOrderedMap.get(dataFiled[r])) ;
						}else{
							tr[r] = tr1[r].replaceAll("<option value=\""+(String)listOrderedMap.get(dataFiled[r])+"\"", "<option value=\""+(String)listOrderedMap.get(dataFiled[r])+"\" selected=\"selected\"") ;
						}
					}
//					删除-按钮，以及隐藏域的值
					tr[tableHeaderLength] ="<input type=\"hidden\" value=\""+main_hid+"\" name=\""+oneChildSceneId+"_main\" />" +
							"<input type=\"hidden\" value=\""+assist_hid+"\" name=\""+oneChildSceneId+"_assist\" />" +
							"<input type=\"hidden\" value=\""+quotavalue_hid+"\" name=\""+oneChildSceneId+"_quotaValue\" />" +
							"<input type=\"hidden\" value=\""+informain_hid+"\" name=\""+oneChildSceneId+"_inforMain\" />" +
							"<input type=\"hidden\" value=\""+inforassist_hid+"\" name=\""+oneChildSceneId+"_inforAssist\" />" +
							"<input type=\"hidden\" value=\""+totalcostmain_hid+"\" name=\""+oneChildSceneId+"_totalCostMain\" />" +
							"<input type=\"hidden\" value=\""+totalcostassist_hid+"\" name=\""+oneChildSceneId+"_totalCostAssist\" />" +
							"<input type=\"hidden\" value=\""+item_no+"\" name=\""+oneChildSceneId+"_itemNo\" />" +
							"<input type=\"hidden\" value=\""+unique_id+"\" name=\""+oneChildSceneId+"_uniqueId\" />" +
							"<input type=\"button\" onclick=\"deltr(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"删除\" />";
					
					tableData.add(tr);
				}
							
				sceneTableModel.setTableData(tableData);
				echoChildSceneTableList.add(sceneTableModel);
			}
		}
		
		
		request.setAttribute("childSceneIds", childSceneIds);
		request.setAttribute("echoChildSceneTableList", echoChildSceneTableList);
		
}
	
	/**
	 * 本地网-预检预修工单查询
	 * 
	 * @author chujingang
	 * @title: localNetworkWorkOrderList
	 * @date Feb 7, 2015 10:28:21 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward  localNetworkWorkOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//int pageSize = CommonUtils.PAGE_SIZE;		
		int pageSize = 50;
		String tempPageSize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

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

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		//String provName = request.getParameter("provName");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");
		String mainSceneId = request.getParameter("mainSceneId");//场景
		String workOrderDegree = request.getParameter("workOrderDegree");//紧急程度
		String keyWord = request.getParameter("keyWord");//关键字
		String approveStartTime = request.getParameter("approveStartTime");//批复开始时间
		String approveEndTime = request.getParameter("approveEndTime");//批复结束时间

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setLineType(lineType);
		conditionQueryModel.setWorkOrderDegree(workOrderDegree);
		conditionQueryModel.setKeyWord(keyWord);
		
		//conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		conditionQueryModel.setApproveStartTime(approveStartTime);
		conditionQueryModel.setApproveEndTime(approveEndTime);
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		// 本地网-预检预修工单集合条数
		int total = 0;
		// 本地网-预检预修工单集合
		List<TaskModel> rPnrTransferList = null;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			try {
				total = pnrTransferNewPrecheckService.getLocalNetworkWorkOrderListCount(areaid,userId,
						"backlog", "transferNewPrechechProcess",
						conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getLocalNetworkWorkOrderList(areaid,userId, "backlog",
								"transferNewPrechechProcess", conditionQueryModel,
								firstResult, endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("tishiFlag","Y");//没选查询条件时，给出提示
		}
		String batchApprovalFlag = "no";
//		System.out.println("--------------status=" + status);
		if (status != null) {
			// if (status.equals("workOrderCheck") ||
			// status.equals("cityLineExamine")
			// || status.equals("cityLineDirectorAudit")
			// || status.equals("cityManageExamine")
			// || status.equals("cityManageDirectorAudit")
			// || status.equals("cityViceAudit")
			// || status.equals("provinceLineExamine")
			// || status.equals("provinceLineViceAudit")
			// || status.equals("provinceManageExamine")
			// || status.equals("provinceManageViceAudit")) {
			// batchApprovalFlag="yes";
			// }

			if (status.equals("provinceLineExamine")
					|| status.equals("provinceLineViceAudit")
					|| status.equals("provinceManageExamine")
					|| status.equals("provinceManageViceAudit")) {
				batchApprovalFlag = "yes";
			}

		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("workOrderDegree", workOrderDegree);
		//request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("mainSceneId", mainSceneId);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);
		request.setAttribute("approveStartTime", approveStartTime);
		request.setAttribute("approveEndTime", approveEndTime);
		
		// 登录人，为了在页面区分是否是省级别的四个工作人员（是否显示批量处理按钮）
		request.setAttribute("loginUserId", userId);
		//返回查询条件
		//request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("localNetworkWorkOrderList");
	}
	
	/**
	 * 处理
	 * 
	 * @author chujingang
	 * @title: todo1
	 * @date Feb 7, 2015 9:32:45 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward openDealWithView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));

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
			PnrTransferOffice pnrTransferOffice = list.get(0);
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);

			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			Map<String, Object> gMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> handlelist = null;
			int handleSize = 0;
			search.addSort("checkTime", true);// 按回复时间排序
			gMap = getHandleList(gMap, transferHandleService, search,
					handlelist, handleSize);

			
			// 获取审批驳回信息
			Map<String, Object> approveBackMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveBackHandlelist = null;
			int approveBackHandleSize = 0;
			Search approveBackSearch = new Search();
			approveBackSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveBackSearch.addFilterEqual("operation", "02");
			approveBackSearch.addSort("checkTime", true);// 按回复时间排序
			approveBackMap = getHandleList(approveBackMap,
					transferHandleService, approveBackSearch,
					approveBackHandlelist, approveBackHandleSize);
			// 获取专家会审信息
			Map<String, Object> triageMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> triagelist = null;
			int triageSize = 0;
			Search triageSearch = new Search();
			triageSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			triageSearch.addFilterEqual("operation", "03");
			triageSearch.addSort("checkTime", true);// 按回复时间排序
			triageMap = getHandleList(triageMap, transferHandleService,
					triageSearch, triagelist, triageSize);
			// 获取施工队处理信息
			Map<String, Object> transferMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> transferlist = null;
			int transferHandleSize = 0;
			Search transferSearch = new Search();
			transferSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			transferSearch.addFilterEqual("operation", "04");
			transferSearch.addSort("checkTime", true);// 按回复时间排序
			transferMap = getHandleList(transferMap, transferHandleService,
					transferSearch, transferlist, transferHandleSize);
			// 获取审核信息
			Map<String, Object> auditMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> auditlist = null;
			int auditSize = 0;
			Search auditSearch = new Search();
			auditSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			auditSearch.addFilterEqual("operation", "05");
			auditSearch.addFilterEqual("state", "rollback");
			auditSearch.addSort("checkTime", true);// 按回复时间排序
			auditMap = getHandleList(auditMap, transferHandleService,
					auditSearch, auditlist, auditSize);

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();
			String userTaskId = task.getTaskDefinitionKey();
			String step = "";// 环节所在步骤的值
			if (userTaskId.equals("need")) {// 工单发起
				step = "1";
			} else if (userTaskId.equals("workOrderCheck")) {// 工单发起审核
				step = "2";
			} else if (userTaskId.equals("cityLineExamine")) {// 市线路业务主管审核
				step = "3";
			} else if (userTaskId.equals("cityLineDirectorAudit")) {// 市线路主任审核
				step = "4";
			} else if (userTaskId.equals("cityManageExamine")) {// 市运维主管审核
				step = "5";
			} else if (userTaskId.equals("cityManageDirectorAudit")) {// 市运维主任审核
				step = "6";
			} else if (userTaskId.equals("cityViceAudit")) {// 市公司副总审核
				step = "7";
			} else if (userTaskId.equals("provinceLineExamine")) {// 省线路主管审核
				step = "8";
			} else if (userTaskId.equals("provinceLineViceAudit")) {// 省线路总经理审核
				step = "9";
			} else if (userTaskId.equals("usertask11")) {// 专家会审
				step = "1";
			} else if (userTaskId.equals("provinceManageExamine")) {// 省运维主管审核
				step = "10";
			} else if (userTaskId.equals("provinceManageViceAudit")) {// 省运维总经理审批
				step = "11";
			} else if (userTaskId.equals("daiweiAudit")) {// 审核
				step = "14";
			} else if (userTaskId.equals("orderAudit")) {// 抽查
				step = "15";
			}

			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceIdAndUserTaskId(
							processInstanceId, step);
		
			// 判断是否为抓回
			String tempRollbackFlag = request.getParameter("rollbackFlag");
			Map<String, String> tempMap = null;
			if ("2".equals(tempRollbackFlag)) {
				tempMap = new HashMap<String, String>();
				tempMap.put("processInstanceId", processInstanceId);
				tempMap.put("linkName", userTaskId);
			}

			if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {// 工单发起
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				// 查询出该工单所关联的图片，回显到页面中
				IPnrTransferOverhaulRemakeService pnrTransferOverhaulRemakeService = (IPnrTransferOverhaulRemakeService) getBean("pnrTransferOverhaulRemakeService");
				String[] photoList = pnrTransferOverhaulRemakeService
						.getPhotoByProcessInstanceId(processInstanceId);
				// 遍历photoID字符串，将图片关联标志state设置成0
				if (photoList[0] != null && !"".equals(photoList[0])) {
					String[] photoesId = photoList[0].split(",");
					if (photoesId != null && photoesId.length > 0) {
						for (String str : photoesId) {
							if (str != null && !"".equals(str)) {
								pnrTransferNewPrecheckService
										.setStateByPhotoId(str, "0");
							}
						}
					}
				}
				// 回显的图片id串
				request.setAttribute("photoIds", photoList[0]);
				// 回显的图片详细信息
				request.setAttribute("photoList", photoList[1]);
				request.setAttribute("mainFaultOccurTime", pnrTransferOffice
						.getSubmitApplicationTime());// 回显申请提交时间

				// 驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));

				//回显子场景
				this.echoChildScene(request, processInstanceId);
				
				String findForward = "new";
				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"workOrderCheck")) {// 工单发起审核
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				request.setAttribute("step", step);
				request.setAttribute("marker", "workOrderCheck");
				request.setAttribute("state", "workOrderState");
				request.setAttribute("cityname", "cityLineChargeAduit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityLineExamine")) {// 市线路业务主管审核
				
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				request.setAttribute("step", step);
				request.setAttribute("marker", "cityLineExamine");
				request.setAttribute("state", "cityLineChargeState");
				request.setAttribute("cityname", "cityLineDirectorAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityLineDirectorAudit")) {// 市线路主任审核
				
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}
				request.setAttribute("step", step);
				request.setAttribute("marker", "cityLineDirectorAudit");
				request.setAttribute("state", "cityLineDirectorState");
				request.setAttribute("cityname", "cityManageChargeAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityManageExamine")) {// 市运维主管审核
				
				// 抓回回显信息
				if ("2".equals(tempRollbackFlag)) {
					TransferOfficeHandleModel transferOfficeHandleModel = transferHandleService
							.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",
							transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel
							.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel
							.getId());
				}

				if ((pnrTransferOffice == null ? 0 : pnrTransferOffice
						.getState()) == 3) {
					request.setAttribute("directList", "waitWorkOrderList");
				}

				request.setAttribute("step", step);
				request.setAttribute("marker", "cityManageExamine");
				request.setAttribute("state", "cityManageChargeState");
				request.setAttribute("cityname", "cityManageDirectorAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityManageDirectorAudit")) {// 市运维主任审核
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "cityManageDirectorAudit");
				request.setAttribute("state", "cityManageChargeState");
				request.setAttribute("cityname", "cityManageDirectorAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityViceAudit")) {// 市公司副总审核
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "cityViceAudit");
				request.setAttribute("state", "");
				request.setAttribute("cityname", "");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceLineExamine")) {// 省线路主管审核
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "provinceLineExamine");
				request.setAttribute("state", "provinceLineChargeState");
				request.setAttribute("cityname", "provinceLineViceAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceLineViceAudit")) {// 省线路总经理审核
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "provinceLineViceAudit");
				request.setAttribute("state", "provinceLineViceState");
				request.setAttribute("cityname", "provinceManageCharge");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceManageExamine")) {// 省运维主管审核
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "provinceManageExamine");
				request.setAttribute("state", "provinceManageChargeState");
				request.setAttribute("cityname", "provinceManageVice");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"usertask11")) {// 专家会审
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"provinceManageViceAudit")) {// 省运维总经理审批
				
				request.setAttribute("step", step);
				request.setAttribute("marker", "provinceManageViceAudit");
				request.setAttribute("state", "cityManageChargeState");
				request.setAttribute("cityname", "cityManageDirectorAudit");
				request.setAttribute("task", task);
				return mapping.findForward("openDealWithView");
			} else if (task.getTaskDefinitionKey()
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

					request
							.setAttribute("auditor", list.get(0)
									.getCreateWork());

				}
				// 驳回信息
				request.setAttribute("auditListsize", auditMap.get("size"));
				request.setAttribute("auditList", auditMap.get("list"));
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"daiweiAudit")) {// 审核-区县传输局
				// 金额限制--start
				String threeMoneyLimitDicId = precheckRoleModel
						.getThreeMoneyLimtDicId();
				String fourMoneyLimitDicId = precheckRoleModel
						.getFourMoneyLimtDicId();
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

				TawSystemDictType dictypeThree = mgr
						.getDictByDictId(threeMoneyLimitDicId);
				TawSystemDictType dictypeFour = mgr
						.getDictByDictId(fourMoneyLimitDicId);
				double doubleThree = 0L;
				double doubleFour = 0L;

				if (dictypeThree != null
						&& StaticMethod.getFloatValue(dictypeThree
								.getDictRemark()) > -1) {
					doubleThree = Double.parseDouble(dictypeThree
							.getDictRemark());
				} else {

					request.setAttribute("msg", "派单审核后-金额设置，有问题，请联系管理员");
					return mapping.findForward("failure");
				}

				if (dictypeFour != null
						&& StaticMethod.getFloatValue(dictypeFour
								.getDictRemark()) > -1) {
					doubleFour = Double
							.parseDouble(dictypeFour.getDictRemark());
				} else {
					request.setAttribute("msg", "最后一处-金额限制，有问题；请联系管理员");
					return mapping.findForward("failure");
				}
				// 金额限制--end

				search.addFilterEqual("linkName", "worker");
				// gMap = getHandleList(gMap,transferHandleService, search,
				// handlelist,handleSize);

				request.setAttribute("PnrTransferHandleList", gMap.get("list"));
				request.setAttribute("listsize", gMap.get("size"));

				if (list.size() > 0) {
					request
							.setAttribute("auditor", list.get(0)
									.getCreateWork());
				}
				showReplySetAttribute(request,
						(List<PnrTransferOfficeHandle>) gMap.get("list"));
				// 根据不同等级的项目估算金额获取不同的抽查人员
				Double projectAmount = pnrTransferOffice.getProjectAmount() == null ? 0.0
						: pnrTransferOffice.getProjectAmount();
				String testAudit = "";
				if (projectAmount == 0.0 || projectAmount < doubleThree) {// 区县维护中心抽查
					testAudit = pnrTransferOffice.getCreateWork();
				} else if (projectAmount < doubleFour) {// 市线路维护中心抽查
					testAudit = pnrTransferOffice.getCityLineCharge();
				} else {// 市运维部抽查
					testAudit = pnrTransferOffice.getCityManageCharge();
				}
				request.setAttribute("testAudit", testAudit);
				// 施工队处理信息
				request.setAttribute("transferListsize", transferMap
						.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));

				// 距离显示
				try {
					String distance = pnrTransferOfficeService.getDistanceShow(
							processInstanceId, "pnrtranfault");
					request.setAttribute("distanceShow", distance);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return mapping.findForward("secondAudit");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"orderAudit")) {// 抽查
				return mapping.findForward("orderAudit");
			}
		}

		return mapping.findForward("error");
	}
	// 将传进来的文件剪切到资料库附件目录
	public String[] MyZipUtils(List<DownAttachMentModel> rPnrTransferList,
			String filePath, String userId) throws Exception {
		
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = f.format(date);
		
		SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMdd");
		String dates = f1.format(date);
		
		String path = filePath + result + "ZIP" + "-" + userId;
				
		String pathnew ="/partner/uploadfile/partner/uploadfile/pnrhome/"+dates;
		
//		System.out.println("-------------------pathnew="+pathnew);
		File dirName = new File(path);
		File zipName = new File(filePath+pathnew);
		try {

			if (!dirName.exists()) {
				dirName.mkdir();// 新建一个文件夹
			}
			//如果没有当天文件夹则创建
			if (!zipName.exists()) {
				zipName.mkdirs();// 新建一个文件夹
			}

			for (int i = 0; i < rPnrTransferList.size(); i++) {

				String sheetId = rPnrTransferList.get(i).getProcessInstanceId();// 工单号
				String cnName = rPnrTransferList.get(i).getAccessoriescnname();// 附件中文名
				String filename = rPnrTransferList.get(i).getAccessoriesname();// 英文名称
				String city = rPnrTransferList.get(i).getCity();// 城市
				String country = rPnrTransferList.get(i).getCountry();// 区县

				String fileStr = cnName.substring(0, cnName.lastIndexOf("."));
				String newpath = filePath + "/" + sheetId + "-" + city + "-"
						+ country + "-" + fileStr + "-" + filename;

				String fullpath = AccessoriesMgrLocator
						.getTawCommonsAccessoriesManagerCOS().getFilePath(
								rPnrTransferList.get(i).getAppcode());

				File linshi = new File(fullpath);
				if (!linshi.exists()) {
					linshi.mkdir();
				}

				String fullName = fullpath + filename;
				File myfile = new File(fullName);
				if (!myfile.exists()) {
					continue;
				}

				FileInputStream fis = new FileInputStream(fullName);
				FileOutputStream fos = new FileOutputStream(newpath);
				byte[] buff = new byte[1024];
				int readed = -1;
				while ((readed = fis.read(buff)) > 0)
					fos.write(buff, 0, readed);
				fis.close();
				fos.close();

				File subfile = new File(newpath);
				int index = newpath.lastIndexOf("/");
				String newfilename = newpath.substring(index);

				File newfile = new File(path + newfilename);
				subfile.renameTo(newfile);
			}

			CompressBook book = new CompressBook();
			
			// 循环剪切
			String zipfile = filePath + pathnew +"/uploadfile" + result + "ZIP" + "-" + userId + ".zip";// 给指定生成ZIP文件 
			String zipfiles = pathnew ;//数据库地址
			book.zip(zipfile, new File(path));
			String [] file=new String [3];
			//文件名
			file[0]="uploadfile" + result + "ZIP" + "-" + userId + ".zip";
			//数据库文件地址
			file[1]=pathnew;
			//文件大小
			File zip = new File(zipfile);
			String fileSize=zip.length()+"";
			file[2]=fileSize;
			return file;

		} catch (Exception e) {
			return null;
		} finally {
			// 删除临时文件夹 path
			// if (dirName.exists()) {
			// dirName.delete();// 删除临时文件夹
			// }
			deleteAll(dirName);
		}
	}
	

	
	/**
	 * 线路下载工单附件打包连接到资料库
	 * 
	 * @author cjg
	 * @title: downAttachMentToZipNew
	 * @date 20150429
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward downAttachMentToZipNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		//获取用户地市id
		String areaid =com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		String themeinterface = request.getParameter("themeinterface");// 工单类型
		String taskdefkey = request.getParameter("taskdefkey");// 所属环节

		// 构造查询条件
		ConditionQueryDAMModel conditionQueryModel = new ConditionQueryDAMModel();
		conditionQueryModel.setSendStartTime(sendStartTime);// 开始时间
		conditionQueryModel.setSendEndTime(sendEndTime);// 结束时间
		conditionQueryModel.setCity(region);// 地市
		conditionQueryModel.setCountry(country);// 区县
		
		conditionQueryModel.setArea(areaid);//当前登陆人地市id
		conditionQueryModel.setThemeinterface(themeinterface);// 工单类型
		conditionQueryModel.setTaskdefkey(taskdefkey);// 所属环节

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");

		// =================附件下载人数限制=================
		if (!pnrTransferNewPrecheckService.IsEnableDownAttachMent(userId)) {
			// 提示下载人数超限
			request.setAttribute("downlimiterror", "同时下载人数超限，请稍后再试！");

			// 获取地市列表
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			List city = PartnerCityByUser.getCityByProvince(province);
			request.setAttribute("city", city);

			// IPnrTransferNewPrecheckService pnrTransferNewPrecheckService =
			// (IPnrTransferNewPrecheckService)
			// getBean("pnrTransferNewPrecheckService");
			// 待下载附件 集合条数
			int total = 0;
			try {
				total = pnrTransferNewPrecheckService.getDownAttachMentCount(
						userId, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 待下载附件 集合
			List<DownAttachMentModel> rPnrTransferList = null;
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getDownAttachMentList(userId, conditionQueryModel,
								firstResult, endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 回传数据 供界面使用
			request.setAttribute("taskList", rPnrTransferList);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", total);

			request.setAttribute("startTime", sendStartTime);// 开始时间
			request.setAttribute("endTime", sendEndTime);// 结束时间
			request.setAttribute("region", region);// 地市
			request.setAttribute("country", country);// 区县
			request.setAttribute("themeinterface", themeinterface);// 工单类型
			request.setAttribute("taskdefkey", taskdefkey);// 所属环节

			return mapping.findForward("downAttachMentList");

		}
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String serial = f.format(date);
		java.util.Random r = new java.util.Random(100);
		int rdm = r.nextInt(100);

		String downInfoId = userId + "-" + serial + "-" + rdm;

		try {
			pnrTransferNewPrecheckService.addDownAttachMentInfo(downInfoId,
					userId);
			// =============================================================================

			// 待下载附件 集合
			List<DownAttachMentModel> rPnrTransferList = null;
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getDownAttachMentListAll(userId, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ==============================================================================
			String[] zipurl = null ;
			// 附件打包 下载
			try {
				String uploadpath = AccessoriesMgrLocator
						.getAccessoriesAttributes().getUploadPath();
				zipurl = MyZipUtils(rPnrTransferList, uploadpath, userId);// 调用本类内方法
				//上传附件
				String appId ="pnrhome";
				String name =zipurl[0];//获取文件名
				String savaPath =zipurl[1];//获取保存文件路径
				Long fileSize =Long.parseLong(zipurl[2].trim());//获取文件大小
				ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) getBean("ItawCommonsAccessoriesManager");
				List fileList=mgr.saveFiles(name,name, savaPath, fileSize, appId);
				
						
				
				//添加到资料库
				MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
				//MaterialLibForm materialLibForm = (MaterialLibForm) form;
				MaterialLib materialLib = new MaterialLib();
				
				
				materialLib.setSubject("线路工单下载附件包=="+name);
				materialLib.setOutline("概述");
				materialLib.setKeyWord("关键字");
				materialLib.setSpecialty("1122501");
				materialLib.setFile("'"+name+"'");
				materialLib.setPublisherId(sessionForm.getUserid());
				materialLib.setPublisherName(sessionForm.getUsername());
				materialLib.setPublisherDeptId(sessionForm.getDeptid());
				materialLib.setPublisherDeptName(sessionForm.getDeptname());
				materialLib.setPublishTime(new Date());
				materialLib.setScopeIds("101,2");
				materialLib.setScopeNames("中国联通山东分公司,巡检公司");
				
				//scopeIds 必填
				String[] scopeIdArr=materialLib.getScopeIds().split(",");
				MatlibScopeDept[] matlibScopeDeptArr=new MatlibScopeDept[scopeIdArr.length];
				int i=0;
				for (String string : scopeIdArr) {
					MatlibScopeDept matlibScopeDept=new MatlibScopeDept();
					matlibScopeDept.setScopedeptid(string);
					matlibScopeDeptArr[i]=matlibScopeDept;
					i++;
				}
					
				materialLibMgr.save(materialLib,matlibScopeDeptArr);
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 删除临时文件
//				File file = new File(zipurl);
//				file.deleteOnExit();
//				deleteAll(file);
			}
		} catch (Exception e) {

		} finally {
			pnrTransferNewPrecheckService.deleteDownAttachMentInfo(downInfoId);
		}

		return null;
	}
	
	/**
	 * 场景模板导出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward scenarioTemplateExcel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		//获取场景和定额信息
		String processInstanceId = request.getParameter("processInstanceId");
		String linkType = request.getParameter("linkType");
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		HSSFWorkbook wb = sceneTemplateService.scenarioTemplateExcel(processInstanceId, linkType, null);
		
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
	            String downFilename="场景模板导出.xls";//要下载的文件名称
	            if("need".equals(linkType)){//新建派发
	            	downFilename="新建工单场景模板导出.xls";
	    		}else if("worker".equals(linkType)){//工单处理
	    			downFilename="施工队回单场景模板导出.xls";
	    		}else if("orderAudit".equals(linkType)){//抽查
	    			downFilename="抽查场景模板模板导出.xls";
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
//	            System.out.println("Output   is   closed ");  
    	 } finally {
    		 	File fileXls =new File(path+fileName);
    		 	if(fileXls.exists()){
    		 		fileXls.delete();
    		 	}
    	 }
         
		return null;

	}
	
	/**
	 * 打开任意回退界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openRejectTaskView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("openRejectTaskView");
	}
	
	/**
	 * 测试任意回退
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward textRejectTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] roleIds = request.getParameterValues("example-basic");
		 for(int i = 0; i < roleIds.length; i++){  
			            System.out.println(roleIds[i]);  
			        }  


//		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
//		String processInstanceId = request.getParameter("procInstId");
//		String toTaskKey = request.getParameter("destTaskKey");
//		String rejectMessage = request.getParameter("rejectMessage");
//		// 设定驳回标志
//		Map<String, Object> variables = new HashMap<String, Object>(0);
////		variables.put("workOrderCheck", "superUser");
////		variables.put("cityManageChargeState", "rollback");
////		
////		variables.put("nextTaskAssignee", "superUser");
////		variables.put("provinceManageChargeState", "rollback");
//		
//		
//		variables.put("workOrderCheck", "superUser");
//		variables.put("provinceLineViceState", "rollback");
//		
////		pnrTransferNewPrecheckService.rejectToAnyTask( processInstanceId,  toTaskKey,
////				 rejectMessage,variables);
//		
//		RejectToAnyTaskUtil.rejectToAnyTask(processInstanceId, toTaskKey, rejectMessage, variables);
		
		//return mapping.findForward("textRejectTask");
		return null;
	}
	
	/**
	 * 打开驳回界面的公共方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showCommonRejectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sign = request.getParameter("sign");
		if(sign!=null&&!"".equals(sign)){
			request.setAttribute("signFlag",sign);
			return mapping.findForward("linkRejectedPage");
		}
		return mapping.findForward("error");		
	}
	
	/**
	 * 驳回 根据市运维业务主管环节进行区分
	 * 市公司运维部线路主管以上环节可以选择退到发起人、市运维业务主管、上一环节三种中的一种；市公司运维部线路主管以下环节退回到发起人、上一环节两种中的一种。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward linkRejected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
//		String taskDefKey="";
//		String taskDefKeyName="";
		try{
			String rejectLink = request.getParameter("rejectLink");//驳回到
			String handle = request.getParameter("handle");//当前环节
			String rejectMessage="";//驳回说明
			//默认驳回上一个环节。
			//市运维主任审核驳回到上一环节和驳回市运维业务主管，都是驳回到同一个环节。
			//工单发起审核驳回到上一环节和驳回发起人，都是驳回到同一个环节。
			if(rejectLink.equals("last")||(rejectLink.equals("cityManageExamine")&&handle.equals("cityManageDirectorAudit"))||(rejectLink.equals("need")&&handle.equals("workOrderCheck"))){
				this.rollback(mapping, form, request, response);//调用原来的驳回方法
			}else{
				String returnPerson="";
				Map<String, Object> variables = new HashMap<String, Object>(0);//驳回参数
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				String processInstanceId = request.getParameter("processInstanceId");//流程号
				String rejectState = request.getParameter("rejectState");//驳回状态名值对
				if(rejectLink.equals("cityManageExamine")){//驳回到市运维业务主管（即：流程图上的市运维主管审核环节）
					rejectMessage="从"+handle+"驳回到cityManageExamine";
					returnPerson=pnrTransferNewPrecheckService.getLinkProcessingUserId("cityManageExamine",processInstanceId);
					variables.put("cityManageChargeAudit",returnPerson);//市运维主管审核人
//					taskDefKey="cityManageExamine";
//					taskDefKeyName="市运维主管审核";
				}else if(rejectLink.equals("need")){//驳回发起人
					rejectMessage="从"+handle+"驳回到need";
					returnPerson=pnrTransferNewPrecheckService.getLinkProcessingUserId("need",processInstanceId);
					variables.put("initiator",returnPerson);//工单发起人
//					taskDefKey="need";
//					taskDefKeyName="工单发起";
				}
				variables.put(rejectState.substring(0,rejectState.indexOf("#")),rejectState.substring(rejectState.indexOf("#")+1));//当前环节的驳回标志
				//调用任意驳回方法
				RejectToAnyTaskUtil.rejectToAnyTask(processInstanceId, rejectLink, rejectMessage, variables);
				
				//修改主表状态
				String msg = "";
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
						.search(search);
				if (pnrTicketList != null) {
					PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
					msg = TASK_MESSAGE
							+ "  "
							+ TASK_NO_STR
							+ pnrTransferOffice.getProcessInstanceId()
							+ ","
							+ TASK_TITLE_STR
							+ pnrTransferOffice.getTheme()
							+ ","
							+ TASK_WORKORDERDEGREE
							+ getDictNameByDictid(pnrTransferOffice
									.getWorkOrderDegree()) + ","
							+ TASK_WORKORDERTYPE
							+ pnrTransferOffice.getWorkOrderTypeName() + ","
							+ TASK_SUBTYPE + pnrTransferOffice.getSubTypeName()
							+ "。";

					if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {
						// 删除会审辅助表记录的辅助信息
						pnrTransferOfficeService
								.deleteCountersignInfo(processInstanceId);
						pnrTransferOffice.setState(7);// 停止会审
					}

					// 市运维主管（有待办权利）-驳回时，将待办状态回执
					if (handle.equals(TASK_CITYMANAGEEXAMINE)) {
						if (3 == pnrTransferOffice.getState()) {

							pnrTransferOffice.setState(0);// 正常派发时刻
						}
					}
					// 将驳回标志置成1，代表该工单是驳回工单
					pnrTransferOffice.setRollbackFlag("1");
					//在工单主表中添加流程信息
					//processTaskService.setTvalueOfTask(processInstanceId, returnPerson, pnrTransferOffice, PnrTransferOffice.class, taskDefKey, taskDefKeyName);
					processTaskService.setTvalueOfTask(processInstanceId, returnPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
					pnrTransferOfficeService.save(pnrTransferOffice);
				}

				//插入环节表信息
				if (!handle.equals(TASK_TRANSFERHANDLE)) {
					IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
					PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
					// 工单ID
					String themeId = request.getParameter("themeId");
					// 工单主题
					String theme = request.getParameter("theme");
					String taskId = request.getParameter("taskId");
					// 将回退原因存入操作表中
					String rejectReason = request.getParameter("rejectReason");
					TawSystemSessionForm sessionform = (TawSystemSessionForm) request
							.getSession().getAttribute("sessionform");

					String userId = sessionform.getUserid();
					Date createTime = new Date();// 审批时间
					entity.setThemeId(themeId);
					entity.setTheme(theme);
					entity.setCheckTime(createTime);
					entity.setTaskId(taskId);
					entity.setProcessInstanceId(processInstanceId);
					entity.setAuditor(userId);
					entity.setHandleDescription(rejectReason);
					entity.setLinkName(handle);
					if ("orderAudit".equals(handle)) {
						entity.setOperation("09");// 抽查环节驳回 标识值为09
					} else {
						entity.setOperation("02");
					}
					entity.setOpinion(rejectMessage);//记录从哪个环节驳回到哪个环节
					entity.setReturnResult("rejectToAnyTask");//记录该环节为任意驳回
					transferHandleService.save(entity);
				}
				
				//发送短信
				CommonUtils.sendMsg(msg, returnPerson);
				out.print("true");
			}
		}catch(Exception e){
			out.print("false");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 市运维主管审批的时候,对项目金额进行判断(针对单个工单操作)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateBudgetAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnJson = "";
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String processInstanceId = request.getParameter("processInstanceId");//应急常规
		String precheckFlag = StaticMethod.nullObject2String(request.getParameter("precheckFlag"));//应急常规
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("precheckFlag", precheckFlag);
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		Map<String, String> resultMap = pnrTransferNewPrecheckService.validateBudgetAmount(sessionform,processInstanceId,paramMap);
		String result=resultMap.get("returnMsg");
		if("exceed".equals(result)){
			returnJson = "{\"result\":\"exceed\",\"content\":\"无法审批通过，已超出预算金额封顶值\"}";
		}else if("notExceed".equals(result)){
			returnJson = "{\"result\":\"notExceed\",\"content\":\"审批通过\"}";
		}else{
			returnJson = "{\"result\":\"error\",\"content\":\""+result+"\"}";
		}
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	/**
	 * 
	 * 批量处理是在市运维主管审核环节的工单，进行项目金额进行判断（针对批量处理的多个工单）	
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateBudgetAmountForBatchApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnJson = "";
		double totalEmergencyAmount=0.0;//应急工单的项目金额的总和
		String emergencyIds="";//记录应急工单的工单号
		double totalOtherAmount=0.0;//其他工单的项目金额的总和
		String otherIds="";//记录其他工单的工单号
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String cityManageStr = StaticMethod.nullObject2String(request.getParameter("cityManageStr"));
//		System.out.println("-----cityManageStr="+cityManageStr);
		String[] ids = cityManageStr.split("#");
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String[] id = ids[i].split(",");
				String taskId = id[0] == null ? "" : id[0].trim();
				String processInstanceId = id[1] == null ? "" : id[1].trim();
				String taskKey = id[2] == null ? "" : id[2].trim();
				
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTransferOffice = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId",processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTransferOffice = pnrTicketList.get(0);
				}
				if(pnrTransferOffice!=null){
					String precheckFlag = pnrTransferOffice.getPrecheckFlag();
					Double tempProjectAmount = pnrTransferOffice.getProjectAmount();
					double projectAmount=tempProjectAmount.doubleValue();
					if("101231401".equals(precheckFlag)){//应急工单
						totalEmergencyAmount=totalEmergencyAmount+projectAmount;
						emergencyIds+=processInstanceId+",";
					}else{//其他
						totalOtherAmount=totalOtherAmount+projectAmount;
						otherIds+=processInstanceId+",";
					}
				}else{
					continue;
				}
			}
		}
		
		String resultEmergency="";//应急工单的结果
		String resultOther="";//非应急工单的结果
		String resultErr="";//其他报错的信息
		
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		
		//1.验证应急工单的总金额是否超标，如果超标，返回差值
		Map<String,String> emergencyParam=new HashMap<String,String>();
		emergencyParam.put("precheckFlag", "101231401");
		emergencyParam.put("isBatch", "yes");
		emergencyParam.put("totalAmount",Double.toString(totalEmergencyAmount));
//		System.out.println("------totalEmergencyAmount="+totalEmergencyAmount);
		Map<String, String> emergencyMap = pnrTransferNewPrecheckService.validateBudgetAmount(sessionform,"",emergencyParam);
		String emergency="";
		emergency=emergencyMap.get("returnMsg");
		if(!"notExceed".equals(emergency)){
			if("exceed".equals(emergency)){
				resultEmergency=emergencyIds+"为应急工单，无法审批通过，已超出预算金额封顶值，超出金额为："+emergencyMap.get("exceedAmount")+"。";
			}else{
				resultErr=emergency;
			}
		}
		
		//2.验证其他工单的总金额是否超标，如果超标，返回差值
		Map<String,String> otherParam=new HashMap<String,String>();
		otherParam.put("precheckFlag", "other");
		otherParam.put("isBatch", "yes");
		otherParam.put("totalAmount",Double.toString(totalOtherAmount));
//		System.out.println("------totalOtherAmount="+totalOtherAmount);
		Map<String, String> otherMap= pnrTransferNewPrecheckService.validateBudgetAmount(sessionform,"",otherParam);
		String other="";
		other=otherMap.get("returnMsg");
		if(!"notExceed".equals(other)){
			if("exceed".equals(other)){
				resultOther=otherIds+"为非应急工单，无法审批通过，已超出预算金额封顶值，超出金额为："+otherMap.get("exceedAmount")+"。";
			}else{
				if("".equals(resultErr)){
					resultErr=other;
				}
			}
		}
		
		if("".equals(resultEmergency)&&"".equals(resultOther)&&"".equals(resultErr)){
			returnJson = "{\"result\":\"success\",\"content\":\"金额验证通过\"}";
		}else{
			returnJson = "{\"result\":\"failure\",\"content\":\""+resultEmergency+resultOther+resultErr+"\"}";
		}
		
		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	/**
	 * 	 商城工单查询
	 	 * @author WangJun
	 	 * @title: queryShoppingMallList
	 	 * @date Nov 28, 2015 10:26:17 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward queryShoppingMallList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		int pageSize = CommonUtils.PAGE_SIZE;
		//int pageSize = 10;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");//开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");//结束时间
		String wsNum = request.getParameter("wsNum");//工单号
		String shopNum = request.getParameter("shopNum");//商城工单号
		
		//拼查询条件
//		String whereStr="where 1=1";
		String whereStr="where t.process_instance_id = m.process_instance_id";
		List<Object> paramList = new ArrayList<Object>();
		if(sendStartTime!=null&&!"".equals(sendStartTime)){
			whereStr+=" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			paramList.add(sendStartTime);
		}
		if(sendEndTime!=null&&!"".equals(sendEndTime)){
			whereStr+=" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			paramList.add(sendEndTime);
		}
		if(wsNum!=null&&!"".equals(wsNum)){
			whereStr+=" and t.process_instance_id = ?";
			paramList.add(wsNum.trim());
		}
		if(shopNum!=null&&!"".equals(shopNum)){
			whereStr+=" and t.order_id = ?";
			paramList.add(shopNum.trim());
		}
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		PreparedStatement pstmt2=null;
		ResultSet rs2=null;
		
		int total=0;
		ArrayList list=new ArrayList();
		try {
			//1.链接数据库
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			con= ed.getCon();
			
			//2.查询条数
			String sqlCount="select count(t.process_instance_id) as total from pnr_act_oper_return_result t, pnr_act_transfer_office_main m "+whereStr;
//			System.out.println("------sqlCount="+sqlCount);
			pstmt = con.prepareStatement(sqlCount); 
			this.pstSetObject(pstmt, paramList);
			rs = pstmt.executeQuery();   
			if(rs.next()){   
				total=rs.getInt("total");   
			}   
			
			//3.查询分页数据
			String sql=	"select temp2.*" +
						"  from (select temp1.*, rownum num" + 
						"          from (select t.order_id," + 
						"                       t.process_instance_id," + 
						"                       m.theme," + 
						"                       m.send_time," + 
						"                       t.backfill_time," + 
						"                       m.themeinterface," + 
						"                       t.order_price," + 
						"                       m.city," + 
						"                       m.project_amount," + 
						"                       nvl(m.worker_project_amount, 0) as worker_project_amount," + 
						"                       nvl(m.orderaudit_project_amount, 0) as orderaudit_project_amount" + 
						"                  from pnr_act_oper_return_result   t," + 
						"                       pnr_act_transfer_office_main m " + whereStr +" ) temp1" + 
						"         where rownum <= ?) temp2" + 
						" where temp2.num > ?";
//			System.out.println("------sql="+sql);
			
			//分页设置
			paramList.add(endResult * pageSize);
			paramList.add(firstResult * pageSize);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder.getInstance().getBean("ItawSystemAreaManager");
			pstmt2 = con.prepareStatement(sql);
			this.pstSetObject(pstmt2, paramList);
			rs2 = pstmt2.executeQuery();
			while(rs2.next()){ 
				HashMap map=new HashMap();
				map.put("orderId",rs2.getString("order_id"));//商城订单号
				map.put("processInstanceId", rs2.getString("process_instance_id"));//现场工单号
				map.put("theme", rs2.getString("theme"));//主题
				//map.put("insertTime", rs2.getString("insert_time"));
				if(rs2.getTimestamp("send_time")!=null){
					map.put("sendTime",format.format(rs2.getTimestamp("send_time")));//发单时间
				}
				if(rs2.getTimestamp("backfill_time")!=null){
					map.put("backfillTime",format.format(rs2.getTimestamp("backfill_time")));//归档时间
				}
				map.put("themeinterface", this.changeThemeinterfaceIntoStr(rs2.getString("themeinterface")));//工单类型
				map.put("orderPrice", rs2.getString("order_price"));//回填金额
				if(rs2.getString("city")!=null&&!"".equals(rs2.getString("city"))){
					TawSystemArea tawSystemArea = mgr.getAreaByAreaId(rs2.getString("city"));
					if(tawSystemArea != null){
						map.put("city",tawSystemArea.getAreaname());//地市
					}					
				}
				map.put("projectAmount", rs2.getObject("project_amount"));//新建项目估算
				map.put("workerProjectAmount", rs2.getObject("worker_project_amount"));//施工队项目估算
				map.put("orderauditProjectAmount", rs2.getObject("orderaudit_project_amount"));//抽查项目估算
				list.add(map);   
			} 
			
//			//1.链接数据库
//			Class.forName("com.mysql.jdbc.Driver");
//			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root","root");
//			//con=DriverManager.getConnection("jdbc:mysql://10.72.32.238:3306/netshopcs2015","scene","qae3#53er");
//			
//			//2.查询条数
//			String sqlCount="select count(*) as total from netshop_scene t "+whereStr;
//			System.out.println("------sqlCount="+sqlCount);
//			pstmt = con.prepareStatement(sqlCount);   
//			rs = pstmt.executeQuery();   
//			if(rs.next()){   
//				total=rs.getInt("total");   
//			}   
//			
//			//3.查询分页数据
//			String sql="select mall_number,other_sys_number,theme,draft_time,backfill_time,repair,backfill_amount,city from netshop_scene t "+whereStr+" order by draft_time limit "+firstResult*pageSize+","+pageSize; 
//			System.out.println("------sql="+sql);
//			pstmt2 = con.prepareStatement(sql);  
//			rs2 = pstmt2.executeQuery(sql);
//			while(rs2.next()){ 
//				HashMap map=new HashMap();
//				map.put("mallNumber",rs2.getString("mall_number"));//商城订单号
//				map.put("otherSysNumber", rs2.getString("other_sys_number"));//现场工单号
//				map.put("theme", rs2.getString("theme"));//主题
//				//map.put("insertTime", rs2.getString("insert_time"));
//				map.put("draftTime", rs2.getObject("draft_time"));//发单时间
//				map.put("backfillTime", rs2.getObject("backfill_time"));//归档时间
//				map.put("repair", rs2.getObject("repair"));//工单类型
//				map.put("backfillAmount", rs2.getObject("backfill_amount"));//回填金额
//				map.put("city", rs2.getObject("city"));//地市
//				list.add(map);   
//			} 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs2.close();
				rs.close();
				pstmt2.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("taskList", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("shopNum", shopNum);
		return mapping.findForward("shoppingMallList");
	}
	
	//工单类型转换
	public static String changeThemeinterfaceIntoStr(String id){
		String str="";
		if(id!=null&&!"".equals(id)){
			if("maleGuest".equals(id)){
				str="公客";
			}else if("themeInterface".equals(id)||"interface".equals(id)){
				str="本地网预检预修";
			}else if("transferOffice".equals(id)){
				str="抢修工单";
			}else if("overhaul".equals(id)){
				str="大修";
			}else if("oltBbuProcess".equals(id)){
				str="oltbbu机房";
			}else if("reform".equals(id)){
				str="改造";
			}else if("arteryPrecheck".equals(id)){
				str="干线预检预修";
			}
		}
		return str;
	}
	
	//把条件作为参数传给PreparedStatement
	 private void pstSetObject(PreparedStatement pstm,List<Object> list) throws SQLException{
	        if(list != null){
	            for(int i = 0; i < list.size(); i++){
	                pstm.setObject(i+1, list.get(i));
	            }
	        }
	    }
	 /**
		 * 进入数据提取页面
		 * 
		 * @author chujingang
		 * @title: extractDataView
		 * @date Feb 1, 2016 3:28:21 PM
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 * @throws Exception
		 *             ActionForward
		 */
		public ActionForward extractDataView(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response){

			return mapping.findForward("extractData");
		}
		 /**
		 * mysql数据库提取数据到oracle
		 * 
		 * @author chujingang
		 * @title: extractData
		 * @date Feb 1, 2016 3:28:21 PM
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException 
		 * @throws Exception
		 *             ActionForward
		 */
		public ActionForward extractData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException{
			
			PrintWriter out = response.getWriter();
			try{
			String startDate=StaticMethod.null2String(request.getParameter("sheetAcceptLimit"));
			String endDate=StaticMethod.null2String(request.getParameter("sheetCompleteLimit"));
				if(startDate!="" && endDate!=""){
					long  count=0;
					long  startTime=System.currentTimeMillis();
					/**获取mysql netshop_scene表数据**/
					Connection mcon=MySqlDBUtil.getConnection();
					String msql="select n.other_sys_number,n.mall_number,n.backfill_time,n.backfill_amount from netshop_scene n";
					String whereSql="";
					whereSql=" where backfill_time BETWEEN '"+startDate+"' and '"+endDate+"' ";
					
					msql+=whereSql;
					int mcount=MySqlDBUtil.getCount(mcon, msql);
//					System.out.println("count========"+mcount);
					if(mcount<100000){//如果数据量小与10w则匹配备份
						List<Map<String, Object>> mlist=MySqlDBUtil.queryMapList(mcon, msql);
						MySqlDBUtil.closeConnection(mcon);
						/** end */	
						if(mlist.size()>0){
							/**获取oracle pnr_act_oper_return_result表数据**/
							Connection ocon=OracleDBUtil.getConnection();
							String osql="select r.process_instance_id from pnr_act_oper_return_result r";
							List<Map<String, Object>> olist=OracleDBUtil.queryMapList(ocon, osql);
							/** end **/
							/**比较other_sys_number字段和process_instance_id字段*/
							for(int i=0;i<mlist.size();i++){
								Boolean istrue=true;
								for(int y=0;y<olist.size();y++){
									if(mlist.get(i).get("other_sys_number").equals(olist.get(y).get("PROCESS_INSTANCE_ID"))){
//										System.out.println("other_sys_number======"+mlist.get(i).get("other_sys_number"));
//										System.out.println("process_instance_id====="+olist.get(y).get("PROCESS_INSTANCE_ID"));
										istrue=false;
										break;
									}
								}
								/**如果istrue等于true说明mysql netshop_scene表中的other_sys_number字段中的数据在
								 * oracle pnr_act_oper_return_result表中的PROCESS_INSTANCE_ID字段不存在，那就把
								 * 此条数据备份到oracle pnr_act_oper_return_result表中*/
								if(istrue){
									String s=UUID.randomUUID().toString();
									String uuid=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
									String saveSql="insert into pnr_act_oper_return_result (ID,TICKET_TYPE,PROCESS_INSTANCE_ID,ORDER_ID,backfill_time,ORDER_PRICE,insert_time)" +
											" values('"+uuid+"'," +
											" ''," +
											" '"+mlist.get(i).get("other_sys_number")+"'," +
											" '"+mlist.get(i).get("mall_number")+"'," +
											" to_date('"+mlist.get(i).get("backfill_time").toString().substring(0, 19)+"','YYYY-MM-DD HH24:MI:SS')," +
											" '"+mlist.get(i).get("backfill_amount")+"'," +
											" to_date('"+mlist.get(i).get("backfill_time").toString().substring(0, 19)+"','YYYY-MM-DD HH24:MI:SS')" +
											")";
//									System.out.println("插入sql==="+saveSql);
									try{
									int num=OracleDBUtil.save(ocon, saveSql);
									count+=1;
									}catch (Exception e) {
										e.printStackTrace();
										try {
											ocon.rollback();
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} 
										out.print("提取错误,请联系管理员!");
									}finally{
										OracleDBUtil.closeConnection(ocon);
									}
								}
							}
							long endTime=System.currentTimeMillis();		
//							System.out.println("程序运行时间："+(endTime-startTime)+" ms,共提取:"+count+"条数据!");
							out.print("提取成功,共提取:"+count+"条数据!");
						}else{
//							System.out.println("Mysql-netshop_scene表中无数据!");
							out.print("网上商城场景表中无数据,无法提取!");
						}
					}else{
//						System.out.println("netshop_scene表数据量大于100000,程序不能进行备份,请按backfill_time字段进行时间段查询!");
						out.print("网上商城场景表查询出数据量超出100000条,程序不能进行提取,请把时间段缩小!");
					}
				}
			}catch (Exception e2){
				e2.printStackTrace();
				out.print("提取错误,请联系管理员!");
			} 
			
			return null;
		}
		
		/**
		 *   测试自由流
		 	 * @author 你的名字
		 	 * @title: newTask
		 	 * @date Jan 14, 2016 9:18:08 AM
		 	 * @param mapping
		 	 * @param form
		 	 * @param request
		 	 * @param response
		 	 * @return
		 	 * @throws Exception ActionForward
		 */
		public ActionForward openFreeFlow(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			return mapping.findForward("openFreeFlow");
		}
		
		/**
		 * 	 
		 	 * @author 你的名字
		 	 * @title: doFreeFlow
		 	 * @date Jan 14, 2016 9:32:09 AM
		 	 * @param mapping
		 	 * @param form
		 	 * @param request
		 	 * @param response
		 	 * @return
		 	 * @throws Exception ActionForward
		 */
		public ActionForward doFreeFlow(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
//			String processInstanceId = StaticMethod.null2String(request.getParameter("processInstanceId"));
//			String taskId = StaticMethod.null2String(request.getParameter("taskId"));
//			String activityId = StaticMethod.null2String(request.getParameter("activityId"));
//			String pesornKey = StaticMethod.null2String(request.getParameter("pesornKey"));
//			String pesornValue = StaticMethod.null2String(request.getParameter("pesornValue"));
//			String flagKey = StaticMethod.null2String(request.getParameter("flagKey"));
//			String flagValue = StaticMethod.null2String(request.getParameter("flagValue"));
//			String turnMessage = StaticMethod.null2String(request.getParameter("turnMessage"));
//			Map<String, Object> params=new HashMap<String, Object>();
//			if(!"".equals(pesornKey)&&!"".equals(pesornValue)){
//				params.put(pesornKey, pesornValue);
//			}
//			if(!"".equals(flagKey)&&!"".equals(flagValue)){
//				params.put(flagKey, flagValue);
//			}
//			//调用任意流
//			FreeFlowUtils.turnTransition(taskId,activityId,params,turnMessage);
//			
//			//往主表里保存基本信息
//			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//			Search search = new Search();
//			search.addFilterEqual("processInstanceId", processInstanceId);
//			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
//			List<PnrTransferOffice> list = t.getResult();
//			// 回复message
//			PnrTransferOffice pnrTransferOffice = list.get(0);
//			processTaskService.setTvalueOfTask(processInstanceId, pesornValue, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
//			pnrTransferOfficeService.save(pnrTransferOffice);
			
			//prepare data packet
			Map<String,Object> variables=new HashMap<String,Object>();
			Map<String,Object> subVariables=new HashMap<String,Object>();
			variables.put("protocol", "UM32");
			variables.put("repository", "10.10.38.99:/home/shirdn/repository");
			variables.put("in", subVariables);
			variables.put("out", new HashMap<String,Object>());
			
			ProcessInstance pi=runtimeService.startProcessInstanceByKey("Mainprocess",variables);
			
			return mapping.findForward("showSuccess");
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
				pnrTransferOffice.setKeyWord("101230807");
				pnrTransferOffice.setMainQuantityOther(netResInspectDraft.getFaultDescription());
				pnrTransferOffice.setNetResInspectId(netResInspectDraft.getInspectProcessInstanceId());
				pnrTransferOffice.setProcessInstanceId(processInstanceId);
			}
			//获取附件信息
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			String sheetAccessories = pnrTransferOfficeService.getAttachmentNamesByProcessInstanceId(processInstanceId);
			List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService.newShowSheetAccessoriesList(processInstanceId);
			
			CommonUtils.getCompetenceLimit(request);
			request.setAttribute("mainFaultOccurTime", new Date());// 申请提交时间显示为系统当前时间
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);
			request.setAttribute("sheetAccessories", accessoriesList);
			return mapping.findForward("new");
		}
		
		/**
		 * 	 查询省公司抽检列表
		 	 * @author Wangjun
		 	 * @title: querySamplingList
		 	 * @date Aug 4, 2016 10:11:32 AM
		 	 * @param mapping
		 	 * @param form
		 	 * @param request
		 	 * @param response
		 	 * @return
		 	 * @throws Exception ActionForward
		 */
		public ActionForward querySamplingList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String queryFlay = "0"; //查询标识
			int pageSize = CommonUtils.PAGE_SIZE;
			//int pageSize = 1;
			int total = 0;
			List<TaskModel> taskList = null;
			String condition = "";
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			List city = PartnerCityByUser.getCityByProvince(province);
			request.setAttribute("city", city);
			
			String sendStartTime = StaticMethod.null2String(request.getParameter("sheetAcceptLimit"));//派单开始时间
			String sendEndTime = StaticMethod.null2String(request.getParameter("sheetCompleteLimit"));//派单结束时间
			String region = StaticMethod.null2String(request.getParameter("region"));// 地市
			String country = StaticMethod.null2String(request.getParameter("country"));// 区县
			String wsNum = StaticMethod.null2String(request.getParameter("wsNum"));// 工单号
			String wsTitle = StaticMethod.null2String(request.getParameter("wsTitle"));// 工单主题
			String samplState = StaticMethod.null2String(request.getParameter("samplState"));// 状态
			String processType = StaticMethod.null2String(request.getParameter("processType"));// 流程类型
			
			
			if(!"".equals(sendStartTime) && !"".equals(sendEndTime)){
				//String noIndex = StaticMethod.null2String(request.getParameter("noIndex"));
				String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
//				if("1".equals(noIndex)){
//					pageIndexString = StaticMethod.null2String(request.getParameter("indexStr"));
//				}else{
//					pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
//				}
				
				int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
				int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
				TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				String userId = sessionForm.getUserid();
				
				ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
				conditionQueryModel.setSendStartTime(sendStartTime);
				conditionQueryModel.setSendEndTime(sendEndTime);
				conditionQueryModel.setCity(region);
				conditionQueryModel.setCountry(country);
				conditionQueryModel.setWorkNumber(wsNum);
				conditionQueryModel.setTheme(wsTitle);
				conditionQueryModel.setStatus(samplState);
				conditionQueryModel.setProcessType(processType);
				
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				
				try {
					total = pnrTransferNewPrecheckService.querySamplingCount(userId,conditionQueryModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				try {
					taskList = pnrTransferNewPrecheckService.querySamplingList(userId,conditionQueryModel,firstResult, endResult, pageSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				queryFlay = "1";
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				condition = pnrTransferOfficeService.joinSamplingListCondition(pageIndexString, conditionQueryModel);
			}
			
			request.setAttribute("condition",condition);
			request.setAttribute("queryFlay", queryFlay);
			request.setAttribute("taskList", taskList);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", total);
			request.setAttribute("startTime", sendStartTime);
			request.setAttribute("endTime", sendEndTime);
			request.setAttribute("region", region);
			
			request.setAttribute("country", country);
			request.setAttribute("wsNum", wsNum);
			request.setAttribute("wsTitle", wsTitle);
			request.setAttribute("samplState", samplState);
			request.setAttribute("processType", processType);
			return mapping.findForward("samplingList");
		}
		
		/**
		 * 	 标记抽检
		 	 * @author WANGJUN
		 	 * @title: markSampling
		 	 * @date Aug 4, 2016 3:52:25 PM
		 	 * @param mapping
		 	 * @param form
		 	 * @param request
		 	 * @param response
		 	 * @return
		 	 * @throws Exception ActionForward
		 */
		public ActionForward markSampling(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String resultMsg = "error";
			String contentMsg = "";
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userId = sessionForm.getUserid();
			String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));//工单号
			String flag = StaticMethod.nullObject2String(request.getParameter("flag"));//标记、取消标识
			if(!"".equals(processInstanceId)){
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		        Search search = new Search();
		        search.addFilterEqual("processInstanceId", processInstanceId);
		        PnrTransferOffice p = pnrTransferOfficeService.searchUnique(search);  
		        if(p != null){
		        	String samplingState = p.getSamplingState();
		        	if("2".equals(samplingState)){
		        		 contentMsg = "工单已抽检，不能操作!";
		        	}else{
		        		try{
			        		if("marked".equals(flag)){
			        			p.setSamplingState("1"); //已标记
			        			p.setSamplingMarkUserid(userId);//标记人
			        		}else if("cancelled".equals(flag)){
			        			p.setSamplingState(null); //未处理
			        			p.setSamplingMarkUserid(null);
			        		}
					        pnrTransferOfficeService.save(p);
					        resultMsg = "success";
					        contentMsg = "操作成功!";
			        	}catch(Exception e){
			        		e.printStackTrace();
			        		contentMsg = "标记异常";
			        	}
		        	}
		        }else{
		        	 contentMsg = "未获取到工单信息!";
		        }
			}else{
				 contentMsg = "工单号不能为空!";
			}
			PrintWriter out = response.getWriter();
			String returnJson = "{\"result\":\""+resultMsg+"\",\"content\":\""+contentMsg+"\"}";
			out.print(returnJson);	
			return null;
		}
		
		/** 
		 *   保存抽检结果
		 	 * @author WANGJUN
		 	 * @title: saveSamplingJudgments
		 	 * @date Aug 5, 2016 10:07:13 AM
		 	 * @param mapping
		 	 * @param form
		 	 * @param request
		 	 * @param response
		 	 * @return
		 	 * @throws Exception ActionForward
		 */
		public ActionForward saveSamplingJudgments(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userId = sessionForm.getUserid();
			
			String returnView = "failure";
			String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));//工单号
			String samplingJudgments = StaticMethod.nullObject2String(request.getParameter("samplingJudgments"));//抽检结果
			
			ParameterModel param = new ParameterModel();
			param.setProcessInstanceId(processInstanceId);
			param.setSamplingJudgments(samplingJudgments);
			param.setUserId(userId);
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			String msg = pnrTransferOfficeService.saveSamplingJudgments(param);
			if("".equals(msg)){
				returnView = "showSuccess";
				String cond = StaticMethod.nullObject2String(request.getParameter("condition"));//查询条件
				try{
					String condition = pnrTransferOfficeService.analysisSamplingListCondition(cond);
					request.setAttribute("condition", condition);
					request.setAttribute("success", "sampling");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			request.setAttribute("msg",msg);
			return mapping.findForward(returnView);
		}
		
		/**
		 *   本地网
		 *   省线路主管审核和省线路总经理审核直接通过
		 	 * @author WANGJUN
		 	 * @title: autoByPassProvincialLine
		 	 * @date Jan 3, 2017 3:28:20 PM
		 	 * @param processInstanceId 流程号
		 	 * @param taskId 任务号
		 	 * @param nextPerson 省运维主管审核人
		 	 * @param currentLink 当前环节：市运维主任审核或者市公司副总审核
		 	 * @param themeId 主表中的ID
		 	 * @param theme 工单主题
		 	 * @param userId void 当前用户
		 */
		private void autoByPassProvincialLine(String processInstanceId,String taskId,String nextPerson,String currentLink,String themeId,String theme,String userId){
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map1 = new HashMap<String, String>();
			//1.市运维主任审核提交到省线路主管审核
			if("cityManageDirectorAudit".equals(currentLink)){//市运维主任审核 
				map1.put("cityManageDirectorState", "province");
			}
			//市公司副总审核提交到省线路主管审核
			else if("cityViceAudit".equals(currentLink)){//市公司副总审核
				map1.put("cityDiveState", "handle");
				
			}
			map1.put("nextTaskAssignee", "superUser");
			//formService.submitTaskFormData(taskId, map1);
			this.submitTaskFromWSDL(processInstanceId, taskId, map1);
			
			//2.省线路主管审核提交到省线路总经理审核
			TaskService taskService = (TaskService) getBean("taskService");
			Task task2 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();  
			String taskId2 = task2.getId();
			System.out.println("-------taskId2="+taskId2);
			PnrTransferOfficeHandle entity2 = new PnrTransferOfficeHandle();

			entity2.setThemeId(themeId);
			entity2.setTheme(theme);
			entity2.setTaskId(taskId2);
			entity2.setCheckTime(new Date());
			entity2.setHandleDescription("系统自动通过");
			entity2.setAuditor(userId);
			entity2.setProcessInstanceId(processInstanceId);
			entity2.setDoPerson("superUser");
			entity2.setLinkName("provinceLineExamine");
			entity2.setOperation("01");
			transferHandleService.save(entity2);
			
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("provinceLineChargeState", "handle");
			map2.put("provinceLineViceAudit", "superUser");
			//formService.submitTaskFormData(taskId2, map2);
			this.submitTaskFromWSDL(processInstanceId,taskId2,map2);
			
			//3.省线路总经理审核提交到省运维主管审核
			Task task3 = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult(); 
			String taskId3 = task3.getId();
			System.out.println("-------taskId3="+taskId3);
			PnrTransferOfficeHandle entity3 = new PnrTransferOfficeHandle();
			entity3.setThemeId(themeId);
			entity3.setTheme(theme);
			entity3.setTaskId(taskId3);
			entity3.setCheckTime(new Date());
			entity3.setHandleDescription("系统自动通过");
			entity3.setAuditor(userId);
			entity3.setProcessInstanceId(processInstanceId);
			entity3.setDoPerson("superUser");
			entity3.setLinkName("provinceLineViceAudit");
			entity3.setOperation("01");
			transferHandleService.save(entity3);
			
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("provinceLineViceState", "handle");
			map3.put("provinceManageCharge",nextPerson);// 省运维主管
			//formService.submitTaskFormData(taskId3, map3);
			this.submitTaskFromWSDL(processInstanceId, taskId3, map3);
		}
		
		/**
		 *   通过webservice接口提交下一环节
		 	 * @author WANGJUN
		 	 * @title: submitTaskFromWSDL
		 	 * @date Jan 3, 2017 5:09:58 PM
		 	 * @param taskId 任务id
		 	 * @param map void 流程提交时需要的参数
		 */
		private void submitTaskFromWSDL(String processInstanceId,String taskId,Map<String, String> map){
			System.out.println("通过webservice接口提交下一环节");
			BocoLog.info(this,1350,"PnrTransferNewPrecheckAction-transferProgram-传递的参数processInstanceId（工单号）："+processInstanceId);
			BocoLog.info(this,1351,"PnrTransferNewPrecheckAction-transferProgram-传递的参数taskId（当前流程Id）:"+taskId);
			
			//接口地址
//			String url ="http://localhost:8080/partner/services/TaskFlowsService?wsdl"; //测试地址
			String url ="http://134.34.63.6:8085/partner/services/TaskFlowsService?wsdl";
			
			//接口报文
			String msgStr ="";
			
			//拼接属性
			for (Map.Entry<String,String> entry : map.entrySet()) {
				String name = entry.getKey();
				String value = entry.getValue();
			    msgStr+="<recordInfo>\n" +
						"<fieldInfo>\n" + 
						"<fieldChName>"+name+"</fieldChName>\n" + 
						"<fieldEnName>"+name+"</fieldEnName>\n" + 
						"<fieldContent>"+value+"</fieldContent>\n" + 
						"</fieldInfo>\n" + 
						"</recordInfo>";
			    BocoLog.info(this,1359,"PnrTransferNewPrecheckAction-transferProgram-传递的参数:流程引擎-"+name+":"+value);
			}
			
			//拼接报文头+任务号+属性+报文尾
			msgStr ="<opDetail>"+
					"<recordInfo>\n" +
					"<fieldInfo>\n" + 
					"<fieldChName>taskId工单任务id</fieldChName>\n" + 
					"<fieldEnName>taskId</fieldEnName>\n" + 
					"<fieldContent>"+taskId+"</fieldContent>\n" + 
					"</fieldInfo>\n" + 
					"</recordInfo>"+
					msgStr+
					"</opDetail>";
			
			InterfaceUtil.gkAgencyMethod(url,"submitTaskFormData" , msgStr);		
			
			BocoLog.info(this,1363,"PnrTransferNewPrecheckAction-transferProgram-流程引擎提交结果:提交完成@！");
		}
}
