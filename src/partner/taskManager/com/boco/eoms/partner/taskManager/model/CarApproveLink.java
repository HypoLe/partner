package com.boco.eoms.partner.taskManager.model;

import java.util.Date;

/**
 * 类说明：车辆审批Link表
 * 创建人： liaojiming
 * 创建时间：2013-05-23
 */
public class CarApproveLink {
	
	/**主键*/
	private String id;
	/**审批Id*/
	private String carApproveId;
	/**操作人*/
	private String operateUser;
	/**操作时间*/
	private Date operateTime; 
	/**操作类型*/
	private String operateType;
	/**操作描述*/
	private String remark;
	
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
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
