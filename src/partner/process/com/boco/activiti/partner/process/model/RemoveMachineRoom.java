package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
   * @author wangyue
   * @ClassName: RemoveMachineRoom
   * @Version 版本 
   * @Copyright boco
   * @date Apr 1, 2015 1:43:13 PM
   * @description 机房拆除实体类
 */
public class RemoveMachineRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 368865843097065470L;
	/**工单名称*/
	private String theme;
	/**部门*/
	private String dept;
	/**地市*/
	private String city;
	/**区县*/
	private String country;
	/**机房类型*/
	private String stationType;
	/**机房名称*/
	private String stationName;
	/**机房级别*/
	private String stationLevel;
	/**机房面积*/
	private String stationArea;
	/**机房产权*/
	private String stationEquity;
	/**租金*/
	private String stationRent;
	/**光缆纤芯数*/
	private String opticCableSum;
	/**设备机甲数*/
	private String equipmentSum;
	/**对应能耗系统机房名称*/
	private String energyStationName;
	/**对应能耗系统机房编号*/
	private String energyStationNumber;
	/**对应能耗系统是否关站*/
	private String energyIsStation;
	/**发起时间*/
	private Date sendTime;
	/**平均经度*/
	private String averageLongitude;
	/**平均纬度*/
	private String averageLatitude;
	/**描述*/
	private String description;
	/**工单流程id*/
	private String processInstanceId;
	/**工单状态*/
	private Integer state;
	/**归档时间*/
	private Date archivingTime;
	/**最后一次回复时间*/
	private Date lastReplyTime;
	/**附件个数*/
    private Integer attachmentsNum;
    /**区县维护中心*/
    private String initiator;
    /**市公司运维部主管*/
    private String cityManageCharge;
    /**省公司运维部主管*/
    private String provinceManageCharge;
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
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
	public String getStationRent() {
		return stationRent;
	}
	public void setStationRent(String stationRent) {
		this.stationRent = stationRent;
	}
	public String getOpticCableSum() {
		return opticCableSum;
	}
	public void setOpticCableSum(String opticCableSum) {
		this.opticCableSum = opticCableSum;
	}
	public String getEquipmentSum() {
		return equipmentSum;
	}
	public void setEquipmentSum(String equipmentSum) {
		this.equipmentSum = equipmentSum;
	}
	public String getEnergyStationName() {
		return energyStationName;
	}
	public void setEnergyStationName(String energyStationName) {
		this.energyStationName = energyStationName;
	}
	public String getEnergyStationNumber() {
		return energyStationNumber;
	}
	public void setEnergyStationNumber(String energyStationNumber) {
		this.energyStationNumber = energyStationNumber;
	}
	public String getEnergyIsStation() {
		return energyIsStation;
	}
	public void setEnergyIsStation(String energyIsStation) {
		this.energyIsStation = energyIsStation;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getArchivingTime() {
		return archivingTime;
	}
	public void setArchivingTime(Date archivingTime) {
		this.archivingTime = archivingTime;
	}
	public Date getLastReplyTime() {
		return lastReplyTime;
	}
	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}
	public Integer getAttachmentsNum() {
		return attachmentsNum;
	}
	public void setAttachmentsNum(Integer attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
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
	
	
	
}
