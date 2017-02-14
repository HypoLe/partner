package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检审批记录 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 5:18:07 PM 
 */
public class InspectPlanApprove {
	
	private String id;
	private String planId;
	private String planChangeId;      //计划变更ID
	private Integer approveStatus;  //0通过 1不通过
	private String approveIdea;
	private String approver;
	private String approverDept;
	private Date approveTime;
	private Integer planType;       //0变更计划 1正常计划
	
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
	public Integer getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getApproveIdea() {
		return approveIdea;
	}
	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproverDept() {
		return approverDept;
	}
	public void setApproverDept(String approverDept) {
		this.approverDept = approverDept;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public String getPlanChangeId() {
		return planChangeId;
	}
	public void setPlanChangeId(String planChangeId) {
		this.planChangeId = planChangeId;
	}
	
	
}
