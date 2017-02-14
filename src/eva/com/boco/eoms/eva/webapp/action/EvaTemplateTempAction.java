package com.boco.eoms.eva.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.mgr.IEvaConfirmMgr;
import com.boco.eoms.eva.mgr.IEvaKpiTempMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateTempMgr;
import com.boco.eoms.eva.model.EvaConfirm;
import com.boco.eoms.eva.model.EvaKpiTemp;
import com.boco.eoms.eva.model.EvaTemplateTemp;
import com.boco.eoms.eva.util.EvaConstants;

public final class EvaTemplateTempAction extends BaseAction {


	/**
	 * 工作协议生成模板(临时任务benweiwei)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward newTemplateTemp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrAgreementEvaMgr pnrAgreementEvaMgr = (IPnrAgreementEvaMgr) getBean("pnrAgreementEvaMgr");
		IPnrAgreementWorkMgr pnrAgreementWorkMgr = (IPnrAgreementWorkMgr) getBean("pnrAgreementWorkMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
//		临时工作
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
		String remark = StaticMethod.null2String(request.getParameter("partner"));
		String agrwor = StaticMethod.null2String(request.getParameter("agrwor"));
		String evaDeptId = StaticMethod.null2String(request.getParameter("evaDeptId"));
		String confirmUser = StaticMethod.null2String(request.getParameter("confirmUser"));
		EvaTree evaDeptNode = treeMgr.getNodeByRemark(evaDeptId);
		String evaDeptNodeNodeId = evaDeptNode.getNodeId();
		EvaTree node = treeMgr.getNodeByRemark(remark);
		String parentNodeId = node.getNodeId();
		
		if("tempTask".equals(agrwor)){
//			临时工作
			List tempTaskList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(agreementId);
			PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
			EvaTemplateTempForm evaTemplateTempForm = new EvaTemplateTempForm();
			evaTemplateTempForm.setTemplateName(pnrTempTaskMain.getTempTaskName()+"-模板");
			evaTemplateTempForm.setAgreementId(pnrTempTaskMain.getId());
			evaTemplateTempForm.setAgrwor(agrwor);
			evaTemplateTempForm.setStartTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getStartTime()));
			evaTemplateTempForm.setEndTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getEndTime()));
			request.setAttribute("toOrgId",pnrTempTaskMain.getToOrgId());
			request.setAttribute("operateId",pnrTempTaskMain.getToEvaOrgId());
			request.setAttribute("evaTemplateTempForm", evaTemplateTempForm);
			request.setAttribute("tempTaskList", tempTaskList);
		}else{
			List agreementWorkList = pnrAgreementWorkMgr.getPnrAgreementWorks(agreementId);
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
			List agreementEvaList = pnrAgreementEvaMgr.getPnrAgreementEvas(agreementId);
			PnrAgreementEva pnrAgreementEva = null;
			for(int i = 0;i<agreementEvaList.size();i++){
				pnrAgreementEva = (PnrAgreementEva)agreementEvaList.get(i);
				pnrAgreementEva.setEvaName(pnrAgreementEva.getEvaName()+"(其他)");
			}			
			for(int i = 0;i<agreementWorkList.size();i++){
				PnrAgreementWork pnrAgreementWork = (PnrAgreementWork)agreementWorkList.get(i);
				pnrAgreementEva = new PnrAgreementEva();
				pnrAgreementEva.setId(pnrAgreementWork.getId());
				pnrAgreementEva.setEvaName(pnrAgreementWork.getWorkEvaName());
				pnrAgreementEva.setEvaWeight(pnrAgreementWork.getWorkEvaWeight());
				pnrAgreementEva.setEvaContent(pnrAgreementWork.getWorkEvaContent());
				pnrAgreementEva.setEvaStartTime(pnrAgreementWork.getWorkEvaStartTime());
				pnrAgreementEva.setEvaEndTime(pnrAgreementWork.getWorkEvaEndTime());
				pnrAgreementEva.setEvaCycle(pnrAgreementWork.getWorkEvaCycle());
				pnrAgreementEva.setEvaSource(pnrAgreementWork.getWorkType());
				pnrAgreementEva.setToEvaUser(pnrAgreementWork.getToOrgUser());
				pnrAgreementEva.setToEvaUserName(pnrAgreementWork.getToOrgUserName());
				pnrAgreementEva.setEvaAlgorithmType(pnrAgreementWork.getAlgorithmType());
				pnrAgreementEva.setAgreementWorkId(pnrAgreementWork.getId());
				agreementEvaList.add(pnrAgreementEva);
			}

			EvaTemplateTempForm evaTemplateTempForm = new EvaTemplateTempForm();
			evaTemplateTempForm.setTemplateName(pnrAgreementMain.getAgreementName()+"-模板");
			evaTemplateTempForm.setAgreementId(pnrAgreementMain.getId());
			evaTemplateTempForm.setContractId(pnrAgreementMain.getContentId());
			evaTemplateTempForm.setStartTime(DateTimeUtil.timestamp2String(pnrAgreementMain.getStartTime()));
			evaTemplateTempForm.setEndTime(DateTimeUtil.timestamp2String(pnrAgreementMain.getEndTime()));
			evaTemplateTempForm.setAgrwor(agrwor);
			request.setAttribute("toOrgId",pnrAgreementMain.getPartyBUser());
			request.setAttribute("operateId",pnrAgreementMain.getPartyAUser());
			request.setAttribute("evaTemplateTempForm", evaTemplateTempForm);
			request.setAttribute("agreementEvaList", agreementEvaList);
			request.setAttribute("contractId", pnrAgreementMain.getContentId());
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		// 所属地市
		JSONArray jsonRoot = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put(UIConstants.JSON_ID, sessionform.getDeptid());
		jitem.put(UIConstants.JSON_NAME, sessionform.getDeptname());
		jsonRoot.put(jitem);
		
		request.setAttribute("agrwor", agrwor);
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("evaDeptNodeNodeId", evaDeptId);
		request.setAttribute("agreementId", agreementId);
		request.setAttribute("orgDeptId", remark);
		request.setAttribute("parentNodeId", parentNodeId);
		request.setAttribute("confirmUser", confirmUser);
		return mapping.findForward("newTemplateTemp");
	}
	*/

	/**
	 * 保存考核模板（新增时确认）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward saveTemplateTemp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
		IEvaKpiTempMgr evakpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
//		String parentNodeId = StaticMethod.null2String(request.getParameter("parentNodeId"));
		String templateName = StaticMethod.null2String(request.getParameter("templateName"));
		String professional = StaticMethod.null2String(request.getParameter("professional"));
		String contractId = StaticMethod.null2String(request.getParameter("contractId"));
//		String themeName = StaticMethod.null2String(request.getParameter("themeName"));
//		String themeId = StaticMethod.null2String(request.getParameter("themeId"));
//		String orgIds = StaticMethod.null2String(request.getParameter("orgIds"));
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		String orgDeptId = StaticMethod.null2String(request.getParameter("orgDeptId"));
		String evaDeptNodeNodeId = StaticMethod.null2String(request.getParameter("evaDeptNodeNodeId"));
		String agrwor = StaticMethod.null2String(request.getParameter("agrwor"));
		String confirmUser = StaticMethod.null2String(request.getParameter("confirmUser"));
		String startTime = StaticMethod.null2String(request.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
//		合作伙伴id

		String[] kpiName = request.getParameterValues("kpiName");
		String[] weight = request.getParameterValues("weight");
		String[] algorithm = request.getParameterValues("algorithm");
		String[] kpiRemark = request.getParameterValues("kpiRemark");
		String[] evaSource = request.getParameterValues("evaSource");
		String[] evaStartTime = request.getParameterValues("evaStartTime");
		String[] evaEndTime = request.getParameterValues("evaEndTime");
		String[] cycle = request.getParameterValues("cycle");
		String[] algorithmType = request.getParameterValues("algorithmType");
		String[] toOrgUser = request.getParameterValues("toOrgUser");
		String[] toOrgUserName = request.getParameterValues("toOrgUserName");
		String[] agreementWorkId = request.getParameterValues("agreementWorkId");
		
		
//		保存临时模板信息	
		EvaTemplateTemp templateTemp = new EvaTemplateTemp();
		templateTemp.setCreator(sessionform.getUserid());
		templateTemp.setCreateTime(StaticMethod.getCurrentDateTime());
		templateTemp.setStartTime(startTime);
		templateTemp.setEndTime(endTime);
		templateTemp.setCreatorOrgId(sessionform.getDeptid());
		templateTemp.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
		templateTemp.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
		templateTemp.setDeleted(EvaConstants.UNDELETED);
		templateTemp.setProfessional(professional);
		templateTemp.setAgreementId(agreementId);
		templateTemp.setContractId(contractId);
		templateTemp.setTemplateName(templateName);
		templateTemp.setRemark(remark);
		templateTemp.setAgrwor(agrwor);
		templateTemp.setPartnerDept(orgDeptId);
		templateTemp.setOrgId(sessionform.getDeptid());
		templateTempMgr.saveEvaTemplateTemp(templateTemp);
		
		EvaConfirm evaConfirm = new EvaConfirm();
		evaConfirm.setEvaTemplateId(templateTemp.getId());
		evaConfirm.setCreateTime(StaticMethod.getLocalTime());
		evaConfirm.setOperateType("user");
		evaConfirm.setOperateId(sessionform.getUserid());
		evaConfirm.setToOrgType("user");
		evaConfirm.setToOrgId(confirmUser);
		evaConfirm.setState("1");
		evaConfirmMgr.saveEvaConfirm(evaConfirm);
//		更新相关协议or临时任务信息
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		
		if(agrwor.equals("tempTask")){
			PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
			pnrTempTaskMain.setEvaTemplateId(templateTemp.getId());
			pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
		} else {
			//更新协议信息
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
			pnrAgreementMain.setEvaTemplateId(templateTemp.getId());
			pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);
		}

//		保存临时指标信息
		for (int i = 0; i < kpiName.length; i++) {
			EvaKpiTemp ek = new EvaKpiTemp();
			ek.setEvaTemplateId(templateTemp.getId());
			ek.setKpiName(kpiName[i]);
			ek.setWeight(Float.valueOf(weight[i]));
			ek.setAlgorithm(algorithm[i]);
			ek.setRemark(kpiRemark[i]);
//				此处为新增字段---考核来源  
			ek.setEvaSource(evaSource[i]);
			ek.setCreator(sessionform.getUserid());
			ek.setDeleted(EvaConstants.UNDELETED);
			ek.setCreateTime(StaticMethod.getCurrentDateTime());
			ek.setEvaEndTime(evaEndTime[i]);
			ek.setEvaStartTime(evaStartTime[i]);
			ek.setCycle(cycle[i]);
			ek.setAlgorithmType(algorithmType[i]);
			ek.setToOrgUser(toOrgUser[i]);
			ek.setToOrgUserName(toOrgUserName[i]);
			ek.setAgreementWorkId(agreementWorkId[i]);
			evakpiTempMgr.saveKpiTemp(ek);
		}
		return mapping.findForward("refreshParent");
	}*/
	/**
	 * 保存考核模板（修改时确认）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward saveTemplateTempEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
		IEvaKpiTempMgr evakpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
//		String parentNodeId = StaticMethod.null2String(request.getParameter("parentNodeId"));
		String templateName = StaticMethod.null2String(request.getParameter("templateName"));
		String professional = StaticMethod.null2String(request.getParameter("professional"));
		String contractId = StaticMethod.null2String(request.getParameter("contractId"));
//		String themeName = StaticMethod.null2String(request.getParameter("themeName"));
//		String themeId = StaticMethod.null2String(request.getParameter("themeId"));
//		String orgIds = StaticMethod.null2String(request.getParameter("orgIds"));
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		String orgDeptId = StaticMethod.null2String(request.getParameter("orgDeptId"));
		String evaDeptNodeNodeId = StaticMethod.null2String(request.getParameter("evaDeptNodeNodeId"));
		String agrwor = StaticMethod.null2String(request.getParameter("agrwor"));
		String confirmUser = StaticMethod.null2String(request.getParameter("confirmUser"));
		String startTime = StaticMethod.null2String(request.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
//		合作伙伴id

		String[] kpiName = request.getParameterValues("kpiName");
		String[] weight = request.getParameterValues("weight");
		String[] algorithm = request.getParameterValues("algorithm");
		String[] kpiRemark = request.getParameterValues("kpiRemark");
		String[] evaSource = request.getParameterValues("evaSource");
		String[] evaStartTime = request.getParameterValues("evaStartTime");
		String[] evaEndTime = request.getParameterValues("evaEndTime");
		String[] cycle = request.getParameterValues("cycle");
		String[] algorithmType = request.getParameterValues("algorithmType");
		String[] toOrgUser = request.getParameterValues("toOrgUser");
		String[] toOrgUserName = request.getParameterValues("toOrgUserName");
		String[] agreementWorkId = request.getParameterValues("agreementWorkId");
		
		
//		保存临时模板信息	
		EvaTemplateTemp templateTemp = new EvaTemplateTemp();
		templateTemp.setCreator(sessionform.getUserid());
		templateTemp.setCreateTime(StaticMethod.getCurrentDateTime());
		templateTemp.setStartTime(startTime);
		templateTemp.setEndTime(endTime);
		templateTemp.setCreatorOrgId(sessionform.getDeptid());
		templateTemp.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
		templateTemp.setActivated(EvaConstants.TEMPLATE_NOTACTIVATED);
		templateTemp.setDeleted(EvaConstants.UNDELETED);
		templateTemp.setProfessional(professional);
		templateTemp.setAgreementId(agreementId);
		templateTemp.setContractId(contractId);
		templateTemp.setTemplateName(templateName);
		templateTemp.setRemark(remark);
		templateTemp.setAgrwor(agrwor);
		templateTemp.setPartnerDept(orgDeptId);
		templateTemp.setOrgId(sessionform.getDeptid());
		templateTempMgr.saveEvaTemplateTemp(templateTemp);
		
		EvaConfirm evaConfirm = new EvaConfirm();
		evaConfirm.setEvaTemplateId(templateTemp.getId());
		evaConfirm.setCreateTime(StaticMethod.getLocalTime());
		evaConfirm.setOperateType("user");
		evaConfirm.setOperateId(sessionform.getUserid());
		evaConfirm.setToOrgType("user");
		evaConfirm.setToOrgId(confirmUser);
		evaConfirm.setState("1");
		evaConfirmMgr.saveEvaConfirm(evaConfirm);
//		更新相关协议or临时任务信息
		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		
		if(agrwor.equals("tempTask")){
			PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
			pnrTempTaskMain.setEvaTemplateId(templateTemp.getId());
			pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
		} else {
			//更新协议信息
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
			pnrAgreementMain.setEvaTemplateId(templateTemp.getId());
			pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);
		}

//		保存临时指标信息
		for (int i = 0; i < kpiName.length; i++) {
			EvaKpiTemp ek = new EvaKpiTemp();
			ek.setEvaTemplateId(templateTemp.getId());
			ek.setKpiName(kpiName[i]);
			ek.setWeight(Float.valueOf(weight[i]));
			ek.setAlgorithm(algorithm[i]);
			ek.setRemark(kpiRemark[i]);
//				此处为新增字段---考核来源  
			ek.setEvaSource(evaSource[i]);
			ek.setCreator(sessionform.getUserid());
			ek.setDeleted(EvaConstants.UNDELETED);
			ek.setCreateTime(StaticMethod.getCurrentDateTime());
			ek.setEvaEndTime(evaEndTime[i]);
			ek.setEvaStartTime(evaStartTime[i]);
			ek.setCycle(cycle[i]);
			ek.setAlgorithmType(algorithmType[i]);
			ek.setToOrgUser(toOrgUser[i]);
			ek.setToOrgUserName(toOrgUserName[i]);
			ek.setAgreementWorkId(agreementWorkId[i]);
			evakpiTempMgr.saveKpiTemp(ek);
		}
//		删除以创建的树图任务及考核信息
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		if(taskList.size()>0){
			EvaTask task = (EvaTask) taskList.get(0);
			IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr)  getBean("IevaTaskDetailMgr");
			List taskDetaiList = taskDetailMgr.listDetailOfTaskByListNo(task.getId(), "");
			for(int i = 0 ; taskDetaiList.size()>i;i++){
				EvaTaskDetail taskDetail = (EvaTaskDetail) taskDetaiList.get(i);
				taskDetailMgr.removeTaskDetail(taskDetail);
			}
			IEvaReportInfoMgr evaReportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
			List evaReportList = evaReportInfoMgr.getReportInfoByTaskId(task.getId());
			for(int i = 0 ; evaReportList.size()>i;i++){
				EvaReportInfo evaReportInfo = (EvaReportInfo) evaReportList.get(i);
				evaReportInfoMgr.removeEvaReportInfo(evaReportInfo);
			}
		}
		
		List evaKpiList = treeMgr.getEvaKpiByParentNodeId(nodeId);
		EvaTree evaTree = null;
		for(int i = 0;evaKpiList.size()>i;i++){
			evaTree = (EvaTree)evaKpiList.get(i);
			kpiMgr.removeEvaKpi(evaTree.getObjectId());
		}		
		
		taskMgr.removeTaskOfTemplate(template.getId());		
		treeMgr.removeTreeNodeByNodeId(nodeId);
		templateMgr.removeTemplate(template);		
		return mapping.findForward("refresh");
	}	*/
	/**
	 * 修改考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateTemplateTemp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
		IEvaKpiTempMgr evaKpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		String startTime = StaticMethod.null2String(request.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
//		合作伙伴id
		
		String[] kpiName = request.getParameterValues("kpiName");
		String[] weight = request.getParameterValues("weight");
		String[] algorithm = request.getParameterValues("algorithm");
		String[] kpiRemark = request.getParameterValues("kpiRemark");
		String[] evaSource = request.getParameterValues("evaSource");
		String[] evaStartTime = request.getParameterValues("evaStartTime");
		String[] evaEndTime = request.getParameterValues("evaEndTime");
		String[] cycle = request.getParameterValues("cycle");
		String[] algorithmType = request.getParameterValues("algorithmType");
		String[] toOrgUser = request.getParameterValues("toOrgUser");
		String[] toOrgUserName = request.getParameterValues("toOrgUserName");
		String[] agreementWorkId = request.getParameterValues("agreementWorkId");
		
//		得到临时模板Id	，并保存临时模板信息
		String evaTemplateId = StaticMethod.null2String(request.getParameter("evaTemplateId"));
		EvaTemplateTemp evaTemplateTemp  = templateTempMgr.getEvaTemplateTemp(evaTemplateId);
		
		evaTemplateTemp.setStartTime(startTime);
		evaTemplateTemp.setEndTime(endTime);
		evaTemplateTemp.setRemark(remark);
		templateTempMgr.saveEvaTemplateTemp(evaTemplateTemp);
		
//		修改得到的模板确认信息
		String confirmId = StaticMethod.null2String(request.getParameter("confirmId"));
		String remarkConfirm = StaticMethod.null2String(request.getParameter("remarkConfirm"));
		EvaConfirm evaConfirm = evaConfirmMgr.getTemplateConfirmById(confirmId);
		
		evaConfirm.setState("2");
		evaConfirm.setOperateTime(StaticMethod.getLocalTime());
		evaConfirm.setRemark(remarkConfirm);
		evaConfirm.setConfirmResult("1");
		evaConfirmMgr.saveEvaConfirm(evaConfirm);
//		新增新的待确认信息
		EvaConfirm evaConfirmNew = new EvaConfirm();
		evaConfirmNew.setEvaTemplateId(evaConfirm.getEvaTemplateId());
		
		evaConfirmNew.setCreateTime(StaticMethod.getLocalTime());
		evaConfirmNew.setOperateType("user");
		evaConfirmNew.setOperateId(sessionform.getUserid());
		evaConfirmNew.setToOrgType("user");
		evaConfirmNew.setToOrgId(evaConfirm.getOperateId());
		evaConfirmNew.setState("1");
		evaConfirmMgr.saveEvaConfirm(evaConfirmNew);
		
		String partAuser = "";
		List evaKpiList = evaKpiTempMgr.getEvaKpiTemps(evaTemplateId);
		for(int i = 0; i < evaKpiList.size(); i++){
			EvaKpiTemp evaKpiTemp = (EvaKpiTemp)evaKpiList.get(i);
			if(i==0){
				partAuser = evaKpiTemp.getCreator();
			}
			evaKpiTempMgr.removeKpiTemp(evaKpiTemp);
		}
//		保存临时指标信息
		for (int i = 0; i < kpiName.length; i++) {
			EvaKpiTemp ek = new EvaKpiTemp();
			ek.setEvaTemplateId(evaTemplateTemp.getId());
			ek.setKpiName(kpiName[i]);
			ek.setWeight(Float.valueOf(weight[i]));
			ek.setAlgorithm(algorithm[i]);
			ek.setRemark(kpiRemark[i]);
//				此处为新增字段---考核来源  
			ek.setEvaSource(evaSource[i]);
			ek.setCreator(partAuser);
			ek.setDeleted(EvaConstants.UNDELETED);
			ek.setCreateTime(StaticMethod.getCurrentDateTime());
			ek.setEvaEndTime(evaEndTime[i]);
			ek.setEvaStartTime(evaStartTime[i]);
			ek.setCycle(cycle[i]);
			ek.setAlgorithmType(algorithmType[i]);
			ek.setToOrgUser(toOrgUser[i]);
			ek.setToOrgUserName(toOrgUserName[i]);
			ek.setAgreementWorkId(agreementWorkId[i]);
			evaKpiTempMgr.saveKpiTemp(ek);
		}

		// 保存完成后返回模板编辑页面
		return mapping.findForward("refreshParent");
	}


    /**
	 * 得到待确认列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    /*public ActionForward getUnConfirmList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrAgreementConstants.PNRAGREEMENTAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");

		Map map = map = (Map)evaConfirmMgr.getTemplateUnConfirms(pageIndex, pageSize, userId, deptId);
		List unConfirmList = (List) map.get("result");
		request.setAttribute("unConfirmList", unConfirmList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("unConfirmList");
	}*/
	
    /**
	 * 增加考核项
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward createEva(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		String tableId = StaticMethod.nullObject2String(request.getParameter("tableId"));
		String tableMax = StaticMethod.nullObject2String(request.getParameter("tableMax"));
		String allWeight = StaticMethod.nullObject2String(request.getParameter("allWeight"));
		String toOrgId = StaticMethod.nullObject2String(request.getParameter("toOrgId"));
		String toOrg[] = toOrgId.split(",");
		String orgData = "";
		for(int i=0 ;!toOrgId.equals("")&&i<toOrg.length;i++){
			if(i!=0){
				orgData = orgData+",";
			}
			orgData = orgData+"{id:'"+toOrg[i]+"',name:'"+mgr.id2Name(toOrg[i], "tawSystemUserDao")+"',nodeType:'user'}";
		}
		if(!"".equals(orgData)){
			orgData = "["+orgData+"]";
		}
		request.setAttribute("tableId", tableId);
		request.setAttribute("tableMax", tableMax);
		request.setAttribute("allWeight", allWeight);
		request.setAttribute("orgData",orgData);
		return mapping.findForward("createEva");
	}    

    /**
	 * 得到需确认的模板信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward confirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String confirmId = StaticMethod.nullObject2String(request.getParameter("confirmId"));
		String evaTemplateId = StaticMethod.nullObject2String(request.getParameter("evaTemplateId"));
		
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
		IEvaKpiTempMgr evaKpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		
		EvaTemplateTemp evaTemplateTemp = templateTempMgr.getEvaTemplateTemp(evaTemplateId);
		List evaKpiList = evaKpiTempMgr.getEvaKpiTemps(evaTemplateId);
		List confirmList  = evaConfirmMgr.getTemplateConfirms(evaTemplateId);
		
		request.setAttribute("evaTemplateTemp", evaTemplateTemp);
		request.setAttribute("evaKpiList", evaKpiList);
		if(confirmList!=null&&confirmList.size()>0){
			request.setAttribute("confirmList", confirmList);
		}
		request.setAttribute("confirmId", confirmId);
		request.setAttribute("evaTemplateId", evaTemplateId);
//		EvaTemplateTempForm evaTemplateTempForm = (EvaTemplateTempForm) convert(evaTemplateTemp);
//		updateFormBean(mapping, request, evaTemplateTempForm);
		return mapping.findForward("confirmTemplateTemp");
	}    
    /**
	 * 得到需编辑的模板信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String confirmId = StaticMethod.nullObject2String(request.getParameter("confirmId"));
		String evaTemplateId = StaticMethod.nullObject2String(request.getParameter("evaTemplateId"));
		
		IEvaTemplateTempMgr templateTempMgr = (IEvaTemplateTempMgr) getBean("IevaTemplateTempMgr");
		IEvaKpiTempMgr evaKpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
		IEvaConfirmMgr evaConfirmMgr = (IEvaConfirmMgr) getBean("IevaConfirmMgr");
		
		EvaTemplateTemp evaTemplateTemp = templateTempMgr.getEvaTemplateTemp(evaTemplateId);
		List evaKpiList = evaKpiTempMgr.getEvaKpiTemps(evaTemplateId);
		List confirmList  = evaConfirmMgr.getTemplateConfirms(evaTemplateId);
		
		request.setAttribute("evaTemplateTemp", evaTemplateTemp);
		request.setAttribute("evaKpiList", evaKpiList);
		if(confirmList!=null&&confirmList.size()>0){
			request.setAttribute("confirmList", confirmList);
		}
		request.setAttribute("confirmId", confirmId);
		request.setAttribute("evaTemplateId", evaTemplateId);
//		EvaTemplateTempForm evaTemplateTempForm = (EvaTemplateTempForm) convert(evaTemplateTemp);
//		updateFormBean(mapping, request, evaTemplateTempForm);
		return mapping.findForward("edit");
	}    

	/**
	 * 修改模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward editTemplateTemp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		
		String agreementId = template.getAgreementId();

		IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
		IPnrAgreementEvaMgr pnrAgreementEvaMgr = (IPnrAgreementEvaMgr) getBean("pnrAgreementEvaMgr");
		IPnrAgreementWorkMgr pnrAgreementWorkMgr = (IPnrAgreementWorkMgr) getBean("pnrAgreementWorkMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
//		临时工作
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		String remark = template.getPartnerDept();
		String agrwor = template.getAgrwor();
//		String evaDeptId = StaticMethod.null2String(request.getParameter("evaDeptId"));
		String confirmUser = StaticMethod.null2String(request.getParameter("confirmUser"));
//		EvaTree evaDeptNode = treeMgr.getNodeByRemark(evaDeptId);
//		String evaDeptNodeNodeId = evaDeptNode.getNodeId();
//		EvaTree node = treeMgr.getNodeByRemark(remark);
//		String parentNodeId = node.getNodeId();
		
		if("tempTask".equals(agrwor)){
//			临时工作
			List tempTaskList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(agreementId);
			PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(agreementId);
			EvaTemplateTempForm evaTemplateTempForm = new EvaTemplateTempForm();
			evaTemplateTempForm.setTemplateName(pnrTempTaskMain.getTempTaskName()+"-模板");
			evaTemplateTempForm.setAgreementId(pnrTempTaskMain.getId());
			evaTemplateTempForm.setAgrwor(agrwor);
			evaTemplateTempForm.setStartTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getStartTime()));
			evaTemplateTempForm.setEndTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getEndTime()));
			confirmUser = pnrTempTaskMain.getToOrgId();
			request.setAttribute("toOrgId",pnrTempTaskMain.getToOrgId());
			request.setAttribute("operateId",pnrTempTaskMain.getToEvaOrgId());
			request.setAttribute("evaTemplateTempForm", evaTemplateTempForm);
			request.setAttribute("tempTaskList", tempTaskList);
		}else{
			List agreementWorkList = pnrAgreementWorkMgr.getPnrAgreementWorks(agreementId);
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreementId);
			List agreementEvaList = pnrAgreementEvaMgr.getPnrAgreementEvas(agreementId);
			PnrAgreementEva pnrAgreementEva = null;
			for(int i = 0;i<agreementEvaList.size();i++){
				pnrAgreementEva = (PnrAgreementEva)agreementEvaList.get(i);
				pnrAgreementEva.setEvaName(pnrAgreementEva.getEvaName()+"(其他)");
			}			
			for(int i = 0;i<agreementWorkList.size();i++){
				PnrAgreementWork pnrAgreementWork = (PnrAgreementWork)agreementWorkList.get(i);
				pnrAgreementEva = new PnrAgreementEva();
				pnrAgreementEva.setId(pnrAgreementWork.getId());
				pnrAgreementEva.setEvaName(pnrAgreementWork.getWorkEvaName());
				pnrAgreementEva.setEvaWeight(pnrAgreementWork.getWorkEvaWeight());
				pnrAgreementEva.setEvaContent(pnrAgreementWork.getWorkEvaContent());
				pnrAgreementEva.setEvaStartTime(pnrAgreementWork.getWorkEvaStartTime());
				pnrAgreementEva.setEvaEndTime(pnrAgreementWork.getWorkEvaEndTime());
				pnrAgreementEva.setEvaCycle(pnrAgreementWork.getWorkEvaCycle());
				pnrAgreementEva.setEvaSource(pnrAgreementWork.getWorkType());
				pnrAgreementEva.setToEvaUser(pnrAgreementWork.getToOrgUser());
				pnrAgreementEva.setToEvaUserName(pnrAgreementWork.getToOrgUserName());
				pnrAgreementEva.setEvaAlgorithmType(pnrAgreementWork.getAlgorithmType());
				pnrAgreementEva.setAgreementWorkId(pnrAgreementWork.getId());
				agreementEvaList.add(pnrAgreementEva);
			}

			EvaTemplateTempForm evaTemplateTempForm = new EvaTemplateTempForm();
			evaTemplateTempForm.setTemplateName(pnrAgreementMain.getAgreementName()+"-模板");
			evaTemplateTempForm.setAgreementId(pnrAgreementMain.getId());
			evaTemplateTempForm.setContractId(pnrAgreementMain.getContentId());
			evaTemplateTempForm.setStartTime(DateTimeUtil.timestamp2String(pnrAgreementMain.getStartTime()));
			evaTemplateTempForm.setEndTime(DateTimeUtil.timestamp2String(pnrAgreementMain.getEndTime()));
			evaTemplateTempForm.setAgrwor(agrwor);
			confirmUser = pnrAgreementMain.getPartyBUser();
			request.setAttribute("toOrgId",pnrAgreementMain.getPartyBUser());
			request.setAttribute("operateId",pnrAgreementMain.getPartyAUser());
			request.setAttribute("evaTemplateTempForm", evaTemplateTempForm);
			request.setAttribute("agreementEvaList", agreementEvaList);
			request.setAttribute("contractId", pnrAgreementMain.getContentId());
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		// 所属地市
		JSONArray jsonRoot = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put(UIConstants.JSON_ID, sessionform.getDeptid());
		jitem.put(UIConstants.JSON_NAME, sessionform.getDeptname());
		jsonRoot.put(jitem);
		
		request.setAttribute("agrwor", agrwor);
		request.setAttribute("orgIds", jsonRoot.toString());
//		request.setAttribute("evaDeptNodeNodeId", evaDeptId);
		request.setAttribute("agreementId", agreementId);
		request.setAttribute("orgDeptId", remark);
//		request.setAttribute("parentNodeId", parentNodeId);
		request.setAttribute("confirmUser", confirmUser);
		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("newTemplateTempEdit");
	}    */
}
