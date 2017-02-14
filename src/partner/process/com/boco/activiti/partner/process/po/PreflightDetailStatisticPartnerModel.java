package com.boco.activiti.partner.process.po;

import java.util.Date;

/**
 * User: wangchang
 * Date: 13-9-17
 * Time: 下午2:45
 */
public class PreflightDetailStatisticPartnerModel {
   private String city;
   private String country;
   private String processinstanceid;
   private String sheetid;
   private String theme;
   private String bearService;//承载业务级别
   private String workorderTypeName;//工单类型
   private String subTypeName;//工单子类型
   private String keyWord;//关键字
   private String workOrderDegree;//紧急程度
   private String faultDescription;//项目实施内容描述
   private String projectAmount;//项目金额
   private Date submitApplicationTime;//申请提交时间
   private String name;//审批环节
   private Date endTime;//批复日期
   private String rowNum;//
   private String expertOpinion;//专家组意见
   private String reviewResult;//会审结果
   private String practice;//是否同意实施
   private Date distributedInterfaceTime;//省公司批复日期
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
public String getProcessinstanceid() {
	return processinstanceid;
}
public void setProcessinstanceid(String processinstanceid) {
	this.processinstanceid = processinstanceid;
}
public String getTheme() {
	return theme;
}
public void setTheme(String theme) {
	this.theme = theme;
}
public String getBearService() {
	return bearService;
}
public void setBearService(String bearService) {
	this.bearService = bearService;
}
public String getWorkorderTypeName() {
	return workorderTypeName;
}
public void setWorkorderTypeName(String workorderTypeName) {
	this.workorderTypeName = workorderTypeName;
}
public String getSubTypeName() {
	return subTypeName;
}
public void setSubTypeName(String subTypeName) {
	this.subTypeName = subTypeName;
}
public String getKeyWord() {
	return keyWord;
}
public void setKeyWord(String keyWord) {
	this.keyWord = keyWord;
}
public String getWorkOrderDegree() {
	return workOrderDegree;
}
public void setWorkOrderDegree(String workOrderDegree) {
	this.workOrderDegree = workOrderDegree;
}
public String getFaultDescription() {
	return faultDescription;
}
public void setFaultDescription(String faultDescription) {
	this.faultDescription = faultDescription;
}
public String getProjectAmount() {
	return projectAmount;
}
public void setProjectAmount(String projectAmount) {
	this.projectAmount = projectAmount;
}
public Date getSubmitApplicationTime() {
	return submitApplicationTime;
}
public void setSubmitApplicationTime(Date submitApplicationTime) {
	this.submitApplicationTime = submitApplicationTime;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Date getEndTime() {
	return endTime;
}
public void setEndTime(Date endTime) {
	this.endTime = endTime;
}
public String getRowNum() {
	return rowNum;
}
public void setRowNum(String rowNum) {
	this.rowNum = rowNum;
}
public String getExpertOpinion() {
	return expertOpinion;
}
public void setExpertOpinion(String expertOpinion) {
	this.expertOpinion = expertOpinion;
}
public String getReviewResult() {
	return reviewResult;
}
public void setReviewResult(String reviewResult) {
	this.reviewResult = reviewResult;
}
public String getPractice() {
	return practice;
}
public void setPractice(String practice) {
	this.practice = practice;
}
public Date getDistributedInterfaceTime() {
	return distributedInterfaceTime;
}
public void setDistributedInterfaceTime(Date distributedInterfaceTime) {
	this.distributedInterfaceTime = distributedInterfaceTime;
}
public String getSheetid() {
	return sheetid;
}
public void setSheetid(String sheetid) {
	this.sheetid = sheetid;
}
   
   
}
