package com.boco.eoms.sheet.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.DictItemXML;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.TawSystemWorkflow;
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ISheetListShowService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.QuerySqlInit;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefineExplain;
import com.boco.eoms.sheet.base.util.flowdefine.xml.PhaseId;
import com.boco.eoms.sheet.overtimetip.util.OvertimeTipUtil;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 22, 2012 10:37:23 AM 
 */
public class SheetListShowServiceImpl implements ISheetListShowService {
	
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
	
	@SuppressWarnings("unused")
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
	public String getRoleConfigPath() {
		return sheetFacadeService.getRoleConfigPath();
	}

	public void deferAppList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	/**
	 * 显示撤销工单列表
	 */
	@SuppressWarnings("unchecked")
	public void showCancelList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);

		// 当前页数
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		String beanName = mapping.getAttribute();
		HashMap condition = new HashMap();
		condition.put("orderCondition", orderCondition);
		condition.put("beanName", beanName);
		// 分页取得列表
		Integer total = this.getMainService().getCancelCount();
		// wps端分页取得列表
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		List result = this.getMainService().getCancelList(pageIndex,
				new Integer(pageSize.intValue()), condition);
		List cancelList = new ArrayList();	
        String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");		
		for (int i = 0; result != null && i < result.size(); i++) {
			Object[] objectList  = (Object[]) result.get(i);
			Map ListMap = new HashMap();
			for(int j= 0;j < objectList.length; j++){
				String variablesKey = variables[j];
				if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
					variablesKey = variablesKey.substring(5);
				}
				ListMap.put(variablesKey, objectList[j]);			
			}			
			cancelList.add(ListMap);
		}		
		request.setAttribute("taskList", cancelList);
		request.setAttribute("total", total);
		// request.setAttribute("method", "showHoldedList");
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("findForward", "cancellist");
		request.setAttribute("module", mapping.getPath().substring(1));

		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);

	}

	public void showDoneListForSameTeam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	/**
	 * 显示草稿列表
	 */
	@SuppressWarnings("unchecked")
	public void showDraftList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap filterMap = new HashMap();
		filterMap.put("TEMPLATE_NAME", getMainService().getFlowTemplateName());
		/** 获取登陆信息，add by qinmin* */
		HashMap sessionMap = new HashMap();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		sessionMap.put("userId", sessionform.getUserid());
		sessionMap.put("password", sessionform.getPassword());
		/** add by qinmin* */

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		// 当前页数
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		String userId = sessionform.getUserid();
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject()
				.getClass().newInstance();
		ITask taskObject = (ITask) this.getTaskService().getTaskModelObject()
				.getClass().newInstance();
		Map condition = new HashMap();
		condition.put("mainObject", mainObject);
		condition.put("userId", userId);
		condition.put("taskObject", taskObject);
		condition.put("orderCondition", orderCondition);
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		HashMap taskListMap = this.getTaskService().getDraftList(condition,
				this.getMainService().getFlowTemplateName(), pageIndex,
				pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskOvertimeList = (List) taskListMap.get("taskList");
		List taskMapList = new ArrayList();
		HashMap columnMap = OvertimeTipUtil.getAllMainColumnByMapping(this
				.getMainService().getFlowTemplateName());
		for (int i = 0; i < taskOvertimeList.size(); i++) {
			ITask tmptask = null;
			Map taskMap = new HashMap();
			Map tmptaskMap = new HashMap();
			HashMap conditionMap = new HashMap();
			if (columnMap.size() > 0) {
				Object[] tmpObjArr = (Object[]) taskOvertimeList.get(i);
				tmptask = (ITask) tmpObjArr[0];
				Iterator it = columnMap.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					j++;
					String elementKey = (String) it.next();
					Object tempcolumn = tmpObjArr[j];
					conditionMap.put(elementKey, tempcolumn);
					tmptaskMap.put(columnMap.get(elementKey), tempcolumn);
				}
			} else {
				tmptask = (ITask) taskOvertimeList.get(i);
			}
			taskMap.putAll(SheetBeanUtils.bean2Map(tmptask));
			taskMap.putAll(tmptaskMap);
			taskMapList.add(taskMap);
		}
		request.setAttribute("taskList", taskMapList);
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * 显示归档工单列表
	 */
	@SuppressWarnings("unchecked")
	public void showHoldedList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		String order = StaticMethod.null2String(request
					.getParameter(new org.displaytag.util.ParamEncoder("taskList")
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod.null2String(request
					.getParameter(new org.displaytag.util.ParamEncoder("taskList")
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		String exportType = StaticMethod.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder("taskList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject().getClass().newInstance();
		String beanName = mapping.getAttribute();
		Map condition = new HashMap();
		condition.put("mainObject", mainObject);
		condition.put("orderCondition", orderCondition);
		condition.put("beanName", beanName);
		
		//判断是否开启了工单查询权限开关
		PnrDeviceInspectSwitchConfig config = (PnrDeviceInspectSwitchConfig) request.getSession()
					.getServletContext().getAttribute("pnrInspect2SwitchConfig");
		if(config.isOpenSheetQueryRights()){
			condition.put("sheetQueryRights", "true");
			//设置用户所在部门和地域以供权限控制
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userDeptId = sessionForm.getDeptid();
			condition.put("userId", sessionForm.getUserid());
			condition.put("userDeptId", userDeptId);
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
						.getInstance().getBean("ItawSystemDeptManager");
			TawSystemDept dept = deptMgr.getDeptinfobydeptid(userDeptId, "0");
			condition.put("userAreaId", dept.getAreaid());
		}
		
		// 当前页数
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		//获取归档列表
		Map map = this.getMainService().getHolds(condition, pageIndex,new Integer(pageSize.intValue()));
		Integer total = Integer.parseInt(map.get("count").toString());
		List result = (List)map.get("list");
		
		List holdedList = new ArrayList();
        String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");		
		for (int i = 0; result != null && i < result.size(); i++) {
			Object[] objectList  = (Object[]) result.get(i);
			Map ListMap = new HashMap();
			for(int j= 0;j < objectList.length; j++){
				String variablesKey = variables[j];
				if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
					variablesKey = variablesKey.substring(5);
				}
				ListMap.put(variablesKey, objectList[j]);			
			}			
			holdedList.add(ListMap);
		}
		request.setAttribute("taskList", holdedList);
		request.setAttribute("total", total);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("findForward", "holdlist");
		request.setAttribute("module", mapping.getPath().substring(1));

		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);
	}

	public void showHoldedListForUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void showListForAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	@SuppressWarnings("unchecked")
	public void showListUndo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap filterMap = new HashMap();
		filterMap.put("TEMPLATE_NAME", getMainService().getFlowTemplateName());

		HashMap sessionMap = new HashMap();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		sessionMap.put("userId", sessionform.getUserid());
		sessionMap.put("password", sessionform.getPassword());

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes")).getPageSize();
		// 当前页数
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String order = StaticMethod
				.null2String(request.getParameter(new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod.null2String(request.getParameter(
				new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		String exportType = StaticMethod
				.null2String(request.getParameter(new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		// 增加map对象，存放main，传入taskService.getUndoTask
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject().getClass().newInstance();
		ITask taskObject = (ITask) this.getTaskService().getTaskModelObject().getClass().newInstance();
		Map condition = new HashMap();
		condition.put("mainObject", mainObject);
		condition.put("taskObject", taskObject);
		condition.put("orderCondition", orderCondition);

		// 得到待处理列表
		String flowName = this.getMainService().getFlowTemplateName();
		
		//---------add by 李峰 2013年2月25日14点  如果是手机客户端访问,则修改pageIndex的取值方式
		if("android".equals(StaticMethod.nullObject2String(request.getParameter("type")))){
			pageIndex = StaticMethod.nullObject2int(request.getParameter("pageIndex"))-1;
			System.out.println("pageIndex   "+pageIndex);
		}
		
		//获取待处理工单列表
		HashMap taskListOvertimeMap = this.getTaskService()
				.getUndoTaskByOverTime(condition, userId, deptId, flowName,pageIndex, pageSize);
		int total = ((Integer) taskListOvertimeMap.get("taskTotal")).intValue();
		List taskOvertimeList = (List) taskListOvertimeMap.get("taskList");
		
		List taskMapList = new ArrayList();
		List taskList = new ArrayList();
		// 设置每条task超时标识
		if (taskOvertimeList != null && taskOvertimeList.size() > 0) {
//			// 查询超时配置表
//			IOvertimeTipManager service = (IOvertimeTipManager) ApplicationContextHolder.getInstance().getBean("iOvertimeTipManager");
//			List timeList = service.getEffectOvertimeTip(this.getMainService().getFlowTemplateName(), userId);
//			// 得到角色细分字段
			HashMap columnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
//			HashMap columnMapOverTip = OvertimeTipUtil.getNotOverTimeColumnByMapping(flowName);
			// 循环为task超时标识赋值
			for (int i = 0; i < taskOvertimeList.size(); i++) {
				ITask tmptask = null;
				Map taskMap = new HashMap();
				Map tmptaskMap = new HashMap();
				HashMap conditionMap = new HashMap();
				if (columnMap.size() > 0) {
					Object[] tmpObjArr = (Object[]) taskOvertimeList.get(i);
					tmptask = (ITask) tmpObjArr[0];
					// 根据角色细分得到需要匹配的字段
					Iterator it = columnMap.keySet().iterator();
					int j = 0;
					while (it.hasNext()) {
						j++;
						String elementKey = (String) it.next();
						Object tempcolumn = tmpObjArr[j];
						conditionMap.put(elementKey, tempcolumn);
						tmptaskMap.put(columnMap.get(elementKey), tempcolumn);
					}
				} else {
					tmptask = (ITask) taskOvertimeList.get(i);
				}
				// 得到超时类型
				if (exportType.equals("")) {
//					String overtimeFlag = OvertimeTipUtil.setOvertimeTipFlag(columnMapOverTip, tmptask.getCompleteTimeLimit(),
//							conditionMap, timeList, flowName);
					
					Date sheetCompleteLimitDate = (Date)tmptaskMap.get("sheetCompleteLimit");
					String overtimeFlag = OvertimeTipUtil.setOvertimeTipFlag(sheetCompleteLimitDate, 30);
					taskMap.put("overtimeType", overtimeFlag);
				}
				taskMap.putAll(SheetBeanUtils.bean2Map(tmptask));
				taskMap.putAll(tmptaskMap);
				taskList.add(tmptask);
				taskMapList.add(taskMap);
			}
		}

		// 将分页后的列表写入页面供使用
//		Integer overTimeTaskCount = this.getTaskService().getOverTimeTaskCount(condition, userId, deptId);
//		request.setAttribute("overTimeTaskCount", overTimeTaskCount);
		
		request.setAttribute("taskList", taskMapList);
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("findForward", "list");
		request.setAttribute("module", mapping.getPath().substring(1));

		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);

		// 需要进行批量回复和批量归档的节点
		String batch = StaticMethod.null2String(request.getParameter("batch"));
		if (!batch.equals("") && batch.equals("true")) {
			// 需要进行批量回复和批量归档的步骤放入到tempMap中
			Map tempMap = new HashMap();
			String dictName = "dict-sheet-" + mapping.getPath().substring(1);
			List dictItems = DictMgrLocator.getDictService().getDictItems(
					Util.constituteDictId(dictName, "activeTemplateId"));
			for (Iterator it = dictItems.iterator(); it.hasNext();) {
				DictItemXML dictItemXml = (DictItemXML) it.next();
				String description = dictItemXml.getDescription();
				if (description.equals("batch:true")) {
					tempMap.put(dictItemXml.getItemId(), dictItemXml
							.getItemName());
				}
			}

			// 和所查找的任务列表进行对比，如果该列表中有批量回复和批量归档的步骤就放入到batchTaskMap中
			Map batchTaskMap = new HashMap();
			if (tempMap.size() > 0) {
				for (Iterator it = tempMap.keySet().iterator(); it.hasNext();) {
					String taskName = (String) it.next();
					for (Iterator tasks = taskList.iterator(); tasks.hasNext();) {
						ITask task = (ITask) tasks.next();
						if (taskName.equals(task.getTaskName())
								&& (task.getSubTaskFlag() == null
										|| task.getSubTaskFlag()
												.equals("false") || task
										.getSubTaskFlag().equals(""))) {
							batchTaskMap.put(task.getTaskName(), task
									.getTaskDisplayName());
							break;
						} else {
							continue;
						}
					}
				}
			}

			request.setAttribute("batchTaskMapKey", batchTaskMap.keySet());
			request.setAttribute("batchTaskMap", batchTaskMap);
		}

	}

	public void showListUndoByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 显示已处理列表
	 */
	@SuppressWarnings("unchecked")
	public void showListsenddone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap filterMap = new HashMap();
		filterMap.put("PROCESS_INSTANCE.TEMPLATE_NAME", getMainService()
				.getFlowTemplateName());
		/** 获取登陆信息，add by qinmin* */
		HashMap sessionMap = new HashMap();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		sessionMap.put("userId", sessionform.getUserid());
		sessionMap.put("password", sessionform.getPassword());
		// modified by Leo
		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);

		// 当前页数
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject()
				.getClass().newInstance();
		ITask taskObject = (ITask) this.getTaskService().getTaskModelObject()
				.getClass().newInstance();
		String beanName = mapping.getAttribute();
		Map condition = new HashMap();
		condition.put("mainObject", mainObject);
		condition.put("taskObject", taskObject);
		condition.put("orderCondition", orderCondition);
		condition.put("beanName", beanName);
		HashMap taskListMap = this.getTaskService().getDoneTask(condition,
				userId, deptId, this.getMainService().getFlowTemplateName(),
				pageIndex, pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");
		StringBuffer taskCondition = new StringBuffer();
		taskCondition.append(" taskOwner = '" + userId+ "' and ifWaitForSubTask='true' and taskStatus<>'"+ Constants.TASK_STATUS_FINISHED + "'");
		List tasks = this.getTaskService().getTasksByCondition(
				taskCondition.toString());
		Map tmpTaskMap = new HashMap();
		if (tasks != null && !tasks.isEmpty()) {
			for (int i = 0; i < tasks.size(); i++) {
				ITask task = (ITask) tasks.get(i);
				tmpTaskMap.put(task.getSheetKey(), task);
			}
		}
		List tmpTaskList = new  ArrayList();
        String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");		
		for (int i = 0; taskList != null && i < taskList.size(); i++) {
			Object[] objectList  = (Object[]) taskList.get(i);
			Map ListMap = new HashMap();
			for(int j= 0;j < objectList.length; j++){
				String variablesKey = variables[j];
				if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
					variablesKey = variablesKey.substring(5);
				}
				ListMap.put(variablesKey, objectList[j]);			
			}			
			if (tmpTaskMap.containsKey(ListMap.get("id"))) {
				ListMap.put("status", new Integer(-11));
			}	
			tmpTaskList.add(ListMap);
		}
		request.setAttribute("taskList", tmpTaskList);
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("findForward", "mainlist");
		request.setAttribute("module", mapping.getPath().substring(1));

		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);

	}

	public void showListsenddoneByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 显示由我启动列表
	 */
	@SuppressWarnings("unchecked")
	public void showOwnStarterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 当前页数
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String order = StaticMethod
				.null2String(request.getParameter(new org.displaytag.util.ParamEncoder(
				"taskList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder("taskList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		HashMap condition = new HashMap();
		String beanName = mapping.getAttribute();
		condition.put("orderCondition", orderCondition);
		condition.put("beanName", beanName);
		String exportType = StaticMethod
				.null2String(request.getParameter(new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		
		// wps端分页取得列表
		HashMap taskListMap = this.getMainService().getStarterList(
				sessionform.getUserid(), pageIndex,
				new Integer(pageSize.intValue()), condition);
		List result = (List) taskListMap.get("sheetList");
		Integer total = (Integer) taskListMap.get("sheetTotal");
		List startList = new ArrayList();
		String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");		
		for (int i = 0; result != null && i < result.size(); i++) {
			Object[] objectList  = (Object[]) result.get(i);
			Map ListMap = new HashMap();
			for(int j= 0;j < objectList.length; j++){
				String variablesKey = variables[j];
				if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
					variablesKey = variablesKey.substring(5);
				}
				ListMap.put(variablesKey, objectList[j]);			
			}			
			startList.add(ListMap);
		}	
		request.setAttribute("taskList", startList);
		request.setAttribute("total", total);
		// request.setAttribute("method", "showOwnStarterList");
		request.setAttribute("pageSize", pageSize);
		
		request.setAttribute("findForward", "startlist");
		request.setAttribute("module", mapping.getPath().substring(1));
		
		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);

	}

	/**
	 * 显示工单查询页面
	 */
	@SuppressWarnings("unchecked")
	public void showQueryPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workflowName = this.getMainService().getFlowTemplateName();
		//找出流程的ID号
		ITawSystemWorkflowManager wfManager=
    		(ITawSystemWorkflowManager)ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
		TawSystemWorkflow workflow = wfManager.getTawSystemWorkflowByName(workflowName);
		request.setAttribute("flowId", workflow.getFlowId());
		
		//设置初始化时间
		Calendar startDay = Calendar.getInstance();
		startDay.add(Calendar.DAY_OF_MONTH, -3);
		Calendar endDay = Calendar.getInstance();
		String startDate = DateUtil.formatDate(startDay.getTime(), "yyyy-MM-dd hh:mm:ss");
		String endDate = DateUtil.formatDate(endDay.getTime(), "yyyy-MM-dd hh:mm:ss");
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		
		//找出该流程中的节点
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(workflowName,this.getRoleConfigPath());
		FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		if (flowDefine != null) {
			PhaseId phaseIds[] = flowDefine.getPhaseId();
			for (int i = 0; i < phaseIds.length; i ++) {
				PhaseId phaseId = phaseIds[i];
				if (!phaseId.getId().equals("receive")) {
					phaseIdMap.put(phaseId.getId(), phaseId.getName());
					phaseIdList.add(phaseId.getId());
				}
			}
		}
		request.setAttribute("phaseIdMap", phaseIdMap);
		request.setAttribute("stepIdList", phaseIdList);
		request.setAttribute("module", mapping.getPath().substring(1));
	}

	public void showUnHoldList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void showUndoListByFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void showUndoListForSameTeam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
	}
	
    /**
     * 通过EOMS工单流水号查询工单列表
     */
    @SuppressWarnings("unchecked")
	public void showSheetListByEomsSheetId(ActionMapping mapping,
				ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		   String eomsSheetId = request.getParameter("eomsSheetId");//EOMS工单流水号
	
			// 获取每页显示条数
			Integer pageSize = ((SheetAttributes) ApplicationContextHolder
					.getInstance().getBean("SheetAttributes")).getPageSize();
			String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			// 当前页数
			final Integer pageIndex = new Integer(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			HashMap condition = new HashMap();
			String beanName = mapping.getAttribute();
			condition.put("eomsSheetId", eomsSheetId);
			condition.put("beanName", beanName);
			Map taskListMap = this.getMainService().getSheetListByEomsSheetId(condition, pageIndex, pageSize);
			
			List result = (List) taskListMap.get("sheetList");
			Integer total = (Integer) taskListMap.get("sheetTotal");
			List startList = new ArrayList();
			String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");		
			for (int i = 0; result != null && i < result.size(); i++) {
				Object[] objectList  = (Object[]) result.get(i);
				Map ListMap = new HashMap();
				for(int j= 0;j < objectList.length; j++){
					String variablesKey = variables[j];
					if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
						variablesKey = variablesKey.substring(5);
					}
					ListMap.put(variablesKey, objectList[j]);			
				}			
				startList.add(ListMap);
			}
			
			request.setAttribute("taskList", startList);
			request.setAttribute("total", total);
			request.setAttribute("pageSize", pageSize);
			
			request.setAttribute("findForward", "startlist");
			request.setAttribute("module", mapping.getPath().substring(1));
	   }
}
