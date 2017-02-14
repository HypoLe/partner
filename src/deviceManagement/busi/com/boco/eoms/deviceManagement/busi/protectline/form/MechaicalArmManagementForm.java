package com.boco.eoms.deviceManagement.busi.protectline.form;

import org.apache.struts.action.ActionForm;

public class MechaicalArmManagementForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String writePerson;//填报人
	private String writeTime;//填报时间
	private String belongTheLocal;//所属地市
	private String approvingPerson;//审核人
	private String projectName;//项目名称
	private String constructionMachineryName;//大型施工机械名称
	private String mechanicalArm_workyard;//大型施工机械手施工地点
	private String mechanicalArmName;//大型施工机械手姓名
	private String contactNumber;//联系电话
	private String visitSituation;//走访情况
	private String remarks;//备注
	private String state;//状态
	private String suggest;//建议
	private String approvingtime;//审核时间
	private String approvingresult;//审核结果
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public String getApprovingtime() {
		return approvingtime;
	}
	public void setApprovingtime(String approvingtime) {
		this.approvingtime = approvingtime;
	}
	public String getApprovingresult() {
		return approvingresult;
	}
	public void setApprovingresult(String approvingresult) {
		this.approvingresult = approvingresult;
	}
	public String getWritePerson() {
		return writePerson;
	}
	public void setWritePerson(String writePerson) {
		this.writePerson = writePerson;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getBelongTheLocal() {
		return belongTheLocal;
	}
	public void setBelongTheLocal(String belongTheLocal) {
		this.belongTheLocal = belongTheLocal;
	}
	public String getApprovingPerson() {
		return approvingPerson;
	}
	public void setApprovingPerson(String approvingPerson) {
		this.approvingPerson = approvingPerson;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getConstructionMachineryName() {
		return constructionMachineryName;
	}
	public void setConstructionMachineryName(String constructionMachineryName) {
		this.constructionMachineryName = constructionMachineryName;
	}
	public String getMechanicalArm_workyard() {
		return mechanicalArm_workyard;
	}
	public void setMechanicalArm_workyard(String mechanicalArmWorkyard) {
		mechanicalArm_workyard = mechanicalArmWorkyard;
	}
	public String getMechanicalArmName() {
		return mechanicalArmName;
	}
	public void setMechanicalArmName(String mechanicalArmName) {
		this.mechanicalArmName = mechanicalArmName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getVisitSituation() {
		return visitSituation;
	}
	public void setVisitSituation(String visitSituation) {
		this.visitSituation = visitSituation;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
