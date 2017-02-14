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
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiFacturyMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiInstanceMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaReportInfoMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskDetailMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;
import com.boco.eoms.partner.eva.util.PnrEvaStaticMethod;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiInstanceForm;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaReportMultiDeptForm;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaReportYearStaticFrom;

public final class PnrEvaReportInfoAction extends BaseAction {

	/**
	 * 单一月份、单一厂商查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportSingle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("IPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.nullObject2String(request.getParameter("taskId"));
		String partner = StaticMethod.nullObject2String(request
				.getParameter("partner"));
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));

		String queryType = StaticMethod.nullObject2String(request
				.getParameter("queryType"));
		if (queryType == null || "".equals(queryType))
			queryType = "run";
		String timeType = "月度";// 后续开发
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.nullObject2String(request.getParameter("time"));
		}
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
					String.valueOf(i));

			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
				PnrEvaKpiInstanceForm ekif = new PnrEvaKpiInstanceForm();
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
				if (PnrEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					PnrEvaKpiInstance ekis = evaKpiInstanceMgr
							.getKpiInstanceByTimeAndPartner(etd.getId(),
									timeType, time, partner);
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())
							&& ekis.getIsPublish().equals(
									PnrEvaConstants.TASK_PUBLISHED)) {
						ekif.setRealScore(ekis.getRealScore());
						ekif.setReduceReason(ekis.getReduceReason());
						ekif.setRemark(ekis.getRemark());
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
			tableList.add(rowListShow);
			if (rowList.size() > maxLevel) {
				maxLevel = rowList.size();
			}
		}

		// 找到当前taskId对应的模板name
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称

		// 找到任务权重总分和实际总得分：
		IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType
				+ "' and eri.partnerId = '" + partner + "'";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = "";
		String totalScore = "";
		if (!reportInfoList.isEmpty()) {
			PnrEvaReportInfo eri = (PnrEvaReportInfo) reportInfoList.get(0);
			totalWeight = eri.getTotalWeight();
			totalScore = String.valueOf(eri.getTotalScore());
		}
		request.setAttribute("totalWeight", totalWeight);
		request.setAttribute("totalScore", totalScore);

		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		return mapping.findForward("reportSingle");
	}

	/**
	 * 不同月份、同一厂商查询初始
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryInitMultiMonth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
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
		request.setAttribute("partnerNodeType", partnerNodeType);
		return mapping.findForward("queryMultiMonth");
	}

	/**
	 * (同一厂商不同月份)查询合作厂商和地域信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportMultiMonth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		String templateTypeId = StaticMethod.nullObject2String(request.getParameter("templateTypeId"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String startYear = StaticMethod.nullObject2String(request.getParameter("startYear"));
		String startMonth = StaticMethod.nullObject2String(request.getParameter("startMonth"));
		String endYear = StaticMethod.nullObject2String(request.getParameter("endYear"));
		String endMonth = StaticMethod.nullObject2String(request.getParameter("endMonth"));
		if(null == timeType || "".equals(timeType)){
			timeType = "月度";//后期开发
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		Map map = (Map) PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(map.get("areaId"));
		TawSystemArea area = areaMgr.getAreaByAreaId(areaId);
		//查处所属地域的下一级地域
		List areas = areaMgr.getSonAreaByAreaId(areaId);
		areas.add(area);
		//根据考核层面筛选地域
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();	
		PnrEvaTemplate template = templateMgr.getTemplate(templateId);
		if(PnrEvaConstants.EXECUTE_TYPE_PROVINCE.equals(template.getExecuteType())){
			for(Iterator it=areas.iterator();it.hasNext();){
				TawSystemArea sa = (TawSystemArea) it.next();
				if((sa.getAreaid().length()-rootAreaId.length())/2!=0){
					it.remove();
				}
			}
		}
		if(PnrEvaConstants.EXECUTE_TYPE_CITY.equals(template.getExecuteType())){
			for(Iterator it=areas.iterator();it.hasNext();){
				TawSystemArea sa = (TawSystemArea) it.next();
				if((sa.getAreaid().length()-rootAreaId.length())/2!=1){
					it.remove();
				}
			}
		}
		if(PnrEvaConstants.EXECUTE_TYPE_TOWN.equals(template.getExecuteType())){
			for(Iterator it=areas.iterator();it.hasNext();){
				TawSystemArea sa = (TawSystemArea) it.next();
				if((sa.getAreaid().length()-rootAreaId.length())/2!=2){
					it.remove();
				}
			}
		}

		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree pnrEvaTree=treeMgr.getTreeNode(template.getBelongNode());
		List allKpiFactury = facturyMgr.getAllKpiFacturyByNodeId(pnrEvaTree.getNodeId());
		request.setAttribute("areas", areas);
		request.setAttribute("allKpiFactury", allKpiFactury);
		request.setAttribute("startYear", startYear);
		request.setAttribute("startMonth", startMonth);
		request.setAttribute("endYear", endYear);
		request.setAttribute("endMonth", endMonth);
		request.setAttribute("timeType", timeType);
		request.setAttribute("templateName", template.getTemplateName());
		request.setAttribute("templateId", templateId);
		request.setAttribute("nodeId", pnrEvaTree.getNodeId());
		request.setAttribute("templateTypeId", templateTypeId);
		return mapping.findForward("queryMultiMonthChooseArea");
	}

	/**
	 * (同一厂商不同月份)查询合作厂商信息(福建个性用)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportMonthChoosePartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		String templateTypeId = StaticMethod.nullObject2String(request.getParameter("templateTypeId"));
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String startYear = StaticMethod.nullObject2String(request.getParameter("startYear"));
		String startMonth = StaticMethod.nullObject2String(request.getParameter("startMonth"));
		String endYear = StaticMethod.nullObject2String(request.getParameter("endYear"));
		String endMonth = StaticMethod.nullObject2String(request.getParameter("endMonth"));
		if(null == timeType || "".equals(timeType)){
			timeType = "月度";//后期开发
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		Map map = (Map) PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(map.get("areaId"));
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskId);
		PnrEvaTemplate pnrEvaTemplate = templateMgr.getTemplate(pnrEvaTask.getTemplateId());
		List allKpiFactury = facturyMgr.getAllKpiFacturyByNodeId(templateTypeId);
		request.setAttribute("areaId", areaId);
		request.setAttribute("allKpiFactury", allKpiFactury);
		request.setAttribute("startYear", startYear);
		request.setAttribute("startMonth", startMonth);
		request.setAttribute("endYear", endYear);
		request.setAttribute("endMonth", endMonth);
		request.setAttribute("timeType", timeType);
		request.setAttribute("templateName", pnrEvaTemplate.getTemplateName());
		request.setAttribute("taskId", taskId);
		request.setAttribute("nodeId", pnrEvaTask.getNodeId());
		request.setAttribute("templateTypeId", templateTypeId);
		request.setAttribute("partnerNodeType", partnerNodeType);
		return mapping.findForward("queryMultiMonthChooseArea");
	}

	/**
	 * 同一月份、不同厂商查询列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryInitMultiList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String startYear = StaticMethod.nullObject2String(request.getParameter("startYear"));
		String startMonth = StaticMethod.nullObject2String(request.getParameter("startMonth"));
		String endYear = StaticMethod.nullObject2String(request.getParameter("endYear"));
		String endMonth = StaticMethod.nullObject2String(request.getParameter("endMonth"));
		String partnerId = StaticMethod.nullObject2String(request.getParameter("partnerId"));
		String area = StaticMethod.nullObject2String(request.getParameter("area"));
		String startTime = startYear + "-" + startMonth ;
		String endTime = endYear + "-" + endMonth  ;
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" and timeType = '"+timeType+"'");
		if(null != area || !"".equals(area) )
			whereStr.append(" and area = '"+area+"'");
		if(null != partnerId || !"".equals(partnerId))
			whereStr.append(" and partnerId = '"+partnerId+"'");
		
		
		//Informix数据库
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			if(null != startTime || !"".equals(startTime))
				whereStr.append(" and '"+startTime+"' <= time ");
			if(null != endTime || !"".equals(endTime))
				whereStr.append(" and time <= '"+endTime+"'" );
		}
		//Oracle数据库		
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			if(null != startTime || !"".equals(startTime))
				whereStr.append(" and to_date('"+startTime+"','yyyy-MM') <= to_date(time,'yyyy-MM')");
			if(null != endTime || !"".equals(endTime))
				whereStr.append(" and to_date(time,'yyyy-MM') <= to_date('"+endTime+"','yyyy-MM')" );
		}
		

		
		
		
		
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask task =  new PnrEvaTask();
		if("".equals(taskId)){
			task = taskMgr.getTaskByTplAndOrg(templateId, area);
		}else{
			task = taskMgr.getTaskById(taskId);
		}
		if	(task.getNodeId() !=null && !"".equals(task.getNodeId()))
			whereStr.append(" and belongNode = '"+task.getNodeId()+"'" );	
		Map map = (Map)reportInfoMgr.listReportInfoForPage(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) iterator.next();
			IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
			PnrEvaTask et = taskMgr.getTaskById(task.getId());
			String createTime = et.getCreateTime().substring(0,10);
			reportInfo.setTaskName(templateMgr.id2Name(et.getTemplateId())+"("+createTime+")");//任务名称
			reportInfo.setArea(areaMgr.getAreaByAreaId(area).getAreaname());			
			reportInfo.setPartnerName(facturyMgr.getKpiFacturyByFactury(reportInfo.getPartnerId()).getFacturyName());
		}
		request.setAttribute("areaId", area);
		request.setAttribute("reportMultiMonthList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("reportMultiList");
	}

	/**
	 * 同一月份、不同厂商查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportMultiDept(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("IPnrEvaKpiInstanceMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.nullObject2String(request.getParameter("taskId"));
		String partner = "";
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));

		String timeType = "月度";// 后续开发
		String time = year + month;

		// 先根据月份，任务从reportInfo表中查询分数：
		IPnrEvaReportInfoMgr reportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType + "'";
		sql += " order by eri.partnerId";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = ""; // 权重总分
		Map kpiWeights = new TreeMap();// 指标ID,权重
		for (int m = 0; m < reportInfoList.size(); m++) {
			PnrEvaReportInfo eri = (PnrEvaReportInfo) reportInfoList.get(m);
			if (totalWeight == null || "".equals(totalWeight)) {
				totalWeight = eri.getTotalWeight();
			}
			PnrEvaReportMultiDeptForm ermdf = new PnrEvaReportMultiDeptForm();
			ermdf.setAreaId(sessionform.getDeptname());
			//存合作伙伴名称
			ermdf.setDeptId(eri.getPartnerId());
			ermdf.setTotalScore(eri.getTotalScore());

			// 相应具体指标分数
			int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
			List kpiList = new ArrayList();// 横向显示的指标
			for (int i = 1; i <= maxListNo; i++) {
				List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
						String.valueOf(i));
				for (int j = 0; j < rowList.size(); j++) {
					PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
					if (PnrEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						PnrEvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, eri.getPartnerId());
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())
								&& ekis.getIsPublish().equals(
										PnrEvaConstants.TASK_PUBLISHED)) {
							kpiList.add(String.valueOf(ekis.getRealScore()));
						} else {
							kpiList.add("");
						}

						// 将对应指标ID-先转换成指标NAME,权重存入kpiWeights中，
						IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
						kpiWeights.put(evaKpiMgr.id2Name(etd.getKpiId()), Float.valueOf(etd.getWeight()));
					}
				}
			}
			ermdf.setKpiScoreList(kpiList);

			// 加入总LIST
			tableList.add(ermdf);
		}
		if (partner == null || "".equals(partner)) {
			partner = "所有合作伙伴";
		}

		// 找到当前taskId对应的模板name
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称

		String isListEmpty = "";
		if (!reportInfoList.isEmpty()) {
			isListEmpty = "notEmpty";
		}
		request.setAttribute("isListEmpty", isListEmpty);// 总list是否有数据

		request.setAttribute("totalWeight", totalWeight);// 总权重
		request.setAttribute("kpiWeights", kpiWeights); // 横向的KPI及权重

		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		return mapping.findForward("reportMultiDept");
	}
	
	//二期开发从这里开始
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
	public ActionForward reportView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		
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
				if(tempRole.getRoleId() == Integer.parseInt(pnrEvaRoleIdList.getReportShowRoleId())){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		
		String deptId = sessionform.getDeptid();
		String executeOrg = PnrEvaConstants.EXECUTE_ORG_EC;//各地域
		
		if(deptId.indexOf(pnrEvaRoleIdList.getNdDept())==0){
			executeOrg = PnrEvaConstants.EXECUTE_ORG_ND;//省网络部
		}else if(deptId.indexOf(pnrEvaRoleIdList.getNmcDept())==0){
			executeOrg = PnrEvaConstants.EXECUTE_ORG_NMC;//省网络中心
		}
		list = taskMgr.listTaskOfArea(areaId,PnrEvaConstants.TEMPLATE_ACTIVATED, templateTypeId, executeOrg);
		
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
	 * 页面二级联动，已知模板分类，返回对应模板任务list,根据模版分类和模版激活时间查询出模版(暂不用)
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
				if(tempRole.getRoleId() == Integer.parseInt(pnrEvaroleIdList.getReportShowRoleId())){
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
	

		//得到登陆人的考核操作角色,用以确定地域信息
/*		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));; 
*/
		// 已激活的任务列表

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
	 * 打开任务报告页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskReportInfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String showType = StaticMethod.nullObject2String(request.getParameter("showType"));
		String startYear = StaticMethod.nullObject2String(request.getParameter("startYear"));
		String startMonth = StaticMethod.nullObject2String(request.getParameter("startMonth"));
		String endYear = StaticMethod.nullObject2String(request.getParameter("endYear"));
		String endMonth = StaticMethod.nullObject2String(request.getParameter("endMonth"));
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String partner = StaticMethod.nullObject2String(request.getParameter("partner"));
		String partnerId = StaticMethod.nullObject2String(request.getParameter("partnerId"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String time = StaticMethod.nullObject2String(request.getParameter("time"));

		String timeType = "月度";// 后续开发
		String startTime = startYear + "-"+startMonth;
		String endTime = endYear+"-"+endMonth;
		if(startTime == null || "-".equals(startTime)){
			startTime = StaticMethod.nullObject2String(request.getParameter("startTime"));
		}
		if(endTime == null || "-".equals(endTime)){
			endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		String hql = "";
		Map map = new HashMap();
		List list = new ArrayList();
		if(showType.equals("detail")){
				list = evaReportInfoMgr.getReportInfoByTimeAndPartner(templateId, 
						areaId, timeType,time,partnerId,PnrEvaConstants.TASK_PUBLISHED);
				map.put("total", Integer.valueOf(list.size()));
		}else{
			PnrEvaTask  pnrEvaTask = taskMgr.getTaskById(taskId);
		if	(pnrEvaTask.getNodeId() !=null && !"".equals(pnrEvaTask.getNodeId()))
				hql = hql + " and belongNode = '"+pnrEvaTask.getNodeId()+"'" ;			
		if	(timeType !=null && !"".equals(timeType))
			hql = hql + " and timeType = '"+timeType+"'" ;
		if	(areaId !=null && !"".equals(areaId))
			hql = hql + " and area = '"+areaId+"'" ;
		if (partnerId != null && !"".equals(partnerId))
			hql = hql + " and partnerId='" + partnerId + "'";
		//Informix数据库
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			if (startTime != null && !"".equals(startTime))
				hql = hql + " and '"+startTime+"'<= time  ";
			if	(endTime != null && !"".equals(endTime))
				hql = hql +" and time <= '"+endTime+"'" ;
		}
		//Oracle数据库		
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			if (startTime != null && !"".equals(startTime))
				hql = hql + " and to_date('"+startTime+"','yyyy-MM') <= to_date(time,'yyyy-MM')  ";
			if	(endTime != null && !"".equals(endTime))
				hql = hql +" and to_date(time,'yyyy-MM') <= to_date('"+endTime+"','yyyy-MM')" ;
		}
			map = (Map)evaReportInfoMgr.listReportInfoForPage(pageIndex, pageSize, hql);
			list = (List) map.get("result");
		}
		Iterator iterator = list.iterator();
		String partnerShow = "false";
		while(iterator.hasNext()){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) iterator.next();
			PnrEvaTask et = taskMgr.getTaskById(reportInfo.getTaskId());
			reportInfo.setTaskName(templateMgr.id2Name(et.getTemplateId()));//任务名称
//			reportInfo.setArea(areaMgr.getAreaByAreaId(areaId).getAreaname());		
			if(reportInfo.getPartnerId()!=null&&!reportInfo.equals("")){
				partnerShow = "ture";
			}
		}
		request.setAttribute("areaId", areaId);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("partner", partner);
		request.setAttribute("partnerShow", partnerShow);
		request.setAttribute("reportInfoList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("reportInfoList");
	}
	
	/**
	 * 全部考核任务报告详情(一般模板合并显示)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportDetailListNormal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr pnrEvaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		List tableList = new ArrayList();
		String taskType="";
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String taskId = StaticMethod
				.nullObject2String(request.getParameter("taskId"));
		if(taskId == null || "".equals(taskId)){
			taskId = taskMgr.getTaskByTplAndOrg(templateId, areaId).getId();
		}
//		String partner = StaticMethod.nullObject2String(request
//				.getParameter("partner"));
		String partner = StaticMethod.nullObject2String(request
				.getParameter("partnerId"));
		
		//页面递归传递用
		String startTime = StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));

		String queryType = StaticMethod.nullObject2String(request
				.getParameter("queryType"));
		if(queryType.equals("0")){
			queryType = "query" ;
		}else{
			queryType = "run" ;
		}
		//String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String timeType = "月度";// 后续开发
		String time = StaticMethod.nullObject2String(request.getParameter("time"));
		String isPublishButton = "";
		String assessUserId="";//评定人
		String assessDeptId="";//评定部门
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		int maxNodeIdSize = 0;
		float allScore = 0;
		
		PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskId);
		String belongNode = pnrEvaTask.getNodeId() ;
		List list = evaReportInfoMgr.getReportInfoByTimeAndPartner(pnrEvaTask.getTemplateId(), 
				areaId, timeType,time,partner,PnrEvaConstants.TASK_PUBLISHED);
		if(list.size()>0){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) list.get(0);
			allScore = reportInfo.getTotalScore();
			assessUserId = reportInfo.getAssessUserId();
			assessDeptId = reportInfo.getAssessDeptId();
		}
		String pNodeId = pnrEvaTreeMgr.getTreeNode(belongNode).getNodeId();
		String nodeId = "";
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
						.getReportInfoByTimeAndPartner(etd.getTemplateId(),etd.getArea(), timeType, time, partner,PnrEvaConstants.TASK_PUBLISHED);
					PnrEvaReportInfo pnrEvaReportInfo = new PnrEvaReportInfo();
					if(pnrEvaReportInfoList.size()>0){
						pnrEvaReportInfo = (PnrEvaReportInfo)pnrEvaReportInfoList.get(0);
					}
					
					
					
					
					
					float totalScore = 0;
					float lastScore = 0;
					if(taskId != null && !"".equals(taskId)){
						lastScore = pnrEvaReportInfo.getTotalScore();
					}else{

					List reportList =  evaReportInfoMgr
						.getReportInfosByTimeAndAllPartner(etd.getTemplateId(),etd.getArea(), timeType, time, PnrEvaConstants.TASK_PUBLISHED);	

					for(int k=0;k<reportList.size();k++){
						pnrEvaReportInfo = (PnrEvaReportInfo) reportList.get(k);
						totalScore += pnrEvaReportInfo.getTotalScore();
					}
					//现在是算已经发布的合作伙伴打分的平均值
					if(reportList.size()>0){
						lastScore = totalScore/reportList.size();
/*					//下面是所有厂商取平均值
					IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
					List facturyList = facturyMgr.getAllKpiFacturyByNodeId(etd.getNodeId());
					int partnerNum = facturyList.size();
					if(partnerNum>0){
					averageScore = totalScore/partnerNum;
*/
						}else{
							lastScore=0;
						}
					}
//					ekif.setReduceReason(ekis.getReduceReason());
					ekif.setRemark(ekis.getRemark());
					ekif.setMaintenanceRatio(ekis.getMaintenanceRatio());
					//得到下级汇总的数据
					//设置float精度小数点后两位
			        BigDecimal bd = new BigDecimal(lastScore);
			        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			        lastScore = bd.floatValue();
					ekif.setRealScore(lastScore);
					//得到汇总状态
					if(pnrEvaReportInfo.getState()!=null&&!"".equals(pnrEvaReportInfo.getState())){
						ekif.setState(pnrEvaReportInfo.getState());
					}else{
						ekif.setState(PnrEvaConstants.AUDIT_UNDO);
					}
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
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())) {
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
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
						isPublishButton = "display:none;";
					}
					assessUserId = ekis.getAssessUserId();
					assessDeptId = ekis.getAssessDeptId();
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
			
//			if (rowList.size() > maxLevel) {
//				maxLevel = rowList.size();
//			}
		}

		// 找到当前taskId对应的模板name	
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		//如果记录不存在，则给出提示
		if(!(taskId == null || "".equals(taskId))){
			PnrEvaTask et = taskMgr.getTaskById(taskId);
			String taskName = templateMgr.id2Name(et.getTemplateId());
			request.setAttribute("taskName", taskName); // 任务名称
			request.setAttribute("templateId", et.getTemplateId());
		}
		
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("areaId", areaId);
		request.setAttribute("allScore", Float.toString(allScore));
		request.setAttribute("assessUserId", assessUserId);
		request.setAttribute("assessDeptId", assessDeptId);
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
//		request.setAttribute("partnerId", partnerId);//合作伙伴Id
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
	 * 全部考核任务报告详情（福建地域合并显示）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportDetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTaskDetailMgr taskDetailMgr = (IPnrEvaTaskDetailMgr) getBean("iPnrEvaTaskDetailMgr");
		IPnrEvaKpiInstanceMgr evaKpiInstanceMgr = (IPnrEvaKpiInstanceMgr) getBean("iPnrEvaKpiInstanceMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTreeMgr pnrEvaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		List tableList = new ArrayList();
		String taskType="";
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String taskId = StaticMethod
				.nullObject2String(request.getParameter("taskId"));
		if(taskId == null || "".equals(taskId)){
			taskId = taskMgr.getTaskByTplAndOrg(templateId, areaId).getId();
		}
		String partner = StaticMethod.nullObject2String(request
				.getParameter("partnerId"));
		//页面递归传递用
		String startTime = StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));

		String queryType = StaticMethod.nullObject2String(request
				.getParameter("queryType"));
		if(queryType.equals("0")){
			queryType = "query" ;
		}else{
			queryType = "run" ;
		}
		//String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String timeType = "月度";// 后续开发
		String time = StaticMethod.nullObject2String(request.getParameter("time"));
		String isPublishButton = "";
		String assessUserId="";//评定人
		String assessDeptId="";//评定部门
		int maxLevel = 0;
		int maxNodeIdSize = 0;
		float allScore = 0;
		
		PnrEvaTask pnrEvaTask = taskMgr.getTaskById(taskId);
		String belongNode = pnrEvaTask.getNodeId() ;
		List list = evaReportInfoMgr.getReportInfoByTimeAndPartner(pnrEvaTask.getTemplateId(), 
				areaId, timeType,time,partner,PnrEvaConstants.TASK_PUBLISHED);
		if(list.size()>0){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) list.get(0);
			allScore = reportInfo.getTotalScore();
			assessUserId = reportInfo.getAssessUserId();
			assessDeptId = reportInfo.getAssessDeptId();
		}
		String pNodeId = pnrEvaTreeMgr.getTreeNode(belongNode).getNodeId();
		String nodeId = "";
		PnrEvaTemplate pnrEvaTemplate = tplMgr.getTemplate(pnrEvaTask.getTemplateId());
		if(!pnrEvaTemplate.getLeaf().equals(PnrEvaConstants.NODE_LEAF)){
			List kpiInstanceList = evaKpiInstanceMgr.getKpiInstanceAndDetail(taskId, timeType, time, partner, PnrEvaConstants.TASK_PUBLISHED);
			PnrEvaKpiInstance  kpiInstance = null;
			PnrEvaTaskDetail taskDetail = null;
			List rowListShow = new ArrayList();
			String areaString = "";
			int rowspan = 1;
			Map areaRowspan = new HashMap();
			for(int i=0;i<kpiInstanceList.size();i++){
				Object[] object = (Object[])kpiInstanceList.get(i);
				kpiInstance = (PnrEvaKpiInstance)object[0];
				taskDetail = (PnrEvaTaskDetail)object[1];
				PnrEvaKpiInstanceForm ekif = new PnrEvaKpiInstanceForm();
				ekif.setId(taskDetail.getId());
				ekif.setTemplateId(taskDetail.getTemplateId());
				ekif.setLeaf(taskDetail.getLeaf());
				ekif.setListNo(taskDetail.getListNo());
				ekif.setNodeId(taskDetail.getNodeId());
				ekif.setParentNodeId(taskDetail.getParentNodeId());
				ekif.setRowspan(taskDetail.getRowspan());
				ekif.setTaskId(taskDetail.getTaskId());	
				ekif.setWeight(taskDetail.getWeight());
				ekif.setColspan(taskDetail.getColspan());
				ekif.setArea(taskDetail.getArea());
				ekif.setRealScore(kpiInstance.getRealScore());
				ekif.setRemark(kpiInstance.getRemark());
				ekif.setMaintenanceRatio(kpiInstance.getMaintenanceRatio());
				// 算法加入
				
				PnrEvaTemplate tpl = tplMgr.getTemplate(ekif.getTemplateId());
				String algorithm = tpl.getSumType();
				if (algorithm == null || "".equals(algorithm)) {
					algorithm = "无";
				}
				ekif.setAlgorithm(algorithm);
				rowListShow.add(ekif);
				if(!areaString.equals(taskDetail.getArea())){
					if(i!=0){
						tableList.add(rowListShow);
						rowListShow = new ArrayList();
						areaRowspan.put(areaString, String.valueOf(rowspan));
						rowspan = 1;
					}
					areaString = taskDetail.getArea();
				}else{
					rowspan++;
				}
			}
			request.setAttribute("areaRowspan", areaRowspan);
		}
		else{
			int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,String.valueOf(i));

			List rowListShow = new ArrayList();
			for (int j = 0; j < rowList.size(); j++) {
				PnrEvaTaskDetail etd = (PnrEvaTaskDetail) rowList.get(j);
				PnrEvaKpiInstanceForm ekif = new PnrEvaKpiInstanceForm();
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
							||PnrEvaConstants.AUDIT_DENY.equals(ekis.getAuditFlag())) {
						ekif.setAuditFlag(PnrEvaConstants.AUDIT_UNDO);
					} else {
						ekif.setAuditFlag(ekis.getAuditFlag());
						isPublishButton = "display:none;";
					}
					assessUserId = ekis.getAssessUserId();
					assessDeptId = ekis.getAssessDeptId();
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
			tableList.add(rowListShow);
			}
		}

		// 找到当前taskId对应的模板name	
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		//如果记录不存在，则给出提示
		if(!(taskId == null || "".equals(taskId))){
			PnrEvaTask et = taskMgr.getTaskById(taskId);
			String taskName = templateMgr.id2Name(et.getTemplateId());
			request.setAttribute("taskName", taskName); // 任务名称
			request.setAttribute("templateId", et.getTemplateId());
		}
		
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("areaId", areaId);
		request.setAttribute("allScore", Float.toString(allScore));
		request.setAttribute("assessUserId", assessUserId);
		request.setAttribute("assessDeptId", assessDeptId);
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
//		request.setAttribute("partnerId", partnerId);//合作伙伴Id
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("time", time); // 时间
		request.setAttribute("isPublishButton", isPublishButton); // 是否发布(控制按钮)
		request.setAttribute("queryType", queryType); // 查询类型，为返回制定
		if(!pnrEvaTemplate.getLeaf().equals(PnrEvaConstants.NODE_LEAF)){
			return mapping.findForward("templateDetailList");
		}
		int titleColspan = (maxNodeIdSize-pNodeId.length())/3;
		request.setAttribute("titleColspan", String.valueOf(titleColspan));
		return mapping.findForward("taskDetailList");
	}

	/**
	 * 打开查看任务报告页面(同一月份不同厂商)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward partnerInfoView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
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
		request.setAttribute("partnerNodeType", partnerNodeType);
		return mapping.findForward("partnerView");
	}

	/**
	 * 页面二级联动，已知模板分类，返回对应模板list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeTemplateTypeOnLeaf(ActionMapping mapping,
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
				if(tempRole.getRoleId() == Integer.parseInt(pnrEvaRoleIdList.getReportShowRoleId())){
					isProvinceAdmin = "Y";
					break;
				}
			}
		}
		//得到登陆人的考核操作角色,用以确定地域信息
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_REPORT_SHOW);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		if(!("").equals(areaId)){
			list = taskMgr.listTaskOfAreaOnTemplateLeaf(areaId,PnrEvaConstants.TEMPLATE_ACTIVATED, templateTypeId);
		}
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			PnrEvaTemplate tpl = (PnrEvaTemplate) iter.next();
			
			PnrEvaTemplate template = templateMgr.getTemplate(tpl.getId());
			String starttime = "";
			if(template.getStartTime()!=null&&!template.getStartTime().equals("")){
				starttime = template.getStartTime().substring(0,10);
			}
			if(isProvinceAdmin.equals("Y")){//考核执行、查询等显示模版名称的时候，根据task来显示的，一个所属部门对应一个task，这样当管理员登陆的时候会看到相同模版名称的记录，在后面加部门名称区分。
				String getOrgType = deptMgr.id2Name(tpl.getOrgType());
				d_list.append("," + tpl.getId());
				d_list.append("," + tempMgr.id2Name(tpl.getId())+"("+getOrgType+starttime+")");
			}
			else {
				d_list.append("," + tpl.getId());
				d_list.append("," + tempMgr.id2Name(tpl.getId())+"("+starttime+")");
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
	 *	根据用户类型找到其权限所能查看到的地域
	 *  
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward partnerListFindArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String templateTypeId = StaticMethod.nullObject2String(request.getParameter("templateTypeId"));
		String timeType = "月度";//后期开发
		String time = year + month ;
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		List areaInfo = taskMgr.listTaskOfTpl(templateId);		
		String taskName = templateMgr.id2Name(templateId) ;
		
		request.setAttribute("taskName", taskName);
		request.setAttribute("templateTypeId", templateTypeId);
		request.setAttribute("timeType", timeType);
		request.setAttribute("time", time);
		request.setAttribute("areaInfo", areaInfo);
		request.setAttribute("templateId", templateId);
		return mapping.findForward("partnerListArea");
	}
	
	/**
	 * 显示某月合作厂商的report列表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward partnerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String timeType = StaticMethod.nullObject2String(request.getParameter("timeType"));
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		if(null == timeType || "".equals(timeType)){
			timeType = "月度";// 后续开发
		}
		String time = StaticMethod.nullObject2String(request.getParameter("time"));
		if(null == time || "".equals(time)){
			time = year+month ;
		}
		if(null == taskId || "".equals(taskId)){
			taskId = taskMgr.getPnrEvaTaskByTemplateIdAndOrgId(templateId, areaId).getId();
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrEvaConstants.AUDITINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		String hSql = "";
		PnrEvaTask  pnrEvaTask = taskMgr.getTaskById(taskId);
		if	(pnrEvaTask.getNodeId() !=null && !"".equals(pnrEvaTask.getNodeId()))
			hSql = hSql + " and belongNode = '"+pnrEvaTask.getNodeId()+"'" ;	
		if	(timeType !=null && !"".equals(timeType))
			hSql = hSql + " and timeType = '"+timeType+"'" ;
		if	(time != null && !"".equals(time))
			hSql = hSql + " and time = '"+time+"'";
		Map map = (Map)evaReportInfoMgr.listReportInfoForPage(pageIndex, pageSize, hSql);
		List list = (List) map.get("result");
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			PnrEvaReportInfo reportInfo = (PnrEvaReportInfo) iterator.next();
			IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
			PnrEvaTask et = taskMgr.getTaskById(taskId);
			String createTime = et.getCreateTime().substring(0,10);
			reportInfo.setTaskName(templateMgr.id2Name(et.getTemplateId())+"("+createTime+")");//任务名称
//			reportInfo.setArea(areaMgr.getAreaByAreaId(areaId).getAreaname());			
			reportInfo.setPartnerName(facturyMgr.getKpiFacturyByFactury(reportInfo.getPartnerId()).getFacturyName());
		}
		request.setAttribute("areaId", areaId);
		request.setAttribute("reportInfoList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("partnerList");
	}
	
	/**
	 * 按年统计报表条件选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReportYearStaticsView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTree et = (PnrEvaTree) list.get(i);
			templateType.add(et);
		}
		request.setAttribute("templateType", templateType);
		return mapping.findForward("reportYearStatics");
	}
	

	/**
	 * 按年,月统计报表条件选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeTemplateTypeForStatics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateTypeId = request.getParameter("templateTypeId");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		//得到该分类下地市对应的模板
		List templateList = templateMgr.getActiveTemplateByExecuteType(templateTypeId, PnrEvaConstants.EXECUTE_TYPE_CITY);
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		PnrEvaTemplate pnrEvaTemplate = null;
		for(int i=0;i<templateList.size();i++){
			pnrEvaTemplate = (PnrEvaTemplate)templateList.get(i);
			d_list.append("," + pnrEvaTemplate.getBelongNode());
			d_list.append("," + templateMgr.id2Name(pnrEvaTemplate.getId()));
		}
		String taskBuffer = d_list.toString();
		taskBuffer = taskBuffer.substring(1, taskBuffer.length());
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(taskBuffer);
		response.getWriter().flush();
		return null;
		
		
		
	}

	/**
	 * 按年,月统计报表条件选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeTemplateTypeForStaticsFj(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateTypeId = request.getParameter("templateTypeId");
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		//得到该分类下地市对应的模板
		List templateList = templateMgr.getNextTemplateByExecuteType(templateTypeId, PnrEvaConstants.EXECUTE_TYPE_PROVINCE);
		StringBuffer d_list = new StringBuffer();
		d_list.append("," + "");
		d_list.append("," + "--请选择模板--");
		PnrEvaTemplate pnrEvaTemplate = null;
		for(int i=0;i<templateList.size();i++){
			pnrEvaTemplate = (PnrEvaTemplate)templateList.get(i);
			d_list.append("," + pnrEvaTemplate.getBelongNode());
			d_list.append("," + templateMgr.id2Name(pnrEvaTemplate.getId()));
		}
		String taskBuffer = d_list.toString();
		taskBuffer = taskBuffer.substring(1, taskBuffer.length());
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(taskBuffer);
		response.getWriter().flush();
		return null;
		
		
		
	}
	/**
	 * 按年统计报表结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReportYearStaticsByTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String belongNode = StaticMethod.nullObject2String(request.getParameter("belongNode"));
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree tree = treeMgr.getTreeNode(belongNode);
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		//得到起止时间
		int startYear,endMonth,endYear;
		String startTime = "";
		String endTime = "";
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改  
		endYear = c.get(Calendar.YEAR);  
		endMonth = c.get(Calendar.MONTH)+1;  
		if(endMonth>6){
			startYear = endYear;
			startTime = startYear + "01";
		}else{
			startYear = endYear-1;
			startTime = startYear + "07";
		}
		if(endMonth>9){
			endTime = endYear +""+ endMonth;
		}else{
			endTime = endYear +"0"+ endMonth;
		}
		List listReports = evaReportInfoMgr.getReportYearStaticsByTime(belongNode,startTime,endTime, PnrEvaConstants.TASK_PUBLISHED);
		//根据地域和合作伙伴信息合并数据
		String area = "";
		String partner = "";
		String excTime = "";
		float totalScore = 0;
		float score = 0;
		float partnerSumScore =0;
		int j=1;
		float sumMonthA=0;
		float sumMonthB=0;
		float sumMonthC=0;
		float sumMonthD=0;
		float sumMonthE=0;
		float sumMonthF=0;
		float sumMonthG=0;
		float sumMonthH=0;
		float sumMonthI=0;
		float sumMonthJ=0;
		float sumMonthK=0;
		float sumMonthL=0;
		int numA=0;
		int numB=0;
		int numC=0;
		int numD=0;
		int numE=0;
		int numF=0;
		int numG=0;
		int numH=0;
		int numI=0;
		int numJ=0;
		int numK=0;
		int numL=0;
		if(listReports.size()>0){
			PnrEvaReportInfo firstReport = (PnrEvaReportInfo)listReports.get(0);
			area = firstReport.getArea();
			partner = firstReport.getPartnerId();
		}
		PnrEvaReportYearStaticFrom pnrEvaReportYearStaticFrom = new PnrEvaReportYearStaticFrom();;
		Map rowMap = new HashMap();
		List fromList = new ArrayList();
		for(int i=0;i<listReports.size();i++,j++){
			PnrEvaReportInfo pnrEvaReportInfo = (PnrEvaReportInfo)listReports.get(i);
			//当地域或合作伙伴变化时list增加一条
			if(!area.equals(pnrEvaReportInfo.getArea())||!partner.equals(pnrEvaReportInfo.getPartnerId())){
				partnerSumScore = partnerSumScore+totalScore/(j-1) ;
				fromList.add(pnrEvaReportYearStaticFrom);
				j=1;
				totalScore=0;
				//当合作伙伴不变时list的合并行数增加一条
				if(partner.equals(pnrEvaReportInfo.getPartnerId())){
					rowMap.put(partner+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partner+"_num"),1)+1));
				//当合作伙伴变化时平均值增加一列
				}else{
					rowMap.put(partner+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partner+"_num"),1)+1));
					PnrEvaReportYearStaticFrom partnerYearStaticFrom = new PnrEvaReportYearStaticFrom();
					partnerYearStaticFrom.setArea("-1");
					partnerYearStaticFrom.setPartnerId(partner);
					partnerYearStaticFrom.setStartTime(startTime);
					partnerYearStaticFrom.setEndTime(endTime);
					partnerYearStaticFrom.setMonthA(sumMonthA/(numA==0?1:numA));
					partnerYearStaticFrom.setMonthB(sumMonthB/(numB==0?1:numB));
					partnerYearStaticFrom.setMonthC(sumMonthC/(numC==0?1:numC));
					partnerYearStaticFrom.setMonthD(sumMonthD/(numD==0?1:numD));
					partnerYearStaticFrom.setMonthE(sumMonthE/(numE==0?1:numE));
					partnerYearStaticFrom.setMonthF(sumMonthF/(numF==0?1:numF));
					partnerYearStaticFrom.setMonthG(sumMonthG/(numG==0?1:numG));
					partnerYearStaticFrom.setMonthH(sumMonthH/(numH==0?1:numH));
					partnerYearStaticFrom.setMonthI(sumMonthI/(numI==0?1:numI));
					partnerYearStaticFrom.setMonthJ(sumMonthJ/(numJ==0?1:numJ));
					partnerYearStaticFrom.setMonthK(sumMonthK/(numK==0?1:numK));
					partnerYearStaticFrom.setMonthL(sumMonthL/(numL==0?1:numL));
					float num= StaticMethod.nullObject2int(rowMap.get(partner+"_num"),1);
					partnerYearStaticFrom.setTotalScore(partnerSumScore/(num-1));
					fromList.add(partnerYearStaticFrom);
					rowMap.put(partner+"_score", String.valueOf(partnerYearStaticFrom.getTotalScore()));
					sumMonthA=0;
					sumMonthB=0;
					sumMonthC=0;
					sumMonthD=0;
					sumMonthE=0;
					sumMonthF=0;
					sumMonthG=0;
					sumMonthH=0;
					sumMonthI=0;
					sumMonthJ=0;
					sumMonthK=0;
					sumMonthL=0;
					partnerSumScore = 0;
					numA=0;
					numB=0;
					numC=0;
					numD=0;
					numE=0;
					numF=0;
					numG=0;
					numH=0;
					numI=0;
					numJ=0;
					numK=0;
					numL=0;
				}
				
				pnrEvaReportYearStaticFrom = new PnrEvaReportYearStaticFrom();
			}

				pnrEvaReportYearStaticFrom.setArea(pnrEvaReportInfo.getArea());
				pnrEvaReportYearStaticFrom.setPartnerId(pnrEvaReportInfo.getPartnerId());
				pnrEvaReportYearStaticFrom.setStartTime(startTime);
				pnrEvaReportYearStaticFrom.setEndTime(endTime);
				excTime = pnrEvaReportInfo.getTime();
				score = pnrEvaReportInfo.getTotalScore();
				switch(Integer.parseInt(excTime)-Integer.parseInt(startTime)) { 
				case 0:pnrEvaReportYearStaticFrom.setMonthA(score);sumMonthA+=score;numA++;break;
				case 1:pnrEvaReportYearStaticFrom.setMonthB(score);sumMonthB+=score;numB++;break;
				case 2:pnrEvaReportYearStaticFrom.setMonthC(score);sumMonthC+=score;numC++;break;
				case 3:pnrEvaReportYearStaticFrom.setMonthD(score);sumMonthD+=score;numD++;break;
				case 4:pnrEvaReportYearStaticFrom.setMonthE(score);sumMonthE+=score;numE++;break;
				case 5:pnrEvaReportYearStaticFrom.setMonthF(score);sumMonthF+=score;numF++;break;
				case 6:pnrEvaReportYearStaticFrom.setMonthG(score);sumMonthG+=score;numG++;break;
				case 7:pnrEvaReportYearStaticFrom.setMonthH(score);sumMonthH+=score;numH++;break;
				case 8:pnrEvaReportYearStaticFrom.setMonthI(score);sumMonthI+=score;numI++;break;
				case 9:pnrEvaReportYearStaticFrom.setMonthJ(score);sumMonthJ+=score;numJ++;break;
				case 10:pnrEvaReportYearStaticFrom.setMonthK(score);sumMonthK+=score;numK++;break;
				case 11:pnrEvaReportYearStaticFrom.setMonthL(score);sumMonthL+=score;numL++;break;
				//跨年统计
				case 94:pnrEvaReportYearStaticFrom.setMonthG(score);sumMonthG+=score;numG++;break;
				case 95:pnrEvaReportYearStaticFrom.setMonthH(score);sumMonthH+=score;numH++;break;
				case 96:pnrEvaReportYearStaticFrom.setMonthI(score);sumMonthI+=score;numI++;break;
				case 97:pnrEvaReportYearStaticFrom.setMonthJ(score);sumMonthJ+=score;numJ++;break;
				case 98:pnrEvaReportYearStaticFrom.setMonthK(score);sumMonthK+=score;numK++;break;
				case 99:pnrEvaReportYearStaticFrom.setMonthL(score);sumMonthL+=score;numL++;break;
				}
				totalScore = totalScore + pnrEvaReportInfo.getTotalScore();
				pnrEvaReportYearStaticFrom.setTotalScore(totalScore/j);
				
			area = pnrEvaReportInfo.getArea();
			partner = pnrEvaReportInfo.getPartnerId();
			//当时最后一条记录的时候
			if(i==listReports.size()-1){
				partnerSumScore = partnerSumScore+totalScore/j ;
				rowMap.put(partner+"_num", String.valueOf(StaticMethod.nullObject2int(rowMap.get(partner+"_num"),1)+1));
				fromList.add(pnrEvaReportYearStaticFrom);
				PnrEvaReportYearStaticFrom partnerYearStaticFrom = new PnrEvaReportYearStaticFrom();
				partnerYearStaticFrom.setArea("-1");
				partnerYearStaticFrom.setPartnerId(partner);
				partnerYearStaticFrom.setStartTime(startTime);
				partnerYearStaticFrom.setEndTime(endTime);
				partnerYearStaticFrom.setMonthA(sumMonthA/(numA==0?1:numA));
				partnerYearStaticFrom.setMonthB(sumMonthB/(numB==0?1:numB));
				partnerYearStaticFrom.setMonthC(sumMonthC/(numC==0?1:numC));
				partnerYearStaticFrom.setMonthD(sumMonthD/(numD==0?1:numD));
				partnerYearStaticFrom.setMonthE(sumMonthE/(numE==0?1:numE));
				partnerYearStaticFrom.setMonthF(sumMonthF/(numF==0?1:numF));
				partnerYearStaticFrom.setMonthG(sumMonthG/(numG==0?1:numG));
				partnerYearStaticFrom.setMonthH(sumMonthH/(numH==0?1:numH));
				partnerYearStaticFrom.setMonthI(sumMonthI/(numI==0?1:numI));
				partnerYearStaticFrom.setMonthJ(sumMonthJ/(numJ==0?1:numJ));
				partnerYearStaticFrom.setMonthK(sumMonthK/(numK==0?1:numK));
				partnerYearStaticFrom.setMonthL(sumMonthL/(numL==0?1:numL));

				float num= StaticMethod.nullObject2int(rowMap.get(partner+"_num"),1);
				partnerYearStaticFrom.setTotalScore(partnerSumScore/(num-1));
				fromList.add(partnerYearStaticFrom);
				rowMap.put(partner+"_score", String.valueOf(partnerYearStaticFrom.getTotalScore()));
			}
		}
		request.setAttribute("title", tree.getNodeName());	
		request.setAttribute("startYear", String.valueOf(startYear));
		request.setAttribute("endYear", String.valueOf(endYear));
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("formList", fromList);
		return mapping.findForward("reportYearStaticsList");
	}
	
	
	
	/**
	 * 按月统计报表条件选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReportMonthStaticsView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			PnrEvaTree et = (PnrEvaTree) list.get(i);
			templateType.add(et);
		}
		request.setAttribute("templateType", templateType);
		return mapping.findForward("reportMonthStatics");
	}
	/**
	 * 按月统计报表结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMonthReportStaticsByTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String belongNode = StaticMethod.nullObject2String(request.getParameter("belongNode"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String area = StaticMethod.nullObject2String(request.getParameter("area"));
		//当地域选项未选择时查询所有地域
		if("-1".equals(area)){
			area="";
		}
		//得到统计的时间标识
		String staticsTime = year+month;
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		IPnrEvaReportInfoMgr evaReportInfoMgr = (IPnrEvaReportInfoMgr) getBean("iPnrEvaReportInfoMgr");
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		//得到各地域和各合作伙伴的总得分
		List listReports = evaReportInfoMgr.getReportsByTime(belongNode, area, staticsTime, PnrEvaConstants.TASK_PUBLISHED);
//   	List listReports = new ArrayList();//屏蔽查询总分,总分通过计算得到
		Map reportMap = new HashMap();//各地域和各合作伙伴的总得分数据
		Map rowMap = new HashMap();//合并行配置数据
		PnrEvaReportInfo reportInfo = null;
		String reportKey = "";
		//取出各地域和各合作伙伴的模板总得分为下面赋值做准备
		for(int i=0;i<listReports.size();i++){
			reportInfo = (PnrEvaReportInfo)listReports.get(i);
			reportKey = reportInfo.getBelongNode()+"_"+reportInfo.getArea()+"_"+reportInfo.getPartnerId();
			reportMap.put(reportKey, String.valueOf(reportInfo.getTotalScore()));
			rowMap.put(reportInfo.getArea()+"_row", String.valueOf(StaticMethod.nullObject2int(rowMap.get(reportInfo.getArea()+"_row"),0)+1));
		}
		//得到所有下一级的模板中地市模板的打分情况
		List listNextReports = evaReportInfoMgr.getReportMouthStaticsByTime(belongNode,area,staticsTime, PnrEvaConstants.TASK_PUBLISHED);
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");		
		PnrEvaTree tree = treeMgr.getTreeNode(belongNode);
		//得到下一级地市模板集合
		List templateList = templateMgr.getNextTemplateByExecuteType(tree.getNodeId(), PnrEvaConstants.EXECUTE_TYPE_CITY);
		
		
		int nodeSize = templateList.size();
		String[] temtitles = new String[nodeSize+1];
		String[] nodeIds =  new String[nodeSize+1];
		nodeIds[0] = tree.getId();//记录积分对应的节点id（总积分）
		temtitles[0] = mgr.id2Name(tree.getObjectId(), "pnrEvaTemplateDao")+"("+tree.getWeight()+"%)";//记录积分对应的节点模板名称（总积分）
		Map map = new HashMap();
		//用节点id标识特定分数在数组的位置
		map.put(tree.getId(), "3");
		PnrEvaTemplate nextTemplate = null;
		PnrEvaReportInfo reportInfo1 = null;
		PnrEvaReportInfo reportInfo2 = null;
		PnrEvaTree tree1 = null;
		PnrEvaTree tree2 = null;
		int j=3;
		PnrEvaTree nextTree = null;
		for(int i=0;i<nodeSize;i++,j++){
			nextTemplate = (PnrEvaTemplate)templateList.get(i);
			nextTree = treeMgr.getTreeNode(nextTemplate.getBelongNode());
			nodeIds[i+1] = nextTemplate.getBelongNode();//记录积分对应的节点id（子积分）
			temtitles[i+1] = nextTemplate.getTemplateName()+"("+nextTree.getWeight()+"%)";//记录积分对应的节点模板名称（子积分）
			map.put(nextTemplate.getBelongNode(), String.valueOf(i+3));
		}
		//呈现数据
		String[] scoreData = new String[j];
		int listReportsSize = listNextReports.size();
		String areaTemp = "";
		String partnerTemp = "";
		List scoreList = new ArrayList();
		int k=2;
		int scoreIndex = 0;
		for(int i=0;i<listReportsSize;i++,k++){
			Object[] item=(Object[])listNextReports.get(i);  
			tree1 = (PnrEvaTree)item[0];
			tree2 = (PnrEvaTree)item[1];
			reportInfo1 = (PnrEvaReportInfo)item[2];
			reportKey = tree1.getId()+"_"+reportInfo1.getArea()+"_"+reportInfo1.getPartnerId();
			//当没有父节点没有执行时设置父节点总分
			if(listReports.size()==0){
				reportMap.put(reportKey, String.valueOf(Float.parseFloat(StaticMethod.nullObject2String(reportMap.get(reportKey),"0"))+(reportInfo1.getTotalScore()*tree2.getWeight()/100)));
			}
			if(!areaTemp.equals(reportInfo1.getArea())||!partnerTemp.equals(reportInfo1.getPartnerId())){
				if(i!=0){
//					if(listReports.size()==0){
						rowMap.put(areaTemp+"_row", String.valueOf(StaticMethod.nullObject2int(rowMap.get(areaTemp+"_row"),0)+1));						
//					}
					scoreList.add(scoreData);
					k = 2;
				}
				scoreData = new String[j];

				areaTemp = reportInfo1.getArea();
				partnerTemp = reportInfo1.getPartnerId();
			}
			scoreData[0] = reportInfo1.getArea();//地域信息
			scoreData[1] = reportInfo1.getPartnerId();//合作伙伴信息
			scoreData[2] = StaticMethod.nullObject2String(reportMap.get(reportKey));//设置总积分
			scoreIndex = StaticMethod.nullObject2int(map.get(reportInfo1.getBelongNode()));
			//根据节点id将积分放入数组对应位置
			scoreData[scoreIndex] = String.valueOf(reportInfo1.getTotalScore());

			if(i==listReportsSize-1){
//				if(listReports.size()==0){
					rowMap.put(reportInfo1.getArea()+"_row", String.valueOf(StaticMethod.nullObject2int(rowMap.get(reportInfo1.getArea()+"_row"),0)+1));
//				}
				scoreList.add(scoreData);
			}
		}
		request.setAttribute("title", tree.getNodeName());
		request.setAttribute("rowMap", rowMap);
		request.setAttribute("listReports", listReports);
		request.setAttribute("temtitles", temtitles);	
		request.setAttribute("scoreList", scoreList);	
		request.setAttribute("staticsTime", staticsTime);
		return mapping.findForward("reportMonthStaticsList");
	}
}

