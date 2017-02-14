package com.boco.eoms.partner.siteEva.webapp.action;

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
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaOrgMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.DateTimeUtil;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.util.SiteEvaStaticMethod;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaTemplateForm;
import com.boco.eoms.log4j.Logger;

public final class SiteEvaTemplateAction extends BaseAction {

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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		// 所有模板
		List list = templateMgr.listTemplate();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			SiteEvaTemplate template = (SiteEvaTemplate) nodeIter.next();
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		ArrayList orgList = new ArrayList();
		// 下发给我
		List distributeList = orgMgr.getTempletByUserId(
				sessionform.getUserid(), SiteEvaConstants.TEMPLATE_DISTRIBUTED,
				SiteEvaConstants.TASK_STSTUS_ACTIVITIES);
		// 驳回给我
		List rejectList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				SiteEvaConstants.TEMPLATE_AUDIT_REJECT,
				SiteEvaConstants.TASK_STSTUS_ACTIVITIES);
		orgList.addAll(distributeList);
		orgList.addAll(rejectList);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			SiteEvaOrg org = (SiteEvaOrg) nodeIter.next();
			SiteEvaTemplate template = new SiteEvaTemplate();
			if (SiteEvaConstants.TEMPLATE_DISTRIBUTED.equals(org.getActionType())) { // 下发状态的是任务，templateId存模板Id
				template = templateMgr.getTemplate(org.getTemplateId());
			} else { // 非下发状态的，templateId存任务Id
				SiteEvaOrg taskOrg = orgMgr.getSiteEvaOrg(org.getTemplateId());
				template = templateMgr.getTemplate(taskOrg.getTemplateId());
			}
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				if (SiteEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态的是任务，templateId存模板Id
					jitem.put("id", org.getId());
				} else { // 非下发状态的，templateId存任务Id
					SiteEvaOrg taskOrg = orgMgr.getSiteEvaOrg(org.getTemplateId());
					jitem.put("id", taskOrg.getId());
				}
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-siteEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				// 根据模板的不同状态显示不同的菜单
				if (SiteEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态
					if (instanceMgr
							.isInstanceExistsInTime(
									org.getId(),
									DateTimeUtil
											.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT))) { // 如果此模板在本周期内的实例已生成
						jitem.put("allowFillInstance", true);
						jitem.put("allowSendToAudit", true);
					} else {
						jitem.put("allowGenInstance", true);
					}
				} else if (SiteEvaConstants.TEMPLATE_AUDIT_WAIT.equals(org
						.getActionType())
						|| SiteEvaConstants.TEMPLATE_REPORTED.equals(org
								.getActionType())) { // 等待审核或审核通过（已上报）状态
					jitem.put("allowViewInstance", true);
				} else if (SiteEvaConstants.TEMPLATE_AUDIT_REJECT.equals(org
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 已处理列表（下发给我但是状态是非激活的）
		List dealtList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				SiteEvaConstants.TEMPLATE_DISTRIBUTED,
				SiteEvaConstants.TASK_STSTUS_INACTIVE);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = dealtList.iterator(); nodeIter.hasNext();) {
			SiteEvaOrg org = (SiteEvaOrg) nodeIter.next();
			SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				// 此处提供任务Id
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-siteEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 提交给我审核
		orgList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				SiteEvaConstants.TEMPLATE_AUDIT_WAIT,
				SiteEvaConstants.TASK_STSTUS_ACTIVITIES);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			SiteEvaOrg org = (SiteEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			SiteEvaOrg oldOrg = orgMgr.getSiteEvaOrg(org.getTemplateId());
			SiteEvaTemplate template = templateMgr.getTemplate(oldOrg
					.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-siteEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 由我审核通过或驳回（提交给我审核，但状态是已处理的，可能有重复的任务）
		List auditedList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				SiteEvaConstants.TEMPLATE_AUDIT_WAIT,
				SiteEvaConstants.TASK_STSTUS_INACTIVE);
		// 遍历审核列表，去掉重复任务
		HashMap hashMap = new HashMap();
		for (Iterator it = auditedList.iterator(); it.hasNext();) {
			SiteEvaOrg auditedTask = (SiteEvaOrg) it.next();
			if (null == hashMap.get(auditedTask.getTemplateId())) {
				// 获得下发的任务
				SiteEvaOrg task = orgMgr.getSiteEvaOrg(auditedTask.getTemplateId());
				orgList.add(task);
				hashMap.put(task.getId(), task);
			}
		}

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			SiteEvaOrg org = (SiteEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			// SiteEvaOrg oldOrg = orgMgr.getSiteEvaOrg(org.getTemplateId());
			SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-siteEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		List ttList = treeMgr.listNextLevelNode("1",
				SiteEvaConstants.NODETYPE_TEMPLATETYPE);
		request.setAttribute("ttList", ttList);
		String templateTypeId = StaticMethod.null2String(request
				.getParameter("templateTypeId"));
		List tList = new ArrayList();
		if (templateTypeId != null && !"".equals(templateTypeId)) {
			tList = treeMgr.listNextLevelNode(templateTypeId,
					SiteEvaConstants.NODETYPE_TEMPLATE);
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
		SiteEvaTemplate template = new SiteEvaTemplate();
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			SiteEvaTemplateForm templateForm = (SiteEvaTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
			template = (SiteEvaTemplate) convert(templateForm);
		} else {
			ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
			SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
			SiteEvaTemplateForm templateForm = (SiteEvaTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
		}
		
		// 所属地市，目前仅为部门，可扩展为角色和用户
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		JSONArray jsonRoot = new JSONArray();
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			SiteEvaTask task = (SiteEvaTask) taskIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, task.getOrgId());
			jitem.put(UIConstants.JSON_NAME, SiteEvaStaticMethod.orgId2Name(task
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
			jitem1.put(UIConstants.JSON_NAME, SiteEvaStaticMethod.orgId2Name(parIdss[i], "dept"));
			jsonRoot1.put(jitem1);
		}
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parIds", jsonRoot1.toString());
		request.setAttribute("parentNodeId", nodeId);
		//页面控制需要的模板对应权值
//		HashMap siteEvaTWHashMap = new HashMap();
//		siteEvaTWHashMap.put(nodeId, templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
//		request.getSession().setAttribute("siteEvaTWHashMap", siteEvaTWHashMap);
		
		request.getSession().setAttribute("siteEvaTW", templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		SiteEvaTemplate template = new SiteEvaTemplate();
		SiteEvaTemplateForm templateForm = (SiteEvaTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (SiteEvaTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			// template.setStartTime(SiteEvaStaticMethod.getStartTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT)));
			// template.setEndTime(SiteEvaStaticMethod.getEndTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT)));
			template.setOrgType(SiteEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(SiteEvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(SiteEvaConstants.UNDELETED);
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
			ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			template.setCycle(templateForm.getCycle());
			// template.setStartTime(SiteEvaStaticMethod.getStartTimeByCycle(
			// templateForm.getCycle(), DateTimeUtil
			// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT)));
			// template.setEndTime(SiteEvaStaticMethod.getEndTimeByCycle(templateForm
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT)));
			template.setRemark(templateForm.getRemark());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(SiteEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
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
		form = (SiteEvaTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			ISiteEvaTreeMgr treeMgr2 = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
			ISiteEvaKpiMgr kpiMgr = (ISiteEvaKpiMgr) getBean("IsiteEvaKpiMgr");

			// 找到新增的节点ID-nodeIdNewSave
			SiteEvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					SiteEvaConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				SiteEvaTree et = (SiteEvaTree) treeKpiList.get(i);
				SiteEvaKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(SiteEvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				SiteEvaTree siteEvaTree = new SiteEvaTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);

				// 保存树节点
				siteEvaTree.setNodeId(newNodeId);
				siteEvaTree.setParentNodeId(newParentNodeId);
				siteEvaTree.setNodeName(ek.getKpiName());
				siteEvaTree.setNodeType(SiteEvaConstants.NODETYPE_KPI);
				siteEvaTree.setLeaf(SiteEvaConstants.NODE_LEAF);
				siteEvaTree.setObjectId(ek.getId());
				siteEvaTree.setWeight(ek.getWeight());
				treeMgr2.saveTreeNode(siteEvaTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						SiteEvaConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,SiteEvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
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
		ISiteEvaTemplateMgr tplMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		SiteEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		SiteEvaTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (SiteEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		String templateId = request.getParameter("nodeId");
		SiteEvaTemplate template = templateMgr.getTemplate(templateId);
		form = (SiteEvaTemplateForm) convert(template);
		updateFormBean(mapping, request, form);

		// 下发组织
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		List orgList = orgMgr.getOrgsByTempletId(templateId);

		request.setAttribute("orgIds", SiteEvaStaticMethod.getOrgList(orgList));

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
			templateId = ((SiteEvaTemplateForm) form).getId();
		}
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		SiteEvaTemplate template = templateMgr.getTemplate(templateId);
		SiteEvaTemplateForm templateForm = (SiteEvaTemplateForm) convert(template);
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		// SiteEvaTemplateForm templateForm = (SiteEvaTemplateForm) form;
		// String orgType = templateForm.getOrgType();
		List recieverOrgList = SiteEvaStaticMethod.jsonOrg2Orgs(request
				.getParameter("recieverOrgId"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String actionType = request.getParameter("actionType");
		// 只查任务
		String conditions = " where org.actionType='"
				+ SiteEvaConstants.TEMPLATE_DISTRIBUTED + "' ";
		// 如果有选择组织
		if (recieverOrgList.iterator().hasNext()) {
			conditions += " and org.orgId in(";
			for (Iterator orgIdIter = recieverOrgList.iterator(); orgIdIter
					.hasNext();) {
				SiteEvaOrg recieverOrg = (SiteEvaOrg) orgIdIter.next();
				conditions += "'" + recieverOrg.getOrgId() + "',";
			}
			if (conditions.endsWith(",")) {
				conditions = conditions.substring(0, conditions.length() - 1);
			}
			conditions += ")";
		}
		if (null != month && !"".equals(month)) {
			String date = year + "-" + month + "-" + "01";
			String startDateStr = SiteEvaStaticMethod.getStartTimeByCycle(
					SiteEvaConstants.CYCLE_MONTH, date);
			String endDateStr = SiteEvaStaticMethod.getEndTimeByCycle(
					SiteEvaConstants.CYCLE_MONTH, date);
			conditions += " and org.siteEvaStartTime='" + startDateStr + "'";
			conditions += " and org.siteEvaEndTime='" + endDateStr + "'";
		}
		if (null != actionType && !"".equals(actionType)) {
			conditions += " and org.id in(select neworg.templateId from SiteEvaOrg neworg where neworg.actionType='"
					+ actionType
					+ "' and neworg.status='"
					+ SiteEvaConstants.TASK_STSTUS_ACTIVITIES + "')";
		}

		List orgList = new ArrayList();
		List list = orgMgr.getTaskByConditions(conditions);
		for (Iterator orgIter = list.iterator(); orgIter.hasNext();) {
			SiteEvaOrg org = (SiteEvaOrg) orgIter.next();
			SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			org.setOrgName(SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
					.getOrgType()));
			// 从XML字典中取状态名称
			String statusName = DictMgrLocator
					.getDictService()
					.itemId2name(
							"dict-siteEva"
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
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		SiteEvaTemplate tpl = templateMgr.getTemplate(templateId);
		if ("".equals(templateId) || "".equals(tplNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (SiteEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (SiteEvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + SiteEvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateId, tplNodeId);
		}
		form = (SiteEvaTemplateForm) convert(tpl);
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}
}
