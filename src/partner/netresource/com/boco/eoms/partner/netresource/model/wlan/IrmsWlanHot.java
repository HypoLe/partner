package com.boco.eoms.partner.netresource.model.wlan;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--直放站室分及WLAN--热点
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:52:01
 */
public class IrmsWlanHot extends EomsModel{

	/**主键*/
	private String id;
		
	/**手工填写，供AP填写时关联所用*/
	private String labelCn;
		
	/**关联属性，关联至【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**根据中国移动WLAN规范，热点与NAS-ID有明确对应关系，所以此字段不能去掉。若个别省公司一个AC一个NAS-ID，可以填写AC的NAS-ID。*/
	private String nasId;
		
	/**集团客户、高等院校等*/
	private String hotpointType;
		
	/**热点地理位置经度*/
	private String longitude;
		
	/**热点地理位置纬度*/
	private String latitude;
		
	/**MSTP、SDH、PON、裸纤等*/
	private String transType;
		
	/**覆盖该热点AP厂家*/
	private String relatedVendor;
		
	/**AP数量*/
	private String apCount;
		
	/**是集团WLAN月度报表明确要求的内容*/
	private String address;
		
	/**是集团WLAN月度报表明确要求的内容*/
	private String wlanCoverageArea;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**地市ID*/
	private String relatedDistrictId;
		

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
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getNasId() {
		return this.nasId;
	}
	
	public void setNasId(String nasId) {
		this.nasId = nasId;
	}
	public String getHotpointType() {
		return this.hotpointType;
	}
	
	public void setHotpointType(String hotpointType) {
		this.hotpointType = hotpointType;
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
	public String getTransType() {
		return this.transType;
	}
	
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getApCount() {
		return this.apCount;
	}
	
	public void setApCount(String apCount) {
		this.apCount = apCount;
	}
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWlanCoverageArea() {
		return this.wlanCoverageArea;
	}
	
	public void setWlanCoverageArea(String wlanCoverageArea) {
		this.wlanCoverageArea = wlanCoverageArea;
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
	
}
