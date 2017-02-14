package com.boco.eoms.sheet.base.model;

/** 
 * Description: Main表中sendObjectTotalJSON对应的对象 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 26, 2012 5:58:44 PM 
 */
public class Performer {
	private String id;
	private String nodeType;
	private String categoryId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
