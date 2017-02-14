package com.boco.eoms.sheet.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ISheetListService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.QuerySqlInit;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefineExplain;
import com.boco.eoms.sheet.base.util.flowdefine.xml.PhaseId;
import com.boco.eoms.sheet.overtimetip.service.IOvertimeTipManager;
import com.boco.eoms.sheet.overtimetip.util.OvertimeTipUtil;


/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 4:18:29 PM 
 */
public class SheetListServiceImpl implements ISheetListService {
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

	public void getAtomLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void getDoneAllLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void getDoneAllListsForPortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	public void getDoneListsForPortal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public int getPageLength() {
		return 0;
	}

	public void getUndoAllLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void getUndoAllListsForPortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@SuppressWarnings("unchecked")
	public Map getUndoList(HttpServletRequest request) throws Exception {
		return null;
	}

	public void getUndoListsForPortal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	/**
	 * 列表查询
	 */
	@SuppressWarnings("unchecked")
	public void performListQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map actionMap = request.getParameterMap();

		// 获取当前用户的角色列表
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		BaseMain mainObject = (BaseMain) this.getMainService().getMainObject().getClass().newInstance();
		ITask taskObject = (ITask) this.getTaskService().getTaskModelObject().getClass().newInstance();
		String order = StaticMethod.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder("taskList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod.null2String(request.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
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

		String flowName = this.getMainService().getFlowTemplateName();
		String beanName = mapping.getAttribute();
		Map condition = new HashMap();
		condition.put("mainObject", mainObject);
		condition.put("taskObject", taskObject);
		condition.put("orderCondition", orderCondition);
		condition.put("userId", userId);
		condition.put("deptId", deptId);
		condition.put("flowName", flowName);
		condition.put("beanName", beanName);

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		String exportType = StaticMethod
				.null2String(request.getParameter(new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		// 分页取得列表
		String[] aSql = { "" };
		int[] aTotal = { 0 };
		List result = this.getMainService().getQueryAclListResult(aSql,
				actionMap, condition, pageIndex,new Integer(pageSize.intValue()), aTotal, userId, deptId);
		Integer total = new Integer(aTotal[0]);
		List taskList = new ArrayList();
		List taskMapList = new ArrayList();
		List mainMapList = new ArrayList();	
		Object done = actionMap.get("doneType");
		if (done != null && done.getClass().isArray()) {
			done = ((Object[]) done)[0];
		}
		if (done != null) {
			if (done.equals("senddone")) {
		        String[] variables= QuerySqlInit.getAllDictItemsName(beanName).split(",");	
                for(int i = 0;result!=null && ! result.isEmpty()&& i < result.size();i++ ){
        			Object[] objectList  = (Object[]) result.get(i);
        			Map ListMap = new HashMap();
        			for(int j= 0;j < objectList.length; j++){
        				String variablesKey = variables[j];
        				if(variablesKey.indexOf("main.")>=0 || variablesKey.indexOf("task.")>=0){
        					variablesKey = variablesKey.substring(5);
        				}
        				ListMap.put(variablesKey, objectList[j]);			
        			}
        			mainMapList.add(ListMap);
                }
				request.setAttribute("taskList", mainMapList);

			} else if (done.equals("sendundo")) {

				// 设置每条task超时标识
				if (result != null && result.size() > 0) {
					// 查询超时配置表
					IOvertimeTipManager service = (IOvertimeTipManager) ApplicationContextHolder
							.getInstance().getBean("iOvertimeTipManager");
					List timeList = service.getEffectOvertimeTip(this
							.getMainService().getFlowTemplateName(), userId);
					// 得到角色细分字段
					HashMap columnMap = OvertimeTipUtil
							.getAllMainColumnByMapping(flowName);
					HashMap columnMapOverTip = OvertimeTipUtil
							.getNotOverTimeColumnByMapping(flowName);
					// 循环为task超时标识赋值
					for (int i = 0; i < result.size(); i++) {
						ITask tmptask = null;
						Map taskMap = new HashMap();
						Map tmptaskMap = new HashMap();
						HashMap conditionMap = new HashMap();
						if (columnMap.size() > 0) {
							Object[] tmpObjArr = (Object[]) result.get(i);
							tmptask = (ITask) tmpObjArr[0];
							// 根据角色细分得到需要匹配的字段
							Iterator it = columnMap.keySet().iterator();
							int j = 0;
							while (it.hasNext()) {
								j++;
								String elementKey = (String) it.next();
								Object tempcolumn = tmpObjArr[j];
								conditionMap.put(elementKey, tempcolumn);
								tmptaskMap.put(columnMap.get(elementKey),
										tempcolumn);
							}
						} else {
							tmptask = (ITask) result.get(i);
						}
						// 得到超时类型
						if (exportType.equals("")) {
							String overtimeFlag = OvertimeTipUtil
									.setOvertimeTipFlag(columnMapOverTip,
											tmptask.getCompleteTimeLimit(),
											conditionMap, timeList, flowName);
							taskMap.put("overtimeType", overtimeFlag);
						}
						taskMap.putAll(SheetBeanUtils.bean2Map(tmptask));
						taskMap.putAll(tmptaskMap);
						taskList.add(tmptask);
						taskMapList.add(taskMap);
					}
				}
				request.setAttribute("taskList", taskMapList);
			}
		}

		request.setAttribute("total", total);
		request.setAttribute("pageSize", pageSize);

		String findForward = StaticMethod.null2String(request
				.getParameter("findForward"));
		request.setAttribute("findForward", findForward);

		// 找出该流程中的节点
		String workflowName = this.getMainService().getFlowTemplateName();
		ArrayList phaseIdList = new ArrayList();
		Map phaseIdMap = new HashMap();
		FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
				workflowName, sheetFacadeService.getRoleConfigPath());
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
		request.setAttribute("module", mapping.getPath().substring(1));

		// 是否要进行批量处理
		String batch = StaticMethod.nullObject2String(request
				.getParameter("batch"));
		if (!batch.equals("")) {
			request.setAttribute("batch", batch);
			if (!batch.equals("") && batch.equals("true")) {
				// 需要进行批量回复和批量归档的步骤放入到tempMap中
				Map tempMap = new HashMap();
				String dictName = "dict-sheet-"
						+ mapping.getPath().substring(1);
				List dictItems = DictMgrLocator.getDictService().getDictItems(
						Util.constituteDictId(dictName, "activeTemplateId"));
				for (Iterator it = dictItems.iterator(); it.hasNext();) {
					DictItemXML dictItemXml = (DictItemXML) it.next();
					String description = dictItemXml.getDescription();
					if (description.equals("batch:true")) {
						tempMap.put(dictItemXml.getItemId(), dictItemXml.getItemName());
					}
				}

				// 和所查找的任务列表进行对比，如果该列表中有批量回复和批量归档的步骤就放入到batchTaskMap中
				Map batchTaskMap = new HashMap();
				if (tempMap.size() > 0) {
					for (Iterator it = tempMap.keySet().iterator(); it
							.hasNext();) {
						String taskName = (String) it.next();
						if (result != null) {
							for (Iterator tasks = taskList.iterator(); tasks
									.hasNext();) {
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
				}
				request.setAttribute("batchTaskMapKey", batchTaskMap.keySet());
				request.setAttribute("batchTaskMap", batchTaskMap);
			}
		}
	}

	/**
	 * 查询提交
	 */
	@SuppressWarnings("unchecked")
	public void performQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map actionMap = request.getParameterMap();

		//分页相关
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();
		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		String[] aSql = { "" };
		int[] aTotal = { 0 };
		String order = StaticMethod.null2String(request.getParameter(
				new org.displaytag.util.ParamEncoder("taskList")
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
		
		//设置查询条件
		Map condition = new HashMap();
		condition.put("orderCondition", orderCondition);
		
		//判断是否开启了工单查询权限开关
		PnrDeviceInspectSwitchConfig config = (PnrDeviceInspectSwitchConfig) request.getSession()
					.getServletContext().getAttribute("pnrInspect2SwitchConfig");
		if(config.isOpenSheetQueryRights()){
			condition.put("sheetQueryRights", "true");
			//设置用户所在部门和地域以供权限控制
			condition.put("rights", "true"); //是否需要权限控制，部门功能模块不需要按权限查询
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userDeptId = sessionForm.getDeptid();
			condition.put("userId", sessionForm.getUserid());
			condition.put("userDeptId", userDeptId);
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
						.getInstance().getBean("ItawSystemDeptManager");
			TawSystemDept dept = deptMgr.getDeptinfobydeptid(userDeptId, "0");
			condition.put("userAreaId", dept.getAreaid());
		}
		
		String exportType = StaticMethod.null2String(
				request.getParameter(new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String queryType = StaticMethod.nullObject2String(request.getParameter("queryType"));
		
		//工单查询
		List result = this.getMainService().getQueryResult(aSql, actionMap,
				condition, pageIndex, new Integer(pageSize.intValue()), aTotal,queryType);

		Integer total = new Integer(aTotal[0]);
		if (queryType != null && queryType.equals("number")) {
			request.setAttribute("recordTotal", total);
		}
		request.setAttribute("taskList", result);
		request.setAttribute("total", total);
		request.setAttribute("pageSize", pageSize);
	}

	public void setPageLength(int pageLenth) {
	}


}
