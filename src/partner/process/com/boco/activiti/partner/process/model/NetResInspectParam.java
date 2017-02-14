package com.boco.activiti.partner.process.model;

import java.io.Serializable;

import com.boco.eoms.base.util.StaticMethod;

/**
 * 
 * 网络资源巡检众筹 用于ACTION和SERVICE之间传递参数的Model类
 * @author WANGJUN
 * 
 */
public class NetResInspectParam implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userId; //当前登录用户
	
	private String processInstanceId; //流程号
	
	private String taskId; //任务id
	
	private String resourceName; //资源名称
	
	private String isOurResources;//是否我方资源
	
	private String isSiteOperation;//是否现场施工作业
	
	private String degree;//紧急程度
	
	private String validity;//有效性
	
	private String importance;//重要程度
	
	private String isHiddenDanger;//是否隐患
	
	private String isBuild;//是否建设
	
	private String isLineHiddenDanger;//是否线路隐患
	
	private String autoSendProcess;//派发流程
	
	private String siteCheckRemark;//现场核实环节描述
	
	private String specialty; //专业
	
	private String handleDescribe;//工单处理描述
	
	private String isFinish;//是否处理完成
	
	private String isTurnSend;//是否转派
	
	private String isSendType;//转派类型
	private String country;//区县

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getIsOurResources() {
		return isOurResources;
	}

	public void setIsOurResources(String isOurResources) {
		this.isOurResources = isOurResources;
	}

	public String getIsSiteOperation() {
		return isSiteOperation;
	}

	public void setIsSiteOperation(String isSiteOperation) {
		this.isSiteOperation = isSiteOperation;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getIsHiddenDanger() {
		return isHiddenDanger;
	}

	public void setIsHiddenDanger(String isHiddenDanger) {
		this.isHiddenDanger = isHiddenDanger;
	}

	public String getIsBuild() {
		return isBuild;
	}

	public void setIsBuild(String isBuild) {
		this.isBuild = isBuild;
	}

	public String getIsLineHiddenDanger() {
		return isLineHiddenDanger;
	}

	public void setIsLineHiddenDanger(String isLineHiddenDanger) {
		this.isLineHiddenDanger = isLineHiddenDanger;
	}

	public String getAutoSendProcess() {
		return autoSendProcess;
	}

	public void setAutoSendProcess(String autoSendProcess) {
		this.autoSendProcess = autoSendProcess;
	}

	public String getSiteCheckRemark() {
		return siteCheckRemark;
	}

	public void setSiteCheckRemark(String siteCheckRemark) {
		this.siteCheckRemark = siteCheckRemark;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getHandleDescribe() {
		return handleDescribe;
	}

	public void setHandleDescribe(String handleDescribe) {
		this.handleDescribe = handleDescribe;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	public String getIsTurnSend() {
		return isTurnSend;
	}

	public void setIsTurnSend(String isTurnSend) {
		this.isTurnSend = isTurnSend;
	}

	public String getIsSendType() {
		return isSendType;
	}

	public void setIsSendType(String isSendType) {
		this.isSendType = isSendType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
