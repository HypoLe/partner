package com.boco.eoms.partner.baseinfo.webapp.action;

import com.boco.eoms.base.webapp.action.BaseAction;


/**
 * <p>
 * Title:合作伙伴流程标示图
 * </p>
 * <p>
 * Description:合作伙伴流程标示图
 * </p>
 * <p>
 * 2010-07-22
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class PnrFlowAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(id);
		if("0".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "协议待确认");
		}
		if("1".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "协议待确认");
		}
		if("2".equals(pnrAgreementMain.getState())){
			IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
			EvaTask task = taskMgr.getTaskByTplAndOrg(pnrAgreementMain.getEvaTemplateId(), "");
			String taskId = task.getId();
			IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
			List AllReport = reportInfoMgr.getReportInfoByTaskId(taskId);	
			EvaReportInfo reportInfo = null;
			boolean addEdit = true;
//			判断任务是否被执行，未执行则增加修改考核信息的右键菜单
			for(int i=0;i<AllReport.size();i++){
				reportInfo = (EvaReportInfo)AllReport.get(i);
				if(!"0".equals(reportInfo.getIsPublish())){
					addEdit = false;
					break;
				}
			}
			if(addEdit){
				IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
				List confirmList  = evaConfirmMgr.getTemplateConfirms(pnrAgreementMain.getEvaTemplateId());
				EvaConfirm evaConfirm = null;
				boolean evaUnDo = true;
				for(int i = 0 ; confirmList.size()>i; i++){
					evaConfirm = (EvaConfirm)confirmList.get(i);
					if("1".equals(evaConfirm.getState())){
						evaUnDo = false;
						break;
					}
				}
				if(evaUnDo){
					request.setAttribute("下一步", "考核确认通过");
				}else{
					request.setAttribute("下一步", "考核待确认");
				}
			} else {
				request.setAttribute("下一步", "考核以执行");
			}
			request.setAttribute("下一步", "协议确认通过");
		}
		
		if("6".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "等待总结");
		}
		if("7".equals(pnrAgreementMain.getState())||"5".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "协议综合评分");
		}	
		if("3".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "综合评分待审核");
		}		
		if("4".equals(pnrAgreementMain.getState())){
			request.setAttribute("下一步", "协议综合评分审核通过");
		}		
		return null;
	}*/
	/*// 显示流程图
	public ActionForward showFlow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		String flowType =  StaticMethod.null2String(request.getParameter("flowType"));
		if("agreement".equals(flowType)){
			IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(id);
			request.setAttribute("agreeId", id);
			request.setAttribute("feeId", pnrAgreementMain.getContentId());
			if("".equals(pnrAgreementMain.getId())){
				request.setAttribute("step", "step1");
			}
			if("0".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step2");
			}
			if("1".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step2");
			}
			if("2".equals(pnrAgreementMain.getState())){
				IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
				EvaTask task = taskMgr.getTaskByTplAndOrg(pnrAgreementMain.getEvaTemplateId(), "");
				String taskId = task.getId();
				IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
				List AllReport = reportInfoMgr.getReportInfoByTaskId(taskId);	
				EvaReportInfo reportInfo = null;
				boolean addEdit = true;
//				判断任务是否被执行，未执行则增加修改考核信息的右键菜单
				for(int i=0;i<AllReport.size();i++){
					reportInfo = (EvaReportInfo)AllReport.get(i);
					if(!"0".equals(reportInfo.getIsPublish())){
						addEdit = false;
						break;
					}
				}
				if(addEdit){
					IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
					List confirmList  = evaConfirmMgr.getTemplateConfirms(pnrAgreementMain.getEvaTemplateId());
					EvaConfirm evaConfirm = null;
					boolean evaUnDo = true;
					for(int i = 0 ; confirmList.size()>i; i++){
						evaConfirm = (EvaConfirm)confirmList.get(i);
						if("1".equals(evaConfirm.getState())){
							evaUnDo = false;
							break;
						}
					}
					if(evaUnDo){
						request.setAttribute("step", "step5");
					}else{
						request.setAttribute("step", "step4");
					}
				} else {
					request.setAttribute("step", "step3");
				}
			}
			
			if("6".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step7");
			}
			if("7".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step8");
			}	
			if("5".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step9");
			}
			if("3".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step10");
			}		
			if("4".equals(pnrAgreementMain.getState())){
				request.setAttribute("step", "step11");
			}
			request.setAttribute("flowName", "agreementFlow");
		}else if("feeinfo".equals(flowType)){
			IPnrFeeInfoPlanMgr pnrFeeInfoPlanMgr = (IPnrFeeInfoPlanMgr) getBean("pnrFeeInfoPlanMgr");
			PnrFeeInfoPlan pnrFeeInfoPlan = pnrFeeInfoPlanMgr.getPnrFeeInfoPlan(id);
			request.setAttribute("planId", id);
			String state = pnrFeeInfoPlan.getPlanState();
			if("0".equals(state)){
				request.setAttribute("step", "step0");
			}
			if("1".equals(state)){
				request.setAttribute("step", "step1");
			}
			if("2".equals(state)){
				request.setAttribute("step", "step2");
			}
			if("3".equals(state)){
				request.setAttribute("step", "step3");
			}
			if("4".equals(state)){
				request.setAttribute("step", "step4");
			}
			if("5".equals(state)){
				request.setAttribute("step", "step5");
			}
			if("6".equals(state)){
				request.setAttribute("step", "step6");
			}
			if("7".equals(state)){
				request.setAttribute("step", "step7");
			}
			
			request.setAttribute("flowName", "feeinfoFlow");
		}
			return mapping.findForward("showFlow");
	}*/
	// 显示付费流程图
	/*public ActionForward showFeeFlow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		IPnrFeeInfoPlanMgr pnrFeeInfoPlanMgr = (IPnrFeeInfoPlanMgr) getBean("pnrFeeInfoPlanMgr");
		List pnrFeeInfoPlans = pnrFeeInfoPlanMgr.getPnrFeeInfoPlans(id);
		request.setAttribute("pnrFeeInfoPlans", pnrFeeInfoPlans);
		return mapping.findForward("showFeeFlow");
	}*/
	/*// 点击协议流程图进入不同的执行页面
	public ActionForward showAgreeUndoFromFlow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) getBean("pnrAgreementAuditMgr");
			IPnrAgreementWorkMgr pnrAgreementWorkMgr = (IPnrAgreementWorkMgr) getBean("pnrAgreementWorkMgr");
			IPnrAgreementEvaMgr pnrAgreementEvaMgr = (IPnrAgreementEvaMgr) getBean("pnrAgreementEvaMgr");
			String agreeId = StaticMethod.null2String(request.getParameter("agreeId"));
			String actionName = StaticMethod.null2String(request.getParameter("actionName"));
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreeId);
			if("newAudit".equals(actionName)){
				PnrAgreementAudit pnrAgreementAudit = pnrAgreementAuditMgr.getPnrAgreementAuditByState(agreeId, "1");
				if("user".equals(pnrAgreementAudit.getToOrgType())&&userId.equals(pnrAgreementAudit.getToOrgId())
					||"dept".equals(pnrAgreementAudit.getToOrgType())&&deptId.equals(pnrAgreementAudit.getToOrgId())){
					List workList = pnrAgreementWorkMgr.getPnrAgreementWorks(agreeId);
					List evaList = pnrAgreementEvaMgr.getPnrAgreementEvas(agreeId);
					PnrAgreementMainForm pnrAgreementMainForm = (PnrAgreementMainForm) convert(pnrAgreementMain);
					updateFormBean(mapping, request, pnrAgreementMainForm);
					List auditList = pnrAgreementAuditMgr.getPnrAgreementAudits(agreeId);
					if(auditList!=null&&auditList.size()>0){
						request.setAttribute("auditList", auditList);
					}
					request.setAttribute("pnrAgreementMainForm", pnrAgreementMainForm);
					request.setAttribute("type", "new");
					request.setAttribute("workList", workList);
					request.setAttribute("evaList", evaList);
					request.setAttribute("id", pnrAgreementAudit.getId());
					return mapping.findForward("auditAgreementForCreate");
				}else{
					request.setAttribute("errMsg", "您没有该操作权限！");
					return mapping.findForward("haveNoRight");
				}
				
			}else if("doEva".equals(actionName)){
				
			}
			return mapping.findForward("showFeeFlow");
	}*/

}