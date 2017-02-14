package com.boco.eoms.partner.lineEva.webapp.action;

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
import com.boco.eoms.partner.lineEva.webapp.form.LineEvaAuditForm;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaAuditInfoMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiInstanceMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaOrgMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;
import com.boco.eoms.partner.lineEva.util.DateTimeUtil;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.partner.lineEva.util.LineEvaStaticMethod;
import com.boco.eoms.partner.lineEva.model.LineEvaAuditInfo;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;

public final class LineEvaAuditAction extends BaseAction {

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
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");

		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		LineEvaOrg orgOld = orgMgr.getLineEvaOrg(templateId);
		// 获取下发的任务
		LineEvaOrg orgTask = orgMgr.getLineEvaOrg(orgOld.getTemplateId());
		// 模板
		LineEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(LineEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		LineEvaOrg activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ILineEvaKpiInstanceMgr instanceMgr = (ILineEvaKpiInstanceMgr) getBean("IlineEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(LineEvaConstants.DATE_FORMAT));
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
		LineEvaAuditForm lineEvaAuditForm = (LineEvaAuditForm) form;
		ILineEvaAuditInfoMgr auditInfoMgr = (ILineEvaAuditInfoMgr) getBean("IlineEvaAuditInfoMgr");
		ILineEvaOrgMgr orgMgr = (ILineEvaOrgMgr) getBean("IlineEvaOrgMgr");
		ILineEvaTemplateMgr templateMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		LineEvaOrg orgOld = orgMgr.getLineEvaOrg(lineEvaAuditForm.getId());
		orgOld.setStatus(LineEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveLineEvaOrg(orgOld); 
		// 获取处理下发的组织
		LineEvaOrg orgUp = orgMgr.getLineEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		LineEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		LineEvaOrg orgNew = new LineEvaOrg();
		if (lineEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(LineEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(LineEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setLineEvaStartTime(template.getStartTime());
			orgNew.setLineEvaEndTime(template.getEndTime());
			orgMgr.saveLineEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(LineEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(LineEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setLineEvaStartTime(template.getStartTime());
			orgNew.setLineEvaEndTime(template.getEndTime());
			orgMgr.saveLineEvaOrg(orgNew);
		}
		// 增加审批信息
		LineEvaAuditInfo lineEvaAuditInfo = new LineEvaAuditInfo();
		lineEvaAuditInfo.setAuditInfo(lineEvaAuditForm.getAuditInfo());
		lineEvaAuditInfo.setTemplateId(orgUp.getId());
		lineEvaAuditInfo.setAuditTime(new Date());
		lineEvaAuditInfo.setAuditUser(sessionform.getUserid());
		lineEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveLineEvaAuditInfo(lineEvaAuditInfo);
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
				LineEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		ILineEvaAuditInfoMgr auditInfoMgr = (ILineEvaAuditInfoMgr) getBean("IlineEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(LineEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
