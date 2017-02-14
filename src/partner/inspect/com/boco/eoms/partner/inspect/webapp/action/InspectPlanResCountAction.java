package com.boco.eoms.partner.inspect.webapp.action;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.IAreaMgr;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisPnrMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;
import com.google.common.base.Strings;
import java.sql.Timestamp;

/**
 * 巡检计划任务统计
 * 
 * @author 朱成旭
 * 
 */
public class InspectPlanResCountAction extends BaseAction {

	public ActionForward inspectPlanResCount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sysuser = this.getUser(request);
		HashMap<String, String> usermap = (HashMap<String, String>) PartnerPrivUtils
				.userIsPersonnel(request);
		String userId = getUserId(request);
		String isPersonnel = usermap.get("isPersonnel");
		String personnelDeptId = sysuser.getDeptid();
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IAreaMgr iAreaMgr = (IAreaMgr) getBean("AreaMgrImpl");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		ITawSystemDeptManager systemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
        TawSystemDept tawSystemDept = systemDeptManager.getDeptinfobydeptid(deptId, "0");
        String AreaId = tawSystemDept.getAreaid();
		
		TawSystemArea tawSystemArea = iAreaMgr.getArea(AreaId);
		String ordercode = null;
		//flag用于判断是否是钻取点入还是 帐号登陆
		String flag = request.getParameter("flag");
		if(flag == null){
			ordercode = tawSystemArea.getOrdercode()+"";
		}else{
			ordercode = request.getParameter("ordercode");
			AreaId = request.getParameter("rootAreaId");
		}
		LocalDate date  = new LocalDate();
	    
			String	year = date.getYear()+"";
			String 	month = date.getMonthOfYear()+"";
			
		
		System.out.println("-----"+ordercode+"--------"+AreaId);
		List<InspectPlanResCountFromNew> resultList = new ArrayList<InspectPlanResCountFromNew>();
		
		List<InspectPlanResCountFromNew> countList = inspectPlanResMgr
		.countInspectPlanResNew(ordercode, AreaId, isPersonnel,
				personnelDeptId,year,month);
		
		
		request.getSession().setAttribute("year", year);
		request.getSession().setAttribute("month", month);
		request.getSession().setAttribute("ordercode", ordercode);
		request.getSession().setAttribute("resultList", countList);
		return mapping.findForward("inspectPlanResCountList");
	}
    
	/**
	 * 巡检完成情况  带巡检 各个专业 钻取
	 * 
	 * 
	 * @param
	 */
	public ActionForward inspectPlanResCountSpecialty(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 分页
		int pageSize = 10;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		String ordercode = request.getParameter("ordercode");
		String AreaId = request.getParameter("rootAreaId");
		String Specialty = request.getParameter("specialty");
		String person = request.getParameter("name");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		
        List<InspectPlanResCountFromSpecialty> resultList = new ArrayList<InspectPlanResCountFromSpecialty>();
		
        
        Map<String, Object> map;
        map =  inspectPlanResMgr
		.countInspectPlanResSpecialty(ordercode, AreaId ,Specialty,person,year,month, firstResult
				* pageSize, endResult
				* pageSize);
        int total = Integer.parseInt(map.get("size").toString());
		List<InspectPlanResCountFromSpecialty> workOrder;
		workOrder = (List<InspectPlanResCountFromSpecialty>) map.get("list");
        
		
		request.getSession().setAttribute("ordercode", ordercode);
		//request.getSession().setAttribute("resultList", countList);
		
		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		return mapping.findForward("inspectPlanResCountListSpecialty");
		
	}
	/**
	 * 查看巡检任务详情 按地区 任务状态 任务异常状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward inspectPlanResCountDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String areaId = request.getParameter("rootAreaId");
		String typeTemp = request.getParameter("type");
		String deptId = request.getParameter("deptId");
		String ordercode = request.getParameter("ordercode");
		int inspectState = this.getRequest(request, "inspectState");
		int exceptionFlag = this.getRequest(request, "exceptionFlag");
		int handleFlag = this.getRequest(request, "handleFlag");

		int type = -1;
		try {
			type = Integer.parseInt(typeTemp);
		} catch (NumberFormatException e) {
		}
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr = " where 1=1 ";
		String dept = StaticMethod.null2String(request.getParameter("pnrDept"));
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		String datetime = tempDate.format(new java.util.Date()) + " 00:00:00";
		String nowDateTime = CommonSqlHelper.formatDateTime(datetime);
		HashMap<String, String> usermap = (HashMap<String, String>) PartnerPrivUtils
				.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");

		if (flag.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			whereStr += "  and executeDept like '" + sysuser.getDeptid() + "%'";
		}
		if (inspectState != -1) {
			whereStr += " and inspectState=" + inspectState;
		}
		if (exceptionFlag != -1) {
			whereStr += " and exceptionFlag=" + exceptionFlag;
		}
		if (handleFlag != -1) {
			whereStr += " and isHandle=" + handleFlag;
		}
		if (!Strings.isNullOrEmpty(areaId) && ordercode.equals("1")) {
			whereStr += " and city='" + areaId + "' ";
		}
		if (!Strings.isNullOrEmpty(areaId) && ordercode.equals("2")) {
			whereStr += " and country='" + areaId + "' ";
		}
		if (!Strings.isNullOrEmpty(deptId) && ordercode.equals("3")) {
			whereStr += " and executeObject='" + deptId + "' ";
		}
		whereStr += " and planStartTime<" + nowDateTime + " and planEndTime>="
				+ nowDateTime;
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("inspectState", inspectState);
		request.setAttribute("exceptionFlag", exceptionFlag);
		request.setAttribute("handleFlag", handleFlag);
		request.setAttribute("rootAreaId", areaId);
		request.setAttribute("ordercode", ordercode);
		request.setAttribute("backShow", "backShow");
		if (areaId.length() >= 4) {
			areaId = areaId.substring(0, 4);
		}
		request.setAttribute("city", areaId);
		return mapping.findForward("inspectPlanResCountDetailList");
	}

	public ActionForward inspectPlanItemCountDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String inspectPlanResId = request.getParameter("inspectPlanResId");
		int exceptionFlag = this.getRequest(request, "exceptionFlag");
		int handleFlag = this.getRequest(request, "handleFlag");
		if (exceptionFlag == 1) {
			exceptionFlag = 0;
		} else {
			if (exceptionFlag == 0) {
				exceptionFlag = 1;
			}
		}
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
		List<InspectPlanItem> inspectPlanItemList = inspectPlanItemMgr
				.findInspectPlanItem(inspectPlanResId, exceptionFlag,
						handleFlag, pageIndex, pageSize);
		List<InspectPlanItemCountFrom> InspectPlanItemCountFromList = new ArrayList<InspectPlanItemCountFrom>();
		if (inspectPlanItemList != null && !inspectPlanItemList.isEmpty()) {
			Iterator<InspectPlanItem> inspectPlanItemIterator = inspectPlanItemList
					.iterator();
			while (inspectPlanItemIterator.hasNext()) {
				InspectPlanItem inspectPlanItem = inspectPlanItemIterator
						.next();
				InspectPlanItemCountFrom inspectPlanItemCountFrom = new InspectPlanItemCountFrom();
				inspectPlanItemCountFrom.setHandleTimestamp(inspectPlanItem
						.getHandleTimestamp());
				inspectPlanItemCountFrom.setHandleUserId(inspectPlanItem
						.getHandleUser());
				inspectPlanItemCountFrom.setHandleUsername("");
				inspectPlanItemCountFrom.setIsHandle(inspectPlanItem
						.getIsHandle());
				inspectPlanItemCountFrom.setContent(inspectPlanItem
						.getContent());
				inspectPlanItemCountFrom.setInspectItem(inspectPlanItem
						.getInspectItem());
				inspectPlanItemCountFrom.setExceptionFlag(inspectPlanItem
						.getExceptionFlag());
				inspectPlanItemCountFrom.setExceptionContent(inspectPlanItem
						.getExceptionContent());
				inspectPlanItemCountFrom.setInspectPlanItemId(inspectPlanItem
						.getId());
				inspectPlanItemCountFrom.setInspectPlanResId(inspectPlanItem
						.getPlanResId());
				InspectPlanItemCountFromList.add(inspectPlanItemCountFrom);
			}
		}
		request.setAttribute("list", InspectPlanItemCountFromList);
		request.setAttribute("resultSize", inspectPlanItemMgr
				.countInspectPlanItem(inspectPlanResId, exceptionFlag,
						handleFlag));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("inspectPlanItemCountDetailList");
	}

	public ActionForward inspectPlanResExceptionCount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sysuser = this.getUser(request);
		HashMap<String, String> usermap = (HashMap<String, String>) PartnerPrivUtils
				.userIsPersonnel(request);
		String isPersonnel = usermap.get("isPersonnel");
		String personnelDeptId = sysuser.getDeptid();
		String userId = getUserId(request);
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IAreaMgr iAreaMgr = (IAreaMgr) getBean("AreaMgrImpl");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		String areaId = request.getParameter("rootAreaId");
		if (StringUtils.isEmpty(areaId)) {
			ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
			TawSystemDept systemdept = mgr.getDeptinfobydeptid(
					sysuser.getDeptid(), "0");
			areaId = systemdept.getAreaid();
		}
		TawSystemArea tawSystemArea = iAreaMgr.getArea(areaId);
		Integer ordercode = null;
		if ("admin".equals(userId)) {
			ordercode = 1;
			areaId = "28";
		}
		if (ordercode == null) {
			ordercode = tawSystemArea.getOrdercode();
		}
		List<InspectPlanResCountFrom> resultList = new ArrayList<InspectPlanResCountFrom>();
		if (ordercode == 3) {
			List<InspectPlanResCountFrom> countList = inspectPlanResMgr
					.countInspectPlanResException(ordercode, areaId,
							isPersonnel, personnelDeptId);
			if (countList != null && !countList.isEmpty()) {
				for (Iterator<InspectPlanResCountFrom> countIt = countList
						.iterator(); countIt.hasNext();) {
					InspectPlanResCountFrom inspectPlanResCountFrom = countIt
							.next();
					PartnerDept partnerDept = partnerDeptMgr
							.getPartnerDept(inspectPlanResCountFrom.getDeptId());
					if (partnerDept != null) {
						inspectPlanResCountFrom.setDeptName(partnerDept
								.getName());
					}
					inspectPlanResCountFrom.setExceptionPercentage(this
							.calculateThePercentage(
									new Double(inspectPlanResCountFrom
											.getExceptionNo()),
									new Double(inspectPlanResCountFrom
											.getResNo())));
					inspectPlanResCountFrom.setHandlePercentage(this
							.calculateThePercentage(
									new Double(inspectPlanResCountFrom
											.getHandleNo()),
									new Double(inspectPlanResCountFrom
											.getExceptionNo())));
					resultList.add(inspectPlanResCountFrom);
				}
			}
		} else {
			List<InspectPlanResCountFrom> countList = inspectPlanResMgr
					.countInspectPlanResException(ordercode, isPersonnel,
							personnelDeptId);
			List list = iAreaMgr.listChildArea(areaId);
			// 逐级取子部门
			if (list != null && !list.isEmpty()) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					InspectPlanResCountFrom inspectPlanResCountFrom = new InspectPlanResCountFrom();
					TawSystemArea item = (TawSystemArea) it.next();
					if (ordercode == 1) {
						inspectPlanResCountFrom.setCityName(item.getAreaname());
						inspectPlanResCountFrom.setCityId(item.getAreaid());
						if (countList != null && !countList.isEmpty()) {
							for (Iterator<InspectPlanResCountFrom> countIt = countList
									.iterator(); countIt.hasNext();) {
								InspectPlanResCountFrom inspectPlanResCountFromTemp = countIt
										.next();
								if (inspectPlanResCountFromTemp.getCityId()
										.equals(inspectPlanResCountFrom
												.getCityId())) {
									inspectPlanResCountFrom
											.setResNo(inspectPlanResCountFromTemp
													.getResNo());
									inspectPlanResCountFrom
											.setCompletedNo(inspectPlanResCountFromTemp
													.getCompletedNo());
									inspectPlanResCountFrom
											.setExceptionNo(inspectPlanResCountFromTemp
													.getExceptionNo());
									inspectPlanResCountFrom
											.setUnfinishedNo(inspectPlanResCountFromTemp
													.getUnfinishedNo());
									inspectPlanResCountFrom
											.setHandleNo(inspectPlanResCountFromTemp
													.getHandleNo());
									inspectPlanResCountFrom
											.setUnhandledNo(inspectPlanResCountFromTemp
													.getUnhandledNo());
									inspectPlanResCountFrom
											.setExceptionPercentage(this.calculateThePercentage(
													new Double(
															inspectPlanResCountFromTemp
																	.getExceptionNo()),
													new Double(
															inspectPlanResCountFromTemp
																	.getResNo())));
									inspectPlanResCountFrom
											.setHandlePercentage(this.calculateThePercentage(
													new Double(
															inspectPlanResCountFromTemp
																	.getHandleNo()),
													new Double(
															inspectPlanResCountFromTemp
																	.getExceptionNo())));
								}
							}
						}
					}
					if (ordercode == 2) {
						inspectPlanResCountFrom.setCountryName(item
								.getAreaname());
						inspectPlanResCountFrom.setCountryId(item.getAreaid());
						if (countList != null && !countList.isEmpty()) {
							for (Iterator<InspectPlanResCountFrom> countIt = countList
									.iterator(); countIt.hasNext();) {
								InspectPlanResCountFrom inspectPlanResCountFromTemp = countIt
										.next();
								if (inspectPlanResCountFromTemp.getCountryId()
										.equals(inspectPlanResCountFrom
												.getCountryId())) {
									inspectPlanResCountFrom
											.setResNo(inspectPlanResCountFromTemp
													.getResNo());
									inspectPlanResCountFrom
											.setCompletedNo(inspectPlanResCountFromTemp
													.getCompletedNo());
									inspectPlanResCountFrom
											.setExceptionNo(inspectPlanResCountFromTemp
													.getExceptionNo());
									inspectPlanResCountFrom
											.setUnfinishedNo(inspectPlanResCountFromTemp
													.getUnfinishedNo());
									inspectPlanResCountFrom
											.setHandleNo(inspectPlanResCountFromTemp
													.getHandleNo());
									inspectPlanResCountFrom
											.setUnhandledNo(inspectPlanResCountFromTemp
													.getUnhandledNo());
									inspectPlanResCountFrom
											.setExceptionPercentage(this.calculateThePercentage(
													new Double(
															inspectPlanResCountFromTemp
																	.getExceptionNo()),
													new Double(
															inspectPlanResCountFromTemp
																	.getResNo())));
									inspectPlanResCountFrom
											.setHandlePercentage(this.calculateThePercentage(
													new Double(
															inspectPlanResCountFromTemp
																	.getHandleNo()),
													new Double(
															inspectPlanResCountFromTemp
																	.getExceptionNo())));
								}
							}
						}
					}
					resultList.add(inspectPlanResCountFrom);
				}
			}
		}
		request.getSession().setAttribute("ordercode", ordercode);
		request.getSession().setAttribute("resultList", resultList);
		return mapping.findForward("inspectPlanResExceptionCountList");
	}

	public ActionForward inspectPlanResExceptionCountDetail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String areaId = request.getParameter("rootAreaId");
		int exceptionFlag = this.getRequest(request, "exceptionFlag");
		int handleFlag = this.getRequest(request, "handleFlag");
		String deptId = request.getParameter("deptId");
		String ordercode = request.getParameter("ordercode");
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr = " where 1=1 ";
		String dept = StaticMethod.null2String(request.getParameter("pnrDept"));
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		String datetime = tempDate.format(new java.util.Date()) + " 00:00:00";
		String nowDateTime = CommonSqlHelper.formatDateTime(datetime);
		HashMap<String, String> usermap = (HashMap<String, String>) PartnerPrivUtils
				.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");

		if (flag.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
								// like
			whereStr += "  and executeDept like '" + sysuser.getDeptid() + "%'";
		} else if (!"admin".equals(userId)) {// 如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId
												// like

			ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");

			TawSystemDept systemdept = mgr.getDeptinfobydeptid(
					sysuser.getDeptid(), "0");
			String areaid = systemdept.getAreaid();
			if (areaid != null && !areaid.trim().equals("")) {
				whereStr += "  and country like '" + areaid + "%'";
			} else {// 对应对应部门没有低于，则不允许查看数据
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		if (handleFlag != -1) {
			whereStr += " and isHandle=" + handleFlag;
		}
		if (exceptionFlag != -1) {
			whereStr += " and exceptionFlag=" + exceptionFlag;
		}
		if (!Strings.isNullOrEmpty(areaId) && ordercode.equals("1")) {
			whereStr += " and city='" + areaId + "' ";
		}
		if (!Strings.isNullOrEmpty(areaId) && ordercode.equals("2")) {
			whereStr += " and country='" + areaId + "' ";
		}
		if (!Strings.isNullOrEmpty(deptId) && ordercode.equals("3")) {
			whereStr += " and executeObject='" + deptId + "' ";
		}
		if (CommonSqlHelper.isOracleDialect()) {

			whereStr += " and planStartTime<" + nowDateTime
					+ " and planEndTime>=" + nowDateTime;
		} else {
			whereStr += " and planStartTime<current and planEndTime>=current";
		}
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("exceptionFlag", exceptionFlag);
		request.setAttribute("handleFlag", handleFlag);
		request.setAttribute("rootAreaId", areaId);
		request.setAttribute("ordercode", ordercode);
		request.setAttribute("backShow", "backShow");
		if (areaId.length() >= 4) {
			areaId = areaId.substring(0, 4);
		}
		request.setAttribute("city", areaId);
		return mapping.findForward("inspectPlanResExceptionCountDetailList");
	}

	public ActionForward inspectPlanItemExceptionCountDetail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String inspectPlanResId = request.getParameter("inspectPlanResId");
		int exceptionFlag = this.getRequest(request, "exceptionFlag");
		int handleFlag = this.getRequest(request, "handleFlag");
		if (exceptionFlag == 1) {
			exceptionFlag = 0;
		} else {
			if (exceptionFlag == 0) {
				exceptionFlag = 1;
			}
		}
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
		List<InspectPlanItem> inspectPlanItemList = inspectPlanItemMgr
				.findInspectPlanItem(inspectPlanResId, exceptionFlag,
						handleFlag, pageIndex, pageSize);
		List<InspectPlanItemCountFrom> InspectPlanItemCountFromList = new ArrayList<InspectPlanItemCountFrom>();
		if (inspectPlanItemList != null && !inspectPlanItemList.isEmpty()) {
			Iterator<InspectPlanItem> inspectPlanItemIterator = inspectPlanItemList
					.iterator();
			while (inspectPlanItemIterator.hasNext()) {
				InspectPlanItem inspectPlanItem = inspectPlanItemIterator
						.next();
				InspectPlanItemCountFrom inspectPlanItemCountFrom = new InspectPlanItemCountFrom();
				inspectPlanItemCountFrom.setHandleTimestamp(inspectPlanItem
						.getHandleTimestamp());
				inspectPlanItemCountFrom.setHandleUserId(inspectPlanItem
						.getHandleUser());
				inspectPlanItemCountFrom.setHandleUsername("");
				inspectPlanItemCountFrom.setIsHandle(inspectPlanItem
						.getIsHandle());
				inspectPlanItemCountFrom.setContent(inspectPlanItem
						.getContent());
				inspectPlanItemCountFrom.setInspectItem(inspectPlanItem
						.getInspectItem());
				inspectPlanItemCountFrom.setExceptionFlag(inspectPlanItem
						.getExceptionFlag());
				inspectPlanItemCountFrom.setExceptionContent(inspectPlanItem
						.getExceptionContent());
				inspectPlanItemCountFrom.setInspectPlanItemId(inspectPlanItem
						.getId());
				inspectPlanItemCountFrom.setInspectPlanResId(inspectPlanItem
						.getPlanResId());
				InspectPlanItemCountFromList.add(inspectPlanItemCountFrom);
			}
		}
		request.setAttribute("list", InspectPlanItemCountFromList);
		request.setAttribute("resultSize", inspectPlanItemMgr
				.countInspectPlanItem(inspectPlanResId, exceptionFlag,
						handleFlag));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("inspectPlanResId", inspectPlanResId);
		request.setAttribute("exceptionFlag", exceptionFlag);
		request.setAttribute("handleFlag", handleFlag);
		
		return mapping.findForward("inspectPlanItemExceptionCountDetailList");
	}

	/**
	 * 处理巡检项异常
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward handleInspectPlanItemException(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String inspectPlanItemIdTemp = request
				.getParameter("inspectPlanItemId");
		String inspectPlanResIdTemp = request.getParameter("inspectPlanResId");
		Long inspectPlanResId = new Long(-1);
		try {
			inspectPlanResId = Long.parseLong(inspectPlanResIdTemp);
		} catch (NumberFormatException e) {
		}
		String exceptionFlagTemp = request.getParameter("exceptionFlag");
		int exceptionFlag = -1;
		try {
			exceptionFlag = Integer.parseInt(exceptionFlagTemp);
		} catch (NumberFormatException e) {
		}
		String isHandleTemp = request.getParameter("isHandle");
		int isHandle = -1;
		try {
			isHandle = Integer.parseInt(isHandleTemp);
		} catch (NumberFormatException e) {
		}
		TawSystemSessionForm tawSystemSessionForm = getUser(request);
		String userId = tawSystemSessionForm.getUserid();
		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
		InspectPlanItem inspectPlanItem = inspectPlanItemMgr
				.findById(inspectPlanItemIdTemp);
		if (inspectPlanItem != null) {
			inspectPlanItem.setIsHandle(1);
			inspectPlanItem.setHandleTimestamp(new Timestamp(new Date()
					.getTime()));
			inspectPlanItem.setHandleUser(userId);
			inspectPlanItemMgr.save(inspectPlanItem);
			Integer no = inspectPlanItemMgr.countInspectPlanItem(
					inspectPlanResIdTemp, 0, 0);
			if (no == 0) {
				IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
				InspectPlanRes inspectPlanRes = inspectPlanResMgr
						.get(inspectPlanResId);
				if (inspectPlanRes != null) {
					inspectPlanRes.setIsHandle(1);
					inspectPlanResMgr.save(inspectPlanRes);
				}
			}
		}
		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/inspectPlanResCount.do?method=inspectPlanItemExceptionCountDetail&isHandle="+isHandle+"&exceptionFlag="+exceptionFlag);
		// actionForward.setRedirect(true);
		return actionForward;
	}

	/**
	 * 从request中获取名称为name的Integer数据，如果为空将返回－1
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	private Integer getRequest(HttpServletRequest request, String name) {
		String temp = request.getParameter(name);
		int r = -1;
		try {
			r = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
		}
		return r;
	}

	private String calculateThePercentage(Double a, Double b) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		NumberFormat nf1 = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);
		if (a == null || a.equals(0) || a.equals(0.0)|| a.equals(0.00)) {
			return nf1.format(0);
		}
		if (b == null || b.equals(0) || b.equals(0.0)|| a.equals(0.00)) {
			return nf1.format(0);
		}
		Double d = new Double(0);
		d = a / b;
		if (d == null || d.equals(100) || d.equals(100.0)|| a.equals(100.00)) {
			return nf1.format(100);
		}
		String r = nf.format(d);
		return r;
	}
}
