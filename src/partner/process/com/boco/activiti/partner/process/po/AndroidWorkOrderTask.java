package com.boco.activiti.partner.process.po;

public class AndroidWorkOrderTask {
	/**流程taskID*/
	private String taskWworkOrderId;
	
	/**工单所处环节*/
	private String taskDefinitionKey;
	
	private String tcountry;
	
	private String tdate;
	
	/**工单流程号*/
	private String processInstanceId;	

	//工单主题
	private String theme;
	
	public String getTcountry() {
		return tcountry;
	}
	public void setTcountry(String tcountry) {
		this.tcountry = tcountry;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getTaskWworkOrderId() {
		return taskWworkOrderId;
	}
	public void setTaskWworkOrderId(String taskWworkOrderId) {
		this.taskWworkOrderId = taskWworkOrderId;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
}
