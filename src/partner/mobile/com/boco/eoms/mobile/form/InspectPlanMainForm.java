package com.boco.eoms.mobile.form;

/** 
 * Description: 巡检主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 5:22:14 PM 
 */
public class InspectPlanMainForm {
	private  String id;
	private String planName;
	private String specialty;
	private String specialtyName;
	private String partnerDeptId;     //合作伙伴
	private String deptMagId;         //合作伙伴部门
	private String deptMagName;         //合作伙伴部门
	private String content;
	private String resNum;           //关联巡检资源总数
	private String resDoneNum;       //已完成巡检资源数
	private String resExceptionNum;  //巡检异常资源数
	private String timeOnSite;         //在站时长
	private String createTime;
	private String creator;
	private String year;
	private String month;
	private String approveObjectType; //审批对象类型
	private String approveObject;     //审批对象
	private String approveStatus;    //0审批不通过 1审批通过 2待审批 3未提交审批
	private String resConfig;        //是否分配巡检资源(0否 1是)
	private String status;           //是否可用(0否 1是)
	
	
	public String getDeptMagName() {
		return deptMagName;
	}
	public void setDeptMagName(String deptMagName) {
		this.deptMagName = deptMagName;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getPartnerDeptId() {
		return partnerDeptId;
	}
	public void setPartnerDeptId(String partnerDeptId) {
		this.partnerDeptId = partnerDeptId;
	}
	public String getDeptMagId() {
		return deptMagId;
	}
	public void setDeptMagId(String deptMagId) {
		this.deptMagId = deptMagId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResNum() {
		return resNum;
	}
	public void setResNum(String resNum) {
		this.resNum = resNum;
	}
	public String getResDoneNum() {
		return resDoneNum;
	}
	public void setResDoneNum(String resDoneNum) {
		this.resDoneNum = resDoneNum;
	}
	public String getResExceptionNum() {
		return resExceptionNum;
	}
	public void setResExceptionNum(String resExceptionNum) {
		this.resExceptionNum = resExceptionNum;
	}
	public String getTimeOnSite() {
		return timeOnSite;
	}
	public void setTimeOnSite(String timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getResConfig() {
		return resConfig;
	}
	public void setResConfig(String resConfig) {
		this.resConfig = resConfig;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
