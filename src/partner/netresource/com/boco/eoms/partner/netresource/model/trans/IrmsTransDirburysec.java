package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--直埋段
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransDirburysec extends EomsModel{

	/**主键*/
	private String id;
		
	/**直埋段名称，作为业务主键，命名要求唯一。管线网络中将光缆或电缆直接埋于地下的部分。[例]重庆彭水-黔江PQ0145号标石-PQ0146号标石*/
	private String dirburySecName;
		
	/**关联【直埋】表-【直埋名称】。*/
	private String relatedDirbury;
		
	/**承载光缆段，命名需规范以实现数据关联。关联【光缆段】表“光缆段名称”。*/
	private String relatedOpcableSec;
		
	/**起始点名称，命名需规范以实现数据关联。关联【标石】表-【标石名称】。*/
	private String relatedStartPointName;
		
	/**末端点名称，命名需规范以实现数据关联。关联【标石】表-【标石名称】。*/
	private String relatedEndPointName;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**直埋段使用单位。例：中国移动。*/
	private String useUnit;
		
	/**直埋段所有权人。例：中国电信。*/
	private String owner;
		
	/**直埋段用途，枚举值：自用、出租、共享*/
	private String use;
		
	/**线段长度，单位：米。例：123445 */
	private String length;
		
	/**枚举值：【是】或者【否】*/
	private String isConstrutShared;
		
	/**如共建，逐一列出共建单位，用“，”分割。*/
	private String constructCompany;
		
	/**枚举值：【是】或者【否】*/
	private String isShared;
		
	/**如共享，逐一列出共享单位，用“，”分割。*/
	private String sharedUnit;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属直埋ID*/
	private String relatedDirburyId;
		
	/**起点名称ID*/
	private String relatedStartPointNameId;
		
	/**末端名称ID*/
	private String relatedEndPointNameId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getDirburySecName() {
		return this.dirburySecName;
	}
	
	public void setDirburySecName(String dirburySecName) {
		this.dirburySecName = dirburySecName;
	}
	public String getRelatedDirbury() {
		return this.relatedDirbury;
	}
	
	public void setRelatedDirbury(String relatedDirbury) {
		this.relatedDirbury = relatedDirbury;
	}
	public String getRelatedOpcableSec() {
		return this.relatedOpcableSec;
	}
	
	public void setRelatedOpcableSec(String relatedOpcableSec) {
		this.relatedOpcableSec = relatedOpcableSec;
	}
	public String getRelatedStartPointName() {
		return this.relatedStartPointName;
	}
	
	public void setRelatedStartPointName(String relatedStartPointName) {
		this.relatedStartPointName = relatedStartPointName;
	}
	public String getRelatedEndPointName() {
		return this.relatedEndPointName;
	}
	
	public void setRelatedEndPointName(String relatedEndPointName) {
		this.relatedEndPointName = relatedEndPointName;
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
	public String getLength() {
		return this.length;
	}
	
	public void setLength(String length) {
		this.length = length;
	}
	public String getIsConstrutShared() {
		return this.isConstrutShared;
	}
	
	public void setIsConstrutShared(String isConstrutShared) {
		this.isConstrutShared = isConstrutShared;
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
	public String getSharedUnit() {
		return this.sharedUnit;
	}
	
	public void setSharedUnit(String sharedUnit) {
		this.sharedUnit = sharedUnit;
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
	public String getRelatedDirburyId() {
		return this.relatedDirburyId;
	}
	
	public void setRelatedDirburyId(String relatedDirburyId) {
		this.relatedDirburyId = relatedDirburyId;
	}
	public String getRelatedStartPointNameId() {
		return this.relatedStartPointNameId;
	}
	
	public void setRelatedStartPointNameId(String relatedStartPointNameId) {
		this.relatedStartPointNameId = relatedStartPointNameId;
	}
	public String getRelatedEndPointNameId() {
		return this.relatedEndPointNameId;
	}
	
	public void setRelatedEndPointNameId(String relatedEndPointNameId) {
		this.relatedEndPointNameId = relatedEndPointNameId;
	}
	
}
