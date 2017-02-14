package com.boco.eoms.partner.resourceInfo.model;

public class Apparatus {
	private int id;//序号
	//private String refid;//关联id，用于变更申请时保存源数据，便于在终止申请时数据恢复
	private String area;//区域
	private String maintenanceMajor ;//维护专业        
	private String apparatusName;//仪器仪表名称    
	private String apparatusType;//型号            
	private String apparatusSerialNumber ;//产品序列号      
	private String purchasingTime;//采购时间        
	private String apparatusDateOfProduction;//出厂日期        
	private String apparatusServiceLife;//使用年限        
	private String apparatusStatus;//状态            
	private String apparatusProperty;//产权属性        
	private String apparatusBelongs;//资产所属        
	private String maintenanceCompany;//代维公司组织机构
	private String visible;//是否可见；当发起申请时visible为1；
	private String deleted;//1表示已经删除；
	private String notes;
	private String addTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getRefid() {
//		return refid;
//	}
//	public void setRefid(String refid) {
//		this.refid = refid;
//	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMaintenanceMajor() {
		return maintenanceMajor;
	}
	public void setMaintenanceMajor(String maintenanceMajor) {
		this.maintenanceMajor = maintenanceMajor;
	}
	public String getApparatusName() {
		return apparatusName;
	}
	public void setApparatusName(String apparatusName) {
		this.apparatusName = apparatusName;
	}
	public String getApparatusType() {
		return apparatusType;
	}
	public void setApparatusType(String apparatusType) {
		this.apparatusType = apparatusType;
	}
	public String getApparatusSerialNumber() {
		return apparatusSerialNumber;
	}
	public void setApparatusSerialNumber(String apparatusSerialNumber) {
		this.apparatusSerialNumber = apparatusSerialNumber;
	}
	public String getPurchasingTime() {
		return purchasingTime;
	}
	public void setPurchasingTime(String purchasingTime) {
		this.purchasingTime = purchasingTime;
	}
	public String getApparatusDateOfProduction() {
		return apparatusDateOfProduction;
	}
	public void setApparatusDateOfProduction(String apparatusDateOfProduction) {
		this.apparatusDateOfProduction = apparatusDateOfProduction;
	}
	public String getApparatusServiceLife() {
		return apparatusServiceLife;
	}
	public void setApparatusServiceLife(String apparatusServiceLife) {
		this.apparatusServiceLife = apparatusServiceLife;
	}
	public String getApparatusStatus() {
		return apparatusStatus;
	}
	public void setApparatusStatus(String apparatusStatus) {
		this.apparatusStatus = apparatusStatus;
	}
	public String getApparatusProperty() {
		return apparatusProperty;
	}
	public void setApparatusProperty(String apparatusProperty) {
		this.apparatusProperty = apparatusProperty;
	}
	public String getApparatusBelongs() {
		return apparatusBelongs;
	}
	public void setApparatusBelongs(String apparatusBelongs) {
		this.apparatusBelongs = apparatusBelongs;
	}
	public String getMaintenanceCompany() {
		return maintenanceCompany;
	}
	public void setMaintenanceCompany(String maintenanceCompany) {
		this.maintenanceCompany = maintenanceCompany;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
}
