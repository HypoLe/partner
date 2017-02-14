package com.boco.eoms.partner.property.model;

import java.util.Date;

/**
 * 类说明：电费费用记录物业合同
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrPropertyAgreement {

	/**主键*/
	private String id;
	
	/**各省自定义编码方式*/
	private String agreementNo;
		
	/***/
	private String site;
		
	/***/
	private String agreementName;
		
	/***/
	private String parta;
		
	/***/
	private String partb;
		
	/**电费合同、租赁合同*/
	private String agreementType;
		
	/***/
	private Date signDate;
		
	/***/
	private Date endDate;
		
	/**规律付款的可选择字典：月付、季付、半年付、年付、其它。默认上述字典对应的最后一天为下次付款时间。
不规律付款的手动选择应设置为下次付款时间
*/
	private String payCycle;
		
	/**精确到县级；保留其上所属的区县、地市层次*/
	private String distirct;
		
	/***/
	private String partaSigningPerson;
		
	/***/
	private String partaSigningPersonTel;
		
	/***/
	private String partbSigningPerson;
		
	/***/
	private String partbSigningPersonTel;
		
	/**保留两位小数*/
	private double agreementAmount;
		
	/**即合同到期前3个月，被短信和网页提醒的人员；可选择多人。
	可以手动填写手机号码
	*/
	private String remindObject;
		
	/***/
	private String attachment;
		
	/***/
	private String remark;
		
	/***/
	private String createTime;
	
	/**合同状态:失效，有效*/
	private String agreementStatus;
		
	/**是否到期提醒:是，否*/
	private String expireRemind;
	 
	/**删除标志位："0"或者"",为不删除，"1"为删除标志*/
	private String deletedFlag;
	
	/**所属省份*/
	private String province;
	
	/**所属地市*/
	private String city;
	
	/**提醒信息发送的日期*/
	private Date msgSendDate;
	
	/**合同信息对应统计信息里的id*/
	private String countId;
	
	
	
	
	public Date getMsgSendDate() {
		return msgSendDate;
	}

	public void setMsgSendDate(Date msgSendDate) {
		this.msgSendDate = msgSendDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAgreementNo() {
		return this.agreementNo;
	}
	
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	public String getSite() {
		return this.site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	public String getAgreementName() {
		return this.agreementName;
	}
	
	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}
	public String getParta() {
		return this.parta;
	}
	
	public void setParta(String parta) {
		this.parta = parta;
	}
	public String getPartb() {
		return this.partb;
	}
	
	public void setPartb(String partb) {
		this.partb = partb;
	}
	public String getAgreementType() {
		return this.agreementType;
	}
	
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
	public Date getSignDate() {
		return this.signDate;
	}
	
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPayCycle() {
		return this.payCycle;
	}
	
	public void setPayCycle(String payCycle) {
		this.payCycle = payCycle;
	}
	public String getDistirct() {
		return this.distirct;
	}
	
	public void setDistirct(String distirct) {
		this.distirct = distirct;
	}
	public String getPartaSigningPerson() {
		return this.partaSigningPerson;
	}
	
	public void setPartaSigningPerson(String partaSigningPerson) {
		this.partaSigningPerson = partaSigningPerson;
	}
	public String getPartaSigningPersonTel() {
		return this.partaSigningPersonTel;
	}
	
	public void setPartaSigningPersonTel(String partaSigningPersonTel) {
		this.partaSigningPersonTel = partaSigningPersonTel;
	}
	public String getPartbSigningPerson() {
		return this.partbSigningPerson;
	}
	
	public void setPartbSigningPerson(String partbSigningPerson) {
		this.partbSigningPerson = partbSigningPerson;
	}
	public String getPartbSigningPersonTel() {
		return this.partbSigningPersonTel;
	}
	
	public void setPartbSigningPersonTel(String partbSigningPersonTel) {
		this.partbSigningPersonTel = partbSigningPersonTel;
	}
	public double getAgreementAmount() {
		return this.agreementAmount;
	}
	
	public void setAgreementAmount(double agreementAmount) {
		this.agreementAmount = agreementAmount;
	}
	public String getRemindObject() {
		return this.remindObject;
	}
	
	public void setRemindObject(String remindObject) {
		this.remindObject = remindObject;
	}
	public String getAttachment() {
		return this.attachment;
	}
	
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAgreementStatus() {
		return agreementStatus;
	}

	public void setAgreementStatus(String agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	public String getExpireRemind() {
		return expireRemind;
	}

	public void setExpireRemind(String expireRemind) {
		this.expireRemind = expireRemind;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getCountId() {
		return countId;
	}

	public void setCountId(String countId) {
		this.countId = countId;
	}
	
}
