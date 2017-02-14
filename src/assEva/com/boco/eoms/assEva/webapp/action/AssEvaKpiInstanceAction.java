package com.boco.eoms.assEva.webapp.action;

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
import com.boco.eoms.assEva.mgr.IAssEvaKpiInstanceMgr;
import com.boco.eoms.assEva.mgr.IAssEvaOrgMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.model.AssEvaOrg;
import com.boco.eoms.assEva.model.AssEvaTemplate;
import com.boco.eoms.assEva.util.DateTimeUtil;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.util.AssEvaStaticMethod;
import com.boco.eoms.assEva.webapp.form.AssEvaKpiInstanceForm;

public final class AssEvaKpiInstanceAction extends BaseAction {

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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(AssEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
			AssEvaTemplate template = templateMgr.getTemplate(templateId);
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
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		// 模板基本信息
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(AssEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(AssEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		AssEvaOrg org = orgMgr.getAssEvaOrg(orgId);
		// 模板
		AssEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(AssEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		AssEvaOrg activeOrg = new AssEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// IAssEvaTemplateKpiMgr templateKpiMgr = (IAssEvaTemplateKpiMgr)
		// getBean("IassEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// AssEvaKpi kpi = (AssEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// AssEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
		AssEvaKpiInstanceForm kpiInstanceForm = (AssEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = AssEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		// 获得已下发的任务
		AssEvaOrg orgTask = orgMgr.getAssEvaOrg(orgId);

		if (AssEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(AssEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveAssEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ AssEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				AssEvaOrg orgOld = (AssEvaOrg) list.get(0);
				orgOld.setStatus(AssEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveAssEvaOrg(orgOld);
			}
		}

		AssEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			AssEvaOrg assEvaOrg = (AssEvaOrg) orgs.get(i);
			assEvaOrg.setTemplateId(orgId);
			assEvaOrg.setActionType(AssEvaConstants.TEMPLATE_AUDIT_WAIT);
			assEvaOrg.setStatus(AssEvaConstants.TASK_STSTUS_ACTIVITIES);
			assEvaOrg.setOperateTime(new Date());
			assEvaOrg.setAssEvaStartTime(template.getStartTime());
			assEvaOrg.setAssEvaEndTime(template.getEndTime());
			orgMgr.saveAssEvaOrg(assEvaOrg);
		}
		return mapping.findForward("success");
	}
}
