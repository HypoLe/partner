package com.boco.eoms.partner.baseinfo.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.priv.util.PrivConstants;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.duty.util.DutyConstacts;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskAuditMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.model.EvaTaskAudit;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;
import com.boco.eoms.workbench.infopub.model.Thread;
import com.boco.eoms.workbench.memo.model.TawWorkbenchMemo;
import com.boco.eoms.workbench.memo.service.ITawWorkbenchMemoManager;
import com.boco.eoms.workbench.memo.util.MemoPage;


public final class PnrIndexAction extends BaseAction {
	
	public  static String defaultParentNodeId = "";//当save 或 remove 方法，调用 search方法时，利用这个变量，定位到相应人力信息的树节点下
 
	/**
	 * 默认方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getIndex(mapping, form, request, response);
	}
 	/**
	 * 首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward getIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			if("admin".equals(userId)){
				request.getSession().setAttribute(
						"pnrmenu",
						PrivMgrLocator.getPrivMgr().operations2json(
								PrivMgrLocator.getPrivMgr().listOpertion(
										userId, deptId,
										sessionform.getRolelist(),PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,
										"33")));//33为个人工作台id
			}else{
			request.getSession().setAttribute(
				"pnrmenu",
				PrivMgrLocator.getPrivMgr().operations2json(
						PrivMgrLocator.getPrivMgr().listOpertion(
								userId, deptId,
								sessionform.getRolelist(),PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,
								"33")));//33为个人工作台id
			}
    		return mapping.findForward("pnrIndex");
    }
 	/**
	 * 待审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward getUndo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    		return mapping.findForward("undo");
    }
 	/**
	 * 待执行
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward getUnAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    		return mapping.findForward("unAudit");
    }
 	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 
    public ActionForward getMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		StringBuffer msgStr = new StringBuffer();
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeeAuditMgr partnerFeeAuditMgr = (PartnerFeeAuditMgr) getBean("partnerFeeAuditMgr");
		//付款计划到期提醒
		Timestamp time = StaticMethod.getTimestamp(5);//提前5天提醒
		List partnerFeePlanList = partnerFeePlanMgr.getFeePlantsByPayUnit(deptId, time, "0");
		PartnerFeePlan partnerFeePlan = null;
		for(int i=0;i<partnerFeePlanList.size();i++){
			if(i==0){
				msgStr.append("您的部门有"+partnerFeePlanList.size()+"条付款计划即将到期:<br>");
			}
			partnerFeePlan = (PartnerFeePlan)partnerFeePlanList.get(i);
			msgStr.append("<a href='"+request.getContextPath()+"/fee/partnerFeePlans.do?method=searchDetail&id="+partnerFeePlan.getId()+ "' target='_blank'>"+partnerFeePlan.getName()+"(金额:"+partnerFeePlan.getPlanPayFee()+")</a><br>");
		}
		//付款计划待审核提醒
		Map unAuditmap = partnerFeeAuditMgr.getPartnerFeeUnAudits(Integer.valueOf(0),Integer.valueOf(10000),userId, deptId, "plan");
		List partnerFeePlanAuditList = (List) unAuditmap.get("result");
		PartnerFeeAudit partnerFeeAudit = null;
		for(int i=0;i<partnerFeePlanAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+partnerFeePlanAuditList.size()+"条待审核的付款计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/fee/partnerFeePlans.do?method=getUnAuditPlanList' target='_blank'>查看列表</a><br>");
			}
			partnerFeeAudit = (PartnerFeeAudit)partnerFeePlanAuditList.get(i);
		}
		//付款计划被驳回提醒
		Map rejectFeePlanMap = partnerFeePlanMgr.getFeePlansByCreator(Integer.valueOf(0),Integer.valueOf(10000),userId, "", PartnerFeePlanConstants.PLAN_STATE_REJECT);
		List rejectFeePlanList = (List) rejectFeePlanMap.get("result");
		PartnerFeePlan rejectFeePlan = null;
		for(int i=0;i<rejectFeePlanList.size();i++){
			if(i==0){
				msgStr.append("您有"+rejectFeePlanList.size()+"条被驳回的付款计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/fee/partnerFeePlans.do?method=getRejectFeePlanList' target='_blank'>查看列表</a><br>");
			}
			rejectFeePlan = (PartnerFeePlan)rejectFeePlanList.get(i);
		}
		//协议待审核提醒
		IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) getBean("pnrAgreementAuditMgr");
		PnrAgreementAudit pnrAgreementAudit = null;
		Map agreementUnauditMap = (Map)pnrAgreementAuditMgr.getPnrAgreementUnAudits(Integer.valueOf(0),Integer.valueOf(10000),userId, deptId,"");
		List unAuditList = (List) agreementUnauditMap.get("result");
		for(int i=0;i<unAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+unAuditList.size()+"条待审核的服务协议:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?method=getUnAuditList' target='_blank'>查看列表</a><br>");
			}
			pnrAgreementAudit = (PnrAgreementAudit)unAuditList.get(i);
		}
		//协议驳回提醒
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
	    PnrAgreementMain pnrAgreementMain = null;
	    StringBuffer pawhere = new StringBuffer();
	    pawhere.append(" where 1=1 and pnrAgreementMain.state = '1'  and pnrAgreementMain.createUser = '");
	    pawhere.append(userId);
	    pawhere.append("' and pnrAgreementMain.createDept='");
	    pawhere.append(deptId);
	    pawhere.append("'");
		Map  pnrAgreementMainMap = pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(1000), pawhere.toString());
		List pnrAgreementList = (List) pnrAgreementMainMap.get("result");
		for(int i=0;i<pnrAgreementList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrAgreementList.size()+"条被驳回的服务协议:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=1' target='_blank'>查看列表</a><br>");
			}
			pnrAgreementMain = (PnrAgreementMain)pnrAgreementList.get(i);
		}
		//工作计划驳回提醒
		IPnrWorkplanMainMgr pnrWorkplanMainMgr = (IPnrWorkplanMainMgr) getBean("pnrWorkplanMainMgr");
	    PnrWorkplanMain pnrWorkplanMain = null;
	    StringBuffer pwpwhere = new StringBuffer();
	    pwpwhere.append(" where 1=1 and pnrWorkplanMain.state = '1'  and pnrWorkplanMain.createUser = '");
	    pwpwhere.append(userId);
	    pwpwhere.append("' and pnrWorkplanMain.createDept='");
	    pwpwhere.append(deptId);
	    pwpwhere.append("'");
		Map  pnrWorkplanMainMap = pnrWorkplanMainMgr.getPnrWorkplanMains(Integer.valueOf(0),Integer.valueOf(1000), pwpwhere.toString());
		List pnrWorkplanMainList = (List) pnrWorkplanMainMap.get("result");
		for(int i=0;i<pnrWorkplanMainList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrWorkplanMainList.size()+"条被驳回的工作计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanMains.do?state=1' target='_blank'>查看列表</a><br>");
			}
			pnrWorkplanMain = (PnrWorkplanMain)pnrWorkplanMainList.get(i);
		}	
		//待审核工作计划提醒
		IPnrWorkplanAuditMgr pnrWorkplanAuditMgr = (IPnrWorkplanAuditMgr) getBean("pnrWorkplanAuditMgr");
		Map map = (Map)pnrWorkplanAuditMgr.getPnrWorkplanUnAudits(Integer.valueOf(0),Integer.valueOf(1000),userId, deptId);

	    PnrWorkplanAudit pnrWorkplanAudit = null;
	    List pnrWorkplanAuditList = (List) map.get("result");
		for(int i=0;i<pnrWorkplanAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrWorkplanAuditList.size()+"条待审核的工作计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanMains.do?method=getUnAuditList' target='_blank'>查看列表</a><br>");
			}
			pnrWorkplanAudit = (PnrWorkplanAudit)pnrWorkplanAuditList.get(i);
		}			
		request.setAttribute("msg", msgStr.toString());

		//待执行工作计划提醒
		IPnrWorkplanExeMgr pnrWorkplanExeMgr = (IPnrWorkplanExeMgr) getBean("pnrWorkplanExeMgr");
		//得到待执行项
		Map exemap = (Map) pnrWorkplanExeMgr.getPnrWorkplanUndo(Integer.valueOf(0),Integer.valueOf(1000),userId,deptId);

	    PnrWorkplanExe pnrWorkplanExe = null;
	    List pnrWorkplanExeList = (List) exemap.get("result");
		Map workNum = new HashMap();
		PnrWorkplanWork pnrWorkplanWork = new PnrWorkplanWork();
		for(int i=0;i<pnrWorkplanExeList.size();i++){
			pnrWorkplanWork=(PnrWorkplanWork)pnrWorkplanExeList.get(i);
			workNum.put(pnrWorkplanWork.getWorkplanId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrWorkplanWork.getWorkplanId()), 0)+1));
		}
		for(int i=0;i<pnrWorkplanExeList.size();i++){
			if(i==0){
				msgStr.append("您有"+workNum.size()+"条待执行的工作计划(包含"+pnrWorkplanExeList.size()+"个工作任务):<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanWorks.do?method=undoList' target='_blank'>查看列表</a><br>");
			}
		}	
		//临时任务驳回提醒
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
	    PnrTempTaskMain pnrTempTaskMain = null;
	    StringBuffer pttwhere = new StringBuffer();
	    pttwhere.append(" where 1=1 and pnrTempTaskMain.state = '1'  and pnrTempTaskMain.createUser = '");
	    pttwhere.append(userId);
	    pttwhere.append("' and pnrTempTaskMain.createDept='");
	    pttwhere.append(deptId);
	    pttwhere.append("'");
		Map  pnrTempTaskMainMap = pnrTempTaskMainMgr.getPnrTempTaskMains(Integer.valueOf(0),Integer.valueOf(1000), pttwhere.toString());
		List pnrTempTaskMainList = (List) pnrTempTaskMainMap.get("result");
		for(int i=0;i<pnrTempTaskMainList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrTempTaskMainList.size()+"条被驳回的临时任务:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/tempTask/pnrTempTaskMains.do?state=1' target='_blank'>查看列表</a><br>");
			}
			pnrTempTaskMain = (PnrTempTaskMain)pnrTempTaskMainList.get(i);
		}	
		//待审核临时任务提醒
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		Map pnrTempTaskAuditmap = (Map)pnrTempTaskAuditMgr.getPnrTempTaskUnAudits(Integer.valueOf(0),Integer.valueOf(1000),userId, deptId);

	    PnrTempTaskAudit pnrTempTaskAudit = null;
	    List pnrTempTaskAuditList = (List) pnrTempTaskAuditmap.get("result");
		for(int i=0;i<pnrTempTaskAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrTempTaskAuditList.size()+"条待审核的临时任务:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/tempTask/pnrTempTaskMains.do?method=getUnAuditList' target='_blank'>查看列表</a><br>");
			}
			pnrTempTaskAudit = (PnrTempTaskAudit)pnrTempTaskAuditList.get(i);
		}			
		request.setAttribute("msg", msgStr.toString());

		//待执行临时任务提醒
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		//得到待执行项
		Map pnrTempTaskExemap = (Map) pnrTempTaskExeMgr.getPnrTempTaskUndo(Integer.valueOf(0),Integer.valueOf(1000),userId,deptId);

	    PnrTempTaskExe pnrTempTaskExe = null;
	    List pnrTempTaskExeList = (List) pnrTempTaskExemap.get("result");
		Map workNumexe = new HashMap();
		PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
		for(int i=0;i<pnrTempTaskExeList.size();i++){
			pnrTempTaskWork=(PnrTempTaskWork)pnrTempTaskExeList.get(i);
			workNumexe.put(pnrTempTaskWork.getTempTaskId(), String.valueOf(StaticMethod.nullObject2int(workNumexe.get(pnrTempTaskWork.getTempTaskId()), 0)+1));
		}
		for(int i=0;i<pnrTempTaskExeList.size();i++){
			if(i==0){
				msgStr.append("您有"+workNumexe.size()+"条待执行的临时任务(包含"+pnrTempTaskExeList.size()+"个工作任务):<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/tempTask/pnrTempTaskWorks.do?method=undoList' target='_blank'>查看列表</a><br>");
			}
		}	
		
		//协议到时提醒
		String date = StaticMethod.getLocalString(5);
	    StringBuffer overWhere = new StringBuffer();
	    overWhere.append(" where 1=1 and pnrAgreementMain.state = '2'  and pnrAgreementMain.partyAUser = '");
	    overWhere.append(userId);
	    overWhere.append("' and pnrAgreementMain.endTime <= '");
	    overWhere.append(date);
	    overWhere.append("' and pnrAgreementMain.isClosed='unclosed'");
		Map  pnrAgreementMainoverMap = pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(100), overWhere.toString());
		List pnrAgreementoverList = (List) pnrAgreementMainoverMap.get("result");
		for(int i=0;i<pnrAgreementoverList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrAgreementoverList.size()+"条即将到期的协议:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=2&overPnr=overPnr&closed=unclosed' target='_blank'>查看列表</a><br>");
			}
		}	
		//协议关闭待审核提醒
		String whereStrx = " and ((pnrAgreementAudit.toOrgId = '"+userId+"' and pnrAgreementAudit.toOrgType = 'user') or (pnrAgreementAudit.toOrgId = '"+deptId+"' and pnrAgreementAudit.toOrgType = 'dept')) ";
		whereStrx += "and pnrAgreementAudit.isClosed = 'waitclosed'";
		Map  pnrAgreementClosedunMap  = (Map)pnrAgreementAuditMgr.getPnrAgreementClosedUnAudits(Integer.valueOf(0),Integer.valueOf(100), whereStrx);
		List pnrAgreementClosedunList = (List) pnrAgreementClosedunMap.get("result");
		for(int i=0;i<pnrAgreementClosedunList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrAgreementClosedunList.size()+"条要关闭的协议需要审核:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?method=getUnAuditList&closed=waitclosed' target='_blank'>查看列表</a><br>");
			}
		}		
		//协议关闭驳回提醒
		
		String retpnrWhere = "  where 1=1  and pnrAgreementMain.partyAUser = '"+userId+"'";
		retpnrWhere += " and pnrAgreementMain.isClosed = 'closedret'";
		Map  retpnrWhereMap  = (Map) pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(100), retpnrWhere);
		List retpnrWhereList = (List) retpnrWhereMap.get("result");
		for(int i=0;i<retpnrWhereList.size();i++){
			if(i==0){
				msgStr.append("您有"+retpnrWhereList.size()+"条要关闭的协议被驳回:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?closed=closedret&retPnr=retPnr' target='_blank'>查看列表</a><br>");
			}
		}			
		request.setAttribute("msg", msgStr.toString());		
		return mapping.findForward("msgWin");
	}
	*/
	/**
	 * 考核未审核任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward evaUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				EvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		String hSql = " where auditResult='"+EvaConstants.AUDIT_WAIT+"' and  ((auditOrgType = 'user' and auditOrg = '"+userId+"') or (auditOrgType = 'dept' and auditOrg = '"+deptId+"')) ";
		Map map = (Map) auditAuditMgr.getEvaTaskAuditByOrgType(pageIndex, pageSize, hSql);
		List list = (List) map.get("result");
		//查询出模板Id的模板名称，存在List集合中
		//查村出指标Id对应的指标名称，存在List集合中
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		Iterator iter = list.iterator(); 
		while (iter.hasNext()) { 
			EvaTaskAudit taskAudit = (EvaTaskAudit) iter.next(); 
			if(!("".equals(taskAudit.getTemplateId()) || null == taskAudit.getTemplateId())){
				EvaTemplate template = null ;
				template = templateMgr.getTemplate(taskAudit.getTemplateId());
				taskAudit.setTemplateName(template.getTemplateName());
			}

			
		}	
		request.setAttribute(EvaConstants.TASK_AUDIT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("evaUnAuditList");
	}

    /**
	 * 得到协议待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
   /* public ActionForward agreeUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrAgreementConstants.PNRAGREEMENTAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) getBean("pnrAgreementAuditMgr");

		String type = StaticMethod.null2String(request.getParameter("type"));
		Map map = map = (Map)pnrAgreementAuditMgr.getPnrAgreementUnAudits(pageIndex, pageSize,userId, deptId,type);
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("agreeUnAuditList");
	}*/
    /**
	 * 得到工作计划待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    /*public ActionForward workplanUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrWorkplanConstants.PNRAGREEMENTAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrWorkplanAuditMgr pnrWorkplanAuditMgr = (IPnrWorkplanAuditMgr) getBean("pnrWorkplanAuditMgr");
		Map map = (Map)pnrWorkplanAuditMgr.getPnrWorkplanUnAudits(pageIndex, pageSize,userId, deptId);
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("workplanUnAuditList");
	}*/
    /**
	 * 临时工作待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
   /* public ActionForward tempTaskUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		Map map = (Map)pnrTempTaskAuditMgr.getPnrTempTaskUnAudits(pageIndex, pageSize,userId, deptId);
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("tempTaskUnAuditList");
	}*/
    
    /**
	 * 得到费用待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
  /*  public ActionForward feeUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrFeeInfoConstants.PNRFEEINFOAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrFeeInfoAuditMgr pnrFeeInfoAuditMgr = (IPnrFeeInfoAuditMgr) getBean("pnrFeeInfoAuditMgr");
		Map map = (Map)pnrFeeInfoAuditMgr.getPnrFeeInfoUnAudits(pageIndex, pageSize,userId, deptId,type);
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("feeUnAuditList");
	}*/
    
	/**
	 * 显示合作伙伴工作计划待执行列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward workplanUndoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String agreeId = StaticMethod.nullObject2String(request.getParameter("agreeId"));
		String workplanId ="";
		if(!"".equals(agreeId)){
			IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreeId);
			workplanId = pnrAgreementMain.getWorkplanId();
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrWorkplanConstants.PNRAGREEMENTWORK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(1000);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrWorkplanMainMgr pnrWorkplanMainMgr = (IPnrWorkplanMainMgr) getBean("pnrWorkplanMainMgr");
		IPnrWorkplanExeMgr pnrWorkplanExeMgr = (IPnrWorkplanExeMgr) getBean("pnrWorkplanExeMgr");
		//得到待执行项
		Map map = (Map) pnrWorkplanExeMgr.getPnrWorkplanForExecute(pageIndex, pageSize,userId,deptId);
		//可结束项
//		Map map = (Map) pnrWorkplanExeMgr.getPnrWorkplanUndo(pageIndex, pageSize,userId,deptId);
		List list = (List) map.get("result");
		List lastList = new ArrayList();
		Map workNum = new HashMap();
		PnrWorkplanWork pnrWorkplanWork = new PnrWorkplanWork();
		for(int i=0;i<list.size();i++){
			pnrWorkplanWork=(PnrWorkplanWork)list.get(i);
			if("".equals(agreeId)){
				lastList.add(pnrWorkplanWork);
				workNum.put(pnrWorkplanWork.getWorkplanId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrWorkplanWork.getWorkplanId()), 0)+1));
			}else if(workplanId.equals(pnrWorkplanWork.getWorkplanId())){
				lastList.add(pnrWorkplanWork);
				workNum.put(pnrWorkplanWork.getWorkplanId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrWorkplanWork.getWorkplanId()), 0)+1));
			}
		}
		request.setAttribute("workNum", workNum);
		request.setAttribute(PnrWorkplanConstants.PNRAGREEMENTWORK_LIST, lastList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("workplanUndoList");
	}*/
	/**
	 * 显示合作伙伴临时任务待执行列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward tempTaskUndoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTWORK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = Integer.valueOf(100);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		//得到待执行项
		Map map = (Map) pnrTempTaskExeMgr.getPnrTempTaskForExecute(pageIndex, pageSize,userId,deptId);
		//可结束项
//		Map map = (Map) pnrTempTaskExeMgr.getPnrTempTaskUndo(pageIndex, pageSize,userId,deptId);
		List list = (List) map.get("result");
		Map workNum = new HashMap();
		PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
		for(int i=0;i<list.size();i++){
			pnrTempTaskWork=(PnrTempTaskWork)list.get(i);
			workNum.put(pnrTempTaskWork.getTempTaskId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrTempTaskWork.getTempTaskId()), 0)+1));
		}
		request.setAttribute("workNum", workNum);
		request.setAttribute(PnrTempTaskConstants.PNRAGREEMENTWORK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("tempTaskUndoList");
	}*/
	/**
	 * 显示合作伙伴待付费列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward feepayUndoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		IPnrFeeInfoMainMgr pnrFeeInfoMainMgr = (IPnrFeeInfoMainMgr) getBean("pnrFeeInfoMainMgr");
		IPnrFeeInfoPayMgr pnrFeeInfoPayMgr = (IPnrFeeInfoPayMgr) getBean("pnrFeeInfoPayMgr");
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		// 得到执行项
		List list = (List) pnrFeeInfoPayMgr.getPnrFeeInfoUndoAll(userId);
//		Map map = pnrFeeInfoPayMgr.getPnrFeeInfoUndoForInterface(Integer.valueOf(1), Integer.valueOf(100), userId, "");
		Map planNum = new HashMap();
		PnrFeeInfoPlan pnrFeeInfoPlan = new PnrFeeInfoPlan();
		
		//筛选出最终显示的待付款任务，去掉相关协议没有综合评分的待付款任务
		List planList = new ArrayList();
		PnrFeeInfoMain pnrFeeInfoMain = null;
		PnrAgreementMain pnrAgreementMain = null;
		List agreementlist = null;
		for(int i=0;i<list.size();i++){
			pnrFeeInfoPlan=(PnrFeeInfoPlan)list.get(i);
			if("eva".equals(pnrFeeInfoPlan.getEvaPlanFlag())){
				pnrFeeInfoMain = pnrFeeInfoMainMgr.getPnrFeeInfoMain(pnrFeeInfoPlan.getFeeId());
				agreementlist = pnrAgreementMainMgr.getPnrAgreementsByContentId(pnrFeeInfoMain.getId());
				boolean okFlag = false;
				for(int j=0;agreementlist!=null&&j<agreementlist.size();j++){
					pnrAgreementMain = (PnrAgreementMain)agreementlist.get(j);
					if("4".equals(pnrAgreementMain.getState())){
						okFlag = true;
					}else{
						okFlag = false;
						break;
					}
				}
				if(okFlag||agreementlist==null){
					planList.add(pnrFeeInfoPlan);
					planNum.put(pnrFeeInfoPlan.getFeeId(), String.valueOf(StaticMethod.nullObject2int(planNum.get(pnrFeeInfoPlan.getFeeId()), 0)+1));
				}
			}else{
				planList.add(pnrFeeInfoPlan);
				planNum.put(pnrFeeInfoPlan.getFeeId(), String.valueOf(StaticMethod.nullObject2int(planNum.get(pnrFeeInfoPlan.getFeeId()), 0)+1));
			}
		}
		request.setAttribute("planNum", planNum);
		request.setAttribute(PnrFeeInfoConstants.PNRFEEINFOPLAN_LIST, planList);
		request.setAttribute("payMgr", pnrFeeInfoPayMgr);
		return mapping.findForward("feepayUndoList");
	}*/
	

	/**
	 * 
	 * @see 我收到的便签查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward getReciverMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int offset;
		int length = 4;
		try {
			String whereStr = "";
			String pageOffset = request.getParameter("pager.offset");
			if (pageOffset == null || pageOffset.equals("")) {
				offset = 0;
			} else {
				offset = Integer.parseInt(pageOffset);
			}
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					DutyConstacts.TAW_WORKBENCH_MEMOLIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);

			final Integer pageIndex = new Integer(
					GenericValidator.isBlankOrNull(request
							.getParameter(pageIndexName)) ? 0 : (Integer
							.parseInt(request.getParameter(pageIndexName)) - 1));
			final Integer pageSize = Integer.valueOf(3);
//			final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUsername();
			String str = request.getParameter("folderPath");
			whereStr = request.getParameter("whereStr");
			request.setAttribute("folderPath", str);
			StringBuffer buffer = new StringBuffer();
			StringBuffer bufferpage = new StringBuffer();

			buffer.append(" where 1= 1 and sendmanner = '3' and reciever like '%"
					+ userId + "%'");
			bufferpage.append("method=xSearchList");
			String title = StaticMethod.null2String(request
					.getParameter("title"));
			bufferpage.append("&title=" + title + "");
			if (title != "") {
				buffer.append(" and title like '%" + title + "%'");
			}
			String content = StaticMethod.null2String(request
					.getParameter("content"));
			bufferpage.append("&content=" + content + "");
			if (!content.equals("")) {
				buffer.append(" and content like '%" + content + "%'");
			}
			String creatBeginTime = StaticMethod.null2String(request
					.getParameter("creatBeginTime"));
			bufferpage.append("&creatBeginTime=" + creatBeginTime + "");
			if (!creatBeginTime.equals("")) {
				buffer.append(" and creattime >= '" + creatBeginTime + "'");
			}
			String creatEndTime = StaticMethod.null2String(request
					.getParameter("creatEndTime"));
			bufferpage.append("&creatEndTime=" + creatEndTime + "");
			if (!creatEndTime.equals("")) {
				buffer.append(" and creattime <= '" + creatEndTime + "'");
			}
			String creatUserId = StaticMethod.null2String(request
					.getParameter("userid"));
			bufferpage.append("&userid=" + creatUserId + "");
			if (!creatUserId.equals("")) {
				buffer.append(" and userid = '" + creatUserId + "'");
			}
			String level = StaticMethod.null2String(request
					.getParameter("level"));
			bufferpage.append("&level=" + level + "");
			if (level.equals("1") || level.equals("2") || level.equals("3")
					|| level.equals("4")) {
				buffer.append(" and level = " + level + "");
			}

			String sendBeginTime = StaticMethod.null2String(request
					.getParameter("sendBeginTime"));
			bufferpage.append("&sendBeginTime=" + sendBeginTime + "");
			if (!sendBeginTime.equals("")) {
				buffer.append(" and sendtime >= '" + sendBeginTime + "'");

			}
			String sendEndTime = StaticMethod.null2String(request
					.getParameter("sendEndTime"));
			bufferpage.append("&sendEndTime=" + sendEndTime + "");
			if (!sendEndTime.equals("")) {
				buffer.append(" and sendtime <= '" + sendEndTime + "'");
			}

			bufferpage.append("&folderPath=" + str);
			whereStr = buffer.toString();
			request.setAttribute("whereStr", whereStr);

			String pageStr = bufferpage.toString();
			ITawWorkbenchMemoManager mgr = (ITawWorkbenchMemoManager) getBean("ItawWorkbenchMemoManager");
			Map map = (Map) mgr.getTawWorkbenchMemos(pageIndex, pageSize,
					whereStr);
			List list = (List) map.get("result");
			List memoList = new ArrayList();
			TawWorkbenchMemo tawWorkbenchMemo = null;
			for (Iterator it = list.iterator(); it.hasNext();) {
				tawWorkbenchMemo = (TawWorkbenchMemo) it.next();
				if (tawWorkbenchMemo.getReciever() != null) {
					String[] userArray = tawWorkbenchMemo.getReciever().split(
							",");
					for (int i = 0; i < userArray.length; i++) {
						if (userId.equals(userArray[i])) {
							memoList.add(tawWorkbenchMemo);
						}
					}
				}
			}
			// 分叶
			String url = request.getContextPath() + "/workbench/memo"
					+ mapping.getPath() + ".do";
			int size = ((Integer) map.get("total")).intValue();

			String pagerHeader = MemoPage.generate(pageIndex.intValue(), size,
					length, url, pageStr);
			request.setAttribute("pagerHeader", pagerHeader);
			request
					.setAttribute(DutyConstacts.TAW_WORKBENCH_MEMOLIST,
							memoList);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("reciverMsg");
	}

	/**
	 * 查询当前滚动的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ZHANGXIAOBO
	 * @since 1.0
	 */
	public ActionForward listInfopubsForIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String appUrl = request.getContextPath();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sysDate = dateFormat.format(new Date());
	
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		
		//-------------------------------

		int length = deptId.length();
		String deptids = "('" + deptId + "')";
		String dept = "(";

		ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = null;
		while (length > 0 && !deptId.equals("-1")) {
			tawSystemDept = deptManager.getDeptinfobydeptid(deptId, "0");
			dept += "'" + tawSystemDept.getDeptId() + "',";
			deptId = tawSystemDept.getParentDeptid();
		}
		if (dept.indexOf(",") > -1) {
			dept = dept.substring(0, dept.length() - 1) + ")";
		}
		String whereStr = " and thread.isDel<>'"
		+ Constants.DELETED_FLAG
		+ "' "
		+" and ((((threadPermissionOrg.orgId in "
		+ dept
		+ " and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_DEPT
		+ "' and threadPermissionOrg.isIncludeSubDept = '1'"
		+ ")"
		+ " or (threadPermissionOrg.orgId in "
		+ deptids
		+ " and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_DEPT
		+ "' and (threadPermissionOrg.isIncludeSubDept <> '1' or threadPermissionOrg.isIncludeSubDept is null)"
		+ ")" + "or(threadPermissionOrg.orgId ='" + userId
		+ "' and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_USER + "'))"
		+ " and thread.id=threadPermissionOrg.threadId)  or thread.createrId='"+userId+"')";
	
		//------------------------------------

		Map map = null;

		// 若点击为根结点（所有版块）则显示所有信息,将版块条件去掉
		map = (Map) mgr.getThreads(new Integer(0),
				new Integer(100),whereStr);

		List list = (List) map.get("result");
		
		
		request.setAttribute("list",list);
		String str = "";
		String detail = "";
		
		for(int i=0;i<list.size();i++){
			Thread thread = (Thread)list.get(i);
			detail = (i+1)+"、"+thread.getTitle()+"。（创建时间："+dateFormat.format(thread.getCreateTime())+"）";
			str +="<a href='"+appUrl+"/workbench/infopub/thread.do?method=detail&id="+thread.getId()+"' target='_blank'>"+detail+"</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}
			
		request.setAttribute("listStr",str);
		
		return mapping.findForward("listInfopub");
	}
	/**
	 * 显示合作伙伴待考核的任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward evaUndoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaReportInfoMgr evaReportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		String localTime = StaticMethod.getLocalString();
		List evaUndoList = evaReportInfoMgr.getUnEvaReport(localTime,deptId);
		request.setAttribute("evaUndoList",evaUndoList);
		return mapping.findForward("evaUndoList");
	}
	
	/**
	 * 分页显示合作伙伴待总结协议
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ActionForward uploadAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String state = "upload";
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrAgreementConstants.PNRAGREEMENTMAIN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
		final Integer pageSize = 100;
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String whereStr = " where 1=1 ";
		whereStr += " and pnrAgreementMain.partyBUser = '"+userId+"'";
		whereStr += " and pnrAgreementMain.state ='6'";
		Map map = (Map) pnrAgreementMainMgr.getPnrAgreementMains(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PnrAgreementConstants.PNRAGREEMENTMAIN_LIST, list);
		request.setAttribute("state", state);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("undoAgreeList");
	}*/

	/**
	 * 分页显示合作伙伴待归档协议
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward toCloseAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String state = "toClose";
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrAgreementConstants.PNRAGREEMENTMAIN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
		final Integer pageSize = 100;
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String whereStr = " where 1=1 ";
		
		whereStr += " and pnrAgreementMain.partyAUser = '"+userId+"'";
		whereStr += " and pnrAgreementMain.state in ('5','7')";
		Map map = (Map) pnrAgreementMainMgr.getPnrAgreementMains(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PnrAgreementConstants.PNRAGREEMENTMAIN_LIST, list);
		request.setAttribute("state", state);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("undoAgreeList");
	}*/

	/**
	 * 未审核任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward unAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				EvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = Integer.valueOf(3);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		String hSql = " where auditResult='"+EvaConstants.AUDIT_WAIT+"' and  ((auditOrgType = 'user' and auditOrg = '"+userId+"') or (auditOrgType = 'dept' and auditOrg = '"+deptId+"')) ";
		Map evaTaskAudit = (Map) auditAuditMgr.getEvaTaskAuditByOrgType(Integer.valueOf(0), Integer.valueOf(100), hSql);
		List list = (List) evaTaskAudit.get("result");
		//查询出模板Id的模板名称，存在List集合中
		//查村出指标Id对应的指标名称，存在List集合中
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		Iterator iter = list.iterator(); 
	
//		协议待审核
		IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) getBean("pnrAgreementAuditMgr");
//		String type = StaticMethod.null2String(request.getParameter("type"));
		Map agreementAuditMap = (Map)pnrAgreementAuditMgr.getPnrAgreementUnAudits(Integer.valueOf(0), Integer.valueOf(100),userId, deptId,"");	
//		临时任务待审核
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		Map pnrTempTaskMap = (Map)pnrTempTaskAuditMgr.getPnrTempTaskUnAudits(Integer.valueOf(0), Integer.valueOf(100),userId, deptId);
//		考核待确认
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		Map evaConfirmMap = (Map)evaConfirmMgr.getTemplateUnConfirms(pageIndex, pageSize, userId, deptId);
		
		List unAuditList = new ArrayList();
		UnAuditForm unAuditForm = null;
		StringBuffer urlSb = null;
		while (iter.hasNext()) { 
			EvaTaskAudit taskAudit = (EvaTaskAudit) iter.next(); 
			if(!("".equals(taskAudit.getTemplateId()) || null == taskAudit.getTemplateId())){
				EvaTemplate template = null ;
				template = templateMgr.getTemplate(taskAudit.getTemplateId());
				taskAudit.setTemplateName(template.getTemplateName());
			}
			unAuditForm = new UnAuditForm();
			unAuditForm.setName(taskAudit.getTemplateName());
			unAuditForm.setModule("考核待审核");
			urlSb = new StringBuffer();
			urlSb.append("/eva/evaTaskAudit.do?method=auditDetail&id=");
			urlSb.append(taskAudit.getId());
			urlSb.append("&taskId=");
			urlSb.append(taskAudit.getTaskId());
			urlSb.append("&templateId=");
			urlSb.append(taskAudit.getTemplateId());
			urlSb.append("&time=");
			urlSb.append(taskAudit.getAuditTime());
			urlSb.append("&timeType=");
			urlSb.append(taskAudit.getAuditCycle());
			urlSb.append("&partner=");
			urlSb.append(taskAudit.getPartner());
			unAuditForm.setUrl(urlSb.toString());
			unAuditList.add(unAuditForm);
		}		
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		List agreementAuditlist = (List) agreementAuditMap.get("result");
		PnrAgreementAudit pnrAgreementAudit = null;
		for(int i= 0 ;agreementAuditlist.size()>i;i++){
			pnrAgreementAudit = (PnrAgreementAudit)agreementAuditlist.get(i);
			unAuditForm = new UnAuditForm();
			unAuditForm.setName(mgr.id2Name(pnrAgreementAudit.getAgreementId(),"pnrAgreementMainDao"));
			if("new".equals(pnrAgreementAudit.getAuditType())){
				unAuditForm.setModule("新建协议待确认");
			} else {
				unAuditForm.setModule("综合评分审核");
			}
			
			urlSb = new StringBuffer();
			urlSb.append("/partner/agreement/pnrAgreementAudits.do?method=audit&agreementId=");
			urlSb.append(pnrAgreementAudit.getAgreementId());
			urlSb.append("&id=");
			urlSb.append(pnrAgreementAudit.getId());
			urlSb.append("&type=");
			urlSb.append(pnrAgreementAudit.getAuditType());
			unAuditForm.setUrl(urlSb.toString());
			unAuditList.add(unAuditForm);
		}
		List pnrTempTasklist = (List) pnrTempTaskMap.get("result");	
		PnrTempTaskAudit pnrTempTaskAudit = null;
		for(int i= 0 ;pnrTempTasklist.size()>i;i++){
			pnrTempTaskAudit = (PnrTempTaskAudit)pnrTempTasklist.get(i);
			unAuditForm = new UnAuditForm();
			unAuditForm.setName(mgr.id2Name(pnrTempTaskAudit.getTempTaskId(),"pnrTempTaskMainDao"));
			unAuditForm.setModule("临时任务待审核");
			urlSb = new StringBuffer();
			urlSb.append("/partner/tempTask/pnrTempTaskMains.do?method=audit&tempTaskId=");
			urlSb.append(pnrTempTaskAudit.getTempTaskId());
			urlSb.append("&id=");
			urlSb.append(pnrTempTaskAudit.getId());
			unAuditForm.setUrl(urlSb.toString());
			unAuditList.add(unAuditForm);
		}		
		
		List evaConfirmList = (List) evaConfirmMap.get("result");
		EvaConfirm evaConfirm = null;
		for(int i= 0 ;evaConfirmList.size()>i;i++){
			evaConfirm = (EvaConfirm)evaConfirmList.get(i);
			unAuditForm = new UnAuditForm();
			unAuditForm.setName(mgr.id2Name(evaConfirm.getEvaTemplateId(),"evaTemplateTempDao"));
			unAuditForm.setModule("考核待确认");
			urlSb = new StringBuffer();
			urlSb.append("/eva/evaTemplateTemps.do?method=confirm&evaTemplateId=");
			urlSb.append(evaConfirm.getEvaTemplateId());
			urlSb.append("&confirmId=");
			urlSb.append(evaConfirm.getId());
			unAuditForm.setUrl(urlSb.toString());
			unAuditList.add(unAuditForm);
		}		
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", unAuditList.size());
		request.setAttribute("pageSize", 0);
		return mapping.findForward("unAuditList");
	}
*/
	/**
	 * 补录合同列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward contractList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String whereStr = " where 1=1 ";
		whereStr += " and pnrAgreementMain.contentId=''";
		whereStr += " and (pnrAgreementMain.partyAUser = '"+userId+"' or pnrAgreementMain.partyBUser = '"+userId+"')";
		whereStr += " and pnrAgreementMain.state = '2' ";
		Map map = (Map) pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf("0"), Integer.valueOf("100"), whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PnrAgreementConstants.PNRAGREEMENTMAIN_LIST, list);
		request.setAttribute("state", "2");
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", "100");
		return mapping.findForward("addContract");
	}  */

	/**
	 * 增加考核关联列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward addEvaList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String whereStr = " where 1=1 ";
		whereStr += " and pnrAgreementMain.evaTemplateId is null";
		whereStr += " and pnrAgreementMain.partyAUser = '"+userId+"'";
		whereStr += " and pnrAgreementMain.state = '2' ";
		Map map = (Map) pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf("0"), Integer.valueOf("100"), whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PnrAgreementConstants.PNRAGREEMENTMAIN_LIST, list);
		request.setAttribute("state", "2");
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", "100");
		return mapping.findForward("addEva");
	}	*/
	
}