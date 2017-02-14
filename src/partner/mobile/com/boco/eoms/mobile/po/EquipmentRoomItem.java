package com.boco.eoms.mobile.po;

import java.io.Serializable;

/**
 *  接入机房 机房设备
 	* @author WANGJUN
 	* @ClassName: EquipmentRoomItem
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright 公司名称
 	* @date Oct 13, 2016 10:07:35 AM
 	* @description 类描述
 */
public class EquipmentRoomItem implements Serializable {

	private static final long serialVersionUID = 1L;

	// 机房id
	private String roomId;
	// 设备名称
	private String deviceName;
	// 网络类型
	private String networkType;
	// 设备厂家
	private String deviceVender;
	// 设备类型
	private String deviceType;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getDeviceVender() {
		return deviceVender;
	}

	public void setDeviceVender(String deviceVender) {
		this.deviceVender = deviceVender;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
