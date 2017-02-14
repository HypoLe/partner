package com.boco.eoms.deviceManagement.faultSheet.model;

public class FaultSheetManagement {
	private String id;
	private String work_OrderNo; // 工单编号
	private String faultState; // 状态
	private String themess; // 主题
	private String operatePerson; // 操作人
	private String operatePerson_Department; // 操作人部门
	private String operatePerson_Contact; // 操作人联系方式
	private String operatePerson_Rule; // 操作人当前角色
	private String operateTime; // 操作时间
	private String attachment; // 附件
	private String detailment_Object; // 派往对象
	private String processLimited; // 处理时限
	private String stationNo; // 站号
	private String base_Station_Location; // 基站属地
	private String bscNo; // 所属BSC名
	private String bcfNo; // bcf号
	private String base_Station_Name; // 基站名称
	private String task_Sources; // 任务来源
	private String task_Profession; // 任务专业
	private String task_Subclass; // 任务子类
	private String importance; // 重要程度
	private String work_Order_Type;// 工单类型
	private String techniqueDifficulty; // 技术难度
	private String coordinateDifficulty; // 协调难度
	private String faultStartTime; // 故障开始时间
	private String faultEndTime; // 故障结束时间
	private String file_TimeLimit; // 归档时限
	private String faultType; // 故障类型
	private String faultDescription; // 故障描述
	private String recovery_Processing_Result; // 故障初步处理结果
	private String fault_Handling_Suggestions; // 故障初步处理建议
	private String remark1;
	private String remark2;
	private String remark3;
	private String remark4;
	private String deleteTime;
	private String idDeleted;
	private String areInTimeReceived;//是否及时接收
	private String areInTimeComplete;//是否及时完成
	private String detailment_dept;//存入部门号
	private String receivedtimelimited;//接收时限
	private String responsereceivedtime;//回复接收时间
	//private String flowTemplateName;//工单流程名称
	
	public String getReceivedtimelimited() {
		return receivedtimelimited;
	}

	public void setReceivedtimelimited(String receivedtimelimited) {
		this.receivedtimelimited = receivedtimelimited;
	}

	public String getAreInTimeReceived() {
		return areInTimeReceived;
	}

	public void setAreInTimeReceived(String areInTimeReceived) {
		this.areInTimeReceived = areInTimeReceived;
	}

	public String getAreInTimeComplete() {
		return areInTimeComplete;
	}

	public void setAreInTimeComplete(String areInTimeComplete) {
		this.areInTimeComplete = areInTimeComplete;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getIdDeleted() {
		return idDeleted;
	}

	public void setIdDeleted(String idDeleted) {
		this.idDeleted = idDeleted;
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


	public String getFaultState() {
		return faultState;
	}

	public void setFaultState(String faultState) {
		this.faultState = faultState;
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

	public String getProcessLimited() {
		return processLimited;
	}

	public void setProcessLimited(String processLimited) {
		this.processLimited = processLimited;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getBase_Station_Location() {
		return base_Station_Location;
	}

	public void setBase_Station_Location(String base_Station_Location) {
		this.base_Station_Location = base_Station_Location;
	}

	public String getBscNo() {
		return bscNo;
	}

	public void setBscNo(String bscNo) {
		this.bscNo = bscNo;
	}

	public String getBcfNo() {
		return bcfNo;
	}

	public void setBcfNo(String bcfNo) {
		this.bcfNo = bcfNo;
	}

	public String getBase_Station_Name() {
		return base_Station_Name;
	}

	public void setBase_Station_Name(String base_Station_Name) {
		this.base_Station_Name = base_Station_Name;
	}

	public String getTask_Sources() {
		return task_Sources;
	}

	public void setTask_Sources(String task_Sources) {
		this.task_Sources = task_Sources;
	}

	public String getTask_Profession() {
		return task_Profession;
	}

	public void setTask_Profession(String task_Profession) {
		this.task_Profession = task_Profession;
	}

	public String getTask_Subclass() {
		return task_Subclass;
	}

	public void setTask_Subclass(String task_Subclass) {
		this.task_Subclass = task_Subclass;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getWork_Order_Type() {
		return work_Order_Type;
	}

	public void setWork_Order_Type(String work_Order_Type) {
		this.work_Order_Type = work_Order_Type;
	}

	public String getTechniqueDifficulty() {
		return techniqueDifficulty;
	}

	public void setTechniqueDifficulty(String techniqueDifficulty) {
		this.techniqueDifficulty = techniqueDifficulty;
	}

	public String getCoordinateDifficulty() {
		return coordinateDifficulty;
	}

	public void setCoordinateDifficulty(String coordinateDifficulty) {
		this.coordinateDifficulty = coordinateDifficulty;
	}

	public String getFaultStartTime() {
		return faultStartTime;
	}

	public void setFaultStartTime(String faultStartTime) {
		this.faultStartTime = faultStartTime;
	}

	public String getFaultEndTime() {
		return faultEndTime;
	}

	public void setFaultEndTime(String faultEndTime) {
		this.faultEndTime = faultEndTime;
	}

	public String getFile_TimeLimit() {
		return file_TimeLimit;
	}

	public void setFile_TimeLimit(String file_TimeLimit) {
		this.file_TimeLimit = file_TimeLimit;
	}

	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getFaultDescription() {
		return faultDescription;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	public String getRecovery_Processing_Result() {
		return recovery_Processing_Result;
	}

	public void setRecovery_Processing_Result(String recovery_Processing_Result) {
		this.recovery_Processing_Result = recovery_Processing_Result;
	}

	public String getFault_Handling_Suggestions() {
		return fault_Handling_Suggestions;
	}

	public void setFault_Handling_Suggestions(String fault_Handling_Suggestions) {
		this.fault_Handling_Suggestions = fault_Handling_Suggestions;
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

	public String getResponsereceivedtime() {
		return responsereceivedtime;
	}

	public void setResponsereceivedtime(String responsereceivedtime) {
		this.responsereceivedtime = responsereceivedtime;
	}

}
