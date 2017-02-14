package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class SchemeRateRejectModel  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String city;                //地市
	private String county;				//区县
	private String themeinterface;		//工单类型
	private String theme;				//工单主题
	private String handleDescription;	//退单原因      --处理描述
	private String processInstanceId;   //流程ID
	private String auditor;				//退单账号      --审批人
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getThemeinterface() {
		return themeinterface;
	}
	public void setThemeinterface(String themeinterface) {
		this.themeinterface = themeinterface;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getHandleDescription() {
		return handleDescription;
	}
	public void setHandleDescription(String handleDescription) {
		this.handleDescription = handleDescription;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
}
