package com.boco.eoms.sheet.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** 
 * Description: 工单列表展示
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 12, 2012 4:20:16 PM 
 */
public interface ISheetListShowService {
	   /**
     * 显示查询界面
     */
    public void showQueryPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示待处理工单列表
     */
    public void showListUndo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示已处理列表
     */
    public void showListsenddone(ActionMapping mapping,ActionForm form, 
    		HttpServletRequest request,HttpServletResponse response) throws Exception;
    
    /**
     * 显示草稿列表
     */
    public void showDraftList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示已归档工单列表
     */
    public void showHoldedList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示已撤销工单列表
     */
    public abstract void showCancelList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示由我启动列表
     */
    public void showOwnStarterList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示未归档工单列表
     */
    public abstract void showListForAdmin(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
	 * 显示本角色未处理列表
	 */
	public void showListUndoByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception;
	
	/**
	 * 显示本角色已处理列表
	 */
	public void showListsenddoneByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
    /**
	 * 同组未处理列表
	 */
	public abstract void showUndoListForSameTeam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
    /**
	 * 获取待归档工单任务列表
	 */
	public abstract void showUnHoldList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

    /**
	 * 获取待处理工单任务列表（不含过滤步骤的任务）
	 */
	public abstract void showUndoListByFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;	
	
	/**
	 * 延期申请列表
	 */
	public void deferAppList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	  /**
	 * 同组处理模式已处理列表（本角色其他人员已经处理完成工单）
	 */
	public abstract void showDoneListForSameTeam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
   /**
    * 
    */
   public abstract void showHoldedListForUser(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response)
           throws Exception;   
   
   /**
    * 通过EOMS工单流水号查询工单列表
    */
   public void showSheetListByEomsSheetId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) 
   			throws Exception;
}
