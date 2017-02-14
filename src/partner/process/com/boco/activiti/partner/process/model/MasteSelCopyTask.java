package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class MasteSelCopyTask implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String quotaField;
	private String existquotaValue;
	private String assistExistquotaValue;
	private String childSceneId;
	private String processInstanceId;
	
	private int seq;
	
	//环节类型
	private String linkType;
		
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuotaField() {
		return quotaField;
	}
	public void setQuotaField(String quotaField) {
		this.quotaField = quotaField;
	}
	public String getExistquotaValue() {
		return existquotaValue;
	}
	public void setExistquotaValue(String existquotaValue) {
		this.existquotaValue = existquotaValue;
	}
	public String getAssistExistquotaValue() {
		return assistExistquotaValue;
	}
	public void setAssistExistquotaValue(String assistExistquotaValue) {
		this.assistExistquotaValue = assistExistquotaValue;
	}
	public String getChildSceneId() {
		return childSceneId;
	}
	public void setChildSceneId(String childSceneId) {
		this.childSceneId = childSceneId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public MasteSelCopyTask() {
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	
	
	
}
