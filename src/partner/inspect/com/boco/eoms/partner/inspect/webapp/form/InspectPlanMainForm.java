package com.boco.eoms.partner.inspect.webapp.form;

import java.util.Date;

import com.boco.eoms.base.webapp.form.BaseForm;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 10, 2012 5:03:31 PM 
 */
public class InspectPlanMainForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	private String id;
	private String planName;
	private String specialty;
	private String partnerDeptId;
	private String content;
	private Date createTime;
	private String creator;
	private String year;
	private String month;
	private String approveObjectType;
	private String approveObject;
	private Integer approveStatus;
	private Integer resConfig;
	private Integer status;
	private Integer copyFlag;         //是否下月复制(0否 1是)
	private String satisfaction;      //满意度
	private String textRemark;        //文本描述
	private Date qualityInspectTime;           //质检时间
	private String qualityInspectUser;           //质检人
	private String resType;           //资源类型
	
	
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
	public Integer getCopyFlag() {
		return copyFlag;
	}
	public void setCopyFlag(Integer copyFlag) {
		this.copyFlag = copyFlag;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getTextRemark() {
		return textRemark;
	}
	public void setTextRemark(String textRemark) {
		this.textRemark = textRemark;
	}
	public Date getQualityInspectTime() {
		return qualityInspectTime;
	}
	public void setQualityInspectTime(Date qualityInspectTime) {
		this.qualityInspectTime = qualityInspectTime;
	}
	public String getQualityInspectUser() {
		return qualityInspectUser;
	}
	public void setQualityInspectUser(String qualityInspectUser) {
		this.qualityInspectUser = qualityInspectUser;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	
	
}
