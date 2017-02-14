package com.boco.eoms.partner.baseinfo.webapp.form;

/**
 * <p>
 * Title:线路按合作伙伴名称和地市统计
 * </p>
 * author:wangjunfeng
 * 2010-3-31 
 * 
 */
public class LineStatReportForm  {


	/**
	 * 合作伙伴ID大
	 */
	private String bigProviderID;

	/**
	 * 合作伙伴名称大
	 */
	private String bigProvider;

	/**
	 * 合作伙伴名称
	 */
	private String provider;
	/**
	 * 地市ID
	 */
	private String region;
	/**
	 * 县区ID
	 */
	private String city;
	/**
	 * 线路长度（公里）
	 */
	private Integer lineLength;
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBigProvider() {
		return bigProvider;
	}
	public void setBigProvider(String bigProvider) {
		this.bigProvider = bigProvider;
	}
	public Integer getLineLength() {
		return lineLength;
	}
	public void setLineLength(Integer lineLength) {
		this.lineLength = lineLength;
	}
	public String getBigProviderID() {
		return bigProviderID;
	}
	public void setBigProviderID(String bigProviderID) {
		this.bigProviderID = bigProviderID;
	}
	

}