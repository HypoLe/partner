package com.boco.eoms.partner.eva.model;

import java.util.Date;
import com.boco.eoms.base.model.BaseObject;

public class PnrEvaAuditInfo extends BaseObject{

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板id
	 */
	private String templateId;

	/**
	 *送审时间
	 */
	private Date createTime ;
	
	/**
	 * 审核类型
	 */
	private String auditType ;
	
	/**
	 * 审核者
	 * @return
	 */
	private String auditOrg ;
	
	/**
	 * 审核类型
	 * @return
	 */
	private String auditOrgType ;
	
	/**
	 * 审核人
	 * @return
	 */
	private String auditUser ;
	
	/**
	 * 审核时间
	 * @return
	 */
	private Date auditTime ;
	
	/**
	 * 审核结果
	 * @return
	 */
	private String auditResult ;
	
	/**
	 * 审核说明
	 * @return
	 */
	private String remark ;
	
	/**
	 * 模板名字（数据库不存在该字段，于页面显示用）
	 * @return
	 */
	private String templateName ;


	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuditOrg() {
		return auditOrg;
	}

	public void setAuditOrg(String auditOrg) {
		this.auditOrg = auditOrg;
	}

	public String getAuditOrgType() {
		return auditOrgType;
	}

	public void setAuditOrgType(String auditOrgType) {
		this.auditOrgType = auditOrgType;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public boolean equals(Object o) {
		return false;
	}

	public String toString() {
		return null;
	}

	public int hashCode() {
		return 0;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

}
