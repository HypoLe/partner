package com.boco.eoms.deviceManagement.busi.line.model;



import com.boco.eoms.base.model.BaseObject;


public class Maintain extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id; 							// 主键

	private String creatUser; 					//填报人

	private String creatTime; 					// 填报时间
	
	private String creatDept; 					// 所属部门

	private String approvalUser; 				// 提交审核人
	
	private String maintainName  ; 				// 割接名称

	private String fiberSection; 				// 光缆线段
	
	private String maintainCause;				 // 割接原因
	
	private String maintainPlace;				 // 割接地点
	
	private String influenceSystem; 			// 影响系统
	
	private String attenuation; 				// 原有衰耗
	
	private String expectationTimeConsuming; 	// 预计用时	
	
	private String expectationDate; 			// 预计时期	
	
	private String remark; 						// 备注
	
	private String accessory;         			//附件
	
	private String  approvalType;               //审核状态
	
	private String  isCheck;              		 //验收状态 1:通过验收,0驳回验收
	
	private String  maintainFinish;               //完成状态 1:完成,0未完成
	
	private String  isRecord;               //验收状态 1:已归档,0未归档
	
	private String  isModify;               //是否可修改
	
	private String  isReport;               //是否提交报告
	////////////////////////////////////
	
	private String  deleted;//删除标示
	
	private String  modifyUser;//修改人
	
	private String  modifyTime;//修改时间
	
	private String  modifyDept;//修改人部门
	
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




	public String getCreatUser() {
		return creatUser;
	}




	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}




	public String getCreatTime() {
		return creatTime;
	}




	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}




	public String getCreatDept() {
		return creatDept;
	}




	public void setCreatDept(String creatDept) {
		this.creatDept = creatDept;
	}




	public String getApprovalUser() {
		return approvalUser;
	}




	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}




	public String getMaintainName() {
		return maintainName;
	}




	public void setMaintainName(String maintainName) {
		this.maintainName = maintainName;
	}




	public String getFiberSection() {
		return fiberSection;
	}




	public void setFiberSection(String fiberSection) {
		this.fiberSection = fiberSection;
	}




	public String getMaintainCause() {
		return maintainCause;
	}




	public void setMaintainCause(String maintainCause) {
		this.maintainCause = maintainCause;
	}




	public String getMaintainPlace() {
		return maintainPlace;
	}




	public void setMaintainPlace(String maintainPlace) {
		this.maintainPlace = maintainPlace;
	}




	public String getInfluenceSystem() {
		return influenceSystem;
	}




	public void setInfluenceSystem(String influenceSystem) {
		this.influenceSystem = influenceSystem;
	}




	public String getAttenuation() {
		return attenuation;
	}




	public void setAttenuation(String attenuation) {
		this.attenuation = attenuation;
	}




	public String getExpectationTimeConsuming() {
		return expectationTimeConsuming;
	}




	public void setExpectationTimeConsuming(String expectationTimeConsuming) {
		this.expectationTimeConsuming = expectationTimeConsuming;
	}




	public String getExpectationDate() {
		return expectationDate;
	}




	public void setExpectationDate(String expectationDate) {
		this.expectationDate = expectationDate;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getAccessory() {
		return accessory;
	}




	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}




	public String getDeleted() {
		return deleted;
	}




	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}




	public String getModifyUser() {
		return modifyUser;
	}




	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}




	public String getModifyTime() {
		return modifyTime;
	}




	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}




	public String getModifyDept() {
		return modifyDept;
	}




	public void setModifyDept(String modifyDept) {
		this.modifyDept = modifyDept;
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




	public static long getSerialVersionUID() {
		return serialVersionUID;
	}




	@Override
	public boolean equals(Object o) {
		if (o instanceof Maintain) {
			Maintain a = (Maintain) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}




	public String getApprovalType() {
		return approvalType;
	}




	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}




	public String getMaintainFinish() {
		return maintainFinish;
	}




	public void setMaintainFinish(String maintainFinish) {
		this.maintainFinish = maintainFinish;
	}




	public String getIsRecord() {
		return isRecord;
	}




	public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}




	public String getIsModify() {
		return isModify;
	}




	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}




	public String getIsReport() {
		return isReport;
	}




	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}




	public String getIsCheck() {
		return isCheck;
	}




	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}










}



