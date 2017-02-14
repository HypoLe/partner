package com.boco.eoms.partner.home.webapp.form;

import java.util.Date;

import com.boco.eoms.base.webapp.form.BaseForm;
	/**
 * <p>
 * Title:公告基本信息
 * </p>
 * <p>
 * Description:公告基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class PublishForm extends BaseForm {
	/**
	 * 公告信息Id
	 */
	private String id;
	//**********************************Info*********************************
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
	 private Date    publishTime;
	 /**
	  * 内容
	  */
	 private String    publishContent;
	 /**
	  * 有效日期
	  */
	 private Date    valid;
	 /**
	  * 是否需要审批  是1 否0
	  */
	 private int  	   isAudit;
	 private String auditorid;
	 private String auditorname;
	 
	 private String  publishedRange;
	 private String  publishedRangeName;
	 
	/**
	  * 附件
	  */
	 private String  	   file;
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
   private int  	   type;
//---------------------------------------------Task-----------------------------------------------   
	 /**
	  * 任务所有者
	  */
	 private String    taskOwnerId;
	 /**
	  * 任务所有者 姓名
	  */
	 private String    taskOwnerName;
	 
	 /**
	  * 任务状态 未开始 -1，已结束 0，运行中1
	  */
	 private int     taskState;
	 /**
	  * 任务名
	  */
	 private String    taskName;
	 /**
	  * 类型 不能查看-1，阅知0，审批1，待查看 2
	  */
	 private int    taskType;
   
	 private Date    taskOperateTime;
	 private String    taskOperateContent;
	 /**task是由哪个PRELINKID操作而产生的*/
	 private String    taskPrelinkid;
//-------------------------------------------------Link------------------------------------------
		 /**
		  *   处理结果 ：作废 -1，草稿0，送审1，通过提交下一审批2，通过3，驳回4，阅知5
		  */
		 private int   operateType;
		 /**
		  * 审批结果 ：不通过 0、通过 1、通过提交下一审批 2
		  */
		 private String operateName;
		 
		 private int operateResult;;
		 /**
		  * 审批意见
		  */
		 private String operateContent;;
		 /**
		  * 操作时间
		  */
		 private String operateTime;
	 
		 private String operateUserid;
		 private String operateDeptid;
		 private String operateDeptname;
   
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getPublishContent() {
		return publishContent;
	}
	public void setPublishContent(String publishContent) {
		this.publishContent = publishContent;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public int getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}
	public String getTaskOwnerName() {
		return taskOwnerName;
	}
	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}
	 
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
	public int getType() {
		return type;
	}
	 /**
	  * 状态 ：作废 -1，草稿 0 ，审批中 1，发布中2，被驳回3，阅知4
	  */
	public void setType(int type) {
		this.type = type;
	}
	public String getTaskOwnerId() {
		return taskOwnerId;
	}
	public void setTaskOwnerId(String taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
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
	public int getOperateType() {
		return operateType;
	}
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}
	 
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublishedRange() {
		return publishedRange;
	}
	public void setPublishedRange(String publishedRange) {
		this.publishedRange = publishedRange;
	}
	public String getPublishedRangeName() {
		return publishedRangeName;
	}
	public void setPublishedRangeName(String publishedRangeName) {
		this.publishedRangeName = publishedRangeName;
	}
	public Date getTaskOperateTime() {
		return taskOperateTime;
	}
	public void setTaskOperateTime(Date taskOperateTime) {
		this.taskOperateTime = taskOperateTime;
	}
	public String getTaskOperateContent() {
		return taskOperateContent;
	}
	public void setTaskOperateContent(String taskOperateContent) {
		this.taskOperateContent = taskOperateContent;
	}
	public String getTaskPrelinkid() {
		return taskPrelinkid;
	}
	public void setTaskPrelinkid(String taskPrelinkid) {
		this.taskPrelinkid = taskPrelinkid;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public int getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(int operateResult) {
		this.operateResult = operateResult;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getOperateUserid() {
		return operateUserid;
	}
	public void setOperateUserid(String operateUserid) {
		this.operateUserid = operateUserid;
	}
	public String getOperateDeptid() {
		return operateDeptid;
	}
	public void setOperateDeptid(String operateDeptid) {
		this.operateDeptid = operateDeptid;
	}
	public String getOperateDeptname() {
		return operateDeptname;
	}
	public void setOperateDeptname(String operateDeptname) {
		this.operateDeptname = operateDeptname;
	}
	public String getAuditorid() {
		return auditorid;
	}
	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}
	public String getAuditorname() {
		return auditorname;
	}
	public void setAuditorname(String auditorname) {
		this.auditorname = auditorname;
	}
	 
}
