package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;

public class CPBaseStation extends BaseObject {
	private static final long serialVersionUID = 1L;

	
	private String id;
	private String checkPointId;
	private String baseStationLevel;//等级
	private String baseStationName;//名称
	private String baseStationId;
	
	public String getBaseStationId() {
		return baseStationId;
	}

	public void setBaseStationId(String baseStationId) {
		this.baseStationId = baseStationId;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CPBaseStation) {
			CPBaseStation baseStation = (CPBaseStation) o;
			if (this.id != null || this.id.equals(baseStation.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getBaseStationLevel() {
		return baseStationLevel;
	}

	public void setBaseStationLevel(String baseStationLevel) {
		this.baseStationLevel = baseStationLevel;
	}

	public String getBaseStationName() {
		return baseStationName;
	}

	public void setBaseStationName(String baseStationName) {
		this.baseStationName = baseStationName;
	}

	public String getCheckPointId() {
		return checkPointId;
	}

	public void setCheckPointId(String checkPointId) {
		this.checkPointId = checkPointId;
	}
	
	
}
