package com.boco.eoms.partner.dataSynch.model;

/** 
 * Description: 数据同步映射配置
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     Zhangkeqi
 * @version:    1.0
 * Create at:   Mar 30, 2012 2:47:05 PM
 */
public class SynchMappingConfig {
	private String fieldName;   //属性名称
	private String required;    //是否必填 Y是 N否
	private String mapping;     //是否映射 Y是 N否
	private String mappingType; //映射方式(可以根据映射方式对属性进行映射)
	private String zhName;      //中文
	private String initDictId;  //字典映射对应的初始字典项
	
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMappingType() {
		return mappingType;
	}
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}
	public String getInitDictId() {
		return initDictId;
	}
	public void setInitDictId(String initDictId) {
		this.initDictId = initDictId;
	}
	
}
