package com.boco.eoms.examonline.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.examonline.model.ExamResult;
import com.boco.eoms.examonline.service.ExamResultService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;

/** 
 * Description: 考试信息发布 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 15, 2012 3:51:29 PM 
 */
public class ExamResultAction extends BaseAction {
	
	/**
	 * 查询代维考生考试结果
	 */
	public ActionForward findExamResultList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){ 
		String issueId = request.getParameter("issueId");
		ExamResultService resultService = (ExamResultService)getBean("examResultService");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PageModel pm = null;
	    List l = null;//分页相关
    	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
    	//定义每页显示数
    	int pageSize = 15;
	    pm = resultService.findExamResultList(issueId, pageIndex, pageSize);
	    l = pm.getDatas();
	    for(int i=0;i<l.size();i++){
	    	ExamResult r = (ExamResult)l.get(i);
	    	if(r.getExamType()==2){
	    		String tester = r.getTester();
	    		r.setTesterName(partnerUserMgr.getPartnerUserByPersonCardNo(tester).getName());
	    	}
	    }
	    request.setAttribute("issueId", issueId);
	    request.setAttribute("list",l);
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("examResultList");
	}
}
