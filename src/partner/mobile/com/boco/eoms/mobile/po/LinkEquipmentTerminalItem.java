package com.boco.eoms.mobile.po;

/**
 * 接入机房 链接设备 端子占用
 * 
 * @author whl
 * 
 */
public class LinkEquipmentTerminalItem {
	private String roomId;

	private String rackId;

	private String linkTerminalLabel;

	private String status;
	private String linkOccupantInfor;
	
	private String portId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getLinkTerminalLabel() {
		return linkTerminalLabel;
	}

	public void setLinkTerminalLabel(String linkTerminalLabel) {
		this.linkTerminalLabel = linkTerminalLabel;
	}

	public String getLinkOccupantInfor() {
		return linkOccupantInfor;
	}

	public void setLinkOccupantInfor(String linkOccupantInfor) {
		this.linkOccupantInfor = linkOccupantInfor;
	}

	public String getPortId() {
		return portId;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}
}
