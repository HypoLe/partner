package com.boco.eoms.partner.inspect.webapp.form;

import java.sql.Timestamp;

public class InspectPlanItemCountFrom {
	/**
	 * 巡检项ID
	 */
	private Integer inspectPlanItemId;
	/**
	 * 所属元任务ID
	 */
	private Long inspectPlanResId;
	/**
	 * 巡检项
	 */
	private String inspectItem;

	/**
	 * 巡检内容
	 */
	private String content;
	/**
	 * 是否处理
	 */
	private Integer isHandle;
	/**
	 * 处理时间
	 */
	private Timestamp handleTimestamp;
	/**
	 * 处理人员ID
	 */
	private String handleUserId;
	/**
	 * 处理人员名称
	 */
	private String handleUsername;
	/**
	 * 异常内容
	 */
	private String exceptionContent;
	/**
	 * 异常标识 0异常,1正常 -1无设备
	 */
	private Integer exceptionFlag;

	public String getInspectItem() {
		return inspectItem;
	}

	public void setInspectItem(String inspectItem) {
		this.inspectItem = inspectItem;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
	}

	public Timestamp getHandleTimestamp() {
		return handleTimestamp;
	}

	public void setHandleTimestamp(Timestamp handleTimestamp) {
		this.handleTimestamp = handleTimestamp;
	}

	public String getHandleUserId() {
		return handleUserId;
	}

	public void setHandleUserId(String handleUserId) {
		this.handleUserId = handleUserId;
	}

	public String getHandleUsername() {
		return handleUsername;
	}

	public void setHandleUsername(String handleUsername) {
		this.handleUsername = handleUsername;
	}

	public String getExceptionContent() {
		return exceptionContent;
	}

	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}

	public Integer getExceptionFlag() {
		return exceptionFlag;
	}

	public void setExceptionFlag(Integer exceptionFlag) {
		this.exceptionFlag = exceptionFlag;
	}

	public Integer getInspectPlanItemId() {
		return inspectPlanItemId;
	}

	public void setInspectPlanItemId(Integer inspectPlanItemId) {
		this.inspectPlanItemId = inspectPlanItemId;
	}

	public Long getInspectPlanResId() {
		return inspectPlanResId;
	}

	public void setInspectPlanResId(Long inspectPlanResId) {
		this.inspectPlanResId = inspectPlanResId;
	}


}
