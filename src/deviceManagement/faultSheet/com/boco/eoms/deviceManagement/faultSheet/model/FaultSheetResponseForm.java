package com.boco.eoms.deviceManagement.faultSheet.model;

import org.apache.struts.action.ActionForm;

public class FaultSheetResponseForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String operatePerson; // 操作人
	private String operatePerson_Department; // 操作人部门
	private String operatePerson_Contact; // 操作人联系方式
	private String operatePerson_Rule; // 操作人当前角色
	private String operateTime; // 操作时间
	private String attachment; // 附件
	private String detailment_Object; // 派往对象
	private String faultEndTime;//故障结束时间
	private String faultDealEndTime;//故障处理结束时间
	private String faultDealResult;//故障处理结果
	private String dealstata;//处理状态
	
	public String getDealstata() {
		return dealstata;
	}
	public void setDealstata(String dealstata) {
		this.dealstata = dealstata;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperatePerson() {
		return operatePerson;
	}
	public void setOperatePerson(String operatePerson) {
		this.operatePerson = operatePerson;
	}
	public String getOperatePerson_Department() {
		return operatePerson_Department;
	}
	public void setOperatePerson_Department(String operatePerson_Department) {
		this.operatePerson_Department = operatePerson_Department;
	}
	public String getOperatePerson_Contact() {
		return operatePerson_Contact;
	}
	public void setOperatePerson_Contact(String operatePerson_Contact) {
		this.operatePerson_Contact = operatePerson_Contact;
	}
	public String getOperatePerson_Rule() {
		return operatePerson_Rule;
	}
	public void setOperatePerson_Rule(String operatePerson_Rule) {
		this.operatePerson_Rule = operatePerson_Rule;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getDetailment_Object() {
		return detailment_Object;
	}
	public void setDetailment_Object(String detailment_Object) {
		this.detailment_Object = detailment_Object;
	}
	public String getFaultEndTime() {
		return faultEndTime;
	}
	public void setFaultEndTime(String faultEndTime) {
		this.faultEndTime = faultEndTime;
	}
	public String getFaultDealEndTime() {
		return faultDealEndTime;
	}
	public void setFaultDealEndTime(String faultDealEndTime) {
		this.faultDealEndTime = faultDealEndTime;
	}
	public String getFaultDealResult() {
		return faultDealResult;
	}
	public void setFaultDealResult(String faultDealResult) {
		this.faultDealResult = faultDealResult;
	}
	
}
