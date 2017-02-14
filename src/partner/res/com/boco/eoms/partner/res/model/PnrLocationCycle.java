package com.boco.eoms.partner.res.model;

/**
 * 线路巡检位置信息上报周期设置
 * @author ZKQ
 *
 */
public class PnrLocationCycle {
	private String id;
	/**巡检执行方式*/
	private String executeType;
	/**自动上报位置信息时间间隔*/
	private String reportInterval;
	private String city;
	private String country;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExecuteType() {
		return executeType;
	}
	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}
	public String getReportInterval() {
		return reportInterval;
	}
	public void setReportInterval(String reportInterval) {
		this.reportInterval = reportInterval;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
