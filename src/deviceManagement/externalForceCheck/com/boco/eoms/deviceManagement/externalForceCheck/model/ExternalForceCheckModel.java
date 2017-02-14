package com.boco.eoms.deviceManagement.externalForceCheck.model;

public class ExternalForceCheckModel {
	private String id;
	private String place;//外力盯防现场地点
	private String ownerUser;//外力盯防负责人
	private String gpsFacility;//外力盯防GPS终端设备（可选）
	private String startDate;//外力盯防起始日期
	private String endDate;//外力盯防结束日期
	private String route;//外力盯防所属线路
	private String routeStage;//外力盯防所属线路段
	private String status;//外力盯防状态
	private String deleted;//删除标记1为删除 
	
	
	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getGpsFacility() {
		return gpsFacility;
	}
	public void setGpsFacility(String gpsFacility) {
		this.gpsFacility = gpsFacility;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getRouteStage() {
		return routeStage;
	}
	public void setRouteStage(String routeStage) {
		this.routeStage = routeStage;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}