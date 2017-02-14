package com.boco.eoms.partner.baseinfo.model;

/**
 * 类说明：代维资源配置模块
 * 创建人： fengguanping
 * 创建时间：2012-12-27 16:18:55
 */
public class PnrSourceStandardConfig {

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
	
	/**代维公司*/
	private String companyName;
		
	/**代维公司主键id*/
	private String companyId;
		
	/**代维公司magid*/
	private String companyMagId;
		
	/**代维专业id*/
	private String professional;
		
	/**配置类型：枚举值：车辆、人员、油机*/
	private String configType;
		
	/**配置的单位：300/人*/
	private String configDw;
		
	/**要求配置数量*/
	private int standardConfig;
	
	/**配置标准*/
	private int standardAmout;
		
	/**实际配置数量*/
	private int actualConfig;
	
	/**到位率*/
	private double configRate;
		
	/**资源配置到位的时间*/
	private String configTime;
		
	/**资源的添加时间年*/
	private int addTimeY;
		
	/**资源的添加时间月*/
	private int addTimeM;
		
	/**资源的添加时间日*/
	private int addTimeD;
		
	/**添加资源配置的人*/
	private String addUser;
		
	/**是否删除：0，表示未删除，1表示删除*/
	private String isdeleted;
		
	/**备注*/
	private String remark;
		
	/**资源是否已经通过了移动管理人员的审核*/
	private String ischecked;
		
	/**保存时间*/
	private String saveTime;
	/**资源类型*/
	private String sourceType;
	/**资源计量单位*/
	private String sourceDw;
	/**维护资源数量*/
	private double sourceAmount;
	
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceDw() {
		return sourceDw;
	}
	public void setSourceDw(String sourceDw) {
		this.sourceDw = sourceDw;
	}
	public double getSourceAmount() {
		return sourceAmount;
	}
	public void setSourceAmount(double sourceAmount) {
		this.sourceAmount = sourceAmount;
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
	public String getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyId() {
		return this.companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyMagId() {
		return this.companyMagId;
	}
	
	public void setCompanyMagId(String companyMagId) {
		this.companyMagId = companyMagId;
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
	
	public void setConfigDw(String configDw) {
		this.configDw = configDw;
	}
	public int getStandardConfig() {
		return this.standardConfig;
	}
	
	public void setStandardConfig(int standardConfig) {
		this.standardConfig = standardConfig;
	}
	public int getActualConfig() {
		return this.actualConfig;
	}
	
	public void setActualConfig(int actualConfig) {
		this.actualConfig = actualConfig;
	}
	public String getConfigTime() {
		return this.configTime;
	}
	
	public void setConfigTime(String configTime) {
		this.configTime = configTime;
	}
	public int getAddTimeY() {
		return this.addTimeY;
	}
	
	public void setAddTimeY(int addTimeY) {
		this.addTimeY = addTimeY;
	}
	public int getAddTimeM() {
		return this.addTimeM;
	}
	
	public void setAddTimeM(int addTimeM) {
		this.addTimeM = addTimeM;
	}
	public int getAddTimeD() {
		return this.addTimeD;
	}
	
	public void setAddTimeD(int addTimeD) {
		this.addTimeD = addTimeD;
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
	public String getIschecked() {
		return this.ischecked;
	}
	
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	public String getSaveTime() {
		return this.saveTime;
	}
	public double getConfigRate() {
		return configRate;
	}
	public void setConfigRate(double configRate) {
		this.configRate = configRate;
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
	public int getStandardAmout() {
		return standardAmout;
	}
	public void setStandardAmout(int standardAmout) {
		this.standardAmout = standardAmout;
	}
	
}
