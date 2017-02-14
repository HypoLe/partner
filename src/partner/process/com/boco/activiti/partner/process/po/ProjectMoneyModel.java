package com.boco.activiti.partner.process.po;

import java.io.Serializable;

public class ProjectMoneyModel implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cityId;//地市id
	private String taskType;
	private double projectMoney;//预算金额
	private double cityCount;//常规	市公司提交数量
	private double cityMoney;//常规	市公司提交金额
	private double provinceCount;//常规	省公司审批通过数量
	private double provinceMoney;//常规	省公司审批通过金额
	private double balance;//常规	预算差额
	
	private double cityCountCrash; //应急 市公司提交数量
	private double cityMoneyCrash;//应急	市公司提交金额
	private double provinceCountCrash;//应急	省公司审批通过数量
	private double provinceMoneyCrash;//应急	省公司审批通过金额
	private double balanceCrash;//应急	预算差额
	
	private double cityCountRepair; //大修改造 市公司提交数量
	private double cityMoneyRepair;//大修改造	市公司提交金额
	private double provinceCountRepair;//大修改造	省公司审批通过数量
	private double provinceMoneyRepair;//大修改造	省公司审批通过金额
	
	
	
	
	public double getCityCountRepair() {
		return cityCountRepair;
	}
	public void setCityCountRepair(double cityCountRepair) {
		this.cityCountRepair = cityCountRepair;
	}
	public double getCityMoneyRepair() {
		return cityMoneyRepair;
	}
	public void setCityMoneyRepair(double cityMoneyRepair) {
		this.cityMoneyRepair = cityMoneyRepair;
	}
	public double getProvinceCountRepair() {
		return provinceCountRepair;
	}
	public void setProvinceCountRepair(double provinceCountRepair) {
		this.provinceCountRepair = provinceCountRepair;
	}
	public double getProvinceMoneyRepair() {
		return provinceMoneyRepair;
	}
	public void setProvinceMoneyRepair(double provinceMoneyRepair) {
		this.provinceMoneyRepair = provinceMoneyRepair;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public double getProjectMoney() {
		return projectMoney;
	}
	public void setProjectMoney(double projectMoney) {
		this.projectMoney = projectMoney;
	}
	public double getCityCount() {
		return cityCount;
	}
	public void setCityCount(double cityCount) {
		this.cityCount = cityCount;
	}
	public double getCityMoney() {
		return cityMoney;
	}
	public void setCityMoney(double cityMoney) {
		this.cityMoney = cityMoney;
	}
	public double getProvinceCount() {
		return provinceCount;
	}
	public void setProvinceCount(double provinceCount) {
		this.provinceCount = provinceCount;
	}
	public double getProvinceMoney() {
		return provinceMoney;
	}
	public void setProvinceMoney(double provinceMoney) {
		this.provinceMoney = provinceMoney;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public ProjectMoneyModel() {

	}
	public double getCityCountCrash() {
		return cityCountCrash;
	}
	public void setCityCountCrash(double cityCountCrash) {
		this.cityCountCrash = cityCountCrash;
	}
	public double getCityMoneyCrash() {
		return cityMoneyCrash;
	}
	public void setCityMoneyCrash(double cityMoneyCrash) {
		this.cityMoneyCrash = cityMoneyCrash;
	}
	public double getProvinceCountCrash() {
		return provinceCountCrash;
	}
	public void setProvinceCountCrash(double provinceCountCrash) {
		this.provinceCountCrash = provinceCountCrash;
	}
	public double getProvinceMoneyCrash() {
		return provinceMoneyCrash;
	}
	public void setProvinceMoneyCrash(double provinceMoneyCrash) {
		this.provinceMoneyCrash = provinceMoneyCrash;
	}
	public double getBalanceCrash() {
		return balanceCrash;
	}
	public void setBalanceCrash(double balanceCrash) {
		this.balanceCrash = balanceCrash;
	}
		
	
}
