package com.boco.eoms.partner.assess.AssReport.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
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
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssOperateStepMgr;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.service.IAssRoleService;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.AssWeight.mgr.ICityWeightExeMgr;
import com.boco.eoms.partner.assess.AssWeight.mgr.ICityWeightMgr;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeight;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeightExe;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.assess.util.DateTimeUtil;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.FusionChart;
import com.boco.eoms.partner.baseinfo.model.FusionChartData;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.FusionChartMethod;

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
public abstract class AssReportInfoAction extends BaseAction{
	
	protected String beenNameForTreeMgr = "";
	protected String beenNameForTemplateMgr = "";
	protected String beenNameForKpiMgr = "";
	protected String beenNameForWeightMgr = "IassWeightMgr";
	protected String beenNameForTaskMgr = "";
	protected String beenNameForTaskDetailMgr ="";
	protected String beenNameForKpiInstanceMgr = "";
	protected String beenNameForReportInfoMgr = "";
	protected String beenNameForFactoryMgr = "";
	protected String beenNameForRoleService = "";
	protected String beenNameForOperateStepMgr = "";
	protected String templateTypeNode = "";
	protected String specialty = "";
	protected String beenNameForCityWeightMgr = "";
	protected String beenNameForCityWeightExeMgr = "";
	/**
	 * 查看考核任务页面（按季度）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskDetailListForQuarter(ActionMapping mapping, ActionForm form,
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
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = assTemplate.getTemplateName();
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = new ArrayList();
		if("".equals(partnerId)){
			partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		}else{
			partnerList.add(partnerId);
		}
		
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		double quarterMoney = 0.0;
		if(feeTotal!=null){
			quarterMoney = feeTotal.getQuarterMoney();
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
			request.setAttribute("uniPrice", uniPrice);
			request.setAttribute("price", feeTotal.getQuarterMoney());
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
		List timeList = new ArrayList();
		if("01".equals(quarter)){
			timeList.add(year+"01");
			timeList.add(year+"02");
			timeList.add(year+"03");
		}else if("02".equals(quarter)){
			timeList.add(year+"04");
			timeList.add(year+"05");
			timeList.add(year+"06");
		}else if("03".equals(quarter)){
			timeList.add(year+"07");
			timeList.add(year+"08");
			timeList.add(year+"09");
		}else if("04".equals(quarter)){
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
		String isPublishButton = "";
		PartnerDept partnerDept = null;
		String partner = "";
		Map map = new HashMap();
		List tableList = new ArrayList();
		int maxLevel = 0;
		
		List reportIdList = new ArrayList();
		
		for(int i = 0 ;i< partnerList.size();i++){
			double totalmoney = 0.0;
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			
			for(int j = 0 ;j< timeList.size();j++){
				AssReportInfo report = assReportInfoMgr.getReportInfoByTimeAndPartner(assTemplate.getBelongNode(), areaId, timeType, String.valueOf(timeList.get(j)), partner, "1");
				if ("1".equals(report.getIsPublish())) {
					isPublishButton = "display:none;";
				}
				if(!"".equals(report.getTotalMoney())&&report.getTotalMoney()!=null){
					totalmoney = totalmoney + Double.parseDouble(report.getTotalMoney());
				}				
				reportIdList.add(report);
//				if(report.getState()==null||"".equals(report.getState())){
//					request.setAttribute("execute", "execute");
//				}
//				得到对应的任务Id，以便进行数据的追溯	
				if(report.getId()!=null&&!"".equals(report.getId())){
					taskId = report.getTaskId();
				}
			}

			map.put(partner, String.valueOf(quarterMoney - totalmoney));	
			// 保存ReportInfo表记录
		}
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		String realScore = "";
		String reduceReason = "";
		String remark = "";
		String money = "";
		String total = "";
		String totalNum = "";
		String totalMoney = "";
		String totalScore = "";
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
						realScore = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_sc")));
						reduceReason = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_rs")));
						remark = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_rm")));
						money = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_money")));
						
						totalMoney = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						totalScore = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if(maxListNo==i){
							if("".equals(totalMoney)||"null".equals(totalMoney)){
								map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", report.getTotalMoney());
							}else{
								if(report.getTotalMoney()==null){
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(totalMoney)));
								}else{
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(report.getTotalMoney())+Double.parseDouble(totalMoney)));
								}
							}
							if("".equals(totalScore)||"null".equals(totalScore)){
								map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", report.getTotalScore());
							}else{
								if(report.getTotalScore()==null){
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(totalScore)));
								}else{
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(report.getTotalScore())+Double.parseDouble(totalScore)));
								}
							}							
						}
						
						if("".equals(realScore)||"null".equals(realScore)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", ekis.getRealScore());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", String.valueOf(Double.parseDouble(ekis.getRealScore())+Double.parseDouble(realScore)));
						}	
						if("".equals(reduceReason)||"null".equals(reduceReason)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", ekis.getTime().substring(4,6)+"月原因:"+ekis.getReduceReason()+";");
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", reduceReason+ekis.getTime().substring(4,6)+"月原因:"+ekis.getReduceReason()+";");
						}
						if("".equals(remark)||"null".equals(remark)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", ekis.getRemark());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", remark+ekis.getRemark());
						}						
						if("".equals(money)||"null".equals(money)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", ekis.getMoney());
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", String.valueOf(Double.parseDouble(ekis.getMoney())+Double.parseDouble(money)));
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", String.valueOf(Double.parseDouble(ekis.getMoney())+Double.parseDouble(money)));
						}
						
						String money2 = StaticMethod.null2String(ekis.getMoney());
						if("".equals(money2)){
							money2 = "0.0";
						}
						total = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", money2);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(money2)+Double.parseDouble(total)));
						}	
						
						String realScore2 = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore2)){
							realScore2 = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", realScore2);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore2)+Double.parseDouble(totalNum)));
						}						
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
				ekif.setRemark(ek.getRemark());
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
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		request.setAttribute("year", year); 
		request.setAttribute("quarter", quarter);
		return mapping.findForward("taskDetailListForQuarter");
	}	
	
	/**
	 * 打开考核任务页面(按季度查询)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskViewForQuarter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
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
		request.setAttribute("areaList", areaList);
		request.setAttribute("taskList", taskList2);
		
//    	获得当前年月。。方便用户使用
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	request.setAttribute("quarter", "0"+String.valueOf(DateTimeUtil.getQuarter(month)));
    	request.setAttribute("year", String.valueOf(year));
		return mapping.findForward("taskViewForQuarter");
	}	

	/**
	 * 查看考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskViewSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		List taskList = taskMgr.listTaskOfProvinceAdmin(AssConstants.TEMPLATE_ACTIVATED, templateTypeNode);
		request.setAttribute("areaList", areaList);
		request.setAttribute("taskList", taskList);
//    	获得当前年月。。方便用户使用
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	request.setAttribute("quarter", "0"+String.valueOf(DateTimeUtil.getQuarter(month)));
    	request.setAttribute("year", String.valueOf(year));
    	if(month>=10){
    		request.setAttribute("month", String.valueOf(month));
    	}else{
    		request.setAttribute("month", "0"+String.valueOf(month));
    	}		
		return mapping.findForward("taskViewSearch");
	}	
	
	/**
	 * 查看考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		IAssOperateStepMgr operateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		String partner =  StaticMethod.null2String(request.getParameter("partnerId"));
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = assTemplate.getTemplateName();
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = new ArrayList();
		if("".equals(partner)){
			partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		}else {
			partnerList.add(partner);
		}
		
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		double monthMoney = 0.0;
		if(feeTotal!=null){
			monthMoney = feeTotal.getMonthMoney();
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
			request.setAttribute("uniPrice", uniPrice);
			request.setAttribute("price", feeTotal.getMonthMoney());
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
		PartnerDept partnerDept = null;
		
		String total = "";
		String totalNum = "";
		
		Map map = new HashMap();
		List tableList = new ArrayList();
		int maxLevel = 0;
		List reportIdList = new ArrayList();
		List allStepList = new ArrayList();
		double totalmoney = 0.0;
		for(int i = 0 ;i< partnerList.size();i++){
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			// 保存ReportInfo表记录
			AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, time, partner, "");
			if ("1".equals(report.getIsPublish())) {
				isPublishButton = "display:none;";
			}
//			if(report.getState()==null||"".equals(report.getState())){
//				request.setAttribute("execute", "execute");
//			}			
			if(!"".equals(report.getTotalMoney())&&report.getTotalMoney()!=null){
				totalmoney = Double.parseDouble(report.getTotalMoney());
			}
			map.put(partner+"_par", report.getDeviceNum());
			request.setAttribute(partner+"_par", report.getDeviceNum());			
			map.put(partner, String.valueOf(monthMoney - totalmoney));
			reportIdList.add(report);
			//得到所有厂商的步骤列表
			List operateSteps = operateStepMgr.getAssOperateSteps(report.getId());
			allStepList.add(operateSteps);
			if(report.getId()!=null&&!"".equals(report.getId())){
				taskId = report.getTaskId();
			}
		}
		
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		
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
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", ekis.getMoney());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
						if(maxListNo==i){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", report.getTotalMoney());
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", report.getTotalScore());
						}
						String money = StaticMethod.null2String(ekis.getMoney());
						if("".equals(money)){
							money = "0.0";
						}
						total = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", money);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(money)+Double.parseDouble(total)));
						}	
						
						String realScore = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore)){
							realScore = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", realScore);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore)+Double.parseDouble(totalNum)));
						}				

					}
				}
				// 算法加入
				AssKpi ek = assKpiMgr.getKpi(ekif.getKpiId());
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				if("collection".equals(ek.getIsCollection())){
					map.put(ekif.getId(), ek.getId());
				}
				ekif.setAlgorithm(algorithm);
				ekif.setKpiType(ek.getKpiType());
				ekif.setRemark(ek.getRemark());
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
		request.setAttribute("allStepList", allStepList); //得到所有厂商的步骤列表
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		request.setAttribute("year", year); 
		request.setAttribute("month", month); 		
		request.setAttribute("quarter", quarter); 
		return mapping.findForward("taskList");
	}	

	/**
	 * 查看考核任务页面(按所选时间)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskViewForTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		List taskList = taskMgr.listTaskOfProvinceAdmin(AssConstants.TEMPLATE_ACTIVATED, templateTypeNode);
		request.setAttribute("areaList", areaList);
		request.setAttribute("taskList", taskList);
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts("", specialty);
		request.setAttribute("partnerList", partnerList);
		return mapping.findForward("taskViewForTime");
	}	
	
	
	/**
	 * 查看考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskListForTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
//		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		IAssOperateStepMgr operateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		
		String year2 = StaticMethod.null2String(request.getParameter("year2"));
		String month2 = StaticMethod.null2String(request.getParameter("month2"));
		String quarter2 = StaticMethod.null2String(request.getParameter("quarter2"));
		
		int year3 = Integer.parseInt(year2)-Integer.parseInt(year);
		String partner = StaticMethod.null2String(request.getParameter("partnerId"));
		List timeList = new ArrayList();
		if("".equals(quarter)&&!"".equals(month)){
			int month3 = year3*12+Integer.parseInt(month2)-Integer.parseInt(month)+1;
			int month4 = 0;
			int year4 = 0 ;
			for(int i = 0 ; i<month3;i++ ){
				month4 =  Integer.parseInt(month)+i;
				year4 = Integer.parseInt(year);
				if(month4==13){
					year4 = year4 +1;
					month4 = 1;
				}
				if(month4>9){
					timeList.add(String.valueOf(year4)+String.valueOf(month4));
				}else{
					timeList.add(String.valueOf(year4)+"0"+String.valueOf(month4));
				}
				
			}
		}
		if("".equals(month)&&!"".equals(quarter)){
			int quarter3 =  year3*4+Integer.parseInt(quarter2)-Integer.parseInt(quarter)+1;
			int quarter4 = 0;
			int year4 = 0 ;
			for(int i = 0 ; i<quarter3;i++ ){
				quarter4 =  Integer.parseInt(quarter)+i;
				year4 = Integer.parseInt(year);
				if(quarter4==5){
					year4 = year4 +1;
					quarter4 = 1;
				}
				timeList.add(String.valueOf(year4)+"0"+String.valueOf(quarter4));
			}
		}		
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = assTemplate.getTemplateName();
		Map map = new HashMap();
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		double money = 0.0;
		if(feeTotal!=null){
			if("".equals(quarter)&&!"".equals(month)){
				money = feeTotal.getMonthMoney();
				request.setAttribute("price", feeTotal.getMonthMoney());
			}else{
				money = feeTotal.getQuarterMoney();
				request.setAttribute("price", feeTotal.getQuarterMoney());
			}
			
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
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

		String isPublishButton = "";
//		PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(partner);
		
		String total = "";
		String totalNum = "";
		
		
		List tableList = new ArrayList();
		int maxLevel = 0;

		List reportIdList = new ArrayList();
		List allStepList = new ArrayList();
		
		
		
		// 保存ReportInfo表记录
		for(int i = 0 ;timeList.size()>i;i++){
			double totalmoney = 0.0;
			AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, String.valueOf(timeList.get(i)), partner, "1");
			if ("1".equals(report.getIsPublish())) {
				isPublishButton = "display:none;";
			}
//			if(report.getState()==null||"".equals(report.getState())){
//				request.setAttribute("execute", "execute");
//			}			
			if(!"".equals(report.getTotalMoney())&&report.getTotalMoney()!=null){
				totalmoney = Double.parseDouble(report.getTotalMoney());
			}
			map.put(timeList.get(i)+"_DeviceNum", report.getDeviceNum());
			map.put(timeList.get(i), String.valueOf(money - totalmoney));
			request.setAttribute("partnerDeviceNum", report.getDeviceNum());
			reportIdList.add(report);
			//得到所有厂商的步骤列表
			List operateSteps = operateStepMgr.getAssOperateSteps(report.getId());
			allStepList.add(operateSteps);
//			得到对应的任务Id，以便进行数据的追溯	
			if(report.getId()!=null&&!"".equals(report.getId())){
				taskId = report.getTaskId();
			}
		}
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId); 
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
						map.put(etd.getNodeId()+"_"+report.getTime()+"_sc", ekis.getRealScore());
						map.put(etd.getNodeId()+"_"+report.getTime()+"_rs", ekis.getReduceReason());
						map.put(etd.getNodeId()+"_"+report.getTime()+"_rm", ekis.getRemark());
						map.put(etd.getNodeId()+"_"+report.getTime()+"_pay", ekis.getMoney());
						map.put(etd.getNodeId()+"_"+report.getTime()+"_money", ekis.getMoney());
						if(maxListNo==i){
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_total", report.getTotalMoney());
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_totalNum", report.getTotalScore());
						}
						String money2 = StaticMethod.null2String(ekis.getMoney());
						if("".equals(money2)){
							money2 = "0.0";
						}
						total = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getTime()+"_total")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_total", money2);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_total", String.valueOf(Double.parseDouble(money2)+Double.parseDouble(total)));
						}	
						
						String realScore = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore)){
							realScore = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getTime()+"_totalNum")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_totalNum", realScore);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getTime()+"_totalNum", String.valueOf(Double.parseDouble(realScore)+Double.parseDouble(totalNum)));
						}				

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
				ekif.setRemark(ek.getRemark());
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
			request.setAttribute("timeStr", year+"年"+month+"月到"+year2+"年"+month2+"月"); // 时间
		} else if ("quarter".equals(timeType)){
			request.setAttribute("timeTypeStr", "季度"); // 时间类型
			request.setAttribute("timeStr", year+"年第"+quarter+"季度到"+year2+"年第"+quarter2+"季度"); // 时间
		} else if ("year".equals(timeType)){
			request.setAttribute("timeTypeStr", "年"); // 时间类型
			request.setAttribute("timeStr", year+"年"); // 时间
		}
		request.setAttribute("allStepList", allStepList); //得到所有厂商的步骤列表
		request.setAttribute("timeList", timeList);		//所有时间段
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partner", partner); // 被考核合作伙伴
		return mapping.findForward("taskListForTime");
	}	

	/**
	 * 打开考核任务选择页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assSelectAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		List taskList = taskMgr.listTaskOfProvinceAdmin(AssConstants.TEMPLATE_ACTIVATED, templateTypeNode);
		request.setAttribute("taskList", taskList);
//    	获得当前年月。。方便用户使用
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	request.setAttribute("quarter", "0"+String.valueOf(DateTimeUtil.getQuarter(month)));
    	request.setAttribute("year", String.valueOf(year));
    	if(month>=10){
    		request.setAttribute("month", String.valueOf(month));
    	}else{
    		request.setAttribute("month", "0"+String.valueOf(month));
    	}		
		return mapping.findForward("assSelectAll");
	}	

	/**
	 * 查看考核任务打分结果页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskListAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
		// 找到当前taskId对应的模板
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts("", specialty);
		String time = year + month;
		if("".equals(month)){
			time = year + quarter;
		}
		
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		AssReportInfo report = null;
		Map map = new HashMap();
		for(int k = 0 ;k< areaList.size();k++){
			String user = "";
			String dept = "";
			String createTime = "";
			for(int i = 0 ;i< partnerList.size();i++){
				report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), String.valueOf(areaList.get(k)), timeType, time, String.valueOf(partnerList.get(i)), "1");
				if(report.getTotalScore()==null){
					report.setTotalScore("");
				}
				if("".equals(user)){
					if(report.getCreateUser()!=null){
						user = report.getCreateUser();
					}
				}
				if("".equals(dept)){
					if(report.getCreateDept()!=null){
						dept = report.getCreateDept();
					}
				}
				if("".equals(createTime)){
					if(report.getCreateTime()!=null){
						createTime = report.getCreateTime();
					}
				}
				map.put(String.valueOf(areaList.get(k))+String.valueOf(partnerList.get(i)), report);	
			}
			if(!"".equals(createTime)){
				map.put(String.valueOf(areaList.get(k)), "中国移动通信集团黑龙江有限公司"+mgr.id2Name(String.valueOf(areaList.get(k)),"tawSystemAreaDao")+"分公司("+mgr.id2Name(dept,"tawSystemDeptDao")
						+"-"+mgr.id2Name(user,"tawSystemUserDao")+")"+createTime.substring(0,11));
			}else{
				map.put(String.valueOf(areaList.get(k)), "中国移动通信集团黑龙江有限公司"+mgr.id2Name(String.valueOf(areaList.get(k)),"tawSystemAreaDao")+"分公司未执行");
			}
		}
		request.setAttribute("year", year);
		request.setAttribute("time", StaticMethod.getLocalString().substring(0,11));
		request.setAttribute("partnerList", partnerList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("map", map);
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("year", year); 
		request.setAttribute("month", month); 		
		request.setAttribute("timeType", timeType); 
		request.setAttribute("quarter", quarter); 
		return mapping.findForward("assSelectAllList");
	}	


	/**
	 * 打开考核任务选择页面(传输)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assSelectTran(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		List taskList = taskMgr.listTaskOfProvinceAdmin(AssConstants.TEMPLATE_ACTIVATED, templateTypeNode);
		request.setAttribute("taskList", taskList);
		return mapping.findForward("assSelectTran");
	}		
	

	/**
	 * 查看考核任务打分结果页面(传输,交换)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskListTran(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String isPublishButton = "";
		String total = "";
		String totalNum = "";		
		
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
		
		// 找到当前taskId对应的模板
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = templateMgr.id2Name(et.getTemplateId());
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts("", specialty);
		
		String time = year + month;
		if("".equals(month)){
			time = year + quarter;
		}
		
		List tableList = new ArrayList();
		int maxLevel = 0;

		List reportIdList = new ArrayList();
		
		Map map = new HashMap();
		for(int i = 0 ;i< partnerList.size();i++){
			for(int k = 0 ;k< areaList.size();k++){
				AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), String.valueOf(areaList.get(k)), timeType, time, String.valueOf(partnerList.get(i)), "1");
				if(report.getId()!=null&&!"".equals(report.getId())){
					reportIdList.add(report);
					map.put(String.valueOf(partnerList.get(i))+"_num", StaticMethod.nullObject2int(map.get(String.valueOf(partnerList.get(i)+"_num")),0)+1);
//					得到对应的任务Id，以便进行数据的追溯	
					taskId = report.getTaskId();
				}
			}
		}
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId); 
		if("112131102".equals(specialty)){
			ICityWeightMgr cityWeightMgr = (ICityWeightMgr) getBean(beenNameForCityWeightMgr);
			ICityWeightExeMgr cityWeightExeMgr = (ICityWeightExeMgr) getBean(beenNameForCityWeightExeMgr);
			CityWeightExe cityWeightExe = null ;
			CityWeight cityWeight = null ;
			List cityWeightExeList = (List)cityWeightExeMgr.getCityWeightExes(" and templateTreeId ='"+assTemplate.getBelongNode()+"' and timeType='"+timeType+"' and time='"+time+"'");
			if(cityWeightExeList.size()>0){
				for(int i = 0 ;cityWeightExeList.size()>i;i++){
					cityWeightExe = (CityWeightExe)cityWeightExeList.get(i);
					map.put("cityWeight"+cityWeightExe.getCity(), cityWeightExe.getWeight());
				}
			} else {
				List cityWeightList = (List)cityWeightMgr.getCityWeights(" and templateTreeId ='"+assTemplate.getBelongNode()+"'");
				for(int i = 0 ;cityWeightList.size()>i;i++){
					cityWeight = (CityWeight)cityWeightList.get(i);
					map.put("cityWeight"+cityWeight.getCity(), cityWeight.getWeight());
					cityWeightExe = new CityWeightExe();
					cityWeightExe.setCity(cityWeight.getCity());
					cityWeightExe.setCreateTime(StaticMethod.getLocalString());
					cityWeightExe.setCreator(cityWeight.getCreator());
					cityWeightExe.setDeleted(cityWeight.getDeleted());
					cityWeightExe.setTemplateId(cityWeight.getTemplateId());
					cityWeightExe.setTemplateTreeId(cityWeight.getTemplateTreeId());
					cityWeightExe.setTime(time);
					cityWeightExe.setTimeType(timeType);
					cityWeightExe.setWeight(cityWeight.getWeight());
					cityWeightExeMgr.saveCityWeightExe(cityWeightExe);
				}			
			}			
		}

		String realScore = "";
		String reduceReason = "";
		String remark = "";
		String money = "";
		String totalMoney = "";
		String totalScore = "";		
		
		
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
				// 算法加入
				AssKpi ek = assKpiMgr.getKpi(ekif.getKpiId());
				
				String algorithm = ek.getAlgorithm();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				ekif.setKpiType(ek.getKpiType());
				ekif.setRemark(ek.getRemark());
				for(int k=0;k<reportIdList.size();k++){
					if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
						AssReportInfo report = (AssReportInfo)reportIdList.get(k);

	//						交换专业按照地市配置获得分数
						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByReport(etd.getId(),report.getId());
						double weight = 1;
	//						判断单项汇总类型 ,获得权重值
						if(!"112131102".equals(specialty)){
							//								等待获得权重，先都以平均计算
//							if("weightAvg".equals(ek.getOneTotal())){
//
//							}else{
//								String temp = String.valueOf(map.get(report.getPartnerId()+"_num"));
//								if(null!=temp&&!"".equals(temp)&&!"null".equals(temp)){
									weight = 1/Double.valueOf(String.valueOf(map.get(report.getPartnerId()+"_num")));
//								}
//							}							
						}else{
//							List cityWeightList = (List)cityWeightMgr.getCityWeights(" and templateTreeId ='"+assTemplate.getBelongNode()+"' and city ='"+report.getArea()+"'");
//							if(cityWeightList.size()>0){
//								cityWeight =(CityWeight)cityWeightList.get(0);
							weight = Double.valueOf(StaticMethod.nullObject2String(map.get("cityWeight"+report.getArea()),"0.0"))/100;
//							}
						}
	
						realScore = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore)){
							realScore = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if("".equals(totalNum)||"null".equals(totalNum)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore)*weight));
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore)*weight+Double.parseDouble(totalNum)));
						}	
						if(maxListNo==i){
							totalScore = StaticMethod.null2String(report.getTotalScore());
							if("".equals(totalScore)){
								totalScore = "0.0";
							}						
							total = StaticMethod.null2String(String.valueOf(map.get(report.getPartnerId()+"_total")));
							if("".equals(total)||"null".equals(total)){
								map.put(report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(totalScore)*weight));
							}else{
								map.put(report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(totalScore)*weight+Double.parseDouble(total)));
							}			
						}
					}
				}
				rowListShow.add(ekif);
			}
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}

		request.setAttribute("taskName", taskName);
		request.setAttribute("taskId", taskId);
		request.setAttribute("year", year);
		request.setAttribute("time", StaticMethod.getLocalString().substring(0,11));
		request.setAttribute("partnerList", partnerList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("map", map);
		request.setAttribute("tableList", tableList);
		request.setAttribute("maxLevel", maxLevel);
		request.setAttribute("quarter", quarter);
		request.setAttribute("timeType", timeType);
		request.setAttribute("month", month);
		return mapping.findForward("taskListTran");
	}	

	/**
	 * 查看考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assessListPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		IAssOperateStepMgr operateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		
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
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		double monthMoney = 0.0;
		if(feeTotal!=null){
			monthMoney = feeTotal.getMonthMoney();
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
			request.setAttribute("uniPrice", uniPrice);
			request.setAttribute("price", feeTotal.getMonthMoney());
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
		PartnerDept partnerDept = null;
		String partner = "";
		String total = "";
		String totalNum = "";
		
		Map map = new HashMap();
		List tableList = new ArrayList();
		int maxLevel = 0;

		List reportIdList = new ArrayList();
		List allStepList = new ArrayList();
		double totalmoney = 0.0;
		for(int i = 0 ;i< partnerList.size();i++){
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			// 保存ReportInfo表记录
			AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, time, partner, "1");
			if ("1".equals(report.getIsPublish())) {
				isPublishButton = "display:none;";
			}
			if(!"".equals(report.getTotalMoney())&&report.getTotalMoney()!=null){
				totalmoney = Double.parseDouble(report.getTotalMoney());
			}
			map.put(partner+"_par", report.getDeviceNum());
			request.setAttribute(partner+"_par", report.getDeviceNum());			
			map.put(partner, String.valueOf(monthMoney - totalmoney));
			reportIdList.add(report);
//			得到对应的任务Id，以便进行数据的追溯	
			if(report.getId()!=null&&!"".equals(report.getId())){
				taskId = report.getTaskId();
			}		
		}
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
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
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", ekis.getMoney());
						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
						if(maxListNo==i){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", report.getTotalMoney());
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", report.getTotalScore());
						}
						String money = StaticMethod.null2String(ekis.getMoney());
						if("".equals(money)){
							money = "0.0";
						}
						total = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", money);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(money)+Double.parseDouble(total)));
						}	
						
						String realScore = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore)){
							realScore = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", realScore);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore)+Double.parseDouble(totalNum)));
						}				

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
				ekif.setRemark(ek.getRemark());
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
		request.setAttribute("allStepList", allStepList); //得到所有厂商的步骤列表
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		request.setAttribute("year", year); 
		request.setAttribute("month", month); 		
		request.setAttribute("quarter", quarter); 
		return mapping.findForward("assessListPrint");
	}	


	/**
	 * 查看考核任务页面（按季度）打印输出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listQuarterPrint(ActionMapping mapping, ActionForm form,
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
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		List partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);
		//查询的到线路专业某地市扣1分关联的金额
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotal feeTotal = feeTotalMgr.getFeeTotalByArea(areaId, year);
		double quarterMoney = 0.0;
		if(feeTotal!=null){
			quarterMoney = feeTotal.getQuarterMoney();
			String uniPrice = String.valueOf(feeTotal.getPointMoney().doubleValue());
			request.setAttribute("uniPrice", uniPrice);
			request.setAttribute("price", feeTotal.getQuarterMoney());
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
		List timeList = new ArrayList();
		if("01".equals(quarter)){
			timeList.add(year+"01");
			timeList.add(year+"02");
			timeList.add(year+"03");
		}else if("02".equals(quarter)){
			timeList.add(year+"04");
			timeList.add(year+"05");
			timeList.add(year+"06");
		}else if("03".equals(quarter)){
			timeList.add(year+"07");
			timeList.add(year+"08");
			timeList.add(year+"09");
		}else if("04".equals(quarter)){
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
		String isPublishButton = "";
		PartnerDept partnerDept = null;
		String partner = "";
		Map map = new HashMap();
		List tableList = new ArrayList();
		int maxLevel = 0;
		List reportIdList = new ArrayList();
		
		for(int i = 0 ;i< partnerList.size();i++){
			double totalmoney = 0.0;
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			
			for(int j = 0 ;j< timeList.size();j++){
				AssReportInfo report = assReportInfoMgr.getReportInfoByTimeAndPartner(taskId, areaId, timeType, String.valueOf(timeList.get(j)), partner, "1");
				if ("1".equals(report.getIsPublish())) {
					isPublishButton = "display:none;";
				}
				if(!"".equals(report.getTotalMoney())&&report.getTotalMoney()!=null){
					totalmoney = totalmoney + Double.parseDouble(report.getTotalMoney());
				}				
				reportIdList.add(report);
//				if(report.getState()==null||"".equals(report.getState())){
//					request.setAttribute("execute", "execute");
//				}
//				得到对应的任务Id，以便进行数据的追溯	
				if(report.getId()!=null&&!"".equals(report.getId())){
					taskId = report.getTaskId();
				}	
			}

			map.put(partner, String.valueOf(quarterMoney - totalmoney));	
			// 保存ReportInfo表记录
		}
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		String realScore = "";
		String reduceReason = "";
		String remark = "";
		String money = "";
		String total = "";
		String totalNum = "";
		String totalMoney = "";
		String totalScore = "";
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
						realScore = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_sc")));
						reduceReason = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_rs")));
						remark = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_rm")));
						money = StaticMethod.null2String(String.valueOf(map.get(etd.getNodeId()+"_"+report.getPartnerId()+"_money")));
						
						totalMoney = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						totalScore = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if(maxListNo==i){
							if("".equals(totalMoney)||"null".equals(totalMoney)){
								map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", report.getTotalMoney());
							}else{
								if(report.getTotalMoney()==null){
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(totalMoney)));
								}else{
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(report.getTotalMoney())+Double.parseDouble(totalMoney)));
								}
							}
							if("".equals(totalScore)||"null".equals(totalScore)){
								map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", report.getTotalScore());
							}else{
								if(report.getTotalScore()==null){
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(totalScore)));
								}else{
									map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(report.getTotalScore())+Double.parseDouble(totalScore)));
								}
							}							
						}
						
						if("".equals(realScore)||"null".equals(realScore)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", ekis.getRealScore());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", String.valueOf(Double.parseDouble(ekis.getRealScore())+Double.parseDouble(realScore)));
						}	
						if("".equals(reduceReason)||"null".equals(reduceReason)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", ekis.getTime().substring(4,6)+"月原因:"+ekis.getReduceReason()+";");
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", reduceReason+ekis.getTime().substring(4,6)+"月原因:"+ekis.getReduceReason()+";");
						}
						if("".equals(remark)||"null".equals(remark)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", ekis.getRemark());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", remark+ekis.getRemark());
						}						
						if("".equals(money)||"null".equals(money)){
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", ekis.getMoney());
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
						}else{
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_pay", String.valueOf(Double.parseDouble(ekis.getMoney())+Double.parseDouble(money)));
							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", String.valueOf(Double.parseDouble(ekis.getMoney())+Double.parseDouble(money)));
						}
						
						String money2 = StaticMethod.null2String(ekis.getMoney());
						if("".equals(money2)){
							money2 = "0.0";
						}
						total = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", money2);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_total", String.valueOf(Double.parseDouble(money2)+Double.parseDouble(total)));
						}	
						
						String realScore2 = StaticMethod.null2String(ekis.getRealScore());
						if("".equals(realScore2)){
							realScore2 = "0.0";
						}
						totalNum = StaticMethod.null2String(String.valueOf(map.get(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum")));
						if("".equals(total)||"null".equals(total)){
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", realScore2);
						}else{
							map.put(etd.getParentNodeId()+"_"+report.getPartnerId()+"_totalNum", String.valueOf(Double.parseDouble(realScore2)+Double.parseDouble(totalNum)));
						}						
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
				ekif.setRemark(ek.getRemark());
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
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("partnerList", partnerList); // 被考核合作伙伴
		request.setAttribute("year", year); 
		request.setAttribute("quarter", quarter);
		return mapping.findForward("listQuarterPrint");
	}	

	/**
	 * 得到考核任务一个季度同一节点下的分数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward nodeQuarter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String timeType = StaticMethod.null2String(request.getParameter("timeType"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String kpiInstanceId = StaticMethod.null2String(request.getParameter("kpiInstanceId"));
		String treeNodeId = StaticMethod.null2String(request.getParameter("treeNodeId"));
		String weight = StaticMethod.null2String(request.getParameter("weight"));
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = getUserId(request);
		IAssRoleService roleService = (IAssRoleService) getBean(beenNameForRoleService);
		List areaList = roleService.getAreasByUserId(userId,AssConstants.OPERATE_REPORT_EXECUTE);
		
		// 找到当前taskId对应的模板name
		AssTask et = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(et.getTemplateId());
		String taskName = assTemplate.getTemplateName();
		
		String time = year ;
		if("".equals(month)&&!"".equals(quarter)){
			time = time + quarter;
		} 
		if("".equals(quarter)&&!"".equals(month)){
			time = time + month;
		}
		
//		查询该合作伙伴维护的地市
		List partnerList = partnerDeptMgr.getPartnerDepts(" and interfaceHeadId = '"+partnerId+"' and specialty = '"+specialty+"'");
		List areaList1 = new ArrayList();
		PartnerDept partnerDept = null;
		for (int i = 0; i < partnerList.size(); i++) {
			partnerDept = (PartnerDept)partnerList.get(i);
			if(!areaList1.contains(partnerDept.getAreaId())){
				areaList1.add(partnerDept.getAreaId());
			}
		}
		
		List areaList2 = new ArrayList();
		for(int i = 0; i < areaList.size(); i++){
//			System.out.println(areaList.get(i));
			for(int k = 0; k < areaList1.size(); k++){
//				System.out.println(areaList1.get(k));
				if(areaList.get(i).equals(areaList1.get(k))){
					if(!areaList2.contains(areaList1.get(k))&&!areaList2.contains(areaList.get(i))){
						areaList2.add(areaList.get(i));
					}
				}
			}
		}
		String partner = "";
		Map map = new HashMap();
		List tableList = new ArrayList();
		String timeQuarter = "";
		AssKpiInstance ekis = null;
		for(int i = 0 ;i< areaList2.size();i++){
			areaId = String.valueOf(areaList2.get(i));
			// 保存ReportInfo表记录
			AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, time, partnerId, "1");
			if(report.getId()!=null&&!"".equals(report.getId())){
				ekis = assKpiInstanceMgr.getKpiInstanceByReport(kpiInstanceId,report.getId());
				map.put(areaId, ekis.getRealScore());
				map.put(areaId+"_rr", ekis.getReduceReason());
				map.put(areaId+"_DeviceNum", report.getDeviceNum());
			}else{
				map.put(areaId, "0");
				map.put(areaId+"_rr", "");
				map.put(areaId+"_DeviceNum", "");
			}
			request.setAttribute("partnerDeviceNum",report.getDeviceNum());
		}
		AssKpi ek = assKpiMgr.getKpi(kpiId);
		String algorithm = ek.getAlgorithm();
		if (algorithm == null || "".equals(algorithm)) {
			algorithm = "无";
		}
		map.put("algorithm", algorithm);
		map.put("remark", ek.getRemark());
		map.put("kpiId", kpiId);
		
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
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("areaId", areaId); // 地市信息
		request.setAttribute("areaList", areaList2); 
		request.setAttribute("treeNodeId", treeNodeId);
		request.setAttribute("partnerId", partnerId); // 合作伙伴Id
		request.setAttribute("kpiId", kpiId); // kpiId
		request.setAttribute("weight", weight);
		return mapping.findForward("nodeQuarter");
	}
	public ActionForward kpiPicture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String timeNum = StaticMethod.null2String(request.getParameter("timeNum"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		String partner = StaticMethod.null2String(request.getParameter("partner"));
		String total = StaticMethod.null2String(request.getParameter("total"));
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		int temp = Integer.parseInt(timeNum);
		List dateList = new ArrayList();
		String strXml = "";
		Float realScore = 0.0f;
		FusionChart fusionChart = new FusionChart();
		if(!"".equals(total)){
			for(int i = 0 ;i<temp;i++){
				String time = StaticMethod.null2String(request.getParameter("time"+String.valueOf(i)));
				String totalNum = StaticMethod.null2String(request.getParameter("total"+String.valueOf(i)));
					FusionChartData fusionChartData = new FusionChartData();
					fusionChartData.setTitle(String.valueOf(time));
					fusionChartData.setNumForFloat(Float.parseFloat(totalNum));
					dateList.add(fusionChartData);
			}
		}else{
			for(int i = 0 ;i<temp;i++){
				String time = StaticMethod.null2String(request.getParameter("time"+String.valueOf(i)));
				realScore = assKpiInstanceMgr.getKpiInstanceRealScore(nodeId, city, time, partner);
					FusionChartData fusionChartData = new FusionChartData();
					fusionChartData.setTitle(String.valueOf(time));
					fusionChartData.setNumForFloat(realScore);
					dateList.add(fusionChartData);
			}
		}
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		StringBuffer sb = new StringBuffer();
		sb.append("合作伙伴-(");
		sb.append(mgr.id2Name(partner,"partnerDeptDao"));
		sb.append(")指标名称-(");
		sb.append(mgr.id2Name(kpiId,"lineAssKpiDao"));
		sb.append(")得分对比图");
		fusionChart.setCaption(sb.toString());
		strXml = FusionChartMethod.getString(dateList, fusionChart);
		request.setAttribute("strXML", strXml);
		return mapping.findForward("kpiPicture");
	}
}
