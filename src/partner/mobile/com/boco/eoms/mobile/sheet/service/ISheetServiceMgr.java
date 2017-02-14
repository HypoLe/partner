package com.boco.eoms.mobile.sheet.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;

public interface ISheetServiceMgr {

	public String showAllListsendundoToJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);
	public String showListsendundoToJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);
	public String showDetailPageToJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response,String beanNameType);
	
	public Map<String,Integer> getUnDoneCheckInfo(String userId);
	public void id2NameMain(BaseMain main,String beanName);
	public void id2NameLink(BaseLink link,String beanName);
}
