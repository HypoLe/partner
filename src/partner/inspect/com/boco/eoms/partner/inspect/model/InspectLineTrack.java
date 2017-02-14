package com.boco.eoms.partner.inspect.model;

import java.util.Date;


/**
 * 本表的数据主要是存储线路敷设点和真实巡检坐标经过筛选过后符合条件的敷设点坐标
 * 
 * 计划是在每天晚上进行筛选
 * @author LEE
 *
 */
public class InspectLineTrack {

	String id;//id
	String planId;//计划ID;
	String lineId;//敷设点ID;
	String segId;//线路段ID
	String longitude;//经度
	String latitude;//纬度
	Date signTime;//定位时间 
	String errorDistance;//误差距离
	public String getErrorDistance() {
		return errorDistance;
	}
	public void setErrorDistance(String errorDistance) {
		this.errorDistance = errorDistance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getSegId() {
		return segId;
	}
	public void setSegId(String segId) {
		this.segId = segId;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	
	
	
	
}
