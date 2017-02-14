package com.boco.eoms.deviceManagement.baseInfo.model;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

public class BaseStationForm extends BaseForm implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String baseStationName;
	private String stationForm;
	private String maintenanceLevel;
	private String stationHouseType;
	private String manufacturer;
	private String carrierFacility;
	private String carrierNum;
	private String longitude;
	private String latitude;
	private String remark;
	private FormFile accessoryName; // 批量录入文件

	public FormFile getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBaseStationName() {
		return baseStationName;
	}
	public void setBaseStationName(String baseStationName) {
		this.baseStationName = baseStationName;
	}

	public String getStationForm() {
		return stationForm;
	}

	public void setStationForm(String stationForm) {
		this.stationForm = stationForm;
	}

	public String getMaintenanceLevel() {
		return maintenanceLevel;
	}

	public void setMaintenanceLevel(String maintenanceLevel) {
		this.maintenanceLevel = maintenanceLevel;
	}


	public String getStationHouseType() {
		return stationHouseType;
	}
	public void setStationHouseType(String stationHouseType) {
		this.stationHouseType = stationHouseType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCarrierFacility() {
		return carrierFacility;
	}

	public void setCarrierFacility(String carrierFacility) {
		this.carrierFacility = carrierFacility;
	}

	public String getCarrierNum() {
		return carrierNum;
	}

	public void setCarrierNum(String carrierNum) {
		this.carrierNum = carrierNum;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BaseStationForm) {
			BaseStationForm baseStationFaultRecordForm = (BaseStationForm) o;
			if (this.id != null || this.id.equals(baseStationFaultRecordForm.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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



}
