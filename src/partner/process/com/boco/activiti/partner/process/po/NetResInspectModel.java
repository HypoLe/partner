package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 网络资源巡检众筹 MODEL
 * 
 */
public class NetResInspectModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//工单号
	private String processInstanceId;
	//工单名称
	private String theme;
	//派单时间
	private Date reportedDate;
	//问题类型
	private String questionType;
	//资源类型
	private String resourceType;
	//地市
	private String city;
	//区县
	private String county;
	//专业
	private String specialty;
	//环节
	private String taskDefKeyName;
	//任务id
	private String taskId;
	//派发流程标识
	private String autoSendProcess;
	//子流程名
	private String subProcessName;
	//子流程id号
	private String subProcessInstanceId;
	
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
	public Date getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getTaskDefKeyName() {
		return taskDefKeyName;
	}
	public void setTaskDefKeyName(String taskDefKeyName) {
		this.taskDefKeyName = taskDefKeyName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getAutoSendProcess() {
		return autoSendProcess;
	}
	public void setAutoSendProcess(String autoSendProcess) {
		this.autoSendProcess = autoSendProcess;
	}
	public String getSubProcessName() {
		return subProcessName;
	}
	public void setSubProcessName(String subProcessName) {
		this.subProcessName = subProcessName;
	}
	public String getSubProcessInstanceId() {
		return subProcessInstanceId;
	}
	public void setSubProcessInstanceId(String subProcessInstanceId) {
		this.subProcessInstanceId = subProcessInstanceId;
	}
}
