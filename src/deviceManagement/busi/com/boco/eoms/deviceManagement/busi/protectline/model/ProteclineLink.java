package com.boco.eoms.deviceManagement.busi.protectline.model;

import com.boco.eoms.base.model.BaseObject;

public class ProteclineLink extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String id; // 主键
	private String mainId; // 主表ID
	private String mainType;//主表类型
	private String actionSendUser;// 动作发起人
	private String actionReceiveUser; // 动作接收人
	private String actionStep;// 动作发生步聚
	private String actionStepExplain;// 动作发生步聚说明
	private String actionHappenTime; //动作发生时间
	private String currentStatus; // 动作发生后的当前状态
	//以下两个字段可以表示驳回或提交的意思见和相应的附件
	//具体是"驳回"还是"提交"由页面跟具actionStep来确定界面显示字样
	private String actionIdea; // 发生该动作的意见，可复用
	private String actionAttachment; // 发生该动作所上传的附件，可复用
	
	private String remark;//备注
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ProteclineLink) {
			ProteclineLink checkPoint = (ProteclineLink) o;
			if (this.id != null || this.id.equals(checkPoint.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getActionSendUser() {
		return actionSendUser;
	}

	public void setActionSendUser(String actionSendUser) {
		this.actionSendUser = actionSendUser;
	}

	public String getActionReceiveUser() {
		return actionReceiveUser;
	}

	public void setActionReceiveUser(String actionReceiveUser) {
		this.actionReceiveUser = actionReceiveUser;
	}

	public String getActionStep() {
		return actionStep;
	}

	public void setActionStep(String actionStep) {
		this.actionStep = actionStep;
	}

	public String getActionStepExplain() {
		return actionStepExplain;
	}

	public void setActionStepExplain(String actionStepExplain) {
		this.actionStepExplain = actionStepExplain;
	}

	public String getActionHappenTime() {
		return actionHappenTime;
	}

	public void setActionHappenTime(String actionHappenTime) {
		this.actionHappenTime = actionHappenTime;
	}

	public String getActionIdea() {
		return actionIdea;
	}

	public void setActionIdea(String actionIdea) {
		this.actionIdea = actionIdea;
	}

	public String getActionAttachment() {
		return actionAttachment;
	}

	public void setActionAttachment(String actionAttachment) {
		this.actionAttachment = actionAttachment;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
}
