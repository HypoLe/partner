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
import java.lang.reflect.Field;
import java.sql.Clob;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Decoder;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.MasteDataTask;
import com.boco.activiti.partner.process.model.MasteRelTaskItem;
import com.boco.activiti.partner.process.model.MasteSelCopyTask;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrOltBbuOfficeRelation;
import com.boco.activiti.partner.process.model.PnrOltBbuRoom;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.model.PnrTransferSpotcheck;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.MasterScene;
import com.boco.activiti.partner.process.po.MaterialModel;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.SceneTableModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TheadModel;
import com.boco.activiti.partner.process.po.TheadModelSel;
import com.boco.activiti.partner.process.po.TransferOfficeHandleModel;
import com.boco.activiti.partner.process.service.IMasteDataTaskService;
import com.boco.activiti.partner.process.service.IMasteRelTaskItemService;
import com.boco.activiti.partner.process.service.IMasteSelCopyTaskService;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrOltBbuOfficeRelationService;
import com.boco.activiti.partner.process.service.IPnrOltBbuRoomService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferOverhaulRemakeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferSpotcheckService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.activiti.partner.process.service.PnrPrecheckPhotoService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.util.UUIDHexGenerator;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.accessories.util.CompressBook;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
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
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.RejectToAnyTaskUtil;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrOltBbuRoomAction extends SheetAction {

	private final String processDefinitionKey = "oltBbuProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger
			.getLogger(PnrTransferPrecheckAction.class);
	// 发信息用的常量
	public final static String TASK_MESSAGE = "OLT-BBU机房优化申请流程工单已派至您工位待处理：";
	public final static String TASK_LINK_NAME = "当前环节：";
	
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
	private final static String TASK_ORDERAUDIT = "orderAudit";
	private final static String TASK_WORKER = "worker";
	// 工单删除状态
	public final static Integer TASK_DELETE_STATE = 1;
	/** 动态获取角色id实体类 */
	private PrecheckRoleModel precheckRoleModel = (PrecheckRoleModel) ApplicationContextHolder
			.getInstance().getBean("precheckRoleModel");
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	
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
		//request.setAttribute("mainFaultOccurTime", new Date());// 申请提交时间显示为系统当前时间
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
		String taskDefKey = "cityLineExamine";
		String taskDefKeyName = "市线路主管审核";
		
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
		
		//1.获取工单信息
		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		setTransferOfficeByRequest(request, pnrTransferOffice);
		// 设置工单的区域
		pnrTransferOffice.setCity(city);
		pnrTransferOffice.setCountry(areaid);
		
		//2.获取手填的机房信息、优化信息
		PnrOltBbuOfficeRelation pnrOltBbuOfficeRelation=new PnrOltBbuOfficeRelation();
		setPnrOltBbuOfficeRelationByRequest(request, pnrOltBbuOfficeRelation);

		String taskAssignee = pnrTransferOffice.getTaskAssignee();
		String initiator = pnrTransferOffice.getInitiator();
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String themeId = pnrTransferOffice.getId();
		String theme = pnrTransferOffice.getTheme();
		String subType = pnrTransferOffice.getSubTypeName();//子场景
		String workOrderType = pnrTransferOffice.getWorkOrderTypeName();//主场景

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//工单编号
		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");
		String sheetId = "";
		String tempSheetId = request.getParameter("sheetId");
		if (tempSheetId != null && !tempSheetId.equals("")) {
			sheetId = tempSheetId;
		} else {
			if (processInstanceId == null || "".equals(processInstanceId)) {

				try {
					sheetId = pnrOltBbuRoomService.getSheetId(
							processDefinitionKey, userId, "JF");

				} catch (Exception e) {
					request.setAttribute("msg", "请联系管理员，生成工单编号错误："
							+ e.toString());
					return mapping.findForward("failure");

				}
			}
		}
		
		// 发起流程
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
		if(!"true".equals(isDraft)){
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("cityLineChargeAduit", taskAssignee);
			formService.submitTaskFormData(taskId, map);
		}
		

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}
		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		// 编号
		pnrTransferOffice.setSheetId(sheetId);
		pnrOltBbuOfficeRelation.setProcessInstanceId(processInstanceId);//设置流程机房关联表中的流程id

		//3.附件处理（新建线路路由简图、附件） 
		// 清空该环节的附件
		if (themeId != null && !"".equals(themeId)) {
			pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, "1");
		}
		String newLineRoutingDiagram = request.getParameter("newLineRoutingDiagram");//新建线路路由简图
		String orderAttachment = request.getParameter("orderAttachment");//附件
		if(newLineRoutingDiagram!=null&&!"".equals(newLineRoutingDiagram)){
			pnrTransferOfficeService.saveMultipleAttachments(processInstanceId, newLineRoutingDiagram, "1", "need", "newLineRoutingDiagram");
			
		}
		if(orderAttachment!=null&&!"".equals(orderAttachment)){
			pnrTransferOfficeService.saveMultipleAttachments(processInstanceId, orderAttachment, "1", "need", "orderAttachment");
			
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
		
		//清空该工单机房关联表中的数据
		IPnrOltBbuOfficeRelationService pnrOltBbuOfficeRelationService = (IPnrOltBbuOfficeRelationService)getBean("pnrOltBbuOfficeRelationService");
		if (themeId != null && !"".equals(themeId)) {
			Map<String,Object> relationParam=new HashMap<String,Object>();
			relationParam.put("processInstanceId", processInstanceId);
			pnrOltBbuOfficeRelationService.deletePnrOltBbuOfficeRelation(relationParam);
		}
		pnrOltBbuOfficeRelationService.save(pnrOltBbuOfficeRelation);//保存关联表数据
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, taskAssignee, pnrTransferOffice, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false);
		pnrTransferOfficeService.save(pnrTransferOffice);//保存主表数据
		
		// 图片信息
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		PnrPrecheckPhotoService pnrPrecheckPhotoService = (PnrPrecheckPhotoService) getBean("pnrPrecheckPhotoService");
		pnrTransferNewPrecheckService.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
		//新光缆段落起点照片
		String startPhotoIds = StaticMethod.nullObject2String(request.getParameter("startPhotoIds"));
		String[] startPhotoId = startPhotoIds.split(",");
		if (startPhotoId != null && startPhotoId.length > 0) {
			for (String str : startPhotoId) {
				if (str != null && !"".equals(str)) {
					PnrPrecheckPhoto pnrPrecheckPhoto = new PnrPrecheckPhoto();
					pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
					pnrPrecheckPhoto.setPhotoId(str);
					pnrPrecheckPhoto.setPhotoFlag("start");
					pnrPrecheckPhotoService.save(pnrPrecheckPhoto);
					
					pnrTransferNewPrecheckService.setStateByPhotoId(str, "1");// 将工单与图片关联起来,将图片的state状态设置成1
				}
			}
		}
		
		//新光缆段落终点照片
		String endPhotoIds = StaticMethod.nullObject2String(request.getParameter("endPhotoIds"));
		String[] endPhotoId = endPhotoIds.split(",");
		if (endPhotoId != null && endPhotoId.length > 0) {
			for (String str : endPhotoId) {
				if (str != null && !"".equals(str)) {
					PnrPrecheckPhoto pnrPrecheckPhoto = new PnrPrecheckPhoto();
					pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
					pnrPrecheckPhoto.setPhotoId(str);
					pnrPrecheckPhoto.setPhotoFlag("end");
					pnrPrecheckPhotoService.save(pnrPrecheckPhoto);
					pnrTransferNewPrecheckService.setStateByPhotoId(str, "1");// 将工单与图片关联起来,将图片的state状态设置成1
				}
			}
		}
		

		// 发短信
		// 短信接收人
		if(!"true".equals(isDraft)){//如果是保存草稿不用短信提醒
			String messagePerson = pnrTransferOffice.getCityLineCharge();//给市线路主管发短信
			String nextTaskLinkName = StaticMethod.nullObject2String(request.getParameter("nextTaskLinkName"));
			String msg = TASK_MESSAGE + "  " +TASK_LINK_NAME+nextTaskLinkName+","+ TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "。";
					//+ TASK_WORKORDERTYPE + workOrderType+ "," + TASK_SUBTYPE + subType + "。";
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
			System.out.println("需要调用预算接口！！！！！！！！");
		}
		return mapping.findForward("success");
	}

	/**
	 * OLT-BBU机房优化申请--待回复查询（待办工单除外）
	 * 
	 * @author chujingang
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
		String jobOrderType = request.getParameter("jobOrderType");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");
		String mainSceneId = request.getParameter("mainSceneSelect");//场景
		String batch = request.getParameter("batch");//批次
		

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setJobOrderType(jobOrderType);
		conditionQueryModel.setBatch(batch);
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

		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");
		// OLT-BBU机房优化申请-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrOltBbuRoomService.getOltBbuCount(userId,
					"backlog", processDefinitionKey,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// OLT-BBU机房优化申请--待回复工单 集合
		List<PnrOltBbuOfficeMainModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrOltBbuRoomService
					.getOltBbuList(userId, "backlog",
							processDefinitionKey, conditionQueryModel,
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
		request.setAttribute("wsStatus", status);
		request.setAttribute("jobOrderType", jobOrderType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("mainSceneSelect", mainSceneId);
		request.setAttribute("batch", batch);
		// 登录人，为了在页面区分是否是省级别的四个工作人员（是否显示批量处理按钮）
		request.setAttribute("loginUserId", userId);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
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
		
		System.out.println("-------------拼接待回复列表查询条件字符串="+condition);
		
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

//			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
//			List<PnrTransferOffice> list = t.getResult();
//			// 回复message
//			PnrTransferOffice pnrTransferOffice = list.get(0);
//			request.setAttribute("pnrTransferOffice", pnrTransferOffice);
			
			IPnrOltBbuOfficeRelationService pnrOltBbuOfficeRelationService = (IPnrOltBbuOfficeRelationService) getBean("pnrOltBbuOfficeRelationService");
			PnrOltBbuOfficeMainModel pnrOltBbuOfficeMainModel = pnrOltBbuOfficeRelationService.findPnrOltBbuOfficeMainByProcessInstanceId(processInstanceId);
			request.setAttribute("pnrTransferOffice", pnrOltBbuOfficeMainModel);

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
			auditSearch.addFilterEqual("operation", "61");
			//auditSearch.addFilterEqual("state", "rollback");
			auditSearch.addSort("checkTime", true);// 按回复时间排序
			auditMap = getHandleList(auditMap, transferHandleService,
					auditSearch, auditlist, auditSize);
			
			//查询pnr_transfer_spotcheck表中的审核信息
//			IPnrTransferSpotcheckService transferSpotcheckService = (IPnrTransferSpotcheckService) getBean("pnrTransferSpotcheckService");
//			List<PnrTransferSpotcheck> auditlist = null;
//			int auditSize = 0;
//			Search auditSearch = new Search();
//			auditSearch.addFilterEqual("processInstanceId", task
//					.getProcessInstanceId());
//			auditSearch.addSort("insertDate", true);// 按回复时间排序
//			
//			SearchResult<PnrTransferSpotcheck> results = transferSpotcheckService.searchAndCount(auditSearch);
//			auditSize = results.getTotalCount();
//			if (auditSize > 0) {
//				auditlist = results.getResult();
//		
//			}

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();
			String userTaskId = task.getTaskDefinitionKey();
			String step = "";// 环节所在步骤的值
			if (userTaskId.equals("need")) {//区县公司发起
				step = "1";
			} else if (userTaskId.equals("cityLineExamine")) {//市线路主管审核
				step = "2";
			}  else if (userTaskId.equals("cityManageExamine")) {// 市运维主管审核
				step = "3";
			} else if (userTaskId.equals("provinceLineExamine")) {// 省线路主管审核
				step = "4";
			}  else if (userTaskId.equals("provinceManageExamine")) {// 省运维主管审核
				step = "5";
			} else if (userTaskId.equals("waitInterface")){//等待接口调用
				step = "6";
			} else if (userTaskId.equals("sendOrder")){//派发代维
				step = "7";
			} else if (userTaskId.equals("worker")){//施工队施工回单
				step = "8";
			} else if (userTaskId.equals("orderAudit")){//区县公司验收
				step = "9";
			} else if (userTaskId.equals("daiweiAudit")){//市线路主管审批
				step = "10";
			}else if (userTaskId.equals("cityManagefile")){//市运维主管审核归档
				step = "11";
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
//			List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService
//					.newShowSheetAccessoriesList(processInstanceId);
			List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService.showPnrOltBbuRoomSheetAccessoriesList(processInstanceId);
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

			if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {// 区县公司发起
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				//1.查询出该工单所关联的图片，回显到页面中
				//1)回显起始照片
				Map<String,String> paramStart=new HashMap<String,String>();
				paramStart.put("photoFlag", "start");
				String[] startPhotoList = pnrOltBbuOfficeRelationService.getPhotoByProcessInstanceId(processInstanceId,paramStart);
				// 遍历photoID字符串，将图片关联标志state设置成0
				if (startPhotoList[0] != null && !"".equals(startPhotoList[0])) {
					String[] photoesId = startPhotoList[0].split(",");
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
				request.setAttribute("startPhotoIds", startPhotoList[0]);
				// 回显的图片详细信息
				request.setAttribute("startPhotoList", startPhotoList[1]);
				
				//2)回显终点照片
				Map<String,String> paramEnd=new HashMap<String,String>();
				paramEnd.put("photoFlag", "end");
				String[] endPhotoList = pnrOltBbuOfficeRelationService.getPhotoByProcessInstanceId(processInstanceId,paramEnd);
				// 遍历photoID字符串，将图片关联标志state设置成0
				if (endPhotoList[0] != null && !"".equals(endPhotoList[0])) {
					String[] photoesId = endPhotoList[0].split(",");
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
				request.setAttribute("endPhotoIds", endPhotoList[0]);
				// 回显的图片详细信息
				request.setAttribute("endPhotoList", endPhotoList[1]);

				//2.驳回信息
				request.setAttribute("approveBackListsize", approveBackMap
						.get("size"));
				request.setAttribute("approveBackList", approveBackMap
						.get("list"));
				
				//3.回显附件
				try{
					Map<String,String> multipleAttachments = pnrTransferOfficeService
					.echoMultipleAttachments(processInstanceId,"1","need","");
					  for (String key : multipleAttachments.keySet()) {
						  PnrTransferOffice ticket1 = new PnrTransferOffice();
						  ticket1.setSheetAccessories(multipleAttachments.get(key));
						  request.setAttribute(key, ticket1);
					  }
				}catch(Exception e){
					e.printStackTrace();
				}

				//4.回显子场景
				//this.echoChildScene(request, processInstanceId);
				Map<String, Object> echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "need", null);
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
				
				String findForward = "new";
				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"cityLineExamine")) {// 市线路主管审核
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
					"cityManageExamine")) {//市运维主管审核
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

				if ((pnrOltBbuOfficeMainModel == null ? 0 : pnrOltBbuOfficeMainModel
						.getState()) == 3) {
					request.setAttribute("directList", "waitWorkOrderList");
				}

				return mapping.findForward("cityManageChargeAudit");
			}  else if (task.getTaskDefinitionKey().equalsIgnoreCase(
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
			} else if (task.getTaskDefinitionKey()
					.equalsIgnoreCase("sendOrder")) {// (工单转派)派发代维
				
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				
				return mapping.findForward("mainSecond");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("worker")) {// (工单处理)施工队施工回单
//				if (list != null) {
//					// 回退原因，需要补录上
//					String id = list.get(0).getId();
//					Search search2 = new Search();
//					search2.addFilterEqual("themeId", id);
//					search2.addFilterEqual("state", "rollback");
//					search2.addSort("checkTime", true);
//
//					List<PnrTransferOfficeHandle> Handlelist2 = transferHandleService
//							.searchAndCount(search2).getResult();
//
//					if (Handlelist2 != null && Handlelist2.size() > 0) {
//						request.setAttribute("reason", Handlelist2.get(0)
//								.getOpinion());
//
//					}
//
//					request
//							.setAttribute("auditor", list.get(0)
//									.getCreateWork());
//
//				}
				// 驳回信息
				//request.setAttribute("auditListsize", auditMap.get("size"));
				//request.setAttribute("auditList", auditMap.get("list"));
				
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				
				
				//显示新建派发A的子场景模板
				Map<String, Object> echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "need", null);
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
			
				request.setAttribute("showWorkerScene","no");//不显示工单处理环节的场景模板
				return mapping.findForward("transferHandle");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"orderAudit")) {// （抽查）区县公司验收
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				
				// 施工队处理信息
				request.setAttribute("transferListsize", transferMap
						.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));
				int workerNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "worker", null);
				Map<String, Object> echoChildScene = null;
				String flagLinkType="worker";
				if(workerNum > 0){
					request.setAttribute("showWorkerScene", "yes");//显示施工队回单环节的场景模板
					//显示施工队回单B的子场景模板 当有施工队场景模板数据的时候
					echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "worker", null);
				}else{
					//施工队没有场景模板数据的时候 显示新建派发环节的场景模板数据 针对升级之前的老工单
					echoChildScene = sceneTemplateService.echoChildScene(processInstanceId, "need", null);
					flagLinkType="need";
				}
				request.setAttribute("childSceneIds", echoChildScene.get("childSceneIds").toString());
				request.setAttribute("echoChildSceneTableList",echoChildScene.get("echoChildSceneTableList"));
				request.setAttribute("flagLinkType", flagLinkType);
				return mapping.findForward("orderAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase(
					"daiweiAudit")) {// (审核-区县传输局)市线路主管审批
				
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				
				// 施工队处理信息
				request.setAttribute("transferListsize", transferMap
						.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));
				
				// 验收信息
				request.setAttribute("orderAuditListsize", auditMap
						.get("size"));
				request.setAttribute("orderAuditList", auditMap.get("list"));
//				request.setAttribute("orderAuditListsize",auditSize);
//				request.setAttribute("orderAuditList", auditlist);
				int workerNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "worker", null);
				int orderAuditNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "orderAudit", null);
				if(workerNum > 0){
					request.setAttribute("showWorkerScene", "yes");//显示施工队回单环节的场景模板
				}
				
				if(orderAuditNum > 0){
					request.setAttribute("showOrderAuditScene", "yes");//显示验收环节的场景模板
				}
				
				return mapping.findForward("secondAudit");
			}else if(task.getTaskDefinitionKey().equalsIgnoreCase("cityManagefile")){//市运维主管审核归档
				// 审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap
						.get("list"));
				
				// 施工队处理信息
				request.setAttribute("transferListsize", transferMap
						.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));
				
				// 验收信息
				request.setAttribute("orderAuditListsize", auditMap
						.get("size"));
				request.setAttribute("orderAuditList", auditMap.get("list"));
//				request.setAttribute("orderAuditListsize",auditSize);
//				request.setAttribute("orderAuditList", auditlist);
				
				int workerNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "worker", null);
				int orderAuditNum = sceneTemplateService.countSceneTemplateNum(processInstanceId, "orderAudit", null);
				if(workerNum > 0){
					request.setAttribute("showWorkerScene", "yes");//显示施工队回单环节的场景模板
				}
				
				if(orderAuditNum > 0){
					request.setAttribute("showOrderAuditScene", "yes");//显示验收环节的场景模板
				}
				
				return mapping.findForward("cityManagefile");
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
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		// 制定人
		String nextPerson = StaticMethod.nullObject2String(request
				.getParameter("nextPerson"));

		String taskId = request.getParameter("taskId");
		// 当前所处环节
		String linkName = request.getParameter("linkName");
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
		if("orderAudit".equals(linkName)){//区县公司验收
			entity.setIsRecover("通过");
			entity.setDoPersons(userId);
			entity.setHandleDescription("区县公司验收通过");
			entity.setOperation("06");
		}

		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		
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
		
		String person="";
		String taskDefKey = null;//在工单主表中添加环节KEY
		String taskDefKeyName = null;//在工单主表中添加环节NAME
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			// 将被处理的工单驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
			if("provinceManageExamine".equals(linkName)){//省运维主管审核
				// 调用工单接口,流程停留在当前环节，并将工单状态state设置成8
				pnrTicket.setState(8);
				pnrTicket.setDistributedInterfaceTime(new Date());// 省运维总经理审批通过时间，即派发接口时间
				person=request.getParameter("interfaceCharge");
				taskDefKey="waitingCallInterface";
				taskDefKeyName="省公司审批通过-等待商城";
			}else if("orderAudit".equals(linkName)){//区县公司验收环节
				pnrTicket.setState(0);
				pnrTicket.setWorkerSceneOrderAuditFlag("2");
				//抽查环节的乙方费用总额
				String sumCostOfPartyB = StaticMethod.nullObject2String(request.getParameter("sumCostOfPartyB"));
				pnrTicket.setSumOrderAuditCostOfPartyB(Double.parseDouble(sumCostOfPartyB));
				//抽查环节的项目金额
				String projectAmount = StaticMethod.nullObject2String(request.getParameter("projectAmount"));
				if(!"".equals(projectAmount)){
					pnrTicket.setOrderauditProjectAmount(Double.parseDouble(projectAmount));
				}
				//抽查环节环节的收支比
				String calculateIncomeRatio = StaticMethod.nullObject2String(request.getParameter("incomeRatio"));
				if(!"".equals(calculateIncomeRatio)){
					pnrTicket.setOrderauditIncomeRatio(Double.parseDouble(calculateIncomeRatio));
				}
				// 子场景IDs
				String orderauditChildIds = StaticMethod.nullObject2String(request.getParameter("childSceneSelect"));
				pnrTicket.setOrderauditChildIds(orderauditChildIds);
				//子场景Names		
				String orderauditChildNames = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
				pnrTicket.setOrderauditChildNames(orderauditChildNames);
				//保存场景模板
				ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
				sceneTemplateService.saveSceneTemplate(request, processInstanceId, "orderAudit", null);
				person=nextPerson;
			}else{
				pnrTicket.setState(0);
				person=nextPerson;
			}
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false);
			pnrTransferOfficeService.save(pnrTicket);
		}
		
		transferHandleService.save(entity);

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
		
		// 跳转页面 check
		String goPage = "check";
		if ("waitWorkOrderList".equals(directList)) {
			goPage = "waitWorkOrderList";
		}
		request.setAttribute("success", goPage);
		request.setAttribute("condition", request.getParameter("condition"));

		// 发短信
		if (isSend) {
			String nextTaskLinkName = StaticMethod.nullObject2String(request.getParameter("nextTaskLinkName"));
			String msg = TASK_MESSAGE + "  " +TASK_LINK_NAME+nextTaskLinkName+","+ TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "。";
			CommonUtils.sendMsg(msg, nextPerson);
		}
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
		if (projectAmount > doubleFirst) {// 金额大于一千--走市公司副总审核
			map.put("cityManageDirectorState", "city");
			map.put("nextTaskAssignee", pnrTicket.getCityGS());
			nextPerson = pnrTicket.getCityGS();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		} else if (projectAmount > doubleSecond) {// 走省线路主管审核
			map.put("cityManageDirectorState", "province");
			map.put("nextTaskAssignee", pnrTicket.getProvinceLineCharge());
			nextPerson = pnrTicket.getProvinceLineCharge();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		} else if (projectAmount <= doubleFirst
				&& projectAmount <= doubleSecond) {// 调用商城接口,流程停留在当前环节
			pnrTicket.setState(8);
			isSend = false;
		}
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
		if (projectAmount > doubleSecond) {// 金额大于一万--走省线路主管审核
			map.put("cityDiveState", "handle");
			map.put("nextTaskAssignee", pnrTicket.getProvinceLineCharge());
			formService.submitTaskFormData(taskId, map);
		} else {// 调用工单接口,流程停留在当前环节，并将工单状态state设置成8
			pnrTicket.setState(8);
			isSend = false;
		}
		pnrTransferOfficeService.save(pnrTicket);
		if (isSend) {
			String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
					+ "," + TASK_TITLE_STR + theme + "," + TASK_WORKORDERDEGREE
					+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
					+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName()
					+ "," + TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
			CommonUtils.sendMsg(msg, pnrTicket.getProvinceLineCharge());
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
	 * olt-bbu机房优化申请--回退
	 * 
	 * @author chujingang
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

		try {
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();

			String hjflag = "";
			// 当前所处环节
			String linkName = "";
			if (handle.equals(TASK_CITYLINEEXAMINE)) {//市线路主管驳回
				hjflag = "市线路主管驳回";
				map.put("initiator", returnPerson);
				map.put("cityLineChargeState", "rollback");
				msgPerson = returnPerson;
				linkName = "cityLineExamine";
			} else if (handle.equals(TASK_CITYMANAGEEXAMINE)) {// 市运维主管驳回
				hjflag = "市运维主管驳回";
				map.put("cityLineChargeAduit", returnPerson);
				map.put("cityManageChargeState", "rollback");
				msgPerson = returnPerson;
				linkName = "cityManageExamine";
			} else if (handle.equals(TASK_PROVINCELINEEXAMINE)) {// 省线路主管驳回
				hjflag = "省线路主管驳回";
				map.put("cityManageChargeAudit", returnPerson);
				map.put("provinceLineChargeState", "rollback");
				msgPerson = initiator;
				linkName = "provinceLineExamine";
			} else if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {// 省运维主管驳回
				hjflag = "省运维主管驳回";
				map.put("nextTaskAssignee", returnPerson);
				map.put("provinceManageChargeState", "rollback");
				msgPerson = initiator;
				linkName = "provinceManageExamine";
			} else if (handle.equals(TASK_ORDERAUDIT)) {//区县公司验收驳回
				hjflag = "区县公司验收驳回";
				map.put("handleWorker", returnPerson);
				map.put("orderAuditHandleState", "rollback");
				msgPerson = initiator;
				linkName = "orderAudit";
			} else if (handle.equals(TASK_WORKER)) {//施工队施工回单驳回
				hjflag = "施工队施工回单驳回";
				map.put("nextTaskAssignee", returnPerson);
				map.put("workerHandleState", "rollback");
				msgPerson = initiator;
				linkName = "worker";
			}


			String msg = "";
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			double projectAmount = 0.0;
			
			
			// 由于省线路主管审批特殊，可能回退到市公司副总或者市运维主任
//			if (handle.equals(TASK_PROVINCELINEEXAMINE)) {// 省线路主管驳回
//				hjflag = "省线路主管驳回";
//				String firstMoneyLimitDicId = precheckRoleModel
//						.getFirstMoneyLimitDicId();
//				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
//
//				TawSystemDictType dictypeFirst = mgr
//						.getDictByDictId(firstMoneyLimitDicId);
//				double doubleFirst = 0L;
//
//				if (dictypeFirst != null
//						&& StaticMethod.getFloatValue(dictypeFirst
//								.getDictRemark()) > -1) {
//					doubleFirst = Double.parseDouble(dictypeFirst
//							.getDictRemark());
//				} else {
//					request.setAttribute("msg", "是否干线处-金额限制，有问题；请联系管理员");
//					return mapping.findForward("failure");
//				}
//				if (projectAmount > doubleFirst) {// 回退到市公司副总
//					map.put("nextTaskAssignee", returnPerson);
//					map.put("provinceLineChargeState", "rollback");
//				} else {// 回退到市运维主任
//					map.put("cityManageDirectorAudit", returnPerson);
//					map.put("provinceLineChargeState", "cityManage");
//				}
//				msgPerson = initiator;
//				// linkName = "cityManageDirectorAudit";
//				linkName = "provinceLineExamine";
//			}

//			if (!handle.equals(TASK_TRANSFERHANDLE)) {
//				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
//				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
//				// 将回退原因存入操作表中
//				String rejectReason = request.getParameter("rejectReason");
//				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//						.getSession().getAttribute("sessionform");
//
//				String userId = sessionform.getUserid();
//				// 审批时间
//				Date createTime = new Date();
//				entity.setThemeId(themeId);
//				entity.setTheme(theme);
//				entity.setCheckTime(createTime);
//				entity.setTaskId(taskId);
//				entity.setProcessInstanceId(processInstanceId);
//				entity.setAuditor(userId);
//				entity.setHandleDescription(rejectReason);
//				entity.setLinkName(linkName);
//				if ("orderAudit".equals(handle)) {
//					entity.setOperation("09");// 抽查环节驳回 标识值为09
//				} else {
//					entity.setOperation("02");
//				}
//				transferHandleService.save(entity);
//			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:OLT-BBU机房优化申请;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--start");
			formService.submitTaskFormData(taskId, map);
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:OLT-BBU机房优化申请;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--end");
			
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);

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

//				if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {
//					// 删除会审辅助表记录的辅助信息
//					pnrTransferOfficeService
//							.deleteCountersignInfo(processInstanceId);
//
//					pnrTransferOffice.setState(7);// 停止会审
//				}

				// 市运维主管（有待办权利）-驳回时，将待办状态回执
//				if (handle.equals(TASK_CITYMANAGEEXAMINE)) {
//					if (3 == pnrTransferOffice.getState()) {
//
//						pnrTransferOffice.setState(0);// 正常派发时刻
//					}
//				}
				// 将驳回标志置成1，代表该工单是驳回工单
				pnrTransferOffice.setRollbackFlag("1");
				
				if(handle.equals(TASK_WORKER)){
					// 清空工单表施工队回单环节的场景模板信息
					pnrTransferOffice.setSumWorkerCostOfPartyB(null);
					pnrTransferOffice.setWorkerProjectAmount(null);
					pnrTransferOffice.setWorkerIncomeRatio(null);
					pnrTransferOffice.setWorkerChildIds("");
					pnrTransferOffice.setWorkerChildNames("");
					pnrTransferOffice.setWorkerSceneHandleFlag("");
					
					//删除施工队回单环节场景模板
					ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
					sceneTemplateService.deleteSceneTemplate(processInstanceId, "worker", null);
				}else if(handle.equals(TASK_ORDERAUDIT)){//区县公司验收环节
					// 清空区县公司验收环节的场景模板信息
					pnrTransferOffice.setSumOrderAuditCostOfPartyB(null);
					pnrTransferOffice.setOrderauditProjectAmount(null);
					pnrTransferOffice.setOrderauditIncomeRatio(null);
					pnrTransferOffice.setOrderauditChildIds("");
					pnrTransferOffice.setOrderauditChildNames("");
					pnrTransferOffice.setWorkerSceneOrderAuditFlag("");
					//删除区县公司验收环节场景模板
					ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
					sceneTemplateService.deleteSceneTemplate(processInstanceId, "orderAudit", null);
				}
				
				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, returnPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);

			}

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
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");//

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
		processTaskService.setTvalueOfTask(processInstanceId, handleWorker, entity, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(entity);
		
		//保存流转信息
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle pnrTransferOfficeHandle = new PnrTransferOfficeHandle();
		pnrTransferOfficeHandle.setThemeId(themeId);
		pnrTransferOfficeHandle.setTheme(entity.getTheme());
		pnrTransferOfficeHandle.setCheckTime(new Date());
		pnrTransferOfficeHandle.setHandleDescription("无");
		pnrTransferOfficeHandle.setAuditor(initiator);
		pnrTransferOfficeHandle.setProcessInstanceId(processInstanceId);
		//pnrTransferOfficeHandle.setDoPerson(initiator);
		pnrTransferOfficeHandle.setLinkName("sendOrder");
		pnrTransferOfficeHandle.setOperation("01");
		transferHandleService.save(pnrTransferOfficeHandle);

		// 发短信
		String nextTaskLinkName = StaticMethod.nullObject2String(request.getParameter("nextTaskLinkName"));
		String msg = TASK_MESSAGE + "  " +TASK_LINK_NAME+nextTaskLinkName+","+ TASK_NO_STR + entity.getProcessInstanceId()
				+ "," + TASK_TITLE_STR + entity.getTheme() + "。";
		
//				+ entity.getProcessInstanceId() + "," + TASK_TITLE_STR
//				+ entity.getTheme() + "," + TASK_WORKORDERDEGREE
//				+ getDictNameByDictid(entity.getWorkOrderDegree()) + ","
//				+ TASK_WORKORDERTYPE + entity.getWorkOrderTypeName() + ","
//				+ TASK_SUBTYPE + entity.getSubTypeName() + "。";
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
		// 发短信
		String deadLineTime = "", contact = "";
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
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
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

		// 流程提交到下一级-end
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOffice.setRollbackFlag("0");// 未驳回标识
			pnrTransferOffice.setState(0);
			pnrTransferOffice.setWorkerSceneHandleFlag("2");//手机端和web端都处理完成了。
			
			//施工队回单环节的乙方费用总额
			String sumCostOfPartyB = StaticMethod.nullObject2String(request.getParameter("sumCostOfPartyB"));
			pnrTransferOffice.setSumWorkerCostOfPartyB(Double.parseDouble(sumCostOfPartyB));
			//施工队回单环节的项目金额
			String projectAmount = StaticMethod.nullObject2String(request.getParameter("projectAmount"));
			if(!"".equals(projectAmount)){
				pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(projectAmount));
			}
			//施工队回单环节的收支比
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
			
			//保存场景模板
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			sceneTemplateService.saveSceneTemplate(request, processInstanceId, "worker", null);
			
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			String nextTaskLinkName = StaticMethod.nullObject2String(request.getParameter("nextTaskLinkName"));
			msg = TASK_MESSAGE + "  " +TASK_LINK_NAME+nextTaskLinkName+","+ TASK_NO_STR + pnrTransferOffice.getProcessInstanceId()
			+ "," + TASK_TITLE_STR + pnrTransferOffice.getTheme() + "。";

//			msg = TASK_MESSAGE
//					+ "  "
//					+ TASK_NO_STR
//					+ pnrTransferOffice.getProcessInstanceId()
//					+ ","
//					+ TASK_TITLE_STR
//					+ pnrTransferOffice.getTheme()
//					+ ","
//					+ TASK_WORKORDERDEGREE
//					+ getDictNameByDictid(pnrTransferOffice
//							.getWorkOrderDegree()) + "," + TASK_WORKORDERTYPE
//					+ pnrTransferOffice.getWorkOrderTypeName() + ","
//					+ TASK_SUBTYPE + pnrTransferOffice.getSubTypeName() + "。";

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		transferHandleService.save(entity);
		
		CommonUtils.sendMsg(msg, auditor);
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");

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
		entity.setOperation("01");
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

		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		processInstanceId = taskList.get(0).getProcessInstanceId();
		search.addFilterEqual("processInstanceId", taskList.get(0)
				.getProcessInstanceId());
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}

		if (auditState.equals("rollback")) {
			if (pnrTicket != null) {

				pnrTicket.setLastReplyTime(null);
				pnrTicket.setRollbackFlag("1");// 未驳回标识
				//pnrTransferOfficeService.save(pnrTicket);

				//auditor = pnrTicket.getTaskAssignee();// 回退到二次处理人
			}
			isSend = false;

		} else if (auditState.equals("handle")) {
			if (pnrTicket != null) {
				processInstanceId = pnrTicket.getProcessInstanceId();
				deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
						.format(pnrTicket.getEndTime());
				contact = pnrTicket.getConnectPerson();
				pnrTicket.setRollbackFlag("0");// 未驳回标识
				pnrTicket.setDaiweiAuditPerson(userId);//保存审核处理人
				//pnrTransferOfficeService.save(pnrTicket);
			}
		}

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String person="";
		if (auditState.equals("handle")) {
			auditor=pnrTicket.getCityManageCharge();
			person=pnrTicket.getCityManageCharge();
			map.put("cityManageChargeAuditor",pnrTicket.getCityManageCharge());
			map.put("initiatorHandleState", "handle");
		} else if (auditState.equals("rollback")) {
			person=pnrTicket.getCountryCSJ();
			map.put("initiatorCheck",pnrTicket.getCountryCSJ());
			map.put("initiatorHandleState", "rollback");
			
		}
		formService.submitTaskFormData(taskId, map);
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTicket);
		transferHandleService.save(entity);
		
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
		String msg="";
		if(isSend){
			String nextTaskLinkName = StaticMethod.nullObject2String(request.getParameter("nextTaskLinkName"));
			msg = TASK_MESSAGE + "  " +TASK_LINK_NAME+nextTaskLinkName+","+ TASK_NO_STR + pnrTicket.getProcessInstanceId()
			+ "," + TASK_TITLE_STR + pnrTicket.getTheme() + "。";
			
//			msg = TASK_MESSAGE + "  " + TASK_NO_STR
//			+ pnrTicket.getProcessInstanceId() + "," + TASK_TITLE_STR
//			+ pnrTicket.getTheme() + "," + TASK_WORKORDERDEGREE
//			+ getDictNameByDictid(pnrTicket.getWorkOrderDegree()) + ","
//			+ TASK_WORKORDERTYPE + pnrTicket.getWorkOrderTypeName() + ","
//			+ TASK_SUBTYPE + pnrTicket.getSubTypeName() + "。";
		}
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
			pnrTransferOfficeService.save(pnrTransferOffice);
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
	 * 
	 * 获取工单基本信息
	 * 
	 * @param request
	 * @param pnrTransferOffice
	 * @throws ParseException
	 */
	private void setTransferOfficeByRequest(HttpServletRequest request,
			PnrTransferOffice pnrTransferOffice) throws ParseException {
		//1.界面上的工单基本信息
		// 工单名称
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 工单发起人（第一派单人）
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		// 发起人部门
		String initiatorDept = StaticMethod.nullObject2String(request
				.getParameter("initiatorDept"));
		// 发起人电话
		String initiatorMobilePhone = StaticMethod.nullObject2String(request
				.getParameter("initiatorMobilePhone"));
		//工单类型
		String jobOrderType = StaticMethod.nullObject2String(request
				.getParameter("jobOrderType"));
		// 主场景ID---mainSceneSelect
		String mainSceneId = StaticMethod.nullObject2String(request
				.getParameter("mainSceneSelect"));
		// 驳回时展现的子场景ID
		String showChildSceneId = StaticMethod.nullObject2String(request
				.getParameter("childSceneSelect"));
		// 子场景ID串
		String childSceneIds = StaticMethod.nullObject2String(request
				.getParameter("childSceneIds"));
		
		String subTypeName = StaticMethod.nullObject2String(request.getParameter("subTypeName"));
		//批次
		String batch = StaticMethod.nullObject2String(request
				.getParameter("batch"));
		//项目金额估算
		String projectAmount = StaticMethod.nullObject2String(request
				.getParameter("projectAmount"));
	
		//是否是草稿
		String isDraft =StaticMethod.nullObject2String(request
				.getParameter("isDraft"));
		// 工单ID
		String themeId = request.getParameter("themeId");
		
		//流程类型
		String themeInterface =StaticMethod.nullObject2String(request
				.getParameter("themeInterface"));
		//流程类型
		String processInstanceId =StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
		String workOrderTypeName = StaticMethod.nullObject2String(request.getParameter("workOrderTypeName"));
		
		//乙方费用的总额
		String sumNeedCostOfPartyB = StaticMethod.nullObject2String(request
				.getParameter("sumCostOfPartyB"));
		
		pnrTransferOffice.setTheme(title);// 工单名称
		pnrTransferOffice.setInitiatorDept(initiatorDept);// 发起人部门
		pnrTransferOffice.setInitiatorMobilePhone(initiatorMobilePhone);//发起人电话
		pnrTransferOffice.setJobOrderType(jobOrderType);
		pnrTransferOffice.setMainSceneId(mainSceneId);//主场景ID
		pnrTransferOffice.setShowChildSceneId(showChildSceneId);//驳回时展现的子场景ID
		pnrTransferOffice.setSubType(showChildSceneId);
		pnrTransferOffice.setChildSceneIds(childSceneIds);//子场景ID串
		pnrTransferOffice.setSubTypeName(subTypeName);
		pnrTransferOffice.setBatch(batch);
		pnrTransferOffice.setProjectAmount(Double.parseDouble(projectAmount));
		pnrTransferOffice.setIsDraft(isDraft);
		pnrTransferOffice.setId(themeId);
		pnrTransferOffice.setSendTime(new Date());//派单时间
		pnrTransferOffice.setRollbackFlag("0");//未驳回标识
		if("true".equals(isDraft)){
			pnrTransferOffice.setSaveDraftDate(new Date());
		}
		pnrTransferOffice.setThemeInterface(themeInterface);//记录是oltbbu流程
		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setWorkOrderTypeName(workOrderTypeName);
		pnrTransferOffice.setSumNeedCostOfPartyB(Double.parseDouble(sumNeedCostOfPartyB));
		
		//2.各个环节的人员
		// 第一派发人
		pnrTransferOffice.setInitiator(initiator);
		pnrTransferOffice.setOneInitiator(initiator);
		pnrTransferOffice.setCreateWork(initiator);
		pnrTransferOffice.setCountryCSJ(initiator);

		// 没有确定的人，只是添加--默认superUser
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		PrecheckShipModel precheckShipModel = pnrTransferNewPrecheckService
				.getPrecheckShipModelBycountryCharge(initiator);
		pnrTransferOffice.setSecondCreateWork(precheckShipModel.getCityLineCharge());
		pnrTransferOffice.setTaskAssignee(precheckShipModel.getCityLineCharge());
		
		// 市线路主管
		pnrTransferOffice.setCityLineCharge(precheckShipModel.getCityLineCharge());
		
		// 市运维主管
		pnrTransferOffice.setCityManageCharge(precheckShipModel
				.getCityManageCharge());
		
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
		//是起点照片还是终点照片
		String photoType = StaticMethod.nullObject2String(request.getParameter("photoType"));
		
		//封装查询条件
		Map<String,String> param=new HashMap<String,String>();
		param.put("userId", userId);
		param.put("photoDescribe", photoDescribe);
		param.put("createPhotoTime", createPhotoTime);
		param.put("photoCreate", photoCreate);
		param.put("faultLocation", faultLocation);
		param.put("photoType", photoType);
		
		// 根据登录人所述地市查询一定时间内的服务器上的图片
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
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
		request.setAttribute("photoType", photoType);
		request.setAttribute("tagName", tagName);
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
			System.out.println(msg);
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
					System.out.println("--------进到这里来了吗");

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
		String returnJson = "";
		String error = "";
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
							
							Map<String, String> taskMap = new HashMap<String, String>();
							String nextPerson = "";// 流程人员标志名
							String nextAssigness = "";// 流程下一步人员
							String transferHandleFlag = "";// 处理标志名
							String transferHandleFlagValue = "";// 处理标志值
							// 解析字符串，获取到所在环节字段，并根据环节获取下一流程人
							if("provinceLineExamine".equals(taskKey)){//省线路主管审核
								nextAssigness = pnrTicket.getProvinceManageCharge();
								nextPerson = "provinceManageCharge";
								transferHandleFlag = "provinceLineChargeState";
								transferHandleFlagValue = "handle";
								b = true;
							}else if ("provinceManageExamine".equals(taskKey)) {//省运维主管审核
								nextAssigness = "superUser";
								nextPerson = "interfaceCharge";
								transferHandleFlag = "provinceManageChargeState";
								transferHandleFlagValue = "handle";
								pnrTicket.setDistributedInterfaceTime(new Date());// 省运维主管审批通过时间，即派发接口时间
								pnrTicket.setState(8);
								b = true;
							}

							if (b) {
								taskMap.put(nextPerson, nextAssigness);
								taskMap.put(transferHandleFlag,
										transferHandleFlagValue);
								formService.submitTaskFormData(taskId,
										taskMap);
							}
							
							if(!"provinceManageExamine".equals(taskKey)){
								//在工单主表中添加流程信息
								processTaskService.setTvalueOfTask(processInstanceId, nextAssigness, pnrTicket, PnrTransferOffice.class, null, null,false);
							}else{
							    processTaskService.setTvalueOfTask(processInstanceId, nextAssigness, pnrTicket, PnrTransferOffice.class, "waitingCallInterface", "省公司审批通过-等待商城",false);
							}

							if (b) {
								// 保存主表
								pnrTransferOfficeService.save(pnrTicket);

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
								entity.setOperation("01");
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
		System.out.println("--------进到批量处理-共用方法了吗？---------------");
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
		System.out.println("--------进到批量处理-市运维主任审核方法了吗？---------------");
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
		System.out.println("--------进到批量处理-市公司副总审核方法了吗？---------------");
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
		System.out.println("--------进到批量处理-省线路主管审核方法了吗？---------------");
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
		System.out.println("--------进到批量处理-省线路总经理审核方法了吗？---------------");
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
							if("provinceManageExamine".equals(taskKey)){//省运维主管审核
								nextPerson = "cityManageChargeAudit";
								beforeAssigness = pnrTicket.getCityManageCharge();
								transferHandleFlag = "provinceManageChargeState";
								b = true;
							}
							else if ("provinceLineExamine".equals(taskKey)) {// 省线路主管审核
								nextPerson = "cityManageChargeAudit";
								beforeAssigness = pnrTicket
										.getCityManageCharge();
								transferHandleFlag = "provinceLineChargeState";
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
		System.out.println("--------进到批量回退-共用方法了吗？---------------");
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
		int pageSize = CommonUtils.PAGE_SIZE;
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
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		//获得查询条件
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");

		//封装查询条件
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
	
		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");

		// 已处理工单条数
		int total = pnrOltBbuRoomService.getHaveProcessCount(
				processDefinitionKey, userId,conditionQueryModel);

		// 已处理工单明细
		List<PnrOltBbuOfficeMainModel> rPnrTicketList = pnrOltBbuRoomService
				.getHaveProcessList(processDefinitionKey, userId,conditionQueryModel,
						firstResult, endResult, pageSize);

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
	 * 详细信息呈现
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("##############详细信息呈现");

		String processId = request.getParameter("processInstanceId");

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processId);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		IPnrOltBbuOfficeRelationService pnrOltBbuOfficeRelationService = (IPnrOltBbuOfficeRelationService) getBean("pnrOltBbuOfficeRelationService");
		PnrOltBbuOfficeMainModel pnrOltBbuOfficeMainModel = pnrOltBbuOfficeRelationService.findPnrOltBbuOfficeMainByProcessInstanceId(processId);
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
		
		ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
		int needNum = sceneTemplateService.countSceneTemplateNum(processId, "need", null);
		int workerNum = sceneTemplateService.countSceneTemplateNum(processId, "worker", null);
		int orderAuditNum = sceneTemplateService.countSceneTemplateNum(processId, "orderAudit", null);
		if(needNum > 0){
			request.setAttribute("showNeedScene", "yes");//是否显示新建派发时候的场景模板
		}
		if(workerNum > 0){
			request.setAttribute("showWorkerScene", "yes");//显示施工队回单环节的场景模板
		}
		
		if(orderAuditNum > 0){
			request.setAttribute("showOrderAuditScene", "yes");//显示验收环节的场景模板
		}

		List<TawCommonsAccessoriesForm> accessoriesList = pnrTransferOfficeService
				.newShowSheetAccessoriesList(processId);
		request.setAttribute("sheetAccessories", accessoriesList);
		request.setAttribute("pnrTransferOffice", pnrOltBbuOfficeMainModel);
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
		System.out.println("##############详细信息呈现");

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
		
		

		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");

		// 由我创建的工单条数
		int total = pnrOltBbuRoomService.getByCreatingWorkOrderCount(
				processDefinitionKey, userId, sendStartTime, sendEndTime,
				wsNum, wsTitle, status);

		// 由我创建的工单明细
		List<PnrOltBbuOfficeMainModel> rPnrTicketList = pnrOltBbuRoomService
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

		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if (taskDefKey.equals("workOrderCheck")) {// 工单发起审核
			map.put("initiator", pnrTransferOffice.getCreateWork());
			map.put("workOrderState", "rollback");
		} else if (taskDefKey.equals("cityLineExamine")) {// 市线路主管审核
			map.put("workOrderCheck", pnrTransferOffice.getSecondCreateWork());
			map.put("cityLineChargeState", "rollback");
		} else if (taskDefKey.equals("cityLineDirectorAudit")) {// 市线路主任审核
			map.put("cityLineChargeAduit", pnrTransferOffice
					.getCityLineCharge());
			map.put("cityLineDirectorState", "rollback");
		} else if (taskDefKey.equals("cityManageExamine")) {// 市运维主管审核
			map.put("cityLineDirectorAudit", pnrTransferOffice
					.getCityLineDirector());
			map.put("cityManageChargeState", "rollback");
		} else if (taskDefKey.equals("cityManageDirectorAudit")) {// 市运维主任审核
			map.put("cityManageChargeAudit", pnrTransferOffice
					.getCityManageCharge());
			map.put("cityManageDirectorState", "rollback");
		}
		formService.submitTaskFormData(taskId, map);

		// 2代表抓回
		pnrTransferOffice.setRollbackFlag("2");
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
			//在工单主表中添加流程信息
		    processTaskService.setTvalueOfTask(processInstanceId, "", pnrTransferOffice, PnrTransferOffice.class, "archive", "已归档",true);
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
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
	//	return listBacklog(mapping, form, request, response);
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
		System.out.println("==============================");
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
		System.out.println("--------主场景searchSql="+searchSql);
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
		 System.out.println(returnJson.toString());

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
		
		System.out.println("------searchSql="+searchSql);
		
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
				
				System.out.println("-----bodySql="+bodySql);
				
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
		for(int i=0;i<split.length;i++){
			System.out.println("-----------------"+split[i]);
		}
		
		
		System.out.println("-------------------childSceneId="+childSceneId);
		System.out.println("-------------------quotaVals="+quotaVals);
		
		//缺少根据infor回显数据的功能
		String infor = request.getParameter("infor");//包含材料ID、数量、单价、合价、材料是否利旧信息的字符串
		String totalCost =StaticMethod.nullObject2String(request.getParameter("totalCost"));//合计金额
		
		System.out.println("-------------------infor="+infor);
		System.out.println("-------------------totalCost="+totalCost);
		
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
		System.out.println("----------------111111111111111111");
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
					        
					"select t.fault_dis,'gvyh','' from" +
					" (select t.fault_dis from maste_area_discount t" +
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

			System.out.println("--------quotaSql-:"+quotaSql);
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
		
		String processInstanceId = request.getParameter("processInstanceId");
		
		List<SceneTableModel> sceneTableList=new ArrayList<SceneTableModel>();
		
		IMasteSelCopyTaskService masteSelCopyTaskService = (IMasteSelCopyTaskService)getBean("masteSelCopyTaskService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
		
		int num = resultTaskList.size();
		
		String childSceneId="";
		SceneTableModel sceneTableModel =null;
		MasteSelCopyTask  entity=null;
		for(int i=0;i<num;i++){
		
			sceneTableModel=new SceneTableModel();

			entity= resultTaskList.get(i);
			//设置子场景id值
			childSceneId=entity.getChildSceneId();
			if (childSceneId != null && !"".equals(childSceneId)) {
				sceneTableModel.setChildSceneId(childSceneId);
				
				CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
				String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota from  maste_copy_thead d  " +
						"where d.copy_no='" +childSceneId+"' order by d.seq";
				
				System.out.println("----------查看子场景详情-searchSql="+searchSql);
				
				List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
				
				
				String childSceneName="";
				int tableHeaderLength=resultList.size();
				String[] tableHeader=new String[tableHeaderLength];  
		
				List  list = new ArrayList();
				int j =0;
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					tableHeader[j]=(String)listOrderedMap.get("thead_name");
					j++;
					childSceneName =(String)listOrderedMap.get("copy_name");
				}
				j=0;
				
				//设置子场景中文值
				sceneTableModel.setChildSceneName(childSceneName);
				
				//设置表头	
				sceneTableModel.setTableHeader(tableHeader);
				
				//设置数据
				//int tableDateLength=4;
				List<String[]> tableData=new ArrayList<String[]>();
				//获取需要查找的sql
				String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+childSceneId+"'";
				
				System.out.println("-----------查看子场景详情-dataSqlConstr="+dataSqlConstr);
				
				List<ListOrderedMap> dataConstr = jdbcService.queryForList(dataSqlConstr);
				
				String task_rel_strs ="";
				String con_strs ="";
				for(ListOrderedMap listOrderedMap : dataConstr){
					task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
					con_strs = (String)listOrderedMap.get("con_strs");
				}
				
				String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO,unique_id from maste_rel_task_item where CHILD_SCENE_ID='"+childSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"' order by seq";
				
				String[] con = con_strs==null?null:con_strs.split("#");
				String newStr ="";
				String lowerStr = "";
				int csize= con==null?0: con.length;
				
				for(int x=0;x<csize;x++){
					//System.out.println("执行了:"+csize+"-000"+selDataSql);
					lowerStr = con[x].toLowerCase();
					newStr="(select tbody_name from maste_copy_tbody  where maste_copy_tbody.tbody_no=maste_rel_task_item."+lowerStr+") "+lowerStr;
					selDataSql = selDataSql.replace(lowerStr, newStr);
				}
				//System.out.println("000000000selDataSql:"+selDataSql);
				System.out.println("-----------查看子场景详情-selDataSql="+selDataSql);
				List<ListOrderedMap> dataList = jdbcService.queryForList(selDataSql);
				
				String[] dataFiled = task_rel_strs.split("#"); 
				String[] tr=null;

				String item_no="";
				String unique_id="";
				for(ListOrderedMap listOrderedMap : dataList){
					tr =new String[tableHeaderLength];
					for(int r=0;r<tableHeaderLength;r++){
						if("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])){
							item_no =(String)listOrderedMap.get("ITEM_NO");
							if(listOrderedMap.get("unique_id")!=null){
								unique_id =(String)listOrderedMap.get("unique_id");
							}
							tr[r] ="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails(&quot;"+item_no+"&quot;,&quot;"+processInstanceId+"&quot;,&quot;"+unique_id+"&quot;)\">查看</a>";
						}else if("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])){
							tr[r] ="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails(&quot;"+item_no+"&quot;,&quot;"+processInstanceId+"&quot;,&quot;"+unique_id+"&quot;)\">查看</a>";
						}else {						
							tr[r] = (String)listOrderedMap.get(dataFiled[r]);
						}
						
					}
					//tr[tableHeaderLength] = (String)listOrderedMap.get("ITEM_NO");
					
					tableData.add(tr);
				}
				
				
				
				
			/*	String[] tr1=new String[tableHeaderLength];
				tr1[0]="22";
				tr1[1]="DAKDE993";
				tr1[2]="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails()\">查看</a>";
				tr1[3]="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails()\">查看</a>";*/
				
			/*	String[] tr2=new String[tableHeaderLength];
				tr2[0]="4543";
				tr2[1]="DAKDE9E3IIF";
				tr2[2]="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails()\">查看</a>";;
				tr2[3]="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails()\">查看</a>";
				tableData.add(tr2);*/
				
				sceneTableModel.setTableData(tableData);
			
			//返回集合
				sceneTableList.add(sceneTableModel);
			}
		}
		
		
		request.setAttribute("sceneTableList", sceneTableList);
		request.setAttribute("processInstanceId",processInstanceId);
		
		return mapping.findForward("childSceneForDetails");
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
		
		
		System.out.println("-------------------查看辅材详情-------searchSql="+searchSql);
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
				
				System.out.println("------------searchSql="+searchSql);
				
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
				System.out.println("-------dataSqlConstr="+dataSqlConstr);
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
				System.out.println("----------selDataSql="+selDataSql);
				
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
							"<input type=\"text\" value=\""+unique_id+"\" name=\""+oneChildSceneId+"_uniqueId\" />" +
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
	 * oltBbu-工单查询
	 * 
	 * @author chujingang
	 * @title: oltBbuWorkOrderList
	 * @date Feb 7, 2015 10:28:21 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward  oltBbuWorkOrderList(ActionMapping mapping, ActionForm form,
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
		String batch =request.getParameter("batch");
		String jobOrderType = request.getParameter("jobOrderType");// 工单类型
		String precheckFlag = request.getParameter("precheckFlag");

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		
		conditionQueryModel.setJobOrderType(jobOrderType);
		conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setBatch(batch);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
		
		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");
		// olt-bbu工单集合条数
		int total = 0;
		// olt-bbu工单集合
		List<PnrOltBbuOfficeMainModel> rPnrTransferList = null;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			try {
				total = pnrOltBbuRoomService.getOltBbuWorkOrderListCount(areaid,userId,
						"backlog", "oltBbuProcess",
						conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rPnrTransferList = pnrOltBbuRoomService
						.getOltBbuWorkOrderList(areaid,userId, "backlog",
								"oltBbuProcess", conditionQueryModel,
								firstResult, endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("tishiFlag","Y");//没选查询条件时，给出提示
		}
		String batchApprovalFlag = "no";
		System.out.println("--------------status=" + status);
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
		request.setAttribute("jobOrderType", jobOrderType);
		request.setAttribute("batch", batch);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("oltBbuWorkOrderList");
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
		
		System.out.println("-------------------pathnew="+pathnew);
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
	
        HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("本地网场景模板");
        ExcelUtils exportExcel = new ExcelUtils(wb, sheet);
        
       
        int y=0;
        
        
			//获取场景和定额信息
			String processInstanceId = request.getParameter("processInstanceId");
		
			List<SceneTableModel> sceneTableList=new ArrayList<SceneTableModel>();
						
			IMasteSelCopyTaskService masteSelCopyTaskService = (IMasteSelCopyTaskService)getBean("masteSelCopyTaskService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			search.addSortAsc("seq");
			SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
			List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
			
			int num = resultTaskList.size();
			
			String childSceneId="";
			SceneTableModel sceneTableModel =null;
			MasteSelCopyTask  entity=null;
			for(int i=0;i<num;i++){
				sceneTableModel=new SceneTableModel();
	
				entity= resultTaskList.get(i);
				//设置子场景id值
				childSceneId=entity.getChildSceneId();
				sceneTableModel.setChildSceneId(childSceneId);
					
				CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
				String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota from  maste_copy_thead d  " +
						"where d.copy_no='" +childSceneId+"' order by d.seq";
				List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
				
				String childSceneName="";
				int tableHeaderLength=resultList.size();
				String[] tableHeader=new String[tableHeaderLength]; 
				
				
		
				List  list = new ArrayList();
				int j =0;
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					tableHeader[j]=(String)listOrderedMap.get("thead_name");
					j++;
					childSceneName =(String)listOrderedMap.get("copy_name");
				}
				j=0;
				
				//设置子场景中文值
				sceneTableModel.setChildSceneName(childSceneName);
				
				//设置表头	
				sceneTableModel.setTableHeader(tableHeader);
				
				//设置数据
				//int tableDateLength=4;
				List<String[]> tableData=new ArrayList<String[]>();
				
				//获取需要查找的sql
				String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+childSceneId+"'";
				List<ListOrderedMap> dataConstr = jdbcService.queryForList(dataSqlConstr);
				
				String task_rel_strs ="";
				String con_strs ="";
				for(ListOrderedMap listOrderedMap : dataConstr){
					task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
					con_strs = (String)listOrderedMap.get("con_strs");
				}
				
				String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO from maste_rel_task_item where CHILD_SCENE_ID='"+childSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"'";
				
				String[] con = con_strs==null?null:con_strs.split("#");
				String newStr ="";
				String lowerStr = "";
				int csize= con==null?0: con.length;
				
				for(int x=0;x<csize;x++){
					System.out.println("执行了:"+csize+"-000"+selDataSql);
					lowerStr = con[x].toLowerCase();
					newStr="(select tbody_name from maste_copy_tbody  where maste_copy_tbody.tbody_no=maste_rel_task_item."+lowerStr+") "+lowerStr;
					selDataSql = selDataSql.replace(lowerStr, newStr);
				}
				System.out.println("000000000selDataSql:"+selDataSql);
				List<ListOrderedMap> dataList = jdbcService.queryForList(selDataSql);
				
				String[] dataFiled = task_rel_strs.split("#"); 
				
				sceneTableModel.setTableData(tableData);			
				
			//返回集合
				sceneTableList.add(sceneTableModel);
								 
		/**------------------------------------------------------------------------------------------------------------------------------------------*/	        	 		
	
			 int titleCount=sceneTableList.get(i).getTableHeader().length;//子场景标题长度
    	     int dataCount=sceneTableList.get(i).getTableData().size();//子场景定额数据大小
    	     int assistMaterialTitleCount=0;
    	     if(sceneTableList.get(i).getAssistMaterialTableHeader()!=null && !"".equals(sceneTableList.get(i).getAssistMaterialTableHeader())){
    	    	 assistMaterialTitleCount=sceneTableList.get(i).getAssistMaterialTableHeader().length;//辅材列标题长度 
    	     }
    	     int manMaterialTitleCount=0;
    	     if(sceneTableList.get(i).getManSceneTableHeader() !=null && !"".equals(sceneTableList.get(i).getManSceneTableHeader())){
    	    	 manMaterialTitleCount=sceneTableList.get(i).getManSceneTableHeader().length;//主材列标题长度 
    	     }
    	     
    	     
    	     String worksheetTitle = sceneTableList.get(i).getChildSceneName();//子场景名称
             
    	       
             // 创建单元格样式   
             HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
             // 指定单元格居中对齐   
             cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
             // 指定单元格垂直居中对齐   
             cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
             // 指定当单元格内容显示不下时自动换行   
             cellStyleTitle.setWrapText(true);  
             // ------------------------------------------------------------------   
             HSSFCellStyle cellStyle = wb.createCellStyle();  
             // 指定单元格居中对齐   
             cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
             // 指定单元格垂直居中对齐   
             cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
             // 指定当单元格内容显示不下时自动换行   
             cellStyle.setWrapText(true);  
             // ------------------------------------------------------------------   
             // 设置单元格字体   
             HSSFFont font = wb.createFont();  
             font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
             font.setFontName("宋体");  
                 font.setFontHeight((short) 200);  
                 cellStyleTitle.setFont(font);  
    	     
    	 
	         int p=0;   	 
	         //获取主材  辅材数据 
			 CommonSpringJdbcService jdbcService1 = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
			 String item_no1="";
			 String[] tr1=null;
			 String[] manSheetName=new String[dataList.size()];
			 String[] assistSheetName=new String[dataList.size()];
			 int num1=0;
			 int num2=0;
             for(ListOrderedMap listOrderedMap : dataList){
                 for(int r=0;r<tableHeaderLength;r++){
                	 if("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])){
                		 item_no1 =(String)listOrderedMap.get("ITEM_NO");
                		 String searchSql3 ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
							"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
							"left join maste_data d on k.data_id = d.id " +
							"where k.type='1' and k.item_no='"+item_no1+"' and k.process_instance_id='"+processInstanceId+"'" +
									" order by to_number(nvl(d.sort_num,'0')) asc";
					
			 		List<ListOrderedMap> resultList3 = jdbcService.queryForList(searchSql3);
					List<SceneTableModel> mainMaterialTableList=new ArrayList<SceneTableModel>();
					
					//设置表头
					int mainMaterialTableHeaderLength=8;
					String[] mainMaterialTableHeader=new String[mainMaterialTableHeaderLength];  
					mainMaterialTableHeader[0]="序号";
					mainMaterialTableHeader[1]="名称";
					mainMaterialTableHeader[2]="规格程式";
					mainMaterialTableHeader[3]="单位";
					mainMaterialTableHeader[4]="数量";
					mainMaterialTableHeader[5]="单价";
					mainMaterialTableHeader[6]="合价";
					mainMaterialTableHeader[7]="材料是否利旧";
					sceneTableModel.setManSceneTableHeader(mainMaterialTableHeader);
					
					//设置数据
					List<String[]> mainMaterialTabletableData=new ArrayList<String[]>();
					String[] tr3=null;
					
					for(ListOrderedMap listOrderedMap3 : resultList3){
						tr3=new String[mainMaterialTableHeaderLength];
						tr3[0]= (String)listOrderedMap3.get("sort_num");
						tr3[1]= (String)listOrderedMap3.get("name");
						tr3[2]= (String)listOrderedMap3.get("standard");
						tr3[3]= (String)listOrderedMap3.get("unit");
						
						tr3[4]= listOrderedMap3.get("num")==null?"0":Double.parseDouble(listOrderedMap3.get("num").toString())+"";
						tr3[5]= listOrderedMap3.get("per_price")==null?"0":Double.parseDouble(listOrderedMap3.get("per_price").toString())+"";
						tr3[6]= listOrderedMap3.get("total_price")==null?"0":Double.parseDouble(listOrderedMap3.get("total_price").toString())+"";
						tr3[7]= (String)listOrderedMap3.get("w");

						mainMaterialTabletableData.add(tr3);
					}
							
							manSheetName[num1] =childSceneName+"主材表"+p;
	                		HSSFSheet sheet2 = wb.createSheet(manSheetName[num1]);

	                		ExcelUtils   exportExcel2 = new ExcelUtils(wb, sheet2);
	                		num1+=1;
	                		
                            int jishuqi1=0; 
                            exportExcel2.createNormalHead("主材表",mainMaterialTableHeaderLength-1,jishuqi1);
                            jishuqi1+=1;
                            //主材列标题
                            exportExcel2.createMaterialTitle(mainMaterialTableHeader, mainMaterialTableHeaderLength, jishuqi1);
                            jishuqi1+=1;
                            
                            //主材数据
                            if(mainMaterialTabletableData !=null && !"".equals(mainMaterialTabletableData)){
               	             for(int x=0;x<mainMaterialTabletableData.size();x++){
               	            	 HSSFRow row4 = sheet2.createRow(jishuqi1);  
               	                 HSSFCell cell4 = null;	
               	                 for(int x1=0;x1<mainMaterialTabletableData.get(x).length;x1++){
               	                	 cell4 = row4.createCell((short) x1);   
               	                	 cell4.setCellStyle(cellStyleTitle);
               		                 cell4.setCellValue(new HSSFRichTextString(mainMaterialTabletableData.get(x)[x1])); 
               	                 }
               	                jishuqi1+=1; 
               	             }
                        }
                            jishuqi1=0;
                            p+=1;
                            
                		 
						}else if("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])){						
							String searchSql1 ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
							"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
							"left join maste_data d on k.data_id = d.id " +
							"where k.type='0' and k.item_no='"+item_no1+"' and k.process_instance_id='"+processInstanceId+"'" +
									" order by to_number(nvl(d.sort_num,'0')) asc";
					
					 		List<ListOrderedMap> resultList1 = jdbcService1.queryForList(searchSql1);
							List<SceneTableModel> assistMaterialTableList=new ArrayList<SceneTableModel>();
							//设置表头
							int assistMaterialTableHeaderLength=8;
							String[] tableHeader1=new String[assistMaterialTableHeaderLength];  
							tableHeader1[0]="序号";
							tableHeader1[1]="名称";
							tableHeader1[2]="规格程式";
							tableHeader1[3]="单位";
							tableHeader1[4]="数量";
							tableHeader1[5]="单价";
							tableHeader1[6]="合价";
							tableHeader1[7]="材料是否利旧";
							sceneTableModel.setAssistMaterialTableHeader(tableHeader1);
							
							
							//设置数据
							List<String[]> assistMaterialTableData=new ArrayList<String[]>();
							String[] tr2=null;
							for(ListOrderedMap listOrderedMap1 : resultList1){
								tr2=new String[assistMaterialTableHeaderLength];
								tr2[0]= (String)listOrderedMap1.get("sort_num");
								tr2[1]= (String)listOrderedMap1.get("name");
								tr2[2]= (String)listOrderedMap1.get("standard");
								tr2[3]= (String)listOrderedMap1.get("unit");
								
								tr2[4]= listOrderedMap1.get("num")==null?"0":Double.parseDouble(listOrderedMap1.get("num").toString())+"";
								tr2[5]= listOrderedMap1.get("per_price")==null?"0":Double.parseDouble(listOrderedMap1.get("per_price").toString())+"";
								tr2[6]= listOrderedMap1.get("total_price")==null?"0":Double.parseDouble(listOrderedMap1.get("total_price").toString())+"";
								tr2[7]= (String)listOrderedMap1.get("w");
		
								assistMaterialTableData.add(tr2);
							}	
						 assistSheetName[num2]=childSceneName+"辅材表"+p;
						 HSSFSheet sheet1 = wb.createSheet(assistSheetName[num2]);
						 ExcelUtils  exportExcel1 = new ExcelUtils(wb, sheet1);
						 num2+=1;
						 int jishuqi=0; 
	                     exportExcel1.createNormalHead("辅材表",assistMaterialTableHeaderLength-1,jishuqi);
	                     jishuqi+=1;
	                     
	                     //辅材列标题
	                     exportExcel1.createMaterialTitle(tableHeader1, assistMaterialTableHeaderLength, jishuqi);
	                     jishuqi+=1;
	                     
	                     //辅材数据
		                     if(assistMaterialTableData !=null && !"".equals(assistMaterialTableData)){
		                    	 for(int x=0;x<assistMaterialTableData.size();x++){
			                    	 HSSFRow row3 = sheet1.createRow(jishuqi);  
			                         HSSFCell cell4 = null;	
			                         for(int x1=0;x1<assistMaterialTableData.get(x).length;x1++){
			                        	 cell4 = row3.createCell((short) x1);   
			                        	 cell4.setCellStyle(cellStyleTitle);
			        	                 cell4.setCellValue(new HSSFRichTextString(assistMaterialTableData.get(x)[x1])); 
			                         }
			                        jishuqi+=1;
			                     }
			                     p+=1;
			                     jishuqi=0;
		                     }
	                     
	           		      	
						}
                	 
                 }
                 
             }
    	      
    	     
             
           
             //子场景名称   
             exportExcel.createNormalHead(worksheetTitle,titleCount-1,y);
             y+=1;
     		
             //子场景列标题
             exportExcel.createTitle(sceneTableList,titleCount,y,i);//参数：1.对象集合含子场景标题2、标题长度，3行数，4对象下标
             y+=1;
             
              
             //子场景定额数据行
             int x=0;
             int n=0;
             for(ListOrderedMap listOrderedMap : dataList){                
            	 HSSFRow row2 = sheet.createRow(y);  
                 HSSFCell cell2 = null;
                 for(int r=0;r<tableHeaderLength;r++){
                	 if("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])){	 
	                		 cell2 = row2.createCell((short) r);
	                		 cell2.setCellValue(new HSSFRichTextString("查看"));
	                		 HSSFHyperlink link = cell2.getHyperlink();
	                		 link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
	                		 link.setAddress("'"+manSheetName[x]+"'!A1");
	                		 cell2.setHyperlink(link);
//	                		 cell2.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//	                		 cell2.setCellFormula("HYPERLINK(\"["+downFilename+"]'"+manSheetName[x]+"'!A1\",\"查看\")"); 
	                		 HSSFCellStyle linkStyle = wb.createCellStyle();
	                		 HSSFFont cellFont= wb.createFont();  
	                		 cellFont.setUnderline((byte) 1);
	                		 cellFont.setColor(HSSFColor.BLUE.index);
	                		 linkStyle.setFont(cellFont);
	                		 linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
	                		 linkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
	                		 cell2.setCellStyle(linkStyle);
	                		 
	                		 x+=1;
						}else if("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])){
							 cell2 = row2.createCell((short) r);
							 cell2.setCellValue(new HSSFRichTextString("查看"));
	                		 HSSFHyperlink link = cell2.getHyperlink();
	                		 link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
	                		 link.setAddress("'"+assistSheetName[n]+"'!A1");
	                		 cell2.setHyperlink(link);
//							 cell2.setCellType(HSSFCell.CELL_TYPE_FORMULA);
//	                		 cell2.setCellFormula("HYPERLINK(\"["+downFilename+"]'"+assistSheetName[n]+"'!A1\",\"查看\")"); 
	                		 HSSFCellStyle linkStyle = wb.createCellStyle();
	                		 HSSFFont cellFont= wb.createFont();
	                		 cellFont.setUnderline((byte) 1);
	                		 cellFont.setColor(HSSFColor.BLUE.index);
	                		 linkStyle.setFont(cellFont);
	                		 linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
	                		 linkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
	                		 cell2.setCellStyle(linkStyle);
			                 n+=1;
						}else {	
							 cell2 = row2.createCell((short) r);   
			                 cell2.setCellStyle(cellStyleTitle);
			                 cell2.setCellValue(new HSSFRichTextString((String)listOrderedMap.get(dataFiled[r]))); 
							  
						}
                	 
                 }
                 
                 y+=1;
             }
             
             
             
     	}	
//			int sheetNum=wb.getNumberOfSheets();
//			System.out.println(sheetNum);
//			for(int i=1;i<sheetNum;i++){
//				wb.setSheetHidden(i, true);//隐藏所有主材辅材sheet页
//			}
		
		/*写入临时文件-----------------------------------------------------------------*/	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
     		String nowDate=df.format(new Date());// new Date()为获取当前系统时间
     		String path="D:/";
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
	            String downFilename="本地网预检预修场景模板.xls";//要下载的文件名称
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
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		String processInstanceId = request.getParameter("procInstId");
		String toTaskKey = request.getParameter("destTaskKey");
		String rejectMessage = request.getParameter("rejectMessage");
		// 设定驳回标志
		Map<String, Object> variables = new HashMap<String, Object>(0);
//		variables.put("workOrderCheck", "superUser");
//		variables.put("cityManageChargeState", "rollback");
//		
//		variables.put("nextTaskAssignee", "superUser");
//		variables.put("provinceManageChargeState", "rollback");
		
		
		variables.put("workOrderCheck", "superUser");
		variables.put("provinceLineViceState", "rollback");
		
//		pnrTransferNewPrecheckService.rejectToAnyTask( processInstanceId,  toTaskKey,
//				 rejectMessage,variables);
		
		RejectToAnyTaskUtil.rejectToAnyTask(processInstanceId, toTaskKey, rejectMessage, variables);
		
		return mapping.findForward("textRejectTask");
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
		try{
			String rejectLink = request.getParameter("rejectLink");//驳回到
			String handle = request.getParameter("handle");//当前环节
			String rejectMessage="";//驳回说明
			//默认驳回上一个环节。
			//市运维主任审核驳回到上一环节和驳回市运维业务主管，都是驳回到同一个环节。
			//工单发起审核驳回到上一环节和驳回发起人，都是驳回到同一个环节。
			if(rejectLink.equals("last")){
				this.rollback(mapping, form, request, response);//调用原来的驳回方法
			}else{
				String returnPerson="";
				Map<String, Object> variables = new HashMap<String, Object>(0);//驳回参数
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
				String processInstanceId = request.getParameter("processInstanceId");//流程号
				String rejectState = request.getParameter("rejectState");//驳回状态名值对
				if(rejectLink.equals("cityManageExamine")){//驳回到市运维主管审核
					rejectMessage="从"+handle+"驳回到cityManageExamine";
					returnPerson=pnrTransferNewPrecheckService.getLinkProcessingUserId("cityManageExamine",processInstanceId);
					variables.put("nextTaskAssignee",returnPerson);//市运维主管审核人
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

					

					// 市运维主管（有待办权利）-驳回时，将待办状态回执
//					if (handle.equals(TASK_CITYMANAGEEXAMINE)) {
//						if (3 == pnrTransferOffice.getState()) {
//
//							pnrTransferOffice.setState(0);// 正常派发时刻
//						}
//					}
					// 将驳回标志置成1，代表该工单是驳回工单
					pnrTransferOffice.setRollbackFlag("1");
					
					if(handle.equals(TASK_WORKER)){
						// 清空工单表施工队回单环节的场景模板信息
						pnrTransferOffice.setSumWorkerCostOfPartyB(null);
						pnrTransferOffice.setWorkerProjectAmount(null);
						pnrTransferOffice.setWorkerIncomeRatio(null);
						pnrTransferOffice.setWorkerChildIds("");
						pnrTransferOffice.setWorkerChildNames("");
						pnrTransferOffice.setWorkerSceneHandleFlag("");
						
						//删除施工队回单环节场景模板
						ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
						sceneTemplateService.deleteSceneTemplate(processInstanceId, "worker", null);
					}else if(handle.equals(TASK_ORDERAUDIT)){//区县公司验收环节
						// 清空区县公司验收环节的场景模板信息
						pnrTransferOffice.setSumOrderAuditCostOfPartyB(null);
						pnrTransferOffice.setOrderauditProjectAmount(null);
						pnrTransferOffice.setOrderauditIncomeRatio(null);
						pnrTransferOffice.setOrderauditChildIds("");
						pnrTransferOffice.setOrderauditChildNames("");
						pnrTransferOffice.setWorkerSceneOrderAuditFlag("");
						//删除区县公司验收环节场景模板
						ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
						sceneTemplateService.deleteSceneTemplate(processInstanceId, "orderAudit", null);
					}
					
					//在工单主表中添加流程信息
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
	 * 市运维主管审批的时候,对项目金额进行判断
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward validateBudgetAmount(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String returnJson = "";
//		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//		.getSession().getAttribute("sessionform");
//		String processInstanceId = request.getParameter("processInstanceId");
//		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
//		String result=pnrTransferNewPrecheckService.validateBudgetAmount(sessionform,processInstanceId);
//		if("exceed".equals(result)){
//			returnJson = "{\"result\":\"exceed\",\"content\":\"无法审批通过，已临近本月预算金额封顶值\"}";
//		}else if("notExceed".equals(result)){
//			returnJson = "{\"result\":\"notExceed\",\"content\":\"审批通过\"}";
//		}else{
//			returnJson = "{\"result\":\"error\",\"content\":\""+result+"\"}";
//		}
//		PrintWriter out = response.getWriter();
//		out.print(returnJson);
//		return null;
//	}
//	
	/**
	 * 
	 * 设置olt-bbu机房优化工单和机房关联
	 * 
	 * @param request
	 * @param pnrTransferOffice
	 * @throws ParseException
	 */
	private void setPnrOltBbuOfficeRelationByRequest(HttpServletRequest request,
			PnrOltBbuOfficeRelation pnrOltBbuOfficeRelation) throws ParseException {
		//机房id
		String roomId = StaticMethod.nullObject2String(request.getParameter("oltBbuRoomId"));
		//地市
		String jfCity = StaticMethod.nullObject2String(request.getParameter("jfCity"));
		//区县
		String jfCountry = StaticMethod.nullObject2String(request.getParameter("jfCountry"));
		//设备位置 局址名称
		String jfAddressName = StaticMethod.nullObject2String(request.getParameter("jfAddressName"));
		//机房内OLT数量
		String oltNumber = StaticMethod.nullObject2String(request.getParameter("oltNumber"));
		//总用户数
		String totalUserNumber = StaticMethod.nullObject2String(request.getParameter("totalUserNumber"));
		//语音用户数
		String voiceUser = StaticMethod.nullObject2String(request.getParameter("voiceUser"));
		//宽带用户数
		String broadbandUser = StaticMethod.nullObject2String(request.getParameter("broadbandUser"));
		//IPTV用户数
		String iptvUser = StaticMethod.nullObject2String(request.getParameter("iptvUser"));
		//机房内有无BBU
		String isNoBbu = StaticMethod.nullObject2String(request.getParameter("isNoBbu"));
		//机房内BBU数量
		String bbuNumber = StaticMethod.nullObject2String(request.getParameter("bbuNumber"));
		//是否需要杆路投资
		String isNeedRodInvestment = StaticMethod.nullObject2String(request.getParameter("isNeedRodInvestment"));
		//是否需要光缆投资
		String isNeedCableInvestment = StaticMethod.nullObject2String(request.getParameter("isNeedCableInvestment"));
		//新建杆路长度*（1000M以内）
		String newBuiltRodLength = StaticMethod.nullObject2String(request.getParameter("newBuiltRodLength"));
		//新建杆路投资
		String newBuiltRodInvestment = StaticMethod.nullObject2String(request.getParameter("newBuiltRodInvestment"));
		//原光缆路由简述*（如A-B-C-D）
		String originalCableRoute = StaticMethod.nullObject2String(request.getParameter("originalCableRoute"));
		//新光缆路由简述*（如A-E-F-D）
		String newCableRoute = StaticMethod.nullObject2String(request.getParameter("newCableRoute"));
		//新建段落（如E-F）
		String newParagraph = StaticMethod.nullObject2String(request.getParameter("newParagraph"));
		//光缆布放芯数
		String cableClothCoreNumber = StaticMethod.nullObject2String(request.getParameter("cableClothCoreNumber"));
		//光缆布放长度*（5KM以内）
		String cableClothLength = StaticMethod.nullObject2String(request.getParameter("cableClothLength"));
		//光缆投资估算
		String cableInvestment = StaticMethod.nullObject2String(request.getParameter("cableInvestment"));
		//线路总投资估算
		String lineTotalInvestment = StaticMethod.nullObject2String(request.getParameter("lineTotalInvestment"));
		//设备板卡需求
		String boardDemand = StaticMethod.nullObject2String(request.getParameter("boardDemand"));
		//传输设备板卡需求
		String transBoardDemand = StaticMethod.nullObject2String(request.getParameter("transBoardDemand"));
		//设备光模块需求
		String lightModuleDemand = StaticMethod.nullObject2String(request.getParameter("lightModuleDemand"));
		//传输设备光模块需求
		String transLightModuleDemand = StaticMethod.nullObject2String(request.getParameter("transLightModuleDemand"));
		//设备类投资估算
		String equipmentInvestment = StaticMethod.nullObject2String(request.getParameter("equipmentInvestment"));
		//备注
		String jfRemark = StaticMethod.nullObject2String(request.getParameter("jfRemark"));
		
		pnrOltBbuOfficeRelation.setRoomId(roomId);
		pnrOltBbuOfficeRelation.setJfCity(jfCity);
		pnrOltBbuOfficeRelation.setJfCountry(jfCountry);
		pnrOltBbuOfficeRelation.setJfAddressName(jfAddressName);
		pnrOltBbuOfficeRelation.setOltNumber(oltNumber);
		pnrOltBbuOfficeRelation.setTotalUserNumber(totalUserNumber);
		pnrOltBbuOfficeRelation.setVoiceUser(voiceUser);
		pnrOltBbuOfficeRelation.setBroadbandUser(broadbandUser);
		pnrOltBbuOfficeRelation.setIptvUser(iptvUser);
		pnrOltBbuOfficeRelation.setIsNoBbu(isNoBbu);
		pnrOltBbuOfficeRelation.setBbuNumber(bbuNumber);
		pnrOltBbuOfficeRelation.setIsNeedRodInvestment(isNeedRodInvestment);
		pnrOltBbuOfficeRelation.setIsNeedCableInvestment(isNeedCableInvestment);
		pnrOltBbuOfficeRelation.setNewBuiltRodLength(newBuiltRodLength);
		pnrOltBbuOfficeRelation.setNewBuiltRodInvestment(newBuiltRodInvestment);
		pnrOltBbuOfficeRelation.setOriginalCableRoute(originalCableRoute);
		pnrOltBbuOfficeRelation.setNewCableRoute(newCableRoute);
		pnrOltBbuOfficeRelation.setNewParagraph(newParagraph);
		pnrOltBbuOfficeRelation.setCableClothCoreNumber(cableClothCoreNumber);
		pnrOltBbuOfficeRelation.setCableClothLength(cableClothLength);
		pnrOltBbuOfficeRelation.setCableInvestment(cableInvestment);
		pnrOltBbuOfficeRelation.setLineTotalInvestment(lineTotalInvestment);
		pnrOltBbuOfficeRelation.setBoardDemand(boardDemand);
		pnrOltBbuOfficeRelation.setTransBoardDemand(transBoardDemand);
		pnrOltBbuOfficeRelation.setLightModuleDemand(lightModuleDemand);
		pnrOltBbuOfficeRelation.setTransLightModuleDemand(transLightModuleDemand);
		pnrOltBbuOfficeRelation.setEquipmentInvestment(equipmentInvestment);
		pnrOltBbuOfficeRelation.setJfRemark(jfRemark);
		
	}
	
	/**
	 * 获取机房基本信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectOltBbuRool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//int pageSize = CommonUtils.PAGE_SIZE;		
		int pageSize = 50;
		String tempPageSize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "roomAssetsList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
				conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		String jfAddressName=request.getParameter("jfAddressName");

		String themeInterface=request.getParameter("themeInterface");
		String oltBbuRoomId=request.getParameter("oltBbuRoomId");
		
		conditionQueryModel.setJfAddressName(jfAddressName);
		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");
		// 机房清单集合数
		int total = 0;
		try {
			total = pnrOltBbuRoomService.selectOltBbuRoomCount(themeInterface,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 机房清单集合
		List<PnrOltBbuRoom> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrOltBbuRoomService
					.selectOltBbuRoolList(themeInterface, conditionQueryModel,
							firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		

		request.setAttribute("roomAssetsList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("oltBbuRoomId", oltBbuRoomId);
		request.setAttribute("total", total);
		request.setAttribute("jfAddressName", jfAddressName);
		return mapping.findForward("selectPnrOltBbuRoom");
	}
	
	/**
	 * 
	 * 根据选择的机房清单ID值,查询机房详细信息,显示到新增派发页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectOltBbuRoomTodo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String selectedOltBbuRoomId = request.getParameter("selectedOltBbuRoomId");

		List<PnrOltBbuRoom> pnrOltBbuRoomList = new ArrayList<PnrOltBbuRoom>();
		JSONArray json = new JSONArray();

		int count = 0;// 计数器

		IPnrOltBbuRoomService pnrOltBbuRoomService = (IPnrOltBbuRoomService) getBean("pnrOltBbuRoomService");

				Search search = new Search();
				search.addFilterEqual("id", selectedOltBbuRoomId);
//				pnrOltBbuRoomList = pnrOltBbuRoomService.search(search);
				pnrOltBbuRoomList = pnrOltBbuRoomService.findOltBbuRoomByid(selectedOltBbuRoomId);
				if (pnrOltBbuRoomList != null && pnrOltBbuRoomList.size() > 0) {
					JSONObject jo = new JSONObject();
					jo.put("id", pnrOltBbuRoomList.get(0).getId());
					//场景类型
					jo.put("themeInterface", pnrOltBbuRoomList.get(0).getThemeInterface());
					//地市
					jo.put("city", pnrOltBbuRoomList.get(0).getJfCity());
					//区县
					jo.put("country", pnrOltBbuRoomList.get(0).getJfCountry());
					//设备位置/局址名称
					jo.put("addressName", pnrOltBbuRoomList.get(0).getJfAddressName());
					//设备位置/局址名称
					jo.put("oltNumber", pnrOltBbuRoomList.get(0).getOltNumber());
					//设备型号
					jo.put("totalUserNumber", pnrOltBbuRoomList.get(0).getTotalUserNumber());
					//语音用户
					jo.put("voiceUser", pnrOltBbuRoomList.get(0).getVoiceUser());
					//宽带用户
					jo.put("broadbandUser", pnrOltBbuRoomList.get(0).getBroadbandUser());
					//IPTV用户
					jo.put("iptvUser", pnrOltBbuRoomList.get(0).getIptvUser());
					//机房内有无BBU
					jo.put("isNoBbu", pnrOltBbuRoomList.get(0).getIsNoBbu());
					//机房内BBU数量
					jo.put("bbuNumber", pnrOltBbuRoomList.get(0).getBbuNumber());
					//线路总投资估算
					jo.put("lineTotalInvestment", pnrOltBbuRoomList.get(0).getLineTotalInvestment());
					json.put(jo);
				}
		

		response.getWriter().write(json.toString());
		return null;
	}

}
