package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--标石
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransMarkstone extends EomsModel{

	/**主键*/
	private String id;
		
	/**标石名称，作为业务主键，命名要求唯一。在直埋光缆和电缆中，每隔一定的距离，使用某种标签来标识此直埋光缆和电缆。[例]重庆彭水-黔江PQ0168号标石*/
	private String markStoneName;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**经度。例：123.123456*/
	private String longitude;
		
	/**纬度。例：123.123456*/
	private String latitude;
		
	/**埋深，单位：米。例：2.3*/
	private String buryDeep;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**使用单位。例：中国移动。*/
	private String useUnit;
		
	/**所有权人。例：中国电信*/
	private String owner;
		
	/**枚举值：普通标石、接头坑。默认为普通标石*/
	private String type;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedDistirctId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getMarkStoneName() {
		return this.markStoneName;
	}
	
	public void setMarkStoneName(String markStoneName) {
		this.markStoneName = markStoneName;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
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
	public String getBuryDeep() {
		return this.buryDeep;
	}
	
	public void setBuryDeep(String buryDeep) {
		this.buryDeep = buryDeep;
	}
	public String getProperty() {
		return this.property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	public String getUseUnit() {
		return this.useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	public String getRelatedDistirctId() {
		return this.relatedDistirctId;
	}
	
	public void setRelatedDistirctId(String relatedDistirctId) {
		this.relatedDistirctId = relatedDistirctId;
	}
	
}
