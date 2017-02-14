package com.boco.eoms.eva.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceMgr;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaOrgMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaOrg;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.util.EvaStaticMethod;
import com.boco.eoms.eva.webapp.form.EvaKpiForm;
import com.boco.eoms.eva.webapp.form.EvaTemplateForm;
import com.boco.eoms.log4j.Logger;


public final class EvaTemplateAction extends BaseAction {

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return templateTree(mapping, form, request, response);
	}

	/**
	 * 考核模板树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward templateTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("templateTree");
	}

	/**
	 * 考核模板执行树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward templateTreeByExcute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("templateTreeByExcute");
	}
	/**
	 * 考核模板历史树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward templateTreeByHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("templateTreeByHistory");
	}	
	/**
	 * 考核模板下发树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward distributeTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("distributeTree");
	}
	
	/**
	 * 考核模板已处理树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dealtTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("dealtTree");
	}

	/**
	 * 考核模板已审核树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditedTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("auditedTree");
	}

	/**
	 * 考核模板审核树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("auditTree");
	}

	/**
	 * 生成模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public String getTemplateNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonRoot = new JSONArray();
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		// 所有模板
		List list = templateMgr.listTemplate();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			EvaTemplate template = (EvaTemplate) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", template.getId());
			jitem.put("text", template.getTemplateName());
			jitem.put("allowEdit", true);
			jitem.put("allowDistribute", true);
			jitem.put("allowDelete", true);
			jitem.put("leaf", true);
			jitem.put(UIConstants.JSON_ICONCLS, "file");
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}/

	/**
	 * 生成下发模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDistributeNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		IEvaKpiInstanceMgr instanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		ArrayList orgList = new ArrayList();
		// 下发给我
		List distributeList = orgMgr.getTempletByUserId(
				sessionform.getUserid(), EvaConstants.TEMPLATE_DISTRIBUTED,
				EvaConstants.TASK_STSTUS_ACTIVITIES);
		// 驳回给我
		List rejectList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				EvaConstants.TEMPLATE_AUDIT_REJECT,
				EvaConstants.TASK_STSTUS_ACTIVITIES);
		orgList.addAll(distributeList);
		orgList.addAll(rejectList);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			EvaOrg org = (EvaOrg) nodeIter.next();
			EvaTemplate template = new EvaTemplate();
			if (EvaConstants.TEMPLATE_DISTRIBUTED.equals(org.getActionType())) { // 下发状态的是任务，templateId存模板Id
				template = templateMgr.getTemplate(org.getTemplateId());
			} else { // 非下发状态的，templateId存任务Id
				EvaOrg taskOrg = orgMgr.getEvaOrg(org.getTemplateId());
				template = templateMgr.getTemplate(taskOrg.getTemplateId());
			}
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				if (EvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态的是任务，templateId存模板Id
					jitem.put("id", org.getId());
				} else { // 非下发状态的，templateId存任务Id
					EvaOrg taskOrg = orgMgr.getEvaOrg(org.getTemplateId());
					jitem.put("id", taskOrg.getId());
				}
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-eva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ EvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				// 根据模板的不同状态显示不同的菜单
				if (EvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态
					if (instanceMgr
							.isInstanceExistsInTime(
									org.getId(),
									DateTimeUtil
											.getCurrentDateTime(EvaConstants.DATE_FORMAT))) { // 如果此模板在本周期内的实例已生成
						jitem.put("allowFillInstance", true);
						jitem.put("allowSendToAudit", true);
					} else {
						jitem.put("allowGenInstance", true);
					}
				} else if (EvaConstants.TEMPLATE_AUDIT_WAIT.equals(org
						.getActionType())
						|| EvaConstants.TEMPLATE_REPORTED.equals(org
								.getActionType())) { // 等待审核或审核通过（已上报）状态
					jitem.put("allowViewInstance", true);
				} else if (EvaConstants.TEMPLATE_AUDIT_REJECT.equals(org
						.getActionType())) { // 审核驳回状态
					jitem.put("allowFillInstance", true);
					jitem.put("allowSendToAudit", true);
				}
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "file");
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 生成已处理模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDealtNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 已处理列表（下发给我但是状态是非激活的）
		List dealtList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				EvaConstants.TEMPLATE_DISTRIBUTED,
				EvaConstants.TASK_STSTUS_INACTIVE);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = dealtList.iterator(); nodeIter.hasNext();) {
			EvaOrg org = (EvaOrg) nodeIter.next();
			EvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				// 此处提供任务Id
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-eva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ EvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				jitem.put("allowViewInstance", true);
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "file");
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 生成待审核模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getNodesWaitAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 提交给我审核
		orgList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				EvaConstants.TEMPLATE_AUDIT_WAIT,
				EvaConstants.TASK_STSTUS_ACTIVITIES);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			EvaOrg org = (EvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			EvaOrg oldOrg = orgMgr.getEvaOrg(org.getTemplateId());
			EvaTemplate template = templateMgr.getTemplate(oldOrg
					.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-eva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ EvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				jitem.put("allowAudit", true);
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "file");
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 生成已审核模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getNodesAudited(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 由我审核通过或驳回（提交给我审核，但状态是已处理的，可能有重复的任务）
		List auditedList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				EvaConstants.TEMPLATE_AUDIT_WAIT,
				EvaConstants.TASK_STSTUS_INACTIVE);
		// 遍历审核列表，去掉重复任务
		HashMap hashMap = new HashMap();
		for (Iterator it = auditedList.iterator(); it.hasNext();) {
			EvaOrg auditedTask = (EvaOrg) it.next();
			if (null == hashMap.get(auditedTask.getTemplateId())) {
				// 获得下发的任务
				EvaOrg task = orgMgr.getEvaOrg(auditedTask.getTemplateId());
				orgList.add(task);
				hashMap.put(task.getId(), task);
			}
		}

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			EvaOrg org = (EvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			// EvaOrg oldOrg = orgMgr.getEvaOrg(org.getTemplateId());
			EvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-eva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ EvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				jitem.put("allowViewAudit", true);
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "file");
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	
	/**
	 * 新建考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String parentNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		if (parentNodeId == null || "".equals(parentNodeId)) {
			parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 所属地市
		JSONArray jsonRoot = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put(UIConstants.JSON_ID, sessionform.getDeptid());
		jitem.put(UIConstants.JSON_NAME, sessionform.getDeptname());
		jsonRoot.put(jitem);
		// 复制需要的信息 add:wangsixuan 2009-2-5
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		List ttList = treeMgr.listNextLevelNode("1",
				EvaConstants.NODETYPE_TEMPLATETYPE);
		request.setAttribute("ttList", ttList);
		String templateTypeId = StaticMethod.null2String(request
				.getParameter("templateTypeId"));
		List tList = new ArrayList();
		if (templateTypeId != null && !"".equals(templateTypeId)) {
			tList = treeMgr.listNextLevelNode(templateTypeId,
					EvaConstants.NODETYPE_TEMPLATE);
		}
		request.setAttribute("tList", tList);
		request.setAttribute("templateTypeId", templateTypeId);

		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parentNodeId", parentNodeId);
		return mapping.findForward("newTemplate");
	}

	/**
	 * 修改考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EvaTemplate template = new EvaTemplate();
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			EvaTemplateForm templateForm = (EvaTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
			template = (EvaTemplate) convert(templateForm);
		} else if (nodeId.length()==4){
			return mapping.findForward("queryHistroy");
		} else if(nodeId.length()<9){
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
//			if("".equals(template.getCycle())||template.getCycle()==null){
//				List trees = treeMgr.getEvaKpiByNodeId(nodeId);
//				int order = 4;
//				String[] orderValue = {"month","quarter","halfYear","year","times"};
//				Map map = new HashMap();
//				map.put("month", 0);
//				map.put("quarter", 1);
//				map.put("halfYear", 2);
//				map.put("year", 3);
//				map.put("times", 4);
//				String cycleValue ="";
//				for(int i=0;trees.size()>i;i++){
//					cycleValue = (String)trees.get(i);
//					if((Integer)map.get(cycleValue)<order){
//						order = (Integer)map.get(cycleValue);
//					} 
//				}
//				if(order==0){
//					order = 1;
//				}
//				String cycle = orderValue[order];
//				template.setCycle(cycle);
//			}
			EvaTemplateForm templateForm = (EvaTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
		} else {
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
			EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			String kpiId = StaticMethod.null2String(tplNode.getObjectId());
			EvaKpi kpi = new EvaKpi();
			Float maxWt = EvaConstants.DEFAULT_MAX_WT;
			Float minWt = EvaConstants.DEFAULT_MIN_WT;
			kpi = kpiMgr.getKpi(kpiId,"0");
			kpi.setWeight(tplNode.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			minWt = kpiMgr.getMinWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			EvaKpiForm evaKpiForm = (EvaKpiForm) convert(kpi);
//			evaKpiForm.setEvaStartTime(kpi.getEvaStartTimeStr());
//			evaKpiForm.setEvaEndTime(kpi.getEvaEndTimeStr());
			updateFormBean(mapping, request, evaKpiForm);
			request.setAttribute("evaKpiForm", evaKpiForm);
			return mapping.findForward("templateKpi");			
		}
		
		// 所属地市，目前仅为部门，可扩展为角色和用户
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		JSONArray jsonRoot = new JSONArray();
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			EvaTask task = (EvaTask) taskIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, task.getOrgId());
			jitem.put(UIConstants.JSON_NAME, EvaStaticMethod.orgId2Name(task
					.getOrgId(), task.getOrgType()));
			jsonRoot.put(jitem);
		}
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parentNodeId", nodeId);
		//页面控制需要的模板对应权值
//		HashMap evaTWHashMap = new HashMap();
//		evaTWHashMap.put(nodeId, templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
//		request.getSession().setAttribute("evaTWHashMap", evaTWHashMap);
		
		request.getSession().setAttribute("evaTW", templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
		return mapping.findForward("editTemplate");
	}

	/**
	 * 保存考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTemplate template = new EvaTemplate();
		EvaTemplateForm templateForm = (EvaTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (EvaTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			// template.setStartTime(EvaStaticMethod.getStartTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(EvaConstants.DATE_FORMAT)));
			// template.setEndTime(EvaStaticMethod.getEndTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(EvaConstants.DATE_FORMAT)));
			template.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(EvaConstants.UNDELETED);
			template.setProfessional(templateForm.getProfessional());
			// 所属地市
			String orgId = StaticMethod.null2String(request
					.getParameter("orgId"));
			if ("".equals(orgId)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgId.split(",");
			templateMgr
					.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			 template.setCycle(templateForm.getCycle());
			 template.setStartTime(templateForm.getStartTime());
			 template.setEndTime(templateForm.getEndTime());
			template.setRemark(templateForm.getRemark());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setProfessional(templateForm.getProfessional());
			// 所属地市
			String orgId = templateForm.getOrgId();
			template.setOrgId(orgId);
			template.setPartnerDept(templateForm.getPartnerDept());
			template.setPartnerDeptName(templateForm.getPartnerDeptName());
			if ("".equals(orgId)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgId.split(",");
			templateMgr.updateTemplate(template, parentNodeId,request, ids);
			request.setAttribute("nodeId", parentNodeId);
			
		}
		form = (EvaTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			IEvaTreeMgr treeMgr2 = (IEvaTreeMgr) getBean("IevaTreeMgr");
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");

			// 找到新增的节点ID-nodeIdNewSave
			EvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					EvaConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				EvaTree et = (EvaTree) treeKpiList.get(i);
				EvaKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(EvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				EvaTree evaTree = new EvaTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);

				// 保存树节点
				evaTree.setNodeId(newNodeId);
				evaTree.setParentNodeId(newParentNodeId);
				evaTree.setNodeName(ek.getKpiName());
				evaTree.setNodeType(EvaConstants.NODETYPE_KPI);
				evaTree.setLeaf(EvaConstants.NODE_LEAF);
				evaTree.setObjectId(ek.getId());
				evaTree.setWeight(ek.getWeight());
				treeMgr2.saveTreeNode(evaTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						EvaConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,EvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
			}
		}

		// 保存完成后返回模板编辑页面
		return mapping.findForward("success");
	}

	/**
	 * 删除考核模板（已激活则逻辑删除，仅从树图上删除；未激活则物理删除）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		EvaTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (EvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
			tplMgr.removeTplLogical(nodeId);
		} else {// 未激活则物理删除
			tplMgr.removeTplPhysical(nodeId);
		}
		return mapping.findForward("success");
	}

	/**
	 * 进入模板下发页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward distributePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 模板
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		String templateId = request.getParameter("nodeId");
		EvaTemplate template = templateMgr.getTemplate(templateId);
		form = (EvaTemplateForm) convert(template);
		updateFormBean(mapping, request, form);

		// 下发组织
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		List orgList = orgMgr.getOrgsByTempletId(templateId);

		request.setAttribute("orgIds", EvaStaticMethod.getOrgList(orgList));

		// 模板已经下发过
		if (orgList.iterator().hasNext()) {
			return viewTemplate(mapping, form, request, response);
		} else {
			return mapping.findForward("distribute");
		}
	}
	
	/**
	 * 查看模板详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		// 如果是action内部调用
		if (null == templateId || "".equals(templateId)) {
			templateId = ((EvaTemplateForm) form).getId();
		}
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTemplate template = templateMgr.getTemplate(templateId);
		EvaTemplateForm templateForm = (EvaTemplateForm) convert(template);
		updateFormBean(mapping, request, templateForm);
		return mapping.findForward("viewTemplate");
	}

	/**
	 * 模板查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("query");
	}

	/**
	 * 模板查询(histroy)页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryHistroy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("queryHistroy");
	}
	/**
	 * 模板查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaOrgMgr orgMgr = (IEvaOrgMgr) getBean("IevaOrgMgr");
		// EvaTemplateForm templateForm = (EvaTemplateForm) form;
		// String orgType = templateForm.getOrgType();
		List recieverOrgList = EvaStaticMethod.jsonOrg2Orgs(request
				.getParameter("recieverOrgId"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String actionType = request.getParameter("actionType");
		// 只查任务
		String conditions = " where org.actionType='"
				+ EvaConstants.TEMPLATE_DISTRIBUTED + "' ";
		// 如果有选择组织
		if (recieverOrgList.iterator().hasNext()) {
			conditions += " and org.orgId in(";
			for (Iterator orgIdIter = recieverOrgList.iterator(); orgIdIter
					.hasNext();) {
				EvaOrg recieverOrg = (EvaOrg) orgIdIter.next();
				conditions += "'" + recieverOrg.getOrgId() + "',";
			}
			if (conditions.endsWith(",")) {
				conditions = conditions.substring(0, conditions.length() - 1);
			}
			conditions += ")";
		}
		if (null != month && !"".equals(month)) {
			String date = year + "-" + month + "-" + "01";
			String startDateStr = EvaStaticMethod.getStartTimeByCycle(
					EvaConstants.CYCLE_MONTH, date);
			String endDateStr = EvaStaticMethod.getEndTimeByCycle(
					EvaConstants.CYCLE_MONTH, date);
			conditions += " and org.evaStartTime='" + startDateStr + "'";
			conditions += " and org.evaEndTime='" + endDateStr + "'";
		}
		if (null != actionType && !"".equals(actionType)) {
			conditions += " and org.id in(select neworg.templateId from EvaOrg neworg where neworg.actionType='"
					+ actionType
					+ "' and neworg.status='"
					+ EvaConstants.TASK_STSTUS_ACTIVITIES + "')";
		}

		List orgList = new ArrayList();
		List list = orgMgr.getTaskByConditions(conditions);
		for (Iterator orgIter = list.iterator(); orgIter.hasNext();) {
			EvaOrg org = (EvaOrg) orgIter.next();
			EvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			org.setOrgName(EvaStaticMethod.getOrgName(org.getOrgId(), org
					.getOrgType()));
			// 从XML字典中取状态名称
			String statusName = DictMgrLocator
					.getDictService()
					.itemId2name(
							"dict-eva"
									+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
									+ "templateStatus",
							orgMgr.getLatestTaskByTaskId(org.getId())
									.getActionType()).toString();
			org.setTemplateName(template.getTemplateName() + "(" + statusName
					+ ")");
			orgList.add(org);
		}
		request.setAttribute("orgIter", orgList.iterator());
		return mapping.findForward("list");
	}
	/**
	 * 模板查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xsearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		
		String tempTaskName = StaticMethod.null2String(request.getParameter("tempTaskName"));
		String partnerDept = StaticMethod.null2String(request.getParameter("partnerDept"));
		StringBuffer where = new StringBuffer();
		
		if(!"".equals(tempTaskName)){
			where.append(" and tem.templateName like '%");
			where.append(tempTaskName);
			where.append("%'");
		}
		if(!"".equals(partnerDept)){
			where.append(" and tem.partnerDept = '");
			where.append(partnerDept);
			where.append("'");
		}
		where.append(" and ( tem.activated='1' or tem.activated='2' )");
		List templateList = templateMgr.getTemplates(where.toString());
		request.setAttribute("templateList", templateList);
		return mapping.findForward("histroyList");
	}

	/**
	 * 从树图中删除模板（非物理删除，仅去掉树图中的显示）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delTemplateFromTree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		treeMgr.removeTreeNodeByNodeId(nodeId);
		return mapping.findForward("success");
	}

	/**
	 * 激活模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTemplateForm templateForm = (EvaTemplateForm) form;
		EvaTemplate tpl = templateMgr.getTemplate(templateForm.getId());
		tpl.setTemplateName(templateForm.getTemplateName());
		tpl.setCycle(templateForm.getCycle());
		tpl.setStartTime(templateForm.getStartTime());
		tpl.setEndTime(templateForm.getEndTime());
		tpl.setRemark(templateForm.getRemark());
		tpl.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
		tpl.setProfessional(templateForm.getProfessional());
		// 所属地市
		String orgId = templateForm.getOrgId();
		tpl.setOrgId(orgId);
		tpl.setPartnerDept(templateForm.getPartnerDept());
		tpl.setPartnerDeptName(templateForm.getPartnerDeptName());
		if ("".equals(orgId)) {
			// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
			return mapping.findForward("fail");
		}
		String[] ids = orgId.split(",");
		templateMgr.updateTemplate(tpl, parentNodeId,request, ids);
		if ("".equals(templateForm.getId()) || "".equals(parentNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (EvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (EvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + EvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateForm.getId(), parentNodeId);
		}
		tpl = templateMgr.getTemplate(tpl.getId());//激活后更新显示的模板信息
		form = (EvaTemplateForm) convert(tpl);
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}
	
	

	/**
	 * 工作协议生成模板(临时任务benweiwei)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward newTemplateFromAgree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrAgreementEvaMgr pnrAgreementEvaMgr = (IPnrAgreementEvaMgr) getBean("pnrAgreementEvaMgr");
		IPnrAgreementWorkMgr pnrAgreementWorkMgr = (IPnrAgreementWorkMgr) getBean("pnrAgreementWorkMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
//		临时工作
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		String remark = StaticMethod.null2String(request.getParameter("partner"));
		String agrwor = StaticMethod.null2String(request.getParameter("agrwor"));
		String evaDeptId = StaticMethod.null2String(request.getParameter("evaDeptId"));
		EvaTree evaDeptNode = treeMgr.getNodeByRemark(evaDeptId);
		String evaDeptNodeNodeId = evaDeptNode.getNodeId();
		EvaTree node = treeMgr.getNodeByRemark(remark);
		String parentNodeId = node.getNodeId();
		
		if("tempTask".equals(agrwor)){
//			临时工作
			List tempTaskList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(agreementId);
			PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
			EvaTemplateForm evaTemplateForm = new EvaTemplateForm();
			evaTemplateForm.setTemplateName(pnrTempTaskMain.getTempTaskName()+"-模板");
			evaTemplateForm.setAgreementId(pnrTempTaskMain.getId());
			evaTemplateForm.setAgrwor(agrwor);
			evaTemplateForm.setStartTime(StaticMethod.date2String(pnrTempTaskMain.getStartTime()));
			evaTemplateForm.setEndTime(StaticMethod.date2String(pnrTempTaskMain.getEndTime()));
			request.setAttribute("toOrgId",pnrTempTaskMain.getToOrgId());
			request.setAttribute("operateId",pnrTempTaskMain.getToEvaOrgId());
			request.setAttribute("evaTemplateForm", evaTemplateForm);
			request.setAttribute("tempTaskList", tempTaskList);
		}else{
			List agreementWorkList = pnrAgreementWorkMgr.getPnrAgreementWorks(agreementId);
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
			List agreementEvaList = pnrAgreementEvaMgr.getPnrAgreementEvas(agreementId);
			PnrAgreementEva pnrAgreementEva = null;
			for(int i = 0;i<agreementEvaList.size();i++){
				pnrAgreementEva = (PnrAgreementEva)agreementEvaList.get(i);
				pnrAgreementEva.setEvaName(pnrAgreementEva.getEvaName()+"(其他)");
			}			
			for(int i = 0;i<agreementWorkList.size();i++){
				PnrAgreementWork pnrAgreementWork = (PnrAgreementWork)agreementWorkList.get(i);
				pnrAgreementEva = new PnrAgreementEva();
				pnrAgreementEva.setEvaName(pnrAgreementWork.getWorkEvaName());
				pnrAgreementEva.setEvaWeight(pnrAgreementWork.getWorkEvaWeight());
				pnrAgreementEva.setEvaContent(pnrAgreementWork.getWorkEvaContent());
				pnrAgreementEva.setEvaStartTime(pnrAgreementWork.getWorkEvaStartTime());
				pnrAgreementEva.setEvaEndTime(pnrAgreementWork.getWorkEvaEndTime());
				pnrAgreementEva.setEvaCycle(pnrAgreementWork.getWorkEvaCycle());
				pnrAgreementEva.setEvaSource(pnrAgreementWork.getWorkType());
				pnrAgreementEva.setToEvaUser(pnrAgreementWork.getToOrgUser());
				pnrAgreementEva.setToEvaUserName(pnrAgreementWork.getToOrgUserName());
				pnrAgreementEva.setEvaAlgorithmType(pnrAgreementWork.getAlgorithmType());
				agreementEvaList.add(pnrAgreementEva);
			}

			EvaTemplateForm evaTemplateForm = new EvaTemplateForm();
			evaTemplateForm.setTemplateName(pnrAgreementMain.getAgreementName()+"-模板");
			evaTemplateForm.setAgreementId(pnrAgreementMain.getId());
			evaTemplateForm.setContractId(pnrAgreementMain.getContentId());
			evaTemplateForm.setStartTime(StaticMethod.date2String(pnrAgreementMain.getStartTime()));
			evaTemplateForm.setEndTime(StaticMethod.date2String(pnrAgreementMain.getEndTime()));
			evaTemplateForm.setAgrwor(agrwor);
			request.setAttribute("toOrgId",pnrAgreementMain.getPartyBUser());
			request.setAttribute("operateId",pnrAgreementMain.getPartyAUser());
			request.setAttribute("evaTemplateForm", evaTemplateForm);
			request.setAttribute("agreementEvaList", agreementEvaList);
			request.setAttribute("contractId", pnrAgreementMain.getContentId());
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		// 所属地市
		JSONArray jsonRoot = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put(UIConstants.JSON_ID, sessionform.getDeptid());
		jitem.put(UIConstants.JSON_NAME, sessionform.getDeptname());
		jsonRoot.put(jitem);
		
		request.setAttribute("agrwor", agrwor);
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("evaDeptNodeNodeId", evaDeptId);
		request.setAttribute("agreementId", agreementId);
		request.setAttribute("orgDeptId", remark);
		request.setAttribute("parentNodeId", parentNodeId);
		return mapping.findForward("newTemplateFromAgree");
	}
	*/

	/**
	 * 保存考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward saveTemplateFromAgree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		EvaTemplate template = new EvaTemplate();
		
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
		String parentNodeId = StaticMethod.null2String(request.getParameter("parentNodeId"));
		String templateName = StaticMethod.null2String(request.getParameter("templateName"));
		String professional = StaticMethod.null2String(request.getParameter("professional"));
		String contractId = StaticMethod.null2String(request.getParameter("contractId"));
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		String orgDeptId = StaticMethod.null2String(request.getParameter("orgDeptId"));
		String orgDeptName = mgr.id2Name(orgDeptId, "tawSystemDeptDao");
		String evaDeptNodeNodeId = StaticMethod.null2String(request.getParameter("evaDeptNodeNodeId"));
		String agrwor = StaticMethod.null2String(request.getParameter("agrwor"));

		String startTime = StaticMethod.null2String(request.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
//		合作伙伴id

		String[] kpiName = request.getParameterValues("kpiName");
		String[] weight = request.getParameterValues("weight");
		String[] algorithm = request.getParameterValues("algorithm");
		String[] kpiRemark = request.getParameterValues("kpiRemark");
		String[] evaSource = request.getParameterValues("evaSource");
		String[] evaStartTime = request.getParameterValues("evaStartTime");
		String[] evaEndTime = request.getParameterValues("evaEndTime");
		String[] cycle = request.getParameterValues("cycle");
		String[] algorithmType = request.getParameterValues("algorithmType");
		String[] toOrgUser = request.getParameterValues("toOrgUser");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setStartTime(startTime);
			template.setEndTime(endTime);
			template.setCreatorOrgId(sessionform.getDeptid());
			template.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(EvaConstants.UNDELETED);
			template.setProfessional(professional);
			template.setAgreementId(agreementId);
			template.setContractId(contractId);
			template.setTemplateName(templateName);
			template.setRemark(remark);
			template.setAgrwor(agrwor);
			template.setPartnerDept(orgDeptId);
			template.setPartnerDeptName(orgDeptName);
			template.setOrgId(evaDeptNodeNodeId);
			String[] ids = evaDeptNodeNodeId.split(",");
			// 所属地市
			
			templateMgr.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
			if(agrwor.equals("tempTask")){
				PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
				pnrTempTaskMain.setEvaTemplateId(template.getId());
				pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
			} else {
				//更新协议信息
				PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
				pnrAgreementMain.setEvaTemplateId(template.getId());
				pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);
			}

			
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			EvaTree templateTree =  treeMgr.getTreeNode(template.getBelongNode());
			for (int i = 0; i < kpiName.length; i++) {
				EvaKpi ek = new EvaKpi();
				ek.setKpiName(kpiName[i]);
				ek.setWeight(Float.valueOf(weight[i]));
				ek.setAlgorithm(algorithm[i]);
				ek.setRemark(kpiRemark[i]);
//				此处为新增字段---考核来源  
				ek.setEvaSource(evaSource[i]);
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(EvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());
				ek.setEvaEndTime(evaEndTime[i]);
				ek.setEvaStartTime(evaStartTime[i]);
				ek.setCycle(cycle[i]);
				ek.setAlgorithmType(algorithmType[i]);
				ek.setToOrgUser(toOrgUser[i]);
				kpiMgr.saveKpiAndNode(ek,templateTree.getNodeId(),null);
			}

		// 保存完成后返回模板编辑页面
		return mapping.findForward("refreshParent");
	}
*/
	/**
	 * 关闭考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward closeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTemplate template = new EvaTemplate();
		String id = StaticMethod.null2String(request.getParameter("id"));
		template = templateMgr.getTemplate(id);
		template.setActivated(EvaConstants.TEMPLATE_CLOSED);
		templateMgr.saveTemplate(template);

		return mapping.findForward("success");
	}

	/**
	 * 保存考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward saveEvaTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
	
//		得到临时模板Id	，保存为正式模板
		String evaTemplateId = StaticMethod.null2String(request.getParameter("evaTemplateId"));
		EvaTemplateTemp evaTemplateTemp  = templateTempMgr.getEvaTemplateTemp(evaTemplateId);		
		EvaTemplate template = new EvaTemplate();

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			template.setCreator(evaTemplateTemp.getCreator());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setStartTime(evaTemplateTemp.getStartTime());
			template.setEndTime(evaTemplateTemp.getEndTime());
			template.setCreatorOrgId(evaTemplateTemp.getCreatorOrgId());
			template.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(EvaConstants.TEMPLATE_ACTIVATED);
			template.setDeleted(EvaConstants.UNDELETED);
			template.setProfessional(evaTemplateTemp.getProfessional());
			template.setAgreementId(evaTemplateTemp.getAgreementId());
			template.setContractId(evaTemplateTemp.getContractId());
			template.setTemplateName(evaTemplateTemp.getTemplateName());
			template.setRemark(evaTemplateTemp.getRemark());
			template.setAgrwor(evaTemplateTemp.getAgrwor());
			template.setPartnerDept(evaTemplateTemp.getPartnerDept());
			template.setOrgId(evaTemplateTemp.getOrgId());
			String[] ids = evaTemplateTemp.getCreatorOrgId().split(",");
//			此处取指标周期最小值作为模板的周期
			IEvaKpiTempMgr evaKpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
			List evaKpiList = evaKpiTempMgr.getEvaKpiTemps(evaTemplateId);			
			int order = 4;
			String[] orderValue = {"month","quarter","halfYear","year","times"};
			Map map = new HashMap();
			map.put("month", 0);
			map.put("quarter", 1);
			map.put("halfYear", 2);
			map.put("year", 3);
			map.put("times", 4);
			String cycleValue ="";
			for(int i=0;evaKpiList.size()>i;i++){
				EvaKpiTemp evaKpiTemp = (EvaKpiTemp)evaKpiList.get(i);
				cycleValue = (String)evaKpiTemp.getCycle();
				if((Integer)map.get(cycleValue)<order){
					order = (Integer)map.get(cycleValue);
				} 
			}
			String cycle = orderValue[order];
			template.setCycle(cycle);
				
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");			
			EvaTree node = treeMgr.getNodeByRemark(evaTemplateTemp.getPartnerDept());
			String parentNodeId = node.getNodeId();
			
			templateMgr.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
//			更新模板确认信息		
			IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
			String confirmId = StaticMethod.null2String(request.getParameter("confirmId"));
			String remarkConfirm = StaticMethod.null2String(request.getParameter("remarkConfirm"));
			EvaConfirm evaConfirm = evaConfirmMgr.getTemplateConfirmById(confirmId);
			
			evaConfirm.setState("2");
			evaConfirm.setOperateTime(StaticMethod.getLocalTime());
			evaConfirm.setRemark(remarkConfirm);
			evaConfirm.setConfirmResult("2");
			evaConfirmMgr.saveEvaConfirm(evaConfirm);
	
			if(evaTemplateTemp.getAgrwor().equals("tempTask")){
				//更新临时任务信息
				PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(evaTemplateTemp.getAgreementId());
				pnrTempTaskMain.setEvaTemplateId(template.getId());
				pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
			} else {
				//更新协议信息
				PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(evaTemplateTemp.getAgreementId());
				pnrAgreementMain.setEvaTemplateId(template.getId());
				pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);
			}

			EvaTree templateTree =  treeMgr.getTreeNode(template.getBelongNode());
//			保存指标信息
			EvaKpi ek = new EvaKpi();
			for (int i = 0; i < evaKpiList.size(); i++) {
				EvaKpiTemp evaKpiTemp  = (EvaKpiTemp)evaKpiList.get(i);
				ek = new EvaKpi();
				ek.setKpiName(evaKpiTemp.getKpiName());
				ek.setWeight(evaKpiTemp.getWeight());
				ek.setAlgorithm(evaKpiTemp.getAlgorithm());
				ek.setRemark(evaKpiTemp.getRemark());
//				此处为新增字段---考核来源  
				ek.setEvaSource(evaKpiTemp.getEvaSource());
				ek.setCreator(evaKpiTemp.getCreator());
				ek.setDeleted(EvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());
				ek.setEvaEndTime(evaKpiTemp.getEvaEndTime());
				ek.setEvaStartTime(evaKpiTemp.getEvaStartTime());
				ek.setCycle(evaKpiTemp.getCycle());
				ek.setAlgorithmType(evaKpiTemp.getAlgorithmType());
				ek.setToOrgUser(evaKpiTemp.getToOrgUser());
				ek.setAgreementWorkId(evaKpiTemp.getAgreementWorkId());
				kpiMgr.saveKpiAndNode(ek,templateTree.getNodeId(),null);
			}
			templateMgr.activeTemplate(template.getId(), templateTree.getNodeId());
		return mapping.findForward("refreshParent");
	}*/
	
}
