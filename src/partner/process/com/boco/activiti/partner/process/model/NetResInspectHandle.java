package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 网络资源巡检众筹 流转信息表MODEL
 * @author WANGJUN
 * 
 */
public class NetResInspectHandle implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//工单流程id
	private String processInstanceId;
	//审批时间
	private Date checkTime;
	//审批人
	private String userId;
	//环节标识
	private String linkFlag;
	//备注（处理描述）
	private String remark;
	//手机端、web端操作标识
	private String operationFlag;
	//任务ID
	private String taskId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLinkFlag() {
		return linkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
