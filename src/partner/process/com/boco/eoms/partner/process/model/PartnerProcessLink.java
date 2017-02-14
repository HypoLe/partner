package com.boco.eoms.partner.process.model;

public class PartnerProcessLink {
	private String id;//uuid
	private String referenceId;//PartnerProcessLink主外键关联
	private String state;//变更状态：审核中、通过审核、驳回（字段值）
	private String dataReceiver;//数据接受者
	private String dataSender;//数据发起者
	private String createTime;//记录产生时间
	private String reason;//审批意见
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDataReceiver() {
		return dataReceiver;
	}
	public void setDataReceiver(String dataReceiver) {
		this.dataReceiver = dataReceiver;
	}
	public String getDataSender() {
		return dataSender;
	}
	public void setDataSender(String dataSender) {
		this.dataSender = dataSender;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
