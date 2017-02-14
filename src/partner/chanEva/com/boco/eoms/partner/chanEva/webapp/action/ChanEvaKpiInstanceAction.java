package com.boco.eoms.partner.chanEva.webapp.action;

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
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaOrgMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;
import com.boco.eoms.partner.chanEva.util.DateTimeUtil;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.util.ChanEvaStaticMethod;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaKpiInstanceForm;

public final class ChanEvaKpiInstanceAction extends BaseAction {

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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
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
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		// 此模板本周期内的考核实例是否已经生成
		boolean flag = instanceMgr.isInstanceExistsInTime(orgId, DateTimeUtil
				.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
		if (!flag) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			instanceMgr.genKpiInstance(orgId, sessionform.getUserid());
		} else {
			request.setAttribute("errorInfo", "本周期内的考核任务已经生成！");
			IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
			ChanEvaTemplate template = templateMgr.getTemplate(templateId);
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
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		// 模板基本信息
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		org.setOrgName(ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		template.setTotalScore(instanceMgr.getTotalScoreOfTemplate(orgId,
				DateTimeUtil.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT)));
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		String orgId = request.getParameter("nodeId");
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		if (null == org.getId() || "".equals(org.getId())) {
			return mapping.findForward("fail");
		}
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("org", org);
		// 考核实例信息
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		// 任务
		String orgId = request.getParameter("nodeId");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		ChanEvaOrg org = orgMgr.getChanEvaOrg(orgId);
		// 模板
		ChanEvaTemplate template = templateMgr.getTemplate(org.getTemplateId());
		org.setOrgName(ChanEvaStaticMethod.getOrgName(org.getOrgId(), org
				.getOrgType()));
		request.setAttribute("org", org);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		ChanEvaOrg activeOrg = new ChanEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(org.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgId,
				DateTimeUtil.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		String templateId = request.getParameter("id");
		String orgId = request.getParameter("orgId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 获得模板下属指标列表
		// IChanEvaTemplateKpiMgr templateKpiMgr = (IChanEvaTemplateKpiMgr)
		// getBean("IchanEvaTemplateKpiMgr");
		// List kpiList = templateKpiMgr.listKpiOfTemplate(templateId);
		// for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
		// ChanEvaKpi kpi = (ChanEvaKpi) kpiIter.next();
		// // 从页面传递过来的指标分数，以指标id为name的参数
		// Float kpiScore = Float.valueOf(StaticMethod.null2String(request
		// .getParameter(kpi.getId())));
		// // 根据指标和任务id获得本周期的考核实例
		// ChanEvaKpiInstance instance = instanceMgr.getKpiInstance(orgId, kpi
		// .getId(), DateTimeUtil
		// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
		// if (null != instance.getId() && !"".equals(instance.getId())) {
		// instance.setKpiScore(kpiScore);
		// instance.setFillOperator(sessionform.getUserid());
		// instance.setFillTime(DateTimeUtil
		// .getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
		ChanEvaKpiInstanceForm kpiInstanceForm = (ChanEvaKpiInstanceForm) form;
		String orgId = request.getParameter("orgId");
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		//List orgs = ChanEvaStaticMethod.jsonOrg2Orgs(kpiInstanceForm.getRecieverOrgId());
		List orgs = null;
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		// 获得已下发的任务
		ChanEvaOrg orgTask = orgMgr.getChanEvaOrg(orgId);

		if (ChanEvaConstants.TASK_STSTUS_ACTIVITIES.equals(orgTask.getStatus())) {// 如果是新下发的送审
			orgTask.setStatus(ChanEvaConstants.TASK_STSTUS_INACTIVE);
			orgMgr.saveChanEvaOrg(orgTask);
		} else {// 如果是已驳回的送审
			String where = " where org.templateId='" + orgId
					+ "' and org.status = '"
					+ ChanEvaConstants.TASK_STSTUS_ACTIVITIES
					+ "' order by org.operateTime";
			List list = orgMgr.getTaskByConditions(where);
			if (list.iterator().hasNext()) {
				ChanEvaOrg orgOld = (ChanEvaOrg) list.get(0);
				orgOld.setStatus(ChanEvaConstants.TASK_STSTUS_INACTIVE);
				orgMgr.saveChanEvaOrg(orgOld);
			}
		}

		ChanEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		for (int i = 0; i < orgs.size(); i++) {
			ChanEvaOrg chanEvaOrg = (ChanEvaOrg) orgs.get(i);
			chanEvaOrg.setTemplateId(orgId);
			chanEvaOrg.setActionType(ChanEvaConstants.TEMPLATE_AUDIT_WAIT);
			chanEvaOrg.setStatus(ChanEvaConstants.TASK_STSTUS_ACTIVITIES);
			chanEvaOrg.setOperateTime(new Date());
			chanEvaOrg.setChanEvaStartTime(template.getStartTime());
			chanEvaOrg.setChanEvaEndTime(template.getEndTime());
			orgMgr.saveChanEvaOrg(chanEvaOrg);
		}
		return mapping.findForward("success");
	}
}
