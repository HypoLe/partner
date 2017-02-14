package com.boco.eoms.sheet.base.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.RequestUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.sheet.base.flowchar.WorkFlow;
import com.boco.eoms.sheet.base.flowchar.xmltree.Graph;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.TawSystemWorkflow;
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ISheetInfoShowService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefineExplain;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 2:24:19 PM 
 */
public class SheetInfoShowServiceImpl implements ISheetInfoShowService {
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
	private ILinkService getLinkService(){
		return sheetFacadeService.getLinkService();
	}
	@SuppressWarnings("unused")
	private IBusinessFlowService getBusinessFlowService(){
		return sheetFacadeService.getBusinessFlowService();
	}
	public ITaskService getTaskService(){
		return sheetFacadeService.getTaskService();
	}
	public void getAllDoRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void getAllWorkflowStepInfoPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
	}

	@SuppressWarnings("unchecked")
	public Map getAttachmentAttributeOfOjbect() {
		return null;
	}

	/**
	 * 显示流程实例图
	 */
	@SuppressWarnings("unchecked")
	public void getLinkOperatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aiid = StaticMethod.nullObject2String(request.getParameter("aiid"), "");
		String sheetKey = "";
		if (!aiid.equals("")) {
			ITask task = (ITask) this.getTaskService().getSinglePO(aiid);
			if (task != null) {
				sheetKey = task.getSheetKey();
			}
		}
		if (!sheetKey.equals("")) {
			BaseMain sheetMain = (BaseMain) this.getMainService()
					.getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", sheetMain);
		}		
		String linkName = (getLinkService().getLinkObject().getClass()).getName();
		List links = this.getLinkService().getLinkOperateByAiid(aiid, linkName);
		
		String module = mapping.getPath();
		module = module.substring(1, module.length());
		request.setAttribute("module", module);
		request.setAttribute("HISTORY", links);
	}

	public void getNowDoRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void getPreRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	@SuppressWarnings("unchecked")
	public Map getProcessOjbectAttribute() {
		return null;
	}

	public void shoWholeWorkFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void showAtomDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void showDealInputTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
     * 显示工单详细界面
     */
	@SuppressWarnings("unchecked")
	public void showDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request.getParameter("sheetKey"), "");
		if (sheetKey.equals("")) {
			sheetKey = StaticMethod.nullObject2String(request.getAttribute("sheetKey"), "");
		}
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"), "");
		if (taskId.equals("")) {
			taskId = StaticMethod.nullObject2String(request.getAttribute("taskId"), "");
		}
		String taskName = StaticMethod.nullObject2String(request.getParameter("taskName"), "");
		if (taskName.equals("")) {
			taskName = StaticMethod.nullObject2String(request.getAttribute("taskName"), "");
		}
		String piid = StaticMethod.nullObject2String(request.getParameter("piid"), "");
		if (piid.equals("")) {
			piid = StaticMethod.nullObject2String(request.getAttribute("piid"),"");
		}
		String operateRoleId = StaticMethod.nullObject2String(request
				.getParameter("operateRoleId"), "");
		if (operateRoleId.equals("")) {
			operateRoleId = StaticMethod.nullObject2String(request.getAttribute("operateRoleId"), "");
		}
		String taskStatus = StaticMethod.nullObject2String(request.getParameter("taskStatus"), "");
		if (taskStatus.equals("")) {
			taskStatus = StaticMethod.nullObject2String(request.getAttribute("taskStatus"), "");
		}
		String preLinkId = StaticMethod.nullObject2String(request.getParameter("preLinkId"), "");
		if (preLinkId.equals("")) {
			preLinkId = StaticMethod.nullObject2String(request.getAttribute("preLinkId"), "");
		}
		String TKID = StaticMethod.nullObject2String(request.getParameter("TKID"));
		if (TKID.equals("")) {
			TKID = StaticMethod.nullObject2String(request.getAttribute("TKID"),"");
		}
		/**同组处理模式标志。**/
		String teamFlag=StaticMethod.nullObject2String(request
				.getParameter("teamFlag"));
		BocoLog.debug(this, "sheetKey==" + sheetKey);
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		if (!sheetKey.equals("")) {
			BaseMain mainObject = null;
			mainObject = (BaseMain) request.getAttribute("main");
			if (mainObject == null) {
				mainObject = getMainService().getSingleMainPO(sheetKey);
			}
			String parentSheetKey = StaticMethod.nullObject2String(mainObject
					.getParentSheetId());
			String parentBeanId = StaticMethod.nullObject2String(mainObject
					.getParentSheetName());
			System.out.println("parentSheetKey=" + parentSheetKey
					+ "parentBeanId=" + parentBeanId);
			if (!parentSheetKey.equals("") && !parentBeanId.equals("")) {
				IMainService parentMainSerivce = (IMainService) ApplicationContextHolder
						.getInstance().getBean(parentBeanId);
				BaseMain parentMain = parentMainSerivce.getSingleMainPO(parentSheetKey);
				String parentSheetId = parentMain.getSheetId();

				ITawSystemWorkflowManager mgr = (ITawSystemWorkflowManager) ApplicationContextHolder
						.getInstance().getBean("ITawSystemWorkflowManager");
				TawSystemWorkflow workflow = mgr
						.getTawSystemWorkflowByBeanId(parentBeanId);
				String parentProcessCnName = workflow.getRemark();

				request.setAttribute("parentSheetId", parentSheetId);
				request.setAttribute("parentProcessCnName",parentProcessCnName);

				System.out.println("parentSheetId=" + parentSheetId
						+ "parentProcessCnName=" + parentProcessCnName);
			}
			request.setAttribute("sheetMain", mainObject);
		}

		String dealTemplateId = StaticMethod.nullObject2String(request
				.getParameter("dealTemplateId"));
		if (dealTemplateId != null && !dealTemplateId.equals("")) {
			request.setAttribute("dealTemplateId", dealTemplateId);
			String operateType = StaticMethod.nullObject2String(request
					.getParameter("operateType"));
			request.setAttribute("operateType", operateType);
		}
		HashMap sessionMap = new HashMap();
		sessionMap.put("userId", sessionform.getUserid());
		sessionMap.put("password", sessionform.getPassword());
		if (!TKID.equals("") && (taskStatus.equals(Constants.TASK_STATUS_READY)
			|| taskStatus.equals(Constants.TASK_STATUS_RUNNING) || taskStatus
			.equals(Constants.TASK_STATUS_CLAIMED))) {
			ITask task = null;
			try {
				task = (ITask) request.getAttribute("task");
				if (task == null) {
					task = this.getTaskService().getSinglePO(TKID);
				}
				//task = this.getTaskService().getSinglePO(TKID);
				String isWaitForSubTask = task.getIfWaitForSubTask();
				if (isWaitForSubTask.equals("true")) {
					List subTaskList = this.getTaskService()
							.getUndealTaskListByParentTaskId(task.getId());
					if (subTaskList != null && subTaskList.size() > 0) {
						request.setAttribute("needDealReply", "true");
					}
				}
				request.setAttribute("task", task);
			} catch (Exception e) {
				request.setAttribute("task", null);
			}
		} else {
			request.setAttribute("task", null);
		}
		
		if(!operateRoleId.equals("")){
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemSubRoleManager");
			TawSystemSubRole subrole = mgr.getTawSystemSubRole(operateRoleId);
			if (subrole != null) {
				System.out.println("==roleId==>>" + subrole.getRoleId() + "");
	 			request.setAttribute("roleId", subrole.getRoleId() + "");
			}
		}
		
		request.setAttribute("mainId", sheetKey);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskName", taskName);
		request.setAttribute("operateRoleId", operateRoleId);
		request.setAttribute("operateUserId", sessionform.getUserid());
		request.setAttribute("operateDeptId", sessionform.getDeptid());
		request.setAttribute("operaterContact", sessionform.getContactMobile());
		request.setAttribute("piid", piid);
		request.setAttribute("TKID", TKID);
		request.setAttribute("preLinkId", preLinkId);
		request.setAttribute("methodBeanId", mapping.getAttribute());
		request.setAttribute("teamFlag", teamFlag);
	}

	public void showInputTemplateSheetPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	/**
	 * main工单的详细信息页面，如归档，由我启动
	 */
	@SuppressWarnings("unchecked")
	public void showMainDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isAdmin = StaticMethod.nullObject2String(request
				.getParameter("isAdmin"));
		String sheetKey = RequestUtils.getStringParameter(request, "sheetKey");
		BaseMain mainObject = this.getMainService().getSingleMainPO(sheetKey);
		String parentSheetKey = StaticMethod.nullObject2String(mainObject
				.getParentSheetId());
		String parentBeanId = StaticMethod.nullObject2String(mainObject
				.getParentSheetName());

		if (!parentSheetKey.equals("") && !parentBeanId.equals("")) {
			IMainService parentMainSerivce = (IMainService) ApplicationContextHolder
					.getInstance().getBean(parentBeanId);
			BaseMain parentMain = parentMainSerivce.getSingleMainPO(parentSheetKey);
			String parentSheetId = parentMain.getSheetId();

			ITawSystemWorkflowManager mgr = (ITawSystemWorkflowManager) ApplicationContextHolder
					.getInstance().getBean("ITawSystemWorkflowManager");
			TawSystemWorkflow workflow = mgr
					.getTawSystemWorkflowByBeanId(parentBeanId);
			String parentProcessCnName = workflow.getRemark();

			request.setAttribute("parentSheetId", parentSheetId);
			request.setAttribute("parentProcessCnName",
							parentProcessCnName);
		}
		request.setAttribute("sheetMain", mainObject);
		if (!isAdmin.equals("")) {
			request.setAttribute("isAdmin", isAdmin);  
		}
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		
		List roleList = sessionform.getRolelist();
		int listSize = roleList.size();
		BocoLog.debug(this, "==listSize==>>" + listSize);
		System.out.println("==listSize==>>" + listSize);
		String roleId = "";
		for(int i=0;i<listSize;i++){
			TawSystemSubRole subRole = (TawSystemSubRole)roleList.get(i);
			roleId = roleId + "," + subRole.getRoleId();
		}
		System.out.println("==roleId==>>" + roleId);
		request.setAttribute("roleId", roleId);
		// 分派的处理
		if (mainObject.getStatus().intValue() == 0) {
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();

			String condition = "sheetKey='"
					+ sheetKey
					+ "' and taskStatus<>'5' and ifWaitForSubTask='true' "
					+ " and ((taskOwner='"
					+ userId
					+ "'"
					+ "or taskOwner='"
					+ deptId
					+ "')"
					+ " or taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='"
					+ userId + "')) ";
			List undoTaskList = this.getTaskService().getTasksByCondition(
					condition);
			ITask task = null;
			if (undoTaskList != null && undoTaskList.size() > 0) {
				if (undoTaskList.size() == 1) {
					task = (ITask) undoTaskList.get(0);
				} else {
					task = (ITask) undoTaskList.get(0);
					String taskId = task.getId();
					for (int i = 0; i < undoTaskList.size(); i++) {
						ITask tmpTask = (ITask) undoTaskList.get(i);
						if (tmpTask.getSubTaskFlag() != null
								&& tmpTask.getSubTaskFlag().equals("true")
								&& !taskId.equals(tmpTask.getId())) {
							taskId = tmpTask.getId();
							task = tmpTask;
						}
						for (int j = i + 1; j < undoTaskList.size(); j++) {
							ITask tmptmpTask = (ITask) undoTaskList.get(j);
							if (taskId.equals(tmptmpTask.getParentTaskId())) {
								taskId = tmptmpTask.getId();
								task = tmptmpTask;
								i = j;
								break;
							}
						}
					}
				}
				request.setAttribute("sheetKey", task.getSheetKey());
				request.setAttribute("taskId", task.getId());
				request.setAttribute("taskName", task.getTaskName());
				request.setAttribute("piid", task.getProcessId());
				request.setAttribute("operateRoleId", task.getOperateRoleId());
				request.setAttribute("taskStatus", task.getTaskStatus());
				request.setAttribute("preLinkId", task.getPreLinkId());
				request.setAttribute("TKID", task.getId());
				this.showDetailPage(mapping, form, request, response);
				request.setAttribute("distributeForward", "detail");
			}
		}
	}

	public void showPhaseReplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void showQueryHidePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	@SuppressWarnings("unchecked")
	public void showRemarkPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String preLinkId = StaticMethod.nullObject2String(request
				.getParameter("preLinkId"), "");
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"), "");
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String TKID = StaticMethod.nullObject2String(request
				.getParameter("TKID"), "");
		if (!sheetKey.equals("")) {
			BaseMain mainObject = getMainService().getSingleMainPO(sheetKey);
			request.setAttribute("sheetMain", mainObject);
		}
		BaseLink baselink = this.getLinkService().getSingleLinkPO(preLinkId);
		request.setAttribute("baselink", baselink);
		request.setAttribute("sheetLink", baselink);
	//	String remark = baselink.getRemark();
		String mainId = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"));
		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		request.setAttribute("taskName", taskName);
		request.setAttribute("tkid", TKID);
		ITask task = this.getTaskService().getSinglePO(TKID);
		request.setAttribute("task", task);
		request.setAttribute("piid", piid);
		request.setAttribute("mainId", mainId);
	//	request.setAttribute("remark", remark);
		
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

	@SuppressWarnings("unchecked")
	public void showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mainId = StaticMethod.null2String(request.getParameter("id"));

		BaseMain sheetMain = this.getMainService().getSingleMainPO(mainId);
		List sheetLinks = this.getLinkService().getLinksByMainId(mainId);

		Map ObjectMap = this.getAttachmentAttributeOfOjbect();
		List mainAttributeList = (List) ObjectMap.get("mainObject");
		List linkAttributeList = (List) ObjectMap.get("linkObject");

		Map operationMap = new HashMap();
		Map userIdMap = new HashMap();
		StringBuffer where = new StringBuffer();

		for (Iterator iterator = mainAttributeList.iterator(); iterator
				.hasNext();) {
			String attribute = (String) iterator.next();
			String getMethod = "get" + StaticMethod.firstToUpperCase(attribute);
			Method setterMethod = sheetMain.getClass().getMethod(getMethod,
					null);
			String accessories = (String) setterMethod.invoke(sheetMain, null);

			if (accessories != null && !accessories.equals("")) {
				String attachments[] = accessories.split(",");
				for (int i = 0; i < attachments.length; i++) {
					String attachmentName = attachments[i].replaceAll("'", "");
					operationMap.put(attachmentName, "");
					userIdMap.put(attachmentName, sheetMain.getSendUserId());
				}
				where.append(accessories);
			}
		}

		if (sheetLinks.size() > 0) {
			for (Iterator it = sheetLinks.iterator(); it.hasNext();) {
				BaseLink baseLink = (BaseLink) it.next();

				for (Iterator iterator = linkAttributeList.iterator(); iterator
						.hasNext();) {
					String attribute = (String) iterator.next();
					String getMethod = "get"
							+ StaticMethod.firstToUpperCase(attribute);
					Method setterMethod = baseLink.getClass().getMethod(getMethod, null);
					String accessories = (String) setterMethod.invoke(baseLink,null);

					if (accessories != null && !accessories.equals("")) {
						String nodeAccessoriesArray[] = accessories.split(",");
						for (int i = 0; i < nodeAccessoriesArray.length; i++) {
							String attachmentName = nodeAccessoriesArray[i]
									.replaceAll("'", "");
							operationMap.put(attachmentName, baseLink
									.getActiveTemplateId());
							userIdMap.put(attachmentName, baseLink
									.getOperateUserId());
						}
						if (!where.toString().equals("")) {
							where.append(",");
						}
						where.append(accessories);
					}
				}
			}
		}

		if (!where.toString().equals("")) {
			List newAttachments = new ArrayList();
			List attachments = this.getMainService().getAllAttachmentsBySheet(
					where.toString());
			if (attachments.size() > 0) {
				for (Iterator it = attachments.iterator(); it.hasNext();) {
					TawCommonsAccessories attachment = (TawCommonsAccessories) it
							.next();
					TawCommonsAccessoriesForm tawCommonsAccessoriesForm = new TawCommonsAccessoriesForm();
					Map attachmentMap = SheetBeanUtils.describe(attachment);
					SheetBeanUtils.populateMap2Bean(tawCommonsAccessoriesForm,
							attachmentMap);
					String ActiveTemplateId = (operationMap.get(attachment
							.getAccessoriesName()) == null ? ""
							: (String) operationMap.get(attachment
									.getAccessoriesName()));
					tawCommonsAccessoriesForm
							.setActiveTemplateId(ActiveTemplateId);
					tawCommonsAccessoriesForm
							.setAccessoriesUploadDate(attachment
									.getAccessoriesUploadTime());
					String userId = (userIdMap.get(attachment
							.getAccessoriesName()) == null ? ""
							: (String) userIdMap.get(attachment
									.getAccessoriesName()));
					tawCommonsAccessoriesForm.setAccessoriesUploader(userId);
					newAttachments.add(tawCommonsAccessoriesForm);
				}
			}
			request.setAttribute("sheetAccessories", newAttachments);
		}
		operationMap.clear();
		userIdMap.clear();
	}

	@SuppressWarnings("unchecked")
	public void showSheetDealList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("taskName"), "");	
		String ifWaitForSubTask = StaticMethod.nullObject2String(request
				.getParameter("ifWaitForSubTask"), "");			
		if (!sheetKey.equals("")) {
			BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
			List list = getLinkService().getLinksByMainId(sheetKey);
			
			String orderByDetp = StaticMethod.nullObject2String(request
					.getParameter("orderByDetp"), "");
			if (orderByDetp.equals("true")) {
				//历史列表按部门排列
				Map deptIdMap = new HashMap();
				List deptIdarray = new ArrayList();
				Map numberMap = new HashMap();
				int i = 0;
				//循环把部门的ID归类
				for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
					BaseLink baseLink = (BaseLink)iterator.next();
					i ++;
					numberMap.put(baseLink.getId(), new Integer(i));
					
					if (deptIdMap.get(baseLink.getOperateDeptId()) == null) {
						List links = new ArrayList();
						links.add(baseLink);
						deptIdarray.add(baseLink.getOperateDeptId());
						deptIdMap.put(baseLink.getOperateDeptId(), links);
					} else {
						List links = (List)deptIdMap.get(baseLink.getOperateDeptId());
						links.add(baseLink);
					}
				}
				
				request.setAttribute("numberMap", numberMap);
				request.setAttribute("deptIdtable", deptIdarray);
				request.setAttribute("deptIdMap", deptIdMap);
			}
			
			request.setAttribute("HISTORY", list);
			request.setAttribute("taskName", taskName);
			request.setAttribute("ifWaitForSubTask", ifWaitForSubTask);
			request.setAttribute("sheetMain", main);
		}
		BaseLink link = this.getLinkService().getLinkObject();
		request.setAttribute("linkClassName", link);
	}

	@SuppressWarnings("unchecked")
	public void showWorkFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 工单的ID号
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"), "");
		String description = StaticMethod.nullObject2String(request
				.getParameter("description"), "");
		String dictSheetName = StaticMethod.nullObject2String(request
				.getParameter("dictSheetName"), "");
		String linkServiceName = StaticMethod.nullObject2String(request
				.getParameter("linkServiceName"), "");
		
		// 得到配置文件如：netdata-config.xml里的内容
		String flowTemplateName = StaticMethod.nullObject2String(request
				.getParameter("flowTemplateName"), "");
		if (flowTemplateName.equals("")) {
			flowTemplateName = this.getMainService().getFlowTemplateName();
		}
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				flowTemplateName, sheetFacadeService.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine == null)
			return;

		// 将link转成map 这里主要是为了在步骤列表上显示处理完成时间
		List links = this.getLinkService().getLinksByMainId(sheetKey);
		Map linkObjectMap = new HashMap();
		for (Iterator it = links.iterator(); it.hasNext();) {
			BaseLink link = (BaseLink) it.next();
			if (link.getActiveTemplateId() != null
					&& !link.getActiveTemplateId().equals("")) {
				// 确认受理
				if (link.getOperateType().intValue() == Constants.ACTION_ACCEPT) {
					linkObjectMap.put(link.getActiveTemplateId() + "1", link);
				} else {
					// 一般的
					linkObjectMap.put(link.getActiveTemplateId() + "2", link);
				}

			}
		}
		
		//设置传入的参数，一个都不能少
		Map parameterMap = new HashMap();
		parameterMap.put("sheetKey", sheetKey);
		parameterMap.put("description", description);
		parameterMap.put("dictSheetName", dictSheetName);
		parameterMap.put("linkServiceName", linkServiceName);
		parameterMap.put("path", mapping.getPath());
		parameterMap.put("flowDefine", flowDefine);
		parameterMap.put("linkObjectMap", linkObjectMap);
		parameterMap.put("tasks", linkObjectMap);
		parameterMap.put("taskService", this.getTaskService());
		
		//在单独类里进行产生动态流程图
		Map resultMap = new WorkFlow().createWorkFlow(parameterMap);
		
		//将得到的结果传入页面
		String workflowXML = flowTemplateName + ".xml";
		String workFlowName = flowDefine.getDescription();
		request.setAttribute("workflowXML", workflowXML);
		request.setAttribute("workFlowName", workFlowName);
		request.setAttribute("stepList", resultMap.get("stepList"));
		request.setAttribute("joinLineList", resultMap.get("joinLineList"));
		request.setAttribute("historyList", resultMap.get("historyList"));
		request.setAttribute("currentList", resultMap.get("currentList"));
	}

	/**
	 * 显示流程实例图
	 */
	@SuppressWarnings("unchecked")
	public void showWorkFlowInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mainId = StaticMethod.nullObject2String(request.getParameter("mainId"), "");
		String taskName = StaticMethod.nullObject2String(request.getParameter("taskName"), "");
		String dictSheetName = StaticMethod.nullObject2String(request.getParameter("dictSheetName"), "");
		String description = StaticMethod.nullObject2String(request.getParameter("description"), "");
		String linkServiceName = StaticMethod.nullObject2String(request.getParameter("linkServiceName"), "");

		// 该环节下的所有任务
		List allAiTaskList = this.getTaskService().getAiTasksByTaskNameAndSheetId(taskName, mainId);
		List allTiTaskList = this.getTaskService().getTkiTasksByTaskNameAndSheetId(taskName, mainId);

		Graph graph = new Graph(linkServiceName, description, dictSheetName);
		String[] vml = graph.draw(allAiTaskList, allTiTaskList);
		request.setAttribute("vml", vml);
		request.setAttribute("module", mapping.getPath());
	}
	
	/**
	 * 显示工单统计信息
	 */
	public void showStatisticInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

}
