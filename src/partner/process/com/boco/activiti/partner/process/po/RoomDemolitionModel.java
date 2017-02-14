package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

public class RoomDemolitionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 任务ID
	private String taskId;
	// userTaskId
	private String taskDefKey;
	// userTaskName
	private String statusName;
	// 主键
	private String id;
	// 工单流程id
	private String processInstanceId;
	// 工单名称
	private String theme;
	// 工单状态
	private Integer state;
	// 工单发起人
	private String createWork;
	// 部门
	private String dept;
	// 地市
	private String city;
	// 区县
	private String country;
	// 机房类型
	private String stationType;
	// 机房名称
	private String stationName;
	// 机房级别
	private String stationLevel;
	// 机房面积
	private String stationArea;
	// 机房产权
	private String stationEquity;
	// 年租金
	private String annualRent;
	// 租用日期
	private Date hireDate;
	// 合同到期时间
	private Date contractTime;
	// 光缆纤芯数
	private String opticcableNum;
	// 光缆改造方式
	private String opticcableWay;
	// 设备机架数
	private String equipmentRackNum;
	// 材料费用
	private String materialCost;
	// 对应能耗系统机房名称
	private String energyStationName;
	// 对应能耗系统机房编号
	private String energyStationCode;
	// 对应能耗系统是否关站
	private String energyIsstation;
	// 发起时间
	private Date initiateTime;
	// 派单时间
	private Date sendTime;
	// 描述
	private String description;
	// 附件个数
	private Integer attachmentsNum;
	// 平均经度
	private String averageLongitude;
	// 平均纬度
	private String averageLatitude;
	// 归档时间
	private Date archivingTime;
	// 最后一次回复时间
	private Date lastreplyTime;
	// 区县维护中心
	private String initiator;
	// 市公司运维部主管
	private String cityManageCharge;
	// 省公司运维部主管
	private String provinceManageCharge;

	// 机房ID
	private String stationId;
	// 机房级别ID
	private String stationLevelId;
	// 事前照片与巡检经纬度比对（允许误差范围1千米）
	private String comparisonResults;
	// 自动计算的合计
	private String amountTotal;
	// 机房经度
	private String roomLongitude;
	// 机房纬度
	private String roomLatitude;
	// 照片平均经度
	private String potoAvgLongitude;
	// 照片平均纬度
	private String potoAvgLatitude;
	// 区县维护中心主管
	private String countryCharge;
	// 工单驳回标识
	private String rollbackFlag;
	// 工单编号
	private String sheetId;
	// 机房拆除批次
	private String roomPici;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateWork() {
		return createWork;
	}

	public void setCreateWork(String createWork) {
		this.createWork = createWork;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationLevel() {
		return stationLevel;
	}

	public void setStationLevel(String stationLevel) {
		this.stationLevel = stationLevel;
	}

	public String getStationArea() {
		return stationArea;
	}

	public void setStationArea(String stationArea) {
		this.stationArea = stationArea;
	}

	public String getStationEquity() {
		return stationEquity;
	}

	public void setStationEquity(String stationEquity) {
		this.stationEquity = stationEquity;
	}

	public String getAnnualRent() {
		return annualRent;
	}

	public void setAnnualRent(String annualRent) {
		this.annualRent = annualRent;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getContractTime() {
		return contractTime;
	}

	public void setContractTime(Date contractTime) {
		this.contractTime = contractTime;
	}

	public String getOpticcableNum() {
		return opticcableNum;
	}

	public void setOpticcableNum(String opticcableNum) {
		this.opticcableNum = opticcableNum;
	}

	public String getOpticcableWay() {
		return opticcableWay;
	}

	public void setOpticcableWay(String opticcableWay) {
		this.opticcableWay = opticcableWay;
	}

	public String getEquipmentRackNum() {
		return equipmentRackNum;
	}

	public void setEquipmentRackNum(String equipmentRackNum) {
		this.equipmentRackNum = equipmentRackNum;
	}

	public String getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(String materialCost) {
		this.materialCost = materialCost;
	}

	public String getEnergyStationName() {
		return energyStationName;
	}

	public void setEnergyStationName(String energyStationName) {
		this.energyStationName = energyStationName;
	}

	public String getEnergyStationCode() {
		return energyStationCode;
	}

	public void setEnergyStationCode(String energyStationCode) {
		this.energyStationCode = energyStationCode;
	}

	public String getEnergyIsstation() {
		return energyIsstation;
	}

	public void setEnergyIsstation(String energyIsstation) {
		this.energyIsstation = energyIsstation;
	}

	public Date getInitiateTime() {
		return initiateTime;
	}

	public void setInitiateTime(Date initiateTime) {
		this.initiateTime = initiateTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAttachmentsNum() {
		return attachmentsNum;
	}

	public void setAttachmentsNum(Integer attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}

	public String getAverageLongitude() {
		return averageLongitude;
	}

	public void setAverageLongitude(String averageLongitude) {
		this.averageLongitude = averageLongitude;
	}

	public String getAverageLatitude() {
		return averageLatitude;
	}

	public void setAverageLatitude(String averageLatitude) {
		this.averageLatitude = averageLatitude;
	}

	public Date getArchivingTime() {
		return archivingTime;
	}

	public void setArchivingTime(Date archivingTime) {
		this.archivingTime = archivingTime;
	}

	public Date getLastreplyTime() {
		return lastreplyTime;
	}

	public void setLastreplyTime(Date lastreplyTime) {
		this.lastreplyTime = lastreplyTime;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getCityManageCharge() {
		return cityManageCharge;
	}

	public void setCityManageCharge(String cityManageCharge) {
		this.cityManageCharge = cityManageCharge;
	}

	public String getProvinceManageCharge() {
		return provinceManageCharge;
	}

	public void setProvinceManageCharge(String provinceManageCharge) {
		this.provinceManageCharge = provinceManageCharge;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationLevelId() {
		return stationLevelId;
	}

	public void setStationLevelId(String stationLevelId) {
		this.stationLevelId = stationLevelId;
	}

	public String getComparisonResults() {
		return comparisonResults;
	}

	public void setComparisonResults(String comparisonResults) {
		this.comparisonResults = comparisonResults;
	}

	public String getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}

	public String getRoomLongitude() {
		return roomLongitude;
	}

	public void setRoomLongitude(String roomLongitude) {
		this.roomLongitude = roomLongitude;
	}

	public String getRoomLatitude() {
		return roomLatitude;
	}

	public void setRoomLatitude(String roomLatitude) {
		this.roomLatitude = roomLatitude;
	}

	public String getPotoAvgLongitude() {
		return potoAvgLongitude;
	}

	public void setPotoAvgLongitude(String potoAvgLongitude) {
		this.potoAvgLongitude = potoAvgLongitude;
	}

	public String getPotoAvgLatitude() {
		return potoAvgLatitude;
	}

	public void setPotoAvgLatitude(String potoAvgLatitude) {
		this.potoAvgLatitude = potoAvgLatitude;
	}

	public String getCountryCharge() {
		return countryCharge;
	}

	public void setCountryCharge(String countryCharge) {
		this.countryCharge = countryCharge;
	}

	public String getRollbackFlag() {
		return rollbackFlag;
	}

	public void setRollbackFlag(String rollbackFlag) {
		this.rollbackFlag = rollbackFlag;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public String getRoomPici() {
		return roomPici;
	}

	public void setRoomPici(String roomPici) {
		this.roomPici = roomPici;
	}



}
