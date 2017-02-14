package com.boco.eoms.commons.mms.base.util;


public class Display
{
	/**
	 * 报表名称
	 */
	public String title = "标题";
	/**
	 * 横坐标字符串
	 */
	public String targetString = ""; 
	/**
	 * 纵坐标字符串
	 */
	public String valueString = "value";
	/**
	 * 指标值的类型  percent:百分比
	 */
	public String numberFormat = "";
	/**
	 * 标识的角度 范围：UP_45，UP_90，DOWN_45，DOWN_90，STANDARD
	 */
	public String labelPositions = "DOWN_45";
	/**
	 * 是否显示指标值 true： 显示
	 */
	public boolean itemLabelsVisible = true;
	
	/**
	 * 绘图质量 float 类型 并且小于1 ：例如:0.8f
	 */
	public float quality = 0.8f; 
	/**
	 * 大小 宽， int 例如：400
	 */
	public int width = 400;
	/**
	 * 大小 高 ，int 例如：300
	 */
	public int height = 300;
	/**
	 * 字体，int 例如：10
	 */
	public int fontSize = 10;
}
