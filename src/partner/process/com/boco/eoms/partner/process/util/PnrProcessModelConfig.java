package com.boco.eoms.partner.process.util;

public class PnrProcessModelConfig {

	private String modelName;//模块名称
	private String modelCode;//编码
	private String modelServiceBeanId;//模块对应的beanId
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelServiceBeanId() {
		return modelServiceBeanId;
	}
	public void setModelServiceBeanId(String modelServiceBeanId) {
		this.modelServiceBeanId = modelServiceBeanId;
	}
	
	
}
