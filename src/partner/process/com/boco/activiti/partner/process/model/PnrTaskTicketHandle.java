package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务工单处理
 * 
 *
 */
public class PnrTaskTicketHandle  implements Serializable{
	
	private String id;
	//工单ID
	private String themeId;
	//工单主题
	private String theme;
	//回单时间
	private Date receiveTime;
	//处理开始时间
	private Date dealStartTime;
	//处理结束时间
	private Date dealEndTime;
	//回单人
	private String taskAssignee;
	//处理人
	private String doPersons;
	//审核人
	private String auditor;
	//完成情况
	private String handleDescription;
	//交通方式
	private String transport;
	
	//里程
	private Double mileage;
	
	//工单流程ID
	private String processInstanceId;
	//审批状态  throughTheAudit：通过；auditDismissed：未通过。
	private String state ;
	
	//审核意见
	private String opinion;
	//审批时间
	private Date checkTime;
	
	//附件
	private String sheetAccessories;
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getThemeId() {
		return themeId;
	}



	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}



	public String getTheme() {
		return theme;
	}



	public void setTheme(String theme) {
		this.theme = theme;
	}



	public Date getReceiveTime() {
		return receiveTime;
	}



	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}




	public Date getDealStartTime() {
		return dealStartTime;
	}



	public void setDealStartTime(Date dealStartTime) {
		this.dealStartTime = dealStartTime;
	}



	public Date getDealEndTime() {
		return dealEndTime;
	}



	public void setDealEndTime(Date dealEndTime) {
		this.dealEndTime = dealEndTime;
	}



	public String getTaskAssignee() {
		return taskAssignee;
	}



	public void setTaskAssignee(String taskAssignee) {
		this.taskAssignee = taskAssignee;
	}



	public String getAuditor() {
		return auditor;
	}



	public void setAuditor(String auditor) {
		auditor = auditor;
	}



	public String getHandleDescription() {
		return handleDescription;
	}



	public void setHandleDescription(String handleDescription) {
		this.handleDescription = handleDescription;
	}



	public String getTransport() {
		return transport;
	}



	public void setTransport(String transport) {
		this.transport = transport;
	}



	public Double getMileage() {
		return mileage;
	}



	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}



	public String getProcessInstanceId() {
		return processInstanceId;
	}



	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getOpinion() {
		return opinion;
	}



	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}



	
	public Date getCheckTime() {
		return checkTime;
	}



	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}



	public String getDoPersons() {
		return doPersons;
	}



	public void setDoPersons(String doPersons) {
		this.doPersons = doPersons;
	}

	

	public String getSheetAccessories() {
		return sheetAccessories;
	}



	public void setSheetAccessories(String sheetAccessories) {
		this.sheetAccessories = sheetAccessories;
	}



	public PnrTaskTicketHandle() {
		
	}
	
	
	
	
}
