package com.boco.eoms.deviceManagement.busi.protectline.model;

import com.boco.eoms.base.model.BaseObject;

public class ConsctRouting extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String id; // 主键

	private String creatUser; //填报人

	private String creatDept; // 所属部门
	
	private String creatTime; // 填报时间
	
	private String areaId; //所属地市
	
	private String status;//状态

	private String approvalUser; // 提交审核人
	
	private String projectName; // 项目名称
	
	private String repeaterSection;//施工路由中继段名称
	
	private double repeaterSectionMileage;//施工路由中继段里程（公里）
	
	private String location;//施工位置
	
	private String riskLevel;//施工路由中继段风险级别
	
	private String maintainLevel;//维护级别
	
	private String disseminationMeasures;//宣传措施
	
	private String careMeasures;//看护措施
	
	private String result;//效果（是否发生障碍）
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	
	private String deletedTime;//删除标记：0表示未删除，1表示逻辑删除
 	
	private String remark;//备注
	
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ConsctRouting) {
			ConsctRouting checkPoint = (ConsctRouting) o;
			if (this.id != null || this.id.equals(checkPoint.getId())) {
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





	public String getCreatUser() {
		return creatUser;
	}





	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}





	public String getCreatDept() {
		return creatDept;
	}





	public void setCreatDept(String creatDept) {
		this.creatDept = creatDept;
	}





	public String getCreatTime() {
		return creatTime;
	}





	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public String getApprovalUser() {
		return approvalUser;
	}





	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}





	public String getProjectName() {
		return projectName;
	}





	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}





	public String getRepeaterSection() {
		return repeaterSection;
	}





	public void setRepeaterSection(String repeaterSection) {
		this.repeaterSection = repeaterSection;
	}








	public double getRepeaterSectionMileage() {
		return repeaterSectionMileage;
	}





	public void setRepeaterSectionMileage(double repeaterSectionMileage) {
		this.repeaterSectionMileage = repeaterSectionMileage;
	}





	public String getLocation() {
		return location;
	}





	public void setLocation(String location) {
		this.location = location;
	}





	public String getRiskLevel() {
		return riskLevel;
	}





	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}





	public String getMaintainLevel() {
		return maintainLevel;
	}





	public void setMaintainLevel(String maintainLevel) {
		this.maintainLevel = maintainLevel;
	}





	public String getDisseminationMeasures() {
		return disseminationMeasures;
	}





	public void setDisseminationMeasures(String disseminationMeasures) {
		this.disseminationMeasures = disseminationMeasures;
	}





	public String getCareMeasures() {
		return careMeasures;
	}





	public void setCareMeasures(String careMeasures) {
		this.careMeasures = careMeasures;
	}





	public String getResult() {
		return result;
	}





	public void setResult(String result) {
		this.result = result;
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
}
