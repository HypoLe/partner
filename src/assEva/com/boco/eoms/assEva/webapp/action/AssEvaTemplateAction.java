package com.boco.eoms.assEva.webapp.action;

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
import com.boco.eoms.assEva.mgr.IAssEvaKpiInstanceMgr;
import com.boco.eoms.assEva.mgr.IAssEvaKpiMgr;
import com.boco.eoms.assEva.mgr.IAssEvaOrgMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTaskMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaKpi;
import com.boco.eoms.assEva.model.AssEvaOrg;
import com.boco.eoms.assEva.model.AssEvaTask;
import com.boco.eoms.assEva.model.AssEvaTemplate;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.DateTimeUtil;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.util.AssEvaStaticMethod;
import com.boco.eoms.assEva.webapp.form.AssEvaTemplateForm;
import com.boco.eoms.log4j.Logger;

public final class AssEvaTemplateAction extends BaseAction {

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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		// 所有模板
		List list = templateMgr.listTemplate();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssEvaTemplate template = (AssEvaTemplate) nodeIter.next();
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		ArrayList orgList = new ArrayList();
		// 下发给我
		List distributeList = orgMgr.getTempletByUserId(
				sessionform.getUserid(), AssEvaConstants.TEMPLATE_DISTRIBUTED,
				AssEvaConstants.TASK_STSTUS_ACTIVITIES);
		// 驳回给我
		List rejectList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				AssEvaConstants.TEMPLATE_AUDIT_REJECT,
				AssEvaConstants.TASK_STSTUS_ACTIVITIES);
		orgList.addAll(distributeList);
		orgList.addAll(rejectList);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			AssEvaOrg org = (AssEvaOrg) nodeIter.next();
			AssEvaTemplate template = new AssEvaTemplate();
			if (AssEvaConstants.TEMPLATE_DISTRIBUTED.equals(org.getActionType())) { // 下发状态的是任务，templateId存模板Id
				template = templateMgr.getTemplate(org.getTemplateId());
			} else { // 非下发状态的，templateId存任务Id
				AssEvaOrg taskOrg = orgMgr.getAssEvaOrg(org.getTemplateId());
				template = templateMgr.getTemplate(taskOrg.getTemplateId());
			}
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				if (AssEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态的是任务，templateId存模板Id
					jitem.put("id", org.getId());
				} else { // 非下发状态的，templateId存任务Id
					AssEvaOrg taskOrg = orgMgr.getAssEvaOrg(org.getTemplateId());
					jitem.put("id", taskOrg.getId());
				}
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-assEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ AssEvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				// 根据模板的不同状态显示不同的菜单
				if (AssEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态
					if (instanceMgr
							.isInstanceExistsInTime(
									org.getId(),
									DateTimeUtil
											.getCurrentDateTime(AssEvaConstants.DATE_FORMAT))) { // 如果此模板在本周期内的实例已生成
						jitem.put("allowFillInstance", true);
						jitem.put("allowSendToAudit", true);
					} else {
						jitem.put("allowGenInstance", true);
					}
				} else if (AssEvaConstants.TEMPLATE_AUDIT_WAIT.equals(org
						.getActionType())
						|| AssEvaConstants.TEMPLATE_REPORTED.equals(org
								.getActionType())) { // 等待审核或审核通过（已上报）状态
					jitem.put("allowViewInstance", true);
				} else if (AssEvaConstants.TEMPLATE_AUDIT_REJECT.equals(org
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 已处理列表（下发给我但是状态是非激活的）
		List dealtList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				AssEvaConstants.TEMPLATE_DISTRIBUTED,
				AssEvaConstants.TASK_STSTUS_INACTIVE);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = dealtList.iterator(); nodeIter.hasNext();) {
			AssEvaOrg org = (AssEvaOrg) nodeIter.next();
			AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				// 此处提供任务Id
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-assEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ AssEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 提交给我审核
		orgList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				AssEvaConstants.TEMPLATE_AUDIT_WAIT,
				AssEvaConstants.TASK_STSTUS_ACTIVITIES);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			AssEvaOrg org = (AssEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			AssEvaOrg oldOrg = orgMgr.getAssEvaOrg(org.getTemplateId());
			AssEvaTemplate template = templateMgr.getTemplate(oldOrg
					.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-assEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ AssEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 由我审核通过或驳回（提交给我审核，但状态是已处理的，可能有重复的任务）
		List auditedList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				AssEvaConstants.TEMPLATE_AUDIT_WAIT,
				AssEvaConstants.TASK_STSTUS_INACTIVE);
		// 遍历审核列表，去掉重复任务
		HashMap hashMap = new HashMap();
		for (Iterator it = auditedList.iterator(); it.hasNext();) {
			AssEvaOrg auditedTask = (AssEvaOrg) it.next();
			if (null == hashMap.get(auditedTask.getTemplateId())) {
				// 获得下发的任务
				AssEvaOrg task = orgMgr.getAssEvaOrg(auditedTask.getTemplateId());
				orgList.add(task);
				hashMap.put(task.getId(), task);
			}
		}

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			AssEvaOrg org = (AssEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			// AssEvaOrg oldOrg = orgMgr.getAssEvaOrg(org.getTemplateId());
			AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-assEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ AssEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		jitem.put(UIConstants.JSON_NAME, "中国移动黑龙江公司");
		jsonRoot.put(jitem);
		
		// 合作伙伴
		JSONArray jsonRoot1 = new JSONArray();
		JSONObject jitem1 = new JSONObject();
		jitem1.put(UIConstants.JSON_ID, "");
		jitem1.put(UIConstants.JSON_NAME, "");
		jsonRoot1.put(jitem1);

		// 复制需要的信息 add:wangsixuan 2009-2-5
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		List ttList = treeMgr.listNextLevelNode("1",
				AssEvaConstants.NODETYPE_TEMPLATETYPE);
		request.setAttribute("ttList", ttList);
		String templateTypeId = StaticMethod.null2String(request
				.getParameter("templateTypeId"));
		List tList = new ArrayList();
		if (templateTypeId != null && !"".equals(templateTypeId)) {
			tList = treeMgr.listNextLevelNode(templateTypeId,
					AssEvaConstants.NODETYPE_TEMPLATE);
		}
		request.setAttribute("tList", tList);
		request.setAttribute("templateTypeId", templateTypeId);

		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parIds", jsonRoot1.toString());
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
		AssEvaTemplate template = new AssEvaTemplate();
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			AssEvaTemplateForm templateForm = (AssEvaTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
			template = (AssEvaTemplate) convert(templateForm);
		} else {
			IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
			AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
			AssEvaTemplateForm templateForm = (AssEvaTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
		}
		
		// 所属地市，目前仅为部门，可扩展为角色和用户
		IAssEvaTaskMgr taskMgr = (IAssEvaTaskMgr) getBean("IassEvaTaskMgr");
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		JSONArray jsonRoot = new JSONArray();
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			AssEvaTask task = (AssEvaTask) taskIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, task.getOrgId());
			jitem.put(UIConstants.JSON_NAME, AssEvaStaticMethod.orgId2Name(task
					.getOrgId(), task.getOrgType()));
			jsonRoot.put(jitem);
		}
		
		String parIds = template.getParIds();
		String[] parIdss = parIds.split(",");
		JSONArray jsonRoot1 = new JSONArray();
		JSONObject jitem1 =  new JSONObject();
		for(int i = 0;parIdss.length>i;i++){
			jitem1 =  new JSONObject();
			jitem1.put(UIConstants.JSON_ID, parIdss[i]);
			jitem1.put(UIConstants.JSON_NAME, AssEvaStaticMethod.orgId2Name(parIdss[i], "dept"));
			jsonRoot1.put(jitem1);
		}
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parIds", jsonRoot1.toString());
		request.setAttribute("parentNodeId", nodeId);
		//页面控制需要的模板对应权值
//		HashMap assEvaTWHashMap = new HashMap();
//		assEvaTWHashMap.put(nodeId, templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
//		request.getSession().setAttribute("assEvaTWHashMap", assEvaTWHashMap);
		
		request.getSession().setAttribute("assEvaTW", templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		AssEvaTemplate template = new AssEvaTemplate();
		AssEvaTemplateForm templateForm = (AssEvaTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (AssEvaTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			// template.setStartTime(AssEvaStaticMethod.getStartTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT)));
			// template.setEndTime(AssEvaStaticMethod.getEndTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT)));
			template.setOrgType(AssEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(AssEvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(AssEvaConstants.UNDELETED);
			template.setProfessional(templateForm.getProfessional());
			
			// 需要考核的合作伙伴
			String parIds = StaticMethod.null2String(request
					.getParameter("parIds"));
			template.setParIds(parIds);
			// 所属地市
			String orgIds = StaticMethod.null2String(request
					.getParameter("orgIds"));
			if ("".equals(orgIds)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgIds.split(",");
			templateMgr
					.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
			IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			template.setCycle(templateForm.getCycle());
			// template.setStartTime(AssEvaStaticMethod.getStartTimeByCycle(
			// templateForm.getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT)));
			// template.setEndTime(AssEvaStaticMethod.getEndTimeByCycle(templateForm
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT)));
			template.setRemark(templateForm.getRemark());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(AssEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setProfessional(templateForm.getProfessional());
			
			// 需要考核的合作伙伴
			String parIds = StaticMethod.null2String(request
					.getParameter("parIds"));
			template.setParIds(parIds);
			
			// 所属地市
			String orgIds = StaticMethod.null2String(request
					.getParameter("orgIds"));
			if ("".equals(orgIds)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgIds.split(",");
			templateMgr.updateTemplate(template, parentNodeId,request, ids);
			request.setAttribute("nodeId", parentNodeId);
			
		}
		form = (AssEvaTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			IAssEvaTreeMgr treeMgr2 = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
			IAssEvaKpiMgr kpiMgr = (IAssEvaKpiMgr) getBean("IassEvaKpiMgr");

			// 找到新增的节点ID-nodeIdNewSave
			AssEvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					AssEvaConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				AssEvaTree et = (AssEvaTree) treeKpiList.get(i);
				AssEvaKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(AssEvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				AssEvaTree assEvaTree = new AssEvaTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);

				// 保存树节点
				assEvaTree.setNodeId(newNodeId);
				assEvaTree.setParentNodeId(newParentNodeId);
				assEvaTree.setNodeName(ek.getKpiName());
				assEvaTree.setNodeType(AssEvaConstants.NODETYPE_KPI);
				assEvaTree.setLeaf(AssEvaConstants.NODE_LEAF);
				assEvaTree.setObjectId(ek.getId());
				assEvaTree.setWeight(ek.getWeight());
				treeMgr2.saveTreeNode(assEvaTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						AssEvaConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,AssEvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
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
		IAssEvaTemplateMgr tplMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		AssEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		AssEvaTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (AssEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		String templateId = request.getParameter("nodeId");
		AssEvaTemplate template = templateMgr.getTemplate(templateId);
		form = (AssEvaTemplateForm) convert(template);
		updateFormBean(mapping, request, form);

		// 下发组织
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		List orgList = orgMgr.getOrgsByTempletId(templateId);

		request.setAttribute("orgIds", AssEvaStaticMethod.getOrgList(orgList));

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
			templateId = ((AssEvaTemplateForm) form).getId();
		}
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		AssEvaTemplate template = templateMgr.getTemplate(templateId);
		AssEvaTemplateForm templateForm = (AssEvaTemplateForm) convert(template);
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		// AssEvaTemplateForm templateForm = (AssEvaTemplateForm) form;
		// String orgType = templateForm.getOrgType();
		List recieverOrgList = AssEvaStaticMethod.jsonOrg2Orgs(request
				.getParameter("recieverOrgId"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String actionType = request.getParameter("actionType");
		// 只查任务
		String conditions = " where org.actionType='"
				+ AssEvaConstants.TEMPLATE_DISTRIBUTED + "' ";
		// 如果有选择组织
		if (recieverOrgList.iterator().hasNext()) {
			conditions += " and org.orgId in(";
			for (Iterator orgIdIter = recieverOrgList.iterator(); orgIdIter
					.hasNext();) {
				AssEvaOrg recieverOrg = (AssEvaOrg) orgIdIter.next();
				conditions += "'" + recieverOrg.getOrgId() + "',";
			}
			if (conditions.endsWith(",")) {
				conditions = conditions.substring(0, conditions.length() - 1);
			}
			conditions += ")";
		}
		if (null != month && !"".equals(month)) {
			String date = year + "-" + month + "-" + "01";
			String startDateStr = AssEvaStaticMethod.getStartTimeByCycle(
					AssEvaConstants.CYCLE_MONTH, date);
			String endDateStr = AssEvaStaticMethod.getEndTimeByCycle(
					AssEvaConstants.CYCLE_MONTH, date);
			conditions += " and org.assEvaStartTime='" + startDateStr + "'";
			conditions += " and org.assEvaEndTime='" + endDateStr + "'";
		}
		if (null != actionType && !"".equals(actionType)) {
			conditions += " and org.id in(select neworg.templateId from AssEvaOrg neworg where neworg.actionType='"
					+ actionType
					+ "' and neworg.status='"
					+ AssEvaConstants.TASK_STSTUS_ACTIVITIES + "')";
		}

		List orgList = new ArrayList();
		List list = orgMgr.getTaskByConditions(conditions);
		for (Iterator orgIter = list.iterator(); orgIter.hasNext();) {
			AssEvaOrg org = (AssEvaOrg) orgIter.next();
			AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			org.setOrgName(AssEvaStaticMethod.getOrgName(org.getOrgId(), org
					.getOrgType()));
			// 从XML字典中取状态名称
			String statusName = DictMgrLocator
					.getDictService()
					.itemId2name(
							"dict-assEva"
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
		IAssEvaTreeMgr treeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
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
		String templateId = StaticMethod.null2String(request
				.getParameter("templateId"));
		String tplNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		AssEvaTemplate tpl = templateMgr.getTemplate(templateId);
		if ("".equals(templateId) || "".equals(tplNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (AssEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (AssEvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + AssEvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateId, tplNodeId);
		}
		form = (AssEvaTemplateForm) convert(tpl);
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}
}
