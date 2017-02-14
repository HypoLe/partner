package com.boco.eoms.partner.deviceInspect.mp;

import java.util.List;

public class NetResourceMapping {
	
	private String specialty;

	private String resourceName;
	
	private String tableName;
	
	private String typeByFiled;
	
	private String fieldName;
	
	private String fieldChName;
	
	private List<String> filedList;
	
	private String resourceMark;

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTypeByFiled() {
		return typeByFiled;
	}

	public void setTypeByFiled(String typeByFiled) {
		this.typeByFiled = typeByFiled;
	}

	public List<String> getFiledList() {
		return filedList;
	}

	public void setFiledList(List<String> filedList) {
		this.filedList = filedList;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getResourceMark() {
		return resourceMark;
	}

	public void setResourceMark(String resourceMark) {
		this.resourceMark = resourceMark;
	}

	public String getFieldChName() {
		return fieldChName;
	}

	public void setFieldChName(String fieldChName) {
		this.fieldChName = fieldChName;
	}
	
	
}
