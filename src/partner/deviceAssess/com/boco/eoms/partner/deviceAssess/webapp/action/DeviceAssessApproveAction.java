package com.boco.eoms.partner.deviceAssess.webapp.action;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 4:05:15 PM 
 */
public class DeviceAssessApproveAction extends BaseAction {
	
	/**
	 * 审批列表
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeviceAssessApproveMgr approveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		DeviceAssessApprove approve = new DeviceAssessApprove();
		approve.setAssessType(request.getParameter("assessType"));
		approve.setName(request.getParameter("name"));
		approve.setSheetNum(request.getParameter("sheetNum"));
		//将当前userid保存在审批人字段
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		approve.setApproveUser(sessionForm.getUserid());
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"list")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PageModel pm = approveMgr.findApproveList(pageIndex*pageSize, pageSize,approve);
		
//		Map<String,Integer> map = approveMgr.findAllDeviceAssessApprove();
//		request.setAttribute("map", map);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 跳转到审批页面
	 */
	public ActionForward toApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String assessId = request.getParameter("assessId");
		DeviceAssessApproveMgr approveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		DeviceAssessApprove approve = approveMgr.getDeviceAssessApprove(id);
		request.setAttribute("approveId", id);
		request.setAttribute("assessId", assessId);
		request.setAttribute("approve", approve);
		return mapping.findForward("approve");
	}
	
	/**
	 * 填写审批意见
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeviceAssessContentMgr contentMgr = (DeviceAssessContentMgr) getBean("deviceAssessContentMgr");
		DeviceAssessApproveMgr approveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		String approveId = request.getParameter("approveId");
		String assessId = request.getParameter("assessId");
		String c = request.getParameter("content");
		String remark = request.getParameter("remark");
		String stateStr = request.getParameter("state");
		Integer state = Integer.parseInt(stateStr);
		DeviceAssessContent content = new DeviceAssessContent();
		content.setApproveId(approveId);
		content.setAssessId(assessId);
		content.setContent(c);
		content.setRemark(remark);
		content.setCommitTime(CommonUtils.toEomsStandardDate(new Date()));
		content.setState(state);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		content.setApproveUser(sessionForm.getUserid());
		contentMgr.saveAssessContent(content);
		//更改审批状态
		approveMgr.modifyApprove(approveId, state);
		return mapping.findForward("success");
	}
	
	/**
	 * 作废
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DeviceAssessApproveMgr approveMgr = (DeviceAssessApproveMgr) getBean("deviceAssessApproveMgr");
		String id = request.getParameter("id");
		approveMgr.deleteApprove(id);
		return mapping.findForward("success");
	}
}
