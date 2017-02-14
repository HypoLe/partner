package com.boco.eoms.deviceManagement.externalForceCheck.model;

public class ExternalForceCheckSublist {
	private String id;
	private String executeUser;//现场盯防人
	private String dutyStartTime;//值班开始时间
	private String dutyEndTime;//值班结束时间
	private String dutyDiary;//值班日志
	private String safety;//是否影响光缆安全
	private String dutySpace;//值班时间间隔
	private String dutyError;//值班时间误差	
	private String planId;//关联的值班计划
	private Integer inOrder;//日志的排序，方便计算间隔、误差等。
	private String dutyStatus;//状态
	
	
	
	
	public Integer getInOrder() {
		return inOrder;
	}
	public void setInOrder(Integer inOrder) {
		this.inOrder = inOrder;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDutyDiary() {
		return dutyDiary;
	}
	public void setDutyDiary(String dutyDiary) {
		this.dutyDiary = dutyDiary;
	}
	public String getDutyEndTime() {
		return dutyEndTime;
	}
	public void setDutyEndTime(String dutyEndTime) {
		this.dutyEndTime = dutyEndTime;
	}
	public String getDutyError() {
		return dutyError;
	}
	public void setDutyError(String dutyError) {
		this.dutyError = dutyError;
	}
	public String getDutySpace() {
		return dutySpace;
	}
	public void setDutySpace(String dutySpace) {
		this.dutySpace = dutySpace;
	}
	public String getDutyStartTime() {
		return dutyStartTime;
	}
	public void setDutyStartTime(String dutyStartTime) {
		this.dutyStartTime = dutyStartTime;
	}
	public String getDutyStatus() {
		return dutyStatus;
	}
	public void setDutyStatus(String dutyStatus) {
		this.dutyStatus = dutyStatus;
	}
	public String getExecuteUser() {
		return executeUser;
	}
	public void setExecuteUser(String executeUser) {
		this.executeUser = executeUser;
	}
	public String getSafety() {
		return safety;
	}
	public void setSafety(String safety) {
		this.safety = safety;
	}
	
	
}
