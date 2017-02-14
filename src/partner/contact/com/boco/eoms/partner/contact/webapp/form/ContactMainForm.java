	/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-10-25 上午11:19:19
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.partner.contact.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

public class ContactMainForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/****************************基本信息 ************************/
	/**
	 * main id
	 */
	 private String id;
	 /**
	  * 文号
	  */
	 private String code;
	/**
	 * 发布人Id
	 */
	 private String publisherId;
	 /**
	* 发布人姓名
	 */
	 private String publisherName;
	/**
	 * 发布人部门Id
	 */
	 private String publisherDeptId;
	 /**
	  * 发布人部门名称
	  */
	 private String  publisherDeptName;
	 /**
	  * 主题
	  */
	 private String  subject;
	 /**
	  * 发布时间
	  */
	 private String  publishTime;
	 /**
	  * 处理期限
	  */
	 private String   deathTime;
	 /**
	  * 发布内容
	  */
	 private String   content;
	 /**
	  * 附件
	  */
	 private String   file;
	 /**
	  * 联系函 状态类型
	  */
	 private int type;
	 /**
	  * 是否短信通知
	  */
	 private int isSendSMS;
	 /**
	  * 是否紧急 紧急需置顶
	  */
	 private int isUrgent;
	 
	 
//	 
//	 
//	 /**
//	  * taks id
//	  */
//	 private String  taskid;
//	 /**
//	  * 任务所有者
//	  */
//	 private String    taskOwnerId;
//	 /**
//	  * 任务所有者姓名 
//	  */
//	 private String    taskOwnerName;
//	 /**
//	  * 任务所有者类型（审批人，阅知人，阅知部门，发布人）
//	  */
//	 private String    taskOwnerType;
//	 /**
//	  * 任务状态 未开始 -1，已结束 0，运行中1
//	  */
//	 private int     taskState;
//	 /**
//	  * 任务名
//	  */
//	 private String    taskName;
//	 /**
//	  * 类型  
//	  */
//	 private int    taskType;
//	 
//	 /**
//	  * 审批，处理时间
//	  */
//	 private String operationTime;
//	 /**
//	  * 审批，处理意见
//	  */
//	 private String operationContent;

	 public String getCode() {
		return code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getPublisherDeptId() {
		return publisherDeptId;
	}
	public void setPublisherDeptId(String publisherDeptId) {
		this.publisherDeptId = publisherDeptId;
	}
	public String getPublisherDeptName() {
		return publisherDeptName;
	}
	public void setPublisherDeptName(String publisherDeptName) {
		this.publisherDeptName = publisherDeptName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getDeathTime() {
		return deathTime;
	}
	public void setDeathTime(String deathTime) {
		this.deathTime = deathTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsSendSMS() {
		return isSendSMS;
	}
	public void setIsSendSMS(int isSendSMS) {
		this.isSendSMS = isSendSMS;
	}
	public int getIsUrgent() {
		return isUrgent;
	}
	public void setIsUrgent(int isUrgent) {
		this.isUrgent = isUrgent;
	}
/*	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskOwnerId() {
		return taskOwnerId;
	}
	public void setTaskOwnerId(String taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}
	public String getTaskOwnerName() {
		return taskOwnerName;
	}
	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}
	public String getTaskOwnerType() {
		return taskOwnerType;
	}
	public void setTaskOwnerType(String taskOwnerType) {
		this.taskOwnerType = taskOwnerType;
	}
	public int getTaskState() {
		return taskState;
	}
	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	*/
	 
	 
	 
	 
	 
	 
	 
	 
}
