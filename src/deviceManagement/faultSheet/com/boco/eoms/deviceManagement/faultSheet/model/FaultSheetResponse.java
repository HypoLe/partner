package com.boco.eoms.deviceManagement.faultSheet.model;

public class FaultSheetResponse {
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
	private String work_OrderNo;//工单编号
	private String dealstata;//处理状态
	private String detailment_dept;
	private String themess; // 主题
	private String completedtime;
	
	public String getCompletedtime() {
		return completedtime;
	}
	public void setCompletedtime(String completedtime) {
		this.completedtime = completedtime;
	}
	public String getThemess() {
		return themess;
	}
	public void setThemess(String themess) {
		this.themess = themess;
	}
	public String getDetailment_dept() {
		return detailment_dept;
	}
	public void setDetailment_dept(String detailment_dept) {
		this.detailment_dept = detailment_dept;
	}
	public String getDealstata() {
		return dealstata;
	}
	public void setDealstata(String dealstata) {
		this.dealstata = dealstata;
	}
	public String getWork_OrderNo() {
		return work_OrderNo;
	}
	public void setWork_OrderNo(String work_OrderNo) {
		this.work_OrderNo = work_OrderNo;
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
