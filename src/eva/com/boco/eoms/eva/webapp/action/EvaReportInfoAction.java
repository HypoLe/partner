package com.boco.eoms.eva.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.mgr.IEvaKpiInstanceMgr;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskDetailMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaKpiInstance;
import com.boco.eoms.eva.model.EvaReportInfo;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTaskDetail;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.webapp.form.EvaKpiForm;
import com.boco.eoms.eva.webapp.form.EvaKpiInstanceForm;
import com.boco.eoms.eva.webapp.form.EvaReportMultiDeptForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.FusionChart;
import com.boco.eoms.partner.baseinfo.model.FusionChartData;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.FusionChartMethod;

public final class EvaReportInfoAction extends BaseAction {
	/**
	 * 单一月份、单一厂商查询初始
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryInitSingle(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		return mapping.findForward("querySingle");
	}

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
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));

		String queryType = StaticMethod.null2String(request
				.getParameter("queryType"));
		if (queryType == null || "".equals(queryType))
			queryType = "run";
		String timeType = "月度";// 后续开发
		String time = year + month;
		if (time == null || "".equals(time)) {
			time = StaticMethod.null2String(request.getParameter("time"));
		}
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
					String.valueOf(i));

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
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())
							&& ekis.getIsPublish().equals(
									EvaConstants.TASK_PUBLISHED)) {
						ekif.setRealScore(ekis.getRealScore());
						ekif.setReduceReason(ekis.getReduceReason());
						ekif.setRemark(ekis.getRemark());
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

		// 找到任务权重总分和实际总得分：
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType
				+ "' and eri.partnerId = '" + partner + "'";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = "";
		String totalScore = "";
		if (!reportInfoList.isEmpty()) {
			EvaReportInfo eri = (EvaReportInfo) reportInfoList.get(0);
			totalWeight = eri.getTotalWeight();
			totalScore = eri.getTotalScore();
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
			sql = "from AreaDeptTree tree where 1=1 and tree.nodeType = 'factory' order by tree.nodeId";
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
		return mapping.findForward("queryMultiMonth");
	}

	/**
	 * 不同月份、同一厂商查询
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
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		List tableList = new ArrayList();

		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String partner = StaticMethod.null2String(request
				.getParameter("partner"));
		String year1 = StaticMethod.null2String(request.getParameter("year1"));
		String month1 = StaticMethod
				.null2String(request.getParameter("month1"));
		String month2 = StaticMethod
				.null2String(request.getParameter("month2"));
		if (month1 == null || "".equals(month1)) {
			month1 = "01";
		}
		if (month2 == null || "".equals(month2)) {
			month2 = "12";
		}
		int month1Int = Integer.parseInt(month1);
		int month2Int = Integer.parseInt(month2);
		String timeType = "月度";// 后续开发

		// 记录选择的月份，月份小于10则前面加个0，如1月显示01
		List monthList = new ArrayList();
		for (int n = month1Int; n <= month2Int - month1Int + 1; n++) {
			String month = n + "";
			if (n < 10)
				month = "0" + month;
			monthList.add(month);
		}

		// 查询总LIST，记录信息
		String queryType = StaticMethod.null2String(request
				.getParameter("queryType"));
		if (queryType == null || "".equals(queryType))
			queryType = "run";
		int maxLevel = 0;
		int maxListNo = taskDetailMgr.getMaxListNoOfTask(taskId);
		for (int i = 1; i <= maxListNo; i++) {
			List rowList = taskDetailMgr.listDetailOfTaskByListNo(taskId,
					String.valueOf(i));

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
				// ekif.setMultiScore(new ArrayList());
				if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					// 选择月份值放入LIST
					List multiScore = new ArrayList();
					for (int m = 0; m < monthList.size(); m++) {
						String month = monthList.get(m).toString();
						String time = year1 + month;

						EvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, partner);
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())
								&& ekis.getIsPublish().equals(
										EvaConstants.TASK_PUBLISHED)) {
							multiScore.add(ekis.getRealScore());
						} else {
							multiScore.add("");
						}
					}
					ekif.setMultiScore(multiScore);
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

		// 找到任务权重总分和实际总得分：
		Map monthTotal = new TreeMap();
		for (int m = 0; m < monthList.size(); m++) {
			String month = monthList.get(m).toString();
			String time = year1 + month;
			IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
			String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
					+ time + "' and eri.timeType = '" + timeType
					+ "' and eri.partnerId = '" + partner + "'";
			List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
			String totalWeight = "";
			String totalScore = "";
			if (!reportInfoList.isEmpty()) {
				EvaReportInfo eri = (EvaReportInfo) reportInfoList.get(0);
				if (totalWeight == null || "".equals(totalWeight)) {
					totalWeight = eri.getTotalWeight();
					request.setAttribute("totalWeight", totalWeight);// 模板指标权重总分
				}
				totalScore = eri.getTotalScore();
			}
			monthTotal.put(month, totalScore);// 月份 和 月份总分
		}
		request.setAttribute("monthTotal", monthTotal); // 模板指标得分总分
		request.setAttribute("tableList", tableList); // 详细列表数据
		request.setAttribute("maxLevel", String.valueOf(maxLevel));
		request.setAttribute("taskId", taskId); // 任务ID
		request.setAttribute("partner", partner); // 合作伙伴信息
		request.setAttribute("timeType", timeType); // 时间类型
		request.setAttribute("year", year1); // 时间年
		request.setAttribute("month1", month1); // 时间月份1
		request.setAttribute("month2", month2); // 时间月份2
		return mapping.findForward("reportMultiMonth");
	}

	/**
	 * 同一月份、不同厂商查询初始
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryInitMultiDept(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			sql = "from AreaDeptTree tree where 1=1 and tree.nodeType = 'factory' order by tree.nodeId";
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
		return mapping.findForward("queryMultiDept");
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
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List tableList = new ArrayList();
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		// String partner =
		// StaticMethod.null2String(request.getParameter("partner"));//地市ID，预留
		String partner = "";
		String year = StaticMethod.null2String(request.getParameter("year"));
		String month = StaticMethod.null2String(request.getParameter("month"));

		String timeType = "月度";// 后续开发
		String time = year + month;

		// 先根据月份，任务从reportInfo表中查询分数：
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType + "'";
		// sql += " and eri.partnerId = '" + partner + "'";//地市ID，预留
		sql += " order by eri.partnerId";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = ""; // 权重总分
		Map kpiWeights = new TreeMap();// 指标ID,权重
		for (int m = 0; m < reportInfoList.size(); m++) {
			EvaReportInfo eri = (EvaReportInfo) reportInfoList.get(m);
			if (totalWeight == null || "".equals(totalWeight)) {
				totalWeight = eri.getTotalWeight();
			}
			EvaReportMultiDeptForm ermdf = new EvaReportMultiDeptForm();
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
					EvaTaskDetail etd = (EvaTaskDetail) rowList.get(j);
					if (EvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						EvaKpiInstance ekis = evaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, eri.getPartnerId());
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())
								&& ekis.getIsPublish().equals(
										EvaConstants.TASK_PUBLISHED)) {
							kpiList.add(ekis.getRealScore());
						} else {
							kpiList.add("");
						}

						// 将对应指标ID-先转换成指标NAME,权重存入kpiWeights中，
						IEvaKpiMgr evaKpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
						kpiWeights.put(evaKpiMgr.id2Name(etd.getKpiId()), etd
								.getWeight());
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
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTask et = taskMgr.getTaskById(taskId);
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
	
	/**
	 * 得到某个模板的所有任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskForTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		String localTime = StaticMethod.getLocalString();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		EvaTree tplNode = null;
		//从流程图点击过来的链接
		String agreeId = StaticMethod.nullObject2String(request.getParameter("agreeId"));
		/*if(!"".equals(agreeId)){
			IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(agreeId);
			tplNode = treeMgr.getNodeByObjId("", pnrAgreementMain.getEvaTemplateId());
			nodeId = tplNode.getNodeId();
		}else{
			tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		}*/
		if(tplNode.getObjectId() == null){
			return mapping.findForward("queryHistroy");
		}
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), deptid);
		String taskId = task.getId();
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		List AllReport = reportInfoMgr.getReportInfoByTaskId(taskId);
		List reportList = new ArrayList();
		EvaReportInfo reportInfo = null;
		String allTimeStr = "";
		String timeStr = "";
		for(int i=0;i<AllReport.size();i++){
			reportInfo = (EvaReportInfo)AllReport.get(i);
			//屏蔽未到期的考核按钮
			timeStr = reportInfo.getEvaTime()+" 00:00:00";
			if(StaticMethod.getTimeDistance(timeStr, localTime)>0){
				reportList.add(reportInfo);
				allTimeStr += reportInfo.getEvaTime()+",";
			}
		}
		//得到当前时间可以执行的所有周期
//		List allCycleList = DateTimeUtil.getAllStrByTimesForEva(template.getCycle(),template.getStartTime(),localTime);
		//增加超期部分的执行任务
		/*
		String timeStr ="" ;
		int reportListSize = reportList.size();
		for(int i=0;i<allCycleList.size();i++){
			timeStr = String.valueOf(allCycleList.get(i));
			//当执行任务没在report表里时增加执行项
			if(allTimeStr.indexOf(timeStr)<0){
				EvaReportInfo report = new EvaReportInfo();
				report.setTime(String.valueOf(i+1));
				report.setTaskId(taskId);
				report.setIsPublish(EvaConstants.TASK_NOT_PUBLISHED);
				reportList.add(report);
			}
		}
		*/
		List kpiList = evaKpiInstanceMgr.getKpiInstanceListByTask(taskId);
		List detailList =  taskDetailMgr.listDetailOfTaskByListNo(taskId, "");
		EvaTaskDetail taskDetail = null;
		Map kpiDetailMap = new HashMap();
		for(int i=0;i<detailList.size();i++){
			taskDetail = (EvaTaskDetail)detailList.get(i);
			
			kpiDetailMap.put(taskDetail.getId(), taskDetail.getNodeId());
		}
		Map kpiMap = new HashMap();
		EvaKpiInstance kpiInstance = null;
		String key ="";
		for(int i=0;i<kpiList.size();i++){
			kpiInstance = (EvaKpiInstance)kpiList.get(i);
			key = kpiInstance.getTime()+"_"+StaticMethod.nullObject2String(kpiDetailMap.get(kpiInstance.getTaskDetailId()));
			kpiMap.put(key, kpiInstance.getRealScore());
		}
		

		List kpiNodeList = treeMgr.listNextLevelNode(nodeId,
				EvaConstants.NODETYPE_KPI);
		request.setAttribute("task", task); 
		request.setAttribute("kpiNodeList", kpiNodeList); 
		request.setAttribute("template", template); 
		request.setAttribute("kpiMap", kpiMap);
		request.setAttribute("detailList", detailList);
//		request.setAttribute("allCycleList", allCycleList);
		request.setAttribute("reportList", reportList);
		return mapping.findForward("getTasks");
	}
	/**
	 * 得到某个模板的执行信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		String localTime = StaticMethod.getLocalString();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), deptid);
		String taskId = task.getId();
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		List reportList = reportInfoMgr.getReportInfoByTaskId(taskId);
		EvaReportInfo reportInfo = null;
		String allTimeStr = "";
		for(int i=0;i<reportList.size();i++){
			reportInfo = (EvaReportInfo)reportList.get(i);
			allTimeStr += reportInfo.getTime()+",";
		}
		//得到开始时间到当前时间的所有周期
//		List allCycleList = DateTimeUtil.getAllStrByTimes(template.getCycle(),template.getStartTime(),localTime);
		//增加超期部分的执行任务
		/*
		String timeStr ="" ;
		int reportListSize = reportList.size();
		for(int i=0;i<allCycleList.size();i++){
			timeStr = String.valueOf(allCycleList.get(i));
			//当执行任务没在report表里时增加执行项
			if(allTimeStr.indexOf(timeStr)<0){
				EvaReportInfo report = new EvaReportInfo();
				report.setTime(timeStr);
				report.setTaskId(taskId);
				report.setIsPublish(EvaConstants.TASK_NOT_PUBLISHED);
				reportList.add(report);
			}
		}
		*/
		request.setAttribute("task", task); 
		request.setAttribute("template", template); 
		request.setAttribute("reportList", reportList); 
		return mapping.findForward("closedTask");
	}
	/**
	 * 得到某个模板的所有任务(history)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskForTemplateByHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		String localTime = StaticMethod.getLocalString();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));

		EvaTree tplNode = null;
		if(!"".equals(templateId)){
			tplNode  = treeMgr.getTreeNode(templateId);
		}else{
			tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		}
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), deptid);
		String taskId = task.getId();
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		List reportList = reportInfoMgr.getReportInfoByTaskId(taskId);
		EvaReportInfo reportInfo = null;
		String allTimeStr = "";
		for(int i=0;i<reportList.size();i++){
			reportInfo = (EvaReportInfo)reportList.get(i);
			allTimeStr += reportInfo.getTime()+",";
		}
		//得到开始时间到当前时间的所有周期
//		List allCycleList = DateTimeUtil.getAllStrByTimes(template.getCycle(),template.getStartTime(),localTime);
		//增加超期部分的执行任务
		/*
		String timeStr ="" ;
		int reportListSize = reportList.size();
		for(int i=0;i<allCycleList.size();i++){
			timeStr = String.valueOf(allCycleList.get(i));
			//当执行任务没在report表里时增加执行项
			if(allTimeStr.indexOf(timeStr)<0){
				EvaReportInfo report = new EvaReportInfo();
				report.setTime(timeStr);
				report.setTaskId(taskId);
				report.setIsPublish(EvaConstants.TASK_NOT_PUBLISHED);
				reportList.add(report);
			}
		}
		*/
		List kpiList = evaKpiInstanceMgr.getKpiInstanceListByTask(taskId);
		List detailList =  taskDetailMgr.listDetailOfTaskByListNo(taskId, "");
		EvaTaskDetail taskDetail = null;
		Map kpiDetailMap = new HashMap();
		for(int i=0;i<detailList.size();i++){
			taskDetail = (EvaTaskDetail)detailList.get(i);
			
			kpiDetailMap.put(taskDetail.getId(), taskDetail.getNodeId());
		}
		Map kpiMap = new HashMap();
		EvaKpiInstance kpiInstance = null;
		String key ="";
		for(int i=0;i<kpiList.size();i++){
			kpiInstance = (EvaKpiInstance)kpiList.get(i);
			key = kpiInstance.getTime()+"_"+StaticMethod.nullObject2String(kpiDetailMap.get(kpiInstance.getTaskDetailId()));
			kpiMap.put(key, kpiInstance.getRealScore());
		}
		List kpiNodeList = null;
		if(!"".equals(nodeId)){
			kpiNodeList = treeMgr.listNextLevelNode(nodeId,EvaConstants.NODETYPE_KPI);
		} else {
			kpiNodeList = treeMgr.listNextLevelNode(tplNode.getNodeId(),EvaConstants.NODETYPE_KPI);
		}
		
		request.setAttribute("task", task); 
		request.setAttribute("kpiNodeList", kpiNodeList); 
		request.setAttribute("template", template); 
		request.setAttribute("kpiMap", kpiMap);
		request.setAttribute("detailList", detailList);
//		request.setAttribute("allCycleList", allCycleList);
		request.setAttribute("reportList", reportList);
		return mapping.findForward("getTasksHis");
	}

	/**
	 * 得到某个模板的所有任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllScroe(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
//		点击时如果是指标节点则查询该指标详细信息
		if(EvaConstants.NODETYPE_KPI.equals(tplNode.getNodeType())){
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");

			String kpiId = StaticMethod.null2String(tplNode.getObjectId());
			EvaKpi kpi = new EvaKpi();
			Float maxWt = EvaConstants.DEFAULT_MAX_WT;
			Float minWt = EvaConstants.DEFAULT_MIN_WT;
			kpi = kpiMgr.getKpi(kpiId,"0");
			kpi.setWeight(tplNode.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			minWt = kpiMgr.getMinWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			EvaKpiForm evaKpiForm = (EvaKpiForm) convert(kpi);
			updateFormBean(mapping, request, evaKpiForm);
			request.setAttribute("evaKpiForm", evaKpiForm);
			return mapping.findForward("templateKpi");
		}
		
		if(EvaConstants.NODETYPE_TEMPLATETYPE.equals(tplNode.getNodeType())){
			return mapping.findForward("queryHistroy");
		}
		
		if("".equals(nodeId)||nodeId==null){
			EvaTemplate template = templateMgr.getTemplate(templateId);
			String belongNode = template.getBelongNode();
			tplNode = treeMgr.getTreeNode(belongNode);
			nodeId = tplNode.getNodeId();
		}	
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		if(template.getActivated().equals(EvaConstants.TASK_NOT_PUBLISHED)){
			return mapping.findForward("notPublish");
		}
		EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), ""); 
		String taskId = task.getId();
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		//得到开始时间到当前时间的所有周期
		String localTime = StaticMethod.getLocalString();
		String endTime = template.getEndTime();
		//当前时间大于计划结束时间时
		/*
		if(StaticMethod.getTimeDistance(endTime,localTime)>0){
			endTime = localTime;
		}
		*/
		List allCycleList = DateTimeUtil.getAllStrByTimes(template.getCycle(),template.getStartTime(),endTime);
		//增加超期部分的执行任务
		String timeStr ="" ;

		List kpiList = evaKpiInstanceMgr.getKpiInstanceListByTask(taskId);
		List detailList =  taskDetailMgr.listDetailOfTaskByListNo(taskId, "");
		EvaTaskDetail taskDetail = null;
		Map kpiDetailMap = new HashMap();
		for(int i=0;i<detailList.size();i++){
			taskDetail = (EvaTaskDetail)detailList.get(i);
			
			kpiDetailMap.put(taskDetail.getId(), taskDetail.getNodeId());
		}
		Map kpiMap = new HashMap();
		EvaKpiInstance kpiInstance = null;
		String key ="";
		String sumKey ="";
		for(int i=0;i<kpiList.size();i++){
			kpiInstance = (EvaKpiInstance)kpiList.get(i);
			if(kpiInstance.getIsPublish().equals(EvaConstants.TASK_PUBLISHED)){
			key = kpiInstance.getTime()+"_"+StaticMethod.nullObject2String(kpiDetailMap.get(kpiInstance.getTaskDetailId()));
			sumKey = kpiInstance.getTime();
			kpiMap.put(key, kpiInstance.getRealScore());
			kpiMap.put(sumKey, String.valueOf(Float.parseFloat(StaticMethod.nullObject2String(kpiMap.get(sumKey), "0"))+Float.parseFloat(kpiInstance.getRealScore())));
			}
		}
//		FusionChartData fusionChartData = null;
//		List dataList = new ArrayList();
//		String sum = "";
//		String timeStrName = "";
//		String url ="";
//		for(int i=0;i<allCycleList.size();i++){
//			sum = StaticMethod.nullObject2String(kpiMap.get(String.valueOf(allCycleList.get(i))));
//			timeStrName = DateTimeUtil.getShowTimeStrByTime(String.valueOf(allCycleList.get(i)));
//			fusionChartData = new FusionChartData();
//			fusionChartData.setNumForFloat(StaticMethod.getFloatValue(sum, 0));
//			fusionChartData.setTitle(timeStrName);
//			url = "n-"+request.getContextPath()+"/eva/evaTasks.do?method=getTaskDetailList&taskId="+taskId+"&timeStr="+allCycleList.get(i)+"&partnerId="+template.getPartnerDept();			
//			fusionChartData.setUrl(url);
//			dataList.add(fusionChartData);
//		}	
//		
//		FusionChart fusionChart = new FusionChart();
//		fusionChart.setCaption("考核执行情况");
//		if("month".equals(template.getCycle())){
//			fusionChart.setLabelDisplay("Rotate");
//			fusionChart.setSlantLabels("1");
//		}
//		String strXML = FusionChartMethod.getString(dataList, fusionChart);
		
		List kpiNodeList = treeMgr.listNextLevelNode(nodeId,EvaConstants.NODETYPE_KPI);
//		request.setAttribute("strXML", strXML);
		request.setAttribute("task", task); 
		request.setAttribute("kpiNodeList", kpiNodeList); 
		request.setAttribute("template", template); 
		request.setAttribute("kpiMap", kpiMap);
		request.setAttribute("detailList", detailList);
		request.setAttribute("allCycleList", allCycleList);
		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("getAllScore");
	}

	/**
	 * 得到某个模板的所有任务(图形)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllScroeView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiInstanceMgr evaKpiInstanceMgr = (IEvaKpiInstanceMgr) getBean("IevaKpiInstanceMgr");
		IEvaTaskDetailMgr taskDetailMgr = (IEvaTaskDetailMgr) getBean("IevaTaskDetailMgr");
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		EvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
//		点击时如果是指标节点则查询该指标详细信息
		if(EvaConstants.NODETYPE_KPI.equals(tplNode.getNodeType())){
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");

			String kpiId = StaticMethod.null2String(tplNode.getObjectId());
			EvaKpi kpi = new EvaKpi();
			Float maxWt = EvaConstants.DEFAULT_MAX_WT;
			Float minWt = EvaConstants.DEFAULT_MIN_WT;
			kpi = kpiMgr.getKpi(kpiId,"0");
			kpi.setWeight(tplNode.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			minWt = kpiMgr.getMinWt(tplNode.getParentNodeId(), tplNode.getObjectId());
			EvaKpiForm evaKpiForm = (EvaKpiForm) convert(kpi);
//			evaKpiForm.setEvaStartTime(kpi.getEvaStartTimeStr());
//			evaKpiForm.setEvaEndTime(kpi.getEvaEndTimeStr());
			updateFormBean(mapping, request, evaKpiForm);
			request.setAttribute("evaKpiForm", evaKpiForm);
			return mapping.findForward("templateKpi");
		}
		
		if(EvaConstants.NODETYPE_TEMPLATETYPE.equals(tplNode.getNodeType())){
			return mapping.findForward("queryHistroy");
		}
		
		if("".equals(nodeId)||nodeId==null){
			EvaTemplate template = templateMgr.getTemplate(templateId);
			String belongNode = template.getBelongNode();
			tplNode = treeMgr.getTreeNode(belongNode);
			nodeId = tplNode.getNodeId();
		}	
		EvaTemplate template = templateMgr.getTemplate(tplNode.getObjectId());
		if(template.getActivated().equals(EvaConstants.TASK_NOT_PUBLISHED)){
			return mapping.findForward("notPublish");
		}
		EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), ""); 
		String taskId = task.getId();
		IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
		//得到开始时间到当前时间的所有周期
		String localTime = StaticMethod.getLocalString();
		String endTime = template.getEndTime();
		//当前时间大于计划结束时间时
		/*
		if(StaticMethod.getTimeDistance(endTime,localTime)>0){
			endTime = localTime;
		}*/
		List allCycleList = DateTimeUtil.getAllStrByTimes(template.getCycle(),template.getStartTime(),endTime);
		//增加超期部分的执行任务
		String timeStr ="" ;

		List kpiList = evaKpiInstanceMgr.getKpiInstanceListByTask(taskId);
//		List detailList =  taskDetailMgr.listDetailOfTaskByListNo(taskId, "");
//		EvaTaskDetail taskDetail = null;
//		Map kpiDetailMap = new HashMap();
//		for(int i=0;i<detailList.size();i++){
//			taskDetail = (EvaTaskDetail)detailList.get(i);
//			
//			kpiDetailMap.put(taskDetail.getId(), taskDetail.getNodeId());
//		}
		Map kpiMap = new HashMap();
		EvaKpiInstance kpiInstance = null;
		String key ="";
		String sumKey ="";
		for(int i=0;i<kpiList.size();i++){
			kpiInstance = (EvaKpiInstance)kpiList.get(i);
			if(kpiInstance.getIsPublish().equals(EvaConstants.TASK_PUBLISHED)){
//			key = kpiInstance.getTime()+"_"+StaticMethod.nullObject2String(kpiDetailMap.get(kpiInstance.getTaskDetailId()));
			sumKey = kpiInstance.getTime();
//			kpiMap.put(key, kpiInstance.getRealScore());  
			kpiMap.put(sumKey, String.valueOf(Float.parseFloat(StaticMethod.nullObject2String(kpiMap.get(sumKey), "0"))+Float.parseFloat(kpiInstance.getRealScore())));
			}
		}
//		图形显示
		FusionChartData fusionChartData = null;
		List dataList = new ArrayList();
		String sum = "";
		String timeStrName = "";
		String url ="";
		for(int i=0;i<allCycleList.size();i++){
			sum = StaticMethod.nullObject2String(kpiMap.get(String.valueOf(i+1)));
			timeStrName = "NO."+(i+1);
			fusionChartData = new FusionChartData();
			float floatnum= StaticMethod.getFloatValue(sum, 0);
			fusionChartData.setNumForFloat(StaticMethod.getFloatValue(sum, 0));
			fusionChartData.setTitle(timeStrName);
			if(!"".equals(sum)){
				url = "n-"+request.getContextPath()+"/eva/evaTasks.do?method=getTaskDetailList&taskId="+taskId+"&time="+(i+1)+"&partnerId="+template.getPartnerDept();
				fusionChartData.setUrl(url);
			} else {
				if(EvaConstants.TEMPLATE_ACTIVATED.equals(template.getActivated())){
					url = "n-"+request.getContextPath()+"/eva/evaTasks.do?method=getTaskDetailList&taskId="+taskId+"&time="+(i+1)+"&partnerId="+template.getPartnerDept();
					fusionChartData.setUrl(url);
				}
			}
			dataList.add(fusionChartData);
		}	
		FusionChart fusionChart = new FusionChart();
		if(EvaConstants.TEMPLATE_CLOSED.equals(template.getActivated())){
			fusionChart.setCaption("考核执行情况(此考核已关闭)");
		} else if (EvaConstants.TEMPLATE_ACTIVATED.equals(template.getActivated())){
			fusionChart.setCaption("考核执行情况(此考核正在执行)");
		}
//		if("month".equals(template.getCycle())){//只有显示月考核斜体
			fusionChart.setLabelDisplay("Rotate");
			fusionChart.setSlantLabels("1");
//		}
		String strXML = FusionChartMethod.getString(dataList, fusionChart);
		
//		List kpiNodeList = treeMgr.listNextLevelNode(nodeId,EvaConstants.NODETYPE_KPI);
		request.setAttribute("strXML", strXML);
		request.setAttribute("view", "view");
//		request.setAttribute("task", task); 
//		request.setAttribute("kpiNodeList", kpiNodeList); 
//		request.setAttribute("template", template); 
//		request.setAttribute("kpiMap", kpiMap);
//		request.setAttribute("detailList", detailList);
//		request.setAttribute("allCycleList", allCycleList);
//		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("getAllScore");
	}	
}
