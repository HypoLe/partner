package com.boco.eoms.partner.res.dao.other.resource.model;

/**
 * 资源系统中的资源清查机房Model
 */
public class JkXcwhRoom {
	private String roomId;// 机房ID
	private String roomName;// 机房名称
	private String bureauId;// 局所ID
	private String bureauName;// 局所名称
	private String areaId;// 区域ID
	private String areaName;// 区域名称
	private String cityId;// 地市ID
	private String cityName;// 地市名称
	private String roomPosition;// 位置
	private String jtGrade;// 机房大类
	private String positionType;// 类型
	private String roomType;// 机房类型
	private String jtClass;// 机房小类
	private String attrOne;// 标准地址
	
	private String cityIdNew;//巡检中的地市id
	private String cityNameNew;//巡检中的地市名称
	private String countryIdNew;//巡检中的区县id
	private String countryNameNew;//巡检中的区县名称
	

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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

	public String getCityIdNew() {
		return cityIdNew;
	}

	public void setCityIdNew(String cityIdNew) {
		this.cityIdNew = cityIdNew;
	}

	public String getCityNameNew() {
		return cityNameNew;
	}

	public void setCityNameNew(String cityNameNew) {
		this.cityNameNew = cityNameNew;
	}

	public String getCountryIdNew() {
		return countryIdNew;
	}

	public void setCountryIdNew(String countryIdNew) {
		this.countryIdNew = countryIdNew;
	}

	public String getCountryNameNew() {
		return countryNameNew;
	}

	public void setCountryNameNew(String countryNameNew) {
		this.countryNameNew = countryNameNew;
	}
	
}
