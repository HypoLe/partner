package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 网络资源巡检众筹 主表MODEL
 * @author WANGJUN
 * 
 */
public class NetResInspect implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//工单流程ID
	private String processInstanceId;
	//工单名称
	private String theme;
	//上报日期
	private Date reportedDate;
	//上报人
	private String reportedUserId;
	//上报人部门
	private String reportedDeptId;
	//上报人电话
	private String reportedPhoneNum;
	//专业
	private String specialty;
	//资源类型
	private String resourceType;
	//问题类型
	private String questionType;
	//市
	private String city;
	//县
	private String county;
	//乡镇
	private String town;
	//街道
	private String street;
	//描述
	private String reportedDescribe;
	//定位地址
	private String locatedAddress;
	//资源名称
	private String resourceName;
	//是否我方资源
	private String isOurResources;
	//是否现场施工作业
	private String isSiteOperation;
	//紧急程度
	private String degree;
	//重要程度
	private String importance;
	//有效性
	private String validity;
	//是否隐患
	private String isHidden;
	//是否处理完成
	private String isHandleComplete;
	//综合调度人
	private String dispatcher;
	//现场核实人
	private String checker;
	//隐患处理人
	private String handlePerson;
	//状态
	private Integer state;
	//归档时间
	private Date archiveTime;
	//处理方式标识（隐患处理、预检预修、抢修）
	private String hiddenHandledFlag;
	//合算积分标识
	private String integral;
	//派发的子流程的工单号
	private String subProcessInstanceId;
	//正在运行中的任务id
	private String taskId;
	//受理人
	private String assignee;
	//工单所处环节
	private String taskDefKey;
	//环节的名称
	private String taskDefKeyName;
	//是否隐患
	private String isHiddenDanger;
	//是否建设
	private String isBuild;
	//是否线路隐患
	private String isLineHiddenDanger;
	//派发的流程标识：0抢修；1本地网
	private String autoSendProcess;
	//现场核实描述
	private String siteCheckRemark;
	//子流程派单人
	private String subSendUserId;
	//现场核实提交时间
	private Date siteCheckDate;
	private String handleDescribe;//工单处理描述
	private String isFinish;//是否处理完成
	
	private Date turnSendDate;//转派时间
	
	//手机端时间显示--start
	private String androidReportedDate ;
	private String androidArchiveTime;
	private String androidSiteCheckDate;
	private String androidTurnSendDate;
	private String androidReportedDeptId;
	private String androidSpecialty;
	private String androidResourceType;
	private String androidQuestionType;
	private String androidCity;
	private String androidCounty;
	private String androidDegree;
	private String androidImportance;
	private String androidValidity;
	private String androidIsOurResources;
	private String androidIsSiteOperation;
	
	//手机端时间显示--end

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Date getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	public String getReportedUserId() {
		return reportedUserId;
	}
	public void setReportedUserId(String reportedUserId) {
		this.reportedUserId = reportedUserId;
	}
	public String getReportedDeptId() {
		return reportedDeptId;
	}
	public void setReportedDeptId(String reportedDeptId) {
		this.reportedDeptId = reportedDeptId;
	}
	public String getReportedPhoneNum() {
		return reportedPhoneNum;
	}
	public void setReportedPhoneNum(String reportedPhoneNum) {
		this.reportedPhoneNum = reportedPhoneNum;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
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
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getReportedDescribe() {
		return reportedDescribe;
	}
	public void setReportedDescribe(String reportedDescribe) {
		this.reportedDescribe = reportedDescribe;
	}
	public String getLocatedAddress() {
		return locatedAddress;
	}
	public void setLocatedAddress(String locatedAddress) {
		this.locatedAddress = locatedAddress;
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
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	public String getIsHandleComplete() {
		return isHandleComplete;
	}
	public void setIsHandleComplete(String isHandleComplete) {
		this.isHandleComplete = isHandleComplete;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getHandlePerson() {
		return handlePerson;
	}
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getArchiveTime() {
		return archiveTime;
	}
	public void setArchiveTime(Date archiveTime) {
		this.archiveTime = archiveTime;
	}
	public String getHiddenHandledFlag() {
		return hiddenHandledFlag;
	}
	public void setHiddenHandledFlag(String hiddenHandledFlag) {
		this.hiddenHandledFlag = hiddenHandledFlag;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getSubProcessInstanceId() {
		return subProcessInstanceId;
	}
	public void setSubProcessInstanceId(String subProcessInstanceId) {
		this.subProcessInstanceId = subProcessInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getTaskDefKeyName() {
		return taskDefKeyName;
	}
	public void setTaskDefKeyName(String taskDefKeyName) {
		this.taskDefKeyName = taskDefKeyName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public String getSubSendUserId() {
		return subSendUserId;
	}
	public void setSubSendUserId(String subSendUserId) {
		this.subSendUserId = subSendUserId;
	}
	public Date getSiteCheckDate() {
		return siteCheckDate;
	}
	public void setSiteCheckDate(Date siteCheckDate) {
		this.siteCheckDate = siteCheckDate;
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
	public Date getTurnSendDate() {
		return turnSendDate;
	}
	public void setTurnSendDate(Date turnSendDate) {
		this.turnSendDate = turnSendDate;
	}
	public String getAndroidReportedDate() {
		return androidReportedDate;
	}
	public void setAndroidReportedDate(String androidReportedDate) {
		this.androidReportedDate = androidReportedDate;
	}
	public String getAndroidArchiveTime() {
		return androidArchiveTime;
	}
	public void setAndroidArchiveTime(String androidArchiveTime) {
		this.androidArchiveTime = androidArchiveTime;
	}
	public String getAndroidSiteCheckDate() {
		return androidSiteCheckDate;
	}
	public void setAndroidSiteCheckDate(String androidSiteCheckDate) {
		this.androidSiteCheckDate = androidSiteCheckDate;
	}
	public String getAndroidTurnSendDate() {
		return androidTurnSendDate;
	}
	public void setAndroidTurnSendDate(String androidTurnSendDate) {
		this.androidTurnSendDate = androidTurnSendDate;
	}
	public String getAndroidReportedDeptId() {
		return androidReportedDeptId;
	}
	public void setAndroidReportedDeptId(String androidReportedDeptId) {
		this.androidReportedDeptId = androidReportedDeptId;
	}
	public String getAndroidSpecialty() {
		return androidSpecialty;
	}
	public void setAndroidSpecialty(String androidSpecialty) {
		this.androidSpecialty = androidSpecialty;
	}
	public String getAndroidResourceType() {
		return androidResourceType;
	}
	public void setAndroidResourceType(String androidResourceType) {
		this.androidResourceType = androidResourceType;
	}
	public String getAndroidQuestionType() {
		return androidQuestionType;
	}
	public void setAndroidQuestionType(String androidQuestionType) {
		this.androidQuestionType = androidQuestionType;
	}
	public String getAndroidCity() {
		return androidCity;
	}
	public void setAndroidCity(String androidCity) {
		this.androidCity = androidCity;
	}
	public String getAndroidCounty() {
		return androidCounty;
	}
	public void setAndroidCounty(String androidCounty) {
		this.androidCounty = androidCounty;
	}
	public String getAndroidDegree() {
		return androidDegree;
	}
	public void setAndroidDegree(String androidDegree) {
		this.androidDegree = androidDegree;
	}
	public String getAndroidImportance() {
		return androidImportance;
	}
	public void setAndroidImportance(String androidImportance) {
		this.androidImportance = androidImportance;
	}
	public String getAndroidValidity() {
		return androidValidity;
	}
	public void setAndroidValidity(String androidValidity) {
		this.androidValidity = androidValidity;
	}
	public String getAndroidIsOurResources() {
		return androidIsOurResources;
	}
	public void setAndroidIsOurResources(String androidIsOurResources) {
		this.androidIsOurResources = androidIsOurResources;
	}
	public String getAndroidIsSiteOperation() {
		return androidIsSiteOperation;
	}
	public void setAndroidIsSiteOperation(String androidIsSiteOperation) {
		this.androidIsSiteOperation = androidIsSiteOperation;
	}
	
}
