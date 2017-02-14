package com.boco.eoms.deviceManagement.faultInfo.model;



import com.boco.eoms.base.model.BaseObject;


public class BaseStationFaultRecord extends BaseObject {
	private static final long serialVersionUID = 1L;
	private String id;
	private String reportPerson;
	private String deptId;
	private String roleId;
	private String contactNumber;
	private String reportTime;
	private String bsfrDateTime;
	private String stagnationPoint;
	private String baseStationName;
	private String maintainLevel;
	private String sheetType;
	private String sheetTaskInfor;
	private String sheetStartTime;
	private String sheetConfirmTime;
	private String sheeEndTime;
	private String takeTime;
	private String sheetReceivePerson;
	private String sheetEndPerson;
	private String sheetStartType;
	private String processingResults;
	private String faultType;
	private String isExit;
	private String deviceAdjust;
	private String residualProblem;
	private String other;
	private String sheetId;
	private String isDeleted;
	private String deleteTime;
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportPerson() {
		return reportPerson;
	}

	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getStagnationPoint() {
		return stagnationPoint;
	}

	public void setStagnationPoint(String stagnationPoint) {
		this.stagnationPoint = stagnationPoint;
	}

	public String getBaseStationName() {
		return baseStationName;
	}

	public void setBaseStationName(String baseStationName) {
		this.baseStationName = baseStationName;
	}

	public String getMaintainLevel() {
		return maintainLevel;
	}

	public void setMaintainLevel(String maintainLevel) {
		this.maintainLevel = maintainLevel;
	}

	public String getSheetType() {
		return sheetType;
	}

	public void setSheetType(String sheetType) {
		this.sheetType = sheetType;
	}

	public String getSheetTaskInfor() {
		return sheetTaskInfor;
	}

	public void setSheetTaskInfor(String sheetTaskInfor) {
		this.sheetTaskInfor = sheetTaskInfor;
	}


	public String getSheetReceivePerson() {
		return sheetReceivePerson;
	}

	public void setSheetReceivePerson(String sheetReceivePerson) {
		this.sheetReceivePerson = sheetReceivePerson;
	}

	public String getSheetEndPerson() {
		return sheetEndPerson;
	}

	public void setSheetEndPerson(String sheetEndPerson) {
		this.sheetEndPerson = sheetEndPerson;
	}

	public String getSheetStartType() {
		return sheetStartType;
	}

	public void setSheetStartType(String sheetStartType) {
		this.sheetStartType = sheetStartType;
	}

	public String getProcessingResults() {
		return processingResults;
	}

	public void setProcessingResults(String processingResults) {
		this.processingResults = processingResults;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getIsExit() {
		return isExit;
	}

	public void setIsExit(String isExit) {
		this.isExit = isExit;
	}

	public String getDeviceAdjust() {
		return deviceAdjust;
	}

	public void setDeviceAdjust(String deviceAdjust) {
		this.deviceAdjust = deviceAdjust;
	}

	public String getResidualProblem() {
		return residualProblem;
	}

	public void setResidualProblem(String residualProblem) {
		this.residualProblem = residualProblem;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4;
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BaseStationFaultRecord) {
			BaseStationFaultRecord baseStationFaultRecord = (BaseStationFaultRecord) o;
			if (this.id != null || this.id.equals(baseStationFaultRecord.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	

	public String getBsfrDateTime() {
		return bsfrDateTime;
	}

	public void setBsfrDateTime(String bsfrDateTime) {
		this.bsfrDateTime = bsfrDateTime;
	}

	public String getSheetStartTime() {
		return sheetStartTime;
	}

	public void setSheetStartTime(String sheetStartTime) {
		this.sheetStartTime = sheetStartTime;
	}

	public String getSheetConfirmTime() {
		return sheetConfirmTime;
	}

	public void setSheetConfirmTime(String sheetConfirmTime) {
		this.sheetConfirmTime = sheetConfirmTime;
	}

	public String getSheeEndTime() {
		return sheeEndTime;
	}

	public void setSheeEndTime(String sheeEndTime) {
		this.sheeEndTime = sheeEndTime;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

}
