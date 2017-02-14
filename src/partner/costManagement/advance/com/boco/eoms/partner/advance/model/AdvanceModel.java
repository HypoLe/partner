package com.boco.eoms.partner.advance.model;

import java.util.Date;


public class AdvanceModel {
	private String id;
	private String advanceName;
	private String use;//使用用途
	private String level;//线路级别
	private String chargeUser;//负责人
	private String useUser;//使用人
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String money;//金额
	private String state;//状态 0为申请 1为通过 2为不通过，3为复查
	private String agencyCompany;//代维公司
	private String useType;//使用类型，分为日常费用和工程费用
	private String isDeleted;//删除标记
	private String approve;//提交部门
	private String creatType;//提交人部门
	private String checkType;//复查状态,0为不复查，1为复查。
	private String checkAllow;//审核意见
	private String reRemark;//复查批注
	private String type;//标识是补款申请还是预提款申请0为预提款，1为补款
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCheckAllow() {
		return checkAllow;
	}
	public void setCheckAllow(String checkAllow) {
		this.checkAllow = checkAllow;
	}
	public String getAdvanceName() {
		return advanceName;
	}
	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getReRemark() {
		return reRemark;
	}
	public void setReRemark(String reRemark) {
		this.reRemark = reRemark;
	}
	public String getCreatType() {
		return creatType;
	}
	public void setCreatType(String creatType) {
		this.creatType = creatType;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getChargeUser() {
		return chargeUser;
	}
	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
