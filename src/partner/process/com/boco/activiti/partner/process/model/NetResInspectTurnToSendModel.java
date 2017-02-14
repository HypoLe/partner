package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

public class NetResInspectTurnToSendModel  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String processInstanceId;
	private String oldSpecialty; //原来的专业
	private String oldResourceType; //原来的资源类型
	private String oldChecker; //原来的现场核验人
	private String oldCounty;//原来的区县
	private String specialty;//转派后的专业
	private String resourceType;//转派后的资源类型
	private String checker;//转派后的现场核验人
	private String county;//转派后的区县
	private Date operateTime;//操作时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getOldSpecialty() {
		return oldSpecialty;
	}
	public void setOldSpecialty(String oldSpecialty) {
		this.oldSpecialty = oldSpecialty;
	}
	public String getOldResourceType() {
		return oldResourceType;
	}
	public void setOldResourceType(String oldResourceType) {
		this.oldResourceType = oldResourceType;
	}
	public String getOldChecker() {
		return oldChecker;
	}
	public void setOldChecker(String oldChecker) {
		this.oldChecker = oldChecker;
	}
	public String getOldCounty() {
		return oldCounty;
	}
	public void setOldCounty(String oldCounty) {
		this.oldCounty = oldCounty;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
}
