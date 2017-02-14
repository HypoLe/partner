package com.boco.eoms.partner.assiEva.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiInstanceMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaOrgMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTemplateMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;
import com.boco.eoms.partner.assiEva.model.AssiEvaTemplate;
import com.boco.eoms.partner.assiEva.util.DateTimeUtil;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.partner.assiEva.util.AssiEvaStaticMethod;
import com.boco.eoms.partner.assiEva.webapp.form.AssiEvaKpiInstanceForm;

public final class AssiEvaKpiInstanceAction extends BaseAction {

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	/**
	 * 考核实例生成页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward genInstancePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(AssiEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		return mapping.findForward("genInstancePage");
	}

	/**
	 * 生成考核实例
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward genInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
			AssiEvaTemplate template = templateMgr.getTemplate(templateId);
			request.setAttribute("template", template);
			return mapping.findForward("generated");
		}
		return mapping.findForward("success");
	}

	/**
	 * 考核实例填写页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fillInstancePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		// 模板基本信息
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(AssiEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		request.setAttribute("kpiList", kpiList);
		return mapping.findForward("fillInstancePage");
	}

	/**
	 * 考核实例送审页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendToAuditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 模板基本信息
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		request.setAttribute("kpiList", kpiList);
		return mapping.findForward("sendToAuditPage");
	}

	/**
	 * 考核实例查看页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		AssiEvaOrg org = orgMgr.getAssiEvaOrg(orgId);
		// 模板
		AssiEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(AssiEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		AssiEvaOrg activeOrg = new AssiEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		request.setAttribute("kpiList", kpiList);
		return mapping.findForward("viewInstance");
	}

	/**
	 * 保存考核实例
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// IAssiEvaTemplateKpiMgr templateKpiMgr = (IAssiEvaTemplateKpiMgr)
		// getBean("IassiEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssiEvaKpi kpi = (AssiEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// AssiEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
		// instanceMgr.saveKpiInstance(instance);
		// }
		// }
		return mapping.findForward("success");
	}

	/**
	 * 提交审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendToAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssiEvaKpiInstanceForm kpiInstanceForm = (AssiEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = AssiEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		// 获得已下发的任务
		AssiEvaOrg orgTask = orgMgr.getAssiEvaOrg(orgId);

		if (AssiEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(AssiEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveAssiEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ AssiEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				AssiEvaOrg orgOld = (AssiEvaOrg) list.get(0);
				orgOld.setStatus(AssiEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveAssiEvaOrg(orgOld);
			}
		}

		AssiEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			AssiEvaOrg assiEvaOrg = (AssiEvaOrg) orgs.get(i);
			assiEvaOrg.setTemplateId(orgId);
			assiEvaOrg.setActionType(AssiEvaConstants.TEMPLATE_AUDIT_WAIT);
			assiEvaOrg.setStatus(AssiEvaConstants.TASK_STSTUS_ACTIVITIES);
			assiEvaOrg.setOperateTime(new Date());
			assiEvaOrg.setAssiEvaStartTime(template.getStartTime());
			assiEvaOrg.setAssiEvaEndTime(template.getEndTime());
			orgMgr.saveAssiEvaOrg(assiEvaOrg);
		}
		return mapping.findForward("success");
	}
}
