package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--客户端设备信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:39
 */
public class IrmsGroupDevice extends EomsModel{

	/**主键*/
	private String id;
		
	/**开通设备的设备资源命名*/
	private String deviceName;
		
	/**开通设备的设备序列号*/
	private String deviceSerialNum;
		
	/**设备所属地市*/
	private String deviceCity;
		
	/**设备所属区县*/
	private String deviceCounty;
		
	/**设备所属乡镇街道*/
	private String deviceTown;
		
	/**设备所属机房*/
	private String deviceRoom;
		
	/**设备厂家名称*/
	private String deviceVendor;
		
	/**设备规则型号*/
	private String deviceModel;
		
	/**设备状态*/
	private String deviceStatus;
		
	/**设备产权单位*/
	private String devicePropertyUnit;
		
	/**设备使用单位*/
	private String deviceUseUnit;
		
	/**设备维护单位*/
	private String deviceManageUnit;
		
	/**设备IP地址*/
	private String deviceIp;
		
	/**设备版本信息*/
	private String deviceVersion;
		
	/**设备入网时间*/
	private String deviceAccessNetDate;
		
	/**设备固定资产编号*/
	private String deviceFixedAssetId;
		
	/**与【T1.1-客户信息表】的【客户编号】关联*/
	private String relatedIinstance;
		
	/**IAD/AG/PBX/IP PBX/协转/光收/光端机*/
	private String clientDeviceType;
		
	/**TDM/POS/ETH*/
	private String clientDevicePortType;
		
	/**1/2/3/4*/
	private String clientDevicePortId;
		
	/**预占/空闲*/
	private String devicePortStatus;
		
	/**当设备类型为数通类型时为必填项，如果地址没有分配，或该设备无需分配地址，则不填。*/
	private String inerconnectIp;
		
	/**当设备类型为数通类型时填写，若设备无需配置此项，则无需填写*/
	private String clientDeviceVlan;
		
	/**当设备类型为数通类型时填写，若设备无需配置此项，则无需填写*/
	private String clientDeviceMac;
		
	/**细化到小数点后6位数*/
	private String pointaLongitude;
		
	/**细化到小数点后6位数*/
	private String pointaLatitude;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属机房ID*/
	private String deviceRoomId;
		
	/**产品实例标识ID*/
	private String relatedInstanceId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceName() {
		return this.deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceSerialNum() {
		return this.deviceSerialNum;
	}
	
	public void setDeviceSerialNum(String deviceSerialNum) {
		this.deviceSerialNum = deviceSerialNum;
	}
	public String getDeviceCity() {
		return this.deviceCity;
	}
	
	public void setDeviceCity(String deviceCity) {
		this.deviceCity = deviceCity;
	}
	public String getDeviceCounty() {
		return this.deviceCounty;
	}
	
	public void setDeviceCounty(String deviceCounty) {
		this.deviceCounty = deviceCounty;
	}
	public String getDeviceTown() {
		return this.deviceTown;
	}
	
	public void setDeviceTown(String deviceTown) {
		this.deviceTown = deviceTown;
	}
	public String getDeviceRoom() {
		return this.deviceRoom;
	}
	
	public void setDeviceRoom(String deviceRoom) {
		this.deviceRoom = deviceRoom;
	}
	public String getDeviceVendor() {
		return this.deviceVendor;
	}
	
	public void setDeviceVendor(String deviceVendor) {
		this.deviceVendor = deviceVendor;
	}
	public String getDeviceModel() {
		return this.deviceModel;
	}
	
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceStatus() {
		return this.deviceStatus;
	}
	
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDevicePropertyUnit() {
		return this.devicePropertyUnit;
	}
	
	public void setDevicePropertyUnit(String devicePropertyUnit) {
		this.devicePropertyUnit = devicePropertyUnit;
	}
	public String getDeviceUseUnit() {
		return this.deviceUseUnit;
	}
	
	public void setDeviceUseUnit(String deviceUseUnit) {
		this.deviceUseUnit = deviceUseUnit;
	}
	public String getDeviceManageUnit() {
		return this.deviceManageUnit;
	}
	
	public void setDeviceManageUnit(String deviceManageUnit) {
		this.deviceManageUnit = deviceManageUnit;
	}
	public String getDeviceIp() {
		return this.deviceIp;
	}
	
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getDeviceVersion() {
		return this.deviceVersion;
	}
	
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getDeviceAccessNetDate() {
		return this.deviceAccessNetDate;
	}
	
	public void setDeviceAccessNetDate(String deviceAccessNetDate) {
		this.deviceAccessNetDate = deviceAccessNetDate;
	}
	public String getDeviceFixedAssetId() {
		return this.deviceFixedAssetId;
	}
	
	public void setDeviceFixedAssetId(String deviceFixedAssetId) {
		this.deviceFixedAssetId = deviceFixedAssetId;
	}
	public String getRelatedIinstance() {
		return this.relatedIinstance;
	}
	
	public void setRelatedIinstance(String relatedIinstance) {
		this.relatedIinstance = relatedIinstance;
	}
	public String getClientDeviceType() {
		return this.clientDeviceType;
	}
	
	public void setClientDeviceType(String clientDeviceType) {
		this.clientDeviceType = clientDeviceType;
	}
	public String getClientDevicePortType() {
		return this.clientDevicePortType;
	}
	
	public void setClientDevicePortType(String clientDevicePortType) {
		this.clientDevicePortType = clientDevicePortType;
	}
	public String getClientDevicePortId() {
		return this.clientDevicePortId;
	}
	
	public void setClientDevicePortId(String clientDevicePortId) {
		this.clientDevicePortId = clientDevicePortId;
	}
	public String getDevicePortStatus() {
		return this.devicePortStatus;
	}
	
	public void setDevicePortStatus(String devicePortStatus) {
		this.devicePortStatus = devicePortStatus;
	}
	public String getInerconnectIp() {
		return this.inerconnectIp;
	}
	
	public void setInerconnectIp(String inerconnectIp) {
		this.inerconnectIp = inerconnectIp;
	}
	public String getClientDeviceVlan() {
		return this.clientDeviceVlan;
	}
	
	public void setClientDeviceVlan(String clientDeviceVlan) {
		this.clientDeviceVlan = clientDeviceVlan;
	}
	public String getClientDeviceMac() {
		return this.clientDeviceMac;
	}
	
	public void setClientDeviceMac(String clientDeviceMac) {
		this.clientDeviceMac = clientDeviceMac;
	}
	public String getPointaLongitude() {
		return this.pointaLongitude;
	}
	
	public void setPointaLongitude(String pointaLongitude) {
		this.pointaLongitude = pointaLongitude;
	}
	public String getPointaLatitude() {
		return this.pointaLatitude;
	}
	
	public void setPointaLatitude(String pointaLatitude) {
		this.pointaLatitude = pointaLatitude;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeviceRoomId() {
		return this.deviceRoomId;
	}
	
	public void setDeviceRoomId(String deviceRoomId) {
		this.deviceRoomId = deviceRoomId;
	}
	public String getRelatedInstanceId() {
		return this.relatedInstanceId;
	}
	
	public void setRelatedInstanceId(String relatedInstanceId) {
		this.relatedInstanceId = relatedInstanceId;
	}
	
}
