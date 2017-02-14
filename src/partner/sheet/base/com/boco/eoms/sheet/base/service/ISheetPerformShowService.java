package com.boco.eoms.sheet.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * Description: 工单操作展示 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 12, 2012 4:20:32 PM 
 */
public interface ISheetPerformShowService {
	/**
	 * 显示工单新增页面
	 */
	public String showNewSheetPage(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	 /**
     * 显示工单新增子页面
     */
    public void showInputNewSheetPage(ActionMapping mapping,ActionForm form, 
    		HttpServletRequest request,HttpServletResponse response) throws Exception;
    
    /**
     * 显示撤销界面
     */
    public void showCancelPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示删除界面
     */
    public void showDeletePage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 显示归档界面
     */
    public void showHoldPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 呈现工单草稿
     */
    public void showDraftPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 呈现工单处理界面
     */
    public abstract void showDealPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
     * 呈现工单处理子界面
     */
    public abstract void showInputDealPage(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 呈现工单驳回界面
     */
    public void showRejectPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;

    /**
     * 呈现工单驳回子界面
     */
    public void showInputRejectPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 撤消的框架页面
     */
    public void showCancelInputPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception ;

	/**
	 * 执行流程的事件页面
	 */
	public void showEventPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	/**
	 * 执行流程的事件输入页面
	 */
	public void showInputEventPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
    /**
     * 工单移交输入页面
     */
    public void showTransferWorkItemPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * 显示工单强制归档、作废页面
	 */
	public void showForceHoldPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception;
	
	/**
	 * 呈现任务分派页面
	 */
	public void showInputSplitPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	public abstract void showPhaseBackToUpPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
	
	public abstract void showPhaseAdvicePage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
	
	/**
	 * 呈现处理回复通过界面
	 */
	public void showDealReplyAcceptPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 呈现处理回复不通过界面
	 */
	public void showDealReplyRejectPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 呈现接口回调回复页面(接口互调)
	 */
	public void showInvokeReplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception ;
	
    /**
     * 根据历史工单生成新的工单新增页面
     */
    public abstract void showNewSendPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;	
    
	
	/**
	 * 批理处理页面
	 */
   public abstract void showBatchDealPage(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response)
           throws Exception; 

}
