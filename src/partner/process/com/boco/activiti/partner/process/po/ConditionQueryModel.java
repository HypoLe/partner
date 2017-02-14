package com.boco.activiti.partner.process.po;

public class ConditionQueryModel {
	/**派单开始时间*/
	private String sendStartTime;
	/**工单结束时间*/
	private String sendEndTime;
	/**工单号*/
	private String workNumber;
	/**工单主题*/
	private String theme;
	/**地市*/
	private String city;
	/**区县*/
	private String country;
	/**线路属性*/
	private String lineType;
	/**工单类型*/
	private String workOrderType;
	/**olt工单类型*/
	private String jobOrderType;
	/**olt批次*/
	private String batch;
	/**工单状态*/
	private String status;
	/**流程类型*/
	private String processType;
	/**预检预修工单类别*/
	private String precheckFlag;
	//主场景ID
	private String mainSceneId;
	//每页显示条数
	private String pageSize;
	/**紧急程度*/
	private String workOrderDegree;
	/**关键字*/
	private String keyWord;
	/**大修改造线路类型*/
	private String linetype;
	/**设备位置/局址名称*/
	private String jfAddressName;
	//公费
	private String totalFee;
	//材料费
	private String totalMaterialCost;
	
	//表名
	private String tableName;
	
	//环节key值
	private String linkKey;
	
	//环节对应的编号
	private String linkSerialNumber;
	
	//月份
	private String month;
	
	//机房拆除批次
	private String roomPici;
	
	//资源类型
	private String resourceType;
	
	//问题类型
	private String questionType;
	
	//专业
	private String specialty; 
	
	//工单子类型
	private String subTypeName;
	
	
	public String getSendStartTime() {
		return sendStartTime;
	}
	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}
	public String getSendEndTime() {
		return sendEndTime;
	}
	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getPrecheckFlag() {
		return precheckFlag;
	}
	public void setPrecheckFlag(String precheckFlag) {
		this.precheckFlag = precheckFlag;
	}
	public String getMainSceneId() {
		return mainSceneId;
	}
	public void setMainSceneId(String mainSceneId) {
		this.mainSceneId = mainSceneId;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getWorkOrderDegree() {
		return workOrderDegree;
	}
	public void setWorkOrderDegree(String workOrderDegree) {
		this.workOrderDegree = workOrderDegree;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getLinetype() {
		return linetype;
	}
	public void setLinetype(String linetype) {
		this.linetype = linetype;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getTotalMaterialCost() {
		return totalMaterialCost;
	}
	public void setTotalMaterialCost(String totalMaterialCost) {
		this.totalMaterialCost = totalMaterialCost;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getLinkKey() {
		return linkKey;
	}
	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}
	public String getLinkSerialNumber() {
		return linkSerialNumber;
	}
	public void setLinkSerialNumber(String linkSerialNumber) {
		this.linkSerialNumber = linkSerialNumber;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getJobOrderType() {
		return jobOrderType;
	}
	public void setJobOrderType(String jobOrderType) {
		this.jobOrderType = jobOrderType;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getJfAddressName() {
		return jfAddressName;
	}
	public void setJfAddressName(String jfAddressName) {
		this.jfAddressName = jfAddressName;
	}
	public String getRoomPici() {
		return roomPici;
	}
	public void setRoomPici(String roomPici) {
		this.roomPici = roomPici;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	
}
