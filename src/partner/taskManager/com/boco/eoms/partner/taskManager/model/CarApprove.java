package com.boco.eoms.partner.taskManager.model;

import java.util.Date;

/**
 * 类说明：车辆审批表
 * 创建人： liaojiming
 * 创建时间：2013-05-23
 */
public class CarApprove {

	/**主键*/
	private String id;
	/**申请时间*/
	private Date applyTime;
	/**车牌号*/
	private String carNum;
	/**申请人*/
	private String applyUser;
	/**申请人部门*/
	private String applyUserDept;
	/**归还人*/
	private String backUser;
	/**归还时间*/
	private Date backTime;
	/**状态*/
	private Integer approveStatue;  //(-1,审批驳回,0待审批,1审批通过,2已结束)	
	/**审批人*/
	private String approveUser;
	/**审批人部门*/
	private String approveUserDept;
	/**审批时间*/
	private Date approveTime;
	/**备注*/
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getApplyUserDept() {
		return applyUserDept;
	}

	public void setApplyUserDept(String applyUserDept) {
		this.applyUserDept = applyUserDept;
	}

	public String getBackUser() {
		return backUser;
	}

	public void setBackUser(String backUser) {
		this.backUser = backUser;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public Integer getApproveStatue() {
		return approveStatue;
	}

	public void setApproveStatue(Integer approveStatue) {
		this.approveStatue = approveStatue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveUserDept() {
		return approveUserDept;
	}

	public void setApproveUserDept(String approveUserDept) {
		this.approveUserDept = approveUserDept;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	
	
}
