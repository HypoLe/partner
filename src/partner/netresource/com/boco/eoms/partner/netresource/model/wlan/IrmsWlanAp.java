package com.boco.eoms.partner.netresource.model.wlan;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--直放站室分及WLAN--AP
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:52:01
 */
public class IrmsWlanAp extends EomsModel{

	/**主键*/
	private String id;
		
	/**建议集团明确“AP名称”的命名规范*/
	private String apName;
		
	/**关联字段，关联至【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**专业内关联属性，关联到【热点】表-【热点名称】*/
	private String relatedHotpoint;
		
	/**华三、思科、大唐等*/
	private String apVendor;
		
	/**胖AP、瘦AP*/
	private String apType;
		
	/**48bit硬件地址*/
	private String apMacAddr;
		
	/**AP管理地址*/
	private String manageApAddr;
		
	/**空口制式*/
	private String stressPattern;
		
	/**AP安装的位置*/
	private String apLocation;
		
	/**AP覆盖的区域*/
	private String apCoverageArea;
		
	/**枚举类型：是、否*/
	private String is2g3gDistributo;
		
	/**关联属性；关联至【WLAN系统】-【交换机】表-【交换机名称】，参考示例：HNZK-WLAN-SW01-MPS3026G*/
	private String relatedHotpingSw;
		
	/**关联属性，关联至【WLAN系统】-【交换机】表。端口管理需要知道AP接入热点交换机的哪个端口
建议参考示例：HNZZ-WLAN-SW01-MPS3026G]FE-0/1/1*/
	private String hotpotSwPort;
		
	/**软件版本号*/
	private String softVersion;
		
	/**专业内关联属性，关联到【AC】表-【AC名称】*/
	private String relatedBackAcCu;
		
	/**工程、现网、空载、退网*/
	private String neWorkingState;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**地市ID*/
	private String relatedDistrictId;
		
	/**所属热点名称ID*/
	private String relatedHotpointId;
		
	/**所属热点交换机名称ID*/
	private String relatedHotpingSwId;
		
	/**所属AC名称ID*/
	private String relatedBackAcCuId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getApName() {
		return this.apName;
	}
	
	public void setApName(String apName) {
		this.apName = apName;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getRelatedHotpoint() {
		return this.relatedHotpoint;
	}
	
	public void setRelatedHotpoint(String relatedHotpoint) {
		this.relatedHotpoint = relatedHotpoint;
	}
	public String getApVendor() {
		return this.apVendor;
	}
	
	public void setApVendor(String apVendor) {
		this.apVendor = apVendor;
	}
	public String getApType() {
		return this.apType;
	}
	
	public void setApType(String apType) {
		this.apType = apType;
	}
	public String getApMacAddr() {
		return this.apMacAddr;
	}
	
	public void setApMacAddr(String apMacAddr) {
		this.apMacAddr = apMacAddr;
	}
	public String getManageApAddr() {
		return this.manageApAddr;
	}
	
	public void setManageApAddr(String manageApAddr) {
		this.manageApAddr = manageApAddr;
	}
	public String getStressPattern() {
		return this.stressPattern;
	}
	
	public void setStressPattern(String stressPattern) {
		this.stressPattern = stressPattern;
	}
	public String getApLocation() {
		return this.apLocation;
	}
	
	public void setApLocation(String apLocation) {
		this.apLocation = apLocation;
	}
	public String getApCoverageArea() {
		return this.apCoverageArea;
	}
	
	public void setApCoverageArea(String apCoverageArea) {
		this.apCoverageArea = apCoverageArea;
	}
	public String getIs2g3gDistributo() {
		return this.is2g3gDistributo;
	}
	
	public void setIs2g3gDistributo(String is2g3gDistributo) {
		this.is2g3gDistributo = is2g3gDistributo;
	}
	public String getRelatedHotpingSw() {
		return this.relatedHotpingSw;
	}
	
	public void setRelatedHotpingSw(String relatedHotpingSw) {
		this.relatedHotpingSw = relatedHotpingSw;
	}
	public String getHotpotSwPort() {
		return this.hotpotSwPort;
	}
	
	public void setHotpotSwPort(String hotpotSwPort) {
		this.hotpotSwPort = hotpotSwPort;
	}
	public String getSoftVersion() {
		return this.softVersion;
	}
	
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getRelatedBackAcCu() {
		return this.relatedBackAcCu;
	}
	
	public void setRelatedBackAcCu(String relatedBackAcCu) {
		this.relatedBackAcCu = relatedBackAcCu;
	}
	public String getNeWorkingState() {
		return this.neWorkingState;
	}
	
	public void setNeWorkingState(String neWorkingState) {
		this.neWorkingState = neWorkingState;
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
	public String getRelatedHotpointId() {
		return this.relatedHotpointId;
	}
	
	public void setRelatedHotpointId(String relatedHotpointId) {
		this.relatedHotpointId = relatedHotpointId;
	}
	public String getRelatedHotpingSwId() {
		return this.relatedHotpingSwId;
	}
	
	public void setRelatedHotpingSwId(String relatedHotpingSwId) {
		this.relatedHotpingSwId = relatedHotpingSwId;
	}
	public String getRelatedBackAcCuId() {
		return this.relatedBackAcCuId;
	}
	
	public void setRelatedBackAcCuId(String relatedBackAcCuId) {
		this.relatedBackAcCuId = relatedBackAcCuId;
	}
	
}
