package com.boco.eoms.examonline.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.examonline.model.ExamNotice;
import com.boco.eoms.examonline.service.ExamNoticeService;

/** 
 * Description: 考试信息发布 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 15, 2012 3:51:29 PM 
 */
public class ExamNoticeAction extends BaseAction {
	public ActionForward findExamNoticeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ExamNoticeService examNoticeService = (ExamNoticeService)ApplicationContextHolder.getInstance().getBean("examNoticeService");
		PageModel pm = null;
	    List<ExamNotice> l = null;
	    try{
	    	//分页相关
	    	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
	        int pageIndex  = Integer.parseInt(pageIndexStr);
	    	//定义每页显示数
	    	int pageSize = 15;
	    	ExamNotice examNotice = new ExamNotice();
		    pm = examNoticeService.findExamNoticeList(examNotice, pageIndex, pageSize);
		    l = pm.getDatas();
		    request.setAttribute("list",l);
    		request.setAttribute("resultSize",pm.getTotal());
    		request.setAttribute("pageSize", pageSize);
	    }catch (Exception e) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("examNoticeList");
	}
	
	public ActionForward showDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		ExamNoticeService examNoticeService = (ExamNoticeService)ApplicationContextHolder.getInstance().getBean("examNoticeService");
		ExamNotice en = examNoticeService.getExamNotice(id);
		request.setAttribute("en", en);
		return mapping.findForward("examNoticeDetail");
	}
	
	public ActionForward toAddExamNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		return mapping.findForward("examNoticeAdd");
	}
	
	public ActionForward addExamNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		 TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
		    request.getSession().getAttribute("sessionform");
		String creator = saveSessionBeanForm.getUserid();
		ExamNotice en = new ExamNotice();
		en.setTitle(title);en.setContent(content);en.setCreator(creator);en.setCreateTime(new Date());
		ExamNoticeService examNoticeService = (ExamNoticeService)ApplicationContextHolder.getInstance().getBean("examNoticeService");
		examNoticeService.saveExamNotice(en);
		return mapping.findForward("suc");
	}
	
	public ActionForward removeExamNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		return mapping.findForward("showExamCount");
	}
}
