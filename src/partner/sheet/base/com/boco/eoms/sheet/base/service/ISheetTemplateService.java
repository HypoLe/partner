package com.boco.eoms.sheet.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * Description: 工单模板 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 3:49:40 PM 
 */
public interface ISheetTemplateService{
	/**
	 * 根据用户查找所有的模板(main表)
	 * @author wangjianhua
	 * @date 2008-07-22
	 */
	public void getTemplatesByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	/**
	 * 根据用户查找所有的模板(link表)
	 * @author wangjianhua
	 * @date 2008-07-22
	 */
	public void getDealTemplatesByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	 /**
     * 保存模板(main表)
     * @author wangjianhua
	 * @date 2008-07-22
     */
    public void saveTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 保存模板(link表)
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void saveDealTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 引用模板
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void referenceTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 查看main模板
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void getTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 查看link模板
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void getDealTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 删除main模板
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void removeTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
    
    /**
     * 删除link模板
     * @author wangjianhua
	 * @date 2008-07-22
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void removeDealTemplate(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
}
