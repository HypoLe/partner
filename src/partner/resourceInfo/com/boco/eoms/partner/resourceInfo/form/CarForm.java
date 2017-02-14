package com.boco.eoms.partner.resourceInfo.form;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class CarForm extends BaseForm {
	private int id;//主键
	private String area;//区域
	private String carNumber;           //车牌号      
	private String carType;             //车型        
	private String carGPSNumber;        //车载GPS编号 
	private String carGPSFactory;       //车载GPS厂家 
	private String carGPSSimCardNumber; //车载GPS的SIM
	private String driver;              //负责人（驾驶员）
	private String driverContact;       //联系方式（驾驶员联系方式）
	private String carProperty;         //产权属性    
	private String maintainCompany;     //代维公司    
	private String carStatus;           //车辆状态   
	private String notes;
	private String addTime;
	private Date locateTime;		   //定位时间;
	private String applyId;				//申请单id;
	private String dispatchStatus;		//车辆调度状态,'0'为空闲，'1'为已预订，'2'为使用中

	public Date getLocateTime() {
		return locateTime;
	}
	public void setLocateTime(Date locateTime) {
		this.locateTime = locateTime;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getDispatchStatus() {
		return dispatchStatus;
	}
	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarGPSNumber() {
		return carGPSNumber;
	}
	public void setCarGPSNumber(String carGPSNumber) {
		this.carGPSNumber = carGPSNumber;
	}
	public String getCarGPSFactory() {
		return carGPSFactory;
	}
	public void setCarGPSFactory(String carGPSFactory) {
		this.carGPSFactory = carGPSFactory;
	}
	public String getCarGPSSimCardNumber() {
		return carGPSSimCardNumber;
	}
	public void setCarGPSSimCardNumber(String carGPSSimCardNumber) {
		this.carGPSSimCardNumber = carGPSSimCardNumber;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getDriverContact() {
		return driverContact;
	}
	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}
	public String getCarProperty() {
		return carProperty;
	}
	public void setCarProperty(String carProperty) {
		this.carProperty = carProperty;
	}
	public String getMaintainCompany() {
		return maintainCompany;
	}
	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}
	public String getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
