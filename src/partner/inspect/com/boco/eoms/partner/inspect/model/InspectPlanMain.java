package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检主体 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 5:22:14 PM 
 */
public class InspectPlanMain {
	private String id;
	private String planName;
	private String specialty;
	private String partnerDeptId;     //合作伙伴
	private String deptMagId;         //合作伙伴部门
	private String content;
	private Integer resNum;           //关联巡检资源总数
	private Integer resDoneNum;       //已完成巡检资源数
	private Integer resPlanDoneNum;   //计划完成巡检资源数
	private Integer resExceptionNum;  //巡检异常资源数
	private Float timeOnSite;         //在站时长
	private Date createTime;
	private String creator;           //创建者
	private String creatorDeptId;     //创建者所在部门
	private String year;
	private String month;
	private String approveObjectType; //审批对象类型(0人员 1角色 2部门)
	private String approveObject;     //审批对象
	private Integer approveStatus;    //0审批不通过 1审批通过 2待审批 3未提交审批
	private Integer resConfig;        //是否分配巡检资源(0否 1是)
	private Integer status;           //是否可用(0否 1是)
	private Integer copyFlag;         //是否下月复制(0否 1是)
	private Integer finishFlag;       //是否全部资源都完成(0否 1是)
	
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
	public Integer getResNum() {
		return resNum;
	}
	public void setResNum(Integer resNum) {
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
	public String getDeptMagId() {
		return deptMagId;
	}
	public void setDeptMagId(String deptMagId) {
		this.deptMagId = deptMagId;
	}
	public Integer getResDoneNum() {
		return resDoneNum;
	}
	public void setResDoneNum(Integer resDoneNum) {
		this.resDoneNum = resDoneNum;
	}
	public Integer getResExceptionNum() {
		return resExceptionNum;
	}
	public void setResExceptionNum(Integer resExceptionNum) {
		this.resExceptionNum = resExceptionNum;
	}
	public Float getTimeOnSite() {
		return timeOnSite;
	}
	public void setTimeOnSite(Float timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	public Integer getResPlanDoneNum() {
		return resPlanDoneNum;
	}
	public void setResPlanDoneNum(Integer resPlanDoneNum) {
		this.resPlanDoneNum = resPlanDoneNum;
	}
	public String getCreatorDeptId() {
		return creatorDeptId;
	}
	public void setCreatorDeptId(String creatorDeptId) {
		this.creatorDeptId = creatorDeptId;
	}
	public Integer getCopyFlag() {
		return copyFlag;
	}
	public void setCopyFlag(Integer copyFlag) {
		this.copyFlag = copyFlag;
	}
	public Integer getFinishFlag() {
		return finishFlag;
	}
	public void setFinishFlag(Integer finishFlag) {
		this.finishFlag = finishFlag;
	}
	
}
