package com.boco.eoms.partner.home.model;

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
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class PublishTask extends BaseObject {
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
	  * 任务状态 未开始 -1，已结束 0，运行中1
	  */
	 private Integer     taskState;
	 /**
	  * 任务名
	  */
	 private String    taskName;
//	 /**
//	  * 类型 不能查看-1，阅知0，审批1，待查看 2
//	  */
	 //modify:任务操作类型。 有的任务操作类型要和任务状态结合起来使用。
	 private Integer    taskType;
	 
	 private Date    operateTime;
	 private String    operateContent;
	 /**task是由哪个PRELINKID操作而产生的*/
	 private String    prelinkid;
	 
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
	  * 类型 不能查看-1，阅知0，审批1，待查看 2
	  */
	public Integer getTaskType() {
		return taskType;
	}
	 /**
	  * 类型 不能查看-1，阅知0，审批1，待查看 2
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
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateContent() {
		return operateContent;
	}
	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	public String getPrelinkid() {
		return prelinkid;
	}
	public void setPrelinkid(String prelinkid) {
		this.prelinkid = prelinkid;
	}
	 
	 
}
