package com.boco.eoms.mobile.po;

public class LinkEquipmentOpticalCableItem {

	// 光缆名称
	private String linkCableId;
	// 光缆名称
	private String linkCableName;

	// 纤芯数
	private String linkCoreNumber;
	
	//光缆标识
	private String extrenityNo;
	
	//光缆标号
	private String extrenityXh;
	
	//占用状态
	private String extrenityStatus;

	public String getLinkCableId() {
		return linkCableId;
	}

	public void setLinkCableId(String linkCableId) {
		this.linkCableId = linkCableId;
	}

	public String getLinkCableName() {
		return linkCableName;
	}

	public void setLinkCableName(String linkCableName) {
		this.linkCableName = linkCableName;
	}

	public String getLinkCoreNumber() {
		return linkCoreNumber;
	}

	public void setLinkCoreNumber(String linkCoreNumber) {
		this.linkCoreNumber = linkCoreNumber;
	}

	public String getExtrenityNo() {
		return extrenityNo;
	}

	public void setExtrenityNo(String extrenityNo) {
		this.extrenityNo = extrenityNo;
	}

	public String getExtrenityXh() {
		return extrenityXh;
	}

	public void setExtrenityXh(String extrenityXh) {
		this.extrenityXh = extrenityXh;
	}

	public String getExtrenityStatus() {
		return extrenityStatus;
	}

	public void setExtrenityStatus(String extrenityStatus) {
		this.extrenityStatus = extrenityStatus;
	}
}
