package com.boco.eoms.eva.webapp.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceForAuditMgr;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceMgr;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskAuditMgr;
import com.boco.eoms.eva.mgr.IEvaTaskDetailMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaKpiInstance;
import com.boco.eoms.eva.model.EvaKpiInstanceForAudit;
import com.boco.eoms.eva.model.EvaReportInfo;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTaskAudit;
import com.boco.eoms.eva.model.EvaTaskDetail;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.util.EvaStaticMethod;
import com.boco.eoms.eva.util.RoleIdList;
import com.boco.eoms.eva.webapp.form.EvaKpiInstanceForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;

public final class EvaTaskAction extends BaseAction {

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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EvaTree et = (EvaTree) list.get(i);
			templateType.add(et);
		}
		// 找到对应的厂商
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
		String userid = sessionform.getUserid();
		PartnerUserAndArea partnerUserAndArea = partnerUserAndAreaMgr.getObjectByUserId(userid);
		String cityId = "(";
		if(partnerUserAndArea!=null&&partnerUserAndArea.getCityId()!=null&&!partnerUserAndArea.getCityId().equals("")){
			String[] city =  partnerUserAndArea.getCityId().split(",");
			for(int i = 0;i<city.length;i++){
				if(i>0){
					cityId += ",";
				}
				cityId += "'"+city[i]+"'";
			}
		}else{
			cityId += "''";
		}
		cityId += ")";
		  	
		String sql = "from AreaDeptTree tree where 1=1 and tree.areaId in "
				+ cityId + " and tree.nodeType = 'factory' order by tree.nodeId";
		if(userid.equals("admin")){
			sql = "from AreaDeptTree tree where 1=1 and  tree.nodeType = 'factory' order by tree.nodeId";
		}
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		List factoryList = new ArrayList();
		List faclist = new ArrayList();
		factoryList = areaDeptTreeMgr.getInfoByCondition(sql);
		AreaDeptTree factree = null;
		String showNodeName = "";
		for(int i=0;i<factoryList.size();i++){
			Map hashMap = new HashMap();
			factree = (AreaDeptTree)factoryList.get(i);
			showNodeName = factree.getNodeName()+"("+mgr.id2Name(factree.getAreaId(),"tawSystemAreaDao")+")";
			hashMap.put("partnerId", factree.getNodeId());
			hashMap.put("partnerName", showNodeName);
			faclist.add(hashMap);
		}
		request.setAttribute("factoryList", faclist);

		request.setAttribute("templateType", templateType);
		return mapping.findForward("taskView");
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
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr tempMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");

		//2009-8-4 模版显示加角色控制，考核模版区（省）负责人能看到所有模版
		List list = new ArrayList();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		RoleIdList roleIdList = (RoleIdList) getBean("evaRoleIdList");
		String isProvinceAdmin = "N";
		if(sessionForm.getUserid().equals("admin")){
			isProvinceAdmin = "Y";
		}
		else {
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == roleIdList.getProvinceAdminRoleId().intValue()){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		if(isProvinceAdmin.equals("Y")){
			list = taskMgr.listTaskOfProvinceAdmin(EvaConstants.TEMPLATE_ACTIVATED, templateTypeId);
		}
		else if(isProvinceAdmin.equals("N")){
			list = taskMgr.listTaskOfOrg(sessionform.getDeptid(),EvaConstants.TEMPLATE_ACTIVATED, templateTypeId);
		}
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		for (int i = 0; i < list.size(); i++) {
			EvaTask et = (EvaTask) list.get(i);
			EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
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
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr tempMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");

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
		RoleIdList roleIdList = (RoleIdList) getBean("evaRoleIdList");
		String isProvinceAdmin = "N";
		if(sessionForm.getUserid().equals("admin")){
			isProvinceAdmin = "Y";
		}
		else {
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == roleIdList.getProvinceAdminRoleId().intValue()){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		if(isProvinceAdmin.equals("Y")){
			list = taskMgr.listTaskOfProvinceAdminInTime(EvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		else if(isProvinceAdmin.equals("N")){
			list = taskMgr.listTaskOfOrgInTime(sessionform.getDeptid(),EvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		for (int i = 0; i < list.size(); i++) {
			EvaTask et = (EvaTask) list.get(i);
			EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
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
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String partnerId = StaticMethod.null2String(request
				.getParameter("partnerId"));
		if(partner==null ||"".equals(partner)){
			partner = partnerId.trim();
		}
		
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));

		String queryType = StaticMethod.null2String(request
				.getParameter("queryType"));
		if(queryType.equals("0")){
			queryType = "query" ;
		}else if(queryType.equals("-1")){
			queryType = "agreement" ;
		}else {
			queryType = "run" ;
		}
//		if(queryType == null || "".equals(queryType)){
//			queryType = "run";
//		}
		String timeType = "月度";// 后续开发
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.null2String(request.getParameter("time"));
		}
		String isPublishButton = "";
		String isPublish= "";
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
					EvaKpiInstance ekis = evaKpiInstanceMgr
							.getKpiInstanceByTimeAndPartner(etd.getId(),
									timeType, time, partner);
					ekif.setRealScore(ekis.getRealScore());
					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(EvaConstants.TASK_NOT_PUBLISHED);
					}
					if ("1".equals(ekis.getIsPublish())||"2".equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
						isPublish = ekis.getIsPublish();
					}
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
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称

		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("templateId", et.getTemplateId()); // 模板id
		request.setAttribute("isPublish", isPublish); // 发布标志位
		
		return mapping.findForward("taskDetailList");
	}

	/**
	 * 根据统一考核页面打开考核任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskDetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaKpiInstanceForAuditMgr evaKpiInstanceForAuditMgr = (IEvaKpiInstanceForAuditMgr) getBean("IevaKpiInstanceForAuditMgr");
		IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
//		IPnrWorkplanWorkMgr pnrWorkplanWorkMgr = (IPnrWorkplanWorkMgr) getBean("pnrWorkplanWorkMgr");
		List tableList = new ArrayList();
		java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
		String reportId = StaticMethod.null2String(request.getParameter("reportId"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
//		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String time =  StaticMethod.null2String(request.getParameter("time"));
		String queryType = StaticMethod.null2String(request.getParameter("queryType"));
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		String evaStartTime = ""; //该周期考核开始时间
		String evaEndTime = ""; //该周期考核结束时间
		String evaUser = "";//被考核人
		List workRecord = null;
		String localTime = StaticMethod.getLocalString();
		EvaTask et = taskMgr.getTaskById(taskId);
		EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
		List allCycleList = DateTimeUtil.getAllStrByTimesForEva(template.getCycle(),template.getStartTime(),localTime);
		if(Integer.parseInt(time)>allCycleList.size()){
				request.setAttribute("err", "beforeEvaTime");
				mapping.findForward("taskDetailList");
		}
		String taskName = template.getTemplateName();
		if(queryType.equals("0")){
			queryType = "query" ;
		}else if(queryType.equals("-1")){
			queryType = "agreement" ;
		}else {
			queryType = "run" ;
		}
		//判断该周期是否已打分
		String isPublishButton = "";
		String isPublish = "";
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		EvaReportInfo eri = null;
		List list = reportInfoMgr.getReportInfoByTaskId(taskId);
		String reportPublish ="";
		for(int i=0;i<list.size();i++){
			eri = (EvaReportInfo)list.get(i);
			if(time.equals(eri.getTime())){
				reportPublish = eri.getIsPublish();
			}
		}
		if ("1".equals(reportPublish)||"2".equals(reportPublish)) {
			isPublishButton = "display:none;";
			isPublish = eri.getIsPublish();
			request.setAttribute("err", "evaSaved");
			mapping.findForward("taskDetailList");
		}
		
		
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
									"", time, template.getPartnerDept());
					ekif.setRealScore(ekis.getRealScore());
					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(EvaConstants.TASK_NOT_PUBLISHED);
					}
//					if ("1".equals(ekis.getIsPublish())||"2".equals(ekis.getIsPublish())) {
//						isPublishButton = "display:none;";
//						isPublish = ekis.getIsPublish();
//					}     
				}

				// 算法加入
				EvaKpi ek = evaKpiMgr.getKpi(ekif.getKpiId());		
				//指标作用周期，起始序号-结束序号
				String scopeStr = DateTimeUtil.getKpiScope(ek.getEvaStartTime(),ek.getEvaEndTime(),ek.getCycle(),template.getStartTime(),template.getCycle(),time);
				List evaSavedList = evaKpiInstanceMgr.getKpiInstanceByScope(etd.getId(),scopeStr);
				if(evaSavedList.size()>0){
					scopeStr = "0-0";
				}else{
					evaSavedList = evaKpiInstanceForAuditMgr.getKpiInstanceForAuditByScope(etd.getId(),scopeStr);
					if(evaSavedList.size()>0){
						scopeStr = "0-0";
					}
				}
				ekif.setScope(scopeStr);  
//				此处更改算法的显示
				String algorithm = ek.getEvaContentByType();
				
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				//	根据不同的数据来源或自动得到结果
				ekif.setEvaSource(ek.getEvaSource());
				String[] cycleNum = scopeStr.split("-");
				
				evaStartTime = DateTimeUtil.getTimesByCycle(ek.getCycle(),ek.getEvaStartTime(),Integer.parseInt(cycleNum[0])-1);
				evaEndTime = DateTimeUtil.getTimesByCycle(ek.getCycle(),ek.getEvaStartTime(),Integer.parseInt(cycleNum[1]));
				//增加默认值，加入工作任务执行次数算法
				String agreeWorkId = ek.getAgreementWorkId();
				String recordStr = "";
				String[] NumStr =new String[2];
				int allworkTaskNum = 0;
//				PnrWorkplanWork workplanWork = null;
				String reasonStr = "";
				float realScore = 0;
				float evaFloat[] = {90,10}; //设置执行考核分占总考核分的比例
				if(!"0-0".equals(scopeStr)&&"subjective".equals(ek.getEvaSource())&&!agreeWorkId.equals("")){
//					recordStr = pnrWorkplanWorkMgr.getRecordStrByTime(evaStartTime+" 00:00:00", evaEndTime+" 00:00:00", agreeWorkId);
					NumStr = recordStr.split("-");
					reasonStr="应执行"+NumStr[1]+"次,共执行"+NumStr[0]+"次";
					if(ekif.getReduceReason()==null||ekif.getReduceReason().equals("")){
						ekif.setReduceReason(reasonStr);
					}
					//根据需要改变默认值算法
					if(ekif.getRealScore()==null||ekif.getRealScore().equals("")){
//						realScore = ekif.getWeight()*(evaFloat[0]/100)+ekif.getWeight()*(evaFloat[1]/100)*(Float.parseFloat(NumStr[0])/Float.parseFloat(NumStr[1]));
//						ekif.setRealScore(String.valueOf(df.format(realScore)));
					}
					if(ekif.getRemark()==null||ekif.getRemark().equals("")){
//						ekif.setRemark("其中"+evaFloat[1]+"%计入执行考评");
					}
				}
				//增加自动采集的数据默认值
				if(!"0-0".equals(scopeStr)&&!"subjective".equals(ek.getEvaSource())){
					evaUser = ek.getToOrgUser();
					//调接口得到考核分数
					//得到不同来源在字典配置文件里配置的不同的接口标识
					String systemid = StaticMethod.nullObject2String(DictMgrLocator.getDictService().itemId2description("dict-eva#evaSource",ek.getEvaSource()));
//					任务执行人
//					evaUser = "lifan,chengjianwei";//测试用数据
					String recvXml = "";
					String returnRate = "";
					try{
					recvXml = EvaStaticMethod.telService(systemid, evaUser, ek.getEvaStartTime(),ek.getEvaEndTime());
					System.out.println("recv:\n" + recvXml);
					returnRate = EvaStaticMethod.getDataFromXml(recvXml);
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//得到返回的值，加上算法后生成得分
//					得到算法分类： 0-直接得分 返回值直接乘kpi权重得到最终得分；1-界线得分，2-范围得分 查看返回值所在区间，得到区间应得分数乘kpi权重得到最终得分
				if(ekif.getRealScore()==null||ekif.getRealScore().equals("")){
//					Float retValue = Float.parseFloat("此处为接口返回值");
					Float retValue = Float.parseFloat(returnRate);//演示用，目前假设及时率为80%
					String algorithmType = ek.getAlgorithmType();
					String algorithmValue = ek.getAlgorithm();
					if("0".equals(algorithmType)){
						realScore = retValue*ekif.getWeight();
						realScore = (float)(Math.round(realScore))/100;
					} else {
						String[] scopeValue = algorithmValue.split("-");
						String[] scope = scopeValue[0].split(",");
						String[] value = scopeValue[1].split(",");
						for(int k = 0 ; scope.length>k ; k++){
							if( retValue<=Float.parseFloat(scope[k]) && retValue>Float.parseFloat(scope[k+1])){
								realScore = Float.parseFloat(value[k])*ekif.getWeight();
								realScore = (float)(Math.round(realScore))/100;
							}
						}
					}
					ekif.setRealScore(String.valueOf(realScore));
					reasonStr="处理及时率为"+String.valueOf(retValue)+"%";
					ekif.setReduceReason(reasonStr);
					
					}
				}
				rowListShow.add(ekif);
			}
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}

		// 找到当前taskId对应的模板name

		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", template.getPartnerDept()); // 合作伙伴信息
		request.setAttribute("timeType", template.getCycle()); // 时间类型
		request.setAttribute("timeStr", "第"+time+"次"); // 时间
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("templateId", et.getTemplateId()); // 模板id
		request.setAttribute("reportId", reportId); // 模板id
		request.setAttribute("isPublish", isPublish); // 发布标志位
		return mapping.findForward("taskDetailList");
	}

	// 保存任务详细信息 王思轩 09-1-21
	public ActionForward saveTaskDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (xsave(mapping, form, request, response,
				EvaConstants.TASK_NOT_PUBLISHED))
			return mapping.findForward("refreshSelfParent");
		else
			return mapping.findForward("fail");
	}

	// 上报任务详细信息 王思轩 09-1-21
	public ActionForward commitTaskDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (xsave(mapping, form, request, response, EvaConstants.TASK_PUBLISHED))
			return mapping.findForward("refreshSelfParent");
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
			String reportId = StaticMethod.null2String(request
					.getParameter("reportId"));
			String taskId = StaticMethod.null2String(request
					.getParameter("taskId"));
			String partner = StaticMethod.null2String(request
					.getParameter("partner"));
			String partnerName = StaticMethod.null2String(request
					.getParameter("partnerName"));
			String timeType = StaticMethod.null2String(request
					.getParameter("timeType"));
			String time = StaticMethod
					.null2String(request.getParameter("time"));
			String toAuditOrg = StaticMethod.null2String(request
					.getParameter("toAuditOrg"));
			String toAuditOrgType = StaticMethod.null2String(request
					.getParameter("toAuditOrgType"));
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String deptId = sessionform.getDeptid();
			String userId = sessionform.getUserid();
			if(!("").equals(toAuditOrg)){
				saveType = EvaConstants.TASK_AUDIT;
			}
			IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
			IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
			IEvaReportInfoMgr evaReportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");

			int maxLevel = 0;
			int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);

			// 指标实际总分
			float totalScore = 0; 
			// 指标权重总分
			float totalWeight = 0;
			for (int i = 1; i <= maxListNo; i++) {
				List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
						String.valueOf(i));
				for (int j = 0; j < rowList.size(); j++) {
					EvaTaskDetail etd = (EvaTaskDetail) rowList.get(j);
					if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						String nodeId = etd.getNodeId();
						String realScore = StaticMethod.null2String(request
								.getParameter(nodeId + "_sc"));
						String reduceReason = StaticMethod.null2String(request
								.getParameter(nodeId + "_rs"));
						String remark = StaticMethod.null2String(request
								.getParameter(nodeId + "_rm"));
						String scope = StaticMethod.null2String(request
								.getParameter(nodeId + "_scope"));
						//得到分数作用时间段
						String[] evaNum = scope.split("-");
						int startNum = StaticMethod.nullObject2int(evaNum[0]);
						int endNum = StaticMethod.nullObject2int(evaNum[1]);
						if (realScore != null && !"".equals(realScore)) {
							realScore = Float.valueOf(realScore).toString();
							// 加入总分
							if(endNum<=Integer.parseInt(time)&&endNum!=0){
							totalScore = totalScore
									+ Float.valueOf(realScore).floatValue();
							}
						}
						totalWeight = totalWeight
								+ etd.getWeight().floatValue();
						if (saveType.equals(EvaConstants.TASK_PUBLISHED)){
							//增加startNum到endNum的循环
							for(int k=startNum;k<=endNum&&k!=0;k++){
								EvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, String.valueOf(k), partner);
						if (ekis.getId() == null || "".equals(ekis.getId())) {
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							ekis.setCreateTime(format.format(new Date()));
						}
						ekis.setCreateUser(userId);
						ekis.setCreateDept(deptId);
						ekis.setIsPublish(saveType);
						ekis.setPartnerId(partner.trim());
						ekis.setPartnerName(partnerName);
						ekis.setRealScore(realScore);
						ekis.setReduceReason(reduceReason);
						ekis.setRemark(remark);
						ekis.setTaskDetailId(etd.getId());
						ekis.setTime(time);
						ekis.setTime(String.valueOf(k));
						ekis.setTimeType(timeType);
						ekis.setScope(scope);
						evaKpiInstanceMgr.saveKpiInstance(ekis);
						
						}
						
					}else{
						//保存考核评分信息
						/*
						EvaKpiInstance ekis = evaKpiInstanceMgr
									.getKpiInstanceByTimeAndPartner(
											etd.getId(), timeType, time,
											partner);
							if (ekis.getId() == null || "".equals(ekis.getId())) {
								DateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								ekis.setCreateTime(format.format(new Date()));
							}
							ekis.setCreateUser(userId);
							ekis.setCreateDept(deptId);
							ekis.setIsPublish(saveType);
							ekis.setPartnerId(partner.trim());
							ekis.setPartnerName(partnerName);
							ekis.setRealScore(realScore);
							ekis.setReduceReason(reduceReason);
							ekis.setRemark(remark);
							ekis.setTaskDetailId(etd.getId());
							ekis.setTime(time);
							ekis.setTimeType(timeType);
							ekis.setScope(scope);
							evaKpiInstanceMgr.saveKpiInstance(ekis);
							*/
							//保存考核评分信息

						IEvaKpiInstanceForAuditMgr evaKpiInstanceForAuditMgr = (IEvaKpiInstanceForAuditMgr) getBean("IevaKpiInstanceForAuditMgr");
						EvaKpiInstanceForAudit ekisa = evaKpiInstanceForAuditMgr
										.getKpiInstanceForAuditByTimeAndPartner(
												etd.getId(), timeType, time,
												partner);
							if (ekisa.getId() == null || "".equals(ekisa.getId())) {
								DateFormat format = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
								ekisa.setCreateTime(format.format(new Date()));
							}
							ekisa.setCreateUser(userId);
							ekisa.setCreateDept(deptId);
							ekisa.setIsPublish(saveType);
							ekisa.setPartnerId(partner.trim());
							ekisa.setPartnerName(partnerName);
							ekisa.setRealScore(realScore);
							ekisa.setReduceReason(reduceReason);
							ekisa.setRemark(remark);
							ekisa.setTaskDetailId(etd.getId());
							ekisa.setTime(time);
							ekisa.setTimeType(timeType);
							ekisa.setScope(scope);
							evaKpiInstanceForAuditMgr.saveKpiInstanceForAudit(ekisa);
						}
					}
				}
				if (rowList.size() > maxLevel) {
					maxLevel = rowList.size();
				}
			}

			// 如果发布，将总分信息存入ReportInfo表中
			EvaReportInfo eri = null;
			if (saveType.equals(EvaConstants.TASK_PUBLISHED)||saveType.equals(EvaConstants.TASK_AUDIT)) {
				eri = evaReportInfoMgr.getEvaReportInfo(reportId);
				EvaReportInfo report = null;
				List reportList = evaReportInfoMgr.getReportInfoByTaskId(taskId);
				for(int i=0;i<reportList.size();i++){
					report = (EvaReportInfo)reportList.get(i);
					if(report.getTime().equals(time)){
						eri = report;
						break;
					}	
				}
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				eri.setCreateTime(format.format(new Date()));
				eri.setIsPublish(saveType);
				eri.setPartnerId(partner.trim());
				eri.setPartnerName(partnerName);
				eri.setTaskId(taskId);
				eri.setTime(time);
				eri.setTimeType(timeType);
				eri.setTotalScore(Float.valueOf(totalScore).toString());
				eri.setTotalWeight(Float.valueOf(totalWeight).toString());
				evaReportInfoMgr.saveEvaReportInfo(eri);
			}
			if(saveType.equals(EvaConstants.TASK_AUDIT)){
				IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
				IEvaTaskAuditMgr auditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
				EvaTask et = taskMgr.getTaskById(taskId);
				//通过taskId取得模板Id
				EvaTaskAudit evaTaskAudit = new EvaTaskAudit();
				//根据当前提交发布用户的地域Id判断其公司所在等级（县公司，地市公司，省公司），提交审核结果状态
				evaTaskAudit.setTaskId(taskId);
				evaTaskAudit.setAuditOrg(toAuditOrg);
				//指定任务审核者类型
				evaTaskAudit.setAuditOrgType(toAuditOrgType);
				//设置任务初始审核状态
				evaTaskAudit.setAuditResult(EvaConstants.AUDIT_WAIT);
				//设置送审时间为系统当前时间
				evaTaskAudit.setAuditCreate(new java.util.Date());
				//设置考核时间
				evaTaskAudit.setAuditTime(time);
				evaTaskAudit.setAuditCycle(timeType);
				evaTaskAudit.setTemplateId(et.getTemplateId());
				evaTaskAudit.setPartner(partner.trim());
				evaTaskAudit.setTotalScore(totalScore);
				evaTaskAudit.setReportId(eri.getId());
				auditMgr.saveEvaTaskAudit(evaTaskAudit);
			}
			//当执行了所有任务后向相关协议的乙方负责人增加一条上传总结的任务
			List allreportList = evaReportInfoMgr.getReportInfoByTaskId(taskId);
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
				EvaTask et = taskMgr.getTaskById(taskId);
				EvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
			/*	IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
				PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(template.getAgreementId());
				pnrAgreementMain.setState(PnrAgreementConstants.AGREEMENT_STATE_UPLOAD);
				pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);*/
			}
			
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// 考核结果查询初始 王思轩 09-1-22
	public ActionForward queryInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// EvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EvaTree et = (EvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
		String userid = sessionform.getUserid();
		PartnerUserAndArea partnerUserAndArea = partnerUserAndAreaMgr.getObjectByUserId(userid);
		String cityId = "(";
		if(partnerUserAndArea!=null&&partnerUserAndArea.getCityId()!=null&&!partnerUserAndArea.getCityId().equals("")){
			String[] city =  partnerUserAndArea.getCityId().split(",");
			for(int i = 0;i<city.length;i++){
				if(i>0){
					cityId += ",";
				}
				cityId += "'"+city[i]+"'";
			}
		}else{
			cityId += "''";
		}
		cityId += ")";
		  	
		String sql = "from AreaDeptTree tree where 1=1 and tree.areaId in "
				+ cityId + " and tree.nodeType = 'factory' order by tree.nodeId";
		if(userid.equals("admin")){
			sql = "from AreaDeptTree tree where 1=1 and  tree.nodeType = 'factory' order by tree.nodeId";
		}
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		List factoryList = new ArrayList();
		List faclist = new ArrayList();
		factoryList = areaDeptTreeMgr.getInfoByCondition(sql);
		AreaDeptTree factree = null;
		String showNodeName = "";
		for(int i=0;i<factoryList.size();i++){
			Map hashMap = new HashMap();
			factree = (AreaDeptTree)factoryList.get(i);
			showNodeName = factree.getNodeName()+"("+mgr.id2Name(factree.getAreaId(),"tawSystemAreaDao")+")";
			hashMap.put("partnerId", factree.getNodeId());
			hashMap.put("partnerName", showNodeName);
			faclist.add(hashMap);
		}
		request.setAttribute("factoryList", faclist);

		request.setAttribute("templateType", templateType);
		return mapping.findForward("query");
	}

	// 考核结果查询 王思轩 09-1-22
	public ActionForward xquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");

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
			searchType = EvaConstants.TASK_PUBLISHED;
		}

		List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId, String
				.valueOf(1));
		List taskDetailList = new ArrayList();
		List taskList = new ArrayList();
		for (int i = 0; i < rowList.size(); i++) {
			EvaTaskDetail etd = (EvaTaskDetail) rowList.get(i);
			if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
				taskList = evaKpiInstanceMgr
						.getKpiInstanceListByTimeAndPartner(etd.getId(),
								partner, timeType, startTime, endTime,
								searchType);
				for (int j = 0; j < taskList.size(); j++) {
					EvaKpiInstance edi = (EvaKpiInstance) taskList.get(j);
					taskDetailList.add(edi);
				}
			}
		}

		//判断登陆人角色，区负责人则显示全部
		RoleIdList roleIdList = (RoleIdList) getBean("evaRoleIdList");
		String isProvinceAdmin = "N";
		if(sessionform.getUserid().equals("admin")){
			isProvinceAdmin = "Y";
		}
		else {
			List roleList = sessionform.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == roleIdList.getProvinceAdminRoleId().intValue()){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		
		List taskDetailListShow = new ArrayList();
		// 找到当前taskId对应的模板name
		for (int i = 0; i < taskDetailList.size(); i++) {
			EvaKpiInstance eki = (EvaKpiInstance) taskDetailList.get(i);
			EvaKpiInstanceForm ekif = (EvaKpiInstanceForm)convert(eki);
			EvaTaskDetail etd = taskDetailMgr.getTaskDetailById(eki.getTaskDetailId());
			EvaTask et = taskMgr.getTaskById(etd.getTaskId());
			System.out.println("-----etd.getTaskId() = "+etd.getTaskId()+"  ------et.getTemplateId() = "+et.getTemplateId());
			if(et.getId()!=null){
				String taskName = templateMgr.id2Name(et.getTemplateId());
				ekif.setTaskName(taskName);
				if(isProvinceAdmin.equals("Y")){
					taskDetailListShow.add(ekif);
				}
				//对当前任务进行判断，如果orgId与deptId一致则要，否者不要
				else {
					if(deptId.equals(et.getOrgId())){
						taskDetailListShow.add(ekif);
				    }
				}
			}
			ekif.setTaskId(etd.getTaskId());
				
				//找到对应的地域ID,前台传参用
//				String sql1 = "from AreaDeptTree tree where 1=1 and tree.nodeId = '"
//						+ ekif.getPartnerId() + "' and tree.nodeType ='factory'";
//				List nodeIdList = areaDeptTreeMgr.getInfoByCondition(sql1);
//				AreaDeptTree adt1 = (AreaDeptTree) nodeIdList.get(0);				
//				ekif.setPartnerId(adt1.getNodeId());

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
			EvaTask et = taskMgr.getTaskById(taskId);
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
	 * 根据模板id得到所有执行的任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xqueryByTpid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");

		String searchType = StaticMethod.null2String(request
				.getParameter("searchType"));//0表示草稿，1表示已发布
		String templateId = StaticMethod
				.null2String(request.getParameter("templateId"));
		String partner = StaticMethod.null2String(request.getParameter("partner"));

		String timeType = "月度";// 后续开发
		if (searchType == null || "".equals(searchType)) {
			searchType = EvaConstants.TASK_PUBLISHED;
		}
		EvaTemplate evaTemplate = templateMgr.getTemplate(templateId);
		List allTemplateList = templateMgr.getTemplateByblnode(evaTemplate.getBelongNode(),"");
				
		List taskDetailList = new ArrayList();
		List taskList = new ArrayList();
		List tasks = new ArrayList();
		List taskDetailListShow = new ArrayList();
		for(int tp=0;tp<allTemplateList.size();tp++){
			evaTemplate = (EvaTemplate)allTemplateList.get(tp);
			templateId = evaTemplate.getId();
		tasks = taskMgr.listTaskOfTpl(templateId);
		for(int k = 0; k < tasks.size(); k++){
			EvaTask task = (EvaTask)tasks.get(k);
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(task.getId(), String.valueOf(1));
		for (int i = 0; i < rowList.size(); i++) {
			EvaTaskDetail etd = (EvaTaskDetail) rowList.get(i);
			if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
				taskList = evaKpiInstanceMgr
						.getKpiInstanceListByTimeAndPartner(etd.getId(),
								partner, timeType, "", "",
								searchType);
				for (int j = 0; j < taskList.size(); j++) {
					EvaKpiInstance edi = (EvaKpiInstance) taskList.get(j);
					taskDetailList.add(edi);
				}
			}
		}
		}
		}

		String isProvinceAdmin = "Y";
		// 找到当前taskId对应的模板name
		for (int i = 0; i < taskDetailList.size(); i++) {
			EvaKpiInstance eki = (EvaKpiInstance) taskDetailList.get(i);
			EvaKpiInstanceForm ekif = (EvaKpiInstanceForm)convert(eki);
			EvaTaskDetail etd = taskDetailMgr.getTaskDetailById(eki.getTaskDetailId());
			EvaTask et = taskMgr.getTaskById(etd.getTaskId());
			System.out.println("-----etd.getTaskId() = "+etd.getTaskId()+"  ------et.getTemplateId() = "+et.getTemplateId());
			if(et.getId()!=null){
				String taskName = templateMgr.id2Name(et.getTemplateId());
				ekif.setTaskName(taskName);
				taskDetailListShow.add(ekif);
			}
			ekif.setTaskId(etd.getTaskId());
				
		}
		// 找到当前taskId对应的模板name
		String taskName = templateMgr.id2Name(templateId);
		request.setAttribute("taskName", taskName); // 任务名称
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("taskDetailList", taskDetailListShow); // 详细列表数据
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("startTime", "最早时间"); // 时间1
		request.setAttribute("endTime", "最晚时间"); // 时间2
		request.setAttribute("templateId", templateId); // 模板id
		request.setAttribute("queryType", "-1"); // 
		return mapping.findForward("queryList");
	}
}
