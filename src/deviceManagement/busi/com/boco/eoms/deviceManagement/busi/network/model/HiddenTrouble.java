package com.boco.eoms.deviceManagement.busi.network.model;

import com.boco.eoms.base.model.BaseObject;

public class HiddenTrouble extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String id; // 主键

	private String reportUser; //上报人，就是当前填写隐患信息的登录人员

	private String reportTime; //上报时间
	
	private String areaId;//地区
	
	private String hiddenTroubleType;//隐患类型
	
	private String isImportant;//是否重要隐患（0：否，1：是）
	
	private String majorType;//专业类型
	
	private String checkPoint;//巡检点
	
	private double longitude;//经度
	
	private double latitude;//纬度

	private String checkUser;//上报巡检员 pnr_user
	
	private String checkUserDept;//代维公司 （上报人所属代维公司）pnr_dept
	
	private String phone;//联系电话
	
	private String email;//email信息
	
	
	private String processStatus;//处理状态(0:未处理，1:已处理)
	
	private String processUser;//处理人
	
	private String handlTime;//处理时间
	
	private String handleMsg;//处理信息
	
	
	
	
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	
	private String deletedTime;//删除时间
 	
	private String remark;//备注
	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof HiddenTrouble) {
			HiddenTrouble HiddenTrouble = (HiddenTrouble) o;
			if (this.id != null || this.id.equals(HiddenTrouble.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}







	public String getReportUser() {
		return reportUser;
	}





	public void setReportUser(String reportUser) {
		this.reportUser = reportUser;
	}





	public String getHiddenTroubleType() {
		return hiddenTroubleType;
	}





	public void setHiddenTroubleType(String hiddenTroubleType) {
		this.hiddenTroubleType = hiddenTroubleType;
	}





	public String getIsImportant() {
		return isImportant;
	}





	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}





	public String getMajorType() {
		return majorType;
	}





	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}





	public String getCheckPoint() {
		return checkPoint;
	}





	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}





	public String getReportTime() {
		return reportTime;
	}





	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}





	public String getCheckUser() {
		return checkUser;
	}





	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}





	public String getCheckUserDept() {
		return checkUserDept;
	}





	public void setCheckUserDept(String checkUserDept) {
		this.checkUserDept = checkUserDept;
	}





	public String getPhone() {
		return phone;
	}





	public void setPhone(String phone) {
		this.phone = phone;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getProcessStatus() {
		return processStatus;
	}





	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}





	public String getProcessUser() {
		return processUser;
	}





	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}





	public String getHandlTime() {
		return handlTime;
	}





	public void setHandlTime(String handlTime) {
		this.handlTime = handlTime;
	}





	public String getHandleMsg() {
		return handleMsg;
	}





	public void setHandleMsg(String handleMsg) {
		this.handleMsg = handleMsg;
	}





	public String getDeleted() {
		return deleted;
	}





	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}





	public String getRemark() {
		return remark;
	}





	public void setRemark(String remark) {
		this.remark = remark;
	}





	public String getDeletedTime() {
		return deletedTime;
	}





	public void setDeletedTime(String deletedTime) {
		this.deletedTime = deletedTime;
	}





	public String getAreaId() {
		return areaId;
	}





	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}





	public double getLongitude() {
		return longitude;
	}





	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}





	public double getLatitude() {
		return latitude;
	}





	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
