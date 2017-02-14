package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检计划资源变更 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
public class InspectPlanResChange {
	private String id;
	private String planId;        //计划ID
	private String planChangeId;  //计划变更ID
	private Long planResId;       //计划资源ID
	private String executeObject; //执行对象
	private String executeDept;   //执行对象部门
	private String executeType;   //执行对象类型
	private Date planStartTime;   //计划开始时间
	private Date planEndTime;     //计划结束时间
	private Integer changeType;   //是否本月执行(0否 1是)
	private String preExecuteObject; //变更前执行对象
	private Date prePlanStartTime;   //变更前计划开始时间
	private Date prePlanEndTime;     //变更前计划结束时间
	private Integer preChangeType;   //变更前是否本月执行(0否 1是)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanChangeId() {
		return planChangeId;
	}
	public void setPlanChangeId(String planChangeId) {
		this.planChangeId = planChangeId;
	}
	public Long getPlanResId() {
		return planResId;
	}
	public void setPlanResId(Long planResId) {
		this.planResId = planResId;
	}
	public String getExecuteObject() {
		return executeObject;
	}
	public void setExecuteObject(String executeObject) {
		this.executeObject = executeObject;
	}
	public String getExecuteType() {
		return executeType;
	}
	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	public String getExecuteDept() {
		return executeDept;
	}
	public void setExecuteDept(String executeDept) {
		this.executeDept = executeDept;
	}
	public String getPreExecuteObject() {
		return preExecuteObject;
	}
	public void setPreExecuteObject(String preExecuteObject) {
		this.preExecuteObject = preExecuteObject;
	}
	public Date getPrePlanStartTime() {
		return prePlanStartTime;
	}
	public void setPrePlanStartTime(Date prePlanStartTime) {
		this.prePlanStartTime = prePlanStartTime;
	}
	public Date getPrePlanEndTime() {
		return prePlanEndTime;
	}
	public void setPrePlanEndTime(Date prePlanEndTime) {
		this.prePlanEndTime = prePlanEndTime;
	}
	public Integer getPreChangeType() {
		return preChangeType;
	}
	public void setPreChangeType(Integer preChangeType) {
		this.preChangeType = preChangeType;
	}
	
	
}
