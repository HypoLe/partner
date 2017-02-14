package com.boco.eoms.deviceManagement.externalForceCheck.util;

public class ReportModel {
	private String place;//外力盯防现场地点
	private String ownerUser;//外力盯防负责人
	private String startDate;//外力盯防起始日期
	private String endDate;//外力盯防结束日期
	private String route;//外力盯防所属线路
	private String routeStage;//外力盯防所属线路段
	private String ownerRate;//负责人巡检率
	private String  executeRate;//执行人巡检率
	
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getExecuteRate() {
		return executeRate;
	}
	public void setExecuteRate(String executeRate) {
		this.executeRate = executeRate;
	}
	public String getOwnerRate() {
		return ownerRate;
	}
	public void setOwnerRate(String ownerRate) {
		this.ownerRate = ownerRate;
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
	
	
	
}
