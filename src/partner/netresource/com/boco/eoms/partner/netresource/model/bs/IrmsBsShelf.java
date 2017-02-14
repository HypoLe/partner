package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--机框
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:18
 */
public class IrmsBsShelf extends EomsModel{

	/**主键*/
	private String id;
		
	/**机框名称，作为业务主键，命名要求唯一。*/
	private String labelCn;
		
	/**关联属性，关联到【机柜】表-【机柜名称】*/
	private String relatedRackCuid;
		
	/**机框编号，在机柜的位置，数字*/
	private String sequence;
		
	/**数字*/
	private String slotAmount;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属机柜ID*/
	private String relatedRackCuidId;
		

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
	public String getRelatedRackCuid() {
		return this.relatedRackCuid;
	}
	
	public void setRelatedRackCuid(String relatedRackCuid) {
		this.relatedRackCuid = relatedRackCuid;
	}
	public String getSequence() {
		return this.sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getSlotAmount() {
		return this.slotAmount;
	}
	
	public void setSlotAmount(String slotAmount) {
		this.slotAmount = slotAmount;
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
	public String getRelatedRackCuidId() {
		return this.relatedRackCuidId;
	}
	
	public void setRelatedRackCuidId(String relatedRackCuidId) {
		this.relatedRackCuidId = relatedRackCuidId;
	}
	
}
