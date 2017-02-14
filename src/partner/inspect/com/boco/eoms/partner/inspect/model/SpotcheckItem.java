package com.boco.eoms.partner.inspect.model;


/** 
 * Description: 巡检抽检资源项
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 28, 2012 9:40:16 AM 
 */
public class SpotcheckItem {
	private String id;
	private String spotcheckResId;
	private String planResId;
	private String planItemId;
	private Float score;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpotcheckResId() {
		return spotcheckResId;
	}
	public void setSpotcheckResId(String spotcheckResId) {
		this.spotcheckResId = spotcheckResId;
	}
	public String getPlanResId() {
		return planResId;
	}
	public void setPlanResId(String planResId) {
		this.planResId = planResId;
	}
	public String getPlanItemId() {
		return planItemId;
	}
	public void setPlanItemId(String planItemId) {
		this.planItemId = planItemId;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	
	
}
