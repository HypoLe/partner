package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--光缆
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransOpcable extends EomsModel{

	/**主键*/
	private String id;
		
	/**光缆名称，作为业务主键，命名要求唯一。由多根光纤组成（如24芯、48芯），位于相邻局站之间的光缆连接部分，光缆段经过若干光交接点设备，若干光缆段构成光缆线路。[例]重庆江北区华新街-人才市场光缆*/
	private String opcableName;
		
	/**设施当前所处状态，枚举值：工程、在网、空载、退网。*/
	private String opcableStatus;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**光缆对应工程名称*/
	private String projectName;
		
	/**省际一干、省内二干、本地骨干、本地汇聚、本地接入、干线本地共用。按照最符合项原则，否则按照承载光路业务的最高级别。*/
	private String businessLevel;
		
	/**自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**路缆、海缆等等*/
	private String type;
		
	/**光缆使用单位*/
	private String useUnit;
		
	/**竣工时间*/
	private String completedDate;
		
	/**所有权人*/
	private String owner;
		
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
	public String getOpcableName() {
		return this.opcableName;
	}
	
	public void setOpcableName(String opcableName) {
		this.opcableName = opcableName;
	}
	public String getOpcableStatus() {
		return this.opcableStatus;
	}
	
	public void setOpcableStatus(String opcableStatus) {
		this.opcableStatus = opcableStatus;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getBusinessLevel() {
		return this.businessLevel;
	}
	
	public void setBusinessLevel(String businessLevel) {
		this.businessLevel = businessLevel;
	}
	public String getProperty() {
		return this.property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getUseUnit() {
		return this.useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getCompletedDate() {
		return this.completedDate;
	}
	
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
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
