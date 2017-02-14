package com.boco.eoms.partner.inspect.model;

/** 
 * Description: 巡检抽检模板项
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 10:00:23 AM 
 */
public class SpotcheckTemplateItem {
	private String id;
	private String spotcheckTemplateId;   //抽检模板
	private String inspectTemplateItemId; //巡检模板项
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpotcheckTemplateId() {
		return spotcheckTemplateId;
	}
	public void setSpotcheckTemplateId(String spotcheckTemplateId) {
		this.spotcheckTemplateId = spotcheckTemplateId;
	}
	public String getInspectTemplateItemId() {
		return inspectTemplateItemId;
	}
	public void setInspectTemplateItemId(String inspectTemplateItemId) {
		this.inspectTemplateItemId = inspectTemplateItemId;
	}
	
}
