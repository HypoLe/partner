package com.boco.eoms.mobile.po;

/**
 * 接入机房 链接设备
 * 
 * @author Jun
 * 
 */
public class LinkEquipmentItem {

	// 机房id
	private String roomId;
	// 机架id
	private String rackId;
	// 机架名称
	private String rackName;
	// 机架类型
	private String rackType;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRackId() {
		return rackId;
	}

	public void setRackId(String rackId) {
		this.rackId = rackId;
	}

	public String getRackName() {
		return rackName;
	}

	public void setRackName(String rackName) {
		this.rackName = rackName;
	}

	public String getRackType() {
		return rackType;
	}

	public void setRackType(String rackType) {
		this.rackType = rackType;
	}

}
