package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:厂家设备问题事件信息
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class Facilityinfo extends BaseObject {

	/** 主键 */
	private String id;
	/** 问题名称 */
	private java.lang.String issueName;
	/** 问题类别 */
	private java.lang.String issueType;
	/** 级别 */
	private java.lang.String facilityLevel;
	/** 发生省份 */
	private java.lang.String province;
	/** 发生地市 */
	private java.lang.String city;
	
	
	/** 厂家 */
	private java.lang.String factory;
	/** 专业 */
	private java.lang.String speciality;
	/**  设备类型  */
	private java.lang.String equipmentType;
	/** 设备名称 */
	private java.lang.String equipmentName;
	/** 设备版本 */
	private java.lang.String equipmentVersion;
	
	
	/**  发生时间 */
	private java.util.Date occurTime;
	/**  状态 */
	private java.lang.String state;
	/** 解决方案 */
	private String accessory;
	/** 解决时间 */
	private java.util.Date solveTime;
	/** 计数 */
	private java.lang.Integer total;

	public boolean equals(Object o) {
		if( o instanceof Facilityinfo ) {
			Facilityinfo facilityinfo=(Facilityinfo)o;
			if (this.id != null || this.id.equals(facilityinfo.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	private DeviceAssessApprove deviceAssessApprove;

	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}

	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public java.lang.String getIssueName() {
		return issueName;
	}

	public void setIssueName(java.lang.String issueName) {
		this.issueName = issueName;
	}

	public java.lang.String getIssueType() {
		return issueType;
	}

	public void setIssueType(java.lang.String issueType) {
		this.issueType = issueType;
	}

	public java.lang.String getFacilityLevel() {
		return facilityLevel;
	}

	public void setFacilityLevel(java.lang.String facilityLevel) {
		this.facilityLevel = facilityLevel;
	}

	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
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

	public java.lang.String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(java.lang.String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public java.lang.String getEquipmentVersion() {
		return equipmentVersion;
	}

	public void setEquipmentVersion(java.lang.String equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}

	public java.util.Date getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(java.util.Date occurTime) {
		this.occurTime = occurTime;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public java.util.Date getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(java.util.Date solveTime) {
		this.solveTime = solveTime;
	}

	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	
	
}