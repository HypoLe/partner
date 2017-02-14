package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--杆路段
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransPolelinesec extends EomsModel{

	/**主键*/
	private String id;
		
	/**杆路段名称，作为业务主键，命名要求唯一。杆路系统中相邻两个电杆之间的部分。[例]重庆沙坪坝区逸园-石板YS杆路YS-23号杆-YS-24号杆*/
	private String polelineSectionName;
		
	/**杆路段所属的杆路。关联到【杆路】表-【杆路名称】。*/
	private String relatedPoleline;
		
	/**起始点名称，关联【电杆】表-【电杆名称】。*/
	private String relatedStartPoleName;
		
	/**末端点名称，关联【电杆】表-【电杆名称】。*/
	private String relatedEndPoleName;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**该杆路段的使用单位。例：中国移动。*/
	private String useUnit;
		
	/**杆路段的所有权人。例：中国移动。*/
	private String owner;
		
	/**杆路段的用途，自用、出租、共享*/
	private String use;
		
	/**线段长度，单位：米。例：1234345*/
	private String sectionLength;
		
	/**枚举值：【是】或者【否】*/
	private String isConstructShared;
		
	/**如共建，逐一列出共建单位，用“，”分割。*/
	private String constructCompany;
		
	/**枚举值：【是】或者【否】*/
	private String isShared;
		
	/**如共享，逐一列出共享单位，用“，”分割。*/
	private String sharedCompany;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属杆路ID*/
	private String relatedPolelineId;
		
	/**起点电杆名称ID*/
	private String relatedStartPoleNameId;
		
	/**末端电杆名称ID*/
	private String relatedEndPoleNameId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPolelineSectionName() {
		return this.polelineSectionName;
	}
	
	public void setPolelineSectionName(String polelineSectionName) {
		this.polelineSectionName = polelineSectionName;
	}
	public String getRelatedPoleline() {
		return this.relatedPoleline;
	}
	
	public void setRelatedPoleline(String relatedPoleline) {
		this.relatedPoleline = relatedPoleline;
	}
	public String getRelatedStartPoleName() {
		return this.relatedStartPoleName;
	}
	
	public void setRelatedStartPoleName(String relatedStartPoleName) {
		this.relatedStartPoleName = relatedStartPoleName;
	}
	public String getRelatedEndPoleName() {
		return this.relatedEndPoleName;
	}
	
	public void setRelatedEndPoleName(String relatedEndPoleName) {
		this.relatedEndPoleName = relatedEndPoleName;
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
	public String getUse() {
		return this.use;
	}
	
	public void setUse(String use) {
		this.use = use;
	}
	public String getSectionLength() {
		return this.sectionLength;
	}
	
	public void setSectionLength(String sectionLength) {
		this.sectionLength = sectionLength;
	}
	public String getIsConstructShared() {
		return this.isConstructShared;
	}
	
	public void setIsConstructShared(String isConstructShared) {
		this.isConstructShared = isConstructShared;
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
	public String getRelatedPolelineId() {
		return this.relatedPolelineId;
	}
	
	public void setRelatedPolelineId(String relatedPolelineId) {
		this.relatedPolelineId = relatedPolelineId;
	}
	public String getRelatedStartPoleNameId() {
		return this.relatedStartPoleNameId;
	}
	
	public void setRelatedStartPoleNameId(String relatedStartPoleNameId) {
		this.relatedStartPoleNameId = relatedStartPoleNameId;
	}
	public String getRelatedEndPoleNameId() {
		return this.relatedEndPoleNameId;
	}
	
	public void setRelatedEndPoleNameId(String relatedEndPoleNameId) {
		this.relatedEndPoleNameId = relatedEndPoleNameId;
	}
	
}
