package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 区县预算金额
 */

public class PnrActCountyBudgetAmount implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//地市ID
	private String cityId;
	//区县名称
	private String cityName;
	//年份
	private String budgetYear;
	//季度
	private String budgetQuarter;
	//预算金额
	private Double budgetAmount;
	//排序码
	private String orderCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBudgetYear() {
		return budgetYear;
	}
	public void setBudgetYear(String budgetYear) {
		this.budgetYear = budgetYear;
	}
	public String getBudgetQuarter() {
		return budgetQuarter;
	}
	public void setBudgetQuarter(String budgetQuarter) {
		this.budgetQuarter = budgetQuarter;
	}
	public Double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
}
