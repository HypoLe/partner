package com.boco.eoms.partner.eva.webapp.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrEvaAuditForm extends BaseForm implements java.io.Serializable{

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板ID
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
	 * 地域信息
	 */
	private String areaId ;
	
	/**
	 * 模板名字（数据库不存在该字段，于页面显示用）
	 * @return
	 */
	private String templateName ;
	

	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

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



	public String getAuditType() {
		return auditType;
	}



	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}



	public String getAreaId() {
		return areaId;
	}



	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}



	public String getTemplateName() {
		return templateName;
	}



	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	

}
