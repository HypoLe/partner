package com.boco.eoms.partner.baseinfo.webapp.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskAuditMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskExeMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskMainMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskWork;


public final class PnrMsgAction extends BaseAction {
	
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
		return getMsg(mapping, form, request, response);
	}
	
 	
 	/**
	 * ������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward getMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		StringBuffer msgStr = new StringBuffer();
		
	/*	IPnrFeeInfoPlanMgr pnrFeeInfoPlanMgr = (IPnrFeeInfoPlanMgr) getBean("pnrFeeInfoPlanMgr");
		IPnrFeeInfoAuditMgr pnrFeeInfoAuditMgr = (IPnrFeeInfoAuditMgr) getBean("pnrFeeInfoAuditMgr");
		
		IPnrFeeInfoPayMgr pnrFeeInfoPayMgr = (IPnrFeeInfoPayMgr) getBean("pnrFeeInfoPayMgr");
		*///付款计划到期提醒
		Timestamp time = StaticMethod.getTimestamp(5);//提前5天提醒
		StringBuffer feePlanwhere = new StringBuffer();
		
		feePlanwhere.append(" and plan.planPayTime  <= ' ");
		feePlanwhere.append(StaticMethod.getLocalString(5));
		feePlanwhere.append("'");
		/*Map partnerFeePlanMap = pnrFeeInfoPayMgr.getPnrFeeInfoUn(Integer.valueOf(0),Integer.valueOf(10000), userId, deptId, feePlanwhere.toString());
		List partnerFeePlanList =	(List)  partnerFeePlanMap.get("result");
		if(partnerFeePlanList.size()>0){
			msgStr.append("您的部门有"+partnerFeePlanList.size()+"条付款计划即将到期:<br>");
			msgStr.append("<a href='"+request.getContextPath()+"/partner/feeInfo/pnrFeeInfoPays.do?method=undoList' target='_blank'>查看列表</a><br>");
		}*/
		//付款计划待审核提醒
		/*Map unAuditmap = pnrFeeInfoAuditMgr.getPnrFeeInfoUnAudits(Integer.valueOf(0),Integer.valueOf(10000),userId, deptId,"feeInfo");
		List partnerFeePlanAuditList = (List) unAuditmap.get("result");
		PartnerFeeAudit partnerFeeAudit = null;
		for(int i=0;i<partnerFeePlanAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+partnerFeePlanAuditList.size()+"条待审核的付款计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/feeInfo/pnrFeeInfoMains.do?method=getUnAuditList&type=feeInfo' target='_blank'>查看列表</a><br>");
			}
//			partnerFeeAudit = (PartnerFeeAudit)partnerFeePlanAuditList.get(i);
		}*/
		//付款计划被驳回提醒
		/*IPnrFeeInfoMainMgr pnrFeeInfoMainMgr = (IPnrFeeInfoMainMgr) getBean("pnrFeeInfoMainMgr");
		String rejectFeewhere = " where 1=1 and pnrFeeInfoMain.state='1'";
		rejectFeewhere += " and pnrFeeInfoMain.payUnit='"+deptId+"'";
		Map rejectFeePlanMap = pnrFeeInfoMainMgr.getPnrFeeInfoMains(Integer.valueOf(0),Integer.valueOf(10000), rejectFeewhere);
		List rejectFeePlanList = (List) rejectFeePlanMap.get("result");
		PartnerFeePlan rejectFeePlan = null;
		for(int i=0;i<rejectFeePlanList.size();i++){
			if(i==0){
				msgStr.append("您有"+rejectFeePlanList.size()+"条被驳回的付款计划:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/feeInfo/pnrFeeInfoMains.do?state=1' target='_blank'>查看列表</a><br>");
			}
//			rejectFeePlan = (PartnerFeePlan)rejectFeePlanList.get(i);
		}*/
		
		//待执行付款任务提醒
		
//		List pnrFeeInfoPaymaplist = (List) pnrFeeInfoPayMgr.getPnrFeeInfoUndo(userId,deptId);

		/*Map pnrFeeInfoPaymapNum = new HashMap();
		PnrFeeInfoPlan pnrFeeInfoPlan = new PnrFeeInfoPlan();
		for(int i=0;i<pnrFeeInfoPaymaplist.size();i++){
			pnrFeeInfoPlan=(PnrFeeInfoPlan)pnrFeeInfoPaymaplist.get(i);
			pnrFeeInfoPaymapNum.put(pnrFeeInfoPlan.getFeeId(), String.valueOf(StaticMethod.nullObject2int(pnrFeeInfoPaymapNum.get(pnrFeeInfoPlan.getFeeId()), 0)+1));
		}
		if(pnrFeeInfoPaymapNum.size()>0){
			msgStr.append("您有"+pnrFeeInfoPaymapNum.size()+"条待执行的付款计划(包含"+pnrFeeInfoPaymaplist.size()+"个付款任务):<br>");
			msgStr.append("<a href='"+request.getContextPath()+"/partner/feeInfo/pnrFeeInfoPays.do?method=undoList' target='_blank'>查看列表</a><br>");
		}*/
//		IPnrWorkplanExeMgr pnrWorkplanExeMgr = (IPnrWorkplanExeMgr) getBean("pnrWorkplanExeMgr");
//		//得到待执行项
//		Map exemap = (Map) pnrWorkplanExeMgr.getPnrWorkplanUndo(Integer.valueOf(0),Integer.valueOf(1000),userId,deptId);
//
//	    PnrWorkplanExe pnrWorkplanExe = null;
//	    List pnrWorkplanExeList = (List) exemap.get("result");
//		Map workNum = new HashMap();
//		PnrWorkplanWork pnrWorkplanWork = new PnrWorkplanWork();
//		for(int i=0;i<pnrWorkplanExeList.size();i++){
//			pnrWorkplanWork=(PnrWorkplanWork)pnrWorkplanExeList.get(i);
//			workNum.put(pnrWorkplanWork.getWorkplanId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrWorkplanWork.getWorkplanId()), 0)+1));
//		}
//		for(int i=0;i<pnrWorkplanExeList.size();i++){
//			if(i==0){
//				msgStr.append("您有"+workNum.size()+"条待执行的工作计划(包含"+pnrWorkplanExeList.size()+"个工作任务):<br>");
//				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanWorks.do?method=undoList' target='_blank'>查看列表</a><br>");
//			}
//		}		
		//工作计划驳回提醒
//		IPnrWorkplanMainMgr pnrWorkplanMainMgr = (IPnrWorkplanMainMgr) getBean("pnrWorkplanMainMgr");
//	    PnrWorkplanMain pnrWorkplanMain = null;
//	    StringBuffer pwpwhere = new StringBuffer();
//	    pwpwhere.append(" where 1=1 and pnrWorkplanMain.state = '1'  and pnrWorkplanMain.createUser = '");
//	    pwpwhere.append(userId);
//	    pwpwhere.append("' and pnrWorkplanMain.createDept='");
//	    pwpwhere.append(deptId);
//	    pwpwhere.append("'");
//		Map  pnrWorkplanMainMap = pnrWorkplanMainMgr.getPnrWorkplanMains(Integer.valueOf(0),Integer.valueOf(1000), pwpwhere.toString());
//		List pnrWorkplanMainList = (List) pnrWorkplanMainMap.get("result");
//		for(int i=0;i<pnrWorkplanMainList.size();i++){
//			if(i==0){
//				msgStr.append("您有"+pnrWorkplanMainList.size()+"条被驳回的工作计划:<br>");
//				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanMains.do?state=1' target='_blank'>查看列表</a><br>");
//			}
//			pnrWorkplanMain = (PnrWorkplanMain)pnrWorkplanMainList.get(i);
//		}	
		//待审核工作计划提醒
//		IPnrWorkplanAuditMgr pnrWorkplanAuditMgr = (IPnrWorkplanAuditMgr) getBean("pnrWorkplanAuditMgr");
//		Map map = (Map)pnrWorkplanAuditMgr.getPnrWorkplanUnAudits(Integer.valueOf(0),Integer.valueOf(1000),userId, deptId);
//
//	    PnrWorkplanAudit pnrWorkplanAudit = null;
//	    List pnrWorkplanAuditList = (List) map.get("result");
//		for(int i=0;i<pnrWorkplanAuditList.size();i++){
//			if(i==0){
//				msgStr.append("您有"+pnrWorkplanAuditList.size()+"条待审核的工作计划:<br>");
//				msgStr.append("<a href='"+request.getContextPath()+"/partner/workplan/pnrWorkplanMains.do?method=getUnAuditList' target='_blank'>查看列表</a><br>");
//			}
//			pnrWorkplanAudit = (PnrWorkplanAudit)pnrWorkplanAuditList.get(i);
//		}			

		//待执行工作计划提醒
		/*IPnrWorkplanExeMgr pnrWorkplanExeMgr = (IPnrWorkplanExeMgr) getBean("pnrWorkplanExeMgr");
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
		}	*/
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

		//新建协议驳回提醒(驳回暂时取消)
//		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
//	    PnrAgreementMain pnrAgreementMain = null;
//	    StringBuffer pawhere = new StringBuffer();
//	    pawhere.append(" where 1=1 and pnrAgreementMain.createUser = '");
//	    pawhere.append(userId);
//	    pawhere.append("'  and pnrAgreementMain.state ='1'");
//		Map  pnrAgreementMainMap = pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(1000), pawhere.toString());
//		List pnrAgreementList = (List) pnrAgreementMainMap.get("result");
//		for(int i=0;i<pnrAgreementList.size();i++){
//			if(i==0){
//				msgStr.append("您有"+pnrAgreementList.size()+"条新建的服务协议被驳回:<br>");
//				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=newReject' target='_blank'>查看列表</a><br>");
//			}
//			pnrAgreementMain = (PnrAgreementMain)pnrAgreementList.get(i);
//		}
		
		//协议关闭驳回提醒
		
	/*	String retpnrWhere = " where 1=1  and pnrAgreementMain.partyAUser = '"+userId+"'";
		retpnrWhere += "  and pnrAgreementMain.state in ('5','7')";

		Map  retpnrWhereMap  = (Map) pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(100), retpnrWhere);
		List retpnrWhereList = (List) retpnrWhereMap.get("result");
		for(int i=0;i<retpnrWhereList.size();i++){
			if(i==0){
				msgStr.append("您有"+retpnrWhereList.size()+"条等待综合分的协议:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=toClose' target='_blank'>查看列表</a><br>");
			}
		}	*/		
		//协议到时提醒
		String date = StaticMethod.getLocalString(5);
	    StringBuffer overWhere = new StringBuffer();
	    overWhere.append(" where 1=1 and pnrAgreementMain.state = '2'  and pnrAgreementMain.partyAUser = '");
	    overWhere.append(userId);
	    overWhere.append("' and pnrAgreementMain.endTime <= '");
	    overWhere.append(date);
	    overWhere.append("' ");
		/*Map  pnrAgreementMainoverMap = pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(100), overWhere.toString());
		List pnrAgreementoverList = (List) pnrAgreementMainoverMap.get("result");
		for(int i=0;i<pnrAgreementoverList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrAgreementoverList.size()+"条即将到期的协议:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=prompt' target='_blank'>查看列表</a><br>");
			}
		}	
	
		//新建的协议送审提醒
		IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) getBean("pnrAgreementAuditMgr");*/
//		String whereStrx =  " where 1=1 and pnrAgreementMain.partyAUser = '"+userId+"'";
//		whereStrx += " and pnrAgreementMain.state ='3' ";
//		Map  pnrAgreementClosedunMap  = (Map) pnrAgreementAuditMgr.getPnrAgreementUnAudits(Integer.valueOf(0),Integer.valueOf(100),userId, deptId,"close");
//		List pnrAgreementClosedunList = (List) pnrAgreementClosedunMap.get("result");
//		for(int i=0;i<pnrAgreementClosedunList.size();i++){
//			if(i==0){
//				msgStr.append("您有"+pnrAgreementClosedunList.size()+"条要新建的协议需要审核:<br>");
//				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=auditing' target='_blank'>查看列表</a><br>");
//			}
//		}
		
		//协议待总结列表
//		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		String whereStr = " where ";
		whereStr += " pnrAgreementMain.partyBUser = '"+userId+"'";
		whereStr += " and pnrAgreementMain.state ='6'";
	/*	Map agreementUpdateMap = (Map) pnrAgreementMainMgr.getPnrAgreementMains(Integer.valueOf(0),Integer.valueOf(100), whereStr);
		List agreementUpdateList = (List) agreementUpdateMap.get("result");
		if(agreementUpdateList.size() != 0){
			msgStr.append("您有"+agreementUpdateList.size()+"条待总结协议:<br>");
			msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?state=upload' target='_blank'>查看列表</a><br>");
		}
			*/	
		//归档的协议送审提醒

	/*	Map  pnrAgreementClosedunMap  = (Map) pnrAgreementAuditMgr.getPnrAgreementUnAudits(Integer.valueOf(0),Integer.valueOf(100),userId, deptId,"close");
		List pnrAgreementClosedunList = (List) pnrAgreementClosedunMap.get("result");
		for(int i=0;i<pnrAgreementClosedunList.size();i++){
			if(i==0){
				msgStr.append("您有"+pnrAgreementClosedunList.size()+"条要归档的协议需要审核:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?method=getUnAuditList&type=close' target='_blank'>查看列表</a><br>");
			}
		}	
		*/
		//新建的协议送审提醒
		
		/*PnrAgreementAudit pnrAgreementAudit = null;
		Map agreementUnauditMap = (Map)pnrAgreementAuditMgr.getPnrAgreementUnAudits(Integer.valueOf(0),Integer.valueOf(100),userId, deptId,"new");
		List unAuditList = (List) agreementUnauditMap.get("result");
		for(int i=0;i<unAuditList.size();i++){
			if(i==0){
				msgStr.append("您有"+unAuditList.size()+"条要新建的协议需要确认:<br>");
				msgStr.append("<a href='"+request.getContextPath()+"/partner/agreement/pnrAgreementMains.do?method=getUnAuditList&type=new' target='_blank'>查看列表</a><br>");
			}
			pnrAgreementAudit = (PnrAgreementAudit)unAuditList.get(i);
		}	*/
		
		request.setAttribute("msg", msgStr.toString());		
		return mapping.findForward("msgWin");
	}
	
}