package com.boco.eoms.partner.taskManager.model;

/**
 * 类说明：车辆审批Task表
 * 创建人： liaojiming
 * 创建时间：2013-05-23
 */
public class CarApproveTask {

	/**主键*/
	private String id;
	/**审批Id*/
	private String carApproveId;
	/**任务拥有者*/
	private String taskUser;
	/**状态*/
	private Integer statue; //(0.代办 1.也办)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarApproveId() {
		return carApproveId;
	}
	public void setCarApproveId(String carApproveId) {
		this.carApproveId = carApproveId;
	}
	public String getTaskUser() {
		return taskUser;
	}
	public void setTaskUser(String taskUser) {
		this.taskUser = taskUser;
	}
	public Integer getStatue() {
		return statue;
	}
	public void setStatue(Integer statue) {
		this.statue = statue;
	}
	
	
}
