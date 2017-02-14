package com.boco.eoms.partner.chanEva.webapp.action;

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
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaOrgMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;
import com.boco.eoms.partner.chanEva.model.ChanEvaTask;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.DateTimeUtil;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.util.ChanEvaStaticMethod;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaTemplateForm;
import com.boco.eoms.log4j.Logger;

public final class ChanEvaTemplateAction extends BaseAction {

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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		// 所有模板
		List list = templateMgr.listTemplate();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			ChanEvaTemplate template = (ChanEvaTemplate) nodeIter.next();
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		ArrayList orgList = new ArrayList();
		// 下发给我
		List distributeList = orgMgr.getTempletByUserId(
				sessionform.getUserid(), ChanEvaConstants.TEMPLATE_DISTRIBUTED,
				ChanEvaConstants.TASK_STSTUS_ACTIVITIES);
		// 驳回给我
		List rejectList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				ChanEvaConstants.TEMPLATE_AUDIT_REJECT,
				ChanEvaConstants.TASK_STSTUS_ACTIVITIES);
		orgList.addAll(distributeList);
		orgList.addAll(rejectList);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			ChanEvaOrg org = (ChanEvaOrg) nodeIter.next();
			ChanEvaTemplate template = new ChanEvaTemplate();
			if (ChanEvaConstants.TEMPLATE_DISTRIBUTED.equals(org.getActionType())) { // 下发状态的是任务，templateId存模板Id
				template = templateMgr.getTemplate(org.getTemplateId());
			} else { // 非下发状态的，templateId存任务Id
				ChanEvaOrg taskOrg = orgMgr.getChanEvaOrg(org.getTemplateId());
				template = templateMgr.getTemplate(taskOrg.getTemplateId());
			}
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				if (ChanEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态的是任务，templateId存模板Id
					jitem.put("id", org.getId());
				} else { // 非下发状态的，templateId存任务Id
					ChanEvaOrg taskOrg = orgMgr.getChanEvaOrg(org.getTemplateId());
					jitem.put("id", taskOrg.getId());
				}
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-chanEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
								.getOrgType()) + "-" + statusName + ")");
				// 根据模板的不同状态显示不同的菜单
				if (ChanEvaConstants.TEMPLATE_DISTRIBUTED.equals(org
						.getActionType())) { // 下发状态
					if (instanceMgr
							.isInstanceExistsInTime(
									org.getId(),
									DateTimeUtil
											.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT))) { // 如果此模板在本周期内的实例已生成
						jitem.put("allowFillInstance", true);
						jitem.put("allowSendToAudit", true);
					} else {
						jitem.put("allowGenInstance", true);
					}
				} else if (ChanEvaConstants.TEMPLATE_AUDIT_WAIT.equals(org
						.getActionType())
						|| ChanEvaConstants.TEMPLATE_REPORTED.equals(org
								.getActionType())) { // 等待审核或审核通过（已上报）状态
					jitem.put("allowViewInstance", true);
				} else if (ChanEvaConstants.TEMPLATE_AUDIT_REJECT.equals(org
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 已处理列表（下发给我但是状态是非激活的）
		List dealtList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				ChanEvaConstants.TEMPLATE_DISTRIBUTED,
				ChanEvaConstants.TASK_STSTUS_INACTIVE);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = dealtList.iterator(); nodeIter.hasNext();) {
			ChanEvaOrg org = (ChanEvaOrg) nodeIter.next();
			ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				// 此处提供任务Id
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-chanEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 提交给我审核
		orgList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				ChanEvaConstants.TEMPLATE_AUDIT_WAIT,
				ChanEvaConstants.TASK_STSTUS_ACTIVITIES);

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			ChanEvaOrg org = (ChanEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			ChanEvaOrg oldOrg = orgMgr.getChanEvaOrg(org.getTemplateId());
			ChanEvaTemplate template = templateMgr.getTemplate(oldOrg
					.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-chanEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus", org.getActionType())
						.toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		List orgList = new ArrayList();
		// 由我审核通过或驳回（提交给我审核，但状态是已处理的，可能有重复的任务）
		List auditedList = orgMgr.getTempletByUserId(sessionform.getUserid(),
				ChanEvaConstants.TEMPLATE_AUDIT_WAIT,
				ChanEvaConstants.TASK_STSTUS_INACTIVE);
		// 遍历审核列表，去掉重复任务
		HashMap hashMap = new HashMap();
		for (Iterator it = auditedList.iterator(); it.hasNext();) {
			ChanEvaOrg auditedTask = (ChanEvaOrg) it.next();
			if (null == hashMap.get(auditedTask.getTemplateId())) {
				// 获得下发的任务
				ChanEvaOrg task = orgMgr.getChanEvaOrg(auditedTask.getTemplateId());
				orgList.add(task);
				hashMap.put(task.getId(), task);
			}
		}

		JSONArray jsonRoot = new JSONArray();
		for (Iterator nodeIter = orgList.iterator(); nodeIter.hasNext();) {
			ChanEvaOrg org = (ChanEvaOrg) nodeIter.next();
			// 到达待审核状态时，org中记录的是上一个状态（下发）的org主键
			// ChanEvaOrg oldOrg = orgMgr.getChanEvaOrg(org.getTemplateId());
			ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			// 如果模板存在
			if (null != template.getId() && !"".equals(template.getId())) {
				JSONObject jitem = new JSONObject();
				jitem.put("id", org.getId());
				// 从XML字典中取状态名称
				String statusName = DictMgrLocator
						.getDictService()
						.itemId2name(
								"dict-chanEva"
										+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
										+ "templateStatus",
								orgMgr.getLatestTaskByTaskId(org.getId())
										.getActionType()).toString();
				jitem.put("text", template.getTemplateName()
						+ "("
						+ ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		List ttList = treeMgr.listNextLevelNode("1",
				ChanEvaConstants.NODETYPE_TEMPLATETYPE);
		request.setAttribute("ttList", ttList);
		String templateTypeId = StaticMethod.null2String(request
				.getParameter("templateTypeId"));
		List tList = new ArrayList();
		if (templateTypeId != null && !"".equals(templateTypeId)) {
			tList = treeMgr.listNextLevelNode(templateTypeId,
					ChanEvaConstants.NODETYPE_TEMPLATE);
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
		ChanEvaTemplate template = new ChanEvaTemplate();
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			ChanEvaTemplateForm templateForm = (ChanEvaTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
			template = (ChanEvaTemplate) convert(templateForm);
		} else {
			IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
			ChanEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
			ChanEvaTemplateForm templateForm = (ChanEvaTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
		}
		
		// 所属地市，目前仅为部门，可扩展为角色和用户
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		JSONArray jsonRoot = new JSONArray();
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			ChanEvaTask task = (ChanEvaTask) taskIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, task.getOrgId());
			jitem.put(UIConstants.JSON_NAME, ChanEvaStaticMethod.orgId2Name(task
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
			jitem1.put(UIConstants.JSON_NAME, ChanEvaStaticMethod.orgId2Name(parIdss[i], "dept"));
			jsonRoot1.put(jitem1);
		}
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parIds", jsonRoot1.toString());
		request.setAttribute("parentNodeId", nodeId);
		//页面控制需要的模板对应权值
//		HashMap chanEvaTWHashMap = new HashMap();
//		chanEvaTWHashMap.put(nodeId, templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
//		request.getSession().setAttribute("chanEvaTWHashMap", chanEvaTWHashMap);
		
		request.getSession().setAttribute("chanEvaTW", templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTemplate template = new ChanEvaTemplate();
		ChanEvaTemplateForm templateForm = (ChanEvaTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (ChanEvaTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			// template.setStartTime(ChanEvaStaticMethod.getStartTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT)));
			// template.setEndTime(ChanEvaStaticMethod.getEndTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT)));
			template.setOrgType(ChanEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(ChanEvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(ChanEvaConstants.UNDELETED);
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
			IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			template.setCycle(templateForm.getCycle());
			// template.setStartTime(ChanEvaStaticMethod.getStartTimeByCycle(
			// templateForm.getCycle(), DateTimeUtil
			// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT)));
			// template.setEndTime(ChanEvaStaticMethod.getEndTimeByCycle(templateForm
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT)));
			template.setRemark(templateForm.getRemark());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(ChanEvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
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
		form = (ChanEvaTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			IChanEvaTreeMgr treeMgr2 = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
			IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");

			// 找到新增的节点ID-nodeIdNewSave
			ChanEvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					ChanEvaConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				ChanEvaTree et = (ChanEvaTree) treeKpiList.get(i);
				ChanEvaKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(ChanEvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				ChanEvaTree chanEvaTree = new ChanEvaTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);

				// 保存树节点
				chanEvaTree.setNodeId(newNodeId);
				chanEvaTree.setParentNodeId(newParentNodeId);
				chanEvaTree.setNodeName(ek.getKpiName());
				chanEvaTree.setNodeType(ChanEvaConstants.NODETYPE_KPI);
				chanEvaTree.setLeaf(ChanEvaConstants.NODE_LEAF);
				chanEvaTree.setObjectId(ek.getId());
				chanEvaTree.setWeight(ek.getWeight());
				treeMgr2.saveTreeNode(chanEvaTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						ChanEvaConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,ChanEvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
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
		IChanEvaTemplateMgr tplMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		ChanEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		ChanEvaTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (ChanEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		String templateId = request.getParameter("nodeId");
		ChanEvaTemplate template = templateMgr.getTemplate(templateId);
		form = (ChanEvaTemplateForm) convert(template);
		updateFormBean(mapping, request, form);

		// 下发组织
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		List orgList = orgMgr.getOrgsByTempletId(templateId);

		request.setAttribute("orgIds", ChanEvaStaticMethod.getOrgList(orgList));

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
			templateId = ((ChanEvaTemplateForm) form).getId();
		}
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTemplate template = templateMgr.getTemplate(templateId);
		ChanEvaTemplateForm templateForm = (ChanEvaTemplateForm) convert(template);
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		// ChanEvaTemplateForm templateForm = (ChanEvaTemplateForm) form;
		// String orgType = templateForm.getOrgType();
		List recieverOrgList = ChanEvaStaticMethod.jsonOrg2Orgs(request
				.getParameter("recieverOrgId"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String actionType = request.getParameter("actionType");
		// 只查任务
		String conditions = " where org.actionType='"
				+ ChanEvaConstants.TEMPLATE_DISTRIBUTED + "' ";
		// 如果有选择组织
		if (recieverOrgList.iterator().hasNext()) {
			conditions += " and org.orgId in(";
			for (Iterator orgIdIter = recieverOrgList.iterator(); orgIdIter
					.hasNext();) {
				ChanEvaOrg recieverOrg = (ChanEvaOrg) orgIdIter.next();
				conditions += "'" + recieverOrg.getOrgId() + "',";
			}
			if (conditions.endsWith(",")) {
				conditions = conditions.substring(0, conditions.length() - 1);
			}
			conditions += ")";
		}
		if (null != month && !"".equals(month)) {
			String date = year + "-" + month + "-" + "01";
			String startDateStr = ChanEvaStaticMethod.getStartTimeByCycle(
					ChanEvaConstants.CYCLE_MONTH, date);
			String endDateStr = ChanEvaStaticMethod.getEndTimeByCycle(
					ChanEvaConstants.CYCLE_MONTH, date);
			conditions += " and org.chanEvaStartTime='" + startDateStr + "'";
			conditions += " and org.chanEvaEndTime='" + endDateStr + "'";
		}
		if (null != actionType && !"".equals(actionType)) {
			conditions += " and org.id in(select neworg.templateId from ChanEvaOrg neworg where neworg.actionType='"
					+ actionType
					+ "' and neworg.status='"
					+ ChanEvaConstants.TASK_STSTUS_ACTIVITIES + "')";
		}

		List orgList = new ArrayList();
		List list = orgMgr.getTaskByConditions(conditions);
		for (Iterator orgIter = list.iterator(); orgIter.hasNext();) {
			ChanEvaOrg org = (ChanEvaOrg) orgIter.next();
			ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
			org.setOrgName(ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
					.getOrgType()));
			// 从XML字典中取状态名称
			String statusName = DictMgrLocator
					.getDictService()
					.itemId2name(
							"dict-chanEva"
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
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTemplate tpl = templateMgr.getTemplate(templateId);
		if ("".equals(templateId) || "".equals(tplNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (ChanEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (ChanEvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + ChanEvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateId, tplNodeId);
		}
		form = (ChanEvaTemplateForm) convert(tpl);
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}
}
