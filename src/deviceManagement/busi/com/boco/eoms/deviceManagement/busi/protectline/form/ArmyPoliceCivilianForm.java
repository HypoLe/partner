package com.boco.eoms.deviceManagement.busi.protectline.form;

import org.apache.struts.action.ActionForm;

public class ArmyPoliceCivilianForm extends ActionForm {

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
	private String nameOfOrganization;//军警民共建单位名称
	private String cooperativeContent ;//合作内容
	private String lengthOfOpticalCableRoutes ;//负责光缆线路里程（km）
	private String realEffect;//实际效果;
	private String remarks;//备注
	private String state;//状态
	private String suggest;//建议
	private String approvingtime;//审核时间
	private String approvingresult;//审核结果
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	
	public String getNameOfOrganization() {
		return nameOfOrganization;
	}
	public void setNameOfOrganization(String nameOfOrganization) {
		this.nameOfOrganization = nameOfOrganization;
	}
	public String getLengthOfOpticalCableRoutes() {
		return lengthOfOpticalCableRoutes;
	}
	public void setLengthOfOpticalCableRoutes(String lengthOfOpticalCableRoutes) {
		this.lengthOfOpticalCableRoutes = lengthOfOpticalCableRoutes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	
	public String getCooperativeContent() {
		return cooperativeContent;
	}
	public void setCooperativeContent(String cooperativeContent) {
		this.cooperativeContent = cooperativeContent;
	}
	
	
	public String getRealEffect() {
		return realEffect;
	}
	public void setRealEffect(String realEffect) {
		this.realEffect = realEffect;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	
}
