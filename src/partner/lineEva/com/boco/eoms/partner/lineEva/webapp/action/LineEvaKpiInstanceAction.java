package com.boco.eoms.partner.lineEva.webapp.action;

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
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiInstanceMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaOrgMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;
import com.boco.eoms.partner.lineEva.util.DateTimeUtil;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.partner.lineEva.util.LineEvaStaticMethod;
import com.boco.eoms.partner.lineEva.webapp.form.LineEvaKpiInstanceForm;

public final class LineEvaKpiInstanceAction extends BaseAction {

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
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(LineEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
			LineEvaTemplate template = templateMgr.getTemplate(templateId);
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
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		// 模板基本信息
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(LineEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(LineEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		LineEvaOrg org = orgMgr.getLineEvaOrg(orgId);
		// 模板
		LineEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(LineEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		LineEvaOrg activeOrg = new LineEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// ILineEvaTemplateKpiMgr templateKpiMgr = (ILineEvaTemplateKpiMgr)
		// getBean("IlineEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// LineEvaKpi kpi = (LineEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// LineEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
		LineEvaKpiInstanceForm kpiInstanceForm = (LineEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = LineEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		// 获得已下发的任务
		LineEvaOrg orgTask = orgMgr.getLineEvaOrg(orgId);

		if (LineEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(LineEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveLineEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ LineEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				LineEvaOrg orgOld = (LineEvaOrg) list.get(0);
				orgOld.setStatus(LineEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveLineEvaOrg(orgOld);
			}
		}

		LineEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			LineEvaOrg lineEvaOrg = (LineEvaOrg) orgs.get(i);
			lineEvaOrg.setTemplateId(orgId);
			lineEvaOrg.setActionType(LineEvaConstants.TEMPLATE_AUDIT_WAIT);
			lineEvaOrg.setStatus(LineEvaConstants.TASK_STSTUS_ACTIVITIES);
			lineEvaOrg.setOperateTime(new Date());
			lineEvaOrg.setLineEvaStartTime(template.getStartTime());
			lineEvaOrg.setLineEvaEndTime(template.getEndTime());
			orgMgr.saveLineEvaOrg(lineEvaOrg);
		}
		return mapping.findForward("success");
	}
}
