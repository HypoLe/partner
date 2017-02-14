package com.boco.eoms.partner.deviceAssess.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Tue Nov 15 11:46:52 CST 2011
 * </p>
 * 
 * @author heminxi
 * @version 2.0
 * 
 */
public class Softupinfo extends BaseObject {

	/**  主键 */
	private java.lang.String id;
	/** 工单ID号 */
	private java.lang.String sheetId;
	/** 工单号 */
	private java.lang.String sheetNum;
	/**  建单时间 */
	private java.util.Date createTime;
	/** 归档时间 */
	private java.util.Date pigeonholeTime;
	/** 事件名称 */
	private java.lang.String affairName;
	/** 级别 */
	private java.lang.String affairLevel;
	/** 发生省份 */
	private java.lang.String province;
	/** 发生地市 */
	private java.lang.String city;
	/** 厂家 */
	private java.lang.String factory;
	/** 专业 */
	private java.lang.String speciality;
	/** 设备类型 */
	private java.lang.String equipmentType;
	/** 设备名称 */
	private java.lang.String equipmentName;
	/** 当前版本 */
	private java.lang.String nonceEdition;
	/** 升级版本 */
	private java.lang.String updateEdition;

//	/** 升级设备数量 */
//	private java.lang.Integer upfixtureAmount;
//	/** 升级成功数量 */
//	private java.lang.Integer uphitAmount;
//	/** 满意度 */
//	private java.lang.Integer satisfaction;
//	/** 升级成功率 */
//	private double uphitRate;
	/** 计数 */
	private java.lang.Integer takeCountOf;

	/** 新加字段： */
	/**未成功的原因*/ 
	private java.lang.String unsucceeReason;
	/**第一次未成功的升级时间*/ 
	private java.util.Date firstUnsucceeTime;
	/**后续升级方案*/ 
	private java.lang.String extendProject;
	/**最终结果*/ 
	private java.lang.String finalResult;
	/**最终升级时间*/ 
	private java.util.Date finalUpTime;
	/**附件*/ 
	private java.lang.String accessory;
	/**统计标识*/ 
	private java.lang.Integer  total;
	/**统计信息*/
	private	DeviceAssessApprove deviceAssessApprove;
	
	public DeviceAssessApprove getDeviceAssessApprove() {
		return deviceAssessApprove;
	}
	public void setDeviceAssessApprove(DeviceAssessApprove deviceAssessApprove) {
		this.deviceAssessApprove = deviceAssessApprove;
	}
	public java.lang.Integer getTotal() {
		return total;
	}
	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	public boolean equals(Object o) {
		if( o instanceof Softupinfo ) {
			Softupinfo softupinfo=(Softupinfo)o;
			if (this.id != null || this.id.equals(softupinfo.getId())) {
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
	public java.lang.String getSheetId() {
		return sheetId;
	}
	public void setSheetId(java.lang.String sheetId) {
		this.sheetId = sheetId;
	}
	public java.lang.String getSheetNum() {
		return sheetNum;
	}
	public void setSheetNum(java.lang.String sheetNum) {
		this.sheetNum = sheetNum;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getPigeonholeTime() {
		return pigeonholeTime;
	}
	public void setPigeonholeTime(java.util.Date pigeonholeTime) {
		this.pigeonholeTime = pigeonholeTime;
	}
	public java.lang.String getAffairName() {
		return affairName;
	}
	public void setAffairName(java.lang.String affairName) {
		this.affairName = affairName;
	}
	public java.lang.String getAffairLevel() {
		return affairLevel;
	}
	public void setAffairLevel(java.lang.String affairLevel) {
		this.affairLevel = affairLevel;
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
	public java.lang.String getNonceEdition() {
		return nonceEdition;
	}
	public void setNonceEdition(java.lang.String nonceEdition) {
		this.nonceEdition = nonceEdition;
	}
	public java.lang.String getUpdateEdition() {
		return updateEdition;
	}
	public void setUpdateEdition(java.lang.String updateEdition) {
		this.updateEdition = updateEdition;
	}
//	public java.lang.Integer getUpfixtureAmount() {
//		return upfixtureAmount;
//	}
//	public void setUpfixtureAmount(java.lang.Integer upfixtureAmount) {
//		this.upfixtureAmount = upfixtureAmount;
//	}
//	public java.lang.Integer getUphitAmount() {
//		return uphitAmount;
//	}
//	public void setUphitAmount(java.lang.Integer uphitAmount) {
//		this.uphitAmount = uphitAmount;
//	}
//	public java.lang.Integer getSatisfaction() {
//		return satisfaction;
//	}
//	public void setSatisfaction(java.lang.Integer satisfaction) {
//		this.satisfaction = satisfaction;
//	}
//	public double getUphitRate() {
//		return uphitRate;
//	}
//	public void setUphitRate(double uphitRate) {
//		this.uphitRate = uphitRate;
//	}
	public java.lang.Integer getTakeCountOf() {
		return takeCountOf;
	}
	public void setTakeCountOf(java.lang.Integer takeCountOf) {
		this.takeCountOf = takeCountOf;
	}
	public java.lang.String getExtendProject() {
		return extendProject;
	}
	public void setExtendProject(java.lang.String extendProject) {
		this.extendProject = extendProject;
	}
	public java.lang.String getFinalResult() {
		return finalResult;
	}
	public void setFinalResult(java.lang.String finalResult) {
		this.finalResult = finalResult;
	}
	public java.util.Date getFinalUpTime() {
		return finalUpTime;
	}
	public void setFinalUpTime(java.util.Date finalUpTime) {
		this.finalUpTime = finalUpTime;
	}
	public java.util.Date getFirstUnsucceeTime() {
		return firstUnsucceeTime;
	}
	public void setFirstUnsucceeTime(java.util.Date firstUnsucceeTime) {
		this.firstUnsucceeTime = firstUnsucceeTime;
	}
	public java.lang.String getUnsucceeReason() {
		return unsucceeReason;
	}
	public void setUnsucceeReason(java.lang.String unsucceeReason) {
		this.unsucceeReason = unsucceeReason;
	}
	public java.lang.String getAccessory() {
		return accessory;
	}
	public void setAccessory(java.lang.String accessory) {
		this.accessory = accessory;
	}
	
}