/**
 * 
 */
package com.boco.eoms.partner.assess.AssExecute.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.service.IAssRoleService;
import com.boco.eoms.partner.assess.AssRight.service.impl.AssRoleService;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Nov 30, 2010 5:40:15 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class LineAssExecuteAction extends AssExecuteAction {
	public LineAssExecuteAction() { 
		beenNameForTreeMgr = "IlineAssTreeMgr";
		beenNameForTemplateMgr = "IlineAssTemplateMgr";
		beenNameForKpiMgr = "IlineAssKpiMgr";
		beenNameForTaskMgr = "IlineAssTaskMgr";
		beenNameForTaskDetailMgr ="IlineAssTaskDetailMgr";
		beenNameForKpiInstanceMgr = "IlineAssKpiInstanceMgr";
		beenNameForReportInfoMgr = "IlineAssReportInfoMgr";
		beenNameForFactoryMgr = "IlineAssFactoryMgr";
		beenNameForRoleService = "IlineAssRoleService";
		beenNameForFlowMgr = "IlineAssFlowMgr";
		beenNameForOperateStepMgr = "IlineAssOperateStepMgr";
		templateTypeNode = "1001";
//		线路
		specialty = "112131103";
		beenNameForSelectedInstanceMgr = "IlineAssSelectedInstanceMgr";
		} 

	/**
	 * 打开考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskDetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = assTemplate.getTemplateName();
//		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//		.getSession().getAttribute("sessionform");
//		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		if(feeTotal!=null){
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
			String monthPrice = String.valueOf(feeTotal.getMonthMoney().doubleValue());
			request.setAttribute("monthPrice", monthPrice);
			request.setAttribute("uniPrice", uniPrice);
		}else{
			request.setAttribute("noPrice", "noPrice");
			request.setAttribute("year", year);
		}		

		if(queryType.equals("0")){
			queryType = "query" ;
		}else{
			queryType = "run" ;
		}
		String time = year ;
		if("".equals(month)&&!"".equals(quarter)){
			time = time + quarter;
		} 
		if("".equals(quarter)&&!"".equals(month)){
			time = time + month;
		}
		if (time == null || "".equals(time)) {
			time = StaticMethod.null2String(request.getParameter("time"));
		}
		String isPublishButton = "";
		String readOnly = "";
		PartnerDept partnerDept = null;
		String partner = "";
		Map map = new HashMap();
		List tableList = new ArrayList();
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		List reportIdList = new ArrayList();
//		String afterTime = "";
//		AssReportInfo reInfo1 = null;
//		AssSelectedInstance assSelectedInstance = null;
//		AssKpiInstance ekis1 = null;
		for(int i = 0 ;i< partnerList.size();i++){
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			// 保存ReportInfo表记录
			AssReportInfo report = assReportInfoMgr.getReportInfoByTimeAndPartner(taskId, areaId, timeType, time, partner, "");
//			增加线路专业引用上月历史数据
//			if(report.getId()==null||"".equals(report.getId())){
//				if(time.substring(4).equals("01")){
//					if(timeType.equals("quarter")){
//						afterTime = String.valueOf(Integer.parseInt(year)-1)+"04";
//					}else{
//						afterTime = String.valueOf(Integer.parseInt(year)-1)+"12";
//					}
//					
//				}else{
//					afterTime = String.valueOf(Integer.parseInt(time)-1);
//				}
//				report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, afterTime, partner, "");
//				if(report.getId()!=null&&!"".equals(report.getId())&&taskId.equals(report.getTaskId())){
//					reInfo1 = new AssReportInfo();
//					reInfo1.setCreateTime(StaticMethod.getLocalString());
//					reInfo1.setPartnerId(partner.trim());
//					reInfo1.setPartnerName(partnerDept.getName());
//					reInfo1.setTaskId(taskId);
//					reInfo1.setTaskName(assTemplate.getTemplateName());
//					reInfo1.setTime(time);
//					reInfo1.setTimeType(timeType);
//					reInfo1.setArea(areaId);
//					reInfo1.setCreateUser(userId);
//					reInfo1.setCreateDept(deptId);
//					reInfo1.setTreeNodeId(assTemplate.getBelongNode());
//					reInfo1.setTemplateTypeNode(templateTypeNode);
//					reInfo1.setState(AssConstants.ASS_STATE_DRAFT);
//					reInfo1.setTotalScore(report.getTotalScore());
//					reInfo1.setTotalMoney(report.getTotalMoney());
//					reInfo1.setTotalWeight(report.getTotalWeight());
//					assReportInfoMgr.saveAssReportInfo(reInfo1);
//					
//					List assKpiList = (List)assKpiInstanceMgr.getKpiInstancesByReport(report.getId());
//					for(int j = 0; j < assKpiList.size(); j++){
//						ekis1 = (AssKpiInstance)assKpiList.get(j);
//						AssKpiInstance ekis = new AssKpiInstance();
//						ekis.setPartnerId(partner.trim());
//						ekis.setPartnerName(partnerDept.getName());
//						ekis.setRealScore(ekis1.getRealScore());
//						ekis.setReduceReason(ekis1.getReduceReason());
//						ekis.setRemark(ekis1.getRemark());
//						ekis.setMoney(ekis1.getMoney());
//						ekis.setTaskDetailId(ekis1.getTaskDetailId());
//						ekis.setReportId(reInfo1.getId());
//						ekis.setTime(time);
//						ekis.setTimeType(timeType);
//						ekis.setCity(areaId);
//						ekis.setCreateTime(StaticMethod.getLocalString());
//						assKpiInstanceMgr.saveKpiInstance(ekis);
//					}
//
//					assReportInfoMgr.saveAssReportInfo(reInfo1);
//					
//					report = reInfo1;
//					request.setAttribute("after", "(已引用上月数据)");
//				}
//			}
			if(report.getState()!=null&&!(AssConstants.ASS_STATE_DRAFT).equals(report.getState())&&!(AssConstants.ASS_STATE_NEW).equals(report.getState())){
				isPublishButton = "display:none;";
				readOnly = "readonly='true'";
			}
			if(report.getState()!=null&&!"".equals(report.getState())&&!(AssConstants.ASS_STATE_DRAFT).equals(report.getState())){
				request.setAttribute("execute", "execute");
			}
			reportIdList.add(report);
		}
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
				for(int k=0;k<reportIdList.size();k++){
					if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
						AssReportInfo report = (AssReportInfo)reportIdList.get(k);
						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByReport(etd.getId(),report.getId());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", ekis.getRealScore());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", ekis.getReduceReason());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", ekis.getRemark());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
					}
				}
				// 算法加入
				AssKpi ek = assKpiMgr.getKpi(ekif.getKpiId());
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				ekif.setKpiType(ek.getKpiType());
				//如果是合计项,得到该项父结点包含所有叶子结点的集合,用户页面脚本求和
				if("total".equals(ek.getKpiType())){
					ekif.setNodesForTotal(taskDetailMgr.getLeafNodesOfChild(taskId, etd.getParentNodeId()));
					
				}
				
				rowListShow.add(ekif);
			}
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}
		if("month".equals(timeType)){
			request.setAttribute("timeTypeStr", "月"); // 时间类型
			request.setAttribute("timeStr", year+"年"+month+"月"); // 时间
		} else if ("quarter".equals(timeType)){
			request.setAttribute("timeTypeStr", "季度"); // 时间类型
			request.setAttribute("timeStr", year+"年,第"+quarter+"季度"); // 时间
		} else if ("year".equals(timeType)){
			request.setAttribute("timeTypeStr", "年"); // 时间类型
			request.setAttribute("timeStr", year+"年"); // 时间
		}
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("readOnly", readOnly); // 是否可填写
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		request.setAttribute("treeNodeId", assTemplate.getBelongNode());
		return mapping.findForward("taskDetailList");
	}
	
}
