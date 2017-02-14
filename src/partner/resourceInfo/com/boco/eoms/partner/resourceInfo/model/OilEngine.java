package com.boco.eoms.partner.resourceInfo.model;

public class OilEngine {
	private int id;//主键
	//private String refid;//关联id，用于变更申请时保存源数据，便于在终止申请时数据恢复
	private String oilEngineNumber;//油机编号              
	private String oilEngineModel;//规格型号               
	private String oilEngineFactory;//生产厂家             
	private String powerRating;//额定功率                  
	private String standardFuelConsumption;//标准油耗      
	private String oilEngineType;//油机类型   
	
	private String fuleType;//燃料种类
	private String longitude;//经度
	private String latitude;//纬度
	private String place;//存放地点
	
	private String oilEngineProperty;//产权归属            
	private String oilEngineStatus;//油机状态              
	private String area;//区域                             
	private String maintainCompany;//代维公司    
	private String visible;//是否可见；当发起申请时visible为1；
	private String deleted;//1表示已经删除；
	private String addTime;//添加时间
	private String notes;//备注
	
	private String responseMan; //责任人
    private String telephone;   //联系电话
    private String siteId;      //所属站点
    private String longitudeNow;//当前经度
    private String latitudeNow; //当前纬度
    private String reportTime;  //位置上报时间
    private String specialty;   //维护专业
    private String changeType;  //变更方式 0为通过页面变更申请，1为通过GIS地图变更。
    private String changeMainId;//关联变更表
    private String changeTime;  //变更时间
    private String changeMan;   //变更提出人
    
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
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getResponseMan() {
		return responseMan;
	}
	public void setResponseMan(String responseMan) {
		this.responseMan = responseMan;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getLongitudeNow() {
		return longitudeNow;
	}
	public void setLongitudeNow(String longitudeNow) {
		this.longitudeNow = longitudeNow;
	}
	public String getLatitudeNow() {
		return latitudeNow;
	}
	public void setLatitudeNow(String latitudeNow) {
		this.latitudeNow = latitudeNow;
	}
	
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getChangeMainId() {
		return changeMainId;
	}
	public void setChangeMainId(String changeMainId) {
		this.changeMainId = changeMainId;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public String getChangeMan() {
		return changeMan;
	}
	public void setChangeMan(String changeMan) {
		this.changeMan = changeMan;
	}
	
	
}
