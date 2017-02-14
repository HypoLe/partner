package com.boco.eoms.partner.netresource.model.bs;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--基站设备及配套--板卡
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:49:19
 */
public class IrmsBsCard extends EomsModel{

	/**主键*/
	private String id;
		
	/**按集团名称规范命名（信息包含基站侧载频、传输板和核心机房BSC 、RNC板卡）*/
	private String labelCn;
		
	/**具体的所属设备名称，关联到【BSC】、【BTSSITE】、【RNC】、【NODEB】、【直放站】名称*/
	private String relatedDeviceCui;
		
	/**关联属性，关联到【机槽】表-【机槽名称】*/
	private String relatedSlotCuid;
		
	/**仓库,代维,待确认,工程部借用,其它,维修中心,现网*/
	private String step;
		
	/**11扩,12扩等*/
	private String projectNo;
		
	/**手工,枚举值{BTSSITE/NODEB/BSC/RNC}*/
	private String deviceType;
		
	/**载频、合路器、主控板、腔体*/
	private String functionType;
		
	/**3BK00797AA等*/
	private String cardModel;
		
	/**JS0167499*/
	private String propertyNo;
		
	/**BS0011U05AW*/
	private String serialNo;
		
	/**枚举{是,否}*/
	private String ifSpare;
		
	/**格式为：YYYY-MM-DD*/
	private String produceTime;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**所属设备ID*/
	private String relatedDeviceCuiId;
		
	/**Column所属槽位ID*/
	private String relatedSlotCuidId;
		

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
	public String getRelatedSlotCuid() {
		return this.relatedSlotCuid;
	}
	
	public void setRelatedSlotCuid(String relatedSlotCuid) {
		this.relatedSlotCuid = relatedSlotCuid;
	}
	public String getStep() {
		return this.step;
	}
	
	public void setStep(String step) {
		this.step = step;
	}
	public String getProjectNo() {
		return this.projectNo;
	}
	
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getDeviceType() {
		return this.deviceType;
	}
	
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getFunctionType() {
		return this.functionType;
	}
	
	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	public String getCardModel() {
		return this.cardModel;
	}
	
	public void setCardModel(String cardModel) {
		this.cardModel = cardModel;
	}
	public String getPropertyNo() {
		return this.propertyNo;
	}
	
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	public String getSerialNo() {
		return this.serialNo;
	}
	
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getIfSpare() {
		return this.ifSpare;
	}
	
	public void setIfSpare(String ifSpare) {
		this.ifSpare = ifSpare;
	}
	public String getProduceTime() {
		return this.produceTime;
	}
	
	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
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
	public String getRelatedSlotCuidId() {
		return this.relatedSlotCuidId;
	}
	
	public void setRelatedSlotCuidId(String relatedSlotCuidId) {
		this.relatedSlotCuidId = relatedSlotCuidId;
	}
	
}
