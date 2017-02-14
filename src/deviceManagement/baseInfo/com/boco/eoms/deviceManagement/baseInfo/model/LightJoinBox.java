package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;

public class LightJoinBox extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String id;
	private String checkPointId;
	private String lightJoinBoxName;//名称
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof LightJoinBox) {
			LightJoinBox lightJoinBox = (LightJoinBox) o;
			if (this.id != null || this.id.equals(lightJoinBox.getId())) {
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

	public String getLightJoinBoxName() {
		return lightJoinBoxName;
	}

	public void setLightJoinBoxName(String lightJoinBoxName) {
		this.lightJoinBoxName = lightJoinBoxName;
	}

	public String getCheckPointId() {
		return checkPointId;
	}

	public void setCheckPointId(String checkPointId) {
		this.checkPointId = checkPointId;
	}
}
