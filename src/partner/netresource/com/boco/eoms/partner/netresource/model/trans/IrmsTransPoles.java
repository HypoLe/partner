package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--电杆
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransPoles extends EomsModel{

	/**主键*/
	private String id;
		
	/**电杆名称，作为业务主键，命名要求唯一。[例]重庆沙坪坝区逸园-石板杆路YS-57号杆*/
	private String poleName;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistirct;
		
	/**枚举：七米杆、八米杆、九米杆。*/
	private String poleHeight;
		
	/**枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String poleProperty;
		
	/**电杆的用途，自用、出租、共享*/
	private String poleUse;
		
	/**使用单位。例：中国移动。*/
	private String poleUseUnit;
		
	/**电杆所有权人。例：中国电信。*/
	private String poleOwership;
		
	/**经度。例：123.123456*/
	private String poleLongitude;
		
	/**纬度。例：34.123456*/
	private String poleLatitude;
		
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
	public String getPoleName() {
		return this.poleName;
	}
	
	public void setPoleName(String poleName) {
		this.poleName = poleName;
	}
	public String getRelatedDistirct() {
		return this.relatedDistirct;
	}
	
	public void setRelatedDistirct(String relatedDistirct) {
		this.relatedDistirct = relatedDistirct;
	}
	public String getPoleHeight() {
		return this.poleHeight;
	}
	
	public void setPoleHeight(String poleHeight) {
		this.poleHeight = poleHeight;
	}
	public String getPoleProperty() {
		return this.poleProperty;
	}
	
	public void setPoleProperty(String poleProperty) {
		this.poleProperty = poleProperty;
	}
	public String getPoleUse() {
		return this.poleUse;
	}
	
	public void setPoleUse(String poleUse) {
		this.poleUse = poleUse;
	}
	public String getPoleUseUnit() {
		return this.poleUseUnit;
	}
	
	public void setPoleUseUnit(String poleUseUnit) {
		this.poleUseUnit = poleUseUnit;
	}
	public String getPoleOwership() {
		return this.poleOwership;
	}
	
	public void setPoleOwership(String poleOwership) {
		this.poleOwership = poleOwership;
	}
	public String getPoleLongitude() {
		return this.poleLongitude;
	}
	
	public void setPoleLongitude(String poleLongitude) {
		this.poleLongitude = poleLongitude;
	}
	public String getPoleLatitude() {
		return this.poleLatitude;
	}
	
	public void setPoleLatitude(String poleLatitude) {
		this.poleLatitude = poleLatitude;
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
