package com.boco.eoms.partner.res.model;

/**
 * 到点率规则设置表
 * @author ZKQ
 *
 */
public class PnrArrivedRate {
	private String id;
	/**到点率*/
	private String arrivedRate;
	private String city;
	private String country;
	private String remark;
	
	public String getId() {
		return id;
	}
	public String getArrivedRate() {
		return arrivedRate;
	}
	public void setArrivedRate(String arrivedRate) {
		this.arrivedRate = arrivedRate;
	}
	public void setId(String id) {
		this.id = id;
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
