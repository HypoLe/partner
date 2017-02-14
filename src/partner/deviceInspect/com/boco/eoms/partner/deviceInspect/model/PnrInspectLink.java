package com.boco.eoms.partner.deviceInspect.model;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：巡检点与网络资源中的设备关联表，一个巡检点具体有哪些网络资源中的设备在这里体现
 * 创建人： zhangkeqi
 * 创建时间：2013-01-24
 */
public class PnrInspectLink extends EomsModel{

	/**主键*/
	private String id;
	/**巡检点id（PnrResConfig中的id）*/
	private String inspectId;
	/**巡检点类型与网络资源设备类型映射id（PnrInspectMapping中的id）*/
	private String inspectMappingId;
	/**网络资源id（网络资源各表中的主键id或立uuid）*/
	private String netResId;
	/**设备专业名称(与网络资源的专业相对应)*/
	private String deviceSpecialtyName;
	/**设备类型名称*/
	private String deviceTypeName;
	/**网络资源表名*/
	private String netresTableName;
	/**网络资源名称*/
	private String netresName;
	/**网络资源字段名*/
	private String netresFieldName;
	/**网络资源字段值*/
	private String netresFieldValue;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInspectId() {
		return inspectId;
	}
	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}
	public String getInspectMappingId() {
		return inspectMappingId;
	}
	public void setInspectMappingId(String inspectMappingId) {
		this.inspectMappingId = inspectMappingId;
	}
	public String getNetResId() {
		return netResId;
	}
	public void setNetResId(String netResId) {
		this.netResId = netResId;
	}
	public String getDeviceSpecialtyName() {
		return deviceSpecialtyName;
	}
	public void setDeviceSpecialtyName(String deviceSpecialtyName) {
		this.deviceSpecialtyName = deviceSpecialtyName;
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
	public String getNetresName() {
		return netresName;
	}
	public void setNetresName(String netresName) {
		this.netresName = netresName;
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
	
}
