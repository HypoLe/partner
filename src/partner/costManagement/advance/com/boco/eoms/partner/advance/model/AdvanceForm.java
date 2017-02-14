package com.boco.eoms.partner.advance.model;

import com.boco.eoms.base.webapp.form.BaseForm;


public class AdvanceForm extends BaseForm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
	private String use;//使用用途
	private String level;//线路级别
	private String chargeUser;//负责人
	private String useUser;//使用人
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String money;//金额
	private String state;//状态 0为申请 1为通过 2为不通过
	private String agencyCompany;//代维公司
	private String useType;//使用类型，分为日常费用和工程费用
	private String approve;
	private String advanceName;
	private String type;//标识是补款申请还是预提款申请0为预提款，1为补款
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdvanceName() {
		return advanceName;
	}
	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getAgencyCompany() {
		return agencyCompany;
	}
	public void setAgencyCompany(String agencyCompany) {
		this.agencyCompany = agencyCompany;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getChargeUser() {
		return chargeUser;
	}
	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getUseUser() {
		return useUser;
	}
	public void setUseUser(String useUser) {
		this.useUser = useUser;
	}
	
	

}