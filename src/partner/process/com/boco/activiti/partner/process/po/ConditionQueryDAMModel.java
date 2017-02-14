package com.boco.activiti.partner.process.po;

/**
 * 
 * @author wyl 
 * 20150430
 * 附件下载查询条件
 */
public class ConditionQueryDAMModel {
	/**派单开始时间*/
	private String sendStartTime;
	/**工单结束时间*/
	private String sendEndTime;
	
	/**地市*/
	private String city;
	/**查询默认地市条件控制*/
	private String area;
	/**区县*/
	private String country;
	
	
	/**工单类型*/
	private String themeinterface;
	
	/**
	 * 所属环节
	 */
	private String taskdefkey;

	/**
	 * @return the sendStartTime
	 */
	public String getSendStartTime() {
		return sendStartTime;
	}

	/**
	 * @param sendStartTime the sendStartTime to set
	 */
	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}

	/**
	 * @return the sendEndTime
	 */
	public String getSendEndTime() {
		return sendEndTime;
	}

	/**
	 * @param sendEndTime the sendEndTime to set
	 */
	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the sheetType
	 */
	public String getThemeinterface() {
		return themeinterface;
	}

	/**
	 * @param sheetType the sheetType to set
	 */
	public void setThemeinterface(String sheetType) {
		this.themeinterface = sheetType;
	}

	/**
	 * @return the taskdefkey
	 */
	public String getTaskdefkey() {
		return taskdefkey;
	}

	/**
	 * @param taskdefkey the taskdefkey to set
	 */
	public void setTaskdefkey(String taskdefkey) {
		this.taskdefkey = taskdefkey;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
