package com.boco.eoms.partner.property.model;

import java.util.Date;

public class PnrRemindMsg {
	/**主键id*/
	private String id;
	/**提示内容*/
	private String content;
	/**发送时间*/
	private Date sendTime;
	/**阅读人员*/
	private String readUser;
	/**是否阅读*/
	private String isRead;
	/**删除标志*/
	private String deletedFlag;
	/**提醒对象*/
	private String remindObject;
	/**合同、账单的id*/
	private String refId;
	/**类型：是合同agreement或者是费用bills*/
	private String type;
	/**阅读时间*/
	private String readTime;
	/**创建时间*/
	private String creatTime;
	/**备注*/
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public String getRemindObject() {
		return remindObject;
	}
	public void setRemindObject(String remindObject) {
		this.remindObject = remindObject;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getReadUser() {
		return readUser;
	}
	public void setReadUser(String readUser) {
		this.readUser = readUser;
	}
	
}
