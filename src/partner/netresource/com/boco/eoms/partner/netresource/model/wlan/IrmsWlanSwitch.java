package com.boco.eoms.partner.netresource.model.wlan;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--直放站室分及WLAN--交换机
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:52:01
 */
public class IrmsWlanSwitch extends EomsModel{

	/**主键*/
	private String id;
		
	/**关联属性，关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**根据中国移动数据网命名规范，如，HNZK-WLAN-SW01-MPS3026G（命名见下述解释）*/
	private String labelCn;
		
	/**设备生产厂家。枚举：华三、迈普等*/
	private String relatedVendorCui;
		
	/**设备硬件型号*/
	private String model;
		
	/**在网的软件版本*/
	private String softVersion;
		
	/**登录设备使用的操作维护IP地址。一般指loopback0地址*/
	private String manageIpAddr;
		
	/**关联属性，互连设备可能是数据网、传输网设备，或其他，命名可参考根据中国移动数据网命名规范。参考示例（本端和对端设备端口信息）：[HNZZ-WLAN-SW01-MPS3026G]GE-0/1/1-[HNZZ-MC-CMNET-RT01-HWNE40E]GE-0/1/1。参考示例见右侧插入对象*/
	private String relatedUpOrigPortName;
		
	/**光口、电口等*/
	private String upPortType;
		
	/**交换机的所有端口数量*/
	private String swPortCount;
		
	/**关联属性，关联至【WLAN系统】-【热点】表-【热点名称】*/
	private String relatedHotpotCui;
		
	/**在用户机房，不关联空间资源-机房*/
	private String relatedRoomCuid;
		
	/**工程、现网、空载、退网*/
	private String status;
		
	/**汇聚层、接入层*/
	private String netLevel;
		
	/**若是接入层交换机填写*/
	private String relatedTswDevCu;
		
	/**若是接入层交换机填写*/
	private String relatedTswDevPo;
		
	/**接入层交换机接入的AP数量*/
	private String maxAccessApCoun;
		
	/**三层汇聚交换机可接入的二层交换机数量*/
	private String maxAccessSwCoun;
		
	/**标准格式：yyyy-MM-dd*/
	private String setupTime;
		
	/***/
	private String realAccessApCou;
		
	/***/
	private String realAceessSwCou;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**地市ID*/
	private String relatedDistrictId;
		
	/**交换机所属热点ID*/
	private String relatedHotpotCuiId;
		

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
	public String getLabelCn() {
		return this.labelCn;
	}
	
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}
	public String getRelatedVendorCui() {
		return this.relatedVendorCui;
	}
	
	public void setRelatedVendorCui(String relatedVendorCui) {
		this.relatedVendorCui = relatedVendorCui;
	}
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
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
	public String getRelatedUpOrigPortName() {
		return this.relatedUpOrigPortName;
	}
	
	public void setRelatedUpOrigPortName(String relatedUpOrigPortName) {
		this.relatedUpOrigPortName = relatedUpOrigPortName;
	}
	public String getUpPortType() {
		return this.upPortType;
	}
	
	public void setUpPortType(String upPortType) {
		this.upPortType = upPortType;
	}
	public String getSwPortCount() {
		return this.swPortCount;
	}
	
	public void setSwPortCount(String swPortCount) {
		this.swPortCount = swPortCount;
	}
	public String getRelatedHotpotCui() {
		return this.relatedHotpotCui;
	}
	
	public void setRelatedHotpotCui(String relatedHotpotCui) {
		this.relatedHotpotCui = relatedHotpotCui;
	}
	public String getRelatedRoomCuid() {
		return this.relatedRoomCuid;
	}
	
	public void setRelatedRoomCuid(String relatedRoomCuid) {
		this.relatedRoomCuid = relatedRoomCuid;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNetLevel() {
		return this.netLevel;
	}
	
	public void setNetLevel(String netLevel) {
		this.netLevel = netLevel;
	}
	public String getRelatedTswDevCu() {
		return this.relatedTswDevCu;
	}
	
	public void setRelatedTswDevCu(String relatedTswDevCu) {
		this.relatedTswDevCu = relatedTswDevCu;
	}
	public String getRelatedTswDevPo() {
		return this.relatedTswDevPo;
	}
	
	public void setRelatedTswDevPo(String relatedTswDevPo) {
		this.relatedTswDevPo = relatedTswDevPo;
	}
	public String getMaxAccessApCoun() {
		return this.maxAccessApCoun;
	}
	
	public void setMaxAccessApCoun(String maxAccessApCoun) {
		this.maxAccessApCoun = maxAccessApCoun;
	}
	public String getMaxAccessSwCoun() {
		return this.maxAccessSwCoun;
	}
	
	public void setMaxAccessSwCoun(String maxAccessSwCoun) {
		this.maxAccessSwCoun = maxAccessSwCoun;
	}
	public String getSetupTime() {
		return this.setupTime;
	}
	
	public void setSetupTime(String setupTime) {
		this.setupTime = setupTime;
	}
	public String getRealAccessApCou() {
		return this.realAccessApCou;
	}
	
	public void setRealAccessApCou(String realAccessApCou) {
		this.realAccessApCou = realAccessApCou;
	}
	public String getRealAceessSwCou() {
		return this.realAceessSwCou;
	}
	
	public void setRealAceessSwCou(String realAceessSwCou) {
		this.realAceessSwCou = realAceessSwCou;
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
	public String getRelatedHotpotCuiId() {
		return this.relatedHotpotCuiId;
	}
	
	public void setRelatedHotpotCuiId(String relatedHotpotCuiId) {
		this.relatedHotpotCuiId = relatedHotpotCuiId;
	}
	
}
