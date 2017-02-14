package com.boco.eoms.partner.siteEva.webapp.action;

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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiInstanceMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaReportInfoMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskDetailMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpiInstance;
import com.boco.eoms.partner.siteEva.model.SiteEvaReportInfo;
import com.boco.eoms.partner.siteEva.model.SiteEvaTask;
import com.boco.eoms.partner.siteEva.model.SiteEvaTaskDetail;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.RoleIdList;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaKpiInstanceForm;

public final class SiteEvaTaskAction extends BaseAction {

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
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		
//		得到专业类型		
		String specialty = StaticMethod.null2String(request.getParameter("specialty"));
		
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// SiteEvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			SiteEvaTree et = (SiteEvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商
		List factoryList = new ArrayList();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
//		String deptName = sessionform.getDeptname();
		
		String userid = sessionform.getUserid();
		PartnerUserAndArea partnerUserAndArea = partnerUserAndAreaMgr.getObjectByUserId(userid);
		String deptnames = "(";
		if(partnerUserAndArea!=null&&partnerUserAndArea.getAreaNames()!=null&&!partnerUserAndArea.getAreaNames().equals("")){
			String[] nn =  partnerUserAndArea.getAreaNames().split(",");
			for(int i = 0;i<nn.length;i++){
				deptnames += "'"+nn[i]+"',";
			}
	    	if(deptnames.lastIndexOf(",")==deptnames.length()-1){
	    		deptnames = deptnames.substring(0, deptnames.length()-1);
	    	}
		}
		else {
			deptnames += "''";
		}
    	deptnames += ")";  	
		String sql = "from AreaDeptTree tree where 1=1 and tree.nodeName in "
				+ deptnames + " and tree.nodeType in ('province','area')";
		if(userid.equals("admin")){
			sql = "from AreaDeptTree tree where 1=1 and tree.nodeType in ('province','area')";
		}
		
		List areaDeptList = areaDeptTreeMgr.getInfoByCondition(sql);
		List faclist = new ArrayList();
		if (!areaDeptList.isEmpty()) {
			String other = "(";
			String nodeid = "";
			Iterator it = areaDeptList.iterator();
			while(it.hasNext()){
				AreaDeptTree adt = (AreaDeptTree) it.next();
				nodeid = adt.getNodeId();
				other += "tree.parentNodeId like '"+nodeid+"%' or ";
			}
			other = other.substring(0, other.length()-3) + ")";
			String hql = " from AreaDeptTree tree where 1=1 and "
					+other+" and tree.nodeType ='factory'";
			factoryList = areaDeptTreeMgr.getInfoByCondition(hql);		
			AreaDeptTree factree = null ;
			AreaDeptTree parenttree = null ;
			String showNodeName = null;
			for(int i=0;i<factoryList.size();i++){
				Map hashMap = new HashMap();
				factree = (AreaDeptTree)factoryList.get(i);
				parenttree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factree.getParentNodeId());
				showNodeName = factree.getNodeName()+"("+parenttree.getNodeName()+")";
				hashMap.put("partnerId", factree.getNodeId());
				hashMap.put("partnerName", showNodeName);
				faclist.add(hashMap);
			}
			
		}
//		for (int i = 0; i < nodeNameList.size(); i++) {
//			String nodeName = nodeNameList.get(i).toString();
//			String sql1 = "from AreaDeptTree tree where 1=1 and tree.nodeName = '"
//					+ nodeName + "' and tree.nodeType ='factory'";
//			List nodeIdList = areaDeptTreeMgr.getInfoByCondition(sql1);
//			AreaDeptTree adt1 = (AreaDeptTree) nodeIdList.get(0);
//			factoryList.add(adt1.getNodeId());
//		}
		request.setAttribute("factoryList", faclist);

		request.setAttribute("templateType", templateType);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
    	city = PartnerCityByUser.getCityByProvince("26");
    	request.setAttribute("city", city);	
    	request.setAttribute("specialty", specialty);
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
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTemplateMgr tempMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");

		//2009-8-4 模版显示加角色控制，考核模版区（省）负责人能看到所有模版
		List list = new ArrayList();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		RoleIdList roleIdList = (RoleIdList) getBean("siteEvaRoleIdList");
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
			list = taskMgr.listTaskOfProvinceAdmin(SiteEvaConstants.TEMPLATE_ACTIVATED, templateTypeId);
		}
		else if(isProvinceAdmin.equals("N")){
			list = taskMgr.listTaskOfOrg(sessionform.getDeptid(),SiteEvaConstants.TEMPLATE_ACTIVATED, templateTypeId);
		}
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		for (int i = 0; i < list.size(); i++) {
			SiteEvaTask et = (SiteEvaTask) list.get(i);
			SiteEvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
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
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTemplateMgr tempMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");

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
		RoleIdList roleIdList = (RoleIdList) getBean("siteEvaRoleIdList");
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
			list = taskMgr.listTaskOfProvinceAdminInTime(SiteEvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		else if(isProvinceAdmin.equals("N")){
			list = taskMgr.listTaskOfOrgInTime(sessionform.getDeptid(),SiteEvaConstants.TEMPLATE_ACTIVATED, templateTypeId, start, end);
		}
		
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		for (int i = 0; i < list.size(); i++) {
			SiteEvaTask et = (SiteEvaTask) list.get(i);
			SiteEvaTemplate template = templateMgr.getTemplate(et.getTemplateId());
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
		ISiteEvaTaskDetailMgr taskDetailMgr = (ISiteEvaTaskDetailMgr) getBean("IsiteEvaTaskDetailMgr");
		ISiteEvaKpiInstanceMgr siteEvaKpiInstanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		ISiteEvaKpiMgr siteEvaKpiMgr = (ISiteEvaKpiMgr) getBean("IsiteEvaKpiMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String partnerId = StaticMethod.null2String(request
				.getParameter("partnerId"));
		String city = StaticMethod.null2String(request
				.getParameter("region"));		
		String specialty = StaticMethod.null2String(request
				.getParameter("specialty"));		
		
		if(partner==null ||"".equals(partner)){
			partner = partnerId.trim();
		}

		// 找到当前taskId对应的模板name
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
		SiteEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		
		SiteEvaTemplate siteEvaTemplate = templateMgr.getTemplate(et.getTemplateId());
		String parIds = siteEvaTemplate.getParIds();
		if(parIds.indexOf(",")==0){
			parIds = parIds.substring(1);
		}
		String[] parIdssTemp = parIds.split(",");

		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		List partnerDeptList = null;
		List parIdssList = new ArrayList();
		for(int i = 0 ; i<parIdssTemp.length;i++){
			StringBuffer where = new StringBuffer();
			where.append(" and areaId = '");
			where.append(city);
			where.append("' and deptMagId like '");
			where.append(parIdssTemp[i]);
			where.append("%' and specialty = '");
			where.append(specialty);
			where.append("'");
			partnerDeptList = partnerDeptMgr.getPartnerDepts(where.toString());
			if(partnerDeptList.size()>0){
				parIdssList.add(parIdssTemp[i]);
			}
		}
		
		StringBuffer str = new StringBuffer();
		for(int i = 0 ;parIdssList.size()>i;i++){
			str.append((String)parIdssList.get(i));
			if(parIdssList.size()!=i+1){
				str.append(",");
			}
		}
		parIds = str.toString();
		
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
		String queryType = StaticMethod.null2String(request
				.getParameter("queryType"));
		if(queryType.equals("0")){
			queryType = "query" ;
		}else{
			queryType = "run" ;
		}
//		if(queryType == null || "".equals(queryType)){
//			queryType = "run";
//		}

//		String timeType = "月度";// 后续开发
		String timeType = StaticMethod.null2String(request
				.getParameter("cycle"));
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
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		Map map = new HashMap();
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,String.valueOf(i));

			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				SiteEvaTaskDetail etd = (SiteEvaTaskDetail) rowList.get(j);
				SiteEvaKpiInstanceForm ekif = new SiteEvaKpiInstanceForm();
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
				if (SiteEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					SiteEvaKpiInstance ekis = new SiteEvaKpiInstance();
					for(int k =0 ; parIdssList.size()>k;k++){
						ekis = siteEvaKpiInstanceMgr
						.getKpiInstanceByTimeAndPartner(etd.getId(),
								timeType, time, String.valueOf(parIdssList.get(k)),city);
//						if(k==0&&k==parIdss.length-1){
//							ekif.setRealScore(ekis.getRealScore());
//							ekif.setReduceReason(ekis.getReduceReason());
//							ekif.setRemark(ekis.getRemark());
//						} else if (k==0) {
//							ekif.setRealScore(ekis.getRealScore()+",");
//							ekif.setReduceReason(ekis.getReduceReason()+",");
//							ekif.setRemark(ekis.getRemark()+",");
//						} else if (k!=0&&k==parIdss.length-1) {
//							ekif.setRealScore(ekif.getRealScore() + ekis.getRealScore());
//							ekif.setReduceReason(ekif.getReduceReason()+ekis.getReduceReason());
//							ekif.setRemark(ekif.getReduceReason()+ekis.getRemark());
//						} else {
//							ekif.setRealScore(ekif.getRealScore() + ekis.getRealScore()+",");
//							ekif.setReduceReason(ekif.getReduceReason()+ekis.getReduceReason()+",");
//							ekif.setRemark(ekif.getReduceReason()+ekis.getRemark()+",");
//						}
						map.put(etd.getId()+String.valueOf(parIdssList.get(k))+"RealScore", ekis.getRealScore());
						map.put(etd.getId()+String.valueOf(parIdssList.get(k))+"ReduceReason", ekis.getReduceReason());
						map.put(etd.getId()+String.valueOf(parIdssList.get(k))+"Remark", ekis.getRemark());
					}
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())) {
						ekif.setIsPublish(ekis.getIsPublish());
					} else {
						ekif.setIsPublish(SiteEvaConstants.TASK_NOT_PUBLISHED);
					}
					if ("1".equals(ekis.getIsPublish())) {
						isPublishButton = "display:none;";
					}
				}

				// 算法加入
				SiteEvaKpi ek = siteEvaKpiMgr.getKpi(ekif.getKpiId());
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

		request.setAttribute("taskName", taskName); // 任务名称

		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
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
		request.setAttribute("map", map);
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		request.setAttribute("city", city); // 地市信息
		request.setAttribute("parIdss", parIdssList); // 被考核合作伙伴
		request.setAttribute("parIds", parIds); // 被考核合作伙伴
		return mapping.findForward("taskDetailList");
	}

	// 保存任务详细信息 王思轩 09-1-21
	public ActionForward saveTaskDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (xsave(mapping, form, request, response,
				SiteEvaConstants.TASK_NOT_PUBLISHED))
			return mapping.findForward("success");
		else
			return mapping.findForward("fail");
	}

	// 上报任务详细信息 王思轩 09-1-21
	public ActionForward commitTaskDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (xsave(mapping, form, request, response, SiteEvaConstants.TASK_PUBLISHED))
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
			String taskId = StaticMethod.null2String(request.getParameter("taskId"));
			String partner = StaticMethod.null2String(request.getParameter("partner"));
			String timeType = StaticMethod.null2String(request.getParameter("timeType"));
			String time = StaticMethod.null2String(request.getParameter("time"));
			String city = StaticMethod.null2String(request.getParameter("city"));
			String parIds = StaticMethod.null2String(request.getParameter("parIds"));
			
			if(parIds.indexOf(",")==0){
				parIds = parIds.substring(1);
			}
			String[] parIdss = parIds.split(",");
			
			ISiteEvaTaskDetailMgr taskDetailMgr = (ISiteEvaTaskDetailMgr) getBean("IsiteEvaTaskDetailMgr");
			ISiteEvaKpiInstanceMgr siteEvaKpiInstanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
			ISiteEvaReportInfoMgr siteEvaReportInfoMgr = (ISiteEvaReportInfoMgr) getBean("IsiteEvaReportInfoMgr");
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
//			有几个合作伙伴保存几次
			for(int f = 0 ; parIdss.length>f;f++){
				partner = parIdss[f];
				AreaDeptTree factree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(partner.trim());
				AreaDeptTree parenttree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factree.getParentNodeId());
				String showNodeName = factree.getNodeName()+"("+parenttree.getNodeName()+")";
	
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
						SiteEvaTaskDetail etd = (SiteEvaTaskDetail) rowList.get(j);
						if (SiteEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
							String nodeId = etd.getNodeId();
							String realScore = StaticMethod.null2String(request
									.getParameter(nodeId + parIdss[f] + "_sc"));
							String reduceReason = StaticMethod.null2String(request
									.getParameter(nodeId + parIdss[f] + "_rs"));
							String remark = StaticMethod.null2String(request
									.getParameter(nodeId + parIdss[f] + "_rm"));
							if (realScore != null && !"".equals(realScore)) {
								realScore = Float.valueOf(realScore).toString();
								// 加入总分
								totalScore = totalScore
										+ Float.valueOf(realScore).floatValue();
							}
							totalWeight = totalWeight
									+ etd.getWeight().floatValue();
	
							SiteEvaKpiInstance ekis = siteEvaKpiInstanceMgr
									.getKpiInstanceByTimeAndPartner(etd.getId(),
											timeType, time, partner);
							if (ekis.getId() == null || "".equals(ekis.getId())) {
								DateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								ekis.setCreateTime(format.format(new Date()));
							}
							ekis.setIsPublish(saveType);
							ekis.setPartnerId(partner.trim());
							ekis.setPartnerName(showNodeName);
							ekis.setRealScore(realScore);
							ekis.setReduceReason(reduceReason);
							ekis.setRemark(remark);
							ekis.setTaskDetailId(etd.getId());
							ekis.setTime(time);
							ekis.setTimeType(timeType);
							ekis.setCity(city);
	
							siteEvaKpiInstanceMgr.saveKpiInstance(ekis);
						}
					}
					if (rowList.size() > maxLevel) {
						maxLevel = rowList.size();
					}
				}
	
				// 如果发布，将总分信息存入ReportInfo表中
				if (saveType.equals(SiteEvaConstants.TASK_PUBLISHED)) {
					SiteEvaReportInfo eri = new SiteEvaReportInfo();
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					eri.setCreateTime(format.format(new Date()));
					eri.setIsPublish(saveType);
					eri.setPartnerId(partner.trim());
					eri.setPartnerName(showNodeName);
					eri.setTaskId(taskId);
					eri.setTime(time);
					eri.setTimeType(timeType);
					eri.setTotalScore(Float.valueOf(totalScore).toString());
					eri.setTotalWeight(Float.valueOf(totalWeight).toString());
					siteEvaReportInfoMgr.saveSiteEvaReportInfo(eri);
				}
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
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTreeMgr treeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// SiteEvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);
		String specialty = StaticMethod.null2String(request
				.getParameter("specialty"));
		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			SiteEvaTree et = (SiteEvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商
		List factoryList = new ArrayList();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
		String userid = sessionform.getUserid();
		PartnerUserAndArea partnerUserAndArea = partnerUserAndAreaMgr.getObjectByUserId(userid);
		String deptnames = "(";
		if(partnerUserAndArea!=null&&partnerUserAndArea.getAreaNames()!=null&&!partnerUserAndArea.getAreaNames().equals("")){
			String[] nn =  partnerUserAndArea.getAreaNames().split(",");
			for(int i = 0;i<nn.length;i++){
				deptnames += "'"+nn[i]+"',";
			}
	    	if(deptnames.lastIndexOf(",")==deptnames.length()-1){
	    		deptnames = deptnames.substring(0, deptnames.length()-1);
	    	}
		}
		else {
			deptnames += "''";
		}
    	deptnames += ")";
  					
//		String deptName = sessionform.getDeptname();
		String sql = "from AreaDeptTree tree where 1=1 and tree.nodeName in "
				+ deptnames + " and tree.nodeType in ('province','area')";
		if(userid.equals("admin")){
			sql = "from AreaDeptTree tree where 1=1 and tree.nodeType in ('province','area')";
		}
		
		List areaDeptList = areaDeptTreeMgr.getInfoByCondition(sql);
		if (!areaDeptList.isEmpty()) {
			String other = "(";
			String nodeid = "";
			Iterator it = areaDeptList.iterator();
			while(it.hasNext()){
				AreaDeptTree adt = (AreaDeptTree) it.next();
				nodeid = adt.getNodeId();
				other += "tree.parentNodeId like '"+nodeid+"%' or ";
			}
			other = other.substring(0, other.length()-3) + ")";
			String hql = "select distinct(tree.nodeName) from AreaDeptTree tree where 1=1 and "
					+other+" and tree.nodeType ='factory'";
			factoryList = areaDeptTreeMgr.getInfoByCondition(hql);
		}
		request.setAttribute("factoryList", factoryList);
		request.setAttribute("specialty", specialty);
		request.setAttribute("templateType", templateType);
		return mapping.findForward("query");
	}

	public ActionForward xquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		ISiteEvaTaskDetailMgr taskDetailMgr = (ISiteEvaTaskDetailMgr) getBean("IsiteEvaTaskDetailMgr");
		ISiteEvaKpiInstanceMgr siteEvaKpiInstanceMgr = (ISiteEvaKpiInstanceMgr) getBean("IsiteEvaKpiInstanceMgr");
		ISiteEvaTaskMgr taskMgr = (ISiteEvaTaskMgr) getBean("IsiteEvaTaskMgr");
		ISiteEvaTemplateMgr templateMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
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
		String specialty = StaticMethod.null2String(request
				.getParameter("specialty"));		
		String timeType = StaticMethod
		.null2String(request.getParameter("timeType"));
		
		String startTime = year1 + month1;
		String endTime = year2 + month2;
		if (searchType == null || "".equals(searchType)) {
			searchType = SiteEvaConstants.TASK_PUBLISHED;
		}

		List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId, String
				.valueOf(1));
		List taskDetailList = new ArrayList();
		List taskList = new ArrayList();
		for (int i = 0; i < rowList.size(); i++) {
			SiteEvaTaskDetail etd = (SiteEvaTaskDetail) rowList.get(i);
			if (SiteEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
				taskList = siteEvaKpiInstanceMgr
						.getKpiInstanceListByTimeAndPartner(etd.getId(),
								partner, timeType, startTime, endTime,
								searchType);
				for (int j = 0; j < taskList.size(); j++) {
					SiteEvaKpiInstance edi = (SiteEvaKpiInstance) taskList.get(j);
					taskDetailList.add(edi);
				}
			}
		}

		//判断登陆人角色，区负责人则显示全部
		RoleIdList roleIdList = (RoleIdList) getBean("siteEvaRoleIdList");
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
		String taskIdValue = "";
		String templateIdValue = "";
		for (int i = 0; i < taskDetailList.size(); i++) {
			SiteEvaKpiInstance eki = (SiteEvaKpiInstance) taskDetailList.get(i);
			SiteEvaKpiInstanceForm ekif = (SiteEvaKpiInstanceForm)convert(eki);
			SiteEvaTaskDetail etd = taskDetailMgr.getTaskDetailById(eki.getTaskDetailId());
			SiteEvaTask et = taskMgr.getTaskById(etd.getTaskId());
			System.out.println("-----etd.getTaskId() = "+etd.getTaskId()+"  ------et.getTemplateId() = "+et.getTemplateId());
			
//			if(taskIdValue.equals(etd.getTaskId())&&templateIdValue.equals(et.getTemplateId())){
//			}else{
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
//			}
			taskIdValue = etd.getTaskId();
			templateIdValue = et.getTemplateId();
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
			SiteEvaTask et = taskMgr.getTaskById(taskId);
			taskName = templateMgr.id2Name(et.getTemplateId());
		}
		request.setAttribute("taskName", taskName); // 任务名称

		request.setAttribute("taskDetailList", taskDetailListShow); // 详细列表数据
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("startTime", startTime); // 时间1
		request.setAttribute("endTime", endTime); // 时间2
//		request.setAttribute("parIds", parIds);//合作伙伴部门id信息
		request.setAttribute("specialty", specialty);//专业		
		return mapping.findForward("queryList");
	}
}
