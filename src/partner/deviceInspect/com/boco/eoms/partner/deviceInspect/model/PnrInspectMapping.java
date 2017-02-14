package com.boco.eoms.partner.deviceInspect.model;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：巡检点类型与网络资源设备类型映射
 * 创建人： zhangkeqi
 * 创建时间：2013-01-19 
 */
public class PnrInspectMapping extends EomsModel{

	/**主键*/
	private String id;
	/**专业*/
	private String specialty;
	/**巡检点类型*/
	private String inspectType;
	/**设备专业(与网络资源的专业相对应)*/
	private String deviceSpecialty;
	/**设备专业名称(与网络资源的专业相对应)*/
	private String deviceSpecialtyName;
	/**设备类别*/
	private String deviceType;
	/**设备类型名称*/
	private String deviceTypeName;
	/**网络资源表名*/
	private String netresTableName;
	/**网络资源字段名*/
	private String netresFieldName;
	/**网络资源字段值*/
	private String netresFieldValue;
	/**备注*/
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getInspectType() {
		return inspectType;
	}
	public void setInspectType(String inspectType) {
		this.inspectType = inspectType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getNetresTableName() {
		return netresTableName;
	}
	public void setNetresTableName(String netresTableName) {
		this.netresTableName = netresTableName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNetresFieldName() {
		return netresFieldName;
	}
	public void setNetresFieldName(String netresFieldName) {
		this.netresFieldName = netresFieldName;
	}
	public String getNetresFieldValue() {
		return netresFieldValue;
	}
	public void setNetresFieldValue(String netresFieldValue) {
		this.netresFieldValue = netresFieldValue;
	}
	public String getDeviceSpecialty() {
		return deviceSpecialty;
	}
	public void setDeviceSpecialty(String deviceSpecialty) {
		this.deviceSpecialty = deviceSpecialty;
	}
	public String getDeviceSpecialtyName() {
		return deviceSpecialtyName;
	}
	public void setDeviceSpecialtyName(String deviceSpecialtyName) {
		this.deviceSpecialtyName = deviceSpecialtyName;
	}
}
