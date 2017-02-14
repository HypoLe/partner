package com.boco.eoms.partner.netresource.model.space;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--空间资源--站点
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:44:33
 */
public class IrmsSpaceSite extends EomsModel{

	/**主键*/
	private String id;
		
	/**站点名称*/
	private String siteName;
		
	/**站点名称拼音缩写*/
	private String spellabbreviation;
		
	/**站点名称别名*/
	private String siteNameAlias;
		
	/**站点编号*/
	private String sitecoding;
		
	/**站点类型  枚举值：核心生产楼、汇聚站点、接入站点、用户站点、其他*/
	private String siteType;
		
	/**站点地址*/
	private String location;
		
	/**所属区域*/
	private String relatedSpace;
		
	/**经度*/
	private String longitude;
		
	/**纬度*/
	private String latitude;
		
	/**传输等级*/
	private String serviceLevel;
		
	/**楼层数*/
	private String floorMaxnum;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedSpaceId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getSiteName() {
		return this.siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSpellabbreviation() {
		return this.spellabbreviation;
	}
	
	public void setSpellabbreviation(String spellabbreviation) {
		this.spellabbreviation = spellabbreviation;
	}
	public String getSiteNameAlias() {
		return this.siteNameAlias;
	}
	
	public void setSiteNameAlias(String siteNameAlias) {
		this.siteNameAlias = siteNameAlias;
	}
	public String getSitecoding() {
		return this.sitecoding;
	}
	
	public void setSitecoding(String sitecoding) {
		this.sitecoding = sitecoding;
	}
	public String getSiteType() {
		return this.siteType;
	}
	
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRelatedSpace() {
		return this.relatedSpace;
	}
	
	public void setRelatedSpace(String relatedSpace) {
		this.relatedSpace = relatedSpace;
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
	public String getServiceLevel() {
		return this.serviceLevel;
	}
	
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getFloorMaxnum() {
		return this.floorMaxnum;
	}
	
	public void setFloorMaxnum(String floorMaxnum) {
		this.floorMaxnum = floorMaxnum;
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
	public String getRelatedSpaceId() {
		return this.relatedSpaceId;
	}
	
	public void setRelatedSpaceId(String relatedSpaceId) {
		this.relatedSpaceId = relatedSpaceId;
	}
	
}
