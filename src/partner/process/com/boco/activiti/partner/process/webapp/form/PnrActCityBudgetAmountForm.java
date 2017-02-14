package com.boco.activiti.partner.process.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 地市预算金额表单
 * @author WangJun
 *
 */

public class PnrActCityBudgetAmountForm extends BaseForm implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//地市ID
	private String cityId;
	//地市名称
	private String cityName;
	//年份
	private String budgetYear;
	//季度
	private String budgetQuarter;
	//预算金额
	private Integer budgetAmount;
	
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
	public Integer getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Integer budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	
}
