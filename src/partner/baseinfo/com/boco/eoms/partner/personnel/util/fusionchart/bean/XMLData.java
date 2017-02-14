package com.boco.eoms.partner.personnel.util.fusionchart.bean;
	/**
 * <p>
 * Title:填充XML文件 
 * </p>
 * <p>
 * Description:填充XML文件 主要数据字典 与数据库数据关联
 * </p>
 * <p>
 * Jul 27, 2012 2:16:30 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class XMLData {
	/**
	 * 柱状图 X轴 (lableName)
	 * 饼图 不使用该属性
	 */
	private String categoryLable;
	/**
	 * 图注
	 * 柱状图  对应XML的 dataset的seriesName
	 */
	private String datasetName;
	/**
	 * 柱状图  对应XML的 dataset的seriesNames
	 * 与values 数组值 一一对应   不采用map的原因是 操作不方便
	 */
	private String[] datasetNames;
	/**
	 * 统计结果数据
	 */
	private String value;
	/**
	 * 多个统计结果值
	 */
	private String[] values;
	public XMLData() {
	}
	public XMLData(String lableName,String setName,String value) {
		this.categoryLable = lableName;
		this.datasetName = setName;
		this.value = value;
	}
	public XMLData(String setName,String value) {
		this.datasetName = setName;
		this.value = value;
	}
	
	
	public String getCategoryLable() {
		return categoryLable;
	}

	public void setCategoryLable(String categoryLable) {
		this.categoryLable = categoryLable;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String[] getDatasetNames() {
		return datasetNames;
	}
	public void setDatasetNames(String[] datasetNames) {
		this.datasetNames = datasetNames;
	}
	
	
	
}
