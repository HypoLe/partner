package com.boco.eoms.partner.siteEva.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaAuditForm;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaAuditInfoMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaOrgMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;
import com.boco.eoms.partner.siteEva.util.DateTimeUtil;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.util.SiteEvaStaticMethod;
import com.boco.eoms.partner.siteEva.model.SiteEvaAuditInfo;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;

public final class SiteEvaAuditAction extends BaseAction {

	/**
	 * 初始化审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.nullObject2String(request
				.getParameter("templateId"));
		request.setAttribute("templateId", templateId);

		// 审核页面加入模板和考核结果
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");

		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		SiteEvaOrg orgOld = orgMgr.getSiteEvaOrg(templateId);
		// 获取下发的任务
		SiteEvaOrg orgTask = orgMgr.getSiteEvaOrg(orgOld.getTemplateId());
		// 模板
		SiteEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(SiteEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		SiteEvaOrg activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ISiteEvaKpiInstanceMgr instanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(SiteEvaConstants.DATE_FORMAT));
		request.setAttribute("kpiList", kpiList);

		return mapping.findForward("auditPage");
	}

	/**
	 * 审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
//		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		SiteEvaAuditForm siteEvaAuditForm = (SiteEvaAuditForm) form;
		ISiteEvaAuditInfoMgr auditInfoMgr = (ISiteEvaAuditInfoMgr) getBean("IsiteEvaAuditInfoMgr");
		ISiteEvaOrgMgr orgMgr = (ISiteEvaOrgMgr) getBean("IsiteEvaOrgMgr");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		SiteEvaOrg orgOld = orgMgr.getSiteEvaOrg(siteEvaAuditForm.getId());
		orgOld.setStatus(SiteEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveSiteEvaOrg(orgOld);
		// 获取处理下发的组织
		SiteEvaOrg orgUp = orgMgr.getSiteEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		SiteEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		SiteEvaOrg orgNew = new SiteEvaOrg();
		if (siteEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(SiteEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(SiteEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setSiteEvaStartTime(template.getStartTime());
			orgNew.setSiteEvaEndTime(template.getEndTime());
			orgMgr.saveSiteEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(SiteEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(SiteEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setSiteEvaStartTime(template.getStartTime());
			orgNew.setSiteEvaEndTime(template.getEndTime());
			orgMgr.saveSiteEvaOrg(orgNew);
		}
		// 增加审批信息
		SiteEvaAuditInfo siteEvaAuditInfo = new SiteEvaAuditInfo();
		siteEvaAuditInfo.setAuditInfo(siteEvaAuditForm.getAuditInfo());
		siteEvaAuditInfo.setTemplateId(orgUp.getId());
		siteEvaAuditInfo.setAuditTime(new Date());
		siteEvaAuditInfo.setAuditUser(sessionform.getUserid());
		siteEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveSiteEvaAuditInfo(siteEvaAuditInfo);
		return mapping.findForward("success");
	}

	/**
	 * 审核意见列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditInfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.nullObject2String(request
				.getParameter("nodeId"));
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		ISiteEvaAuditInfoMgr auditInfoMgr = (ISiteEvaAuditInfoMgr) getBean("IsiteEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(SiteEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
