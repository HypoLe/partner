package com.boco.eoms.partner.eva.webapp.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiFacturyMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiInstanceMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaReportInfoMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskAuditMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskDetailMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTaskAudit;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;
import com.boco.eoms.partner.eva.util.PnrEvaStaticMethod;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiInstanceForm;

public final class PnrEvaTaskAction extends BaseAction {

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String partnerNodeType = StaticMethod.null2String(request.getParameter("partnerNodeType"));
		if("templateType".equals(partnerNodeType)){
			return taskViewForPartner(mapping, form, request, response);
		}
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
//		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));; 
		
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTree et = (PnrEvaTree) list.get(i);
			templateType.add(et);
		}
		request.setAttribute("areaId", areaId);
		request.setAttribute("templateType", templateType);
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
	public ActionForward taskViewForPartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
//		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));; 
		
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTree et = (PnrEvaTree) list.get(i);
			templateType.add(et);
		}
		request.setAttribute("areaId", areaId);
		request.setAttribute("templateType", templateType);
		return mapping.findForward("taskViewForPartner");
	}
	/**
	 * 页面二级联动，已知模板分类，返回对应模板任务list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeTemplateType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateTypeId = request.getParameter("templateTypeId");
		// 已激活的任务列表
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr tempMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		//2009-8-4 模版显示加角色控制，考核模版区（省）负责人能看到所有模版
		List list = new ArrayList();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PnrEvaRoleIdList pnrEvaRoleIdList = (PnrEvaRoleIdList) getBean("pnrEvaRoleIdList");
		String isProvinceAdmin = "N";
		if(sessionForm.getUserid().equals("admin")){
			isProvinceAdmin = "Y";
		}
		else {
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == Integer.parseInt(pnrEvaRoleIdList.getReportExecuteRoleId())){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
//		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));;
//		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		
		String deptId = sessionform.getDeptid();
		String executeOrg = PnrEvaConstants.EXECUTE_ORG_EC;//各地域
		
		if(deptId.indexOf(pnrEvaRoleIdList.getNdDept())==0){
			executeOrg = PnrEvaConstants.EXECUTE_ORG_ND;//省网络部
		}else if(deptId.indexOf(pnrEvaRoleIdList.getNmcDept())==0){
			executeOrg = PnrEvaConstants.EXECUTE_ORG_NMC;//省网络中心
		}
		list = taskMgr.listTaskOfArea(areaId,PnrEvaConstants.TEMPLATE_ACTIVATED, templateTypeId,executeOrg);
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		String templateIdTemp = "";
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTask et = (PnrEvaTask) list.get(i);
			PnrEvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
			String starttime = "";
			if(template.getStartTime()!=null&&!template.getStartTime().equals("")){
				starttime = template.getStartTime().substring(0,10);
			}
			//过滤掉重复的任务
			if(!templateIdTemp.equals(et.getTemplateId())){
				d_list.append("," + et.getId());
				d_list.append("," + tempMgr.id2Name(et.getTemplateId())+"("+starttime+")");
			}
			templateIdTemp = et.getTemplateId();
		}
			
		String taskBuffer = d_list.toString();
		taskBuffer = taskBuffer.substring(1, taskBuffer.length());
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(taskBuffer);
		response.getWriter().flush();
		return null;
	}
	
	public static String getLastDayOfMonth(String sDate1) throws ParseException   { 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
        Date date = format.parse(sDate1);                    
        Calendar cDay1 = Calendar.getInstance();   
        cDay1.setTime(date);   
        final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);       
        return Integer.toString(lastDay);   
    }  
	/**
	 * 页面二级联动，已知模板分类，返回对应模板任务list,根据模版分类和模版激活时间查询出模版
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeTemplateTypeAndTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateTypeId = request.getParameter("templateTypeId");
		// 已激活的任务列表
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr tempMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");

		String year1 = request.getParameter("year1");
		String month1 = request.getParameter("month1");
		String year2 = request.getParameter("year2");
		String month2 = request.getParameter("month2");
		String queryType = request.getParameter("queryType");
		//组装时间参数
		String start = year1+"-"+month1+"-01"+" 00:00:00";
		String end = "";
		if(queryType.equals("2")){//考核结果查询页面
			end = year2+"-"+month2+"-28";
			end = year2+"-"+month2+"-"+getLastDayOfMonth(end)+" 23:59:59";
		}
		else if(queryType.equals("1")){//单一月份单一厂商页面，同一月份不同厂商
			end = year1+"-"+month1+"-28";
			end = year1+"-"+month1+"-"+getLastDayOfMonth(end)+" 23:59:59";
		}
		else if(queryType.equals("1.5")){//同一月份不同厂商
			end = year1+"-"+month2+"-28";
			end = year1+"-"+month2+"-"+getLastDayOfMonth(end)+" 23:59:59";
		}
		
		//2009-8-4 模版显示加角色控制，考核模版区（省）负责人能看到所有模版
		List list = new ArrayList();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PnrEvaRoleIdList pnrEvaroleIdList = (PnrEvaRoleIdList) getBean("pnrEvaRoleIdList");
		String isProvinceAdmin = "N";
		if(sessionForm.getUserid().equals("admin")){
			isProvinceAdmin = "Y";
		}
		else {
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == Integer.parseInt(pnrEvaroleIdList.getReportExecuteRoleId())){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		if(isProvinceAdmin.equals("Y")){
			list = taskMgr.listTaskOfProvinceAdminInTime(PnrEvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		else if(isProvinceAdmin.equals("N")){
			list = taskMgr.listTaskOfOrgInTime(sessionform.getDeptid(),PnrEvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTask et = (PnrEvaTask) list.get(i);
			PnrEvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
			String starttime = "";
			if(template.getStartTime()!=null&&!template.getStartTime().equals("")){
				starttime = template.getStartTime().substring(0,10);
			}
			if(isProvinceAdmin.equals("Y")){//考核执行、查询等显示模版名称的时候，根据task来显示的，一个所属部门对应一个task，这样当管理员登陆的时候会看到相同模版名称的记录，在后面加部门名称区分。
				String deptName = deptMgr.id2Name(et.getOrgId());
				d_list.append("," + et.getId());
				d_list.append("," + tempMgr.id2Name(et.getTemplateId())+"("+deptName+starttime+")");
			}
			else {
				d_list.append("," + et.getId());
				d_list.append("," + tempMgr.id2Name(et.getTemplateId())+"("+starttime+")");
			}

		}
		String taskBuffer = d_list.toString();
		taskBuffer = taskBuffer.substring(1, taskBuffer.length());
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(taskBuffer);
		response.getWriter().flush();
		return null;
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
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr pnrEvaTaskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr pnrEvaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
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
		
		PnrEvaTask pnrEvaTask = pnrEvaTaskMgr.getTaskById(taskId);
		String belongNode = pnrEvaTask.getNodeId() ;
		String pNodeId = pnrEvaTreeMgr.getTreeNode(belongNode).getNodeId();
		String areaId = pnrEvaTask.getOrgId();
		//在任务发布之前，判断report表中当月记录是否存在，如果存在，则不能重复发布当月任务
		StringBuffer whereStr = new StringBuffer();
		if(null != time || !"".equals(time))
			whereStr.append(" and time = '"+time+"'");
		if(null != timeType || !"".equals(timeType))
			whereStr.append(" and timeType = '"+timeType+"'");
		if(!(null == partner || "".equals(partner)))
			whereStr.append(" and partnerId = '"+partner+"'");
		if(null != belongNode ||!"".equals(belongNode))
			whereStr.append(" and belongNode = '"+belongNode+"'");
		if(null != areaId || !"".equals(areaId))
			whereStr.append(" and area = '"+areaId+"'");
		whereStr.append(" and (is_publish = '"+PnrEvaConstants.TASK_PUBLISHED+"'");
		whereStr.append(" or state = '"+PnrEvaConstants.AUDIT_WAIT+"')");
		whereStr.append(" order by createTime desc");
		List oldReportInfoList = evaReportInfoMgr.getReportInfoByCondition(whereStr.toString());
		
		if(oldReportInfoList.size()>0){
			request.setAttribute("message", "该模板任务本月已发布，请下个月再执行！");
			request.setAttribute("oldReportInfo", (PnrEvaReportInfo)oldReportInfoList.get(0));
			return mapping.findForward("tips");
		}

		int maxLevel = 0;
		int maxNodeIdSize = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		String nodeId = "";
		String taskType = "";
		float minSroce = 999;
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
					//得到汇总状态
					ekif.setState(PnrEvaConstants.AUDIT_PASS);
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
					}
					if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
					if (ekis.getAuditFlag() == null
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
							||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
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
					if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
					if (ekis.getAuditFlag() == null
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
							||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
						isPublishButton = "display:none;";
					}
				}
				int colspan = StaticMethod.nullObject2int(etd.getColspan());
				if(colspan>maxLevel){
					maxLevel = colspan;
				}
				int nodeIdSize = StaticMethod.nullObject2String(etd.getNodeId()).length();
				if(nodeIdSize>maxNodeIdSize){
					maxNodeIdSize = nodeIdSize;
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
			
		}

		// 找到当前taskId对应的模板name
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称
		
		//取出任务审核表里的审核信息
		IPnrEvaTaskAuditMgr taskAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		List taskAuditInfo = taskAuditMgr.getPnrEvaTaskAudit(taskId, time, timeType,partner);
		Iterator iter = taskAuditInfo.iterator();
		while(iter.hasNext()){
			PnrEvaTaskAudit taskAudit = (PnrEvaTaskAudit) iter.next();
			taskAudit.setAreaName(areaMgr.getAreaByAreaId(taskAudit.getAreaId()).getAreaname());
		}
		request.setAttribute("taskAuditInfo", taskAuditInfo);
		
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
			return mapping.findForward("templateDetailList");
		}
		int titleColspan = (maxNodeIdSize-pNodeId.length())/3;
		request.setAttribute("titleColspan", String.valueOf(titleColspan));
		return mapping.findForward("taskDetailList");
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
	public ActionForward taskDetailListForPartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr pnrEvaTaskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr pnrEvaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
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
		
		PnrEvaTask pnrEvaTask = pnrEvaTaskMgr.getTaskById(taskId);
		String belongNode = pnrEvaTask.getNodeId() ;
		String pNodeId = pnrEvaTreeMgr.getTreeNode(belongNode).getNodeId();
		String areaId = pnrEvaTask.getOrgId();
		//在任务发布之前，判断report表中当月记录是否存在，如果存在，则不能重复发布当月任务
		StringBuffer whereStr = new StringBuffer();
		if(null != time || !"".equals(time))
			whereStr.append(" and time = '"+time+"'");
		if(null != timeType || !"".equals(timeType))
			whereStr.append(" and timeType = '"+timeType+"'");
		if(!(null == partner || "".equals(partner)))
			whereStr.append(" and partnerId = '"+partner+"'");
		if(null != belongNode ||!"".equals(belongNode))
			whereStr.append(" and belongNode = '"+belongNode+"'");
		if(null != areaId || !"".equals(areaId))
			whereStr.append(" and area = '"+areaId+"'");
		whereStr.append(" and (is_publish = '"+PnrEvaConstants.TASK_PUBLISHED+"'");
		whereStr.append(" or state = '"+PnrEvaConstants.AUDIT_WAIT+"')");
			whereStr.append(" order by createTime desc");
		List oldReportInfoList = evaReportInfoMgr.getReportInfoByCondition(whereStr.toString());
		if(oldReportInfoList.size()>0){
			//isPublishButton = "display:none;";
			request.setAttribute("message", "该模板任务本月已发布，请下个月再执行！");
			request.setAttribute("oldReportInfo", (PnrEvaReportInfo)oldReportInfoList.get(0));
			return mapping.findForward("tips");
		}

		int maxLevel = 0;
		int maxNodeIdSize = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		String nodeId = "";
		String taskType = "";
		float minSroce = 999;
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
					List pnrEvaReportInfoList =  evaReportInfoMgr
					.getReportInfoByTimeAndPartner(etd.getTemplateId(), etd.getArea(), timeType, time, partner, PnrEvaConstants.TASK_PUBLISHED);
					PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
					if(pnrEvaReportInfoList.size()>0){
						pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
					}
					float totalScore = pnrEvaReportInfo.getTotalScore();
					ekif.setRemark(ekis.getRemark());
					ekif.setMaintenanceRatio(ekis.getMaintenanceRatio());
					//得到下级汇总的数据
					//设置float精度小数点后两位
			        BigDecimal bd = new BigDecimal(totalScore);
			        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			        totalScore = bd.floatValue();
					ekif.setRealScore(totalScore);
					//得到汇总状态
					ekif.setState(PnrEvaConstants.AUDIT_PASS);
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
					}
					if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
					if (ekis.getAuditFlag() == null
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
							||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
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
					if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
					if (ekis.getAuditFlag() == null
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
							||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
						isPublishButton = "display:none;";
					}
				}
				int colspan = StaticMethod.nullObject2int(etd.getColspan());
				if(colspan>maxLevel){
					maxLevel = colspan;
				}
				int nodeIdSize = StaticMethod.nullObject2String(etd.getNodeId()).length();
				if(nodeIdSize>maxNodeIdSize){
					maxNodeIdSize = nodeIdSize;
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
			
		}

		// 找到当前taskId对应的模板name
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称
		
		//取出任务审核表里的审核信息
		IPnrEvaTaskAuditMgr taskAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		List taskAuditInfo = taskAuditMgr.getPnrEvaTaskAudit(taskId, time, timeType,partner);
		Iterator iter = taskAuditInfo.iterator();
		while(iter.hasNext()){
			PnrEvaTaskAudit taskAudit = (PnrEvaTaskAudit) iter.next();
			taskAudit.setAreaName(areaMgr.getAreaByAreaId(taskAudit.getAreaId()).getAreaname());
		}
		request.setAttribute("taskAuditInfo", taskAuditInfo);
		
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
			return mapping.findForward("templateDetailList");
		}
		int titleColspan = (maxNodeIdSize-pNodeId.length())/3;
		request.setAttribute("titleColspan", String.valueOf(titleColspan));
		return mapping.findForward("taskDetailList");
	}

	/**
	 * 打开考核任务页面-省网络部和网络中心
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskDetailListForAllCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr pnrEvaTaskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr pnrEvaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		List tableList = new ArrayList();
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
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));	
		PnrEvaTask pnrEvaTask = null;
		if("".equals(templateId)){
			String tempTaskId = StaticMethod.nullObject2String(request.getParameter("taskId"));	
			pnrEvaTask = pnrEvaTaskMgr.getTask(tempTaskId);
			templateId = pnrEvaTask.getTemplateId();
		}

		String taskId = "";
		List taskList = pnrEvaTaskMgr.listTaskOfTpl(templateId);
		
		String belongNode = "";
		String pNodeId = "";
		String areaId = "";
		String nodeId = "";
		String taskType = "";
		Map minSorceMap = new HashMap();
		int maxLevel = 0;
		int maxNodeIdSize = 0;
		int maxListNo = 0;
		Map areaRowMap = new HashMap();
		for(int city=0;city<taskList.size();city++){
			pnrEvaTask = (PnrEvaTask)taskList.get(city);
			belongNode = pnrEvaTask.getNodeId() ;
			pNodeId = pnrEvaTreeMgr.getTreeNode(belongNode).getNodeId();
			areaId = pnrEvaTask.getOrgId();
			taskId = pnrEvaTask.getId();
			maxLevel = 0;
			maxNodeIdSize = 0;
			maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
			float minSroce = 999;
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
						List pnrEvaReportInfoList =  evaReportInfoMgr
						.getReportInfoByTimeAndPartner(etd.getTemplateId(), etd.getArea(), timeType, time, partner, PnrEvaConstants.TASK_PUBLISHED);
						PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
						if(pnrEvaReportInfoList.size()>0){
							pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
						}
						float totalScore = pnrEvaReportInfo.getTotalScore();
						ekif.setRemark(ekis.getRemark());
						ekif.setMaintenanceRatio(ekis.getMaintenanceRatio());
						//得到下级汇总的数据
						//设置float精度小数点后两位
				        BigDecimal bd = new BigDecimal(totalScore);
				        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				        totalScore = bd.floatValue();
						ekif.setRealScore(totalScore);
						//得到汇总状态
						ekif.setState(PnrEvaConstants.AUDIT_PASS);
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())) {
							ekif.setIsPublish(ekis.getIsPublish());
						} else {
							ekif.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);
						}
						if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
							isPublishButton = "display:none;";
						}
						if (ekis.getAuditFlag() == null
								||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
								||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
							ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
						} else {
							ekif.setAuditFlag(ekis.getAuditFlag());
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
						if (PnrEvaConstants.TASK_PUBLISHED.equals(ekis.getIsPublish())) {
							isPublishButton = "display:none;";
						}
						if (ekis.getAuditFlag() == null
								||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())
								||PnrEvaConstants.AUDIT_UNDO.equals(ekis.getAuditFlag())) {
							ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
						} else {
							ekif.setAuditFlag(ekis.getAuditFlag());
							isPublishButton = "display:none;";
						}
						areaRowMap.put(etd.getArea(), StaticMethod.nullObject2int(areaRowMap.get(etd.getArea()))+1);
					}
					int colspan = StaticMethod.nullObject2int(etd.getColspan());
					if(colspan>maxLevel){
						maxLevel = colspan;
					}
					int nodeIdSize = StaticMethod.nullObject2String(etd.getNodeId()).length();
					if(nodeIdSize>maxNodeIdSize){
						maxNodeIdSize = nodeIdSize;
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
				
			}
		}
		// 找到当前taskId对应的模板name
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称
		
		//取出任务审核表里的审核信息
		IPnrEvaTaskAuditMgr taskAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		List taskAuditInfo = taskAuditMgr.getPnrEvaTaskAudit(taskId, time, timeType,partner);
		Iterator iter = taskAuditInfo.iterator();
		while(iter.hasNext()){
			PnrEvaTaskAudit taskAudit = (PnrEvaTaskAudit) iter.next();
			taskAudit.setAreaName(areaMgr.getAreaByAreaId(taskAudit.getAreaId()).getAreaname());
		}
		request.setAttribute("taskAuditInfo", taskAuditInfo);
		
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
		request.setAttribute("areaRowMap", areaRowMap);//每个地域占的行数
		if(taskType.equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
			return mapping.findForward("templateDetailListForDept");
		}
		int titleColspan = (maxNodeIdSize-pNodeId.length())/3;
		request.setAttribute("titleColspan", String.valueOf(titleColspan));
		return mapping.findForward("taskDetailListForDept");
	}
		
		
	// 保存任务详细信息 王思轩 09-1-21
	public ActionForward saveTaskDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (xsave(mapping, form, request, response,
				PnrEvaConstants.TASK_NOT_PUBLISHED))
			return mapping.findForward("success");
			
		else
			return mapping.findForward("fail");
	}

	// 上报任务详细信息 王思轩 09-1-21
	public ActionForward commitTaskDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (xsave(mapping, form, request, response, PnrEvaConstants.TASK_PUBLISHED))
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
			String saveType) throws Exception {
		try {
			
			//得到登陆人的考核操作角色,用以确定地域信息
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userId = sessionForm.getUserid();
			String deptId = sessionForm.getDeptid();
			Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
			String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
			PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)getBean("pnrEvaRoleIdList");
			String rootAreaId = roleIdList.getRootAreaId();

			String[] taskId = request.getParameterValues("taskId");
			String partner = StaticMethod.null2String(request
					.getParameter("partner"));
			String timeType = StaticMethod.null2String(request
					.getParameter("timeType"));
			String time = StaticMethod
					.null2String(request.getParameter("time"));
			String type = StaticMethod
				.null2String(request.getParameter("type"));
			IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
			IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
			IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
			IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
			IPnrEvaTaskMgr pnrEvaTaskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
			IPnrEvaTemplateMgr pnrEvaTemplateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			for(int tasks=0;tasks<taskId.length;tasks++){
			
			//根据taskId得到
			PnrEvaTask pnrEvaTask = pnrEvaTaskMgr.getTaskById(taskId[tasks]);
			PnrEvaTemplate pnrEvaTemplate = pnrEvaTemplateMgr.getTemplate(pnrEvaTask.getTemplateId());
			
			//根据地域信息查出该地域以及上级所有地域信息集合
			String rootArea = roleIdList.getRootAreaId();
			String areaStr = rootArea;
			//根据地域信息查出该地域以及上级所有地域信息集合
			int areaNum = (areaId.length()-rootArea.length())/2;
			for(int i=0;i<areaNum;i++){
				areaStr = areaStr + ",'"+areaId.substring(0,rootArea.length()+areaNum*2)+"'";
			}
			
			PnrEvaTree templateNode = treeMgr.getTreeNode(pnrEvaTemplate.getBelongNode());
			//得到某模板下所有个性权重信息
			Map areaWeight = treeMgr.listWeightByPNodeIdAndArea(templateNode.getNodeId(), areaStr,PnrEvaConstants.ALL_CHILD_NODE);
			IPnrEvaTaskAuditMgr auditAuditMgr = (IPnrEvaTaskAuditMgr) getBean("iPnrTaskAuditMgr");
			int maxLevel = 0;
			int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId[tasks]);
			// 指标实际总分
			float totalScore = 0;
			// 指标权重总分
			float totalWeight = 0;
			//如果执行类型是指标
			if(type.equals(PnrEvaConstants.NODETYPE_KPI)){
			for (int i = 1; i <= maxListNo; i++) {
				List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId[tasks],
						String.valueOf(i));
				for (int j = 0; j < rowList.size(); j++) {
					PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
					if (PnrEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						String nodeId = etd.getNodeId();
						String area = etd.getArea();
						float realScore = Float.parseFloat(StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_sc")));
						String reduceReason = StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_rs"));
						String remark = StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_rm"));
						//对于多级子指标节点向上将所有父指标权重累乘

						/*指标按多级权重累乘统计时用下面代码
						//模板下多级指标个性权重累乘植
						float lastWeight = 1;
						String parentNodeId = nodeId;
						PnrEvaTree pnrEvaTree = null;
						float selfWeight = 0;
						while(!(templateNode.getNodeId()).equals(parentNodeId)){
							pnrEvaTree =  treeMgr.getTreeNodeByNodeId(parentNodeId);
							//置换为个性权重
							selfWeight = pnrEvaTree.getWeight();
							if(areaWeight.get(parentNodeId)!=null){
								selfWeight = Float.parseFloat(StaticMethod.nullObject2String(areaWeight.get(parentNodeId)));
							}
							parentNodeId = pnrEvaTree.getParentNodeId();
							lastWeight = lastWeight*selfWeight/100;
							if("1".equals(parentNodeId)){
								break;
							}
						}
						
						totalScore = totalScore+ realScore*lastWeight;
						totalWeight = totalWeight + etd.getWeight();
						*/
						//福建个性要求,多级指标不设置权重,而是采取分数拆分（扣分制）
						totalScore = totalScore+ realScore;
						PnrEvaTree pnrEvaTree =  treeMgr.getTreeNodeByNodeId(nodeId);
						totalWeight = totalWeight + etd.getWeight();
						
						
						
						PnrEvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, partner);
						if (ekis.getId() == null || "".equals(ekis.getId())) {
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							ekis.setCreateTime(format.format(new Date()));
						}
						ekis.setPartnerId(partner);
						ekis.setPartnerName(partner);
						ekis.setRealScore(realScore);
						ekis.setReduceReason(reduceReason);
						ekis.setRemark(remark);
						ekis.setTaskDetailId(etd.getId());
						ekis.setTime(time);
						ekis.setTimeType(timeType);
						ekis.setAssessUserId(userId);
						ekis.setAssessDeptId(deptId);
						if(PnrEvaConstants.TASK_NOT_PUBLISHED.equals(saveType)){//草稿
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
							ekis.setIsPublish(saveType);//
						}
						//如果是省公司发布任务，直接为审核“通过”状态
						else if((areaId.length()-rootAreaId.length())/2==0
								&&deptId.indexOf(roleIdList.getNdDept())!=0 && deptId.indexOf(roleIdList.getNmcDept())!=0){
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_PASS);
							ekis.setIsPublish(saveType);//省公司，省网络部，省网络中心的直接发布
						}else{
							ekis.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);//非审核的通过后再发布
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
						}
						evaKpiInstanceMgr.saveKpiInstance(ekis);
					}
				}
				if (rowList.size() > maxLevel) {
					maxLevel = rowList.size();
				}
			}
			// 如果点击发布，将总分信息存入ReportInfo表中,如果记录表中有该记录了,则对现有记录进行修改(主要用于审核驳回的汇总记录)
			if (saveType.equals(PnrEvaConstants.TASK_PUBLISHED)) {
				List pnrEvaReportInfoList =  evaReportInfoMgr
				.getReportInfoByTimeAndPartner(pnrEvaTask.getTemplateId(),areaId, timeType, time, partner,PnrEvaConstants.TASK_NOT_PUBLISHED);
				PnrEvaReportInfo eri = new PnrEvaReportInfo();
				if(pnrEvaReportInfoList.size()>0){
					eri = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
				}
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				eri.setCreateTime(format.format(new Date()));
				if((areaId.length()-rootAreaId.length())/2==0
						||deptId.indexOf(roleIdList.getNdDept())==0 || deptId.indexOf(roleIdList.getNmcDept())==0){
					eri.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司，省网络部，省网络中心的直接发布
					eri.setState(PnrEvaConstants.AUDIT_PASS);
				}else{
					eri.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);//非审核的通过后再发布
					eri.setState(PnrEvaConstants.AUDIT_WAIT);
				}
				totalScore = PnrEvaConstants.DEFAULT_MAX_WT-totalScore;//扣分制，叶子考核表满分100
				eri.setPartnerId(partner);
				eri.setPartnerName(partner);
				eri.setTaskId(taskId[tasks]);
				eri.setTime(time);
				eri.setTimeType(timeType);
				eri.setTotalScore(totalScore);
				eri.setTotalWeight(Float.valueOf(totalWeight).toString());
				eri.setArea(pnrEvaTask.getOrgId());
				eri.setAssessUserId(userId);
				eri.setAssessDeptId(deptId);
				eri.setBelongNode(pnrEvaTask.getNodeId());
				evaReportInfoMgr.savePnrEvaReportInfo(eri);
			}
		} else{ //如果执行类型是模板汇总
			String tempNodeId ="";
			for (int i = 1; i <= maxListNo; i++) {
				List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId[tasks],
						String.valueOf(i));
				for (int j = 0; j < rowList.size(); j++) {
					PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
					if (PnrEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						String nodeId = etd.getNodeId();
						String area = etd.getArea();
						float realScore = Float.parseFloat(StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_sc")));
						float maintenanceRatio = Float.parseFloat(StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_mr")));
						String remark = StaticMethod.null2String(request
								.getParameter(nodeId +"_"+area+ "_rm"));
							// 加入总分
							totalScore += realScore*(maintenanceRatio/100)*etd.getWeight()/100;
							//当不同节点时权重叠加
						if(!tempNodeId.equals(nodeId)){
							totalWeight = totalWeight + etd.getWeight();
							tempNodeId = nodeId;
						}
						
						PnrEvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, partner);
						if (ekis.getId() == null || "".equals(ekis.getId())) {
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							ekis.setCreateTime(format.format(new Date()));
						}
						ekis.setPartnerId(partner);
						ekis.setPartnerName(partner);
						ekis.setRealScore(realScore);
						ekis.setMaintenanceRatio(maintenanceRatio);
						ekis.setRemark(remark);
						ekis.setTaskDetailId(etd.getId());
						ekis.setTime(time);
						ekis.setTimeType(timeType);
						ekis.setAssessUserId(userId);
						ekis.setAssessDeptId(deptId);
						if(PnrEvaConstants.TASK_NOT_PUBLISHED.equals(saveType)){//草稿
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
							ekis.setIsPublish(saveType);//
						}
						//如果是省公司发布任务，直接为审核“通过”状态
						else if((areaId.length()-rootAreaId.length())/2==0
								|| deptId.indexOf(roleIdList.getNdDept())==0 || deptId.indexOf(roleIdList.getNmcDept())==0){
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_PASS);
							ekis.setIsPublish(saveType);//省公司，省网络部，省网络中心的直接发布
						}else{
							ekis.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);//非审核的通过后再发布
							ekis.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
						}
						evaKpiInstanceMgr.saveKpiInstance(ekis);
					}
				}
				if (rowList.size() > maxLevel) {
					maxLevel = rowList.size();
				}
			}
			
			
			
			// 如果发布，将总分信息存入ReportInfo表中
			if (saveType.equals(PnrEvaConstants.TASK_PUBLISHED)) {
				List pnrEvaReportInfoList =  evaReportInfoMgr
				.getReportInfoByTimeAndPartner(pnrEvaTask.getTemplateId(),areaId, timeType, time, partner,PnrEvaConstants.TASK_NOT_PUBLISHED);
				PnrEvaReportInfo eri = new PnrEvaReportInfo();
				if(pnrEvaReportInfoList.size()>0){
					eri = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
				}
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				eri.setCreateTime(format.format(new Date()));
				if((areaId.length()-rootAreaId.length())/2==0
						|| deptId.indexOf(roleIdList.getNdDept())==0 || deptId.indexOf(roleIdList.getNmcDept())==0){
					eri.setIsPublish(PnrEvaConstants.TASK_PUBLISHED);//省公司,省网络部，省网管中心的直接发布
					eri.setState(PnrEvaConstants.AUDIT_PASS);
				}else{
					eri.setIsPublish(PnrEvaConstants.TASK_NOT_PUBLISHED);//非审核的通过后再发布
					eri.setState(PnrEvaConstants.AUDIT_WAIT);//非审核的通过后再发布
				}
				eri.setPartnerId(partner);
				eri.setPartnerName(partner);
				eri.setTaskId(taskId[tasks]);
				eri.setTime(time);
				eri.setTimeType(timeType);
				eri.setTotalScore(totalScore);
				eri.setTotalWeight(Float.valueOf(totalWeight).toString());
				eri.setArea(pnrEvaTask.getOrgId());
				eri.setBelongNode(pnrEvaTask.getNodeId());
				eri.setAssessUserId(userId);
				eri.setAssessDeptId(deptId);
				evaReportInfoMgr.savePnrEvaReportInfo(eri);
			}
		}
			
			
			//如果发布，将任务信息存入任务审核表taskAudit中
			if(saveType.equals(PnrEvaConstants.TASK_PUBLISHED) && !rootAreaId.equals(areaId)
					&&deptId.indexOf(roleIdList.getNdDept())!=0 && deptId.indexOf(roleIdList.getNmcDept())!=0){
				//指定任务审核人

				Map map = PnrEvaStaticMethod.getSubroleByAreaId(areaId, PnrEvaConstants.OPERATE_REPORT_AUDIT);
				//subRoleId指定审核人
				String subRoleId = StaticMethod.nullObject2String(map.get("subRoleId"));
				//通过taskId取得模板Id
				PnrEvaTaskAudit evaTaskAudit = new PnrEvaTaskAudit();
				//根据当前提交发布用户的地域Id判断其公司所在等级（县公司，地市公司，省公司），提交审核结果状态
				evaTaskAudit.setTaskId(taskId[tasks]);
				evaTaskAudit.setAuditOrg(subRoleId);
				//指定任务审核者类型
				evaTaskAudit.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
				evaTaskAudit.setAreaId(areaId);
				//设置任务初始审核状态
				evaTaskAudit.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
				//设置送审时间为系统当前时间
				evaTaskAudit.setAuditCreate(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
				//设置考核时间
				evaTaskAudit.setAuditTime(time);
				evaTaskAudit.setAuditCycle(timeType);
				evaTaskAudit.setTemplateId(pnrEvaTask.getTemplateId());
				evaTaskAudit.setPartner(partner);
				evaTaskAudit.setTotalScore(totalScore);
				auditAuditMgr.savePnrEvaTaskAudit(evaTaskAudit);
			}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public ActionForward xquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");

		String searchType = StaticMethod.null2String(request
				.getParameter("searchType"));//0表示草稿，1表示已发布
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String year1 = StaticMethod.null2String(request.getParameter("year1"));
		String month1 = StaticMethod
				.null2String(request.getParameter("month1"));
		String year2 = StaticMethod.null2String(request.getParameter("year2"));
		String month2 = StaticMethod
				.null2String(request.getParameter("month2"));
		String timeType = "月度";// 后续开发
		String startTime = year1 + month1;
		String endTime = year2 + month2;
		if (searchType == null || "".equals(searchType)) {
			searchType = PnrEvaConstants.TASK_PUBLISHED;
		}
		List taskDetailList = new ArrayList();
		List taskList = evaKpiInstanceMgr.getKpiInstanceListByTimeAndPartner("",
				partner, timeType, startTime, endTime,
				searchType);
		for (int j = 0; j < taskList.size(); j++) {
			PnrEvaKpiInstance edi = (PnrEvaKpiInstance) taskList.get(j);
			taskDetailList.add(edi);
		}

	
		List taskDetailListShow = new ArrayList();
		// 找到当前taskId对应的模板name
		String tempTaskId = "";
		String tempPartner = "";
		for (int i = 0; i < taskDetailList.size(); i++) {
			PnrEvaKpiInstance eki = (PnrEvaKpiInstance) taskDetailList.get(i);
			PnrEvaKpiInstanceForm ekif = (PnrEvaKpiInstanceForm)convert(eki);
			PnrEvaTaskDetail etd = taskDetailMgr.getTaskDetailById(eki.getTaskDetailId());
			PnrEvaTask et = taskMgr.getTaskById(etd.getTaskId());
			System.out.println("-----etd.getTaskId() = "+etd.getTaskId()+"  ------et.getTemplateId() = "+et.getTemplateId());
			if(et.getId()!=null){
				String taskName = templateMgr.id2Name(et.getTemplateId());
				ekif.setTaskName(taskName);

				//对当前任务进行判断，如果orgId与deptId一致则要，否者不要
		        if(areaId.equals(et.getOrgId())){
		        	if(!tempTaskId.equals(et.getId())){
		        		taskDetailListShow.add(ekif);
		        	}else if(tempPartner.equals(et.getOrgId())){
		        		taskDetailListShow.add(ekif);
		        	}
					}
		        tempTaskId = et.getId();
		        tempPartner = et.getOrgId();
			}
			ekif.setTaskId(etd.getTaskId());
		}

		if (partner == null || "".equals(partner)) {
			partner = "所有合作伙伴";
		}
		if (startTime == null || "".equals(startTime)) {
			startTime = "最早时间";
		}
		if (endTime == null || "".equals(endTime)) {
			endTime = "最晚时间";
		}

		// 找到当前taskId对应的模板name
		String taskName = "所有任务";
		if (taskId != null && !"".equals(taskId)) {
			PnrEvaTask et = taskMgr.getTaskById(taskId);
			taskName = templateMgr.id2Name(et.getTemplateId());
		}
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("taskDetailList", taskDetailListShow); // 详细列表数据
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("startTime", startTime); // 时间1
		request.setAttribute("endTime", endTime); // 时间2
		return mapping.findForward("queryList");
	}
	/**
	 * 打开合作伙伴选择页面(叶子模板才关联厂商,厂商挂在叶子模板上)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */


	public ActionForward choosePartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
//		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));; 
		
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String queryType = StaticMethod.nullObject2String(request.getParameter("queryType"));
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.nullObject2String(request.getParameter("time"));
		}
		String templateTypeId = StaticMethod.nullObject2String(request.getParameter("templateTypeId"));
		PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskId);
		String templateId = pnrEvaTask.getTemplateId();
		PnrEvaTemplate pnrEvaTemplate = templateMgr.getTemplate(templateId);
		PnrEvaTree pnrEvaTree = treeMgr.getTreeNode(pnrEvaTemplate.getBelongNode());
		List facturyList = facturyMgr.getAllKpiFacturyByNodeId(pnrEvaTree.getNodeId());
		
		request.setAttribute("taskId", taskId);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("time", time);
		request.setAttribute("queryType", queryType);
		request.setAttribute("templateTypeId", templateTypeId);
		request.setAttribute("templateId", templateId);
		request.setAttribute("areaId", areaId);
		request.setAttribute("pnrEvaTemplate", pnrEvaTemplate);
		request.setAttribute("facturyList", facturyList);
		
		return mapping.findForward("choosePartner");
	}
	/**
	 * 打开合作伙伴选择页面(所有模板都关联厂商,厂商挂在模板分类上)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */


	public ActionForward choosePartnerForAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
//		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));; 
		
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String queryType = StaticMethod.nullObject2String(request.getParameter("queryType"));
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.nullObject2String(request.getParameter("time"));
		}
		String templateTypeId = StaticMethod.nullObject2String(request.getParameter("templateTypeId"));
		PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskId);
		String templateId = pnrEvaTask.getTemplateId();
		List facturyList = facturyMgr.getAllKpiFacturyByNodeId(templateTypeId);
		
		request.setAttribute("taskId", taskId);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("time", time);
		request.setAttribute("queryType", queryType);
		request.setAttribute("templateTypeId", templateTypeId);
		request.setAttribute("templateId", templateId);
		request.setAttribute("areaId", areaId);
		request.setAttribute("facturyList", facturyList);

		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)getBean("pnrEvaRoleIdList");
		String deptId = sessionform.getDeptid();
		if(deptId.indexOf(roleIdList.getNdDept())==0||deptId.indexOf(roleIdList.getNmcDept())==0){
			return mapping.findForward("choosePartnerForDept");
		}
		return mapping.findForward("choosePartnerForAll");
	}
	/**
	 * 草稿列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward draftList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		PnrEvaRoleIdList pnrEvaroleIdList = (PnrEvaRoleIdList) getBean("pnrEvaRoleIdList");
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_EXECUTE);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");

//		List taskDetailList = new ArrayList();
		List taskDetailList = evaKpiInstanceMgr.getDraftKpiInstanceList(areaId);
		List taskDetailListShow = new ArrayList();
		// 找到当前taskId对应的模板name
		String tempTaskId = "";
		String time = "";
		String tempArea = "";
		String tempPartner = "";
		String executeOrg = "0";
		for (int i = 0; i < taskDetailList.size(); i++) {
			PnrEvaKpiInstance eki = (PnrEvaKpiInstance) taskDetailList.get(i);
			PnrEvaKpiInstanceForm ekif = (PnrEvaKpiInstanceForm)convert(eki);
			PnrEvaTaskDetail etd = taskDetailMgr.getTaskDetailById(eki.getTaskDetailId());
			PnrEvaTask et = taskMgr.getTaskById(etd.getTaskId());
			System.out.println("-----etd.getTaskId() = "+etd.getTaskId()+"  ------et.getTemplateId() = "+et.getTemplateId());
			
			if(et.getId()!=null){
				String taskName = templateMgr.id2Name(et.getTemplateId());
				ekif.setTaskName(taskName);
				if(!tempTaskId.equals(et.getId())||!tempArea.equals(et.getOrgId())||!tempPartner.equals(StaticMethod.nullObject2String(eki.getPartnerId()))||!time.equals(eki.getTime())){
				if(et.getExecuteOrg().equals(PnrEvaConstants.EXECUTE_ORG_EC)&&!"".equals(areaId)){
					taskDetailListShow.add(ekif);
				}else if((et.getExecuteOrg().equals(PnrEvaConstants.EXECUTE_ORG_ND)&deptId.indexOf(pnrEvaroleIdList.getNdDept())==0)
						||(et.getExecuteOrg().equals(PnrEvaConstants.EXECUTE_ORG_NMC)&deptId.indexOf(pnrEvaroleIdList.getNmcDept())==0)){
					if(!(tempPartner.equals(StaticMethod.nullObject2String(eki.getPartnerId()))&&time.equals(eki.getTime()))){
						taskDetailListShow.add(ekif);
						executeOrg = et.getExecuteOrg();
					}
					
					
				}
					
					
				}
		        tempTaskId = et.getId();
		        tempArea = et.getOrgId();
		        tempPartner = StaticMethod.nullObject2String(eki.getPartnerId());
		        time = eki.getTime();
			}
			ekif.setTaskId(etd.getTaskId());
		}


		// 找到当前taskId对应的模板name
		String taskName = "所有任务";
		request.setAttribute("executeOrg", executeOrg); 
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("taskDetailList", taskDetailListShow); // 详细列表数据
		return mapping.findForward("queryList");
	}
}
