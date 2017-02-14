package com.boco.eoms.sheet.base.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 工单列表接口
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 10:07:20 AM 
 */
public interface ISheetListService{

    /**
     * 设置任务列表每页显示的长度
     * 
     * @param pageLenth
     */
    public abstract void setPageLength(int pageLenth);

    /**
     * 获取任务列表每页显示的长度
     */
    public abstract int getPageLength();
    
    /**
     * 执行查询方法
     */
    public abstract void performQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    
    /**
     * 执行列表查询方法
     */
    public abstract void performListQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;


	@SuppressWarnings("unchecked")
	public Map getUndoList(HttpServletRequest request) throws Exception;
	
    /**
     * 显示待处理工单列表(Atom系统)
     */
    public abstract void getAtomLists(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
  
  
    /**
     * 显示待处理工单列表(portal)
     */
    public abstract void getUndoListsForPortal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    /**
     * 显示已处理工单列表(portal)
     */
    public abstract void getDoneListsForPortal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;  

	
	/**
	 * 显示所有待处理任务列表(portal)
	 */
	public void getUndoAllListsForPortal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 显示所有已处理任务列表(portal)
	 */
	public void getDoneAllListsForPortal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 显示所有待处理任务列表
	 */
	public void getUndoAllLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 显示所有已处理任务列表
	 */
	public void getDoneAllLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	
}
