package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--NODEB
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsNodeb extends EomsModel{

	/**主键*/
	private String id;
		
	/**按照集团要求，OMC中网元名称要与综合资管命名保持一致，此项命名参照集团规范*/
	private String userlabelCm;
		
	/**中文名称*/
	private String labelCn;
		
	/**关联属性，关联到【RNC】表网元名称*/
	private String relatedRnc;
		
	/** 光传输、微波、卫星*/
	private String transType;
		
	/**A频段、B频段、E频段、F频段、A+B频段*/
	private String siteCategory;
		
	/** 枚举:非VIP,一级VIP,二级VIP,三级VIP,非基站机房,VVIP类型,超级VIP基站*/
	private String vipType;
		
	/** 枚举{宏蜂窝,微蜂窝}*/
	private String beehiveType;
		
	/**设备型号*/
	private String model;
		
	/**关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站虚拟机房名称，名称中可通过扩展虚拟字段进行区分。*/
	private String relatedRoom;
		
	/**网优中心排障组等*/
	private String failAccUnit;
		
	/**江苏海讯科技有限公司,京信通信系统(广州)有限公司等等*/
	private String manageCompany;
		
	/** 枚举值：{现网、工程、空载、退网}*/
	private String status;
		
	/**枚举值：诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
	private String relatedVendor;
		
	/**开通时间*/
	private String openTime;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属RNC ID*/
	private String relatedRncId;
		
	/**所属机房ID*/
	private String relatedRoomId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getUserlabelCm() {
		return this.userlabelCm;
	}
	
	public void setUserlabelCm(String userlabelCm) {
		this.userlabelCm = userlabelCm;
	}
	public String getLabelCn() {
		return this.labelCn;
	}
	
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}
	public String getRelatedRnc() {
		return this.relatedRnc;
	}
	
	public void setRelatedRnc(String relatedRnc) {
		this.relatedRnc = relatedRnc;
	}
	public String getTransType() {
		return this.transType;
	}
	
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getSiteCategory() {
		return this.siteCategory;
	}
	
	public void setSiteCategory(String siteCategory) {
		this.siteCategory = siteCategory;
	}
	public String getVipType() {
		return this.vipType;
	}
	
	public void setVipType(String vipType) {
		this.vipType = vipType;
	}
	public String getBeehiveType() {
		return this.beehiveType;
	}
	
	public void setBeehiveType(String beehiveType) {
		this.beehiveType = beehiveType;
	}
	public String getModel() {
		return this.model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	public String getRelatedRoom() {
		return this.relatedRoom;
	}
	
	public void setRelatedRoom(String relatedRoom) {
		this.relatedRoom = relatedRoom;
	}
	public String getFailAccUnit() {
		return this.failAccUnit;
	}
	
	public void setFailAccUnit(String failAccUnit) {
		this.failAccUnit = failAccUnit;
	}
	public String getManageCompany() {
		return this.manageCompany;
	}
	
	public void setManageCompany(String manageCompany) {
		this.manageCompany = manageCompany;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRelatedVendor() {
		return this.relatedVendor;
	}
	
	public void setRelatedVendor(String relatedVendor) {
		this.relatedVendor = relatedVendor;
	}
	public String getOpenTime() {
		return this.openTime;
	}
	
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
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
	public String getRelatedRncId() {
		return this.relatedRncId;
	}
	
	public void setRelatedRncId(String relatedRncId) {
		this.relatedRncId = relatedRncId;
	}
	public String getRelatedRoomId() {
		return this.relatedRoomId;
	}
	
	public void setRelatedRoomId(String relatedRoomId) {
		this.relatedRoomId = relatedRoomId;
	}
	
}
