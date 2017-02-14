package com.boco.eoms.eva.webapp.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.eva.model.EvaAuditInfo;
import com.boco.eoms.eva.util.DateTimeUtil;

public class EvaTaskAuditForm extends BaseForm implements java.io.Serializable{

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 任务编号
	 * @return
	 */
	private String taskId ;
	
	/**
	 * 模板编号
	 * @return
	 */
	private String templateId ;
	
	/**
	 * 指标编号
	 * @return
	 */
	private String kpiId ;
	
	/**
	 * 审核者
	 * @return
	 */
	private String auditOrg ;
	
	/**
	 * 审核者类型
	 * @return
	 */
	private String auditOrgType ;
	
	/**
	 * 地域信息
	 * @return
	 */
	private String areaId ;
	
	/**
	 * 审核结果
	 * @return
	 */
	private String auditResult ;
	
	/**
	 * 审核说明
	 * @return
	 */
	private String auditRemark ;
	
	/**
	 * 审核日期
	 * @return
	 */
	private Date auditDate ;
	
	/**
	 * 审核人
	 * @return
	 */
	private String auditUser ;
	
	/**
	 * 送审时间
	 * @return
	 */
	private Date auditCreate ;
	
	/**
	 * 审核时间
	 * @return
	 */
	private String auditTime ;
	
	/**
	 * 审核时间
	 * @return
	 */
	private String auditTimeStr ;
	
	/**
	 * 审核周期
	 * @return
	 */
	private String auditCycle ;

	/**
	 * 模板名称（数据库中不存在该字段，作页面显示用）
	 * @return
	 */
	private String templateName ;
	
	/**
	 * 指标名称（数据库中不存在该字段，作页面显示用）
	 * @return
	 */
	private String kpiName ;
	
	/**
	 * 地域名称（数据库中不存在该字段，作页面显示用）
	 * @return
	 */
	private String areaName ;
	
	

	public String getAreaName() {
		return areaName;
	}



	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}



	public String getTemplateName() {
		return templateName;
	}



	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}



	public String getKpiName() {
		return kpiName;
	}



	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}



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





	public String getAuditResult() {
		return auditResult;
	}



	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}







	public String getAreaId() {
		return areaId;
	}



	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}



	public String getTaskId() {
		return taskId;
	}



	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	public String getKpiId() {
		return kpiId;
	}



	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}



	public String getAuditRemark() {
		return auditRemark;
	}



	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	



	public Date getAuditDate() {
		return auditDate;
	}



	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}



	public Date getAuditCreate() {
		return auditCreate;
	}



	public void setAuditCreate(Date auditCreate) {
		this.auditCreate = auditCreate;
	}



	public String getAuditTime() {
		return auditTime;
	}



	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditTimeStr() {
		return "第"+auditTime+"次";
	}

	public String getAuditCycle() {
		return auditCycle;
	}



	public void setAuditCycle(String auditCycle) {
		this.auditCycle = auditCycle;
	}

	

}
