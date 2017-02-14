package com.boco.eoms.partner.process.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

public class MainprocessTest extends AbstractTest {

	@Override
	protected void initialize() throws Exception {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("diagrams/Subprocess.Check.bpmn20.xml")
				.deploy();
		deploymentIdList.add(deployment.getId());
		
		deployment = repositoryService.createDeployment()
		.addClasspathResource("diagrams/Subprocess.Mainprocess.bpmn20.xml")
		.deploy();
		deploymentIdList.add(deployment.getId());
	}

	@Override
	protected void destroy() throws Exception {
		for(String deployment:deploymentIdList){
			repositoryService.deleteDeployment(deployment,true);
		}
	}
	
	public void testSubProcess(){
		//prepare data packet
		Map<String,Object> variables=new HashMap<String,Object>();
		Map<String,Object> subVariables=new HashMap<String,Object>();
		variables.put("protocol", "UM32");
		variables.put("repository", "10.10.38.99:/home/shirdn/repository");
		variables.put("in", subVariables);
		variables.put("out", new HashMap<String,Object>());
		
		ProcessInstance pi=runtimeService.startProcessInstanceByKey("Mainprocess",variables);
		
		assertEquals(true,pi.isEnded());
		
	}

}
