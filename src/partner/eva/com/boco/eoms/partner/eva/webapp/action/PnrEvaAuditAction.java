package com.boco.eoms.partner.eva.webapp.action;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaAuditInfoMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.partner.eva.util.PnrEvaStaticMethod;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaAuditForm;

public final class PnrEvaAuditAction extends BaseAction {

	

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

	public ActionForward editAuditInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userName = sessionform.getUsername();
		PnrEvaAuditForm evaAuditForm = (PnrEvaAuditForm) form;
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		PnrEvaAuditInfo auditInfo = auditInfoMgr.getPnrEvaAuditInfo(evaAuditForm.getId());
		//默认设置模板审核人为当前登录用户
		auditInfo.setAuditUser(userName);
		//根据模板Id取出模板名称
		PnrEvaTemplate template = templateMgr.getTemplate(auditInfo.getTemplateId());
		request.setAttribute("template", template);
		request.setAttribute("pnrEvaAuditForm", auditInfo);
		return mapping.findForward("audit");
	}

	/**
	 *	审核
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
		PnrEvaAuditForm evaAuditForm = (PnrEvaAuditForm) form;
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		PnrEvaAuditInfo auditInfo = new PnrEvaAuditInfo();
		
		auditInfo = auditInfoMgr.getPnrEvaAuditInfo(evaAuditForm.getId());
		//设置模板审核
		auditInfo.setAuditTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		auditInfo.setAuditUser(sessionform.getUserid());
		auditInfo.setAuditType(PnrEvaConstants.AUDIT_NEW);
		auditInfo.setAuditResult(evaAuditForm.getAuditResult());
		auditInfo.setRemark(evaAuditForm.getRemark());
		auditInfoMgr.savePnrEvaAuditInfo(auditInfo);
		
		//修改模板信息表里审核状态
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTemplate template = new PnrEvaTemplate();
		template = templateMgr.getTemplate(evaAuditForm.getTemplateId());
		template.setAuditFlag(evaAuditForm.getAuditResult());
		templateMgr.saveTemplate(template);
		
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
	public ActionForward unAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String operateType = PnrEvaConstants.OPERATE_TEMPLATE_AUDIT;
		Map map0 = (Map) PnrEvaStaticMethod.getRoleAreaByUserId(userId,operateType);
		String subRoleId = StaticMethod.nullObject2String(map0.get("subRoleId"));
		//String areaId = StaticMethod.nullObject2String(map0.get("areaId"));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		String hSql = " where auditResult='"+PnrEvaConstants.AUDIT_WAIT+"' and  ((auditOrgType = 'user' and auditOrg = '"+userId+"') or (auditOrgType = 'dept' and auditOrg = '"+deptId+"') " +
				" or (auditOrgType = 'subRole' and auditOrg = '"+subRoleId+"')) ";
		Map map = (Map) auditInfoMgr.getAuditInfoByCondition(pageIndex,
				pageSize, hSql);
		List list = (List) map.get("result");
		//查询出模板Id的模板名称，存在List集合中
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		Iterator iter = list.iterator(); 
		while (iter.hasNext()) { 
			PnrEvaTemplate template = null ;
			PnrEvaAuditInfo auditInfo = (PnrEvaAuditInfo) iter.next(); 
			template = templateMgr.getTemplate(auditInfo.getTemplateId());
			auditInfo.setTemplateName(template.getTemplateName());
		}	
		request.setAttribute(PnrEvaConstants.AUDITINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("auditInfoList");
	}
}
