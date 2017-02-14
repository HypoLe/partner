package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检抽检资源 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 28, 2012 9:40:16 AM 
 */
public class SpotcheckRes {
	private String id;
	private String planId;
	private String planResId;
	private String spotcheckUser;
	private Date spotcheckTime;
	private Float score;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanResId() {
		return planResId;
	}
	public void setPlanResId(String planResId) {
		this.planResId = planResId;
	}
	public String getSpotcheckUser() {
		return spotcheckUser;
	}
	public void setSpotcheckUser(String spotcheckUser) {
		this.spotcheckUser = spotcheckUser;
	}
	public Date getSpotcheckTime() {
		return spotcheckTime;
	}
	public void setSpotcheckTime(Date spotcheckTime) {
		this.spotcheckTime = spotcheckTime;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	
}
