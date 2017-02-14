package com.boco.eoms.partner.netresource.model.group;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--集客家客--客户信息表
 * 创建人： zhangkeqi
 * 创建时间：2013-03-06 17:37:38
 */
public class IrmsGrouCustomer extends EomsModel{

	/**主键*/
	private String id;
		
	/**由BOSS系统提供，用于业务侧与网络侧唯一标示一个客户。可作为T1.1/T1.2/T2.1～T2.5/T3.1关联的对象*/
	private String customerNo;
		
	/**填写集团客户名称*/
	private String customerName;
		
	/**填写客户详细地址*/
	private String customerAddr;
		
	/**选项：A类/B类/C类/D类*/
	private String customerLevel;
		
	/**选项：金、银、铜、标准*/
	private String serviceLevel;
		
	/**填写客户公司归属的行业名称*/
	private String industry;
		
	/**填写对应的城市名称*/
	private String city;
		
	/**填写客户所在地理区域（包括正式客户和潜在客户）*/
	private String region;
		
	/**字符串（10）*/
	private String linkman;
		
	/**字符串（20）*/
	private String linkPhone;
		
	/***/
	private String relatedManagerCu;
		
	/***/
	private String relatedManagerPh;
		
	/***/
	private String groupContact;
		
	/***/
	private String groupPhone;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerNo() {
		return this.customerNo;
	}
	
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddr() {
		return this.customerAddr;
	}
	
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public String getCustomerLevel() {
		return this.customerLevel;
	}
	
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}
	public String getServiceLevel() {
		return this.serviceLevel;
	}
	
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getIndustry() {
		return this.industry;
	}
	
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return this.region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLinkman() {
		return this.linkman;
	}
	
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkPhone() {
		return this.linkPhone;
	}
	
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getRelatedManagerCu() {
		return this.relatedManagerCu;
	}
	
	public void setRelatedManagerCu(String relatedManagerCu) {
		this.relatedManagerCu = relatedManagerCu;
	}
	public String getRelatedManagerPh() {
		return this.relatedManagerPh;
	}
	
	public void setRelatedManagerPh(String relatedManagerPh) {
		this.relatedManagerPh = relatedManagerPh;
	}
	public String getGroupContact() {
		return this.groupContact;
	}
	
	public void setGroupContact(String groupContact) {
		this.groupContact = groupContact;
	}
	public String getGroupPhone() {
		return this.groupPhone;
	}
	
	public void setGroupPhone(String groupPhone) {
		this.groupPhone = groupPhone;
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
	
}
