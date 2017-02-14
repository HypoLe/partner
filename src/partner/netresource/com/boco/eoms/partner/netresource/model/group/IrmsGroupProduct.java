package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--客户开通业务信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:38
 */
public class IrmsGroupProduct extends EomsModel{

	/**主键*/
	private String id;
		
	/**客户名称*/
	private String customerName;
		
	/**客户编号*/
	private String customerNo;
		
	/**业务类型*/
	private String prodType;
		
	/**产品实例标识*/
	private String prodCode;
		
	/**业务接入点地址A*/
	private String customerAddressa;
		
	/**客户业务接入点A所属地市*/
	private String citya;
		
	/**客户业务接入点A所属区县*/
	private String regiona;
		
	/**业务接入点地址Z*/
	private String customerAddressz;
		
	/**客户业务接入点Z所属地市*/
	private String cityz;
		
	/**客户业务接入点Z所属区县*/
	private String regionz;
		
	/**客户技术联系人*/
	private String customerLinkMan;
		
	/**客户技术联系人电话*/
	private String customerLinkPhon;
		
	/**业务描述*/
	private String businessDemand;
		
	/**业务开通日期*/
	private String openTime;
		
	/**业务服务终止时间*/
	private String busiEndTime;
		
	/**业务保障等级*/
	private String serviceLevel;
		
	/**创建时间*/
	private String createTime;
		
	/**备注*/
	private String remark;
		
	/**客户编号ID*/
	private String customerNoId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerNo() {
		return this.customerNo;
	}
	
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getProdType() {
		return this.prodType;
	}
	
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getProdCode() {
		return this.prodCode;
	}
	
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getCustomerAddressa() {
		return this.customerAddressa;
	}
	
	public void setCustomerAddressa(String customerAddressa) {
		this.customerAddressa = customerAddressa;
	}
	public String getCitya() {
		return this.citya;
	}
	
	public void setCitya(String citya) {
		this.citya = citya;
	}
	public String getRegiona() {
		return this.regiona;
	}
	
	public void setRegiona(String regiona) {
		this.regiona = regiona;
	}
	public String getCustomerAddressz() {
		return this.customerAddressz;
	}
	
	public void setCustomerAddressz(String customerAddressz) {
		this.customerAddressz = customerAddressz;
	}
	public String getCityz() {
		return this.cityz;
	}
	
	public void setCityz(String cityz) {
		this.cityz = cityz;
	}
	public String getRegionz() {
		return this.regionz;
	}
	
	public void setRegionz(String regionz) {
		this.regionz = regionz;
	}
	public String getCustomerLinkMan() {
		return this.customerLinkMan;
	}
	
	public void setCustomerLinkMan(String customerLinkMan) {
		this.customerLinkMan = customerLinkMan;
	}
	public String getCustomerLinkPhon() {
		return this.customerLinkPhon;
	}
	
	public void setCustomerLinkPhon(String customerLinkPhon) {
		this.customerLinkPhon = customerLinkPhon;
	}
	public String getBusinessDemand() {
		return this.businessDemand;
	}
	
	public void setBusinessDemand(String businessDemand) {
		this.businessDemand = businessDemand;
	}
	public String getOpenTime() {
		return this.openTime;
	}
	
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getBusiEndTime() {
		return this.busiEndTime;
	}
	
	public void setBusiEndTime(String busiEndTime) {
		this.busiEndTime = busiEndTime;
	}
	public String getServiceLevel() {
		return this.serviceLevel;
	}
	
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
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
	public String getCustomerNoId() {
		return this.customerNoId;
	}
	
	public void setCustomerNoId(String customerNoId) {
		this.customerNoId = customerNoId;
	}
	
}
