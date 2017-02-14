package com.boco.eoms.partner.res.model;

/**
 * 	备份资源清查采集来的机房的数据，也用于比对再次采集机房时的重复机房数据
 * 	对应表PNR_RESOURCE_INVENTORY_ROOM
 	* @author WangJun
 	* @ClassName: PnrResourceInventoryRoom
 	* @date Nov 27, 2015 2:39:54 PM
 	* @description 
 */
public class PnrResourceInventoryRoom {
	private String id;
	private String roomName;
	private String bureauId;
	private String bureauName;
	private String areaId;
	private String areaName;
	private String cityId;
	private String cityName;
	private String roomPosition;
	private String jtGrade;
	private String positionType;
	private String roomType;
	private String jtClass;
	private String attrOne;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBureauId() {
		return bureauId;
	}

	public void setBureauId(String bureauId) {
		this.bureauId = bureauId;
	}

	public String getBureauName() {
		return bureauName;
	}

	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRoomPosition() {
		return roomPosition;
	}

	public void setRoomPosition(String roomPosition) {
		this.roomPosition = roomPosition;
	}

	public String getJtGrade() {
		return jtGrade;
	}

	public void setJtGrade(String jtGrade) {
		this.jtGrade = jtGrade;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getJtClass() {
		return jtClass;
	}

	public void setJtClass(String jtClass) {
		this.jtClass = jtClass;
	}

	public String getAttrOne() {
		return attrOne;
	}

	public void setAttrOne(String attrOne) {
		this.attrOne = attrOne;
	}

}
