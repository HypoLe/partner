package com.boco.eoms.partner.netresource.model.space;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--空间资源--区域
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:44:33
 */
public class IrmsSpaceArea extends EomsModel{

	/**主键*/
	private String id;
		
	/**区域名称*/
	private String areaName;
		
	/**区域名称中文缩写*/
	private String abbreviation;
		
	/**拼音缩写*/
	private String spellabbreviation;
		
	/**别名*/
	private String areaAlias;
		
	/**长途区号*/
	private String areaCode;
		
	/**所属区域名称*/
	private String relatedSpace;
		
	/**地区类型  枚举值：全国、省、市、区县、乡镇*/
	private String areaType;
		
	/**是否省会  枚举值：是、否*/
	private String isCapital;
		
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
	public String getAreaName() {
		return this.areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAbbreviation() {
		return this.abbreviation;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getSpellabbreviation() {
		return this.spellabbreviation;
	}
	
	public void setSpellabbreviation(String spellabbreviation) {
		this.spellabbreviation = spellabbreviation;
	}
	public String getAreaAlias() {
		return this.areaAlias;
	}
	
	public void setAreaAlias(String areaAlias) {
		this.areaAlias = areaAlias;
	}
	public String getAreaCode() {
		return this.areaCode;
	}
	
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getRelatedSpace() {
		return this.relatedSpace;
	}
	
	public void setRelatedSpace(String relatedSpace) {
		this.relatedSpace = relatedSpace;
	}
	public String getAreaType() {
		return this.areaType;
	}
	
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getIsCapital() {
		return this.isCapital;
	}
	
	public void setIsCapital(String isCapital) {
		this.isCapital = isCapital;
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
