package com.boco.eoms.partner.shortperiod.po;

public class TowerQueryConditionModel {
	
	//查询标识
	private String flag;
	
	//地市
	private String region;
	
	//区县
	private String country;
	
	//站点名称
	private String resName;
	
	//产品业务确认编号
	private String confirmNum;
	
	//是否修改过
	private String isModify;
	
	//数据筛选条件
	private String dataFilter;
	
	//产品类型（新）
	private String newProductType;
	
	///机房类型（新）
	private String newRoomType;
	
	//天线挂高（新）
	private String newAntennaHeight;
	
	//产品类型（旧） 枚举值
	private String oldProductType;
	
	//机房类型（旧）枚举值
	private String oldRoomType;
	
	//天线挂高（旧）枚举值
	private String oldAntennaHeight;
	
	
	//产品类型（旧）中文值
	private String oldProductTypeName;
	
	//机房类型（旧）中文值
	private String oldRoomTypeName;
	
	//天线挂高（旧）中文值
	private String oldAntennaHeightName;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(String confirmNum) {
		this.confirmNum = confirmNum;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(String dataFilter) {
		this.dataFilter = dataFilter;
	}

	public String getNewProductType() {
		return newProductType;
	}

	public void setNewProductType(String newProductType) {
		this.newProductType = newProductType;
	}

	public String getNewRoomType() {
		return newRoomType;
	}

	public void setNewRoomType(String newRoomType) {
		this.newRoomType = newRoomType;
	}

	public String getNewAntennaHeight() {
		return newAntennaHeight;
	}

	public void setNewAntennaHeight(String newAntennaHeight) {
		this.newAntennaHeight = newAntennaHeight;
	}

	public String getOldProductType() {
		return oldProductType;
	}

	public void setOldProductType(String oldProductType) {
		this.oldProductType = oldProductType;
	}

	public String getOldRoomType() {
		return oldRoomType;
	}

	public void setOldRoomType(String oldRoomType) {
		this.oldRoomType = oldRoomType;
	}

	public String getOldAntennaHeight() {
		return oldAntennaHeight;
	}

	public void setOldAntennaHeight(String oldAntennaHeight) {
		this.oldAntennaHeight = oldAntennaHeight;
	}

	public String getOldProductTypeName() {
		return oldProductTypeName;
	}

	public void setOldProductTypeName(String oldProductTypeName) {
		this.oldProductTypeName = oldProductTypeName;
	}

	public String getOldRoomTypeName() {
		return oldRoomTypeName;
	}

	public void setOldRoomTypeName(String oldRoomTypeName) {
		this.oldRoomTypeName = oldRoomTypeName;
	}

	public String getOldAntennaHeightName() {
		return oldAntennaHeightName;
	}

	public void setOldAntennaHeightName(String oldAntennaHeightName) {
		this.oldAntennaHeightName = oldAntennaHeightName;
	}
}
