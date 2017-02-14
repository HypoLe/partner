/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.webapp.action;

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
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssConfirmMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssConfirm;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
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
 * Date:Dec 2, 2010 6:39:50 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class DeviceAssReportInfoAction extends AssReportInfoAction{
	
	public DeviceAssReportInfoAction() { 
		beenNameForTreeMgr = "IdeviceAssTreeMgr";
		beenNameForTemplateMgr = "IdeviceAssTemplateMgr";
		beenNameForKpiMgr = "IdeviceAssKpiMgr";
		beenNameForTaskMgr = "IdeviceAssTaskMgr";
		beenNameForTaskDetailMgr ="IdeviceAssTaskDetailMgr";
		beenNameForKpiInstanceMgr = "IdeviceAssKpiInstanceMgr";
		beenNameForReportInfoMgr = "IdeviceAssReportInfoMgr";
		beenNameForFactoryMgr = "IdeviceAssFactoryMgr";
		beenNameForRoleService = "IdeviceAssRoleService";
		beenNameForOperateStepMgr = "IdeviceAssOperateStepMgr";
		templateTypeNode = "1005";
//		辅助设备
		specialty = "112131105";
		} 	
	
	
	/**
	 * 打开代维考核任务选择页面(按季度)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		List taskList = taskMgr.listTaskOfProvinceAdmin(AssConstants.TEMPLATE_ACTIVATED, templateTypeNode);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		AssTask assTask = null;
		AssTemplate assTemplate = null;
		List taskList2 = new ArrayList();
		for(int i = 0;taskList.size()>i;i++){
			assTask = (AssTask)taskList.get(i);
			assTemplate = templateMgr.getTemplate(assTask.getTemplateId());
			if("month".equals(assTemplate.getCycle())){
				taskList2.add(assTask);
			}
		}
		request.setAttribute("taskList", taskList2);
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts("", specialty);
		request.setAttribute("partnerList", partnerList);
		return mapping.findForward("assSelect");
	}	
	
	/**
	 * 查看代维考核任务费用确认信息（按季度）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		IAssConfirmMgr assConfirmMgr = (IAssConfirmMgr) getBean("IassConfirmMgr");
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		AssTree assTree = assTreeMgr.getTreeNode(assTemplate.getBelongNode());
//		按汇总节点取出数据
		List assTreeList = assTreeMgr.listChildNodesByTotal(assTree.getNodeId());
//		List assTreeList = assTreeMgr.listNextLevelNode(assTree.getNodeId(), "");
		String time = year ;
		String timeStr = "";
		List timeList = new ArrayList();
		if("01".equals(quarter)){
			timeStr = "'"+year+"01','"+year+"02','"+year+"03'"; 
			timeList.add(year+"01");
			timeList.add(year+"02");
			timeList.add(year+"03");
		}else if("02".equals(quarter)){
			timeStr = "'"+year+"04','"+year+"05','"+year+"06'"; 
			timeList.add(year+"04");
			timeList.add(year+"05");
			timeList.add(year+"06");
		}else if("03".equals(quarter)){
			timeStr = "'"+year+"07','"+year+"08','"+year+"09'"; 
			timeList.add(year+"07");
			timeList.add(year+"08");
			timeList.add(year+"09");
		}else if("04".equals(quarter)){
			timeStr = "'"+year+"10','"+year+"11','"+year+"12'"; 
			timeList.add(year+"10");
			timeList.add(year+"11");
			timeList.add(year+"12");
		}
		if(!"".equals(quarter)){
			time = time + quarter;
		} 
		if (time == null || "".equals(time)) {
			time = StaticMethod.null2String(request.getParameter("time"));
		}
//		得到操作确认费用信息
		List assconfirmList = assConfirmMgr.getAssConfirmList(" where taskId = '"+taskId+"' and time ='"+time+"'");
		AssConfirm assConfirm = null;
		if(assconfirmList!=null&&assconfirmList.size()>0){
			assConfirm = (AssConfirm)assconfirmList.get(0);
			request.setAttribute("taskId", assConfirm.getTaskId());
			request.setAttribute("provinceMoney", assConfirm.getProvinceMoney());
			request.setAttribute("assId", assConfirm.getId());
		}
		
//		map中存页面需要显示的数据
		Map map = new HashMap();
		
//		查询该合作伙伴维护的地市（线路）
		List partnerList = partnerDeptMgr.getPartnerDepts(" and interfaceHeadId = '"+partnerId+"' and specialty = '"+specialty+"'");
		List areaList = new ArrayList();
		PartnerDept partnerDept = null;
		for (int i = 0; i < partnerList.size(); i++) {
			partnerDept = (PartnerDept)partnerList.get(i);
			if(!areaList.contains(partnerDept.getAreaId())){
				areaList.add(partnerDept.getAreaId());
			}
		}
		String totalKpi ="0.0";
		String realMoneyStr ="0.0";
		String totalQuarterStr ="0.0";
		String quarterMoneyStr ="0.0";
		for(int f = 0; f < areaList.size(); f++){
			areaId = String.valueOf(areaList.get(f));
			
			//查询的到线路专业某地市每季度基础维护费用
			IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
			FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
			if(feeTotal!=null){
				String uniPrice = String.valueOf(feeTotal.getQuarterMoney().doubleValue());
				request.setAttribute("uniPrice", uniPrice);
			}else{
				request.setAttribute("noPrice", "noPrice");
				request.setAttribute("year", year);
				request.setAttribute("areaId", areaId);
				return mapping.findForward("assList");
			}
	//		对应指标扣款
			double total = 0.0;
	//		季度总扣款
			double totalQuarter = 0.0;
			
			for(int k=0;k<assTreeList.size();k++){
				AssTree assTree1 = (AssTree)assTreeList.get(k);
				total = assKpiInstanceMgr.listKpiInstance(assTree1.getNodeId(), partnerId, timeStr,areaId);
				totalQuarter = totalQuarter + total;
				map.put(assTree1.getNodeId()+"_"+areaId+"_total", String.valueOf(total));
//				计算合计对应指标合计扣款
				totalKpi = StaticMethod.null2String(String.valueOf(map.get(assTree1.getNodeId()+"_totalKpi")));
				if("".equals(totalKpi)||"null".equals(totalKpi)){
					map.put(assTree1.getNodeId()+"_totalKpi", String.valueOf(total));
				}else{
					map.put(assTree1.getNodeId()+"_totalKpi", String.valueOf(Double.parseDouble(totalKpi)+total));
				}					
			}
			
			double realMoney = feeTotal.getQuarterMoney() - totalQuarter;
			
			realMoneyStr = StaticMethod.null2String(String.valueOf(map.get("realMoney")));
			totalQuarterStr = StaticMethod.null2String(String.valueOf(map.get("totalKpi")));
			quarterMoneyStr = StaticMethod.null2String(String.valueOf(map.get("quarterMoney")));
			if("".equals(realMoneyStr)||"null".equals(realMoneyStr)){
				map.put("realMoney", String.valueOf(realMoney));
			}else{
				map.put("realMoney", String.valueOf(Double.parseDouble(realMoneyStr)+realMoney));
			}	
			
			if("".equals(totalQuarterStr)||"null".equals(totalQuarterStr)){
				map.put("totalKpi", String.valueOf(totalQuarter));
			}else{
				map.put("totalKpi", String.valueOf(Double.parseDouble(totalQuarterStr)+totalQuarter));
			}	
			
			if("".equals(quarterMoneyStr)||"null".equals(quarterMoneyStr)){
				map.put("quarterMoney", String.valueOf(feeTotal.getQuarterMoney()));
			}else{
				map.put("quarterMoney", String.valueOf(Double.parseDouble(quarterMoneyStr)+feeTotal.getQuarterMoney()));
			}	
			map.put(areaId+"_realMoney", String.valueOf(realMoney));		
			map.put(areaId+"_totalQuarter", String.valueOf(totalQuarter));
			map.put(areaId+"_quarterMoney", String.valueOf(feeTotal.getQuarterMoney()));
			
//			
		}
		if("month".equals(timeType)){
			request.setAttribute("timeTypeStr", "月"); // 时间类型
			if("01".equals(quarter)){
				request.setAttribute("timeStr", year+"年第一季度"); 
			}else if("02".equals(quarter)){
				request.setAttribute("timeStr", year+"年第二季度"); 
			}else if("03".equals(quarter)){
				request.setAttribute("timeStr", year+"年第三季度"); 
			}else if("04".equals(quarter)){
				request.setAttribute("timeStr", year+"年第四季度"); 
			}
		} 
		String userId = this.getUser(request).getUserid();
		request.setAttribute("userId", userId);
		request.setAttribute("asseseTime", StaticMethod.getLocalString().substring(0,11));
		request.setAttribute("assTreeList", assTreeList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		return mapping.findForward("assList");
	}	
	/**
	 * 保存代维考核任务结果页面(季度)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssConfirmMgr assConfirMgr = (IAssConfirmMgr) getBean("IassConfirmMgr");
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String userId = StaticMethod.null2String(request.getParameter("userId"));
		String time = StaticMethod.null2String(request.getParameter("time"));
		String realMoney = StaticMethod.null2String(request.getParameter("real"));	
		String deductMoney = StaticMethod.null2String(request.getParameter("deduct"));
		String provinceMoney = StaticMethod.null2String(request.getParameter("province"));
		double dp = Double.parseDouble(deductMoney)+Double.parseDouble(provinceMoney);
		double rp = Double.parseDouble(realMoney)-Double.parseDouble(provinceMoney);
		AssConfirm assConfirm = new AssConfirm();
		assConfirm.setDeductMoney(String.valueOf(dp));
		assConfirm.setProvinceMoney(provinceMoney);
		assConfirm.setRealMoney(String.valueOf(rp));
		assConfirm.setTaskId(taskId);
		assConfirm.setTime(time);
		assConfirm.setUserId(userId);
		assConfirMgr.saveAssConfirm(assConfirm);
		
		return mapping.findForward("success");
	}
}
