package com.boco.eoms.partner.sheet.commontask.webapp.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;

/** 
 * Description: 通用任务工单ACTION
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
 
 public class PnrCommonTaskAction extends SheetAction  {
 	
 	 /**
	 * showDrawing
	 */
	public ActionForward showDrawing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("draw");
	}
	
	
	/**
	 * showPic
	 */
	public ActionForward showPic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("pic");
	}
	
	
	/**
	 * showKPI
	 */
	public ActionForward showKPI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("kpi");
	}
	
	/**
	 * 工单的初始化页面 流程互调时 调用的新增
	 */
	public ActionForward showNewInputSheetPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.showNewInputSheetPage(mapping, form, request, response);
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
		return mapping.findForward("inputNew");
	}
	/**
	 * 
	 */
	public ActionForward showInputDealPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.showInputDealPage(mapping, form, request, response);
		return mapping.findForward("inputDealpage");
	}
	/**
	 * 草稿
	 */
	public ActionForward showDraftPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.showDraftPage(mapping, form, request, response);
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
		return mapping.findForward("draft");
	}

 }
 



