package com.boco.eoms.partner.netresource.model.bs;

import java.io.Serializable;

/**
 * 室外箱体
 * @author chenbing
 *
 */
public class OutdoorCabinet implements Serializable{
		
	/**主键*/
	private String id;
	/**资源类标识*/
	private String resIdentifier;
	/**数据标识*/
	private String dataIdentifier;
	/**设备放置点名称*/
	private String outCabinetName;
	/**机房别名*/
	private String anotherOCName;
	/**
	 * 所属地市ID
	 */
	private String city;
	/**所属区域ID*/
	private String country;
	/**巡检专业*/
	private String specialty;
	/**资源类别*/
	private String resType;
	/**资源级别*/
	private String resLevel;
	/**维护区域*/
	private String maintenanceArea;
	/**放置点地址*/
	private String ocAddress;
	/**放置点标准地址*/
	private String place;
	/**经度*/
	private double  longitude;
	/**纬度*/
	private double  latitude;
	/**是否租用空间*/
	private String  isRentSpace;
	/**租用空间面积*/
	private String  rentalSpaceArea;
	/**出租方*/
	private String  lessor;
	/**租用期限*/
	private String  leaseDuration;
	/**备注*/
	private String  remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getResIdentifier() {
		return resIdentifier;
	}
	public void setResIdentifier(String resIdentifier) {
		this.resIdentifier = resIdentifier;
	}
	public String getDataIdentifier() {
		return dataIdentifier;
	}
	public void setDataIdentifier(String dataIdentifier) {
		this.dataIdentifier = dataIdentifier;
	}
	public String getOutCabinetName() {
		return outCabinetName;
	}
	public void setOutCabinetName(String outCabinetName) {
		this.outCabinetName = outCabinetName;
	}
	public String getAnotherOCName() {
		return anotherOCName;
	}
	public void setAnotherOCName(String anotherOCName) {
		this.anotherOCName = anotherOCName;
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
	public String getMaintenanceArea() {
		return maintenanceArea;
	}
	public void setMaintenanceArea(String maintenanceArea) {
		this.maintenanceArea = maintenanceArea;
	}
	public String getOcAddress() {
		return ocAddress;
	}
	public void setOcAddress(String ocAddress) {
		this.ocAddress = ocAddress;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getIsRentSpace() {
		return isRentSpace;
	}
	public void setIsRentSpace(String isRentSpace) {
		this.isRentSpace = isRentSpace;
	}
	public String getRentalSpaceArea() {
		return rentalSpaceArea;
	}
	public void setRentalSpaceArea(String rentalSpaceArea) {
		this.rentalSpaceArea = rentalSpaceArea;
	}
	public String getLessor() {
		return lessor;
	}
	public void setLessor(String lessor) {
		this.lessor = lessor;
	}
	public String getLeaseDuration() {
		return leaseDuration;
	}
	public void setLeaseDuration(String leaseDuration) {
		this.leaseDuration = leaseDuration;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResLevel() {
		return resLevel;
	}
	public void setResLevel(String resLevel) {
		this.resLevel = resLevel;
	}
	public OutdoorCabinet() {
		
	}
	public OutdoorCabinet(String id, String outCabinetName) {
		this.id = id;
		this.outCabinetName = outCabinetName;
	}
	
	
	
	
	
}
