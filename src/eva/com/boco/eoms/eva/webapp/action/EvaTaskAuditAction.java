package com.boco.eoms.eva.webapp.action;


import java.text.DateFormat;
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

import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceForAuditMgr;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceMgr;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskAuditMgr;
import com.boco.eoms.eva.mgr.IEvaTaskDetailMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaKpiInstance;
import com.boco.eoms.eva.model.EvaKpiInstanceForAudit;
import com.boco.eoms.eva.model.EvaReportInfo;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTaskAudit;
import com.boco.eoms.eva.model.EvaTaskDetail;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.webapp.form.EvaKpiInstanceForm;
import com.boco.eoms.eva.webapp.form.EvaTaskAuditForm;


public final class EvaTaskAuditAction extends BaseAction {
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
	public ActionForward unAuditTaskList(ActionMapping mapping, ActionForm form,
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
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
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
		return mapping.findForward("unAuditTaskList");
	}
	/**
	 * 显示待审核任务详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceForAuditMgr evaKpiInstanceForAuditMgr = (IEvaKpiInstanceForAuditMgr) getBean("IevaKpiInstanceForAuditMgr");
		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		List tableList = new ArrayList();
		//审核评定人和评定部门
		String evaUser = "";
		String evaDept = "";
		String id = StaticMethod.null2String(request.getParameter("id"));
		EvaTaskAudit evaTaskAudit = auditAuditMgr.getEvaTaskAudit(id);
		evaTaskAudit.setAuditUser(userId);
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String partnerId = StaticMethod.null2String(request.getParameter("partner"));
		String time =  StaticMethod.null2String(request.getParameter("time"));
		String timeType =  StaticMethod.null2String(request.getParameter("timeType"));

		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,String.valueOf(i));

			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				EvaTaskDetail etd = (EvaTaskDetail) rowList.get(j);
				EvaKpiInstanceForm ekif = new EvaKpiInstanceForm();
				ekif.setId(etd.getId());
				ekif.setKpiId(etd.getKpiId());
				ekif.setLeaf(etd.getLeaf());
				ekif.setListNo(etd.getListNo());
				ekif.setNodeId(etd.getNodeId());
				ekif.setParentNodeId(etd.getParentNodeId());
				ekif.setRowspan(etd.getRowspan());
				ekif.setTaskId(etd.getTaskId());
				ekif.setWeight(etd.getWeight());
				ekif.setColspan(etd.getColspan());
				if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					EvaKpiInstanceForAudit ekis = evaKpiInstanceForAuditMgr
							.getKpiInstanceForAuditByTimeAndPartner(etd.getId(),
									timeType, time, partnerId);
					ekif.setRealScore(ekis.getRealScore());
					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					evaUser = ekis.getCreateUser();
					evaDept = ekis.getCreateDept();
				}

				// 算法加入
				EvaKpi ek = evaKpiMgr.getKpi(ekif.getKpiId());
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				rowListShow.add(ekif);
			}
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}
		// 找到当前taskId对应的模板name
		EvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partnerId); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("timeStr", "第"+time+"次"); // 时间
		request.setAttribute("time", time); // 时间
		request.setAttribute("templateId", et.getTemplateId()); // 模板id
		request.setAttribute("taskAudit", evaTaskAudit); // 模板id
		request.setAttribute("evaUser", evaUser); // 模板id
		request.setAttribute("evaDept", evaDept); // 模板id
		return mapping.findForward("taskDetailAuditList");
	}
	/**
	 * 执行任务审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		IEvaKpiInstanceMgr instanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaKpiInstanceForAuditMgr instanceForAuditMgr = (IEvaKpiInstanceForAuditMgr) getBean("IevaKpiInstanceForAuditMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		EvaTaskAuditForm taskAuditForm = (EvaTaskAuditForm) form;
		EvaTaskAudit taskAudit = null ;
		taskAudit = auditAuditMgr.getEvaTaskAudit(taskAuditForm.getId());
		taskAudit.setAuditUser(userId);
		taskAudit.setAuditResult(taskAuditForm.getAuditResult());
		taskAudit.setAuditRemark(taskAuditForm.getAuditRemark());
		taskAudit.setAuditDate(new java.util.Date());
		auditAuditMgr.saveEvaTaskAudit(taskAudit);

		//修改任务审核记录成功，更改任务审核状态
		List allTaskDetail = taskDetailMgr.listDetailOfTaskByListNo(taskAudit.getTaskId(),"");
		Iterator iter = allTaskDetail.iterator(); 
		EvaKpiInstanceForAudit kpiInstanceForAudit = new EvaKpiInstanceForAudit();
		while(iter.hasNext()){
			EvaTaskDetail taskDetail = null ;
			taskDetail = (EvaTaskDetail) iter.next();
			kpiInstanceForAudit = instanceForAuditMgr.getKpiInstanceForAuditByTimeAndPartner(taskDetail.getId(),taskAudit.getAuditCycle(),taskAudit.getAuditTime(),taskAudit.getPartner());
			//审核级别判断
			if(EvaConstants.AUDIT_PASS.equals(taskAuditForm.getAuditResult())){
				String[] evaNum = kpiInstanceForAudit.getScope().split("-");
				int startNum = StaticMethod.nullObject2int(evaNum[0]);
				int endNum = StaticMethod.nullObject2int(evaNum[1]);
				//清除作用时间段不是该周期的指标数据
//				if(startNum!=Integer.parseInt(kpiInstanceForAudit.getTime())){
					//更新作用时间段的指标数据
					for(int k=startNum;k<=endNum&&k!=0;k++){
						EvaKpiInstance ekis = instanceMgr
						.getKpiInstanceByTimeAndPartner(taskDetail.getId(),
								taskAudit.getAuditCycle(), String.valueOf(k), taskAudit.getPartner());
						if (ekis.getId() == null || "".equals(ekis.getId())) {
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							ekis.setCreateTime(format.format(new Date()));
						}
						ekis.setCreateUser(kpiInstanceForAudit.getCreateUser());
						ekis.setCreateDept(kpiInstanceForAudit.getCreateDept());
						ekis.setIsPublish(EvaConstants.TASK_PUBLISHED);
						ekis.setPartnerId(taskAudit.getPartner());
						ekis.setPartnerName(kpiInstanceForAudit.getPartnerName());
						ekis.setRealScore(kpiInstanceForAudit.getRealScore());
						ekis.setReduceReason(kpiInstanceForAudit.getReduceReason());
						ekis.setRemark(kpiInstanceForAudit.getRemark());
						ekis.setTaskDetailId(kpiInstanceForAudit.getTaskDetailId());
						ekis.setTime(String.valueOf(k));
						ekis.setTimeType(kpiInstanceForAudit.getTimeType());
						ekis.setScope(k+"-"+k);
						instanceMgr.saveKpiInstance(ekis);
					}
//
//					kpiInstance.setRealScore("0");
//					kpiInstance.setReduceReason("");
//					kpiInstance.setRemark("");
//				}
					kpiInstanceForAudit.setIsPublish(EvaConstants.TASK_PUBLISHED);
			}else{
				kpiInstanceForAudit.setIsPublish(EvaConstants.TASK_DENY);
			}
			instanceForAuditMgr.saveKpiInstanceForAudit(kpiInstanceForAudit);
		}
			//修改report表IsPublish字段

		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");

		EvaReportInfo pnrEvaReportInfo = reportInfoMgr.getEvaReportInfo(taskAudit.getReportId());

		if(EvaConstants.AUDIT_PASS.equals(taskAuditForm.getAuditResult())){
			pnrEvaReportInfo.setIsPublish(EvaConstants.TASK_PUBLISHED);
		}else{
			pnrEvaReportInfo.setIsPublish(EvaConstants.TASK_DENY);
		}
		reportInfoMgr.saveEvaReportInfo(pnrEvaReportInfo);
		
		//当执行了所有任务后向相关协议的乙方负责人增加一条上传总结的任务
		List allreportList = reportInfoMgr.getReportInfoByTaskId(taskAudit.getTaskId());
		boolean finish = true;
		EvaReportInfo report = null;
		for(int i=0;i<allreportList.size();i++){
			report = (EvaReportInfo)allreportList.get(i);
			if(!EvaConstants.TASK_PUBLISHED.equals((report.getIsPublish()))){
				finish = false;
				break;
			}
		}
		if(finish){
			//增加对乙方负责人的任务(将相关协议修改为)
			IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
			IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			EvaTask et = taskMgr.getTaskById(taskAudit.getTaskId());
			EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
		/*	IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(template.getAgreementId());
			pnrAgreementMain.setState(PnrAgreementConstants.AGREEMENT_STATE_UPLOAD);
			pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);*/
		}
		return mapping.findForward("refreshParent");
	}
	/**
	 * 批量执行任务审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doMoreAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String[] ids = request.getParameterValues("ids");
		String auditResult = request.getParameter("auditResult");
		String auditRemark = request.getParameter("auditRemark");
		EvaTaskAudit taskAudit = null ;
		for(int i=0;i<ids.length;i++){
			
		taskAudit = auditAuditMgr.getEvaTaskAudit(ids[i]);
		
		taskAudit.setAuditUser(userId);
		taskAudit.setAuditResult(auditResult);
		taskAudit.setAuditRemark(auditRemark);
		taskAudit.setAuditDate(new java.util.Date());
		auditAuditMgr.saveEvaTaskAudit(taskAudit);
		
		//修改任务审核记录成功，更改任务审核状态
		IEvaKpiInstanceMgr instanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceForAuditMgr instanceForAuditMgr = (IEvaKpiInstanceForAuditMgr) getBean("IevaKpiInstanceForAuditMgr");
		List allTaskDetail = taskDetailMgr.listDetailOfTaskByListNo(taskAudit.getTaskId(),"");
		Iterator iter = allTaskDetail.iterator(); 
//		EvaKpiInstance kpiInstance = new EvaKpiInstance();
		EvaKpiInstanceForAudit kpiInstanceForAudit = new EvaKpiInstanceForAudit();
		while(iter.hasNext()){
			EvaTaskDetail taskDetail = null ;
			taskDetail = (EvaTaskDetail) iter.next();
			kpiInstanceForAudit = instanceForAuditMgr.getKpiInstanceForAuditByTimeAndPartner(taskDetail.getId(),
					taskAudit.getAuditCycle(),taskAudit.getAuditTime(),taskAudit.getPartner());
			//审核级别判断
			if(EvaConstants.AUDIT_PASS.equals(auditResult)){
				String[] evaNum = kpiInstanceForAudit.getScope().split("-");
				int startNum = StaticMethod.nullObject2int(evaNum[0]);
				int endNum = StaticMethod.nullObject2int(evaNum[1]);
				//清除作用时间段不是该周期的指标数据
//				if(startNum!=Integer.parseInt(kpiInstanceForAudit.getTime())){
					//更新作用时间段的指标数据
					for(int k=startNum;k<=endNum&&k!=0;k++){
						EvaKpiInstance ekis = instanceMgr
						.getKpiInstanceByTimeAndPartner(taskDetail.getId(),
								taskAudit.getAuditCycle(), String.valueOf(k), taskAudit.getPartner());
						if (ekis.getId() == null || "".equals(ekis.getId())) {
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							ekis.setCreateTime(format.format(new Date()));
						}
						ekis.setCreateUser(kpiInstanceForAudit.getCreateUser());
						ekis.setCreateDept(kpiInstanceForAudit.getCreateDept());
						ekis.setIsPublish(EvaConstants.TASK_PUBLISHED);
						ekis.setPartnerId(taskAudit.getPartner());
						ekis.setPartnerName(kpiInstanceForAudit.getPartnerName());
						ekis.setRealScore(kpiInstanceForAudit.getRealScore());
						ekis.setReduceReason(kpiInstanceForAudit.getReduceReason());
						ekis.setRemark(kpiInstanceForAudit.getRemark());
						ekis.setTaskDetailId(kpiInstanceForAudit.getTaskDetailId());
						ekis.setTime(String.valueOf(k));
						ekis.setTimeType(kpiInstanceForAudit.getTimeType());
						ekis.setScope(k+"-"+k);
						instanceMgr.saveKpiInstance(ekis);
					}

//					kpiInstance.setRealScore("0");
//					kpiInstance.setReduceReason("");
//					kpiInstance.setRemark("");
//				}
				kpiInstanceForAudit.setIsPublish(EvaConstants.TASK_PUBLISHED);
				
			}else{
				kpiInstanceForAudit.setIsPublish(EvaConstants.TASK_DENY);
			}
			instanceForAuditMgr.saveKpiInstanceForAudit(kpiInstanceForAudit);

		}
		//修改report表IsPublish字段

			IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
			EvaReportInfo pnrEvaReportInfo = reportInfoMgr.getEvaReportInfo(taskAudit.getReportId());
		if(EvaConstants.AUDIT_PASS.equals(auditResult)){
			pnrEvaReportInfo.setIsPublish(EvaConstants.TASK_PUBLISHED);
			reportInfoMgr.saveEvaReportInfo(pnrEvaReportInfo);
		}else{
			pnrEvaReportInfo.setIsPublish(EvaConstants.TASK_DENY);
			reportInfoMgr.saveEvaReportInfo(pnrEvaReportInfo);
		}
	}
		//当执行了所有任务后向相关协议的乙方负责人增加一条上传总结的任务
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		List allreportList = reportInfoMgr.getReportInfoByTaskId(taskAudit.getTaskId());
		boolean finish = true;
		EvaReportInfo report = null;
		for(int i=0;i<allreportList.size();i++){
			report = (EvaReportInfo)allreportList.get(i);
			if(!EvaConstants.TASK_PUBLISHED.equals((report.getIsPublish()))){
				finish = false;
				break;
			}
		}
		if(finish){
			//增加对乙方负责人的任务(将相关协议修改为)
			IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			EvaTask et = taskMgr.getTaskById(taskAudit.getTaskId());
			EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
			/*IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(template.getAgreementId());
			pnrAgreementMain.setState(PnrAgreementConstants.AGREEMENT_STATE_UPLOAD);
			pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);*/
		}
		
		return mapping.findForward("success");
	}
}
