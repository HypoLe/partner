package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检计划变更 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:26:12 PM 
 */
public class InspectPlanChange {
	private String id;
	private String planId;
	private String changeTitle;
	private String changeOption;
	private Integer state;            //0审批不通过 1审批通过 2待审批 3未提交审批
	private Date changeTime;
	private String creator;
	private String approveObjectType; //审批对象类型
	private String approveObject;     //审批对象
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
	public String getChangeTitle() {
		return changeTitle;
	}
	public void setChangeTitle(String changeTitle) {
		this.changeTitle = changeTitle;
	}
	public String getChangeOption() {
		return changeOption;
	}
	public void setChangeOption(String changeOption) {
		this.changeOption = changeOption;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getApproveObjectType() {
		return approveObjectType;
	}
	public void setApproveObjectType(String approveObjectType) {
		this.approveObjectType = approveObjectType;
	}
	public String getApproveObject() {
		return approveObject;
	}
	public void setApproveObject(String approveObject) {
		this.approveObject = approveObject;
	}
	
	
}
