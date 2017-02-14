package com.boco.eoms.sheet.base.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.RequestUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.prm.service.impl.Pojo2PojoServiceImpl;
import com.boco.eoms.sheet.base.exception.SheetException;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.Performer;
import com.boco.eoms.sheet.base.model.TawSystemWorkflow;
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ISheetPerformShowService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.EomsInterpreter;
import com.boco.eoms.sheet.base.util.SheetUtils;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefineExplain;
import com.boco.eoms.sheet.base.util.flowdefine.xml.PhaseId;
import com.boco.eoms.sheet.base.util.flowdefine.xml.ToPhaseId;
import com.boco.eoms.sheet.base.webapp.action.IBaseSheet;
import com.boco.eoms.sheet.limit.model.LevelLimit;
import com.boco.eoms.sheet.limit.model.StepLimit;
import com.boco.eoms.sheet.limit.service.ISheetDealLimitManager;
import com.boco.eoms.sheet.limit.util.SheetLimitUtil;
import com.boco.eoms.sheet.tool.access.model.TawSheetAccess;
import com.boco.eoms.sheet.tool.access.service.ITawSheetAccessManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 15, 2012 11:29:21 AM 
 */
public class SheetPerformShowServiceImpl implements ISheetPerformShowService {
	private ISheetFacadeService sheetFacadeService;
	public ISheetFacadeService getSheetFacadeService() {
		return sheetFacadeService;
	}
	public void setSheetFacadeService(ISheetFacadeService sheetFacadeService) {
		this.sheetFacadeService = sheetFacadeService;
	}
	private IMainService getMainService(){
		return sheetFacadeService.getMainService();
	}
	public ILinkService getLinkService(){
		return sheetFacadeService.getLinkService();
	}
	public IBusinessFlowService getBusinessFlowService(){
		return sheetFacadeService.getBusinessFlowService();
	}
	public ITaskService getTaskService(){
		return sheetFacadeService.getTaskService();
	}
	public String getRoleConfigPath() {
		return sheetFacadeService.getRoleConfigPath();
	}

	public void showInputNewSheetPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject()
				.getClass().newInstance();
		String sendUserName = "";
		String sendDeptName = "";
		String sendRoleName = "";
		if (sessionform != null) {
			mainObject.setSendUserId(sessionform.getUserid());
			mainObject.setSendDeptId(sessionform.getDeptid());
			mainObject.setSendRoleId(StaticMethod.nullObject2String(sessionform
					.getRoleid()));
			mainObject.setSendOrgType(UIConstants.NODETYPE_SUBROLE);
			mainObject.setSendContact(sessionform.getContactMobile());
			sendUserName = sessionform.getUsername();
			sendDeptName = sessionform.getDeptname();
			sendRoleName = sessionform.getRolename();
		}
		String acceptLimit = StaticMethod.getLocalString(1);
		String completeLimit = StaticMethod.getLocalString(3);
		mainObject.setSheetAcceptLimit(SheetUtils.stringToDate(acceptLimit));
		mainObject.setSheetCompleteLimit(SheetUtils.stringToDate(completeLimit));
		mainObject.setSendTime(StaticMethod.getLocalTime());
		
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}
		String parentCorrelation = StaticMethod.nullObject2String(request
				.getParameter("parentCorrelation"), "");
		String parentSheetId = StaticMethod.nullObject2String(request
				.getParameter("parentSheetId"), "");
		String parentSheetName = StaticMethod.nullObject2String(request
				.getParameter("parentSheetName"), "");
		String parentPhaseName = StaticMethod.nullObject2String(request
				.getParameter("parentPhaseName"), "");
		String invokeMode = StaticMethod.nullObject2String(request
				.getParameter("invokeMode"), "");
		
		mainObject.setParentCorrelation(parentCorrelation);
		mainObject.setParentSheetId(parentSheetId);
		mainObject.setParentSheetName(parentSheetName);
		mainObject.setParentPhaseName(parentPhaseName);
		mainObject.setInvokeMode(invokeMode);
		
		String invokerId = StaticMethod.nullObject2String(request
				.getParameter("invokerId"), "");
		
		if (!invokerId.equals("")) {
			String invokerObject = StaticMethod.nullObject2String(request
					.getParameter("invokerObject"), "");
			IBaseSheet basesheet = (IBaseSheet) ApplicationContextHolder
					.getInstance().getBean(invokerObject);
			BaseMain baseMain = basesheet.getMainService().getSingleMainPO(
					invokerId);
			if (baseMain != null) {
				Pojo2PojoServiceImpl p2pService = (Pojo2PojoServiceImpl) ApplicationContextHolder
						.getInstance().getBean("p2pService");
				p2pService.p2p(baseMain, mainObject);
			}
		}
		if (!parentSheetId.equals("") && !parentSheetName.equals("")) {
			IMainService parentMainSerivce = (IMainService) ApplicationContextHolder
					.getInstance().getBean(parentSheetName);
			BaseMain parentMain = parentMainSerivce.getSingleMainPO(parentSheetId);
			String tmpparentSheetId = parentMain.getSheetId();
		
			ITawSystemWorkflowManager mgr = (ITawSystemWorkflowManager) ApplicationContextHolder
					.getInstance().getBean("ITawSystemWorkflowManager");
			TawSystemWorkflow workflow = mgr
					.getTawSystemWorkflowByBeanId(parentSheetName);
			String parentProcessName = workflow.getName();
		
			request.setAttribute("parentSheetId", tmpparentSheetId);
			request.setAttribute("parentProcessName",parentProcessName);
			
			System.out.println("parentSheetId=" + parentSheetId
					+ "parentProcessName=" + parentProcessName);
		}
		request.setAttribute("sendUserName", sendUserName);
		request.setAttribute("sendDeptName", sendDeptName);
		request.setAttribute("sendRoleName", sendRoleName);
		request.setAttribute("sheetMain", mainObject);
		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("status", Constants.SHEET_RUN);
		request.setAttribute("sendOrgType", UIConstants.NODETYPE_SUBROLE);
		request.setAttribute("methodBeanId", mapping.getAttribute());
	}


	@SuppressWarnings("unchecked")
	public String showNewSheetPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String actionForward="new";
		//判断当前用户是否具有建单者权限
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
        ITawSystemSubRoleManager roleManager = (ITawSystemSubRoleManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemSubRoleManager");
        String processName=this.getMainService().getFlowTemplateName();
        ITawSystemWorkflowManager wfManager = (ITawSystemWorkflowManager) ApplicationContextHolder
		    .getInstance().getBean("ITawSystemWorkflowManager");
        TawSystemWorkflow wf=wfManager.getTawSystemWorkflowByName(processName);
        String roleId=StaticMethod.nullObject2String(wf.getRoleId());
        if(!roleId.equals("")){
         List list=roleManager.getSubRoles(sessionform.getUserid(),roleId);
         if(list == null || list.size() == 0) {
        	actionForward="nopriv";
        	return actionForward;
         }
        }
		String parentCorrelation = StaticMethod.nullObject2String(request
				.getParameter("parentCorrelation"), "");
		String parentSheetId = StaticMethod.nullObject2String(request
				.getParameter("parentSheetId"), "");
		String parentSheetName = StaticMethod.nullObject2String(request
				.getParameter("parentSheetName"), "");
		String parentPhaseName = StaticMethod.nullObject2String(request
				.getParameter("parentPhaseName"), "");
		String invokeMode = StaticMethod.nullObject2String(request
				.getParameter("invokeMode"), "");

		request.setAttribute("parentCorrelation", parentCorrelation);
		request.setAttribute("parentSheetId", parentSheetId);
		request.setAttribute("parentSheetName", parentSheetName);
		request.setAttribute("parentPhaseName", parentPhaseName);
		request.setAttribute("invokeMode", invokeMode);
		return actionForward;
	}
	public void showBatchDealPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
	}
	public void showCancelInputPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
	}
	public void showCancelPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
	}
	
	public void showDealPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request.getParameter("sheetKey"));
		String aiid = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request.getParameter("taskName"));
		String operateRoleId = StaticMethod.nullObject2String(request.getParameter("operateRoleId"));
		String preLinkId = StaticMethod.nullObject2String(request.getParameter("preLinkId"), "");
		String TKID = StaticMethod.nullObject2String(request.getParameter("TKID"));
		String operateType = StaticMethod.nullObject2String(request.getParameter("operateType"));
		String dealTemplateId = StaticMethod.nullObject2String(request.getParameter("dealTemplateId"));
		String backFlag = StaticMethod.nullObject2String(request.getParameter("backFlag"));
		/**同组处理标志**/
		String teamFlag = StaticMethod.nullObject2String(request.getParameter("teamFlag"));

		if (!backFlag.equals("")) {
			request.setAttribute("backFlag", backFlag);
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		String taskStatus = StaticMethod.nullObject2String(request
				.getParameter("taskStatus"));

		request.setAttribute("taskStatus", taskStatus);
		request.setAttribute("sheetKey", sheetKey);
		request.setAttribute("taskId", aiid);
		request.setAttribute("piid", piid);
		request.setAttribute("taskName", taskName);
		request.setAttribute("operateRoleId", operateRoleId);
		request.setAttribute("TKID", TKID);
		request.setAttribute("preLinkId", preLinkId);
		request.setAttribute("operateType", operateType);
		request.setAttribute("dealTemplateId", dealTemplateId);
		request.setAttribute("module",mapping.getPath().substring(1));
		request.setAttribute("teamFlag", teamFlag);
		//是否是批量回复的转向
		String batch = StaticMethod.nullObject2String(request.getParameter("batch"));
		if (!batch.equals("")) {
			request.setAttribute("batch", batch);
			String taskIds = StaticMethod.nullObject2String(request.getParameter("taskIds"));
			request.setAttribute("taskIds", taskIds);
		}
		
	}
	public void showDealReplyAcceptPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void showDealReplyRejectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void showDeletePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 显示草稿
	 */
	public void showDraftPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request.getParameter("sheetKey"));
		String operateType = StaticMethod.nullObject2String(request.getParameter("operateType"));
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"), "");
		String taskName = StaticMethod.nullObject2String(request.getParameter("taskName"), "");
		String piid = StaticMethod.nullObject2String(request.getParameter("piid"), "");
		String TKID = StaticMethod.nullObject2String(request.getParameter("TKID"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("dealTemplateId"));
		String backFlag = StaticMethod.nullObject2String(request.getParameter("backFlag"));
		BocoLog.debug(this, "sheetKey==" + sheetKey);
		if (!taskName.equals("")){
			setParentTaskOperateWhenRejct(request);
		}
		BaseMain mainObject = null;
		if (!sheetKey.equals("")) {
			mainObject = new BaseMain();
			if (taskName.equalsIgnoreCase("DraftHumTask")
					&& !templateId.equals("")) {
				mainObject = getMainService().getSingleMainPO(templateId);
			} else {
				mainObject = getMainService().getSingleMainPO(sheetKey);
			}
			request.setAttribute("sheetMain", mainObject);
			request.setAttribute("status", Constants.SHEET_RUN);
			request.setAttribute("sheetKey", sheetKey);
			request.setAttribute("taskId", taskId);
			request.setAttribute("taskName", taskName);
			request.setAttribute("piid", piid);
			request.setAttribute("TKID", TKID);
			request.setAttribute("operateType", operateType);
		} else {
			throw new SheetException(this.getClass().getName()
					+ "工单主键为空,请联系管理员!");
		}
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		if (!preLinkId.equals("")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}

		JSONArray sendUserAndRoles = new JSONArray();
		if (backFlag.equals("")) {
			
			if(mainObject!=null && !StringUtils.isEmpty(mainObject.getSendObjectTotalJSON())){
				Type listType = new TypeToken<List<Performer>>(){}.getType();  
				List<Performer> list = new Gson().fromJson(mainObject.getSendObjectTotalJSON(), listType);
				for (Performer performer : list) {
					JSONObject data = new JSONObject();
					data.put("id", performer.getId());
					data.put("nodeType", performer.getNodeType());
					data.put("categoryId", performer.getCategoryId());
					sendUserAndRoles.put(data.toString());
				}
			}
		}
		request.setAttribute("sendUserAndRoles", sendUserAndRoles);
		request.setAttribute("mainId", sheetKey);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskName", taskName);
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		
	}
	
	/**
	 * ADD PANLONG 当驳回的时候查询上一条任务的执行者对象
	 * 从request中通过request.getAttribute(fOperateroleid/ftaskOwner/fOperateroleidType)
	 * 取得上一条任务的Operateroleid taskOwner OperateroleidType
	 */
	public void setParentTaskOperateWhenRejct(HttpServletRequest request)
			throws Exception {
		String prelinkid = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"));
		BaseLink preLink = (BaseLink) this.getLinkService()
				.getSingleLinkPO(prelinkid);
		String fOperateroleid = "";
		String ftaskOwner = "";
		String fOperateroleidType = "";
		String fPreTaskName = "";
		if (preLink != null) {
			// 不是流程第一个操作步骤
			String parentTaskId = StaticMethod.nullObject2String(preLink
					.getAiid());
			if (!parentTaskId.equals("")) {
				ITask task = this.getTaskService().getSinglePO(parentTaskId);
				fOperateroleid = task.getOperateRoleId();
				ftaskOwner = task.getTaskOwner();
				fOperateroleidType = task.getOperateType();
				fPreTaskName = task.getTaskName();
			} else {
				String sheetKey = preLink.getMainId();
				BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
				fOperateroleid = main.getSendRoleId();
				ftaskOwner = main.getSendUserId();
				fOperateroleidType = UIConstants.NODETYPE_SUBROLE;
			}
		}
		request.setAttribute("fOperateroleid", fOperateroleid);
		request.setAttribute("ftaskOwner", ftaskOwner);
		request.setAttribute("fOperateroleidType", fOperateroleidType);
		request.setAttribute("fPreTaskName", fPreTaskName);
	}
	
	
	public void showEventPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 工单强制归档、作废页面
	 */
	@SuppressWarnings("unchecked")
	public void showForceHoldPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		String tempType = StaticMethod.nullObject2String(request
				.getParameter("operateType"), "");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		BaseLink linkObject = (BaseLink) getLinkService().getLinkObject()
				.getClass().newInstance();

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
			linkObject.setOperaterContact(sessionform.getContactMobile());
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		if (!preLinkId.equals("") && !preLinkId.equals("null")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}
		linkObject.setOperateRoleId(operateRoleId);
		linkObject.setOperateTime(StaticMethod.getLocalTime());
		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);

		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);
		request.setAttribute("operateType", tempType);
		String correlationKey = StaticMethod.nullObject2String(request
				.getParameter("correlationKey"), "");

		request.setAttribute("correlationKey", correlationKey);
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}
		
		
		//在整合中没有必要整合到新的版本中所以就在这里修改添加了几个参数
		String processTemplateName = this.getMainService().getFlowTemplateName();
		//beanId
		ITawSystemWorkflowManager flowmanage = (ITawSystemWorkflowManager) ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
		String beanid = flowmanage.getTawSystemWorkflowByName(processTemplateName).getMainServiceBeanId();
		request.setAttribute("beanId", beanid);
		// mainClassName
		Class mainclazz = this.getMainService().getMainObject().getClass();
		request.setAttribute("mainClassName", mainclazz.getName());
		// linkClassName
		Class linkclazz = this.getLinkService().getLinkObject().getClass();
		request.setAttribute("linkClassName", linkclazz.getName());
		request.setAttribute("processTemplateName", processTemplateName);
	}
	public void showHoldPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * 呈现工单处理子界面
     */
	@SuppressWarnings("unchecked")
	public void showInputDealPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String taskStatus = StaticMethod.nullObject2String(request
				.getParameter("taskStatus"));
		String processName = this.getMainService().getFlowTemplateName();
		String teamFlag = StaticMethod.nullObject2String(request
				.getParameter("teamFlag"));

		// 获取当前task对象
		BaseLink linkObject = null;
		ITask taskModel = this.getTaskService().getSinglePO(TKID);
		String linkId = "";
		if (taskModel != null) {
			linkId = StaticMethod.nullObject2String(taskModel.getCurrentLinkId());
		}
		if (!linkId.equals(""))
			linkObject = this.getLinkService().getSingleLinkPO(linkId);
		if (linkObject == null) {
			linkObject = (BaseLink) getLinkService().getLinkObject().getClass()
					.newInstance();
			linkObject.setOperaterContact(sessionform.getContactMobile());
			String acceptLimit = StaticMethod.getLocalString(1);
			String completeLimit = StaticMethod.getLocalString(3);
			linkObject.setNodeAcceptLimit(SheetUtils.stringToDate(acceptLimit));
			linkObject.setNodeCompleteLimit(SheetUtils
					.stringToDate(completeLimit));
		}

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
			linkObject.setOperaterContact(StaticMethod.nullObject2String(
					linkObject.getOperaterContact(), sessionform
							.getContactMobile()));
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		if (!preLinkId.equals("") && !preLinkId.equals("null")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}
		linkObject.setOperateRoleId(operateRoleId);
		linkObject.setOperateTime(StaticMethod.getLocalTime());

		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);
		// 取mainId
		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("task", taskModel);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);
		request.setAttribute("taskStatus", taskStatus);

		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}

		ITawSheetAccessManager mgr = (ITawSheetAccessManager) ApplicationContextHolder
				.getInstance().getBean("ItawSheetAccessManager");
		TawSheetAccess access = mgr.getAccessByPronameAndTaskname(processName,
				taskName);

		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		request.setAttribute("tawSheetAccess", access);
		request.setAttribute("module", mapping.getPath().substring(1));
		request.setAttribute("teamFlag", teamFlag);
		//批量回复或批量归档
		String taskIds = StaticMethod.nullObject2String(request.getParameter("taskIds"));
		if (!taskIds.equals("")) {
			request.setAttribute("taskIds", taskIds);
			Map tempId = new HashMap();
			StringBuffer mainIds = new StringBuffer();
			String taskCondition = " id in  ('" + taskIds.replaceAll(",", "','") + "')";
			//查出所有的任务列表
			List tasks = this.getTaskService().getTasksByCondition(taskCondition);
			for (Iterator it = tasks.iterator(); it.hasNext(); ) {
				ITask task = (ITask)it.next();
				String thismainId = task.getSheetKey();
				if (tempId.get(thismainId) == null) {
					tempId.put(thismainId, thismainId);
					if (!mainIds.toString().equals("")) {
						mainIds.append(",");
					}
					mainIds.append("'").append(thismainId).append("'");
				}
			}
			
			//查出所有的main表
			String condition = " id in (" + mainIds.toString() + ")";
			List mains = this.getMainService().getMainsByCondition(condition);
			request.setAttribute("mains", mains);
		}
		String operateType = StaticMethod.nullObject2String(request.getParameter("operateType"));
		Object tempMain = request.getAttribute("sheetMain");
		if(tempMain!=null){
			linkObject = this.setStepLimitToLink((BaseMain)tempMain,linkObject, taskName, operateType);
			request.setAttribute("sheetLink", linkObject);
		}
	}
	public void showInputEventPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void showInputRejectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 呈现分派页面
	 */
	@SuppressWarnings("unchecked")
	public void showInputSplitPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String taskStatus = StaticMethod.nullObject2String(request
				.getParameter("taskStatus"));
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));

		// 获取当前task对象
		BaseLink linkObject = null;
		ITask taskModel = this.getTaskService().getSinglePO(TKID);
		String linkId = StaticMethod.nullObject2String(taskModel
				.getCurrentLinkId());
		if (!linkId.equals(""))
			linkObject = this.getLinkService().getSingleLinkPO(linkId);
		if (linkObject == null) {
			linkObject = (BaseLink) getLinkService().getLinkObject().getClass()
					.newInstance();
		}

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
			linkObject.setOperaterContact(sessionform.getContactMobile());
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		if (!preLinkId.equals("") && !preLinkId.equals("null")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}
		// linkObject.setOperateType(new Integer(1));// 转派标识
		linkObject.setOperateRoleId(operateRoleId);
		String acceptLimit = StaticMethod.getLocalString(1);
		String completeLimit = StaticMethod.getLocalString(3);
		linkObject.setNodeAcceptLimit(SheetUtils.stringToDate(acceptLimit));
		linkObject.setNodeCompleteLimit(SheetUtils.stringToDate(completeLimit));
		linkObject.setOperateTime(StaticMethod.getLocalTime());

		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);
		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);
		request.setAttribute("taskStatus", taskStatus);
		request.setAttribute("operateType", operateType);

		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}
		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		String name = service.id2Name(operateRoleId, "tawSystemSubRoleDao");
		if (!name.equals("")) {
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			TawSystemSubRole subRole = mgr.getTawSystemSubRole(operateRoleId);
			String roleId = Long.toString(subRole.getRoleId());
			request.setAttribute("roleId", roleId);
		}
		
		//在整合中没有必要整合到新的版本中所以就在这里修改添加了几个参数
		String processTemplateName = this.getMainService().getFlowTemplateName();
		//beanId
		ITawSystemWorkflowManager flowmanage = (ITawSystemWorkflowManager) ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
		String beanid = flowmanage.getTawSystemWorkflowByName(processTemplateName).getMainServiceBeanId();
		request.setAttribute("beanId", beanid);
		// mainClassName
		Class mainclazz = this.getMainService().getMainObject().getClass();
		request.setAttribute("mainClassName", mainclazz.getName());
		// linkClassName
		Class linkclazz = this.getLinkService().getLinkObject().getClass();
		request.setAttribute("linkClassName", linkclazz.getName());
		request.setAttribute("processTemplateName", processTemplateName);
		String subtaskName = StaticMethod.nullObject2String(request.getParameter("subtaskName"));
		request.setAttribute("subtaskName", subtaskName);
		request.setAttribute("TKID", TKID);
		String workflow = this.getMainService().getFlowTemplateName();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(workflow,this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		request.setAttribute("taskNamespace", flowDefine.getTasknamespace());
	}
	public void showInvokeReplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void showNewSendPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 执行显示阶段通知页面
	 */
	@SuppressWarnings("unchecked")
	public void showPhaseAdvicePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		System.out.println("taskName:" + taskName);
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");

		List list = this.getTaskService().getCurrentUndoTask(sheetKey);
		List toList = new ArrayList();
		for (int i = 0; list != null && i < list.size(); i++) {
			ITask task = (ITask) list.get(i);
			ITask tempTask = (ITask) this.getTaskService().getTaskModelObject()
					.getClass().newInstance();
			tempTask.setOperateRoleId(task.getOperateRoleId());
			if (task.getOperateType() == null
					|| task.getOperateType().equals("")
					|| task.getOperateType().equals("null")) {
				ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
				String name = service.id2Name(task.getOperateRoleId(),
						"tawSystemUserDao");
				if (name.equals("")) {
					tempTask.setOperateType("subrole");
				} else {
					tempTask.setOperateType("user");
				}
			} else {
				tempTask.setOperateType(task.getOperateType());
			}
			tempTask.setTaskOwner(task.getTaskOwner());
			tempTask.setTaskDisplayName(task.getTaskDisplayName());
			toList.add(tempTask);
		}
		request.setAttribute("toOperaterList", toList);
		System.out.println("toOperaterList>>>>>>>" + toList);

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		BaseLink linkObject = (BaseLink) getLinkService().getLinkObject()
				.getClass().newInstance();

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
			linkObject.setOperaterContact(sessionform.getContactMobile());
		}

		linkObject.setOperateType(Integer
				.valueOf(Constants.ACTION_DRIVERFORWARD));// 阶段通知标识
		linkObject.setOperateRoleId(operateRoleId);
		String acceptLimit = StaticMethod.getLocalString(1);
		String completeLimit = StaticMethod.getLocalString(3);
		linkObject.setNodeAcceptLimit(SheetUtils.stringToDate(acceptLimit));
		linkObject.setNodeCompleteLimit(SheetUtils.stringToDate(completeLimit));
		linkObject.setOperateTime(StaticMethod.getLocalTime());
		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);
		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);

		String correlationKey = StaticMethod.nullObject2String(request
				.getParameter("correlationKey"), "");

		request.setAttribute("correlationKey", correlationKey);
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		request.setAttribute("sheetKey", sheetKey);
		request.setAttribute("preLinkId", preLinkId);
		
		//在整合中没有必要整合到新的版本中所以就在这里修改添加了几个参数
		String processTemplateName = this.getMainService().getFlowTemplateName();
		//beanId
		ITawSystemWorkflowManager flowmanage = (ITawSystemWorkflowManager) ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
		String beanid = flowmanage.getTawSystemWorkflowByName(processTemplateName).getMainServiceBeanId();
		request.setAttribute("beanId", beanid);
		// mainClassName
		Class mainclazz = this.getMainService().getMainObject().getClass();
		request.setAttribute("mainClassName", mainclazz.getName());
		// linkClassName
		Class linkclazz = this.getLinkService().getLinkObject().getClass();
		request.setAttribute("linkClassName", linkclazz.getName());
		request.setAttribute("processTemplateName", processTemplateName);
		request.setAttribute("TKID", TKID);
		String taskNamespace = "http://" + mapping.getPath().substring(1)+ "Process";
		request.setAttribute("taskNamespace", taskNamespace);
	}
	
	/**
	 * 执行阶段回复输入页面
	 */
	@SuppressWarnings("unchecked")
	public void showPhaseBackToUpPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		System.out.println("taskName:" + taskName);
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");

		List toList = new ArrayList();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		if (!preLinkId.equals(""))// 当preLinkId不为空时，则表示需要从派发给我的link中取出operateRoleId
		{
			// 阶段回复
			String linkClassName = this.getLinkService().getLinkObject()
					.getClass().getName();
			List linkList = this.getTaskService().getExceptMeTask(sheetKey,
					linkClassName, userId);
			for (int i = 0; linkList != null && i < linkList.size(); i++) {
				ITask task = (ITask) this.getTaskService().getTaskModelObject()
						.getClass().newInstance();
				Object[] obj = (Object[]) linkList.get(i);
				task.setOperateRoleId(StaticMethod.nullObject2String(obj[0]));
				task.setOperateType(StaticMethod.nullObject2String(obj[1]));
				task.setTaskOwner(StaticMethod.nullObject2String(obj[2]));
				task.setTaskDisplayName(StaticMethod.nullObject2String(obj[3]));

				if (task.getTaskDisplayName() != null
						&& (task.getTaskDisplayName().indexOf("草稿") == -1 && task
								.getTaskDisplayName().indexOf("驳回") == -1)) {
					// ifAddReceive = false;
					toList.add(task);
				}
			}
		}
		ITask task = (ITask) this.getTaskService().getTaskModelObject()
				.getClass().newInstance();
		BaseMain mainObj = this.getMainService().getSingleMainPO(sheetKey);
		task.setOperateRoleId(mainObj.getSendRoleId());
		String name = service.id2Name(mainObj.getSendRoleId(),
				"tawSystemUserDao");
		if (name.equals("")) {
			task.setOperateType("subrole");
			task.setTaskOwner(mainObj.getSendRoleId());
		} else {
			task.setOperateType("user");
			task.setTaskOwner(mainObj.getSendUserId());
		}
		task.setTaskDisplayName("新增工单");
		toList.add(task);
		BaseLink linkObject = (BaseLink) getLinkService().getLinkObject()
				.getClass().newInstance();

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
		}
		request.setAttribute("toOperaterList", toList);

		if (!preLinkId.equals("") && !preLinkId.equals("null")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}
		linkObject.setOperateType(Integer
				.valueOf(Constants.ACTION_PHASE_BACKTOUP));
		linkObject.setOperateRoleId(operateRoleId);
		linkObject.setOperaterContact(sessionform.getContactMobile());
		String acceptLimit = StaticMethod.getLocalString(1);
		String completeLimit = StaticMethod.getLocalString(3);
		linkObject.setNodeAcceptLimit(SheetUtils.stringToDate(acceptLimit));
		linkObject.setNodeCompleteLimit(SheetUtils.stringToDate(completeLimit));
		linkObject.setOperateTime(StaticMethod.getLocalTime());
		// end
		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);
		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);

		String correlationKey = StaticMethod.nullObject2String(request
				.getParameter("correlationKey"), "");

		request.setAttribute("correlationKey", correlationKey);
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		request.setAttribute("sheetPageName", sheetPageName);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		request.setAttribute("sheetKey", sheetKey);
		request.setAttribute("preLinkId", preLinkId);
		
		//在整合中没有必要整合到新的版本中所以就在这里修改添加了几个参数
		String processTemplateName = this.getMainService().getFlowTemplateName();
		//beanId
		ITawSystemWorkflowManager flowmanage = (ITawSystemWorkflowManager) ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
		String beanid = flowmanage.getTawSystemWorkflowByName(processTemplateName).getMainServiceBeanId();
		request.setAttribute("beanId", beanid);
		// mainClassName
		Class mainclazz = this.getMainService().getMainObject().getClass();
		request.setAttribute("mainClassName", mainclazz.getName());
		// linkClassName
		Class linkclazz = this.getLinkService().getLinkObject().getClass();
		request.setAttribute("linkClassName", linkclazz.getName());
		request.setAttribute("processTemplateName", processTemplateName);
		
	}
	public void showRejectPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 工单移交输入页面
	 */
	@SuppressWarnings("unchecked")
	public void showTransferWorkItemPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String aiid = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"));
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"));
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		BaseLink linkObject = (BaseLink) getLinkService().getLinkObject()
				.getClass().newInstance();

		if (sessionform != null) {
			linkObject.setOperateUserId(sessionform.getUserid());
			linkObject.setOperateDeptId(sessionform.getDeptid());
			linkObject.setOperaterContact(sessionform.getContactMobile());
		}
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		if (!preLinkId.equals("") && !preLinkId.equals("null")) {
			BaseLink preLink = getLinkService().getSingleLinkPO(preLinkId);
			request.setAttribute("preLink", preLink);
		}
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(this
				.getMainService().getFlowTemplateName(), this
				.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		String bigRoleId = "0";
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				ToPhaseId[] toPhaseIds = phaseId.getToPhaseId();
				for (int j = 0; toPhaseIds != null && j < toPhaseIds.length; j++) {
					ToPhaseId toPhaseId = toPhaseIds[j];
					if (toPhaseId.getId().equals(taskName)) {
						String roleId = toPhaseId.getRole();
						if (roleId != null && !roleId.equals("")
								&& roleId.indexOf("@") != 0) {
							bigRoleId = roleId.substring(
									roleId.indexOf("@") + 1, roleId.length());
							break;
						}
					}
				}
			}
		}
		request.setAttribute("bigRoleId", bigRoleId);
		linkObject.setOperateRoleId(operateRoleId);
		String acceptLimit = StaticMethod.getLocalString(1);
		String completeLimit = StaticMethod.getLocalString(3);
		linkObject.setNodeAcceptLimit(SheetUtils.stringToDate(acceptLimit));
		linkObject.setNodeCompleteLimit(SheetUtils.stringToDate(completeLimit));
		linkObject.setOperateTime(StaticMethod.getLocalTime());
		linkObject.setPiid(piid);
		linkObject.setAiid(aiid);
		linkObject.setTkid(TKID);

		String mainId = RequestUtils.getStringParameter(request, "mainId");
		linkObject.setMainId(mainId);
		request.setAttribute("sheetLink", linkObject);
		request.setAttribute("taskName", taskName);
		request.setAttribute("operateType", operateType);
		String correlationKey = StaticMethod.nullObject2String(request
				.getParameter("correlationKey"), "");

		request.setAttribute("correlationKey", correlationKey);
		String sheetPageName = StaticMethod.nullObject2String(request
				.getParameter("sheetPageName"), "");
		if (!sheetPageName.equals("") && sheetPageName.indexOf(".") == -1) {
			sheetPageName = sheetPageName + ".";
		}

		// 在整合中没有必要整合到新的版本中所以就在这里修改添加了几个参数
		String processTemplateName = this.getMainService()
				.getFlowTemplateName();
		// beanId
		ITawSystemWorkflowManager flowmanage = (ITawSystemWorkflowManager) ApplicationContextHolder
				.getInstance().getBean("ITawSystemWorkflowManager");
		String beanid = flowmanage.getTawSystemWorkflowByName(
				processTemplateName).getMainServiceBeanId();
		request.setAttribute("beanId", beanid);
		// mainClassName
		Class mainclazz = this.getMainService().getMainObject().getClass();
		request.setAttribute("mainClassName", mainclazz.getName());
		// linkClassName
		Class linkclazz = this.getLinkService().getLinkObject().getClass();
		request.setAttribute("linkClassName", linkclazz.getName());
		
	}

	/**
	 * 为环节设置默认的受理时限和处理时限
	 */
	@SuppressWarnings("unchecked")
	public BaseLink setStepLimitToLink(BaseMain main,BaseLink link,String taskName,String operateType) throws Exception{
		//查询时限配置得到受理时限和处理时限的默认值
		//查询下一步的phaseid
		FlowDefineExplain explain = new FlowDefineExplain();
		if (taskName.equals("")) {
			explain.setFlowId(this.getMainService().getFlowTemplateName());
			explain.setCurrentPhaseId("receive");
		} else {
			explain.setFlowId(this.getMainService().getFlowTemplateName());
			explain.setCurrentPhaseId(taskName);
		}
		String toPhaseIdStr = "";
		if (this.getRoleConfigPath() != null
				&& !this.getRoleConfigPath().equals("")) {
			List list = explain.explain(this.getRoleConfigPath());
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					ToPhaseId toPhaseId = (ToPhaseId)list.get(i);
					String condition = toPhaseId.getCondition();
					if (!condition.equals("")) {
						Hashtable conhash = EomsInterpreter
								.getParamNameFromCondition(condition);
						Enumeration eunm = conhash.keys();

						while (eunm.hasMoreElements()) {
							String key = (String) eunm.nextElement();
							String value = operateType;
							if (value.equals("")) {
								value = "null";
							}
							condition = condition.replaceAll(
									"\\$\\{" + key + "\\}", value);
						}
						boolean isPass = EomsInterpreter
								.getbooleanFromExpression(condition);
						System.out.println("condition===" + condition
								+ "isPass====" + isPass);
						if (isPass == false) {
							continue;
						}else{
							toPhaseIdStr = toPhaseId.getId();
							break;
						}
					}
				}
			}
		}
		//查询下一步的处理时限和处理时限
		int acceptLimit = 0;
		int completeLimit = 0;
		int stepId = 0;
			String flowTemplateName = this.getMainService().getFlowTemplateName();
			HashMap conditionMap = SheetLimitUtil.getConditionByMapping(main,flowTemplateName);
		
			ISheetDealLimitManager sheetlimitmgr = (ISheetDealLimitManager) ApplicationContextHolder.getInstance().getBean("iSheetDealLimitManager");
			conditionMap.put("flowName", flowTemplateName);
			List sheetLimitList = sheetlimitmgr.getlevelLimitBySpecials(conditionMap);
			LevelLimit limit = null;
			String levelId = "";
			if (sheetLimitList != null && sheetLimitList.size() > 0) {
				limit = (LevelLimit) sheetLimitList.get(0);
				levelId = limit.getId();
				List stepList = sheetlimitmgr.getStepLimitByLevel(levelId);
				if(stepList!=null&&stepList.size()>0){
					for(int i=0;i<stepList.size();i++){
						StepLimit tmpStepLimit = (StepLimit)stepList.get(i);
						String tmptaskName = tmpStepLimit.getTaskName();
						String tmpAcceptLimit = tmpStepLimit.getAcceptLimit();
						String tmpCompleteLimit = tmpStepLimit.getCompleteLimit();
						if(tmpAcceptLimit==null) tmpAcceptLimit="0";
						if(tmpCompleteLimit==null) tmpCompleteLimit="0";
						//如果按照operateType没有查到下一步的name，则通过stepid得到下一步
						if(toPhaseIdStr==null||toPhaseIdStr.equals("")){
							if(stepId==0){
								if(tmptaskName.equals(taskName)){
									stepId = Integer.parseInt(tmpStepLimit.getStepId());
									stepId++;
								}
							}else{
								if(stepId==Integer.parseInt(tmpStepLimit.getStepId())){
									acceptLimit = Integer.parseInt(tmpAcceptLimit);
									completeLimit = Integer.parseInt(tmpCompleteLimit);
								}
							}
						}else{
							if(tmptaskName.equals(toPhaseIdStr)){
								acceptLimit = Integer.parseInt(tmpAcceptLimit);
								completeLimit = Integer.parseInt(tmpCompleteLimit);
							}
						}
					}
				}
			}
		if(acceptLimit!=0||completeLimit!=0){
			Date currentDate = new Date();
			//将工作时间和休息时间计算进来
			acceptLimit = SheetLimitUtil.getTimeLimitByConfig(currentDate,0,acceptLimit,flowTemplateName);
			completeLimit = SheetLimitUtil.getTimeLimitByConfig(currentDate,acceptLimit,completeLimit,flowTemplateName);
			
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = dateFormat.format(currentDate);
			Date tempAcceptLimit = SheetUtils.stringToDate(StaticMethod.getDateForMinute(date,acceptLimit));
			Date tempCompleteLimit = SheetUtils.stringToDate(StaticMethod.getDateForMinute(date,completeLimit));
			link.setNodeAcceptLimit(tempAcceptLimit);
			link.setNodeCompleteLimit(tempCompleteLimit);
		}
		return link;
	}
	
	/**
	 * 取得会审人员
	 */
	public JSONArray getSubAuditPerformer(TawSystemSessionForm sessionform,
			String piid) throws Exception {
		return null;
	}

}
