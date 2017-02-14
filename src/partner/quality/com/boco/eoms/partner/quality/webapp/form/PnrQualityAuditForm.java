package com.boco.eoms.partner.quality.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

public class PnrQualityAuditForm extends BaseForm{
    
	private String id;
	private String mainId;
	private String auditMan;
	private String auditDept;
	private String auditTel;
	private String isOvertime;
	private String isQualified;
	private String score;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getAuditMan() {
		return auditMan;
	}
	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}
	public String getAuditDept() {
		return auditDept;
	}
	public void setAuditDept(String auditDept) {
		this.auditDept = auditDept;
	}
	public String getAuditTel() {
		return auditTel;
	}
	public void setAuditTel(String auditTel) {
		this.auditTel = auditTel;
	}
	public String getIsOvertime() {
		return isOvertime;
	}
	public void setIsOvertime(String isOvertime) {
		this.isOvertime = isOvertime;
	}
	public String getIsQualified() {
		return isQualified;
	}
	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
