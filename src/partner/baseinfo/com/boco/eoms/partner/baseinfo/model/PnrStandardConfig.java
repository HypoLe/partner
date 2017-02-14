package com.boco.eoms.partner.baseinfo.model;

/**
 * 类说明：代维资源配置标准模块
 * 创建人： fengguanping
 * 创建时间：2013 01-22 14:13:55
 */
public class PnrStandardConfig {

	/**主键id*/
	private String id;
		
	/**区域id*/
	private String areaId;
		
	/**省份id*/
	private String provinceId;
		
	/**地市id*/
	private String cityId;
		
	/**区县id*/
	private String countryId;
	
	/**区域名称*/
	private String areaName;
		
	/**代维专业id*/
	private String professional;
		
	/**配置类型：枚举值：车辆、人员*/
	private String configType;
		
	/**配置的单位：300/人*/
	private String configDw;
		
	/**配置标准*/
	private int standardConfig;
		
	/**添加资源配置的人*/
	private String addUser;
		
	/**是否删除：0，表示未删除，1表示删除*/
	private String isdeleted;
		
	/**备注*/
	private String remark;
		
	/**保存时间*/
	private String saveTime;
	
	/**资源类型*/
	private String sourceType;
	
	/**资源计量单位*/
	private String sourceDw;
	
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public String getSaveTime() {
		return saveTime;
	}
	public String getSourceDw() {
		return sourceDw;
	}
	public void setSourceDw(String sourceDw) {
		this.sourceDw = sourceDw;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getAreaId() {
		return this.areaId;
	}
	
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getProvinceId() {
		return this.provinceId;
	}
	
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return this.cityId;
	}
	
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountryId() {
		return this.countryId;
	}
	
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public String getProfessional() {
		return this.professional;
	}
	
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getConfigType() {
		return this.configType;
	}
	
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public String getConfigDw() {
		return this.configDw;
	}
	
	public void setConfigDw(String configDw){
		this.configDw = configDw;
	}
	public int getStandardConfig() {
		return this.standardConfig;
	}
	
	public void setStandardConfig(int standardConfig) {
		this.standardConfig = standardConfig;
	}
	public String getAddUser() {
		return this.addUser;
	}
	
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
