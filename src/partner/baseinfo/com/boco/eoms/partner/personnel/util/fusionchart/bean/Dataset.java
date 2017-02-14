package com.boco.eoms.partner.personnel.util.fusionchart.bean;

import java.util.List;
/**
 * <p>
 * Title:图表数据
 * </p>
 * <p>
 * Description: 图表数据
 * </p>
 * <p>
 * Jul 23, 2012 9:06:53 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class Dataset {
	
	private String seriesName; //图注名
	/**
	 * 柱状图 颜色 
	 */
	private String color;
	private int showValue;
	private int lineThickness;
	private int yaxismaxvalue;//Y轴最大值
	private int anchorSides;
	private int anchorRadius;
	private int anchorAlpha;
	/**
	 * 设置 图注 参考的Y轴
	 * P：左边的Y轴
	 * S：右边的Y轴
	 */
	private String parentYAxis;
	private List<SetValue> set;
	
	public Dataset() {
		// TODO Auto-generated constructor stub
	}
	public Dataset(String seriesName,String color,List<SetValue> set) {
		this.seriesName = seriesName;
		this.color = color;
		this.set = set;
		this.showValue =1;
		this.lineThickness = 2;
		this.yaxismaxvalue = 100;
		this.parentYAxis = "P";
		this.anchorSides = 10;
		this.anchorRadius = 2;
		this.anchorAlpha = 100;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getShowValue() {
		return showValue;
	}
	public void setShowValue(int showValue) {
		this.showValue = showValue;
	}
	public int getLineThickness() {
		return lineThickness;
	}
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}
	public int getYaxismaxvalue() {
		return yaxismaxvalue;
	}
	public void setYaxismaxvalue(int yaxismaxvalue) {
		this.yaxismaxvalue = yaxismaxvalue;
	}
	public int getAnchorSides() {
		return anchorSides;
	}
	public void setAnchorSides(int anchorSides) {
		this.anchorSides = anchorSides;
	}
	public int getAnchorRadius() {
		return anchorRadius;
	}
	public void setAnchorRadius(int anchorRadius) {
		this.anchorRadius = anchorRadius;
	}
	public int getAnchorAlpha() {
		return anchorAlpha;
	}
	public void setAnchorAlpha(int anchorAlpha) {
		this.anchorAlpha = anchorAlpha;
	}
	public List<SetValue> getSet() {
		return set;
	}
	public void setSet(List<SetValue> set) {
		this.set = set;
	}
	public String getParentYAxis() {
		return parentYAxis;
	}
	public void setParentYAxis(String parentYAxis) {
		this.parentYAxis = parentYAxis;
	}
	
	
}
