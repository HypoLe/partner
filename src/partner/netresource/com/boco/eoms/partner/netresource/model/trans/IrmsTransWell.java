package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--人手井
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:07
 */
public class IrmsTransWell extends EomsModel{

	/**主键*/
	private String id;
		
	/**人手井名称，作为业务主键，命名要求唯一。[例]重庆渝中区石桥铺-两路口管道SL-10号井*/
	private String name;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedArea;
		
	/**枚举值：局前、直通、三通、四通等,默认为直通*/
	private String wellMode;
		
	/**枚举值：引上、单井、双井等*/
	private String wellType;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String ownership;
		
	/**枚举值：自用、出租、共享,默认为自用*/
	private String purpose;
		
	/**枚举值：人井、手井*/
	private String wellRealType;
		
	/**经度。例：123.123456*/
	private String longitude;
		
	/**纬度。例：34.123456*/
	private String latitude;
		
	/**使用单位。例：中国移动。*/
	private String useUnit;
		
	/**所有权人名称。例：中国电信。*/
	private String ownerUnit;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedAreaId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getRelatedArea() {
		return this.relatedArea;
	}
	
	public void setRelatedArea(String relatedArea) {
		this.relatedArea = relatedArea;
	}
	public String getWellMode() {
		return this.wellMode;
	}
	
	public void setWellMode(String wellMode) {
		this.wellMode = wellMode;
	}
	public String getWellType() {
		return this.wellType;
	}
	
	public void setWellType(String wellType) {
		this.wellType = wellType;
	}
	public String getOwnership() {
		return this.ownership;
	}
	
	public void setOwnership (String ownership) {
		this.ownership = ownership;
	}
	public String getPurpose() {
		return this.purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getWellRealType() {
		return this.wellRealType;
	}
	
	public void setWellRealType(String wellRealType) {
		this.wellRealType = wellRealType;
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
	public String getUseUnit() {
		return this.useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getOwnerUnit() {
		return this.ownerUnit;
	}
	
	public void setOwnerUnit(String ownerUnit) {
		this.ownerUnit = ownerUnit;
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
	public String getRelatedAreaId() {
		return this.relatedAreaId;
	}
	
	public void setRelatedAreaId(String relatedAreaId) {
		this.relatedAreaId = relatedAreaId;
	}
	
}
