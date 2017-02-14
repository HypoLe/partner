package com.boco.eoms.partner.taskManager.model;

/**
 * 类说明：车辆任务表
 * 创建人： liaojiming
 * 创建时间：2013-05-23
 */
public class CarTask {

	/**主键*/
	private String id;
	/**车牌号*/
	private String carNum;
	/**审批Id*/
	private String carApproveId;
	/**任务状态*/
	private Integer taskStatue;
	/**任务Id*/
	private String taskId;
	/**任务类型*/
	private String taskType;
	/***/
	private String taskName;
	/**申请人也是当前使用人*/
	private String carUser;
	
	public String getCarUser() {
		return carUser;
	}
	public void setCarUser(String carUser) {
		this.carUser = carUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getCarApproveId() {
		return carApproveId;
	}
	public void setCarApproveId(String carApproveId) {
		this.carApproveId = carApproveId;
	}
	public Integer getTaskStatue() {
		return taskStatue;
	}
	public void setTaskStatue(Integer taskStatue) {
		this.taskStatue = taskStatue;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
}
