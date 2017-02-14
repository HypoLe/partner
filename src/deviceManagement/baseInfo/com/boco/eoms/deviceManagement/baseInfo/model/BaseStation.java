package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;

public class BaseStation extends BaseObject {
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
	private String addman;
	private String addtime;

	public String getAddman() {
		return addman;
	}

	public void setAddman(String addman) {
		this.addman = addman;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getBaseStationName() {
		return baseStationName;
	}

	public void setBaseStationName(String baseStationName) {
		this.baseStationName = baseStationName;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof BaseStation) {
			BaseStation baseStation = (BaseStation) o;
			if (this.id != null || this.id.equals(baseStation.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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
