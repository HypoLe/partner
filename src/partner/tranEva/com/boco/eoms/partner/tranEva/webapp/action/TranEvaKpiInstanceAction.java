package com.boco.eoms.partner.tranEva.webapp.action;

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
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiInstanceMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaOrgMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTemplateMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;
import com.boco.eoms.partner.tranEva.model.TranEvaTemplate;
import com.boco.eoms.partner.tranEva.util.DateTimeUtil;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.partner.tranEva.util.TranEvaStaticMethod;
import com.boco.eoms.partner.tranEva.webapp.form.TranEvaKpiInstanceForm;

public final class TranEvaKpiInstanceAction extends BaseAction {

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
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(TranEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
			TranEvaTemplate template = templateMgr.getTemplate(templateId);
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
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		// 模板基本信息
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(TranEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(TranEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		TranEvaOrg org = orgMgr.getTranEvaOrg(orgId);
		// 模板
		TranEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(TranEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		TranEvaOrg activeOrg = new TranEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// ITranEvaTemplateKpiMgr templateKpiMgr = (ITranEvaTemplateKpiMgr)
		// getBean("ItranEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// TranEvaKpi kpi = (TranEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// TranEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
		TranEvaKpiInstanceForm kpiInstanceForm = (TranEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = TranEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		// 获得已下发的任务
		TranEvaOrg orgTask = orgMgr.getTranEvaOrg(orgId);

		if (TranEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(TranEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveTranEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ TranEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				TranEvaOrg orgOld = (TranEvaOrg) list.get(0);
				orgOld.setStatus(TranEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveTranEvaOrg(orgOld);
			}
		}

		TranEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			TranEvaOrg tranEvaOrg = (TranEvaOrg) orgs.get(i);
			tranEvaOrg.setTemplateId(orgId);
			tranEvaOrg.setActionType(TranEvaConstants.TEMPLATE_AUDIT_WAIT);
			tranEvaOrg.setStatus(TranEvaConstants.TASK_STSTUS_ACTIVITIES);
			tranEvaOrg.setOperateTime(new Date());
			tranEvaOrg.setTranEvaStartTime(template.getStartTime());
			tranEvaOrg.setTranEvaEndTime(template.getEndTime());
			orgMgr.saveTranEvaOrg(tranEvaOrg);
		}
		return mapping.findForward("success");
	}
}
