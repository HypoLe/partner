package com.boco.eoms.deviceManagement.baseInfo.model;

import com.boco.eoms.base.model.BaseObject;

public class HandWell extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String id;
	private String checkPointId;
	private String checkPointUser;//巡检员
	private String loadSysLevel;//承载系统级别
	private String loadCableSegmentName;//承载光缆中继段名称
	private String handleWellNum;//人手井编号
	private String pipelineName;//所属管道名
	private String isJointWell;//是否接头井
	private String isCableObligate;//是否有光缆预留
	private String isImportantFocus;//是否重要关注点 
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof HandWell) {
			HandWell handWell = (HandWell) o;
			if (this.id != null || this.id.equals(handWell.getId())) {
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

	public String getCheckPointUser() {
		return checkPointUser;
	}

	public void setCheckPointUser(String checkPointUser) {
		this.checkPointUser = checkPointUser;
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

	public String getHandleWellNum() {
		return handleWellNum;
	}

	public void setHandleWellNum(String handleWellNum) {
		this.handleWellNum = handleWellNum;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getIsJointWell() {
		return isJointWell;
	}

	public void setIsJointWell(String isJointWell) {
		this.isJointWell = isJointWell;
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
