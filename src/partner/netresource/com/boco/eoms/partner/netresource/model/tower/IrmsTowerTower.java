package com.boco.eoms.partner.netresource.model.tower;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--铁塔及天馈--铁塔
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:53:03
 */
public class IrmsTowerTower extends EomsModel{

	/**主键*/
	private String id;
		
	/****机房**塔（空间资源机房名称+铁塔类型）*/
	private String labelCn;
		
	/**2*/
	private String towerPlatnum;
		
	/**1*/
	private String usePlatnum;
		
	/**关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站*/
	private String relatedRoom;
		
	/**单位:米*/
	private String towerStature;
		
	/**枚举{落地角钢塔,楼顶角钢塔,落地三管塔,楼顶三管塔,落地内爬单管塔,落地外爬单管塔,楼顶单管塔,落地拉线塔,楼顶拉线塔,楼顶井字架,落地景观塔,楼顶景观塔,抱杆,楼顶美化天线,集束天线、其他}*/
	private String towerType;
		
	/**江苏邮政机械厂、浙江电联*/
	private String vendor;
		
	/**移动、联通、电信、其他*/
	private String towerPropertyAtt;
		
	/**填写格式:yyyy-MM-dd*/
	private String buildtime;
		
	/**单位：米，高度包括楼高+塔身高*/
	private String towerHeight;
		
	/**公斤*/
	private String designBareWeight;
		
	/**公斤*/
	private String realBareWeight;
		
	/**浙江和勤通信工程有限公司*/
	private String manageCompany;
		
	/**枚举值：【是】或者【否】*/
	private String isConstructShare;
		
	/**如共建，逐一列出共建单位，用“，”分割。*/
	private String constructCompany;
		
	/**枚举值：【是】或者【否】*/
	private String isShared;
		
	/**如共建，逐一列出共建单位，用“，”分割。*/
	private String sharedCompany;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属机房ID*/
	private String relatedRoomId;
		

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
	public String getTowerPlatnum() {
		return this.towerPlatnum;
	}
	
	public void setTowerPlatnum(String towerPlatnum) {
		this.towerPlatnum = towerPlatnum;
	}
	public String getUsePlatnum() {
		return this.usePlatnum;
	}
	
	public void setUsePlatnum(String usePlatnum) {
		this.usePlatnum = usePlatnum;
	}
	public String getRelatedRoom() {
		return this.relatedRoom;
	}
	
	public void setRelatedRoom(String relatedRoom) {
		this.relatedRoom = relatedRoom;
	}
	public String getTowerStature() {
		return this.towerStature;
	}
	
	public void setTowerStature(String towerStature) {
		this.towerStature = towerStature;
	}
	public String getTowerType() {
		return this.towerType;
	}
	
	public void setTowerType(String towerType) {
		this.towerType = towerType;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getTowerPropertyAtt() {
		return this.towerPropertyAtt;
	}
	
	public void setTowerPropertyAtt(String towerPropertyAtt) {
		this.towerPropertyAtt = towerPropertyAtt;
	}
	public String getBuildtime() {
		return this.buildtime;
	}
	
	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}
	public String getTowerHeight() {
		return this.towerHeight;
	}
	
	public void setTowerHeight(String towerHeight) {
		this.towerHeight = towerHeight;
	}
	public String getDesignBareWeight() {
		return this.designBareWeight;
	}
	
	public void setDesignBareWeight(String designBareWeight) {
		this.designBareWeight = designBareWeight;
	}
	public String getRealBareWeight() {
		return this.realBareWeight;
	}
	
	public void setRealBareWeight(String realBareWeight) {
		this.realBareWeight = realBareWeight;
	}
	public String getManageCompany() {
		return this.manageCompany;
	}
	
	public void setManageCompany(String manageCompany) {
		this.manageCompany = manageCompany;
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
	public String getRelatedRoomId() {
		return this.relatedRoomId;
	}
	
	public void setRelatedRoomId(String relatedRoomId) {
		this.relatedRoomId = relatedRoomId;
	}
	
}
