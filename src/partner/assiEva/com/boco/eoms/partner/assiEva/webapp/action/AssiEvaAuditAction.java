package com.boco.eoms.partner.assiEva.webapp.action;

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
import com.boco.eoms.partner.assiEva.webapp.form.AssiEvaAuditForm;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaAuditInfoMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiInstanceMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaOrgMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;
import com.boco.eoms.partner.assiEva.util.DateTimeUtil;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.partner.assiEva.util.AssiEvaStaticMethod;
import com.boco.eoms.partner.assiEva.model.AssiEvaAuditInfo;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.assiEva.model.AssiEvaTemplate;

public final class AssiEvaAuditAction extends BaseAction {

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
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");

		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		AssiEvaOrg orgOld = orgMgr.getAssiEvaOrg(templateId);
		// 获取下发的任务
		AssiEvaOrg orgTask = orgMgr.getAssiEvaOrg(orgOld.getTemplateId());
		// 模板
		AssiEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(AssiEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		AssiEvaOrg activeOrg = new AssiEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IAssiEvaKpiInstanceMgr instanceMgr = (IAssiEvaKpiInstanceMgr) getBean("IassiEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(AssiEvaConstants.DATE_FORMAT));
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
		SimpleDateFormat simpleDFmt = new SimpleDateFormat("yyyy-MM-dd");
		AssiEvaAuditForm assiEvaAuditForm = (AssiEvaAuditForm) form;
		IAssiEvaAuditInfoMgr auditInfoMgr = (IAssiEvaAuditInfoMgr) getBean("IassiEvaAuditInfoMgr");
		IAssiEvaOrgMgr orgMgr = (IAssiEvaOrgMgr) getBean("IassiEvaOrgMgr");
		IAssiEvaTemplateMgr templateMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		AssiEvaOrg orgOld = orgMgr.getAssiEvaOrg(assiEvaAuditForm.getId());
		orgOld.setStatus(AssiEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveAssiEvaOrg(orgOld);
		// 获取处理下发的组织
		AssiEvaOrg orgUp = orgMgr.getAssiEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		AssiEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		AssiEvaOrg orgNew = new AssiEvaOrg();
		if (assiEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(AssiEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(AssiEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setAssiEvaStartTime(template.getStartTime());
			orgNew.setAssiEvaEndTime(template.getEndTime());
			orgMgr.saveAssiEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(AssiEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(AssiEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setAssiEvaStartTime(template.getStartTime());
			orgNew.setAssiEvaEndTime(template.getEndTime());
			orgMgr.saveAssiEvaOrg(orgNew);
		}
		// 增加审批信息
		AssiEvaAuditInfo assiEvaAuditInfo = new AssiEvaAuditInfo();
		assiEvaAuditInfo.setAuditInfo(assiEvaAuditForm.getAuditInfo());
		assiEvaAuditInfo.setTemplateId(orgUp.getId());
		assiEvaAuditInfo.setAuditTime(new Date());
		assiEvaAuditInfo.setAuditUser(sessionform.getUserid());
		assiEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveAssiEvaAuditInfo(assiEvaAuditInfo);
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
				AssiEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IAssiEvaAuditInfoMgr auditInfoMgr = (IAssiEvaAuditInfoMgr) getBean("IassiEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(AssiEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
