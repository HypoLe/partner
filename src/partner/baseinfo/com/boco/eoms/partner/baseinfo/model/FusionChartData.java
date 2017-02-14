package com.boco.eoms.partner.baseinfo.model;



public class FusionChartData {
	/**
	 * 显示标题
	 */
	String title;
	/**
	 * 整数数据
	 */
	String numForInt;
	/**
	 * 小数数据
	 */
	float numForFloat;
	/**
	 * 超链接
	 */
	String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNumForInt() {
		return numForInt;
	}
	public void setNumForInt(String numForInt) {
		this.numForInt = numForInt;
	}
	public float getNumForFloat() {
		return numForFloat;
	}
	public void setNumForFloat(float numForFloat) {
		this.numForFloat = numForFloat;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}