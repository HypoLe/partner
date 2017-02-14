package com.boco.eoms.partner.resourceInfo.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class OilEngineForm extends BaseForm {
	private int id;//主键
	private String oilEngineNumber;//油机编号              
	private String oilEngineModel;//油机型号               
	private String oilEngineFactory;//油机厂商             
	private String powerRating;//额定功率                  
	private String standardFuelConsumption;//标准油耗      
	private String oilEngineType;//油机类型                
	private String oilEngineProperty;//产权归属            
	private String oilEngineStatus;//油机状态              
	private String area;//区域                             
	private String maintainCompany;//代维公司              
	private String currentLocation;//当前位置   
	private String addTime;//添加时间
	private String notes;//备注
	
	private String fuleType;//燃料种类
	private String longitude;//经度
	private String latitude;//纬度
	private String place;//存放地点
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOilEngineNumber() {
		return oilEngineNumber;
	}
	public void setOilEngineNumber(String oilEngineNumber) {
		this.oilEngineNumber = oilEngineNumber;
	}
	public String getOilEngineModel() {
		return oilEngineModel;
	}
	public void setOilEngineModel(String oilEngineModel) {
		this.oilEngineModel = oilEngineModel;
	}
	public String getOilEngineFactory() {
		return oilEngineFactory;
	}
	public void setOilEngineFactory(String oilEngineFactory) {
		this.oilEngineFactory = oilEngineFactory;
	}
	public String getPowerRating() {
		return powerRating;
	}
	public void setPowerRating(String powerRating) {
		this.powerRating = powerRating;
	}
	public String getStandardFuelConsumption() {
		return standardFuelConsumption;
	}
	public void setStandardFuelConsumption(String standardFuelConsumption) {
		this.standardFuelConsumption = standardFuelConsumption;
	}
	public String getOilEngineType() {
		return oilEngineType;
	}
	public void setOilEngineType(String oilEngineType) {
		this.oilEngineType = oilEngineType;
	}
	public String getOilEngineProperty() {
		return oilEngineProperty;
	}
	public void setOilEngineProperty(String oilEngineProperty) {
		this.oilEngineProperty = oilEngineProperty;
	}
	public String getOilEngineStatus() {
		return oilEngineStatus;
	}
	public void setOilEngineStatus(String oilEngineStatus) {
		this.oilEngineStatus = oilEngineStatus;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMaintainCompany() {
		return maintainCompany;
	}
	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}
	public String getFuleType() {
		return fuleType;
	}
	public void setFuleType(String fuleType) {
		this.fuleType = fuleType;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
}
