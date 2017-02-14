package com.boco.eoms.assEva.webapp.action;

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
import com.boco.eoms.assEva.webapp.form.AssEvaAuditForm;
import com.boco.eoms.assEva.mgr.IAssEvaAuditInfoMgr;
import com.boco.eoms.assEva.mgr.IAssEvaKpiInstanceMgr;
import com.boco.eoms.assEva.mgr.IAssEvaOrgMgr;
import com.boco.eoms.assEva.model.AssEvaOrg;
import com.boco.eoms.assEva.util.DateTimeUtil;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.util.AssEvaStaticMethod;
import com.boco.eoms.assEva.model.AssEvaAuditInfo;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.assEva.model.AssEvaTemplate;

public final class AssEvaAuditAction extends BaseAction {

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
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");

		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		AssEvaOrg orgOld = orgMgr.getAssEvaOrg(templateId);
		// 获取下发的任务
		AssEvaOrg orgTask = orgMgr.getAssEvaOrg(orgOld.getTemplateId());
		// 模板
		AssEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(AssEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		AssEvaOrg activeOrg = new AssEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IAssEvaKpiInstanceMgr instanceMgr = (IAssEvaKpiInstanceMgr) getBean("IassEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(AssEvaConstants.DATE_FORMAT));
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
		AssEvaAuditForm assEvaAuditForm = (AssEvaAuditForm) form;
		IAssEvaAuditInfoMgr auditInfoMgr = (IAssEvaAuditInfoMgr) getBean("IassEvaAuditInfoMgr");
		IAssEvaOrgMgr orgMgr = (IAssEvaOrgMgr) getBean("IassEvaOrgMgr");
		IAssEvaTemplateMgr templateMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		AssEvaOrg orgOld = orgMgr.getAssEvaOrg(assEvaAuditForm.getId());
		orgOld.setStatus(AssEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveAssEvaOrg(orgOld);
		// 获取处理下发的组织
		AssEvaOrg orgUp = orgMgr.getAssEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		AssEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		AssEvaOrg orgNew = new AssEvaOrg();
		if (assEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(AssEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(AssEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setAssEvaStartTime(template.getStartTime());
			orgNew.setAssEvaEndTime(template.getEndTime());
			orgMgr.saveAssEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(AssEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(AssEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setAssEvaStartTime(template.getStartTime());
			orgNew.setAssEvaEndTime(template.getEndTime());
			orgMgr.saveAssEvaOrg(orgNew);
		}
		// 增加审批信息
		AssEvaAuditInfo assEvaAuditInfo = new AssEvaAuditInfo();
		assEvaAuditInfo.setAuditInfo(assEvaAuditForm.getAuditInfo());
		assEvaAuditInfo.setTemplateId(orgUp.getId());
		assEvaAuditInfo.setAuditTime(new Date());
		assEvaAuditInfo.setAuditUser(sessionform.getUserid());
		assEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveAssEvaAuditInfo(assEvaAuditInfo);
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
				AssEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IAssEvaAuditInfoMgr auditInfoMgr = (IAssEvaAuditInfoMgr) getBean("IassEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(AssEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
