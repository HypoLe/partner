package com.boco.eoms.partner.process.test;

import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class Initialization implements JavaDelegate{
	
	private static final Logger log=Logger.getLogger(Initialization.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("variavles="+execution.getVariables());
		execution.setVariable("m:i","Mainprocess:Initialization");
		log.info("I am Initialization in mainprocess");
		
		execution.setVariable("varOutFromMainprocess","dy-dc-whzx");
		log.info("in mainprocess set(varOutFromMainprocess)"+execution.getVariable("varOutFromMainprocess"));
	}

}
