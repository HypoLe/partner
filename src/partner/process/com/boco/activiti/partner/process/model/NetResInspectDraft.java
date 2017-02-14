package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 草稿表
 * 
 * @author WANGJUN
 * 
 */
public class NetResInspectDraft implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

	// 工单主题
	private String theme;

	// 派单人
	private String initiator;

	// 发起人部门
	private String initiatorDept;

	// 故障处理时限
	private Double processLimit;

	// 故障来源
	private String faultSource;

	// 故障发生时间
	private Date createTime;

	// 工单子类型
	private String subType;

	// 故障联系人+手机号
	private String connectPerson;

	// 故障描述
	private String faultDescription;
	
	//工单号
	private String processInstanceId;
	
	//任务ID
	private String taskId;
	
	//流程类型
	private String processType;
	
	//众筹流程ID（父流程ID）
	private String inspectProcessInstanceId;
	
	//资源地址
	private String locatedAddress;
	
	//地市
	private String city;
	//区县
	private String country;
	//紧急程度
	private String degree;
	//重要程度
	private String importance;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getInitiatorDept() {
		return initiatorDept;
	}

	public void setInitiatorDept(String initiatorDept) {
		this.initiatorDept = initiatorDept;
	}

	public Double getProcessLimit() {
		return processLimit;
	}

	public void setProcessLimit(Double processLimit) {
		this.processLimit = processLimit;
	}

	public String getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getConnectPerson() {
		return connectPerson;
	}

	public void setConnectPerson(String connectPerson) {
		this.connectPerson = connectPerson;
	}

	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getInspectProcessInstanceId() {
		return inspectProcessInstanceId;
	}

	public void setInspectProcessInstanceId(String inspectProcessInstanceId) {
		this.inspectProcessInstanceId = inspectProcessInstanceId;
	}

	public String getLocatedAddress() {
		return locatedAddress;
	}

	public void setLocatedAddress(String locatedAddress) {
		this.locatedAddress = locatedAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}
	
}
