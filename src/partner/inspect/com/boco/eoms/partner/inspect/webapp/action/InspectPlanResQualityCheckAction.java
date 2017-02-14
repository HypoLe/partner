package com.boco.eoms.partner.inspect.webapp.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResQualityCheckMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.InspectPlanResQualityCheck;
import com.googlecode.genericdao.search.Search;

/** 
 * Description: 巡检任务质检
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 19, 2013 3:58:14 PM
 */
public class InspectPlanResQualityCheckAction extends BaseAction {
	
	/**
	 *  进入质检填写页面
	 */
	public ActionForward toResQualityCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String planResId = request.getParameter("planResId");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(planResId));
		request.setAttribute("planRes", inspectPlanRes);
		return mapping.findForward("qualityInspectFrom");
	}
	
	/**
	 *  质检填写
	 */
	public ActionForward saveResQualityCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResQualityCheckMgr inspectPlanResQualityCheckMgr = (IInspectPlanResQualityCheckMgr) getBean("inspectPlanResQualityCheckMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		String planResId = StaticMethod.null2String(request.getParameter("planResId"));
		String planId = StaticMethod.null2String(request.getParameter("planId"));
		String scoreInTime = StaticMethod.null2String(request.getParameter("scoreInTime"));
		String scoreStandard = StaticMethod.null2String(request.getParameter("scoreStandard"));
		String scoreFinish = StaticMethod.null2String(request.getParameter("scoreFinish"));
		String scoreStatisfied = StaticMethod.null2String(request.getParameter("scoreStatisfied"));
		String scoreTotal = StaticMethod.null2String(request.getParameter("scoreTotal"));
		String satisfaction = StaticMethod.null2String(request.getParameter("satisfaction"));
		String remark = StaticMethod.null2String(request.getParameter("textRemark"));
		InspectPlanResQualityCheck inspectPlanResQualityCheck = new InspectPlanResQualityCheck();
		inspectPlanResQualityCheck.setCheckTime(new Date());
		inspectPlanResQualityCheck.setCheckUser(this.getUserId(request));
		inspectPlanResQualityCheck.setPlanResId(Long.parseLong(planResId));
		inspectPlanResQualityCheck.setPlanId(planId);
		inspectPlanResQualityCheck.setRemark(remark);
		inspectPlanResQualityCheck.setSatisfaction(satisfaction);
		inspectPlanResQualityCheck.setScoreFinish(Float.parseFloat(scoreFinish));
		inspectPlanResQualityCheck.setScoreInTime(Float.parseFloat(scoreInTime));
		inspectPlanResQualityCheck.setScoreStandard(Float.parseFloat(scoreStandard));
		inspectPlanResQualityCheck.setScoreStatisfied(Float.parseFloat(scoreStatisfied));
		inspectPlanResQualityCheck.setScoreTotal(Float.parseFloat(scoreTotal));
		inspectPlanResQualityCheckMgr.save(inspectPlanResQualityCheck);
		InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(planResId));
		inspectPlanRes.setSatisfaction(satisfaction);
		inspectPlanRes.setQualityInspectTime(new Date());
		inspectPlanRes.setQualityInspectUser(this.getUserId(request));
		inspectPlanResMgr.save(inspectPlanRes);
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectPlanExecute.do?method=gotoWiteQualityInspectList");
		actionForward.setRedirect(true);
		
		return actionForward;
	}
	
	/**
	 *  质检详情查看
	 */
	public ActionForward getResQualityCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResQualityCheckMgr inspectPlanResQualityCheckMgr = (IInspectPlanResQualityCheckMgr) getBean("inspectPlanResQualityCheckMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("planResId", id);
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(id));
		
		InspectPlanResQualityCheck check = inspectPlanResQualityCheckMgr.searchUnique(search);
		request.setAttribute("resname", inspectPlanRes.getResName());
		request.setAttribute("check", check);
		return mapping.findForward("qualityInspectDetail");
	}
}
