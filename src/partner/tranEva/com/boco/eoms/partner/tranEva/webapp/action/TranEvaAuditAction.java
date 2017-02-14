package com.boco.eoms.partner.tranEva.webapp.action;

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
import com.boco.eoms.partner.tranEva.webapp.form.TranEvaAuditForm;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaAuditInfoMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiInstanceMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaOrgMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;
import com.boco.eoms.partner.tranEva.util.DateTimeUtil;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.partner.tranEva.util.TranEvaStaticMethod;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.tranEva.model.TranEvaTemplate;

public final class TranEvaAuditAction extends BaseAction {

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
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");

		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		TranEvaOrg orgOld = orgMgr.getTranEvaOrg(templateId);
		// 获取下发的任务
		TranEvaOrg orgTask = orgMgr.getTranEvaOrg(orgOld.getTemplateId());
		// 模板
		TranEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(TranEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		TranEvaOrg activeOrg = new TranEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		ITranEvaKpiInstanceMgr instanceMgr = (ITranEvaKpiInstanceMgr) getBean("ItranEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(TranEvaConstants.DATE_FORMAT));
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
		TranEvaAuditForm tranEvaAuditForm = (TranEvaAuditForm) form;
		ITranEvaAuditInfoMgr auditInfoMgr = (ITranEvaAuditInfoMgr) getBean("ItranEvaAuditInfoMgr");
		ITranEvaOrgMgr orgMgr = (ITranEvaOrgMgr) getBean("ItranEvaOrgMgr");
		ITranEvaTemplateMgr templateMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		TranEvaOrg orgOld = orgMgr.getTranEvaOrg(tranEvaAuditForm.getId());
		orgOld.setStatus(TranEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveTranEvaOrg(orgOld);
		// 获取处理下发的组织
		TranEvaOrg orgUp = orgMgr.getTranEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		TranEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		TranEvaOrg orgNew = new TranEvaOrg();
		if (tranEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(TranEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(TranEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setTranEvaStartTime(template.getStartTime());
			orgNew.setTranEvaEndTime(template.getEndTime());
			orgMgr.saveTranEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(TranEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(TranEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setTranEvaStartTime(template.getStartTime());
			orgNew.setTranEvaEndTime(template.getEndTime());
			orgMgr.saveTranEvaOrg(orgNew);
		}
		// 增加审批信息
		TranEvaAuditInfo tranEvaAuditInfo = new TranEvaAuditInfo();
		tranEvaAuditInfo.setAuditInfo(tranEvaAuditForm.getAuditInfo());
		tranEvaAuditInfo.setTemplateId(orgUp.getId());
		tranEvaAuditInfo.setAuditTime(new Date());
		tranEvaAuditInfo.setAuditUser(sessionform.getUserid());
		tranEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveTranEvaAuditInfo(tranEvaAuditInfo);
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
				TranEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		ITranEvaAuditInfoMgr auditInfoMgr = (ITranEvaAuditInfoMgr) getBean("ItranEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(TranEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
