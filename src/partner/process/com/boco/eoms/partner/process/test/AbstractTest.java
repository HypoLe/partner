package com.boco.eoms.partner.process.test;

import java.util.List;

import junit.framework.TestCase;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.eoms.base.util.ApplicationContextHolder;

public abstract class AbstractTest extends TestCase {
	private ProcessEngine processEngine;
	protected String deploymentId;
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected TaskService taskService;
	protected FormService formService;
	protected HistoryService historyService;
	protected IdentityService identityService;
	protected ManagementService managementService;
	protected List<String> deploymentIdList;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (processEngine == null) {
			//processEngine = (ProcessEngine) ApplicationContextHolder.getInstance().getBean("processEngine");
			processEngine = ProcessEngines.getDefaultProcessEngine();
		}
		repositoryService = (RepositoryService) ApplicationContextHolder.getInstance().getBean("repositoryService");
		//repositoryService = processEngine.getRepositoryService();
		runtimeService = (RuntimeService) ApplicationContextHolder.getInstance().getBean("runtimeService");
		//runtimeService = processEngine.getRuntimeService();
		taskService =(TaskService)ApplicationContextHolder.getInstance().getBean("taskService");
		//taskService = processEngine.getTaskService();
		formService =(FormService)ApplicationContextHolder.getInstance().getBean("formService");
		//formService = processEngine.getFormService();
		historyService =(HistoryService)ApplicationContextHolder.getInstance().getBean("historyService");
		//historyService = processEngine.getHistoryService();
		identityService =(IdentityService)ApplicationContextHolder.getInstance().getBean("identityService");
		//identityService = processEngine.getIdentityService();
		managementService =(ManagementService)ApplicationContextHolder.getInstance().getBean("managementService");
		//managementService = processEngine.getManagementService();
		initialize();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		destroy();
	}

	protected abstract void initialize() throws Exception;

	protected abstract void destroy() throws Exception;
}
