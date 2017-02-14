package com.boco.eoms.partner.deviceAssess.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:软件申请问题记录
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2011
 * </p>
 * 
 * @author zhangkeqi
 * @version 1.0
 * 
 */
public class SoftApplyRecord extends BaseObject {

	/**  主键 */
	private java.lang.String id;
	/** 升级事件名称 */
	private java.lang.String affairName;
	/**  升级申请时间  */
	private String createTime;
	/** 厂家 */
	private java.lang.String factory;
	/** 专业 */
	private java.lang.String speciality;
	/** 设备类型 */
	private java.lang.String equipmentType;
	/**许可证问题*/
	private String licenseProblem;
	/**方案问题*/
	private String schemeProblem;
	/**软件或补丁功能描述错误*/
	private String describeErrors;
	/**其他漏报项目*/
	private String otherOmissionProject;
	/**备注*/
	private String remark;
	
	/**审批实体*/
	private DeviceAssessApprove deviceAssessApprove;
	
	/**  计数 */
	private java.lang.Integer amount;
	
	private java.lang.Integer total;

	public java.lang.Integer getAmount() {
		return amount;
	}

	public void setAmount(java.lang.Integer amount) {
		this.amount = amount;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public boolean equals(Object o) {
		if( o instanceof SoftApplyRecord ) {
			SoftApplyRecord insideDispose=(SoftApplyRecord)o;
			if (this.id != null || this.id.equals(insideDispose.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getAffairName() {
		return affairName;
	}
	public void setAffairName(java.lang.String affairName) {
		this.affairName = affairName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getFactory() {
		return factory;
	}
	public void setFactory(java.lang.String factory) {
		this.factory = factory;
	}
	public java.lang.String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(java.lang.String speciality) {
		this.speciality = speciality;
	}
	public java.lang.String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(java.lang.String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getLicenseProblem() {
		return licenseProblem;
	}
	public void setLicenseProblem(String licenseProblem) {
		this.licenseProblem = licenseProblem;
	}
	public String getSchemeProblem() {
		return schemeProblem;
	}
	public void setSchemeProblem(String schemeProblem) {
		this.schemeProblem = schemeProblem;
	}
	public String getDescribeErrors() {
		return describeErrors;
	}
	public void setDescribeErrors(String describeErrors) {
		this.describeErrors = describeErrors;
	}
	public String getOtherOmissionProject() {
		return otherOmissionProject;
	}
	public void setOtherOmissionProject(String otherOmissionProject) {
		this.otherOmissionProject = otherOmissionProject;
	}
	public void setDeviceAssessApprove(DeviceAssessApprove daa) {
		this.deviceAssessApprove = daa;
	}
	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}
}