package com.boco.eoms.mobile.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public interface InspectPlanMgr {
	
	public String saveAllCheckResult(HttpServletRequest request,String userId);
	public void listInspectPlanItem(HttpServletRequest request,HttpServletResponse response,String userId) throws ServletException, IOException ;
	public void listInspectPlanRes(HttpServletRequest request, HttpServletResponse response, String userId,String deptId) throws ServletException, IOException ;
	public void listInspectBarRes(HttpServletRequest request, HttpServletResponse response, String userId,String deptId) throws ServletException, IOException ;
	public void listResource(HttpServletRequest request, HttpServletResponse response, String userId,String deptId) throws ServletException, IOException ;
	public void saveLocation(HttpServletRequest request, HttpServletResponse response, String userId) ;
	public void saveLineLocation(HttpServletRequest request, HttpServletResponse response, String userId);
	public void getTransLineList(HttpServletRequest request, HttpServletResponse response);
	public void getTransLinePointList(HttpServletRequest request, HttpServletResponse response);
}
