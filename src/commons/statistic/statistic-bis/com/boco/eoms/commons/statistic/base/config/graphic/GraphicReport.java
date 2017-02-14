package com.boco.eoms.commons.statistic.base.config.graphic;

public class GraphicReport {

	/**
	 * 唯一标识统计图形报表
	 */
	private String name = null;
	
	/**
	 * 图形报表标题
	 */
	private String title = null;
	
	/**
	 * 横坐标字符串
	 */
	private String targetString = null;
	
	/**
	 * 纵坐标字符串
	 */
	private String valueString = null;
	
	/**
	 * 指标值的类型  percent:百分比
	 */
	private String numberFormat = null;
	
	/**
	 * 标识的角度 范围：UP_45，UP_90，DOWN_45，DOWN_90，STANDARD
	 */
	private String labelPositions = "DOWN_45";
	
	/**
	 * 是否显示指标值 true： 显示
	 */
	private boolean itemLabelsVisible = true;
	
	/**
	 * 绘图质量 float 类型 并且小于1 ：例如:0.8f
	 */
	private float quality = 0.8f; 
	/**
	 * 大小 宽， int 例如：400
	 */
	private int width = 400;
	/**
	 * 大小 高 ，int 例如：300
	 */
	private int height = 300;
	/**
	 * 字体，int 例如：10
	 */
	private int fontSize = 10;
	
	/**
	 * 标识图形报表的类型，是线，柱，饼，线柱结合等，取值范围:
	 */
	private String configPath = null;
	
	/**
	 * 显示图形报表配置文件路径
	 */
	private String flashPath = null;
	
	/**
	 * 标识图形报表的类型，是线，柱，饼，线柱结合等，取值范围:colum\line\columnline\pie
	 */
	private String type = null;
	
	/**
	 * 可以供选择的图形报表类型
	 */
	private String selectType = null;
	
	private SummaryDefine[] SummaryDefines = null;

	private FieldDefine[] fieldDefines = null;
	
	/**
	 * 设置GraphicReport下的所有FieldDefine节点的type
	 */
	public void setFieldDefinesType(String type)
	{
		FieldDefine[] fieldDefines = getFieldDefines();
		for(int i=0;i<fieldDefines.length;i++)
		{
			fieldDefines[i].setType(type);
		}
	}

	public SummaryDefine[] getSummaryDefines() {
		return SummaryDefines;
	}

	public void setSummaryDefines(SummaryDefine[] summaryDefines) {
		SummaryDefines = summaryDefines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public String getFlashPath() {
		return flashPath;
	}
	
	public void setConfigAndFalshPath(String type)
	{
		String template = "statisticfile/swfs/template/";
		String swfs = "statisticfile/swfs/swfs/";
		this.type = type; 
		if(type.equalsIgnoreCase("line"))
		{
			//如果没有配置configPath就使用默认的
			if(configPath == null || "".equalsIgnoreCase(configPath))
			{
				configPath = template + "2DLine.xml";
			}
			flashPath = swfs + "2DLine.swf";
		}
		else if(type.equalsIgnoreCase("column"))
		{
			if(configPath == null || "".equalsIgnoreCase(configPath))
			{
				configPath = template + "2D-Column.xml";
			}
			flashPath = swfs + "2DColumn.swf";
		}
		else if(type.equalsIgnoreCase("pie"))
		{
			if(configPath == null || "".equalsIgnoreCase(configPath))
			{
				configPath = template + "2D-Pie.xml";
			}
			flashPath = swfs + "2DPie.swf";
		}
		else if(type.equalsIgnoreCase("columnline"))
		{
			if(configPath == null || "".equalsIgnoreCase(configPath))
			{
				configPath = template + "2D-Column-Line.xml";
			}
			flashPath = swfs + "2DColumn_Line.swf";
		}
	}

	public void setFlashPath(String flashPath) {
		this.flashPath = flashPath;
	}

	public FieldDefine[] getFieldDefines() {
		return fieldDefines;
	}
	
	public FieldDefine getFieldDefinesForId(String id) {
		
		for(int i=0;i<fieldDefines.length;i++)
		{
			if(fieldDefines[i].getId().equalsIgnoreCase(id))
			{
				return fieldDefines[i];
			}
		}
		
		return null;
	}

	public void setFieldDefines(FieldDefine[] fieldDefines) {
		this.fieldDefines = fieldDefines;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getTargetString() {
		return targetString;
	}

	public void setTargetString(String targetString) {
		this.targetString = targetString;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public String getLabelPositions() {
		return labelPositions;
	}

	public void setLabelPositions(String labelPositions) {
		this.labelPositions = labelPositions;
	}

	public boolean isItemLabelsVisible() {
		return itemLabelsVisible;
	}

	public void setItemLabelsVisible(boolean itemLabelsVisible) {
		this.itemLabelsVisible = itemLabelsVisible;
	}

	public float getQuality() {
		return quality;
	}

	public void setQuality(float quality) {
		this.quality = quality;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}
