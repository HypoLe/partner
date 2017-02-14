package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;

public class CheckPoint extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String id;
	private String resourceCode;//资源点编码
	private String resourceName;//资源点名称
	private String address;//地址
	private String type;//类型
	private double longitude;//经度
	private double latitude;//纬度
	private String cableSegment;//所属光缆段
	private String cableSystem;//所属光缆系统
	private String checkPointSegmentId;//所属巡检段
	private String importantFocus;//重要关注点
	private String isCheckPoint;//是否为检查点
	private String addTime;
	private String agreementNO;
	public String getAgreementNO() {
		return agreementNO;
	}

	public void setAgreementNO(String agreementNO) {
		this.agreementNO = agreementNO;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CheckPoint) {
			CheckPoint checkPoint = (CheckPoint) o;
			if (this.id != null || this.id.equals(checkPoint.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getResourceCode() {
		return resourceCode;
	}


	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}



	public String getCableSegment() {
		return cableSegment;
	}

	public void setCableSegment(String cableSegment) {
		this.cableSegment = cableSegment;
	}

	public String getCableSystem() {
		return cableSystem;
	}

	public void setCableSystem(String cableSystem) {
		this.cableSystem = cableSystem;
	}

	public String getResourceName() {
		return resourceName;
	}





	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}








	public String getCheckPointSegmentId() {
		return checkPointSegmentId;
	}





	public void setCheckPointSegmentId(String checkPointSegmentId) {
		this.checkPointSegmentId = checkPointSegmentId;
	}





	public String getImportantFocus() {
		return importantFocus;
	}





	public void setImportantFocus(String importantFocus) {
		this.importantFocus = importantFocus;
	}





	public String getIsCheckPoint() {
		return isCheckPoint;
	}





	public void setIsCheckPoint(String isCheckPoint) {
		this.isCheckPoint = isCheckPoint;
	}





	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
}
