package com.boco.eoms.partner.personnel.util.fusionchart.bean;

import java.util.List;
/**
 * <p>
 * Title:Chart数据
 * </p>
 * <p>
 * Description: Chart数据
 * </p>
 * <p>
 * Jul 23, 2012 9:06:53 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class Chart {
	
	/**
	 * 数字后缀
	 */
	private String numberSuffix;
	private int palette;
	private int lineThickness;
	/**
	 * 图像上是否显示说明
	 * 1：显示 0：不显示
	 */
	private int showValues;
	/**
	 *X 轴 数据显示方式
	 *ROTATE：旋转 
	 *WRAP ：如果它的长度超过所分配的面积包装标签的文字.
	 *STAGGER ： 分成多行的标签. 
	 *
	 */
	private String labelDisplay;
	/**
	 * 旋转角度
	 * 1：45度 0：垂直
	 */
	private int slantLabels;
	private int areaOverColumns;
	private int useRoundEdges;
	/**
	 * 图标标题
	 */
	private String caption;
	/**
	 * 图例 位置 
	 * BOTTOM：底部
	 * RIGHT ：右侧
	 */
	private String legendPosition; 
	/**
	 * 字体大小
	 */
	private String baseFontSize;

	
	private List<Category> categories;
	private List<Dataset> dataset;
	public Chart() {
		// TODO Auto-generated constructor stub
	}
	public Chart(String title,String numberSuffix,int showValus,List<Category> categories,List<Dataset> dataset) {
		this.caption = title;
		this.numberSuffix = numberSuffix==null?"":numberSuffix;
		this.palette = 3;
		this.lineThickness = 5;
		this.showValues  =showValus;  
		this.labelDisplay = "WRAP"; 
		this.slantLabels  = 1;
		this.areaOverColumns  = 0;
		this.useRoundEdges  = 1;
		this.categories = categories;
		this.dataset = dataset;
		this.legendPosition = "BOTTOM";
		this.baseFontSize = "14";
	}
	public String getNumberSuffix() {
		return numberSuffix;
	}
	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}
	public int getPalette() {
		return palette;
	}
	public void setPalette(int palette) {
		this.palette = palette;
	}
	public int getLineThickness() {
		return lineThickness;
	}
	public void setLineThickness(int lineThickness) {
		this.lineThickness = lineThickness;
	}
	public int getShowValues() {
		return showValues;
	}
	public void setShowValues(int showValues) {
		this.showValues = showValues;
	}
	public String getLabelDisplay() {
		return labelDisplay;
	}
	public void setLabelDisplay(String labelDisplay) {
		this.labelDisplay = labelDisplay;
	}
	public int getSlantLabels() {
		return slantLabels;
	}
	public void setSlantLabels(int slantLabels) {
		this.slantLabels = slantLabels;
	}
	public int getAreaOverColumns() {
		return areaOverColumns;
	}
	public void setAreaOverColumns(int areaOverColumns) {
		this.areaOverColumns = areaOverColumns;
	}
	public int getUseRoundEdges() {
		return useRoundEdges;
	}
	public void setUseRoundEdges(int useRoundEdges) {
		this.useRoundEdges = useRoundEdges;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<Dataset> getDataset() {
		return dataset;
	}
	public void setDataset(List<Dataset> dataset) {
		this.dataset = dataset;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getLegendPosition() {
		return legendPosition;
	}
	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}
	public String getBaseFontSize() {
		return baseFontSize;
	}
	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}
		
}