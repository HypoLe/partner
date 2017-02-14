package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
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
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.ParameterModel;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferOfficeHandleModel;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTransferArteryPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferOverhaulRemakeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.PnrPrecheckPhotoService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
   * @author wangyue
   * @ClassName: PnrTransferArteryPrecheckAction
   * @Version 版本 
   * @Copyright boco
   * @date Mar 6, 2015 8:59:22 AM
   * @description 干线预检预修工单action
 */
public class PnrTransferArteryPrecheckAction extends SheetAction {
	
	private final String processDefinitionKey = "transferArteryPrecheckProcess";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger.getLogger(PnrTransferPrecheckAction.class);
	
	// 发信息用的常量
	public final static String TASK_MESSAGE = "干线预检预修工单已派至您工位待处理：";
	public final static String TASK_WORKORDERDEGREE = "紧急程度：";
	public final static String TASK_WORKORDERTYPE = "类型：";
	public final static String TASK_SUBTYPE = "子类型：";
	public final static String TASK_NO_STR = "工单号:";
	public final static String TASK_TITLE_STR = "主题:";
	public final static String TASK_DEADSUBMIT_STR = "申请提交时间:";
	public final static String TASK_DEADLINE_STR = "截止时间:";
	public final static String TASK_CONTACT_STR = "联系人及电话:";
	
	private final static String TASK_WORKORDERCHECK = "workOrderCheck";
	private final static String TASK_CITYLINEEXAMINE = "cityLineExamine";
	private final static String TASK_CITYLINEDIRECTOR = "cityLineDirectorAudit";
	private final static String TASK_PROVINCEMANAGE = "provinceManageCheck";
	private final static String TASK_CITYMANAGEEXAMINE = "cityManageExamine";
	private final static String TASK_CITYMANAGEDIRECTOR = "cityManageDirectorAudit";
	private final static String TASK_CITYVICEAUDIT = "cityViceAudit";
	private final static String TASK_PROVINCELINEVICE = "provinceLineViceAudit";
	private final static String TASK_PROVINCEMANAGEEXAMINE = "provinceManageExamine";
	private final static String TASK_PROVINCEMANAGEVICE = "provinceManageViceAudit";
	// 施工队
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 工单删除状态
	public final static Integer TASK_DELETE_STATE = 1;
	/** 动态获取角色id实体类 */
	private PrecheckRoleModel precheckRoleModel = (PrecheckRoleModel) ApplicationContextHolder
			.getInstance().getBean("precheckRoleModel");
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	/**
	 * 干线预检预修工单--新建页面111
	  * @author wangyue
	  * @title: newTask
	  * @date Mar 6, 2015 9:30:22 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward newTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CommonUtils.getCompetenceLimit(request);
		request.setAttribute("mainFaultOccurTime", new Date());//申请提交时间显示为系统当前时间
		return mapping.findForward("new");
	}
	
	/**
	 * 干线预检预修工单--待回复工单查询
	  * @author wangyue
	  * @title: performAdd
	  * @date Mar 6, 2015 9:30:22 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskDefKey = "workOrderCheck";
		String taskDefKeyName = "工单发起审核";
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String isDraft = request.getParameter("isDraft");
	
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
		if("true".equals(isDraft)){//如果为true说明是保存草稿 状态为2
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
		
		//图片信息
		String photoesIds = request.getParameter("photoIds");
		String photoNames = request.getParameter("photoName");
		
		
		//设置工单的区域
		pnrTransferOffice.setCity(city); 
		pnrTransferOffice.setCountry(areaid);
		
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		String sheetId = "";
		String tempSheetId = request.getParameter("sheetId");
		if (tempSheetId != null && !tempSheetId.equals("")) {
			sheetId = tempSheetId;
		} else {
			if (processInstanceId == null || "".equals(processInstanceId)) {

				try {
					sheetId = pnrTransferOfficeService.getSheetId(
							processDefinitionKey, userId, "G", "JX");

				} catch (Exception e) {
					request.setAttribute("msg", "请联系管理员，生成工单编号错误："
							+ e.toString());
					return mapping.findForward("failure");

				}
			}
		}
		
		System.out.println("----------sheetId="+sheetId);
		
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
			Map<String, String> map = new HashMap<String, String>();
			String tempKeyValue="";//输出key-value用
			map.put("workOrderCheck", taskAssignee);
			tempKeyValue+="workOrderAudit"+":"+taskAssignee+";";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formService.submitTaskFormData(taskId, map);
			// 流程结束
		}
		

		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			setTransferOfficeByRequest(request, pnrTransferOffice);// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);
		pnrTransferOffice.setThemeInterface("arteryPrecheck");
        
		//工单号
		pnrTransferOffice.setSheetId(sheetId);
		// attachment--start
		//清空该环节的附件
		if (processInstanceId != null && !"".equals(processInstanceId)) {
			pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, "1");
		}
		
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,"1");
		}
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, taskAssignee, pnrTransferOffice, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false);
		
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);
		
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		//将工单与图片关联起来
		PnrPrecheckPhotoService pnrPrecheckPhotoService = (PnrPrecheckPhotoService)getBean("pnrPrecheckPhotoService");
		String[] photoesId = photoesIds.split(",");
		if(photoesId!=null &&photoesId.length>0){
			pnrTransferNewPrecheckService.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
			for(String str :photoesId){
				if(str!=null && !"".equals(str)){
					PnrPrecheckPhoto pnrPrecheckPhoto = new PnrPrecheckPhoto();
					pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
					pnrPrecheckPhoto.setPhotoId(str);
					pnrPrecheckPhotoService.save(pnrPrecheckPhoto);
					pnrTransferNewPrecheckService.setStateByPhotoId(str, "1");
				}
			}
		}
		
		if(!"true".equals(isDraft)){//true 为保存草稿 则不用发送短信
			//发短信
			//短信接收人
			String messagePerson = pnrTransferOffice.getCountryCSJ();
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
				workOrderDegree+","+TASK_WORKORDERTYPE+workOrderType+","+TASK_SUBTYPE+subType+"。";
				CommonUtils.sendMsg(msg, messagePerson);
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 预检预修工单--待回复查询（待办工单除外）
	  * @author wangyue
	  * @title: listBacklog
	  * @date Feb 6, 2015 3:28:21 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");//工单状态
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");//线路属性
		String provName = request.getParameter("provName");//工单类型
		String precheckFlag = request.getParameter("precheckFlag");//工单类别
		//String mainSceneId = request.getParameter("mainSceneId");//场景
		
		 ConditionQueryModel conditionQueryModel =new  ConditionQueryModel();
		 conditionQueryModel.setSendStartTime(sendStartTime);
		 conditionQueryModel.setSendEndTime(sendEndTime);
		 conditionQueryModel.setWorkNumber(wsNum);
		 conditionQueryModel.setTheme(wsTitle);
		 conditionQueryModel.setStatus(status);
		 conditionQueryModel.setCity(region);
		 conditionQueryModel.setCountry(country);
		 conditionQueryModel.setLineType(lineType);
		 conditionQueryModel.setWorkOrderType(provName);
		 conditionQueryModel.setPrecheckFlag(precheckFlag);
		 //conditionQueryModel.setMainSceneId(mainSceneId);
		 conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getNewPrecheckCount(userId,
					"backlog", processDefinitionKey, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		double sumProjectAmount = 0.0D;//总的项目金额
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getTransferNewPrecheckList(userId, "backlog", processDefinitionKey,
							conditionQueryModel, firstResult, endResult,
							pageSize);
			
			//用来计算总的项目金额
			List<TaskModel> transferNewPrecheckList = pnrTransferNewPrecheckService.getTransferNewPrecheckList(userId, "backlog", processDefinitionKey,
					conditionQueryModel, firstResult, endResult,-1);
			for(TaskModel m:transferNewPrecheckList){
				sumProjectAmount += m.getProjectAmount();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String batchApprovalFlag="no";
		if(status!=null){
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
		request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);		
		request.setAttribute("precheckFlag", precheckFlag);
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
		//线路属性
		condition+="&lineType="+StaticMethod.nullObject2String(conditionQueryModel.getLineType());
		//工单类型
		condition+="&provName="+StaticMethod.nullObject2String(conditionQueryModel.getWorkOrderType());
		//场景
		//condition+="&mainSceneId="+StaticMethod.nullObject2String(conditionQueryModel.getMainSceneId());
		//工单状态
		condition+="&status="+StaticMethod.nullObject2String(conditionQueryModel.getStatus());
		//工单类别
		condition+="&precheckFlag="+StaticMethod.nullObject2String(conditionQueryModel.getPrecheckFlag());
		//工单子类型
		condition+="&subTypeName="+StaticMethod.nullObject2String(conditionQueryModel.getSubTypeName());
		//关键字
		condition+="&keyWord="+StaticMethod.nullObject2String(conditionQueryModel.getKeyWord());
		//紧急程度
		condition+="&workOrderDegree="+StaticMethod.nullObject2String(conditionQueryModel.getWorkOrderDegree());
		
		//每页显示条数
		if(conditionQueryModel.getPageSize()==null||"".equals(conditionQueryModel.getPageSize())){
			condition+="&pagesize=20";//默认条数50
		}else{
			condition+="&pagesize="+StaticMethod.nullObject2String(conditionQueryModel.getPageSize());
		}
		
		System.out.println("-------------干线流程 拼接待回复列表查询条件字符串="+condition);
		
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
		String provName = StaticMethod.nullObject2String(request.getParameter("provName"));//工单类型
		String subTypeName = StaticMethod.nullObject2String(request.getParameter("subTypeName"));//工单子类型
		String keyWord = StaticMethod.nullObject2String(request.getParameter("keyWord"));//关键字
		String workOrderDegree = StaticMethod.nullObject2String(request.getParameter("workOrderDegree"));//紧急程度
		
		//String mainSceneId = StaticMethod.nullObject2String(request.getParameter("mainSceneId"));//场景
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
		conditionQueryModel.setWorkOrderType(provName);
		conditionQueryModel.setSubTypeName(subTypeName);
		conditionQueryModel.setKeyWord(keyWord);
		conditionQueryModel.setWorkOrderDegree(workOrderDegree);
		//conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(pageSize);
		return conditionQueryModel;
	}
	
	
	/**
	 * 更改干线预检预修工单--紧急程度
	  * @author wangyue
	  * @title: changeDegree
	  * @date Mar 10, 2015 2:51:04 PM
	  * @return String
	 */
	public String changeDegree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		String workOrderDegree = request.getParameter("degree");
		String processInstanceId = request.getParameter("processInstanceId");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if(processInstanceId!=null && !"".equals(processInstanceId)){
			Search search = new Search();
			search.addFilterEqual("processInstanceId",processInstanceId);
			
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			response.setCharacterEncoding("utf-8");		
			
			if(list!=null && list.size()>0){
				//更改工单紧急程度
				PnrTransferOffice pnrTransferOffice =list.get(0);
				pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
				pnrTransferOfficeService.save(pnrTransferOffice);
				response.getWriter().write("紧急程度修改成功！");
			}else{
				response.getWriter().write("紧急程度修改失败！");
				
			}
		}else{
			response.getWriter().write("紧急程度修改失败！");
		}
		return "";
	}
	/**
	 * 新预检预修工单--回复
	  * @author wangyue
	  * @title: todo
	  * @date Feb 9, 2015 9:32:45 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
			PnrTransferOffice pnrTransferOffice =list.get(0);
			request.setAttribute("pnrTransferOffice", pnrTransferOffice);
			
			
			
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			Map<String, Object> gMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> handlelist = null;
			int handleSize =0;		
			search.addSort("checkTime", true);// 按回复时间排序
			gMap=getHandleList(gMap,transferHandleService, search, handlelist,handleSize);
			
			//获取审批信息
			Map<String, Object> approveMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveHandlelist = null;
			int approveHandleSize =0;
			Search approveSearch = new Search();
			approveSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveSearch.addFilterEqual("operation", "01");
			approveSearch.addSort("checkTime", true);// 按回复时间排序
			approveMap=getHandleList(approveMap,transferHandleService, approveSearch, approveHandlelist,approveHandleSize);
			//获取审批驳回信息
			Map<String, Object> approveBackMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> approveBackHandlelist = null;
			int approveBackHandleSize =0;	
			Search approveBackSearch = new Search();
			approveBackSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveBackSearch.addFilterEqual("operation", "02");
			approveBackSearch.addSort("checkTime", true);// 按回复时间排序
			approveBackMap=getHandleList(approveBackMap,transferHandleService, approveBackSearch, approveBackHandlelist,approveBackHandleSize);
			//获取专家会审信息
			Map<String, Object> triageMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> triagelist = null;
			int triageSize =0;	
			Search triageSearch = new Search();
			triageSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			triageSearch.addFilterEqual("operation", "03");
			triageSearch.addSort("checkTime", true);// 按回复时间排序
			triageMap=getHandleList(triageMap,transferHandleService, triageSearch, triagelist,triageSize);
			//获取施工队处理信息
			Map<String, Object> transferMap = new HashMap<String, Object>();
			List<PnrTransferOfficeHandle> transferlist = null;
			int transferHandleSize =0;	
			Search transferSearch = new Search();
			transferSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			transferSearch.addFilterEqual("operation", "04");
			transferSearch.addSort("checkTime", true);// 按回复时间排序
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
			auditSearch.addSort("checkTime", true);// 按回复时间排序
			auditMap=getHandleList(auditMap,transferHandleService, auditSearch, auditlist,auditSize);
			

			// attachments
			PnrTransferOffice ticket = new PnrTransferOffice();
			String userTaskId=task.getTaskDefinitionKey();
			String step="";//环节所在步骤的值
			if(userTaskId.equals("need")){//工单发起
				step="1";
			}else if(userTaskId.equals("workOrderCheck")){//工单发起审核
				step="2";
			}else if(userTaskId.equals("cityLineExamine")){//市线路业务主管审核
				step="3";
			}else if(userTaskId.equals("cityLineDirectorAudit")){//市线路主任审核
				step="4";
			}else if(userTaskId.equals("provinceLineExamine")){//省线路主管审核
				step="8";
			}else if(userTaskId.equals("provinceLineViceAudit")){//省线路总经理审核
				step="9";
			}else if (userTaskId.equals("usertask11")){//专家会审
				step="1";
			}else if(userTaskId.equals("provinceManageExamine")){//省运维主管审核
				step="10";
			}else if(userTaskId.equals("provinceManageViceAudit")){// 省运维总经理审批
				step="11";
			}else if (userTaskId.equals("daiweiAudit")) {// 审核
				step = "14";
			}else if(userTaskId.equals("orderAudit")){//抽查
				step = "15";
			}

			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceIdAndUserTaskId(processInstanceId,step);

//			String sheetAccessories = pnrTransferOfficeService
//					.getAttachmentNamesByProcessInstanceId(processInstanceId);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain", ticket);
			//String nn = task.getTaskDefinitionKey();
			// attachments-end
			List<TawCommonsAccessoriesForm> accessoriesList  = pnrTransferOfficeService.newShowSheetAccessoriesList(processInstanceId);
			request.setAttribute("sheetAccessories", accessoriesList);
			
			// 判断是否为抓回
			String tempRollbackFlag = request.getParameter("rollbackFlag");	
			Map<String, String> tempMap=null;
			if("2".equals(tempRollbackFlag)){
				tempMap = new HashMap<String, String>();
				tempMap.put("processInstanceId", processInstanceId);
				tempMap.put("linkName", userTaskId);
			}
			
			if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {//工单发起
				IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
				//查询出该工单所关联的图片，回显到页面中 
				IPnrTransferOverhaulRemakeService pnrTransferOverhaulRemakeService = (IPnrTransferOverhaulRemakeService)getBean("pnrTransferOverhaulRemakeService");
				String[] photoList = pnrTransferOverhaulRemakeService.getPhotoByProcessInstanceId(processInstanceId);
				//遍历photoID字符串，将图片关联标志state设置成0
				if(photoList[0]!=null&&!"".equals(photoList[0])){
					String[] photoesId = photoList[0].split(",");
					if(photoesId!=null &&photoesId.length>0){
						for(String str :photoesId){
							if(str!=null && !"".equals(str)){
									pnrTransferNewPrecheckService.setStateByPhotoId(str, "0");
							}
						}
					}
				}
				//回显的图片id串
				request.setAttribute("photoIds", photoList[0]);
				//回显的图片详细信息
				request.setAttribute("photoList", photoList[1]);
				request.setAttribute("mainFaultOccurTime",pnrTransferOffice.getSubmitApplicationTime());//回显申请提交时间\
				
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				
				String findForward = "new";
				return mapping.findForward(findForward);
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("workOrderCheck")) {//工单发起审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				//抓回回显信息				
				if("2".equals(tempRollbackFlag)){	
					TransferOfficeHandleModel transferOfficeHandleModel=transferHandleService.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel.getId());
				}
				return mapping.findForward("workOrderAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("cityLineExamine")) {//市线路业务主管审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				//抓回回显信息				
				if("2".equals(tempRollbackFlag)){	
					TransferOfficeHandleModel transferOfficeHandleModel=transferHandleService.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel.getId());
				}
				return mapping.findForward("cityLineChargeAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("cityLineDirectorAudit")) {//市线路主任审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				//抓回回显信息				
				if("2".equals(tempRollbackFlag)){	
					TransferOfficeHandleModel transferOfficeHandleModel=transferHandleService.getOneTransferHandle(tempMap);
					request.setAttribute("handleDescription",transferOfficeHandleModel.getHandleDescription());
					request.setAttribute("doPerson", transferOfficeHandleModel.getDoPerson());
					request.setAttribute("handleId", transferOfficeHandleModel.getId());
				}
				return mapping.findForward("cityLineDirectorAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("provinceLineExamine")) {//省线路主管审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				
				if((pnrTransferOffice==null?0:pnrTransferOffice.getState())==3){
					request.setAttribute("directList", "waitWorkOrderList");
				}
				
				return mapping.findForward("provinceLineChargeAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("provinceLineViceAudit")) {//省线路总经理审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				return mapping.findForward("provinceLineViceAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("provinceManageExamine")) {//省运维主管审核
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				return mapping.findForward("provinceManageChargeAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("usertask11")) {//专家会审
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("provinceManageViceAudit")) {// 省运维总经理审批
				//审批消息
				request.setAttribute("approveListsize", approveMap.get("size"));
				request.setAttribute("approveHandleList", approveMap.get("list"));
				//驳回信息
				request.setAttribute("approveBackListsize", approveBackMap.get("size"));
				request.setAttribute("approveBackList", approveBackMap.get("list"));
				// 显示附件
				showReplySetAttribute(request,(List<PnrTransferOfficeHandle>)gMap.get("list"));
				String findForward = "provinceManageViceAudit";

				return mapping.findForward(findForward);
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
						
					request.setAttribute("auditor", list.get(0).getCreateWork());
					
				}
				//驳回信息
				request.setAttribute("auditListsize", auditMap.get("size"));
				request.setAttribute("auditList", auditMap.get("list"));
				return mapping.findForward("transferHandle");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("daiweiAudit")) {// 审核-区县传输局
				//金额限制--start
//				String arteryLastDicId = precheckRoleModel.getArteryLastDicId();
//				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
				
//				TawSystemDictType dictypeLast = mgr.getDictByDictId(arteryLastDicId);
//				double doubleLast = 0L;
				
//				if(dictypeLast !=null && StaticMethod.getFloatValue(dictypeLast.getDictRemark())>-1){
//					doubleLast = Double.parseDouble(dictypeLast.getDictRemark());
//				}else{
//					
//					request.setAttribute("msg","派单审核后-金额设置，有问题，请联系管理员");
//					return mapping.findForward("failure");
//				}
				
				//金额限制--end
				
				search.addFilterEqual("linkName", "worker");

				request.setAttribute("PnrTransferHandleList", gMap.get("list"));
				request.setAttribute("listsize", gMap.get("size"));

				if (list.size() > 0) {
					request.setAttribute("auditor", list.get(0)
							.getCreateWork());
				}
				showReplySetAttribute(request, (List<PnrTransferOfficeHandle>)gMap.get("list"));
				//根据不同等级的项目估算金额获取不同的抽查人员
//				Double projectAmount = pnrTransferOffice.getProjectAmount()==null?0.0:pnrTransferOffice.getProjectAmount();
//				String testAudit = "";
//					if(projectAmount== null || projectAmount<doubleLast){//区县维护中心抽查
//						testAudit=pnrTransferOffice.getCreateWork();
//					}else{//市运维部抽查
//						testAudit=pnrTransferOffice.getCityManageCharge();
//					}
					
				//市线路主管
				String testAudit = pnrTransferOffice.getCityLineCharge();
				if(testAudit == null || "".equals(testAudit)){
					testAudit = "superUser";
				}
				request.setAttribute("testAudit",testAudit);
				//施工队处理信息
				request.setAttribute("transferListsize", transferMap.get("size"));
				request.setAttribute("transferList", transferMap.get("list"));
				
				//距离显示
				try {
					String distance = pnrTransferOfficeService.getDistanceShow(
							processInstanceId, "pnrtranfault");
					request.setAttribute("distanceShow", distance);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return mapping.findForward("secondAudit");
			}else if(task.getTaskDefinitionKey().equalsIgnoreCase("orderAudit")){//抽查
				return mapping.findForward("orderAudit");
			}
		}

		return mapping.findForward("error");
	}
	
	/**
	 * 干线预检预修工单--普通审核通过
	  * @author wangyue
	  * @title: transferProgram
	  * @date Feb 9, 2015 10:40:03 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward transferProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

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
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
		String attachments = request.getParameter(linkName);
		
		String step = request.getParameter("step");
		
		String handleId = request.getParameter("handleId");
		
		String directList = request.getParameter("directList");//区分处理完毕后跳转的页面

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
		
		// attachment--start
		//清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
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
		
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			//将驳回标志置成0，代表该工单不在是驳回工单
			pnrTicket.setRollbackFlag("0");
			pnrTicket.setState(0);
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, nextPerson, pnrTicket, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTicket);
		}
		
		//跳转页面 check
		 String  goPage ="check";
		if("waitWorkOrderList".equals(directList)){
			goPage ="waitWorkOrderList";
		}
		request.setAttribute("success", goPage);
		request.setAttribute("condition", request.getParameter("condition"));

		// 发短信
		if (isSend) {
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, nextPerson);
		}
		return mapping.findForward("showSuccess");
	}
	
	
	/**
	 * 干线预检预修工单--省线路总经理审核
	  * @author wangyue
	  * @title: cityViceAudit
	  * @date Feb 9, 2015 5:29:17 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward provinceViceAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
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
			//将驳回标志置成0，代表该工单不再是驳回工单
			pnrTicket.setRollbackFlag("0");
		}
		// attachment--start
		//清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		// attachment--end
		//在此判断金额大小，根据不同的金额走不同的流程
		//工单发起填写的预算金额
		double projectAmount = 0.0;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
			projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
		}
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String arteryFirstDicId = precheckRoleModel.getArteryFirstDicId();
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		TawSystemDictType dictypeFirst = mgr.getDictByDictId(arteryFirstDicId);
		double doubleFirst = 0L;
		
		if(dictypeFirst != null && StaticMethod.getFloatValue(dictypeFirst.getDictRemark())>-1){
			doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
		}else{
			request.setAttribute("msg","省线路总经理审批下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");				
		}
		String person="";//在工单主表中添加处理人
		String taskDefKey = null;//在工单主表中添加环节KEY
		String taskDefKeyName = null;//在工单主表中添加环节NAME
		if(projectAmount>doubleFirst){//金额大于0--走省运维主管审核
			map.put("provinceLineViceState", "handle");
			map.put("provinceManageCharge", pnrTicket.getProvinceManageCharge());
			person = pnrTicket.getProvinceManageCharge();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		}else {//调用商城接口,流程停留在当前环节
			pnrTicket.setState(8);
			isSend = false;
			taskDefKey="waitingCallInterface";
			taskDefKeyName="省公司审批通过-等待商城";
		}
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName,false); 
		pnrTransferOfficeService.save(pnrTicket);
		if(isSend){
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, pnrTicket.getProvinceLineCharge());
		}
		
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 干线预检预修工单--省运维部总经理审批
	  * @author wangyue
	  * @title: provinceManageViceAudit
	  * @date Feb 9, 2015 5:28:36 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward provinceManageViceAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
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
			//将驳回标志置成0，代表该工单不再是驳回工单
			pnrTicket.setRollbackFlag("0");
		}
		// attachment--start
		//清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		//调用工单接口,流程停留在当前环节，并将工单状态state设置成8
			pnrTicket.setState(8);
			pnrTicket.setDistributedInterfaceTime(new Date());//省运维总经理审批通过时间，即派发接口时间
			//在工单主表中添加流程信息
		    processTaskService.setTvalueOfTask(processInstanceId, "", pnrTicket, PnrTransferOffice.class, "waitingCallInterface", "省公司审批通过-等待商城",false);
			pnrTransferOfficeService.save(pnrTicket);
		
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));	
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 干线预检预修工单--外包公司派发
	  * @author wangyue
	  * @title: mainSecond
	  * @date Feb 13, 2015 9:20:22 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
		entity.setRollbackFlag("0");//未驳回标识
		
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, handleWorker, entity, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime()==null?"":sFormat.format(entity.getEndTime());
		
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+entity.getProcessInstanceId()+","+TASK_TITLE_STR+entity.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(entity.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+entity.getWorkOrderTypeName()+","+TASK_SUBTYPE+entity.getSubTypeName()+"。";
		  CommonUtils.sendMsg(msg, handleWorker);
		
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");

	}
	
	/**
	 * 预检预修工单--施工队处理
	  * @author wangyue
	  * @title: transferHandle
	  * @date Feb 13, 2015 9:31:13 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOffice.setRollbackFlag("0");//未驳回标识
			
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false); 
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			
			msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			
			
			deadLineTime =pnrTransferOffice.getEndTime()==null?"":sFormat.format(pnrTransferOffice.getEndTime());
			contact=pnrTransferOffice.getConnectPerson();
		}
		transferHandleService.save(entity);
		
		CommonUtils.sendMsg(msg, auditor);
		 
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));  
		return mapping.findForward("showSuccess");

	}
	
	/**
	 * 预检预修工单--区县维护中心审核
	  * @author wangyue
	  * @title: secondAudit
	  * @date Feb 13, 2015 9:56:19 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
		
		// 决算金额-工费
		String balanceOperatingCosts = request.getParameter("balanceOperatingCosts");
		
		// 决算金额-材料费
		String balanceMaterialsCosts = request.getParameter("balanceMaterialsCosts");

		// 附件
		String attachments = request.getParameter("daiweiAudit");

		// 环节步骤值
		String step = request.getParameter("step");
		
		String person="";

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
		//决算金额-工费
		if (balanceOperatingCosts != null && !"".equals(balanceOperatingCosts)) {
			entity.setBalanceOperatingCosts(Double.parseDouble(balanceOperatingCosts));
		}
		//决算金额-材料费
		if (balanceMaterialsCosts != null && !"".equals(balanceMaterialsCosts)) {
			entity.setBalanceMaterialsCosts(Double.parseDouble(balanceMaterialsCosts));
		}
		
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
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if (pnrTicketList != null) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
		}
		if (auditState.equals("rollback")) {
			if(pnrTicket!=null){
				
				pnrTicket.setLastReplyTime(null);
				pnrTicket.setRollbackFlag("1");//驳回标识
				//pnrTransferOfficeService.save(pnrTicket);
				auditor = pnrTicket.getTaskAssignee();// 回退到二次处理人
				pnrTicket.setDaiweiAuditSceneHandleFlag(null);
			}
			isSend = false;
			map.put("initiatorHandleState", "rollback");
			map.put("handleWorker", auditor);
			person=auditor;
		}else if(auditState.equals("handle")){
			if(pnrTicket!=null){
			processInstanceId = pnrTicket.getProcessInstanceId();
			deadlineTime = pnrTicket.getEndTime()==null?"":sFormat.format(pnrTicket.getEndTime());
			contact = pnrTicket.getConnectPerson();
			pnrTicket.setRollbackFlag("0");//未驳回标识
			pnrTicket.setDaiweiAuditSceneHandleFlag("2");
			//pnrTransferOfficeService.save(pnrTicket);
			}
			
			//在此判断金额大小，根据不同的金额让不同的人抽查
			//工单发起填写的预算金额
			double projectAmount = 0.0;
			if (pnrTicketList != null && pnrTicketList.size() > 0) {
				pnrTicket = pnrTicketList.get(0);
				projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
			}
			
			String arteryLastDicId = precheckRoleModel.getArteryLastDicId();
			ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
			
			TawSystemDictType dictypeLast = mgr.getDictByDictId(arteryLastDicId);
			double doubleLast = 0L;
			
			if(dictypeLast != null && StaticMethod.getFloatValue(dictypeLast.getDictRemark())>-1){
				doubleLast = Double.parseDouble(dictypeLast.getDictRemark());
			}else{
				request.setAttribute("msg","省线路总经理审批下-金额限制，有问题；请联系管理员");
				return mapping.findForward("failure");				
			}
			String nextPerson = "";
			if(projectAmount>doubleLast){//金额大于5000,市运维部抽查
				nextPerson = pnrTicket.getCityManageCharge();
			}else {//市线路维护中心抽查
				nextPerson = pnrTicket.getCityLineCharge();
			}
			map.put("initiatorHandleState", "handle");
			map.put("testAudit", nextPerson);
			person=nextPerson;
		}
		
		String hjflag="";//区县维护中心审核 提交 驳回 标识
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
		
		//在工单主表中添加流程信息
		//processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, taskDefKey, taskDefKeyName);
		processTaskService.setTvalueOfTask(processInstanceId, person, pnrTicket, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTicket);
		
		//保存附件
		pnrTransferOfficeService
		.deleteAttachmentNamesByProcessInstanceIdAndStep(
				processInstanceId, step);
		
		if (attachments != null && !"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, step);
		}
		
		request.setAttribute("success", "check");

		// 发短信
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTicket.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
		CommonUtils.sendMsg(msg, auditor);
		
		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
	}

	/**
	 * 预检预修工单--回退
	  * @author wangyue
	  * @title: rollback
	  * @date Feb 10, 2015 10:15:50 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
		if (handle.equals(TASK_WORKORDERCHECK)) {//工单发起审核驳回
			hjflag="工单发起审核驳回";
			map.put("initiator", returnPerson);
			map.put("workOrderState", "rollback");
			msgPerson = returnPerson;
			linkName = "workOrderCheck";
		}
		else if (handle.equals(TASK_CITYLINEEXAMINE)) {//市线路业务主管驳回
			hjflag="市线路业务主管驳回";
			map.put("workOrderCheck",returnPerson);
			map.put("cityLineChargeState", "rollback");
			msgPerson=returnPerson;
			linkName = "cityLineExamine";
		}
		else if (handle.equals(TASK_CITYLINEDIRECTOR)) {//市线路主任驳回
			hjflag="市线路主任驳回";
			map.put("cityLineChargeAduit",returnPerson);
			map.put("cityLineDirectorState", "rollback");
			msgPerson=returnPerson;
			linkName = "cityLineDirectorAudit";
		}
		else if (handle.equals(TASK_CITYMANAGEDIRECTOR)) {//省线路主管驳回
			hjflag="省线路主管驳回";
			map.put("cityLineDirectorAudit", returnPerson);
			map.put("provinceLineChargeState", "rollback");
			msgPerson=initiator;
			linkName = "provinceLineExamine";
		}
		else if (handle.equals(TASK_PROVINCELINEVICE)) {//省线路总经理驳回
			hjflag="省线路总经理驳回";
			map.put("nextTaskAssignee", returnPerson);
			map.put("provinceLineViceState", "rollback");
			msgPerson=initiator;
			linkName = "provinceLineViceAudit";
		}
		else if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)) {//省运维主管驳回
			hjflag="省运维主管驳回";
			map.put("provinceLineViceAudit", returnPerson);
			map.put("provinceManageChargeState", "rollback");
			msgPerson=initiator;
			linkName = "provinceManageExamine";
		}
		else if (handle.equals(TASK_PROVINCEMANAGEVICE)) {//省运维总经理驳回
			hjflag="省运维总经理驳回";
			map.put("provinceManageCharge", returnPerson);
			map.put("provinceManageViceState", "rollback");
			msgPerson=initiator;
			linkName = "provinceManageViceAudit";
		}else if(handle.equals("orderAudit")){//抽查
			hjflag = "抽查";
			map.put("initiatorCheck", returnPerson);
			map.put("orderAuditHandleState", "rollback");
			msgPerson = returnPerson;
			linkName = "orderAudit";
		}
		
		String msg="";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		double projectAmount= 0.0;
		PnrTransferOffice pnrTransferOffice=null;
		if (pnrTicketList != null) {
			pnrTransferOffice = pnrTicketList.get(0);
			//将驳回标志置成1，代表该工单是驳回工单
			pnrTransferOffice.setRollbackFlag("1");
			projectAmount = pnrTransferOffice.getProjectAmount()==null?-1:pnrTransferOffice.getProjectAmount();
			
			msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
		
			
			if (handle.equals(TASK_PROVINCEMANAGEEXAMINE)){
				//删除会审辅助表记录的辅助信息
				pnrTransferOfficeService.deleteCountersignInfo(processInstanceId);
				
				pnrTransferOffice.setState(7);//停止会审
			}
			//省线路主管（有待办权利）-驳回时，将待办状态回执
			if(handle.equals(TASK_CITYMANAGEDIRECTOR)){
				if(3==pnrTransferOffice.getState()){
					
					pnrTransferOffice.setState(0);//正常派发时刻
				}
			}
			//pnrTransferOfficeService.save(pnrTransferOffice);
			
		}
		 
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
				if("orderAudit".equals(handle)){
					entity.setOperation("09");//抽查环节驳回 标识值为09
				}else{
					entity.setOperation("02");
				}
				transferHandleService.save(entity);
			} 
		
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		   formService.submitTaskFormData(taskId, map);
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
		   
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId, returnPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTransferOffice);
		 
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
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
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
				pnrTransferOffice.setRollbackFlag("1");//驳回标识
				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, initiator, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);
				
				msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			}
			
			//当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
			  CommonUtils.sendMsg(msg, msgPerson);
			  
		request.setAttribute("success", "check");	  
		return mapping.findForward("showSuccess");
	}
		/**
	 * 预检预修工单--待办功能
	  * @author wangyue
	  * @title: waitWorkOrder
	  * @date Feb 9, 2015 5:49:55 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward waitWorkOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//拼接查询条件
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
		//并将工单状态state设置成3
			pnrTicket.setState(3);
			pnrTransferOfficeService.save(pnrTicket);
		
		request.setAttribute("success", "check");
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 干线预检预修工单--待办工单查询
	  * @author wangyue
	  * @title: waitWorkOrderList
	  * @date Feb 10, 2015 8:32:32 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
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
		String status = request.getParameter("status");
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		String provName = request.getParameter("provName");//工单类型
		String precheckFlag = request.getParameter("precheckFlag");//工单类别
		//String mainSceneId = request.getParameter("mainSceneId");//场景
		
		 ConditionQueryModel conditionQueryModel =new  ConditionQueryModel();
		 conditionQueryModel.setSendStartTime(sendStartTime);
		 conditionQueryModel.setSendEndTime(sendEndTime);
		 conditionQueryModel.setWorkNumber(wsNum);
		 conditionQueryModel.setTheme(wsTitle);
		 conditionQueryModel.setStatus(status);
		 conditionQueryModel.setCity(region);
		 conditionQueryModel.setCountry(country);
		 conditionQueryModel.setLineType(lineType);
		 conditionQueryModel.setWorkOrderType(provName);
		 conditionQueryModel.setPrecheckFlag(precheckFlag);
		 //conditionQueryModel.setMainSceneId(mainSceneId);
		 conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferNewPrecheckService.getNewPrecheckCount(userId,
					"wait", processDefinitionKey, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferNewPrecheckService
					.getTransferNewPrecheckList(userId, "wait", processDefinitionKey,
							conditionQueryModel, firstResult, endResult,
							pageSize);
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
		request.setAttribute("precheckFlag", precheckFlag);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("waitWorkOrderList");
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
		// 项目金额估算
		String projectAmount = StaticMethod.nullObject2String(request
				.getParameter("projectAmount"));
		//实物赔补
		String kindRestitution = StaticMethod.nullObject2String(request.getParameter("kindRestitution"));
		//收支比
		String incomeRatio = StaticMethod.nullObject2String(request.getParameter("incomeRatio"));
		
		//线路名称
		String lineName = StaticMethod.nullObject2String(request
				.getParameter("lineName"));
		//项目起点
		String projectStartPoint = StaticMethod.nullObject2String(request
				.getParameter("projectStartPoint"));
		//项目终点
		String projectEndPoint = StaticMethod.nullObject2String(request
				.getParameter("projectEndPoint"));
		//具体地点
		String specificLocation = StaticMethod.nullObject2String(request
				.getParameter("specificLocation"));
		//是否为保存草稿
		String isDraft = StaticMethod.nullObject2String(request
				.getParameter("isDraft"));
		// 接收人
		String taskAssignee = "";
		// 接收人字符串
		String taskAssigneeJSON = "";
		// 工单ID
		String themeId = request.getParameter("themeId");
		
		//未做任何操作--start
		// 经度
		String longitude = StaticMethod.nullObject2String(request
				.getParameter("longitude"));
		// 纬度
		String latitude = StaticMethod.nullObject2String(request
				.getParameter("latitude"));
		//图片
		String picture = request.getParameter("picture");
		
		// 工单类别
		String precheckFlag = StaticMethod.nullObject2String(request
				.getParameter("precheckFlag"));
		// type of workorder
		String compensate = StaticMethod.nullObject2String(request
				.getParameter("compensate"));
		//--end
		
		
		
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
		pnrTransferOffice.setSubType(subType);
		pnrTransferOffice.setSubTypeName(CommonUtils.getId2NameString(subType,1, ","));
		pnrTransferOffice.setWorkOrderTypeName(CommonUtils.getId2NameString(workOrderType,1, ","));
		// 紧急程度
		pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
		// 关键字
		pnrTransferOffice.setKeyWord(keyWord);
		//没有确定的人，只是添加--默认superUser
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		PrecheckShipModel precheckShipModel = pnrTransferNewPrecheckService.getPrecheckShipModelBycountryCharge(initiator);
		//区县主任或者市城区班长
		pnrTransferOffice.setSecondCreateWork(precheckShipModel.getCountryDirector());
		pnrTransferOffice.setTaskAssignee(precheckShipModel.getCountryDirector());
		//市线路主管
		pnrTransferOffice.setCityLineCharge(precheckShipModel.getCityLineCharge());
		//市线路主任
		pnrTransferOffice.setCityLineDirector(precheckShipModel.getCityLineDirector());
		//市运维主管
		pnrTransferOffice.setCityManageCharge(precheckShipModel.getCityManageCharge());
		//角色获取
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		//List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceLineExamine());
		List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getArteryProvinceLineExamine());
		String provinceLineCharge = "superUser";
		if(list!=null && list.size()>0){
			if(list.get(0).get("USERID")!=null && !"".equals(list.get(0).get("USERID"))){
				provinceLineCharge = list.get(0).get("USERID").toString();
			}
		}
		//省线路主管
		pnrTransferOffice.setProvinceLineCharge(provinceLineCharge);
		
		//List<Map> provinceLineDirectorList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceLineConductor());
		List<Map> provinceLineDirectorList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getArteryProvinceLineConductor());
		String provinceLineDirector = "superUser";
		if(provinceLineDirectorList!=null && provinceLineDirectorList.size()>0){
			if(provinceLineDirectorList.get(0).get("USERID")!=null && !"".equals(provinceLineDirectorList.get(0).get("USERID"))){
				provinceLineDirector = provinceLineDirectorList.get(0).get("USERID").toString();
			}
		}
		//省线路总经理
		pnrTransferOffice.setProvinceLine(provinceLineDirector);
		
		List<Map> provinceManageChargeList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceManageExamine());
		String provinceManageCharge = "superUser";
		if(provinceManageChargeList!=null && provinceManageChargeList.size()>0){
			if(provinceManageChargeList.get(0).get("USERID")!=null && !"".equals(provinceManageChargeList.get(0).get("USERID"))){
				provinceManageCharge = provinceManageChargeList.get(0).get("USERID").toString();
			}
		}
		//省运维主管
		pnrTransferOffice.setProvinceManageCharge(provinceManageCharge);
		
		List<Map> provinceManageDirectorList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceManageConductor());
		String provinceManageDirector = "superUser";
		if(provinceManageDirectorList!=null && provinceManageDirectorList.size()>0){
			if(provinceManageDirectorList.get(0).get("USERID")!=null && !"".equals(provinceManageDirectorList.get(0).get("USERID"))){
				provinceManageDirector = provinceManageDirectorList.get(0).get("USERID").toString();
			}
		}
		//省运维总经理
		pnrTransferOffice.setProvinceManage(provinceManageDirector);
		
		//版本标志：1 代表新预检预修工单，旧预检预修工单为空
		//pnrTransferOffice.setVersionFlag("2");

		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		
		pnrTransferOffice.setWorkType(workType);
		pnrTransferOffice.setProjectAmount(Double.parseDouble(projectAmount==""?"0":projectAmount)); //项目金额
		
		//线路名称
		pnrTransferOffice.setLineName(lineName);
		//项目起点
		pnrTransferOffice.setProjectStartPoint(projectStartPoint);
		//项目终点
		pnrTransferOffice.setProjectEndPoint(projectEndPoint);
		//具体地点
		pnrTransferOffice.setSpecificLocation(specificLocation);
		pnrTransferOffice.setSendTime(new Date());
		pnrTransferOffice.setRollbackFlag("0");
		pnrTransferOffice.setPrecheckFlag(precheckFlag);
		if("true".equals(isDraft)){//草稿保存时间
			pnrTransferOffice.setSaveDraftDate(new Date());
		}
		pnrTransferOffice.setKindRestitution(Double.parseDouble(kindRestitution==""?"0":kindRestitution)); //实物赔补
		pnrTransferOffice.setCompensate(Double.parseDouble(compensate==""?"0":compensate)); //现金赔补
		pnrTransferOffice.setCalculateIncomeRatio(Double.parseDouble(incomeRatio==""?"0":incomeRatio));//收支比
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
	
	/**
	 * 打开批量处理子窗口
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
		boolean b =false;
		boolean flag =true;
		String returnJson = "";
		String error = "";
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
							pnrTicket.setState(0);
							// 改变回退工单状态
							pnrTicket.setRollbackFlag("0");
							String nextPerson = "";
							String nextAssigness = "";// 流程下一步人员
							String transferHandleFlag = "";// 处理标志
							
							// 解析字符串，获取到所在环节字段，并根据环节获取下一流程人
							if ("provinceManageViceAudit".equals(taskKey)) {
								pnrTicket.setDistributedInterfaceTime(new Date());// 省运维总经理审批通过时间，即派发接口时间
								pnrTicket.setState(8);
								b = true;
								flag = false;
							} else {
								Map<String, String> taskMap = new HashMap<String, String>();
								if("cityLineExamine".equals(taskKey)){//市线路主管审核
									nextAssigness = pnrTicket.getCityLineDirector();
									nextPerson = "cityLineDirectorAudit";
									transferHandleFlag = "cityLineChargeState";
									b = true;
								}else if("cityLineDirectorAudit".equals(taskKey)){//市线路主任审核
									nextAssigness = pnrTicket.getProvinceLineCharge();
									nextPerson = "nextTaskAssignee";
									transferHandleFlag = "cityLineDirectorState";
									b = true;
								}else if ("provinceLineExamine".equals(taskKey)) {// 省线路主管
									nextAssigness = pnrTicket.getProvinceLine();
									nextPerson = "provinceLineViceAudit";
									transferHandleFlag = "provinceLineChargeState";
									pnrTicket.setState(0);
									b = true;
								} else if ("provinceLineViceAudit".equals(taskKey)) {// 省线路总经理
									nextAssigness = pnrTicket
											.getProvinceManageCharge();
									nextPerson = "provinceManageCharge";
									transferHandleFlag = "provinceLineViceState";
									b = true;
								} else if ("provinceManageExamine".equals(taskKey)) {// 省运维主管
									nextAssigness = pnrTicket.getProvinceManage();
									nextPerson = "provinceManageVice";
									transferHandleFlag = "provinceManageChargeState";
									b = true;
								}
								
								if(b){
									taskMap.put(nextPerson, nextAssigness);
									taskMap.put(transferHandleFlag, "handle");
									formService.submitTaskFormData(taskId, taskMap);
									
								}

							}
							
							//在工单主表中添加流程信息
							if(flag){
								processTaskService.setTvalueOfTask(processInstanceId, nextAssigness, pnrTicket, PnrTransferOffice.class, null,null,false);
							}else{//调接口
								processTaskService.setTvalueOfTask(processInstanceId, "", pnrTicket, PnrTransferOffice.class, "waitingCallInterface", "省公司审批通过-等待商城",false);
							}
							
							if(b){
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
							
						}else{
							error += "工单号:" + processInstanceId + ",不存在;";
						}

						b=false;
						flag=true;
					} catch (Exception e) {
						b=false;
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
			returnJson = "{\"result\":\"error\",\"content\":\"" + error
					+ "\"}";

		}
		out.print(returnJson);
		return null;

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
			String taskId=map.get("taskId");

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
				
				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省线路主管审核
				taskMap.put("provinceLineChargeState", "handle");	//省线路主管审核标志 
				taskMap.put("provinceLineViceAudit", pnrTicket.getProvinceLine());	//省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);
				
				//发短信
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
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
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				boolean isSend = true;
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}
				
				//在此判断金额大小，根据不同的金额走不同的流程
				//工单发起填写的预算金额
				double projectAmount = 0.0;				
				projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
				String arteryFirstDicId = precheckRoleModel.getArteryFirstDicId();
				TawSystemDictType dictypeFirst = mgr.getDictByDictId(arteryFirstDicId);
				double doubleFirst = 0L;
				if(dictypeFirst != null && StaticMethod.getFloatValue(dictypeFirst.getDictRemark())>-1){
					doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
				}else{
					String tempMsg = "省线路总经理审批下-金额限制，有问题；请联系管理员";
					erorrStr += "工单号:" + processInstanceId + ":" + tempMsg
							+ ";";
					continue;			
				}
				
				//调用流程
				Map<String, String> taskMap = new HashMap<String, String>();				
				if(projectAmount>doubleFirst){//金额大于0--走省运维主管审核
					taskMap.put("provinceLineViceState", "handle");
					taskMap.put("provinceManageCharge", pnrTicket.getProvinceManageCharge());
					formService.submitTaskFormData(taskId, taskMap);
					pnrTicket.setState(0);
				}else {//调用商城接口,流程停留在当前环节
					pnrTicket.setState(8);
					isSend = false;
				}
				pnrTransferOfficeService.save(pnrTicket);
				
				//发短信
				if(isSend){
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceManageCharge());
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
		System.out.println("--------进到批量处理-省运维主管审核方法了吗？---------------");
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
			String taskId=map.get("taskId");

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

				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省运维主管审核
				taskMap.put("provinceManageChargeState", "handle");	//省运维主管审核标志
				taskMap.put("provinceManageVice", pnrTicket.getProvinceManage());	//省运维总经理审批人
				formService.submitTaskFormData(taskId, taskMap);
				
				//发短信
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
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
		System.out.println("--------进到批量处理-省运维总经理审批方法了吗？---------------");
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
				entity.setLinkName(paramMap.get("wsStatus"));
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchRegressionView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		boolean b =false;
		String error = ""; // 回复内容
		String returnJson = "";
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");
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
							pnrTicket.setState(0);
							
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
							
							if(b){
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
							
						}else{
							error += "工单号:" + processInstanceId + ",不存在;";
						}

						b=false;
					} catch (Exception e) {
						b=false;
						e.printStackTrace();
						error+="工单号:" + processInstanceId + ",处理异常;";
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
			returnJson = "{\"result\":\"error\",\"content\":\"" + error
					+ "\"}";
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
					taskMap.put("cityLineDirectorAudit", pnrTicket.getCityLineDirector());
					taskMap.put("provinceLineChargeState", "rollback");
					msgPerson=pnrTicket.getCityLineDirector();
					linkName = "provinceLineExamine";
				} else if (wsStatus.equals("provinceLineViceAudit")) {// 省线路总经理
					taskMap.put("nextTaskAssignee", pnrTicket.getProvinceLineCharge());
					taskMap.put("provinceLineViceState", "rollback");
					msgPerson=pnrTicket.getProvinceLineCharge();
					linkName = "provinceLineViceAudit";
				} else if (wsStatus.equals("provinceManageExamine")) {// 省运维主管
					taskMap.put("provinceLineViceAudit", pnrTicket.getProvinceLine());
					taskMap.put("provinceManageChargeState", "rollback");
					msgPerson=pnrTicket.getProvinceLine();
					linkName = "provinceManageExamine";
				} else if (wsStatus.equals("provinceManageViceAudit")) {// 省运维总经理
					taskMap.put("provinceManageCharge", pnrTicket.getProvinceManageCharge());
					taskMap.put("provinceManageViceState", "rollback");
					msgPerson=pnrTicket.getProvinceManageCharge();
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
		//logger.info("预检预修工单-已处理工单-查询开始");
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
//		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		
		//已处理工单条数
		int total = pnrTransferNewPrecheckService.getHaveProcessCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//已处理工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService.getHaveProcessList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);
		
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
	 * 由我创建的工单查询
	 * 
	 * @return
	 */
	public ActionForward listRunningProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
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
		
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));

		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		
		//由我创建的工单条数
		int total = pnrTransferNewPrecheckService.getByCreatingWorkOrderCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//由我创建的工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService.getByCreatingWorkOrderList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);

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
	 * 工单抓回
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

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));

		IPnrTransferArteryPrecheckService pnrTransferArteryPrecheckService = (IPnrTransferArteryPrecheckService) getBean("pnrTransferArteryPrecheckService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = pnrTransferArteryPrecheckService.getBackSheetCount(
					processDefinitionKey,userId,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = pnrTransferArteryPrecheckService
					.getBackSheetList(
							processDefinitionKey,userId,
							conditionQueryModel,
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
		if(taskDefKey.equals("workOrderCheck")){//工单发起审核
			map.put("initiator", pnrTransferOffice.getCreateWork());
			map.put("workOrderState", "rollback");
			person = pnrTransferOffice.getCreateWork();
		}else if(taskDefKey.equals("cityLineExamine")){//市线路主管审核
			map.put("workOrderCheck",pnrTransferOffice.getSecondCreateWork());
			map.put("cityLineChargeState", "rollback");
			person = pnrTransferOffice.getSecondCreateWork();
		}else if(taskDefKey.equals("cityLineDirectorAudit")){//市线路主任审核
			map.put("cityLineChargeAduit",pnrTransferOffice.getCityLineCharge());
			map.put("cityLineDirectorState", "rollback");
			person = pnrTransferOffice.getCityLineCharge();
		}else if(taskDefKey.equals("provinceLineExamine")){//省线路主管审核
			map.put("cityLineDirectorAudit", pnrTransferOffice.getCityLineDirector());
			map.put("provinceLineChargeState", "rollback");
			person = pnrTransferOffice.getCityLineDirector();
		}
		formService.submitTaskFormData(taskId, map);
		
		//2代表抓回
		pnrTransferOffice.setRollbackFlag("2");
		//在工单主表中添加流程信息
		processTaskService.setTvalueOfTask(processInstanceId,person,pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTransferOffice);
		
		return mapping.findForward("catchBackSuccess");
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
		
		List<TawCommonsAccessoriesForm> accessoriesList  = pnrTransferOfficeService.newShowSheetAccessoriesList(processId);
		
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
	 * 打开选择图片窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openSelectPhotoView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("photoDescribe","");
		request.setAttribute("createPhotoTime","");
		return mapping.findForward("openSelectPhotoView");
	}
	/**
	   *本地网、干线工单照片查看
	   * @param mapping
	   * @param form
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	  public ActionForward precheckShowPicture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
		  String id = request.getParameter("id");
		    String pageIndexString = request.getParameter("curPage");
		    getClass().getClassLoader().getResource("images/login.png");
//		    System.out.println("--测试----" + pageIndexString);
		    int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

		    PnrAndroidWorkOderPhotoFile taskFile = null;
		    IPnrAndroidWorkOderPhotoFileService pnrService = (IPnrAndroidWorkOderPhotoFileService)getBean("pnrAndroidWorkOderPhotoFileService");
		    Search search = new Search();
		    search.addFilterEqual("id", id);
		    List list = pnrService.search(search);
		    if ((list != null) && (list.size() > 0))
		      taskFile = (PnrAndroidWorkOderPhotoFile)list.get(0);

		    //String path = taskFile.getPath();
		    String path="wyphotoTest/qq.jpg";
		    String patch = path;  
		    
		    //获取盘符
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
		    	patch = strUrl+patch;
		    }else if("/".equals(flag)){//Linux环境下的分隔符是/
		    	patch = "/"+strUrl+patch;
		    }
		    System.out.println(patch);
		    //肉哥思路
		    FileInputStream is = new FileInputStream(patch);  
			int i = is.available(); // 得到文件大小  
			byte data[] = new byte[i];  
			is.read(data); // 读数据  
			is.close();  
			response.setContentType("image/*"); // 设置返回的文件类型  
			OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
			toClient.write(data); // 输出数据  
			toClient.close(); 
		   
		  return null;
	  }
	  /**
	   * 处理添加页面选择的图片
	    * @author wangyue
	    * @title: selectPhotoTodo
	    * @date Mar 16, 2015 2:54:00 PM
	    * @param mapping
	    * @param form
	    * @param request0
	    * @param response
	    * @return
	    * @throws Exception ActionForward
	   */
	  public ActionForward selectPhotoTodo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
		  IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		  String photoes = request.getParameter("photoes");
		  String[] ids = photoes.split(",");
		  String names="";
		  List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>(); 
		  JSONArray json = new JSONArray();
		  //获取每一张图片的信息
		  for(String str:ids){
			  if(!"".equals(str)){
				  IPnrAndroidMgr pnrAndroidMgr = (IPnrAndroidMgr)getBean("ipnrAndroidMgrImpl");
				    Search search = new Search();
				    search.addFilterEqual("id", str);
				    photoList = pnrAndroidMgr.search(search);
				    if(photoList!=null && photoList.size()>0){
				    	
				    	JSONObject jo = new JSONObject();
				    	jo.put("id", photoList.get(0).getId());
				    	jo.put("longitude",photoList.get(0).getLongitude()==null?'无':photoList.get(0).getLongitude());
				    	jo.put("latitude", photoList.get(0).getLatitude()==null?'无':photoList.get(0).getLatitude());
				    	jo.put("createtime", photoList.get(0).getCreateTime()==null?'无':photoList.get(0).getCreateTime());
				    	jo.put("userId", photoList.get(0).getUserId()==null?'无':CommonUtils.getId2NameString(photoList.get(0).getUserId(), 4, ","));
				    	json.put(jo);
				    }
			  }
		  }
		  response.getWriter().write(json.toString());			
		  return null;
	  }

		/***
		 * 条件查询本地网和干线图片
		  * @author wangyue
		  * @title: conditionSelectPhoto
		  * @date Mar 19, 2015 2:36:04 PM
		  * @param mapping
		  * @param form
		  * @param request
		  * @param response
		  * @return
		  * @throws Exception ActionForward
		 */
//		public ActionForward conditionSelectPhoto(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response)throws Exception{
//			//获取登录人userID
//			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//			.getSession().getAttribute("sessionform");
//			String userId = sessionform.getUserid();
//			String deptId = sessionform.getDeptid();
//			request.setAttribute("deptId", deptId);
//			String country = deptId;
//			if (country.length() > 5) {
//				country = country.substring(0, 5);
//			}
//			if ("admin".equals(userId) || "superUser".equals(userId)) {// 超级管理员
//				country = "";
//			}
//				request.setAttribute("country", country);
//			// 图片描述
//			String photoDescribe = StaticMethod.nullObject2String(request
//					.getParameter("photoDescribe"));
//			// 拍照时间
//			String createPhotoTime = StaticMethod.nullObject2String(request
//					.getParameter("createPhotoTime"));
//			// 拍照时间
//			String photoCreate = StaticMethod.nullObject2String(request
//					.getParameter("photoCreate"));
//			//根据登录人所述地市查询一定时间内的服务器上的图片
//			IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
//			List<PnrAndroidPhotoFile> photoList = pnrTransferNewPrecheckService.getPrecheckPhotoes(userId,photoDescribe,createPhotoTime,photoCreate);
//			request.setAttribute("list", photoList);
//			request.setAttribute("photoDescribe",photoDescribe);
//			request.setAttribute("createPhotoTime",createPhotoTime);
//			request.setAttribute("photoCreate",photoCreate);
//			
//			return mapping.findForward("openSelectPhotoView");
//		}
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
				return mapping.findForward("showPhotoView");
		  }
		  
		  /**
		   * 本地网和干线工单照片查看--查看的图片以二进制流存在数据库中
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
//				    getClass().getClassLoader().getResource("images/login.png");
//				    System.out.println("--测试----" + pageIndexString);
				    int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

				    PnrAndroidPhotoFile taskFile = null;
//				    IPnrAndroidMgrImpl pnrAndroidMgrImpl = (IPnrAndroidMgrImpl)getBean("ipnrAndroidMgrImpl");
				    IPnrAndroidMgr pnrAndroidMgrImpl = (IPnrAndroidMgr)getBean("ipnrAndroidMgrImpl");

				    Search search = new Search();
				    search.addFilterEqual("id", id);
				    List list = pnrAndroidMgrImpl.search(search);
				    if ((list != null) && (list.size() > 0))
				      taskFile = (PnrAndroidPhotoFile)list.get(0);

				    String path = taskFile.getPath();
//				    String path="wyphotoTest/qq.jpg";
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
				    	patch = strUrl+patch;
				    }else if("/".equals(flag)){//Linux环境下的分隔符是/
				    	patch = "/"+strUrl+patch;
				    }
				    System.out.println(patch);
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
			            String msg = "工单照片选择-下载远程文件出错：" + e.getLocalizedMessage();
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
		//String status = request.getParameter("status");
		String status = "archived";
		String themeInterface="arteryPrecheck";
		
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		
		//已归档工单条数
		int total = pnrTransferNewPrecheckService.getArchivedCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status,themeInterface);
		
		//已归档工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService.getArchivedList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status,themeInterface,firstResult,
				endResult, pageSize);
		
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
			pnrTransferOffice.setWorkerSceneOrderAuditFlag("2");
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
	 * 预检预修工单--待回复查询（待办工单除外）
	  * @author wangyue
	  * @title: listBacklog
	  * @date Feb 6, 2015 3:28:21 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward trunkLineWorkOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");//工单状态
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");//线路属性
		String provName = request.getParameter("provName");//工单类型
		String precheckFlag = request.getParameter("precheckFlag");//工单类别
		String keyWord = request.getParameter("keyWord");//关键字
		String workOrderDegree = request.getParameter("workOrderDegree");//紧急程度
		String subTypeName = StaticMethod.nullObject2String(request.getParameter("subTypeName"));//工单子类型
		//String mainSceneId = request.getParameter("mainSceneId");//场景
		
		 ConditionQueryModel conditionQueryModel =new  ConditionQueryModel();
		 
		 
		 conditionQueryModel.setWorkOrderDegree(workOrderDegree);
		 conditionQueryModel.setKeyWord(keyWord);
		 conditionQueryModel.setSendStartTime(sendStartTime);
		 conditionQueryModel.setSendEndTime(sendEndTime);
		 conditionQueryModel.setWorkNumber(wsNum);
		 conditionQueryModel.setTheme(wsTitle);
		 conditionQueryModel.setStatus(status);
		 conditionQueryModel.setCity(region);
		 conditionQueryModel.setCountry(country);
		 conditionQueryModel.setLineType(lineType);
		 conditionQueryModel.setWorkOrderType(provName);
		 conditionQueryModel.setPrecheckFlag(precheckFlag);
		 conditionQueryModel.setSubTypeName(subTypeName);
		 //conditionQueryModel.setMainSceneId(mainSceneId);
		 conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
    	IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		// 工单管理-传输局工单-预检预修工单-工单查询集合数
		int total = 0;
		// 工单管理-传输局工单-预检预修工单-工单查询集合
		List<TaskModel> rPnrTransferList = null;
		String queryAllowed = StaticMethod.nullObject2String(request.getParameter("queryAllowed"));//查询标识
		if("Y".equals(queryAllowed)){
			try {
				total = pnrTransferNewPrecheckService.getLocalNetworkWorkOrderListCount(areaid,userId,
						"backlog", processDefinitionKey, conditionQueryModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				rPnrTransferList = pnrTransferNewPrecheckService
						.getLocalNetworkWorkOrderList(areaid,userId, "backlog", processDefinitionKey,
								conditionQueryModel, firstResult, endResult,
								pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			request.setAttribute("tishiFlag","Y");//没选查询条件时，给出提示
		}
		
		String batchApprovalFlag="no";
		if(status!=null){
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
		request.setAttribute("workOrderDegree", workOrderDegree);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("wsStatus", status);
		request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);		
		request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("subTypeName", subTypeName);
		request.setAttribute("loginUserId", userId);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("trunkLineWorkOrderList");
	}
	
	//查看审批信息 界面优化改造
//	public ActionForward viewApprovalInfor(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		//工单号
//		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
//		System.out.println("------processInstanceId="+processInstanceId);
//		//环节名称
//		String taskKey = StaticMethod.nullObject2String(request.getParameter("taskKey"));
//		System.out.println("------taskKey="+taskKey);
//		
//		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
//		//显示审批信息的环节
//		if("workOrderCheck".equals(taskKey)||"cityLineExamine".equals(taskKey)||"cityLineDirectorAudit".equals(taskKey)||"provinceLineExamine".equals(taskKey)||"provinceLineViceAudit".equals(taskKey)||"provinceManageExamine".equals(taskKey)||"provinceManageViceAudit".equals(taskKey)){
//			//获取审批信息
//			Map<String, Object> approveMap = new HashMap<String, Object>();
//			List<PnrTransferOfficeHandle> approveHandlelist = null;
//			int approveHandleSize =0;
//			Search approveSearch = new Search();
//			approveSearch.addFilterEqual("processInstanceId", processInstanceId);
//			approveSearch.addFilterEqual("operation", "01");
//			approveSearch.addSort("checkTime", true);// 按回复时间排序
//			approveMap=getHandleList(approveMap,transferHandleService, approveSearch, approveHandlelist,approveHandleSize);
//			request.setAttribute("approveListsize", approveMap.get("size"));
//			request.setAttribute("approveHandleList", approveMap.get("list"));
//		}
//		
//		//显示驳回信息的环节
//		if("workOrderCheck".equals(taskKey)||"cityLineExamine".equals(taskKey)||"cityLineDirectorAudit".equals(taskKey)||"provinceLineExamine".equals(taskKey)||"provinceLineViceAudit".equals(taskKey)||"provinceManageExamine".equals(taskKey)||"provinceManageViceAudit".equals(taskKey)){
//			//驳回信息
//			Map<String, Object> approveBackMap = new HashMap<String, Object>();
//			List<PnrTransferOfficeHandle> approveBackHandlelist = null;
//			int approveBackHandleSize =0;	
//			Search approveBackSearch = new Search();
//			approveBackSearch.addFilterEqual("processInstanceId", processInstanceId);
//			approveBackSearch.addFilterEqual("operation", "02");
//			approveBackSearch.addSort("checkTime", true);// 按回复时间排序
//			approveBackMap=getHandleList(approveBackMap,transferHandleService, approveBackSearch, approveBackHandlelist,approveBackHandleSize);
//			request.setAttribute("approveBackListsize", approveBackMap.get("size"));
//			request.setAttribute("approveBackList", approveBackMap.get("list"));
//		}
//		
//		//显示施工队处理信息的环节
//		if("daiweiAudit".equals(taskKey)){
//			Map<String, Object> transferMap = new HashMap<String, Object>();
//			List<PnrTransferOfficeHandle> transferlist = null;
//			int transferHandleSize =0;	
//			Search transferSearch = new Search();
//			transferSearch.addFilterEqual("processInstanceId", processInstanceId);
//			transferSearch.addFilterEqual("operation", "04");
//			transferSearch.addSort("checkTime", true);// 按回复时间排序
//			transferMap=getHandleList(transferMap,transferHandleService, transferSearch, transferlist,transferHandleSize);
//			request.setAttribute("transferListsize", transferMap.get("size"));
//			request.setAttribute("transferList", transferMap.get("list"));
//		}
//		
//		if("worker".equals(taskKey)){
//			//获取审核信息
//			Map<String, Object> auditMap = new HashMap<String, Object>();
//			List<PnrTransferOfficeHandle> auditlist = null;
//			int auditSize =0;	
//			Search auditSearch = new Search();
//			auditSearch.addFilterEqual("processInstanceId", processInstanceId);
//			auditSearch.addFilterEqual("operation", "05");
//			auditSearch.addFilterEqual("state", "rollback");
//			auditSearch.addSort("checkTime", true);// 按回复时间排序
//			auditMap=getHandleList(auditMap,transferHandleService, auditSearch, auditlist,auditSize);
//			request.setAttribute("auditListsize", auditMap.get("size"));
//			request.setAttribute("auditList", auditMap.get("list"));
//		}
//		
//		return mapping.findForward("viewApprovalInfor");
//	}
//	
//	//新的查看历史
//	public void viewHistoryNew(HttpServletRequest request,String processInstanceId)throws Exception {
//		//int pageSize = CommonUtils.PAGE_SIZE;
//		int pageSize = 10;
//		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
//				request, "historicTasks");
//		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
//				.valueOf(pageIndexString).intValue() - 1;
//		int totalSize = 0;
//
//		HistoryService historyService = (HistoryService) getBean("historyService");
//		//String processInstanceId = request.getParameter("processInstanceId");
//		List<HistoricTaskInstance> historicTasks = historyService
//				.createHistoricTaskInstanceQuery().processInstanceId(
//						processInstanceId)
//				.orderByHistoricTaskInstanceStartTime().asc().listPage(
//						firstResult * pageSize, pageSize);
//		totalSize = (int) historyService.createHistoricTaskInstanceQuery()
//				.processInstanceId(processInstanceId).count();
//		List<HistoricVariableInstance> historicVariableInstances = historyService
//				.createHistoricVariableInstanceQuery().processInstanceId(
//						processInstanceId).list();
//		// historyService
//
//		request.setAttribute("historicTasks", historicTasks);
//		request.setAttribute("historicTasksPageSize", pageSize);
//		request.setAttribute("historicTasksTotal", totalSize);
//		request.setAttribute("historicVariableInstances",
//				historicVariableInstances);
//		request.setAttribute("historicVariableInstancesPageSize", pageSize);
//		request.setAttribute("historicVariableInstancesTotal",
//				historicVariableInstances.size());
//	}
	
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
}
