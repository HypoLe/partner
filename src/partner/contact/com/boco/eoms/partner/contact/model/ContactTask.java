package com.boco.eoms.partner.contact.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

	/**
 * <p>
 * Title:Task信息
 * </p>
 * <p>
 * Description:Task信息
 * </p>
 * <p>
 * Oct 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class ContactTask extends BaseObject {
	 /**
	  * 主键
	  */
	 private String  id;
	 /**  
	  * 公告Id
	  */
	 private String  mainId;
	 /**
	  * 任务所有者
	  */
	 private String    taskOwnerId;
	 /**
	  * 任务所有者姓名 
	  */
	 private String    taskOwnerName;
	 /**
	  * 任务所有者类型（审批人3，阅知人1，阅知部门2）
	  */
	 private Integer    taskOwnerType;
	 /**
	  * 任务状态 未开始 -1，已结束 0，运行中1
	  */
	 private Integer     taskState;
	 /**
	  * 任务名
	  */
	 private String    taskName;
	 /**
	  * 类型  
	  */
	 private Integer    taskType;
	 
	 /**
	  * 审批，处理时间
	  */
	 private Date operationTime;
	 /**
	  * 审批，处理意见
	  */
	 private String operationContent;
	 /**
	  * 生成task记录当前操作的LinkId
	  */
	 private String preLinkId;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	 /**
	  * 任务状态 未开始 -1，已结束 0，运行中1
	  */
	public Integer getTaskState() {
		return taskState;
	}
	 /**
	  * 任务状态 未开始 -1，已结束 0，运行中1
	  */
	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	 /**
	  * 类型  
	  */
	public Integer getTaskType() {
		return taskType;
	}
	 /**
	  * 类型  
	  */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getTaskOwnerId() {
		return taskOwnerId;
	}
	public void setTaskOwnerId(String taskOwnerId) {
		this.taskOwnerId = taskOwnerId;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	public String getTaskOwnerName() {
		return taskOwnerName;
	}
	public void setTaskOwnerName(String taskOwnerName) {
		this.taskOwnerName = taskOwnerName;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public String getOperationContent() {
		return operationContent;
	}
	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}
	public Integer getTaskOwnerType() {
		return taskOwnerType;
	}
	public void setTaskOwnerType(Integer taskOwnerType) {
		this.taskOwnerType = taskOwnerType;
	}
	public String getPreLinkId() {
		return preLinkId;
	}
	public void setPreLinkId(String preLinkId) {
		this.preLinkId = preLinkId;
	}
	 
	 
}
