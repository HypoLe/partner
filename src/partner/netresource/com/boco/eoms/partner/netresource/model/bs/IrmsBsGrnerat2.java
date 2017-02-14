package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--开关电源
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsGrnerat2 extends EomsModel{

	/**主键*/
	private String id;
		
	/**关联属性，关联到【空间资源】-【机房】表-【机房名称】*/
	private String relatedRoom;
		
	/**枚举值：高压配电、低压配电、发电机组、开关电源系统、UPS系统 、列头柜、专用空调、中央空调、普通空调、节能设备、动环监控采集、图像系统、LSC系统、CSC系统、交流配电系统、开关电源系统、变换设备、UPS系统、普通空调、节能设备、新能源设备、极早期烟感、动环监控采集、防雷接地系统、车载油机、小油机、车载电池、其他*/
	private String deviceType;
		
	/**枚举值：整流器柜、其他*/
	private String deviceSubclass;
		
	/**符合设备命名规范*/
	private String labelDev;
		
	/**符合设备编码规范*/
	private String deviceSequence;
		
	/**开关电源的系统编号*/
	private String sysNo;
		
	/**设备的资产编号，可选择填写*/
	private String equipmentcode;
		
	/**不带品牌说明的设备产品型号规格*/
	private String model;
		
	/**设备的生产厂商*/
	private String relatedVendor;
		
	/**品牌的简称.如：许继、ABB*/
	private String trademark;
		
	/**设备供货商*/
	private String supplier;
		
	/**设备代维公司*/
	private String manageCompany;
		
	/**格式为：YYYY-MM-DD*/
	private String startTime;
		
	/**格式为：YYYY-MM-DD*/
	private String endTime;
		
	/**枚举值：工程，现网，空载，退网*/
	private String status;
		
	/**枚举值：-48,-24*/
	private String expRatingVoltage;
		
	/**枚举值：分列式，组合式*/
	private String smpsType;
		
	/**填写监控模块型号*/
	private String monitorModel;
		
	/**单位：块*/
	private String commModelNum;
		
	/**填写整流模块型号*/
	private String commModel;
		
	/**枚举值：380,220*/
	private String commModelVoltage;
		
	/**单个整流模块额定输出容量，单位：A*/
	private String commModelExpCap;
		
	/**单组电池组单个熔丝的容量及并联个数*/
	private String batteryCapability;
		
	/**用于连接电池组的熔丝组数*/
	private String batteryEleccapGr;
		
	/**开关电源系统是否具有二次下电功能*/
	private String isUnderSecond;
		
	/**移动公司该设备的负责人*/
	private String preserver;
		
	/**枚举值：【是】或者【否】*/
	private String isConstructShare;
		
	/**如共建，逐一列出共建单位，用“，”分割。*/
	private String constructCompany;
		
	/**枚举值：【是】或者【否】*/
	private String isShared;
		
	/**如共建，逐一列出共享单位，用“，”分割。*/
	private String sharedCompany;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**机房（基站）名称ID*/
	private String relatedRoomId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getRelatedRoom() {
		return this.relatedRoom;
	}
	
	public void setRelatedRoom(String relatedRoom) {
		this.relatedRoom = relatedRoom;
	}
	public String getDeviceType() {
		return this.deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceSubclass() {
		return this.deviceSubclass;
	}
	
	public void setDeviceSubclass(String deviceSubclass) {
		this.deviceSubclass = deviceSubclass;
	}
	public String getLabelDev() {
		return this.labelDev;
	}
	
	public void setLabelDev(String labelDev) {
		this.labelDev = labelDev;
	}
	public String getDeviceSequence() {
		return this.deviceSequence;
	}
	
	public void setDeviceSequence(String deviceSequence) {
		this.deviceSequence = deviceSequence;
	}
	public String getSysNo() {
		return this.sysNo;
	}
	
	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}
	public String getEquipmentcode() {
		return this.equipmentcode;
	}
	
	public void setEquipmentcode(String equipmentcode) {
		this.equipmentcode = equipmentcode;
	}
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getTrademark() {
		return this.trademark;
	}
	
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getSupplier() {
		return this.supplier;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getManageCompany() {
		return this.manageCompany;
	}
	
	public void setManageCompany(String manageCompany) {
		this.manageCompany = manageCompany;
	}
	public String getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpRatingVoltage() {
		return this.expRatingVoltage;
	}
	
	public void setExpRatingVoltage(String expRatingVoltage) {
		this.expRatingVoltage = expRatingVoltage;
	}
	public String getSmpsType() {
		return this.smpsType;
	}
	
	public void setSmpsType(String smpsType) {
		this.smpsType = smpsType;
	}
	public String getMonitorModel() {
		return this.monitorModel;
	}
	
	public void setMonitorModel(String monitorModel) {
		this.monitorModel = monitorModel;
	}
	public String getCommModelNum() {
		return this.commModelNum;
	}
	
	public void setCommModelNum(String commModelNum) {
		this.commModelNum = commModelNum;
	}
	public String getCommModel() {
		return this.commModel;
	}
	
	public void setCommModel(String commModel) {
		this.commModel = commModel;
	}
	public String getCommModelVoltage() {
		return this.commModelVoltage;
	}
	
	public void setCommModelVoltage(String commModelVoltage) {
		this.commModelVoltage = commModelVoltage;
	}
	public String getCommModelExpCap() {
		return this.commModelExpCap;
	}
	
	public void setCommModelExpCap(String commModelExpCap) {
		this.commModelExpCap = commModelExpCap;
	}
	public String getBatteryCapability() {
		return this.batteryCapability;
	}
	
	public void setBatteryCapability(String batteryCapability) {
		this.batteryCapability = batteryCapability;
	}
	public String getBatteryEleccapGr() {
		return this.batteryEleccapGr;
	}
	
	public void setBatteryEleccapGr(String batteryEleccapGr) {
		this.batteryEleccapGr = batteryEleccapGr;
	}
	public String getIsUnderSecond() {
		return this.isUnderSecond;
	}
	
	public void setIsUnderSecond(String isUnderSecond) {
		this.isUnderSecond = isUnderSecond;
	}
	public String getPreserver() {
		return this.preserver;
	}
	
	public void setPreserver(String preserver) {
		this.preserver = preserver;
	}
	public String getIsConstructShare() {
		return this.isConstructShare;
	}
	
	public void setIsConstructShare(String isConstructShare) {
		this.isConstructShare = isConstructShare;
	}
	public String getConstructCompany() {
		return this.constructCompany;
	}
	
	public void setConstructCompany(String constructCompany) {
		this.constructCompany = constructCompany;
	}
	public String getIsShared() {
		return this.isShared;
	}
	
	public void setIsShared(String isShared) {
		this.isShared = isShared;
	}
	public String getSharedCompany() {
		return this.sharedCompany;
	}
	
	public void setSharedCompany(String sharedCompany) {
		this.sharedCompany = sharedCompany;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRelatedRoomId() {
		return this.relatedRoomId;
	}
	
	public void setRelatedRoomId(String relatedRoomId) {
		this.relatedRoomId = relatedRoomId;
	}
	
}
