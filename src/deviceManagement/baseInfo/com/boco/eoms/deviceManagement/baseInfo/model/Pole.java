package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;

public class Pole extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String id;
	private String checkPointId;
	private String poletUser;//巡检员
	private String loadSysLevel;//承载系统级别
	private String loadCableSegmentName;//承载光缆中继段名称
	private String poleNum;//电杆编号
	private String poleName;//所属杆路名
	private String isJointPole;//是否接头杆
	private String isCableObligate;//是否有光缆预留
	private String isImportantFocus;//是否重要关注点 
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pole) {
			Pole pole = (Pole) o;
			if (this.id != null || this.id.equals(pole.getId())) {
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

	public String getPoletUser() {
		return poletUser;
	}

	public void setPoletUser(String poletUser) {
		this.poletUser = poletUser;
	}

	public String getLoadSysLevel() {
		return loadSysLevel;
	}

	public void setLoadSysLevel(String loadSysLevel) {
		this.loadSysLevel = loadSysLevel;
	}

	public String getLoadCableSegmentName() {
		return loadCableSegmentName;
	}

	public void setLoadCableSegmentName(String loadCableSegmentName) {
		this.loadCableSegmentName = loadCableSegmentName;
	}

	public String getPoleNum() {
		return poleNum;
	}

	public void setPoleNum(String poleNum) {
		this.poleNum = poleNum;
	}

	public String getPoleName() {
		return poleName;
	}

	public void setPoleName(String poleName) {
		this.poleName = poleName;
	}

	public String getIsJointPole() {
		return isJointPole;
	}

	public void setIsJointPole(String isJointPole) {
		this.isJointPole = isJointPole;
	}

	public String getIsCableObligate() {
		return isCableObligate;
	}

	public void setIsCableObligate(String isCableObligate) {
		this.isCableObligate = isCableObligate;
	}

	public String getIsImportantFocus() {
		return isImportantFocus;
	}

	public void setIsImportantFocus(String isImportantFocus) {
		this.isImportantFocus = isImportantFocus;
	}

	public String getCheckPointId() {
		return checkPointId;
	}

	public void setCheckPointId(String checkPointId) {
		this.checkPointId = checkPointId;
	}
	
	
}
