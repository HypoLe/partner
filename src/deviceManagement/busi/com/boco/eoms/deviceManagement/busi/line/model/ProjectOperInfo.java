package com.boco.eoms.deviceManagement.busi.line.model;

public class ProjectOperInfo {
	
	private String id;
	private String projectId;
	private String operator;
	private String operateTime;
	private String nextOperator;
	private String operateRemarks;
	private String operateType;

	public ProjectOperInfo() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNextOperator() {
		return nextOperator;
	}

	public void setNextOperator(String nextOperator) {
		this.nextOperator = nextOperator;
	}

	public String getOperateRemarks() {
		return operateRemarks;
	}

	public void setOperateRemarks(String operateRemarks) {
		this.operateRemarks = operateRemarks;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateTime() {
		return operateTime;
	}
	
	

}
