package com.boco.activiti.partner.process.po;

/**
 * 交接箱MODEL类
 * 
 * @author Jun
 * 
 */
public class JunctionBoxModel {
	
	// 光交接箱ID
	private String fiberNodeId;

	// 光交接箱名称
	private String fiberNodeNo;

	// 局所ID
	private String bureauId;

	// 局所名称
	private String bureauName;

	// 区域ID
	private String areaId;

	// 区域名称
	private String areaName;
	// 地市ID
	private String cityId;
	// 地市名称
	private String cityName;
	// 标准地址
	private String memo;
	// 模块数量
	private String countNum;

	

	public String getFiberNodeId() {
		return fiberNodeId;
	}

	public void setFiberNodeId(String fiberNodeId) {
		this.fiberNodeId = fiberNodeId;
	}

	public String getFiberNodeNo() {
		return fiberNodeNo;
	}

	public void setFiberNodeNo(String fiberNodeNo) {
		this.fiberNodeNo = fiberNodeNo;
	}

	public String getBureauId() {
		return bureauId;
	}

	public void setBureauId(String bureauId) {
		this.bureauId = bureauId;
	}

	public String getBureauName() {
		return bureauName;
	}

	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCountNum() {
		return countNum;
	}

	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
}
