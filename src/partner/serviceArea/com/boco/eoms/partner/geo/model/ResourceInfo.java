package com.boco.eoms.partner.geo.model;

import java.util.List;

public class ResourceInfo {

	private int id;		
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	private String resourceId;
	private String resourceName;
	private String resourceType;
	private String uiProvider = "col";
	private String cls = "master-task";
	private String iconCls = "task-folder";
	private boolean leaf = true;
	
	private List<ResourceInfo> children;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getUiProvider() {
		return uiProvider;
	}
	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public List<ResourceInfo> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceInfo> children) {
		this.children = children;
	}
	
	
}
