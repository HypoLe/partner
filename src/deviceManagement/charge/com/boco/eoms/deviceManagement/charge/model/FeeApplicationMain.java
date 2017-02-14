package com.boco.eoms.deviceManagement.charge.model;



import com.boco.eoms.base.model.BaseObject;


public class FeeApplicationMain extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; // 主键

	
	
	private String feeApplicationUser;// 
	
	private String feeApplicationCall;//  
	
	private String feeApplicationDept;//
	
	private String feeApplicationArea;//
	
	private String feeApplicationCity;//
	
	private String feeApplicationGreatTime;//
	
	private String feeApplicationType;//
	
	private String feeApplicationMoney;//
	
	private String feeApplicationDiscribe;//
	
	private String feeApplicationRemark;//
	
	private String feeApplicationAccessory;//
	
	private String feeApplicationStatus;//1 为草稿状态，2为一级审批，3为二级审批，4为审批通过，5为已归档
	
	private String deleted;//0为有效数据，1为逻辑删除
	
	private String deleteTime;//
	
	private String feeApplicationRoleID;//派往角色ID
	
	private String feeApplicationCompanyName;//代维公司名

	private String subType;//提交动作类型
	
	private String feeapplicationprovince;//省份，默认为黑龙江




	public String getId() {
		return id;
	}










	public void setId(String id) {
		this.id = id;
	}








	public String getFeeApplicationUser() {
		return feeApplicationUser;
	}










	public void setFeeApplicationUser(String feeApplicationUser) {
		this.feeApplicationUser = feeApplicationUser;
	}










	public String getFeeApplicationCall() {
		return feeApplicationCall;
	}










	public void setFeeApplicationCall(String feeApplicationCall) {
		this.feeApplicationCall = feeApplicationCall;
	}










	public String getFeeApplicationDept() {
		return feeApplicationDept;
	}










	public void setFeeApplicationDept(String feeApplicationDept) {
		this.feeApplicationDept = feeApplicationDept;
	}










	public String getFeeApplicationArea() {
		return feeApplicationArea;
	}










	public void setFeeApplicationArea(String feeApplicationArea) {
		this.feeApplicationArea = feeApplicationArea;
	}










	public String getFeeApplicationCity() {
		return feeApplicationCity;
	}










	public void setFeeApplicationCity(String feeApplicationCity) {
		this.feeApplicationCity = feeApplicationCity;
	}










	public String getFeeApplicationGreatTime() {
		return feeApplicationGreatTime;
	}










	public void setFeeApplicationGreatTime(String feeApplicationGreatTime) {
		this.feeApplicationGreatTime = feeApplicationGreatTime;
	}










	public String getFeeApplicationType() {
		return feeApplicationType;
	}










	public void setFeeApplicationType(String feeApplicationType) {
		this.feeApplicationType = feeApplicationType;
	}










	public String getFeeApplicationMoney() {
		return feeApplicationMoney;
	}










	public void setFeeApplicationMoney(String feeApplicationMoney) {
		this.feeApplicationMoney = feeApplicationMoney;
	}










	public String getFeeApplicationDiscribe() {
		return feeApplicationDiscribe;
	}










	public void setFeeApplicationDiscribe(String feeApplicationDiscribe) {
		this.feeApplicationDiscribe = feeApplicationDiscribe;
	}










	public String getFeeApplicationRemark() {
		return feeApplicationRemark;
	}










	public void setFeeApplicationRemark(String feeApplicationRemark) {
		this.feeApplicationRemark = feeApplicationRemark;
	}










	public String getFeeApplicationAccessory() {
		return feeApplicationAccessory;
	}










	public void setFeeApplicationAccessory(String feeApplicationAccessory) {
		this.feeApplicationAccessory = feeApplicationAccessory;
	}










	public String getFeeApplicationStatus() {
		return feeApplicationStatus;
	}










	public void setFeeApplicationStatus(String feeApplicationStatus) {
		this.feeApplicationStatus = feeApplicationStatus;
	}










	public String getDeleted() {
		return deleted;
	}










	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}










	public String getDeleteTime() {
		return deleteTime;
	}










	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}










	public String getFeeApplicationRoleID() {
		return feeApplicationRoleID;
	}










	public void setFeeApplicationRoleID(String feeApplicationRoleID) {
		this.feeApplicationRoleID = feeApplicationRoleID;
	}










	public String getFeeApplicationCompanyName() {
		return feeApplicationCompanyName;
	}










	public void setFeeApplicationCompanyName(
			String feeApplicationCompanyName) {
		this.feeApplicationCompanyName = feeApplicationCompanyName;
	}










	public String getSubType() {
		return subType;
	}










	public void setSubType(String subType) {
		this.subType = subType;
	}










	public String getFeeapplicationprovince() {
		return feeapplicationprovince;
	}










	public void setFeeapplicationprovince(String feeapplicationprovince) {
		this.feeapplicationprovince = feeapplicationprovince;
	}










	@Override
	public boolean equals(Object o) {
		if (o instanceof FeeApplicationMain) {
			FeeApplicationMain a = (FeeApplicationMain) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}



