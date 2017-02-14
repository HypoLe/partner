/**
 * 
 */
package com.boco.eoms.partner.assess.AssExecute.webapp.action;

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
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssFlowMgr;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssOperateStepMgr;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.service.IAssRoleService;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.assess.util.DateTimeUtil;
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
 * Date:Nov 30, 2010 2:11:22 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssExecuteAction extends BaseAction {
	
	protected String beenNameForTreeMgr = "";
	protected String beenNameForTemplateMgr = "";
	protected String beenNameForKpiMgr = "";
	protected String beenNameForWeightMgr = "IassWeightMgr";
	protected String beenNameForTaskMgr = "";
	protected String beenNameForTaskDetailMgr ="";
	protected String beenNameForKpiInstanceMgr = "";
	protected String beenNameForReportInfoMgr = "";
	protected String beenNameForFactoryMgr = "";
	protected String beenNameForFlowMgr = "";
	protected String beenNameForOperateStepMgr = "";
	protected String beenNameForRoleService = "";
	protected String templateTypeNode = "";
	protected String specialty = "";
	protected String beenNameForSelectedInstanceMgr = "";

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return taskView(mapping, form, request, response);
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
	public ActionForward taskView(ActionMapping mapping, ActionForm form,
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
    	
		return mapping.findForward("taskView");
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
		IAssSelectedInstanceMgr instanceMgr = null;
//		传输专业，辅助设备代维
		if("112131101".equals(specialty)||"112131105".equals(specialty)){
			instanceMgr = (IAssSelectedInstanceMgr) getBean(beenNameForSelectedInstanceMgr);
		}
		
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		//查询要考核的厂商
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
//		List factoryList = assFactoryMgr.getAllKpiFactory(et.getTemplateId());
//		List partnerList = new ArrayList();
//		for(int i=0;i<factoryList.size();i++){
//			AssFactory assFactory = (AssFactory)factoryList.get(i);
//			partnerList.add(assFactory.getFactory());
//		}
		List partnerList = assFactoryMgr.getPartnerDepts(areaId, specialty);

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
		List reportIdList = new ArrayList();
		String afterTime = "";
		AssReportInfo reInfo1 = null;
		AssSelectedInstance assSelectedInstance = null;
		AssKpiInstance ekis1 = null;
		for(int i = 0 ;i< partnerList.size();i++){
			partner = String.valueOf(partnerList.get(i));
			partnerDept = partnerDeptMgr.getPartnerDept(partner);
			// 保存ReportInfo表记录
			AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, time, partner, "");
			map.put(report.getId()+"partner", partner);
//			引用上月记录
			if(report.getId()==null||"".equals(report.getId())){
				if(time.substring(4).equals("01")){
					if(timeType.equals("quarter")){
						afterTime = String.valueOf(Integer.parseInt(year)-1)+"04";
					}else{
						afterTime = String.valueOf(Integer.parseInt(year)-1)+"12";
					}
					
				}else{
					afterTime = String.valueOf(Integer.parseInt(time)-1);
				}
//				获得上月记录
				report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, afterTime, partner, "");
				if(report.getId()!=null&&!"".equals(report.getId())&&taskId.equals(report.getTaskId())){
					reInfo1 = new AssReportInfo();
					reInfo1.setCreateTime(StaticMethod.getLocalString());
					reInfo1.setPartnerId(partner.trim());
					reInfo1.setPartnerName(partnerDept.getName());
					reInfo1.setTaskId(taskId);
					reInfo1.setTaskName(assTemplate.getTemplateName());
					reInfo1.setTime(time);
					reInfo1.setTimeType(timeType);
					reInfo1.setArea(areaId);
					reInfo1.setCreateUser(userId);
					reInfo1.setCreateDept(deptId);
					reInfo1.setTreeNodeId(assTemplate.getBelongNode());
					reInfo1.setTemplateTypeNode(templateTypeNode);
					reInfo1.setState(AssConstants.ASS_STATE_DRAFT);
					reInfo1.setTotalScore(report.getTotalScore());
					reInfo1.setTotalMoney(report.getTotalMoney());
					reInfo1.setTotalWeight(report.getTotalWeight());
					reInfo1.setDeviceNum(report.getDeviceNum());
					assReportInfoMgr.saveAssReportInfo(reInfo1);
//					查询并保存指标为采集的实例
					List assKpiList = (List)assKpiInstanceMgr.getAssKpiInstanceByQuote(report.getId(),"quote");
					for(int j = 0; j < assKpiList.size(); j++){
						ekis1 = (AssKpiInstance)assKpiList.get(j);
						AssKpiInstance ekis = new AssKpiInstance();
						ekis.setPartnerId(partner.trim());
						ekis.setPartnerName(partnerDept.getName());
						ekis.setRealScore(ekis1.getRealScore());
						ekis.setReduceReason(ekis1.getReduceReason());
						ekis.setRemark(ekis1.getRemark());
						ekis.setMoney(ekis1.getMoney());
						ekis.setTaskDetailId(ekis1.getTaskDetailId());
						ekis.setReportId(reInfo1.getId());
						ekis.setTime(time);
						ekis.setTimeType(timeType);
						ekis.setCity(areaId);
						ekis.setCreateTime(StaticMethod.getLocalString());
						assKpiInstanceMgr.saveKpiInstance(ekis);
					}

//					得到打分指标实例
					if("112131101".equals(specialty)||"112131105".equals(specialty)){
						List instanceList = (List)instanceMgr.getAssSelectedInstanceListByQuote(taskId, areaId, afterTime, partner, "quote");
						for(int l = 0 ;instanceList.size()>l;l++){
							assSelectedInstance = (AssSelectedInstance)instanceList.get(l);
							AssSelectedInstance assSelectedInstance1 = new AssSelectedInstance();
							assSelectedInstance1.setAreaId(areaId);
							assSelectedInstance1.setConfigId(assSelectedInstance.getConfigId());
							assSelectedInstance1.setKpiId(assSelectedInstance.getKpiId());
							assSelectedInstance1.setNodeId(assSelectedInstance.getNodeId());
							assSelectedInstance1.setPartnerId(partner);
							assSelectedInstance1.setReportId(reInfo1.getId());
							assSelectedInstance1.setTaskId(taskId);
							assSelectedInstance1.setTime(time);
							assSelectedInstance1.setWeight(assSelectedInstance.getWeight());
							instanceMgr.saveAssSelectedInstance(assSelectedInstance1);
						}
					}
					report = reInfo1;
					request.setAttribute("after", "(已引用上月数据)");
				}
			}

			if(report.getState()!=null&&!(AssConstants.ASS_STATE_DRAFT).equals(report.getState())&&!(AssConstants.ASS_STATE_NEW).equals(report.getState())){
				isPublishButton = "display:none;";
				readOnly = "readonly='true'";
			}
			if(report.getState()!=null&&!"".equals(report.getState())&&!(AssConstants.ASS_STATE_DRAFT).equals(report.getState())){
				request.setAttribute("execute", "execute");
			}
			request.setAttribute(partner+"_par", report.getDeviceNum());
			reportIdList.add(report);
		}
		
		int maxLevel = 0;
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
				for(int f = 0; f < partnerList.size(); f++){
					if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
						AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), areaId, timeType, time, String.valueOf(partnerList.get(f)), "");
//						自动采集后不存在reportId ，所以使用按条件查询的方式。
//						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByReport(etd.getId(),report.getId());
						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByTimeAndPartner(etd.getId(),timeType,time,String.valueOf(partnerList.get(f)),areaId);
						map.put(etd.getNodeId()+"_"+String.valueOf(partnerList.get(f))+"_sc", ekis.getRealScore());
						map.put(etd.getNodeId()+"_"+String.valueOf(partnerList.get(f))+"_rs", ekis.getReduceReason());
						map.put(etd.getNodeId()+"_"+String.valueOf(partnerList.get(f))+"_rm", ekis.getRemark()); 
						map.put(etd.getNodeId()+"_"+String.valueOf(partnerList.get(f))+"_money", ekis.getMoney());
						//当得分是指标分数的百分之25
						if(etd.getWeight().floatValue()!=0&&(Float.valueOf(StaticMethod.null2String(ekis.getRealScore(),"0"))/etd.getWeight().floatValue())<=0.25){
							map.put(etd.getNodeId()+"_"+String.valueOf(partnerList.get(f))+"_color", "color:red");
						}
					}
				}
//				for(int k=0;k<reportIdList.size();k++){
//					if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
//						AssReportInfo report = (AssReportInfo)reportIdList.get(k);
////						自动采集后不存在reportId ，所以使用按条件查询的方式。
////						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByReport(etd.getId(),report.getId());
//						AssKpiInstance ekis = assKpiInstanceMgr.getKpiInstanceByTimeAndPartner(etd.getId(),timeType,time,String.valueOf(map.get(report.getId()+"partner")),areaId);
//						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_sc", ekis.getRealScore());
//						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rs", ekis.getReduceReason());
//						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_rm", ekis.getRemark());
//						map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_money", ekis.getMoney());
//						//当得分是指标分数的百分之25
//						if(etd.getWeight().floatValue()!=0&&(Float.valueOf(StaticMethod.null2String(ekis.getRealScore(),"0"))/etd.getWeight().floatValue())<=0.25){
//							map.put(etd.getNodeId()+"_"+report.getPartnerId()+"_color", "color:red");
//						}
//					}
//				}
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
				//全扣分指标得到该项父结点包含所有叶子结点的集合,用户页面脚本求和
				if("deductScore".equals(ek.getKpiType())){
					ekif.setNodesForTotal(taskDetailMgr.getLeafNodesOfChildForDeduct(taskId, etd.getParentNodeId()));
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
		//得到网组设备量
		for(int k=0;k<reportIdList.size();k++){
				AssReportInfo report = (AssReportInfo)reportIdList.get(k);
				map.put(report.getPartnerId()+"_par", report.getDeviceNum());
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
	
	/**
	 * 根据taskId得到对应模板的评估周期
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCycle(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		AssTask assTask = taskMgr.getTaskById(taskId);
		AssTemplate assTemplate = templateMgr.getTemplate(assTask.getTemplateId());
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(assTemplate.getCycle());
		response.getWriter().flush();
		return null;
	}	
	/**
	 * 保存评估表打分记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTaskDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if (xsave(mapping, form, request, response,
				AssConstants.ASS_STATE_DRAFT))
			return mapping.findForward("success");
		else
			return mapping.findForward("fail");
	}
	/**
	 * 发布评估表打分记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward commitTaskDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (xsave(mapping, form, request, response, AssConstants.ASS_STATE_NEW))
			return mapping.findForward("success");
		else
			return mapping.findForward("fail");
	}

	/**
	 * 保存任务细节
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public boolean xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String state) throws Exception {
			IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean(beenNameForTaskDetailMgr);
			IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
			IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			IAssTaskMgr taskMgr = (IAssTaskMgr) getBean(beenNameForTaskMgr);
			IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
			IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			String treeNodeId = StaticMethod.null2String(request.getParameter("treeNodeId"));
		try {
			//根据流程xml得到下一步状态
			String nextState = (flowMgr.getAssFlowByXml(state)).getGotoCell();
			
			String taskId = StaticMethod.null2String(request.getParameter("taskId"));
			String timeType = StaticMethod.null2String(request.getParameter("timeType"));
			String time = StaticMethod.null2String(request.getParameter("time"));
			String areaId = StaticMethod.null2String(request.getParameter("areaId"));
			String[] partnerId = request.getParameterValues("partnerId");
			PartnerDept partnerDept = null;
			String partner = "";
			AssTask assTask = taskMgr.getTaskById(taskId);
			AssTemplate assTemplate = templateMgr.getTemplate(assTask.getTemplateId());
//			有几个合作伙伴保存几次
			for(int i = 0 ;i< partnerId.length;i++){
				partner = partnerId[i];
				partnerDept = partnerDeptMgr.getPartnerDept(partner);
				// 保存ReportInfo表记录
				String par = StaticMethod.null2String(request.getParameter(partner + "_par"));
				AssReportInfo report = assReportInfoMgr.getReportInfoByTimeAndPartner(taskId, areaId, timeType, time, partner, "");
				if(report.getId()==null){
					report.setCreateTime(StaticMethod.getLocalString());
					report.setPartnerId(partner.trim());
					report.setPartnerName(partnerDept.getName());
					report.setTaskId(taskId);
					report.setTaskName(assTemplate.getTemplateName());
					report.setTime(time);
					report.setTimeType(timeType);
					report.setArea(areaId);
					report.setCreateUser(userId);
					report.setCreateDept(deptId);
					report.setTreeNodeId(treeNodeId);
					report.setTemplateTypeNode(templateTypeNode);
					assReportInfoMgr.saveAssReportInfo(report);
				}

				//各打分指标信息记录
				int maxLevel = 0;
				int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
				// 指标实际总分
				float totalScore = 0;
				// 实际金额
				float totalMoney = 0;
				// 指标权重总分
				float totalWeight = 0;
				for (int j = 1; j <= maxListNo; j++) {
					List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
							String.valueOf(j));
					for (int k = 0; k < rowList.size(); k++) {
						AssTaskDetail etd = (AssTaskDetail) rowList.get(k);
						if (AssConstants.NODE_LEAF.equals(etd.getLeaf())) {
							String nodeId = etd.getNodeId();
							String realScore = StaticMethod.null2String(request
									.getParameter(nodeId +"_"+ partner + "_sc"));
							String reduceReason = StaticMethod.null2String(request
									.getParameter(nodeId +"_"+ partner + "_rs"));
							String remark = StaticMethod.null2String(request
									.getParameter(nodeId +"_"+ partner + "_rm"));
							String money = StaticMethod.null2String(request
									.getParameter(nodeId +"_"+ partner + "_pay"));
							if (realScore != null && !"".equals(realScore)) {
								realScore = Float.valueOf(realScore).toString();
								// 加入总分
								totalScore = totalScore
										+ Float.valueOf(realScore).floatValue();
							}
							if (money != null && !"".equals(money)) {
								money = Float.valueOf(money).toString();
								// 加入总分
								totalMoney = totalMoney
										+ Float.valueOf(money).floatValue();
							}
							totalWeight = totalWeight
									+ etd.getWeight().floatValue();
	
							AssKpiInstance ekis = assKpiInstanceMgr
									.getKpiInstanceByTimeAndPartner(etd.getId(),
											timeType, time, partner,areaId);
							if (ekis.getId() == null || "".equals(ekis.getId())) {
								ekis.setCreateTime(StaticMethod.getLocalString());
							}
//							ekis.setIsPublish(saveType);
							ekis.setPartnerId(partner.trim());
							ekis.setPartnerName(partnerDept.getName());
							ekis.setRealScore(realScore);
							ekis.setReduceReason(reduceReason);
							ekis.setRemark(remark);
							ekis.setMoney(money);
							ekis.setTaskDetailId(etd.getId());
							ekis.setReportId(report.getId());
							ekis.setTime(time);
							ekis.setTimeType(timeType);
							ekis.setCity(areaId);
							assKpiInstanceMgr.saveKpiInstance(ekis);
						}
					}
					if (rowList.size() > maxLevel) {
						maxLevel = rowList.size();
					}
				}
	
				// 将总分更新到ReportInfo表中
//				report.setIsPublish(saveType);
				report.setState(nextState);
				if("1".equals(nextState)){
					report.setCreateUser(userId);
					report.setCreateDept(deptId);
					report.setCreateTime(StaticMethod.getLocalString());
				}
				report.setDeviceNum(par);
				report.setTotalScore(String.valueOf(totalScore));
				report.setTotalMoney(String.valueOf(totalMoney));
				report.setTotalWeight(String.valueOf(totalWeight));
				assReportInfoMgr.saveAssReportInfo(report);
				//评估流程控制
				if(state.equals(AssConstants.ASS_STATE_NEW)){
				IAssOperateStepMgr operateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
				operateStepMgr.saveAssOperateStep(request, report.getId(), nextState);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
}
