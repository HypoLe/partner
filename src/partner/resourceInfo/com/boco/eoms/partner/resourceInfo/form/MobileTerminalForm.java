package com.boco.eoms.partner.resourceInfo.form;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class MobileTerminalForm extends BaseForm {
	private int id;//主键
	private String mobileTerminalNumber;//设备编号
	private String maintainCompany;//代维公司
	private String maintainTeam;//维护组
	private String mobileTerminalType;//移动终端类型
	private String mobileTerminalFactory;//生产厂家
	private String mobileTerminalSerilNumber;//机身序列号
	private String simCardType;//SIM类型
	private String simCardNumber;//SIM卡号
	private String identificationCode;//设备识别码
	private String area;//所属区域
	private String notes;
	private String addTime;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
	public String getMobileTerminalNumber() {
		return mobileTerminalNumber;
	}
	public void setMobileTerminalNumber(String mobileTerminalNumber) {
		this.mobileTerminalNumber = mobileTerminalNumber;
	}
	public String getMaintainCompany() {
		return maintainCompany;
	}
	public void setMaintainCompany(String maintainCompany) {
		this.maintainCompany = maintainCompany;
	}
	public String getMaintainTeam() {
		return maintainTeam;
	}
	public void setMaintainTeam(String maintainTeam) {
		this.maintainTeam = maintainTeam;
	}
	public String getMobileTerminalType() {
		return mobileTerminalType;
	}
	public void setMobileTerminalType(String mobileTerminalType) {
		this.mobileTerminalType = mobileTerminalType;
	}
	public String getMobileTerminalFactory() {
		return mobileTerminalFactory;
	}
	public void setMobileTerminalFactory(String mobileTerminalFactory) {
		this.mobileTerminalFactory = mobileTerminalFactory;
	}
	public String getMobileTerminalSerilNumber() {
		return mobileTerminalSerilNumber;
	}
	public void setMobileTerminalSerilNumber(String mobileTerminalSerilNumber) {
		this.mobileTerminalSerilNumber = mobileTerminalSerilNumber;
	}
	public String getSimCardType() {
		return simCardType;
	}
	public void setSimCardType(String simCardType) {
		this.simCardType = simCardType;
	}
	public String getSimCardNumber() {
		return simCardNumber;
	}
	public void setSimCardNumber(String simCardNumber) {
		this.simCardNumber = simCardNumber;
	}
	public String getIdentificationCode() {
		return identificationCode;
	}
	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	private FormFile importFile;

	public FormFile getImportFile() {
		return importFile;
	}
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}

}
