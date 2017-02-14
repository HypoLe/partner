package com.boco.eoms.partner.netresource.model.wlan;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--直放站室分及WLAN--AC
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:52:01
 */
public class IrmsWlanAc extends EomsModel{

	/**主键*/
	private String id;
		
	/**地市*/
	private String relatedDistrict;
		
	/**AC名称*/
	private String acName;
		
	/**设备厂家*/
	private String relatedVendor;
		
	/**硬件型号*/
	private String hardMode;
		
	/**AC软件版本*/
	private String softVersion;
		
	/**管理IP地址*/
	private String manageIpAddr;
		
	/**上行互联设备端口*/
	private String upOrigDevName;
		
	/**上行数据接口线速转发速率总和*/
	private String upDateinterfaceS;
		
	/**机柜编号*/
	private String labelNo;
		
	/**接入电源柜位置*/
	private String insertPowerLocat;
		
	/**工程状态*/
	private String neWorkingState;
		
	/**设备最大管理AP数量*/
	private String maxManageApCoun;
		
	/**AC_NAME*/
	private String acName2;
		
	/**AC NAS_IP*/
	private String nasIp;
		
	/**入网时间*/
	private String setupTime;
		
	/**实际管理AP数量*/
	private String apCount;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**地市ID*/
	private String relatedDistrictId;
		
	/**机柜编号ID*/
	private String labelNoId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getAcName() {
		return this.acName;
	}
	
	public void setAcName(String acName) {
		this.acName = acName;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getHardMode() {
		return this.hardMode;
	}
	
	public void setHardMode(String hardMode) {
		this.hardMode = hardMode;
	}
	public String getSoftVersion() {
		return this.softVersion;
	}
	
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getManageIpAddr() {
		return this.manageIpAddr;
	}
	
	public void setManageIpAddr(String manageIpAddr) {
		this.manageIpAddr = manageIpAddr;
	}
	public String getUpOrigDevName() {
		return this.upOrigDevName;
	}
	
	public void setUpOrigDevName(String upOrigDevName) {
		this.upOrigDevName = upOrigDevName;
	}
	public String getUpDateinterfaceS() {
		return this.upDateinterfaceS;
	}
	
	public void setUpDateinterfaceS(String upDateinterfaceS) {
		this.upDateinterfaceS = upDateinterfaceS;
	}
	public String getLabelNo() {
		return this.labelNo;
	}
	
	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}
	public String getInsertPowerLocat() {
		return this.insertPowerLocat;
	}
	
	public void setInsertPowerLocat(String insertPowerLocat) {
		this.insertPowerLocat = insertPowerLocat;
	}
	public String getNeWorkingState() {
		return this.neWorkingState;
	}
	
	public void setNeWorkingState(String neWorkingState) {
		this.neWorkingState = neWorkingState;
	}
	public String getMaxManageApCoun() {
		return this.maxManageApCoun;
	}
	
	public void setMaxManageApCoun(String maxManageApCoun) {
		this.maxManageApCoun = maxManageApCoun;
	}
	public String getAcName2() {
		return this.acName2;
	}
	
	public void setAcName2(String acName2) {
		this.acName2 = acName2;
	}
	public String getNasIp() {
		return this.nasIp;
	}
	
	public void setNasIp(String nasIp) {
		this.nasIp = nasIp;
	}
	public String getSetupTime() {
		return this.setupTime;
	}
	
	public void setSetupTime(String setupTime) {
		this.setupTime = setupTime;
	}
	public String getApCount() {
		return this.apCount;
	}
	
	public void setApCount(String apCount) {
		this.apCount = apCount;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRelatedDistrictId() {
		return this.relatedDistrictId;
	}
	
	public void setRelatedDistrictId(String relatedDistrictId) {
		this.relatedDistrictId = relatedDistrictId;
	}
	public String getLabelNoId() {
		return this.labelNoId;
	}
	
	public void setLabelNoId(String labelNoId) {
		this.labelNoId = labelNoId;
	}
	
}
