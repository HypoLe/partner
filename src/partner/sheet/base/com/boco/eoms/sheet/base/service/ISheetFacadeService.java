package com.boco.eoms.sheet.base.service;

/** 
 * Description: 工单业务门面设计模式接口 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang
 * @version:    1.0 
 * Create at:   Oct 11, 2012 4:12:43 PM 
 */
public interface ISheetFacadeService {
	public ITaskService getTaskService();

	public void setTaskService(ITaskService taskService);
	/**
     * 获取link的service
     * 
     */
    public ILinkService getLinkService();

    /**
     * 设置link的service
     * 
     */
    public void setLinkService(ILinkService linkService);

    /**
     * 获取main的service
     */
    public IMainService getMainService();

    /**
     * 设置main的service、
     */
    public void setMainService(IMainService mainService);

    /**
     * 获取业务流程service
     */
    public IBusinessFlowService getBusinessFlowService();

    /**
     * 设置业务流程service
     */
    public void setBusinessFlowService(IBusinessFlowService businessFlowService);

    /**
     * 获取任务列表所要呈现的流程模板的名称
     */
    public String getFlowTemplateName();

    /**
     * 设置任务列表所要呈现的流程模板的名称
     */
    public abstract void setFlowTemplateName(String flowTemplateName);
    
    //--------------上面是NewBaseSheet
    
    public IPreEngineDataHandler getPreEngineDataHandler();
	public void setPreEngineDataHandler(IPreEngineDataHandler preEngineDataHandler) ;
	
	public String getRoleConfigPath();
	public void setRoleConfigPath(String roleConfigPath);
}
