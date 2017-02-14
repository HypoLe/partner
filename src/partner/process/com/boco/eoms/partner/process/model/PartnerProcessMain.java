package com.boco.eoms.partner.process.model;

public class PartnerProcessMain {
	private String id;//主键uuid
	private String currentState;//最新状态
	private String changeAttachment ;//变更附件
	private String startTime;//发起时间
	private String endTime;//终结时间
	private String createUser;//变更申请创建人
	private String referenceModel;//仪器仪表、车辆管理、油机信息管理、移动终端管理
	private String changeType;//变更类型:增加、删除、修改（字典值）
	private String deleted;//"1"为删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	
	public String getChangeAttachment() {
		return changeAttachment;
	}
	public void setChangeAttachment(String changeAttachment) {
		this.changeAttachment = changeAttachment;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getReferenceModel() {
		return referenceModel;
	}
	public void setReferenceModel(String referenceModel) {
		this.referenceModel = referenceModel;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
