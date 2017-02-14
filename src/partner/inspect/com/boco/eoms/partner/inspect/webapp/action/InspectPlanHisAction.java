package com.boco.eoms.partner.inspect.webapp.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanHisMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;

/** 
 * Description: 巡检历史 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 10, 2012 11:32:55 AM 
 */
public class InspectPlanHisAction extends BaseAction {
	
	/**
	 * 查询历史巡检主体列表
	 */
	public ActionForward findInspectPlanMainHisList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereSql = " where 1=1 ";
		if(!StringUtils.isEmpty(request.getParameter("planName")) ){
			whereSql += " and plan_name like '%"+request.getParameter("planName")+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("specialty")) ){
			whereSql += " and specialty='"+request.getParameter("specialty")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("year")) ){
			whereSql += " and year='"+request.getParameter("year")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("month")) ){
			whereSql += " and month='"+request.getParameter("month")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("partnerDeptId")) ){
			whereSql += " and partner_Dept_Id='"+request.getParameter("partnerDeptId")+"'";
		}
		IInspectPlanHisMgr inspectPlanHisMgr = (IInspectPlanHisMgr)this.getBean("inspectPlanHisMgr");
		PageModel pm = inspectPlanHisMgr.findInspectPlanMainHisList(whereSql, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		return mapping.findForward("inspectPlanMainHisList");
	}
	
	/**
	 * 查看历史计划详情
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getInspectPlanMainHisDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectPlanHisMgr inspectPlanHisMgr = (IInspectPlanHisMgr)this.getBean("inspectPlanHisMgr");
		String id = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanHisMgr.getInspectPlanMainHis(id);
		request.setAttribute("planMain", planMain);
		
		//审批历史列表
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr)getBean("inspectPlanApproveMgr");
		Search search = new Search();
		search.addFilterEqual("planId", id);
		search.addSortDesc("approveTime");
		List<InspectPlanApprove> approveList = inspectPlanApproveMgr.search(search);
		request.setAttribute("approveList", approveList);
		request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
		request.setAttribute("planTypeMap", InspectConstants.planTypeMap);
		return mapping.findForward("inspectPlanMainHisDetail");
	}
	
	/**
	 * 查看历史巡检资源列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResHisList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectPlanHisMgr inspectPlanHisMgr = (IInspectPlanHisMgr)this.getBean("inspectPlanHisMgr");
		String planId = request.getParameter("id");
		request.setAttribute("planId", planId);
		
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereSql = " where plan_Id='"+planId+"' ";
		if(!StringUtils.isEmpty(request.getParameter("resName")) ){
			whereSql += " and res_Name like '%"+request.getParameter("resName")+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("chgExecuteObject")) ){
			whereSql += " and execute_object='"+request.getParameter("chgExecuteObject")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("inspectCycle")) ){
			whereSql += " and inspect_Cycle='"+request.getParameter("inspectCycle")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("inspectState")) ){
			whereSql += " and inspect_State='"+request.getParameter("inspectState")+"'";
		}
		PageModel pm = inspectPlanHisMgr.findInspectPlanResHisList(whereSql, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("resultSize", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("inspectPlanResHisList");
	}
	
	/**
	 * 查看历史巡检资源巡检项执行详情
	 */
	public ActionForward findInspectPlanItemHisList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		return null;
	}
}
