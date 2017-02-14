package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--机柜
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsRack extends EomsModel{

	/**主键*/
	private String id;
		
	/**机柜名称，作为业务主键，命名要求唯一。*/
	private String labelCn;
		
	/**关联属性，关联到【BTSSITE】或【NODEB】表网元名称,只对基站无线设备要求填写*/
	private String relatedDeviceCui;
		
	/**关联属性，关联到【空间资源】-【机架位置表】表-【机架编号】，要求核心生产楼无线专业设备机柜需要与其关联，基站机柜不需与此项关联，基站已与机房关联*/
	private String relatedBody;
		
	/**枚举值：诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
	private String vendor;
		
	/**枚举值：BSC、RNC、BTS、NODEB*/
	private String rackType;
		
	/**数字，机框数*/
	private String shelfAmount;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属基站ID*/
	private String relatedDeviceCuiId;
		
	/**所属机架ID*/
	private String relatedBodyId;
		

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
	public String getRelatedDeviceCui() {
		return this.relatedDeviceCui;
	}
	
	public void setRelatedDeviceCui(String relatedDeviceCui) {
		this.relatedDeviceCui = relatedDeviceCui;
	}
	public String getRelatedBody() {
		return this.relatedBody;
	}
	
	public void setRelatedBody(String relatedBody) {
		this.relatedBody = relatedBody;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getRackType() {
		return this.rackType;
	}
	
	public void setRackType(String rackType) {
		this.rackType = rackType;
	}
	public String getShelfAmount() {
		return this.shelfAmount;
	}
	
	public void setShelfAmount(String shelfAmount) {
		this.shelfAmount = shelfAmount;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRelatedDeviceCuiId() {
		return this.relatedDeviceCuiId;
	}
	
	public void setRelatedDeviceCuiId(String relatedDeviceCuiId) {
		this.relatedDeviceCuiId = relatedDeviceCuiId;
	}
	public String getRelatedBodyId() {
		return this.relatedBodyId;
	}
	
	public void setRelatedBodyId(String relatedBodyId) {
		this.relatedBodyId = relatedBodyId;
	}
	
}
