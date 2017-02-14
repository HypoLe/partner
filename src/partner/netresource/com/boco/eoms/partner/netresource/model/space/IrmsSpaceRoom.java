package com.boco.eoms.partner.netresource.model.space;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--空间资源--机房
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:44:33
 */
public class IrmsSpaceRoom extends EomsModel{

	/**主键*/
	private String id;
		
	/**机房名称*/
	private String roomName;
		
	/**机房缩写*/
	private String abbreviation;
		
	/**机房别名*/
	private String roomAlias;
		
	/**机房类型*/
	private String roomType;
		
	/**传输业务级别*/
	private String serviceLevel;
		
	/**所属站点*/
	private String relatedSiteName;
		
	/**所在楼层*/
	private String floorNum;
		
	/**长*/
	private String length;
		
	/**宽*/
	private String width;
		
	/**高*/
	private String height;
		
	/**机架起始行号*/
	private String rackStartRowNum;
		
	/**机架终止行号*/
	private String rackEndRowNum;
		
	/**机架起始列号*/
	private String rackStartColNum;
		
	/**机架终止列号*/
	private String rackEndColNum;
		
	/**机架行方向 枚举值：从东到西、从西到东、从南到北、从北到南
说明：对于核心生产楼的机房此项必填*/
	private String rackRowDirect;
		
	/**机架列方向 机架列方向 枚举值：从东到西、从西到东、从南到北、从北到南
说明：对于核心生产楼的机房此项必填*/
	private String rackColumnDirect;
		
	/**是否共建 枚举值：【是】或者【否】*/
	private String isConstructShare;
		
	/**共建单位*/
	private String buildTogtherUnit;
		
	/**是否共享 枚举值：【是】或者【否】*/
	private String isShared;
		
	/**共享单位*/
	private String sharedUnit;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属站点ID*/
	private String relatedSiteNameId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomName() {
		return this.roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getAbbreviation() {
		return this.abbreviation;
	}
	
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getRoomAlias() {
		return this.roomAlias;
	}
	
	public void setRoomAlias(String roomAlias) {
		this.roomAlias = roomAlias;
	}
	public String getRoomType() {
		return this.roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getServiceLevel() {
		return this.serviceLevel;
	}
	
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getRelatedSiteName() {
		return this.relatedSiteName;
	}
	
	public void setRelatedSiteName(String relatedSiteName) {
		this.relatedSiteName = relatedSiteName;
	}
	public String getFloorNum() {
		return this.floorNum;
	}
	
	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}
	public String getLength() {
		return this.length;
	}
	
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return this.width;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return this.height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	public String getRackStartRowNum() {
		return this.rackStartRowNum;
	}
	
	public void setRackStartRowNum(String rackStartRowNum) {
		this.rackStartRowNum = rackStartRowNum;
	}
	public String getRackEndRowNum() {
		return this.rackEndRowNum;
	}
	
	public void setRackEndRowNum(String rackEndRowNum) {
		this.rackEndRowNum = rackEndRowNum;
	}
	public String getRackStartColNum() {
		return this.rackStartColNum;
	}
	
	public void setRackStartColNum(String rackStartColNum) {
		this.rackStartColNum = rackStartColNum;
	}
	public String getRackEndColNum() {
		return this.rackEndColNum;
	}
	
	public void setRackEndColNum(String rackEndColNum) {
		this.rackEndColNum = rackEndColNum;
	}
	public String getRackRowDirect() {
		return this.rackRowDirect;
	}
	
	public void setRackRowDirect(String rackRowDirect) {
		this.rackRowDirect = rackRowDirect;
	}
	public String getRackColumnDirect() {
		return this.rackColumnDirect;
	}
	
	public void setRackColumnDirect(String rackColumnDirect) {
		this.rackColumnDirect = rackColumnDirect;
	}
	public String getIsConstructShare() {
		return this.isConstructShare;
	}
	
	public void setIsConstructShare(String isConstructShare) {
		this.isConstructShare = isConstructShare;
	}
	public String getBuildTogtherUnit() {
		return this.buildTogtherUnit;
	}
	
	public void setBuildTogtherUnit(String buildTogtherUnit) {
		this.buildTogtherUnit = buildTogtherUnit;
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
	public String getRelatedSiteNameId() {
		return this.relatedSiteNameId;
	}
	
	public void setRelatedSiteNameId(String relatedSiteNameId) {
		this.relatedSiteNameId = relatedSiteNameId;
	}
	
}
