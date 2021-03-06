package com.boco.eoms.partner.chanEva.webapp.action;

import java.text.DateFormat;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiInstanceMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaReportInfoMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskDetailMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpiInstance;
import com.boco.eoms.partner.chanEva.model.ChanEvaReportInfo;
import com.boco.eoms.partner.chanEva.model.ChanEvaTask;
import com.boco.eoms.partner.chanEva.model.ChanEvaTaskDetail;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaKpiInstanceForm;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaReportMultiDeptForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;

import java.net.URLDecoder;

public final class ChanEvaReportInfoAction extends BaseAction {
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// ChanEvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ChanEvaTree et = (ChanEvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商
		String userid = sessionform.getUserid();
		List factoryList = new ArrayList();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
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
		IChanEvaTaskDetailMgr taskDetailMgr = (IChanEvaTaskDetailMgr) getBean("IchanEvaTaskDetailMgr");
		IChanEvaKpiInstanceMgr chanEvaKpiInstanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		IChanEvaKpiMgr chanEvaKpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
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
				ChanEvaTaskDetail etd = (ChanEvaTaskDetail) rowList.get(j);
				ChanEvaKpiInstanceForm ekif = new ChanEvaKpiInstanceForm();
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
				if (ChanEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					ChanEvaKpiInstance ekis = chanEvaKpiInstanceMgr
							.getKpiInstanceByTimeAndPartner(etd.getId(),
									timeType, time, partner);
					if (ekis.getIsPublish() != null
							&& !"".equals(ekis.getIsPublish())
							&& ekis.getIsPublish().equals(
									ChanEvaConstants.TASK_PUBLISHED)) {
						ekif.setRealScore(ekis.getRealScore());
						ekif.setReduceReason(ekis.getReduceReason());
						ekif.setRemark(ekis.getRemark());
					}
				}
				// 算法加入
				ChanEvaKpi ek = chanEvaKpiMgr.getKpi(ekif.getKpiId());
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称

		// 找到任务权重总分和实际总得分：
		IChanEvaReportInfoMgr reportInfoMgr = (IChanEvaReportInfoMgr) getBean("IchanEvaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType
				+ "' and eri.partnerId = '" + partner + "'";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = "";
		String totalScore = "";
		if (!reportInfoList.isEmpty()) {
			ChanEvaReportInfo eri = (ChanEvaReportInfo) reportInfoList.get(0);
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// ChanEvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ChanEvaTree et = (ChanEvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商		
		String userid = sessionform.getUserid();
		List factoryList = new ArrayList();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
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
		IChanEvaTaskDetailMgr taskDetailMgr = (IChanEvaTaskDetailMgr) getBean("IchanEvaTaskDetailMgr");
		IChanEvaKpiInstanceMgr chanEvaKpiInstanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
		IChanEvaKpiMgr chanEvaKpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
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
				ChanEvaTaskDetail etd = (ChanEvaTaskDetail) rowList.get(j);
				ChanEvaKpiInstanceForm ekif = new ChanEvaKpiInstanceForm();
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
				if (ChanEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
					// 选择月份值放入LIST
					List multiScore = new ArrayList();
					for (int m = 0; m < monthList.size(); m++) {
						String month = monthList.get(m).toString();
						String time = year1 + month;

						ChanEvaKpiInstance ekis = chanEvaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, partner);
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())
								&& ekis.getIsPublish().equals(
										ChanEvaConstants.TASK_PUBLISHED)) {
							multiScore.add(ekis.getRealScore());
						} else {
							multiScore.add("");
						}
					}
					ekif.setMultiScore(multiScore);
				}
				// 算法加入
				ChanEvaKpi ek = chanEvaKpiMgr.getKpi(ekif.getKpiId());
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTask et = taskMgr.getTaskById(taskId);
		String taskName = templateMgr.id2Name(et.getTemplateId());
		request.setAttribute("taskName", taskName); // 任务名称

		// 找到任务权重总分和实际总得分：
		Map monthTotal = new TreeMap();
		for (int m = 0; m < monthList.size(); m++) {
			String month = monthList.get(m).toString();
			String time = year1 + month;
			IChanEvaReportInfoMgr reportInfoMgr = (IChanEvaReportInfoMgr) getBean("IchanEvaReportInfoMgr");
			String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
					+ time + "' and eri.timeType = '" + timeType
					+ "' and eri.partnerId = '" + partner + "'";
			List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
			String totalWeight = "";
			String totalScore = "";
			if (!reportInfoList.isEmpty()) {
				ChanEvaReportInfo eri = (ChanEvaReportInfo) reportInfoList.get(0);
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		// // 已激活的任务列表
		// List taskList = taskMgr.listTaskOfOrg(sessionform.getDeptid(),
		// ChanEvaConstants.TEMPLATE_ACTIVATED);
		// request.setAttribute("taskList", taskList);

		// 改为2级联动，第一级为模板分类，第二级为模板，初始化的时候先显示第1级-王思轩
		List list = treeMgr.listChildNodes("", "TEMPLATETYPE", "0");
		List templateType = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ChanEvaTree et = (ChanEvaTree) list.get(i);
			templateType.add(et);
		}

		// 合作伙伴信息,先通过部门NAME找到nodeid，然后找到对应的厂商		
		String userid = sessionform.getUserid();
		List factoryList = new ArrayList();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		
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
    	deptnames += ")";
			
		String sql = "from AreaDeptTree tree where 1=1 and tree.nodeName in "
			+ deptnames + " and tree.nodeType in ('province','area')";
	    if(userid.equals("admin")){
		   sql = "from AreaDeptTree tree where 1=1 and tree.nodeType in ('province','area')";
	    }
		
		List areaDeptList = areaDeptTreeMgr.getInfoByCondition(sql);
		if (!areaDeptList.isEmpty()) {
			AreaDeptTree adt = (AreaDeptTree) areaDeptList.get(0);
			String hql = "select distinct(tree.nodeName) from AreaDeptTree tree where 1=1 and tree.parentNodeId like '"
					+ adt.getNodeId() + "%' and tree.nodeType ='factory'";
			factoryList = areaDeptTreeMgr.getInfoByCondition(hql);
		}
		request.setAttribute("factoryList", factoryList);

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
		IChanEvaTaskDetailMgr taskDetailMgr = (IChanEvaTaskDetailMgr) getBean("IchanEvaTaskDetailMgr");
		IChanEvaKpiInstanceMgr chanEvaKpiInstanceMgr = (IChanEvaKpiInstanceMgr) getBean("IchanEvaKpiInstanceMgr");
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
		IChanEvaReportInfoMgr reportInfoMgr = (IChanEvaReportInfoMgr) getBean("IchanEvaReportInfoMgr");
		String sql = " and eri.taskId = '" + taskId + "' and eri.time = '"
				+ time + "' and eri.timeType = '" + timeType + "'";
		// sql += " and eri.partnerId = '" + partner + "'";//地市ID，预留
		sql += " order by eri.partnerId";
		List reportInfoList = reportInfoMgr.getReportInfoByCondition(sql);
		String totalWeight = ""; // 权重总分
		Map kpiWeights = new TreeMap();// 指标ID,权重
		for (int m = 0; m < reportInfoList.size(); m++) {
			ChanEvaReportInfo eri = (ChanEvaReportInfo) reportInfoList.get(m);
			if (totalWeight == null || "".equals(totalWeight)) {
				totalWeight = eri.getTotalWeight();
			}
			ChanEvaReportMultiDeptForm ermdf = new ChanEvaReportMultiDeptForm();
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
					ChanEvaTaskDetail etd = (ChanEvaTaskDetail) rowList.get(j);
					if (ChanEvaConstants.NODE_LEAF.equals(etd.getLeaf())) {
						ChanEvaKpiInstance ekis = chanEvaKpiInstanceMgr
								.getKpiInstanceByTimeAndPartner(etd.getId(),
										timeType, time, eri.getPartnerId());
						if (ekis.getIsPublish() != null
								&& !"".equals(ekis.getIsPublish())
								&& ekis.getIsPublish().equals(
										ChanEvaConstants.TASK_PUBLISHED)) {
							kpiList.add(ekis.getRealScore());
						} else {
							kpiList.add("");
						}

						// 将对应指标ID-先转换成指标NAME,权重存入kpiWeights中，
						IChanEvaKpiMgr chanEvaKpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
						kpiWeights.put(chanEvaKpiMgr.id2Name(etd.getKpiId()), etd
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
		IChanEvaTaskMgr taskMgr = (IChanEvaTaskMgr) getBean("IchanEvaTaskMgr");
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
		ChanEvaTask et = taskMgr.getTaskById(taskId);
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
}
