package com.boco.eoms.sheet.base.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * Description: 显示工单页面 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 10:07:20 AM 
 */
public interface ISheetInfoShowService{
	
    
    /**
     * 显示工单详细界面
     */
    public void showDetailPage(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 工单详细处理记录列表
     */
    public abstract void showSheetDealList(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * main工单的详细信息页面，如归档，由我启动
     */
    public void showMainDetailPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception ;
    
    /**
     * 显示模板页面(main表)
     */
    public void showInputTemplateSheetPage(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
	
    /**
     * 显示模板页面(link表)
     */
    public void showDealInputTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 
     */
    public void showSheetAccessoriesList(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
	/**
	 * 用途不明，待确认
	 */  
	public abstract void showRemarkPage(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception;
	
	 /**
     * 显示流程图
	 */
	public void showWorkFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;

	 /**
     * 显示流程实例图
	 */ 
	public void showWorkFlowInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;

	/**
	 * 显示隐藏工单的查询页面
	 */
	public void showQueryHidePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	
    /**
     * 显示详细信息页面(Atom系统)
     */
    public abstract void showAtomDetailPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;   
    
    /**
     * 阶段回复(Atom系统)
     */
    public abstract void showPhaseReplyPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;    

	 /**
	  * 显示全流程图
	  */
	public void shoWholeWorkFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	/**
	 * 全流程步骤的详细信息
	 */
	public void getAllWorkflowStepInfoPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
   
  
   public void getAllDoRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	public void getNowDoRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	public void getPreRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception; 
	/**
	 * 取流程对象的属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getProcessOjbectAttribute();
	/**
	 * 取得保存附件字段属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getAttachmentAttributeOfOjbect();
	
	/**
	 * 显示流程实例图
	 * @author zhangxb
	 * @since 2008-09-09
	 */
	public void getLinkOperatePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 显示工单统计信息
	 */
	public void showStatisticInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
