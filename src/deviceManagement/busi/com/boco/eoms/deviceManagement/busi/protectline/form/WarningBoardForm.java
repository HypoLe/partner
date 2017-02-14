package com.boco.eoms.deviceManagement.busi.protectline.form;

import com.boco.eoms.base.webapp.form.BaseForm;

public class WarningBoardForm   extends BaseForm implements
java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String id; // 主键
	
	private String personnelId;//填报人
	
	private String greatTime;//填报时间
	
	private String areaId;//所属地市
	
    private String auditId; // 审核人
	
	private String itemName;//项目名称
	
	private String repeaterSection;//增补警示牌/宣传牌中继段名称
	
	private String repeaterSectionMileage;//增补警示牌/宣传牌中继段里程（公里）
	
	private String warningBoardOldNumber; // 中继段原有警示牌/宣传牌数量（块）
		
    private String warningBoardNewNumber;//计划增补警示牌/宣传牌数量（块）
		
	private String actualCompletion;//实际完成情况（块）
		
	private String completionTime;//完成时间
		
	private String unfinishedReason;//未完成原因
		
	private String assessResult;//新增补警示牌/宣传牌质量评估结果
	
    private String remarks;//备注
			
	private String status;//状态
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除

	
	
	
	



	public String getActualCompletion() {
		return actualCompletion;
	}








	public void setActualCompletion(String actualCompletion) {
		this.actualCompletion = actualCompletion;
	}








	public String getAreaId() {
		return areaId;
	}








	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}








	public String getAssessResult() {
		return assessResult;
	}








	public void setAssessResult(String assessResult) {
		this.assessResult = assessResult;
	}








	public String getAuditId() {
		return auditId;
	}








	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}








	public String getCompletionTime() {
		return completionTime;
	}








	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}








	public String getDeleted() {
		return deleted;
	}








	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}








	public String getGreatTime() {
		return greatTime;
	}








	public void setGreatTime(String greatTime) {
		this.greatTime = greatTime;
	}








	public String getId() {
		return id;
	}








	public void setId(String id) {
		this.id = id;
	}








	public String getItemName() {
		return itemName;
	}








	public void setItemName(String itemName) {
		this.itemName = itemName;
	}








	public String getPersonnelId() {
		return personnelId;
	}








	public void setPersonnelId(String personnelId) {
		this.personnelId = personnelId;
	}








	public String getRemarks() {
		return remarks;
	}








	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}








	public String getRepeaterSection() {
		return repeaterSection;
	}








	public void setRepeaterSection(String repeaterSection) {
		this.repeaterSection = repeaterSection;
	}








	public String getRepeaterSectionMileage() {
		return repeaterSectionMileage;
	}








	public void setRepeaterSectionMileage(String repeaterSectionMileage) {
		this.repeaterSectionMileage = repeaterSectionMileage;
	}








	public String getStatus() {
		return status;
	}








	public void setStatus(String status) {
		this.status = status;
	}








	public String getUnfinishedReason() {
		return unfinishedReason;
	}








	public void setUnfinishedReason(String unfinishedReason) {
		this.unfinishedReason = unfinishedReason;
	}








	public String getWarningBoardNewNumber() {
		return warningBoardNewNumber;
	}








	public void setWarningBoardNewNumber(String warningBoardNewNumber) {
		this.warningBoardNewNumber = warningBoardNewNumber;
	}








	public String getWarningBoardOldNumber() {
		return warningBoardOldNumber;
	}








	public void setWarningBoardOldNumber(String warningBoardOldNumber) {
		this.warningBoardOldNumber = warningBoardOldNumber;
	}








	@Override
	public boolean equals(Object o) {
		if (o instanceof WarningBoardForm) {
			WarningBoardForm a = (WarningBoardForm) o;
			if (this.id != null || this.id.equals(a.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	

}
