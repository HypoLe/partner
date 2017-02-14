package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--管道段
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransPipesec extends EomsModel{

	/**主键*/
	private String id;
		
	/**管道段名称，作为业务主键，命名要求唯一。管道段截面的空心部分，可穿放若干条光、电缆或子管孔，一个管道段可以有多个管孔。[例]重庆渝中区中山四路ZS-6号井-ZS-7号井*/
	private String pipeSectionName;
		
	/**起始点名称，关联【人手井】表“人手井名称”*/
	private String startWellName;
		
	/**末端点名称，关联【人手井】表“人手井名称”*/
	private String endWellName;
		
	/**枚举值：波纹管、梅花管，组合*/
	private String stuff;
		
	/**移动管孔数目。例：3*/
	private String poreCount;
		
	/**移动子孔数目。例：3*/
	private String subPoreCount;
		
	/**关联到【管道】表-【管道名称】*/
	private String relatedPipe;
		
	/**承载光缆段，命名需规范以实现数据关联。*/
	private String relatedSection;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**管道段的使用单位。例：中国移动。*/
	private String useUnit;
		
	/**管道段的所有权人。例：中国电信。*/
	private String ownerUnit;
		
	/**管道段长度，单位：米。*/
	private String sectionLength;
		
	/**枚举值：【是】或者【否】*/
	private String isConstructShare;
		
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
		
	/**起始点人手井名称ID*/
	private String startWellNameId;
		
	/**末端点人手井名称ID*/
	private String endWellNameId;
		
	/**所属管道ID*/
	private String relatedPipeId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPipeSectionName() {
		return this.pipeSectionName;
	}
	
	public void setPipeSectionName(String pipeSectionName) {
		this.pipeSectionName = pipeSectionName;
	}
	public String getStartWellName() {
		return this.startWellName;
	}
	
	public void setStartWellName(String startWellName) {
		this.startWellName = startWellName;
	}
	public String getEndWellName() {
		return this.endWellName;
	}
	
	public void setEndWellName(String endWellName) {
		this.endWellName = endWellName;
	}
	public String getStuff() {
		return this.stuff;
	}
	
	public void setStuff(String stuff) {
		this.stuff = stuff;
	}
	public String getPoreCount() {
		return this.poreCount;
	}
	
	public void setPoreCount(String poreCount) {
		this.poreCount = poreCount;
	}
	public String getSubPoreCount() {
		return this.subPoreCount;
	}
	
	public void setSubPoreCount(String subPoreCount) {
		this.subPoreCount = subPoreCount;
	}
	public String getRelatedPipe() {
		return this.relatedPipe;
	}
	
	public void setRelatedPipe(String relatedPipe) {
		this.relatedPipe = relatedPipe;
	}
	public String getRelatedSection() {
		return this.relatedSection;
	}
	
	public void setRelatedSection(String relatedSection) {
		this.relatedSection = relatedSection;
	}
	public String getProperty() {
		return this.property ;
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
	public String getOwnerUnit() {
		return this.ownerUnit;
	}
	
	public void setOwnerUnit(String ownerUnit) {
		this.ownerUnit = ownerUnit;
	}
	public String getSectionLength() {
		return this.sectionLength;
	}
	
	public void setSectionLength(String sectionLength) {
		this.sectionLength = sectionLength;
	}
	public String getIsConstructShare() {
		return this.isConstructShare;
	}
	
	public void setIsConstructShare(String isConstructShare) {
		this.isConstructShare = isConstructShare;
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
	public String getStartWellNameId() {
		return this.startWellNameId;
	}
	
	public void setStartWellNameId(String startWellNameId) {
		this.startWellNameId = startWellNameId;
	}
	public String getEndWellNameId() {
		return this.endWellNameId;
	}
	
	public void setEndWellNameId(String endWellNameId) {
		this.endWellNameId = endWellNameId;
	}
	public String getRelatedPipeId() {
		return this.relatedPipeId;
	}
	
	public void setRelatedPipeId(String relatedPipeId) {
		this.relatedPipeId = relatedPipeId;
	}
	
}
