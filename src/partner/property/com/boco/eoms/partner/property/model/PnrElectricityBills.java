package com.boco.eoms.partner.property.model;

import java.util.Date;

/**
 * 类说明：物业合同管理-电费费用记录
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:36
 */
public class PnrElectricityBills {

	/**主键*/
	private String id;
	
	/**合同相关联id*/
	private String refId;
		
	/**关联物业合同*/
	private String relatedAgreementNo;
		
	/**关联物业合同*/
	private String relatedAgreementName;
		
	/**关联物业合同*/
	private String relatedParta;
		
	/**关联物业合同*/
	private String relatedPartb;
		
	/**关联物业合同*/
	private String relatedAgreementType;
		
	/**关联物业合同*/
	private String relatedDistrict;
	
	/**关联物业合同*/
	private String relatedSite;
		
	/**规律付款的可选择字典：月付、季付、半年付、年付、其它。默认上述字典对应的最后一天为下次付款时间。
	不规律付款的手动选择应设置为下次付款时间。
	 */
	private String payCircle;
		
	/**即所结算的费用作用于何时*/
	private Date settlementTimeSectStart;
	
	/**即所结算的费用到何时结束*/
	private Date settlementTimeSectEnd;
		
	/**第一次手动填写，其后由系统提取上一次费用记录表单中的“本期读数”自动生成为本次的“上期读数”
		支持手动修改（有手动数据修改时，需备注说明）
	*/
	private double lastNum;
		
	/***/
	private double nowNum;
		
	/***/
	private double univalency;
		
	/**不套用公式；保留两位小数*/
	private double mustPayMoney;
		
	/**保留两位小数*/
	private double paidMoney;
		
	/**保留两位小数*/
	private double unpaidMoney;
		
	/**现金、转账*/
	private String payWay;
		
	/**结算日期*/
	private Date settlementDate;
	
	/**计划支付日期*/
	private Date planPayDate;
	
	/**支付状态*/
	private String payStatus;
		
	/**即办理此项费用的人员姓名*/
	private String manager;
		
	/**即合同付款周期到期前15天，被短信和网页提醒的人员。
	默认提醒经办人；可选择多人。可以输入手机号码
	*/
	private String remindObject;
		
	/***/
	private String attachment;
		
	/***/
	private String remark;
		
	/***/
	private String createTime;
	
	/**所属省份*/
	private String province;
	
	/**所属地市*/
	private String city;
		
	/**关联账单统计的id*/
	private String relatedCountId;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRelatedAgreementNo() {
		return this.relatedAgreementNo;
	}
	
	public void setRelatedAgreementNo(String relatedAgreementNo) {
		this.relatedAgreementNo = relatedAgreementNo;
	}
	public String getRelatedAgreementName() {
		return this.relatedAgreementName;
	}
	
	public void setRelatedAgreementName(String relatedAgreementName) {
		this.relatedAgreementName = relatedAgreementName;
	}
	public String getRelatedParta() {
		return this.relatedParta;
	}
	
	public void setRelatedParta(String relatedParta) {
		this.relatedParta = relatedParta;
	}
	public String getRelatedPartb() {
		return this.relatedPartb;
	}
	
	public void setRelatedPartb(String relatedPartb) {
		this.relatedPartb = relatedPartb;
	}
	public String getRelatedAgreementType() {
		return this.relatedAgreementType;
	}
	
	public void setRelatedAgreementType(String relatedAgreementType) {
		this.relatedAgreementType = relatedAgreementType;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getPayCircle() {
		return this.payCircle;
	}
	
	public void setPayCircle(String payCircle) {
		this.payCircle = payCircle;
	}
	public double getLastNum() {
		return this.lastNum;
	}
	
	public void setLastNum(double lastNum) {
		this.lastNum = lastNum;
	}
	public double getNowNum() {
		return this.nowNum;
	}
	
	public void setNowNum(double nowNum) {
		this.nowNum = nowNum;
	}
	public double getUnivalency() {
		return this.univalency;
	}
	
	public Date getSettlementTimeSectStart() {
		return settlementTimeSectStart;
	}

	public void setSettlementTimeSectStart(Date settlementTimeSectStart) {
		this.settlementTimeSectStart = settlementTimeSectStart;
	}

	public Date getSettlementTimeSectEnd() {
		return settlementTimeSectEnd;
	}

	public void setSettlementTimeSectEnd(Date settlementTimeSectEnd) {
		this.settlementTimeSectEnd = settlementTimeSectEnd;
	}

	public void setUnivalency(double univalency) {
		this.univalency = univalency;
	}
	public double getMustPayMoney() {
		return this.mustPayMoney;
	}
	
	public void setMustPayMoney(double mustPayMoney) {
		this.mustPayMoney = mustPayMoney;
	}
	public double getPaidMoney() {
		return this.paidMoney;
	}
	
	public void setPaidMoney(double paidMoney) {
		this.paidMoney = paidMoney;
	}
	public double getUnpaidMoney() {
		return this.unpaidMoney;
	}
	
	public void setUnpaidMoney(double unpaidMoney) {
		this.unpaidMoney = unpaidMoney;
	}
	public String getPayWay() {
		return this.payWay;
	}
	
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public Date getSettlementDate() {
		return this.settlementDate;
	}
	
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getManager() {
		return this.manager;
	}
	
	public void setManager(String manager) {
		this.manager = manager;
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
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Date getPlanPayDate() {
		return planPayDate;
	}

	public void setPlanPayDate(Date planPayDate) {
		this.planPayDate = planPayDate;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public String getRelatedSite() {
		return relatedSite;
	}

	public void setRelatedSite(String relatedSite) {
		this.relatedSite = relatedSite;
	}

	public String getRelatedCountId() {
		return relatedCountId;
	}

	public void setRelatedCountId(String relatedCountId) {
		this.relatedCountId = relatedCountId;
	}
	
}
