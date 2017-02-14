package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: wangchang
 * Date: 13-9-12
 * Time: 下午2:58
 * 附件下载列表类
 * To change this template use File | Settings | File Templates.
 */
public class DownWorkOrderModel implements Serializable {
	
	 private Date sendTime ;//派单时间
	 private String themeinterface;// 工单类别
	 private String taskDefkey;// 环节
	 private String city ;//地市
	 private String country ;//区县
	 private String processInstanceId ;//工单号
	 private String sheetId;// 项目编号
	 private String theme;// 项目名称
	 private String bearService;// 承载业务级别
	 private String workOrderType;// 工单类型
	 private String subType;// 工单子类型
	 private String keyWord;// 关键字
	 private String workOrderDegree ;//紧急程度
	 private String faultDescription ;//项目实施内容描述
	 private String projectAmount;// 项目金额（元）
	 
	 
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getThemeinterface() {
		return themeinterface;
	}
	public void setThemeinterface(String themeinterface) {
		this.themeinterface = themeinterface;
	}
	
	public String getTaskDefkey() {
		return taskDefkey;
	}
	public void setTaskDefkey(String taskDefkey) {
		this.taskDefkey = taskDefkey;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getBearService() {
		return bearService;
	}
	public void setBearService(String bearService) {
		this.bearService = bearService;
	}
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getWorkOrderDegree() {
		return workOrderDegree;
	}
	public void setWorkOrderDegree(String workOrderDegree) {
		this.workOrderDegree = workOrderDegree;
	}
	public String getFaultDescription() {
		return faultDescription;
	}
	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}
	public String getProjectAmount() {
		return projectAmount;
	}
	public void setProjectAmount(String projectAmount) {
		this.projectAmount = projectAmount;
	}
	
	 

}
