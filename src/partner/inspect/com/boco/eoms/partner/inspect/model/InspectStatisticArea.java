package com.boco.eoms.partner.inspect.model;

/** 
 * Description: 巡检统计地域对象 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 1, 2012 3:56:45 PM 
 */
public class InspectStatisticArea {
	private String id;
	private String area;
	private Integer planResNum;
	private Float doneRate;
	private Float aveTimeOnSite;
	private Integer resExceptionNum;
	private String year;
	private String month;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getPlanResNum() {
		return planResNum;
	}
	public void setPlanResNum(Integer planResNum) {
		this.planResNum = planResNum;
	}
	public Float getDoneRate() {
		return doneRate;
	}
	public void setDoneRate(Float doneRate) {
		this.doneRate = doneRate;
	}
	public Float getAveTimeOnSite() {
		return aveTimeOnSite;
	}
	public void setAveTimeOnSite(Float aveTimeOnSite) {
		this.aveTimeOnSite = aveTimeOnSite;
	}
	public Integer getResExceptionNum() {
		return resExceptionNum;
	}
	public void setResExceptionNum(Integer resExceptionNum) {
		this.resExceptionNum = resExceptionNum;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
