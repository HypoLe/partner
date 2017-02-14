package com.boco.eoms.sheet.base.service.impl;

import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.IPreEngineDataHandler;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ITaskService;

/** 
 * Description: 工单业务层门面模式接口实现 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 12, 2012 9:35:48 AM 
 */
public class SheetFacadeServiceImpl implements ISheetFacadeService {

	private IMainService mainService;
	private ILinkService linkService;
	private ITaskService taskService;
	protected IBusinessFlowService businessFlowService;
//	private ITask sheetTask;
	private String flowTemplateName;
	private String roleConfigPath;
	private IPreEngineDataHandler preEngineDataHandler;

	public ITaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public String getFlowTemplateName() {
		return flowTemplateName;
	}

	public void setFlowTemplateName(String flowTemplateName) {
		this.flowTemplateName = flowTemplateName;
	}
	
	public IBusinessFlowService getBusinessFlowService() {
		return businessFlowService;
	}

	public void setBusinessFlowService(IBusinessFlowService businessFlowService) {
		this.businessFlowService = businessFlowService;
	}

//	public ITask getSheetTask() {
//		return sheetTask;
//	}
//
//	public void setSheetTask(ITask sheetTask) {
//		this.sheetTask = sheetTask;
//	}

	public ILinkService getLinkService() {
		return linkService;
	}

	public void setLinkService(ILinkService linkService) {
		this.linkService = linkService;
	}

	public IMainService getMainService() {
		return mainService;
	}

	public void setMainService(IMainService mainService) {
		this.mainService = mainService;
	}

	public IPreEngineDataHandler getPreEngineDataHandler() {
		return preEngineDataHandler;
	}
	public void setPreEngineDataHandler(IPreEngineDataHandler preEngineDataHandler) {
		this.preEngineDataHandler = preEngineDataHandler;
	}
	public String getRoleConfigPath() {
		return roleConfigPath;
	}

	public void setRoleConfigPath(String roleConfigPath) {
		this.roleConfigPath = roleConfigPath;
	}
	
	

}
