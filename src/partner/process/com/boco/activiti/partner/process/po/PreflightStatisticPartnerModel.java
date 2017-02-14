package com.boco.activiti.partner.process.po;

/**
 * User: wangchang
 * Date: 13-9-17
 * Time: 下午2:45
 */
public class PreflightStatisticPartnerModel {
    private String city;
    private int auditNum;//申报项目数量
    private String auditMoney;//申报项目金额
    private String auditPercent;//比率
    
    private int passNum;//审批合格数
    private String passMoney;//审批合格金额
    private String passPercent;//会审合格率
    
    private int noPassNum;//不合格数量
    private String noPassNumMoney;//不合格金额
    
    private int auditPassNum;//审批同意项目数量
    private String auditPassMoney;//审批同意金额
    private String auditPassPercent;//审批同意比率
    
    private String budgetAmount;//预算金额
    
    private String themeinterface;
    private String taskdefkey;
    private String month;
    private String quarter;
    private String country;
    
    
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAuditNum() {
		return auditNum;
	}

	public void setAuditNum(int auditNum) {
		this.auditNum = auditNum;
	}

	public String getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(String auditMoney) {
		this.auditMoney = auditMoney;
	}

	

	public int getPassNum() {
		return passNum;
	}

	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}

	public String getPassMoney() {
		return passMoney;
	}

	public void setPassMoney(String passMoney) {
		this.passMoney = passMoney;
	}

	

	public int getNoPassNum() {
		return noPassNum;
	}

	public void setNoPassNum(int noPassNum) {
		this.noPassNum = noPassNum;
	}

	public String getNoPassNumMoney() {
		return noPassNumMoney;
	}

	public void setNoPassNumMoney(String noPassNumMoney) {
		this.noPassNumMoney = noPassNumMoney;
	}

	public int getAuditPassNum() {
		return auditPassNum;
	}

	public void setAuditPassNum(int auditPassNum) {
		this.auditPassNum = auditPassNum;
	}

	public String getAuditPassMoney() {
		return auditPassMoney;
	}

	public void setAuditPassMoney(String auditPassMoney) {
		this.auditPassMoney = auditPassMoney;
	}


	public String getAuditPassPercent() {
		return auditPassPercent;
	}

	public void setAuditPassPercent(String auditPassPercent) {
		this.auditPassPercent = auditPassPercent;
	}

	public String getAuditPercent() {
		return auditPercent;
	}

	public void setAuditPercent(String auditPercent) {
		this.auditPercent = auditPercent;
	}

	public String getPassPercent() {
		return passPercent;
	}

	public void setPassPercent(String passPercent) {
		this.passPercent = passPercent;
	}

	public String getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getThemeinterface() {
		return themeinterface;
	}

	public void setThemeinterface(String themeinterface) {
		this.themeinterface = themeinterface;
	}

	public String getTaskdefkey() {
		return taskdefkey;
	}

	public void setTaskdefkey(String taskdefkey) {
		this.taskdefkey = taskdefkey;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

}
