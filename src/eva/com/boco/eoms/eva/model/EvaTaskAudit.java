package com.boco.eoms.eva.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.eva.util.DateTimeUtil;

public class EvaTaskAudit extends BaseObject{

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
	 * 汇总表id
	 * @return
	 */
	private String reportId ;
	
	
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
	 * 实际得分
	 * @return
	 */
	private float totalScore ;
	
	/**
	 * 合作伙伴
	 * @return
	 */
	private String partner ;
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
	 *  送审时间（String），页面显示用
	 * @return
	 */
	private String auditCreateString ;
	
	/**
	 * 审核日期（String），页面显示用
	 * @return
	 */
	private String auditDateString ;
	
	
	public String getAuditCreateString() {
		String returnValue = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			returnValue = sdf.format(auditCreate);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public String getAuditDateString() {
		String returnValue = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(auditDate==null){
				return "";
			}
			returnValue = sdf.format(auditDate);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return returnValue;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}


	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditCycle() {
		return auditCycle;
	}

	public void setAuditCycle(String auditCycle) {
		this.auditCycle = auditCycle;
	}

	public Date getAuditCreate() {
		return auditCreate;
	}

	public void setAuditCreate(Date auditCreate) {
		this.auditCreate = auditCreate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	
	public String getAuditTimeStr() {
		return "第"+auditTime+"次";
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}
