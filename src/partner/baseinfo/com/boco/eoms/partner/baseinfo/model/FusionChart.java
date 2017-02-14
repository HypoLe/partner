package com.boco.eoms.partner.baseinfo.model;

import com.boco.eoms.base.model.BaseObject;


public class FusionChart {
	/**
	 * 显示主标题
	 */
	String caption;
	/**
	 * 显示副标题
	 */
	String subCaption;
	/**
	 * 横向坐标轴(x轴)名称
	 */
	String xAxisName;
	/**
	 * 纵向坐标轴(y轴)名称
	 */
	String yAxisName;
	/**
	 * 是否动画显示数据，默认为1(True)
	 */
	String animation;
	/**
	 *  是否显示横向坐标轴(x轴)标签名称
	 */
	String showNames;
	/**
	 *  是否旋转显示标签，默认为0(False):横向显示
	 */
	String rotateNames;
	/**
	 *  是否在图表显示对应的数据值，默认为1(True)
	 */
	String showValues;
	/**
	 *  指定纵轴(y轴)最小值，数字
	 */
	String yAxisMinValue;
	/**
	 *  指定纵轴(y轴)最大值，数字
	 */
	String yAxisMaxValue;
	/**
	 *  是否显示图表限值(y轴最大、最小值)，默认为1(True)
	 */
	String showLimits;
	/**
	 *  分类标题显示方式
	 */	
	String labelDisplay;
	
	/**
	 *  分类标题显示倾斜角度
	 */	
	String slantLabels;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getSubCaption() {
		return subCaption;
	}
	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}
	public String getXAxisName() {
		return xAxisName;
	}
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}
	public String getYAxisName() {
		return yAxisName;
	}
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}
	public String getAnimation() {
		return animation;
	}
	public void setAnimation(String animation) {
		this.animation = animation;
	}
	public String getShowNames() {
		return showNames;
	}
	public void setShowNames(String showNames) {
		this.showNames = showNames;
	}
	public String getRotateNames() {
		return rotateNames;
	}
	public void setRotateNames(String rotateNames) {
		this.rotateNames = rotateNames;
	}
	public String getShowValues() {
		return showValues;
	}
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	public String getYAxisMinValue() {
		return yAxisMinValue;
	}
	public void setYAxisMinValue(String axisMinValue) {
		yAxisMinValue = axisMinValue;
	}
	public String getYAxisMaxValue() {
		return yAxisMaxValue;
	}
	public void setYAxisMaxValue(String axisMaxValue) {
		yAxisMaxValue = axisMaxValue;
	}
	public String getShowLimits() {
		return showLimits;
	}
	public void setShowLimits(String showLimits) {
		this.showLimits = showLimits;
	}
	public String getLabelDisplay() {
		return labelDisplay;
	}
	public void setLabelDisplay(String labelDisplay) {
		this.labelDisplay = labelDisplay;
	}
	public String getSlantLabels() {
		return slantLabels;
	}
	public void setSlantLabels(String slantLabels) {
		this.slantLabels = slantLabels;
	}
}