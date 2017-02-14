package com.boco.eoms.partner.inspect.webapp.form;

public class InspectPlanResCountFrom {
	/**
	 * 地市名称
	 */
	private String cityName;
	/**
	 * 地市ID
	 */
	private String cityId;
	/**
	 * 区县名称
	 */
	private String countryName;
	/**
	 * 区县ID
	 */
	private String countryId;
	/**
	 * 部门ID
	 */
	private String deptId;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 任务数量
	 */
	private int resNo;
	/**
	 * 完成数量
	 */
	private int completedNo;
	/**
	 * 未完成数量
	 */
	private int unfinishedNo;
	/**
	 * 异常数量
	 */
	private int exceptionNo;
	/**
	 * 未处理数量
	 */
	private int unhandledNo;
	/**
	 * 已处理数量
	 */
	private int handleNo;
	/**
	 * 完成百分比
	 */
	private String completedPercentage;
	/**
	 * 异常百分比
	 */
	private String exceptionPercentage;
	/**
	 * 已处理百分比
	 */
	private String handlePercentage;


	public String getCompletedPercentage() {
		return completedPercentage;
	}

	public void setCompletedPercentage(String completedPercentage) {
		this.completedPercentage = completedPercentage;
	}

	public String getExceptionPercentage() {
		return exceptionPercentage;
	}

	public void setExceptionPercentage(String exceptionPercentage) {
		this.exceptionPercentage = exceptionPercentage;
	}

	public String getHandlePercentage() {
		return handlePercentage;
	}

	public void setHandlePercentage(String handlePercentage) {
		this.handlePercentage = handlePercentage;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public int getCompletedNo() {
		return completedNo;
	}

	public void setCompletedNo(int completedNo) {
		this.completedNo = completedNo;
	}

	public int getUnfinishedNo() {
		return unfinishedNo;
	}

	public void setUnfinishedNo(int unfinishedNo) {
		this.unfinishedNo = unfinishedNo;
	}

	public int getExceptionNo() {
		return exceptionNo;
	}

	public void setExceptionNo(int exceptionNo) {
		this.exceptionNo = exceptionNo;
	}

	public int getResNo() {
		return resNo;
	}

	public void setResNo(int resNo) {
		this.resNo = resNo;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getUnhandledNo() {
		return unhandledNo;
	}

	public void setUnhandledNo(int unhandledNo) {
		this.unhandledNo = unhandledNo;
	}

	public int getHandleNo() {
		return handleNo;
	}

	public void setHandleNo(int handleNo) {
		this.handleNo = handleNo;
	}

}
