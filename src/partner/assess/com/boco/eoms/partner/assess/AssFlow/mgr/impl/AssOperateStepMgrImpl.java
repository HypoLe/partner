package com.boco.eoms.partner.assess.AssFlow.mgr.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;
import com.boco.eoms.partner.assess.AssFlow.dao.IAssOperateStepDao;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssFlowMgr;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssOperateStepMgr;
import com.boco.eoms.partner.assess.AssFlow.model.AssFlow;
import com.boco.eoms.partner.assess.AssFlow.model.AssOperateStep;
import com.boco.eoms.partner.assess.AssFlow.webapp.form.AssOperateStepForm;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:后评估步骤列表信息
 * </p>
 * <p>
 * Description:后评估步骤列表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssOperateStepMgrImpl implements IAssOperateStepMgr {
 
	protected String beenNameForFlowMgr = "";
	protected String beenNameForTaskMgr = "";
	protected String beenNameForReportInfoMgr = "";
	protected String beenNameForTaskDetailMgr = "";
	protected String beenNameForKpiInstanceMgr = "";
	protected String beenNameForKpiMgr = "";
	protected String templateTypeNode = "";
	
	private IAssOperateStepDao  assOperateStepDao;
 	
	public IAssOperateStepDao getAssOperateStepDao() {
		return this.assOperateStepDao;
	}
 	
	public void setAssOperateStepDao(IAssOperateStepDao assOperateStepDao) {
		this.assOperateStepDao = assOperateStepDao;
	}
 	
    public List getAssOperateSteps() {
    	return assOperateStepDao.getAssOperateSteps();
    }
 	
    public List getAssOperateSteps(String assId) {
    	return assOperateStepDao.getAssOperateSteps(assId);
    }
    
    public AssOperateStep getAssOperateStep(final String id) {
    	return assOperateStepDao.getAssOperateStep(id);
    }
    
    public void saveAssOperateStep(HttpServletRequest request,String reportId,String nextState) {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		IAssFlowMgr assFlowMgr = (IAssFlowMgr) ApplicationContextHolder.getInstance().getBean(beenNameForFlowMgr);

		//得到所有步骤相关信息
    	String operateId = StaticMethod.null2String(request.getParameter("operateId"));
    	String areaId = StaticMethod.null2String(request.getParameter("areaId"));
    	String operateOrgId = assFlowMgr.getSubRoleIdByArea(nextState,areaId);
    	String operateOrgType = "subRole";
    	String localTime = StaticMethod.getLocalString();
    	String operateUserContact = StaticMethod.null2String(request.getParameter("operateUserContact"));
    	String operateOpinion = StaticMethod.null2String(request.getParameter("operateOpinion"));
    	String accessoriesId = StaticMethod.null2String(request.getParameter("accessoriesId"));
		//确定流传完成状态
		String auditResult = StaticMethod.nullObject2String(request.getParameter("auditResult"));
		String OperateFlag = AssConstants.OPERATE_FLAG_DONO;
		if("1".equals(auditResult)){
			OperateFlag = AssConstants.OPERATE_FLAG_REJECT;
		}else if("2".equals(auditResult)){
			OperateFlag = AssConstants.OPERATE_FLAG_PASS;
		}
		
    	//更新当前任务记录
    	if(!"".equals(operateId)){
    		AssOperateStep assOperateStep = getAssOperateStep(operateId);
    		assOperateStep.setOperateUser(userId);
    		assOperateStep.setOperateTime(localTime);
    		assOperateStep.setOperateOpinion(operateOpinion);
    		assOperateStep.setOperateUserContact(operateUserContact);
    		assOperateStep.setAccessoriesId(accessoriesId);
    		assOperateStep.setOperateFlag(OperateFlag);
    		saveAssOperateStep(assOperateStep);
    	}
    	//如果不是归档操作，则新增一条任务
    	if(!nextState.equals(AssConstants.ASS_STATE_FINISH)){
    		AssOperateStep assOperateStep = new AssOperateStep();
    		assOperateStep.setCreateTime(localTime);
    		assOperateStep.setReportId(reportId);
    		assOperateStep.setOperateFlag(AssConstants.OPERATE_FLAG_UNDO);
    		assOperateStep.setOperateOrgId(operateOrgId);
    		assOperateStep.setOperateOrgType(operateOrgType);
    		assOperateStep.setState(nextState);
    		saveAssOperateStep(assOperateStep);
    	}
		IAssReportInfoMgr reportMgr = (IAssReportInfoMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForReportInfoMgr);
		AssReportInfo report = reportMgr.getAssReportInfo(reportId);
		report.setState(nextState);
		reportMgr.saveAssReportInfo(report);
    }
    
    public void saveAssOperateStep(AssOperateStep assOperateStep) {
    	assOperateStepDao.saveAssOperateStep(assOperateStep);
    }
    
    public void removeAssOperateStep(final String id) {
    	assOperateStepDao.removeAssOperateStep(id);
    }
    
    public Map getAssOperateSteps(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return assOperateStepDao.getAssOperateSteps(curPage, pageSize, whereStr);
	}
    
    public Map listUndoAss(final String userId,final Integer curPage, final Integer pageSize) {
    	StringBuffer whereStr = new StringBuffer();
    	//现在只支持角色，以后可以在这里扩展
    	ITawSystemUserRefRoleManager roleManager = (ITawSystemUserRefRoleManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserRefRoleManager");
		List list = roleManager.getSubRoleByUserId(userId, RoleConstants.ROLETYPE_SUBROLE);
		TawSystemSubRole subRole = null;
		whereStr.append(" and step.operateOrgId in (");
		for(int i=0;i<list.size();i++){
			if(i!=0){
				whereStr.append(",");
			}
			subRole = (TawSystemSubRole)list.get(i);
			whereStr.append("'"+subRole.getId()+"'");
		}
    	whereStr.append(") and step.operateOrgType='subRole' ");
    	whereStr.append(" and step.operateFlag='"+AssConstants.OPERATE_FLAG_UNDO+"' ");

    	//筛选出该专业的待处理
    	whereStr.append(" and report.templateTypeNode='"+templateTypeNode+"' ");
    	
		return assOperateStepDao.getAssOperateSteps(curPage, pageSize,whereStr.toString());
	}
    
	public List putReportAndStepListToForm(List reportAndStepList){
		IAssFlowMgr assFlowMgr = (IAssFlowMgr) ApplicationContextHolder.getInstance().getBean(beenNameForFlowMgr);
		List formList = new ArrayList();
		AssReportInfo report = null;
		AssOperateStep step = null;
		AssFlow assFlow = null;
		for(int i=0;i<reportAndStepList.size();i++){
			Object[] object = (Object[])reportAndStepList.get(i);
			report = (AssReportInfo)object[0];
			step = (AssOperateStep)object[1];
			AssOperateStepForm form = new AssOperateStepForm();
			form.setId(step.getId());
			form.setReportId(step.getReportId());
			form.setOperateOrgId(step.getOperateOrgId());
			form.setOperateOrgType(step.getOperateOrgType());
			form.setOperateUser(step.getOperateUser());
			form.setOperateTime(step.getOperateTime());
			form.setOperateUserContact(step.getOperateUserContact());
			form.setOperateOpinion(step.getOperateOpinion());
			form.setState(step.getState());
			assFlow = assFlowMgr.getAssFlowByXml(step.getState());
			form.setStateName(assFlow.getDescription());
			form.setOperateFlag(step.getOperateFlag());
			form.setAccessoriesId(step.getAccessoriesId());
			form.setArea(report.getArea());
			form.setTaskName(report.getTaskName());
			form.setTime(report.getTime());
			form.setPartnerId(report.getPartnerId());
			form.setPartnerName(report.getPartnerName());
			form.setCreateTime(report.getCreateTime());
			formList.add(form);
		}
		return formList;
	}
	
	public List getKpiInstance(HttpServletRequest request,String reportId){
		int leafNum = 0;
//		String viewFlag = StaticMethod.nullObject2String(request.getAttribute("viewFlag"));
		IAssTaskMgr taskMgr = (IAssTaskMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForTaskMgr);
		IAssReportInfoMgr reportMgr = (IAssReportInfoMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForReportInfoMgr);
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForTaskDetailMgr);
		IAssKpiInstanceMgr kpiInstanceMgr = (IAssKpiInstanceMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr kpiMgr = (IAssKpiMgr) ApplicationContextHolder
		.getInstance().getBean(beenNameForKpiMgr);
		//查询已经评分
		AssReportInfo report  = reportMgr.getAssReportInfo(reportId);
		AssTask assTask = taskMgr.getTask(report.getTaskId());
		List tableList = new ArrayList();
		String taskId = assTask.getId();
		String timeType = "month";// 后续开发
		String lastWeight = "";
	

		//各打分指标信息记录
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		// 指标实际总分
		float totalScore = 0;
		// 实际金额
		float totalMoney = 0;
		// 指标权重总分
		float totalWeight = 0;
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,String.valueOf(i));
			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				AssTaskDetail etd = (AssTaskDetail) rowList.get(j);
				AssKpiInstanceForm ekif = new AssKpiInstanceForm();
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
			if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
					AssKpiInstance ekis = kpiInstanceMgr.getKpiInstanceByReport(etd.getId(),report.getId());
//					map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", ekis.getRealScore());
//					map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", ekis.getReduceReason());
//					map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", ekis.getRemark());
//					map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
					ekif.setRealScore(ekis.getRealScore());
					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					ekif.setMoney(ekis.getMoney());
				}
				// 算法加入
				AssKpi ek = kpiMgr.getKpi(ekif.getKpiId());
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				ekif.setKpiType(ek.getKpiType());
				//如果是合计项,得到该项父结点包含所有叶子结点的集合,用户页面脚本求和
				if("total".equals(ek.getKpiType())){
					ekif.setNodesForTotal(taskDetailMgr.getLeafNodesOfChild(taskId, etd.getParentNodeId()));
					
				}else{
					ekif.setNodesForTotal("");
				}
				
				rowListShow.add(ekif);
			}
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr)ApplicationContextHolder.getInstance().getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(report.getArea(), report.getTime().substring(0,4));
		String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
		String monthPrice = String.valueOf(feeTotal.getMonthMoney().doubleValue());
//		String uniPrice = "231.11";
		request.setAttribute("monthPrice", monthPrice);
		request.setAttribute("uniPrice", uniPrice);
		// 找到当前taskId对应的模板name
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("leafNum", String.valueOf(leafNum)); // 详细列表数据
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partnerId", report.getPartnerId()); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", report.getTime()); // 时间
		request.setAttribute("areaId", report.getArea()); // 时间
		request.setAttribute("state", "1");
		return tableList;
	}

}