package com.boco.eoms.partner.baseinfo.model;

/**  
 * @Title: PnrQualification.java
 * @Package com.boco.eoms.partner.baseinfo.model
 * @Description: 代维资质信息
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 18, 2013  5:14:49 PM  
 */
public class PnrQualification {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 资质名称
	 */
	private String qualifyName;
	/**
	 * 资质级别
	 */
	private String qualifyLevel;
	/**
	 * 发布机构
	 */
	private String issueAuthority;
	/**
	 * 资质截止日期
	 */
	private String outOfDate;
	/**
	 * 关联部门id
	 */
	private String relatedDeptId;
	/**
	 * 添加时间
	 */
	private String addTime;
	/**
	 * 是否删除
	 */
	private String isDeleted;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 系统编号
	 */
	private String sysno;
	/**
	 * 附件路径
	 */
	private String accessories;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQualifyName() {
		return qualifyName;
	}
	public void setQualifyName(String qualifyName) {
		this.qualifyName = qualifyName;
	}
	public String getQualifyLevel() {
		return qualifyLevel;
	}
	public void setQualifyLevel(String qualifyLevel) {
		this.qualifyLevel = qualifyLevel;
	}
	public String getIssueAuthority() {
		return issueAuthority;
	}
	public void setIssueAuthority(String issueAuthority) {
		this.issueAuthority = issueAuthority;
	}
	public String getOutOfDate() {
		return outOfDate;
	}
	public void setOutOfDate(String outOfDate) {
		this.outOfDate = outOfDate;
	}
	
	public String getRelatedDeptId() {
		return relatedDeptId;
	}
	public void setRelatedDeptId(String relatedDeptId) {
		this.relatedDeptId = relatedDeptId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAccessories() {
		return accessories;
	}
	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}
	public String getSysno() {
		return sysno;
	}
	public void setSysno(String sysno) {
		this.sysno = sysno;
	}
	
}
