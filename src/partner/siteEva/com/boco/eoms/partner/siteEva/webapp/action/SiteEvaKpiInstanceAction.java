package com.boco.eoms.partner.siteEva.webapp.action;

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
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaOrgMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.util.DateTimeUtil;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.util.SiteEvaStaticMethod;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaKpiInstanceForm;

public final class SiteEvaKpiInstanceAction extends BaseAction {

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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
			SiteEvaTemplate template = templateMgr.getTemplate(templateId);
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
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		// 模板基本信息
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
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
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		SiteEvaOrg org = orgMgr.getSiteEvaOrg(orgId);
		// 模板
		SiteEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(SiteEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		SiteEvaOrg activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
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
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// ISiteEvaTemplateKpiMgr templateKpiMgr = (ISiteEvaTemplateKpiMgr)
		// getBean("IsiteEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// SiteEvaKpi kpi = (SiteEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// SiteEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
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
		SiteEvaKpiInstanceForm kpiInstanceForm = (SiteEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = SiteEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		// 获得已下发的任务
		SiteEvaOrg orgTask = orgMgr.getSiteEvaOrg(orgId);

		if (SiteEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(SiteEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveSiteEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ SiteEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				SiteEvaOrg orgOld = (SiteEvaOrg) list.get(0);
				orgOld.setStatus(SiteEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveSiteEvaOrg(orgOld);
			}
		}

		SiteEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			SiteEvaOrg siteEvaOrg = (SiteEvaOrg) orgs.get(i);
			siteEvaOrg.setTemplateId(orgId);
			siteEvaOrg.setActionType(SiteEvaConstants.TEMPLATE_AUDIT_WAIT);
			siteEvaOrg.setStatus(SiteEvaConstants.TASK_STSTUS_ACTIVITIES);
			siteEvaOrg.setOperateTime(new Date());
			siteEvaOrg.setSiteEvaStartTime(template.getStartTime());
			siteEvaOrg.setSiteEvaEndTime(template.getEndTime());
			orgMgr.saveSiteEvaOrg(siteEvaOrg);
		}
		return mapping.findForward("success");
	}
}
