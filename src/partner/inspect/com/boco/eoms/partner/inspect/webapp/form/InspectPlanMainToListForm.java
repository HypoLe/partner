package com.boco.eoms.partner.inspect.webapp.form;

import java.util.Date;

/** 
 * Description: 巡检主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 5:22:14 PM 
 */
public class InspectPlanMainToListForm {
	private String id;
	private String planName;
	private String specialty;
	private String partnerDeptId;
	private String content;
	private String resNum;            //关联巡检资源总数
	private Date createTime;
	private String creator;
	private String year;
	private String month;
	private String approveObjectType; //审批对象类型
	private String approveObject;     //审批对象
	private Integer approveStatus;    //0审批不通过 1审批通过 2待审批 3未提交审批
	private Integer resConfig;        //是否分配巡检资源(0否 1是)
	private Integer status;           //是否可用(0否 1是)
	private Integer hasDone;           //已完成数
	private Integer resNumber;           //已完成数
	private Integer planNumber;           //计划完成数
	private String deptMagId;         //合作伙伴部门
	
	public Integer getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(Integer planNumber) {
		this.planNumber = planNumber;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Integer getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getResConfig() {
		return resConfig;
	}
	public void setResConfig(Integer resConfig) {
		this.resConfig = resConfig;
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
	public Integer getResNumber() {
		return resNumber;
	}
	public void setResNumber(Integer resNumber) {
		this.resNumber = resNumber;
	}
	public Integer getHasDone() {
		return hasDone;
	}
	public void setHasDone(Integer hasDone) {
		this.hasDone = hasDone;
	}
	public String getDeptMagId() {
		return deptMagId;
	}
	public void setDeptMagId(String deptMagId) {
		this.deptMagId = deptMagId;
	}
	
	
}
