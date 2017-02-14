package com.boco.eoms.partner.eva.webapp.action;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiInstanceMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaReportInfoMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskAuditMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskDetailMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTaskAudit;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;
import com.boco.eoms.partner.eva.util.PnrEvaStaticMethod;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiInstanceForm;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaTaskAuditForm;


public final class PnrEvaTaskAuditAction extends BaseAction {
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
		String operateType = PnrEvaConstants.OPERATE_REPORT_AUDIT;
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
		
		IPnrEvaTaskAuditMgr auditAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		String hSql = " where auditResult='"+PnrEvaConstants.AUDIT_WAIT+"' and  ((auditOrgType = 'user' and auditOrg = '"+userId+"') or (auditOrgType = 'dept' and auditOrg = '"+deptId+"') " +
				" or (auditOrgType = 'subRole' and auditOrg = '"+subRoleId+"'))";
		Map map = (Map) auditAuditMgr.getPnrEvaTaskAuditByOrgType(pageIndex, pageSize, hSql);
		List list = (List) map.get("result");
		//查询出模板Id的模板名称，存在List集合中
		//查村出指标Id对应的指标名称，存在List集合中
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		Iterator iter = list.iterator(); 
		while (iter.hasNext()) { 
			PnrEvaTaskAudit taskAudit = (PnrEvaTaskAudit) iter.next(); 
			if(!("".equals(taskAudit.getTemplateId()) || null == taskAudit.getTemplateId())){
				PnrEvaTemplate template = null ;
				template = templateMgr.getTemplate(taskAudit.getTemplateId());
				taskAudit.setTemplateName(template.getTemplateName());
			}
			if(!("".equals(taskAudit.getKpiId()) || null == taskAudit.getKpiId())){
				PnrEvaKpi kpi = null ;
				kpi = kpiMgr.getKpi(taskAudit.getKpiId());
				taskAudit.setKpiName(kpi.getKpiName());
			}
			
		}	
		request.setAttribute(PnrEvaConstants.TASK_AUDIT_LIST, list);
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
	public ActionForward taskDetailAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));

		String queryType = StaticMethod.null2String(request
				.getParameter("queryType"));
		if(queryType.equals("0")){
			queryType = "query" ;
		}else{
			queryType = "run" ;
		}
		String timeType = "月度";// 后续开发
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.null2String(request.getParameter("time"));
		}
		String isPublishButton = "";
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		String nodeId = "";
		String taskType = "";
		float minSroce = 999;
		String assessUserId="";//评定人
		String assessDeptId="";//评定部门
		float allScore = 0;
		Map minSorceMap = new HashMap();
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,String.valueOf(i));

			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
				PnrEvaKpiInstanceForm ekif = new PnrEvaKpiInstanceForm();
				//判断考核表内容是模板还是指标
				//如果是模板
				if(etd.getTaskType().equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
					taskType = PnrEvaConstants.NODETYPE_TEMPLATE;
					ekif.setId(etd.getId());
					ekif.setTemplateId(etd.getTemplateId());
					ekif.setLeaf(etd.getLeaf());
					ekif.setListNo(etd.getListNo());
					ekif.setNodeId(etd.getNodeId());
					ekif.setParentNodeId(etd.getParentNodeId());
					ekif.setRowspan(etd.getRowspan());
					ekif.setTaskId(etd.getTaskId());
					ekif.setWeight(etd.getWeight());
					ekif.setColspan(etd.getColspan());
					ekif.setArea(etd.getArea());
					PnrEvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(), timeType, time, partner);
					List reportList =  evaReportInfoMgr
					.getReportInfosByTimeAndAllPartner(etd.getTemplateId(),etd.getArea(), timeType, time, PnrEvaConstants.TASK_PUBLISHED);	
				PnrEvaReportInfo pnrEvaReportInfo = null;
				float totalScore = 0;
				float averageScore = 0;
				for(int k=0;k<reportList.size();k++){
					pnrEvaReportInfo = (PnrEvaReportInfo) reportList.get(k);
					totalScore += pnrEvaReportInfo.getTotalScore();
				}
				//现在是算已经发布的合作伙伴打分的平均值
				if(reportList.size()>0){
					averageScore = totalScore/reportList.size();
/*					//下面是所有厂商取平均值
				IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
				List facturyList = facturyMgr.getAllKpiFacturyByNodeId(etd.getNodeId());
				int partnerNum = facturyList.size();
				if(partnerNum>0){
				averageScore = totalScore/partnerNum;
*/
				}else{
					averageScore=0;
				}
					ekif.setRemark(ekis.getRemark());
					ekif.setMaintenanceRatio(ekis.getMaintenanceRatio());
					//得到下级汇总的数据
					//设置float精度小数点后两位
			        BigDecimal bd = new BigDecimal(averageScore);
			        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					averageScore = bd.floatValue();
					ekif.setRealScore(averageScore);
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
					}
					if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}

					//得到最小分数的记录对象
					if(ekif.getArea()!=null&&!ekif.getArea().equals("")){
					if(nodeId.equals(etd.getNodeId())){
						if(ekif.getRealScore()<minSroce){
							minSroce = ekif.getRealScore();
							minSorceMap.put(etd.getNodeId(), etd.getNodeId()+"_"+etd.getArea());
						}
					}
					}else{
						minSroce = 999;
					}
					nodeId = etd.getNodeId();
					
					
					// 算法加入
					
					PnrEvaTemplate tpl = tplMgr.getTemplate(ekif.getTemplateId());
					String algorithm = tpl.getSumType();
					if (algorithm == null || "".equals(algorithm)) {
						algorithm = "无";
					}
					ekif.setAlgorithm(algorithm);
					rowListShow.add(ekif);
					if(algorithm.equals(PnrEvaConstants.SUMTYPE_MIN)){
						
					}
					
				}else{
					taskType = PnrEvaConstants.NODETYPE_KPI;
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
				ekif.setArea(etd.getArea());
				if (PnrEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					PnrEvaKpiInstance ekis = evaKpiInstanceMgr
							.getKpiInstanceByTimeAndPartner(etd.getId(),
									timeType, time, partner);
					ekif.setRealScore(ekis.getRealScore());
					ekif.setMaintenanceRatio(ekis.getMaintenanceRatio());
					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
					}
					if ("1".equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
				}

				// 算法加入
				PnrEvaKpi ek = evaKpiMgr.getKpi(ekif.getKpiId());
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				rowListShow.add(ekif);
				}
			}
			tableList.add(rowListShow);
			
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}

		// 找到当前taskId对应的模板name
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		List list = evaReportInfoMgr.getReportInfoByTimeAndPartner(et.getTemplateId(), 
				et.getOrgId(), timeType,time,partner,PnrEvaConstants.TASK_NOT_PUBLISHED);
		if(list.size()>0){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) list.get(0);
			allScore = reportInfo.getTotalScore();
			assessUserId = reportInfo.getAssessUserId();
			assessDeptId = reportInfo.getAssessDeptId();
		}
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称
		
		
		//任务审核信息
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String auditTime = StaticMethod.null2String(request.getParameter("auditTime"));
		String userName = sessionform.getUsername();
		PnrEvaTaskAudit taskAudit = new PnrEvaTaskAudit();
		taskAudit.setId(id);
		taskAudit.setAuditUser(userName);
		taskAudit.setAuditTime(auditTime);
		request.setAttribute("taskAudit", taskAudit);
		request.setAttribute("allScore", Float.valueOf(allScore));
		request.setAttribute("assessUserId", assessUserId);
		request.setAttribute("assessDeptId", assessDeptId);
		request.setAttribute("templateId", et.getTemplateId());
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("minSorceMap", minSorceMap); // 返回某模板汇总积分最小值的集合
		if(taskType.equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
			return mapping.findForward("taskTemplateDetailAuditList");
		}	
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
		IPnrEvaTaskAuditMgr auditAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		PnrEvaTaskAuditForm taskAuditForm = (PnrEvaTaskAuditForm) form;
		PnrEvaTaskAudit taskAudit = null ;
		taskAudit = auditAuditMgr.getPnrEvaTaskAudit(taskAuditForm.getId());
		//根据所存地域Id判断任务审核级别，如果为县公司，需再交给市公司审核
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
		//县公司，需再提交给市公司审核，插入一条新纪录
		if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 2 && PnrEvaConstants.AUDIT_PASS.equals(taskAuditForm.getAuditResult())){
			ITawSystemAreaManager iTawSystemAreaManager = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
			//得到地市Id
			TawSystemArea tawSystemArea = iTawSystemAreaManager.getAreaByAreaId(taskAudit.getAreaId());
			String parentAreaId = tawSystemArea.getParentAreaid();
			Map map = PnrEvaStaticMethod.getSubroleByAreaId(parentAreaId, PnrEvaConstants.OPERATE_REPORT_AUDIT);
			//subRoleId指定审核人
			String subRoleId = StaticMethod.nullObject2String(map.get("subRoleId"));
			PnrEvaTaskAudit evaTaskAudit = new PnrEvaTaskAudit();
			evaTaskAudit.setAuditOrg(subRoleId);
			evaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
			evaTaskAudit.setAreaId(parentAreaId);
			evaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
			evaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
			//设置考核时间
			evaTaskAudit.setAuditTime(taskAudit.getAuditTime());
			//设置考核周期
			evaTaskAudit.setAuditCycle(taskAudit.getAuditCycle());
			////设置合作伙伴
			evaTaskAudit.setPartner(taskAudit.getPartner());
			evaTaskAudit.setTemplateId(taskAudit.getTemplateId());
			evaTaskAudit.setTaskId(taskAudit.getTaskId());
			evaTaskAudit.setTotalScore(taskAudit.getTotalScore());
			auditAuditMgr.savePnrEvaTaskAudit(evaTaskAudit);
		}
		taskAudit.setAuditUser(userId);
		taskAudit.setAuditResult(taskAuditForm.getAuditResult());
		taskAudit.setAuditRemark(taskAuditForm.getAuditRemark());
		taskAudit.setAuditDate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		auditAuditMgr.savePnrEvaTaskAudit(taskAudit);
		
		//修改任务审核记录成功，更改任务审核状态
		IPnrEvaKpiInstanceMgr instanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		List allTaskDetail = taskDetailMgr.listDetailOfTaskId(taskAudit.getTaskId());
		Iterator iter = allTaskDetail.iterator(); 
		PnrEvaKpiInstance kpiInstance = new PnrEvaKpiInstance();
		while(iter.hasNext()){
			PnrEvaTaskDetail taskDetail = null ;
			taskDetail = (PnrEvaTaskDetail) iter.next();
//			kpiInstance = instanceMgr.getKpiInstanceByTaskDetailId(taskDetail.getId());
			kpiInstance = instanceMgr.getKpiInstanceByTimeAndPartner(taskDetail.getId(),
					taskAudit.getAuditCycle(),taskAudit.getAuditTime(),taskAudit.getPartner());
			//审核级别判断
			if(PnrEvaConstants.AUDIT_PASS.equals(taskAuditForm.getAuditResult())){
				if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 2){
					//当县审核通过时
					kpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
				}else if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 1){
					kpiInstance.setAuditFlag(taskAuditForm.getAuditResult());
					kpiInstance.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);
				}
			}else{
				kpiInstance.setAuditFlag(taskAuditForm.getAuditResult());
				kpiInstance.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
			}
			instanceMgr.saveKpiInstance(kpiInstance);
			//修改report表IsPublish字段
			PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskAudit.getTaskId());
			
		if(PnrEvaConstants.AUDIT_PASS.equals(taskAuditForm.getAuditResult())
				&&(taskAudit.getAreaId().length() - rootAreaId.length())/2 == 1){

			IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
			List pnrEvaReportInfoList =  reportInfoMgr
			.getReportInfoByTimeAndPartner(taskAudit.getTemplateId(),pnrEvaTask.getOrgId(), 
					taskAudit.getAuditCycle(), taskAudit.getAuditTime(), 
					taskAudit.getPartner(),PnrEvaConstants.TASK_NOT_PUBLISHED);
			PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
			if(pnrEvaReportInfoList.size()>0){
				pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
			}
		if(pnrEvaReportInfo.getId()!=null){
			pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);
			pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
			reportInfoMgr.savePnrEvaReportInfo(pnrEvaReportInfo);
		}
			
		}
		if(PnrEvaConstants.AUDIT_DENY.equals(taskAuditForm.getAuditResult())){
			IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
			List pnrEvaReportInfoList =  reportInfoMgr
			.getReportInfoByTimeAndPartner(taskAudit.getTemplateId(),pnrEvaTask.getOrgId(), 
					taskAudit.getAuditCycle(), taskAudit.getAuditTime(), 
					taskAudit.getPartner(),PnrEvaConstants.TASK_NOT_PUBLISHED);
			PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
			if(pnrEvaReportInfoList.size()>0){
				pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
			}
		if(pnrEvaReportInfo.getId()!=null){
			pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_DENY);
			reportInfoMgr.savePnrEvaReportInfo(pnrEvaReportInfo);
			}
		}
		}
		return mapping.findForward("success");
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
		IPnrEvaTaskAuditMgr auditAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String[] ids = request.getParameterValues("ids");
		String auditResult = request.getParameter("auditResult");
		String auditRemark = request.getParameter("auditRemark");
		PnrEvaTaskAudit taskAudit = null ;
		for(int i=0;i<ids.length;i++){
			
		taskAudit = auditAuditMgr.getPnrEvaTaskAudit(ids[i]);
		//根据所存地域Id判断任务审核级别，如果为县公司，需再交给市公司审核
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
		//县公司，需再提交给市公司审核，插入一条新纪录
		if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 2 && PnrEvaConstants.AUDIT_PASS.equals(auditResult)){
			ITawSystemAreaManager iTawSystemAreaManager = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
			//得到地市Id
			TawSystemArea tawSystemArea = iTawSystemAreaManager.getAreaByAreaId(taskAudit.getAreaId());
			String parentAreaId = tawSystemArea.getParentAreaid();
			Map map = PnrEvaStaticMethod.getSubroleByAreaId(parentAreaId, PnrEvaConstants.OPERATE_REPORT_AUDIT);
			//subRoleId指定审核人
			String subRoleId = StaticMethod.nullObject2String(map.get("subRoleId"));
			PnrEvaTaskAudit evaTaskAudit = new PnrEvaTaskAudit();
			evaTaskAudit.setAuditOrg(subRoleId);
			evaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
			evaTaskAudit.setAreaId(parentAreaId);
			evaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
			evaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
			//设置考核时间
			evaTaskAudit.setAuditTime(taskAudit.getAuditTime());
			//设置考核周期
			evaTaskAudit.setAuditCycle(taskAudit.getAuditCycle());
			////设置合作伙伴
			evaTaskAudit.setPartner(taskAudit.getPartner());
			evaTaskAudit.setTemplateId(taskAudit.getTemplateId());
			evaTaskAudit.setTaskId(taskAudit.getTaskId());
			auditAuditMgr.savePnrEvaTaskAudit(evaTaskAudit);
		}
		taskAudit.setAuditUser(userId);
		taskAudit.setAuditResult(auditResult);
		taskAudit.setAuditRemark(auditRemark);
		taskAudit.setAuditDate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		auditAuditMgr.savePnrEvaTaskAudit(taskAudit);
		
		//修改任务审核记录成功，更改任务审核状态
		IPnrEvaKpiInstanceMgr instanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		List allTaskDetail = taskDetailMgr.listDetailOfTaskId(taskAudit.getTaskId());
		Iterator iter = allTaskDetail.iterator(); 
		PnrEvaKpiInstance kpiInstance = new PnrEvaKpiInstance();
		while(iter.hasNext()){
			PnrEvaTaskDetail taskDetail = null ;
			taskDetail = (PnrEvaTaskDetail) iter.next();
//			kpiInstance = instanceMgr.getKpiInstanceByTaskDetailId(taskDetail.getId());
			kpiInstance = instanceMgr.getKpiInstanceByTimeAndPartner(taskDetail.getId(),
					taskAudit.getAuditCycle(),taskAudit.getAuditTime(),taskAudit.getPartner());
			//审核级别判断
			if(PnrEvaConstants.AUDIT_PASS.equals(auditResult)){
				if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 2){
					//当县审核通过时
					kpiInstance.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
				}else if((taskAudit.getAreaId().length() - rootAreaId.length())/2 == 1){
					kpiInstance.setAuditFlag(auditResult);
					kpiInstance.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);
				}
			}else{
				kpiInstance.setAuditFlag(auditResult);
				kpiInstance.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
			}
			instanceMgr.saveKpiInstance(kpiInstance);
			//修改report表IsPublish字段
			PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskAudit.getTaskId());
			
		if(PnrEvaConstants.AUDIT_PASS.equals(auditResult)
				&&(taskAudit.getAreaId().length() - rootAreaId.length())/2 == 1){

			IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
			List pnrEvaReportInfoList =  reportInfoMgr
			.getReportInfoByTimeAndPartner(taskAudit.getTemplateId(),pnrEvaTask.getOrgId(), 
					taskAudit.getAuditCycle(), taskAudit.getAuditTime(), 
					taskAudit.getPartner(),PnrEvaConstants.TASK_NOT_PUBLISHED);
			PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
			if(pnrEvaReportInfoList.size()>0){
				pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
			}
		if(pnrEvaReportInfo.getId()!=null){
			pnrEvaReportInfo.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);
			pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_PASS);
			reportInfoMgr.savePnrEvaReportInfo(pnrEvaReportInfo);
		}
			
		}
		if(PnrEvaConstants.AUDIT_DENY.equals(auditResult)){
			IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
			List pnrEvaReportInfoList =  reportInfoMgr
			.getReportInfoByTimeAndPartner(taskAudit.getTemplateId(),pnrEvaTask.getOrgId(), 
					taskAudit.getAuditCycle(), taskAudit.getAuditTime(), 
					taskAudit.getPartner(),PnrEvaConstants.TASK_NOT_PUBLISHED);
			PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
			if(pnrEvaReportInfoList.size()>0){
				pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
			}
		if(pnrEvaReportInfo.getId()!=null){
			pnrEvaReportInfo.setState(PnrEvaConstants.AUDIT_DENY);
			reportInfoMgr.savePnrEvaReportInfo(pnrEvaReportInfo);
			}
		}
		}
		}
		return mapping.findForward("success");
	}
}
