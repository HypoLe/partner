package com.boco.eoms.partner.netresource.model.wlan;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--直放站室分及WLAN--直放站
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:52:01
 */
public class IrmsWlanRepeater extends EomsModel{

	/**主键*/
	private String id;
		
	/**参照集团命名规范*/
	private String labelCn;
		
	/**枚举值为现网、工程、空载、退网*/
	private String status;
		
	/**1：光纤直放站，2：无线直放站3：其它*/
	private String nodetype;
		
	/**填写信源小区，关联到【CELL】、【UTRANCELL】表中的网元名称*/
	private String relatedCellCuid;
		
	/**1：室外站;2：室内站;3：室内分布;4：室外分布*/
	private String coverageType;
		
	/**小数点后保留5位*/
	private String longitude;
		
	/**小数点后保留5位*/
	private String latitude;
		
	/**设备提供商名称*/
	private String relatedVendor;
		
	/**1:选频;2:选带；3：宽频*/
	private String signalReceivetype;
		
	/**1:太阳能;2:市电;3:风能*/
	private String powerType;
		
	/**代维公司*/
	private String manageCompany;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属小区ID*/
	private String relatedCellCuidId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelCn() {
		return this.labelCn;
	}
	
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNodetype() {
		return this.nodetype;
	}
	
	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}
	public String getRelatedCellCuid() {
		return this.relatedCellCuid;
	}
	
	public void setRelatedCellCuid(String relatedCellCuid) {
		this.relatedCellCuid = relatedCellCuid;
	}
	public String getCoverageType() {
		return this.coverageType;
	}
	
	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}
	public String getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getSignalReceivetype() {
		return this.signalReceivetype;
	}
	
	public void setSignalReceivetype(String signalReceivetype) {
		this.signalReceivetype = signalReceivetype;
	}
	public String getPowerType() {
		return this.powerType;
	}
	
	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public String getManageCompany() {
		return this.manageCompany;
	}
	
	public void setManageCompany(String manageCompany) {
		this.manageCompany = manageCompany;
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
	public String getRelatedCellCuidId() {
		return this.relatedCellCuidId;
	}
	
	public void setRelatedCellCuidId(String relatedCellCuidId) {
		this.relatedCellCuidId = relatedCellCuidId;
	}
	
}
