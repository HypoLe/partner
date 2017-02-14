package com.boco.eoms.deviceManagement.qualify.model;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class TaskOrderForm extends BaseForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private String type;
	private String netGroup;
	private String attachment;
	private String sendTo;
	private String sendTo2;
	private String deadline;
	private String description;
	private String satisfiedLevel; 
	private String remarks1;
	private String remarks2;
	private String remarks3;
	private String status;
	private FormFile formFile;
	private String replayMsg;
	private String endMsg;

	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setNetGroup(String netGroup) {
		this.netGroup = netGroup;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public void setSendTo2(String sendTo2) {
		this.sendTo2 = sendTo2;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public String getTopic() {
		return topic;
	}
	public String getType() {
		return type;
	}
	public String getNetGroup() {
		return netGroup;
	}
	public String getAttachment() {
		return attachment;
	}
	public String getSendTo() {
		return sendTo;
	}
	public String getSendTo2() {
		return sendTo2;
	}
	public String getDeadline() {
		return deadline;
	}
	public String getDescription() {
		return description;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	public String getRemarks2() {
		return remarks2;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setSatisfiedLevel(String satisfiedLevel) {
		this.satisfiedLevel = satisfiedLevel;
	}
	public String getSatisfiedLevel() {
		return satisfiedLevel;
	}
	public void setRemarks3(String remarks3) {
		this.remarks3 = remarks3;
	}
	public String getRemarks3() {
		return remarks3;
	}
	public void setFormFile(FormFile formFile) {
		this.formFile = formFile;
	}
	public FormFile getFormFile() {
		return formFile;
	}
	public void setReplayMsg(String replayMsg) {
		this.replayMsg = replayMsg;
	}
	public String getReplayMsg() {
		return replayMsg;
	}
	public void setEndMsg(String endMsg) {
		this.endMsg = endMsg;
	}
	public String getEndMsg() {
		return endMsg;
	}
	
	

}
