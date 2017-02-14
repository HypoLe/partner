package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--机槽
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsSlot extends EomsModel{

	/**主键*/
	private String id;
		
	/**机槽名称，作为业务主键，命名要求唯一。*/
	private String labelCn;
		
	/**枚举值：在用、空闲*/
	private String status;
		
	/**关联属性，关联到无线专业【机框】表-【机框名称】*/
	private String relatedShelfCuid;
		
	/**机槽编号，在机框的位置，数字*/
	private String sequence;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属机框ID*/
	private String relatedShelfCuidId;
		

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
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRelatedShelfCuid() {
		return this.relatedShelfCuid;
	}
	
	public void setRelatedShelfCuid(String relatedShelfCuid) {
		this.relatedShelfCuid = relatedShelfCuid;
	}
	public String getSequence() {
		return this.sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
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
	public String getRelatedShelfCuidId() {
		return this.relatedShelfCuidId;
	}
	
	public void setRelatedShelfCuidId(String relatedShelfCuidId) {
		this.relatedShelfCuidId = relatedShelfCuidId;
	}
	
}
