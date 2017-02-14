package com.boco.eoms.partner.chanEva.webapp.action;

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
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaAuditForm;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaAuditInfoMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaOrgMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;
import com.boco.eoms.partner.chanEva.util.DateTimeUtil;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.util.ChanEvaStaticMethod;
import com.boco.eoms.partner.chanEva.model.ChanEvaAuditInfo;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;

public final class ChanEvaAuditAction extends BaseAction {

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
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");

		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		ChanEvaOrg orgOld = orgMgr.getChanEvaOrg(templateId);
		// 获取下发的任务
		ChanEvaOrg orgTask = orgMgr.getChanEvaOrg(orgOld.getTemplateId());
		// 模板
		ChanEvaTemplate template = templateMgr.getTemplate(orgTask.getTemplateId());
		orgTask.setOrgName(ChanEvaStaticMethod.getOrgName(orgTask.getOrgId(),
				orgTask.getOrgType()));
		request.setAttribute("org", orgTask);
		request.setAttribute("template", template);

		// 任务当前处于激活状态的记录
		ChanEvaOrg activeOrg = new ChanEvaOrg();
		activeOrg = orgMgr.getLatestTaskByTaskId(orgTask.getId());
		request.setAttribute("activeOrg", activeOrg);

		// 考核实例信息
		IChanEvaKpiInstanceMgr instanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		List kpiList = instanceMgr.listKpiOfTemplateWithScore(orgTask.getId(),
				DateTimeUtil.getCurrentDateTime(ChanEvaConstants.DATE_FORMAT));
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
		ChanEvaAuditForm chanEvaAuditForm = (ChanEvaAuditForm) form;
		IChanEvaAuditInfoMgr auditInfoMgr = (IChanEvaAuditInfoMgr) getBean("IchanEvaAuditInfoMgr");
		IChanEvaOrgMgr orgMgr = (IChanEvaOrgMgr) getBean("IchanEvaOrgMgr");
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		// 更改原来任务的状态为已执行
		ChanEvaOrg orgOld = orgMgr.getChanEvaOrg(chanEvaAuditForm.getId());
		orgOld.setStatus(ChanEvaConstants.TASK_STSTUS_INACTIVE);
		orgMgr.saveChanEvaOrg(orgOld);
		// 获取处理下发的组织
		ChanEvaOrg orgUp = orgMgr.getChanEvaOrg(orgOld.getTemplateId());
		// 获取模板信息
		ChanEvaTemplate template = templateMgr.getTemplate(orgUp.getTemplateId());
		ChanEvaOrg orgNew = new ChanEvaOrg();
		if (chanEvaAuditForm.getAuditFlag().equals("1")) { // 驳回
			// 增加驳回任务
			orgNew.setOrgId(orgUp.getOrgId());
			orgNew.setOrgType(orgUp.getOrgType());
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setActionType(ChanEvaConstants.TEMPLATE_AUDIT_REJECT);
			orgNew.setStatus(ChanEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setOperateTime(new Date());
			orgNew.setChanEvaStartTime(template.getStartTime());
			orgNew.setChanEvaEndTime(template.getEndTime());
			orgMgr.saveChanEvaOrg(orgNew);
		} else { // 审核通过
			// 增加上报任务
			orgNew.setOrgId(template.getCreator());
			orgNew.setOrgType(StaticVariable.PRIV_ASSIGNTYPE_USER);
			orgNew.setTemplateId(orgUp.getId());
			orgNew.setStatus(ChanEvaConstants.TASK_STSTUS_ACTIVITIES);
			orgNew.setActionType(ChanEvaConstants.TEMPLATE_REPORTED);
			orgNew.setOperateTime(new Date());
			orgNew.setChanEvaStartTime(template.getStartTime());
			orgNew.setChanEvaEndTime(template.getEndTime());
			orgMgr.saveChanEvaOrg(orgNew);
		}
		// 增加审批信息
		ChanEvaAuditInfo chanEvaAuditInfo = new ChanEvaAuditInfo();
		chanEvaAuditInfo.setAuditInfo(chanEvaAuditForm.getAuditInfo());
		chanEvaAuditInfo.setTemplateId(orgUp.getId());
		chanEvaAuditInfo.setAuditTime(new Date());
		chanEvaAuditInfo.setAuditUser(sessionform.getUserid());
		chanEvaAuditInfo.setStatus(orgNew.getStatus());
		auditInfoMgr.saveChanEvaAuditInfo(chanEvaAuditInfo);
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
				ChanEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IChanEvaAuditInfoMgr auditInfoMgr = (IChanEvaAuditInfoMgr) getBean("IchanEvaAuditInfoMgr");
		String hSql = " where templateId='" + templateId + "'";
		Map map = (Map) auditInfoMgr.getAuditInfoByTempateId(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		request.setAttribute(ChanEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
