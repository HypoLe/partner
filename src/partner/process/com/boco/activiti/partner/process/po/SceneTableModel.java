package com.boco.activiti.partner.process.po;

import java.util.List;

/**
 * 场景模板选择表格MODEL
 * 
 * @author WANGJUN
 * 
 */
public class SceneTableModel {
	// 子场景id值
	private String childSceneId;
	// 子场景中文名
	private String childSceneName;
	// 子场景对应的表头
	private String[] tableHeader;
	// 子场景对应的数据
	private List<String[]> tableData;	
	// 子场景对应主材表头
	private String[] manSceneTableHeader;
	// 子场景对应辅材表头
	private String[] assistMaterialTableHeader;

	// 新建页面回显用
	// 定额字段串
	private String quotaField;
	// 主材选择的定额的值串
	private String existQuotaValue;
	// 辅材选择的定额的值串
	private String assistExistQuotaValue;

	public String getChildSceneId() {
		return childSceneId;
	}

	public void setChildSceneId(String childSceneId) {
		this.childSceneId = childSceneId;
	}

	public String getChildSceneName() {
		return childSceneName;
	}

	public void setChildSceneName(String childSceneName) {
		this.childSceneName = childSceneName;
	}

	public String[] getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(String[] tableHeader) {
		this.tableHeader = tableHeader;
	}

	public List<String[]> getTableData() {
		return tableData;
	}

	public void setTableData(List<String[]> tableData) {
		this.tableData = tableData;
	}

	public String getQuotaField() {
		return quotaField;
	}

	public void setQuotaField(String quotaField) {
		this.quotaField = quotaField;
	}

	public String getExistQuotaValue() {
		return existQuotaValue;
	}

	public void setExistQuotaValue(String existQuotaValue) {
		this.existQuotaValue = existQuotaValue;
	}

	public String getAssistExistQuotaValue() {
		return assistExistQuotaValue;
	}

	public void setAssistExistQuotaValue(String assistExistQuotaValue) {
		this.assistExistQuotaValue = assistExistQuotaValue;
	}

	public String[] getAssistMaterialTableHeader() {
		return assistMaterialTableHeader;
	}

	public void setAssistMaterialTableHeader(String[] assistMaterialTableHeader) {
		this.assistMaterialTableHeader = assistMaterialTableHeader;
	}

	public String[] getManSceneTableHeader() {
		return manSceneTableHeader;
	}

	public void setManSceneTableHeader(String[] manSceneTableHeader) {
		this.manSceneTableHeader = manSceneTableHeader;
	}

	
	
}
